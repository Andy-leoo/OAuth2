package com.mxg.oauth.sso;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

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

    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/23 15:07 <br>
     * @desc 当客户端要请求资源服务器中的资源时，我们需要带上令牌给资源服务器，
     * 由于我们使用了 @EnableOAuth2Sso注解，
     * SpringBoot 会在请求上下文中添加一个 OAuth2ClientContext 对象，而我们只要在配置类中向容器中添加
     * 一个 OAuth2RestTemplate 对象，请求的资源服务器时就会把令牌带上转发过去。
     */
    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory){
        return factory.getUserInfoRestTemplate();
    }

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
