package com.lmxdawn.wallet.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Coin {

	/**
	 * 币种ID自增
	 */
	private Long id;

	/**
	 * 币种别名
	 */
	private String name;

	/**
	 * 币种全称
	 */
	private String coinName;

	/**
	 * 币种单位
	 */
	private String symbol;

	/**
	 * 币种精度
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
