package com.jony.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jony.entity.UserUpdateLog;
import com.jony.mapper.UserUpdateLogMapper;
import com.jony.service.UserUpdateLogService;


/**
 * userUpdateLog表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@Service
public class UserUpdateLogServiceImpl extends ServiceImpl<UserUpdateLogMapper, UserUpdateLog> implements UserUpdateLogService {

}

