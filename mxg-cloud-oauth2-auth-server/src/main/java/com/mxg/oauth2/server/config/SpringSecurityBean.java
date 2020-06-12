package com.mxg.oauth2.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author jiangxiao
 * @Title: SpringSecurityBean
 * @Package
 * @Description: 统一管理Bean配置类
 * @date 2020/6/1212:39
 */
@Configuration
public class SpringSecurityBean {


    @Bean  //加密方式， 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
