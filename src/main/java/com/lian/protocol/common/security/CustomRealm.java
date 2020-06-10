package com.lian.protocol.common.security;


import com.lian.protocol.common.constants.RedisKeyConstant;
import com.lian.protocol.common.myRedis.MyRedisTemplate;
import com.lian.protocol.common.utils.JwtTokenUtil;
import com.lian.protocol.model.Permission;
import com.lian.protocol.model.Role;
import com.lian.protocol.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Component
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    private MyRedisTemplate myRedisTemplate;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getPrincipal();
        Long userId = JwtTokenUtil.getId(token);
        if (userId == null||!JwtTokenUtil.verify(token)) {
            throw new AuthenticationException("你不是城堡里的人，请出门右转注册良民证");
        }
        //redis校验
        Object redisToken = myRedisTemplate.opsForValue().get(RedisKeyConstant.TOKEN+userId);
        if (!String.valueOf(redisToken).equals(token)){
            throw new AuthenticationException("进入城堡需要扣押良民证，请出门左转交出良民证");
        }
        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        Long id = JwtTokenUtil.getId(principals.toString());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得该用户角色
        List<Role> roles = userService.findRoleListByUserId(id);
        Set<String> roleSet = new HashSet<>();
        roles.stream().forEach(e -> roleSet.add(e.getRoleName()));
        //获取所有角色权限
        List<Permission> permissions = userService.findPermissionListByUserId(id);
        Set<String> permissionSet = new HashSet<>();
        permissions.forEach(e->permissionSet.add(e.getName()));
        //设置该用户拥有的角色和权限
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    public static void main(String[] args) {
        String sign = JwtTokenUtil.sign(1010L);
        System.out.println(sign);
        System.out.println(JwtTokenUtil.getId(sign));
        System.out.println(JwtTokenUtil.getId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAxMCwiZXhwIjoxNTkxMjc2NjQxfQ.McNZi-CP2PJCj7w5dZ8Tga2DpJ_VL0iqpCLP1dGkylI"));
    }
}
