/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;

import java.util.List;

/**
 * 服务类目录列表DAO接口
 * @author 段文昌
 * @version 2015-12-01
 */
@MyBatisDao
public interface ScoSerTreeDao extends TreeDao<ScoSerTree> {
    /**
     * 按父Id查询所有子节点
     * @param scoSerTree 父对象
     * @return ScoGoodsTree集合
     */
    public List<ScoSerTree> getByParentTree(ScoSerTree scoSerTree);

    /**
     * 按父Id查询所有子节点
     * @param parentId 父ID
     * @return ScoGoodsTree集合
     */
    public List<ScoSerTree> getByParentId(String parentId);
}