package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.SysAuth;
import com.jony.enums.UserEnum;
import com.jony.security.dto.UserRegisterDto;
import com.jony.security.vo.UserInfoVo;
import jakarta.servlet.http.HttpServletResponse;


/**
 * sysAuth表-服务接口
 *
 * @author jony
 * @since 2024-02-07 13:06:29
 */
public interface SysAuthService extends IService<SysAuth> {

    /**
     * 登录授权
     *
     * @param name         -
     * @param pwd          -
     * @param authType     -
     * @param isRememberMe -
     * @param response     -
     * @return -
     */
    UserInfoVo login(String name, String pwd, UserEnum.AuthType authType, Boolean isRememberMe, HttpServletResponse response);

    boolean register(UserRegisterDto userRegisterDto, String registerType, HttpServletResponse response);
}


