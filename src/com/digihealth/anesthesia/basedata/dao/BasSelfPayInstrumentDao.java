package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasSelfPayInstrument;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface BasSelfPayInstrumentDao {
    int deleteByPrimaryKey(String id);

    int insert(BasSelfPayInstrument record);

    int insertSelective(BasSelfPayInstrument record);

    BasSelfPayInstrument selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasSelfPayInstrument record);

    int updateByPrimaryKey(BasSelfPayInstrument record);
    
    List<BasSelfPayInstrument> selectAll(@Param("beid")String beid);

    List<BasSelfPayInstrument> searchSelfPayInstrument(@Param("baseQuery")BaseInfoQuery baseQuery);

    List<BasSelfPayInstrument> querySelfPayInstrumentList(@Param("filter")String filter, @Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);

    int querySelfPayInstrumentTotal(@Param("filter")String filter, @Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);

    BasSelfPayInstrument searchSelfPayInstrumentById(@Param("id")String id);
}