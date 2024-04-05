package com.jony.controller.qa;

import com.jony.api.CommonResult;
import com.jony.entity.UserAnswer;
import com.jony.entity.UserQuestion;
import com.jony.service.UserAnswerService;
import com.jony.service.UserQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qa")
@AllArgsConstructor
@Tag(name = "qa问题相关")
@RequiredArgsConstructor
public class QaController {

    private final UserQuestionService userQuestionService;
    private final UserAnswerService userAnswerService;

    // region 问题相关

    @GetMapping("/question/fetchWhole")
    @Operation(summary = "取得所有问题")
    public CommonResult<?> fetchWholeQuestion() {
        List<UserQuestion> questionList = userQuestionService.fetchWhole();
        return CommonResult.success(questionList);
    }

    @GetMapping("/question/pagingQuery")
    @Operation(summary = "分页查询问题")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<UserQuestion> questionList = userQuestionService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(questionList);
    }

    @GetMapping("/question/fetchById")
    @Operation(summary = "根据id查询单个问题")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        UserQuestion question = userQuestionService.fetchById(id);
        return CommonResult.success(question);
    }


    //endregion


    // region 问题相关

    @GetMapping("/answer/fetchWhole")
    @Operation(summary = "取得所有回答")
    public CommonResult<?> fetchWholeAnswer(@RequestParam Long questionId) {
        List<UserAnswer> userAnswers = userAnswerService.fetchWhole(questionId);
        return CommonResult.success(userAnswers);
    }

    //endregion

}
