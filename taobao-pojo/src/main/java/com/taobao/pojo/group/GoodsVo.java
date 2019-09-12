package com.taobao.pojo.group;

import com.taobao.pojo.TbGoodsDesc;

import java.io.Serializable;

public class GoodsVo implements Serializable {
    private Long id;

    private String name;

    private String title;

    private Integer status;

    private Long categoryId;

    private Integer isDelete;

    private String bigImg;

    private String specification;

    private Double price;

    private String introduction;

    private TbGoodsDesc goodsDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", categoryId=" + categoryId +
                ", isDelete=" + isDelete +
                ", bigImg='" + bigImg + '\'' +
                ", specification='" + specification + '\'' +
                ", price=" + price +
                ", introduction='" + introduction + '\'' +
                ", goodsDesc=" + goodsDesc +
                '}';
    }
}
