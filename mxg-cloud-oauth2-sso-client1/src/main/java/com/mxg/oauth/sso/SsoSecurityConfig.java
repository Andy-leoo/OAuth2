package com.mxg.oauth.sso;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/15 10:49 <br>
 * @ SSO 登录配置类
 * @see com.mxg.oauth.sso <br>
 */
@EnableOAuth2Sso
@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //首页所有人可访问
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
        .and()
                //退出系统
        .logout()
                //当前退出成功后  跳转认证服务器退出
        .logoutSuccessUrl("http://localhost:7001/auth/logout")
        .and().csrf().disable()
    ;
    }
}
