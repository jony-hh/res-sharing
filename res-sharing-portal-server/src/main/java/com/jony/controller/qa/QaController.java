package com.jony.controller.qa;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jony.api.CommonResult;
import com.jony.convert.UserQuestionConvert;
import com.jony.entity.RmTagResRelation;
import com.jony.entity.SysUser;
import com.jony.entity.UserAnswer;
import com.jony.entity.UserQuestion;
import com.jony.mapper.RmTagMapper;
import com.jony.mapper.RmTagResRelationMapper;
import com.jony.mapper.SysUserMapper;
import com.jony.service.UserAnswerService;
import com.jony.service.UserQuestionService;
import com.jony.vo.QuestionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qa")
@Tag(name = "qa问题相关")
@RequiredArgsConstructor
public class QaController {

    private final UserQuestionService userQuestionService;
    private final UserAnswerService userAnswerService;
    private final SysUserMapper userMapper;
    private final RmTagResRelationMapper tagResRelationMapper;
    private final RmTagMapper tagMapper;

    // region 问题相关

    @GetMapping("/question/fetchWhole")
    @Operation(summary = "取得所有问题")
    public CommonResult<?> fetchWholeQuestion() {
        List<UserQuestion> questionList = userQuestionService.fetchWhole();
        List<QuestionVO> questionVOS = questionList.stream()
                .map(question -> {
                    // 根据question.getUserId()查询用户信息（昵称、头像、提问时间）
                    SysUser sysUser = userMapper.selectById(question.getUserId());
                    QuestionVO questionVO = UserQuestionConvert.INSTANCE.toQuestionVO(question);
                    questionVO.setNickname(sysUser.getNickname());
                    questionVO.setAvatar(sysUser.getAvatar());
                    // 查tags
                    // 这里的tags是通过document的id查询的，所以需要将document的id传进去
                    LambdaQueryWrapper<RmTagResRelation> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(RmTagResRelation::getResId, question.getId());
                    List<RmTagResRelation> rmTagResRelations = tagResRelationMapper.selectList(wrapper);
                    List<String> tags = rmTagResRelations.stream()
                            .map(rmTagResRelation -> tagMapper.selectById(rmTagResRelation.getTagId()).getName())
                            .collect(Collectors.toList());
                    questionVO.setTags(tags);
                    return questionVO;
                })
                .collect(Collectors.toList());
        return CommonResult.success(questionVOS);
    }

    @GetMapping("/question/pagingQuery")
    @Operation(summary = "分页查询问题")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<UserQuestion> questionList = userQuestionService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(questionList);
    }

    @GetMapping("/question/single")
    @Operation(summary = "根据id查询单个问题")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        UserQuestion question = userQuestionService.fetchById(id);
        return CommonResult.success(question);
    }


    // endregion


    // region 问题相关

    @GetMapping("/answer/byQuestionId")
    @Operation(summary = "取得所有回答")
    public CommonResult<?> fetchWholeAnswer(@RequestParam("id") Long questionId) {
        List<UserAnswer> userAnswers = userAnswerService.fetchWhole(questionId);
        return CommonResult.success(userAnswers);
    }

    // endregion


}
