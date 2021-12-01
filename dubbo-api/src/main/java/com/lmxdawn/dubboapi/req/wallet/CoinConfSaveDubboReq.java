package com.lmxdawn.dubboapi.req.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 币种配置添加/修改
 */
@ApiModel
@Data
public class CoinConfSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "币种ID")
    @NotNull(message = "币种不能为空")
    private Long coinId;

    @ApiModelProperty(value = "协议ID")
    @NotNull(message = "协议不能为空")
    private Long protocolId;

    @ApiModelProperty(value = "当前币种协议的转账私钥")
    private String privateKey;

    @ApiModelProperty(value = "合约地址（代币需要）")
    private String contractAddress;

    @ApiModelProperty(value = "是否可提现（1：否，2：是）")
    @NotNull(message = "是否可提现必传")
    @Min(value = 1, message = "是否可提现格式错误")
    @Max(value = 2, message = "是否可提现格式错误")
    private Integer isWithdraw;

    @ApiModelProperty(value = "提现费率")
    @Min(value = 0, message = "提现费率格式错误")
    private Double withdrawRate;

    @ApiModelProperty(value = "最低提现费用")
    @Min(value = 0, message = "最低提现费用格式错误")
    private Double minWithdrawFee;

    @ApiModelProperty(value = "最低提现")
    @Min(value = 0, message = "最低提现格式错误")
    private Double minWithdraw;

    @ApiModelProperty(value = "最大提现")
    @Min(value = 0, message = "最大提现格式错误")
    private Double maxWithdraw;

    @ApiModelProperty(value = "是否可充值（1：否，2：是）")
    @NotNull(message = "是否可充值必传")
    @Min(value = 1, message = "是否可充值格式错误")
    @Max(value = 2, message = "是否可充值格式错误")
    private Integer isRecharge;

    @ApiModelProperty(value = "最低充值")
    @Min(value = 0, message = "最低充值格式错误")
    private Double minRecharge;

    @ApiModelProperty(value = "确认数量")
    @Min(value = 0, message = "确认数量格式错误")
    private Integer confirmations;

    @ApiModelProperty(value = "最低归集数量（满足当前数量自动归集）")
    @Min(value = 0, message = "最低归集数量格式错误")
    private Double minGather;

    @ApiModelProperty(value = "归集的地址")
    private String gatherAddress;
}
