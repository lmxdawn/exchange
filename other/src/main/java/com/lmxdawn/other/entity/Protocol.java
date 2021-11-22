package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Protocol {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 协议的key
	 */
	private String key;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态（0：禁用，1：开启）
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
