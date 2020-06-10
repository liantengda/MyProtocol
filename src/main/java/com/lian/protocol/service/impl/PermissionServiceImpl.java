package com.lian.protocol.service.impl;

import com.lian.protocol.common.enums.MyBusinessExceptionEnum;
import com.lian.protocol.mapper.PermissionMapper;
import com.lian.protocol.model.Permission;
import com.lian.protocol.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Permission add(Permission permission) {
        permission.setCreateTime(System.currentTimeMillis());
        Permission add = permissionMapper.add(permission);
        Permission byId = permissionMapper.findById(permission.getId());
        return byId;
    }

    @Override
    public Permission update(Permission permission) {
        Permission update = permissionMapper.update(permission);
        return update;
    }

    @Override
    public List<Permission> list() {
        List<Permission> list = permissionMapper.list();
        return list;
    }

    @Override
    public Permission delete(Long id) {
        Permission byId = permissionMapper.findById(id);
        MyBusinessExceptionEnum.USER_ALREADY_DELETED.assertNotNull(byId);
        Permission delete = permissionMapper.delete(id);
        return delete;
    }

    @Override
    public Permission findById(Long id) {
        Permission byId = permissionMapper.findById(id);
        return byId;
    }
}
