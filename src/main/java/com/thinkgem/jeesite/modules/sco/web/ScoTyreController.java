/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.ScoLabor;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.entity.ScoTyre;
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
import com.thinkgem.jeesite.modules.sco.entity.ScoTyre;
import com.thinkgem.jeesite.modules.sco.service.ScoTyreService;

import java.util.List;

/**
 * 轮胎表维护Controller
 * @author 段文昌
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoTyre")
public class ScoTyreController extends BaseController {

	@Autowired
	private ScoTyreService scoTyreService;

	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public ScoTyre get(@RequestParam(required=false) String id) {
		ScoTyre entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoTyreService.get(id);
		}
		if (entity == null){
			entity = new ScoTyre();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoTyre:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoTyre scoTyre, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		scoTyre.setCreateBy(scoTyre.getCurrentUser());
		Page<ScoTyre> page = scoTyreService.findPage(new Page<ScoTyre>(request, response), scoTyre);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoTyre/list");
		model.addAttribute("sco", "scoTyre");
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		

		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoTyre.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_2","supplier_tree_"+scoTyre.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoTyreList";
	}

	@RequiresPermissions("sco:scoTyre:view")
	@RequestMapping(value = "form")
	public String form(ScoTyre scoTyre, ScoSerTree scoSerTree, Model model) {
		model.addAttribute("scoTyre", scoTyre);
		model.addAttribute("scoSerTree",scoSerTree);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_2","supplier_tree_"+scoTyre.getType(),"")); //轮胎
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoTyreForm";
	}
	@RequiresPermissions("sco:scoTyre:view")
	@RequestMapping(value = "formView")
	public String formView(ScoTyre scoTyre, ScoSerTree scoSerTree, Model model) {
		this.form(scoTyre, scoSerTree, model);
		return "modules/sco/scoTyreFormView";
	}

	@RequiresPermissions("sco:scoTyre:edit")
	@RequestMapping(value = "save")
	public String save(ScoTyre scoTyre, ScoSerTree scoSerTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoTyre)){
			return form(scoTyre,scoSerTree, model);
		}
		scoTyreService.save(scoTyre);
		String lableTitle = DictUtils.getDictValue("lable_title", "third_form_"+scoTyre.getType(), "");
		addMessage(redirectAttributes, "保存"+lableTitle+"成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoTyre/list?type="+scoTyre.getType()+"&repage";
	}
	
	@RequiresPermissions("sco:scoTyre:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoTyre scoTyre, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoTyreService.delete(scoTyre);
		String lableTitle = DictUtils.getDictValue("lable_title", "third_form_"+scoTyre.getType(), "");
		addMessage(redirectAttributes, "删除"+lableTitle+"成功");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoCTyre/list?type="+scoTyre.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoTyre scoTyre, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoTyre, redirectAttributes,response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoTyre/auditList?type="+scoTyre.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	
	
	
	/**
	 * 查看审核商品
	 * @param scoTyre
	 * @param scoSerTree
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoTyre scoTyre, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		TODO:数据权限
		//scoTyre.setCreateBy(scoTyre.getCurrentUser());
		Page<ScoTyre> page = scoTyreService.findPage(new Page<ScoTyre>(request, response), scoTyre);
		model.addAttribute("page", page);

		model.addAttribute("url", "/sco/scoTyre/auditList");
		model.addAttribute("sco", "scoTyre");
		

		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);

		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoTyre.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		
		model.addAttribute("userList", userList);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_2","supplier_tree_"+scoTyre.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		return "modules/sco/scoTyreList";
	}
	/**
	 * 查看审核商品
	 * @param scoTyre
	 * @param scoSerTree
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/**@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoTyre scoTyre, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
//		scoTyre.setCreateBy(scoTyre.getCurrentUser());
		Page<ScoTyre> page = scoTyreService.findPage(new Page<ScoTyre>(request, response), scoTyre);
		model.addAttribute("page", page);
		model.addAttribute("url", "/sco/scoTyre/auditList");

		model.addAttribute("sco", "scoTyre");
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//商品种类
		List<Dict> kindList = DictUtils.getDictList("kind_list_"+scoTyre.getType());
		model.addAttribute("kindList", kindList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("role_"+scoTyre.getType(),"supplier_role","");
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);
		
		
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoTyre.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoTyre.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoTyre.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoCarAuditList";
	}
	*/
	
	
	@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoTyre scoTyre, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoTyre.getState()+"&createBy.id="+scoTyre.getCreateBy().getId();
		scoTyre.setState(Integer.valueOf(DictUtils.getDictValue("审核通过","product_audit","1")));
		scoTyreService.audit(scoTyre);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoTyre/auditList?type="+scoTyre.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoTyre scoTyre, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoTyre.getState()+"&createBy.id="+scoTyre.getCreateBy().getId();
		scoTyre.setState(Integer.valueOf(DictUtils.getDictValue("审核不通过","product_audit","3")));
		scoTyreService.audit(scoTyre);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoTyre/auditList?type="+scoTyre.getType()+searchParam+"&repage";
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
	@RequiresPermissions("sco:scoTyre:audit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoTyre scoTyre, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoTyre> list = scoTyreService.findList(scoTyre);
			
			new ExportExcel("商品数据", ScoTyre.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return null;
	}

}