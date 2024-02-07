package com.jony.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jony.entity.UserLoginLog;
import com.jony.mapper.UserLoginLogMapper;
import com.jony.service.UserLoginLogService;


/**
 * userLoginLog表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

}

