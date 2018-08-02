package com.digihealth.anesthesia.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.tmp.po.TmpAnalgesic;

@MyBatisDao
public interface TmpAnalgesicDao {
    int deleteByPrimaryKey(String analTmpId);

    int insert(TmpAnalgesic record);

    int insertSelective(TmpAnalgesic record);

    TmpAnalgesic selectByPrimaryKey(String analTmpId);

    int updateByPrimaryKeySelective(TmpAnalgesic record);

    int updateByPrimaryKey(TmpAnalgesic record);
    
    List<TmpAnalgesic> selectTmpAnalgesicByType(@Param("analgesicType")String analgesicType,@Param("beid")String beid);
    
    int deleteTmpAnalgesicByType(@Param("analgesicType")String analgesicType,@Param("beid")String beid);
}