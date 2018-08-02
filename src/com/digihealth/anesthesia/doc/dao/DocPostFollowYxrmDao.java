/*
 * DocPostFollowYxrmDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-05-29 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPostFollowYxrm;

@MyBatisDao
public interface DocPostFollowYxrmDao {
    int deleteByPrimaryKey(String postFollowYxrmId);

    int insert(DocPostFollowYxrm record);

    int insertSelective(DocPostFollowYxrm record);

    DocPostFollowYxrm selectByPrimaryKey(String postFollowYxrmId);

    int updateByPrimaryKeySelective(DocPostFollowYxrm record);

    int updateByPrimaryKey(DocPostFollowYxrm record);

	DocPostFollowYxrm selectPostFollowYxrmByFollowId(@Param("postFollowId") String postFollowId);
	
	int deleteFollowYxrmByFollowId(@Param("postFollowId") String postFollowId); 
}