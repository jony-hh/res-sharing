package com.jony.controller.res;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.convert.ResDocumentConvert;
import com.jony.dto.ResDocumentDTO;
import com.jony.entity.ResDocument;
import com.jony.entity.RmTagResRelation;
import com.jony.mapper.RmTagMapper;
import com.jony.mapper.RmTagResRelationMapper;
import com.jony.service.ResDocumentService;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import com.jony.vo.ResDocumentVO;
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
import java.util.stream.Collectors;

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
    RmTagMapper tagMapper;

    @Resource
    RmTagResRelationMapper tagResRelationMapper;
    @Resource
    FileService fileService;

    @PostMapping("/addDocument")
    @Operation(summary = "用户上传文档资源")
    public CommonResult<?> addDocument(@RequestBody ResDocumentDTO resDocumentDTO) {
        // 判断标题是否敏感
        // String content = resDocumentDTO.getTitle();
        // List<String> wordList = SensitiveWordHelper.findAll(content);
        // if (!wordList.isEmpty()) {
        //     return CommonResult.failed("标题违规，请重试！");
        // }
        ResDocument resDocument = ResDocumentConvert.INSTANCE.toResDocument(resDocumentDTO);
        if (resDocumentDTO.getName().contains("docx") || resDocumentDTO.getName().contains("doc")) {
            resDocument.setTypeUrl("https://s21.ax1x.com/2024/04/10/pFOhc5Q.png");
        }
        if (resDocumentDTO.getName().contains("pdf")) {
            resDocument.setTypeUrl("https://s21.ax1x.com/2024/04/10/pFOhR8s.png");
        }
        if (resDocumentDTO.getName().contains("ppt") || resDocumentDTO.getName().contains("pptx")) {
            resDocument.setTypeUrl("https://s21.ax1x.com/2024/04/10/pFOhR8s.png");
        }
        if (resDocumentDTO.getName().contains("xls") || resDocumentDTO.getName().contains("xlsx")) {
            resDocument.setTypeUrl("https://s21.ax1x.com/2024/04/10/pFOhO2R.png");
        }
        // 雪花漂移id
        long id = YitIdHelper.nextId();
        resDocument.setId(id);
        resDocument.setPublishStatus(0);
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
        List<ResDocumentVO> newDocumentList = documentList.stream()
                .map(document -> {
                    // 进行对象转换的逻辑，将document转为ResDocumentVO
                    ResDocumentVO resDocumentVO = ResDocumentConvert.INSTANCE.toResDocumentVO(document);
                    // 查询tags
                    // 这里的tags是通过document的id查询的，所以需要将document的id传进去
                    LambdaQueryWrapper<RmTagResRelation> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(RmTagResRelation::getResId, document.getId());
                    List<RmTagResRelation> rmTagResRelations = tagResRelationMapper.selectList(wrapper);
                    List<String> tags = rmTagResRelations.stream()
                            .map(rmTagResRelation -> tagMapper.selectById(rmTagResRelation.getTagId()).getName())
                            .collect(Collectors.toList());
                    resDocumentVO.setTags(tags);
                    return resDocumentVO;
                })
                .collect(Collectors.toList());
        return CommonResult.success(newDocumentList);
    }

    @GetMapping("/single")
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

