package com.xxx.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel("响应消息体")
public class Result implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;
    @ApiModelProperty("响应消息")
    private String msg;
    @ApiModelProperty("响应结果")
    private Map<String,Object> data = new HashMap<>();

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Map<String,Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(){
        return new Result(200,"操作成功");
    }

    public static Result error(){
        return new Result(500,"操作失败",null);
    }

    public static Result error(String msg){
        return new Result(500,msg,null);
    }

    //链式添加数据
    public Result put(String key, Object data){
        this.data.put(key,data);
        return this;
    }


}