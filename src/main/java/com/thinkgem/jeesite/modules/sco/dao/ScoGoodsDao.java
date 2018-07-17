/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoods;
import com.thinkgem.jeesite.modules.sco.entity.ScoGoodsStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品列表DAO接口
 * @author 段文昌
 * @version 2015-11-14
 */
@MyBatisDao
public interface ScoGoodsDao extends CrudDao<ScoGoods> {
    /**
     * 统计用户的商品条数
     * @param userId 用户ID
     * @return ScoGoodsTreeBrand
     */
    public Long count(@Param("userId") String userId,@Param("DEL_FLAG_NORMAL") String DEL_FLAG_NORMAL);

    /**
     * 用户部分商品统计
     * @param scoGoods
     * @return
     */
    public List<ScoGoodsStatistic> statistic(ScoGoods scoGoods);

    public List<ScoGoods> getGoodsNameList(ScoGoods scoGoods);

    /**
     * 更新状态
     * @param scoGoods
     * @return
     */
    public Integer updateState(ScoGoods scoGoods);
}