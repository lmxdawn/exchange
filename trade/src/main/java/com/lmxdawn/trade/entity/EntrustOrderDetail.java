package com.lmxdawn.trade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class EntrustOrderDetail {

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
	 * 买家用户ID
	 */
	private Long buyMemberId;

	/**
	 * 买家订单ID
	 */
	private Long buyOrderId;

	/**
	 * 买家手续费
	 */
	private Double buyFee;

	/**
	 * 卖家单用户ID
	 */
	private Long sellMemberId;

	/**
	 * 卖家订单ID
	 */
	private Long sellOrderId;

	/**
	 * 卖家手续费
	 */
	private Double sellFee;

	/**
	 * 价格
	 */
	private Double price;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
