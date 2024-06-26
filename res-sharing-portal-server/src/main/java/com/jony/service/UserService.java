package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.dto.*;
import com.jony.entity.SysUser;
import com.jony.enums.ThumbOrStarStatusEum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService extends IService<SysUser> {

    /**
     * feat: 获取当前登录用户
     *
     * @param request 请求
     * @return SysUser
     */
    SysUser getLoginUser(HttpServletRequest request);

    /**
     * feat: 注册站内账户
     *
     * @param userRegisterDTO
     */
    boolean localRegister(UserRegisterDTO userRegisterDTO);

    /**
     * feat: 获取当前用户的角色
     *
     * @param id
     * @return
     */
    List<String> getUserRole(Long id);

    /**
     * feat: login
     *
     * @param userLoginDTO,response
     * @return boolean
     */
    boolean authenticate(UserLoginDTO userLoginDTO, HttpServletResponse response);

    /**
     * feat: logout
     *
     * @param request
     * @return boolean
     */
    boolean logout(HttpServletRequest request);

    /**
     * feat: 用户点赞
     *
     * @param request,userThumbDTO
     * @return boolean
     */
    ThumbOrStarStatusEum thumb(HttpServletRequest request, UserThumbDTO userThumbDTO);

    /**
     * feat: 用户收藏
     *
     * @param request,userThumbDTO
     * @return boolean
     */
    ThumbOrStarStatusEum star(HttpServletRequest request, UserStarDTO userStarDTO);

    /**
     * feat: 用户更新信息
     *
     * @param userUpdateDTO
     * @return boolean
     */
    boolean updateUser(UserUpdateDTO userUpdateDTO);
}
