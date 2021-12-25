package com.lmxdawn.trade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class UsdtRate {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 符号
	 */
	private String symbol;

	/**
	 * USDT汇率
	 */
	private Double price;

	/**
	 * 精度
	 */
	private Integer precision;

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
