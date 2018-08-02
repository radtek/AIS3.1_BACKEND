/*
 * DocTitleDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-06 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.formbean.DocTitleFormBean;
import com.digihealth.anesthesia.doc.po.DocTitle;

@MyBatisDao
public interface DocTitleDao {
    int deleteByPrimaryKey(String docTitleId);

    int insert(DocTitle record);

    int insertSelective(DocTitle record);

    DocTitle selectByPrimaryKey(String docTitleId);

    int updateByPrimaryKeySelective(DocTitle record);

    int updateByPrimaryKey(DocTitle record);
    
    List<DocTitleFormBean> searchDocTitleByThemeId(String docThemeId);
    
    DocTitleFormBean searchDocTitleByTitleId(String titleId);
    
    int delDocTitleByThemeId(String docThemeId);
}