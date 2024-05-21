package com.jony.controller.upload;

import com.jony.api.CommonResult;
import com.jony.exception.ErrorCode;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/img")
@Tag(name = "图片上传")
public class ImageUploadController {

    @Resource
    private FileService fileService;

    /**
     * feat: 图片上传接口
     *
     * @param imgList 图片
     * @return BaseResponse
     */
    @PostMapping("/upload")
    public CommonResult<List<List<HashMap<String, String>>>> uploadImg(@RequestParam("file") MultipartFile[] imgList) {
        // 1.判断文件类型
        // 获取原始文件名
        if (imgList == null) {
            return CommonResult.error(ErrorCode.UPLOAD_FAILED);
        }
        List<List<HashMap<String, String>>> urlList = new ArrayList<>();
        for (MultipartFile img : imgList) {
            String originalFilename = img.getOriginalFilename();
            ArrayList<HashMap<String, String>> item = new ArrayList<>();
            // 对原始文件名进行判断(非 .jpg;.jpeg;.png;.bmp;.gif;.webp;.ico 抛出异常)
            if (originalFilename != null &&
                    !originalFilename.endsWith(".png") &&
                    !originalFilename.endsWith(".jpg") &&
                    !originalFilename.endsWith(".jpeg") &&
                    !originalFilename.endsWith(".bmp") &&
                    !originalFilename.endsWith(".gif") &&
                    !originalFilename.endsWith(".webp") &&
                    !originalFilename.endsWith(".ico")) {
                return CommonResult.error(ErrorCode.UPLOAD_FAILED);
            }
            // 2.如果判断通过上传文件到OSS
            String filePath = null;
            if (originalFilename != null) {
                filePath = PathUtils.generateFilePath(originalFilename);
            }
            String url = fileService.uploadOss(img, filePath);//  2099/2/3/test.png
            // 3.返回上传信息
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


}