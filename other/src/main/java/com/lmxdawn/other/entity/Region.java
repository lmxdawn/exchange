package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Region {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 上级省份ID
	 */
	private Integer provinceId;

	/**
	 * 上级城市ID
	 */
	private Integer cityId;

	/**
	 * 上级县ID
	 */
	private Integer countyId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否是叶子节点（0：不是，1是）
	 */
	private Integer isLeaf;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
