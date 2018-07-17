/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Sco全局配置类
 * @author 段文昌
 * @version 2015-11-18
 */
public class ScoGlobal {

	/**
	 * 当前对象实例
	 */
	private static ScoGlobal global = new ScoGlobal();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();


	/** 产品审核设置属性 */
	public static final String APPROVE_CONFIG = "approveConfig";
	/** 验收单审核设置 */
	public static final String YSD_CONFIG = "ysdConfig";
	/** 零配件审核配置 */
	public static final String SPA_CONFIG = "spaConfig";
	/** 工时审核配置 */
	public static final String MH_CONFIG = "mhConfig";
	/** 机油审核配置 */
	public static final String OIL_CONFIG = "oilConfig";
	/** 轮胎审核配置 */
	public static final String TYRE_CONFIG = "tyreConfig";
	/** 结算单审核配置 */
	public static final String JSD_CONFIG = "jsdConfig";

	/** 审核属性全局配置 - 必须审核 - 2 */
	public static final String AUDIT_GLOBAL_Y = "2";
	/** 审核属性全局配置 - 无需审核 - 1 */
	public static final String AUDIT_GLOBAL_N = "1";

	/** 产品审核配置 - 仅审核无图片商品 - 1 */
	public static final String APPROVE_AUDIT_1 = "1";
	/** 产品审核配置 - 仅审核无条形码商品 - 2 */
	public static final String APPROVE_AUDIT_2 = "2";
	/** 产品审核配置 - 审核无图片和无条形码商品 - 3 */
	public static final String APPROVE_AUDIT_3 = "3";
	/** 产品审核配置 - 无需审核 - 4 */
	public static final String APPROVE_AUDIT_4 = "4";
	/** 产品审核配置 - 必须审核 - 5 */
	public static final String APPROVE_AUDIT_5 = "5";

	/** 树形顶级节点 */
	public static final String TREE_TOP_LEVEL = "0";

	/** 办公用品验收单审核设置 */
	public static final String WORK_YSD_CONFIG = "workYsdConfig";
	/** 车辆维修结算单审核配置 */
	public static final String CAR_JSD_CONFIG = "carJsdConfig";
	/** 印刷结算单审核设置 */
	public static final String PRINTING_JSD_CONFIG = "printingJsdConfig";
	/** 图文制作结算单审核设置 */
	public static final String IMAGE_TEXT_JSD_CONFIG = "imageTextJsdConfig";
	/** 喷绘结算单审核设置 */
	public static final String INKJET_JSD_CONFIG = "inkjetJsdConfig";
	/** 视频制作结算单审核设置 */
	public static final String VIDEO_JSD_CONFIG = "videoJsdConfig";
	/** 软件开发结算单审核设置 */
	public static final String SOFT_JSD_CONFIG = "softJsdConfig";

	/** 文体用品角色值 */
	public static final String WORK_ROLE = "3";
	/** 车辆维修角色值 */
	public static final String CAR_ROLE = "4";
	/** 图文制作角色值 */
	public static final String IMAGE_TEXT_ROLE = "5";
	/** 印刷角色值 */
	public static final String PRINTING_ROLE = "6";
	/** 视频制作角色值 */
	public static final String VIDEO_ROLE = "8";
	/** 软件开发角色值 */
	public static final String SOFT_ROLE = "9";

	/**
	 * 获取当前对象实例
	 */
	public static ScoGlobal getInstance() {
		return global;
	}

}
