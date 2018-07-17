/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.AdminScoAcceptanceStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceReport;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 验收单DAO接口
 * @author thinkgem
 * @version 2015-11-12
 */
@MyBatisDao
public interface ScoAcceptanceReportDao extends CrudDao<ScoAcceptanceReport> {
    /**
     * 查找未完成的验收单
     * @param scoAcceptanceReport
     * @return
     */
    public ScoAcceptanceReport findUnFinish(ScoAcceptanceReport scoAcceptanceReport);

    /**
     * 更新状态
     * @param scoAcceptanceReport
     * @return
     */
    public Integer updateState(ScoAcceptanceReport scoAcceptanceReport);

    /**
     * 获取用户验收单的所有年份
     * @param statistic
     * @return
     */
    public List<ScoAcceptanceStatistic> getYear(ScoAcceptanceStatistic statistic);

    /**
     * 用户部分验收单统计
     * @param statistic
     * @return
     */
    public List<ScoAcceptanceStatistic> statistic(ScoAcceptanceStatistic statistic);

    /**
     * 管理员部分验收单审核统计
     * @param adminStatistic
     * @return
     */
    public List<AdminScoAcceptanceStatistic> auditList(AdminScoAcceptanceStatistic adminStatistic);

    /**
     * 打印查看验收单
     * @param scoAcceptanceReport
     * @return
     */
    public List<ScoAcceptanceReport> findPageList(ScoAcceptanceReport scoAcceptanceReport);

    /**
     * 管理员部分验收单统计
     * @param adminStatistic
     * @return
     */
    public List<AdminScoAcceptanceStatistic> adminStatistic(AdminScoAcceptanceStatistic adminStatistic);

    public List<ScoAcceptanceReport> getDepartmentList(ScoAcceptanceReport scoAcceptanceReport);

    public List<AdminScoAcceptanceStatistic> adminStatisticDepartments(AdminScoAcceptanceStatistic adminStatistic);

}