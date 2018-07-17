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
 * 服务类目录列表Entity
 * @author 段文昌
 * @version 2015-12-01
 */
public class ScoSerTree extends TreeEntity<ScoSerTree> {
	
	private static final long serialVersionUID = 1L;
	private ScoSerTree parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private String firstletter;		// 首字母
	private String level;		// 树级别
	private String descr;		// 描述
	private String type;		// 类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发
	private String subType; 	//二级类型,如车辆类下的:汽车,机油,轮胎
	private Integer sort;		// 排序
	private User user;		// 拥有者
	
	public ScoSerTree() {
		super();
	}

	public ScoSerTree(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public ScoSerTree getParent() {
		return parent;
	}

	public void setParent(ScoSerTree parent) {
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
	
	@Length(min=0, max=2, message="首字母长度必须介于 0 和 2 之间")
	public String getFirstletter() {
		return firstletter;
	}

	public void setFirstletter(String firstletter) {
		this.firstletter = firstletter;
	}
	
	@Length(min=0, max=11, message="树级别长度必须介于 0 和 11 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@Length(min=1, max=32, message="类型，汽车维修，印刷，图文制作，喷绘，视频制作，软件开发长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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