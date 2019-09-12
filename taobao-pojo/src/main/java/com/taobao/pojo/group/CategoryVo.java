package com.taobao.pojo.group;

import com.taobao.pojo.TbCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryVo {
    private Long id;

    private String name;

    private Long parentId;

    private List<CategoryVo> categoryList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<CategoryVo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryVo> categoryList) {
        this.categoryList = categoryList;
    }
}
