package com.taobao.shopcart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.taobao.mapper.TbOrderItemMapper;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.group.Result;
import com.taobao.shopcart.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;


/*    @Override
    public Result save(List<TbOrderItem> orderItemList, Long userId) {
        redisTemplate.boundHashOps("ORDER_ITEM").put(String.valueOf(userId), JSONArray.toJSONString(orderItemList));
        return null;
    }*/

    @Override
    public void add(List<TbOrderItem> orderItemList) {


        for (TbOrderItem orderItem : orderItemList) {

            orderItemMapper.insert(orderItem);
        }
    }


}