package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.entity.UserFollow;
import com.xxx.mapper.UserFollowMapper;
import com.xxx.service.UserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.utils.SecurityContextUtil;
import com.xxx.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Override
    public Result insert(Integer fid,Integer gid) {
        if (fid == null || gid == null)
            return Result.error("参数异常");
        User user = SecurityContextUtil.getLoginUser();
        UserFollow userFollow = getOne(new LambdaQueryWrapper<UserFollow>().eq(UserFollow::getFid, fid).eq(UserFollow::getUid, user.getId()));
        if (userFollow != null)
            return Result.error("已经关注了!");
        userFollow = new UserFollow(user.getId(), fid,1);
        if (save(userFollow))
            return Result.success();
        return Result.error();
    }

    @Override
    public Result remove(Integer fid) {
        if (fid == null)
            return Result.error("参数异常");
        User user = SecurityContextUtil.getLoginUser();
        if (remove(new LambdaQueryWrapper<UserFollow>().eq(UserFollow::getUid,user.getId()).eq(UserFollow::getFid,fid)))
            return Result.success();
        return Result.error();
    }

    @Override
    public Result edit(Integer fid, Integer gid) {
        if (fid == null || gid == null)
            return Result.error("参数异常");
        User user = SecurityContextUtil.getLoginUser();
        UserFollow userFollow = getOne(new LambdaQueryWrapper<UserFollow>().eq(UserFollow::getFid, fid).eq(UserFollow::getUid, user.getId()));
        if (userFollow == null)
            return Result.error("参数异常!");
        userFollow.setGid(gid);
        if (updateById(userFollow))
            return Result.success();
        return Result.error();
    }


    @Override
    public Result getFansList(Integer page, Integer limit, Integer uid) {
        List<UserVo> fansList = baseMapper.getFansList(uid, page, limit);
        return Result.success().put("list",fansList);
    }


    @Override
    public List<UserFollow> getAllFans(Integer uid) {
        return baseMapper.selectList(new LambdaQueryWrapper<UserFollow>().eq(UserFollow::getFid, uid));
    }

    @Override
    public Result getGroupContainUser(Integer page,Integer limit,Integer gid) {
        User user = SecurityContextUtil.getLoginUser();
        List<UserVo> list = baseMapper.getGroupContainUser(user.getId(), page, limit, gid);
        return Result.success().put("list",list);
    }

}
