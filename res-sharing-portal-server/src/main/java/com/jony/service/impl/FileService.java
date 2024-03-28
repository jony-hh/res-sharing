package com.jony.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务类
 */
@Service
@Slf4j
@ConfigurationProperties(prefix = "storage.qiniu")
@Data
public class FileService {

    // 声明oss访问参数
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    /**
     * feat: qiniu 文件上传sdk
     *
     * @param imgFile  MultipartFile
     * @param filePath path
     * @return String
     */
    public String uploadOss(MultipartFile imgFile, String filePath) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, filePath, upToken, null, null);
                // 解析上传成功的结果
                // DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                // System.out.println(putRet.key);
                // System.out.println(putRet.hash);
                return domain + filePath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
            }
        } catch (Exception ex) {
            log.error("An exception has occurred");
        }
        return "An exception has occurred";
    }
}