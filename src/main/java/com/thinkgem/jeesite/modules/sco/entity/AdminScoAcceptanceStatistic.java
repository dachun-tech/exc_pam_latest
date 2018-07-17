/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 验收单统计Entity
 * @author thinkgem
 * @version 2015-11-12
 */
public class AdminScoAcceptanceStatistic extends DataEntity<AdminScoAcceptanceStatistic> {

	private static final long serialVersionUID = 1L;

	private String year;		// 年
	private String beginDate;	// 开日
	private String endDate;		// 终日
	private String month;		// 月
	private Integer receivingNumbers;		// 验收单数量
	private Integer goodsNumbers;		// 商品数量
	private BigDecimal subtotal = new BigDecimal(0);		// 月份小计
	private ScoAcceptanceReport report; //验收单

	private BigDecimal totalGoods = new BigDecimal(0); //商品总数量
	private BigDecimal totalAmt = new BigDecimal(0); //商品总金额
	private BigDecimal totalReceiving = new BigDecimal(0); //验收单总数量

	private List<AdminScoAcceptanceStatistic> statisticList;//单供应商验收单统计列表

	private String adminStaticDepartment;

	/** 统计使用 */
	private String goodsTreeId;		// 目录ID
	private String goodsName;		// 商品名称
	private String subId;		// 二级目录id
	
	private BigDecimal minSubtotal;
	private BigDecimal maxSubtotal;
	
	private String type;

	public AdminScoAcceptanceStatistic() {
		super();
		Calendar a=Calendar.getInstance();
		this.year = Integer.toString(a.get(Calendar.YEAR)); //当前年
	}


	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String str) {
		this.beginDate = str;
	}
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String str) {
		this.endDate = str;
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
			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			return BigDecimal.valueOf(Double.valueOf(df.format(subtotal)));
//			subtotal=subtotal.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public ScoAcceptanceReport getReport() {
		return report;
	}

	public void setReport(ScoAcceptanceReport report) {
		this.report = report;
	}

	public List<AdminScoAcceptanceStatistic> getStatisticList() {
		return statisticList;
	}

	public void setStatisticList(List<AdminScoAcceptanceStatistic> statisticList) {
		this.statisticList = statisticList;
	}


	public String getAdminStaticDepartment() {
		return adminStaticDepartment;
	}

	public void setAdminStaticDepartment(String adminStaticDepartment) {
		this.adminStaticDepartment = adminStaticDepartment;
	}

	public BigDecimal getTotalGoods() {
		return totalGoods;
	}

	public void setTotalGoods(BigDecimal totalGoods) {
		this.totalGoods = totalGoods;
	}

	public BigDecimal getTotalAmt() {
		if(totalAmt!=null){

			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			return BigDecimal.valueOf(Double.valueOf(df.format(totalAmt)));
//			totalAmt=totalAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getTotalReceiving() {
		return totalReceiving;
	}

	public void setTotalReceiving(BigDecimal totalReceiving) {
		this.totalReceiving = totalReceiving;
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

	public BigDecimal getMinSubtotal() {
		return minSubtotal;
	}

	public void setMinSubtotal(BigDecimal minSubtotal) {
		this.minSubtotal = minSubtotal;
	}

	public BigDecimal getMaxSubtotal() {
		return maxSubtotal;
	}

	public void setMaxSubtotal(BigDecimal maxSubtotal) {
		this.maxSubtotal = maxSubtotal;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Override
	public String toString() {
		return "AdminScoAcceptanceStatistic{" +
				"year='" + year + '\'' +
				", month='" + month + '\'' +
				", receivingNumbers=" + receivingNumbers +
				", goodsNumbers=" + goodsNumbers +
				", subtotal=" + subtotal +
				", report=" + report +
				", statisticList=" + statisticList +
				'}';
 	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}