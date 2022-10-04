package com.xxx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vote")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建用户
     */
    private Integer uid;

    /**
     * 投票标题
     */
    private String title;

    /**
     * 说明
     */
    private String description;

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
     * 有效时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime validModified;

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
     * 0正常,1冻结
     */
    private Integer status;

    /**
     * 0单选,1多选
     */
    private Integer multi;

    @TableField(exist = false)
    private Integer day;

    @TableField(exist = false)
    private List<String> items;


}
