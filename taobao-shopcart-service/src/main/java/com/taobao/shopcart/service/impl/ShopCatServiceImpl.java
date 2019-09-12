package com.taobao.shopcart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;
import com.taobao.shopcart.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class ShopCatServiceImpl implements ShopCartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void updateCart(Cart cart) {
        String jsonString = JSONArray.toJSONString(cart.getCartItemList());
        redisTemplate.boundHashOps("SHOP_CART").put(String.valueOf(cart.getUserId()),jsonString);

    }

    @Override
    public Cart getCart(Long userId) {
        String json = (String) redisTemplate.boundHashOps("SHOP_CART").get(String.valueOf(userId));
        List<CartItem> cartItemList = JSONArray.parseArray(json, CartItem.class);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCartItemList(cartItemList);
        return cart;
    }

    @Override
    public void delete(List<Long> idList,Long userId) {
        Cart userCart = getCart(userId);
        List<CartItem> cartItemList = userCart.getCartItemList();
        Iterator<CartItem> iterator = cartItemList.iterator();

        while (iterator.hasNext()){
            CartItem item = iterator.next();
            if(idList.contains(item.getId())){
                iterator.remove();
            }
        }

        //再写回到redis中
        userCart.setCartItemList(cartItemList);

        updateCart(userCart);
    }


}
