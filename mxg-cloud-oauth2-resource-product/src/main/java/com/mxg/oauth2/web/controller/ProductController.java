package com.mxg.oauth2.web.controller;

import com.mxg.base.result.MxgResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/14 8:18 <br>
 * @ 商品资源获取类
 * @see com.mxg.oauth2.web.controller <br>
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('product:list')")
    public MxgResult list() {
        List<String> list = new ArrayList<>();
        list.add("ipad");
        list.add("ipod");
        list.add("Mac");
        return MxgResult.ok(list);
    }
}
