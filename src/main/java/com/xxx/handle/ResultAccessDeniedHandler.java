package com.xxx.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.entity.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
* 访问权限不足,自定义返回结果
* */
@Component
public class ResultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Result result = new Result(403,"当前权限不足");
        response.setStatus(403);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
