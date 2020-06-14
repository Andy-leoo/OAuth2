package com.mxg.oauth2.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
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
 * @see com.mxg.oauth2.resource <br>
 */
@Configuration
@EnableResourceServer //标识为资源服务器，所有发往这个服务的请求，都会去请求头里找 token，找不到或者通过认证服务器验证不合法，则不允许访问。
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级权限控制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //配置当前资源服务器的ID
    private static final String RESOURCE_ID = "product-server";

    @Autowired
    private TokenStore tokenStore;
    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/14 11:08 <br>
     * @desc
     *      配置当前资源服务器ID
     *      添加校验令牌服务
     *          1. 创建 RemoteTokenServices 远程校验令牌服务，去校验令牌有效性，原因：
     *              因为当前认证和资源服务器不是在同一工程中，所以要通过远程调用认证服务器校验令牌是否有
     *              效。
     *          2. 如果认证和资源服务器在同一工程中，可以使用 DefaultTokenServices 配置校验令牌。
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)//配置当前资源服务器的ID, 会在认证服务器验证(客户端表的 resources配置了就可以访问这个服务)
                    .tokenStore(tokenStore);
//                .tokenServices(tokenServices());//实现令牌服务ResourceServerTokenServices实例
    }

    /**
     * 配置资源服务器如何验证token有效性
     * 1. DefaultTokenServices
     * 如果认证服务器和资源服务器同一服务时,则直接采用此默认服务验证即可
     * 2. RemoteTokenServices (当前采用这个)
     * 当认证服务器和资源服务器不是同一服务时, 要使用此服务去远程认证服务器验证

    public ResourceServerTokenServices tokenServices(){
        //资源服务器去远程认证服务器验证token是否有效
        RemoteTokenServices services = new RemoteTokenServices();
        //请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        services.setCheckTokenEndpointUrl("http://localhost:8090/oauth/check_token");
        //在认证服务器配置的客户端id
        services.setClientId("JX-pc");
        //在认证服务器配置的客户端密码
        services.setClientSecret("JX-PWD");
        return services;
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                // SpringSecurity 不会创建也不会使用 HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 资源授权规则
                .antMatchers("/product/**").hasAuthority("product")
                // 所有的请求对应访问的用户都要有 all 范围权限
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                ;
    }
}
