package com.taobao.seller.service;

import com.taobao.pojo.TbCategory;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.group.PageResult;

import java.util.List;

public interface SellerMapper {
    List<TbCategory> findAllCategory(long id);


    void updateCategory(TbCategory tbCategory);

    void addCategory(TbCategory tbCategory);

    void delCategory(long[] ids);

    List<TbCategory> findPerentCategory(long id);

    TbCategory findOneCategory(Long id);

    void addGoods(TbGoods goods);

    TbGoods findSpecification(long id);

    PageResult findGoodsPage(int pageNmu, int pageSize);
}
