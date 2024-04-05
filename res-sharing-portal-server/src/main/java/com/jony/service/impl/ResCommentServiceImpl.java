package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.convert.ResCommentConvert;
import com.jony.dto.ResCommentDTO;
import com.jony.entity.ResComment;
import com.jony.mapper.ResCommentMapper;
import com.jony.service.ResCommentService;
import com.jony.vo.ResCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * resComment表-服务实现类
 *
 * @author jony
 * @since 2024-04-05 15:39:40
 */
@Service
@RequiredArgsConstructor
public class ResCommentServiceImpl extends ServiceImpl<ResCommentMapper, ResComment> implements ResCommentService {

    final private ResCommentMapper resCommentMapper;

    @Override
    public List<ResCommentVO> fetchWhole(Long id) {
        // 1、拿取数据
        LambdaQueryWrapper<ResComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ResComment::getContentId, id);
        lambdaQueryWrapper.eq(ResComment::getDeleted, 0);
        List<ResComment> resComments = resCommentMapper.selectList(lambdaQueryWrapper);
        // 2、拿到根评论
        List<ResCommentVO> rootComments = resComments.stream()
                .filter(resComment -> resComment.getParentId() == -1)
                .map(ResCommentConvert.INSTANCE::toResCommentVO)
                .toList();
        // 3、拿到二级评论
        for (ResCommentVO comment : rootComments) {
            List<ResCommentVO> commentVOS =new ArrayList<>();
            for (ResComment c : resComments) {
                if (c.getParentId() > -1 && c.getParentId().equals(comment.getId())) {
                    ResCommentVO resCommentVO = ResCommentConvert.INSTANCE.toResCommentVO(c);
                    commentVOS.add(resCommentVO);
                }
            }
            comment.setReply(commentVOS);
        }
        return rootComments;
    }

    @Override
    public boolean comment(ResCommentDTO resCommentDTO) {
        // 0、敏感词替换
        String content = resCommentDTO.getContent();
        List<String> wordList = SensitiveWordHelper.findAll(content);
        if (!wordList.isEmpty()) {
            resCommentDTO.setContent(SensitiveWordHelper.replace(content));
        }
        // 1、转为ResComment
        ResComment resComment = ResCommentConvert.INSTANCE.toResComment(resCommentDTO);
        resComment.setId(YitIdHelper.nextId());
        // 2、save
        int insert = resCommentMapper.insert(resComment);
        return insert==1;
    }

    @Override
    public boolean deleteComment(Long commentID, Long userId) {
        ResComment resComment = resCommentMapper.selectById(commentID);
        if (resComment.getUserId().equals(userId)) {
            resComment.setDeleted(2);
            resComment.setContent("该评论已被删除");
        }
        int insert = resCommentMapper.insert(resComment);
        return insert==1;
    }
}

