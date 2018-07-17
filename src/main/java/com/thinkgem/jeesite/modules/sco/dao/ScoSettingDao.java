/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;

/**
 * 系统审核设置DAO接口
 * @author 段文昌
 * @version 2015-11-04
 */
@MyBatisDao
public interface ScoSettingDao extends CrudDao<ScoSetting> {
    /**
     * 根据attribute属性查询对象
     * @param attribute 属性名称
     * @return ScoSetting
     */
    public ScoSetting getByAttribute(String attribute);
}