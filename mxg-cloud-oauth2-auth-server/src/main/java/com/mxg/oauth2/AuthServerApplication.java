package com.mxg.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jiangxiao
 * @Title: AuthServerApplication
 * @Package
 * @Description: 认证服务器  启动类，
 * @date 2020/6/1115:42
 */
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
