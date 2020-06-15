package com.mxg.oauth.controller;

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

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/member")
    public String member() {
        return "member";
    }
}
