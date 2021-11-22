package com.lmxdawn.admin.service.auth.impl;

import com.lmxdawn.admin.entity.auth.AuthPermission;
import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.admin.entity.auth.AuthRoleAdmin;
import com.lmxdawn.admin.service.auth.*;
import com.lmxdawn.admin.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthLoginServiceImpl implements AuthLoginService {

    @Resource
    private AuthRoleAdminService authRoleAdminService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthPermissionRuleService authPermissionRuleService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据 管理员id 获取权限
     * @param adminId
     * @return
     */
    @Override
    public List<String> listRuleByAdminId(Long adminId) {

        List<String> authRules = new ArrayList<>();
        // 超级管理员
        if (adminId.equals(1L)) {
            authRules.add("admin");
            return authRules;
        }

        // 如果存在，先从缓存中获取权限
        String aarKey = String.format(CacheConstant.ADMIN_AUTH_RULES, adminId);
        if (redisTemplate.hasKey(aarKey)) {
            return new ArrayList<>(Objects.requireNonNull(redisTemplate.opsForSet().members(aarKey)));
        }
        log.info("开始获取数据库中的用户的权限规则列表");

        // 获取角色ids
        List<AuthRoleAdmin> authRoleAdmins = authRoleAdminService.listByAdminId(adminId);

        List<Long> roleIds = authRoleAdmins.stream().map(AuthRoleAdmin::getRoleId).collect(Collectors.toList());

        // 角色授权列表
        List<AuthPermission> authPermissions = authPermissionService.listByRoleIdIn(roleIds);
        List<Long> permissionRuleIds  = authPermissions.stream().map(AuthPermission::getPermissionRuleId).collect(Collectors.toList());

        // 获取授权的规则
        List<AuthPermissionRule> authPermissionRules = authPermissionRuleService.listByIdIn(permissionRuleIds);

        // 获取权限列表
        authRules = authPermissionRules.stream().map(AuthPermissionRule::getName).collect(Collectors.toList());

        // 如果为空，则添加一个空值
        if (authRules.isEmpty()) {
            authRules.add("");
        }

        String[] strings = authRules.toArray(new String[0]);
        redisTemplate.opsForSet().add(aarKey, strings);
        redisTemplate.expire(aarKey, 7200L, TimeUnit.SECONDS); // 两小时后过期

        return authRules;
    }

}
