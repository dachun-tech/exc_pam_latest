/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统公告设置Entity
 * @author 段文昌
 * @version 2015-11-07
 */
public class ScoNotice extends DataEntity<ScoNotice> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// 采购机构ID
	private String roleId;		// 角色ID
	private String officeName;		// 采购机构
	private String title;		// 标题
	private String type;		// 类型
	private String state;		// 审核状态,0-待审,1-通过,2-未通过
	private String content;		// 内容
	private User createByName;		// 创建者
	private Date publishDate;		// 发布时间
	
	public ScoNotice() {
		super();
		this.type = "1"; //默认为欢迎页
//		this.roleId = "2"; //默认角色管理员
	}

	public ScoNotice(String id){
		super(id);
	}

	@NotNull(message="采购机构ID不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=4, message="角色ID长度必须介于 0 和 4 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Length(min=0, max=64, message="采购机构长度必须介于 0 和 64 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=128, message="标题长度必须介于 0 和 128 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
//	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=2, message="审核状态,0-待审,1-通过,2-未通过长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@NotNull(message="创建者不能为空")
	public User getCreateByName() {
		return createByName;
	}

	public void setCreateByName(User createByName) {
		this.createByName = createByName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发布时间不能为空")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
}