package com.mxg.oauth2.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author jiangxiao
 * @Title: SpringSecurityConfig
 * @Package
 * @Description: 安全配置类，这里是用要进行认证用户的用户名和密码，这个用户名和密码是资源所有者的
 * @date 2020/6/1212:43
 */
@EnableWebSecurity //这里包含了 configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 在 SpringSecurityBean 添加到容器了
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用内存的方式来存储用户信息
//        auth.inMemoryAuthentication()
//                .withUser("andy")
//                .password(passwordEncoder.encode("1234"))
//                .authorities("produce");
        auth.userDetailsService(customUserDetailsService);
    }
}
