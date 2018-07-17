/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sco.entity.ScoNotice;
import com.thinkgem.jeesite.modules.sco.service.ScoNoticeService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

/**
 * 系统公告设置Controller
 *
 * @author 段文昌
 * @version 2015-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sco/scoNotice")
public class ScoNoticeController extends BaseController {

    @Autowired
    private ScoNoticeService scoNoticeService;

    @ModelAttribute
    public ScoNotice get(@RequestParam(required = false) String id) {
        ScoNotice entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = scoNoticeService.get(id);
        }
        if (entity == null) {
            entity = new ScoNotice();
            entity.setCreateByName(UserUtils.getUser());
            entity.setPublishDate(new Date());
        }
        return entity;
    }


    @RequiresPermissions("sco:scoNotice:view")
    @RequestMapping(value = {"list", ""})
    public String list(ScoNotice scoNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ScoNotice> page = scoNoticeService.findPage(new Page<ScoNotice>(request, response), scoNotice);
        model.addAttribute("page", page);
        return "modules/sco/scoNoticeList";
    }

    @RequiresPermissions("sco:scoNotice:view")
    @RequestMapping(value = "form")
    public String form(ScoNotice scoNotice, Model model) {
        model.addAttribute("scoNotice", scoNotice);
        return "modules/sco/scoNoticeForm";
    }

    @RequiresPermissions("sco:scoNotice:edit")
    @RequestMapping(value = "save")
    public String save(ScoNotice scoNotice, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, scoNotice)) {
            return form(scoNotice, model);
        }
        scoNoticeService.save(scoNotice);
        addMessage(redirectAttributes, "保存系统公告设置成功");
        return "redirect:" + Global.getAdminPath() + "/sco/scoNotice/list?repage";
    }

    @RequiresPermissions("sco:scoNotice:edit")
    @RequestMapping(value = "delete")
    public String delete(ScoNotice scoNotice, RedirectAttributes redirectAttributes) {
        scoNoticeService.delete(scoNotice);
        addMessage(redirectAttributes, "删除系统公告设置成功");
        return "redirect:" + Global.getAdminPath() + "/sco/scoNotice/list?repage";
    }

    @RequiresPermissions("sco:scoNotice:view")
    @RequestMapping(value = {"view", ""})
    public String view(ScoNotice scoNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
//        String priceData = SeleniumDriver.main();
//        model.addAttribute("priceData", priceData);
        List<ScoNotice> list = scoNoticeService.findList(scoNotice);
        model.addAttribute("list", list);
        return "modules/sco/scoNoticeViews";
    }

}