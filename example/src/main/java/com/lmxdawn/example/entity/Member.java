package com.lmxdawn.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Member {

	/**
	 * 用户ID
	 */
	private Long uid;

	/**
	 * 手机号
	 */
	private String tel;

	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * 用户昵称
	 */
	private String name;

	/**
	 * 用户头像
	 */
	private String avatar;

	/**
	 * 个性签名
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifiedTime;
}
