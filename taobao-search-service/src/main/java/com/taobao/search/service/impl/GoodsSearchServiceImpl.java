package com.taobao.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taobao.mapper.TbCategoryMapper;
import com.taobao.mapper.TbGoodsDescMapper;
import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.*;
import com.taobao.pojo.group.Goods;
import com.taobao.pojo.group.PageResult;
import com.taobao.search.service.GoodsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GoodsSearchServiceImpl implements GoodsSearchService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbCategoryMapper categoryMapper;




    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap();
        TbGoodsExample goodsExample = postCondition(searchMap);
        //得到分页信息
        Integer pageNum = (Integer) searchMap.get("pageNo");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize == null || pageSize == 0) {
            pageSize = 20;
        }
        PageHelper.startPage(pageNum, pageSize);
        Page page = (Page) tbGoodsMapper.selectByExample(goodsExample);


        Long total = page.getTotal();
        int totalCount = total.intValue();
        int totalPage = 0;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }

        List<TbGoods> list = page.getResult();
        List<Goods> goodsList = new ArrayList();
        Goods goods = null;
        for (TbGoods tbGoods : list) {
            goods = new Goods();
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(tbGoods.getId());
            goods.setGoods(tbGoods);
            goods.setGoodsDesc(goodsDesc);
            goodsList.add(goods);
        }


        PageResult pageResult = new PageResult(page.getTotal(), goodsList);
        map.put("page", pageResult);
        map.put("totalPage", totalPage);
        map.put("pageNo",pageNum+1);
        map.put("total",total);

        String category = (String) searchMap.get("category");
        if(!StringUtils.isEmpty(category)){
            long categoryId = Long.parseLong(category);
            TbCategory tbCategory = categoryMapper.selectByPrimaryKey(categoryId);
            map.put("category",tbCategory.getName());
        }
        return map;
    }



    //封装搜索条件
    private TbGoodsExample postCondition(Map searchMap) {
        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();



        //1、得到搜索的关键词
        String keywords = (String) searchMap.get("keywords");
        if(!StringUtils.isEmpty(keywords)){

            criteria.andTitleLike("%" + keywords + "%");
        }

        //2、得到价格区间
        String price = (String) searchMap.get("price");
        if (!StringUtils.isEmpty(price)) {

            String[] priceStr = price.split("-");
            if (!priceStr[1].equals("*")) {

                criteria.andPriceGreaterThan(Double.parseDouble(priceStr[0]));
                criteria.andPriceLessThan(Double.parseDouble(priceStr[1]));
                System.out.println("进行了价格1");
            } else {
                criteria.andPriceGreaterThan(Double.parseDouble(priceStr[0]));
                System.out.println("进行了价格2");
            }
        }

        //3、是否排序
        String orderKey = (String) searchMap.get("order");
        if(!StringUtils.isEmpty(orderKey)){
            //按价格排序
            if(orderKey.equals("price")){
                example.setOrderByClause("price desc");
            }
        }

        //4、按分类查找
        String category = (String) searchMap.get("category");
        if(!StringUtils.isEmpty(category)){
            long categoryId = Long.parseLong(category);
            System.out.println("categoryId:"+categoryId);
            criteria.andCategoryIdEqualTo(categoryId);
        }

        return example;

    }

}
