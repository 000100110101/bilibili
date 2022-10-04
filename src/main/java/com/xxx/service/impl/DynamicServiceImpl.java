package com.xxx.service.impl;

import com.xxx.entity.Dynamic;
import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.mapper.DynamicMapper;
import com.xxx.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.utils.SecurityContextUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {

    private final RabbitTemplate rabbitTemplate;

    public DynamicServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Result insert(Dynamic dynamic) {
        User user = SecurityContextUtil.getLoginUser();
        dynamic.setUid(user.getId());
        if (save(dynamic)){
            rabbitTemplate.convertAndSend("dynamic",dynamic);
            return Result.success();
        }
        return Result.error();
    }
}
