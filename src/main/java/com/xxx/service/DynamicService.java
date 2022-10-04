package com.xxx.service;

import com.xxx.entity.Dynamic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.entity.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
public interface DynamicService extends IService<Dynamic> {

    Result insert(Dynamic dynamic);
}
