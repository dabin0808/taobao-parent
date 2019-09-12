package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.goods.service.GoodsService;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goodsDetail")
public class GoodsDetailController {

    @Reference(timeout = 600000)
    private GoodsService goodsService;

    /*@Reference
    private UserMapper userMapper;*/

    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

   /* @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody TbUser user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        Map<String, Object> map = new HashMap<>();
        TbUser selectedUser = userMapper.findByUserNameAndPassword(user);
        if (selectedUser == null) {
            map.put("success",false);
            map.put("message","用户名或密码错误");
        } else {
            map.put("success",true);
            map.put("message","登录成功");
        }
        return map;
    }*/
}
