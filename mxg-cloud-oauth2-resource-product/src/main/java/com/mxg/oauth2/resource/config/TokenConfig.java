package com.mxg.oauth2.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/14 17:38 <br>
 * @ 令牌配置类
 * @see com.mxg.oauth2.resource.config <br>
 */
@Configuration
public class TokenConfig {

    private static final String SINGIN_KEY = "JX-KEY";


    @Bean
    public TokenStore tokenStore(){
        //JWT管理令牌 ，
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    // 使用 JwtAccessTokenConverter 中定义jwt签名密码
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称密钥来签署我们的令牌，资源服务器也将使用此密钥来验证准确性
        converter.setSigningKey(SINGIN_KEY);
        return converter;
    }
}
