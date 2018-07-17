/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.service;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.dao.ScoAcceptanceReportDao;
import com.thinkgem.jeesite.modules.sco.entity.*;
import com.thinkgem.jeesite.modules.sco.util.SerialNumberUtil;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sco.dao.ScoGoodsDao;

/**
 * 商品列表Service
 * @author 段文昌
 * @version 2015-11-14
 */
@Service
@Transactional(readOnly = true)
public class ScoGoodsService extends CrudService<ScoGoodsDao, ScoGoods> {

	@Autowired
	private ScoGoodsTreeService scoGoodsTreeService;
	@Autowired
	private ScoSettingService scoSettingService;
	@Autowired
	private ScoGoodsTreeBrandService scoGoodsTreeBrandService;

	/** 持久层对象 */
	@Autowired
	protected ScoGoodsDao dao;

	public ScoGoods get(String id) {
		return super.get(id);
	}
	
	public List<ScoGoods> findList(ScoGoods scoGoods) {
		return super.findList(scoGoods);
	}
	
	public Page<ScoGoods> findPage(Page<ScoGoods> page, ScoGoods scoGoods) {
		User user = UserUtils.getUser();
		scoGoods.setCreateBy(user);
		scoGoods.setUpdateBy(user);
		return super.findPage(page, scoGoods);
	}

	public Page<ScoGoods> findPageList(Page<ScoGoods> page, ScoGoods scoGoods) {
		return super.findPage(page, scoGoods);
	}

	@Transactional(readOnly = false)
	public void save(ScoGoods scoGoods) {
		//查看是否需要审核
		ScoSetting scoSetting = scoSettingService.getByAttribute(ScoGlobal.APPROVE_CONFIG);
		if(ScoGlobal.AUDIT_GLOBAL_N.equals(scoSetting.getValue())){ //无需审核
			scoGoods.setState("2"); //默认通过
		} else if(ScoGlobal.AUDIT_GLOBAL_Y.equals(scoSetting.getValue())){ //待审
			scoGoods.setState("0");
		}
		
		String serialNumber = SerialNumberUtil.createSerialNumber();
		
		if(scoGoods.getCurrentUser()!=null){
			if(CollectionUtils.isNotEmpty(scoGoods.getCurrentUser().getRoleList())){
				Role role = scoGoods.getCurrentUser().getRoleList().get(0);
				if(role!=null){
					///TODO:暂时写死
					serialNumber = SerialNumberUtil.createSerialNumber(scoGoods.getCurrentUser().getOffice().getCode(), role.getId());
				}
			}
			
		}
		
		scoGoods.setGoodsNo(serialNumber);
		super.save(scoGoods);
	}

//	@Transactional(readOnly = false)
//	public void save(ScoGoods scoGoods) {
//		//去掉创建二级目录及自创建品牌
////		//保存二级目录
////		if(StringUtils.isBlank(scoGoods.getSubId())){
////			ScoGoodsTree goodsTree = scoGoodsTreeService.get(scoGoods.getGoodsTreeId());
////			ScoGoodsTree subTree = scoGoodsTreeService.getByName(scoGoods.getSubName(),scoGoods.getGoodsTreeId(),scoGoods.getCurrentUser().getId());
////			if(subTree == null){
////				ScoGoodsTree sTree = new ScoGoodsTree();
////				sTree.setParent(goodsTree);
////				sTree.setName(scoGoods.getSubName());
////				sTree.setType("3");
////				sTree.setSort(30);
////				sTree.setUser(scoGoods.getCurrentUser());
////				scoGoodsTreeService.save(sTree);
////				scoGoods.setSubId(sTree.getId());
////			}
////		}
////		//查看品牌是否重复
////		ScoGoodsTreeBrand scoGoodsTreeBrand = scoGoodsTreeBrandService.getByName(scoGoods.getBrand(),scoGoods.getGoodsTreeId(),scoGoods.getCurrentUser().getId());
////		if(scoGoodsTreeBrand == null){
////			ScoGoodsTreeBrand brand = new ScoGoodsTreeBrand();
////			brand.setName(scoGoods.getBrand());
////			brand.setTreeId(scoGoods.getGoodsTreeId());
////			brand.setSort(30);
////			brand.setUser(scoGoods.getCurrentUser());
////			scoGoodsTreeBrandService.save(brand);
////		}
////		//查看是否需要审核
////		ScoSetting scoSetting = scoSettingService.getByAttribute(ScoGlobal.APPROVE_CONFIG);
////		if(ScoGlobal.APPROVE_AUDIT_4.equals(scoSetting.getValue())){ //无需审核
////			scoGoods.setState("2");
////		} else if(ScoGlobal.APPROVE_AUDIT_4.equals(scoSetting.getValue())){ //仅审核无图片商品
////			if(!StringUtils.isBlank(scoGoods.getPicUrl())){
////				scoGoods.setState("2");
////			}
////		} else if(ScoGlobal.APPROVE_AUDIT_4.equals(scoSetting.getValue())){ //仅审核无条形码商品
////			if(!StringUtils.isBlank(scoGoods.getBarCode())){
////				scoGoods.setState("2");
////			}
////		} else if(ScoGlobal.APPROVE_AUDIT_4.equals(scoSetting.getValue())){ //审核无图片和无条形码商品
////			if(!StringUtils.isBlank(scoGoods.getBarCode()) && !StringUtils.isBlank(scoGoods.getPicUrl())){
////				scoGoods.setState("2");
////			}
////		}
//		scoGoods.setGoodsNo(SerialNumberUtil.createSerialNumber());
//		super.save(scoGoods);
//	}

	/**
	 * 统计用户的商品条数
	 * @param userId 用户ID
	 * @return ScoGoodsTreeBrand
	 */
	public Long count(String userId){
		return dao.count(userId, BaseEntity.DEL_FLAG_NORMAL);
	}

	/**
	 * 用户部分统计商品分类条数
	 * @param scoGoods
	 * @return
     */
	public List<ScoGoodsStatistic> statistic(ScoGoods scoGoods){
		return dao.statistic(scoGoods);
	}

	public List<ScoGoods> getGoodsNameList(ScoGoods scoGoods){
		return dao.getGoodsNameList(scoGoods);
	}

	@Transactional(readOnly = false)
	public void delete(ScoGoods scoGoods) {
		super.delete(scoGoods);
	}

	/**
	 * 通过审核
	 * @param scoGoods
	 */
	@Transactional(readOnly = false)
	public void passAudit(ScoGoods scoGoods) {
		scoGoods.setState(DictUtils.getDictValue("审核通过","product_audit","1"));
		dao.updateState(scoGoods);
	}

	/**
	 * 未通过审核
	 * @param scoGoods
	 */
	@Transactional(readOnly = false)
	public void refuseAudit(ScoGoods scoGoods) {
		scoGoods.setState(DictUtils.getDictValue("审核不通过","product_audit","3"));
		dao.updateState(scoGoods);
	}
	
}