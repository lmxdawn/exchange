package com.lmxdawn.user.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Member {

	/**
	 * 用户ID
	 */
	private Long memberId;

	/**
	 * 手机号区号
	 */
	private String telAreaCode;

	/**
	 * 手机号
	 */
	private String tel;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * 支付密码
	 */
	private String payPwd;

	/**
	 * 谷歌验证key
	 */
	private String googleKey;

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
