package com.mxg.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/14 11:01 <br>
 * @ 资源服务器
 * ResourceServerConfigurerAdapter 资源服务器适配器
 * 网关也被认为是资源服务器, 因为要访问每个微服务资源，都要经过网关进行拦截判断是否允许访问。
 * 下面就要配置每个微服务的权限规则，实现客户端权限拦截，这样只有当客户端拥有了对应权限才可以访问到对应
 * 微服务
 * @see com.mxg.oauth2.resource <br>
 */
@Configuration
public class ResourceServerConfig {

    //配置当前资源服务器的ID
    private static final String RESOURCE_ID = "product-server";

    @Autowired
    private TokenStore tokenStore;


    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/22 10:09 <br>
     * @desc 认证服务器 资源拦截。
     *      所有请求放行。
     */
    @Configuration
    @EnableResourceServer
    public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter{
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID)//配置当前资源服务器的ID, 会在认证服务器验证(客户端表的 resources配置了就可以访问这个服务)
                    .tokenStore(tokenStore);
//                .tokenServices(tokenServices());//实现令牌服务ResourceServerTokenServices实例
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {
            //认证服务器资源全部房型，用于处理认证
            http.authorizeRequests().anyRequest().permitAll();
        }
    }


    public class ProductResourceServerConfig extends ResourceServerConfigurerAdapter{
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
        }


        //客户端要有 PRODUCT_API 范围才可访问
        @Override
        public void configure(HttpSecurity http) throws Exception {
           http.authorizeRequests()
                   .antMatchers("/product/**")
                   .access("#oauth2.hasScope('PRODUCT_API')")
                   ;
        }
    }


}
