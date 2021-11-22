package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionHot {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

	/**
	 * 状态（0：禁用，1：正常）
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
