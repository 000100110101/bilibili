package com.xxx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("t_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */

    private Integer uid;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 0未知,1男,2女
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 0保留,1删除
     */
    private Integer remove;

    /**
     * 个人简介
     */
    private Integer description;


}
