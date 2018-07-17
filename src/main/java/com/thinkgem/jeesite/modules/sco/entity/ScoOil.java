/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 机油表维护Entity
 * @author 段文昌
 * @version 2015-12-03
 */
public class ScoOil extends DataEntity<ScoOil> {
	
	private static final long serialVersionUID = 1L;
	private ScoSerTree serTreeId;		// 目录
	private ScoSerTree subId;		// 二级目录id
	private String name;		// 型号名称
	private String qualityGrade;		// 质量等级
	private String viscosityGrade;		// 粘度等级
	private String capacityn;		// 容量
	private BigDecimal normalPrice;		// 挂牌价
	private BigDecimal agrtPrice;		// 协议价
	private String type;		// 类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发
	private Integer state;		// 审核状态,0-待审，1-未完成，2-审核通过，3-默认通过，4-审核未通过
	private String commitment; 	//服务承诺
	
	private String kind;
	

	/**
	 * 协议价的
	 */
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	
	public ScoOil() {
		super();
	}

	public ScoOil(String id){
		super(id);
	}

//	@NotNull(message="")
	@ExcelField(title = "品牌", align = 2, sort = 6, value = "serTreeId.name")
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

//	@Length(min=0, max=64, message="型号名称长度必须介于 0 和 64 之间")
	@ExcelField(title = "商品名称", align = 2, sort = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	@Length(min=0, max=64, message="质量等级长度必须介于 0 和 64 之间")
	@ExcelField(title = "质量等级", align = 2, sort = 2)
	public String getQualityGrade() {
		return qualityGrade;
	}

	public void setQualityGrade(String qualityGrade) {
		this.qualityGrade = qualityGrade;
	}
	
//	@Length(min=0, max=64, message="粘度等级长度必须介于 0 和 64 之间")
	@ExcelField(title = "粘度等级", align = 2, sort = 3)
	public String getViscosityGrade() {
		return viscosityGrade;
	}

	public void setViscosityGrade(String viscosityGrade) {
		this.viscosityGrade = viscosityGrade;
	}
	
//	@Length(min=0, max=64, message="容量长度必须介于 0 和 64 之间")
	@ExcelField(title = "容量长度", align = 2, sort = 4)
	public String getCapacityn() {
		return capacityn;
	}

	public void setCapacityn(String capacityn) {
		this.capacityn = capacityn;
	}
	
	@NotNull(message="挂牌价不能为空")
	@ExcelField(title = "挂牌价", align = 2, sort = 5)
	public BigDecimal getNormalPrice() {
		if(normalPrice!=null){
//			normalPrice = normalPrice.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);

			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			return BigDecimal.valueOf(Double.valueOf(df.format(normalPrice)));
		}
		return normalPrice;
	
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}
	
	@NotNull(message="协议价不能为空")
	@ExcelField(title = "协议价", align = 2, sort = 6)
	public BigDecimal getAgrtPrice() {
		if(agrtPrice!=null){
//			agrtPrice = agrtPrice.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);

			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			return BigDecimal.valueOf(Double.valueOf(df.format(agrtPrice)));
		}
		return agrtPrice;
	}

	public void setAgrtPrice(BigDecimal agrtPrice) {
		this.agrtPrice = agrtPrice;
	}
	
	@Length(min=1, max=32, message="类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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
	@ExcelField(title = "供应商", align = 2, sort = 8, value = "createBy.name")
	public User getCreateBy() {
		return super.getCreateBy();
	}
	
	
}