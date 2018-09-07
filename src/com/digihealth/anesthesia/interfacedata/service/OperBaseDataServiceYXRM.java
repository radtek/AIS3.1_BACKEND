package com.digihealth.anesthesia.interfacedata.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMethodDao;
import com.digihealth.anesthesia.basedata.dao.BasBusEntityDao;
import com.digihealth.anesthesia.basedata.dao.BasChargeItemDao;
import com.digihealth.anesthesia.basedata.dao.BasDeptDao;
import com.digihealth.anesthesia.basedata.dao.BasDiagnosedefDao;
import com.digihealth.anesthesia.basedata.dao.BasDispatchDao;
import com.digihealth.anesthesia.basedata.dao.BasInstrumentDao;
import com.digihealth.anesthesia.basedata.dao.BasMedicineDao;
import com.digihealth.anesthesia.basedata.dao.BasOperationPeopleDao;
import com.digihealth.anesthesia.basedata.dao.BasOperdefDao;
import com.digihealth.anesthesia.basedata.dao.BasPriceDao;
import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.dao.ControllerDao;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasChargeItem;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasInstrument;
import com.digihealth.anesthesia.basedata.po.BasMedicine;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasPrice;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.utils.ConnectionManager;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.dao.DocAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesMedicineAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAllergicReactionDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixCanalDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixGenDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixVisitDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryVenipunctureDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicInformedConsentDao;
import com.digihealth.anesthesia.doc.dao.DocExitOperSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocGeneralAnaesDao;
import com.digihealth.anesthesia.doc.dao.DocNerveBlockDao;
import com.digihealth.anesthesia.doc.dao.DocOperBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocOptCareRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptNurseDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPreVisitDao;
import com.digihealth.anesthesia.doc.dao.DocSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocSpinalCanalPunctureDao;
import com.digihealth.anesthesia.doc.po.DocAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixCanal;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixGen;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocAnalgesicInformedConsent;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.doc.po.DocPatInspectItem;
import com.digihealth.anesthesia.doc.po.DocPatInspectRecord;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;

/**
 * 永兴人民医院基础数据同步处理类
 * 
 */
@Service
public class OperBaseDataServiceYXRM {
	
	/**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
	private BasBusEntityDao basBusEntityDao = SpringContextHolder.getBean(BasBusEntityDao.class);
    private BasDeptDao basDeptDao = SpringContextHolder.getBean(BasDeptDao.class);
    private BasOperdefDao basOperdefDao = SpringContextHolder.getBean(BasOperdefDao.class);
    private BasDiagnosedefDao basDiagnosedefDao = SpringContextHolder.getBean(BasDiagnosedefDao.class);
    private BasOperationPeopleDao basOperationPeopleDao = SpringContextHolder.getBean(BasOperationPeopleDao.class);
    private BasAnaesMethodDao basAnaesMethodDao = SpringContextHolder.getBean(BasAnaesMethodDao.class);
    private BasInstrumentDao basInstrumentDao = SpringContextHolder.getBean(BasInstrumentDao.class);
    private BasMedicineDao basMedicineDao = SpringContextHolder.getBean(BasMedicineDao.class);
    private BasPriceDao basPriceDao = SpringContextHolder.getBean(BasPriceDao.class);
    private BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
    private DocPatInspectRecordDao docPatInspectRecordDao = SpringContextHolder.getBean(DocPatInspectRecordDao.class);
    private DocPatInspectItemDao docPatInspectItemDao = SpringContextHolder.getBean(DocPatInspectItemDao.class);
    private BasChargeItemDao basChargeItemDao = SpringContextHolder.getBean(BasChargeItemDao.class);
    //private BasUserDao basUserDao = SpringContextHolder.getBean(BasUserDao.class);
    private ControllerDao controllerDao = SpringContextHolder.getBean(ControllerDao.class);
    private DocAnaesRecordDao docAnaesRecordDao = SpringContextHolder.getBean(DocAnaesRecordDao.class);
    private DocAnalgesicInformedConsentDao docAnalgesicInformedConsentDao = SpringContextHolder.getBean(DocAnalgesicInformedConsentDao.class);
    private BasDispatchDao basDispatchDao = SpringContextHolder.getBean(BasDispatchDao.class);
    
	/**
	 * 获取HIS视图VIEW_OPERATION_NAME，并插入当前数据库
	 * 手术名称数据同步  his不提供code值 
	 */
	@Transactional
	public void synHisOperNameList(){
		logger.info("-------start synHisOperNameList-----------");
		String sql = "select * from view_operation_name ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if(!StringUtils.isEmpty(rs.getString("name")))
                {
                    //手术名称为空，则不需插入到数据库中
                    String code = rs.getString("code");
                    String name = StringFilter(rs.getString("name")).trim(); //过滤特殊字符
                    Integer enable = rs.getInt("enable");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    BaseInfoQuery baseQuery =new BaseInfoQuery();
                    baseQuery.setCode(code);
                    BasOperdef operDefFormBean= basOperdefDao.selectByCode(code, basBusEntityDao.getBeid());
                    if (null != operDefFormBean)
                    {
                        
                            BasOperdef operdef = basOperdefDao.queryOperdefById(operDefFormBean.getOperdefId());
                            if (checkData(operdef.getName(),name) || operdef.getEnable() != enable)
                            {
                                operdef.setName(name);
                                operdef.setEnable(enable);
                                operdef.setPinYin(pinyin);
                                basOperdefDao.update(operdef);
                            }
                       
                    }
                    else
                    {
                        BasOperdef odef = new BasOperdef();
                        odef.setOperdefId(GenerateSequenceUtil.generateSequenceNo());
                        odef.setCode(code);
                        odef.setName(name);
                        odef.setPinYin(pinyin);
                        odef.setEnable(enable);
                        odef.setBeid(basBusEntityDao.getBeid());
                        basOperdefDao.insert(odef);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisOperNameList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisOperNameList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisOperNameList-----------");
	}
	
	/**
	 * 获取手术医生
	 * 视图名称：VIEW_OPERATION_DOCTOR
	 */
	@Transactional
	public void synHisOperDoctorList(){
		logger.info("-------start synHisOperDoctorList-----------");
		
		String sql = "select * from view_operation_doctor ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    Integer region = rs.getInt("region");
                    Integer enable = rs.getInt("enable");
                    //手术医生名字为空，则不进行插入操作
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    
                    BasOperationPeople opd = basOperationPeopleDao.selectByCode(code, basBusEntityDao.getBeid());
                    if (null != opd)
                    {
                        if (checkData(opd.getName(),name.trim()) || opd.getEnable() != enable || !opd.getRegion().equals(region))
                        {
                        	 opd.setOperatorId(opd.getOperatorId());
                        	 opd.setCode(code);
                        	 opd.setName(name.trim());
                        	 opd.setEnable(enable);
                        	 opd.setRegion(region);
                        	 opd.setPinYin(pinyin);
                             basOperationPeopleDao.update(opd);
                       }
                       
                    }
                    else
                    {
                        opd = new BasOperationPeople();
                        opd.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                        opd.setCode(code);
                        opd.setName(name.trim());
                        opd.setEnable(enable);
                        opd.setRegion(region);
                        opd.setPinYin(pinyin);
                        opd.setBeid(basBusEntityDao.getBeid());
                        basOperationPeopleDao.insert(opd);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisOperDoctorList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn,pstmt,rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisOperDoctorList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisOperDoctorList-----------");
	}
	
	/**
	 * 获取麻醉方法列表
	 * 视图名称：VIEW_ANAES_METHOD
	 */
	@Transactional
	public void synHisAnaesMethodList(){
		logger.info("-------start synHisAnaesMethodList-----------");
		String sql = "select * from view_anaes_method";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
        {
		    conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String name = rs.getString("name");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    Integer isValid = rs.getInt("isvalid");
                    String code = rs.getString("code");
                    
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    
                    BasAnaesMethod anaesMethod = basAnaesMethodDao.selectByCode(code, basBusEntityDao.getBeid());
                    
                    if (null != anaesMethod)
                    {
                        if (checkData(anaesMethod.getCode(), code) || checkData(anaesMethod.getName(), name.trim()) || anaesMethod.getIsValid() != isValid)
                        {
                            anaesMethod.setAnaMedId(anaesMethod.getAnaMedId());
                            anaesMethod.setName(name.trim());
                            anaesMethod.setIsValid(isValid);
                            anaesMethod.setPinYin(pinyin);
                            basAnaesMethodDao.update(anaesMethod);
                        }
                    }
                    else
                    {
                        anaesMethod = new BasAnaesMethod();
                        anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                        anaesMethod.setCode(code);
                        anaesMethod.setName(name.trim());
                        anaesMethod.setIsValid(isValid);
                        anaesMethod.setPinYin(pinyin);
                        anaesMethod.setBeid(basBusEntityDao.getBeid());
                        basAnaesMethodDao.insert(anaesMethod);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisAnaesMethodList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisAnaesMethodList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisAnaesMethodList-----------");
	}
	
	/**
	 * 获取诊断名称列表
	 * 视图名称：VIEW_DIAG_NAME
	 */
	@Transactional
	public void synHisDiagNameList(){
		logger.info("-------start synHisDiagNameList-----------");
		
		String sql = "select * from view_diag_name";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String name = StringFilter(rs.getString("name"));
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    Integer enable = rs.getInt("enable");
                    
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    
                    BasDiagnosedef diagnosedef = basDiagnosedefDao.selectByCode(code, basBusEntityDao.getBeid());
                    
                    
                    if (null != diagnosedef)
					{

						if (checkData(diagnosedef.getName(), name) || diagnosedef.getEnable() != enable)
						{
							diagnosedef.setName(name);
							diagnosedef.setEnable(enable);
							diagnosedef.setPinYin(pinyin);
							basDiagnosedefDao.update(diagnosedef);
						}

					}
                    else
                    {
                        diagnosedef = new BasDiagnosedef();
            			diagnosedef.setDiagDefId(GenerateSequenceUtil.generateSequenceNo());
                        diagnosedef.setCode(code);
                        diagnosedef.setName(name);
                        diagnosedef.setEnable(enable);
                        diagnosedef.setPinYin(pinyin);
                        diagnosedef.setBeid(basBusEntityDao.getBeid());
                        basDiagnosedefDao.insert(diagnosedef);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisDiagNameList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisDiagNameList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisDiagNameList-----------");
	}
	
	/**
	 * 获取科室列表
	 * 视图名称：VIEW_DEPT_ROOM
	 */
	
	@Transactional
	public void synHisDeptRoomList(){
		logger.info("-------start synHisDeptRoomList-----------");
		String sql = "select * from view_dept_room";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            String beid = basBusEntityDao.getBeid();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String name = rs.getString("name");
                    Integer enable = rs.getInt("enable");
                    String id = rs.getString("deptId");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    BasDept dt = basDeptDao.searchDeptById(id,beid);
                    
                    if (dt!=null && (checkData(dt.getName(),name.trim()) || dt.getEnable() != enable))
                    {
                        dt.setName(name.trim());
                        dt.setEnable(enable);
                        dt.setPinYin(pinyin);
                        basDeptDao.update(dt);
                    }
                    
                    if(null == dt){
                        BasDept dept = new BasDept();
                        dept.setDeptId(id);
                        dept.setName(name.trim());
                        dept.setEnable(enable);
                        dept.setPinYin(pinyin);
                        dept.setBeid(beid);
                        basDeptDao.insert(dept);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisDeptRoomList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisDeptRoomList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisDeptRoomList-----------");
	}
	
	/**
	 * 获取器械列表
	 * 视图名称：VIEW_INSTRUMENT  
	 */
	@Transactional
	public void synHisInstrumentList(){
		logger.info("-------start synHisInstrumentList-----------");
        Connection conn = null;
        String sql = "select * from view_instrument";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String pinyin = rs.getString("pinyin");
                    Integer enable = rs.getInt("enable");
                    String beid = basBusEntityDao.getBeid();
                    BasInstrument instrument = basInstrumentDao.selectByCode(code, beid);
                    if (null != instrument)
					{

						if (checkData(instrument.getType(), type) || instrument.getEnable() != enable
								|| checkData(instrument.getName(), name))
						{
							instrument.setCode(code);
							instrument.setEnable(enable);
							instrument.setName(name);
							instrument.setType(type);
							instrument.setPinYin(StringUtils.isBlank(pinyin) ? PingYinUtil
											.getFirstSpell(name) : pinyin);
							basInstrumentDao.update(instrument);
						}

					}
                    else
                    {
                        instrument = new BasInstrument();
                        instrument.setInstrumentId(GenerateSequenceUtil.generateSequenceNo());
                        instrument.setEnable(enable);
                        instrument.setName(name);
                        instrument.setType(type);
                        instrument.setCode(code);
                        instrument.setPinYin(pinyin);
                        instrument.setBeid(beid);
                        basInstrumentDao.insert(instrument);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisInstrumentList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisInstrumentList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
        
		
		logger.info("-------end synHisInstrumentList-----------");
	}
	
	/**
	 * 获取药品库信息
	 * 视图名称：VIEW_DRUG_STORE 
	 */
	@Transactional
	public void synHisDrugStoreList(){
		logger.info("-------start synHisDrugStoreList-----------");
		
		Connection conn = null;
		String sql = "select * from view_drug_store";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String type = rs.getString("type");
                    Integer enable = rs.getInt("enable");
                    String name = rs.getString("name");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    String briefName = rs.getString("briefName");
                    Float packageDosageAmout = rs.getFloat("packageDosageAmount");
                    String dosageUnit = rs.getString("dosageUnit");
                    String spec = rs.getString("spec");
                    String code = rs.getString("code");
                    BasMedicine medicine = basMedicineDao.selectByCode(code, basBusEntityDao.getBeid());
                    if (null != medicine)
                    {
                        if (checkData(medicine.getBriefName(),briefName)
                                || checkData(medicine.getDosageUnit(),dosageUnit) || medicine.getEnable() != enable
                                || checkData(medicine.getName(),name)
                                || medicine.getPackageDosageAmount() != packageDosageAmout
                                || checkData(medicine.getSpec(),spec) || checkData(medicine.getType(),type))
						{
							medicine.setType(type);
							//medicine.setEnable(enable); 不更新状态了，麻醉用药由罗艳辉自己在现场维护的
							medicine.setPinYin(pinyin);
							medicine.setName(name);
							medicine.setBriefName(briefName);
							medicine.setPackageDosageAmount(packageDosageAmout);
							medicine.setDosageUnit(dosageUnit);
							medicine.setSpec(spec);
							basMedicineDao.update(medicine);
						}
                            
                    }
                    else
                    {
                        medicine = new BasMedicine();
                        medicine.setMedicineId(GenerateSequenceUtil.generateSequenceNo());
                        medicine.setCode(code);
                        medicine.setType(type);
                        medicine.setEnable(enable);
                        medicine.setPinYin(pinyin);
                        medicine.setName(name);
                        medicine.setBriefName(briefName);
                        medicine.setPackageDosageAmount(packageDosageAmout);
                        medicine.setDosageUnit(dosageUnit);
                        medicine.setSpec(spec);
                        medicine.setBeid(basBusEntityDao.getBeid());
                        basMedicineDao.insert(medicine);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisDrugStoreList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisDrugStoreList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		
		logger.info("-------end synHisDrugStoreList-----------");
	}
	
	/**
	 * 获取药品价格列表
	 * 视图：VIEW_MEDICINE_PRICE
	 */
	@Transactional
	public void synHisMedicinePriceList(){
		logger.info("-------start synHisMedicinePriceList-----------");
		Connection conn = null;
		String sql = "select * from view_medicine_price";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            //basPriceDao.updateEnable();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String spec = rs.getString("spec");
                    String firm = rs.getString("firm");
                    String batch = rs.getString("batch");
                    String minPackageUnit = rs.getString("minPackageUnit");
                    Float priceMinPackage = rs.getFloat("priceMinPackage");
                    Integer enable = rs.getInt("enable");
                    BasPrice price = basPriceDao.selectByCode(code,basBusEntityDao.getBeid());
                    if (null != price)
                    {
                        
                        if (checkData(price.getSpec(),spec) 
                            	|| checkData(price.getFirm(),firm)
                                || checkData(price.getBatch(),batch) || checkData(price.getMinPackageUnit(),minPackageUnit)
                                || price.getPriceMinPackage() != priceMinPackage
                                || price.getEnable() != enable)
						{
							price.setSpec(spec);
							price.setFirm(firm);
							price.setBatch(batch);
							price.setMinPackageUnit(minPackageUnit);
							price.setPriceMinPackage(priceMinPackage);
							price.setEnable(enable);
							basPriceDao.update(price);
						}
                        
                    }
                    else
                    {
                        price = new BasPrice();
                        price.setPriceId(GenerateSequenceUtil.generateSequenceNo());
                        price.setCode(code);
                        price.setSpec(spec);
                        price.setFirm(firm);
                        price.setBatch(batch);
                        price.setMinPackageUnit(minPackageUnit);
                        price.setPriceMinPackage(priceMinPackage);
                        price.setEnable(enable);
                        price.setBeid(basBusEntityDao.getBeid());
                        basPriceDao.insert(price);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisMedicinePriceList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisMedicinePriceList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		
		logger.info("-------end synHisMedicinePriceList-----------");
	}
	
	
	/**
	 * 获取收费项目列表
	 * 视图：VIEW_CHARGE_ITEM
	 */
	@Transactional
	public void synHisChargeItemList(){
		logger.info("-------start synHisChargeItemList-----------");
		Connection conn = null;
		String sql = "select * from view_charge_item";
		ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    List<BasChargeItem> chargeItems = basChargeItemDao.selectByCode(code, basBusEntityDao.getBeid());
                    String spec = rs.getString("spec");

                    String chargeItemName = rs.getString("name");
                    
                    if(StringUtils.isNotBlank(chargeItemName)){
                    	chargeItemName = StringFilter(chargeItemName); //chargeItemName.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
                    }
                    logger.info("----------------------------"+chargeItemName+"-----------------------------");

                    String pinyin = rs.getString("pinyin");
                    if(StringUtils.isNotBlank(pinyin)){
                    	pinyin = pinyin.replaceAll("[^a-zA-Z]", "");
                    }

                    String unit = rs.getString("unit");
                    if(StringUtils.isNotBlank(unit)){
                    	unit = unit.trim();
                    }
                    float basicUnitAmout = rs.getFloat("basicUnitAmount");
                    float price = rs.getFloat("price");
                    float basicUnitPrice = rs.getFloat("basicUnitPrice");
                    String type = rs.getString("type");
                    String chargeType = rs.getString("chargeType");
                    Integer enable = rs.getInt("enable");
                    if (null != chargeItems && chargeItems.size() > 0)
                    {
                        for (BasChargeItem chargeItem : chargeItems)
                        {
                        	if(checkData(chargeItem.getChargeItemName(), chargeItemName) || checkData(chargeItem.getSpec(),spec) ||
                        			checkData(chargeItem.getUnit(),unit) || 
                        			Math.abs(chargeItem.getBasicUnitAmount()-basicUnitAmout)> 0 ||
                        			Math.abs(chargeItem.getPrice() - price)> 0 ||
                        			Math.abs(chargeItem.getBasicUnitPrice() - basicUnitPrice) >0 ||
                        			checkData(chargeItem.getType(),type) || checkData(chargeItem.getChargeType(),chargeType) ||
                        			chargeItem.getEnable() != enable){
                                chargeItem.setChargeItemName(chargeItemName);
                                chargeItem.setSpec(spec);
                                chargeItem.setPinYin(pinyin);
                                chargeItem.setUnit(unit);
                                chargeItem.setBasicUnitAmount(basicUnitAmout);
                                chargeItem.setPrice(price);
                                chargeItem.setBasicUnitPrice(basicUnitPrice);
                                chargeItem.setType(type);
                                chargeItem.setChargeType(chargeType);
                                chargeItem.setEnable(enable);
                                basChargeItemDao.updateByPrimaryKeySelective(chargeItem);
                            }
                        }
                    }
                    else
                    {
                        BasChargeItem chargeItem = new BasChargeItem();
                        chargeItem.setChargeItemId(GenerateSequenceUtil.generateSequenceNo());
                        chargeItem.setChargeItemCode(code);
                        chargeItem.setChargeItemName(chargeItemName);
                        chargeItem.setSpec(spec);
                        chargeItem.setPinYin(pinyin);
                        chargeItem.setUnit(unit);
                        chargeItem.setBasicUnitAmount(basicUnitAmout);
                        chargeItem.setPrice(price);
                        chargeItem.setBasicUnitPrice(basicUnitPrice);
                        chargeItem.setType(type);
                        chargeItem.setChargeType(chargeType);
                        chargeItem.setEnable(enable);
                        chargeItem.setBeid(basBusEntityDao.getBeid());
                        basChargeItemDao.insert(chargeItem);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisChargeItemList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisChargeItemList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
        
		logger.info("-------end synHisChargeItemList-----------");
	}
	
	public static String StringFilter(String str) throws PatternSyntaxException {
		if(StringUtils.isBlank(str)){
			return str;
		}
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "['?？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	
	public static String StringNumberFilter(String str) throws PatternSyntaxException {
		if(StringUtils.isBlank(str)){
			return str;
		}
		// 只允许字母和数字
		 String regEx = "[^0-9]";
		// 清除掉所有特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return  m.replaceAll("").trim();
	}
	
	
	public boolean checkData(String changeValue,String sourceValue){
		if(changeValue == null){
			changeValue = "";
		}
		if(sourceValue == null){
			sourceValue = "";
		}
		if(!changeValue.equals(sourceValue)){
			return true;
		}
		return false;
	}
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException
    {
        ConnectionManager.closeConnection();
        
        if (null != pstmt)
        {
            pstmt.close();
        }
        
        if (null != rs)
        {
            rs.close();
        }
    }
	
	 /**
     * 获取永兴检验报告
     */
    @Transactional
    public void queryCheckListMasterYXRM(String regOptId)
    {
        logger.info("---------------------begin queryCheckListMasterYXRM------------------------");
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		Connection conn = null;
		String sql = "select * from v_report_sm where hid = '"+regOpt.getHid()+"'";
		ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            List<DocPatInspectRecord> patRecordList = new ArrayList<DocPatInspectRecord>();
            
            while (rs.next()) 
            {
                /**
                 * 报告状态 inspectStatus   N   1：未出报告 2：已出报告，未审核 3：已出报告，已审核
                 */
                DocPatInspectRecord rec = new DocPatInspectRecord();
                
                rec.setInspectId(rs.getString("inspectid"));
                if(StringUtils.isNotEmpty(rs.getString("inspecttime")))
                {
                	rec.setDate(DateUtils.getParseTime(rs.getString("inspecttime")));
                }
                if(StringUtils.isNotEmpty(rs.getString("seporttime")))
                {
                	rec.setReportDate(DateUtils.getParseTime(rs.getString("seporttime")));
                }
                rec.setState(rs.getString("inspectstaus"));
                rec.setDep(rs.getString("deptname"));
                rec.setReqDoctorId(rs.getString("doctorname"));
                rec.setInstruction(rs.getString("inspectname"));//inspectName
                rec.setRegOptId(regOptId);
                patRecordList.add(rec);
            }
            
            if(patRecordList != null && patRecordList.size()>0){
                for (DocPatInspectRecord record : patRecordList) {

                    //查询一个检查是否已经存在了，如果不存在插入；否则更新状态和详情。
                    String inspectId = record.getInspectId();
                    String status = record.getState();
                    
                    DocPatInspectRecord patInspectRecord = null;
                    patInspectRecord = docPatInspectRecordDao.selectRecordByInspectId(regOptId,inspectId);
                    if(null != patInspectRecord)
                    {
                        //String remark = patInspectRecord.getState();
                        //判断状态是否发生变化，变化了更新状态
                        if(!patInspectRecord.getState().equals(status))
                        {
                            patInspectRecord.setState(status);
                            docPatInspectRecordDao.updateByPrimaryKeySelective(patInspectRecord);
                        }
                        
                        List<DocPatInspectItem> patiItemList = docPatInspectItemDao.queryRecordByInspectId(patInspectRecord.getRegOptId(),patInspectRecord.getId());
                        if(null == patiItemList || patiItemList.size()==0)
                        {
                            List<DocPatInspectItem> itemList = queryCheckDetailDataListYXRM(record.getInspectId(),patInspectRecord.getId());
                            if(null != itemList && itemList.size()>0)
                            {
                                for(DocPatInspectItem patInspectItem : itemList)
                                {
                                    String itId = GenerateSequenceUtil.generateSequenceNo();
                                    patInspectItem.setId(itId);
                                    patInspectItem.setRegOptId(regOptId);
                                    docPatInspectItemDao.insertSelective(patInspectItem);
                                }
                            }
                        }
                    }
                    else
                    {
                        patInspectRecord = new DocPatInspectRecord();
                        String id = GenerateSequenceUtil.generateSequenceNo();
                        patInspectRecord.setId(id);
                        patInspectRecord.setRegOptId(record.getRegOptId());
                        patInspectRecord.setInspectId(record.getInspectId());
                        patInspectRecord.setState(record.getState());
                        patInspectRecord.setDate(record.getDate());
                        patInspectRecord.setReportDate(record.getReportDate());
                        patInspectRecord.setDep(record.getDep());
                        patInspectRecord.setReqDoctorId(record.getReqDoctorId());
                        patInspectRecord.setInstruction(record.getInstruction());//inspectName
                        docPatInspectRecordDao.insertSelective(patInspectRecord);
                        
                        List<DocPatInspectItem> itemList = queryCheckDetailDataListYXRM(patInspectRecord.getInspectId(),patInspectRecord.getId());
                        if(null != itemList && itemList.size()>0)
                        {
                            for(DocPatInspectItem patInspectItem : itemList)
                            {
                                String itId = GenerateSequenceUtil.generateSequenceNo();
                                patInspectItem.setId(itId);
                                patInspectItem.setRegOptId(regOptId);
                                docPatInspectItemDao.insertSelective(patInspectItem);
                            }
                        }
                    }
                }
            }   
            
            logger.info("queryCheckListMasterYXRM=============while end===============");
        } catch (Exception e) {
            logger.info("queryCheckListMasterYXRM Exception ====="+Exceptions.getStackTraceAsString(e));
            e.printStackTrace();
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("queryCheckListMasterYXRM--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
        logger.info("-------end queryCheckListMasterYXRM-----------");
    }
    
    /**
     * 永兴人民检验检查报告详细信息
     */
    public List<DocPatInspectItem> queryCheckDetailDataListYXRM(String inspectId,String recordId)
    {
        logger.info("---------------------begin queryCheckDetailDataListYXRM------------------------");
        Connection conn = null;
		String sql = "select * from v_report_sm_mx where inspectId = '"+inspectId+"'";
		ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = ConnectionManager.getYXRMHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            List<DocPatInspectItem> patInspectItemList = new ArrayList<DocPatInspectItem>();
            
            while (rs.next()) 
            {
                DocPatInspectItem rec = new DocPatInspectItem();
                rec.setRecId(recordId);
                rec.setName(rs.getString("itemname"));
                rec.setRefVal(rs.getString("refrange"));
                rec.setVal(rs.getString("result"));
                if(StringUtils.isNotEmpty(rs.getString("reporttime")))
                    rec.setDate(DateUtils.getParseTime(rs.getString("reporttime")));
                
                /**
                 * 0：正常 1：偏高 2：偏低
                 */
                String abnormal = rs.getString("abnormal");
                if(StringUtils.isNotEmpty(abnormal)){
                    if(abnormal.equals("0")){
                        rec.setResult("正常");
                    }
                    if(abnormal.equals("1")){
                        rec.setResult("偏高");
                    }
                    if(abnormal.equals("2")){
                        rec.setResult("偏低");
                    }
                }
                patInspectItemList.add(rec);
            }
            logger.info("queryCheckDetailDataListYXRM=============while end===============");
            return patInspectItemList;
        } catch (Exception e) {
            logger.info("queryCheckDetailDataListYXRM Exception ====="+Exceptions.getStackTraceAsString(e));
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("queryCheckListMasterYXRM--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
    }
    
	/**
	 * 同步HIS手术通知数据,作为接口同步的补充
	 */
	@Transactional(readOnly =false)
	public void synHisOperList(){
		logger.info("---------------------begin synHisOperList------------------------");
		String beid = basBusEntityDao.getBeid();
		List<BasRegOpt> broList = basRegOptDao.searchRegOptByOperaDate(DateUtils.DateToString(DateUtils.addDays(new Date(),-1)), beid);
        Map<String,BasRegOpt> localMap = new HashMap<String,BasRegOpt>();
        
        String reservenumberStr = "";
        
        if(null!=broList && broList.size()>0){
	        for (BasRegOpt regOpt : broList) {
	        	if(StringUtils.isNotBlank(regOpt.getPreengagementnumber())){
	        		localMap.put(regOpt.getPreengagementnumber(), regOpt);
	        		reservenumberStr += regOpt.getPreengagementnumber() + ",";
	        	}
			}
        }
		
		Connection conn = ConnectionManager.getYXRMHisConnection();
		Connection conn2 = ConnectionManager.getYXRMHisConnection();
		
		logger.info("synHisOperList=============conn = "+conn+"===============");
		
	    String sql = "select * from v_手术申请信息  where OPERDATE >= TO_CHAR(sysdate,'yyyy-mm-dd') and operstat = '正常' ";
	    
	    //将已经同步过的数据剔除掉
	    if(StringUtils.isNotBlank(reservenumberStr)){
        	reservenumberStr = reservenumberStr.substring(0,reservenumberStr.length()-1);
        	sql = sql + " and reservenumber not in ("+reservenumberStr+")";
        }
	    
	    List<BasRegOpt> hisRegOptList = new ArrayList<BasRegOpt>();
	    
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;
	    
	    int total = 0; //计数器
	    
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        
	        logger.info("synHisOperList=============sql =  "+sql+"===============");
	        
	        rs = pstmt.executeQuery();
	        while (rs.next()) 
	        {
	        	BasRegOpt regOpt = new BasRegOpt();
            	
            	regOpt.setPreengagementnumber(rs.getString("reservenumber"));
            	regOpt.setName(rs.getString("name"));
            	//HIS传过来的年龄，月，天  都在 age 字段
                String hisAge = rs.getString("age");
                if(StringUtils.isNotBlank(hisAge))
                {
                	if(hisAge.contains("岁"))
                	{
                		String [] ageList = hisAge.split("岁");
                    	if(null != ageList && ageList.length >0)
                    	{
                    		String age = ageList[0];
                    		regOpt.setAge(Integer.valueOf(age));
                    		if(ageList.length == 2)
                     		{
                     			hisAge = ageList[1];
                     		}
                    	}
                	}
                	
                	if(hisAge.contains("月"))
                	{
                		String [] ageMonList = hisAge.split("月");
                    	if(null != ageMonList && ageMonList.length>0)
                    	{
                    		String month = ageMonList[0];
                    		regOpt.setAgeMon(Integer.valueOf(month));
                    		if(ageMonList.length == 2)
                     		{
                     			hisAge = ageMonList[1];
                     		}
                    	}
                	}
                	
                	if(hisAge.contains("天"))
                	{
                		String [] ageDayList = hisAge.split("天");
                    	if(null != ageDayList && ageDayList.length>0)
                    	{
                    		String ageDay = ageDayList[0];
                    		regOpt.setAgeDay(Integer.valueOf(ageDay));
                    		if(ageDayList.length == 2)
                     		{
                     			hisAge = ageDayList[1];
                     		}
                    	}
                	}
                	
                	if(hisAge.contains("小时"))
                	{
                		String [] ageHuorList = hisAge.split("小时");
                    	if(null != ageHuorList && ageHuorList.length>0)
                    	{
                    		String ageHour = ageHuorList[0];
                    	    int age = Integer.valueOf(ageHour);
                    	    int ageDay2 = (int) Math.ceil(age/24) ;  
                    	    int ageDay = 0;
                    	    if(null != regOpt.getAgeDay())
                    	    {
                    	    	ageDay = regOpt.getAgeDay(); 
                    	    }
                    	    int sumDay = ageDay + ageDay2;
                    	    if(sumDay < 1)
                    	    {
                    	    	sumDay = 1;
                    	    }
                    	    regOpt.setAgeDay(sumDay);
                    	}
                	}
                }
            	
        		if(StringUtils.isNotEmpty(rs.getString("birthday"))){
        			regOpt.setBirthday(DateUtils.strToStr(rs.getString("birthday"), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
        		}
            	regOpt.setSex(rs.getString("sex"));
            	regOpt.setMedicalType(rs.getString("medicaltype"));
            	regOpt.setIdentityNo(rs.getString("crednumber"));
            	regOpt.setHid(rs.getString("hid"));
            	regOpt.setCid(rs.getString("cid"));
        		regOpt.setRegionId(rs.getString("regionid"));
        		regOpt.setRegionName(rs.getString("regionname"));
        		regOpt.setDeptName(rs.getString("deptname"));
        		regOpt.setDeptId(rs.getString("deptid"));
            	regOpt.setBed(rs.getString("bed"));
            	
            	/**
            	 * 拟实施诊断
            	 */
            	String diagDef = "";
                String diagName = "";
                String[] diagnames = null;
                if (StringUtils.isNotEmpty(rs.getString("diagname")))
                {
                	diagnames = rs.getString("diagname").split(",");
                    for (int i = 0; i < diagnames.length; i++)
                    {
                    	String name = diagnames[i];
                    	BasDiagnosedef basDiagnosedef = basDiagnosedefDao.selectByCode(name, beid);
                    	if (null != basDiagnosedef)
                        {
                            diagDef = diagDef + basDiagnosedef.getDiagDefId() + ",";
                            diagName = diagName + basDiagnosedef.getName() + ",";
                        }
                        else
                        {
                            BasDiagnosedef diagnosedef = new BasDiagnosedef();
                            diagnosedef.setDiagDefId(GenerateSequenceUtil.generateSequenceNo());
                            diagnosedef.setCode(null);
                            diagnosedef.setName(name);
                            diagnosedef.setEnable(1);
                            diagnosedef.setPinYin(PingYinUtil.getFirstSpell(name));
                            diagnosedef.setBeid(beid);
                            basDiagnosedefDao.insert(diagnosedef);
                            
                            diagDef = diagDef + diagnosedef.getDiagDefId() + ",";
                            diagName = diagName + name + ",";
                        }
                    }
                }
                
                diagDef = StringUtils.isNotBlank(diagDef) ? diagDef.substring(0, diagDef.length() - 1) : "";
                diagName = StringUtils.isNotBlank(diagName) ? diagName.substring(0, diagName.length() - 1) : "";
                regOpt.setDiagnosisCode(diagDef);
                regOpt.setDiagnosisName(diagName);
            	
            	//拟施手术
        		//regOpt.setDesignedOptName(rs.getString("opername"));
        		// 手术名称
                String operId = "";
                String operName = "";
                String[] operCodes = null;
                if (StringUtils.isNotEmpty(rs.getString("opercode")))
                {
                    operCodes = rs.getString("opercode").split(",");
                    for (int i = 0; i < operCodes.length; i++)
                    {
                    	BasOperdef basOperdef = basOperdefDao.selectByCode(operCodes[i], beid);
                        if (null != basOperdef)
                        {
                            operId = operId + basOperdef.getOperdefId() + ",";
                            operName = operName + basOperdef.getName() + ",";
                        }
                    }
                }
                if ("".equals(operName) && StringUtils.isNotBlank(rs.getString("opername")))
                {
                    String[] operNames = rs.getString("opername").split(",");
                    for (int i = 0; i < operNames.length; i++)
                    {
                        String name = operNames[i];
                        String code = null != operCodes ? operCodes[i] : null;
                        List<BasOperdef> opers = basOperdefDao.selectByName(name, beid);
                        if (null != opers && opers.size() > 0)
                        {
                            operId = operId + opers.get(0).getOperdefId() + ",";
                            operName = operName + opers.get(0).getName() + ",";
                        }
                        else
                        {
                            BasOperdef operdef = new BasOperdef();
                            operdef.setOperdefId(GenerateSequenceUtil.generateSequenceNo());
                            operdef.setName(name);
                            operdef.setCode(code);
                            operdef.setEnable(1);
                            operdef.setPinYin(PingYinUtil.getFirstSpell(name));
                            operdef.setBeid(beid);
                            basOperdefDao.insert(operdef);
                            
                            operId = operId + operdef.getOperdefId() + ",";
                            operName = operName + name + ",";
                        }
                    }
                }
                operId = StringUtils.isNotBlank(operId) ? operId.substring(0, operId.length() - 1) : "";
                operName = StringUtils.isNotBlank(operName) ? operName.substring(0, operName.length() - 1) : "";
                regOpt.setDesignedOptCode(operId);
                regOpt.setDesignedOptName(operName);
                
                // 手术医生
                String operatorId = "";
                String operatorName = "";
                String[] operatorCodes = null;
                if (StringUtils.isNotEmpty(rs.getString("surgerydoctorid")))
                {
                    operatorCodes = rs.getString("surgerydoctorid").split(",");
                    for (int i = 0; i < operatorCodes.length; i++)
                    {
                    	BasOperationPeople basOperationPeople =
                            basOperationPeopleDao.selectByCode(operatorCodes[i], beid);
                        if (null != basOperationPeople)
                        {
                            operatorId = operatorId + basOperationPeople.getOperatorId() + ",";
                            operatorName = operatorName + basOperationPeople.getName() + ",";
                        }
                    }
                }
                if ("".equals(operatorName) && StringUtils.isNotBlank(rs.getString("surgerydoctorname")))
                {
                    String[] operatorNames = rs.getString("surgerydoctorname").split(",");
                    
                    for (int i = 0; i < operatorNames.length; i++)
                    {
                        String name = operatorNames[i];
                        String code = null != operatorCodes ? operatorCodes[i] : null;
                        List<BasOperationPeople> operators =
                            basOperationPeopleDao.selectByName(name, beid);
                        if (null != operators && operators.size() > 0)
                        {
                            operatorId = operatorId + operators.get(0).getOperatorId() + ",";
                            operatorName = operatorName + operators.get(0).getName() + ",";
                        }
                        else
                        {
                            BasOperationPeople operationPeople = new BasOperationPeople();
                            operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                            operationPeople.setName(name);
                            operationPeople.setCode(code);
                            operationPeople.setEnable(1);
                            operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                            operationPeople.setBeid(beid);
                            basOperationPeopleDao.insert(operationPeople);
                            
                            operatorId = operatorId + operationPeople.getOperatorId() + ",";
                            operatorName = operatorName + name + ",";
                        }
                    }
                }
                operatorId = StringUtils.isNotBlank(operatorId) ? operatorId.substring(0, operatorId.length() - 1) : "";    
                operatorName = StringUtils.isNotBlank(operatorName) ? operatorName.substring(0, operatorName.length() - 1) : ""; 
                regOpt.setOperatorName(operatorName);
                regOpt.setOperatorId(operatorId);
                
        		// 助手医生处理
                String assistantId = "";
                String assistantName = "";
                String[] assistantCodes = null;
                if (StringUtils.isNotEmpty(rs.getString("assistantid")))
                {
                    assistantCodes = rs.getString("assistantid").split(",");
                    for (int i = 0; i < assistantCodes.length; i++)
                    {
                    	BasOperationPeople basOperationPeople =
                            basOperationPeopleDao.selectByCode(assistantCodes[i], beid);
                        if (null != basOperationPeople)
                        {
                            assistantId = assistantId + basOperationPeople.getOperatorId() + ",";
                            assistantName = assistantName + basOperationPeople.getName() + ",";
                        }
                    }
                }
                if ("".equals(assistantName) && StringUtils.isNotBlank(rs.getString("assistantname")))
                {
                    String[] operatorNames = rs.getString("assistantname").split(",");
                    for (int i = 0; i < operatorNames.length; i++)
                    {
                        String name = operatorNames[i];
                        String code = null != assistantCodes ? assistantCodes[i] : null;
                        
                        List<BasOperationPeople> operators =
                            basOperationPeopleDao.selectByName(name, beid);
                        if (null != operators && operators.size() > 0)
                        {
                            assistantId = assistantId + operators.get(0).getOperatorId() + ",";
                            assistantName = assistantName + operators.get(0).getName() + ",";
                        }
                        else
                        {
                            BasOperationPeople operationPeople = new BasOperationPeople();
                            operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                            operationPeople.setName(name);
                            operationPeople.setCode(code);
                            operationPeople.setEnable(1);
                            operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                            operationPeople.setBeid(beid);
                            basOperationPeopleDao.insert(operationPeople);
                            
                            assistantId = assistantId + operationPeople.getOperatorId() + ",";
                            assistantName = assistantName + name + ",";
                        }
                    }
                }
                assistantId = StringUtils.isNotBlank(assistantId) ? assistantId.substring(0, assistantId.length() - 1) : "";
                assistantName = StringUtils.isNotBlank(assistantName) ? assistantName.substring(0, assistantName.length() - 1) : "";
                regOpt.setAssistantId(assistantId);
                regOpt.setAssistantName(assistantName);
        		
        		if(StringUtils.isNotBlank(rs.getString("weight"))){
        			regOpt.setWeight(Float.parseFloat(rs.getString("weight").trim()));
        		}
            	if(StringUtils.isNotBlank(rs.getString("height"))){
        			regOpt.setHeight(Float.parseFloat(rs.getString("height").trim()));
        		}
        		regOpt.setHbsag(rs.getString("hbsag"));
        		regOpt.setHcv(rs.getString("hcv"));
        		regOpt.setHiv(rs.getString("hiv"));
        		regOpt.setHp(rs.getString("hp"));
        		//String operDate = DateUtils.strToStr(rs.getString("operdate"), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            	regOpt.setOperaDate(rs.getString("operdate"));
            	regOpt.setStartTime(rs.getString("operstarttime"));
            	String operlevel =rs.getString("operlevel");
            	if(StringUtils.isNotBlank(operlevel))
            	{
            		if("小".equals(operlevel))
            		{
            			regOpt.setOptLevel("一级");
            		}else if("中".equals(operlevel))
            		{
            			regOpt.setOptLevel("二级");
            		}else if("大".equals(operlevel))
            		{
            			regOpt.setOptLevel("三级");
            		}else if("特".equals(operlevel))
            		{
            			regOpt.setOptLevel("四级");
            		}
            	}
            	           	
            	if(StringUtils.isNotBlank(rs.getString("incisionlevel"))){
            		Integer cutLevel = null;
            		if("Ⅰ".equals(rs.getString("incisionlevel")))
            			cutLevel = 1;
            		if("Ⅱ".equals(rs.getString("incisionlevel")))
            			cutLevel = 2;
            		if("Ⅲ".equals(rs.getString("incisionlevel")))
            			cutLevel = 3;
            		if("Ⅳ".equals(rs.getString("incisionlevel")))
            			cutLevel = 4;
        			regOpt.setCutLevel(cutLevel);
        		}
            	
            	if(StringUtils.isNotBlank(rs.getString("opersource"))){
        			regOpt.setOperSource(new Integer(rs.getString("opersource")));
        		}

        		if(!StringUtils.isEmpty(rs.getString("opertype"))){
        			regOpt.setEmergency(Integer.parseInt(rs.getString("opertype").trim()));
        		}
            	
        		// 麻醉方法
                String designedAnaesMethodName = "";
                String designedAnaesMethodCode = "";
                String[] anaesCodes = null;
                
                if (StringUtils.isNotBlank(rs.getString("anaesid")))
                {
                    anaesCodes = rs.getString("anaesid").split(",");
                    
                    for (String anaesId : anaesCodes)
                    {
                    	BasAnaesMethod basAnaesMethod = basAnaesMethodDao.selectByCode(anaesId, beid);
                        if (null != basAnaesMethod)
                        {
                            designedAnaesMethodCode = designedAnaesMethodCode + basAnaesMethod.getAnaMedId() + ",";
                            designedAnaesMethodName = designedAnaesMethodName + basAnaesMethod.getName() + ",";
                        }
                    }
                }
                if ("".equals(designedAnaesMethodName) && StringUtils.isNotBlank(rs.getString("anaesname")))
                {
                    String[] anaesNames = rs.getString("anaesname").split(",");
                    for (int i = 0; i < anaesNames.length; i++)
                    {
                        String name = anaesNames[i];
                        String code = null != anaesCodes ? anaesCodes[i] : null;
                        List<BasAnaesMethod> anaesMethods = basAnaesMethodDao.selectByName(name, beid);
                        if (null != anaesMethods && anaesMethods.size() > 0)
                        {
                            designedAnaesMethodCode = designedAnaesMethodCode + anaesMethods.get(0).getAnaMedId() + ",";
                            designedAnaesMethodName = designedAnaesMethodName + anaesMethods.get(0).getName() + ",";
                        }
                        else
                        {
                            BasAnaesMethod anaesMethod = new BasAnaesMethod();
                            anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                            anaesMethod.setCode(code);
                            anaesMethod.setName(name);
                            anaesMethod.setIsValid(1);
                            anaesMethod.setPinYin(PingYinUtil.getFirstSpell(name));
                            anaesMethod.setBeid(beid);
                            basAnaesMethodDao.insert(anaesMethod); 
                            
                            designedAnaesMethodCode = designedAnaesMethodCode + anaesMethod.getAnaMedId() + ",";
                            designedAnaesMethodName = designedAnaesMethodName + name + ",";
                        }
                    }
                }
                designedAnaesMethodCode = StringUtils.isNotBlank(designedAnaesMethodCode) ? designedAnaesMethodCode.substring(0, designedAnaesMethodCode.length() - 1) : "";
                designedAnaesMethodName = StringUtils.isNotBlank(designedAnaesMethodName) ? designedAnaesMethodName.substring(0, designedAnaesMethodName.length() - 1) : "";
                regOpt.setDesignedAnaesMethodCode(designedAnaesMethodCode);
                regOpt.setDesignedAnaesMethodName(designedAnaesMethodName);
                regOpt.setDesignedAnaesMethodCodes(StringUtils.getListByString(designedAnaesMethodCode));
        		//全麻局麻控制
        		BasRegOptUtils.IsLocalAnaesSet(regOpt);
            	regOpt.setCreateUser(rs.getString("createuser"));
            	if(StringUtils.isNotBlank(rs.getString("frontoperforbidtake"))){
        			regOpt.setFrontOperForbidTake(new Integer(rs.getString("frontoperforbidtake")));
        		}
        		regOpt.setFrontOperSpecialCase(rs.getString("frontoperspecialcase"));
        		regOpt.setCreateTime(DateUtils.formatDateTime(rs.getDate("createoperdate")));
        		regOpt.setState(OperationState.NO_SCHEDULING);
        		regOpt.setBeid(beid);
        		//手日期和手术创建日期都是今天，把手术设置为急诊
        		checkEmergency(regOpt);
        		hisRegOptList.add(regOpt);
        		total++;
	        }
	        
	        logger.info("synHisOperList==============HIS传入需要匹配的数据条数为："+total+"===============");
	        
	        
	        
	        if(null!=hisRegOptList && hisRegOptList.size()>0)
			{
	        	for(BasRegOpt basRegOpt:hisRegOptList)
				{
					String regOptId = GenerateSequenceUtil.generateSequenceNo();
					basRegOpt.setRegOptId(regOptId);
					basRegOptDao.insert(basRegOpt);
					logger.info("从HIS视图v_手术申请信息导入" + basRegOpt.getName()
							+ ",的信息！！");

					Controller controller = new Controller();
					controller.setRegOptId(basRegOpt.getRegOptId());
					controller.setCostsettlementState("0");
					
					//如果是急诊手术直接到术前，如果流程不需要批准手术 则直接将手术状态变为未排班，并且创建手术文书
		            if( 1 == basRegOpt.getEmergency().intValue())
		            {
		            	controller.setState(OperationState.PREOPERATIVE);
						creatDocument(basRegOpt);
		            }
		            else if (0 == basRegOpt.getEmergency().intValue() && "2".equals(CustomConfigureUtil.isRatify()))
		            {
		                controller.setState(OperationState.NO_SCHEDULING);
		                creatDocument(basRegOpt);
		            }
		            else
		            {
		                controller.setState(OperationState.NOT_REVIEWED);
		            }
					controllerDao.update(controller);
				}

			}
	        
	        //把作废的手术取消掉
	        String sql2 = "select * from v_手术申请信息  where OPERDATE >= TO_CHAR(sysdate,'yyyy-mm-dd') and operstat = '作废' ";
            pstmt2 = (PreparedStatement)conn2.prepareStatement(sql2);
	        logger.info("synHisOperList 取消手术=============sql2 =  "+sql2+"===============");
	        rs2 = pstmt2.executeQuery();
	        while (rs2.next()) 
	        {
            	String qxPreengagementnumber = rs2.getString("reservenumber");
            	BasRegOpt regOpt = basRegOptDao.searchRegOptByReservenumber(qxPreengagementnumber, beid);
            	if(null != regOpt)
            	{
            		regOpt.setReasons("HIS作废了这个手术");
            		basRegOptDao.updateByPrimaryKeySelective(regOpt);
            		Controller controller = controllerDao.getControllerById(regOpt.getRegOptId());
            		//未审核、未排班、术前 NOT_REVIEWED、NO_SCHEDULING、PREOPERATIVE允许取消手术
            		if (OperationState.NOT_REVIEWED.equals(controller.getState()) || OperationState.NO_SCHEDULING.equals(controller.getState()) 
            				|| OperationState.PREOPERATIVE.equals(controller.getState())) 
            		{
            			controllerDao.checkOperation(regOpt.getRegOptId(), OperationState.CANCEL, controller.getState());
            		}
            	}
	        }

	        logger.info("synHisOperList=============while end===============");
	    } catch (Exception e) {
	    	logger.info("synHisOperList Exception ====="+Exceptions.getStackTraceAsString(e));
	        e.printStackTrace();
	    }
	    finally
        {
            try
            {
                close(conn, pstmt, rs);
                close(conn2, pstmt2, rs2);
            }
            catch (SQLException e)
            {
                logger.error("synHisOperList close conn --------------"+Exceptions.getStackTraceAsString(e));
            }
        }
	    logger.info("------------------end synHisOperList-------------------------");
	}
	
    private void checkEmergency(BasRegOpt regOpt)
	{
    	String crtime = DateUtils.strToStr(regOpt.getCreateTime(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
    	String operTime = regOpt.getOperaDate();
    	String todayTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
    	if(todayTime.equals(crtime) && todayTime.equals(operTime))
    	{
    		regOpt.setEmergency(1);
    	}else
    	{
    		regOpt.setEmergency(0);
    	}
	}

	/** 
     * 创建文书
     * <功能详细描述>
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void creatDocument(BasRegOpt regOpt)
    {
        //术前访视单
        DocPreVisit preVisit = new DocPreVisit();
        preVisit.setPreVisitId(GenerateSequenceUtil.generateSequenceNo());
        preVisit.setRegOptId(regOpt.getRegOptId());
        preVisit.setProcessState("NO_END");
        DocPreVisitDao docPreVisitDao = SpringContextHolder.getBean(DocPreVisitDao.class);
        docPreVisitDao.insert(preVisit);
        
        //创建麻醉同意书
        DocAccede accede = new DocAccede();
        accede.setAccedeId(GenerateSequenceUtil.generateSequenceNo());
        accede.setRegOptId(regOpt.getRegOptId());
        accede.setFlag("1");
        accede.setProcessState("NO_END");
        DocAccedeDao docAccedeDao = SpringContextHolder.getBean(DocAccedeDao.class);
        docAccedeDao.insert(accede);
        
        //手术麻醉使用药品知情同意书
        DocAnaesMedicineAccede anaesMedicineAccede = new DocAnaesMedicineAccede();
        anaesMedicineAccede.setRegOptId(regOpt.getRegOptId());
        anaesMedicineAccede.setProcessState("NO_END");
        anaesMedicineAccede.setId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesMedicineAccedeDao docAnaesMedicineAccedeDao = SpringContextHolder.getBean(DocAnaesMedicineAccedeDao.class);
        docAnaesMedicineAccedeDao.insert(anaesMedicineAccede);
        
        
        // 创建麻醉记录单
        DocAnaesRecord anaesRecord = new DocAnaesRecord();
        anaesRecord.setAnaRecordId(GenerateSequenceUtil.generateSequenceNo());
        anaesRecord.setOther("O2L/min");
        anaesRecord.setProcessState("NO_END");
        anaesRecord.setRegOptId(regOpt.getRegOptId());
        docAnaesRecordDao.insert(anaesRecord);
        
        // 麻醉总结单
        DocAnaesSummary anaesSummary = new DocAnaesSummary();
        anaesSummary.setRegOptId(regOpt.getRegOptId());
        anaesSummary.setProcessState("NO_END");
        anaesSummary.setAnaSumId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryDao docAnaesSummaryDao = SpringContextHolder.getBean(DocAnaesSummaryDao.class);
        docAnaesSummaryDao.insert(anaesSummary);
        
        // 椎管内麻醉
        DocAnaesSummaryAppendixCanal anaesSummaryAppendixCanal = new DocAnaesSummaryAppendixCanal();
        anaesSummaryAppendixCanal.setAnaSumId(anaesSummary.getAnaSumId());
        anaesSummaryAppendixCanal.setAnaSumAppCanId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryAppendixCanalDao docAnaesSummaryAppendixCanalDao = SpringContextHolder.getBean(DocAnaesSummaryAppendixCanalDao.class);
        docAnaesSummaryAppendixCanalDao.insert(anaesSummaryAppendixCanal);
        
        // 全麻
        DocAnaesSummaryAppendixGen anaesSummaryAppendixGen = new DocAnaesSummaryAppendixGen();
        anaesSummaryAppendixGen.setAnaSumId(anaesSummary.getAnaSumId());
        anaesSummaryAppendixGen.setAnaSumAppGenId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryAppendixGenDao docAnaesSummaryAppendixGenDao = SpringContextHolder.getBean(DocAnaesSummaryAppendixGenDao.class);
        docAnaesSummaryAppendixGenDao.insert(anaesSummaryAppendixGen);
        // 术后访视
        DocAnaesSummaryAppendixVisit anaesSummaryAppendixVisit = new DocAnaesSummaryAppendixVisit();
        anaesSummaryAppendixVisit.setAnaSumId(anaesSummary.getAnaSumId());
        anaesSummaryAppendixVisit.setAnesSumVisId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryAppendixVisitDao docAnaesSummaryAppendixVisitDao = SpringContextHolder.getBean(DocAnaesSummaryAppendixVisitDao.class);
        docAnaesSummaryAppendixVisitDao.insert(anaesSummaryAppendixVisit);
        // 椎管内穿刺
        DocSpinalCanalPuncture spinalCanalPuncture = new DocSpinalCanalPuncture();
        spinalCanalPuncture.setAnaSumId(anaesSummary.getAnaSumId());
        spinalCanalPuncture.setSpinalCanalId(GenerateSequenceUtil.generateSequenceNo());
        DocSpinalCanalPunctureDao docSpinalCanalPunctureDao = SpringContextHolder.getBean(DocSpinalCanalPunctureDao.class);
        docSpinalCanalPunctureDao.insert(spinalCanalPuncture);
        // 神经阻滞
        DocNerveBlock nerveBlock = new DocNerveBlock();
        nerveBlock.setAnaSumId(anaesSummary.getAnaSumId());
        nerveBlock.setNerveBlockId(GenerateSequenceUtil.generateSequenceNo());
        DocNerveBlockDao docNerveBlockDao = SpringContextHolder.getBean(DocNerveBlockDao.class);
        docNerveBlockDao.insert(nerveBlock);
        // 全身麻醉
        DocGeneralAnaes generalAnaes = new DocGeneralAnaes();
        generalAnaes.setAnaSumId(anaesSummary.getAnaSumId());
        generalAnaes.setGeneralAnaesId(GenerateSequenceUtil.generateSequenceNo());
        DocGeneralAnaesDao docGeneralAnaesDao = SpringContextHolder.getBean(DocGeneralAnaesDao.class);
        docGeneralAnaesDao.insert(generalAnaes);
        // 并发症
        DocAnaesSummaryAllergicReaction anaesSummaryAllergicReaction = new DocAnaesSummaryAllergicReaction();
        anaesSummaryAllergicReaction.setAnaSumId(anaesSummary.getAnaSumId());
        anaesSummaryAllergicReaction.setAnaSumAllReaId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryAllergicReactionDao docAnaesSummaryAllergicReactionDao = SpringContextHolder.getBean(DocAnaesSummaryAllergicReactionDao.class);
        docAnaesSummaryAllergicReactionDao.insert(anaesSummaryAllergicReaction);
        // 中心静脉穿刺
        DocAnaesSummaryVenipuncture anaesSummaryVenipuncture = new DocAnaesSummaryVenipuncture();
        anaesSummaryVenipuncture.setAnaSumId(anaesSummary.getAnaSumId());
        anaesSummaryVenipuncture.setAnesSumVenId(GenerateSequenceUtil.generateSequenceNo());
        DocAnaesSummaryVenipunctureDao docAnaesSummaryVenipunctureDao = SpringContextHolder.getBean(DocAnaesSummaryVenipunctureDao.class);
        docAnaesSummaryVenipunctureDao.insert(anaesSummaryVenipuncture);
        
        // 创建手术护理记录文书
        DocOptCareRecord optCareRecord = new DocOptCareRecord();
        optCareRecord.setRegOptId(regOpt.getRegOptId());
        optCareRecord.setId(GenerateSequenceUtil.generateSequenceNo());
        optCareRecord.setProcessState("NO_END");
        DocOptCareRecordDao docOptCareRecordDao = SpringContextHolder.getBean(DocOptCareRecordDao.class);
        docOptCareRecordDao.insert(optCareRecord);
        
        // 创建手术清点记录
        DocOptNurse optNurse = new DocOptNurse();
        optNurse.setRegOptId(regOpt.getRegOptId());
        optNurse.setOptNurseId(GenerateSequenceUtil.generateSequenceNo());
        optNurse.setProcessState("NO_END");
        DocOptNurseDao docOptNurseDao = SpringContextHolder.getBean(DocOptNurseDao.class);
        docOptNurseDao.insert(optNurse);
        
        // 术后随访记录单
        DocPostFollowRecord postFollowRecord = new DocPostFollowRecord();
        postFollowRecord.setRegOptId(regOpt.getRegOptId());
        postFollowRecord.setProcessState("NO_END");
        postFollowRecord.setPostFollowId(GenerateSequenceUtil.generateSequenceNo());
        DocPostFollowRecordDao docPostFollowRecordDao = SpringContextHolder.getBean(DocPostFollowRecordDao.class);
        docPostFollowRecordDao.insert(postFollowRecord);
        
        
        // 创建手术核查单
        DocSafeCheck safeCheck = new DocSafeCheck();
        safeCheck.setRegOptId(regOpt.getRegOptId());
        safeCheck.setProcessState("NO_END");
        safeCheck.setSafCheckId(GenerateSequenceUtil.generateSequenceNo());
        DocSafeCheckDao docSafeCheckDao = SpringContextHolder.getBean(DocSafeCheckDao.class);
        docSafeCheckDao.insert(safeCheck);
        DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = new DocAnaesBeforeSafeCheck();
        anaesBeforeSafeCheck.setRegOptId(regOpt.getRegOptId());
        anaesBeforeSafeCheck.setAnesBeforeId(GenerateSequenceUtil.generateSequenceNo());
        anaesBeforeSafeCheck.setProcessState("NO_END");
        DocAnaesBeforeSafeCheckDao docAnaesBeforeSafeCheckDao = SpringContextHolder.getBean(DocAnaesBeforeSafeCheckDao.class);
        docAnaesBeforeSafeCheckDao.insert(anaesBeforeSafeCheck);
        DocOperBeforeSafeCheck operBeforeSafeCheck = new DocOperBeforeSafeCheck();
        operBeforeSafeCheck.setRegOptId(regOpt.getRegOptId());
        operBeforeSafeCheck.setOperBeforeId(GenerateSequenceUtil.generateSequenceNo());
        operBeforeSafeCheck.setProcessState("NO_END");
        DocOperBeforeSafeCheckDao docOperBeforeSafeCheckDao = SpringContextHolder.getBean(DocOperBeforeSafeCheckDao.class);
        docOperBeforeSafeCheckDao.insert(operBeforeSafeCheck);
        DocExitOperSafeCheck exitOperSafeCheck = new DocExitOperSafeCheck();
        exitOperSafeCheck.setRegOptId(regOpt.getRegOptId());
        exitOperSafeCheck.setProcessState("NO_END");
        exitOperSafeCheck.setExitOperId(GenerateSequenceUtil.generateSequenceNo());
        DocExitOperSafeCheckDao docExitOperSafeCheckDao = SpringContextHolder.getBean(DocExitOperSafeCheckDao.class);
        docExitOperSafeCheckDao.insert(exitOperSafeCheck);
        
        //术后（术前）镇痛知情同意书
    	DocAnalgesicInformedConsent docAnalgesicInformedConsent = new DocAnalgesicInformedConsent();
    	docAnalgesicInformedConsent.setRegOptId(regOpt.getRegOptId());
    	docAnalgesicInformedConsent.setProcessState("NO_END");
    	docAnalgesicInformedConsent.setAnalgesicId(GenerateSequenceUtil.generateSequenceNo());
    	docAnalgesicInformedConsentDao.insert(docAnalgesicInformedConsent);
    	
        // 在审核的时候 生成排程信息记录
        int dispatchCount = basDispatchDao.searchDistchByRegOptId(regOpt.getRegOptId());
        if (dispatchCount < 1)
        {
            BasDispatch dispatch = new BasDispatch();
            dispatch.setRegOptId(regOpt.getRegOptId());
            dispatch.setBeid(basBusEntityDao.getBeid());
            basDispatchDao.insert(dispatch);
        }
    }

}
