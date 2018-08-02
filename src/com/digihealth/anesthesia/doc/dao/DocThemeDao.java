/*
 * DocThemeDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-06 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.formbean.DocThemeFormBean;
import com.digihealth.anesthesia.doc.po.DocTheme;

@MyBatisDao
public interface DocThemeDao {
    int deleteByPrimaryKey(String docThemeId);

    int insert(DocTheme record);

    int insertSelective(DocTheme record);

    DocTheme selectByPrimaryKey(String docThemeId);

    int updateByPrimaryKeySelective(DocTheme record);

    int updateByPrimaryKey(DocTheme record);
    
    List<DocThemeFormBean> seachDocTheme(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    Integer seachDocThemeTotal(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    DocThemeFormBean seachDocThemeById(@Param("docThemeId")String docThemeId);

    DocThemeFormBean seachDocThemeByMenuId(@Param("menuId")String menuId);
}