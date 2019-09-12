package com.taobao.pojo.group;

import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;

import java.util.List;

/**
 * @Author: dabin0808
 * @Date: 2019/9/10 15:19
 * Explain:
 */
public class Orders {

    TbOrder order;
    List<TbOrderItem> orderItems;

    public TbOrder getOrder() {
        return order;
    }

    public void setOrder(TbOrder order) {
        this.order = order;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
