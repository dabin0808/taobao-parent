package com.taobao.user.controller;

/**
 * @Author: dabin0808
 * @Date: 2019/9/10 15:00
 * Explain:
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.Orders;
import com.taobao.pojo.group.PageResult;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.OrderItemService;
import com.taobao.user.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/userorder")
public class UserOrderController {

    @Reference(timeout = 50000000)
    private OrderService orderService;
    @Reference(timeout = 50000000)
    private OrderItemService orderItemService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbOrder> findAll() {
        return orderService.findAll();
    }


    @RequestMapping("/findById")
    public List<Orders> listByUserId(@RequestBody TbUser user) {
        System.out.println("Order id: " + user.getId());

        List<TbOrder> tbOrders = orderService.listByUserId(user.getId());
        List<Orders> ordersList = new ArrayList<>();

        for (int i = 0; i < tbOrders.size(); i++) {
            Orders orders = new Orders();
            orders.setOrder(tbOrders.get(i));

            List<TbOrderItem> tbOrderItems = orderItemService.listByOrderId(tbOrders.get(i).getId());
            orders.setOrderItems(tbOrderItems);

            ordersList.add(orders);
        }

        return ordersList;
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return orderService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param order
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbOrder order) {
        try {
            orderService.add(order);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param order
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbOrder order) {
        try {
            orderService.update(order);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbOrder findOne(Long id) {
        return orderService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            orderService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbOrder order, int page, int rows) {
        return orderService.findPage(order, page, rows);
    }

}
