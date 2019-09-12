package com.taobao.search.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.group.PageResult;
import com.taobao.search.service.GoodsSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsSearchController {

    @Reference(timeout = 600000)
    private GoodsSearchService goodsSearchService;

    @RequestMapping("/search")
    public Map search(@RequestBody Map searchMap) {
        System.out.println("搜索条件："+searchMap);
        return goodsSearchService.search(searchMap);

    }

}
