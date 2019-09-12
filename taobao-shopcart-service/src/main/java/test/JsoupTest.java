package test;

import com.alibaba.fastjson.JSONArray;
import com.taobao.mapper.TbGoodsDescMapper;
import com.taobao.mapper.TbGoodsMapper;
import com.taobao.pojo.TbGoods;
import com.taobao.pojo.TbGoodsDesc;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
public class JsoupTest {

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Test
    public void test() {
        String keywords = "手机";
        // 需要爬取商品信息的网站地址
        String url = "https://list.tmall.com/search_product.htm?spm=a220m.1000858.0.0.1e9f2a68u5mhYA&s=120&q=%CA%D6%BB%FA&sort=s&style=g&type=pc#J_Filter";
        // 动态模拟请求数据
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 模拟浏览器浏览（user-agent的值可以通过浏览器浏览，查看发出请求的头文件获取）
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");


        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();

            // 如果状态响应码为200，则获取html实体内容或者json文件
            HttpEntity responseEntity = response.getEntity();
            if (statusCode == 200) {
                String html = EntityUtils.toString(responseEntity, Consts.UTF_8);
                //提取html得到商品信息
                Document doc = null;
                doc = Jsoup.parse(html);
                Elements elements1 = doc.select("div[class='view']");
                Elements elements2 = elements1.select("div[class='product']");

                for (Element element : elements2) {
                    TbGoods tbGoods = new TbGoods();
                    Elements productWrap = element.select("div[class='product-iWrap']");
                    //得到商品图片
                    List<String> imgList = new ArrayList();
                    String img = productWrap.select("div[class='productImg-wrap']").select("a[class='productImg']").select("img").attr("src");
                    System.out.println(productWrap.select("div[class='productImg-wrap']").select("a[class='productImg']").select("img"));

                    if (StringUtils.isEmpty(img)) {
                        String imgSrc = productWrap.select("div[class='productImg-wrap']").select("a[class='productImg']").select("img").attr("data-ks-lazyload");
                        imgList.add(imgSrc);
                    } else {
                        imgList.add(img);
                    }


                    //得到商品副图
                    Elements imgs = productWrap.select("div[class='productThumb clearfix']").select("div[class='proThumb-wrap']").select("p[class='ks-switchable-content']").select("b[class='proThumb-img']");
                    /*for(Element imgEle:imgs){

                        //String img2 = imgEle.select("img").attr("src");

                        String imgSrc = imgEle.select("img").attr("data-ks-lazyload");
                        //System.out.println(imgSrc);
                        if(imgList.size()<=2){
                            if(!StringUtils.isEmpty(imgSrc)){

                                imgList.add(imgSrc);
                            }
                        }

                    }*/
                    String jsonString = JSONArray.toJSONString(imgList);
                    tbGoods.setBigImg(jsonString);
                    //得到商品价格
                    String price = productWrap.select("p[class='productPrice']").select("em").attr("title");
                    tbGoods.setPrice(Double.parseDouble(price));
                    //得到商品名称
                    Elements titEle = productWrap.select("p[class='productTitle']");
                    String desc = titEle.select("a").attr("title");//商品描述
                    tbGoods.setTitle(desc);
                    String name = titEle.select("a").text();//商品名称
                    tbGoods.setName(name);

                    tbGoods.setCategoryId(1240L);
                    tbGoods.setIntroduction("手机");
                    tbGoods.setIsDelete(0);
                    tbGoods.setStatus(0);
                    String spec = "{\"网络格式\":[\"4G\",\"5G\"],\"内存大小\":[\"128G\",\"64G\"]}";
                    tbGoods.setSpecification(spec);
                    goodsMapper.insert(tbGoods);
                    TbGoodsDesc tbGoodsDesc = new TbGoodsDesc();
                    tbGoodsDesc.setGoodsId(tbGoods.getId());
                    tbGoodsDesc.setCommentNum(0);
                    tbGoodsDesc.setCount(0);
                    tbGoodsDesc.setCreateTime(new Date());
                    tbGoodsDesc.setUpdateTime(new Date());
                    goodsDescMapper.insert(tbGoodsDesc);
                    //https://list.tmall.com/search_product.htm?spm=a220m.1000858.0.0.5c9a2a68HEiOVe&s=60&q=%CA%D6%BB%FA&sort=s&style=g&type=pc#J_Filter
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
