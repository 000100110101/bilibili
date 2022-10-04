package com.xxx.aop;

import com.xxx.entity.User;
import com.xxx.entity.UserInfo;
import com.xxx.handle.CustomizeException;
import com.xxx.utils.SecurityContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class SelfEditSelfAspect {

    @Pointcut("@annotation(com.xxx.annotation.SelfEditSelf)")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        User user = SecurityContextUtil.getLoginUser();
        for (Object arg : args) {
            if (arg instanceof UserInfo){
                if (!((UserInfo) arg).getUid().equals(user.getId())) {
                    throw new CustomizeException("参数异常",500);
                }
            }
        }

    }
}
