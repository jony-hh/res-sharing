package com.jony.controller.res;


import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.convert.ResVideoConvert;
import com.jony.dto.ResVideoDTO;
import com.jony.entity.ResVideo;
import com.jony.service.ResVideoService;
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
 * resVideo表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:27:08
 */
@RestController
@RequestMapping("res/video")
@Tag(name = "视频课程接口")
@Slf4j
public class ResVideoController {

    @Resource
    private ResVideoService resVideoService;

    @Resource
    FileService fileService;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有视频课程数据")
    public CommonResult<?> fetchWhole() {
        List<ResVideo> documentList = resVideoService.fetchWhole();
        return CommonResult.success(documentList);
    }

    @PostMapping("/addVideo")
    @Operation(summary = "用户上传视频课程资源")
    public CommonResult<?> addVideo(@RequestBody ResVideoDTO resVideoDTO) {
        ResVideo resVideo = ResVideoConvert.INSTANCE.toResVideo(resVideoDTO);
        // 雪花漂移id
        long id = YitIdHelper.nextId();
        resVideo.setId(id);
        String resultMessage = resVideoService.addVideo(resVideo);
        return CommonResult.success(resVideo, resultMessage);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询视频课程数据")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResVideo> documentList = resVideoService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(documentList);
    }

    @GetMapping("/fetchById")
    @Operation(summary = "根据id查询单个视频课程数据")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResVideo documentList = resVideoService.fetchById(id);
        return CommonResult.success(documentList);
    }


    @PostMapping("/uploadVideo")
    @Operation(summary = "用户上传视频课程资源")
    public CommonResult<?> uploadDocument(HttpServletRequest request,
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

