package com.xxx.service.impl;

import com.xxx.entity.Result;
import com.xxx.entity.UserInfo;
import com.xxx.mapper.UserInfoMapper;
import com.xxx.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public Result updateBySelf(UserInfo userInfo) {
        if (updateById(userInfo))
            return Result.success();
        return Result.error();
    }

    @Override
    public Result updateByAdmin(UserInfo userInfo) {
        if (updateById(userInfo))
            return Result.success();
        return Result.error();
    }
}
