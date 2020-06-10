package com.lian.protocol.service.impl;

import com.lian.protocol.common.utils.CopyNonNullUtils;
import com.lian.protocol.mapper.RoleMapper;
import com.lian.protocol.model.Role;
import com.lian.protocol.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:23
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role add(Role role) {
        roleMapper.add(role);
        Role byId = roleMapper.findById(role.getId());
        log.info("role{}",byId);
        return byId;
    }

    @Override
    public Role update(Role role) {
        Role roleExist = findById(role.getId());
        CopyNonNullUtils.copyNonNullProperties(role,roleExist);
        roleMapper.update(roleExist);
        Role byId = roleMapper.findById(role.getId());
        return byId;
    }

    @Override
    public List<Role> list() {
        List<Role> list = roleMapper.list();
        return list;
    }

    @Override
    public Role delete(Long id) {
        Role delete = roleMapper.delete(id);
        return delete;
    }

    @Override
    public Role findById(Long id) {
        Role byId = roleMapper.findById(id);
        return byId;
    }
}
