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
 * 验收单商品列表Entity
 * @author 段文昌
 * @version 2015-11-26
 */
public class ScoPrintsAcceptance extends DataEntity<ScoPrintsAcceptance> {
	
	private static final long serialVersionUID = 1L;
	private String arId;		// 验收单编号
	private ScoPrints printsId;		// 商品ID
	private Integer numbers;		// 数量
	private BigDecimal totalPrice;		// 总价
	private BigDecimal discountPercent;		// 折扣百分比

	
	public ScoPrintsAcceptance() {
		super();
		this.numbers=1;
	}

	public ScoPrintsAcceptance(String id){
		super(id);
	}

	@Length(min=1, max=64, message="验收单编号长度必须介于 1 和 64 之间")
	public String getArId() {
		return arId;
	}

	public void setArId(String arId) {
		this.arId = arId;
	}
	
	
	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}


	public BigDecimal getTotalPrice() {
		if(this.printsId!=null&&this.printsId.getAgrtPrice()!=null){
			totalPrice = this.printsId.getAgrtPrice().multiply(new BigDecimal(numbers));
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
		if(discountPercent!=null){
			discountPercent.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public ScoPrints getPrintsId() {
		return printsId;
	}

	public void setPrintsId(ScoPrints printsId) {
		this.printsId = printsId;
	}
	
	
}