/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 商品列表Entity
 * @author 段文昌
 * @version 2015-11-14
 */
public class ScoGoods extends DataEntity<ScoGoods> {
	
	private static final long serialVersionUID = 1L;
	private String goodsTreeId;		// 目录ID
	private String goodsTreeName;		// 目录名称
	private String subName;		// 二级目录名称
	private String subId;		// 二级目录id
	private String name;	//商品名称
	private String brand;		// 品牌
	private String goodsModel;		// 商品型号
	private BigDecimal normalPrice;		// 挂牌价
	private BigDecimal agrtPrice;		// 协议价
	private String units;		// 计量单位
	private String specification;		// 规格参数
	private String barCode;		// 条形码
	private Boolean isExistCode;		// 是否有条形码，0-有，1-无
	private String picUrl;		// 商品url地址
	private String state;		// 审核状态,0-待审，1-审核通过，2-默认通过，3-审核未通过
	private String directories;	//拥有目录
	private String goodsNo;	//商品编号
	
	

	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	
	public ScoGoods() {
		super();
		isExistCode = true; //默认选中有条形码
//		state = "0"; //默认待审
	}

	public ScoGoods(String id){
		super(id);
	}

	@Length(min=1, max=64, message="目录长度必须介于 1 和 64 之间")
	public String getGoodsTreeId() {
		return goodsTreeId;
	}

	public void setGoodsTreeId(String goodsTreeId) {
		this.goodsTreeId = goodsTreeId;
	}
	
	@Length(min=0, max=64, message="二级目录名称长度必须介于 0 和 64 之间")
//	@ExcelField(title = "二级目录", align = 2, sort = 24)
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	@Length(min=0, max=64, message="二级目录id长度必须介于 0 和 64 之间")
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	@Length(min=0, max=64, message="品牌长度必须介于 0 和 64 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=128, message="商品型号长度必须介于 0 和 128 之间")
	@ExcelField(title = "商品型号", align = 2, sort = 6)
	public String getGoodsModel() {
		return goodsModel;
	}

	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}
	@ExcelField(title = "挂牌价", align = 2, sort = 15)
	public BigDecimal getNormalPrice() {
		if(normalPrice!=null){
			normalPrice=	normalPrice.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}
	@ExcelField(title = "协议价", align = 2, sort = 18)
	public BigDecimal getAgrtPrice() {
		if(agrtPrice!=null){
			agrtPrice = agrtPrice.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		}
		return agrtPrice;
	}

	public void setAgrtPrice(BigDecimal agrtPrice) {
		this.agrtPrice = agrtPrice;
	}
	
	@Length(min=0, max=64, message="计量单位长度必须介于 0 和 64 之间")
	@ExcelField(title = "计量单位", align = 2, sort = 12)
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@ExcelField(title = "商品规格", align = 2, sort = 9)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Length(min=0, max=64, message="条形码长度必须介于 0 和 64 之间")
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public Boolean getIsExistCode() {
		return isExistCode;
	}

	public void setIsExistCode(Boolean isExistCode) {
		this.isExistCode = isExistCode;
	}
	
	@Length(min=0, max=350, message="商品url地址长度必须介于 0 和 350 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=2, message="审核状态,0-待审，1-未完成，2-审核通过，3-默认通过，4-审核未通过长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDirectories() {
		return directories;
	}

	public void setDirectories(String directories) {
		this.directories = directories;
	}
	@ExcelField(title = "商品名称", align = 2, sort = 3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title = "目录", align = 2, sort = 2)
	public String getGoodsTreeName() {
		return goodsTreeName;
	}

	public void setGoodsTreeName(String goodsTreeName) {
		this.goodsTreeName = goodsTreeName;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Override
	@ExcelField(title = "供应商", align = 2, sort = 1, value = "createBy.name")
	public User getCreateBy() {
		return super.getCreateBy();
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
	public String toString() {
		return "ScoGoods{" +
				"goodsTreeId='" + goodsTreeId + '\'' +
				", goodsTreeName='" + goodsTreeName + '\'' +
				", subName='" + subName + '\'' +
				", subId='" + subId + '\'' +
				", name='" + name + '\'' +
				", brand='" + brand + '\'' +
				", goodsModel='" + goodsModel + '\'' +
				", normalPrice='" + normalPrice + '\'' +
				", agrtPrice='" + agrtPrice + '\'' +
				", units='" + units + '\'' +
				", specification='" + specification + '\'' +
				", barCode='" + barCode + '\'' +
				", isExistCode='" + isExistCode + '\'' +
				", picUrl='" + picUrl + '\'' +
				", state='" + state + '\'' +
				", goodsNo='" + goodsNo + '\'' +
				", directories='" + directories + '\'' +
				'}';
	}
}