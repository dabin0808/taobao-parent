package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.mapper.TbCategoryMapper;
import com.taobao.pojo.TbCategory;
import com.taobao.pojo.TbCategoryExample;
import com.taobao.user.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 创建者：肖楠
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private TbCategoryMapper categoryMapper;

    @Override
    public List<TbCategory> findByParentId(Long id) {
        TbCategoryExample categoryExample = new TbCategoryExample();
        TbCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        return categoryMapper.selectByExample(categoryExample);
    }

}
