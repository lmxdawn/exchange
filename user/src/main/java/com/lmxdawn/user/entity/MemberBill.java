package com.lmxdawn.user.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MemberBill {

	/**
	 * 自增ID
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
	 * 类别（member_bill_category表）
	 */
	private String category;

	/**
	 * 类型（1：收入，2：支出）
	 */
	private Integer type;

	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 手续费
	 */
	private Double fee;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
