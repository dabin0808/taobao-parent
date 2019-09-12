package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.exceptions.ClientException;
import com.taobao.mapper.TbOrderMapper;
import com.taobao.mapper.TbUserMapper;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.TbUserExample;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import utils.SmsUtil;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TbUser findByUserNameAndPassword(TbUser user) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andPasswordEqualTo(user.getPassword());
        List<TbUser> tbUsers = userMapper.selectByExample(tbUserExample);
        if (tbUsers == null || tbUsers.size() == 0) {
            return null;
        }
        return tbUsers.get(0);
    }

    @Override
    public TbUser findByUserName(String username) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(userExample);
        return (userList == null || userList.size() == 0) ? null : userList.get(0);
    }

    @Override
    public TbUser findByTelphone(String telphone) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTelphoneEqualTo(telphone);
        List<TbUser> users = userMapper.selectByExample(userExample);
        return (users == null || users.size() == 0) ? null : users.get(0);
    }

    @Override
    public void saveUser(TbUser user) {
        userMapper.insert(user);
    }

    @Autowired
    TbOrderMapper orderMapper;
    @Override
    public void paySuccess(TbOrder tbOrder) {

        orderMapper.updateByPrimaryKeySelective(tbOrder);
    }

    @Override
    public String sendMsmVerificationCode(String telphone) throws ClientException {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        for (int i = 0; i < 6; i++) {
            int index = (int) Math.ceil(Math.random() * str.length()) - 1;
            sb.append(str.charAt(index));
        }
        String code = sb.toString();
        System.out.println("UserServiceImpl: " + code);
        SmsUtil.sendSms(telphone, "百万项目团队", "SMS_173251321", code);
        return code;
    }

    @Override
    public TbUser findOne(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbUser user) {
        userMapper.updateByPrimaryKey(user);
    }

}
