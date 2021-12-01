package com.lmxdawn.market.entity;

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
	 * 手续费率
	 */
	private Double fee;

	/**
	 * 手续费精度
	 */
	private Integer feePrecision;

	/**
	 * 交易额精度
	 */
	private Integer tradeTotalPrecision;

	/**
	 * 价格精度
	 */
	private Integer tradePricePrecision;

	/**
	 * 买入量精度
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
