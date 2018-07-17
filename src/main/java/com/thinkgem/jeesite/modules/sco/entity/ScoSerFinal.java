/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 结算单与服务类型表维护Entity
 * @author 段文昌
 * @version 2015-12-03
 */
public class ScoSerFinal extends DataEntity<ScoSerFinal> {
	
	private static final long serialVersionUID = 1L;
	private String reportId;		// 结算单编号
	private String serId;		// 服务ID
	private Integer numbers;		// 数量
	private String serType;		// 类型，按服务序列定义，例如：零配件-1，工时-2，轮胎-3，机油-4
	private BigDecimal totalPrice;		// 总价
	private BigDecimal discountPercent;		// 折扣百分比

	private ScoSparepart sparepart; //第一类
	private ScoLabor labor; //第二类
	private ScoTyre tyre; //第三类
	private ScoOil oil; //第四类
	private ScoPrints scoPrints; //第四类

	private BigDecimal normalPrice;		// 挂牌价
	private BigDecimal agrtPrice;		// 协议价
	
	public ScoSerFinal() {
		super();
		this.numbers=1;
	}

	public ScoSerFinal(String id){
		super(id);
	}

	@Length(min=1, max=64, message="结算单编号长度必须介于 1 和 64 之间")
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Length(min=0, max=64, message="服务ID长度必须介于 0 和 64 之间")
	public String getSerId() {
		return serId;
	}

	public void setSerId(String serId) {
		this.serId = serId;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}
	
	@Length(min=1, max=32, message="类型，按服务序列定义，例如：零配件-1，工时-2，轮胎-3，机油-4长度必须介于 1 和 32 之间")
	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public BigDecimal getTotalPrice() {
		if(this.getAgrtPrice()!=null){
			totalPrice = this.getAgrtPrice().multiply(new BigDecimal(numbers));
		}
		if(totalPrice!=null){
			totalPrice.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getDiscountPercent() {
		MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
		if(this.getAgrtPrice()!=null&&this.getNormalPrice()!=null){
			discountPercent = this.getAgrtPrice().divide(this.getNormalPrice(), mc);
		}
		if(discountPercent!=null){
			discountPercent.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public ScoSparepart getSparepart() {
		return sparepart;
	}

	public void setSparepart(ScoSparepart sparepart) {
		this.sparepart = sparepart;
	}

	public ScoLabor getLabor() {
		return labor;
	}

	public void setLabor(ScoLabor labor) {
		this.labor = labor;
	}

	public ScoTyre getTyre() {
		return tyre;
	}

	public void setTyre(ScoTyre tyre) {
		this.tyre = tyre;
	}

	public ScoOil getOil() {
		return oil;
	}

	public void setOil(ScoOil oil) {
		this.oil = oil;
	}

	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public BigDecimal getAgrtPrice() {
		return agrtPrice;
	}

	public void setAgrtPrice(BigDecimal agrtPrice) {
		this.agrtPrice = agrtPrice;
	}

	public ScoPrints getScoPrints() {
		return scoPrints;
	}

	public void setScoPrints(ScoPrints scoPrints) {
		this.scoPrints = scoPrints;
	}
	
	
}