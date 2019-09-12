package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.group.CollectionVo;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.CollectionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Reference(timeout = 500000)
    private CollectionService collectionService;


    @RequestMapping("/add")
    public Result add(Long goodsId,Long userId) {
        return collectionService.add(goodsId,userId);
    }

    @RequestMapping("/list")
    public List<CollectionVo> listByUserId(Long userId){
        return collectionService.listByUserId(userId);
    }
    @RequestMapping("/delete")
    public Result delete(Long id){
        return collectionService.delete(id);
    }
}
