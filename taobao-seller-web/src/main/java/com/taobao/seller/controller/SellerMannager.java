package com.taobao.seller.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.common.UpLoadImageBed;
import com.taobao.pojo.TbCategory;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.group.PageResult;
import com.taobao.seller.service.SellerMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //这个注释是指该类下所有方法返回json字符串
@RequestMapping("/seller")
public class SellerMannager {
    @Reference
   SellerMapper service;
    @RequestMapping("/login")
    public void login(){

    }

    @RequestMapping("/findAll")
    public List<TbCategory> findAllCategory(long id){

        List<TbCategory> tbCategoryList =service.findAllCategory(id);

        return tbCategoryList;
    }
    @RequestMapping("/findOne")
    public TbCategory findOneCategory(long id){

        TbCategory tbCategoryList =service.findOneCategory(id);

        return tbCategoryList;
    }

    /**
     * 改变分类
     * @param tbCategory
     */
    @RequestMapping("/updateCategory")
    public void updateCategory(@RequestBody TbCategory tbCategory){
       service.updateCategory(tbCategory);
    }

    @RequestMapping("/addCategory")
    public void addCategory(@RequestBody TbCategory tbCategory){
        service.addCategory(tbCategory);
    }

    @RequestMapping("/delCategory")
    public void delCategory(long[] ids){
        service.delCategory(ids);
    }

    @RequestMapping("/findParent")
    public List<TbCategory> findPerentCategory(long id){
        List<TbCategory> perentCategory = service.findPerentCategory(id);
//        System.out.println(perentCategory);
        return perentCategory;
    }
    @RequestMapping("/addGoods")
    public void addGoods(@RequestBody TbGoods goods){
        //新添加的商品默认为0
        goods.setIsDelete(0);
        goods.setStatus(0);
        service.addGoods(goods);
    }

    @RequestMapping("/uploadImage")
    public Map uploadImage(MultipartFile file){
        Map<String,String> result = new HashMap<>();
        result.put("msg","图片上传成功");
        String url="";
        try {
            url = UpLoadImageBed.up(file.getInputStream(),file.getOriginalFilename());
        } catch (IOException e) {
            result.put("msg","图片上传失败");
            e.printStackTrace();
        }
        result.put("url",url);

//        System.out.println(file.getOriginalFilename());
        return result;
    }

    @RequestMapping("/findSpecification.do")
    public TbGoods findSpecification(long id){
        return service.findSpecification(id);
    }

    @RequestMapping("/findGoodsPage")
    public PageResult findGoodsPage(int pageNmu,int pageSize){

        return service.findGoodsPage(pageNmu,pageSize);
    }




}
