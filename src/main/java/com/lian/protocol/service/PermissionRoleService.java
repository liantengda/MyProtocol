package com.lian.protocol.service;

import com.lian.protocol.model.PermissionRole;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
public interface PermissionRoleService {
    /**
     * 新增权限角色关联记录
     * @param permissionRole
     * @return
     */
    public PermissionRole add(PermissionRole permissionRole);

    /**
     * 修改权限角色关联记录
     * @param permissionRole
     * @return
     */
    public PermissionRole update(PermissionRole permissionRole);

    /**
     * 查询权限角色关联记录列表
     * @return
     */
    public List<PermissionRole> list();

    /**
     * 删除权限角色关联记录
     * @param id
     * @return
     */
    public PermissionRole delete(Long id);

    public List<PermissionRole> findListByRoleId(Long roleId);

    Map<Long,List<PermissionRole>> findMapGroupByRoleId();

}
