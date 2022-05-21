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
	 * 用户ID
	 */
	private Long memberId;

	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	 * 手续费
	 */
	private Double fee;

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
