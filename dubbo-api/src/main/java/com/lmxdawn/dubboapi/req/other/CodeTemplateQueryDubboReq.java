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
public class CodeTemplateQueryDubboReq extends ListPageDubboReq {
    @ApiModelProperty(value = "平台（1：华为云短信，2：阿里云短信，3：邮件）", position = 1)
    private Integer platform;

    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码）", position = 2)
    private Integer scene;

    @ApiModelProperty(value = "语言", position = 2)
    private String lang;

}
