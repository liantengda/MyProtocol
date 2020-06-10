package com.lian.protocol.service.impl;

import com.lian.protocol.mapper.RoleUserMapper;
import com.lian.protocol.model.RoleUser;
import com.lian.protocol.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    RoleUserMapper roleUserMapper;

    @Override
    public RoleUser add(RoleUser roleUser) {
        roleUserMapper.add(roleUser);
        RoleUser roleUserExist = roleUserMapper.findListById(roleUser.getId());
        return roleUserExist;
    }

    @Override
    public RoleUser update(RoleUser roleUser) {
        roleUserMapper.update(roleUser);
        RoleUser update = roleUserMapper.findListById(roleUser.getId());
        return update;
    }

    @Override
    public List<RoleUser> list() {
        List<RoleUser> list = roleUserMapper.list();
        return list;
    }

    @Override
    public RoleUser delete(Long id) {

        RoleUser delete = roleUserMapper.delete(id);
        return delete;
    }

    @Override
    public List<RoleUser> findListByUserId(Long userId) {
        List<RoleUser> roleUsers = roleUserMapper.findListByUserId(userId);
        return roleUsers;
    }

    @Override
    public Map<Long, List<RoleUser>> findMapGroupByUserId() {
        Map<Long, List<RoleUser>> mapGroupByUserId = roleUserMapper.findMapGroupByUserId();
        return mapGroupByUserId;
    }

}
