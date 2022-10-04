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
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_follow")
public class UserFollow implements Serializable {

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
     * 被关注用户id
     */
    private Integer fid;

    /**
     * 关注分组id,1默认分组,2特别关注,自定义分组
     */
    private Integer gid;

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

    public UserFollow(Integer uid, Integer fid, Integer gid) {
        this.uid = uid;
        this.fid = fid;
        this.gid = gid;
    }
}
