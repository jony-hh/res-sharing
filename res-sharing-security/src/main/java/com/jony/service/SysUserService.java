package com.jony.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.SysUser;
import com.jony.security.vo.UserInfoVo;

/**
 * sysUser表-服务接口
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 账号查询
     *
     * @param userAuthIdentifier 账号
     * @param authType           账号类型
     * @return 用户信息
     */
    UserInfoVo findByUserAuthIdentifier(String userAuthIdentifier, String authType);
}


