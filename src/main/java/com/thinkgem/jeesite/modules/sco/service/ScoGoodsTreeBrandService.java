/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTreeBrand;
import com.thinkgem.jeesite.modules.sco.dao.ScoGoodsTreeBrandDao;

/**
 * 商品属性Service
 * @author thinkgem
 * @version 2015-11-10
 */
@Service
@Transactional(readOnly = true)
public class ScoGoodsTreeBrandService extends CrudService<ScoGoodsTreeBrandDao, ScoGoodsTreeBrand> {

	public ScoGoodsTreeBrand get(String id) {
		return super.get(id);
	}

	/**
	 * 根据名称查询对象
	 * @param name 名称
	 * @param treeId 目录ID
	 * @param userId 用户ID
	 * @return ScoGoodsTreeBrand
	 */
	public ScoGoodsTreeBrand getByName(String name,String treeId,String userId){
		return dao.getByName(name, treeId, userId);
	}

	public List<ScoGoodsTreeBrand> findList(ScoGoodsTreeBrand scoGoodsTreeBrand) {
		return super.findList(scoGoodsTreeBrand);
	}
	
	public Page<ScoGoodsTreeBrand> findPage(Page<ScoGoodsTreeBrand> page, ScoGoodsTreeBrand scoGoodsTreeBrand) {
		return super.findPage(page, scoGoodsTreeBrand);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoGoodsTreeBrand scoGoodsTreeBrand) {
		super.save(scoGoodsTreeBrand);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoGoodsTreeBrand scoGoodsTreeBrand) {
		super.delete(scoGoodsTreeBrand);
	}
	
}