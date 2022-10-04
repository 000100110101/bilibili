package com.xxx.controller;


import com.xxx.annotation.SelfEditSelf;
import com.xxx.entity.Result;
import com.xxx.entity.UserInfo;
import com.xxx.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @SelfEditSelf
    @PutMapping
    @ApiOperation("更新信息")
    public Result updateBySelf(@RequestBody @ApiParam("用户信息对象") UserInfo userInfo){
        return update(userInfo);
    }

    @PutMapping("/admin")
    @ApiOperation("管理员修改用户")
    public Result update(@RequestBody @ApiParam("用户信息对象") UserInfo userInfo){
        return userInfoService.updateByAdmin(userInfo);
    }
}

