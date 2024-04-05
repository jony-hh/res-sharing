package com.jony.controller.res;

import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.dto.ResCommentDTO;
import com.jony.service.ResCommentService;
import com.jony.vo.ResCommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("res/comment")
@Tag(name = "课程讨论")
@RequiredArgsConstructor
public class ResCommentController {

    final private ResCommentService resCommentService;


    @GetMapping("/fetchWhole")
    @Operation(summary = "返回目标所有评论")
    public CommonResult<?> fetchWhole(@RequestParam Long id) {
        List<ResCommentVO> resCommentVOS = resCommentService.fetchWhole(id);
        return CommonResult.success(resCommentVOS);
    }


    @PostMapping("/send")
    @Operation(summary = "发表评论")
    @AuthCheck(mustRole = "user")
    public CommonResult<?> comment (@RequestBody ResCommentDTO resCommentDTO) {
        boolean result = resCommentService.comment(resCommentDTO);
        return CommonResult.success(result);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @AuthCheck(mustRole = "user")
    public CommonResult<?> deleteComment (@RequestParam Long commentId,@RequestParam Long userId) {
        boolean result = resCommentService.deleteComment(commentId,userId);
        return CommonResult.success(result);
    }
}
