package com.lmxdawn.user.entity;

import lombok.Data;

@Data
public class MemberBillCategory {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 标题
	 */
	private String title;
}
