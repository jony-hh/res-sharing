package com.jony.entity;

import java.sql.Timestamp;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * sysAuth表-实体类
 *
 * @author jony
 * @since 2024-02-07 13:06:29
 */
@TableName("sys_auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAuth implements Serializable {
    /**
     * 自增id
     */     
    private Long id;
    
    /**
     * 用户id
     */     
    private Long userId;
    
    /**
     * 1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
     */     
    private String authType;
    
    /**
     * 身份标识(手机号 邮箱 用户名或第三方应用的唯一标识)
     */     
    private String identifier;
    
    /**
     * 密码(站内的保存密码，站外的不保存或保存token)
     */     
    private String certificate;
    
    /**
     * 版本号
     */     
    private Integer version;
    
    /**
     * 删除标识  0：正常   1：已删除
     */     
    private Integer deleted;
    
    /**
     * 创建者
     */     
    private Long creator;
    
    /**
     * 绑定时间
     */     
    private Timestamp createdTime;
    
    /**
     * 更新者
     */     
    private Long updater;
    
    /**
     * 更新绑定时间
     */     
    private Timestamp updatedTime;
    

}

