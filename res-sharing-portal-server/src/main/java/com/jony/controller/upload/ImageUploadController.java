package com.jony.controller.upload;

import com.jony.exception.ErrorCode;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import com.jony.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@RequestMapping("/upload")
public class ImageUploadController {

    @Resource
    private FileService fileService;

    /**
     * feat: 图片上传接口
     *
     * @param img 图片
     * @return BaseResponse
     */
    @PostMapping("/img")
    public Result<String> uploadImg(@RequestParam("file") MultipartFile img) {
        // 1.判断文件类型
        // 获取原始文件名
        if (img == null) {
            return Result.ok("failed");
        }
        String originalFilename = img.getOriginalFilename();
        // 对原始文件名进行判断(非 .jpg;.jpeg;.png;.bmp;.gif;.webp;.ico 抛出异常)
        if (originalFilename != null &&
                !originalFilename.endsWith(".png") &&
                !originalFilename.endsWith(".jpg") &&
                !originalFilename.endsWith(".jpeg") &&
                !originalFilename.endsWith(".bmp") &&
                !originalFilename.endsWith(".gif") &&
                !originalFilename.endsWith(".webp") &&
                !originalFilename.endsWith(".ico")) {
            return Result.error(ErrorCode.UPLOAD_FAILED);
        }
        // 2.如果判断通过上传文件到OSS
        String filePath = null;
        if (originalFilename != null) {
            filePath = PathUtils.generateFilePath(originalFilename);
        }
        String url = fileService.uploadOss(img, filePath);//  2099/2/3/test.png
        // 3.图片链接持久化，存入MySQL
        return Result.ok(url);
    }


}