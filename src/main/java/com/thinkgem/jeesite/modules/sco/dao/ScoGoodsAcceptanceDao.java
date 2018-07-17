/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsAcceptance;

/**
 * 验收单商品列表DAO接口
 * @author 段文昌
 * @version 2015-11-26
 */
@MyBatisDao
public interface ScoGoodsAcceptanceDao extends CrudDao<ScoGoodsAcceptance> {
    /**
     * 更新商品数量
     * @param scoGoodsAcceptance
     * @return
     */
    public Integer updateNumbers(ScoGoodsAcceptance scoGoodsAcceptance);
    
    
    public List<ScoGoodsAcceptance> findPrintsList(ScoGoodsAcceptance scoGoodsAcceptance);
}