package com.lmxdawn.wallet.entity;

import lombok.Data;
import java.util.Date;

@Data
public class CoinProtocol {

	/**
	 * 协议ID自增
	 */
	private Long id;

	/**
	 * 协议名称
	 */
	private String name;

	/**
	 * rpc主机
	 */
	private String rpcHost;

	/**
	 * rpc登录用户
	 */
	private String rpcUser;

	/**
	 * rpc登录密码
	 */
	private String rpcPwd;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

	/**
	 * 状态（1：关闭，2：开启）
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
