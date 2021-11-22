package com.lmxdawn.admin.service.auth;

import com.lmxdawn.admin.entity.auth.AuthPermission;

import java.util.List;

public interface AuthPermissionService {


    List<AuthPermission> listByRoleIdIn(List<Long> roleIds);

    List<AuthPermission> listByRoleId(Long roleId);

    int insertAuthPermissionAll(List<AuthPermission> authPermissionList);

    boolean deleteByRoleId(Long roleId);

}
