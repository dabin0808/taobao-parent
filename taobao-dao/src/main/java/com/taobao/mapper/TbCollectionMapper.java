package com.taobao.mapper;

import com.taobao.pojo.TbCollection;
import com.taobao.pojo.TbCollectionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCollectionMapper {
    int countByExample(TbCollectionExample example);

    int deleteByExample(TbCollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbCollection record);

    int insertSelective(TbCollection record);

    List<TbCollection> selectByExample(TbCollectionExample example);

    TbCollection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbCollection record, @Param("example") TbCollectionExample example);

    int updateByExample(@Param("record") TbCollection record, @Param("example") TbCollectionExample example);

    int updateByPrimaryKeySelective(TbCollection record);

    int updateByPrimaryKey(TbCollection record);
}