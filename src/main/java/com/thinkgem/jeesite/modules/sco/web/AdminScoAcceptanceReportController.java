/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sco.entity.*;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsService;
import org.apache.commons.collections.CollectionUtils;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelReceiving;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.service.ScoAcceptanceReportService;
import com.thinkgem.jeesite.modules.sco.service.ScoGoodsTreeService;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sco.util.DateUtil;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 验收单Controller
 *
 * @author thinkgem
 * @version 2015-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/admin/sco/scoAcceptanceReport")
public class AdminScoAcceptanceReportController extends BaseController {

    @Autowired
    private ScoAcceptanceReportService scoAcceptanceReportService;
    @Autowired
    private ScoGoodsTreeService scoGoodsTreeService;
    @Autowired
    private ScoGoodsService scoGoodsService;
    @Autowired
    private ScoSerTreeService scoSerTreeService;

    @Autowired
    private SystemService systemService;


    @ModelAttribute
    public AdminScoAcceptanceStatistic get(@RequestParam(required = false) String id) {
        AdminScoAcceptanceStatistic entity = null;
        ScoAcceptanceReport scoAcceptanceReport = null;
        if (StringUtils.isNotBlank(id)) {
            scoAcceptanceReport = scoAcceptanceReportService.get(id);
        }
        if (scoAcceptanceReport == null) {
            scoAcceptanceReport = new ScoAcceptanceReport();
        }
        if (entity == null) {
            entity = new AdminScoAcceptanceStatistic();
            entity.setReport(scoAcceptanceReport);
        }
        return entity;
    }


    @RequiresPermissions("sco:adminAcceptanceReport:edit")
    @RequestMapping(value = {"auditList"})
    public String auditList(AdminScoAcceptanceStatistic adminStatistic, HttpServletRequest request, HttpServletResponse response, Model model) {
        ScoAcceptanceStatistic acceptanceStatistic = new ScoAcceptanceStatistic();
        List<ScoAcceptanceStatistic> yearList = scoAcceptanceReportService.getYear(acceptanceStatistic);
        model.addAttribute("yearList", yearList);
        List<String> monthList = new ArrayList<String>();
        monthList.add("全部");
        monthList.add("1");
        monthList.add("2");
        monthList.add("3");
        monthList.add("4");
        monthList.add("5");
        monthList.add("6");
        monthList.add("7");
        monthList.add("8");
        monthList.add("9");
        monthList.add("10");
        monthList.add("11");
        monthList.add("12");
        model.addAttribute("monthList", monthList);

        //审核状态菜单
        List<Dict> auditReportList = DictUtils.getDictList("audit_report");
        model.addAttribute("auditReportList", auditReportList);
        //供应商菜单
        String roleId = DictUtils.getDictValue("办公用品", "sys_supplier", adminStatistic.getType());
        User user = new User();
        user.setRole(new Role(roleId));
        List<User> userList = systemService.findUser(user);
        model.addAttribute("userList", userList);
        //XXX 采购单位编号菜单

        Page<AdminScoAcceptanceStatistic> page = scoAcceptanceReportService.findPageAuditList(new Page<AdminScoAcceptanceStatistic>(request, response), adminStatistic);
        model.addAttribute("page", page);
        return "modules/sco/admin/adminAcceptanceReportAuditList";
    }

    @RequiresPermissions("sco:adminAcceptanceReport:edit")
    @RequestMapping(value = {"statistic"})
    public String statistic(AdminScoAcceptanceStatistic adminStatistic, HttpServletRequest request, HttpServletResponse response, Model model) {
        ScoAcceptanceStatistic scoAcceptanceStatistic = new ScoAcceptanceStatistic();
        List<ScoAcceptanceStatistic> yearList = scoAcceptanceReportService.getYear(scoAcceptanceStatistic);
        model.addAttribute("yearList", yearList);
        List<String> monthList = new ArrayList<String>();
        monthList.add("全部");
        monthList.add("31");
        monthList.add("28");
        monthList.add("31");
        monthList.add("30");
        monthList.add("31");
        monthList.add("30");
        monthList.add("31");
        monthList.add("31");
        monthList.add("30");
        monthList.add("31");
        monthList.add("30");
        monthList.add("31");
        model.addAttribute("monthList", monthList);
        model.addAttribute("beginDate", adminStatistic.getBeginDate());
        model.addAttribute("endDate", adminStatistic.getEndDate());
        //供应商菜单
        String roleId = DictUtils.getDictValue("办公用品", "sys_supplier", adminStatistic.getType());
        User user = new User();
        user.setRole(new Role(roleId));
        List<User> userList = systemService.findUser(user);
        model.addAttribute("userList", userList);

        List<ScoGoodsTree> goodsTreeList = scoGoodsTreeService.getByParentId(ScoGlobal.TREE_TOP_LEVEL);
        model.addAttribute("goodsTreeList", goodsTreeList);
        List<ScoGoodsTree> goodsSecTreeList = scoGoodsTreeService.getByParentId(adminStatistic.getGoodsTreeId());
        model.addAttribute("goodsSecTreeList", goodsSecTreeList);

        ScoSerTree scoSerTree = new ScoSerTree();
        ScoSerTree parentTree = new ScoSerTree();
        parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
        scoSerTree.setParent(parentTree);
//		scoSerTree.setSubType("category");
        scoSerTree.setSubType(DictUtils.getDictValue("tree_1", "supplier_tree_" + adminStatistic.getType(), ""));
        List<ScoSerTree> serTreeList = scoSerTreeService.getByParentId(scoSerTree);
        model.addAttribute("serTreeList", serTreeList);

        if (adminStatistic.getGoodsTreeId() != null) { //二级目录
            List<ScoSerTree> serSubTreeList = scoSerTreeService.getByParentId(adminStatistic.getGoodsTreeId());
            model.addAttribute("serSubTreeList", serSubTreeList);
        }

        ScoGoods scoGoods = new ScoGoods();
        scoGoods.setName("");
        List<ScoGoods> scoGoodsNameList = scoGoodsService.getGoodsNameList(scoGoods);
        model.addAttribute("goodsNameList", scoGoodsNameList);

        ScoAcceptanceReport scoAcceptanceReport = new ScoAcceptanceReport();
        scoAcceptanceReport.setType("");
        List<ScoAcceptanceReport> scoDepartmentList = scoAcceptanceReportService.getDepartmentList(scoAcceptanceReport);
        model.addAttribute("departmentList", scoDepartmentList);
//		if(CollectionUtils.isNotEmpty(serTreeList)&&"5".equals(adminStatistic.getType())){
//			model.addAttribute("serTreeId", serTreeList.get(0).getId());
//		}

        List<AdminScoAcceptanceStatistic> dbList = scoAcceptanceReportService.adminStatistic(adminStatistic);
        List<AdminScoAcceptanceStatistic> list = new ArrayList<AdminScoAcceptanceStatistic>();
        ;
        AdminScoAcceptanceStatistic statistic = null;

        Set<String> set = new HashSet<String>();
        //计算合计
        BigDecimal totalReceiving = new BigDecimal(0); //验收单总数量
        BigDecimal totalGoods = new BigDecimal(0); //商品总数量
        BigDecimal totalAmt = new BigDecimal(0); //商品总金额
        //计算供应商小计
        BigDecimal sTotalReceiving; //验收单总数量
        BigDecimal sTotalGoods; //商品总数量
        BigDecimal sTotalAmt; //商品总金额
        List<AdminScoAcceptanceStatistic> statisticList = null;//供应商组列表
        for (AdminScoAcceptanceStatistic acceptance : dbList) {
            totalReceiving = totalReceiving.add(new BigDecimal(acceptance.getReceivingNumbers()));
            totalGoods = totalGoods.add(new BigDecimal(acceptance.getGoodsNumbers()));
            totalAmt = totalAmt.add(acceptance.getSubtotal());
            boolean b = set.add(acceptance.getCreateBy().getId());
            if (b) { //如果不重复
                statisticList = new ArrayList<AdminScoAcceptanceStatistic>();
                statistic = new AdminScoAcceptanceStatistic(); //新的统计对象
                statistic.setCreateBy(acceptance.getCreateBy()); //放入用户信息
                sTotalReceiving = new BigDecimal(0); //验收单总数量
                sTotalGoods = new BigDecimal(0); //商品总数量
                sTotalAmt = new BigDecimal(0); //商品总金额
                sTotalReceiving = sTotalReceiving.add(new BigDecimal(acceptance.getReceivingNumbers()));
                sTotalGoods = sTotalGoods.add(new BigDecimal(acceptance.getGoodsNumbers()));
                sTotalAmt = sTotalAmt.add(acceptance.getSubtotal());

                //放入小计
                statistic.setTotalReceiving(sTotalReceiving);
                statistic.setTotalGoods(sTotalGoods);
                statistic.setTotalAmt(sTotalAmt);
                statisticList.add(acceptance);
                statistic.setStatisticList(statisticList);
                list.add(statistic); //添加至列表
            } else {
                statistic.getStatisticList().add(acceptance);
                sTotalReceiving = statistic.getTotalReceiving().add(new BigDecimal(acceptance.getReceivingNumbers()));
                sTotalGoods = statistic.getTotalGoods().add(new BigDecimal(acceptance.getGoodsNumbers()));
                sTotalAmt = statistic.getTotalAmt().add(acceptance.getSubtotal());
                //放入小计
                statistic.setTotalReceiving(sTotalReceiving);
                statistic.setTotalGoods(sTotalGoods);
                statistic.setTotalAmt(sTotalAmt);
            }
        }

        try {
            Collections.sort(list, new Comparator<AdminScoAcceptanceStatistic>() {

                @Override
                public int compare(AdminScoAcceptanceStatistic o1, AdminScoAcceptanceStatistic o2) {
                    return o1.getTotalAmt().compareTo(o2.getTotalAmt());
                }
            });
        } catch (Exception e) {
            logger.error("排序异常", e);
        }
        model.addAttribute("list", list);

        model.addAttribute("totalReceiving", totalReceiving);
        model.addAttribute("totalGoods", totalGoods);
        model.addAttribute("totalAmt", totalAmt);
        return "modules/sco/admin/adminAcceptanceStatistic";
    }

    @RequiresPermissions("sco:adminAcceptanceReport:edit")
    @RequestMapping(value = {"statistic/export"})
    public String statisticExport(AdminScoAcceptanceStatistic adminStatistic, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

        List<AdminScoAcceptanceStatistic> dbList = scoAcceptanceReportService.adminStatistic(adminStatistic);
        List<AdminScoAcceptanceStatistic> adminStatisticDepartments = scoAcceptanceReportService.adminStatisticDepartments(adminStatistic);
        List<AdminScoAcceptanceStatistic> list = new ArrayList<AdminScoAcceptanceStatistic>();
        ;
        AdminScoAcceptanceStatistic statistic = null;

        Set<String> set = new HashSet<String>();
        //计算合计
        BigDecimal totalReceiving = new BigDecimal(0); //验收单总数量
        BigDecimal totalGoods = new BigDecimal(0); //商品总数量
        BigDecimal totalAmt = new BigDecimal(0); //商品总金额
        //计算供应商小计
        BigDecimal sTotalReceiving; //验收单总数量
        BigDecimal sTotalGoods; //商品总数量
        BigDecimal sTotalAmt; //商品总金额
        List<AdminScoAcceptanceStatistic> statisticList = null;//供应商组列表
        String departmentStr = "";
        int ii = 0;
        for (AdminScoAcceptanceStatistic unit : adminStatisticDepartments) {
            if(unit.getAdminStaticDepartment().isEmpty() || unit.getAdminStaticDepartment().equals(null)) continue;
            if(departmentStr.length() > 50) {
                departmentStr+=", ...";
                break;
            }
            if (ii != 0) departmentStr += ",";
            departmentStr += unit.getAdminStaticDepartment();
            ii++;
        }
        for (AdminScoAcceptanceStatistic acceptance : dbList) {
            totalReceiving = totalReceiving.add(new BigDecimal(acceptance.getReceivingNumbers()));
            totalGoods = totalGoods.add(new BigDecimal(acceptance.getGoodsNumbers()));
            totalAmt = totalAmt.add(acceptance.getSubtotal());
            boolean b = set.add(acceptance.getCreateBy().getId());
            if (b) { //如果不重复
                statisticList = new ArrayList<AdminScoAcceptanceStatistic>();
                statistic = new AdminScoAcceptanceStatistic(); //新的统计对象
                statistic.setCreateBy(acceptance.getCreateBy()); //放入用户信息
                sTotalReceiving = new BigDecimal(0); //验收单总数量
                sTotalGoods = new BigDecimal(0); //商品总数量
                sTotalAmt = new BigDecimal(0); //商品总金额
                sTotalReceiving = sTotalReceiving.add(new BigDecimal(acceptance.getReceivingNumbers()));
                sTotalGoods = sTotalGoods.add(new BigDecimal(acceptance.getGoodsNumbers()));
                sTotalAmt = sTotalAmt.add(acceptance.getSubtotal());

                //放入小计
                statistic.setTotalReceiving(sTotalReceiving);
                statistic.setTotalGoods(sTotalGoods);
                statistic.setTotalAmt(sTotalAmt);
                statisticList.add(acceptance);
                statistic.setStatisticList(statisticList);
                list.add(statistic); //添加至列表
            } else {
                statistic.getStatisticList().add(acceptance);
                sTotalReceiving = statistic.getTotalReceiving().add(new BigDecimal(acceptance.getReceivingNumbers()));
                sTotalGoods = statistic.getTotalGoods().add(new BigDecimal(acceptance.getGoodsNumbers()));
                sTotalAmt = statistic.getTotalAmt().add(acceptance.getSubtotal());
                //放入小计
                statistic.setTotalReceiving(sTotalReceiving);
                statistic.setTotalGoods(sTotalGoods);
                statistic.setTotalAmt(sTotalAmt);
            }
        }
        model.addAttribute("list", list);

        model.addAttribute("totalReceiving", totalReceiving);
        model.addAttribute("totalGoods", totalGoods);
        model.addAttribute("totalAmt", totalAmt);

        //导出excel
        List<AdminAcceptanceExport> exportList = new ArrayList<AdminAcceptanceExport>();


        for (AdminScoAcceptanceStatistic sas : list) {
            AdminAcceptanceExport export = new AdminAcceptanceExport();


            List<AdminAcceptanceExport.RowData> innerList = new ArrayList<AdminAcceptanceExport.RowData>();
            DecimalFormat df = new DecimalFormat("### ##0.00");
            export.setSuppliers(sas.getCreateBy().getName());

            //内部类数据
            List<AdminScoAcceptanceStatistic> dataList = sas.getStatisticList();
            innerList = new ArrayList<AdminAcceptanceExport.RowData>();
            for (AdminScoAcceptanceStatistic data : dataList) {
                AdminAcceptanceExport.RowData rowData = export.new RowData();
                String bD = adminStatistic.getBeginDate();
                String eD = adminStatistic.getEndDate();
                rowData.setMonth(((bD.isEmpty() || bD.equals("")) ? (data.getYear()) : bD) + " ~ " + ((eD.isEmpty() || eD.equals("")) ? (data.getMonth()) : eD));
                rowData.setReceiving(data.getReceivingNumbers());
                rowData.setGoodsCount(data.getGoodsNumbers());
                rowData.setTotalAmt(data.getSubtotal());
                innerList.add(rowData);
            }
            export.setRowDatas(innerList);
            exportList.add(export);
        }
        //总计
        AdminAcceptanceExport export = new AdminAcceptanceExport();
        export.setSuppliers("总计");
        AdminAcceptanceExport.RowData rowData = export.new RowData();
        List<AdminAcceptanceExport.RowData> innerList = new ArrayList<AdminAcceptanceExport.RowData>();
        rowData.setMonth("");
        rowData.setReceiving(totalReceiving.intValue());
        rowData.setGoodsCount(totalGoods.intValue());
        rowData.setTotalAmt(totalAmt);
        innerList.add(rowData);
        export.setRowDatas(innerList);
        exportList.add(export);

        List<String> headerList = new ArrayList<String>();
        headerList.add("供应商");
        headerList.add("统计时间");
        headerList.add("验收单数量");
        headerList.add("商品总数量");
        headerList.add("采购总金额");
        headerList.add("采购单位：" + departmentStr);
        try {
            String title = null;
            if ("3".equals(adminStatistic.getType())) {
                title = "物资类数据统计";
            } else if ("4".equals(adminStatistic.getType())) {
                title = "车辆数据统计";
            } else if ("5".equals(adminStatistic.getType())) {
                title = "图文数据统计";
            } else if ("6".equals(adminStatistic.getType())) {
                title = "印刷数据统计";
            }

            String fileName = title + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            String year = adminStatistic.getYear();
            new ExportExcelReceiving(year + title, headerList).setDataList(exportList).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出统计数据失败！失败信息：" + e.getMessage());
        }

        return "redirect:" + adminPath + "/admin/sco/scoAcceptanceReport/statistic";
    }


    @RequiresPermissions("sco:adminAcceptanceReport:edit")
    @RequestMapping(value = "exportexcel")
    public String exportexcel(AdminScoAcceptanceStatistic adminStatistic, HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String fileName = null;
            String title = null;
            if ("3".equals(adminStatistic.getType())) {
                title = "物资定点采购验收单";
                fileName = "物资定点采购验收单_" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm") + ".xlsx";
            } else if ("5".equals(adminStatistic.getType())) {
                title = "图文定点采购验收单";
                fileName = "图文定点采购验收单_" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm") + ".xlsx";
            } else if ("6".equals(adminStatistic.getType())) {
                title = "印刷定点采购验收单";
                fileName = "印刷定点采购验收单_" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm") + ".xlsx";
            }


            List<AdminScoAcceptanceStatistic> list = new ArrayList<AdminScoAcceptanceStatistic>();

            Page<AdminScoAcceptanceStatistic> page =
                    scoAcceptanceReportService.findPageAuditList(new Page<AdminScoAcceptanceStatistic>(1, 100), adminStatistic);

            if (CollectionUtils.isNotEmpty(page.getList())) {
                list.addAll(page.getList());
            }

            logger.info("查询总页数：{},总记录数：{}", page.getTotalPage(), page.getCount());
            for (int i = 1; i < page.getTotalPage(); i++) {
                logger.info("开始查询第{}页数据", i + 1);
//				request.setAttribute("pageNo",i+1);
//				Page<AdminScoAcceptanceStatistic> pageTemp = new Page<AdminScoAcceptanceStatistic>();
//				pageTemp.setPageNo(i+1);
//				adminStatistic.setPage(pageTemp);
                Page<AdminScoAcceptanceStatistic> page2 =
                        scoAcceptanceReportService.findPageAuditList(new Page<AdminScoAcceptanceStatistic>(i + 1, 100), adminStatistic);
                if (CollectionUtils.isNotEmpty(page2.getList())) {
                    list.addAll(page2.getList());
                }

            }

            List<AdminScoAcceptanceStatisticExport> exportList = new ArrayList<AdminScoAcceptanceStatisticExport>();
            for (AdminScoAcceptanceStatistic adminScoAcceptanceStatistic : list) {
                AdminScoAcceptanceStatisticExport acceptanceStatisticExport = new AdminScoAcceptanceStatisticExport();
                acceptanceStatisticExport.setGoodsNumbers(adminScoAcceptanceStatistic.getGoodsNumbers() + "");
                if (adminScoAcceptanceStatistic.getReport() != null) {
                    acceptanceStatisticExport.setBuyDate(adminScoAcceptanceStatistic.getReport().getBuyDate());
                    acceptanceStatisticExport.setDepartment(adminScoAcceptanceStatistic.getReport().getDepartment());
                    acceptanceStatisticExport.setInvoicenum(adminScoAcceptanceStatistic.getReport().getInvoicenum());
                    if (adminScoAcceptanceStatistic.getReport().getState() != null) {
                        acceptanceStatisticExport.setState(DictUtils.getDictLabel(adminScoAcceptanceStatistic.getReport().getState(), "audit_report", "0"));
                    }
                }
                if (adminScoAcceptanceStatistic.getCreateBy() != null) {
                    acceptanceStatisticExport.setName(adminScoAcceptanceStatistic.getCreateBy().getName());
                }
                if (adminScoAcceptanceStatistic.getSubtotal() != null) {
                    DecimalFormat df = new DecimalFormat("### ##0.00");
                    acceptanceStatisticExport.setSubtotal(df.format(adminScoAcceptanceStatistic.getSubtotal()));
                }

                exportList.add(acceptanceStatisticExport);
            }
//    		for (Hotel bean:list) {
//    			String  switchopen= bean.getSwitchOpen();
//                if(switchopen==null || "".equals(switchopen)){
//                	bean.setSwitchopendesc(DictUtils.getDictLabel(switchopen, "BusinessEnums", ""));
//                	bean.setSwitchcreatetime(null);
//                }else{
//                	bean.setSwitchopendesc(DictUtils.getDictLabel(switchopen, "BusinessEnums", ""));
//                }
//			}


            new ExportExcel(title, AdminScoAcceptanceStatisticExport.class, 1).setDataList(exportList).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出验收单信息失败:", e);
            e.printStackTrace();
            addMessage(redirectAttributes, "验收单数据excel导出失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/admin/sco/scoAcceptanceReport/auditList?repage";

    }

}