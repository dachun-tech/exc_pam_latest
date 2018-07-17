/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoNotice;

/**
 * 系统公告设置DAO接口
 * @author 段文昌
 * @version 2015-11-07
 */
@MyBatisDao
public interface ScoNoticeDao extends CrudDao<ScoNotice> {
	
}