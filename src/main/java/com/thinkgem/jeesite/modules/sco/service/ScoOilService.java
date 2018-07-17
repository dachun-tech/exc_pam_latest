/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoOil;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.dao.ScoOilDao;

/**
 * 机油表维护Service
 * @author 段文昌
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class ScoOilService extends CrudService<ScoOilDao, ScoOil> {

	public ScoOil get(String id) {
		return super.get(id);
	}
	
	public List<ScoOil> findList(ScoOil scoOil) {
		return super.findList(scoOil);
	}
	
	public Page<ScoOil> findPage(Page<ScoOil> page, ScoOil scoOil) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, scoOil);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoOil scoOil) {
		super.save(scoOil);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoOil scoOil) {
		super.delete(scoOil);
	}
	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void audit(ScoOil scoOil) {
		
		dao.updateState(scoOil);
	}
}