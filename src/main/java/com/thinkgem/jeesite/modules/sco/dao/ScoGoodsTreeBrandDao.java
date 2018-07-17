/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTreeBrand;
import org.apache.ibatis.annotations.Param;

/**
 * 商品属性DAO接口
 * @author thinkgem
 * @version 2015-11-10
 */
@MyBatisDao
public interface ScoGoodsTreeBrandDao extends CrudDao<ScoGoodsTreeBrand> {
    /**
     * 根据名称查询对象
     * @param name 名称
     * @param treeId 目录ID
     * @param userId 用户ID
     * @return ScoGoodsTreeBrand
     */
    public ScoGoodsTreeBrand getByName(@Param("name") String name,@Param("treeId") String treeId,@Param("userId") String userId);
}