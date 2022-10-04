package com.xxx.service.impl;

import com.xxx.entity.Item;
import com.xxx.entity.Result;
import com.xxx.entity.User;
import com.xxx.entity.Vote;
import com.xxx.handle.CustomizeException;
import com.xxx.mapper.VoteMapper;
import com.xxx.service.ItemService;
import com.xxx.service.VoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.utils.SecurityContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {

    private final ItemService itemService;

    public VoteServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public Result insert(Vote vote) {
        User user = SecurityContextUtil.getLoginUser();
        vote.setUid(user.getId());
        vote.setValidModified(LocalDateTime.now().plusDays(vote.getDay()));//有效期
        if (save(vote)){
            if (vote.getItems() == null || vote.getItems().size() < 2){
                throw new CustomizeException("至少两条选项",500);
            }
            int vid = vote.getId();
            List<Item> list = new ArrayList<>();
            for (String item : vote.getItems()) {
                list.add(new Item(vid,item));
            }
            if (!itemService.saveBatch(list)){
                throw new CustomizeException("操作失败",555);
            }
        }else {
            return Result.error();
        }
        return Result.success();
    }
}
