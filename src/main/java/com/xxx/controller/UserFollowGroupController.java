package com.xxx.controller;


import com.xxx.entity.Result;
import com.xxx.service.UserFollowGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
@Api(tags = "关注分组")
@RestController
@RequestMapping("/user-follow-group")
public class UserFollowGroupController {

    private final UserFollowGroupService userFollowGroupService;

    public UserFollowGroupController(UserFollowGroupService userFollowGroupService) {
        this.userFollowGroupService = userFollowGroupService;
    }

    @GetMapping
    @ApiOperation("获取所有分组")
    public Result getFollowGroup(){
        return userFollowGroupService.getFollowGroup();
    }
}

