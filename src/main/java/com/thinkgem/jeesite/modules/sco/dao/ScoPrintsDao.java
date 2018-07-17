/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoPrints;

/**
 * 印刷品DAO接口
 * @author oddbitter
 * @version 2016-04-30
 */
@MyBatisDao
public interface ScoPrintsDao extends CrudDao<ScoPrints> {
	  /**
     * 更新状态
     * @param scoFinalReport
     * @return
     */
    public Integer updateState(ScoPrints scoPrints);
}