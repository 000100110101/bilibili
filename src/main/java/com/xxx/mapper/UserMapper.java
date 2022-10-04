package com.xxx.mapper;

import com.xxx.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
public interface UserMapper extends BaseMapper<User> {

    void removeToken(String username);

    void insertToken(@Param("username") String username,@Param("refreshToken") String refreshToken);

    String selectByToken(String username);
}
