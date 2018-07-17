package com.thinkgem.jeesite.modules.sco.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalCache {
	
	public static final Map<String, String> serTreeMap = new ConcurrentHashMap<String, String>();

}
