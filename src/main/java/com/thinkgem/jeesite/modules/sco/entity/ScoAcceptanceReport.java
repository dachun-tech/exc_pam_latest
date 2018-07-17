/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 验收单Entity
 * @author thinkgem
 * @version 2015-11-12
 */
public class ScoAcceptanceReport extends DataEntity<ScoAcceptanceReport> {
	
	private static final long serialVersionUID = 1L;
	private String serialNumber;		// 验收单编号
	private Office office;		// 采购机构ID
	private String officeName;		// 采购机构
	private String invoicenum;		// 发票编号
	private Date buyDate;		// 采购日期
	private String department;		// 单位编号
	private String linkman;		//联系人
	private String mobile;		//联系人电话
	private String state;		// 审核状态,0-待审，1-未完成，2-审核通过，3-默认通过，4-审核未通过
	private Integer goodsNumbers;		// 商品数量
	private BigDecimal subtotal = new BigDecimal(0);		// 月份小计
	
	private String type;
	 
	
	public ScoAcceptanceReport() {
		super();
	}

	public ScoAcceptanceReport(String id){
		super(id);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@NotNull(message="采购机构ID不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=64, message="采购机构长度必须介于 0 和 64 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=128, message="发票编号长度必须介于 0 和 128 之间")
	public String getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	@Length(min=1, max=64, message="单位编号长度必须介于 1 和 64 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=2, message="审核状态,0-未完成，1-待审，2-审核通过，3-默认通过，4-审核未通过长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getGoodsNumbers() {
		return goodsNumbers;
	}

	public void setGoodsNumbers(Integer goodsNumbers) {
		this.goodsNumbers = goodsNumbers;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}