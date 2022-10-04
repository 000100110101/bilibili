package com.xxx.handle;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xxx.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* 全局统一异常处理
* */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JWTVerificationException.class)
    public Result jwtException(){
        return new Result(555,"参数异常,请重新登录");
    }

    //自定义异常
    @ExceptionHandler(CustomizeException.class)
    public Result customizeException(CustomizeException e){
        return new Result(e.getCode(),e.getMessage());
    }

    //其他异常
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        return Result.error("操作异常");
    }
}
