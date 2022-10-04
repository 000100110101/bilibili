package com.xxx.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.entity.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
    * token失效或未登录访问，自定义返回结果
    * */
@Component
public class ResultAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Result result = new Result(555,"当前未登录");
        response.setStatus(401);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
