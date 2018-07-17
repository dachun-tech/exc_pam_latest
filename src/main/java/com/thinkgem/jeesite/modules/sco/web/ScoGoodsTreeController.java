/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsTreeService;

/**
 * 商品属性Controller
 * @author thinkgem
 * @version 2015-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoGoodsTree")
public class ScoGoodsTreeController extends BaseController {

	@Autowired
	private ScoGoodsTreeService scoGoodsTreeService;
	
	@ModelAttribute
	public ScoGoodsTree get(@RequestParam(required=false) String id) {
		ScoGoodsTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoGoodsTreeService.get(id);
		}
		if (entity == null){
			entity = new ScoGoodsTree();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoGoodsTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoGoodsTree scoGoodsTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ScoGoodsTree> list = scoGoodsTreeService.findList(scoGoodsTree);
		model.addAttribute("list", list);
		return "modules/sco/scoGoodsTreeList";
	}

	@RequiresPermissions("sco:scoGoodsTree:view")
	@RequestMapping(value = "form")
	public String form(ScoGoodsTree scoGoodsTree, Model model) {
		if (scoGoodsTree.getParent()!=null && StringUtils.isNotBlank(scoGoodsTree.getParent().getId())){
			scoGoodsTree.setParent(scoGoodsTreeService.get(scoGoodsTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(scoGoodsTree.getId())){
				ScoGoodsTree scoGoodsTreeChild = new ScoGoodsTree();
				scoGoodsTreeChild.setParent(new ScoGoodsTree(scoGoodsTree.getParent().getId()));
				List<ScoGoodsTree> list = scoGoodsTreeService.findList(scoGoodsTree); 
				if (list.size() > 0){
					scoGoodsTree.setSort(list.get(list.size()-1).getSort());
					if (scoGoodsTree.getSort() != null){
						scoGoodsTree.setSort(scoGoodsTree.getSort() + 30);
					}
				}
			}
		}
		if (scoGoodsTree.getSort() == null){
			scoGoodsTree.setSort(30);
		}
		model.addAttribute("scoGoodsTree", scoGoodsTree);
		return "modules/sco/scoGoodsTreeForm";
	}

	@RequiresPermissions("sco:scoGoodsTree:edit")
	@RequestMapping(value = "save")
	public String save(ScoGoodsTree scoGoodsTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoodsTree)){
			return form(scoGoodsTree, model);
		}
		scoGoodsTreeService.save(scoGoodsTree);
		addMessage(redirectAttributes, "保存商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoodsTree/?repage";
	}
	
	@RequiresPermissions("sco:scoGoodsTree:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoGoodsTree scoGoodsTree, RedirectAttributes redirectAttributes) {
		scoGoodsTreeService.delete(scoGoodsTree);
		addMessage(redirectAttributes, "删除商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoodsTree/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ScoGoodsTree> list = scoGoodsTreeService.findList(new ScoGoodsTree());
		for (int i=0; i<list.size(); i++){
			ScoGoodsTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}