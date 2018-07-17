/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * 验收单统计Entity
 * @author thinkgem
 * @version 2015-11-12
 */
public class ScoAcceptanceStatistic extends DataEntity<ScoAcceptanceStatistic> {

	private static final long serialVersionUID = 1L;
	private String year;		// 年
	private String month;		// 月
	private Integer receivingNumbers;		// 验收单数量
	private Integer goodsNumbers;		// 商品数量
	private BigDecimal subtotal = new BigDecimal(0);		// 月份小计
	private String type;
	

	/** 统计使用 */
	private String goodsTreeId;		// 目录ID
	private String subId;		// 二级目录id
	
	

    public ScoAcceptanceStatistic() {
        super();
        Calendar a=Calendar.getInstance();
        this.year = Integer.toString(a.get(Calendar.YEAR)); //当前年
    }

    public ScoAcceptanceStatistic(String year){
        Calendar a=Calendar.getInstance();
        this.year = Integer.toString(a.get(Calendar.YEAR)); //当前年
    }

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getReceivingNumbers() {
		return receivingNumbers;
	}

	public void setReceivingNumbers(Integer receivingNumbers) {
		this.receivingNumbers = receivingNumbers;
	}

	public Integer getGoodsNumbers() {
		return goodsNumbers;
	}

	public void setGoodsNumbers(Integer goodsNumbers) {
		this.goodsNumbers = goodsNumbers;
	}

	public BigDecimal getSubtotal() {

		if(subtotal!=null){
			subtotal=subtotal.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return subtotal;
	
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "ScoAcceptanceStatistic{" +
				"year='" + year + '\'' +
				", month='" + month + '\'' +
				", receivingNumbers=" + receivingNumbers +
				", goodsNumbers=" + goodsNumbers +
				", subtotal=" + subtotal +
				'}';
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGoodsTreeId() {
		return goodsTreeId;
	}

	public void setGoodsTreeId(String goodsTreeId) {
		this.goodsTreeId = goodsTreeId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	
}