package com.digihealth.anesthesia.sysMng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasDictGroup;

@MyBatisDao
public interface BasDictGroupDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasDictGroup record);

    int insertSelective(BasDictGroup record);

    BasDictGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasDictGroup record);

    int updateByPrimaryKey(BasDictGroup record);
    
    BasDictGroup getDictGroupByGroupId(@Param("groupId")String groupId,@Param("beid")String beid);
    
    List<BasDictGroup> getDictItemGroups(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    Integer getDictItemGroupsNum(@Param("filters")List<Filter> filters);
    
    List<BasDictGroup> getDictItemByBeid(@Param("beid")String beid);
    
    int deleteBasDictGroupByBeid (@Param("beid")String beid);
}
