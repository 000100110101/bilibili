package com.xxx.service;

import com.xxx.entity.Result;
import com.xxx.entity.UserFollowGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
public interface UserFollowGroupService extends IService<UserFollowGroup> {

    Result getFollowGroup();

}
