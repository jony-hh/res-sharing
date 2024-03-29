package com.jony.controller.res;


import com.github.yitter.idgen.YitIdHelper;
import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.convert.ResDocumentConvert;
import com.jony.dto.ResDocumentDTO;
import com.jony.entity.ResDocument;
import com.jony.service.ResDocumentService;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * resDocument表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:26:17
 */
@RestController
@RequestMapping("res/document")
@Tag(name = "文档接口")
@Slf4j
public class ResDocumentController {

    @Resource
    private ResDocumentService resDocumentService;

    @Resource
    FileService fileService;

    @PostMapping("/addDocument")
    @Operation(summary = "用户上传文档资源")
    public CommonResult<?> addDocument(@RequestBody ResDocumentDTO resDocumentDTO) {
        ResDocument resDocument = ResDocumentConvert.INSTANCE.toResDocument(resDocumentDTO);
        // 雪花漂移id
        long id = YitIdHelper.nextId();
        resDocument.setId(id);
        resDocument.setPublishStatus(1);
        String resultMessage = resDocumentService.addDocument(resDocument);
        return CommonResult.success(resDocument, resultMessage);
    }


    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有文档数据")
    public CommonResult<?> fetchWhole() {
        List<ResDocument> documentList = resDocumentService.fetchWhole();
        return CommonResult.success(documentList);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询文档数据")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResDocument> documentList = resDocumentService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(documentList);
    }

    @GetMapping("/fetchById")
    @Operation(summary = "根据id查询单个文档数据")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResDocument documentList = resDocumentService.fetchById(id);
        return CommonResult.success(documentList);
    }


    @PostMapping("/uploadDocument")
    @Operation(summary = "用户上传文档资源")
    public CommonResult<?> uploadDocument(HttpServletRequest request,
                                          @RequestParam("id") Long id,
                                          @RequestParam("file") MultipartFile[] fileList) throws Exception {
        String uploadUser = request.getParameter("uploadUser");
        if (uploadUser.isEmpty()) {
            return CommonResult.failed("upload-user is empty");
        }
        log.info("upload-user:{}", uploadUser);
        List<HashMap<String, String>> urlList = new ArrayList<>();
        for (MultipartFile multipartFile : fileList) {
            // 解析文件信息和保存
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                return CommonResult.failed("file name has mistake");
            }
            String filePath = PathUtils.generateFilePath(originalFilename);
            String url = fileService.uploadOss(multipartFile, filePath);
            HashMap<String, String> fileMap = new HashMap<>();
            fileMap.put(originalFilename, url);
            urlList.add(fileMap);
        }
        return CommonResult.success(urlList);
    }
}

