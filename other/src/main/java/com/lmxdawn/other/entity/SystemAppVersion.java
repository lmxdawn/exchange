package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SystemAppVersion {

	/**
	 * null
	 */
	private Long id;

	/**
	 * 更新类型（ 1：全量更新，2：增量更新）
	 */
	private Integer type;

	/**
	 * 平台（1：苹果，2：安卓）
	 */
	private Integer platform;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 下载链接
	 */
	private String url;

	/**
	 * 是否强制更新（ 0：否 ，1：是）
	 */
	private Integer isForceUpdate;

	/**
	 * 是否清空缓存（ 0：否 ，1：是）
	 */
	private Integer isClearCache;

	/**
	 * 更新日志
	 */
	private String log;

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
