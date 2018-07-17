/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sco.entity.ScoAcceptanceReport;
import com.thinkgem.jeesite.modules.sco.service.ScoAcceptanceReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsAcceptance;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsAcceptanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 验收单商品列表Controller
 * @author 段文昌
 * @version 2015-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoGoodsAcceptance")
public class ScoGoodsAcceptanceController extends BaseController {

	@Autowired
	private ScoGoodsAcceptanceService scoGoodsAcceptanceService;
	@Autowired
	private ScoAcceptanceReportService scoAcceptanceReportService;

	@ModelAttribute
	public ScoGoodsAcceptance get(@RequestParam(required=false) String id) {
		ScoGoodsAcceptance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoGoodsAcceptanceService.get(id);
		}
		if (entity == null){
			entity = new ScoGoodsAcceptance();
		}
		return entity;
	}

	@RequiresPermissions("sco:scoGoodsAcceptance:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoGoodsAcceptance scoGoodsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScoGoodsAcceptance> page = scoGoodsAcceptanceService.findPage(new Page<ScoGoodsAcceptance>(request, response), scoGoodsAcceptance);
		model.addAttribute("page", page);
		return "modules/sco/scoGoodsAcceptanceList";
	}

	@RequiresPermissions("sco:scoGoodsAcceptance:view")
	@RequestMapping(value = "form")
	public String form(ScoGoodsAcceptance scoGoodsAcceptance, Model model) {
		model.addAttribute("scoGoodsAcceptance", scoGoodsAcceptance);
		return "modules/sco/scoGoodsAcceptanceForm";
	}

	@RequiresPermissions("sco:scoGoodsAcceptance:edit")
	@RequestMapping(value = "save")
	public String save(ScoGoodsAcceptance scoGoodsAcceptance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoodsAcceptance)){
			return form(scoGoodsAcceptance, model);
		}
		scoGoodsAcceptanceService.save(scoGoodsAcceptance);
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoodsAcceptance/?repage";
	}


	@RequiresPermissions("sco:scoGoodsAcceptance:edit")
	@RequestMapping(value = "updateGoodsAcceptance",method = RequestMethod.POST)
	@ResponseBody
	public Object updateGoodsAcceptance(ScoGoodsAcceptance scoGoodsAcceptance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoodsAcceptance)){
			return form(scoGoodsAcceptance, model);
		}
		scoGoodsAcceptanceService.updateNumbers(scoGoodsAcceptance);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code",0);
		map.put("message","success");
		return map;
	}

	/**
	 * 商品添加至验收单
	 * @param scoGoodsAcceptance
	 * @param model
	 * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("sco:scoGoodsAcceptance:edit")
	@RequestMapping(value = "addAcceptanceReport",method = RequestMethod.POST)
	@ResponseBody
	public Object addAcceptanceReport(ScoGoodsAcceptance scoGoodsAcceptance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoodsAcceptance)){
			return form(scoGoodsAcceptance, model);
		}
		scoGoodsAcceptanceService.save(scoGoodsAcceptance);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code",0);
		map.put("message","success");
		return map;
	}

	@RequiresPermissions("sco:scoGoodsAcceptance:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoGoodsAcceptance scoGoodsAcceptance, RedirectAttributes redirectAttributes) {
		scoGoodsAcceptanceService.delete(scoGoodsAcceptance);
		addMessage(redirectAttributes, "删除商品成功");
		
		
//		return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addGoods?id="+scoGoodsAcceptance.getArId();
		
		
		if("6".equals(scoGoodsAcceptance.getType())
				||"5".equals(scoGoodsAcceptance.getType())){
			List<ScoGoodsAcceptance> list = scoGoodsAcceptanceService.findPrintsList(scoGoodsAcceptance);
//			if(list == null || list.isEmpty()){
//				ScoAcceptanceReport scoAcceptanceReport = new ScoAcceptanceReport();
//				scoAcceptanceReport.setId(scoGoodsAcceptance.getArId());
//				scoAcceptanceReportService.delete(scoAcceptanceReport);
//				return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/form?type="+scoGoodsAcceptance.getType();
//			}
			return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addPrints?id="+scoGoodsAcceptance.getArId()+"&type="+scoGoodsAcceptance.getType();
		}else{
			List<ScoGoodsAcceptance> list = scoGoodsAcceptanceService.findList(scoGoodsAcceptance);
//			if(list == null || list.isEmpty()){
//				ScoAcceptanceReport scoAcceptanceReport = new ScoAcceptanceReport();
//				scoAcceptanceReport.setId(scoGoodsAcceptance.getArId());
//				scoAcceptanceReportService.delete(scoAcceptanceReport);
//				return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/form?type="+scoGoodsAcceptance.getType();
//			}
			return "redirect:"+Global.getAdminPath()+"/sco/scoAcceptanceReport/addGoods?id="+scoGoodsAcceptance.getArId()+"&type="+scoGoodsAcceptance.getType();
		}
		
	}

}