package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.model.Permission;
import com.lian.protocol.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:27
 */
@RestController
@RequestMapping(value = "permission")
@Api(tags = "权限管理接口")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加权限",notes = "只有超级管理员可以添加权限")
    @RequiresPermissions("superAdmin")
    public R<Permission> add(@RequestBody Permission permission){
        Permission add = permissionService.add(permission);
        return new R<>(add);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "查询权限信息",notes = "超级管理员，管理员，运营人员，开发人员都可以查看权限信息")
    @RequiresPermissions("admin")
    public R<Permission> select(@PathVariable("id") Long id){
        Permission permissionById = permissionService.findById(id);
        return new R<>(permissionById);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新权限信息",notes = "只有超级管理员可以更新权限信息")
    @RequiresPermissions("superAdmin")
    public R<Permission> update(@RequestBody Permission permission){
        Permission update = permissionService.update(permission);
        return new R<>(update);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "查询权限列表",notes = "超级管理员，管理员，运营人员，开发人员都可以查看权限列表")
    @RequiresPermissions("admin")
    public R<List<Permission>> list(){
        List<Permission> list = permissionService.list();
        return new R<>(list);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限",notes = "只有超级管理员可以删除权限")
    @RequiresPermissions("superAdmin")
    public R<Permission> delete(@PathVariable("id") Long id){
        Permission delete = permissionService.delete(id);
        return new R<>(delete);
    }

}
