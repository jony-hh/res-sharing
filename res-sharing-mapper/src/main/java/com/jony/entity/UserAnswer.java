package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * userAnswer表-实体类
 *
 * @author jony
 * @since 2024-03-30 14:54:58
 */
@TableName("user_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswer implements Serializable {
    /**
     * 回答id
     */     
    private Long id;
    
    /**
     * 用户id
     */     
    private Long userId;
    
    /**
     * 所回答的问题id
     */     
    private Long questionId;
    
    /**
     * 摘要
     */     
    private String summary;
    
    /**
     * 内容
     */     
    private String content;
    
    /**
     * 特殊标签
     */     
    private String flag;
    
    /**
     * hot回答、精致回答
     */     
    private String sign;
    
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

