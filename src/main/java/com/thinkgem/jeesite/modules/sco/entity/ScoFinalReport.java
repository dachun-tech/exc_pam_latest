/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.sys.entity.Office;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 结算单表维护Entity
 * @author 段文昌
 * @version 2015-12-03
 */
public class ScoFinalReport extends DataEntity<ScoFinalReport> {
	
	private static final long serialVersionUID = 1L;
	private String itemName;			//项目名称
	private String serialNumber;		// 结算单编号
	private Office office;		// 采购机构ID
	private String officeName;		// 采购机构
	private String department;		// 单位编号
	private String invoicenum;		// 发票编号
	private String identifier;		// 车架号或发动机号
	private String identifierType;		// 车架号或发动机号类型，0-车架号，1-发动机号
	private Date buyDate;		// 采购日期
	private String linkman;		// 联系人
	private String mobile;		// 联系人手机
	private ScoSerTree serTreeId;		// 目录
	private ScoSerTree subId;		// 二级目录id
	private ScoSerTree thirdId;		// 三级目录id
	private String type;		// 类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发
	private String state;		// 审核状态,0-待审，1-未完成，2-审核通过，3-默认通过，4-审核未通过
	private Integer serNumbers;		// 数量
	private BigDecimal subtotal = new BigDecimal(0);		// 月份小计
	private String year;		// 年
	private String month;		// 月
	private String specs;		//规格
	private String pages;		//页数
	private String quantity;	//数量
	private BigDecimal otherPrice; 	//其它费用
	

	private BigDecimal minSubtotal;
	private BigDecimal maxSubtotal;
	
	
	private ScoSerTree paperTypeId;
	private String  description;
	private String units;
	private BigDecimal price;
	private String picUrl;
	

	private String[] descs;
	
	public ScoFinalReport() {
		super();
		this.identifierType = "0";
	}

	public ScoFinalReport(String id){
		super(id);
	}

	@Length(min=1, max=64, message="结算单编号长度必须介于 1 和 64 之间")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
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
	
	@Length(min=1, max=64, message="单位编号长度必须介于 1 和 64 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=128, message="发票编号长度必须介于 0 和 128 之间")
	public String getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}
	
	@Length(min=0, max=128, message="车架号或发动机号长度必须介于 0 和 128 之间")
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	@Length(min=0, max=128, message="车架号或发动机号类型，0-车架号，1-发动机号长度必须介于 0 和 128 之间")
	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	@Length(min=0, max=128, message="联系人长度必须介于 0 和 128 之间")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Length(min=0, max=128, message="联系人手机长度必须介于 0 和 128 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	@NotNull(message="")
	public ScoSerTree getSerTreeId() {
		return serTreeId;
	}

	public void setSerTreeId(ScoSerTree serTreeId) {
		this.serTreeId = serTreeId;
	}

//	@NotNull(message="")
	public ScoSerTree getSubId() {
		return subId;
	}

	public void setSubId(ScoSerTree subId) {
		this.subId = subId;
	}

//	@NotNull(message="")
	public ScoSerTree getThirdId() {
		return thirdId;
	}

	public void setThirdId(ScoSerTree thirdId) {
		this.thirdId = thirdId;
	}
	
	@Length(min=1, max=32, message="类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSerNumbers() {
		return serNumbers;
	}

	public void setSerNumbers(Integer serNumbers) {
		this.serNumbers = serNumbers;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public ScoSerTree getPaperTypeId() {
		return paperTypeId;
	}

	public void setPaperTypeId(ScoSerTree paperTypeId) {
		this.paperTypeId = paperTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String[] getDescs() {
		return descs;
	}

	public void setDescs(String[] descs) {
		this.descs = descs;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
}