package com.taobao.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.pay.service.PayMapper;
import com.taobao.pojo.TbGoods;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付服务
 */
@Service
public class ImplPayMapper implements PayMapper {
    @Autowired
    TbGoods dao;
    @Override
    public void execute() {

    }
}
