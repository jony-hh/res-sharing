package com.jony.entity;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * sysMenu表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@TableName("sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 上级ID，一级菜单为0
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜是否需要认证
     */
    private String IsNeedAuthorization;

    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    private String authority;

    /**
     * 类型   0：菜单   1：按钮   2：接口
     */
    private Integer type;

    /**
     * 打开方式   0：内部   1：外部
     */
    private Integer openStyle;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

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
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新者
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Timestamp updateTime;


}

