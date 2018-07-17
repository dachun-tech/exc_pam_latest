/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sco.entity.ScoFinalReport;
import com.thinkgem.jeesite.modules.sco.service.ScoFinalReportService;
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
import com.thinkgem.jeesite.modules.sco.entity.ScoSerFinal;
import com.thinkgem.jeesite.modules.sco.service.ScoSerFinalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算单与服务类型表维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoSerFinal")
public class ScoSerFinalController extends BaseController {

	@Autowired
	private ScoSerFinalService scoSerFinalService;

	@Autowired
	private ScoFinalReportService scoFinalReportService;
	
	@ModelAttribute
	public ScoSerFinal get(@RequestParam(required=false) String id) {
		ScoSerFinal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoSerFinalService.get(id);
		}
		if (entity == null){
			entity = new ScoSerFinal();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoFinalReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoSerFinal scoSerFinal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScoSerFinal> page = scoSerFinalService.findPage(new Page<ScoSerFinal>(request, response), scoSerFinal); 
		model.addAttribute("page", page);
		return "modules/sco/scoSerFinalList";
	}

	@RequiresPermissions("sco:scoFinalReport:view")
	@RequestMapping(value = "form")
	public String form(ScoSerFinal scoSerFinal, Model model) {
		model.addAttribute("scoSerFinal", scoSerFinal);
		return "modules/sco/scoSerFinalForm";
	}

	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "save")
	public String save(ScoSerFinal scoSerFinal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSerFinal)){
			return form(scoSerFinal, model);
		}
		scoSerFinalService.save(scoSerFinal);
		addMessage(redirectAttributes, "保存服务类型成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoSerFinal/list?type="+scoSerFinal.getSerType()+"&repage";
	}
	
	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoSerFinal scoSerFinal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		scoSerFinalService.delete(scoSerFinal);
		addMessage(redirectAttributes, "删除服务类型成功");
		String type = request.getParameter("type");
		scoSerFinal.setSerType(null);
		scoSerFinal.setSerId(null);
		List<ScoSerFinal> list = scoSerFinalService.findList(scoSerFinal);
		if(list == null || list.isEmpty()){
			ScoFinalReport report = new ScoFinalReport();
			report.setId(scoSerFinal.getReportId());
			scoFinalReportService.delete(report);
			return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/form?type="+type;
		}
		return "redirect:"+Global.getAdminPath()+"/sco/scoFinalReport/addSer?id="+scoSerFinal.getReportId()+"&type="+type;
	}

	/**
	 * 服务添加至结算单
	 * @param scoSerFinal
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "addFinalReport",method = RequestMethod.POST)
	@ResponseBody
	public Object addFinalReport(ScoSerFinal scoSerFinal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSerFinal)){
			return form(scoSerFinal, model);
		}
		scoSerFinalService.save(scoSerFinal);
		System.out.println(scoSerFinal);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code",0);
		map.put("message","success");
		return map;
	}

	@RequiresPermissions("sco:scoFinalReport:edit")
	@RequestMapping(value = "updateSerFinal",method = RequestMethod.POST)
	@ResponseBody
	public Object updateGoodsAcceptance(ScoSerFinal ScoSerFinal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ScoSerFinal)){
			return form(ScoSerFinal, model);
		}
		scoSerFinalService.updateNumbers(ScoSerFinal);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code",0);
		map.put("message","success");
		return map;
	}

}