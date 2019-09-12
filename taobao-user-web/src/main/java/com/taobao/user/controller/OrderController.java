package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;
import com.taobao.pojo.group.Result;
import com.taobao.shopcart.service.CartItemService;
import com.taobao.shopcart.service.OrderItemService;
import com.taobao.shopcart.service.OrderService;
import com.taobao.shopcart.service.ShopCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Reference(timeout = 50000000)
    private OrderService orderService;

    @Reference(timeout = 50000000)
    private OrderItemService orderItemService;

    @Reference(timeout = 50000000)
    private ShopCartService shopCartService;
    /**
     * 创建一个订单  订单中有多个订单项
     * @param
     * @return
     */
    @RequestMapping("/add")
    public Result addOrderAndOrderItem(@RequestBody Map map){

        List<JSONObject> cartItemList = (List<JSONObject>) map.get("cartItemList");
        long addressId = (int) map.get("addressId");
        long userId = (int) map.get("userId");
        JSONObject jsonObject = cartItemList.get(0);


        TbOrder tbOrder = new TbOrder();
        long id = System.currentTimeMillis();
        tbOrder.setId(id);
        tbOrder.setAddressId(addressId);
        tbOrder.setCreateTime(new Date());
        double totalPrice = 0;
        List<TbOrderItem> orderItemList = new ArrayList();
        //保存提交了订单的购物项
        List<Long> idList = new ArrayList();
        for (JSONObject itemJson:cartItemList){
            CartItem cartItem = itemJson.toJavaObject(CartItem.class);
            TbOrderItem orderItem = new TbOrderItem();
            //对象复制
            BeanUtils.copyProperties(cartItem,orderItem);
            orderItem.setGoodsImages(cartItem.getGoodsImage());
            orderItem.setOrderId(tbOrder.getId());
            orderItemList.add(orderItem);

            //订单总价格
            totalPrice+=cartItem.getTotalPrice();

            idList.add(cartItem.getId());
        }
        tbOrder.setPaymentPrice(totalPrice);
        tbOrder.setPaymentStatus(0);
        tbOrder.setPaymentType(0);
        tbOrder.setUpadteTime(new Date());

        //添加订单到数据库
        orderService.add(tbOrder);
        //添加订单项到数据库
        orderItemService.add(orderItemList);



        //删除redis中提交的购物项的列表
        shopCartService.delete(idList,userId);

       return new Result(true,"添加订单成功",tbOrder);

    }

    @RequestMapping("/get")
    public TbOrder getOrder(Long id){
        return orderService.findById(id);
    }



}
