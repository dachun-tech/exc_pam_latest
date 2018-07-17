/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.List;

/**
 * 服务类结算单统计导出Entity
 * @author jeesite
 * @version 2015-11-12
 */
public class AdminFinalExport extends DataEntity<AdminFinalExport> {

	private static final long serialVersionUID = 1L;

	private String suppliers;		//供应商
	private List<RowData> rowDatas; //数据行

	@ExcelField(title = "供应商", align = 2, sort = 20)
	public String getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(String suppliers) {
		this.suppliers = suppliers;
	}
	public List<RowData> getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(List<RowData> rowDatas) {
		this.rowDatas = rowDatas;
	}

	public class RowData{
		private String month;		// 月份
		private Integer finalNumbers;		// 结算单数量/总结算单数量
		private BigDecimal sparepartAmt = new BigDecimal(0);//零配件金额 (第一)
		private BigDecimal laborAmt = new BigDecimal(0);//工时金额 (第二)
		private BigDecimal tyreAmt = new BigDecimal(0);//轮胎金额 (第三)
		private BigDecimal oilAmt = new BigDecimal(0);//机油金额 (第四)
		private BigDecimal totalAmt;		// 采购金额/采购总金额

		@ExcelField(title = "月份", align = 2, sort = 25)
		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		@ExcelField(title = "采购总金额", align = 2, sort = 40)
		public BigDecimal getTotalAmt() {
			if(totalAmt!=null){
				totalAmt = totalAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return totalAmt;
		}

		public void setTotalAmt(BigDecimal totalAmt) {
			this.totalAmt = totalAmt;
		}

		public Integer getFinalNumbers() {
			return finalNumbers;
		}

		public void setFinalNumbers(Integer finalNumbers) {
			this.finalNumbers = finalNumbers;
		}

		public BigDecimal getSparepartAmt() {


			if(sparepartAmt!=null){
				sparepartAmt = sparepartAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return sparepartAmt;
		
		
		}

		public void setSparepartAmt(BigDecimal sparepartAmt) {
			this.sparepartAmt = sparepartAmt;
		}

		public BigDecimal getLaborAmt() {
			if(laborAmt!=null){
				laborAmt = laborAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return laborAmt;
		}

		public void setLaborAmt(BigDecimal laborAmt) {
			this.laborAmt = laborAmt;
		}

		public BigDecimal getTyreAmt() {
			if(tyreAmt!=null){
				tyreAmt = tyreAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return tyreAmt;
		}

		public void setTyreAmt(BigDecimal tyreAmt) {
			this.tyreAmt = tyreAmt;
		}

		public BigDecimal getOilAmt() {

			if(oilAmt!=null){
				oilAmt = oilAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return oilAmt;
		
		}

		public void setOilAmt(BigDecimal oilAmt) {
			this.oilAmt = oilAmt;
		}
	}

	@Override
	public String toString() {
		return "AdminAcceptanceExport{" +
				"suppliers='" + suppliers + '\'' +
				", rowDatas=" + rowDatas +
				'}';
	}
}