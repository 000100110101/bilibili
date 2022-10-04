package com.xxx.controller;

import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.service.UserService;
import com.xxx.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody @ApiParam("用户对象") UserDto userDto, BindingResult result, HttpServletRequest request){
        return userService.login(userDto,result,request);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result login(){
        return userService.logout();
    }

    @ApiOperation("根据刷新token获取登录token")
    @PostMapping("/token")
    public Result getToken(String token){
        return userService.getToken(token);
    }

    @ApiOperation("添加用户")
    @PostMapping
    public Result insert(@Valid @RequestBody @ApiParam("用户对象") UserDto userDto, BindingResult result){
        return userService.insert(userDto,result);
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @ApiParam("用户id") Integer id){
        return userService.delete(id);
    }

    @ApiOperation("更新用户")
    @PutMapping
    public Result update(@RequestBody @ApiParam("用户对象") User user){
        return userService.update(user);
    }

    @ApiOperation("获取本人信息")
    @GetMapping
    public Result info(){
        return userService.info();
    }

    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public Result info(@PathVariable @ApiParam("用户id") Integer id){
        return userService.info(id);
    }
}

