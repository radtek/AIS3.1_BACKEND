package com.digihealth.anesthesia.interfacedata.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasChargeItem;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasInstrument;
import com.digihealth.anesthesia.basedata.po.BasMedicine;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasPrice;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.BasRegion;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.ConnectionManager;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.dao.DocAnaesRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectRecordDao;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.dao.EvtOptRealOperDao;
import com.digihealth.anesthesia.sysMng.dao.BasUserDao;

@Service
public class OperBaseDataServiceSYBX extends BaseService{

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private BasDeptDao basDeptDao = SpringContextHolder.getBean(BasDeptDao.class);
    private BasOperdefDao basOperdefDao = SpringContextHolder.getBean(BasOperdefDao.class);
    private BasDiagnosedefDao basDiagnosedefDao = SpringContextHolder.getBean(BasDiagnosedefDao.class);
    private BasOperationPeopleDao basOperationPeopleDao = SpringContextHolder.getBean(BasOperationPeopleDao.class);
    private BasAnaesMethodDao basAnaesMethodDao = SpringContextHolder.getBean(BasAnaesMethodDao.class);
    private BasInstrumentDao basInstrumentDao = SpringContextHolder.getBean(BasInstrumentDao.class);
    private BasMedicineDao basMedicineDao = SpringContextHolder.getBean(BasMedicineDao.class);
    private BasPriceDao basPriceDao = SpringContextHolder.getBean(BasPriceDao.class);
    private BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
    private BasUserDao basUserDao = SpringContextHolder.getBean(BasUserDao.class);
    private DocAnaesRecordDao docAnaesRecordDao = SpringContextHolder.getBean(DocAnaesRecordDao.class);
    private BasDispatchDao basDispatchDao = SpringContextHolder.getBean(BasDispatchDao.class);
	/**
	 * 获取外部系统视图VIEW_OPERATION_NAME，并插入当前数据库
	 * 手术名称数据同步  his不提供code值 
	 */
	@Transactional
	public void synHisOperNameList(){
		logger.info("-------start synHisOperNameList-----------");
		
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            while (rs.next())
            {
                if(!StringUtils.isEmpty(rs.getString(1)))
                {
                    //手术名称为空，则不需插入到数据库中
                    String code = rs.getString("code");
                    logger.debug("=================code:=====" + code + "==================");
                    String name = rs.getString("name");
                    Integer enable = rs.getInt("enable");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    String beid = getBeid();
                    List<BasOperdef> operdefList = basOperdefDao.selectOperDefsByCode(code, beid);
                    if (null != operdefList && operdefList.size() > 0)
                    {
                        for (BasOperdef operDefFormBean : operdefList)
                        {
                            BasOperdef operdef = basOperdefDao.queryOperdefById(operDefFormBean.getOperdefId());
                            if (isNeedUpdate(operdef.getName(), name) || operdef.getEnable() != enable)
                            {
                                operdef.setName(name);
                                operdef.setEnable(enable);
                                operdef.setCode(code);
                                operdef.setPinYin(pinyin);
                                operdef.setBeid(beid);
                                basOperdefDao.update(operdef);
                            }
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
                        odef.setBeid(beid);
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
		
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Doctors";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            System.out.println("============================");
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    logger.debug("=================code:=====" + code + "==================");
                    String name = rs.getString("name");
                    Integer region = rs.getInt("region");
                    Integer enable = rs.getInt("enable");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    
                    String beid = getBeid();
                    List<BasOperationPeople> operationPeoples = basOperationPeopleDao.selectPeoplesByCode(code, beid);
                    if (null != operationPeoples && operationPeoples.size() > 0)
                    {
                        for (BasOperationPeople opd : operationPeoples)
                        {
                           if (isNeedUpdate(opd.getName(), name) || opd.getEnable() != enable || opd.getRegion() != region)
                            {
                               opd.setCode(code);
                               opd.setName(name);
                               opd.setEnable(enable);
                               opd.setRegion(region);
                               opd.setPinYin(pinyin);
                               opd.setBeid(beid);
                               basOperationPeopleDao.update(opd);
                            }
                        }
                    }
                    else
                    {
                        BasOperationPeople opd = new BasOperationPeople();
                        opd.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                        opd.setCode(code);
                        opd.setName(name);
                        opd.setEnable(enable);
                        opd.setRegion(region);
                        opd.setPinYin(pinyin);
                        opd.setBeid(beid);
                        basOperationPeopleDao.insert(opd);
                    }
                }
            }
            System.out.println("============================");
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
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Anesthesia";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
        {
		    conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            //basAnaesMethodDao.updateEnable();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String name = rs.getString("name");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    Integer isValid = rs.getInt("is_valid");
                    String code = rs.getString("code");
                    logger.debug("=================code:=====" + code + "==================");
                    String beid = getBeid();
                	BasAnaesMethod basAnaesMethod = new BasAnaesMethod();
                	basAnaesMethod.setCode(code);
                	basAnaesMethod.setBeid(beid);
                    List<BasAnaesMethod> anaesMethods = basAnaesMethodDao.selectEntityList(basAnaesMethod);
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    if (null != anaesMethods && anaesMethods.size() > 0)
                    {
                        for (BasAnaesMethod anaesMethod : anaesMethods)
                        {
                            if (isNeedUpdate(anaesMethod.getName(), name) || anaesMethod.getIsValid() != isValid)
                            {
                                anaesMethod.setName(name);
                                anaesMethod.setIsValid(isValid);
                                anaesMethod.setPinYin(pinyin);
                                anaesMethod.setCode(code);
                                anaesMethod.setBeid(beid);
                                basAnaesMethodDao.update(anaesMethod);
                            }
                        }
                    }
                    else
                    {
                        BasAnaesMethod anaesMethod = new BasAnaesMethod();
                        anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                        anaesMethod.setCode(code);
                        anaesMethod.setName(name);
                        anaesMethod.setIsValid(isValid);
                        anaesMethod.setPinYin(pinyin);
                        anaesMethod.setBeid(beid);
                        if ("4".equals(code))
                        {
                            anaesMethod.setIsLocalAnaes(1);
                        }
                        else
                        {
                            anaesMethod.setIsLocalAnaes(0);
                        }
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
		
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Diagnose";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            //basDiagnosedefDao.updateEnable();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    Integer enable = rs.getInt("enable");
                    
                    String beid = getBeid();
                    List<BasDiagnosedef> diagnosedefs = basDiagnosedefDao.selectDiagnosedefsByCode(code, beid);
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    
                    if (null != diagnosedefs && diagnosedefs.size() > 0)
                    {
                        for (BasDiagnosedef diagnosedef : diagnosedefs)
                        {
                            if (isNeedUpdate(diagnosedef.getName(), name) || diagnosedef.getEnable() != enable)
                            {
                                diagnosedef.setName(name);
                                diagnosedef.setEnable(enable);
                                diagnosedef.setPinYin(pinyin);
                                diagnosedef.setCode(code);
                                basDiagnosedefDao.update(diagnosedef);
                            }
                        }
                    }
                    else
                    {
                        BasDiagnosedef diagnosedef = new BasDiagnosedef();
                        diagnosedef.setDiagDefId(GenerateSequenceUtil.generateSequenceNo());
                        diagnosedef.setCode(code);
                        diagnosedef.setName(name);
                        diagnosedef.setEnable(enable);
                        diagnosedef.setPinYin(pinyin);
                        diagnosedef.setBeid(beid);
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
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Deptartment";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String beid = getBeid();
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            //basDeptDao.updateEnable();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String name = rs.getString("name");
                    Integer enable = rs.getInt("enable");
                    String id = rs.getString("deptid");
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    BasDept dt = basDeptDao.searchDeptById(id,beid);
                    
                    if (null != dt && (isNeedUpdate(dt.getName(), name) || dt.getEnable() != enable))
                    {
                        dt.setName(name);
                        dt.setEnable(enable);
                        basDeptDao.update(dt);
                    }

                    if(null == dt)
                    {
                        BasDept dept = new BasDept();
                        dept.setDeptId(id);
                        dept.setName(name);
                        dept.setEnable(enable);
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
	 * 获取病区列表
	 * 视图名称：VIEW_REGION
	 */
	@Transactional
	public void synHisRegionList(){
		logger.info("-------start synHisRegionList-----------");
		
		Connection conn = null;
		String sql = "select * from SZHS_Operation_Ward";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String beid = getBeid();
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String name = rs.getString("name");
                    String regionId = rs.getString("regionId");
                    Integer enable = rs.getInt("enable");
                    BasRegion re = basRegionDao.searchRegionById(regionId,beid);
                    if (null == name)
                    {
                        continue;
                    }
                    
                    //根据id查询科室信息，不为空则更新，为空则新增
                    if (null != re && (isNeedUpdate(re.getName(), name) || re.getEnable() != enable))
                    {
                        re.setName(name);
                        re.setEnable(enable);
                        basRegionDao.update(re);
                    }
                    
                    if (null == re)
                    {
                        BasRegion region = new BasRegion();
                        region.setRegionId(regionId);
                        region.setName(name);
                        region.setEnable(enable);
                        region.setBeid(beid);
                        basRegionDao.insert(region);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("synHisRegionList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisRegionList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
		logger.info("-------end synHisRegionList-----------");
	}
	
	/**
	 * 检验项目(只需要血气检查的)
	 * 视图名称：VIEW_INSPECT_ITEM 接口暂不做
	 */
	/*@Transactional
	public void synHisCheckItemList(){
		logger.info("-------start synHisInspectItemList-----------");
		Connection conn = ConnectionManager.getOracleConnection();
		String sql = "select * from portal_lis.BloodGasAnalysis";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
        {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String unit = rs.getString("unit");
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    String enable = rs.getString("enable");
                    List<CheckItem> checkItems = checkItemDao.selectByCode(code);
                    if (null != checkItems && checkItems.size() > 0)
                    {
                        for (CheckItem checkItem : checkItems)
                        {
                            if (!checkItem.getUnit().equals(unit) || !checkItem.getEnable().equals(enable)
                                || !checkItem.getName().equals(name))
                            {
                                CheckItem ck = new CheckItem();
                                ck.setCheItemId(checkItem.getCheItemId());
                                ck.setEnable(enable);
                                ck.setName(name);
                                ck.setUnit(unit);
                                checkItemDao.update(ck);
                            }
                            
                        }
                    }
                    else
                    {
                        CheckItem ck = new CheckItem();
                        ck.setEnable(enable);
                        ck.setName(name);
                        ck.setUnit(unit);
                        ck.setCode(code);
                        checkItemDao.insert(ck);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
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
                e.printStackTrace();
            }
        }
		logger.info("-------end synHisInspectItemList-----------");
	}*/
	
	/**
	 * 获取器械列表  
	 * 视图名称：inventory_djhs 用友系统提供
	 */
	@Transactional
	public void synHisInstrumentList(){
		logger.info("-------start synHisInstrumentList-----------");
		Connection conn = null;
        String sql = "select * from inventory_djhs";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXYYConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String pinyin = rs.getString("pingyin");
                    Integer enable = rs.getInt("enable");
                    String beid = getBeid();
                    List<BasInstrument> instruments = basInstrumentDao.selectInstrumentsByCode(code, beid);
                    if (null != instruments && instruments.size() > 0)
                    {
                        for (BasInstrument instrument : instruments)
                        {
                            if (isNeedUpdate(instrument.getType(), type) || instrument.getEnable() != enable || isNeedUpdate(instrument.getName(), name))
                            {
                                instrument.setCode(code);
                                instrument.setEnable(enable);
                                instrument.setName(name);
                                instrument.setType(type);
                                instrument.setPinYin(StringUtils.isBlank(pinyin) ? PingYinUtil.getFirstSpell(name) : pinyin); 
                                basInstrumentDao.update(instrument);
                            }
                            
                        }
                    }
                    else
                    {
                        BasInstrument instrument = new BasInstrument();
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
	 * 视图名称：VIEW_DRUG_STORE 批次没有
	 */
	@Transactional
	public void synHisDrugStoreList(){
		logger.info("-------start synHisDrugStoreList-----------");
		
		Connection conn = null;
		String sql = "select * from SZHS_Operation_DrugStore";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            //basMedicineDao.updateEnable();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String type = rs.getString("type");
                    Integer enable = rs.getInt("enable");
                    String name = rs.getString("name");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    String briefName = rs.getString("brief_name");
                    Float packageDosageAmout = rs.getFloat("package_dosage_amount");
                    String dosageUnit = rs.getString("dosage_unit");
                    String spec = rs.getString("spec");
                    String code = rs.getString("code");
                    String beid = getBeid();
                    List<BasMedicine> medicines = basMedicineDao.selectMedicinesByCode(code, beid);
                    if (null != medicines && medicines.size() > 0)
                    {
                        for (BasMedicine medicine : medicines)
                        {
                            if (isNeedUpdate(medicine.getBriefName(), briefName) || isNeedUpdate(medicine.getDosageUnit(), dosageUnit) || medicine.getEnable() != enable 
                                || isNeedUpdate(medicine.getName(), name) || !medicine.getPackageDosageAmount().equals(packageDosageAmout) || isNeedUpdate(medicine.getSpec(), spec) 
                                || isNeedUpdate(medicine.getType(), type))
                            {
                                medicine.setCode(code);
                                medicine.setType(type);
                                medicine.setEnable(enable);
                                medicine.setPinYin(pinyin);
                                medicine.setName(name);
                                medicine.setBriefName(briefName);
                                medicine.setPackageDosageAmount(packageDosageAmout);
                                medicine.setDosageUnit(dosageUnit);
                                medicine.setSpec(spec);
                                basMedicineDao.update(medicine);
                            }
                            
                        }
                    }
                    else
                    {
                        BasMedicine medicine = new BasMedicine();
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
                        medicine.setBeid(beid);
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
		String sql = "select * from SZHS_Operation_MedicinesPrice";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
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
                    String minPackageUnit = rs.getString("min_package_unit");
                    Float priceMinPackage = rs.getFloat("price_min_package");
                    Integer enable = rs.getInt("enable");
                    String beid = getBeid();
                    BaseInfoQuery baseQuery = new BaseInfoQuery();
                    baseQuery.setCode(code);
                    baseQuery.setBeid(beid);
                    List<BasPrice> prices = basPriceDao.searchPriceList(baseQuery);
                    if (null != prices && prices.size() > 0)
                    {
                        for (BasPrice price : prices)
                        {
                            if (isNeedUpdate(price.getSpec(), spec) || isNeedUpdate(price.getFirm(), firm) || isNeedUpdate(price.getBatch(), batch) 
                                || isNeedUpdate(price.getMinPackageUnit(), minPackageUnit) || !price.getPriceMinPackage().equals(priceMinPackage) 
                                || price.getEnable() != enable)
                            {
                                price.setSpec(spec);
                                price.setFirm(firm);
                                price.setBatch(batch);
                                price.setMinPackageUnit(minPackageUnit);
                                price.setPriceMinPackage(priceMinPackage);
                                price.setEnable(enable);
                                price.setCode(code);
                                basPriceDao.update(price);
                            }
                        }
                    }
                    else
                    {
                        BasPrice price = new BasPrice();
                        price.setPriceId(GenerateSequenceUtil.generateSequenceNo());
                        price.setCode(code);
                        price.setSpec(spec);
                        price.setFirm(firm);
                        price.setBatch(batch);
                        price.setMinPackageUnit(minPackageUnit);
                        price.setPriceMinPackage(priceMinPackage);
                        price.setEnable(enable);
                        price.setBeid(beid);
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
		String sql = "select * from SZHS_Operation_CureItem";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String code = rs.getString("charge_item_code");
                    String beid = getBeid();
                    List<BasChargeItem> chargeItems = basChargeItemDao.selectByCode(code, beid);
                    String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
                    Pattern p = Pattern.compile(regEx);  
                    Matcher m = p.matcher(rs.getString("charge_item_name"));
                    

                    String spec = rs.getString("spec");//StringUtils.StringFilter(rs.getString("spec"));

                    
                    String chargeItemName = rs.getString("charge_item_name");
                    if(StringUtils.isNotBlank(chargeItemName)){
                    	chargeItemName = chargeItemName.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
                    }
                    logger.info("----------------------------"+chargeItemName+"-----------------------------");

                    
                    //String spec = rs.getString("spec");

                    //logger.info("----------------------------"+rs.getString("pinyin").toString().trim()+"-----------------------------");
                    
                    String pinyin = rs.getString("pinyin");
                    if(StringUtils.isNotBlank(pinyin)){
                    	pinyin = pinyin.replaceAll("[^a-zA-Z]", "");
                    }

                    String unit = rs.getString("unit");
                    float basicUnitAmout = rs.getFloat("basic_unit_amount");
                    float price = rs.getFloat("price");
                    float basicUnitPrice = rs.getFloat("basic_unit_price");
                    String type = rs.getString("type");
                    String chargeType = rs.getString("charge_type");
                    Integer enable = rs.getInt("enable");
                    if (null != chargeItems && chargeItems.size() > 0)
                    {
                        for (BasChargeItem chargeItem : chargeItems)
                        {
                            if (isNeedUpdate(chargeItem.getChargeItemName(), chargeItemName)
                                || isNeedUpdate(chargeItem.getSpec(), spec) || isNeedUpdate(chargeItem.getUnit(), unit)
                                || chargeItem.getBasicUnitAmount() != basicUnitAmout || chargeItem.getPrice() != price
                                || chargeItem.getBasicUnitPrice() != basicUnitPrice
                                || isNeedUpdate(chargeItem.getType(), type) || isNeedUpdate(chargeItem.getChargeType(), chargeType)
                                || chargeItem.getEnable() != enable)
                            {
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
                                chargeItem.setChargeItemCode(code);
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
                        chargeItem.setBeid(beid);
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
	
	/**
	 * 获取用户列表 （麻醉医生或护士）
	 * 视图：VIEW_SYS_USER
	 */
	/*@Transactional
	public void synHisSysUserList(){
		logger.info("-------start synHisSysUserList-----------");
		
		Connection conn = ConnectionManager.getSYBXHisConnection();
		String sql = "select * from SZHS_Operation_Anesthesia_DoctorNurse";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try
        {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (null == rs)
            {
                return;
            }
            while (rs.next())
            {
                if (!StringUtils.isEmpty(rs.getString(1)))
                {
                    String id = rs.getString("id");
                    User user = userDao.get(id);
                    
                    String name = rs.getString("name");
                    String mobile = rs.getString("mobile");
                    String userType = rs.getString("user_type");
                    String pinyin = PingYinUtil.getFirstSpell(name);
                    String delFlag = rs.getString("del_flag");
                    if (null != user
                        && (!user.getName().equals(name) || !user.getMobile().equals(mobile)
                            || !user.getUserType().equals(userType) || !user.getDelFlag().equals(delFlag)))
                    {
                        user.setName(name);
                        user.setMobile(mobile);
                        user.setUserType(userType);
                        user.setPinyin(pinyin);
                        user.setDelFlag(delFlag);
                        userDao.update(user);
                    }
                    else
                    {
                        user = new User();
                        user.setName(name);
                        user.setMobile(mobile);
                        user.setUserType(userType);
                        user.setPinyin(pinyin);
                        user.setDelFlag(delFlag);
                        userDao.insert(user);
                    }
                }
            }
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
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
                e.printStackTrace();
            }
        }
        
		logger.info("-------end synHisSysUserList-----------");
	}*/
	
	@Transactional
	public void synHisOperList(){
        logger.info("---------------------begin synHisOperList------------------------");
        Connection conn = null;
        String sql = "select * from SZHS_Operation_Digihealth where operDate >='" + DateUtils.DateToString(new Date()) + "'";
        logger.info("=================sql=================" + sql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getSYBXHisConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql); 
            rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            logger.info("synHisOperList=============col"+col+"===============");
            while (rs.next()) {
                String s1 = rs.getString("reservenumber");
                String s2 = rs.getString("operDate");
                String time = DateUtils.getParseNYRTime(rs.getString("operDate"));
                String beid = getBeid();
                int result = basRegOptDao.selectHisToRegOpt(rs.getString("reservenumber"), DateUtils.getParseNYRTime(rs.getString("operDate")), beid);
                if(result<1){
//                    for (int i = 1; i <= col; i++) {
//                        logger.info("rs.getString(i) " + "\t");
//                    }
                    String regOptId = GenerateSequenceUtil.generateSequenceNo();
                    BasRegOpt regOpt = new BasRegOpt();
                    regOpt.setRegOptId(regOptId);
                    regOpt.setPreengagementnumber(rs.getString("reservenumber"));
                    regOpt.setName(rs.getString("name"));
                    int[] age = DateUtils.getNaturalAge(rs.getString("birthday"), DateUtils.getDate());
                    if (0 != age[0])
                    {
                        regOpt.setAge(age[0]);
                    }
                    else if(0 != age[1])
                    {
                        regOpt.setAgeMon(age[1]);
                    }
                    else
                    {
                        regOpt.setAgeDay(age[2]);
                    }
                    regOpt.setBirthday(rs.getString("birthday"));
                    regOpt.setSex(rs.getString("sex"));
                    regOpt.setMedicalType(rs.getString("medicalType"));
                    regOpt.setIdentityNo(rs.getString("credNumber"));
                    regOpt.setHid(rs.getString("hid"));
                    regOpt.setCid(rs.getString("cid"));
                    if(!StringUtils.isEmpty(rs.getString("regionId"))){
                        regOpt.setRegionId(rs.getString("regionId"));
                    }
                    regOpt.setRegionName(rs.getString("regionName"));
                    if(!StringUtils.isEmpty(rs.getString("deptId"))){
                        regOpt.setDeptId(rs.getString("deptId"));
                    }
                    regOpt.setDeptName(rs.getString("deptName"));
                    regOpt.setBed(rs.getString("bed"));
                    
                    //诊断名称
                    if (StringUtils.isNotEmpty(rs.getString("diagCode")))
                    {
                        String diagDef = "";
                        String diagName = "";
                        String[] diagCode = rs.getString("diagCode").split(",");
                        for (int i = 0; i < diagCode.length; i++)
                        {
                            List<BasDiagnosedef> diagnosedefs = basDiagnosedefDao.selectDiagnosedefsByCode(diagCode[i], beid);
                            if (null != diagnosedefs && diagnosedefs.size() > 0)
                            {
                                diagDef = diagDef + diagnosedefs.get(0).getDiagDefId() + ",";
                                diagName = diagName + diagnosedefs.get(0).getName() + ",";
                            }
                            else if (StringUtils.isNotBlank(rs.getString("diagName")))
                            {
                                String[] diagNames = rs.getString("diagName").split(",");
                                BasDiagnosedef diagnosedef = new BasDiagnosedef();
                                diagnosedef.setDiagDefId(GenerateSequenceUtil.generateSequenceNo());
                                diagnosedef.setCode(diagCode[i]);
                                diagnosedef.setName(diagNames[i]);
                                diagnosedef.setEnable(1);
                                diagnosedef.setPinYin(PingYinUtil.getFirstSpell(diagNames[i]));
                                diagnosedef.setBeid(beid);
                                basDiagnosedefDao.insert(diagnosedef);
                                
                                diagDef = diagDef + diagnosedef.getDiagDefId() + ",";
                                diagName = diagName + diagNames[i] + ",";
                            }
                        }
                        regOpt.setDiagnosisCode(StringUtils.isNotBlank(diagDef) ? diagDef.substring(0, diagDef.length() - 1) : "");
                        regOpt.setDiagnosisName(rs.getString("diagName"));
                    }
                    
                    //手术名称
                    if (StringUtils.isNotEmpty(rs.getString("operCode")))
                    {
                        String operId = "";
                        String operName = "";
                        String[] operCodes = rs.getString("operCode").split(",");
                        for (int i = 0; i < operCodes.length; i++)
                        {
                            List<BasOperdef> opers = basOperdefDao.selectOperDefsByCode(operCodes[i], beid);
                            if (null != opers && opers.size() > 0)
                            {
                                operId = operId + opers.get(0).getOperdefId() + ",";
                                operName = operName + opers.get(0).getName() + ",";
                            }
                            else if (StringUtils.isNotBlank(rs.getString("operName")))
                            {
                                String[] operNames = rs.getString("operName").split(",");
                                BasOperdef operdef = new BasOperdef();
                                operdef.setOperdefId(GenerateSequenceUtil.generateSequenceNo());
                                operdef.setCode(operCodes[i]);
                                operdef.setName(operNames[i]);
                                operdef.setEnable(1);
                                operdef.setPinYin(PingYinUtil.getFirstSpell(operNames[i]));
                                operdef.setBeid(beid);
                                basOperdefDao.insert(operdef);
                                
                                operId = operId + operdef.getOperdefId() + ",";
                                operName = operName + operNames[i] + ",";
                            }
                        }
                        regOpt.setDesignedOptCode(StringUtils.isNotBlank(operId) ? operId.substring(0, operId.length() - 1) : "");
                        regOpt.setDesignedOptName(rs.getString("operName"));
                    }
                    
                    //手术医生
                    if (StringUtils.isNotEmpty(rs.getString("surgeryDoctorId")))
                    {
                        String operatorId = "";
                        String operatorName = "";
                        String[] operatorCodes = rs.getString("surgeryDoctorId").split(",");
                        for (int i = 0; i < operatorCodes.length; i++)
                        {
                            List<BasOperationPeople> operators = basOperationPeopleDao.selectPeoplesByCode(operatorCodes[i], beid);
                            if (null != operators && operators.size() > 0)
                            {
                                operatorId = operatorId + operators.get(0).getOperatorId() + ",";
                                operatorName = operatorName + operators.get(0).getName() + ",";
                            }
                            else if (StringUtils.isNotBlank(rs.getString("surgeryDoctorName")))
                            {
                                String[] operatorNames = rs.getString("surgeryDoctorName").split(",");
                                BasOperationPeople operationPeople = new BasOperationPeople();
                                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());;
                                operationPeople.setCode(operatorCodes[i]);
                                operationPeople.setName(operatorNames[i]);
                                operationPeople.setEnable(1);
                                operationPeople.setPinYin(PingYinUtil.getFirstSpell(operatorNames[i]));
                                operationPeople.setBeid(beid);
                                basOperationPeopleDao.insert(operationPeople);
                                
                                operatorId = operatorId + operationPeople.getOperatorId() + ",";
                                operatorName = operatorName + operatorNames[i] + ",";
                            }
                        }
                        regOpt.setOperatorId(StringUtils.isNotBlank(operatorId) ? operatorId.substring(0, operatorId.length() - 1) : "");
                        regOpt.setOperatorName(rs.getString("surgeryDoctorName"));
                    }
                    
                    //助手医生处理
                    if (StringUtils.isNotEmpty(rs.getString("assistantId")))
                    {
                        String operatorId = "";
                        String operatorName = "";
                        String[] operatorCodes = rs.getString("assistantId").split(",");
                        for (int i = 0; i < operatorCodes.length; i++)
                        {
                            List<BasOperationPeople> operators = basOperationPeopleDao.selectPeoplesByCode(operatorCodes[i], beid);
                            if (null != operators && operators.size() > 0)
                            {
                                operatorId = operatorId + operators.get(0).getOperatorId() + ",";
                                operatorName = operatorName + operators.get(0).getName() + ",";
                            }
                            else if (StringUtils.isNotBlank(rs.getString("assistantName")))
                            {
                                String[] operatorNames = rs.getString("assistantName").split(",");
                                BasOperationPeople operationPeople = new BasOperationPeople();
                                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());;
                                operationPeople.setCode(operatorCodes[i]);
                                operationPeople.setName(operatorNames[i]);
                                operationPeople.setEnable(1);
                                operationPeople.setPinYin(PingYinUtil.getFirstSpell(operatorNames[i]));
                                operationPeople.setBeid(beid);
                                basOperationPeopleDao.insert(operationPeople);
                                
                                operatorId = operatorId + operationPeople.getOperatorId() + ",";
                                operatorName = operatorName + operatorNames[i] + ",";
                            }
                        }
                        regOpt.setAssistantId(StringUtils.isNotBlank(operatorId) ? operatorId.substring(0, operatorId.length() - 1) : "");
                        regOpt.setAssistantName(rs.getString("assistantName"));
                    }
                    
                    if(!StringUtils.isEmpty(rs.getString("weight"))){
                        regOpt.setWeight(Float.parseFloat(rs.getString("weight").trim()));
                    }
                    if(!StringUtils.isEmpty(rs.getString("height"))){
                        regOpt.setHeight(Float.parseFloat(rs.getString("height").trim()));
                    }
                    regOpt.setHbsag(rs.getString("hbsag"));
                    regOpt.setHcv(rs.getString("hcv"));
                    regOpt.setHiv(rs.getString("hiv"));
                    regOpt.setHp(rs.getString("hp"));
                    regOpt.setOperaDate(DateUtils.getParseNYRTime(rs.getString("operDate")));
                    regOpt.setStartTime(rs.getString("operStartTime"));
                    regOpt.setEndTime(rs.getString("operEndTime"));
                    regOpt.setHyperSusceptiBility(rs.getString("dragAllergy"));
                    regOpt.setOptLevel(rs.getString("operLevel"));
                    regOpt.setEmergency(0);
                    //regOpt.setCutLevel(rs.getString("IncisionLevel"));
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
                    
                    
                    regOpt.setOperSource(rs.getInt("OpeSource"));
                    
                    //麻醉方法
                    String designedAnaesMethodName = rs.getString("anaesName");
                    String designedAnaesMethodCode = rs.getString("anaesID");
                   
                    if (StringUtils.isNotBlank(designedAnaesMethodName) && StringUtils.isNotBlank(designedAnaesMethodCode))
                    {
                    	BasAnaesMethod basAnaesMethod = new BasAnaesMethod();
                    	basAnaesMethod.setCode(designedAnaesMethodCode);
                    	basAnaesMethod.setBeid(beid);
                        List<BasAnaesMethod> anaesMethods = basAnaesMethodDao.selectEntityList(basAnaesMethod);
                        if (null != anaesMethods && anaesMethods.size() > 0)
                        {
                            regOpt.setDesignedAnaesMethodCode(anaesMethods.get(0).getAnaMedId()+"");
                            regOpt.setDesignedAnaesMethodName(anaesMethods.get(0).getName());
                            regOpt.setIsLocalAnaes(anaesMethods.get(0).getIsLocalAnaes());
                        }
                        else
                        {
                            
                            BasAnaesMethod anaesMethod = new BasAnaesMethod();
                            anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                            anaesMethod.setCode(designedAnaesMethodCode);
                            anaesMethod.setName(designedAnaesMethodName);
                            anaesMethod.setIsValid(1);
                            /*if ("4".equals(designedAnaesMethodCode))
                            {
                                anaesMethod.setIsLocalAnaes(1);
                            }
                            else
                            {
                            }*/
                            anaesMethod.setIsLocalAnaes(0);
                            anaesMethod.setPinYin(StringUtils.isNotBlank(designedAnaesMethodName) ? PingYinUtil.getFirstSpell(designedAnaesMethodName) : null);
                            anaesMethod.setBeid(beid);
                            basAnaesMethodDao.insert(anaesMethod);
                            
                            regOpt.setDesignedAnaesMethodCode(anaesMethod.getAnaMedId());
                            regOpt.setDesignedAnaesMethodName(designedAnaesMethodName);
                            regOpt.setIsLocalAnaes(anaesMethod.getIsLocalAnaes());
                        }
                    }
                    
                    /*if("4".equals(designedAnaesMethodCode)){
                        regOpt.setIsLocalAnaes(1); //1是局麻，0是全麻
                    }else{
                        regOpt.setIsLocalAnaes(0);
                    }*/
                    
                    
                    regOpt.setCreateTime(DateUtils.formatDateTime(new Date()));
                    regOpt.setCreateUser(basUserDao.selectUserNameByHisId(rs.getString("createUser"), beid));
                    // 增加备注
                    regOpt.setRemark(rs.getString("instructions"));//从his中获取
//                    //手术序号
                    regOpt.setOperateNumber(rs.getInt("Operate_Number"));//从his中获取
                    
                    regOpt.setState(OperationState.NOT_REVIEWED);
                    regOpt.setBeid(beid);
                    int resultInsert = basRegOptDao.insert(regOpt);
//                    operatelogService.saveOperatelog(regOptId, operatelogService.OPT_TYPE_INFO_SAVE,
//                            operatelogService.OPT_MODULE_INTERFACE, "HIS手术通知单", JsonType.jsonType(regOpt));
                    
                }
            }
            logger.info("synHisOperList=============while end===============");
        } catch (Exception e) {
            logger.error("synHisOperList--------------"+Exceptions.getStackTraceAsString(e));
        }
        finally
        {
            try
            {
                close(conn, pstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("synHisOperList--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
        logger.info("------------------end synHisOperList-------------------------");
    }
	
	/**
     * 手术安排后回写给his
     * @param regOpt
     * @return
     */
    @Transactional
    public void sendScheduleToHis(String regOptId){
        logger.info("begin sendScheduleToHis");
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
        
        String beid = getBeid();
        DispatchFormbean dispatchBean = basDispatchDao.getDispatchOperByRegOptId(regOpt.getRegOptId(), beid);
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
        
        if (null == dispatchBean)
        {
            logger.error("==========没有查询到相关的排程信息===============");
            return;
        }

        Connection conn = null;
        CallableStatement cstmt = null;
        try{
            conn = ConnectionManager.getSYBXHisConnection();
            cstmt = conn.prepareCall("{call dbo.SZHS_Operattion_ArrangerMent("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?)}");
            cstmt.setInt(1, Integer.parseInt(regOpt.getPreengagementnumber()));//his手术单号
            cstmt.setInt(2, 212);//科室id
            cstmt.setString(3, regOpt.getHid());//住院号
            String aprq = regOpt.getOperaDate()+" "+(StringUtils.isNotBlank(dispatchBean.getStartTime()) ? dispatchBean.getStartTime() : "00:00")+":00";
            String ssrq = anaesRecord.getOperStartTime();
            logger.debug("================安排日期：" + aprq);
            logger.debug("================手术日期：" + ssrq);
            cstmt.setTimestamp(4, new java.sql.Timestamp(DateUtils.getParseTime(aprq).getTime()));//手术安排日期
            cstmt.setTimestamp(5, new java.sql.Timestamp(DateUtils.getParseTime(ssrq).getTime()));//手术日期
            String designed = regOpt.getDesignedOptCode();
            if (StringUtils.isNoneBlank(designed))
            {
                String[] designedAry = designed.split(",");
                BasOperdef operdef = basOperdefDao.queryOperdefById(designedAry[0]);
                cstmt.setInt(6, Integer.parseInt(operdef.getCode()));//手术名称id
            }
            else
            {
                cstmt.setInt(6, 0);//手术名称id
            }
            cstmt.setString(7, dispatchBean.getOperRoomName());//手术房间
            cstmt.setString(8, dispatchBean.getPcs()+"");//手术台号
            if (!StringUtils.isEmpty(regOpt.getOperatorId()))
            {
                String[] operator = regOpt.getOperatorId().split(",");
                BasOperationPeople op1 = basOperationPeopleDao.queryOperationPeopleById(operator[0]);
                cstmt.setString(9, null != op1 ? op1.getCode() : "");//手术医师1
                BasOperationPeople op2 = operator.length > 1 ? basOperationPeopleDao.queryOperationPeopleById(operator[1]) : null;
                cstmt.setString(10, null != op2 ? op2.getCode() : "");//手术医师2
                BasOperationPeople op3 = operator.length > 2 ? basOperationPeopleDao.queryOperationPeopleById(operator[2]) : null;
                cstmt.setString(32, null != op3 ? op3.getCode() : "");//手术医师3
            }
            else
            {
                cstmt.setString(9, "");//手术医师1
                cstmt.setString(10, "");//手术医师2
                cstmt.setString(32, "");//手术医师3
            }
            if (!StringUtils.isEmpty(regOpt.getAssistantId()))
            {
                String[] assistant = regOpt.getAssistantId().split(",");
                BasOperationPeople assistant1 = basOperationPeopleDao.queryOperationPeopleById(assistant[0]);
                cstmt.setString(11, null != assistant1 ? assistant1.getCode() : ""); //手术一助
                BasOperationPeople assistant2 = assistant.length > 1 ? basOperationPeopleDao.queryOperationPeopleById(assistant[1]) : null;
                cstmt.setString(12, null != assistant2 ? assistant2.getCode() : ""); //手术二助
                BasOperationPeople assistant3 = assistant.length > 2 ? basOperationPeopleDao.queryOperationPeopleById(assistant[2]) : null;
                cstmt.setString(13, null != assistant3 ? assistant3.getCode() : ""); //手术三助
                BasOperationPeople assistant4 = assistant.length > 3 ? basOperationPeopleDao.queryOperationPeopleById(assistant[3]) : null;
                cstmt.setString(33, null != assistant4 ? assistant4.getCode() : ""); //手术助手(四)
                BasOperationPeople assistant5 = assistant.length > 4 ? basOperationPeopleDao.queryOperationPeopleById(assistant[4]) : null;
                cstmt.setString(34, null != assistant5 ? assistant5.getCode() : ""); //手术助手(五)
                BasOperationPeople assistant6 = assistant.length > 5 ? basOperationPeopleDao.queryOperationPeopleById(assistant[5]) : null;
                cstmt.setString(35, null != assistant6 ? assistant6.getCode() : ""); //手术助手(六)
                BasOperationPeople assistant7 = assistant.length > 6 ? basOperationPeopleDao.queryOperationPeopleById(assistant[6]) : null;
                cstmt.setString(36, null != assistant7 ? assistant7.getCode() : ""); //手术助手(七)
                BasOperationPeople assistant8 = assistant.length > 7 ? basOperationPeopleDao.queryOperationPeopleById(assistant[7]) : null;
                cstmt.setString(37, null != assistant8 ? assistant8.getCode() : ""); //手术助手(八)
                BasOperationPeople assistant9 = assistant.length > 8 ? basOperationPeopleDao.queryOperationPeopleById(assistant[8]) : null;
                cstmt.setString(38, null != assistant9 ? assistant9.getCode() : ""); //手术助手(九)
            }
            else
            {
                cstmt.setString(11, null); //手术一助
                cstmt.setString(12, null); //手术二助
                cstmt.setString(13, null); //手术三助
                cstmt.setString(33, null);//手术助手(四)
                cstmt.setString(34, null);//手术助手(五)
                cstmt.setString(35, null);//手术助手(六)
                cstmt.setString(36, null);//手术助手(七)
                cstmt.setString(37, null);//手术助手(八)
                cstmt.setString(38, null);//手术助手(九)
            }
            cstmt.setString(14, null);//实习医生
            cstmt.setString(15, basUserDao.selectHisIdByUserName(dispatchBean.getInstrnurseId1(), beid)); //洗手护士1
            cstmt.setString(16, basUserDao.selectHisIdByUserName(dispatchBean.getInstrnurseId2(), beid)); //洗手护士2
            cstmt.setString(17, basUserDao.selectHisIdByUserName(dispatchBean.getCircunurseId1(), beid)); //巡回护士1
            cstmt.setString(18, basUserDao.selectHisIdByUserName(dispatchBean.getCircunurseId2(), beid)); //巡回护士2
            BasAnaesMethod anaesMethod = basAnaesMethodDao.searchAnaesMethodById(regOpt.getDesignedAnaesMethodCode());
            cstmt.setString(19, null != anaesMethod ? anaesMethod.getCode() : ""); //麻醉代码
            cstmt.setString(20, basUserDao.selectHisIdByUserName(dispatchBean.getAnesthetistId(), beid)); //麻醉医生1
            cstmt.setString(21, null); //麻醉医生2
            cstmt.setInt(22, regOpt.getEmergency()); //急诊标志
            cstmt.setString(23, null);  //手术要求
            cstmt.setString(24, null);  //注意事项
            cstmt.setString(25, basUserDao.selectHisIdByUserName(regOpt.getCreateUser(), beid));
            if (!StringUtils.isEmpty(regOpt.getDesignedOptCode()))
            {
                String[] designedOptCode = regOpt.getDesignedOptCode().split(",");
                BasOperdef operdef1 = basOperdefDao.queryOperdefById(designedOptCode[0]);
                cstmt.setInt(26, null != operdef1 ? Integer.parseInt(operdef1.getCode()) : 0);   //手术内(编)码(一)
                BasOperdef operdef2 = designedOptCode.length > 1 ? basOperdefDao.queryOperdefById(designedOptCode[1]) : null;
                cstmt.setInt(27, null != operdef2 ? Integer.parseInt(operdef2.getCode()) : 0);  //手术内(编)码(二)
                BasOperdef operdef3 = designedOptCode.length > 2 ? basOperdefDao.queryOperdefById(designedOptCode[2]) : null;
                cstmt.setInt(28, null != operdef3 ? Integer.parseInt(operdef3.getCode()) : 0);  //手术内(编)码(三)
            }
            else
            {
                cstmt.setInt(26, 0);  //手术内(编)码(一)
                cstmt.setInt(27, 0);  //手术内(编)码(二)
                cstmt.setInt(28, 0);  //手术内(编)码(三)
            }
            
            if (!StringUtils.isEmpty(regOpt.getDesignedOptName()))
            {
                String[] designedOptName = regOpt.getDesignedOptName().split(",");
                cstmt.setString(29, designedOptName[0]);  //手术名称(一)
                cstmt.setString(30, designedOptName.length > 1 ? designedOptName[1] : null);  //手术名称(二)
                cstmt.setString(31, designedOptName.length > 2 ? designedOptName[2] : null);  //手术名称(三)
            }
            else
            {
                cstmt.setString(29, null);  //手术名称(一)
                cstmt.setString(30, null);  //手术名称(二)
                cstmt.setString(31, null);  //手术名称(三)
            }
            cstmt.setString(39, null);  //麻醉医生(三)
            cstmt.setString(40, null);  //麻醉医生(四)
            cstmt.setString(41, null);  //麻醉医生(五)
            cstmt.setString(42, null);  //麻醉医生(六)
            cstmt.setString(43, null);  //洗手护士(三)
            cstmt.setString(44, null);  //巡回护士(三)
            
            if (!StringUtils.isEmpty(regOpt.getDiagnosisCode()))
            {
                String[] diagnosisCode = regOpt.getDiagnosisCode().split(",");
                BasDiagnosedef diagnosedef1 = basDiagnosedefDao.searchDiagnosedefById(diagnosisCode[0]);
                cstmt.setInt(45, null != diagnosedef1 ? Integer.parseInt(diagnosedef1.getCode()) : 0);  //诊断序号(一)
                BasDiagnosedef diagnosedef2 = diagnosisCode.length > 1 ? basDiagnosedefDao.searchDiagnosedefById(diagnosisCode[1]) : null;
                cstmt.setInt(47, null != diagnosedef2 ? Integer.parseInt(diagnosedef2.getCode()) : 0);  //诊断序号(二)
                BasDiagnosedef diagnosedef3 = diagnosisCode.length > 2 ? basDiagnosedefDao.searchDiagnosedefById(diagnosisCode[2]) : null;
                cstmt.setInt(49, null != diagnosedef3 ? Integer.parseInt(diagnosedef3.getCode()) : 0);  //诊断序号(三)
                BasDiagnosedef diagnosedef4 = diagnosisCode.length > 3 ? basDiagnosedefDao.searchDiagnosedefById(diagnosisCode[3]) : null;
                cstmt.setInt(51, null != diagnosedef4 ? Integer.parseInt(diagnosedef4.getCode()) : 0);  //诊断序号(四)
            }
            else
            {
                cstmt.setInt(45, 0);  //诊断序号(一)
                cstmt.setInt(47, 0);  //诊断序号(二)
                cstmt.setInt(49, 0);  //诊断序号(三)
                cstmt.setInt(51, 0);  //诊断序号(四)
            }
            
            if (!StringUtils.isEmpty(regOpt.getDiagnosisName()))
            {
                String[] diagnosisName = regOpt.getDiagnosisName().split(",");
                cstmt.setString(46, diagnosisName[0]);  //诊断名称(一)
                cstmt.setString(48, diagnosisName.length > 1 ? diagnosisName[1] : null);  //诊断名称(二)
                cstmt.setString(50, diagnosisName.length > 2 ? diagnosisName[2] : null);  //诊断名称(三)
                cstmt.setString(52, diagnosisName.length > 3 ? diagnosisName[3] : null);  //诊断名称(四)
            }
            else
            {
                cstmt.setString(46, null);  //诊断名称(一)
                cstmt.setString(48, null);  //诊断名称(二)
                cstmt.setString(50, null);  //诊断名称(三)
                cstmt.setString(52, null);  //诊断名称(四)
            }
            cstmt.execute();
            logger.info("------------------sendScheduleToHis 更新成功!--------------------");
         }catch(Exception e){
             logger.error("sendScheduleToHis--------------"+Exceptions.getStackTraceAsString(e));
         }
        finally{
            try
            {
                close(conn,cstmt, null);
            }
            catch (SQLException e)
            {
                logger.error("sendScheduleToHis--------------"+Exceptions.getStackTraceAsString(e));
            }
            
        }
        logger.info("end sendScheduleToHis");
         
    }
	
    /**
     * 手术取消时信息回传HIS
     * @param regOpt
     * @return
     *//*
    @Transactional
    public void sendCancleRegOpt(BasRegOpt regOpt)
    {
        logger.info("begin sendCancleRegOpt");
        String insertSql = "insert into SZHS_Operation_Add_Temporary(SQDH,SSKS,ZYHM,APRQ,SSRQ,SSNM,SSYS,ZFBZ,CZGH) values(?,?,?,?,?,?,?,?,?)";
        String updateSql ="update SZHS_Operation_Add_Temporary set ZFBZ=? where SQDH = ?";
        
        int i = 0;
        Connection conn = ConnectionManager.getSYBXHisConnection();
        PreparedStatement insertPstmt = null;
        PreparedStatement selectPstmt = null;
        PreparedStatement updatePstmt = null;
        ResultSet rs = null;
        if (StringUtils.isBlank(regOpt.getPreengagementnumber()))
        {
            logger.error("============手术申请单号错误==============" + regOpt.getPreengagementnumber());
            return;
        }
        
        String selectSql = "select * from SZHS_Operation_Add_Temporary where SQDH = '"+regOpt.getPreengagementnumber()+"'";
        
        try{
            selectPstmt = (PreparedStatement)conn.prepareStatement(selectSql);
            rs = selectPstmt.executeQuery();
            if(!rs.next()){
                insertPstmt = (PreparedStatement)conn.prepareStatement(insertSql);
                insertPstmt.setString(1, regOpt.getPreengagementnumber());//his手术单号
                insertPstmt.setString(2, regOpt.getDeptId()+"");//科室id
                insertPstmt.setString(3, regOpt.getHid());//住院号
                insertPstmt.setString(4, regOpt.getOperaDate());//手术安排日期
                insertPstmt.setString(5, regOpt.getOperaDate());//手术日期
                insertPstmt.setString(6, StringUtils.isNoneBlank(regOpt.getDesignedOptCode()) ? regOpt.getDesignedOptCode() : "0");//手术名称id
                insertPstmt.setString(7, "");//手术医师
                insertPstmt.setString(8, "1"); //作废标志
                insertPstmt.setString(9, regOpt.getCreateUser());   //操作工号
                int result = insertPstmt.executeUpdate();
                if(result > 0){
                    logger.info("------------------sendCancleRegOpt 插入成功!--------------------");
                }else{
                    logger.info("------------------sendCancleRegOpt 插入失败!--------------------");
                }
            }else{
                updatePstmt = (PreparedStatement)conn.prepareStatement(updateSql);
                updatePstmt.setString(1, "1"); //作废标志
                updatePstmt.setString(2, regOpt.getPreengagementnumber());
                int result = updatePstmt.executeUpdate();
                if(result > 0){
                    logger.info("------------------sendCancleRegOpt 更新成功!--------------------");
                }else{
                    logger.info("------------------sendCancleRegOpt 更新失败!--------------------");
                }
            }
         }catch(Exception e){
             logger.error("sendCancleRegOpt--------------"+Exceptions.getStackTraceAsString(e));
         }
        finally{
            try
            {
                close(conn,insertPstmt, rs);
                close(conn,updatePstmt, rs);
                close(conn,selectPstmt, rs);
            }
            catch (SQLException e)
            {
                logger.error("sendCancleRegOpt--------------"+Exceptions.getStackTraceAsString(e));
            }
        }
        logger.info("begin sendCancleRegOpt");
    }*/
	
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
    
    private boolean isNeedUpdate(String value, String hisValue)
    {
        if ((null == value && null != hisValue) || (null != value && !value.equals(hisValue)))
        {
            return true;
        }
        
        return false;
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
    
}
