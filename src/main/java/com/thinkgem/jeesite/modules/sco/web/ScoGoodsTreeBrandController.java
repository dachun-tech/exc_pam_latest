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
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTreeBrand;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsTreeBrandService;

/**
 * 商品属性Controller
 * @author thinkgem
 * @version 2015-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoGoodsTreeBrand")
public class ScoGoodsTreeBrandController extends BaseController {

	@Autowired
	private ScoGoodsTreeBrandService scoGoodsTreeBrandService;
	
	@ModelAttribute
	public ScoGoodsTreeBrand get(@RequestParam(required=false) String id) {
		ScoGoodsTreeBrand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoGoodsTreeBrandService.get(id);
		}
		if (entity == null){
			entity = new ScoGoodsTreeBrand();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoGoodsTreeBrand:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoGoodsTreeBrand scoGoodsTreeBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScoGoodsTreeBrand> page = scoGoodsTreeBrandService.findPage(new Page<ScoGoodsTreeBrand>(request, response), scoGoodsTreeBrand); 
		model.addAttribute("page", page);
		return "modules/sco/scoGoodsTreeBrandList";
	}

	@RequiresPermissions("sco:scoGoodsTreeBrand:view")
	@RequestMapping(value = "form")
	public String form(ScoGoodsTreeBrand scoGoodsTreeBrand, Model model) {
		model.addAttribute("scoGoodsTreeBrand", scoGoodsTreeBrand);
		return "modules/sco/scoGoodsTreeBrandForm";
	}

	@RequiresPermissions("sco:scoGoodsTreeBrand:edit")
	@RequestMapping(value = "save")
	public String save(ScoGoodsTreeBrand scoGoodsTreeBrand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoodsTreeBrand)){
			return form(scoGoodsTreeBrand, model);
		}
		scoGoodsTreeBrandService.save(scoGoodsTreeBrand);
		addMessage(redirectAttributes, "保存商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoodsTreeBrand/?repage";
	}
	
	@RequiresPermissions("sco:scoGoodsTreeBrand:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoGoodsTreeBrand scoGoodsTreeBrand, RedirectAttributes redirectAttributes) {
		scoGoodsTreeBrandService.delete(scoGoodsTreeBrand);
		addMessage(redirectAttributes, "删除商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoodsTreeBrand/?repage";
	}

}