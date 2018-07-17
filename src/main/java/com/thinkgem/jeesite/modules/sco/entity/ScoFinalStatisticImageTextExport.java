/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 验收单统计Entity
 * @author thinkgem
 * @version 2015-11-12
 */
public class ScoFinalStatisticImageTextExport  {


	private static final long serialVersionUID = 1L;

	private Integer goodsNumbers;		// 商品数量
	private String subtotal;	// 月份小计
	private String name;    // 姓名
	
	private Office office;		// 采购机构ID
	private String officeName;		// 采购机构
	private String invoicenum;		// 发票编号
	private Date buyDate;		// 采购日期
	private String department;		// 单位编号
	private String state;		// 审核状态,0-待审，1-未完成，2-审核通过，3-默认通过，4-审核未通过
	
	private String identifier;
	private String vehicleType;
	
	public Integer getGoodsNumbers() {
		return goodsNumbers;
	}
	public void setGoodsNumbers(Integer goodsNumbers) {
		this.goodsNumbers = goodsNumbers;
	}
	
	@ExcelField(title="总价", type=1, align=2, sort=6)
	public String getSubtotal() {

		if(subtotal!=null){
			BigDecimal decimal = BigDecimal.valueOf(Double.valueOf(subtotal));
			if(decimal!=null){
				decimal = decimal.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
				return decimal.doubleValue()+"";
			}
		}
		return subtotal;
	
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	@ExcelField(title="供应商", type=1, align=2, sort=2)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	@ExcelField(title="发票编号", type=1, align=2, sort=7)
	public String getInvoicenum() {
		return invoicenum;
	}
	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}
	@ExcelField(title="生成时间", type=1, align=2, sort=1)
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	@ExcelField(title="采购单位编号", type=1, align=2, sort=3)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@ExcelField(title="状态", type=1, align=2, sort=8)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	
	
	

}