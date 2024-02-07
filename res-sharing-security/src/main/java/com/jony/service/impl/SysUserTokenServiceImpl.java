package com.jony.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jony.entity.SysUserToken;
import com.jony.mapper.SysUserTokenMapper;
import com.jony.service.SysUserTokenService;


/**
 * sysUserToken表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenService {

}

