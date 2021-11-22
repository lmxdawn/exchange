package com.lmxdawn.dubboapi.req.other;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信模板
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsTemplateQueryDubboReq extends ListPageDubboReq {
    @ApiModelProperty(value = "平台（1：华为云，2：阿里云）", position = 1)
    private Integer platform;

    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定，4：注销，5：找回密码）", position = 2)
    private Integer scene;

}
