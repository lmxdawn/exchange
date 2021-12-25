package com.lmxdawn.market.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KLine {
	/**
	 * 时间
	 */
	private Long time;
	/**
	 * 以基础币种计量的交易量
	 */
	private BigDecimal amount = BigDecimal.ZERO;
	/**
	 * 交易次数
	 */
	private BigDecimal count = BigDecimal.ZERO;
	/**
	 * 本阶段开盘价（前端实际使用上一个的价格）
	 */
	private BigDecimal open = BigDecimal.ZERO;
	/**
	 * 本阶段收盘价
	 */
	private BigDecimal close = BigDecimal.ZERO;
	/**
	 * 本阶段最低价
	 */
	private BigDecimal low = BigDecimal.ZERO;
	/**
	 * 本阶段最高价
	 */
	private BigDecimal high = BigDecimal.ZERO;
	/**
	 * 以报价币种计量的交易量
	 */
	private BigDecimal vol = BigDecimal.ZERO;
}
