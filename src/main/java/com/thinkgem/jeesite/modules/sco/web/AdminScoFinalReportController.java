/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelFinal;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.AdminFinalExport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalReport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatisticImageTextExport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatisticPrintExport;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatisticVehicleExport;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.enums.TypeEnum;
import com.thinkgem.jeesite.modules.sco.service.ScoFinalReportService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.util.DateUtil;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 验收单Controller
 * @author thinkgem
 * @version 2015-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/admin/sco/scoFinalReport")
public class AdminScoFinalReportController extends BaseController {

	@Autowired
	private ScoFinalReportService scoFinalReportService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;

	@Autowired
	private SystemService systemService;


	@ModelAttribute
	public ScoFinalStatistic get(@RequestParam(required=false) String id) {
		ScoFinalStatistic entity = null;
		ScoFinalReport scoFinalReport = null;
		if (StringUtils.isNotBlank(id)){
			scoFinalReport = scoFinalReportService.get(id);
		}
		if (scoFinalReport == null){
			scoFinalReport = new ScoFinalReport();
		}
		if (entity == null){
			entity = new ScoFinalStatistic();
			entity.setReport(scoFinalReport);
		}
		return entity;
	}



	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoFinalStatistic scoFinalStatistic, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> yearList = scoFinalReportService.getYear(scoFinalStatistic.getCreateBy());
		model.addAttribute("yearList", yearList);
		List<String> monthList = new ArrayList<String>();
		monthList.add("全部");
		monthList.add("1");
		monthList.add("2");
		monthList.add("3");
		monthList.add("4");
		monthList.add("5");
		monthList.add("6");
		monthList.add("7");
		monthList.add("8");
		monthList.add("9");
		monthList.add("10");
		monthList.add("11");
		monthList.add("12");
		model.addAttribute("monthList", monthList);
		
		List<String> identifierList = scoFinalReportService.selectIdentifier();
		
		model.addAttribute("identifierList", identifierList);
		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("audit_report");
		model.addAttribute("auditReportList", auditReportList);
		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoFinalStatistic.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		//XXX 采购单位编号菜单

		Page<ScoFinalStatistic> page = scoFinalReportService.findPageAuditList(new Page<ScoFinalStatistic>(request, response), scoFinalStatistic);
		model.addAttribute("page", page);
//		scoFinalStatistic.getReport().setCreateBy(scoFinalStatistic.getCreateBy());
//		model.addAttribute("report", scoFinalStatistic.getReport());
		return "modules/sco/admin/adminFinalReportAuditList";
	}

	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = {"statistic"})
	public String statistic(ScoFinalReport scoFinalReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> yearList = scoFinalReportService.getYear();
		model.addAttribute("yearList", yearList);
		List<String> monthList = new ArrayList<String>();
		monthList.add("全部");
		monthList.add("1");
		monthList.add("2");
		monthList.add("3");
		monthList.add("4");
		monthList.add("5");
		monthList.add("6");
		monthList.add("7");
		monthList.add("8");
		monthList.add("9");
		monthList.add("10");
		monthList.add("11");
		monthList.add("12");
		model.addAttribute("monthList", monthList);

		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoFinalReport.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);

		ScoSerTree scoSerTree = new ScoSerTree();
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoFinalReport.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);

		/** 计算每月份小计 */
		List<ScoFinalStatistic> dbList = scoFinalReportService.adminStatistic(scoFinalReport);
		for (ScoFinalStatistic statistic : dbList) {
			BigDecimal subtotalAmt = statistic.getSparepartAmt()
					.add(statistic.getLaborAmt())
					.add(statistic.getTyreAmt())
					.add(statistic.getOilAmt())
					.add(statistic.getPrintsAmt());
			
			if(statistic.getOtherAmt()!= null){//全部所有费用 = 全部费用 + 其它费用
				subtotalAmt =subtotalAmt.add(statistic.getOtherAmt());
			} 
			
			statistic.setSubtotal(subtotalAmt); //小计金额
		}
		
		
		

		List<ScoFinalStatistic> list = new ArrayList<ScoFinalStatistic>();;
		ScoFinalStatistic statistic = null;

		Set<String> set = new HashSet<String>();


        //计算整体总计
        BigDecimal totalFinal = new BigDecimal(0); //结算单总数量
        BigDecimal totalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
        BigDecimal totalLaborAmt = new BigDecimal(0); //工时总金额(第二)
        BigDecimal totalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
        BigDecimal totalOilAmt = new BigDecimal(0); //机油总金额(第四)
        BigDecimal totalOtherAmt = new BigDecimal(0); //商品总金额
        BigDecimal totalAmt = new BigDecimal(0); //商品总金额

		//计算供应商小计
		BigDecimal sTotalFinal; //验收单总数量
        BigDecimal sTotalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
        BigDecimal sTotalLaborAmt = new BigDecimal(0); //工时总金额(第二)
        BigDecimal sTotalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
        BigDecimal sTotalOilAmt = new BigDecimal(0); //机油总金额(第四)
        BigDecimal sTotalOtherAmt = new BigDecimal(0); //机油总金额(第四)
		BigDecimal sTotalAmt; //商品总金额
		List<ScoFinalStatistic>  statisticList = null;//供应商组列表
        for (ScoFinalStatistic sfs : dbList) {
            /** 总计计算 */
            totalFinal = totalFinal.add(new BigDecimal(sfs.getFinalNumbers()));
            totalSparepartAmt = totalSparepartAmt.add(sfs.getSparepartAmt());
            totalLaborAmt = totalLaborAmt.add(sfs.getLaborAmt());
            totalTyreAmt = totalTyreAmt.add(sfs.getTyreAmt());
            totalOilAmt = totalOilAmt.add(sfs.getOilAmt());
            totalOtherAmt = totalOtherAmt.add(sfs.getOtherAmt());
            totalAmt = totalAmt.add(sfs.getSubtotal());
            boolean b = set.add(sfs.getCreateBy().getId());
            if (b) { //如果不重复
                /** 供应商小计计算 */
                statisticList = new ArrayList<ScoFinalStatistic>();
                statistic = new ScoFinalStatistic(); //新的统计对象
                statistic.setCreateBy(sfs.getCreateBy()); //放入用户信息
                sTotalFinal = new BigDecimal(0); //验收单总数量
                sTotalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
                sTotalLaborAmt = new BigDecimal(0); //工时总金额(第二)
                sTotalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
                sTotalOilAmt = new BigDecimal(0); //机油总金额(第四)
                sTotalOtherAmt = new BigDecimal(0); //机油总金额(第四)
                sTotalAmt = new BigDecimal(0); //商品总金额

                sTotalFinal = sTotalFinal.add(new BigDecimal(sfs.getFinalNumbers()));
                sTotalSparepartAmt = sTotalSparepartAmt.add(sfs.getSparepartAmt());
                sTotalLaborAmt = sTotalLaborAmt.add(sfs.getLaborAmt());
                sTotalTyreAmt = sTotalTyreAmt.add(sfs.getTyreAmt());
                sTotalOilAmt = sTotalOilAmt.add(sfs.getOilAmt());
                sTotalOtherAmt = sTotalOtherAmt.add(sfs.getOtherAmt());
                sTotalAmt = sTotalAmt.add(sfs.getSubtotal());

                //放入小计
                statistic.setTotalFinal(sTotalFinal);
                statistic.setSparepartAmt(sTotalSparepartAmt);
                statistic.setLaborAmt(sTotalLaborAmt);
                statistic.setTyreAmt(sTotalTyreAmt);
                statistic.setOilAmt(sTotalOilAmt);
                statistic.setOtherAmt(sTotalOtherAmt);
                statistic.setTotalAmt(sTotalAmt);
                statisticList.add(sfs);
                statistic.setStatisticList(statisticList);
                list.add(statistic); //添加至列表
            } else {
                statistic.getStatisticList().add(sfs);
                sTotalFinal = statistic.getTotalFinal().add(new BigDecimal(sfs.getFinalNumbers()));
                sTotalSparepartAmt = statistic.getSparepartAmt().add(sfs.getSparepartAmt());
                sTotalLaborAmt = statistic.getLaborAmt().add(sfs.getLaborAmt());
                sTotalTyreAmt = statistic.getTyreAmt().add(sfs.getTyreAmt());
                sTotalOilAmt = statistic.getOilAmt().add(sfs.getOilAmt());
                sTotalOtherAmt = sTotalOtherAmt.add(sfs.getOtherAmt());
                sTotalAmt = statistic.getTotalAmt().add(sfs.getSubtotal());
                //放入小计
                statistic.setTotalFinal(sTotalFinal);
                statistic.setSparepartAmt(sTotalSparepartAmt);
                statistic.setLaborAmt(sTotalLaborAmt);
                statistic.setTyreAmt(sTotalTyreAmt);
                statistic.setOilAmt(sTotalOilAmt);
                statistic.setOtherAmt(sTotalOtherAmt);
                statistic.setTotalAmt(sTotalAmt);
            }
        }
        
        try {
			Collections.sort(list, new Comparator<ScoFinalStatistic>() {

				@Override
				public int compare(ScoFinalStatistic o1, ScoFinalStatistic o2) {
					return o1.getTotalAmt().compareTo(o2.getTotalAmt());
				}
			});
		} catch (Exception e) {
			logger.error("排序异常",e);
		}

        model.addAttribute("list", list);

		model.addAttribute("totalFinal",totalFinal);
		model.addAttribute("totalSparepartAmt",totalSparepartAmt);
		model.addAttribute("totalLaborAmt",totalLaborAmt);
		model.addAttribute("totalTyreAmt",totalTyreAmt);
		model.addAttribute("totalOilAmt",totalOilAmt);
		model.addAttribute("totalOtherAmt",totalOtherAmt);
		model.addAttribute("totalAmt",totalAmt);

		return "modules/sco/admin/adminFinalStatistic";
	}

	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = {"statistic/export"})
	public String statisticExport(ScoFinalReport scoFinalReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

		/** 计算每月份小计 */
		List<ScoFinalStatistic> dbList = scoFinalReportService.adminStatistic(scoFinalReport);
		for (ScoFinalStatistic statistic : dbList) {
			BigDecimal subtotalAmt = statistic.getSparepartAmt()
					.add(statistic.getLaborAmt())
					.add(statistic.getTyreAmt())
					.add(statistic.getOilAmt());
			statistic.setSubtotal(subtotalAmt); //小计金额
		}

		List<ScoFinalStatistic> list = new ArrayList<ScoFinalStatistic>();;
		ScoFinalStatistic statistic = null;

		Set<String> set = new HashSet<String>();


		//计算整体总计
		BigDecimal totalFinal = new BigDecimal(0); //结算单总数量
		BigDecimal totalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
		BigDecimal totalLaborAmt = new BigDecimal(0); //工时总金额(第二)
		BigDecimal totalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
		BigDecimal totalOilAmt = new BigDecimal(0); //机油总金额(第四)
		BigDecimal totalAmt = new BigDecimal(0); //商品总金额

		//计算供应商小计
		BigDecimal sTotalFinal; //验收单总数量
		BigDecimal sTotalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
		BigDecimal sTotalLaborAmt = new BigDecimal(0); //工时总金额(第二)
		BigDecimal sTotalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
		BigDecimal sTotalOilAmt = new BigDecimal(0); //机油总金额(第四)
		BigDecimal sTotalAmt; //商品总金额
		List<ScoFinalStatistic>  statisticList = null;//供应商组列表
		for (ScoFinalStatistic sfs : dbList) {
			/** 总计计算 */
			totalFinal = totalFinal.add(new BigDecimal(sfs.getFinalNumbers()));
			totalSparepartAmt = totalSparepartAmt.add(sfs.getSparepartAmt());
			totalLaborAmt = totalLaborAmt.add(sfs.getLaborAmt());
			totalTyreAmt = totalTyreAmt.add(sfs.getTyreAmt());
			totalOilAmt = totalOilAmt.add(sfs.getOilAmt());
			totalAmt = totalAmt.add(sfs.getSubtotal());
			boolean b = set.add(sfs.getCreateBy().getId());
			if (b) { //如果不重复
				/** 供应商小计计算 */
				statisticList = new ArrayList<ScoFinalStatistic>();
				statistic = new ScoFinalStatistic(); //新的统计对象
				statistic.setCreateBy(sfs.getCreateBy()); //放入用户信息
				sTotalFinal = new BigDecimal(0); //验收单总数量
				sTotalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
				sTotalLaborAmt = new BigDecimal(0); //工时总金额(第二)
				sTotalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
				sTotalOilAmt = new BigDecimal(0); //机油总金额(第四)
				sTotalAmt = new BigDecimal(0); //商品总金额

				sTotalFinal = sTotalFinal.add(new BigDecimal(sfs.getFinalNumbers()));
				sTotalSparepartAmt = sTotalSparepartAmt.add(sfs.getSparepartAmt());
				sTotalLaborAmt = sTotalLaborAmt.add(sfs.getLaborAmt());
				sTotalTyreAmt = sTotalTyreAmt.add(sfs.getTyreAmt());
				sTotalOilAmt = sTotalOilAmt.add(sfs.getOilAmt());
				sTotalAmt = sTotalAmt.add(sfs.getSubtotal());

				//放入小计
				statistic.setTotalFinal(sTotalFinal);
				statistic.setSparepartAmt(sTotalSparepartAmt);
				statistic.setLaborAmt(sTotalLaborAmt);
				statistic.setTyreAmt(sTotalTyreAmt);
				statistic.setOilAmt(sTotalOilAmt);
				statistic.setTotalAmt(sTotalAmt);
				statisticList.add(sfs);
				statistic.setStatisticList(statisticList);
				list.add(statistic); //添加至列表
			} else {
				statistic.getStatisticList().add(sfs);
				sTotalFinal = statistic.getTotalFinal().add(new BigDecimal(sfs.getFinalNumbers()));
				sTotalSparepartAmt = statistic.getSparepartAmt().add(sfs.getSparepartAmt());
				sTotalLaborAmt = statistic.getLaborAmt().add(sfs.getLaborAmt());
				sTotalTyreAmt = statistic.getTyreAmt().add(sfs.getTyreAmt());
				sTotalOilAmt = statistic.getOilAmt().add(sfs.getOilAmt());
				sTotalAmt = statistic.getTotalAmt().add(sfs.getSubtotal());
				//放入小计
				statistic.setTotalFinal(sTotalFinal);
				statistic.setSparepartAmt(sTotalSparepartAmt);
				statistic.setLaborAmt(sTotalLaborAmt);
				statistic.setTyreAmt(sTotalTyreAmt);
				statistic.setOilAmt(sTotalOilAmt);
				statistic.setTotalAmt(sTotalAmt);
			}
		}

		model.addAttribute("list", list);

		model.addAttribute("totalFinal",totalFinal);
		model.addAttribute("totalSparepartAmt",totalSparepartAmt);
		model.addAttribute("totalLaborAmt",totalLaborAmt);
		model.addAttribute("totalTyreAmt",totalTyreAmt);
		model.addAttribute("totalOilAmt",totalOilAmt);
		model.addAttribute("totalAmt",totalAmt);


		//导出excel
		List<AdminFinalExport> exportList = new ArrayList<AdminFinalExport>();
		for (ScoFinalStatistic sfs: list){
			AdminFinalExport export = new AdminFinalExport();

			DecimalFormat df = new DecimalFormat("### ##0.00") ;
			export.setSuppliers(sfs.getCreateBy().getName() + "\r\n" + "(销售总数量:"+sfs.getTotalFinal()+", 总金额:"+df.format(sfs.getTotalAmt())+")");

			//内部类数据
			List<ScoFinalStatistic> dataList = sfs.getStatisticList();
			List<AdminFinalExport.RowData> innerList = new ArrayList<AdminFinalExport.RowData>();
			for (ScoFinalStatistic data : dataList){
				AdminFinalExport.RowData rowData = export.new RowData();
				rowData.setMonth(data.getMonth());
				rowData.setFinalNumbers(data.getFinalNumbers());
				rowData.setSparepartAmt(data.getSparepartAmt());
				rowData.setLaborAmt(data.getLaborAmt());
				rowData.setTyreAmt(data.getTyreAmt());
				rowData.setOilAmt(data.getOilAmt());
				rowData.setTotalAmt(data.getSubtotal());
				innerList.add(rowData);
			}
			export.setRowDatas(innerList);
			exportList.add(export);
		}
		//总计
		AdminFinalExport export = new AdminFinalExport();
		export.setSuppliers("总计");
		AdminFinalExport.RowData rowData = export.new RowData();
		List<AdminFinalExport.RowData> innerList = new ArrayList<AdminFinalExport.RowData>();
		rowData.setMonth("");
		rowData.setFinalNumbers(totalFinal.intValue());
		rowData.setSparepartAmt(totalSparepartAmt);
		rowData.setLaborAmt(totalLaborAmt);
		rowData.setTyreAmt(totalTyreAmt);
		rowData.setOilAmt(totalOilAmt);
		rowData.setTotalAmt(totalAmt);
		innerList.add(rowData);
		export.setRowDatas(innerList);
		exportList.add(export);

		List<String> headerList = new ArrayList<String>();
		headerList.add("供应商");
		headerList.add("月份");
		headerList.add("结算单数量");
		headerList.add("零配件金额");
		headerList.add("工时金额");
		headerList.add("轮胎金额");
		headerList.add("机油金额");
		headerList.add("采购总金额");
		try {
			
			
			String title = null;
			if("3".equals(scoFinalReport.getType())){
				title = "物资类数据统计";
			}else if("4".equals(scoFinalReport.getType())){
				title = "车辆数据统计";
			}else if("5".equals(scoFinalReport.getType())){
				title = "图文数据统计";
			}else if("6".equals(scoFinalReport.getType())){
				title = "印刷数据统计";
			}
			
			String fileName = title+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			String year = scoFinalReport.getYear();
			new ExportExcelFinal(year + title, headerList).setDataList(exportList).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出统计数据失败！失败信息："+e.getMessage());
		}

		return "redirect:" + adminPath + "/admin/sco/scoFinalReport/statistic?type="+scoFinalReport.getType();
	}

	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoFinalReport scoFinalReport, RedirectAttributes redirectAttributes) {
		String searchParam = "&report.state="+scoFinalReport.getState()+"&createBy.id="+scoFinalReport.getCreateBy().getId()+"&report.department="+scoFinalReport.getDepartment();
		scoFinalReportService.passAudit(scoFinalReport);
		addMessage(redirectAttributes, "结算单审核通过");
		return "redirect:"+ Global.getAdminPath()+"/admin/sco/scoFinalReport/auditList/?type="+scoFinalReport.getType()+searchParam+"&repage";
	}
	
	
	
	
	
	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = "exportexcel")
	public String exportexcel(ScoFinalStatistic scoFinalStatistic,HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
//            String fileName = "结算单_"+DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm")+".xlsx";
            
            
            List<ScoFinalStatistic> list = new ArrayList<ScoFinalStatistic>();
            
            Page<ScoFinalStatistic> page = 
            		scoFinalReportService.findPageAuditList(new Page<ScoFinalStatistic>(1, 100), scoFinalStatistic);
            
            if(CollectionUtils.isNotEmpty(page.getList())){
            	list.addAll(page.getList());
            }
			
			logger.info("查询总页数：{},总记录数：{}",page.getTotalPage(),page.getCount());
			for (int i = 1; i < page.getTotalPage(); i++) {
				logger.info("开始查询第{}页数据",i+1);
				Page<ScoFinalStatistic> page2 = 
	            		scoFinalReportService.findPageAuditList(new Page<ScoFinalStatistic>(i+1, 100), scoFinalStatistic);
				if(CollectionUtils.isNotEmpty(page2.getList())){
		            list.addAll(page2.getList());
	            }
            
			}
			
			
			TypeEnum enum1 = TypeEnum.findById(scoFinalStatistic.getType());
			switch (enum1) {
			case cheliang:
				doVehicleExport(list, response);
				break;
			case yinshua:
				doPrintExport(list, response);
				break;
			case tuwen:
				doImageTextExport(list, response);
				break;

			default:
				break;
			}
			
    		return null;
		} catch (Exception e) {
			logger.error("导出验收单信息失败:",e);
			e.printStackTrace();
			addMessage(redirectAttributes, "验收单数据excel导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/admin/sco/scoAcceptanceReport/auditList?repage";
		
	}
	
	/**
	 * 导出车辆
	 * @param list
	 * @param response
	 * @throws IOException
	 */
	private void doVehicleExport(List<ScoFinalStatistic> list ,HttpServletResponse response) throws IOException{
		String fileName = "车辆结算单_"+DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm")+".xlsx";
		List<ScoFinalStatisticVehicleExport> exportList = new ArrayList<ScoFinalStatisticVehicleExport>();
		for (ScoFinalStatistic adminScoAcceptanceStatistic : list) {
			ScoFinalStatisticVehicleExport acceptanceStatisticExport  =new ScoFinalStatisticVehicleExport();
//			acceptanceStatisticExport.setGoodsNumbers(adminScoAcceptanceStatistic.getGoodsNumbers());
			if(adminScoAcceptanceStatistic.getReport()!=null){
				acceptanceStatisticExport.setBuyDate(adminScoAcceptanceStatistic.getReport().getBuyDate());
				acceptanceStatisticExport.setDepartment(adminScoAcceptanceStatistic.getReport().getDepartment());
				acceptanceStatisticExport.setInvoicenum(adminScoAcceptanceStatistic.getReport().getInvoicenum());
				acceptanceStatisticExport.setIdentifier(adminScoAcceptanceStatistic.getReport().getIdentifier());
				
				StringBuilder sf = new StringBuilder();
				if(adminScoAcceptanceStatistic.getReport().getSerTreeId()!=null
						&&adminScoAcceptanceStatistic.getReport().getSerTreeId().getName()!=null){
					sf.append(adminScoAcceptanceStatistic.getReport().getSerTreeId().getName());
				}
				if(adminScoAcceptanceStatistic.getReport().getSubId()!=null
						&&adminScoAcceptanceStatistic.getReport().getSubId().getName()!=null){
					sf.append(">");
					sf.append(adminScoAcceptanceStatistic.getReport().getSubId().getName());
				}
				if(adminScoAcceptanceStatistic.getReport().getThirdId()!=null
						&&adminScoAcceptanceStatistic.getReport().getThirdId().getName()!=null){
					sf.append(">");
					sf.append(adminScoAcceptanceStatistic.getReport().getThirdId().getName());
				}
				acceptanceStatisticExport.setVehicleType(sf.toString());
				
				if(adminScoAcceptanceStatistic.getReport().getState()!=null){
					acceptanceStatisticExport.setState(DictUtils.getDictLabel(adminScoAcceptanceStatistic.getReport().getState(), "audit_report", "0"));
				}
			}
			if(adminScoAcceptanceStatistic.getCreateBy()!=null){
				acceptanceStatisticExport.setName(adminScoAcceptanceStatistic.getCreateBy().getName());
			}
			if(adminScoAcceptanceStatistic.getSubtotal()!=null){
				DecimalFormat df = new DecimalFormat("### ##0.00") ;
				acceptanceStatisticExport.setSubtotal(df.format(adminScoAcceptanceStatistic.getSubtotal()));
			}
			
			exportList.add(acceptanceStatisticExport);
		}
		new ExportExcel("车辆结算单", ScoFinalStatisticVehicleExport.class, 1).setDataList(exportList).write(response, fileName).dispose();
	}
	/**
	 * 导出印刷
	 * @param list
	 * @param response
	 * @throws IOException
	 */
	private void doPrintExport(List<ScoFinalStatistic> list ,HttpServletResponse response) throws IOException{
		String fileName = "印刷结算单_"+DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm")+".xlsx";
		List<ScoFinalStatisticPrintExport> exportList = new ArrayList<ScoFinalStatisticPrintExport>();
		for (ScoFinalStatistic adminScoAcceptanceStatistic : list) {
			ScoFinalStatisticPrintExport acceptanceStatisticExport  =new ScoFinalStatisticPrintExport();
//			acceptanceStatisticExport.setGoodsNumbers(adminScoAcceptanceStatistic.getGoodsNumbers());
			if(adminScoAcceptanceStatistic.getReport()!=null){
				acceptanceStatisticExport.setBuyDate(adminScoAcceptanceStatistic.getReport().getBuyDate());
				acceptanceStatisticExport.setDepartment(adminScoAcceptanceStatistic.getReport().getDepartment());
				acceptanceStatisticExport.setInvoicenum(adminScoAcceptanceStatistic.getReport().getInvoicenum());
//				acceptanceStatisticExport.setIdentifier(adminScoAcceptanceStatistic.getReport().getIdentifier());
//				
//				StringBuilder sf = new StringBuilder();
//				if(adminScoAcceptanceStatistic.getReport().getSerTreeId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getSerTreeId().getName()!=null){
//					sf.append(adminScoAcceptanceStatistic.getReport().getSerTreeId().getName());
//				}
//				if(adminScoAcceptanceStatistic.getReport().getSubId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getSubId().getName()!=null){
//					sf.append(">");
//					sf.append(adminScoAcceptanceStatistic.getReport().getSubId().getName());
//				}
//				if(adminScoAcceptanceStatistic.getReport().getThirdId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getThirdId().getName()!=null){
//					sf.append(">");
//					sf.append(adminScoAcceptanceStatistic.getReport().getThirdId().getName());
//				}
//				acceptanceStatisticExport.setVehicleType(sf.toString());
				
				if(adminScoAcceptanceStatistic.getReport().getState()!=null){
					acceptanceStatisticExport.setState(DictUtils.getDictLabel(adminScoAcceptanceStatistic.getReport().getState(), "audit_report", "0"));
				}
			}
			if(adminScoAcceptanceStatistic.getCreateBy()!=null){
				acceptanceStatisticExport.setName(adminScoAcceptanceStatistic.getCreateBy().getName());
			}
			if(adminScoAcceptanceStatistic.getSubtotal()!=null){
				DecimalFormat df = new DecimalFormat("### ##0.00") ;
				acceptanceStatisticExport.setSubtotal(df.format(adminScoAcceptanceStatistic.getSubtotal()));
			}
			
			exportList.add(acceptanceStatisticExport);
		}
		new ExportExcel("印刷结算单", ScoFinalStatisticPrintExport.class, 1).setDataList(exportList).write(response, fileName).dispose();
	}
	/**
	 * 导出图文
	 * @param list
	 * @param response
	 * @throws IOException
	 */
	private void doImageTextExport(List<ScoFinalStatistic> list ,HttpServletResponse response) throws IOException{
		String fileName = "图文结算单_"+DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm")+".xlsx";
		List<ScoFinalStatisticImageTextExport> exportList = new ArrayList<ScoFinalStatisticImageTextExport>();
		for (ScoFinalStatistic adminScoAcceptanceStatistic : list) {
			ScoFinalStatisticImageTextExport acceptanceStatisticExport  =new ScoFinalStatisticImageTextExport();
//			acceptanceStatisticExport.setGoodsNumbers(adminScoAcceptanceStatistic.getGoodsNumbers());
			if(adminScoAcceptanceStatistic.getReport()!=null){
				acceptanceStatisticExport.setBuyDate(adminScoAcceptanceStatistic.getReport().getBuyDate());
				acceptanceStatisticExport.setDepartment(adminScoAcceptanceStatistic.getReport().getDepartment());
				acceptanceStatisticExport.setInvoicenum(adminScoAcceptanceStatistic.getReport().getInvoicenum());
//				acceptanceStatisticExport.setIdentifier(adminScoAcceptanceStatistic.getReport().getIdentifier());
				
//				StringBuilder sf = new StringBuilder();
//				if(adminScoAcceptanceStatistic.getReport().getSerTreeId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getSerTreeId().getName()!=null){
//					sf.append(adminScoAcceptanceStatistic.getReport().getSerTreeId().getName());
//				}
//				if(adminScoAcceptanceStatistic.getReport().getSubId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getSubId().getName()!=null){
//					sf.append(">");
//					sf.append(adminScoAcceptanceStatistic.getReport().getSubId().getName());
//				}
//				if(adminScoAcceptanceStatistic.getReport().getThirdId()!=null
//						&&adminScoAcceptanceStatistic.getReport().getThirdId().getName()!=null){
//					sf.append(">");
//					sf.append(adminScoAcceptanceStatistic.getReport().getThirdId().getName());
//				}
//				acceptanceStatisticExport.setVehicleType(sf.toString());
				
				if(adminScoAcceptanceStatistic.getReport().getState()!=null){
					acceptanceStatisticExport.setState(DictUtils.getDictLabel(adminScoAcceptanceStatistic.getReport().getState(), "audit_report", "0"));
				}
			}
			if(adminScoAcceptanceStatistic.getCreateBy()!=null){
				acceptanceStatisticExport.setName(adminScoAcceptanceStatistic.getCreateBy().getName());
			}
			if(adminScoAcceptanceStatistic.getSubtotal()!=null){
				DecimalFormat df = new DecimalFormat("### ##0.00") ;
				acceptanceStatisticExport.setSubtotal(df.format(adminScoAcceptanceStatistic.getSubtotal()));
			}
			
			exportList.add(acceptanceStatisticExport);
		}
		new ExportExcel("图文结算单", ScoFinalStatisticImageTextExport.class, 1).setDataList(exportList).write(response, fileName).dispose();
	}
}