/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 印刷品Entity
 * @author oddbitter
 * @version 2016-04-30
 */
public class ScoPrints extends DataEntity<ScoPrints> {
	
	private static final long serialVersionUID = 1L;
	private String printTreeId;		// 目录
	private String subName;		// 二级目录名称
	private ScoSerTree serTreeId;		// 目录
	private ScoSerTree subId;		// 二级目录id
	private ScoSerTree thirdId;		// 三级目录id
	private ScoSerTree paperTreeId;		// 纸张目录id
	private String printNo;		// 商品编号
	private String printName;		// 商品名称
	private String specification;		// 规格参数
	private String printPageSize;		// 页数
	private String paperTypeId;		// 纸张种类id
	private String paperTypeName;		// 纸张种类id
	private String description;		// 工艺描述
	private String price;		// 单价
	private String units;		// 计量单位
	private String picUrl;		// 商品url地址
	private String state;		// 审核状态,0-待审，1-审核通过，2-默认通过，3-审核未通过
	
	private String type;
	

	private BigDecimal normalPrice;		// 挂牌价
	private BigDecimal agrtPrice;		// 协议价
	
	

	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	
	private String[] descs;
	
	public ScoPrints() {
		super();
	}

	public ScoPrints(String id){
		super(id);
	}

	
	@Length(min=0, max=64, message="商品编号长度必须介于 0 和 64 之间")
	public String getPrintNo() {
		return printNo;
	}

	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}
	
	@Length(min=1, max=128, message="商品名称长度必须介于 1 和 128 之间")
	@ExcelField(title = "商品名称", align = 2, sort = 1)
	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}
	
	@Length(min=0, max=100, message="规格参数长度必须介于 0 和 100 之间")
	@ExcelField(title = "规格参数", align = 2, sort = 2)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Length(min=0, max=100, message="页数长度必须介于 0 和 100 之间")
	@ExcelField(title = "页数", align = 2, sort = 3)
	public String getPrintPageSize() {
		return printPageSize;
	}

	public void setPrintPageSize(String printPageSize) {
		this.printPageSize = printPageSize;
	}

	@Length(min=0, max=64, message="纸张种类id长度必须介于 0 和 64 之间")
	public String getPaperTypeId() {
		return paperTypeId;
	}

	public void setPaperTypeId(String paperTypeId) {
		this.paperTypeId = paperTypeId;
	}
	
	@ExcelField(title = "工艺描述", align = 2, sort = 4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title = "单价", align = 2, sort = 5)
	public String getPrice() {

		if(price!=null){
			BigDecimal decimal = BigDecimal.valueOf(Double.valueOf(price));
			if(decimal!=null){
//				decimal = decimal.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
//				return decimal.doubleValue()+"";
				DecimalFormat df = new DecimalFormat("### ##0.00") ;
				return df.format(decimal);
			}

		}
		return price;
	
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	@Length(min=0, max=64, message="计量单位长度必须介于 0 和 64 之间")
	@ExcelField(title = "单位", align = 2, sort = 6)
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	@Length(min=0, max=350, message="商品url地址长度必须介于 0 和 350 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=2, message="审核状态,0-待审，1-审核通过，2-默认通过，3-审核未通过长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ScoSerTree getSerTreeId() {
		return serTreeId;
	}

	public void setSerTreeId(ScoSerTree serTreeId) {
		this.serTreeId = serTreeId;
	}

	public ScoSerTree getSubId() {
		return subId;
	}

	public void setSubId(ScoSerTree subId) {
		this.subId = subId;
	}

	public ScoSerTree getThirdId() {
		return thirdId;
	}

	public void setThirdId(ScoSerTree thirdId) {
		this.thirdId = thirdId;
	}

	public ScoSerTree getPaperTreeId() {
		return paperTreeId;
	}

	public void setPaperTreeId(ScoSerTree paperTreeId) {
		this.paperTreeId = paperTreeId;
	}

	public String getPrintTreeId() {
		return printTreeId;
	}

	public void setPrintTreeId(String printTreeId) {
		this.printTreeId = printTreeId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String[] getDescs() {
		return descs;
	}

	public void setDescs(String[] descs) {
		this.descs = descs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public BigDecimal getAgrtPrice() {
		return agrtPrice;
	}

	public void setAgrtPrice(BigDecimal agrtPrice) {
		this.agrtPrice = agrtPrice;
	}

	public String getPaperTypeName() {
		return paperTypeName;
	}

	public void setPaperTypeName(String paperTypeName) {
		this.paperTypeName = paperTypeName;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	
	@Override
	@ExcelField(title = "供应商", align = 2, sort = 1, value = "createBy.name")
	public User getCreateBy() {
		return super.getCreateBy();
	}
}