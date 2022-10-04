package com.xxx.controller;


import com.xxx.entity.Result;
import com.xxx.service.UserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JINMING
 * @since 2022-10-01
 */
@Api(tags = "用户关注")
@RestController
@RequestMapping("/user-follow")
public class UserFollowController {

    private final UserFollowService userFollowService;

    public UserFollowController(UserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @ApiOperation("添加关注")
    @PostMapping("/{fid}/{gid}")
    public Result insert(@PathVariable @ApiParam("被关注的id") Integer fid,
                         @PathVariable @ApiParam("组id") Integer gid){
        return userFollowService.insert(fid,gid);
    }

    @ApiOperation("取消关注")
    @DeleteMapping("/{fid}")
    public Result remove(@PathVariable @ApiParam("取关的id") Integer fid){
        return userFollowService.remove(fid);
    }

    @ApiOperation("修改分组")
    @PutMapping("/{fid}/{gid}")
    public Result edit(@PathVariable @ApiParam("要修改分组的id") Integer fid,
                       @PathVariable @ApiParam("组id") Integer gid){
        return userFollowService.edit(fid,gid);
    }

    @ApiOperation("获取粉丝列表")
    @GetMapping("/{page}/{limit}")
    public Result page(@PathVariable @ApiParam("每页数量") Integer limit,
                       @PathVariable @ApiParam("第几页") Integer page,
                       @PathVariable @ApiParam("用户id") Integer uid){
        return userFollowService.getFansList((page - 1)*limit, limit,uid);
    }

    @ApiOperation("分组列表")
    @GetMapping("/{page}/{limit}/{gid}")
    public Result getGroupContainUser(@PathVariable @ApiParam("组id") Integer gid,
                                      @PathVariable @ApiParam("每页数量") Integer limit,
                                      @PathVariable @ApiParam("第几页") Integer page){
        return userFollowService.getGroupContainUser((page - 1)*limit,limit,gid);
    }
}

