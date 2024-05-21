package com.jony.controller.res;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.convert.ResVideoConvert;
import com.jony.dto.ResVideoDTO;
import com.jony.dto.ResVideoDetailDTO;
import com.jony.entity.ResVideo;
import com.jony.entity.ResVideoDetail;
import com.jony.mapper.ResVideoDetailMapper;
import com.jony.service.ResVideoService;
import com.jony.service.impl.FileService;
import com.jony.utils.PathUtils;
import com.jony.vo.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ResVideoController {

    private final ResVideoService resVideoService;
    private final ResVideoDetailMapper resVideoDetailMapper;


    @Resource
    FileService fileService;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有视频课程数据")
    public CommonResult<?> fetchWhole() {
        List<ResVideo> documentList = resVideoService.fetchWhole();
        return CommonResult.success(documentList);
    }

    @GetMapping("/fetchByUserId")
    @Operation(summary = "取得某个用户所有视频课程数据")
    public CommonResult<?> fetchByUserId(@RequestParam("userId") Long userId) {
        List<ResVideo> documentList = resVideoService.fetchByUserId(userId);
        return CommonResult.success(documentList);
    }


    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询视频课程数据")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResVideo> documentList = resVideoService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(documentList);
    }

    @GetMapping("/single")
    @Operation(summary = "根据id查询单个课程的数据")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResVideo video = resVideoService.fetchById(id);
        LambdaQueryWrapper<ResVideoDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResVideoDetail::getVideoId, id);
        wrapper.eq(ResVideoDetail::getPublishStatus, 1);
        List<ResVideoDetail> resVideoDetails = resVideoDetailMapper.selectList(wrapper);
        CourseVO courseVO = ResVideoConvert.INSTANCE.toCourseVO(video);
        courseVO.setVideoDetailList(resVideoDetails);
        return CommonResult.success(courseVO);
    }


    @PostMapping("/addCourse")
    @Operation(summary = "用户创建课程")
    public CommonResult<?> addCourse(@RequestBody ResVideoDTO resVideoDTO) {
        // 判断标题是否敏感
        // String content = resVideoDTO.getCourseName();
        // List<String> wordList = SensitiveWordHelper.findAll(content);
        // if (!wordList.isEmpty()) {
        //     return CommonResult.failed("课程名违规，请重试！");
        // }
        ResVideo resVideo = ResVideoConvert.INSTANCE.toResVideo(resVideoDTO);
        resVideo.setId(YitIdHelper.nextId());
        resVideo.setPublishStatus(0);
        ResVideo video = resVideoService.addVideo(resVideo);
        return CommonResult.success(video);
    }


    @PostMapping("/addVideo")
    @Operation(summary = "用户上传视频")
    public CommonResult<?> addVideo(@RequestBody ResVideoDetailDTO resVideoDetailDTO) {
        ResVideoDetail resVideoDetail = ResVideoConvert.INSTANCE.toResVideoDetail(resVideoDetailDTO);
        resVideoDetail.setId(YitIdHelper.nextId());
        resVideoDetail.setPublishStatus(0);
        int insert = resVideoDetailMapper.insert(resVideoDetail);
        return CommonResult.success(null, insert > 0 ? "success" : "failed");
    }


    @PostMapping("/uploadVideo")
    @Operation(summary = "用户上传视频课程资源至oss")
    public CommonResult<?> uploadVideo(@RequestParam("file") MultipartFile[] fileList) throws Exception {
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

