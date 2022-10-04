package com.xxx.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxx.entity.Dynamic;
import com.xxx.entity.UserFollow;
import com.xxx.service.UserFollowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.xxx.constants.RedisConstants.USER_FOLLOW_DYNAMIC_LIST;

@Component
@RabbitListener(queues = "dynamic")
public class DynamicListener {

    private final RedisTemplate<String,String> redisTemplate;
    private final UserFollowService userFollowService;

    public DynamicListener(RedisTemplate<String, String> redisTemplate, UserFollowService userFollowService) {
        this.redisTemplate = redisTemplate;
        this.userFollowService = userFollowService;
    }

    @RabbitHandler
    public void receive(Dynamic dynamic) {
        //查询该用户粉丝,存储到redis中
        List<UserFollow> list = userFollowService.getAllFans(dynamic.getUid());
        List<Dynamic> newDynamics;
        for (UserFollow userFollow : list) {
            String oldDynamic = redisTemplate.opsForValue().get(USER_FOLLOW_DYNAMIC_LIST + userFollow.getUid());
            if (StringUtils.isEmpty(oldDynamic)){
                newDynamics = new ArrayList<>();
            }else {
                newDynamics = JSONArray.parseArray(oldDynamic, Dynamic.class);
            }
            newDynamics.add(dynamic);
            redisTemplate.opsForValue().set(USER_FOLLOW_DYNAMIC_LIST + userFollow.getUid(), JSONObject.toJSONString(newDynamics));
        }
    }
}