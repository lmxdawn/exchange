package com.lmxdawn.admin.controller.auth;

import com.github.pagehelper.PageInfo;
import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthAdmin;
import com.lmxdawn.admin.entity.auth.AuthRole;
import com.lmxdawn.admin.entity.auth.AuthRoleAdmin;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.req.auth.AuthAdminSaveReq;
import com.lmxdawn.admin.req.auth.AuthAdminQueryReq;
import com.lmxdawn.admin.service.auth.AuthAdminService;
import com.lmxdawn.admin.service.auth.AuthRoleAdminService;
import com.lmxdawn.admin.service.auth.AuthRoleService;
import com.lmxdawn.admin.util.PasswordUtils;
import com.lmxdawn.admin.res.auth.AuthAdminRoleRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.admin.res.PageSimpleRes;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.res.auth.AuthAdminRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理员相关
 */
@Api(tags = "管理员")
@RestController
public class AuthAdminController {

    @Resource
    private AuthAdminService authAdminService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthRoleAdminService authRoleAdminService;

    /**
     * 获取管理员列表
     */
    @ApiOperation(value = "获取管理员列表")
    @AuthRuleAnnotation("auth/admin/index")
    @GetMapping("auth/admin/index")
    public BaseRes<PageSimpleRes<AuthAdmin>> index(@Valid AuthAdminQueryReq authAdminQueryRequest,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authAdminQueryRequest.getRoleId() != null) {
            List<AuthRoleAdmin> authRoleAdmins = authRoleAdminService.listByRoleId(authAdminQueryRequest.getRoleId());
            List<Long> ids = new ArrayList<>();
            if (authRoleAdmins != null && !authRoleAdmins.isEmpty()) {
                ids = authRoleAdmins.stream().map(AuthRoleAdmin::getAdminId).collect(Collectors.toList());
            }
            authAdminQueryRequest.setIds(ids);
        }
        List<AuthAdmin> authAdminList = authAdminService.listAdminPage(authAdminQueryRequest);

        // 查询所有的权限
        List<Long> adminIds = authAdminList.stream().map(AuthAdmin::getId).collect(Collectors.toList());
        List<AuthRoleAdmin> authRoleAdminList = authRoleAdminService.listByAdminIdIn(adminIds);

        // 视图列表
        List<AuthAdminRes> authAdminResList = authAdminList.stream().map(item -> {
            AuthAdminRes authAdminRes = new AuthAdminRes();
            BeanUtils.copyProperties(item, authAdminRes);
            List<Long> roles = authRoleAdminList.stream()
                    .filter(authRoleAdmin -> authAdminRes.getId().equals(authRoleAdmin.getAdminId()))
                    .map(AuthRoleAdmin::getRoleId)
                    .collect(Collectors.toList());
            authAdminRes.setRoles(roles);
            return authAdminRes;
        }).collect(Collectors.toList());

        PageInfo<AuthAdmin> authAdminPageInfo = new PageInfo<>(authAdminList);
        PageSimpleRes<AuthAdminRes> authAdminPageSimpleRes = new PageSimpleRes<>();
        authAdminPageSimpleRes.setTotal(authAdminPageInfo.getTotal());
        authAdminPageSimpleRes.setList(authAdminResList);

        return ResultVOUtils.success(authAdminPageSimpleRes);

    }


    /**
     * 获取角色列表
     */
    @ApiOperation(value = "获取角色列表")
    @AuthRuleAnnotation("auth/admin/roleList")
    @GetMapping("auth/admin/roleList")
    public BaseRes<PageSimpleRes<AuthAdminRoleRes>> roleList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "limit", defaultValue = "100") Integer limit) {

        List<AuthRole> authRoleList = authRoleService.listAuthAdminRolePage(page, limit, null);
        PageInfo<AuthRole> pageInfo = new PageInfo<>(authRoleList);
        PageSimpleRes<AuthAdminRoleRes> pageSimpleRes = new PageSimpleRes<>();
        pageSimpleRes.setTotal(pageInfo.getTotal());
        List<AuthAdminRoleRes> authAdminRoleRespons = authRoleList.stream().map(e -> {
            AuthAdminRoleRes authAdminRoleRes = new AuthAdminRoleRes();
            BeanUtils.copyProperties(e, authAdminRoleRes);
            return authAdminRoleRes;
        }).collect(Collectors.toList());
        pageSimpleRes.setList(authAdminRoleRespons);

        return ResultVOUtils.success(pageSimpleRes);

    }


    /**
     * 新增
     *
     * @return
     */
    @ApiOperation(value = "新增管理员")
    @AuthRuleAnnotation("auth/admin/save")
    @PostMapping("auth/admin/save")
    public BaseRes<Map> save(@RequestBody @Valid AuthAdminSaveReq authAdminSaveReq,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 检查是否存在相同名称的管理员
        AuthAdmin byUserName = authAdminService.findByUserName(authAdminSaveReq.getUsername());
        if (byUserName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前管理员已存在");
        }

        AuthAdmin authAdmin = new AuthAdmin();
        BeanUtils.copyProperties(authAdminSaveReq, authAdmin);

        if (authAdmin.getPassword() != null) {
            authAdmin.setPassword(PasswordUtils.authAdminPwd(authAdmin.getPassword()));
        }

        boolean b = authAdminService.insertAuthAdmin(authAdmin);

        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        // 插入角色
        if (authAdminSaveReq.getRoles() != null) {
            authRoleAdminService.insertRolesAdminIdAll(authAdminSaveReq.getRoles(), authAdmin.getId());
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", authAdmin.getId());
        return ResultVOUtils.success(res);
    }

    /**
     * 修改
     *
     * @return
     */
    @ApiOperation(value = "编辑管理员")
    @AuthRuleAnnotation("auth/admin/edit")
    @PostMapping("auth/admin/edit")
    public BaseRes edit(@RequestBody @Valid AuthAdminSaveReq authAdminSaveReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authAdminSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "参数错误！");
        }

        // 检查是否存在除了当前管理员的其它名称的管理员
        AuthAdmin byUserName = authAdminService.findByUserName(authAdminSaveReq.getUsername());
        if (byUserName != null && !authAdminSaveReq.getId().equals(byUserName.getId())) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前管理员已存在");
        }

        AuthAdmin authAdmin = new AuthAdmin();
        BeanUtils.copyProperties(authAdminSaveReq, authAdmin);
        if (authAdmin.getPassword() != null) {
            authAdmin.setPassword(PasswordUtils.authAdminPwd(authAdmin.getPassword()));
        }

        boolean b = authAdminService.updateAuthAdmin(authAdmin);

        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        // 修改角色
        if (authAdminSaveReq.getRoles() != null) {
            // 先删除之前的
            authRoleAdminService.deleteByAdminId(authAdmin.getId());
            authRoleAdminService.insertRolesAdminIdAll(authAdminSaveReq.getRoles(), authAdmin.getId());
        }

        return ResultVOUtils.success();
    }

    /**
     * 删除
     *
     * @return
     */
    @ApiOperation(value = "删除管理员")
    @AuthRuleAnnotation("auth/admin/delete")
    @PostMapping("auth/admin/delete")
    public BaseRes delete(@RequestBody AuthAdminSaveReq authAdminSaveReq) {

        if (authAdminSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "参数错误！");
        }

        boolean b = authAdminService.deleteById(authAdminSaveReq.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        // 先删除之前的角色
        authRoleAdminService.deleteByAdminId(authAdminSaveReq.getId());

        return ResultVOUtils.success();
    }


}
