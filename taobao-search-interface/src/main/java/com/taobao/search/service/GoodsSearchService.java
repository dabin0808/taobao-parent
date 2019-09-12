package com.taobao.search.service;

import com.taobao.pojo.group.PageResult;

import java.util.Map;

public interface GoodsSearchService {
    /**
     * 搜索
     * @param searchMap
     * @return
     */
    public Map search(Map searchMap);


}
