package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taobao.mapper.TbOrderItemMapper;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbOrderItemExample;
import com.taobao.pojo.group.PageResult;
import com.taobao.user.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbOrderItem> findAll() {
        return orderItemMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbOrderItem> page = (Page<TbOrderItem>) orderItemMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbOrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbOrderItem orderItem) {
        orderItemMapper.updateByPrimaryKey(orderItem);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbOrderItem findOne(Long id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            orderItemMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbOrderItem orderItem, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();

        if (orderItem != null) {
            if (orderItem.getGoodsImages() != null && orderItem.getGoodsImages().length() > 0) {
                criteria.andGoodsImagesLike("%" + orderItem.getGoodsImages() + "%");
            }
            if (orderItem.getTitle() != null && orderItem.getTitle().length() > 0) {
                criteria.andTitleLike("%" + orderItem.getTitle() + "%");
            }

        }

        Page<TbOrderItem> page = (Page<TbOrderItem>) orderItemMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbOrderItem> listByOrderId(Long orderId) {
        TbOrderItemExample example = new TbOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return orderItemMapper.selectByExample(example);
    }

}
