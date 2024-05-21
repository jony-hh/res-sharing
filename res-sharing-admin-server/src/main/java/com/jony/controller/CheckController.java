package com.jony.controller;

import com.jony.api.CommonResult;
import com.jony.entity.ResDocument;
import com.jony.entity.ResVideo;
import com.jony.entity.ResVideoDetail;
import com.jony.service.ResDocumentService;
import com.jony.service.ResVideoDetailService;
import com.jony.service.ResVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/check")
@Tag(name = "内容审核")
@RequiredArgsConstructor
public class CheckController {

    private final ResDocumentService documentService;
    private final ResVideoService courseService;
    private final ResVideoDetailService videoService;

    @GetMapping("document")
    @Operation(summary = "获取待审核文档列表")
    public CommonResult<List<HashMap<String, Object>>> getUserList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer size) {
        List<HashMap<String, Object>> list = documentService.getList(query, page, size);
        return CommonResult.success(list);
    }

    @GetMapping("document/{id}")
    @Operation(summary = "确认通过document")
    public CommonResult<?> passDocument(@PathVariable("id") Long id){
        ResDocument document = documentService.getById(id);
        document.setPublishStatus(1);
        boolean b = documentService.updateById(document);
        if (b) {
            return CommonResult.success(null,"审核通过");
        } else {
            return CommonResult.failed();
        }
    }



    @GetMapping("course")
    @Operation(summary = "获取课程待审核列表")
    public CommonResult<List<HashMap<String, Object>>> getCourseList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer size) {
        List<HashMap<String, Object>> list = courseService.getList(query, page, size);
        return CommonResult.success(list);
    }

    @GetMapping("course/{id}")
    @Operation(summary = "确认通过course")
    public CommonResult<?> passCourse(@PathVariable("id") Long id){
        ResVideo document = courseService.getById(id);
        document.setPublishStatus(1);
        boolean b = courseService.updateById(document);
        if (b) {
            return CommonResult.success(null,"审核通过");
        } else {
            return CommonResult.failed();
        }
    }


    @GetMapping("video")
    @Operation(summary = "获取待审核视频列表")
    public CommonResult<List<HashMap<String, Object>>> getVideoList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer size) {
        List<HashMap<String, Object>> list = videoService.getList(query, page, size);
        return CommonResult.success(list);
    }

    @GetMapping("video/{id}")
    @Operation(summary = "确认通过video")
    public CommonResult<?> passVideo(@PathVariable("id") Long id){
        ResVideoDetail videoDetail = videoService.getById(id);
        videoDetail.setPublishStatus(1);
        boolean b = videoService.updateById(videoDetail);
        if (b) {
            return CommonResult.success(null,"审核通过");
        } else {
            return CommonResult.failed();
        }
    }

    @DeleteMapping("document/{id}")
    @Operation(summary = "鉴定为违规内容")
    public CommonResult<?> deleteDocument(@PathVariable("id") Long id){
        boolean removeById = documentService.removeById(id);
        if (removeById) {
            return CommonResult.success(null,"成功删除违规内容");
        } else {
            return CommonResult.failed();
        }
    }

    @DeleteMapping("course/{id}")
    @Operation(summary = "鉴定为违规内容")
    public CommonResult<?> deleteCourse(@PathVariable("id") Long id){
        boolean removeById = courseService.removeById(id);
        if (removeById) {
            return CommonResult.success(null,"成功删除违规内容");
        } else {
            return CommonResult.failed();
        }
    }

    @DeleteMapping("video/{id}")
    @Operation(summary = "鉴定为违规内容")
    public CommonResult<?> deleteVideo(@PathVariable("id") Long id){
        boolean removeById = videoService.removeById(id);
        if (removeById) {
            return CommonResult.success(null,"成功删除违规内容");
        } else {
            return CommonResult.failed();
        }
    }


}
