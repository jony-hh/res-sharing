package com.jony.security.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jony.entity.SysMenu;
import com.jony.entity.SysRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jony
 * @date ：2023-01-01 15:03
 * @description ：
 */
@Setter
@Getter
@ToString
public class UserInfoVo implements UserDetails {
    @Serial
    private static final long serialVersionUID = -7850778107226817897L;

    /**
     * 用户id
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个人简介
     */
    private String userAbout;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 地址
     */
    private String address;
    /**
     * 职业
     */
    private String job;
    /**
     * 用户过期true：没有过期  false：过期
     */
    private Boolean userIsAccountNonExpired;
    /**
     * 用户锁定true：没有锁定  false：被锁定
     */
    private Boolean userIsAccountNonLocked;
    /**
     * 最后一次登录时间
     */
    private Timestamp userAuthLastLoginDate;
    /**
     * 用户授权id
     */
    private Long userAuthId;
    /**
     * 授权类型
     */
    private String userAuthType;
    /**
     * 登录号：唯一识别码
     */
    private String userAuthIdentifier;
    /**
     * 凭证信息
     */
    @JsonIgnore
    private String userAuthCredential;
    /**
     * 凭证信息
     */
    @JsonIgnore
    private String password;
    /**
     * 刷新token
     */
    private String userAuthRefreshToken;
    /**
     * 授权昵称
     */
    private String userAuthNickname;
    /**
     * 授权头像
     */
    private String userAuthPhoto;
    /**
     * 同时登录客户端的人数:最小1最大50（默认1）
     */
    private Integer userMaxLoginClientNumber;
    /**
     * 角色集合
     */
    private List<SysRole> roleInfoList;
    /**
     * 权限集合
     */
    private Set<String> authoritySet;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!authoritySet.isEmpty()) {
            Set<SimpleGrantedAuthority> authorities = authoritySet.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            return authorities;
        }
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userAuthIdentifier;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.userIsAccountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.userIsAccountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


}
