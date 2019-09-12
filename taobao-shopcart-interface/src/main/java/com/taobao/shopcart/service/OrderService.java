package com.taobao.shopcart.service;

import com.taobao.pojo.TbOrder;

public interface OrderService {

    /**
     * 产生一个订单
     * @param order
     */
    public void add(TbOrder order);

    /**
     * 通过id查询订单
     */
    public TbOrder findById(Long id);
}
