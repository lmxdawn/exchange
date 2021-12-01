package com.lmxdawn.wallet.entity;

import lombok.Data;
import java.util.Date;

@Data
public class CoinConf {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 币种ID
	 */
	private Long coinId;

	/**
	 * 协议ID
	 */
	private Long protocolId;

	/**
	 * 当前币种协议的转账私钥
	 */
	private String privateKey;

	/**
	 * 合约地址（代币需要）
	 */
	private String contractAddress;

	/**
	 * 是否可提现（1：否，2：是）
	 */
	private Integer isWithdraw;

	/**
	 * 提现费率
	 */
	private Double withdrawRate;

	/**
	 * 最低提现费用
	 */
	private Double minWithdrawFee;

	/**
	 * 最低提现
	 */
	private Double minWithdraw;

	/**
	 * 最大提现
	 */
	private Double maxWithdraw;

	/**
	 * 是否可充值（1：否，2：是）
	 */
	private Integer isRecharge;

	/**
	 * 最低充值
	 */
	private Double minRecharge;

	/**
	 * 确认数量
	 */
	private Integer confirmations;

	/**
	 * 最低归集数量（满足当前数量自动归集）
	 */
	private Double minGather;

	/**
	 * 归集的地址
	 */
	private String gatherAddress;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
