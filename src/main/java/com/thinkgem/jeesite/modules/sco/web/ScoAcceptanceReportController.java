/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoFinalStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsAcceptance;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsAcceptanceService;
import com.thinkgem.jeesite.modules.sco.service.ScoPrintsService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.util.AmountUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceReport;
import com.thinkgem.jeesite.modules.sco.service.ScoAcceptanceReportService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 验收单Controller
 * @author thinkgem
 * @version 2015-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoAcceptanceReport")
public class ScoAcceptanceReportController extends BaseController {

	@Autowired
	private ScoAcceptanceReportService scoAcceptanceReportService;
	@Autowired
	private ScoGoodsAcceptanceService scoGoodsAcceptanceService;
	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private ScoPrintsService scoPrintsService;

	@ModelAttribute
	public ScoAcceptanceReport get(@RequestParam(required=false) String id) {
		ScoAcceptanceReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoAcceptanceReportService.get(id);
		}
		if (entity == null){
			entity = new ScoAcceptanceReport();
		}
		return entity;
	}

	@RequiresPermissions("sco:scoAcceptanceReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoAcceptanceReport scoAcceptanceReport, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ScoAcceptanceReport> page = scoAcceptanceReportService.findPage(new Page<ScoAcceptanceReport>(request, response), scoAcceptanceReport);
		scoAcceptanceReport.setCreateBy(scoAcceptanceReport.getCurrentUser());
		Page<ScoAcceptanceReport> page = scoAcceptanceReportService.findPageList(new Page<ScoAcceptanceReport>(request, response), scoAcceptanceReport);
		model.addAttribute("page", page);
		
		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("audit_report");
		model.addAttribute("auditReportList", auditReportList);
//		if("6".equals(scoAcceptanceReport.getType())
//				||"5".equals(scoAcceptanceReport.getType())){
//			return "modules/sco/scoPrintsReportList";
//		}else{
			return "modules/sco/scoAcceptanceReportList";
//		}
		
	}

	@RequiresPermissions("sco:scoAcceptanceReport:view")
	@RequestMapping(value = "form")
	public String form(ScoAcceptanceReport scoAcceptanceReport, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		scoAcceptanceReport.setCreateBy(scoAcceptanceReport.getCurrentUser());//设置当前用户
		ScoAcceptanceReport report = scoAcceptanceReportService.findUnFinish(scoAcceptanceReport);
		if(report != null){
			if(report.getId().equals(scoAcceptanceReport.getId())){
				return "modules/sco/scoAcceptanceReportForm";
			}
			if("6".equals(scoAcceptanceReport.getType())
					||"5".equals(scoAcceptanceReport.getType())){
				return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addPrints?id="+report.getId()+"&type="+scoAcceptanceReport.getType();
			}else{
				return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addGoods?id="+report.getId()+"&type="+scoAcceptanceReport.getType();
			}
		}
		return "modules/sco/scoAcceptanceReportForm";
	}

	/**
	 * 验收单内商品列表
	 * @param scoAcceptanceReport
	 * @param scoGoodsAcceptance
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = "addGoods")
	public String addGoods(ScoAcceptanceReport scoAcceptanceReport, ScoGoodsAcceptance scoGoodsAcceptance,HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		scoGoodsAcceptance.setArId(scoAcceptanceReport.getId());
		List<ScoGoodsAcceptance> list =  scoGoodsAcceptanceService.findList(scoGoodsAcceptance);
		model.addAttribute("list", list);

		BigDecimal totalAmt = new BigDecimal(0);
		for(ScoGoodsAcceptance goodsAcceptance:list){
			if(goodsAcceptance.getTotalPrice()!=null){
				totalAmt = totalAmt.add(goodsAcceptance.getTotalPrice());
			}
		}
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		return "modules/sco/scoGoodsAcceptanceList";
	}
	/**
	 * 验收单内商品列表
	 * @param scoAcceptanceReport
	 * @param scoGoodsAcceptance
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = "addPrints")
	public String addPrints(ScoAcceptanceReport scoAcceptanceReport, ScoGoodsAcceptance scoGoodsAcceptance,HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		scoGoodsAcceptance.setArId(scoAcceptanceReport.getId());
		List<ScoGoodsAcceptance> list = scoGoodsAcceptanceService.findPrintsList(scoGoodsAcceptance);	
		model.addAttribute("list", list);
		
		BigDecimal totalAmt = new BigDecimal(0);
		for(ScoGoodsAcceptance goodsAcceptance:list){
			if(goodsAcceptance.getTotalPrice()!=null){
				totalAmt = totalAmt.add(goodsAcceptance.getTotalPrice());
			}
		}
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		return "modules/sco/scoPrintsAcceptanceList";
	}

	@RequiresPermissions("sco:scoGoodsAcceptance:edit")
	@RequestMapping(value = "addGoodsFinish")
	public String addGoodsFinish(ScoAcceptanceReport scoAcceptanceReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoAcceptanceReport)){
			return form(scoAcceptanceReport, model);
		}
		scoAcceptanceReportService.enterIntoAudit(scoAcceptanceReport);
		addMessage(redirectAttributes, "添加验收单成功");
		
		return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/?repage"+"&type="+scoAcceptanceReport.getType();
	}

	/**
	 * 查看或打印验收单
	 * @param scoAcceptanceReport
	 * @param scoGoodsAcceptance
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequiresPermissions("sco:scoAcceptanceReport:edit")
//	@RequestMapping(value = "view")
	@RequestMapping(value = "report/{action}")
	public String view(@PathVariable String action, ScoAcceptanceReport scoAcceptanceReport, ScoGoodsAcceptance scoGoodsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		scoGoodsAcceptance.setArId(scoAcceptanceReport.getId());
		List<ScoGoodsAcceptance> list = scoGoodsAcceptanceService.findList(scoGoodsAcceptance);
		model.addAttribute("list", list);

		BigDecimal totalAmt = new BigDecimal(0);
		for(ScoGoodsAcceptance goodsAcceptance:list){
			if(goodsAcceptance.getTotalPrice()!=null){
				totalAmt = totalAmt.add(goodsAcceptance.getTotalPrice());
			}
		}
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		String returnUrl = "";
		if("view".equals(action)){
			returnUrl = "modules/sco/scoAcceptanceReportView";
		} else if("print".equals(action)){
			returnUrl = "modules/sco/scoAcceptanceReportPrint";
		}
		return returnUrl;
	}
	/**
	 * 查看或打印验收单
	 * @param scoAcceptanceReport
	 * @param scoGoodsAcceptance
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoAcceptanceReport:edit")
//	@RequestMapping(value = "view")
	@RequestMapping(value = "report/print/{action}")
	public String viewPrints(@PathVariable String action, ScoAcceptanceReport scoAcceptanceReport, ScoGoodsAcceptance scoGoodsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		scoGoodsAcceptance.setArId(scoAcceptanceReport.getId());
		List<ScoGoodsAcceptance> list = scoGoodsAcceptanceService.findPrintsList(scoGoodsAcceptance);
		model.addAttribute("list", list);
		
//		scoAcceptanceReport.setCurrentUser(scoAcceptanceReport.getCurrentUser());
		BigDecimal totalAmt = new BigDecimal(0);
		for(ScoGoodsAcceptance goodsAcceptance:list){
			if(goodsAcceptance.getTotalPrice()!=null){
				totalAmt = totalAmt.add(goodsAcceptance.getTotalPrice());
			}
			

			if(goodsAcceptance.getPrintsId()!=null){
				List<ScoSerTreeElement> elements = scoSerTreeService.queryDesc();
				scoPrintsService.fillElement(elements, goodsAcceptance.getPrintsId().getDescription());
//				model.addAttribute("descs", elements);
				goodsAcceptance.setElements(elements);
				
				//重新封装名称
			}
			
		}
		String totalAmtChinese = AmountUtil.digitUppercase(totalAmt.doubleValue());
		model.addAttribute("totalAmt",totalAmt);
		model.addAttribute("totalAmtChinese",totalAmtChinese);
		String returnUrl = "";
		if("view".equals(action)){
			returnUrl = "modules/sco/scoPrintsReportView";
		} else if("print".equals(action)){
			returnUrl = "modules/sco/scoPrintsReportPrint";
		}
		return returnUrl;
	}

	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = "report/qrCode")
	public void view(ScoAcceptanceReport scoAcceptanceReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoAcceptanceReport", scoAcceptanceReport);
		try {
			ZxingHandler.encode2("单据号:  "+scoAcceptanceReport.getSerialNumber(), 180, 180, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = "save")
	public String save(ScoAcceptanceReport scoAcceptanceReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoAcceptanceReport)){
			return form(scoAcceptanceReport, model);
		}
		scoAcceptanceReport.setCreateBy(scoAcceptanceReport.getCurrentUser());//设置当前用户
		scoAcceptanceReportService.save(scoAcceptanceReport);
		addMessage(redirectAttributes, "保存验收单成功");
		ScoAcceptanceReport report = scoAcceptanceReportService.findUnFinish(scoAcceptanceReport);
		
		
		String id = scoAcceptanceReport.getId();
		
		if(report != null){
			id = report.getId();
		}
		
		if("6".equals(scoAcceptanceReport.getType())
				||"5".equals(scoAcceptanceReport.getType())){
			return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addPrints?id="+id+"&type="+scoAcceptanceReport.getType();
		}else{
			return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addGoods?id="+id+"&type="+scoAcceptanceReport.getType();
		}
//		return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/?repage";
	}

	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoAcceptanceReport scoAcceptanceReport, RedirectAttributes redirectAttributes) {
		scoAcceptanceReportService.delete(scoAcceptanceReport);
		addMessage(redirectAttributes, "删除验收单成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/list?repage"+"&type="+scoAcceptanceReport.getType();
	}

	@RequiresPermissions("sco:adminAcceptanceReport:edit")
	@RequestMapping(value = "admin/delete")
	public String adminDelete(ScoAcceptanceReport scoAcceptanceReport, RedirectAttributes redirectAttributes) {
		scoAcceptanceReportService.delete(scoAcceptanceReport);
		addMessage(redirectAttributes, "删除验收单成功");
		return "redirect:"+Global.getAdminPath()+"/admin/sco/scoAcceptanceReport/auditList/?repage"+"&type="+scoAcceptanceReport.getType();
	}

	@RequiresPermissions("sco:adminAcceptanceReport:edit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoAcceptanceReport scoAcceptanceReport, RedirectAttributes redirectAttributes) {
		String searchParam = "report.state="+scoAcceptanceReport.getState()
				+"&createBy.id="+scoAcceptanceReport.getCreateBy().getId()
				+"&report.department="+scoAcceptanceReport.getDepartment()
				+"&type="+scoAcceptanceReport.getType();
		scoAcceptanceReportService.passAudit(scoAcceptanceReport);
		addMessage(redirectAttributes, "验收单审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/?repage";
		return "redirect:"+Global.getAdminPath()+"/admin/sco/scoAcceptanceReport/auditList/?"+searchParam+"&repage";
	}

	@RequiresPermissions("sco:scoAcceptanceReport:edit")
	@RequestMapping(value = {"statistic"})
	public String statistic(ScoAcceptanceStatistic scoAcceptanceStatistic, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoAcceptanceStatistic.setCreateBy(scoAcceptanceStatistic.getCurrentUser());
		List<ScoAcceptanceStatistic> yearList = scoAcceptanceReportService.getYear(scoAcceptanceStatistic);
		model.addAttribute("yearList", yearList);
		List<ScoAcceptanceStatistic> list = scoAcceptanceReportService.statistic(scoAcceptanceStatistic);
		model.addAttribute("list", list);
		//计算总计
		BigDecimal totalReceiving = new BigDecimal(0); //验收单总数量
		BigDecimal totalGoods = new BigDecimal(0); //商品总数量
		BigDecimal totalAmt = new BigDecimal(0); //商品总金额
		for(ScoAcceptanceStatistic acceptanceStatistic:list){
			totalReceiving = totalReceiving.add(new BigDecimal(acceptanceStatistic.getReceivingNumbers()));
			totalGoods = totalGoods.add(new BigDecimal(acceptanceStatistic.getGoodsNumbers()));
			totalAmt = totalAmt.add(acceptanceStatistic.getSubtotal());
		}
		
		
		try {
			Collections.sort(list, new Comparator<ScoAcceptanceStatistic>() {

				@Override
				public int compare(ScoAcceptanceStatistic o1, ScoAcceptanceStatistic o2) {
					return o1.getSubtotal().compareTo(o2.getSubtotal());
				}
			});
		} catch (Exception e) {
			logger.error("排序异常",e);
		}
		
		model.addAttribute("totalReceiving",totalReceiving);
		model.addAttribute("totalGoods",totalGoods);
		model.addAttribute("totalAmt",totalAmt);
		return "modules/sco/scoAcceptanceStatistic";
	}

}