package com.lmxdawn.admin.util;

import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.admin.res.auth.AuthPermissionRuleMergeRes;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限规则生成树形节点工具类
 */
public class PermissionRuleTreeUtils {

    /**
     * 多维数组
     */
    public static List<AuthPermissionRuleMergeRes> merge(List<AuthPermissionRule> authPermissionRuleList,
                                                         Long pid) {

        List<AuthPermissionRuleMergeRes> authPermissionRuleMergeResList = new ArrayList<>();
        for (AuthPermissionRule v : authPermissionRuleList) {
            AuthPermissionRuleMergeRes authPermissionRuleMergeRes = new AuthPermissionRuleMergeRes();
            BeanUtils.copyProperties(v, authPermissionRuleMergeRes);
            if (pid.equals(v.getPid())) {
                authPermissionRuleMergeRes.setChildren(merge(authPermissionRuleList, v.getId()));
                authPermissionRuleMergeResList.add(authPermissionRuleMergeRes);
            }
        }

        return authPermissionRuleMergeResList;
    }



}
