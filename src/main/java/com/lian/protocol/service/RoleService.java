package com.lian.protocol.service;

import com.lian.protocol.model.Role;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
public interface RoleService {
    /**
     * 新增角色
     * @param role
     * @return
     */
    public Role add(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    public Role update(Role role);

    /**
     * 查询角色列表
     * @return
     */
    public List<Role> list();

    /**
     * 删除角色
     * @param id
     * @return
     */
    public Role delete(Long id);

    /**
     * 根据角色id查询单个角色
     * @param id
     * @return
     */
    public Role findById(Long id);
}
