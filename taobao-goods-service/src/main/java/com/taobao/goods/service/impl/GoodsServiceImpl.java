package com.taobao.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.goods.service.GoodsService;
import com.taobao.mapper.TbGoodsDescMapper;
import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.TbGoodsDesc;
import com.taobao.pojo.group.Goods;
import org.springframework.beans.factory.annotation.Autowired;

@Service(timeout = 500000)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Override
    public Goods findOne(Long id) {

        Goods goods = new Goods();

        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);

        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);

        return goods;
    }
}
