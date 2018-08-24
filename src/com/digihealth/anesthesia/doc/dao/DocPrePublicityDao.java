/*
 * DocPrePublicityDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.CrudDao;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPrePublicity;
@MyBatisDao
public interface DocPrePublicityDao extends CrudDao<DocPrePublicity>{
	public DocPrePublicity searchPrePublicityByRegOptId(@Param("regOptId") String regOptId);
	
	public DocPrePublicity searchPrePublicityById(@Param("id") String id);
	
    int deleteByPrimaryKey(String id);

    int insert(DocPrePublicity record);

    int insertSelective(DocPrePublicity record);

    DocPrePublicity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPrePublicity record);

    int updateByPrimaryKey(DocPrePublicity record);
}