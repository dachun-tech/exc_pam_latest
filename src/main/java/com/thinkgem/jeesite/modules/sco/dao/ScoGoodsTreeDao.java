/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性DAO接口
 * @author thinkgem
 * @version 2015-11-10
 */
@MyBatisDao
public interface ScoGoodsTreeDao extends TreeDao<ScoGoodsTree> {
    /**
     * 按父Id查询所有子节点
     * @param parentId 父ID
     * @return ScoGoodsTree集合
     */
    public List<ScoGoodsTree> getByParentId(String parentId);


    public List<ScoGoodsTree> getByParentIds(String... parentId);

    public List<ScoGoodsTree> getByIds(String... id);

    /**
     * 根据名称查询对象
     * @param name 二级目录名称
     * @param parentId 二级目录父ID
     * @param userId 用户ID
     * @return ScoGoodsTree
     */
    public ScoGoodsTree getByName(@Param("name") String name,@Param("parentId") String parentId,@Param("userId") String userId);
}