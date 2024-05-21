package com.jony.controller.upload;

import com.jony.api.CommonResult;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
@Tag(name = "文件上传与下载")
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/multi/upload")
    @Operation(summary = "多文件上传")
    public CommonResult<?> upload(HttpServletRequest request,
                                  @RequestParam("file") MultipartFile[] fileList) throws Exception {
        // String uploadUser = request.getParameter("uploadUser");
        // if (uploadUser.isEmpty()) {
        //     return CommonResult.failed("upload-user is empty");
        // }
        // log.info("upload-user:{}", uploadUser);
        List<List<HashMap<String, String>>> urlList = new ArrayList<>();
        for (MultipartFile multipartFile : fileList) {
            ArrayList<HashMap<String, String>> item = new ArrayList<>();
            // 解析文件信息和保存
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                return CommonResult.failed("file name has mistake");
            }
            String filePath = PathUtils.generateFilePath(originalFilename);
            String url = fileService.uploadOss(multipartFile, filePath);
            HashMap<String, String> name = new HashMap<>();
            HashMap<String, String> fileUrl = new HashMap<>();
            name.put("name", originalFilename);
            fileUrl.put("url", url);
            item.add(name);
            item.add(fileUrl);
            urlList.add(item);
        }
        return CommonResult.success(urlList);
    }


    /**
     * 文件下载
     */
    @GetMapping("/single/download")
    @Operation(summary = "单文件下载")
    public CommonResult<?> upload(@RequestParam("fileName") String fileName,
                                  HttpServletResponse response) throws Exception {
        return null;
    }
}