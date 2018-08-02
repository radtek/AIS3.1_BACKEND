package com.digihealth.anesthesia.sysMng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.sysMng.formbean.SearchViewFormBean;
import com.digihealth.anesthesia.sysMng.po.BasEntity;

@MyBatisDao
public interface BasViewDao{
    
    List<Map<String, Object>> selectAllView();

    List<BasEntity> selectAllColumnsOfView(@Param("viewName") String viewName);

    List<Map<String, Object>> selectByColumns(@Param("filter")String filter,@Param("formBean")SearchViewFormBean formBean);
    
    int selectByColumnsTotal(@Param("filter")String filter,@Param("formBean")SearchViewFormBean formBean);
}