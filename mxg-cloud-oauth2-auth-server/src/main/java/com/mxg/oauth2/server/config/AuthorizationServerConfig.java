package com.mxg.oauth2.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @author jiangxiao
 * @Title: AuthorizationServerConfig
 * @Package
 * @Description: 认证配置类
 * @date 2020/6/1212:18
 */
@Configuration//标识配置类
@EnableAuthorizationServer // 开启oauth2认证服务器功能
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/13 17:37 <br>
     * @desc 使用jdbc方式管理客户端信息
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 这里涉及到一些参数 配置 简述
     * withClient：允许访问此认证服务器的客户端id , 如：PC、APP、小程序各不同的的客户端id。
     *
     * secret：客户端密码，要加密存储，不然获取不到令牌一直要求登录,, 而且一定不能被泄露。
     *
     * authorizedGrantTypes: 授权类型, 可同时支持多种授权类型：
     *          可配置："authorization_code", "password", "implicit","client_credentials","refresh_token"
     *
     * scopes：授权范围标识，如指定微服务名称，则只能访问指定的微服务。
     *
     * autoApprove：false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码。
     *
     * redirectUris 当获取授权码后，认证服务器会重定向到这个URI，并且带着一个授权码 code 响应回来。
     */

    /**
     * 被允许访问此认证服务器的客户端详情信息
     * *                                    方式1：内存方式管理
     * *                                    方式2：数据库管理
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用内存方式
//        clients.inMemory()
//                .withClient("JX-pc")//客户端id
//                // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
//                .secret(passwordEncoder.encode("JX-PWD"))
//                // 资源id, 如商品资源
//                .resourceIds("product-server")
//                // 授权类型, 可同时支持多种授权类型
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token","implicit","client_credentials")
//                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
//                .scopes("all")
//                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
//                .autoApprove(false)
//                .redirectUris("http://www.mengxuegu.com/"); // 客户端回调地址

        //使用jdbc
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DataSource dataSource;

    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/13 11:27 <br>
     * @desc  授权码管理策略
     *          JDBC方式保存授权码到 oauth_code 表中,
     *          意义不大，因为获取一次令牌后，授权码就失效了
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //刷新令牌获取新令牌 需要
        endpoints.userDetailsService(customUserDetailsService);

        //令牌管理策略
        endpoints.tokenStore(tokenStore);

        //授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
    }

    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/13 19:29 <br>
     * @desc 令牌端点的安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 所有人可访问 /oauth/token_key 后面要获取公钥, 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 认证后可访问 /oauth/check_token , 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
    }
}
