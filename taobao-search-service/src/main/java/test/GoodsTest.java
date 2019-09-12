package test;


import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.group.GoodsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
public class GoodsTest {


    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Test
    public void testGet(){
        GoodsVo goodsVo = tbGoodsMapper.findById(14L);
        System.out.println(goodsVo);
    }
}
