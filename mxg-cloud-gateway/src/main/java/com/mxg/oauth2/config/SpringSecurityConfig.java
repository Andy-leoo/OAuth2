package com.mxg.oauth2.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/22 10:27 <br>
 * @   安全配置类
 * 关于拦截功能都交给资源配置类处理，在安全配置中将所有请求放行即可，不然默认情况下所有请求都要认证
 * @see com.mxg.oauth2.config <br>
 */
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //让资源配置类 进行拦截操作，这里放行所有即可
        http.authorizeRequests().anyRequest().permitAll();
    }
}
