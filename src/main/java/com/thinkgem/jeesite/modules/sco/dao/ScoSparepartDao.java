/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;

/**
 * 零配件维护DAO接口
 * @author 段文昌
 * @version 2015-12-03
 */
@MyBatisDao
public interface ScoSparepartDao extends CrudDao<ScoSparepart> {

    /**
     * 更新状态
     * @param scoFinalReport
     * @return
     */
    public Integer updateState(ScoSparepart scoSparepart);
}