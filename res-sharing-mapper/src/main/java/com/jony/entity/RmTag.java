package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * rmTag表-实体类
 *
 * @author jony
 * @since 2024-03-30 14:56:30
 */
@TableName("rm_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RmTag implements Serializable {
    /**
     * tag_id
     */     
    private Long id;
    
    /**
     * tag名称
     */     
    private String name;
    
    /**
     * tag作用域（video、topic、document、wiki、search）
     */     
    private String type;
    
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

