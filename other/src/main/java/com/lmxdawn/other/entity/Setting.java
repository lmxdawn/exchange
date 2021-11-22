package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Setting {

	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 模块（1：运营配置，2：文件存储相关，3：华为云短信）
	 */
	private Integer module;

	/**
	 * 配置的key
	 */
	private String key;

	/**
	 * 描述
	 */
	private String describe;

	/**
	 * 具体值
	 */
	private String value;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
