package com.xxx.service;

import com.xxx.entity.Result;
import com.xxx.entity.Vote;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
public interface VoteService extends IService<Vote> {

    Result insert(Vote vote);
}
