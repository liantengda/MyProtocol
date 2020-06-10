package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.model.PermissionRole;
import com.lian.protocol.service.PermissionRoleService;
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
 * @date 2020/6/2 21:22
 */
@RestController
@RequestMapping(value = "permissionRole")
@Api(tags = "权限角色关联接口")
public class PermissionRoleController {
    @Autowired
    PermissionRoleService permissionRoleService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加权限角色关联记录",notes = "只有超级管理员可以添加权限角色关联记录")
    @RequiresRoles("超级管理员")
    public R<PermissionRole> add(@RequestBody PermissionRole permissionRole){
        PermissionRole add = permissionRoleService.add(permissionRole);
        return new R<>(add);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询当前用户关联权限角色关联记录",notes = "超级管理员，管理员，运营人员，开发人员都可以查看权限角色关联记录信息")
    @RequiresRoles("管理员")
    public R<List<PermissionRole>> select(@PathVariable("id") Long id){
        List<PermissionRole> listByUserId = permissionRoleService.findListByRoleId(id);
        return new R<>(listByUserId);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新权限角色关联记录信息",notes = "只有超级管理员可以更新权限角色关联记录信息")
    @RequiresRoles("超级管理员")
    public R<PermissionRole> update(@RequestBody PermissionRole permissionRole){
        PermissionRole update = permissionRoleService.update(permissionRole);
        return new R<>(update);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "根据角色id分组查询每个角色拥有的权限列表",notes = "超级管理员，管理员，运营人员，开发人员都可以查看权限角色关联记录列表")
    @RequiresRoles("管理员")
    public R<Map<Long,List<PermissionRole>>> list(){
        Map<Long, List<PermissionRole>> map = permissionRoleService.findMapGroupByRoleId();
        return new R<>(map);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限角色关联记录",notes = "只有超级管理员可以删除权限角色关联记录")
    @RequiresRoles("超级管理员")
    public R<PermissionRole> delete(@PathVariable("id") Long id){
        PermissionRole delete = permissionRoleService.delete(id);
        return new R<>(delete);
    }
}
