package com.lmxdawn.user.res;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class MemberBillRes {

    @ApiModelProperty(value = "币种信息")
    private CoinSimpleDubboRes coin;

    @ApiModelProperty(value = "账单分类名称")
    private String category;

    @ApiModelProperty(value = "类型（1：收入，2：支出）")
    private String type;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
