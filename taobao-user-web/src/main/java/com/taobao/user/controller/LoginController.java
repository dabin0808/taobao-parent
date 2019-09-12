package com.taobao.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.taobao.common.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class LoginController {

    @Reference(timeout = 50000000)
    private UserService userService;

    @RequestMapping("/login")
    public Result login(@RequestBody TbUser user, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String token1 = (String) session.getAttribute("USER_TOKEN");
        System.out.println("token:" + token1);
        //存在用户token直接不用登录
        if (token1 != null && !"".equals(token1)) {

            System.out.println("有token了。。。。。");
            String cookieValue = CookieUtils.getCookieValue(request, token1);
            if(cookieValue==null||cookieValue==""){
                //会话过期
                session.invalidate();
                return new Result(false, "用户会话过期");
            }
            return new Result(true, token1);
        }
        try {
            TbUser user1 = userService.findByUserNameAndPassword(user);
            System.out.println("user : "+user1.toString());
            String token = UUID.randomUUID().toString();
            //将用户token保存在session中
            CookieUtils.setCookie(request, response, token, JSON.toJSONString(user1), 60*60*1000);

            if (user1 != null) {
                session.setAttribute("USER_TOKEN", token);

                //将用户信息保存在cookie中
                return new Result(true, token, user1);
            } else {
                return new Result(false, "用户名或密码有误");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, "登录出错");
        }

    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
        String token = (String) session.getAttribute("USER_TOKEN");
        if (token == null || "".equals(token)) {
            return new Result(false, "用户会话已过期");
        }
        //销毁会话
        session.invalidate();
        //从cookie中取出用户会话
       CookieUtils.deleteCookie(request,response,token);

        return new Result(true,"用户注销成功");
    }

    @RequestMapping("/getToken")
    public Result getToken(HttpServletRequest request,HttpSession session){
        String token  = (String) session.getAttribute("USER_TOKEN");

        if(token!=null){
            String value = CookieUtils.getCookieValue(request, token);


            return new Result(true,value);
        }

        return new Result(false,"用户会话不存在");
    }

    /**
     * 合并购物车
     */



}
