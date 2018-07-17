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
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsAcceptance;
import com.thinkgem.jeesite.modules.sco.dao.ScoGoodsAcceptanceDao;

/**
 * 验收单商品列表Service
 * @author 段文昌
 * @version 2015-11-26
 */
@Service
@Transactional(readOnly = true)
public class ScoGoodsAcceptanceService extends CrudService<ScoGoodsAcceptanceDao, ScoGoodsAcceptance> {

	@Autowired
	private ScoGoodsAcceptanceDao scoGoodsAcceptanceDao;

	public ScoGoodsAcceptance get(String id) {
		ScoGoodsAcceptance scoGoodsAcceptance = super.get(id);
		return scoGoodsAcceptance;
	}
	
	public List<ScoGoodsAcceptance> findList(ScoGoodsAcceptance scoGoodsAcceptance) {
		return super.findList(scoGoodsAcceptance);
	}
	public List<ScoGoodsAcceptance> findPrintsList(ScoGoodsAcceptance scoGoodsAcceptance) {
		return dao.findPrintsList(scoGoodsAcceptance);
	}
	
	public Page<ScoGoodsAcceptance> findPage(Page<ScoGoodsAcceptance> page, ScoGoodsAcceptance scoGoodsAcceptance) {
		return super.findPage(page, scoGoodsAcceptance);
	}

	/**
	 * 更新商品数量
	 * @param scoGoodsAcceptance
	 * @return
     */
	@Transactional(readOnly = false)
	public Integer updateNumbers(ScoGoodsAcceptance scoGoodsAcceptance){
		return scoGoodsAcceptanceDao.updateNumbers(scoGoodsAcceptance);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoGoodsAcceptance scoGoodsAcceptance) {
		super.save(scoGoodsAcceptance);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoGoodsAcceptance scoGoodsAcceptance) {
		super.delete(scoGoodsAcceptance);
	}
	
}