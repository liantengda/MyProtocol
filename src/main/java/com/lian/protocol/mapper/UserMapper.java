package com.lian.protocol.mapper;

import com.lian.protocol.model.User;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserMapper {
    private static HashMap<Long,User> userTable = new HashMap<>();

    static {
        userTable.put(1L,new User(1L,"ted","1234567","18909877890",System.currentTimeMillis()));
        userTable.put(2L,new User(2L,"和中堂","1234567","18909877891",System.currentTimeMillis()));
        userTable.put(3L,new User(3L,"纪大人","1234567","18909877892",System.currentTimeMillis()));
        userTable.put(4L,new User(4L,"杜小月","1234567","18909877893",System.currentTimeMillis()));
        userTable.put(5L,new User(5L,"黄克明","1234567","18909877894",System.currentTimeMillis()));
        userTable.put(6L,new User(6L,"祝君豪","1234567","18909877895",System.currentTimeMillis()));
        userTable.put(7L,new User(7L,"丰申殷德","1234567","18909877896",System.currentTimeMillis()));
        userTable.put(8L,new User(8L,"杏儿","1234567","18909877897",System.currentTimeMillis()));
        userTable.put(9L,new User(9L,"黄三爷","1234567","18909877898",System.currentTimeMillis()));
        userTable.put(10L,new User(10L,"四姑娘","1234567","18909877899",System.currentTimeMillis()));
        userTable.put(11L,new User(11L,"苏卿莲","1234567","18909877810",System.currentTimeMillis()));
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    public User add(User user){
        user.setCreateTime(System.currentTimeMillis());
        userTable.put(user.getId(), user);
        User save = userTable.get(user.getId());
        return save;
    }

    /**
     * 根据用户id查询个人信息
     * @param id
     * @return
     */
    public User findById(Long id){
        User user = userTable.get(id);
        return user;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    public User update(User user){
        User put = userTable.put(user.getId(), user);
        return put;
    }

    /**
     * 查询用户列表
     * @return
     */
    public List<User> list(){
        List<User> collect = userTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public User delete(Long id){
        User remove = userTable.remove(id);
        return remove;
    }

    /**
     * 根据手机号码查找单个用户
     * @param userPhone
     * @return
     */
    public User findByPhone(String userPhone){
        List<User> collect = userTable.entrySet().stream().map(e -> e.getValue()).filter(e->e.getPhone().equals(userPhone)).collect(Collectors.toList());
        User userExist = collect.size()>0?collect.get(0):null;
        return userExist;
    }
}
