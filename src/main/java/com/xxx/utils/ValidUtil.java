package com.xxx.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidUtil {
    //参数校验
    public static FieldError paramValid(BindingResult result){
        int errorCount = result.getErrorCount();
        if (errorCount > 0){
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                return  fieldError;
            }
        }
        return null;
    }
}
