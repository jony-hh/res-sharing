package com.jony.service.impl;
    
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.security.vo.UserInfoVo;
import com.jony.security.token.EmailAuthenticationToken;
import com.jony.security.token.LocalAuthenticationToken;
import com.jony.security.token.PhoneAuthenticationToken;
import com.jony.enums.UserEnum;
import com.jony.exception.BusinessException;
import com.jony.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.jony.entity.SysAuth;
import com.jony.mapper.SysAuthMapper;
import com.jony.service.SysAuthService;


/**
 * sysAuth表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 13:06:29
 */
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl extends ServiceImpl<SysAuthMapper, SysAuth> implements SysAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Override
    public UserInfoVo login(String name, String pwd, UserEnum.AuthType authType, Boolean isRememberMe, HttpServletResponse response) {
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)) {
            authentication = authenticationManager.authenticate(new LocalAuthenticationToken(name, pwd));
        } else if (UserEnum.AuthType.EMAIL.equals(authType)) {
            authentication = authenticationManager.authenticate(new EmailAuthenticationToken(name, pwd));
        } else if (UserEnum.AuthType.PHONE.equals(authType)) {
            authentication = authenticationManager.authenticate(new PhoneAuthenticationToken(name, pwd));
        } else {
            throw new BusinessException("不支持的登录方式");
        }
        UserInfoVo userInfoVO = (UserInfoVo) authentication.getPrincipal();

        // 生成token，或者在provider中生成
        if (userInfoVO != null && !tokenUtils.setUserInfoVo(userInfoVO, isRememberMe, response)) {
            throw new BusinessException(401, "登录失败");
        }
        return userInfoVO;
    }
}

