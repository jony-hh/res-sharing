package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * sysRole表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@TableName("sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据
     */
    private Integer dataScope;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 租户ID
     */
    private Long tenantId;

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

