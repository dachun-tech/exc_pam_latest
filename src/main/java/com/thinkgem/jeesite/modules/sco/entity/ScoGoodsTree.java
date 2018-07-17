/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 商品属性Entity
 * @author thinkgem
 * @version 2015-11-10
 */
public class ScoGoodsTree extends TreeEntity<ScoGoodsTree> {
	
	private static final long serialVersionUID = 1L;
	private ScoGoodsTree parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private String descr;		// 描述
	private String type;		// 类型，办公用品，汽车维修
	private Integer sort;		// 排序
	private User user;		// 拥有者
	
	public ScoGoodsTree() {
		super();
	}

	public ScoGoodsTree(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public ScoGoodsTree getParent() {
		return parent;
	}

	public void setParent(ScoGoodsTree parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@Length(min=1, max=32, message="类型，办公用品，汽车维修长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@NotNull(message="拥有者不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}