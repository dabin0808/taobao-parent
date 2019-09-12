package com.taobao.user.service;

import com.taobao.pojo.TbAddress;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.PageResult;

import java.util.List;

/**
 * @Author: dabin0808
 * @Date: 2019/8/27 19:22
 * Explain: 用户地址接口
 */
public interface AddressService {

    /**
     * 返回用户ID全部列表
     *
     * @return
     */
    public List<TbAddress> findUserId(TbUser tbUser);


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void addAddress(TbAddress address);


    /**
     * 修改
     */
    public void update(TbAddress address);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbAddress findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbAddress address, int pageNum, int pageSize);

    /**
     * 得到用户的所有收货地址
     */
    public List<TbAddress> listByUserId(Long userId);
}
