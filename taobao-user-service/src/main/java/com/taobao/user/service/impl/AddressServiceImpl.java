package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taobao.mapper.TbAddressMapper;
import com.taobao.pojo.TbAddress;
import com.taobao.pojo.TbAddressExample;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.PageResult;
import com.taobao.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: dabin0808
 * @Date: 2019/8/27 19:25
 * Explain:用户地址实现类
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    TbAddressMapper addressMapper;

    /**
     * 查询用户Id地址
     */
    @Override
    public List<TbAddress> findUserId(TbUser tbUser) {
        TbAddressExample tx = new TbAddressExample();
        tx.createCriteria().andUserIdEqualTo(tbUser.getId());
        return addressMapper.selectByExample(tx);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbAddress> page = (Page<TbAddress>) addressMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void addAddress(TbAddress address) {
        addressMapper.insert(address);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbAddress address) {
        addressMapper.updateByPrimaryKey(address);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbAddress findOne(Long id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            addressMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbAddress address, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbAddressExample example = new TbAddressExample();
        TbAddressExample.Criteria criteria = example.createCriteria();

        if (address != null) {
            if (address.getReceiverAddress() != null && address.getReceiverAddress().length() > 0) {
                criteria.andReceiverAddressLike("%" + address.getReceiverAddress() + "%");
            }
            if (address.getPhone() != null && address.getPhone().length() > 0) {
                criteria.andPhoneLike("%" + address.getPhone() + "%");
            }
            if (address.getContact() != null && address.getContact().length() > 0) {
                criteria.andContactLike("%" + address.getContact() + "%");
            }
            if (address.getAlias() != null && address.getAlias().length() > 0) {
                criteria.andAliasLike("%" + address.getAlias() + "%");
            }
            if (address.getUserId() != null) {
                criteria.andUserIdEqualTo(address.getUserId());
            }

        }

        Page<TbAddress> page = (Page<TbAddress>) addressMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbAddress> listByUserId(Long userId) {
        TbAddressExample example = new TbAddressExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return addressMapper.selectByExample(example);
    }


}
