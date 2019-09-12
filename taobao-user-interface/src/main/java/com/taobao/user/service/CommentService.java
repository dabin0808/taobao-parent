package com.taobao.user.service;

import com.taobao.pojo.TbComment;
import com.taobao.pojo.group.Result;

import java.util.List;

public interface CommentService {

    /**
     * 通过评论等级得到评论
     */
    public List<TbComment> findByStar(Long gooodsId,Integer star);

    /**
     * 得到商品的所有评价
     *
     */
    public List<TbComment> findByGoodsId(Long goodsId);

    /**
     * 创建一条评价
     * @param comment
     * @return
     */
    public Result addComment(TbComment comment);
}
