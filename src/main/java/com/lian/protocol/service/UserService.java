package com.lian.protocol.service;

import com.alibaba.fastjson.JSONObject;
import com.lian.protocol.model.Permission;
import com.lian.protocol.model.Role;
import com.lian.protocol.model.User;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
public interface UserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    JSONObject register(User user);

    /**
     * 新增用户
     * @param user
     * @return
     */
     User add(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
     User update(User user);

    /**
     * 查询用户列表
     * @return
     */
     List<User> list();

    /**
     * 根据用户id获取用户个人信息
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 删除用户
     * @param id
     * @return
     */
     User delete(Long id);

    /**
     * 用户登陆
     * @param user
     * @return
     */
     JSONObject logIn(User user);

     List<Role> findRoleListByUserId(Long userId);

     List<Permission> findPermissionListByUserId(Long userId);
}
