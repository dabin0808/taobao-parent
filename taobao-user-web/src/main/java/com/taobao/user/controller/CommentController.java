package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.goods.service.GoodsService;
import com.taobao.pojo.TbComment;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.CommentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Reference
    private CommentService commentService;


    @RequestMapping("/findByStar")
    public List<TbComment> findByStar(Long goodsId,Integer star){
        return commentService.findByStar(goodsId,star);
    }


    /**
     * 添加一条评价
     */
    @RequestMapping("/add")
    public Result addComment(@RequestBody TbComment comment){
        return commentService.addComment(comment);
    }
}
