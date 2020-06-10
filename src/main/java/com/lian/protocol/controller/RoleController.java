package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.model.Role;
import com.lian.protocol.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:12
 */
@RestController
@RequestMapping(value = "/role")
@Api(tags = "角色管理接口")
@Slf4j
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加角色",notes = "只有超级管理员可以添加角色")
    @RequiresRoles("超级管理员")
    public R<Role> add(@RequestBody Role role){
        Role add = roleService.add(role);
        return new R<>(add);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "查询角色信息",notes = "超级管理员，管理员，运营人员，开发人员都可以查看角色信息")
    @RequiresRoles("管理员")
    public R<Role> select(@PathVariable("id") Long id){
        Role roleById = roleService.findById(id);
        return new R<>(roleById);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新角色信息",notes = "只有超级管理员可以更新角色信息")
    @RequiresRoles("超级管理员")
    public R<Role> update(@RequestBody Role role){
        Role update = roleService.update(role);
        return new R<>(update);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "查询角色列表",notes = "超级管理员，管理员都可以查看角色列表")
    @RequiresPermissions("role:list")
    public R<List<Role>> list(){
        List<Role> list = roleService.list();
        return new R<>(list);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色",notes = "只有超级管理员可以删除角色")
    @RequiresRoles("超级管理员,管理员")
    public R<Role> delete(@PathVariable("id") Long id){
        Role delete = roleService.delete(id);
        return new R<>(delete);
    }
}
