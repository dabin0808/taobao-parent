package com.taobao.shopcart.service;

import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;

import java.util.List;

public interface ShopCartService {


    /**
     * 更新购物车
     *
     */
    void updateCart(Cart cart);

    /**
     * 得到用户购物车
     */
    Cart getCart(Long userId);

    /**
     * 删除redis中提交的购物项的列表
     */

    void delete(List<Long> idList, Long userId);
}
