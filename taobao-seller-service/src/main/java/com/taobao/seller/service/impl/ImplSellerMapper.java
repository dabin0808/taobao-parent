package com.taobao.seller.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taobao.mapper.TbCategoryMapper;
import com.taobao.mapper.TbGoodsDescMapper;
import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.*;
import com.taobao.pojo.group.PageResult;
import com.taobao.seller.service.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ImplSellerMapper implements SellerMapper {


    @Autowired
    TbCategoryMapper categoryMapper;

    @Autowired
    TbGoodsMapper goodsMapper;

    @Override
    public List<TbCategory> findAllCategory(long id) {
        TbCategoryExample tbCategoryExample = new TbCategoryExample();
        TbCategoryExample.Criteria criteria = tbCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);

        return categoryMapper.selectByExample(tbCategoryExample);
    }

    @Override
    public void updateCategory(TbCategory tbCategory) {

        categoryMapper.updateByPrimaryKey(tbCategory);
    }

    @Override
    public void addCategory(TbCategory tbCategory) {
            categoryMapper.insert(tbCategory);
    }

    @Override
    public void delCategory(long[] ids) {
        for (long id : ids) {
            //先删除本身
            categoryMapper.deleteByPrimaryKey(id);

            //条件
            TbCategoryExample tbCategoryExample = new TbCategoryExample();
            TbCategoryExample.Criteria criteria = tbCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(id);

            //获取直接子
            List<TbCategory> tbCategories = categoryMapper.selectByExample(tbCategoryExample);
            for (TbCategory tbCategory : tbCategories) {
                TbCategoryExample tbCategoryExample2 = new TbCategoryExample();
                TbCategoryExample.Criteria criteria2 = tbCategoryExample2.createCriteria();
                criteria2.andParentIdEqualTo(tbCategory.getId());
                //删除间接子
                categoryMapper.deleteByExample(tbCategoryExample2);
            }
            //删除直接子
            categoryMapper.deleteByExample(tbCategoryExample);
        }

    }

    /**
     * 根据父id查父节点
     * @param
     * @return
     */
    @Override
    public List<TbCategory> findPerentCategory(long id) {
        //要返回的列表
        List<TbCategory> result = new ArrayList<TbCategory>();
        //条件
        long parentId =id;
        while(parentId!=0){
            TbCategory tbCategories = categoryMapper.selectByPrimaryKey(parentId);

//            将查询到的父节点添加到容器
            result.add(tbCategories);

            parentId=tbCategories.getParentId();

        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public TbCategory findOneCategory(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Autowired
    TbGoodsDescMapper goodsDescMapper;
    @Override
    public void addGoods(TbGoods goods) {
        goodsMapper.insert(goods);
        //存入商品详情
        TbGoodsDesc tbGoodsDesc = new TbGoodsDesc();
        tbGoodsDesc.setGoodsId(goods.getId());
        //设置时间
        tbGoodsDesc.setCreateTime(new Date());
        tbGoodsDesc.setCommentNum(0);
        tbGoodsDesc.setCount(0);
        tbGoodsDesc.setCommentStarScore(0);
        goodsDescMapper.insert(tbGoodsDesc);
    }

    @Autowired
    TbGoodsMapper tbGoodsMapper;
    //商品分页查询
    @Override
    public PageResult findGoodsPage(int pageNmu, int pageSize) {

        //这是分页设置
        PageHelper.startPage(pageNmu,pageSize);
        //只查询没有删除的
        TbGoodsExample tbGoodsExample = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = tbGoodsExample.createCriteria();
        criteria.andIsDeleteEqualTo(0);
        //假分页
        Page<TbGoods> tbGoods = (Page<TbGoods>) tbGoodsMapper.selectByExample(tbGoodsExample);
        return new PageResult(tbGoods.getTotal(),tbGoods.getResult());
    }

    @Override
    public TbGoods findSpecification(long id) {
       return  goodsMapper.selectByPrimaryKey(id);
    }
}
