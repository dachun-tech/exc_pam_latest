/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.sco.entity.ScoLabor;
import com.thinkgem.jeesite.modules.sco.entity.ScoOil;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.service.ScoLaborService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 工时表维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoLabor")
public class ScoLaborController extends BaseController {

	@Autowired
	private ScoLaborService scoLaborService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public ScoLabor get(@RequestParam(required=false) String id) {
		ScoLabor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoLaborService.get(id);
		}
		if (entity == null){
			entity = new ScoLabor();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoLabor:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoLabor scoLabor,ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoLabor.setCreateBy(scoLabor.getCurrentUser());
		Page<ScoLabor> page = scoLaborService.findPage(new Page<ScoLabor>(request, response), scoLabor);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoLabor/list");
		model.addAttribute("sco", "scoLabor");
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);

		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoLabor.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoLabor.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoLabor.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoLabor.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		if(scoLabor.getSubId() != null){ // 三级目录
			List<ScoSerTree> serThirdTreeList = scoSerTreeService.getByParentId(scoLabor.getSubId().getId());
			model.addAttribute("serThirdTreeList", serThirdTreeList);
		}
		return "modules/sco/scoLaborList";
	}

	@RequiresPermissions("sco:scoLabor:view")
	@RequestMapping(value = "form")
	public String form(ScoLabor scoLabor, ScoSerTree scoSerTree, Model model) {
		model.addAttribute("scoLabor", scoLabor);
		model.addAttribute("scoSerTree",scoSerTree);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoLabor.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoLabor.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoLabor.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		if(scoLabor.getSubId() != null){ // 三级目录
			List<ScoSerTree> serThirdTreeList = scoSerTreeService.getByParentId(scoLabor.getSubId().getId());
			model.addAttribute("serThirdTreeList", serThirdTreeList);
		}
		return "modules/sco/scoLaborForm";
	}
	@RequiresPermissions("sco:scoLabor:view")
	@RequestMapping(value = "formView")
	public String formView(ScoLabor scoLabor, ScoSerTree scoSerTree, Model model) {
		this.form(scoLabor, scoSerTree, model);
		return "modules/sco/scoLaborFormView";
	}

	@RequiresPermissions("sco:scoLabor:edit")
	@RequestMapping(value = "save")
	public String save(ScoLabor scoLabor, ScoSerTree scoSerTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoLabor)){
			return form(scoLabor, scoSerTree, model);
		}
		scoLaborService.save(scoLabor);
		String lableTitle = DictUtils.getDictValue("lable_title", "second_form_"+scoLabor.getType(), "");
		addMessage(redirectAttributes, "保存"+lableTitle+"成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoLabor/list?type="+scoLabor.getType()+"&repage";
	}
	
	@RequiresPermissions("sco:scoLabor:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoLabor scoLabor, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoLaborService.delete(scoLabor);
		String lableTitle = DictUtils.getDictValue("lable_title", "second_form_"+scoLabor.getType(), "");
		addMessage(redirectAttributes, "删除"+lableTitle+"成功");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoLabor/list?type="+scoLabor.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoLabor scoLabor, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoLabor, redirectAttributes,response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoLabor/auditList?type="+scoLabor.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	
	
	
	/**
	 * 查看审核商品
	 * @param scoLabor
	 * @param scoSerTree
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoLabor scoLabor,ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		TODO:数据权限
		//scoLabor.setCreateBy(scoLabor.getCurrentUser());
		Page<ScoLabor> page = scoLaborService.findPage(new Page<ScoLabor>(request, response), scoLabor);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoLabor/auditList");
		model.addAttribute("sco", "scoLabor");

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);

		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoLabor.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoLabor.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoLabor.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoLabor.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		if(scoLabor.getSubId() != null){ // 三级目录
			List<ScoSerTree> serThirdTreeList = scoSerTreeService.getByParentId(scoLabor.getSubId().getId());
			model.addAttribute("serThirdTreeList", serThirdTreeList);
		}
		return "modules/sco/scoLaborList";
	}
	
	/**@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoLabor scoLabor, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		scoLabor.setCreateBy(scoLabor.getCurrentUser());
		Page<ScoLabor> page = scoLaborService.findPage(new Page<ScoLabor>(request, response), scoLabor);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoLabor/auditList");

		model.addAttribute("sco", "scoLabor");
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//商品种类
		List<Dict> kindList = DictUtils.getDictList("kind_list_"+scoLabor.getType());
		model.addAttribute("kindList", kindList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoLabor.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoLabor.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoLabor.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoLabor.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoCarAuditList";
	}*/
	
	
	@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoLabor scoLabor, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoLabor.getState()+"&createBy.id="+scoLabor.getCreateBy().getId();
		scoLabor.setState(Integer.valueOf(DictUtils.getDictValue("审核通过","product_audit","1")));
		scoLaborService.audit(scoLabor);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoLabor/auditList?type="+scoLabor.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoLabor scoLabor, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoLabor.getState()+"&createBy.id="+scoLabor.getCreateBy().getId();
		scoLabor.setState(Integer.valueOf(DictUtils.getDictValue("审核不通过","product_audit","3")));
		scoLaborService.audit(scoLabor);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoLabor/auditList?type="+scoLabor.getType()+searchParam+"&repage";
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
	@RequiresPermissions("sco:scoLabor:audit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoLabor scoLabor, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoLabor> list = scoLaborService.findList(scoLabor);
			
			new ExportExcel("商品数据", ScoLabor.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return null;
	}

}