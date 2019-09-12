package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.mapper.TbCollectionMapper;
import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.TbCollection;
import com.taobao.pojo.TbCollectionExample;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.group.CollectionVo;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.CollectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private TbCollectionMapper collectionMapper;

    @Autowired
    private TbGoodsMapper goodsMapper;


    @Override
    public Result add(Long goodsId, Long userId) {

        //先判断用户是否已收藏
        TbCollection tbCollection = getByUserIdAndGoodsId(userId, goodsId);
        if(tbCollection!=null){
            return new Result(false,"你已收藏过该商品");
        }

        TbCollection collection = new TbCollection();
        collection.setCreateTime(new Date());
        collection.setGoodsId(goodsId);
        collection.setUserId(userId);

        collectionMapper.insert(collection);
        return new Result(true,"添加收藏成功");
    }

    @Override
    public List<CollectionVo> listByUserId(Long userId) {
        TbCollectionExample example = new TbCollectionExample();
        TbCollectionExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<TbCollection> collectionList = collectionMapper.selectByExample(example);
        if(collectionList==null){
            return null;
        }
        List<CollectionVo> collectionVoList = new ArrayList();
        for (TbCollection collection:collectionList){
            CollectionVo collectionVo = null;
            //找到该商品
            TbGoods goods = goodsMapper.selectByPrimaryKey(collection.getGoodsId());
            if(goods!=null){
                collectionVo = new CollectionVo();
                BeanUtils.copyProperties(collection,collectionVo);
                collectionVo.setGoodsName(goods.getName());
                String bigImg = goods.getBigImg();

                List<String> list = JSONObject.parseArray(bigImg, String.class);
                collectionVo.setGoodsImage(list.get(0));
                collectionVo.setGoodsPrice(goods.getPrice());
            }
            if (collectionVo!=null){
                collectionVoList.add(collectionVo);
            }


        }
        return collectionVoList;
    }

    @Override
    public TbCollection getByUserIdAndGoodsId(Long userId, Long goodsId) {
        TbCollectionExample example = new TbCollectionExample();
        TbCollectionExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andUserIdEqualTo(userId);
        List<TbCollection> collections = collectionMapper.selectByExample(example);

        if(collections!=null&&collections.size()>0){

            return collections.get(0);
        }
        return null;
    }

    @Override
    public Result delete(Long id) {
        System.out.println("id:"+id);
        try {

            collectionMapper.deleteByPrimaryKey(id);
            return new Result(true,"删除成功");
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    public static void main(String[] args) {
        String img = "[\"http://www.peter666.xyz/uploadimgtopeter666/ImageBed/2019/08/29/1567081359968.jpg\",\"http://www.peter666.xyz/uploadimgtopeter666/ImageBed/2019/08/29/1567081368446.jpg\"]";
        List<String> list = JSONObject.parseArray(img, String.class);


        System.out.println(list.get(0));
    }
}
