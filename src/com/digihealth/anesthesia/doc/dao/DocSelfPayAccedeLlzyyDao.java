/*
 * DocSelfPayAccedeLlzyyDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.CrudDao;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocSelfPayAccedeLlzyy;
@MyBatisDao
public interface DocSelfPayAccedeLlzyyDao extends CrudDao<DocSelfPayAccedeLlzyy>{
	public DocSelfPayAccedeLlzyy searchSelfPayAccedeByRegOptId(@Param("regOptId") String regOptId);
	
	public DocSelfPayAccedeLlzyy searchSelfPayAccedeById(@Param("id") String id);
	
    int deleteByPrimaryKey(String id);

    int insert(DocSelfPayAccedeLlzyy record);

    int insertSelective(DocSelfPayAccedeLlzyy record);

    DocSelfPayAccedeLlzyy selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocSelfPayAccedeLlzyy record);

    int updateByPrimaryKey(DocSelfPayAccedeLlzyy record);
}