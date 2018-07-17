/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.entity.ScoTyre;
import com.thinkgem.jeesite.modules.sco.dao.ScoTyreDao;

/**
 * 轮胎表维护Service
 * @author 段文昌
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class ScoTyreService extends CrudService<ScoTyreDao, ScoTyre> {

	public ScoTyre get(String id) {
		return super.get(id);
	}
	
	public List<ScoTyre> findList(ScoTyre scoTyre) {
		return super.findList(scoTyre);
	}
	
	public Page<ScoTyre> findPage(Page<ScoTyre> page, ScoTyre scoTyre) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, scoTyre);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoTyre scoTyre) {
		super.save(scoTyre);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoTyre scoTyre) {
		super.delete(scoTyre);
	}
	
	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void audit(ScoTyre scoTyre) {
		
		dao.updateState(scoTyre);
	}
}