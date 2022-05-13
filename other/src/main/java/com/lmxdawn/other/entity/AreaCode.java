package com.lmxdawn.other.entity;

import lombok.Data;
import java.util.Date;

@Data
public class AreaCode {

	/**
	 * null
	 */
	private Long id;

	/**
	 * 语言
	 */
	private String lang;

	/**
	 * 地区名字
	 */
	private String name;

	/**
	 * 地区编号
	 */
	private Integer code;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

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
