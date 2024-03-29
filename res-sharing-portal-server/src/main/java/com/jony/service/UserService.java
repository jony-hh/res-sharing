package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.dto.UserLoginDTO;
import com.jony.entity.SysUser;
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
     * feat: 获取当前用户
     *
     * @param id
     * @return
     */
    List<String> getUserRole(Long id);

    /**
     * login
     */
    boolean authenticate(UserLoginDTO userLoginDTO, HttpServletResponse response);

    /**
     * logout
     */
    boolean logout(HttpServletRequest request);


}
