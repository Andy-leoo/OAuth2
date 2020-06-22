package com.mxg.oauth2.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/22 10:48 <br>
 * @ 将解析后的用户信息 转发给 相应的 微服务，
 * 这样目标微服务可以判断用户可访问的权限
 *
 * 请求资源前，先通过 此过滤器进行用户令牌解析与校验，转发
 * @see com.mxg.oauth2.filter <br>
 */
@Component
public class AuthenticationFilter extends ZuulFilter {
    /**
     * 自定义过虑器需要继承 ZuulFilter，ZuulFilter是一个抽象类，需要覆盖它的4个方法，如下：
     * filterType：返回字符串代表过滤器的类型，返回值有：
         * pre：在请求路由之前执行
         * route：在请求路由时调用
         * post：请求路由之后调用， 也就是在route和errror过滤器之后调用
         * error：处理请求发生错误时调用
     * filterOrder：此方法返回整型数值，通过此数值来定义过滤器的执行顺序，数字越小优先级越高。
     * shouldFilter：返回Boolean值，判断该过滤器是否执行。返回true表示要执行此过虑器，false不执行。
     * run：过滤器的业务逻辑。
     *
     */

    @Override
    public String filterType() {
        //请求路由前调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器执行顺序，数值越小优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //true  执行下面 run 方法
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取从security 上下文中获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //JWT 令牌有效，则会解析用户信息，将信息封装到OAuth2Authentication 对象中
        if (authentication instanceof OAuth2Authentication) {
            return  null;
        }

        //取出用户名
        Object username = authentication.getPrincipal();
        //获取用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> authoritiesSet = AuthorityUtils.authorityListToSet(authorities);
        //请求详情
        Object details = authentication.getDetails();

        //信息封装
        HashMap<String, Object> result = new HashMap<>();
        result.put("principal",username);
        result.put("details",details);
        result.put("authorities",authoritiesSet);


        try {
            //获取当前上下文
            RequestContext currentContext = RequestContext.getCurrentContext();
            String  base64 = Base64Utils.encodeToString(JSON.toJSONString(result).getBytes("UTF-8"));
            currentContext.addZuulRequestHeader("auth-token",base64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }
}
