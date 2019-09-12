package com.taobao.user.service;

import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbUser;

public interface UserService {
    /**
     * 根据用户名和密码查询用户
     * @param user
     * @return
     */
    public TbUser findByUserNameAndPassword(TbUser user);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public TbUser findByUserName(String userName);

    /**
     * 根据电话号码查询用户
     * @param telphone
     * @return
     */
    public TbUser findByTelphone(String telphone);

    /**
     * 保存用户对象
     * @param user
     */
    public void saveUser(TbUser user);

    /**
     * 发送短信
     * @param telphone
     * @return
     */
    public String sendMsmVerificationCode(String telphone) throws Exception;

    void paySuccess(TbOrder tbOrder);
    TbUser findOne(Long id);

    /**
     * 修改用户信息
     * @param user
     */
    void update(TbUser user);
}
