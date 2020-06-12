package com.mxg.oauth2.server.service;

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
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("andy",passwordEncoder.encode("1234"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("product"));
    }
}
