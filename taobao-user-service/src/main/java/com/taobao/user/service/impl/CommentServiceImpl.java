package com.taobao.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.mapper.TbCommentMapper;
import com.taobao.pojo.TbComment;
import com.taobao.pojo.TbCommentExample;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private TbCommentMapper commentMapper;

    @Override
    public List<TbComment> findByStar(Long goodsId,Integer star) {
        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        if(star>=5){
            //好评
            criteria.andStarEqualTo(star);
        }else if(star>=3){
            //中评
            criteria.andStarBetween(3,4);

        }else if(star>0){
            //差评
            criteria.andStarBetween(1,2);
        }

        return commentMapper.selectByExample(example);
    }

    @Override
    public List<TbComment> findByGoodsId(Long goodsId) {

        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
         return commentMapper.selectByExample(example);
    }

    @Override
    public Result addComment(TbComment comment) {

        try {
            commentMapper.insert(comment);
            return new Result(true,"创建成功");
        }catch (RuntimeException e){
            return new Result(false,"创建失败");
        }
    }

    public static void main(String[] args) {
        Class<?> aClass = HashMap.class;
        Method[] methods = aClass.getDeclaredMethods();
        for(Method method:methods){
            System.out.println(method.getName());
        }
        Object obj = new Object();

    }
}
