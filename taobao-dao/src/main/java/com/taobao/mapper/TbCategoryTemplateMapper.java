package com.taobao.mapper;

import com.taobao.pojo.TbCategoryTemplate;
import com.taobao.pojo.TbCategoryTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCategoryTemplateMapper {
    int countByExample(TbCategoryTemplateExample example);

    int deleteByExample(TbCategoryTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbCategoryTemplate record);

    int insertSelective(TbCategoryTemplate record);

    List<TbCategoryTemplate> selectByExample(TbCategoryTemplateExample example);

    TbCategoryTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbCategoryTemplate record, @Param("example") TbCategoryTemplateExample example);

    int updateByExample(@Param("record") TbCategoryTemplate record, @Param("example") TbCategoryTemplateExample example);

    int updateByPrimaryKeySelective(TbCategoryTemplate record);

    int updateByPrimaryKey(TbCategoryTemplate record);
}