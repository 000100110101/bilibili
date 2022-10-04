package com.xxx.mapper;

import com.xxx.entity.UserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.vo.UserVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
public interface UserFollowMapper extends BaseMapper<UserFollow> {
    public List<UserVo> getFansList(@Param("uid") Integer uid,@Param("page") Integer page,@Param("limit") Integer limit);
    public List<UserVo> getGroupContainUser(@Param("uid") Integer uid,@Param("page") Integer page,@Param("limit") Integer limit,@Param("gid") Integer gid);

}
