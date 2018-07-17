/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;
import com.thinkgem.jeesite.modules.sco.dao.ScoSettingDao;

/**
 * 系统审核设置Service
 * @author 段文昌
 * @version 2015-11-04
 */
@Service
@Transactional(readOnly = true)
public class ScoSettingService extends CrudService<ScoSettingDao, ScoSetting> {

	
	public ScoSetting get(String id) {
		ScoSetting scoSetting = super.get(id);
		return scoSetting;
	}

	/**
	 * 根据attribute属性查询对象
	 * @param attribute 属性名称
	 * @return ScoSetting
	 */
	public ScoSetting getByAttribute(String attribute){
		return dao.getByAttribute(attribute);
	}

	public List<ScoSetting> findList(ScoSetting scoSetting) {
		return super.findList(scoSetting);
	}
	
	public Page<ScoSetting> findPage(Page<ScoSetting> page, ScoSetting scoSetting) {
		return super.findPage(page, scoSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoSetting scoSetting) {
		super.save(scoSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoSetting scoSetting) {
		super.delete(scoSetting);
	}
	
}