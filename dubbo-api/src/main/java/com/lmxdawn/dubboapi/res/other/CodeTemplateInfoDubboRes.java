package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class CodeTemplateInfoDubboRes implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传", position = 1)
    private Integer id;

    @ApiModelProperty(value = "平台（1：华为云短信，2：阿里云短信，3：邮件）", position = 1)
    private Integer platform;

    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码）", position = 2)
    private Integer scene;

    @ApiModelProperty(value = "语言（国际化）", position = 2)
    private String lang;

    @ApiModelProperty(value = "模板ID", position = 2)
    private String templateId;

    @ApiModelProperty(value = "模板参数", position = 2)
    private String templateParas;

    @ApiModelProperty(value = "备注", position = 2)
    private String remark;

    @ApiModelProperty(value = "状态（0：关闭验证，1：开启验证）", position = 2)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
