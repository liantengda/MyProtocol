package com.lian.protocol.mapper;

import com.lian.protocol.model.RoleUser;
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
public class RoleUserMapper {
    private static HashMap<Long,RoleUser> roleUserTable = new HashMap<>();

    static {
        roleUserTable.put(1L,new RoleUser(1L,1L,1L,System.currentTimeMillis()));
        roleUserTable.put(2L,new RoleUser(2L,2L,3L,System.currentTimeMillis()));
        roleUserTable.put(3L,new RoleUser(3L,2L,2L,System.currentTimeMillis()));
        roleUserTable.put(4L,new RoleUser(4L,3L,3L,System.currentTimeMillis()));
        roleUserTable.put(5L,new RoleUser(5L,3L,2L,System.currentTimeMillis()));
        roleUserTable.put(6L,new RoleUser(6L,4L,5L,System.currentTimeMillis()));
        roleUserTable.put(7L,new RoleUser(7L,5L,7L,System.currentTimeMillis()));
        roleUserTable.put(8L,new RoleUser(8L,6L,7L,System.currentTimeMillis()));
        roleUserTable.put(9L,new RoleUser(9L,7L,7L,System.currentTimeMillis()));
        roleUserTable.put(10L,new RoleUser(10L,8L,8L,System.currentTimeMillis()));
        roleUserTable.put(11L,new RoleUser(11L,9L,9L,System.currentTimeMillis()));
        roleUserTable.put(12L,new RoleUser(12L,10L,5L,System.currentTimeMillis()));
        roleUserTable.put(13L,new RoleUser(13L,11L,5L,System.currentTimeMillis()));
    }
    /**
     * 新增角色用户关联记录
     * @param roleUser
     * @return
     */
    public RoleUser add(RoleUser roleUser){
        RoleUser put = roleUserTable.put(roleUser.getId(), roleUser);
        return put;
    }

    /**
     * 修改角色用户关联记录
     * @param roleUser
     * @return
     */
    public RoleUser update(RoleUser roleUser){
        RoleUser put = roleUserTable.put(roleUser.getId(), roleUser);
        return put;
    }

    /**
     * 查询角色用户关联记录列表
     * @return
     */
    public List<RoleUser> list(){
        List<RoleUser> collect = roleUserTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    /**
     * 删除角色用户关联记录
     * @param id
     * @return
     */
    public RoleUser delete(Long id){
        RoleUser remove = roleUserTable.remove(id);
        return remove;
    }

    /**
     * 根据用户id查询角色用户关联列表
     * @param userId
     * @return
     */
    public List<RoleUser> findListByUserId(Long userId){
        List<RoleUser> collect = roleUserTable.entrySet().stream().map(e -> e.getValue()).filter(e -> e.getUserId().equals(userId)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 根据主键id查询用户角色关联记录
     * @param id
     * @return
     */
    public RoleUser findListById(Long id){
        RoleUser roleUser = roleUserTable.get(id);
        return roleUser;
    }

    public Map<Long,List<RoleUser>> findMapGroupByUserId(){
        List<RoleUser> collect = roleUserTable.entrySet().stream().map(e->e.getValue()).collect(Collectors.toList());
        Map<Long, List<RoleUser>> resultGroupByUserId = collect.stream().collect(Collectors.groupingBy(e -> e.getUserId()));
        return resultGroupByUserId;
    }
}
