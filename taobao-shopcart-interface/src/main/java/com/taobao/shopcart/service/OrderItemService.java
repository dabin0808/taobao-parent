package com.taobao.shopcart.service;

import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.group.Result;

import java.util.List;

public interface OrderItemService {

  /*  *//**
     * 保存订单项到redis
     *//*
    public Result save(List<TbOrderItem> orderItemList,Long userId);*/


    /**
     * 添加订单项到数据库
     */
    public void add(List<TbOrderItem> orderItemList);
}
