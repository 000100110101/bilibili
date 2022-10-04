package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.entity.UserFollow;
import com.xxx.entity.UserFollowGroup;
import com.xxx.mapper.UserFollowGroupMapper;
import com.xxx.service.UserFollowGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.service.UserFollowService;
import com.xxx.utils.SecurityContextUtil;
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
public class UserFollowGroupServiceImpl extends ServiceImpl<UserFollowGroupMapper, UserFollowGroup> implements UserFollowGroupService {

    private final UserFollowService userFollowService;

    public UserFollowGroupServiceImpl(UserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @Override
    public Result getFollowGroup() {
        User user = SecurityContextUtil.getLoginUser();
        List<UserFollowGroup> userFollowGroups = baseMapper.selectList(new LambdaQueryWrapper<UserFollowGroup>().eq(UserFollowGroup::getUid, user.getId()).or().isNull(UserFollowGroup::getUid));
        for (UserFollowGroup userFollowGroup : userFollowGroups) {
            userFollowGroup.setCount(userFollowService.count(new LambdaQueryWrapper<UserFollow>().eq(UserFollow::getGid,userFollowGroup.getId()).eq(UserFollow::getUid,user.getId())));
        }
        return Result.success().put("list",userFollowGroups);
    }

}
