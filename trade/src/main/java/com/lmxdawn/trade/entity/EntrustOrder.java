package com.lmxdawn.trade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class EntrustOrder {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long memberId;

	/**
	 * 交易币种ID
	 */
	private Long tradeCoinId;

	/**
	 * 计价币种ID
	 */
	private Long coinId;

	/**
	 * 类型（1：限价，2：市价）
	 */
	private Integer type;

	/**
	 * 方向（1：买入，2：卖出）
	 */
	private Integer direction;

	/**
	 * 价格
	 */
	private Double price;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 已完成数量
	 */
	private Double amountComplete;

	/**
	 * 交易额
	 */
	private Double total;

	/**
	 * 已完成的交易额
	 */
	private Double totalComplete;

	/**
	 * 总的手续费
	 */
	private Double totalFee;

	/**
	 * 状态（1：未完成，2：已完成）
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
