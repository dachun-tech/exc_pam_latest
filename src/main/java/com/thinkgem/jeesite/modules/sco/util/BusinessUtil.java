package com.thinkgem.jeesite.modules.sco.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.sco.config.ScoGlobal;
import com.thinkgem.jeesite.modules.sco.domain.ScoSerTreeElement;
import com.thinkgem.jeesite.modules.sco.entity.ScoSerTree;
import com.thinkgem.jeesite.modules.sco.service.ScoSerTreeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

@Component
public class BusinessUtil {
    @Autowired
    private ScoSerTreeService scoSerTreeService;
    
    
    
    public Map<String, String> getSerTreeMap(){
    	if(MapUtils.isEmpty(GlobalCache.serTreeMap)){
    		fillMap(DictUtils.getDictValue("tree_1","supplier_tree_"+5,""));
    		fillMap(DictUtils.getDictValue("tree_2","supplier_tree_"+5,""));
    		fillMap(DictUtils.getDictValue("tree_1","supplier_tree_"+6,""));
    		fillMap(DictUtils.getDictValue("tree_2","supplier_tree_"+6,""));
//    		fillMap("category");
//    		fillMap("paper");
    	}
    	return GlobalCache.serTreeMap;
    }
    
    
    private void fillMap(String key){


		ScoSerTree parentTree = new ScoSerTree();
		parentTree.setId(ScoGlobal.TREE_TOP_LEVEL);
		ScoSerTree scoSerTree = new ScoSerTree();
		scoSerTree.setParent(parentTree);
		scoSerTree.setSubType(key);
		List<ScoSerTree> serTreeList = scoSerTreeService.findList(scoSerTree);
		
	    if(CollectionUtils.isNotEmpty(serTreeList)){
	    	for (ScoSerTree parentSerTree : serTreeList) {
	    		GlobalCache.serTreeMap.put(parentSerTree.getId(), parentSerTree.getName());
	    		

				ScoSerTree scoSerTreeQuery = new ScoSerTree();
				scoSerTreeQuery.setParent(parentSerTree);
				
				List<ScoSerTree> serTreeList2 = scoSerTreeService.findList(scoSerTreeQuery);
	    		
	    		
	    		if(CollectionUtils.isNotEmpty(serTreeList2)){
	    	    	for (ScoSerTree scoSerTree2 : serTreeList2) {
	    	    		GlobalCache.serTreeMap.put(scoSerTree2.getId(), scoSerTree2.getName());
					}
	    	    }
			}
	    }
    }
    
}
