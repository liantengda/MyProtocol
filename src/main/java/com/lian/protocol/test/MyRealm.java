package com.lian.protocol.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/4 22:10
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    /**
     * 模拟数据库数据
     */
    Map<String, String> userMap = new HashMap<>(16);

    {
        userMap.put("Ted", "123456");
        super.setName("myRealm"); // 设置自定义Realm的名称，取什么无所谓..
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("-----------------权限认证开始--------------");
        String userName = (String) principalCollection.getPrimaryPrincipal();
        log.info("userName{}",userName);
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }


    /**
     * 模拟从数据库中获取权限数据
     *
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:add");
        return permissions;
    }

    /**
     * 模拟从数据库中获取角色数据
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("----------身份认证开始----------------");
        // 1.从主体传过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();
        log.info("userName{}",userName);
        // 2.通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("wmyskxz", password, "myRealm");
        return authenticationInfo;
    }

    /**
     * 模拟从数据库取凭证的过程
     *
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
