/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 商品统计Entity
 * @author 段文昌
 * @version 2015-11-14
 */
public class ScoGoodsStatistic extends DataEntity<ScoGoodsStatistic> {

	private static final long serialVersionUID = 1L;
	private String goodsTreeId;		// 目录ID
	private String goodsTreeName;		// 目录名称

	private Integer numbers;	//统计数量

	public ScoGoodsStatistic() {
		super();
	}

	public ScoGoodsStatistic(String id){
		super(id);
	}

	public String getGoodsTreeId() {
		return goodsTreeId;
	}

	public void setGoodsTreeId(String goodsTreeId) {
		this.goodsTreeId = goodsTreeId;
	}

	public String getGoodsTreeName() {
		return goodsTreeName;
	}

	public void setGoodsTreeName(String goodsTreeName) {
		this.goodsTreeName = goodsTreeName;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}

	@Override
	public String toString() {
		return "ScoGoodsStatistic{" +
				"goodsTreeId='" + goodsTreeId + '\'' +
				", goodsTreeName='" + goodsTreeName + '\'' +
				", numbers=" + numbers +
				'}';
	}
}