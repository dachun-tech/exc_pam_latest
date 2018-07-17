/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoods;
import com.thinkgem.jeesite.modules.sco.entity.ScoPrints;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.service.ScoSparepartService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 零配件维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoSparepart")
public class ScoSparepartController extends BaseController {

	@Autowired
	private ScoSparepartService scoSparepartService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public ScoSparepart get(@RequestParam(required=false) String id) {
		ScoSparepart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoSparepartService.get(id);
		}
		if (entity == null){
			entity = new ScoSparepart();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoSparepart:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoSparepart scoSparepart, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoSparepart.setCreateBy(scoSparepart.getCurrentUser());
		Page<ScoSparepart> page = scoSparepartService.findPage(new Page<ScoSparepart>(request, response), scoSparepart);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoSparepart/list");
		
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoSparepart.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoSparepart.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoSparepart.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoSparepart.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoSparepartList";
	}
	@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = {"auditList"})
	public String auditlist(ScoSparepart scoSparepart, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		//TODO:此处后续要加数据权限
		//scoSparepart.setCreateBy(scoSparepart.getCurrentUser());
		Page<ScoSparepart> page = scoSparepartService.findPage(new Page<ScoSparepart>(request, response), scoSparepart);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoSparepart/auditList");
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoSparepart.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoSparepart.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoSparepart.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoSparepart.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoSparepartList";
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
	/**@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoSparepart scoSparepart, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		scoSparepart.setCreateBy(scoSparepart.getCurrentUser());
		Page<ScoSparepart> page = scoSparepartService.findPage(new Page<ScoSparepart>(request, response), scoSparepart);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoSparepart/auditList");

		model.addAttribute("sco", "scoSparepart");
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//商品种类
		List<Dict> kindList = DictUtils.getDictList("kind_list_"+scoSparepart.getType());
		model.addAttribute("kindList", kindList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoSparepart.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoSparepart.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoSparepart.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoSparepart.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoCarAuditList";
	}
	**/
	
	@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoSparepart scoSparepart, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoSparepart.getState()+"&createBy.id="+scoSparepart.getCreateBy().getId();
		scoSparepart.setState(Integer.valueOf(DictUtils.getDictValue("审核通过","product_audit","1")));
		scoSparepartService.audit(scoSparepart);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoSparepart/auditList?type="+scoSparepart.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoSparepart scoSparepart, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoSparepart.getState()+"&createBy.id="+scoSparepart.getCreateBy().getId();
		scoSparepart.setState(Integer.valueOf(DictUtils.getDictValue("审核不通过","product_audit","3")));
		scoSparepartService.audit(scoSparepart);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoSparepart/auditList?type="+scoSparepart.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}

	
	
	@RequiresPermissions("sco:scoSparepart:view")
	@RequestMapping(value = "form")
	public String form(ScoSparepart scoSparepart, ScoSerTree scoSerTree, Model model) {
		model.addAttribute("scoSparepart", scoSparepart);
		model.addAttribute("scoSerTree",scoSerTree);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoSparepart.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoSparepart.getSerTreeId() != null){
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoSparepart.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoSparepartForm";
	}
	@RequiresPermissions("sco:scoSparepart:view")
	@RequestMapping(value = "formView")
	public String formView(ScoSparepart scoSparepart, ScoSerTree scoSerTree, Model model) {
		this.form(scoSparepart, scoSerTree, model);
		return "modules/sco/scoSparepartFormView";
	}

	@RequiresPermissions("sco:scoSparepart:edit")
	@RequestMapping(value = "save")
	public String save(ScoSparepart scoSparepart,ScoSerTree scoSerTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoSparepart)){
			return form(scoSparepart,scoSerTree, model);
		}
		scoSparepartService.save(scoSparepart);
		String lableTitle = DictUtils.getDictValue("lable_title", "first_form_"+scoSparepart.getType(), "");
		addMessage(redirectAttributes, "保存"+lableTitle+"成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoSparepart/list?type="+scoSparepart.getType()+"&repage";
	}
	
	@RequiresPermissions("sco:scoSparepart:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoSparepart scoSparepart, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoSparepartService.delete(scoSparepart);
		String lableTitle = DictUtils.getDictValue("lable_title", "first_form_"+scoSparepart.getType(), "");
		addMessage(redirectAttributes, "删除"+lableTitle+"成功");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoSparepart/list?type="+scoSparepart.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoSparepart scoSparepart, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoSparepart, redirectAttributes, response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoSparepart/auditList?type="+scoSparepart.getType()+"&repage";
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
	@RequiresPermissions("sco:scoSparepart:audit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoSparepart scoSparepart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoSparepart> list = scoSparepartService.findList(scoSparepart);
			
			new ExportExcel("商品数据", ScoSparepart.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return null;
	}

}