package com.xxx.controller;


import com.xxx.entity.Dynamic;
import com.xxx.entity.Result;
import com.xxx.service.DynamicService;
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
@Api(tags = "动态管理")
@RestController
@RequestMapping("/dynamic")
public class DynamicController {
    private final DynamicService dynamicService;

    public DynamicController(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    @PostMapping
    @ApiOperation("添加动态")
    public Result insert(@RequestBody Dynamic dynamic){
        return dynamicService.insert(dynamic);
    }
}

