/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerFinal;
import com.thinkgem.jeesite.modules.sco.dao.ScoSerFinalDao;

/**
 * 结算单与服务类型表维护Service
 * @author 段文昌
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class ScoSerFinalService extends CrudService<ScoSerFinalDao, ScoSerFinal> {

	public ScoSerFinal get(String id) {
		return super.get(id);
	}
	
	public List<ScoSerFinal> findList(ScoSerFinal scoSerFinal) {
		return super.findList(scoSerFinal);
	}
	
	public Page<ScoSerFinal> findPage(Page<ScoSerFinal> page, ScoSerFinal scoSerFinal) {
		return super.findPage(page, scoSerFinal);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoSerFinal scoSerFinal) {
		super.save(scoSerFinal);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoSerFinal scoSerFinal) {
		super.delete(scoSerFinal);
	}

	/**
	 * 第一
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<ScoSerFinal> findFirstList(ScoSerFinal entity){
		return dao.findFirstList(entity);
	}

	/**
	 * 第二
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<ScoSerFinal> findSecondList(ScoSerFinal entity){
		return dao.findSecondList(entity);
	}
	/**
	 * 第三
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<ScoSerFinal> findThirdList(ScoSerFinal entity){
		return dao.findThirdList(entity);
	}

	/**
	 * 第四
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<ScoSerFinal> findForthList(ScoSerFinal entity){
		return dao.findForthList(entity);
	}
	/**
	 * 第四
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<ScoSerFinal> findFiveList(ScoSerFinal entity){
		return dao.findFiveList(entity);
	}

	/**
	 * 更新商品数量
	 * @param scoSerFinal
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateNumbers(ScoSerFinal scoSerFinal){
		return dao.updateNumbers(scoSerFinal);
	}
}