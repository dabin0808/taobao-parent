package com.taobao.goods.service;


import com.taobao.pojo.group.Goods;

public interface GoodsService {

    /**
     * 通过id获得组合实体
     */
    public Goods findOne(Long id);
}
