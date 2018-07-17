/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.dao.ScoSerTreeDao;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;

/**
 * 服务类目录列表Service
 * @author 段文昌
 * @version 2015-12-01
 */
@Service
@Transactional(readOnly = true)
public class ScoSerTreeService extends TreeService<ScoSerTreeDao, ScoSerTree> {

	public ScoSerTree get(String id) {
		return super.get(id);
	}
	
	public List<ScoSerTree> findList(ScoSerTree scoSerTree) {
		if (StringUtils.isNotBlank(scoSerTree.getParentIds())){
			scoSerTree.setParentIds(","+scoSerTree.getParentIds()+",");
		}
		return super.findList(scoSerTree);
	}

	/**
	 * 按父Id查询所有子节点
	 * @param parentId 父ID
	 * @return ScoGoodsTree集合
	 */
	public List<ScoSerTree> getByParentId(String parentId){
		List<ScoSerTree> list =dao.getByParentId(parentId);
		return list;
	}

	/**
	 * 按父Id查询所有子节点
	 * @param scoSerTree 父对象
	 * @return ScoGoodsTree集合
	 */
	public List<ScoSerTree> getByParentId(ScoSerTree scoSerTree){
		List<ScoSerTree> list =dao.getByParentTree(scoSerTree);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void save(ScoSerTree scoSerTree) {
		super.save(scoSerTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoSerTree scoSerTree) {
		super.delete(scoSerTree);
	}
	
	
	/**
	 * 工艺描述封装转换
	 * @return
	 */
	public List<ScoSerTreeElement> queryDesc(){
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		ScoSerTree scoSerTree = new ScoSerTree();
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType("desc");
		List<ScoSerTree> serTreeList = this.findList(scoSerTree);
		
		
		List<ScoSerTreeElement> descs  = new ArrayList<ScoSerTreeElement>();
		if(CollectionUtils.isNotEmpty(serTreeList)){
			for (ScoSerTree parentSerTree : serTreeList) {
				ScoSerTreeElement parent  =new ScoSerTreeElement();
				parent.setName(parentSerTree.getName());
				parent.setId(parentSerTree.getId());
				descs.add(parent);

				ScoSerTree scoSerTreeQuery = new ScoSerTree();
				scoSerTreeQuery.setParent(parentSerTree);
				
				List<ScoSerTree> serTreeList2 = this.findList(scoSerTreeQuery);
				
				List<ScoSerTreeElement> children  = new ArrayList<ScoSerTreeElement>();
				for (ScoSerTree childSerTree : serTreeList2) {
					ScoSerTreeElement  child =new ScoSerTreeElement();
					child.setParent(parent);
					child.setName(childSerTree.getName());
					child.setId(childSerTree.getId());
					children.add(child);
				}
				
				parent.setChildren(children);
				
			}
		}
		return descs;
	}
	
	/**
	 * 工艺描述封装转换
	 * @return
	 */
	public Map<String, String> queryDescMap(){
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		ScoSerTree scoSerTree = new ScoSerTree();
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType("desc");
		List<ScoSerTree> serTreeList = this.findList(scoSerTree);
		
		Map<String, String> map = new HashMap<String, String>();
		
		List<ScoSerTreeElement> descs  = new ArrayList<ScoSerTreeElement>();
		if(CollectionUtils.isNotEmpty(serTreeList)){
			for (ScoSerTree parentSerTree : serTreeList) {

				ScoSerTree scoSerTreeQuery = new ScoSerTree();
				scoSerTreeQuery.setParent(parentSerTree);
				
				List<ScoSerTree> serTreeList2 = this.findList(scoSerTreeQuery);

				if(CollectionUtils.isNotEmpty(serTreeList2)){
					for (ScoSerTree  serTree2 : serTreeList2) {
						map.put(serTree2.getId(),serTree2.getName());
					}
				}
				map.put(parentSerTree.getId(),parentSerTree.getName());
			}
		}
		return map;
	}
	
}