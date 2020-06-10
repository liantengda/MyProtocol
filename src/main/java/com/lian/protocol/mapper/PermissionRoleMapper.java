package com.lian.protocol.mapper;

import com.lian.protocol.model.PermissionRole;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 22:06
 */
@Repository
public class PermissionRoleMapper {
    private static HashMap<Long,PermissionRole> permissionRoleTable = new HashMap<>();

    static {
        /*1-超级管理员，2-管理员，3-运营，4-开发，5-市场部，6-视觉组，7-绿帽组，8-纪府丫鬟，9-董事部*/
        permissionRoleTable.put(1L,new PermissionRole(1L,1L,1L,System.currentTimeMillis()));
        permissionRoleTable.put(2L,new PermissionRole(2L,1L,2L,System.currentTimeMillis()));
        permissionRoleTable.put(3L,new PermissionRole(3L,1L,3L,System.currentTimeMillis()));
        permissionRoleTable.put(4L,new PermissionRole(4L,1L,4L,System.currentTimeMillis()));
        permissionRoleTable.put(5L,new PermissionRole(5L,1L,5L,System.currentTimeMillis()));
        permissionRoleTable.put(6L,new PermissionRole(6L,1L,6L,System.currentTimeMillis()));
        permissionRoleTable.put(7L,new PermissionRole(7L,1L,7L,System.currentTimeMillis()));
        permissionRoleTable.put(8L,new PermissionRole(8L,1L,8L,System.currentTimeMillis()));
        permissionRoleTable.put(9L,new PermissionRole(9L,1L,9L,System.currentTimeMillis()));
        permissionRoleTable.put(10L,new PermissionRole(10L,1L,10L,System.currentTimeMillis()));
        permissionRoleTable.put(11L,new PermissionRole(11L,1L,11L,System.currentTimeMillis()));
        permissionRoleTable.put(12L,new PermissionRole(12L,1L,12L,System.currentTimeMillis()));
        permissionRoleTable.put(13L,new PermissionRole(13L,2L,4L,System.currentTimeMillis()));
        permissionRoleTable.put(14L,new PermissionRole(14L,2L,8L,System.currentTimeMillis()));
        permissionRoleTable.put(15L,new PermissionRole(15L,2L,12L,System.currentTimeMillis()));
    }
    /**
     * 新增权限角色关联记录
     * @param permissionRole
     * @return
     */
    public PermissionRole add(PermissionRole permissionRole){
        PermissionRole put = permissionRoleTable.put(permissionRole.getId(), permissionRole);
        return put;
    }

    /**
     * 修改权限角色关联记录
     * @param permissionRole
     * @return
     */
    public PermissionRole update(PermissionRole permissionRole){
        PermissionRole put = permissionRoleTable.put(permissionRole.getId(), permissionRole);
        return put;
    }

    /**
     * 查询权限角色关联记录列表
     * @return
     */
    public List<PermissionRole> list(){
        List<PermissionRole> collect = permissionRoleTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    /**
     * 删除权限角色关联记录
     * @param id
     * @return
     */
    public PermissionRole delete(Long id){
        PermissionRole remove = permissionRoleTable.remove(id);
        return remove;
    }

    public List<PermissionRole> findListByRoleId(Long roleId){
        List<PermissionRole> collect = permissionRoleTable.entrySet().stream().
                map(e -> e.getValue()).filter(e -> e.getRoleId().equals(roleId)).
                collect(Collectors.toList());
        return collect;
    }

    public Map<Long,List<PermissionRole>> findMapGroupByRoleId(){
        Map<Long, List<PermissionRole>> collect = permissionRoleTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.groupingBy(PermissionRole::getRoleId));
       return collect;
    }

    public PermissionRole findById(Long id){
        PermissionRole permissionRole = permissionRoleTable.get(id);
        return permissionRole;
    }
}
