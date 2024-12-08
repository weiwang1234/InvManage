package com.happycode.security;

import com.happycode.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 从请求头中获取 Authorization 字段
        String token = request.getHeader("Authorization");

        // 如果 token 存在并且以 "Bearer " 开头
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // 去掉 "Bearer " 前缀

            // 如果 token 未过期，进行解析并进行认证
            if (!JwtUtil.isTokenExpired(token)) {
                String username = JwtUtil.getUsernameFromToken(token);

                // 这里我们可以根据用户名加载用户的具体信息（如角色等），可以通过 userDetailsService 实现
                // 在这个简单实现中，我们默认用户名是认证信息的凭据
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, null);

                // 将认证信息存入 Spring Security 上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 将请求继续传递给下一个过滤器
        filterChain.doFilter(request, response);
    }
}
