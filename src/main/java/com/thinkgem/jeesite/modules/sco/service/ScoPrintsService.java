/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.entity.ScoPrints;
import com.thinkgem.jeesite.modules.sco.entity.ScoSetting;
import com.thinkgem.jeesite.modules.sco.entity.ScoSparepart;
import com.thinkgem.jeesite.modules.sco.util.SerialNumberUtil;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.dao.ScoPrintsDao;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sys.entity.Role;

/**
 * 印刷品Service
 * @author oddbitter
 * @version 2016-04-30
 */
@Service
@Transactional(readOnly = true)
public class ScoPrintsService extends CrudService<ScoPrintsDao, ScoPrints> {

	@Autowired
	private ScoSettingService scoSettingService;
	
	public ScoPrints get(String id) {
		return super.get(id);
	}
	
	public List<ScoPrints> findList(ScoPrints scoPrints) {
		return super.findList(scoPrints);
	}
	
	public Page<ScoPrints> findPage(Page<ScoPrints> page, ScoPrints scoPrints) {
		return super.findPage(page, scoPrints);
	}
	
	@Transactional(readOnly = false)
	public void save(ScoPrints scoPrints) {
		
		//查看是否需要审核
		ScoSetting scoSetting = scoSettingService.getByAttribute(ScoGlobal.APPROVE_CONFIG);
		if(ScoGlobal.AUDIT_GLOBAL_N.equals(scoSetting.getValue())){ //无需审核
			scoPrints.setState("2"); //默认通过
		} else if(ScoGlobal.AUDIT_GLOBAL_Y.equals(scoSetting.getValue())){ //待审
			scoPrints.setState("0");
		}
		String serialNumber = SerialNumberUtil.createSerialNumber();
		
		scoPrints.setPrintNo(serialNumber);
		super.save(scoPrints);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScoPrints scoPrints) {
		super.delete(scoPrints);
	}
	
	

	/**
	 * 通过审核
	 * @param scoFinalReport
	 */
	@Transactional(readOnly = false)
	public void audit(ScoPrints scoPrints) {
		
		dao.updateState(scoPrints);
	}
	
	public  void fillElement(List<ScoSerTreeElement> elements,String desc){
		
		if(CollectionUtils.isNotEmpty(elements)&&desc!=null){
			String[] array = desc.split(",");
			List<String> list = new ArrayList<String>();
			Collections.addAll(list, array);
			
			for (ScoSerTreeElement scoSerTreeElement : elements) {
				boolean flag =false;
				if(CollectionUtils.isNotEmpty(scoSerTreeElement.getChildren())){
					for (ScoSerTreeElement children : scoSerTreeElement.getChildren()) {
						if(list.contains(children.getId())){
							children.setSelected(true);
							flag =true;
						}
					}
				}
				if(flag){
					scoSerTreeElement.setSelected(true);
				}
			}
		}
	}
	
}