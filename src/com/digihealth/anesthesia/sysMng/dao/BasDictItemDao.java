/*
 * DictItemDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2016-05-04 Created
 */
package com.digihealth.anesthesia.sysMng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;



@MyBatisDao
public interface BasDictItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasDictItem record);

    int insertSelective(BasDictItem record);

    BasDictItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasDictItem record);

    int updateByPrimaryKey(BasDictItem record);
    
    List<BasDictItem> getListByGroupId(@Param("groupId")String groupId,@Param("codeValue")String codeValue,@Param("beid")String beid);
    
    BasDictItem getListByGroupIdandCodeName(@Param("groupId")String groupId,@Param("codeName")String codeName,@Param("beid")String beid);
    
    List<BasDictItem> getDictItemsByGroupId(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    Integer getDictItemsNumByGroupId(@Param("filters")List<Filter> filters);
    
    int deleteDictItemGroup (@Param("groupId")String groupId,@Param("beid")String beid);
    
    int deleteDictItemByBeid (@Param("beid")String beid);
    
    List<BasDictItem> getDictItemsByBeid(@Param("beid")String beid);
    
    public List<SysCodeFormbean> searchSysCodeByGroupId(@Param("groupId")String groupId,@Param("beid")String beid);
    
    public List<SysCodeFormbean> searchSysCodeByGroupIdAndCodeValue(@Param("groupId")String groupId,@Param("codeValue")String codeValue,@Param("beid")String beid);
    
    public List<SysCodeFormbean> searchSysCodeByGroupIdAndCodeName(@Param("groupId")String groupId,@Param("codeName")String codeName,@Param("beid")String beid);
}