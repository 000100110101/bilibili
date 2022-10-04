package com.xxx.service;

import com.xxx.entity.Result;
import com.xxx.entity.UserFollow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
public interface UserFollowService extends IService<UserFollow> {
    Result insert(Integer fid,Integer gid);

    Result remove(Integer fid);

    Result edit(Integer fid, Integer gid);

    //分页查询用户粉丝
    Result getFansList(Integer page, Integer limit, Integer uid);

    //获取用户所有粉丝列表
    List<UserFollow> getAllFans(Integer uid);

    Result getGroupContainUser(Integer page,Integer limit,Integer gid);
}
