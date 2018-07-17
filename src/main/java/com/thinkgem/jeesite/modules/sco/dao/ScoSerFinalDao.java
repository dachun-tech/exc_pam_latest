/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerFinal;

import java.util.List;

/**
 * 结算单与服务类型表维护DAO接口
 * @author 段文昌
 * @version 2015-12-03
 */
@MyBatisDao
public interface ScoSerFinalDao extends CrudDao<ScoSerFinal> {

    /**
     * 第一
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<ScoSerFinal> findFirstList(ScoSerFinal entity);

    /**
     * 第二
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<ScoSerFinal> findSecondList(ScoSerFinal entity);
    /**
     * 第三
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<ScoSerFinal> findThirdList(ScoSerFinal entity);

    /**
     * 第四
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<ScoSerFinal> findForthList(ScoSerFinal entity);
    /**
     * 第五
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<ScoSerFinal> findFiveList(ScoSerFinal entity);

    /**
     * 更新商品数量
     * @param scoSerFinal
     * @return
     */
    public Integer updateNumbers(ScoSerFinal scoSerFinal);
}