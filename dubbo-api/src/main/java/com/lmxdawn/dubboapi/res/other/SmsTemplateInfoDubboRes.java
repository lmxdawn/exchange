package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SmsTemplateInfoDubboRes implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传", position = 1)
    private Integer id;

    @ApiModelProperty(value = "平台（1：华为云，2：阿里云）", position = 1)
    private Integer platform;

    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定，4：注销，5：找回密码）", position = 2)
    private Integer scene;

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
