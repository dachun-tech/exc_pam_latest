/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsStatistic;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsTreeService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoods;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsService;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表Controller
 * @author 段文昌
 * @version 2015-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoGoods")
public class ScoGoodsController extends BaseController {

	@Autowired
	private ScoGoodsService scoGoodsService;

	@Autowired
	private ScoGoodsTreeService scoGoodsTreeService;

	@Autowired
	private SystemService systemService;

	
	@ModelAttribute
	public ScoGoods get(@RequestParam(required=false) String id) {
		ScoGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoGoodsService.get(id);
		}
		if (entity == null){
			entity = new ScoGoods();
		}
		return entity;
	}

	@RequiresPermissions("sco:scoGoods:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScoGoods> page = scoGoodsService.findPage(new Page<ScoGoods>(request, response), scoGoods);
//		List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
//		model.addAttribute("goodsTreeList", goodsTreeList);
//		List<ScoGoods> list = page.getList();
//		for (ScoGoods goods : list) {
//			for (ScoGoodsTree tree : goodsTreeList) {
//				if (goods.getGoodsTreeId().equals(tree.getId())) {
//					goods.setGoodsTreeName(tree.getName());
//				}
//			}
//		}

		String directories = scoGoods.getCurrentUser().getDirectories();
		if(!StringUtils.isBlank(directories)){
			String[] ids = directories.split(",");
			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByIds(ids);
			List<ScoGoods> list = page.getList();
			for (ScoGoods goods : list){
				for (ScoGoodsTree tree : goodsTreeList){
					if(goods.getGoodsTreeId().equals(tree.getId())){
						goods.setGoodsTreeName(tree.getName());
					}
				}
			}
			model.addAttribute("goodsTreeList", goodsTreeList);
		}
		List<ScoGoodsTree> goodsSecTreeList = scoGoodsTreeService.getByParentId(scoGoods.getGoodsTreeId());
		model.addAttribute("goodsSecTreeList", goodsSecTreeList);
		model.addAttribute("page", page);
		return "modules/sco/scoGoodsList";
	}

//	/**
//	 * 支持目录分配功能
//	 * @param scoGoods
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions("sco:scoGoods:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ScoGoods> page = scoGoodsService.findPage(new Page<ScoGoods>(request, response), scoGoods);
//		String directories = scoGoods.getCurrentUser().getDirectories();
//		if(!StringUtils.isBlank(directories)){
//			String[] ids = directories.split(",");
//			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentIds(ids);
//			List<ScoGoods> list = page.getList();
//			for (ScoGoods goods : list){
//				for (ScoGoodsTree tree : goodsTreeList){
//					if(goods.getGoodsTreeId().equals(tree.getId())){
//						goods.setGoodsTreeName(tree.getName());
//					}
//				}
//			}
//			model.addAttribute("goodsTreeList", goodsTreeList);
//		}
//		model.addAttribute("page", page);
//		return "modules/sco/scoGoodsList";
//	}

	@RequiresPermissions("sco:scoGoods:view")
	@RequestMapping(value = "form")
	public String form(ScoGoods scoGoods, Model model) {
		if (scoGoods.getAgrtPrice() != null) {
			scoGoods.setAgrtPrice(scoGoods.getAgrtPrice().setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
		}
		if (scoGoods.getNormalPrice() != null) {
			scoGoods.setNormalPrice(scoGoods.getNormalPrice().setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
		}
		model.addAttribute("scoGoods", scoGoods);
//		List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
//		model.addAttribute("goodsTreeList", goodsTreeList);
		List<ScoGoodsTree> goodsSecTreeList = scoGoodsTreeService.getByParentId(scoGoods.getGoodsTreeId());
		model.addAttribute("goodsSecTreeList", goodsSecTreeList);
//		//支持目录权限部分，需求变动，注释
		String directories = scoGoods.getCurrentUser().getDirectories();
		if(!StringUtils.isBlank(directories)){
			String[] ids = directories.split(",");
//			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentIds(ids);
			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByIds(ids);
			model.addAttribute("goodsTreeList", goodsTreeList);
		}
		return "modules/sco/scoGoodsForm";
	}

	@RequiresPermissions("sco:scoGoods:view")
	@RequestMapping(value = "secMenuTree")
	@ResponseBody
	public Object secMenuTree(ScoGoods scoGoods, Model model) {
		List<ScoGoodsTree> goodsSecTreeList = scoGoodsTreeService.getByParentId(scoGoods.getGoodsTreeId());
		return goodsSecTreeList;
	}

	@RequiresPermissions("sco:scoGoods:edit")
	@RequestMapping(value = "save")
	public String save(ScoGoods scoGoods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoGoods)){
			return form(scoGoods, model);
		}
		scoGoods.setName(scoGoods.getSubName());
		scoGoodsService.save(scoGoods);
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoGoods/?repage";
	}
	
	@RequiresPermissions("sco:scoGoods:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoGoods scoGoods, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoGoodsService.delete(scoGoods);
		addMessage(redirectAttributes, "删除商品成功");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoGoods/?repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoGoods:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoGoods scoGoods, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoGoods, redirectAttributes,response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoGoods/auditList?repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}

	@RequiresPermissions("sco:scoGoods:view")
	@RequestMapping(value = {"view"})
	public String view(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoGoods", scoGoods);
		if (!StringUtils.isBlank(scoGoods.getPicUrl())){
			String[] picUrl = scoGoods.getPicUrl().split("\\|");
			List<String> picList = new ArrayList<String>();
			for(String url : picUrl){
				if(!StringUtils.isBlank(url)){
					picList.add(url);
				}
			}
			model.addAttribute("picList", picList);
		}
		return "modules/sco/scoGoodsView";
	}

	@RequiresPermissions("sco:scoGoods:view")
	@RequestMapping(value = {"statistic"})
	public String statistic(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoGoods.setCreateBy(scoGoods.getCurrentUser());
		List<ScoGoodsStatistic> list = scoGoodsService.statistic(scoGoods);
		List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
		model.addAttribute("goodsTreeList", goodsTreeList);
		for (ScoGoodsStatistic statistic : list) {
			for (ScoGoodsTree tree : goodsTreeList) {
				if (statistic.getGoodsTreeId().equals(tree.getId())) {
					statistic.setGoodsTreeName(tree.getName());
				}
			}
		}
		model.addAttribute("list", list);
		return "modules/sco/scoGoodsStatistic";
	}


	@RequiresPermissions("sco:adminScoGoods:edit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScoGoods> page = scoGoodsService.findPageList(new Page<ScoGoods>(request, response), scoGoods);
		List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
		model.addAttribute("goodsTreeList", goodsTreeList);
		List<ScoGoods> list = page.getList();
		for (ScoGoods goods : list) {
			for (ScoGoodsTree tree : goodsTreeList) {
				if (goods.getGoodsTreeId().equals(tree.getId())) {
					goods.setGoodsTreeName(tree.getName());
				}
			}
		}
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier","3");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);

		List<ScoGoodsTree> goodsSecTreeList = scoGoodsTreeService.getByParentId(scoGoods.getGoodsTreeId());
		model.addAttribute("goodsSecTreeList", goodsSecTreeList);
		model.addAttribute("page", page);
		return "modules/sco/scoGoodsAuditList";
	}

	/**
	 * 导出商品数据
	 * @param scoGoods
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sco:adminScoGoods:edit")
	@RequestMapping(value = "auditList/export", method= RequestMethod.POST)
	public String exportFile(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page exportPage = new Page<ScoGoods>(request, response, -1);
			exportPage.setPageSize(1000000000);
			Page<ScoGoods> page = scoGoodsService.findPageList(exportPage, scoGoods);
//			Page<ScoGoods> page = scoGoodsService.findPage(new Page<ScoGoods>(request, response, -1), scoGoods);
			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
			List<ScoGoods> list = page.getList();
			for (ScoGoods goods : list) {
				for (ScoGoodsTree tree : goodsTreeList) {
					if (goods.getGoodsTreeId().equals(tree.getId())) {
						goods.setGoodsTreeName(tree.getName());
					}
				}
			}
			new ExportExcel("商品数据", ScoGoods.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sco/scoGoods/auditList";
	}

	@RequiresPermissions("sco:adminScoGoods:edit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoGoods scoGoods, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		scoGoodsService.passAudit(scoGoods);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoGoods/auditList?repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}

	@RequiresPermissions("sco:adminScoGoods:edit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoGoods scoGoods, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		scoGoodsService.refuseAudit(scoGoods);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoGoods/auditList?repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}

}