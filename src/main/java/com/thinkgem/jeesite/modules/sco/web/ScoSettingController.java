/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;
import com.thinkgem.jeesite.modules.sco.service.ScoSettingService;

import java.util.List;
import java.util.Map;

/**
 * 系统审核设置Controller
 * @author 段文昌
 * @version 2015-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoSetting")
public class ScoSettingController extends BaseController {

	@Autowired
	private ScoSettingService scoSettingService;
	
	@ModelAttribute
	public ScoSetting get(@RequestParam(required=false) String id) {
		ScoSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoSettingService.get(id);
		}
		if (entity == null){
			entity = new ScoSetting();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoSetting:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoSetting scoSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ScoSetting> list= scoSettingService.findList(scoSetting);
		model.addAttribute("list", list);
		return "modules/sco/scoSettingList";
	}

	@RequiresPermissions("sco:scoSetting:edit")
	@RequestMapping(value = "update")
	public String update(ScoSetting scoSetting, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSetting)){
			return form(scoSetting, model);
		}
		Map<String, String[]> map = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String id = entry.getKey().split("_")[1];
			String[] values = entry.getValue();
			scoSetting = scoSettingService.get(id);
			System.out.println(scoSetting.getName());
			System.out.println(scoSetting.getRemarks());
			scoSetting.setValue(values[0]);
			scoSettingService.save(scoSetting);
		}
		addMessage(redirectAttributes, "保存系统审核设置成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoSetting/list";
	}

	@RequiresPermissions("sco:scoSetting:edit")
	@RequestMapping(value = "form")
	public String form(ScoSetting scoSetting, Model model) {
		model.addAttribute("scoSetting", scoSetting);
		return "modules/sco/scoSettingForm";
	}

	@RequiresPermissions("sco:scoSetting:edit")
	@RequestMapping(value = "save")
	public String save(ScoSetting scoSetting, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSetting)){
			return form(scoSetting, model);
		}
		scoSettingService.save(scoSetting);
		addMessage(redirectAttributes, "保存系统审核设置成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoSetting/?repage";
	}
}