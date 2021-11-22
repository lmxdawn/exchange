package com.lmxdawn.admin.controller.auth;

import com.github.pagehelper.PageInfo;
import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthPermission;
import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.admin.entity.auth.AuthRole;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.req.auth.AuthRoleAuthReq;
import com.lmxdawn.admin.req.auth.AuthRoleQueryReq;
import com.lmxdawn.admin.req.auth.AuthRoleSaveReq;
import com.lmxdawn.admin.service.auth.AuthPermissionRuleService;
import com.lmxdawn.admin.service.auth.AuthPermissionService;
import com.lmxdawn.admin.service.auth.AuthRoleService;
import com.lmxdawn.admin.util.PermissionRuleTreeUtils;
import com.lmxdawn.admin.res.PageSimpleRes;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.res.auth.AuthPermissionRuleMergeRes;
import com.lmxdawn.admin.res.auth.AuthRoleRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色相关
 */
@Api(tags = "角色管理")
@RestController
public class AuthRoleController {

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthPermissionRuleService authPermissionRuleService;

    @Resource
    private AuthPermissionService authPermissionService;

    /**
     * 角色列表
     */
    @ApiOperation(value = "角色列表")
    @AuthRuleAnnotation("auth/role/index")
    @GetMapping("/auth/role/index")
    public BaseRes<PageSimpleRes<AuthRoleRes>> index(@Valid AuthRoleQueryReq authRoleQueryRequest,
                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<AuthRole> authRoleList = authRoleService.listAdminPage(authRoleQueryRequest);
        List<AuthRoleRes> authRoleResList = authRoleList.stream().map(item -> {
            AuthRoleRes authRoleRes = new AuthRoleRes();
            BeanUtils.copyProperties(item, authRoleRes);
            return authRoleRes;
        }).collect(Collectors.toList());

        PageInfo<AuthRole> pageInfo = new PageInfo<>(authRoleList);
        PageSimpleRes<AuthRoleRes> pageSimpleRes = new PageSimpleRes<>();
        pageSimpleRes.setTotal(pageInfo.getTotal());
        pageSimpleRes.setList(authRoleResList);
        return ResultVOUtils.success(pageSimpleRes);
    }

    /**
     * 获取授权列表
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取已授权列表")
    @AuthRuleAnnotation("auth/role/authList")
    @ApiImplicitParam(name = "id", value = "角色ID")
    @GetMapping("/auth/role/authList")
    public BaseRes authList(@RequestParam("id") Long id) {

        // 查询当前角色拥有的权限id
        List<AuthPermission> authPermissionList = authPermissionService.listByRoleId(id);
        List<Long> checkedKeys = authPermissionList.stream()
                .map(AuthPermission::getPermissionRuleId)
                .collect(Collectors.toList());

        // 查询所有权限规则
        List<AuthPermissionRule> authPermissionRuleList = authPermissionRuleService.listAll();
        List<AuthPermissionRuleMergeRes> merge = PermissionRuleTreeUtils.merge(authPermissionRuleList, 0L);

        Map<String, Object> restMap = new HashMap<>();
        restMap.put("list", merge);
        restMap.put("checkedKeys", checkedKeys);
        return ResultVOUtils.success(restMap);
    }

    /**
     * 授权
     * @param authRoleAuthReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "授权")
    @AuthRuleAnnotation("auth/role/auth")
    @PostMapping("/auth/role/auth")
    public BaseRes auth(@RequestBody @Valid AuthRoleAuthReq authRoleAuthReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 先删除之前的授权
        authPermissionService.deleteByRoleId(authRoleAuthReq.getRoleId());

        List<AuthPermission> authPermissionList = authRoleAuthReq.getAuthRules().stream()
                .map(aLong -> {
                    AuthPermission authPermission = new AuthPermission();
                    authPermission.setRoleId(authRoleAuthReq.getRoleId());
                    authPermission.setPermissionRuleId(aLong);
                    authPermission.setType("admin");
                    return authPermission;
                }).collect(Collectors.toList());

        int i = authPermissionService.insertAuthPermissionAll(authPermissionList);

        return ResultVOUtils.success();
    }

    /**
     * 新增
     *
     * @param authRoleSaveReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增角色")
    @AuthRuleAnnotation("auth/role/save")
    @PostMapping("/auth/role/save")
    public BaseRes save(@RequestBody @Valid AuthRoleSaveReq authRoleSaveReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        AuthRole byName = authRoleService.findByName(authRoleSaveReq.getName());
        if (byName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }

        AuthRole authRole = new AuthRole();
        BeanUtils.copyProperties(authRoleSaveReq, authRole);

        boolean b = authRoleService.insertAuthRole(authRole);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", authRole.getId());
        return ResultVOUtils.success(res);
    }

    /**
     * 编辑
     *
     * @param authRoleSaveReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "编辑角色")
    @AuthRuleAnnotation("auth/role/edit")
    @PostMapping("/auth/role/edit")
    public BaseRes edit(@RequestBody @Valid AuthRoleSaveReq authRoleSaveReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authRoleSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        // 检查是否存在当前角色
        AuthRole byName = authRoleService.findByName(authRoleSaveReq.getName());
        if (byName != null && !authRoleSaveReq.getId().equals(byName.getId())) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }

        AuthRole authRole = new AuthRole();
        BeanUtils.copyProperties(authRoleSaveReq, authRole);

        boolean b = authRoleService.updateAuthRole(authRole);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
     * 删除
     *
     * @param authRoleSaveReq
     * @return
     */
    @ApiOperation(value = "删除角色")
    @AuthRuleAnnotation("auth/role/delete")
    @PostMapping("/auth/role/delete")
    public BaseRes delete(@RequestBody AuthRoleSaveReq authRoleSaveReq) {

        if (authRoleSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        boolean b = authRoleService.deleteById(authRoleSaveReq.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        //TODO 删除角色后先前授权的缓存不会消失

        // 再删除之前的授权
        authPermissionService.deleteByRoleId(authRoleSaveReq.getId());

        return ResultVOUtils.success();
    }


}
