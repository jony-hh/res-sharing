package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * userStar表-实体类
 *
 * @author jony
 * @since 2024-03-30 15:50:54
 */
@TableName("user_star")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStar implements Serializable {
    /**
     * 收藏记录id
     */     
    private Long id;
    
    /**
     * 收藏的用户id
     */     
    private Long userId;
    
    /**
     * 收藏内容的id
     */     
    private Long contentId;
    
    /**
     * 收藏内容的类型
     */     
    private String contentType;
    
    /**
     * 0 取消 1 收藏
     */     
    private Integer status;
    
    /**
     * 版本号
     */     
    private Integer version;
    
    /**
     * 删除标识  0：正常   1：封禁   2：删除
     */     
    private Integer deleted;
    
    /**
     * 创建者
     */     
    private Long creator;
    
    /**
     * 创建时间
     */     
    private Timestamp createdTime;
    
    /**
     * 更新者
     */     
    private Long updater;
    
    /**
     * 修改时间
     */     
    private Timestamp updatedTime;
    

}

