package com.taobao.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.taobao.common.CookieUtils;
import com.taobao.common.UpLoadImageBed;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：肖楠
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 50000000)
    private UserService userService;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody TbUser user) {
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
        Map<String, Object> map = new HashMap<>();
        TbUser selectedUser = userService.findByUserNameAndPassword(user);
        if (selectedUser == null) {
            map.put("success", false);
            map.put("message", "用户名或密码错误");
        } else {
            map.put("success", true);
            map.put("message", "登录成功");
        }
        return map;
    }

    @RequestMapping("/register")
    public Map<String, Object> register(@RequestBody TbUser user) {
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(user.getTelphone());
        Map<String, Object> map = new HashMap<>();
        TbUser userByName = userService.findByUserName(user.getUsername());
        if (userByName != null) {
            map.put("success", false);
            map.put("message", "该用户名已存在");
            return map;
        }
        TbUser userByTel = userService.findByTelphone(user.getTelphone());
        if (userByTel != null) {
            map.put("success", false);
            map.put("message", "该手机号码已注册过了");
            return map;
        }
        userService.saveUser(user);
        map.put("success", true);
        map.put("message", "注册成功");
        return map;
    }

    @RequestMapping("/existTelphone")
    public Map<String, Object> existTelphone(@RequestBody TbUser user) {
        Map<String, Object> map = new HashMap<>();
        TbUser userByTel = userService.findByTelphone(user.getTelphone());
        if (userByTel != null) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/sendMsmVerificationCode")
    public String sendMsmVerificationCode(String telphone) {
        try {
            return userService.sendMsmVerificationCode(telphone);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送短信出异常了");
        }
    }

    @RequestMapping("/findOne")
    public TbUser findOne(Long id) {
        return userService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody TbUser user, HttpServletRequest request, HttpServletResponse response) {
        try {
            user.setUpdateTime(new Date());
            userService.update(user);
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("USER_TOKEN");
            String jsonString = JSONObject.toJSONString(user);
            //返回修改
            CookieUtils.setCookie(request, response, token, jsonString);
            return new Result(true, "修改成功", user);
        } catch (Exception e) {
            e.printStackTrace();
            user = userService.findOne(user.getId());       //返回修改之前
            return new Result(false, "修改失败", user);
        }
    }

    @RequestMapping("/uploadImage")
    public Map uploadImage(MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        System.out.println("图片上传方法: " + file.getName());
        result.put("msg", "图片上传成功");
        String url = "";
        try {
            url = UpLoadImageBed.up(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            result.put("msg", "图片上传失败");
        }
        result.put("url", url);

        return result;
    }

}
