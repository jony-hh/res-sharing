package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * userUpdateLog表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@TableName("user_update_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateLog implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 属性对应旧的值
     */
    private String attributeOldVal;

    /**
     * 属性对应新的值
     */
    private String attributeNewVal;

    /**
     * 修改时间
     */
    private Timestamp updateTime;


}

