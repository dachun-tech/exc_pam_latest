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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;

/**
 * 服务类目录列表Controller
 * @author 段文昌
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoSerTree")
public class ScoSerTreeController extends BaseController {

	@Autowired
	private ScoSerTreeService scoSerTreeService;
	
	@ModelAttribute
	public ScoSerTree get(@RequestParam(required=false) String id) {
		ScoSerTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoSerTreeService.get(id);
		}
		if (entity == null){
			entity = new ScoSerTree();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoSerTree:view")
	@RequestMapping(value = {"list/{type}", ""})
	public String list(@PathVariable String type, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoSerTree.setType(type);
		List<ScoSerTree> list = scoSerTreeService.findList(scoSerTree);
		model.addAttribute("list", list);
		model.addAttribute("scoSerTree",scoSerTree);
		return "modules/sco/scoSerTreeList";
	}

	@RequiresPermissions("sco:scoSerTree:view")
	@RequestMapping(value = "form")
	public String form(ScoSerTree scoSerTree, HttpServletRequest request, Model model) {
		if (scoSerTree.getParent()!=null && StringUtils.isNotBlank(scoSerTree.getParent().getId())){
			scoSerTree.setParent(scoSerTreeService.get(scoSerTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(scoSerTree.getId())){
				ScoSerTree scoSerTreeChild = new ScoSerTree();
				scoSerTreeChild.setParent(new ScoSerTree(scoSerTree.getParent().getId()));
				List<ScoSerTree> list = scoSerTreeService.findList(scoSerTree); 
				if (list.size() > 0){
					scoSerTree.setSort(list.get(list.size()-1).getSort());
					if (scoSerTree.getSort() != null){
						scoSerTree.setSort(scoSerTree.getSort() + 30);
					}
				}
			}
		}
		if (scoSerTree.getSort() == null){
			scoSerTree.setSort(30);
		}
		if (scoSerTree.getType() == null){
			String type = request.getParameter("type");
			if(!StringUtils.isBlank(type)){
				scoSerTree.setType(type);
			} else { //如果没有业务类型,那么抛出空页面
				return null;
			}
		}
		model.addAttribute("scoSerTree", scoSerTree);
		return "modules/sco/scoSerTreeForm";
	}

	@RequiresPermissions("sco:scoSerTree:edit")
	@RequestMapping(value = "save")
	public String save(ScoSerTree scoSerTree, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSerTree)){
			return form(scoSerTree, request, model);
		}
		scoSerTreeService.save(scoSerTree);
		addMessage(redirectAttributes, "保存目录成功");
		String type = scoSerTree.getType();
		String subType = scoSerTree.getSubType();
		String url = "redirect:"+Global.getAdminPath()+"/sco/scoSerTree/list/"+type+"?subType="+subType+"&repage";
		if(StringUtils.isBlank(subType)){
			url = "redirect:"+Global.getAdminPath()+"/sco/scoSerTree/list/"+type+"?repage";
		}
		return url;
	}
	
	@RequiresPermissions("sco:scoSerTree:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoSerTree scoSerTree, RedirectAttributes redirectAttributes) {
		scoSerTreeService.delete(scoSerTree);
		addMessage(redirectAttributes, "删除目录成功");
		String type = scoSerTree.getType();
		String subType = scoSerTree.getSubType();
		String url = "redirect:"+Global.getAdminPath()+"/sco/scoSerTree/list/"+type+"?subType="+subType+"&repage";
		if(StringUtils.isBlank(subType)){
			url = "redirect:"+Global.getAdminPath()+"/sco/scoSerTree/list/"+type+"?repage";
		}
		return url;
	}

	@RequiresPermissions("sco:scoSerTree:subView")
	@RequestMapping(value = "serSubTree")
	@ResponseBody
	public Object secSubTree(ScoSerTree scoSerTree, Model model) {
		List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoSerTree.getId());
		return serSubTreeList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ScoSerTree scoSerTree = new ScoSerTree();
		String type = request.getParameter("type");
		String subType = request.getParameter("subType");
		scoSerTree.setType(type);
		scoSerTree.setSubType(subType);
		List<ScoSerTree> list = scoSerTreeService.findList(scoSerTree);
		for (int i=0; i<list.size(); i++){
			ScoSerTree e = list.get(i);
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