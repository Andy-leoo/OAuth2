package com.mxg.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jiangxiao
 * @Title: AuthServerApplication
 * @Package
 * @Description: 认证服务器  启动类，
 * 这里简述一下 这个包的作用：
 * 1. 配置允许访问此认证服务器的客户端信息，没有在此配置的客户端是不允许访问的
 * 2. 管理令牌 ：
 *          配置令牌管理策略（jdbc redis  jwt）
 *          配置令牌生成策略
 *          配置令牌端点
 *          令牌端点的安全配置
 * @date 2020/6/1115:42
 */
@SpringBootApplication
public class AuthServerApplication {

    /**
     * /oauth/authorize ：申请授权码 code,  涉及的类 AuthorizationEndpoint
     * /oauth/token ：获取令牌 token,  涉及的类 TokenEndpoint
     * /oauth/check_token ：用于资源服务器请求端点来检查令牌是否有效,  涉及的类 CheckTokenEndpoint
     * /oauth/confirm_access ：用户确认授权提交,  涉及的类 WhitelabelApprovalEndpoint
     * /oauth/error ：授权服务错误信息,  涉及的类   WhitelabelErrorEndpoint
     * /oauth/token_key ：提供公有密匙的端点，使用 JWT 令牌时会使用 , 涉及的类 TokenKeyEndpoint
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
