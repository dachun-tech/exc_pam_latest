/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import com.thinkgem.jeesite.modules.sco.dao.ScoGoodsTreeDao;

/**
 * 商品属性Service
 * @author thinkgem
 * @version 2015-11-10
 */
@Service
@Transactional(readOnly = true)
public class ScoGoodsTreeService extends TreeService<ScoGoodsTreeDao, ScoGoodsTree> {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected ScoGoodsTreeDao dao;

	public ScoGoodsTree get(String id) {
		return super.get(id);
	}

	/**
	 * 按父Id查询所有子节点
	 * @param parentId 父ID
	 * @return ScoGoodsTree集合
	 */
	public List<ScoGoodsTree> getByParentId(String parentId){
		return dao.getByParentId(parentId);
	}

	/**
	 * 按父Id查询所有子节点
	 * @param parentId 父ID
	 * @return ScoGoodsTree集合
	 */
	public List<ScoGoodsTree> getByParentIds(String... parentId){
		return dao.getByParentIds(parentId);
	}

	/**
	 * 获取指定ID组节点
	 * @param id
	 * @return
     */
	public List<ScoGoodsTree> getByIds(String... id){
		return dao.getByIds(id);
	}

	/**
	 * 根据名称查询对象
	 * @param name 二级目录名称
	 * @param parentId 二级目录父ID
	 * @param userId 用户ID
	 * @return ScoGoodsTree
	 */
	public ScoGoodsTree getByName(String name,String parentId,String userId){
		return dao.getByName(name,parentId,userId);
	}
	
	public List<ScoGoodsTree> findList(ScoGoodsTree scoGoodsTree) {
		if (StringUtils.isNotBlank(scoGoodsTree.getParentIds())){
			scoGoodsTree.setParentIds(","+scoGoodsTree.getParentIds()+",");
		}
		return super.findList(scoGoodsTree);
	}

	@Transactional(readOnly = false)
	public void save(ScoGoodsTree scoGoodsTree) {
		super.save(scoGoodsTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoGoodsTree scoGoodsTree) {
		super.delete(scoGoodsTree);
	}
	
}