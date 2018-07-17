/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerFinal;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.service.ScoSerFinalService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.util.AmountUtil;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalReport;
import com.thinkgem.jeesite.modules.sco.service.ScoFinalReportService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 结算单表维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoFinalReport")
public class ScoFinalReportController extends BaseController {

	@Autowired
	private ScoFinalReportService scoFinalReportService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;

	@Autowired
	private ScoSerFinalService scoSerFinalService;
	
	@ModelAttribute
	public ScoFinalReport get(@RequestParam(required=false) String id) {
		ScoFinalReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoFinalReportService.get(id);
		}
		if (entity == null){
			entity = new ScoFinalReport();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoFinalReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoFinalReport scoFinalReport, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ScoFinalReport> page = scoFinalReportService.findPage(new Page<ScoFinalReport>(request, response), scoFinalReport);
		scoFinalReport.setCreateBy(scoFinalReport.getCurrentUser());
		Page<ScoFinalReport> page = scoFinalReportService.findPageList(new Page<ScoFinalReport>(request, response), scoFinalReport);
		model.addAttribute("page", page);
		List<String> yearList = scoFinalReportService.getYear(scoFinalReport.getCurrentUser());
		model.addAttribute("yearList", yearList);
		List<String> monthList = new ArrayList<String>();
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
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("audit_report");
		model.addAttribute("auditReportList", auditReportList);
		
		return "modules/sco/scoFinalReportList";
	}

	@RequiresPermissions("sco:scoFinalReport:view")
	@RequestMapping(value = "form")
	public String form(ScoFinalReport scoFinalReport, ScoSerTree scoSerTree, Model model) {
		model.addAttribute("scoFinalReport", scoFinalReport);
		model.addAttribute("scoSerTree",scoSerTree);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoFinalReport.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoFinalReport.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoFinalReport.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		if(scoFinalReport.getSubId() != null){ // 三级目录
			List<ScoSerTree> serThirdTreeList = scoSerTreeService.getByParentId(scoFinalReport.getSubId().getId());
			model.addAttribute("serThirdTreeList", serThirdTreeList);
		}
		scoFinalReport.setCreateBy(scoFinalReport.getCurrentUser());//设置本人
		ScoFinalReport report = scoFinalReportService.findUnFinish(scoFinalReport);
		
		

		ScoSerTree parentTree2 = new ScoSerTree();
		parentTree2.setId(ScoGlobal.TREE_TOP_LEVEL);
		parentTree2.setParent(parentTree2);
//		parentTree2.setSubType("paper");
		parentTree2.setSubType(DictUtils.getDictValue("tree_2","supplier_tree_"+scoFinalReport.getType(),"xxxx"));
		List<ScoSerTree> paperSerTreeList = scoSerTreeService.getByParentId(parentTree2);
		model.addAttribute("paperSerTreeList", paperSerTreeList);
		

		model.addAttribute("descs", scoSerTreeService.queryDesc());
		
		if(report != null){
			if(report.getId().equals(scoFinalReport.getId())){
				return "modules/sco/scoFinalReportForm";
			}
			return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/addSer?id="+report.getId();
		}
		return "modules/sco/scoFinalReportForm";
	}

	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "save")
	public String save(ScoFinalReport scoFinalReport, ScoSerTree scoSerTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoFinalReport)){
			return form(scoFinalReport, scoSerTree, model);
		}
		
		if(StringUtils.isNoneBlank(scoFinalReport.getQuantity())){
			BigDecimal bd = new BigDecimal(scoFinalReport.getQuantity()); 
			bd = scoFinalReport.getPrice().multiply(bd);
			if(bd.doubleValue() > 100000){
				addMessage(model, "总金额不能超过100000");
				return form(scoFinalReport, scoSerTree, model);
			}
		}

		StringBuilder sf  =new StringBuilder();
		if("6".equals(scoFinalReport.getType())){
			if(scoFinalReport.getDescs()!=null){
				for (int i = 0; i < scoFinalReport.getDescs().length; i++) {
					sf.append(scoFinalReport.getDescs()[i]);
					if(i!=scoFinalReport.getDescs().length-1){
						sf.append(",");
					}
				}
			}
			scoFinalReport.setDescription(sf.toString());
		}
		
		scoFinalReportService.save(scoFinalReport);
		addMessage(redirectAttributes, "保存结算单成功");
		scoFinalReport.setCreateBy(scoFinalReport.getCurrentUser());//设置本人
		ScoFinalReport report = scoFinalReportService.findUnFinish(scoFinalReport);
		if(report != null){
			return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/addSer?id="+report.getId();
		}
//		return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/list?type="+scoFinalReport.getType()+"&repage";
		return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/addSer?id="+scoFinalReport.getId();
	}

	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoFinalReport scoFinalReport, RedirectAttributes redirectAttributes) {
		scoFinalReportService.delete(scoFinalReport);
		addMessage(redirectAttributes, "删除结算单成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/list?type="+scoFinalReport.getType()+"&repage";
	}

	@RequiresPermissions("sco:adminScoFinalReport:edit")
	@RequestMapping(value = "admin/delete")
	public String adminDelete(ScoFinalReport scoFinalReport, RedirectAttributes redirectAttributes) {
		scoFinalReportService.delete(scoFinalReport);
		addMessage(redirectAttributes, "删除结算单成功");
		return "redirect:"+Global.getAdminPath()+"/admin/sco/scoFinalReport/auditList/?type="+scoFinalReport.getType()+"&repage";
	}

	/**
	 * 添加服务
	 * @param scoFinalReport
	 * @param scoSerFinal
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "addSer")
	public String addSer(ScoFinalReport scoFinalReport, ScoSerFinal scoSerFinal, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoFinalReport", scoFinalReport);
		scoSerFinal.setReportId(scoFinalReport.getId());
		//第一级别
		scoSerFinal.setSerType("1");
		List<ScoSerFinal> firstList = scoSerFinalService.findFirstList(scoSerFinal);
		model.addAttribute("firstList", firstList);
		//第二级别
		scoSerFinal.setSerType("2");
		List<ScoSerFinal> secondList = scoSerFinalService.findSecondList(scoSerFinal);
		model.addAttribute("secondList", secondList);
		//第三级别
		scoSerFinal.setSerType("3");
		List<ScoSerFinal> thirdList = scoSerFinalService.findThirdList(scoSerFinal);
		model.addAttribute("thirdList", thirdList);
		//第四级别
		scoSerFinal.setSerType("4");
		List<ScoSerFinal> forthList = scoSerFinalService.findForthList(scoSerFinal);
		model.addAttribute("forthList", forthList);
		//第四级别
//		scoSerFinal.setSerType("5");
//		List<ScoSerFinal> fiveList = scoSerFinalService.findFiveList(scoSerFinal);
//		model.addAttribute("fiveList", fiveList);

		BigDecimal totalAmt = new BigDecimal(0);
		for(ScoSerFinal serFinal:firstList){
			if(serFinal.getTotalPrice()!=null){
				totalAmt = totalAmt.add(serFinal.getTotalPrice());
			}
		}
		for(ScoSerFinal serFinal:secondList){
			if(serFinal.getTotalPrice()!=null){
				totalAmt = totalAmt.add(serFinal.getTotalPrice());
			}
		}
		for(ScoSerFinal serFinal:thirdList){
			if(serFinal.getTotalPrice()!=null){
				totalAmt = totalAmt.add(serFinal.getTotalPrice());
			}
		}
		for(ScoSerFinal serFinal:forthList){
			if(serFinal.getTotalPrice()!=null){
				totalAmt = totalAmt.add(serFinal.getTotalPrice());
			}
		}
//		for(ScoSerFinal serFinal:fiveList){
//			if(serFinal.getTotalPrice()!=null){
//				totalAmt = totalAmt.add(serFinal.getTotalPrice());
//			}
//		}
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		return "modules/sco/scoSerFinalList";
	}

	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "addSerFinish")
	public String addSerFinish(ScoFinalReport scoFinalReport, Model model, RedirectAttributes redirectAttributes) {

		scoFinalReportService.enterIntoAudit(scoFinalReport);
		addMessage(redirectAttributes, "添加结算单成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/list?type="+scoFinalReport.getType()+"&repage";
	}

	/**
	 * 查看或打印结算单
	 * @param scoFinalReport
	 * @param scoSerFinal
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "report/{action}")
	public String view(@PathVariable String action, ScoFinalReport scoFinalReport, ScoSerFinal scoSerFinal, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoFinalReport", scoFinalReport);
		scoSerFinal.setReportId(scoFinalReport.getId());
		//第一级别
		scoSerFinal.setSerType("1");
		List<ScoSerFinal> firstList = scoSerFinalService.findFirstList(scoSerFinal);
		model.addAttribute("firstList", firstList);
		//第二级别
		scoSerFinal.setSerType("2");
		List<ScoSerFinal> secondList = scoSerFinalService.findSecondList(scoSerFinal);
		model.addAttribute("secondList", secondList);
		//第三级别
		scoSerFinal.setSerType("3");
		List<ScoSerFinal> thirdList = scoSerFinalService.findThirdList(scoSerFinal);
		model.addAttribute("thirdList", thirdList);
		//第四级别
		scoSerFinal.setSerType("4");
		List<ScoSerFinal> forthList = scoSerFinalService.findForthList(scoSerFinal);
		model.addAttribute("forthList", forthList);
		//第五级别
//		scoSerFinal.setSerType("5");
//		List<ScoSerFinal> fiveList = scoSerFinalService.findFiveList(scoSerFinal);
//		model.addAttribute("fiveList",fiveList);

		List<ScoSerFinal> materialsList = new ArrayList<ScoSerFinal>(); //材料List集合
		materialsList.addAll(firstList);
		materialsList.addAll(thirdList);
		materialsList.addAll(forthList);
		model.addAttribute("materialsList", materialsList);

		List<ScoSerFinal> laborList = new ArrayList<ScoSerFinal>(); //工时List集合
		laborList.addAll(secondList);
		model.addAttribute("laborList", laborList);


		BigDecimal totalAmt = new BigDecimal(0);	//全部费用
		BigDecimal materialsCost = new BigDecimal(0); //材料费
		BigDecimal laborCost = new BigDecimal(0); //工时费
		BigDecimal firstCost = new BigDecimal(0); //第一项费
		BigDecimal thirdCost = new BigDecimal(0); //第三项费
		BigDecimal forthCost = new BigDecimal(0); //第四项费
		BigDecimal allAmt = new BigDecimal(0);	//全部所有费用 = 全部费用 + 其它费用
		for(ScoSerFinal serFinal:firstList){
			totalAmt = totalAmt.add(serFinal.getTotalPrice());
			materialsCost = materialsCost.add(serFinal.getTotalPrice());//材料
			firstCost = firstCost.add(serFinal.getTotalPrice());//第一项费
		}
		for(ScoSerFinal serFinal:secondList){
			totalAmt = totalAmt.add(serFinal.getTotalPrice());
			laborCost = laborCost.add(serFinal.getTotalPrice());//工时
		}
		for(ScoSerFinal serFinal:thirdList){
			totalAmt = totalAmt.add(serFinal.getTotalPrice());
			materialsCost = materialsCost.add(serFinal.getTotalPrice());//材料
			thirdCost = thirdCost.add(serFinal.getTotalPrice());//第三项费
		}
		for(ScoSerFinal serFinal:forthList){
			totalAmt = totalAmt.add(serFinal.getTotalPrice());
			materialsCost = materialsCost.add(serFinal.getTotalPrice());//材料
			forthCost = forthCost.add(serFinal.getTotalPrice());//第四项费
		}
//		for(ScoSerFinal serFinal:fiveList){
//			totalAmt = totalAmt.add(serFinal.getTotalPrice());
//			materialsCost = materialsCost.add(serFinal.getTotalPrice());//材料
//			forthCost = forthCost.add(serFinal.getTotalPrice());//第四项费
//		}
		if(scoFinalReport.getOtherPrice() != null){//全部所有费用 = 全部费用 + 其它费用
			allAmt = totalAmt.add(scoFinalReport.getOtherPrice());
		} else {
			allAmt = totalAmt;
		}
		
		if(scoFinalReport.getQuantity()!=null&&scoFinalReport.getPrice()!=null){
			BigDecimal bd = BigDecimal.valueOf(Integer.valueOf(scoFinalReport.getQuantity()));
			allAmt = totalAmt.add(bd.multiply(scoFinalReport.getPrice()));
		}
		
		//四项费用
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		//材料费用
		String materialsCostChinese = AmountUtil.digitUppercase(materialsCost.doubleValue());
		model.addAttribute("materialsCost",materialsCost);
		model.addAttribute("materialsCostChinese",materialsCostChinese);
		//第二项费用(工时费)
		String laborCostChinese = AmountUtil.digitUppercase(laborCost.doubleValue());
		model.addAttribute("laborCost",laborCost);
		model.addAttribute("laborCostChinese",laborCostChinese);

		//印刷,图文,软件,视频所需数据计算
		model.addAttribute("firstCost",firstCost);//1
		model.addAttribute("thirdCost",thirdCost);//3
		model.addAttribute("forthCost",forthCost);//4
		//全部费用
		String allAmtChinese = AmountUtil.digitUppercase(allAmt.doubleValue());
		model.addAttribute("allAmt",allAmt);
		model.addAttribute("allAmtChinese",allAmtChinese);

		String returnUrl = "";
		String type = scoFinalReport.getType();
		String role4 = DictUtils.getDictValue("车辆维修","sys_supplier","4");
		String role5 = DictUtils.getDictValue("图文制作","sys_supplier","5");
		String role6 = DictUtils.getDictValue("印刷","sys_supplier","6");
		String role8 = DictUtils.getDictValue("视频制作","sys_supplier","8");
		String role9 = DictUtils.getDictValue("软件开发","sys_supplier","9");
		
		
		
		List<ScoSerTreeElement> elements = scoSerTreeService.queryDesc();
		this.fillElement(elements, scoFinalReport.getDescription());
		model.addAttribute("descs", elements);
		
		//车辆页面地址
		if(role4.equals(type)) {
//			if("view".equals(action)){
				returnUrl = "modules/sco/scoFinalReportView";
//			} else if("print".equals(action)){
//				returnUrl = "modules/sco/scoFinalReportPrint";
//			}
		}
		//印刷,图文页面地址
		if(role5.equals(type) || role6.equals(type)){
			returnUrl = "modules/sco/scoFinalReportImgTextView";
		}
		//视频页面地址
		if(role8.equals(type)) {
			returnUrl = "modules/sco/scoFinalReportVideoView";
		}
		//软件页面地址
		if(role9.equals(type)) {
			returnUrl = "modules/sco/scoFinalReportSoftView";
		}
		return returnUrl;
	}

	
	public  void fillElement(List<ScoSerTreeElement> elements,String desc){
		
		if(CollectionUtils.isNotEmpty(elements)&&desc!=null){
			String[] array = desc.split(",");
			List<String> list = new ArrayList<String>();
			Collections.addAll(list, array);
			
			for (ScoSerTreeElement scoSerTreeElement : elements) {
				boolean flag =false;
				if(CollectionUtils.isNotEmpty(scoSerTreeElement.getChildren())){
					for (ScoSerTreeElement children : scoSerTreeElement.getChildren()) {
						if(list.contains(children.getId())){
							children.setSelected(true);
							flag =true;
						}
					}
				}
				if(flag){
					scoSerTreeElement.setSelected(true);
				}
			}
		}
	}
	
	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "report/qrCode")
	public void qrCode(ScoFinalReport scoFinalReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoFinalReport", scoFinalReport);
		try {
			ZxingHandler.encode2("单据号:  "+scoFinalReport.getSerialNumber(), 180, 180, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = {"statistic"})
	public String statistic(ScoFinalReport scoFinalReport,ScoSerTree scoSerTree , HttpServletRequest request, HttpServletResponse response, Model model) {
		scoFinalReport.setCreateBy(scoFinalReport.getCurrentUser());
		List<String> yearList = scoFinalReportService.getYear(scoFinalReport.getCurrentUser());
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
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoFinalReport.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoFinalReport.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoFinalReport.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		
		List<ScoFinalStatistic> list = scoFinalReportService.statistic(scoFinalReport);
		//计算小计
		for (ScoFinalStatistic statistic : list) {
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
		
		try {
			Collections.sort(list, new Comparator<ScoFinalStatistic>() {

				@Override
				public int compare(ScoFinalStatistic o1, ScoFinalStatistic o2) {
					return o1.getSubtotal().compareTo(o2.getSubtotal());
				}
			});
		} catch (Exception e) {
			logger.error("排序异常",e);
		}
		model.addAttribute("list", list);

		//计算总计
		BigDecimal totalFinal = new BigDecimal(0); //结算单总数量
		BigDecimal totalSparepartAmt = new BigDecimal(0); //零配件总金额(第一)
		BigDecimal totalLaborAmt = new BigDecimal(0); //工时总金额(第二)
		BigDecimal totalTyreAmt = new BigDecimal(0); //轮胎总金额(第三)
		BigDecimal totalOilAmt = new BigDecimal(0); //机油总金额(第四)
		BigDecimal totalAmt = new BigDecimal(0); //商品总金额
		BigDecimal totalOtherAmt = new BigDecimal(0); //商品总金额
		for(ScoFinalStatistic statistic:list){
			totalFinal = totalFinal.add(new BigDecimal(statistic.getFinalNumbers()));
			totalSparepartAmt = totalSparepartAmt.add(statistic.getSparepartAmt());
			totalLaborAmt = totalLaborAmt.add(statistic.getLaborAmt());
			totalTyreAmt = totalTyreAmt.add(statistic.getTyreAmt());
			totalOilAmt = totalOilAmt.add(statistic.getOilAmt());
			totalOtherAmt = totalOtherAmt.add(statistic.getOtherAmt());
			totalAmt = totalAmt.add(statistic.getSubtotal());
		}
		model.addAttribute("totalFinal",totalFinal);
		model.addAttribute("totalSparepartAmt",totalSparepartAmt);
		model.addAttribute("totalLaborAmt",totalLaborAmt);
		model.addAttribute("totalTyreAmt",totalTyreAmt);
		model.addAttribute("totalOilAmt",totalOilAmt);
		model.addAttribute("totalOtherAmt",totalOtherAmt);
		model.addAttribute("totalAmt",totalAmt);
		return "modules/sco/scoFinalStatistic";
	}

}