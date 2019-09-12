package com.taobao.pojo.group;

import com.taobao.pojo.TbGoods;
import com.taobao.pojo.TbGoodsDesc;

import java.io.Serializable;

/**
 * 商品组合实体类
 */

public class Goods implements Serializable {

    private TbGoods goods;

    private TbGoodsDesc goodsDesc;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}
