package com.mxg.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/15 21:36 <br>
 * @  启动类， zuul 网关， 代替 资源服务器
 * @see com.mxg.oauth2 <br>
 */
@EnableZuulProxy // 开启zuul功能
@EnableEurekaClient
@SpringBootApplication
public class ZuulServer_7001 {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServer_7001.class,args);
    }
}
