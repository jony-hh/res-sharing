package com.jony.entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * resVideo表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:14
 */
@TableName("res_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResVideo implements Serializable {
    /**
     * 雪花漂移id
     */     
    private Long id;
    
    /**
     * 课程名称
     */     
    private String courseName;
    
    /**
     * 讲师
     */     
    private String lecturer;
    
    /**
     * 课程标识：国家示范
     */     
    private String sign;
    
    /**
     * 封面地址
     */     
    private String coverUrl;
    
    /**
     * 缩略图
     */     
    private String thumbnail;
    
    /**
     * 归属大学
     */     
    private String college;
    
    /**
     * 视频所属标签
     */     
    private String tag;
    
    /**
     * 发布作者
     */     
    private String author;
    
    /**
     * 用于特殊操作
     */     
    private String flag;
    
    /**
     * 课程摘要
     */     
    private String summary;
    
    /**
     * 参与课程学生数
     */     
    private Long studentNum;
    
    /**
     * 观看数
     */     
    private Long viewCount;
    
    /**
     * 发布时间
     */     
    private Timestamp publishTime;
    
    /**
     * 发布状态 0草稿 1已发布
     */     
    private Integer publishStatus;
    
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

