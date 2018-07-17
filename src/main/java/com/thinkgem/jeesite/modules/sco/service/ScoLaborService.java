/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoLabor;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.dao.ScoLaborDao;

/**
 * 工时表维护Service
 * @author 段文昌
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class ScoLaborService extends CrudService<ScoLaborDao, ScoLabor> {

	public ScoLabor get(String id) {
		return super.get(id);
	}
	
	public List<ScoLabor> findList(ScoLabor scoLabor) {
		return super.findList(scoLabor);
	}
	
	public Page<ScoLabor> findPage(Page<ScoLabor> page, ScoLabor scoLabor) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, scoLabor);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoLabor scoLabor) {
		super.save(scoLabor);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoLabor scoLabor) {
		super.delete(scoLabor);
	}
	
	
	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void audit(ScoLabor scoLabor) {
		
		dao.updateState(scoLabor);
	}
	
}