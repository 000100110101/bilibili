package com.xxx.service;

import com.xxx.entity.Result;
import com.xxx.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.validation.BindingResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-09-29
 */
public interface UserInfoService extends IService<UserInfo> {

    Result updateBySelf(UserInfo userInfo);

    Result updateByAdmin(UserInfo userInfo);
}
