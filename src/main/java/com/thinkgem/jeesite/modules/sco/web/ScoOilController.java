/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sco.entity.ScoOil;
import com.thinkgem.jeesite.modules.sco.service.ScoOilService;

import java.util.List;

/**
 * 机油表维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoOil")
public class ScoOilController extends BaseController {

	@Autowired
	private ScoOilService scoOilService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public ScoOil get(@RequestParam(required=false) String id) {
		ScoOil entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoOilService.get(id);
		}
		if (entity == null){
			entity = new ScoOil();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoOil:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoOil scoOil, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoOil.setCreateBy(scoOil.getCurrentUser());
		Page<ScoOil> page = scoOilService.findPage(new Page<ScoOil>(request, response), scoOil);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoOil/list");
		model.addAttribute("sco", "scoOil");
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoOil.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_3","supplier_tree_"+scoOil.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoOilList";
	}

	@RequiresPermissions("sco:scoOil:view")
	@RequestMapping(value = "form")
	public String form(ScoOil scoOil,ScoSerTree scoSerTree,  Model model) {
		model.addAttribute("scoOil", scoOil);
		model.addAttribute("scoSerTree",scoSerTree);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_3","supplier_tree_"+scoOil.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoOilForm";
	}
	@RequiresPermissions("sco:scoOil:view")
	@RequestMapping(value = "formView")
	public String formView(ScoOil scoOil,ScoSerTree scoSerTree,  Model model) {
		this.form(scoOil, scoSerTree, model);
		return "modules/sco/scoOilFormView";
	}

	@RequiresPermissions("sco:scoOil:edit")
	@RequestMapping(value = "save")
	public String save(ScoOil scoOil, ScoSerTree scoSerTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoOil)){
			return form(scoOil,scoSerTree, model);
		}
		scoOilService.save(scoOil);
		String lableTitle = DictUtils.getDictValue("lable_title", "fourth_form_"+scoOil.getType(), "");
		addMessage(redirectAttributes, "保存"+lableTitle+"成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoOil/list?type="+scoOil.getType()+"&repage";
	}
	
	@RequiresPermissions("sco:scoOil:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoOil scoOil, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoOilService.delete(scoOil);
		String lableTitle = DictUtils.getDictValue("lable_title", "fourth_form_"+scoOil.getType(), "");
		addMessage(redirectAttributes, "删除"+lableTitle+"成功");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoOil/list?type="+scoOil.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoOil scoOil, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoOil, redirectAttributes,response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoOil/auditList?type="+scoOil.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	
	
	
	
	/**
	 * 查看审核商品
	 * @param scoSparepart
	 * @param scoSerTree
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoOil scoOil, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//TODO:数据权限
//		scoOil.setCreateBy(scoOil.getCurrentUser());
		Page<ScoOil> page = scoOilService.findPage(new Page<ScoOil>(request, response), scoOil);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoOil/auditList");
		model.addAttribute("sco", "scoOil");
		

		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoOil.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_3","supplier_tree_"+scoOil.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoOilList";
	
	}
	
	/**
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoOil scoOil, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		scoSparepart.setCreateBy(scoSparepart.getCurrentUser());
		Page<ScoOil> page = scoOilService.findPage(new Page<ScoOil>(request, response), scoOil);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoOil/auditList");
		model.addAttribute("sco", "scoOil");
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//商品种类
		List<Dict> kindList = DictUtils.getDictList("kind_list_"+scoOil.getType());
		model.addAttribute("kindList", kindList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoOil.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoOil.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoOil.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoOil.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoCarAuditList";
	}*/
	
	
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoOil scoOil, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoOil.getState()+"&createBy.id="+scoOil.getCreateBy().getId();
		scoOil.setState(Integer.valueOf(DictUtils.getDictValue("审核通过","product_audit","1")));
		scoOilService.audit(scoOil);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/ScoOil/auditList?type="+scoOil.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoOil scoOil, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoOil.getState()+"&createBy.id="+scoOil.getCreateBy().getId();
		scoOil.setState(Integer.valueOf(DictUtils.getDictValue("审核不通过","product_audit","3")));
		scoOilService.audit(scoOil);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/ScoOil/auditList?type="+scoOil.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	
	
	/**
	 * 导出商品数据
	 * @param scoGoods
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sco:scoOil:audit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoOil scoOil, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoOil> list = scoOilService.findList(scoOil);
			
			new ExportExcel("商品数据", ScoOil.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return null;
	}

}