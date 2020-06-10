package com.lian.protocol.mapper;

import com.lian.protocol.model.Role;
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
public class RoleMapper {
    private static HashMap<Long,Role> roleTable = new HashMap<>();
    
    static {
        roleTable.put(1L,new Role(1L,"超级管理员",System.currentTimeMillis()));
        roleTable.put(2L,new Role(2L,"管理员",System.currentTimeMillis()));
        roleTable.put(3L,new Role(3L,"运营组",System.currentTimeMillis()));
        roleTable.put(4L,new Role(4L,"开发组",System.currentTimeMillis()));
        roleTable.put(5L,new Role(5L,"市场部",System.currentTimeMillis()));
        roleTable.put(6L,new Role(6L,"视觉组",System.currentTimeMillis()));
        roleTable.put(7L,new Role(7L,"绿帽组",System.currentTimeMillis()));
        roleTable.put(8L,new Role(8L,"纪府丫鬟",System.currentTimeMillis()));
        roleTable.put(9L,new Role(9L,"董事部",System.currentTimeMillis()));
    }
    /**
     * 新增角色
     * @param role
     * @return
     */
    public Role add(Role role){
        role.setCreateTime(System.currentTimeMillis());
        Role put = roleTable.put(role.getId(), role);
        return put;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    public Role update(Role role){
        Role put = roleTable.put(role.getId(), role);
        return put;
    }

    /**
     * 查询角色列表
     * @return
     */
    public List<Role> list(){
        List<Role> collect = roleTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    public Role delete(Long id){
        Role remove = roleTable.remove(id);
        return remove;
    }

    public Role findById(Long id){
        Role role = roleTable.get(id);
        return role;
    }
}
