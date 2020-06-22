package com.mxg.oauth2.resource.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/22 16:31 <br>
 * @  自定义过滤器拦截 token ， 将用户信息封装到usernamepasswordauthenticationToken中 完成授权
 * @see com.mxg.oauth2.resource.filter <br>
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. 从请求头中获取网关转发过来的明文 token
        String authToken = request.getHeader("auth-token");

        if (StringUtils.isNotEmpty(authToken)) {
            //对authtoken 进行base 64 解码
            String authTokenJson = new String(Base64Utils.decodeFromString(authToken));
            //转成json对象
            JSONObject json = JSON.parseObject(authTokenJson);
            //取到用户权限
            String authorities = ArrayUtils.toString(json.getJSONArray("authorities").toArray());

            //构建一个 authentication 对象，springsecurity会用于权限判断
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(json.get("principal"), null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

            //请求详情
            usernamePasswordAuthenticationToken.setDetails(json.get("details"));

            //传递上下文，这样服务可以进行获取对应的数据
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }

        //放行
        filterChain.doFilter(request,response);
    }
}
