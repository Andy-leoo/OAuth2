package com.mxg.oauth2.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/13 10:11 <br>
 * @TODO 令牌管理策略。
 *      默认情况下，令牌是通过 randomUUID 产生32位随机数的来进行填充的，而产生的令牌默认是存储在内存中。
 *        内存存储采用的是 TokenStore 接口的默认实现类 InMemoryTokenStore  , 开发时方便调试，适用单机版。
 *          1. RedisTokenStore 将令牌存储到 Redis 非关系型数据库中，适用于高并发的服务。
 *          2. JdbcTokenStore 基于 JDBC 将令牌存储到 关系型数据库中，可以在不同的服务器之间共享令牌。
 *          3. JwtTokenStore （JSON Web Token）将用户信息直接编码到令牌中，这样后端可以不用存储它，前端拿到令
 *                  牌可以直接解析出用户信息。
 * @see com.mxg.oauth2.server.config <br>
 */
@Configuration
public class TokenConfig {

    /**
     * Redis 管理令牌
     * 1. 启动 redis 服务器
     * 2. 添加 redis 相关依赖
     * 3. 添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
     */
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /**
     * JDBC 管理令牌
     * 1. 创建相关数据表
     * 2. 添加 jdbc 相关依赖
     * 3. 配置数据源信息
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    @Bean
    public TokenStore tokenStore(){
        //redis 管理令牌
//        return new RedisTokenStore(redisConnectionFactory);
        //jdbc 管理令牌
//        return new JdbcTokenStore(dataSource());
        //JWT管理令牌 ，
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    private static final String SINGIN_KEY = "JX-KEY";

    // 使用 JwtAccessTokenConverter 中定义jwt签名密码
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称密钥来签署我们的令牌，资源服务器也将使用此密钥来验证准确性
        converter.setSigningKey(SINGIN_KEY);
        return converter;
    }
}
