/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoods;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsTree;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsService;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsTreeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品列表Controller
 * @author 段文昌
 * @version 2015-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoGoodsImpExp")
public class ScoGoodsImportExportController extends BaseController {

	@Autowired
	private ScoGoodsService scoGoodsService;

	@Autowired
	private ScoGoodsTreeService scoGoodsTreeService;
	

	

//	@RequiresPermissions("sco:scoGoodsImpExp:edit")
//	@RequestMapping(value = "batch/index")
//	public String batchImport(ScoGoods scoGoods, Model model) {
//		String directories = scoGoods.getCurrentUser().getDirectories();
//		if(!StringUtils.isBlank(directories)){
//			String[] ids = directories.split(",");
//			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentIds(ids);
//			model.addAttribute("goodsTreeList", goodsTreeList);
//		}
//		return "modules/sco/scoGoodsBatchIndex";
//	}


//	@RequiresPermissions("sco:scoGoodsImpExp:edit")
//	@RequestMapping(value = "import/template")
//	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		try {
//			String fileName = "商品数据导入模板.xlsx";
//			List<ScoGoods> list = Lists.newArrayList();
//			new ExportExcel("商品数据", ScoGoods.class, 2).setDataList(list).write(response, fileName).dispose();
//			return null;
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
//		}
//		return "redirect:" + adminPath + "/sco/scoGoodsImpExp/batch/index?repage";
//	}

	@RequiresPermissions("sco:scoGoodsImpExp:edit")
	@RequestMapping(value = "backup/index")
	public String backupIndex(ScoGoods scoGoods, Model model) {
		Long count = scoGoodsService.count(scoGoods.getCurrentUser().getId());
		model.addAttribute("count",count);
		return "modules/sco/scoGoodsBackupIndex";
	}

	/**
	 * 导出商品数据
	 * @param scoGoods
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sco:scoGoodsImpExp:edit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(ScoGoods scoGoods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			scoGoods.setCreateBy(scoGoods.getCurrentUser());
			List<ScoGoods> list = scoGoodsService.findList(scoGoods);
			List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
			for (ScoGoods goods : list) {
				for (ScoGoodsTree tree : goodsTreeList) {
					if (goods.getGoodsTreeId().equals(tree.getId())) {
						goods.setGoodsTreeName(tree.getName());
					}
				}
			}
			new ExportExcel("商品数据", ScoGoods.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sco/scoGoodsImpExp/backup/index";
	}
}