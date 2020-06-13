package com.mxg.oauth2.server.service;

import com.mxg.oauth2.web.entities.SysUser;
import com.mxg.oauth2.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author jiangxiao
 * @Title: CustomUserDetailsService
 * @Package
 * @Description:
 *
 * @date 2020/6/1217:19
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService{
//public class CustomUserDetailsService implements UserDetailsService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        //内存方式 存储 用户信息
//        return new User("andy",passwordEncoder.encode("1234"),
//                AuthorityUtils.commaSeparatedStringToAuthorityList("product"));
//    }

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Override
    SysUser findSysUser(String usernameOrMobile){
        LOG.info("请求认证的用户名：" + usernameOrMobile);
        return sysUserService.findByUsername(usernameOrMobile);
    }
}
