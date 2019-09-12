package com.taobao.shopcart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.taobao.mapper.TbOrderMapper;
import com.taobao.pojo.TbOrder;
import com.taobao.shopcart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Override
    public void add(TbOrder order) {
        tbOrderMapper.insert(order);
    }

    @Override
    public TbOrder findById(Long id) {
        return tbOrderMapper.selectByPrimaryKey(id);
    }
}
