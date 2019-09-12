package com.taobao.pojo.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
购物车
 */
public class Cart implements Serializable {

    private Long userId;
    private List<CartItem> cartItemList = new ArrayList();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
