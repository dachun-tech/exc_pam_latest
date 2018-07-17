/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * 验收单统计Entity
 * @author thinkgem
 * @version 2015-11-12
 */
public class ScoFinalStatistic extends DataEntity<ScoFinalStatistic> {

	private static final long serialVersionUID = 1L;
	private String year;		// 年
	private String month;		// 月
	private Integer finalNumbers;		// 结算单数量
	private BigDecimal subtotal = new BigDecimal(0);		// 月份小计
	private ScoFinalReport report; //结算单

	private BigDecimal sparepartAmt = new BigDecimal(0);//零配件金额 (第一)
	private BigDecimal laborAmt = new BigDecimal(0);//工时金额 (第二)
	private BigDecimal tyreAmt = new BigDecimal(0);//轮胎金额 (第三)
	private BigDecimal oilAmt = new BigDecimal(0);//机油金额 (第四)
	private BigDecimal printsAmt = new BigDecimal(0);//印刷品金额 (第五)
	private BigDecimal otherAmt = new BigDecimal(0);//其他金额 (第五)

	private BigDecimal totalAmt = new BigDecimal(0); //商品总金额
	private BigDecimal totalFinal = new BigDecimal(0); //结算单总数量
	/** 作为参数接收使用.类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发 */
	private String type; // 类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发
	
	

	private BigDecimal minSubtotal;
	private BigDecimal maxSubtotal;
	
	private String identifier;


	private List<ScoFinalStatistic> statisticList;//单供应商结算单统计列表

    public ScoFinalStatistic() {
        super();
        Calendar a=Calendar.getInstance();
        this.year = Integer.toString(a.get(Calendar.YEAR)); //当前年
    }

    public ScoFinalStatistic(String year){
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

	public Integer getFinalNumbers() {
		return finalNumbers;
	}

	public void setFinalNumbers(Integer finalNumbers) {
		this.finalNumbers = finalNumbers;
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

	public ScoFinalReport getReport() {
		return report;
	}

	public void setReport(ScoFinalReport report) {
		this.report = report;
	}

	public BigDecimal getSparepartAmt() {

		if(sparepartAmt!=null){
			sparepartAmt=sparepartAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return sparepartAmt;
	
	}

	public void setSparepartAmt(BigDecimal sparepartAmt) {
		this.sparepartAmt = sparepartAmt;
	}

	public BigDecimal getLaborAmt() {
		if(laborAmt!=null){
			laborAmt=laborAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return laborAmt;
	}

	public void setLaborAmt(BigDecimal laborAmt) {
		this.laborAmt = laborAmt;
	}

	public BigDecimal getTyreAmt() {
		if(tyreAmt!=null){
			tyreAmt=tyreAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return tyreAmt;
	}

	public void setTyreAmt(BigDecimal tyreAmt) {
		this.tyreAmt = tyreAmt;
	}

	public BigDecimal getOilAmt() {
		if(oilAmt!=null){
			oilAmt=oilAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return oilAmt;
	}

	public void setOilAmt(BigDecimal oilAmt) {
		this.oilAmt = oilAmt;
	}

	public BigDecimal getTotalAmt() {

		if(totalAmt!=null){
			totalAmt=totalAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return totalAmt;
	
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getTotalFinal() {
		return totalFinal;
	}

	public void setTotalFinal(BigDecimal totalFinal) {
		this.totalFinal = totalFinal;
	}

	public List<ScoFinalStatistic> getStatisticList() {
		return statisticList;
	}

	public void setStatisticList(List<ScoFinalStatistic> statisticList) {
		this.statisticList = statisticList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public BigDecimal getPrintsAmt() {
		return printsAmt;
	}

	public void setPrintsAmt(BigDecimal printsAmt) {
		this.printsAmt = printsAmt;
	}

	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}
	
	
	
	
}