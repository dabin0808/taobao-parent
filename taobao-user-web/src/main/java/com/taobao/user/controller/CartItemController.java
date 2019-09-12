package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.taobao.common.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;
import com.taobao.pojo.group.Result;
import com.taobao.shopcart.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {


    @Reference(timeout = 50000000)
    private CartItemService cartItemService;



    @RequestMapping("/add")
    public Result addCartItem(@RequestBody ArrayList<CartItem> cartItemList, HttpServletRequest request){
        System.out.println("cartitemList:"+cartItemList);

         try {
           HttpSession session = request.getSession();
           String token = (String) session.getAttribute("USER_TOKEN");
           String cookieValue = CookieUtils.getCookieValue(request, token);
           TbUser user = JSON.parseObject(cookieValue, TbUser.class);
           cartItemService.add(cartItemList,user.getId());
           return new Result(true,"成功添加");
       }catch (RuntimeException e){
           e.printStackTrace();
           return new Result(false,"添加失败");
       }


    }


    @RequestMapping("/get")
    public List<CartItem> getCartItemList(HttpServletRequest request){

            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("USER_TOKEN");
            String cookieValue = CookieUtils.getCookieValue(request, token);
            TbUser user = JSON.parseObject(cookieValue, TbUser.class);
            List<CartItem> cartItemList = cartItemService.getCartItemList(user.getId());
            return cartItemList;
    }

}
