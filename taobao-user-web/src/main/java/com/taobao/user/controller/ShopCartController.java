package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.common.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Cart;
import com.taobao.pojo.group.CartItem;
import com.taobao.pojo.group.Result;
import com.taobao.shopcart.service.ShopCartService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/shopCart")
public class ShopCartController {

    private static Integer VISIT_NUM = 0;//访问cookie购物车的次数
    private static final String USER_CART = "SHOPCART";
    @Reference(timeout = 600000)
    private ShopCartService shopCartService;

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public Result addToCart(@RequestBody CartItem cartItem, HttpServletRequest request, HttpServletResponse response) {
        cartItem.setId(System.currentTimeMillis());
        TbUser user = getUser(request);
        if (user != null) {

            //用户登录的情况,保存到redis

            Cart cart = shopCartService.getCart(user.getId());
            if (cart == null) {
                cart = new Cart();
            }
            cart.setUserId(user.getId());
            List<CartItem> cartItemList = cart.getCartItemList();
            //判断购物车中是否存在相同的商品
            boolean noExist = true;
            for (CartItem item : cartItemList) {
                if (item.getGoodsId() == cartItem.getGoodsId() && item.getTitle().equals(cartItem.getTitle())) {
                    item.setNum(item.getNum() + cartItem.getNum());
                    noExist = false;
                    break;
                }
            }
            if (noExist) {

                cartItemList.add(cartItem);
            }
            cart.setCartItemList(cartItemList);
            shopCartService.updateCart(cart);
            return new Result(true, "添加到购物车成功");


        }

        //用户未登录的情况或者会话过期
        try {
            List<CartItem> cartList = getCartList(request);
            if (cartList == null) {
                cartList = new ArrayList();
            }
            //判断购物车中是否存在相同的商品
            boolean noExist = true;
            for (CartItem item : cartList) {
                if (item.getGoodsId() == cartItem.getGoodsId() && item.getTitle().equals(cartItem.getTitle())) {
                    item.setNum(item.getNum() + cartItem.getNum());
                    noExist = false;
                    break;
                }
            }
            if (noExist) {

                cartList.add(cartItem);
            }
            addCookie(cartList, request, response);
            return new Result(true, "添加到购物车成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加到购物车失败");
        }


    }


    /**
     * 得到cookie中购物车的最后一个购物项
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLast")
    public CartItem getLast(HttpServletRequest request) {

        List<CartItem> cartList = getCartList(request);

        if (cartList != null) {
            return cartList.get(cartList.size() - 1);
        }
        return null;
    }

    /**
     * 得到购物车中所有的购物项
     */
    @RequestMapping("/findAll")
    public List<CartItem> findAll(HttpServletRequest request) {
        TbUser user = getUser(request);
        if (user == null) {
            //用户未登录
            return getCartList(request);
        } else {

            //用户登录则需要合并购物车  购物车只需要合并一次
            if (VISIT_NUM < 1) {

                mergeCart(request, user.getId());
            }
            Cart cart = shopCartService.getCart(user.getId());
            if (cart != null) {
                return cart.getCartItemList();
            }

        }
        return null;


    }

    /**
     * 合并cookie中与redis的购物车 用户登录后
     *
     * @param request
     * @param userId
     */
    private void mergeCart(HttpServletRequest request, Long userId) {

        //先得到cookie中的购物车列表
        List<CartItem> cookieCartList = getCartList(request);
        if (CollectionUtils.isEmpty(cookieCartList)) {
            return;
        }
        //得到redis中的购物车
        Cart cart = shopCartService.getCart(userId);
        List<CartItem> redisCartList = cart.getCartItemList();
        //合并逻辑
        if (!CollectionUtils.isEmpty(redisCartList)) {
            Iterator<CartItem> redisCart = redisCartList.iterator();

            while (redisCart.hasNext()) {
                CartItem redisItem = redisCart.next();

                for (CartItem cookieItem : cookieCartList) {
                    //商品id 、商品标题与规格相同才能合并
                    if (redisItem.getGoodsId() == cookieItem.getGoodsId() && redisItem.getTitle().equals(cookieItem.getTitle())) {

                        cookieItem.setNum(cookieItem.getNum() + redisItem.getNum());
                        cookieItem.setTotalPrice(cookieItem.getNum()*cookieItem.getPrice());
                        redisCart.remove();
                        break;
                    }

                }
            }
        }
        List newCartList = new ArrayList();
        //合并
        if (redisCartList != null && redisCartList.size() > 0) {
            newCartList.addAll(redisCartList);
        }
        if (cookieCartList != null && cookieCartList.size() > 0) {
            newCartList.addAll(cookieCartList);
        }
        //更新redis中的购物车
        cart.setCartItemList(newCartList);
        shopCartService.updateCart(cart);
        VISIT_NUM++;

    }

    /**
     * 批量删除
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids, HttpServletRequest request, HttpServletResponse response) {

        if (ids == null || ids.length == 0) {
            return new Result(false, "删除项为空");
        }
        //用户未登录
        String token = (String) request.getSession().getAttribute("USER_TOKEN");
        if (token == null || token == "") {


            List<CartItem> cartList = getCartList(request);
            if (cartList == null) {
                return new Result(false, "购物车为空");
            }
            List<Long> idList = Arrays.asList(ids);
            Iterator<CartItem> iterator = cartList.iterator();
            while (iterator.hasNext()) {
                CartItem cartItem = iterator.next();
                if (idList.contains(cartItem.getId())) {
                    iterator.remove();
                    System.out.println("删除了：" + cartItem.getId());
                }
            }

            System.out.println(cartList.size());
            addCookie(cartList, request, response);
            return new Result(true, "删除成功");
        } else {
            String cookieValue = CookieUtils.getCookieValue(request, token);
            TbUser user = JSONObject.parseObject(cookieValue, TbUser.class);
            Cart cart = shopCartService.getCart(user.getId());
            List<Long> idList = Arrays.asList(ids);
            Iterator<CartItem> iterator = cart.getCartItemList().iterator();
            while (iterator.hasNext()) {
                CartItem cartItem = iterator.next();
                if (idList.contains(cartItem.getId())) {
                    iterator.remove();
                    System.out.println("删除了：" + cartItem.getId());
                }
            }

            shopCartService.updateCart(cart);
            return new Result(true, "删除成功");
        }
    }

    /**
     * 添加购物项到cookie
     *
     * @param cartList
     * @param request
     */
    private void addCookie(List<CartItem> cartList, HttpServletRequest request, HttpServletResponse response) {

        //再将购物车列表重新添加到cookie中
        String cartJson = JSONArray.toJSONString(cartList);
        CookieUtils.setCookie(request, response, USER_CART, cartJson, "utf-8");
    }

    /**
     * 得到cookie中的购物项
     *
     * @param request
     * @return
     */
    private List<CartItem> getCartList(HttpServletRequest request) {
        String cookieValue = CookieUtils.getCookieValue(request, USER_CART);
        try {
            if (cookieValue != null) {
                cookieValue = URLDecoder.decode(cookieValue, "utf-8");
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<CartItem> cartItems = JSONArray.parseArray(cookieValue, CartItem.class);
        return cartItems;
    }

    /**
     * 得到会话中的用户
     */
    private TbUser getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //用户token
        String token = (String) session.getAttribute("USER_TOKEN");
        if (token == null) {
            return null;
        }
        //得到cookie中的用户会话
        String cookieValue = CookieUtils.getCookieValue(request, token);
        if (cookieValue == null) {
            return null;
        }
        TbUser user = JSON.parseObject(cookieValue, TbUser.class);
        return user;
    }


}
