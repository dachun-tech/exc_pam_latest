/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.dao.ScoAcceptanceReportDao;
import com.thinkgem.jeesite.modules.sco.entity.AdminScoAcceptanceStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceReport;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;
import com.thinkgem.jeesite.modules.sco.util.SerialNumberUtil;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 验收单Serviceß
 * @author thinkgem
 * @version 2015-11-12
 */
@Service
@Transactional(readOnly = true)
public class ScoAcceptanceReportService extends CrudService<ScoAcceptanceReportDao, ScoAcceptanceReport> {

	@Autowired
	private ScoAcceptanceReportDao scoGoodsAcceptanceDao;
	@Autowired
	private ScoSettingService scoSettingService;


	public ScoAcceptanceReport get(String id) {
		return super.get(id);
	}
	
	public List<ScoAcceptanceReport> findList(ScoAcceptanceReport scoAcceptanceReport) {
		return super.findList(scoAcceptanceReport);
	}
	
	public Page<ScoAcceptanceReport> findPage(Page<ScoAcceptanceReport> page, ScoAcceptanceReport scoAcceptanceReport) {
		return super.findPage(page, scoAcceptanceReport);
	}

	/**
	 * 未完成验收单
	 * @param scoAcceptanceReport
	 * @return
     */
	public ScoAcceptanceReport findUnFinish(ScoAcceptanceReport scoAcceptanceReport){
		scoAcceptanceReport.setState(DictUtils.getDictValue("未完成","audit_report","0"));
		ScoAcceptanceReport report = scoGoodsAcceptanceDao.findUnFinish(scoAcceptanceReport);
		return report;
	}
	
	@Transactional(readOnly = false)
	public void save(ScoAcceptanceReport scoAcceptanceReport) {
		if(StringUtils.isBlank(scoAcceptanceReport.getSerialNumber())){
			
			String serialNumber = SerialNumberUtil.createSerialNumber();
			
			if(scoAcceptanceReport.getCurrentUser()!=null){
				if(CollectionUtils.isNotEmpty(scoAcceptanceReport.getCurrentUser().getRoleList())){
					Role role = scoAcceptanceReport.getCurrentUser().getRoleList().get(0);
					if(role!=null){
						serialNumber = SerialNumberUtil.createSerialNumber(scoAcceptanceReport.getCurrentUser().getOffice().getCode(), role.getId());
					}
				}
				
			}
			scoAcceptanceReport.setSerialNumber(serialNumber);
		}
		scoAcceptanceReport.setState(DictUtils.getDictValue("未完成","audit_report","0"));
		super.save(scoAcceptanceReport);
	}

	/**
	 * 判断进入哪个审核状态
	 * @param scoAcceptanceReport
     */
	@Transactional(readOnly = false)
	public void enterIntoAudit(ScoAcceptanceReport scoAcceptanceReport){
		//查看是否需要审核
		ScoSetting scoSetting = scoSettingService.getByAttribute(ScoGlobal.WORK_YSD_CONFIG);
		if(ScoGlobal.AUDIT_GLOBAL_N.equals(scoSetting.getValue())){ //无需审核
			this.defaultPassAudit(scoAcceptanceReport);
		} else if(ScoGlobal.AUDIT_GLOBAL_Y.equals(scoSetting.getValue())){ //待审
			this.waitAudit(scoAcceptanceReport);
		}
	}

	/**
	 * 待审核
	 * @param scoAcceptanceReport
     */
	@Transactional(readOnly = false)
	public void waitAudit(ScoAcceptanceReport scoAcceptanceReport) {
		scoAcceptanceReport.setState(DictUtils.getDictValue("待审核","audit_report","1"));
		scoGoodsAcceptanceDao.updateState(scoAcceptanceReport);
	}

	/**
	 * 通过审核
	 * @param scoAcceptanceReport
	 */
	@Transactional(readOnly = false)
	public void passAudit(ScoAcceptanceReport scoAcceptanceReport) {
		scoAcceptanceReport.setState(DictUtils.getDictValue("审核通过","audit_report","2"));
		scoGoodsAcceptanceDao.updateState(scoAcceptanceReport);
	}

	/**
	 * 通过审核
	 * @param scoAcceptanceReport
	 */
	@Transactional(readOnly = false)
	public void defaultPassAudit(ScoAcceptanceReport scoAcceptanceReport) {
		scoAcceptanceReport.setState(DictUtils.getDictValue("默认通过","audit_report","3"));
		scoGoodsAcceptanceDao.updateState(scoAcceptanceReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoAcceptanceReport scoAcceptanceReport) {
		super.delete(scoAcceptanceReport);
	}

	/**
	 * 获取用户验收单的所有年份
	 * @param statistic
	 * @return
	 */
	public List<ScoAcceptanceStatistic> getYear(ScoAcceptanceStatistic statistic){
		List<ScoAcceptanceStatistic> list = dao.getYear(statistic);
		list.removeAll(Collections.singleton(null));
		return list;
	}

	/**
	 * 用户部分验收单统计
	 * @param statistic
	 * @return
	 */
	public List<ScoAcceptanceStatistic> statistic(ScoAcceptanceStatistic statistic){

		return dao.statistic(statistic);
	}

	/**
	 * 管理员部分验收单审核统计
	 * @param adminStatistic
	 * @return
	 */
	public Page<AdminScoAcceptanceStatistic> findPageAuditList(Page<AdminScoAcceptanceStatistic> page, AdminScoAcceptanceStatistic adminStatistic) {
		page.setOrderBy("a.buy_date desc");
		adminStatistic.setPage(page);
		page.setList(dao.auditList(adminStatistic));
		return page;
	}


	/**
	 * 打印查看验收单
	 * @param scoAcceptanceReport
	 * @return
	 */
	public Page<ScoAcceptanceReport> findPageList(Page<ScoAcceptanceReport> page, ScoAcceptanceReport scoAcceptanceReport){
		scoAcceptanceReport.setPage(page);
		page.setList(dao.findPageList(scoAcceptanceReport));
		return page;
	}

	/**
	 * 管理员部分验收单统计
	 * @param adminStatistic
	 * @return
	 */
	public List<AdminScoAcceptanceStatistic> adminStatistic(AdminScoAcceptanceStatistic adminStatistic){
		return dao.adminStatistic(adminStatistic);
	}

	public List<AdminScoAcceptanceStatistic> adminStatisticDepartments(AdminScoAcceptanceStatistic adminStatistic){
		return dao.adminStatisticDepartments(adminStatistic);
	}

	public List<ScoAcceptanceReport> getDepartmentList(ScoAcceptanceReport scoAcceptanceReport){
		return dao.getDepartmentList(scoAcceptanceReport);
	}
	
}