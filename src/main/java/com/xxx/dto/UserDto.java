package com.xxx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("登录对象")
public class UserDto {

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @ApiModelProperty("密码")
    private String pwd;

    @NotEmpty
    @NotNull
    @ApiModelProperty("uuid")
    private String uuid;

    @Length(min = 4,max = 4)
    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("用户名")
    public String name;
}
