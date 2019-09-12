package com.taobao.user.service;

import com.taobao.pojo.TbCategory;

import java.util.List;

/**
 * 创建者：肖楠
 */
public interface CategoryService {

    /**
     * 通过父目录id查询所有类别
     */
    public List<TbCategory> findByParentId(Long id);

}
