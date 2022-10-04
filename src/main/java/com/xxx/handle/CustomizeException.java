package com.xxx.handle;


public class CustomizeException extends RuntimeException{
    private final Integer code;

    public CustomizeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
