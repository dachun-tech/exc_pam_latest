/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoods;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import com.thinkgem.jeesite.modules.sco.entity.ScoPrints;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.service.ScoPrintsService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.util.BusinessUtil;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 印刷品Controller
 * @author oddbitter
 * @version 2016-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoPrints")
public class ScoPrintsController extends BaseController {

	@Autowired
	private ScoPrintsService scoPrintsService;
	@Autowired
	private ScoSerTreeService scoSerTreeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private BusinessUtil businessUtil;
	
	@ModelAttribute
	public ScoPrints get(@RequestParam(required=false) String id) {
		ScoPrints entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scoPrintsService.get(id);
		}
		if (entity == null){
			entity = new ScoPrints();
		}
		return entity;
	}
	
	@RequiresPermissions("sco:scoPrints:view")
	@RequestMapping(value = {"list", ""})
	public String list(ScoPrints scoPrints, ScoSerTree scoSerTree ,HttpServletRequest request, HttpServletResponse response, Model model) {
		scoPrints.setCreateBy(scoPrints.getCurrentUser());
		model.addAttribute("url", "/sco/scoPrints/list");
		
		
//		StringBuilder sf = new StringBuilder();
//		Map<String, Object> map = request.getParameterMap();
//		if(MapUtils.isNotEmpty(map)){
//			for (Entry<String, Object> entry : map.entrySet()) {
//				if(entry.getValue()!=null){
//					sf.append("&");
//					sf.append(entry.getKey()).append("=").append(entry.getValue().toString());
//				}
//			}
//		}
//		model.addAttribute("queryparam", sf.toString());
		return this.doList(scoPrints, scoSerTree, request, response, model);
	}
	
	private String doList(ScoPrints scoPrints, ScoSerTree scoSerTree ,HttpServletRequest request, HttpServletResponse response, Model model) {

		
		Page<ScoPrints> page = scoPrintsService.findPage(new Page<ScoPrints>(request, response), scoPrints); 
		model.addAttribute("page", page);


		//审核状态菜单
		List<Dict> auditReportList = DictUtils.getDictList("product_audit");
		model.addAttribute("auditReportList", auditReportList);
		
		//供应商菜单
		String roleId = DictUtils.getDictValue("办公用品","sys_supplier",scoPrints.getType());
		User user = new User();
		user.setRole(new Role(roleId));
		List<User> userList = systemService.findUser(user);
		model.addAttribute("userList", userList);

		model.addAttribute("sco", "scoPrints");
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
//		scoSerTree.setSubType("category");
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoPrints.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		if(scoPrints.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoPrints.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		return "modules/sco/scoPrintsList";
	
	}

	@RequiresPermissions("sco:scoPrints:view")
	@RequestMapping(value = "form")
	public String form(ScoPrints scoPrints, ScoSerTree scoSerTree ,Model model) {
		model.addAttribute("scoPrints", scoPrints);
		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		scoSerTree.setParent(parentTree);
//		scoSerTree.setSubType("category");
		scoSerTree.setSubType(DictUtils.getDictValue("tree_1","supplier_tree_"+scoPrints.getType(),""));
		List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
		model.addAttribute("serTreeList", serTreeList);
		
		
//		if(CollectionUtils.isNotEmpty(serTreeList)&&"5".equals(scoPrints.getType())){
//			model.addAttribute("serTreeId", serTreeList.get(0).getId());
//		}
		if(scoPrints.getSerTreeId() != null){ //二级目录
			List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(scoPrints.getSerTreeId().getId());
			model.addAttribute("serSubTreeList", serSubTreeList);
		}
		
		
		
		ScoSerTree parentTree2 = new ScoSerTree();
		parentTree2.setId(ScoGlobal.TREE_TOP_LEVEL);
		parentTree2.setParent(parentTree2);
//		parentTree2.setSubType("paper");
		parentTree2.setSubType(DictUtils.getDictValue("tree_2","supplier_tree_"+scoPrints.getType(),""));
		List<ScoSerTree> paperSerTreeList = scoSerTreeService.getByParentId(parentTree2);
		model.addAttribute("paperTreeList", paperSerTreeList);
//		if(CollectionUtils.isNotEmpty(paperSerTreeList)){
//			model.addAttribute("paperSerSubId", paperSerTreeList.get(0).getId());
//		}
//		model.addAttribute("paperSerTreeList", paperSerTreeList);
//		if(scoPrints.getPaperTreeId() != null){ //二级目录
//			List<ScoSerTree> paperTreeList = scoSerTreeService.getByParentId(scoPrints.getPaperTreeId().getId());
//			model.addAttribute("paperTreeList", paperTreeList);
//		}
		
		model.addAttribute("descs", scoSerTreeService.queryDesc());
		return "modules/sco/scoPrintsForm";
	}

	@RequiresPermissions("sco:scoPrints:edit")
	@RequestMapping(value = "save")
	public String save(ScoPrints scoPrints, ScoSerTree scoSerTree,Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, scoPrints)){
			return form(scoPrints,scoSerTree, model);
		}
		
		StringBuilder sf  =new StringBuilder();
		
		
		if("6".equals(scoPrints.getType())){
			if(scoPrints.getDescs()!=null){
				for (int i = 0; i < scoPrints.getDescs().length; i++) {
					sf.append(scoPrints.getDescs()[i]);
					if(i!=scoPrints.getDescs().length-1){
						sf.append(",");
					}
				}
			}
			scoPrints.setDescription(sf.toString());
		}
		
		scoPrints.setSubName(queryName(scoPrints.getSubId().getId()));
		
		scoPrints.setPaperTypeName(queryName(scoPrints.getPaperTypeId()));
		scoPrintsService.save(scoPrints);
		addMessage(redirectAttributes, "保存印刷品成功");
		return "redirect:"+Global.getAdminPath()+"/sco/scoPrints/?type="+scoPrints.getType()+"&repage";
	}
	
	private String queryName(String id){
		String subName = businessUtil.getSerTreeMap().get(id);
		if(subName==null){
			ScoSerTree scoSerTree2 =scoSerTreeService.get(id);
			if(scoSerTree2!=null){
				subName = scoSerTree2.getName();
			}
		}
		return subName;
	}
	
	@RequiresPermissions("sco:scoPrints:edit")
	@RequestMapping(value = "delete")
	public String delete(ScoPrints scoPrints, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		scoPrintsService.delete(scoPrints);
		addMessage(redirectAttributes, "删除印刷品成功");
		// 转换成json，easyui最终要使用json在页面展示数据

		

		response.setContentType("text/json;charset=UTF-8");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoPrints/?type="+scoPrints.getType()+"&repage";
		return null;
	}
	@RequiresPermissions("sco:scoPrints:audit")
	@RequestMapping(value = "auditDelete")
	public String auditDelete(ScoPrints scoPrints, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		this.delete(scoPrints, redirectAttributes,response);
//		return "redirect:"+Global.getAdminPath()+"/sco/scoPrints/auditList?type="+scoPrints.getType()+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	
	
	
	@RequiresPermissions("sco:scoPrints:view")
	@RequestMapping(value = {"view"})
	public String view(ScoPrints scoPrints, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("scoPrints", scoPrints);
		if (!StringUtils.isBlank(scoPrints.getPicUrl())){
			String[] picUrl = scoPrints.getPicUrl().split("\\|");
			List<String> picList = new ArrayList<String>();
			for(String url : picUrl){
				if(!StringUtils.isBlank(url)){
					picList.add(url);
				}
			}
			model.addAttribute("picList", picList);
		}
		   
//		List<ScoSerTreeElement>  descs = new Gson().fromJson(scoPrints.getDescription(),new TypeToken<List<ScoSerTreeElement>>() {
//		}.getType());
		
		if("6".equals(scoPrints.getType())){
			List<ScoSerTreeElement> elements = scoSerTreeService.queryDesc();
			scoPrintsService.fillElement(elements, scoPrints.getDescription());
			model.addAttribute("descs", elements);
		}
		
		return "modules/sco/scoPrintsView";
	}
	
	
	/**
	 * 查看审核商品
	 * @param scoPrints
	 * @param scoSerTree
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sco:scoPrints:audit")
	@RequestMapping(value = {"auditList"})
	public String auditList(ScoPrints scoPrints, ScoSerTree scoSerTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("url", "/sco/scoPrints/auditList");
		return this.doList(scoPrints, scoSerTree, request, response, model);
	}
	
	
	@RequiresPermissions("sco:scoPrints:audit")
	@RequestMapping(value = "passAudit")
	public String passAudit(ScoPrints scoPrints, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoPrints.getState()+"&createBy.id="+scoPrints.getCreateBy().getId();
		scoPrints.setState(DictUtils.getDictValue("审核通过","product_audit","1"));
		scoPrintsService.audit(scoPrints);
		addMessage(redirectAttributes, "商品审核通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoPrints/auditList?type="+scoPrints.getType()+searchParam+"&repage";
		response.setContentType("text/json;charset=UTF-8");
		return null;
	}
	@RequiresPermissions("sco:scoPrints:audit")
	@RequestMapping(value = "refuseAudit")
	public String refuseAudit(ScoPrints scoPrints, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		String searchParam = "&report.state="+scoPrints.getState()+"&createBy.id="+scoPrints.getCreateBy().getId();
		scoPrints.setState(DictUtils.getDictValue("审核不通过","product_audit","3"));
		scoPrintsService.audit(scoPrints);
		addMessage(redirectAttributes, "商品审核未通过");
//		return "redirect:"+Global.getAdminPath()+"/sco/scoPrints/auditList?type="+scoPrints.getType()+searchParam+"&repage";
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
	@RequiresPermissions("sco:scoPrints:audit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoPrints scoPrints, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoPrints> list = scoPrintsService.findList(scoPrints);
			
			if(CollectionUtils.isNotEmpty(list)){
				Map<String, String> map = scoSerTreeService.queryDescMap();
				
				for (ScoPrints scoPrints2 : list) {
					if("6".equals(scoPrints2.getType())){
						if(org.apache.commons.lang3.StringUtils.isNotBlank(scoPrints2.getDescription())){
							String[] array = scoPrints2.getDescription().split(",");
							StringBuffer sf = new StringBuffer();
							for (int i = 0; i < array.length; i++) {
								if(map.get(array[i])!=null){
									sf.append(map.get(array[i]));
									if(i!=array.length-1){
										sf.append(",");
									}
								}
							}
							scoPrints2.setDescription(sf.toString());
						}
					}
				}
			}
//			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
//			for (ScoGoods goods : list) {
//				for (ScoGoodsTree tree : goodsTreeList) {
//					if (goods.getGoodsTreeId().equals(tree.getId())) {
//						goods.setGoodsTreeName(tree.getName());
//					}
//				}
//			}
			new ExportExcel("商品数据", ScoPrints.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return null;
	}

}