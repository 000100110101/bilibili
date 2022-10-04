package com.xxx.controller;


import com.xxx.entity.Result;
import com.xxx.entity.Vote;
import com.xxx.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JINMING
 * @since 2022-10-03
 */
@Api(tags = "投票处理")
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @ApiOperation("新建投票")
    @PostMapping
    public Result insert(@RequestBody Vote vote){
        return voteService.insert(vote);
    }
}

