package com.lian.protocol.mapper;

import com.lian.protocol.model.Permission;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 22:05
 */
@Repository
public class PermissionMapper {
    private static HashMap<Long,Permission> permissionTable = new HashMap<>();
    
    static {
        permissionTable.put(1L,new Permission(1L,"user:add","",System.currentTimeMillis()));
        permissionTable.put(2L,new Permission(2L,"user:update","",System.currentTimeMillis()));
        permissionTable.put(3L,new Permission(3L,"user:delete","",System.currentTimeMillis()));
        permissionTable.put(4L,new Permission(4L,"user:list","",System.currentTimeMillis()));
        permissionTable.put(5L,new Permission(5L,"role:add","",System.currentTimeMillis()));
        permissionTable.put(6L,new Permission(6L,"role:update","",System.currentTimeMillis()));
        permissionTable.put(7L,new Permission(7L,"role:delete","",System.currentTimeMillis()));
        permissionTable.put(8L,new Permission(8L,"role:list","",System.currentTimeMillis()));
        permissionTable.put(9L,new Permission(9L,"permission:add","",System.currentTimeMillis()));
        permissionTable.put(10L,new Permission(10L,"permission:update","",System.currentTimeMillis()));
        permissionTable.put(11L,new Permission(11L,"permission:delete","",System.currentTimeMillis()));
        permissionTable.put(12L,new Permission(12L,"permission:list","",System.currentTimeMillis()));
    }
    /**
     * 新增权限
     * @param permission
     * @return
     */
    public Permission add(Permission permission){
        Permission put = permissionTable.put(permission.getId(), permission);
        return put;
    }

    /**
     * 修改权限
     * @param permission
     * @return
     */
    public Permission update(Permission permission){
        Permission put = permissionTable.put(permission.getId(), permission);
        return put;
    }

    /**
     * 查询权限列表
     * @return
     */
    public List<Permission> list(){
        List<Permission> collect = permissionTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    public Permission delete(Long id){
        Permission remove = permissionTable.remove(id);
        return remove;
    }

    public Permission findById(Long id){
        Permission permission = permissionTable.get(id);
        return permission;
    }
}
