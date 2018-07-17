/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoNotice;
import com.thinkgem.jeesite.modules.sco.dao.ScoNoticeDao;

/**
 * 系统公告设置Service
 * @author 段文昌
 * @version 2015-11-07
 */
@Service
@Transactional(readOnly = true)
public class ScoNoticeService extends CrudService<ScoNoticeDao, ScoNotice> {

	public ScoNotice get(String id) {
		return super.get(id);
	}
	
	public List<ScoNotice> findList(ScoNotice scoNotice) {
		return super.findList(scoNotice);
	}
	
	public Page<ScoNotice> findPage(Page<ScoNotice> page, ScoNotice scoNotice) {
		return super.findPage(page, scoNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoNotice scoNotice) {
		if (scoNotice.getContent()!=null){
			scoNotice.setContent(StringEscapeUtils.unescapeHtml4(
					scoNotice.getContent()));
		}
		super.save(scoNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoNotice scoNotice) {
		super.delete(scoNotice);
	}
	
}