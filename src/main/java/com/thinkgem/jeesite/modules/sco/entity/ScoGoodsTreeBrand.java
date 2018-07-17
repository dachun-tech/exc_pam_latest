/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品属性Entity
 * @author thinkgem
 * @version 2015-11-10
 */
public class ScoGoodsTreeBrand extends DataEntity<ScoGoodsTreeBrand> {
	
	private static final long serialVersionUID = 1L;
	private String treeId;		// 目录树id
	private String name;		// 名称
	private Integer sort;		// 排序
	private User user;		// 拥有者
	
	public ScoGoodsTreeBrand() {
		super();
	}

	public ScoGoodsTreeBrand(String id){
		super(id);
	}

	@Length(min=1, max=64, message="目录树id长度必须介于 1 和 64 之间")
	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}