package com.lmxdawn.user.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MemberCoin {

	/**
	 * null
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long memberId;

	/**
	 * 币种ID
	 */
	private Long coinId;

	/**
	 * 可用余额
	 */
	private Double balance;

	/**
	 * 冻结余额
	 */
	private Double frozenBalance;

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
