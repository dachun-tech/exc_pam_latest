/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统审核设置Entity
 * @author 段文昌
 * @version 2015-11-04
 */
public class ScoSetting extends DataEntity<ScoSetting> {
	
	private static final long serialVersionUID = 1L;
	private String attribute;		// key值
	private String value;		// value值
	private String name;		// 名称
	private String roleId;		// 角色ID
	
	public ScoSetting() {
		super();
	}

	public ScoSetting(String id){
		super(id);
	}

	@Length(min=0, max=64, message="attribute值长度必须介于 0 和 64 之间")
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	@Length(min=0, max=64, message="value值长度必须介于 0 和 64 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=4, message="角色ID长度必须介于 0 和 4 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "ScoSetting{" +
				"attribute='" + attribute + '\'' +
				", value='" + value + '\'' +
				", name='" + name + '\'' +
				", roleId='" + roleId + '\'' +
				'}';
	}
}