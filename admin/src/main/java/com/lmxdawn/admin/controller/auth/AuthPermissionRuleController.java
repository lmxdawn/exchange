package com.lmxdawn.admin.controller.auth;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.req.auth.AuthPermissionRuleSaveReq;
import com.lmxdawn.admin.service.auth.AuthPermissionRuleService;
import com.lmxdawn.admin.util.PermissionRuleTreeUtils;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.res.auth.AuthPermissionRuleMergeRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限规则相关
 */
@Api(tags = "权限规则管理")
@RestController
public class AuthPermissionRuleController {

    @Resource
    private AuthPermissionRuleService authPermissionRuleService;

    /**
     * 列表
     * @return
     */
    @ApiOperation(value = "权限规则列表")
    @AuthRuleAnnotation("auth/permission_rule/index")
    @GetMapping("/auth/permission_rule/index")
    public BaseRes<List<AuthPermissionRuleMergeRes>> index() {


        List<AuthPermissionRule> authPermissionRuleList = authPermissionRuleService.listAll();
        List<AuthPermissionRuleMergeRes> merge = PermissionRuleTreeUtils.merge(authPermissionRuleList,0L);

        Map<String,Object> restMap = new HashMap<>();
        restMap.put("list", merge);
        return ResultVOUtils.success(restMap);
    }

    /**
     * 新增
     * @param authPermissionRuleSaveReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增权限规则")
    @AuthRuleAnnotation("auth/permission_rule/save")
    @PostMapping("/auth/permission_rule/save")
    public BaseRes save(@RequestBody @Valid AuthPermissionRuleSaveReq authPermissionRuleSaveReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authPermissionRuleSaveReq.getPid() == null) {
            authPermissionRuleSaveReq.setPid(0L); // 默认设置
        }
        AuthPermissionRule authPermissionRule = new AuthPermissionRule();
        BeanUtils.copyProperties(authPermissionRuleSaveReq, authPermissionRule);

        boolean b = authPermissionRuleService.insertAuthPermissionRule(authPermissionRule);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", authPermissionRule.getId());
        return ResultVOUtils.success(res);
    }

    /**
     * 编辑
     * @param authPermissionRuleSaveReq
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "编辑权限规则")
    @AuthRuleAnnotation("auth/permission_rule/edit")
    @PostMapping("/auth/permission_rule/edit")
    public BaseRes edit(@RequestBody @Valid AuthPermissionRuleSaveReq authPermissionRuleSaveReq,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authPermissionRuleSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        authPermissionRuleSaveReq.setPid(null); // 不能修改父级 pid

        AuthPermissionRule authPermissionRule = new AuthPermissionRule();
        BeanUtils.copyProperties(authPermissionRuleSaveReq, authPermissionRule);

        boolean b = authPermissionRuleService.updateAuthPermissionRule(authPermissionRule);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
     * 删除
     * @param authPermissionRuleSaveReq
     * @return
     */
    @ApiOperation(value = "删除权限规则")
    @AuthRuleAnnotation("auth/permission_rule/delete")
    @PostMapping("/auth/permission_rule/delete")
    public BaseRes delete(@RequestBody AuthPermissionRuleSaveReq authPermissionRuleSaveReq) {

        if (authPermissionRuleSaveReq.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        boolean b = authPermissionRuleService.deleteById(authPermissionRuleSaveReq.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }


}
