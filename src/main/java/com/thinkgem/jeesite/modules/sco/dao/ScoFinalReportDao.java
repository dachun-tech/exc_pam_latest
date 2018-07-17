/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalReport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatistic;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.List;

/**
 * 结算单表维护DAO接口
 * @author 段文昌
 * @version 2015-12-03
 */
@MyBatisDao
public interface ScoFinalReportDao extends CrudDao<ScoFinalReport> {

    /**
     * 查找未完成的结算单
     * @param scoFinalReport
     * @return
     */
    public ScoFinalReport findUnFinish(ScoFinalReport scoFinalReport);

    /**
     * 更新状态
     * @param scoFinalReport
     * @return
     */
    public Integer updateState(ScoFinalReport scoFinalReport);

    /**
     * 打印查看结算单
     * @param scoFinalReport
     * @return
     */
    public List<ScoFinalReport> findPageList(ScoFinalReport scoFinalReport);

    /**
     * 获取用户结算单的所有年份
     * @param createBy
     * @return
     */
    public List<String> getYear(User createBy);

    /**
     * 用户部分结算单统计
     * @param scoFinalReport
     * @return
     */
    public List<ScoFinalStatistic> statistic(ScoFinalReport scoFinalReport);

    /**
     * 管理员部分审核结算单
     * @param scoFinalStatistic
     * @return
     */
    public List<ScoFinalStatistic> auditList(ScoFinalStatistic scoFinalStatistic);

    /**
     * 管理员结算单统计
     * @param scoFinalReport
     * @return
     */
    public List<ScoFinalStatistic> adminStatistic(ScoFinalReport scoFinalReport);
    
    
    
    /**
     * 查询所有车牌号
     * @return
     */
    public List<String> selectIdentifier();
}