/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 * 地区工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class AreaUtils {

	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	public static final String AREA_CACHE = "areaCache";
	public static final String AREA_CACHE_ID_ = "id_";

	
	/**
	 * 根据ID获取地区
	 * @param id
	 * @return 取不到返回null
	 */
	public static Area get(String id){
		Area area = (Area)CacheUtils.get(AREA_CACHE, AREA_CACHE_ID_ + id);
		if (area ==  null){
			area = areaDao.get(id);
			if (area == null || area.getParent().getId().equals(0)){
				return null;
			}
			area.setParent(get(area.getParentId()));
			CacheUtils.put(AREA_CACHE, AREA_CACHE_ID_ + area.getId(), area);
		}
		return area;
	}
}
