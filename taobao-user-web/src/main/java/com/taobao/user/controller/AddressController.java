package com.taobao.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TbAddress;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.group.PageResult;
import com.taobao.pojo.group.Result;
import com.taobao.user.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: dabin0808
 * @Date: 2019/8/27 19:30
 * Explain:
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference(timeout = 500000000)
    AddressService addressService;

    /*
        添加地址
     */
    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody TbAddress tbAddress) {
        try {
            addressService.addAddress(tbAddress);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "添加失败");
        }

    }

    /**
     * 返回用户ID全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbAddress> findAll(@RequestBody TbUser tbUser) {
        System.out.println("userID " + tbUser.getId());
        return addressService.findUserId(tbUser);
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return addressService.findPage(page, rows);
    }

    /**
     * 修改
     *
     * @param address
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbAddress address) {
        try {
            addressService.update(address);
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
    public TbAddress findOne(Long id) {
        return addressService.findOne(id);
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
            addressService.delete(ids);
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
     * @param
     * @return
     */
    /*@RequestMapping("/search")
    public PageResult search(@RequestBody TbAddress address, int page, int rows) {
        System.out.println("address :" + address.getUserId());
        List<TbAddress> list = addressService.findUserId(address);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserId() != address.getUserId()) {
                list.remove(i);
            }
        }
        PageResult result = new PageResult(list.size(), list);
        System.out.println("111 " + result.getRows().toString());
        System.out.println("222  " + result.getRows().size());
        System.out.println("3333  " + result.getTotal());
        System.out.println(result.getRows().toString());
        System.out.println(result.getRows().size());
        return result;

    }*/
    @RequestMapping("/findById")
    public List<TbAddress> listByUserId(Long userId) {
        return addressService.listByUserId(userId);
    }


}
