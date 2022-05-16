package com.lmxdawn.other.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CodeTemplate {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 平台（1：华为云短信，2：阿里云短信，3：邮件）
	 */
	private Integer platform;

	/**
	 * 场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码）
	 */
	private Integer scene;

	/**
	 * 模板ID
	 */
	private String templateId;

	/**
	 * 模板参数
	 */
	private String templateParas;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态（0：关闭验证，1：开启验证）
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
