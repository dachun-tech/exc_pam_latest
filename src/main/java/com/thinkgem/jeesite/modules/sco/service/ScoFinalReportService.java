/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.dao.ScoFinalReportDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalReport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;
import com.thinkgem.jeesite.modules.sco.util.SerialNumberUtil;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 结算单表维护Service
 * @author 段文昌
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class ScoFinalReportService extends CrudService<ScoFinalReportDao, ScoFinalReport> {

	@Autowired
	private ScoSettingService scoSettingService;

	public ScoFinalReport get(String id) {
		return super.get(id);
	}
	
	public List<ScoFinalReport> findList(ScoFinalReport scoFinalReport) {
		return super.findList(scoFinalReport);
	}
	
	public Page<ScoFinalReport> findPage(Page<ScoFinalReport> page, ScoFinalReport scoFinalReport) {
		return super.findPage(page, scoFinalReport);
	}

	/**
	 * 打印查看结算单
	 * @param scoFinalReport
	 * @return
	 */
	public Page<ScoFinalReport> findPageList(Page<ScoFinalReport> page, ScoFinalReport scoFinalReport){
		scoFinalReport.setPage(page);
		page.setList(dao.findPageList(scoFinalReport));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ScoFinalReport scoFinalReport) {
		
		if(StringUtils.isBlank(scoFinalReport.getSerialNumber())){
			String serialNumber = SerialNumberUtil.createSerialNumber();
			if(scoFinalReport.getCurrentUser()!=null){
				if(CollectionUtils.isNotEmpty(scoFinalReport.getCurrentUser().getRoleList())){
					Role role = scoFinalReport.getCurrentUser().getRoleList().get(0);
					if(role!=null){
						serialNumber = SerialNumberUtil.createSerialNumber(scoFinalReport.getCurrentUser().getOffice().getCode(), role.getId());
					}
				}
				
			}
			scoFinalReport.setSerialNumber(serialNumber);
			
		}
		scoFinalReport.setState(DictUtils.getDictValue("未完成","audit_report","0"));
		super.save(scoFinalReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoFinalReport scoFinalReport) {
		super.delete(scoFinalReport);
	}

	/**
	 * 未完成结算单
	 * @param scoFinalReport
	 * @return
	 */
	public ScoFinalReport findUnFinish(ScoFinalReport scoFinalReport){
		scoFinalReport.setState(DictUtils.getDictValue("未完成","audit_report","0"));
		ScoFinalReport report = dao.findUnFinish(scoFinalReport);
		return report;
	}

	/**
	 * 判断进入哪个审核状态
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void enterIntoAudit(ScoFinalReport scoFinalReport){
		//查看是否需要审核
		ScoSetting scoSetting = new ScoSetting();
		if(scoFinalReport.getType().equals(ScoGlobal.CAR_ROLE)){//汽车
			scoSetting = scoSettingService.getByAttribute(ScoGlobal.CAR_JSD_CONFIG);
		} else if(scoFinalReport.getType().equals(ScoGlobal.IMAGE_TEXT_ROLE)){//图文
			scoSetting = scoSettingService.getByAttribute(ScoGlobal.IMAGE_TEXT_JSD_CONFIG);
		} else if(scoFinalReport.getType().equals(ScoGlobal.PRINTING_ROLE)){//印刷
			scoSetting = scoSettingService.getByAttribute(ScoGlobal.PRINTING_JSD_CONFIG);
		} else if(scoFinalReport.getType().equals(ScoGlobal.VIDEO_ROLE)){//视频
			scoSetting = scoSettingService.getByAttribute(ScoGlobal.VIDEO_JSD_CONFIG);
		} else if(scoFinalReport.getType().equals(ScoGlobal.SOFT_ROLE)){//软件
			scoSetting = scoSettingService.getByAttribute(ScoGlobal.SOFT_JSD_CONFIG);
		}
		// 选择处理状态
		if(ScoGlobal.AUDIT_GLOBAL_N.equals(scoSetting.getValue())){ //无需审核
			this.defaultPassAudit(scoFinalReport);
		} else if(ScoGlobal.AUDIT_GLOBAL_Y.equals(scoSetting.getValue())){ //待审
			this.waitAudit(scoFinalReport);
		}
	}

	/**
	 * 待审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void waitAudit(ScoFinalReport scoFinalReport) {
		scoFinalReport.setState(DictUtils.getDictValue("待审核","audit_report","1"));
		dao.updateState(scoFinalReport);
	}

	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void passAudit(ScoFinalReport scoFinalReport) {
		scoFinalReport.setState(DictUtils.getDictValue("审核通过","audit_report","2"));
		dao.updateState(scoFinalReport);
	}

	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void defaultPassAudit(ScoFinalReport scoFinalReport) {
		scoFinalReport.setState(DictUtils.getDictValue("默认通过","audit_report","3"));
		dao.updateState(scoFinalReport);
	}

	/**
	 * 获取用户结算单的所有年份
	 * @param createBy
	 * @return
	 */
	public List<String> getYear(User createBy){
		return dao.getYear(createBy);
	}

	/**
	 * 获取用户结算单的所有年份
	 * @return
	 */
	public List<String> getYear(){
		List<String> list = this.getYear(new User());
		list.removeAll(Collections.singleton(null));
		return list;
	}

	/**
	 * 管理员部分验收单审核统计
	 * @param page
	 * @param scoFinalStatistic
	 * @return
	 */
	public Page<ScoFinalStatistic> findPageAuditList(Page<ScoFinalStatistic> page, ScoFinalStatistic scoFinalStatistic) {
		page.setOrderBy("a.buy_date desc");
		scoFinalStatistic.setPage(page);
		page.setList(dao.auditList(scoFinalStatistic));
		
		
		
//		for (ScoFinalStatistic statistic : page.getList()) {
//			BigDecimal subtotalAmt = statistic.getSparepartAmt()
//					.add(statistic.getLaborAmt())
//					.add(statistic.getTyreAmt())
//					.add(statistic.getOilAmt());
//			if(statistic.getReport().getOtherPrice() != null){//全部所有费用 = 全部费用 + 其它费用
//				subtotalAmt = subtotalAmt.add(statistic.getReport().getOtherPrice());
//			}
//			if(statistic.getPrintsAmt() != null){//全部所有费用 = 全部费用 + 印刷品
//				subtotalAmt = subtotalAmt.add(statistic.getPrintsAmt());
//			}
//			statistic.setSubtotal(subtotalAmt); //小计金额
//		}
		
		return page;
	}

	/**
	 * 用户部分结算单统计
	 * @param scoFinalReport
	 * @return
	 */
	public List<ScoFinalStatistic> statistic(ScoFinalReport scoFinalReport){
		return dao.statistic(scoFinalReport);
	}

	/**
	 * 管理员结算单统计
	 * @param scoFinalReport
	 * @return
	 */
	public List<ScoFinalStatistic> adminStatistic(ScoFinalReport scoFinalReport){
		return dao.adminStatistic(scoFinalReport);
	}
	
	
	  /**
     * 查询所有车牌号
     * @return
     */
    public List<String> selectIdentifier(){
    	List<String> listNew = new  ArrayList<String>();
    	List<String> list =dao.selectIdentifier();
    	if(CollectionUtils.isNotEmpty(list)){
    		for (String string : list) {
				if(StringUtils.isBlank(string)||"null".equals(string)){
					continue;
				}else{
					listNew.add(string);
				}
			}
    	}
    	return listNew;
    }
}