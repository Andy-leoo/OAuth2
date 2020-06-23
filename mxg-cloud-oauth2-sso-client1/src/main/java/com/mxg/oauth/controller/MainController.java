package com.mxg.oauth.controller;

import com.mxg.base.result.MxgResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/15 9:04 <br>
 * @控制跳转
 * @see com.mxg.oauth.controller <br>
 */
@Controller
public class MainController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/member")
    public String member() {
        MxgResult result = restTemplate.getForObject("http://localhost:7001/product/list",MxgResult.class);
        System.out.println("获取商品信息："+ result);
        return "member";
    }
}
