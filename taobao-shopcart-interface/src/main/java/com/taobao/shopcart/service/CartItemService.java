package com.taobao.shopcart.service;

import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;

import java.util.List;

public interface CartItemService {

    /**
     * 添加用户的购物列表到redis
     */
    public void add(List<CartItem> cartItemList,Long userId);

    /**
     * 从redis中取出用户最近添加的购物项列表
     */
    public List<CartItem>  getCartItemList(Long userId);

    /**
     *
     */
}
