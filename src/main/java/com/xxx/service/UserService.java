package com.xxx.service;

import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.dto.UserDto;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
public interface UserService extends IService<User> {

    User getUSerByUsername(String username);

    Result insert(UserDto userDto, BindingResult result);

    Result delete(Integer id);

    Result update(User user);

    Result info(Integer id);

    Result info();

    Result login(UserDto userDto, BindingResult result, HttpServletRequest request);

    Result getToken(String token);

    Result logout();
}
