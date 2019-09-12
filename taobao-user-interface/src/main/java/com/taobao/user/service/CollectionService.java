package com.taobao.user.service;

import com.taobao.pojo.TbCollection;
import com.taobao.pojo.group.CollectionVo;
import com.taobao.pojo.group.Result;

import java.util.List;

public interface CollectionService {


    /**
     * 添加到收藏
     *
     */
    public Result add(Long goodsId,Long userId);

    /**
     * 得到用户收藏夹
     */
    public List<CollectionVo> listByUserId(Long userId);

    /**
     * 通过用户id与商品id得到收藏
     */
    public TbCollection getByUserIdAndGoodsId(Long userId,Long goodsId);

    /**
     * 删除收藏夹中的商品
     */
    public Result delete(Long id);
}
