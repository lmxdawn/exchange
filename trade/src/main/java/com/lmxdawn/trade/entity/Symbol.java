package com.lmxdawn.trade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Symbol {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 交易币种ID
	 */
	private Long tradeCoinId;

	/**
	 * 计价币种ID
	 */
	private Long coinId;

	/**
	 * 买入手续费率
	 */
	private Double buyFee;

	/**
	 * 买入手续费精度
	 */
	private Integer buyFeePrecision;

	/**
	 * 卖出手续费率
	 */
	private Double sellFee;

	/**
	 * 卖出手续费精度
	 */
	private Integer sellFeePrecision;

	/**
	 * 最低交易数量
	 */
	private Double minAmount;

	/**
	 * 最低交易额
	 */
	private Double minTotal;

	/**
	 * 交易额精度
	 */
	private Integer tradeTotalPrecision;

	/**
	 * 价格精度
	 */
	private Integer tradePricePrecision;

	/**
	 * 交易量精度
	 */
	private Integer tradeAmountPrecision;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

	/**
	 * 状态（1：关闭，2：开启）
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
