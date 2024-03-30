package com.jony.controller.qa;

import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.dto.QuestionDTO;
import com.jony.entity.UserQuestion;
import com.jony.service.UserQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qa/question")
@AllArgsConstructor
@Tag(name = "qa问答")
public class QuestionController {

    @Resource
    UserQuestionService userQuestionService;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有问题")
    public CommonResult<?> fetchWhole() {
        List<UserQuestion> questionList = userQuestionService.fetchWhole();
        return CommonResult.success(questionList);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询问题")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<UserQuestion> questionList = userQuestionService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(questionList);
    }


    @PostMapping("/askForHelp")
    @Operation(summary = "提问题")
    public CommonResult<?> askForHelp(@RequestBody QuestionDTO questionDTO) {
        UserQuestion question = new UserQuestion();
        long id = YitIdHelper.nextId();
        question.setId(id);
        question.setUserId(questionDTO.getUserId());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        String resultMessage = userQuestionService.addQuestion(question);
        return CommonResult.success(question, resultMessage);
    }

    @GetMapping("/fetchById")
    @Operation(summary = "根据id查询单个问题")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        UserQuestion question = userQuestionService.fetchById(id);
        return CommonResult.success(question);
    }

}
