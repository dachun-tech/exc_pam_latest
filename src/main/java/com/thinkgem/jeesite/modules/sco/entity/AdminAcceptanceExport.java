/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 物资类验收单统计导出Entity
 * @author jeesite
 * @version 2015-11-12
 */
public class AdminAcceptanceExport extends DataEntity<AdminAcceptanceExport> {

	private static final long serialVersionUID = 1L;

	private String suppliers;		//供应商
	private String units;		//采购单位
	private List<RowData> rowDatas; //数据行

	@ExcelField(title = "供应商", align = 2, sort = 20)
	public String getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(String suppliers) {
		this.suppliers = suppliers;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public List<RowData> getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(List<RowData> rowDatas) {
		this.rowDatas = rowDatas;
	}

	public class RowData{
		private String month;		// 结束
		private Integer receiving;		// 验收单数量/总验收单数量
		private Integer goodsCount;		// 商品数量/总商品数量
		private BigDecimal totalAmt;		// 采购金额/采购总金额


		@ExcelField(title = "统计时间", align = 2, sort = 25)
		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		@ExcelField(title = "验收单数量", align = 2, sort = 30)
		public Integer getReceiving() {
			return receiving;
		}

		public void setReceiving(Integer receiving) {
			this.receiving = receiving;
		}
		@ExcelField(title = "商品总数量", align = 2, sort = 35)
		public Integer getGoodsCount() {
			return goodsCount;
		}

		public void setGoodsCount(Integer goodsCount) {
			this.goodsCount = goodsCount;
		}
		@ExcelField(title = "采购总金额", align = 2, sort = 40)
		public BigDecimal getTotalAmt() {

			if(totalAmt!=null){

				DecimalFormat df = new DecimalFormat("### ##0.00") ;
				return BigDecimal.valueOf(Double.valueOf(df.format(totalAmt)));
				
				
//				totalAmt = totalAmt.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
			}
			return totalAmt;
		
		}

		public void setTotalAmt(BigDecimal totalAmt) {
			this.totalAmt = totalAmt;
		}

		@Override
		public String toString() {
			return "RowData{" +
					"month='" + month + '\'' +
					", receiving=" + receiving +
					", goodsCount=" + goodsCount +
					", totalAmt=" + totalAmt +
					'}';
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