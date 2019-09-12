package com.taobao.mapper;

import com.taobao.pojo.TbGoods;
import com.taobao.pojo.TbGoodsExample;
import com.taobao.pojo.group.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbGoodsMapper {
    int countByExample(TbGoodsExample example);

    int deleteByExample(TbGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbGoods record);

    int insertSelective(TbGoods record);

    List<TbGoods> selectByExampleWithBLOBs(TbGoodsExample example);

    List<TbGoods> selectByExample(TbGoodsExample example);

    TbGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbGoods record, @Param("example") TbGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") TbGoods record, @Param("example") TbGoodsExample example);

    int updateByExample(@Param("record") TbGoods record, @Param("example") TbGoodsExample example);

    int updateByPrimaryKeySelective(TbGoods record);

    int updateByPrimaryKeyWithBLOBs(TbGoods record);

    int updateByPrimaryKey(TbGoods record);

    //关联查询
    public GoodsVo findById(Long id);
}