/*
 * EvtSpecialMaterialEventDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-06-26 Created
 */
package com.digihealth.anesthesia.evt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtSpecialMaterialEvent;

@MyBatisDao
public interface EvtSpecialMaterialEventDao {
    int deleteByPrimaryKey(String specialMaterialEventId);

    int insert(EvtSpecialMaterialEvent record);

    int insertSelective(EvtSpecialMaterialEvent record);

    EvtSpecialMaterialEvent selectByPrimaryKey(String specialMaterialEventId);

    int updateByPrimaryKeySelective(EvtSpecialMaterialEvent record);

    int updateByPrimaryKey(EvtSpecialMaterialEvent record);
    
    List<EvtSpecialMaterialEvent> selectSpecialMaterialEvent(@Param("searchBean")SearchFormBean searchBean);
}