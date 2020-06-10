package com.lian.protocol.controller;

import com.alibaba.fastjson.JSONObject;
import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.model.Permission;
import com.lian.protocol.model.Role;
import com.lian.protocol.model.User;
import com.lian.protocol.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:08
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
@Api(tags = "用户管理接口")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册",notes = "任何人都可以注册")
    public R<JSONObject> add(@RequestBody User user){
        JSONObject jsonObject = userService.register(user);
        return new R<>(jsonObject);
    }

    @RequestMapping(value = "/logIn",method = RequestMethod.GET)
    @ApiOperation(value = "用户登陆",notes = "任何人都可以登陆")
    public R<JSONObject> logIn(User user){
        log.info("user{}",user);
        JSONObject jsonObject= userService.logIn(user);
        return new R<>(jsonObject);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "查询用户个人信息",notes = "要求必须是登陆的用户才可以请求接口")
    @RequiresUser
    public R<User> select(@PathVariable("id") Long id,
                          @RequestHeader(value = "Authorization",required = true)String token){
        User userById = userService.findUserById(id);
        return new R<>(userById);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户信息",notes = "要求必须是登陆的用户才可以请求此接口")
    @RequiresUser
    public R<User> update(@RequestBody User user){
        User update = userService.update(user);
        return new R<>(update);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "查询用户列表",notes = "要求必须有管理员权限的用户才可以请求此接口")
    @RequiresPermissions("user:list")
    public R<List<User>> list(){
        List<User> list = userService.list();
        return new R<>(list);
    }

    @RequestMapping(value = "/logOff/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "注销账户",notes = "要求必须是登陆的用户才可以请求此接口")
    @RequiresUser
    public R<User> delete(@PathVariable("id") Long id){
        User delete = userService.delete(id);
        return new R<>(delete);
    }

    @RequestMapping(value = "/listRole/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id获取用户角色列表",notes = "要求必须是管理员才可以请求此接口")
    @RequiresPermissions("role:list")
    public R<List<Role>> listUserRoles(@PathVariable("id") Long id){
        List<Role> roles = userService.findRoleListByUserId(id);
        return new R<>(roles);
    }

    @RequestMapping(value = "/listPermission/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id获取用户权限列表",notes = "要求必须是管理员才可以请求此接口")
    @RequiresRoles("管理员")
    public R<List<Permission>> listUserPermissions(@PathVariable("id") Long id){
        List<Permission> permissions = userService.findPermissionListByUserId(id);
        return new R<>(permissions);
    }

    @RequestMapping(value = "/abuseJXL",method = RequestMethod.GET)
    @ApiOperation(value = "吆喝纪晓岚",notes = "只有纪府的丫鬟敢吆喝纪晓岚")
    @RequiresRoles({"纪府丫鬟","杜小月丫鬟"})
    public R<JSONObject> abuseJXL(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("杏儿","卖干果的，听见没有，我们家小姐叫你呢");
        jsonObject.put("纪晓岚","是吗你们家小姐在哪呢");
        return new R<>(jsonObject);
    }
}
