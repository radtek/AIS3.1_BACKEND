/*
 * BasCustomConfigureDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-08 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.digihealth.anesthesia.basedata.po.BasCustomConfigure;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasCustomConfigureDao {
    int deleteByPrimaryKey(String configureId);

    int insert(BasCustomConfigure record);

    int insertSelective(BasCustomConfigure record);

    BasCustomConfigure selectByPrimaryKey(String configureId);
    
    BasCustomConfigure getConfigureValueByModularType(@Param("modularType") String modularType,@Param("beid") String beid);

    int updateByPrimaryKeySelective(BasCustomConfigure record);

    int updateByPrimaryKeyWithBLOBs(BasCustomConfigure record);

    int updateByPrimaryKey(BasCustomConfigure record);
    
    public List<BasCustomConfigure> searchBasCustomConfigureList(@Param("baseQuery") BasCustomConfigure baseQuery,@Param("beid") String beid);
}