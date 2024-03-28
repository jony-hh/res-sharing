package com.jony.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.UserRegisterLog;
import com.jony.mapper.UserRegisterLogMapper;
import com.jony.service.UserRegisterLogService;
import org.springframework.stereotype.Service;


/**
 * userRegisterLog表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@Service
public class UserRegisterLogServiceImpl extends ServiceImpl<UserRegisterLogMapper, UserRegisterLog> implements UserRegisterLogService {

}

