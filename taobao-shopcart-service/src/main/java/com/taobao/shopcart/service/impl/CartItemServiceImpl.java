package com.taobao.shopcart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.taobao.pojo.group.CartItem;
import com.taobao.shopcart.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(List<CartItem> cartItemList, Long userId) {
        String jsonString = JSONArray.toJSONString(cartItemList);
        redisTemplate.boundHashOps("CARTITEM").put(String.valueOf(userId), jsonString);
        //再删除购物车中对应的商品
        String json = (String) redisTemplate.boundHashOps("SHOP_CART").get(String.valueOf(userId));
        List<CartItem> cartItemList2 = JSONArray.parseArray(json, CartItem.class);

        List<Long> idList = new ArrayList();
        for(CartItem item:cartItemList){
            idList.add(item.getGoodsId());
        }
        Iterator<CartItem> iterator = cartItemList2.iterator();
        while (iterator.hasNext()){
            CartItem cartItem = iterator.next();
            if(idList.contains(cartItem.getGoodsId())){
                iterator.remove();
            }
        }
        String json2 = JSONArray.toJSONString(cartItemList2);
        redisTemplate.boundHashOps("SHOP_CART").put(String.valueOf(userId),json2);



    }

    @Override
    public List<CartItem> getCartItemList(Long userId) {

        String jsonCartItem = (String) redisTemplate.boundHashOps("CARTITEM").get(String.valueOf(userId));
        List<CartItem> cartItems = JSONArray.parseArray(jsonCartItem, CartItem.class);
        return cartItems;
    }


}
