package com.lian.protocol.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lian.protocol.common.constants.RedisKeyConstant;
import com.lian.protocol.common.enums.MyBusinessExceptionEnum;
import com.lian.protocol.common.myRedis.MyRedisTemplate;
import com.lian.protocol.common.utils.CopyNonNullUtils;
import com.lian.protocol.common.utils.JwtTokenUtil;
import com.lian.protocol.mapper.UserMapper;
import com.lian.protocol.model.*;
import com.lian.protocol.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MyRedisTemplate myRedisTemplate;
    @Autowired
    RoleUserService roleUserService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    PermissionRoleService permissionRoleService;
    @Override
    public JSONObject register(User user) {
        JSONObject jsonObject = new JSONObject();
        User newUser = add(user);
        myRedisTemplate.opsForValue().set(RedisKeyConstant.TOKEN+newUser.getId(),JwtTokenUtil.sign(newUser.getId()));
        Object redisToken = myRedisTemplate.opsForValue().get(RedisKeyConstant.TOKEN + newUser.getId());
        jsonObject.put("user",newUser);
        jsonObject.put("token",String.valueOf(redisToken));
        return jsonObject;
    }

    @Override
    public User add(User user) {
        User userExist = userMapper.findByPhone(user.getPhone());
        MyBusinessExceptionEnum.USER_ALREADY_EXIST.assertEquals(userExist,null);
        User add = userMapper.add(user);
        return add;
    }

    @Override
    public User update(User user) {
        User userExist = findUserById(user.getId());
        MyBusinessExceptionEnum.USER_NOT_NULL.assertNotNull(userExist);
        CopyNonNullUtils.copyNonNullProperties(user,userExist);
        User update = userMapper.update(userExist);
        return update;
    }

    @Override
    public List<User> list() {
        List<User> list = userMapper.list();
        return list;
    }

    @Override
    public User findUserById(Long id) {
        User byId = userMapper.findById(id);
        MyBusinessExceptionEnum.USER_NOT_EXIST.assertNotNull(byId);
        return byId;
    }

    @Override
    public User delete(Long id) {
        User delete = userMapper.delete(id);
        return delete;
    }

    @Override
    public JSONObject logIn(User user) {
        JSONObject jsonObject = new JSONObject();
        MyBusinessExceptionEnum.USER_PHONE_NOT_NULL.assertNotNull(user.getPhone());
        User userExist = userMapper.findByPhone(user.getPhone());
        log.info("user{}",userExist);
        MyBusinessExceptionEnum.USER_NOT_NULL.assertNotNull(userExist);
        MyBusinessExceptionEnum.USER_PASSWORD_WRONG.assertEquals(user.getPassword(),userExist.getPassword());
        myRedisTemplate.opsForValue().set(RedisKeyConstant.TOKEN+userExist.getId(),JwtTokenUtil.sign(userExist.getId()));
        Object token = myRedisTemplate.opsForValue().get(RedisKeyConstant.TOKEN + userExist.getId());
        List<Role> roleListByUserId = findRoleListByUserId(userExist.getId());
        List<Permission> permissionListByUserId = findPermissionListByUserId(userExist.getId());
        log.info("key{},token{}",RedisKeyConstant.TOKEN+userExist.getId(),token);
        jsonObject.put("token",String.valueOf(token));
        jsonObject.put("user",userExist);
        jsonObject.put("role",roleListByUserId);
        jsonObject.put("permission",permissionListByUserId);
        return jsonObject;
    }

    @Override
    public List<Role> findRoleListByUserId(Long userId) {
        List<RoleUser> roleUsers = roleUserService.findListByUserId(userId);
        ArrayList<Role> roles = new ArrayList<>();
        for (RoleUser roleUser:roleUsers) {
            Role role = roleService.findById(roleUser.getRoleId());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Permission> findPermissionListByUserId(Long userId) {
        List<RoleUser> roleUsers = roleUserService.findListByUserId(userId);
        ArrayList<Permission> permissions = new ArrayList<>();
        for (RoleUser roleUser:roleUsers){
            List<PermissionRole> permissionRoles = permissionRoleService.findListByRoleId(roleUser.getRoleId());
            for (PermissionRole permissionRole:permissionRoles){
                permissions.add(permissionService.findById(permissionRole.getPermissionId()));
            }
        }
        return permissions;
    }
}
