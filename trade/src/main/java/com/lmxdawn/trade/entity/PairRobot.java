package com.lmxdawn.trade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class PairRobot {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 交易对ID
	 */
	private Long pairId;

	/**
	 * 交易币种ID
	 */
	private Long tradeCoinId;

	/**
	 * 计价币种ID
	 */
	private Long coinId;

	/**
	 * 小写的交易对名称
	 */
	private String lowerCoinName;

	/**
	 * 大写的交易对名称
	 */
	private String upperCoinName;

	/**
	 * 最低交易量
	 */
	private Double minAmount;

	/**
	 * 交易量随机数范围 1%概率
	 */
	private Double randRange0;

	/**
	 * 交易量随机数范围 9%概率
	 */
	private Double randRange1;

	/**
	 * 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
	 */
	private Double randRange2;

	/**
	 * 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
	 */
	private Double randRange3;

	/**
	 * 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
	 */
	private Double randRange4;

	/**
	 * 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
	 */
	private Double randRange5;

	/**
	 * 交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率
	 */
	private Double randRange6;

	/**
	 * 价格精度
	 */
	private Integer pricePrecision;

	/**
	 * 交易量精度
	 */
	private Integer amountPrecision;

	/**
	 * 买盘最高价与卖盘最低价相差超过该值
	 */
	private Double maxSubPrice;

	/**
	 * 初始订单量
	 */
	private Integer initOrderCount;

	/**
	 * 价格变化步长(0.01 = 1%)
	 */
	private Double priceStepRate;

	/**
	 * 行情请求间隔时间（单位毫秒 1000 = 1秒）
	 */
	private Integer runTime;

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
