/*
 * BasBloodDefinationDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-27 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.po.BasBloodDefination;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface BasBloodDefinationDao {
    int deleteByPrimaryKey(String bloodId);

    int insert(BasBloodDefination record);

    int insertSelective(BasBloodDefination record);

    BasBloodDefination selectByPrimaryKey(String bloodId);

    int updateByPrimaryKeySelective(BasBloodDefination record);

    int updateByPrimaryKey(BasBloodDefination record);
    
    public List<BasBloodDefination> queryAllList(@Param("beid")String beid);
}