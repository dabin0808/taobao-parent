package com.taobao.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TbCategory;
import com.taobao.pojo.group.CategoryVo;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者：肖楠
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Reference(timeout = 500000)
    private CategoryService categoryService;

    @RequestMapping("/findAllAnother")
    public List<Map<String, Object>> findAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<TbCategory> root = categoryService.findByParentId(0L);
        for (TbCategory category: root) {
            Map<String, Object> map = new HashMap<>();
            List<TbCategory> categories = categoryService.findByParentId(category.getId());
            map.put("root",category);
//            System.out.println(category.getName());
            int i = 1;
            for (TbCategory cg: categories) {
                List<TbCategory> categoryList = categoryService.findByParentId(cg.getId());
//                System.out.println(cg.getName());
                map.put("category"+i,cg);
                map.put("categoryList"+i, categoryList);
                i ++;
            }
            map.put("size",i-1);
            list.add(map);
        }
        return list;
    }


    @RequestMapping("/findAll")
    public Result test() {
        //1父级
        CategoryVo categoryVo = new CategoryVo();

        List<CategoryVo> categoryVo1List = new ArrayList();
        List<TbCategory> category1List = categoryService.findByParentId(0L);
        if (category1List != null) {
            for (TbCategory cat1 : category1List) {
                CategoryVo vo1 = new CategoryVo();
                BeanUtils.copyProperties(cat1, vo1);

                //查二级
                List<TbCategory> category2List = categoryService.findByParentId(cat1.getId());
                if (category2List != null) {
                    List<CategoryVo> categoryVo2List = new ArrayList();
                    for (TbCategory cat2 : category2List) {
                        CategoryVo vo2 = new CategoryVo();
                        BeanUtils.copyProperties(cat2, vo2);
                        categoryVo2List.add(vo2);
                        ////查三级
                        List<TbCategory> category3List = categoryService.findByParentId(cat2.getId());
                        if (category3List != null) {
                            List<CategoryVo> categoryVo3List = new ArrayList();
                            for (TbCategory cat3 : category2List) {
                                CategoryVo vo3 = new CategoryVo();
                                BeanUtils.copyProperties(cat3, vo3);
                                categoryVo3List.add(vo3);

                            }
                            vo2.setCategoryList(categoryVo3List);
                        }
                        categoryVo2List.add(vo2);
                    }
                    vo1.setCategoryList(categoryVo2List);
                }
                categoryVo1List.add(vo1);

            }
            categoryVo.setCategoryList(categoryVo1List);
        }

        return new Result(true,"成功",categoryVo);
    }
}
