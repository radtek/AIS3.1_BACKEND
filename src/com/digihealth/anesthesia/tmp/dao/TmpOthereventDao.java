/*
 * TmpSciTempDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-04-06 Created
 */
package com.digihealth.anesthesia.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SearchDoctempFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.research.formbean.SciTempFormBean;
import com.digihealth.anesthesia.tmp.po.TmpOtherevent;

@MyBatisDao
public interface TmpOthereventDao {
    int deleteByPrimaryKey(String id);

    int insert(TmpOtherevent record);

    int insertSelective(TmpOtherevent record);

    TmpOtherevent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TmpOtherevent record);

    int updateByPrimaryKeyWithBLOBs(TmpOtherevent record);

    int updateByPrimaryKey(TmpOtherevent record);
    
    List<SciTempFormBean> getSciTempList(@Param("beid")String beid);
    
    List<TmpOtherevent> queryOthereventTempList(@Param("filters")List<Filter> filters,@Param("searchFormBean")SearchDoctempFormBean searchDoctempFormBean);
    
    int queryTotalCntOthereventTemp(@Param("filters")List<Filter> filters,@Param("searchFormBean")SearchDoctempFormBean searchDoctempFormBean);
}