package com.lian.protocol.service;

import com.lian.protocol.model.RoleUser;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
public interface RoleUserService {

    /**
     * 新增角色用户关联记录
     * @param roleUser
     * @return
     */
    public RoleUser add(RoleUser roleUser);


    /**
     * 修改角色用户关联记录
     * @param roleUser
     * @return
     */
    public RoleUser update(RoleUser roleUser);

    /**
     * 查询角色用户关联记录列表
     * @return
     */
    public List<RoleUser> list();

    /**
     * 删除角色用户关联记录
     * @param id
     * @return
     */
    public RoleUser delete(Long id);

    /**
     * 根据用户id查询当前用户角色关联列表
     * @param uid
     * @return
     */
    public List<RoleUser> findListByUserId(Long uid);

    public Map<Long,List<RoleUser>> findMapGroupByUserId();
}
