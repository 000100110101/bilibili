package com.xxx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * <p>
 *
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
@ApiModel(value = "用户对象")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称 长度为2-8个字符
     */
    @ApiModelProperty("昵称,长度2-8")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码，长度6-12")
    private String pwd;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @ApiModelProperty("最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    @Version
    private Integer version;

    /**
     * 0保留,1删除
     */
    @JsonIgnore
    @ApiModelProperty(value = "是否删除",hidden = true)
    private Integer remove;

    @TableField(exist = false)
    @ApiModelProperty("用户信息")
    private UserInfo userInfo;

    @ApiModelProperty("是否冻结")
    private Integer status;

    public User(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getStatus() == 0;
    }
}
