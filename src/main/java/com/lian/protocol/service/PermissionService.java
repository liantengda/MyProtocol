package com.lian.protocol.service;

import com.lian.protocol.model.Permission;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
public interface PermissionService {

    /**
     * 新增权限
     * @param permission
     * @return
     */
    public Permission add(Permission permission);

    /**
     * 修改权限
     * @param permission
     * @return
     */
    public Permission update(Permission permission);

    /**
     * 查询权限列表
     * @return
     */
    public List<Permission> list();

    /**
     * 删除权限
     * @param id
     * @return
     */
    public Permission delete(Long id);

    /**
     * 根据权限id查询单个权限
     * @param id
     * @return
     */
    public Permission findById(Long id);
}
