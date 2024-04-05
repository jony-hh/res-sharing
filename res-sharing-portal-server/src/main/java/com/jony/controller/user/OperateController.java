package com.jony.controller.user;

import com.github.yitter.idgen.YitIdHelper;
import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.convert.UserAnswerConvert;
import com.jony.dto.AnswerDTO;
import com.jony.dto.QuestionDTO;
import com.jony.dto.UserStarDTO;
import com.jony.dto.UserThumbDTO;
import com.jony.entity.UserAnswer;
import com.jony.entity.UserQuestion;
import com.jony.enums.ThumbOrStarStatusEum;
import com.jony.service.UserAnswerService;
import com.jony.service.UserQuestionService;
import com.jony.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/operate")
@Tag(name = "用户操作")
@RequiredArgsConstructor
public class OperateController {

    private final UserService userService;
    private final UserQuestionService userQuestionService;
    private final UserAnswerService userAnswerService;


    // region 【用户点赞、收藏、浏览记录、动态】

    @PostMapping("thumb")
    @AuthCheck(mustRole = "user")
    @Operation(summary = "点赞操作")
    public CommonResult<?> thumb(HttpServletRequest request,@RequestBody UserThumbDTO userThumbDTO) {
        ThumbOrStarStatusEum thumbOrStarStatusEum = userService.thumb(request,userThumbDTO);
        return CommonResult.success(thumbOrStarStatusEum.getCode(), thumbOrStarStatusEum.getMsg());
    }


    @PostMapping("star")
    @AuthCheck(mustRole = "user")
    @Operation(summary = "收藏操作")
    public CommonResult<?> star(HttpServletRequest request,@RequestBody UserStarDTO userStarDTO) {
        ThumbOrStarStatusEum thumbOrStarStatusEum = userService.star(request,userStarDTO);
        return CommonResult.success(thumbOrStarStatusEum.getCode(), thumbOrStarStatusEum.getMsg());
    }

    // endregion


    // region 提问题/写回答

    @PostMapping("/pose/question")
    @Operation(summary = "提问题")
    public CommonResult<?> poseQuestion(@RequestBody QuestionDTO questionDTO) {
        UserQuestion question = new UserQuestion();
        long id = YitIdHelper.nextId();
        question.setId(id);
        question.setUserId(questionDTO.getUserId());
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        String resultMessage = userQuestionService.addQuestion(question);
        return CommonResult.success(question, resultMessage);
    }


    @PostMapping("/write/answer")
    @Operation(summary = "写回答")
    public CommonResult<?> writeAnswer(@RequestBody AnswerDTO answerDTO) {
        UserAnswer userAnswer = UserAnswerConvert.INSTANCE.toUserAnswer(answerDTO);
        userAnswer.setId(YitIdHelper.nextId());
        String resultMessage = userAnswerService.addAnswer(userAnswer);
        return CommonResult.success(null,resultMessage);
    }

    // endregion

}
