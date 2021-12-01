package com.lmxdawn.dubboapi.res.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class CoinConfDubboRes implements Serializable {
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "币种ID")
    private Long coinId;

    @ApiModelProperty(value = "协议ID")
    private Long protocolId;

    @ApiModelProperty(value = "当前币种协议的转账私钥")
    private String privateKey;

    @ApiModelProperty(value = "合约地址（代币需要）")
    private String contractAddress;

    @ApiModelProperty(value = "是否可提现（1：否，2：是）")
    private Integer isWithdraw;

    @ApiModelProperty(value = "提现费率")
    private Double withdrawRate;

    @ApiModelProperty(value = "最低提现费用")
    private Double minWithdrawFee;

    @ApiModelProperty(value = "最低提现")
    private Double minWithdraw;

    @ApiModelProperty(value = "最大提现")
    private Double maxWithdraw;

    @ApiModelProperty(value = "是否可充值（1：否，2：是）")
    private Integer isRecharge;

    @ApiModelProperty(value = "最低充值")
    private Double minRecharge;

    @ApiModelProperty(value = "确认数量")
    private Integer confirmations;

    @ApiModelProperty(value = "最低归集数量（满足当前数量自动归集）")
    private Double minGather;

    @ApiModelProperty(value = "归集的地址")
    private String gatherAddress;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;
}
