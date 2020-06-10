package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.model.RoleUser;
import com.lian.protocol.service.RoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:14
 */
@RestController
@RequestMapping(value = "/roleUser")
@Api(tags = "用户角色关联接口")
public class RoleUserController {
    @Autowired
    RoleUserService roleService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户角色关联记录",notes = "只有超级管理员可以添加用户角色关联记录")
    @RequiresRoles("超级管理员")
    public R<RoleUser> add(@RequestBody RoleUser roleUser){
        RoleUser add = roleService.add(roleUser);
        return new R<>(add);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询当前用户关联用户角色关联记录",notes = "超级管理员，管理员，运营人员，开发人员都可以查看用户角色关联记录信息")
    @RequiresRoles("管理员")
    public R<List<RoleUser>> select(@PathVariable("id") Long id){
        List<RoleUser> listByUserId = roleService.findListByUserId(id);
        return new R<>(listByUserId);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户角色关联记录信息",notes = "只有超级管理员可以更新用户角色关联记录信息")
    @RequiresRoles("超级管理员")
    public R<RoleUser> update(@RequestBody RoleUser roleUser){
        RoleUser update = roleService.update(roleUser);
        return new R<>(update);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id分组查询每个用户拥有的角色列表",notes = "超级管理员，管理员，运营人员，开发人员都可以查看用户角色关联记录列表")
    @RequiresRoles("管理员")
    public R<Map<Long,List<RoleUser>>> list(){
        Map<Long, List<RoleUser>> map = roleService.findMapGroupByUserId();
        return new R<>(map);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户角色关联记录",notes = "只有超级管理员可以删除用户角色关联记录")
    @RequiresRoles("超级管理员")
    public R<RoleUser> delete(@PathVariable("id") Long id){
        RoleUser delete = roleService.delete(id);
        return new R<>(delete);
    }

}
