/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * 验收单商品列表Entity
 * @author 段文昌
 * @version 2015-11-26
 */
public class ScoGoodsAcceptance extends DataEntity<ScoGoodsAcceptance> {
	
	private static final long serialVersionUID = 1L;
	private String arId;		// 验收单编号
	private ScoGoods goodsId;		// 商品ID
	private ScoPrints printsId;		// 商品ID
	private Integer numbers;		// 数量
	private BigDecimal totalPrice;		// 总价
	private BigDecimal discountPercent;		// 折扣百分比
	
	private List<ScoSerTreeElement> elements;
	
	private String type;

	
	public ScoGoodsAcceptance() {
		super();
		this.numbers=1;
	}

	public ScoGoodsAcceptance(String id){
		super(id);
	}

	@Length(min=1, max=64, message="验收单编号长度必须介于 1 和 64 之间")
	public String getArId() {
		return arId;
	}

	public void setArId(String arId) {
		this.arId = arId;
	}
	
	public ScoGoods getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(ScoGoods goodsId) {
		this.goodsId = goodsId;
	}
	
	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}


	public BigDecimal getTotalPrice() {
		if(this.goodsId!=null&&this.goodsId.getAgrtPrice()!=null){
			totalPrice = this.goodsId.getAgrtPrice().multiply(new BigDecimal(numbers));
		}
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
		if( this.goodsId.getAgrtPrice()!=null){
			discountPercent = this.goodsId.getAgrtPrice().divide(this.goodsId.getNormalPrice(), mc);
		}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ScoSerTreeElement> getElements() {
		return elements;
	}

	public void setElements(List<ScoSerTreeElement> elements) {
		this.elements = elements;
	}
	
	
}