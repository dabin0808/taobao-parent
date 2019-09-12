package com.taobao.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbGoodsDesc implements Serializable {
    private Long goodsId;

    private Integer commentNum;

    private Integer count;

    private Date createTime;

    private Date updateTime;

    private Integer commentStarScore;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommentStarScore() {
        return commentStarScore;
    }

    public void setCommentStarScore(Integer commentStarScore) {
        this.commentStarScore = commentStarScore;
    }

    @Override
    public String toString() {
        return "TbGoodsDesc{" +
                "goodsId=" + goodsId +
                ", commentNum=" + commentNum +
                ", count=" + count +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", commentStarScore=" + commentStarScore +
                '}';
    }
}