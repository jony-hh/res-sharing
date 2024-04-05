package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.dto.ResCommentDTO;
import com.jony.entity.ResComment;
import com.jony.vo.ResCommentVO;

import java.util.List;


/**
 * resComment表-服务接口
 *
 * @author jony
 * @since 2024-04-05 15:39:40
 */
public interface ResCommentService extends IService<ResComment> {

    /**
     * 根据id获取所有评论
     *
     * @param id
     */
    List<ResCommentVO> fetchWhole(Long id);

    boolean comment(ResCommentDTO resCommentDTO);

    boolean deleteComment(Long commentID, Long userId);
}


