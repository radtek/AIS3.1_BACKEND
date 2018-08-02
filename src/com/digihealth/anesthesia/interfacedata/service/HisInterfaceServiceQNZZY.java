package com.digihealth.anesthesia.interfacedata.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
import com.digihealth.anesthesia.basedata.formbean.DispatchPeopleNameFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
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
import com.digihealth.anesthesia.doc.dao.DocBloodTransRecordDao;
import com.digihealth.anesthesia.doc.dao.DocDifficultCaseDiscussDao;
import com.digihealth.anesthesia.doc.dao.DocExitOperSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocGeneralAnaesDao;
import com.digihealth.anesthesia.doc.dao.DocNerveBlockDao;
import com.digihealth.anesthesia.doc.dao.DocOperBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocOptCareRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptNurseDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatRescurRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPreVisitDao;
import com.digihealth.anesthesia.doc.dao.DocSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayInstrumentAccedeDao;
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
import com.digihealth.anesthesia.doc.po.DocBloodTransRecord;
import com.digihealth.anesthesia.doc.po.DocDifficultCaseDiscuss;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.doc.po.DocPatInspectItem;
import com.digihealth.anesthesia.doc.po.DocPatInspectRecord;
import com.digihealth.anesthesia.doc.po.DocPatRescurRecord;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.evt.dao.EvtOptRealOperDao;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.interfaceParameters.qnzzy.zlsoft.ClsIntegrationPlatfrom;
import com.digihealth.anesthesia.interfaceParameters.qnzzy.zlsoft.ClsIntegrationPlatfromSoap;
import com.digihealth.anesthesia.interfacedata.po.qnzzy.HisOutputMessage;
import com.digihealth.anesthesia.interfacedata.po.qnzzy.Row;
import com.digihealth.anesthesia.sysMng.dao.BasUserDao;
import com.google.common.base.Objects;

@Service
public class HisInterfaceServiceQNZZY
{
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    //private BaseService baseService = SpringContextHolder.getBean(BaseService.class);
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
    private BasUserDao basUserDao = SpringContextHolder.getBean(BasUserDao.class);
    private EvtOptRealOperDao evtOptRealOperDao = SpringContextHolder.getBean(EvtOptRealOperDao.class);
    private DocAnaesRecordDao docAnaesRecordDao = SpringContextHolder.getBean(DocAnaesRecordDao.class);
    private BasDispatchDao basDispatchDao = SpringContextHolder.getBean(BasDispatchDao.class);
    private ControllerDao controllerDao = SpringContextHolder.getBean(ControllerDao.class);
    
    //============================================基础数据同步  begin=================================================================
    /**
     * 获取科室列表
     * 
     */
    @Transactional
    public void synHisDeptRoomList(){
        logger.info("-------start synHisDeptRoomList-----------");
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E001");
        String inBody = "<InBody><OnlyUsed>1</OnlyUsed><DeptType>护理</DeptType></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============synHisDeptRoomList请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            //调用his接口获取科室信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============synHisDeptRoomList响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();  
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if(null != response)
                {   
                     if(!"1".equals(response.getReturnCode()))
                     {
                         throw new RuntimeException(response.getErrorMessage()); 
                     }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> deptList = response.getData().getRows().getRowList();
                    if (null != deptList && deptList.size() > 0)
                    {
                        for (Row row : deptList)
                        {
                            String code = row.getDeptCode();
                            String name = row.getDeptName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            BasDept dt = basDeptDao.searchDeptById(code,beid);
                            
                            if (dt!=null && !Objects.equal(dt.getName(),name))
                            {
                                dt.setName(name);
                                dt.setPinYin(pinyin);
                                basDeptDao.update(dt);
                            }
                            
                            if(null == dt)
                            {
                                BasDept dept = new BasDept();
                                dept.setDeptId(code);
                                dept.setName(name);
                                dept.setEnable(1);
                                dept.setPinYin(pinyin);
                                dept.setBeid(beid);
                                basDeptDao.insert(dept);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取科室字典值异常:"+Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("-------end synHisDeptRoomList-----------");
    }
    
    
    /**
     * 
     * 手术名称数据同步 
     */
    @Transactional
    public void synHisOperNameList(){
        logger.info("-------start synHisOperNameList-----------");
        
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E008");
        String inBody = "<InBody><OnlyUsed>1</OnlyUsed></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============synHisOperNameList请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============synHisOperNameList响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> opernameList = response.getData().getRows().getRowList();
                    if (null != opernameList && opernameList.size() > 0)
                    {
                        for (Row row : opernameList)
                        {
                            String code = row.getOperationCode();
                            String name = row.getOperationName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            BasOperdef operdef = basOperdefDao.selectByCode(code, basBusEntityDao.getBeid());
                            
                            if (operdef != null && !Objects.equal(operdef.getName(), name))
                            {
                                operdef.setName(name);
                                operdef.setPinYin(pinyin);
                                basOperdefDao.update(operdef);
                            }
                            
                            if (null == operdef)
                            {
                                operdef = new BasOperdef();
                                operdef.setOperdefId(GenerateSequenceUtil.generateSequenceNo());
                                operdef.setCode(code);
                                operdef.setName(name);
                                operdef.setEnable(1);
                                operdef.setPinYin(pinyin);
                                operdef.setBeid(beid);
                                basOperdefDao.insert(operdef);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取手术名称字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end synHisOperNameList-----------");
    }
    
    
    /**
     * 获取诊断名称列表
     * 
     */
    @Transactional
    public void synHisDiagNameList(){
        logger.info("-------start synHisDiagNameList-----------");
        
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E011");
        String inBody = "<InBody><DiseaseType>D</DiseaseType></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============synHisDiagNameList请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============synHisDiagNameList响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> diagnosedefList = response.getData().getRows().getRowList();
                    if (null != diagnosedefList && diagnosedefList.size() > 0)
                    {
                        for (Row row : diagnosedefList)
                        {
                            String code = row.getDiseaseId();
                            String name = row.getDiseaseName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            BasDiagnosedef diagnosedef = basDiagnosedefDao.selectByCode(code, basBusEntityDao.getBeid());
                            
                            if (diagnosedef != null && !Objects.equal(diagnosedef.getName(), name))
                            {
                                diagnosedef.setName(name);
                                diagnosedef.setPinYin(pinyin);
                                basDiagnosedefDao.update(diagnosedef);
                            }
                            
                            if (null == diagnosedef)
                            {
                                diagnosedef = new BasDiagnosedef();
                                diagnosedef.setDiagDefId(GenerateSequenceUtil.generateSequenceNo());
                                diagnosedef.setCode(code);
                                diagnosedef.setName(name);
                                diagnosedef.setEnable(1);
                                diagnosedef.setPinYin(pinyin);
                                diagnosedef.setBeid(beid);
                                basDiagnosedefDao.insert(diagnosedef);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取诊断名称字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end synHisDiagNameList-----------");
    }
    
    /**
     * 获取手术医生列表
     * 
     */
    @Transactional
    public void syncHisOperDoctor(){
        logger.info("-------start syncHisOperDoctor-----------");
        
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E002");
        String inBody = "<InBody><OnlyDefaultDept>1</OnlyDefaultDept></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncOperDoctor请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisOperDoctor响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> doctorList = response.getData().getRows().getRowList();
                    if (null != doctorList && doctorList.size() > 0)
                    {
                        for (Row row : doctorList)
                        {
                            String userJob = row.getUserJob();
                            if (!"医生".equals(userJob))
                            {
                                continue;
                            }
                            
                            String code = row.getUserCode();
                            String name = row.getUserName();
                            Integer region = StringUtils.isNotBlank(row.getUserDept()) ? Integer.valueOf(row.getUserDept()) : null; 
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            BasOperationPeople operationPeople = basOperationPeopleDao.selectByCode(code, basBusEntityDao.getBeid());
                            
                            if (operationPeople != null && (!Objects.equal(operationPeople.getName(), name) || !Objects.equal(operationPeople.getRegion(), region)))
                            {
                                operationPeople.setName(name);
                                operationPeople.setPinYin(pinyin);
                                operationPeople.setRegion(region);
                                basOperationPeopleDao.update(operationPeople);
                            }
                            
                            if (null == operationPeople)
                            {
                                operationPeople = new BasOperationPeople();
                                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                                operationPeople.setCode(code);
                                operationPeople.setName(name);
                                operationPeople.setEnable(1);
                                operationPeople.setPinYin(pinyin);
                                operationPeople.setRegion(region);
                                operationPeople.setBeid(beid);
                                basOperationPeopleDao.insert(operationPeople);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取手术医生字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end syncHisOperDoctor-----------");
    }
    
    /**
     * 获取麻醉方法列表
     * 
     */
    @Transactional
    public void syncHisAnaesMethod(){
        logger.info("-------start syncHisAnaesMethod-----------");
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E015");
        String inBody = "<InBody><OnlyUsed>1</OnlyUsed></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncHisAnaesMethod请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取麻醉方法信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisAnaesMethod响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> anaesMethodList = response.getData().getRows().getRowList();
                    if (null != anaesMethodList && anaesMethodList.size() > 0)
                    {
                        for (Row row : anaesMethodList)
                        {
                            String code = row.getID();
                            String name = row.getName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            String type = row.getType();
                            BasAnaesMethod anaesMethod = basAnaesMethodDao.selectByCode(code, basBusEntityDao.getBeid());
                            
                            if (anaesMethod != null && (!Objects.equal(anaesMethod.getName(), name) || (!Objects.equal(anaesMethod.getCate1(), type))))
                            {
                                anaesMethod.setName(name);
                                anaesMethod.setPinYin(pinyin);
                                anaesMethod.setCate1(type);
                                basAnaesMethodDao.update(anaesMethod);
                            }
                            
                            if (null == anaesMethod)
                            {
                                anaesMethod = new BasAnaesMethod();
                                anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                                anaesMethod.setCode(code);
                                anaesMethod.setName(name);
                                anaesMethod.setIsValid(1);
                                anaesMethod.setPinYin(pinyin);
                                anaesMethod.setCate1(type);
                                anaesMethod.setIsLocalAnaes(0);
                                if (name.contains("局麻"))
                                {
                                    anaesMethod.setIsLocalAnaes(1);
                                }
                                anaesMethod.setBeid(beid);
                                basAnaesMethodDao.insert(anaesMethod);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取麻醉方法字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end syncHisAnaesMethod-----------");
    }
    
    
    /**
     * 获取器械列表
     * 
     */
    @Transactional
    public void syncHisInstrument(){
        logger.info("-------start syncHisInstrument-----------");
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E016");
        String inBody = "<InBody><OnlyUsed>1</OnlyUsed></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncHisInstrument请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取麻醉方法信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisInstrument响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> instrumentList = response.getData().getRows().getRowList();
                    if (null != instrumentList && instrumentList.size() > 0)
                    {
                        for (Row row : instrumentList)
                        {
                            String code = row.getID();
                            String name = row.getName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            String type = row.getType();
                            BasInstrument instrument = basInstrumentDao.selectByCode(code, basBusEntityDao.getBeid());
                            
                            if (instrument != null && (!Objects.equal(instrument.getName(), name) || (!Objects.equal(instrument.getType(), type))))
                            {
                                instrument.setName(name);
                                instrument.setPinYin(pinyin);
                                instrument.setType(type);
                                basInstrumentDao.update(instrument);
                            }
                            
                            if (null == instrument)
                            {
                                instrument = new BasInstrument();
                                instrument.setInstrumentId(GenerateSequenceUtil.generateSequenceNo());
                                instrument.setCode(code);
                                instrument.setName(name);
                                instrument.setPinYin(pinyin);
                                instrument.setType(type);
                                instrument.setBeid(beid);
                                instrument.setEnable(1);
                                basInstrumentDao.insert(instrument);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取器械字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end syncHisInstrument-----------");
    }
    
    
    /**
     * 获取药品字典
     * 
     */
    @Transactional
    public void syncHisMedicine(){
        logger.info("-------start syncHisMedicine-----------");
        String beid = basBusEntityDao.getBeid();
        String inHead = getInHead("E017");
        String inBody = "<InBody><OnlyUsed>1</OnlyUsed></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncHisMedicine请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取麻醉方法信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisMedicine响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
                
                if (null != response.getData() && null != response.getData().getRows())
                {
                    List<Row> medicineList = response.getData().getRows().getRowList();
                    if (null != medicineList && medicineList.size() > 0)
                    {
                        for (Row row : medicineList)
                        {
                            String code = row.getID();
                            String type = row.getType();
                            String name = row.getName();
                            String pinyin = PingYinUtil.getFirstSpell(name);
                            String spec = row.getSpec();
                            String unit = row.getUnit();
                            Float packageDosageAmount = StringUtils.isNotBlank(row.getPackageDosageAmount()) ? Float.valueOf(row.getPackageDosageAmount()) : null;
                            String dosageUnit = row.getDosageUnit();
                            String firm = row.getFirm();
                            Float price = StringUtils.isNotBlank(row.getPrice()) ? Float.valueOf(row.getPrice()) : null;
                            BasMedicine medicine = basMedicineDao.selectByCode(code, basBusEntityDao.getBeid());
                            BasPrice basPrice = basPriceDao.selectByCode(code, beid);
                            
                            if (medicine != null
                                && (!Objects.equal(medicine.getName(), name)
                                    || !Objects.equal(medicine.getType(), type)
                                    || !Objects.equal(medicine.getSpec(), spec)
                                    || !Objects.equal(medicine.getDosageUnit(), dosageUnit)
                                    || !Objects.equal(medicine.getPackageDosageAmount(), packageDosageAmount)))
                            {
                                medicine.setName(name);
                                medicine.setPinYin(pinyin);
                                medicine.setType(type);
                                medicine.setSpec(spec);
                                medicine.setDosageUnit(dosageUnit);
                                medicine.setPackageDosageAmount(packageDosageAmount);
                                basMedicineDao.update(medicine);
                            }
                            if (null == medicine)
                            {
                                medicine = new BasMedicine();
                                medicine.setMedicineId(GenerateSequenceUtil.generateSequenceNo());
                                medicine.setCode(code);
                                medicine.setName(name);
                                medicine.setPinYin(pinyin);
                                medicine.setType(type);
                                medicine.setSpec(spec);
                                medicine.setDosageUnit(dosageUnit);
                                medicine.setPackageDosageAmount(packageDosageAmount);
                                medicine.setBeid(beid);
                                medicine.setEnable(1);
                                basMedicineDao.insert(medicine);
                            }
                            
                            if (null != basPrice 
                                && (!Objects.equal(basPrice.getSpec(), spec)
                                    || !Objects.equal(basPrice.getFirm(), firm)
                                    || !Objects.equal(basPrice.getMinPackageUnit(), unit) 
                                    || !Objects.equal(basPrice.getPriceMinPackage(), price)))
                            {
                                basPrice.setSpec(spec);
                                basPrice.setFirm(firm);
                                basPrice.setMinPackageUnit(unit);
                                basPrice.setPriceMinPackage(price);
                                basPriceDao.update(basPrice);
                            }
                            
                            if (null == basPrice)
                            {
                                basPrice = new BasPrice();
                                basPrice.setPriceId(GenerateSequenceUtil.generateSequenceNo());
                                basPrice.setCode(code);
                                basPrice.setBeid(beid);
                                basPrice.setEnable(1);
                                basPrice.setSpec(spec);
                                basPrice.setFirm(firm);
                                basPrice.setMinPackageUnit(unit);
                                basPrice.setPriceMinPackage(price);
                                basPriceDao.insert(basPrice);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取器械字典值异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end syncHisInstrument-----------");
    }
    
    //============================================基础数据同步  end==================================================================
    
    /**
     * 同步his检验报告
     */
    @Transactional
    public void syncHisInspection(String regOptId)
    {
        logger.info("---------------------begin syncHisInspection------------------------");
        
        //获取his系统中的病人id
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
        if (null == regOpt || StringUtils.isBlank(regOpt.getPatientId()) || StringUtils.isBlank(regOpt.getVisitId()))
        {
            return;
        }
        String patientId = regOpt.getPatientId();
        String visitId = regOpt.getVisitId();
        
        String inHead = getInHead("E009");
        String inBody = "<InBody><PatientId>" + patientId + "</PatientId><VisitId>" + visitId + "</VisitId><LisVer>OLD</LisVer></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncHisInspection请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisInspection响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取检验报告异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        List<DocPatInspectRecord> patRecordList = new ArrayList<DocPatInspectRecord>();
        if (null != response.getData() && null != response.getData().getRows())
        {
            List<Row> reportList = response.getData().getRows().getRowList();
            if (null != reportList && reportList.size() > 0)
            {
                for (Row row : reportList)
                {
                    DocPatInspectRecord rec = new DocPatInspectRecord();
                    rec.setInspectId(row.getTestNo());
                    if (StringUtils.isNotBlank(row.getSpcmReceivedDateTime()))
                    {
                        rec.setDate(DateUtils.parseDate(row.getSpcmReceivedDateTime()));
                    }
                    if (StringUtils.isNotBlank(row.getResultsRptDateTime()))
                    {
                        rec.setReportDate(DateUtils.parseDate(row.getResultsRptDateTime()));
                    }
                    rec.setState(row.getResultStatus());
                    BasDept dept= basDeptDao.searchDeptById(row.getOrderingDept(), basBusEntityDao.getBeid());
                    rec.setDep(null != dept ? dept.getName() : null);
                    rec.setReqDoctorId(row.getOrderingProvider());
                    rec.setInstruction(row.getTestCause());
                    rec.setRegOptId(regOptId);
                    patRecordList.add(rec);
                }
            }
        }
        if (patRecordList.size() > 0)
        {
            for (DocPatInspectRecord record : patRecordList)
            {
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
                        List<DocPatInspectItem> itemList = syncHisInspectionDetail(record.getInspectId(),patInspectRecord.getId());
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
                    
                    List<DocPatInspectItem> itemList = syncHisInspectionDetail(patInspectRecord.getInspectId(),patInspectRecord.getId());
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
        logger.info("---------------------end syncHisInspection------------------------");
    }
    
    
    /** 
     * 检验报告详情
     * <功能详细描述>
     * @param inspectId
     * @param recordId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<DocPatInspectItem> syncHisInspectionDetail(String inspectId, String recordId)
    {
        logger.info("---------------------begin syncHisInspectionDetail------------------------");
        String inHead = getInHead("E010");
        String inBody = "<InBody><TestNo>" + inspectId + "</TestNo><LisVer>OLD</LisVer></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============syncHisInspection请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============syncHisInspectionDetail响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取检验报告详情异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        List<DocPatInspectItem> patInspectItemList = new ArrayList<DocPatInspectItem>();
        if (null != response.getData() && null != response.getData().getRows())
        {
            List<Row> results = response.getData().getRows().getRowList();
            if (null != results && results.size() > 0)
            {
                for (Row row : results)
                {
                    DocPatInspectItem rec = new DocPatInspectItem();
                    rec.setRecId(recordId);
                    rec.setName(row.getReportItemName());
                    rec.setRefVal(row.getReferenceResult());
                    rec.setVal(row.getResult());
                    rec.setResult(row.getAbnormalIndicator());
                    rec.setUnit(row.getUnits());
                    patInspectItemList.add(rec);
                }
            }
        }
        
        logger.info("---------------------end syncHisInspectionDetail------------------------");
        return patInspectItemList;
    }
    
    /** 
     * 手术排班记录回写
     * <功能详细描述>
     * @param dispatch
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void sendDispatchToHis(BasDispatch dispatch,BasRegOpt regOpt)
    {
        logger.info("---------------------begin sendDispatchToHis------------------------");
        String inHead = getInHead("E014");
        Row row = new Row();
        row.setScheduleId(regOpt.getPreengagementnumber());
        row.setDeptID(regOpt.getDeptId());
        row.setScheduledDateTime(regOpt.getOperaDate() + " 00:00:00");
        row.setOperRoom(dispatch.getOperRoomId());
        row.setSequence(dispatch.getPcs());
        row.setDiagBeforeOperation(regOpt.getDiagnosisName());
        row.setOperationName(regOpt.getDesignedOptName());
        row.setAnesMethod(regOpt.getDesignedAnaesMethodName());
        row.setSurgeon(regOpt.getOperatorName());
        if (StringUtils.isNotBlank(regOpt.getAssistantName()))
        {
            String[] assistantNames = regOpt.getAssistantName().split(",");
            row.setFirstOperAssistant(assistantNames[0]);
            row.setSecondOperAssistant(assistantNames.length > 1 ? assistantNames[1] : null);
        }
        
        String beid = basBusEntityDao.getBeid();
        row.setAnesDoctor(basUserDao.selectNameByUserName(dispatch.getAnesthetistId(), beid));
        row.setFirstAnesAssistant(basUserDao.selectNameByUserName(dispatch.getCircuAnesthetistId(), beid));
        //row.setSecondAnesAssistant("");
        row.setFirstOperNurse(basUserDao.selectNameByUserName(dispatch.getCircunurseId1(), beid));
        row.setSecondOperNurse(basUserDao.selectNameByUserName(dispatch.getCircunurseId2(), beid));
        row.setFirstSupplyNurse(basUserDao.selectNameByUserName(dispatch.getInstrnurseId1(), beid));
        row.setSecondSupplyNurse(basUserDao.selectNameByUserName(dispatch.getInstrnurseId2(), beid));
        //row.setWardID("");
        
        try
        {
            String asXml = getObjectToXml(row);
            
            String inBody = "<InBody><Rows>" + asXml + "</Rows></InBody>";
            String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
            logger.info("============sendDispatchToHis请求报文：  " + req);
            HisOutputMessage response = null;
            String resp = "";
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============sendDispatchToHis响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("回传手术排班记录异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("---------------------end sendDispatchToHis------------------------");
    }
    
    /** 
     * 手术麻醉记录回传
     * <功能详细描述>
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void sendAnaesRecordToHis(String regOptId)
    {
        logger.info("---------------------begin sendAnaesRecordToHis------------------------");
        String inHead = getInHead("E012");
        Row row = new Row();
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
        row.setSMXTID(regOptId.substring(2, regOptId.length()));
        row.setPatientId(regOpt.getPatientId());
        row.setVisitId(regOpt.getVisitId());
        row.setSSSJ(regOpt.getOperaDate() + " 00:00:00");
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
        row.setSSKSSJ(anaesRecord.getOperStartTime());
        row.setSSJSSJ(anaesRecord.getOperEndTime());
        row.setLXSS(regOpt.getDesignedOptName());
        
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        String beid = basBusEntityDao.getBeid();
        searchBean.setBeid(beid);
        List<String> operDefNames = new ArrayList<String>();
        List<EvtOptRealOper> operDefs = evtOptRealOperDao.searchOptRealOperList(searchBean);
        if (null != operDefs && operDefs.size() > 0)
        {
            for (EvtOptRealOper optRealOper : operDefs)
            {
                operDefNames.add(optRealOper.getName());
            }
        }
        row.setYXSS(StringUtils.getStringByList(operDefNames));
        
        row.setZDYS(regOpt.getOperatorName());
        if (StringUtils.isNotBlank(regOpt.getAssistantName()))
        {
            String[] assistantNames = regOpt.getAssistantName().split(",");
            row.setDYZS(assistantNames[0]);
            row.setDEZS(assistantNames.length > 1 ? assistantNames[1] : null);
        }
        //row.setMZLX("");
        DispatchPeopleNameFormBean dispatchName = basDispatchDao.searchPeopleNameByRegOptId(regOptId,beid);
        row.setMZYS(dispatchName.getAnesthetistName());
        if (Objects.equal(regOpt.getCutLevel(), 1))
        {
            row.setQK("Ⅰ");
        }
        else if (Objects.equal(regOpt.getCutLevel(), 2))
        {
            row.setQK("Ⅱ");
        }
        else if (Objects.equal(regOpt.getCutLevel(), 3))
        {
            row.setQK("Ⅲ");
        }
        else if (Objects.equal(regOpt.getCutLevel(), 4))
        {
            row.setQK("Ⅳ");
        }   
        row.setJLR(dispatchName.getAnesthetistName());
        if (0 == regOpt.getEmergency())
        {
            row.setSSQK("择期");
        }
        else if (1 == regOpt.getEmergency())
        {
            row.setSSQK("急诊");
        }
        
        if (StringUtils.isNotBlank(anaesRecord.getAsaLevel()))
        {
            row.setASAFJ("P" + anaesRecord.getAsaLevel());
        }
            
        if ("一级".equals(regOpt.getOptLevel()))
        {
            row.setSSJB("1");
        }
        else if ("二级".equals(regOpt.getOptLevel()))
        {
            row.setSSJB("2");
        }
        else if ("三级".equals(regOpt.getOptLevel()))
        {
            row.setSSJB("3");
        }
        else if ("四级".equals(regOpt.getOptLevel()))
        {
            row.setSSJB("4");
        }
        
        try
        {
            String asXml = getObjectToXml(row);
            
            String inBody = "<InBody><Rows>" + asXml + "</Rows></InBody>";
            String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
            logger.info("============sendAnaesRecordToHis请求报文：  " + req);
            HisOutputMessage response = null;
            String resp = "";
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============sendAnaesRecordToHis响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("回传手术麻醉记录异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("---------------------end sendAnaesRecordToHis------------------------");
    }
    
    /**
     * 获取手术通知单
     * 
     */
    @Transactional
    public void getHisOperateNotice()
    {
        logger.info("---------------------begin getHisOperateNotice------------------------");
        String inHead = getInHead("E005");
        String inBody = "<InBody><DatStart>"+ DateUtils.getDate() + " 00:00:00" +"</DatStart><DatEnd>"+ DateUtils.plusDay(DateUtils.getDate(), 5) + " 00:00:00" +"</DatEnd></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============getHisOperateNotice请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        String beid = basBusEntityDao.getBeid(); 
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============getHisOperateNotice响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取手术通知单异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        if (null != response.getData() && null != response.getData().getRows())
        {
            List<Row> results = response.getData().getRows().getRowList();
            if (null != results && results.size() > 0)
            {
                for (Row row : results)
                {
                    BasRegOpt regOpt = basRegOptDao.searchRegOptByReservenumber(row.getScheduleId(), beid);
                    
                    /*//手术信息存在，并且申请单号没有改变，说明该手术信息已存入到手麻系统中，并且his那边没有做过修改
                    if (null != regOpt && regOpt.getPreengagementnumber().equals(row.getScheduleId()))
                    {
                        continue;
                    }*/
                    
                    //手术信息不存在，直接存入到手麻系统
                    if (null == regOpt)
                    {
                        regOpt = new BasRegOpt();
                        regOpt.setRegOptId(GenerateSequenceUtil.generateSequenceNo());
                        regOpt.setPatientId(row.getPatientId());
                        regOpt.setVisitId(row.getVisitId());
                        setRegOpt(regOpt, row, beid);
                        
                        basRegOptDao.insert(regOpt);
                        
                        Controller controller = new Controller();
                        controller.setRegOptId(regOpt.getRegOptId());
                        controller.setCostsettlementState("0");
                        
                        //如果是急诊手术，则直接将手术状态变为未排班，并且创建手术文书
                        if (1 == regOpt.getEmergency())
                        {
                            controller.setState(OperationState.NO_SCHEDULING);
                            creatDocument(regOpt);
                        }
                        else
                        {
                            controller.setState(OperationState.NOT_REVIEWED);
                        }
                        controllerDao.update(controller);
                    }
                    //手术信息存在，但是申请单号不同，说明his那边对该手术做过修改，需要更新手术信息
                   /* else
                    {
                        setRegOpt(regOpt, row, beid);
                        basRegOptDao.updateByPrimaryKey(regOpt);
                    }*/
                }
            }
        }
        logger.info("---------------------end getHisOperateNotice------------------------");
    }

    private void setRegOpt(BasRegOpt regOpt, Row row, String beid)
    {
        regOpt.setPreengagementnumber(row.getScheduleId());
        regOpt.setDeptId(row.getDeptStayed());
        BasDept dept = basDeptDao.searchDeptById(row.getDeptStayed(), beid);
        regOpt.setDeptName(null != dept ? dept.getName() : null);
        regOpt.setBed(row.getBedNo());
        if (StringUtils.isNotBlank(row.getScheduledDateTime()))
        {
            regOpt.setOperaDate(row.getScheduledDateTime().substring(0, 10));
        }
        
        //术前诊断
        if (StringUtils.isNotBlank(row.getDiagBeforeOperation()))
        {
            List<String> diagnoseIds = new ArrayList<String>();
            String[] diagNames = row.getDiagBeforeOperation().split(",");
            for (String name : diagNames)
            {
                List<BasDiagnosedef> diagnosedefs = basDiagnosedefDao.selectByName(name, beid);
                if (null != diagnosedefs && diagnosedefs.size() > 0)
                {
                    diagnoseIds.add(diagnosedefs.get(0).getDiagDefId());
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
                    diagnoseIds.add(diagnosedef.getDiagDefId());
                }
            }
            regOpt.setDiagnosisCode(StringUtils.getStringByList(diagnoseIds));
            regOpt.setDiagnosisName(row.getDiagBeforeOperation());
        }
        
        //切口等级
        if("Ⅰ级".equals(row.getIncisionLevel()) || "1".equals(row.getIncisionLevel()) || "一级".equals(row.getIncisionLevel()) || "一".equals(row.getIncisionLevel()))
        {
            regOpt.setCutLevel(1);
        }
        else if("Ⅱ级".equals(row.getIncisionLevel()) || "2".equals(row.getIncisionLevel()) || "二级".equals(row.getIncisionLevel()) || "二".equals(row.getIncisionLevel()))
        {
            regOpt.setCutLevel(2);
        }
        else if("Ⅲ级".equals(row.getIncisionLevel()) || "3".equals(row.getIncisionLevel()) || "三级".equals(row.getIncisionLevel()) || "三".equals(row.getIncisionLevel()))
        {
            regOpt.setCutLevel(3);
        }
        else if("Ⅳ级".equals(row.getIncisionLevel()) || "4".equals(row.getIncisionLevel()) || "四级".equals(row.getIncisionLevel()) || "四".equals(row.getIncisionLevel()))
        {
            regOpt.setCutLevel(4);
        }
        
        //手术等级
        /*if ("小".equals(row.getOperationScale()))
        {
            regOpt.setOptLevel("一级");
        }
        else if ("中".equals(row.getOperationScale()))
        {
            regOpt.setOptLevel("二级");
        }
        else if ("大".equals(row.getOperationScale()))
        {
            regOpt.setOptLevel("三级");
        }
        else if ("特大".equals(row.getOperationScale()))
        {
            regOpt.setOptLevel("四级");
        }*/
        
        //急诊标识
        if (StringUtils.isNotBlank(row.getEmergencyIndicator()))
        {
            regOpt.setEmergency(Integer.valueOf(row.getEmergencyIndicator()));
        }
        
        //手术医生
        if (StringUtils.isNotBlank(row.getSurgeon()))
        {
            List<BasOperationPeople> bopList = basOperationPeopleDao.selectByName(row.getSurgeon(), beid);
            regOpt.setOperatorName(row.getSurgeon());
            if (null != bopList && bopList.size() > 0)
            {
                regOpt.setOperatorId((null != bopList && bopList.size() > 0) ? bopList.get(0).getOperatorId() : null);
            }
            else
            {
                BasOperationPeople operationPeople = new BasOperationPeople();
                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                operationPeople.setCode(null);
                operationPeople.setName(row.getSurgeon());
                operationPeople.setEnable(1);
                operationPeople.setPinYin(PingYinUtil.getFirstSpell(row.getSurgeon()));
                operationPeople.setBeid(beid);
                basOperationPeopleDao.insert(operationPeople);
                
                regOpt.setOperatorId(operationPeople.getOperatorId());
            }
        }
        
        //手术助手
        List<String> assistantIds = new ArrayList<String>();
        List<String> assistantNames = new ArrayList<String>();
        if (StringUtils.isNotBlank(row.getFirstAssistant()))
        {
            String name = row.getFirstAssistant();
            List<BasOperationPeople> bopList = basOperationPeopleDao.selectByName(name, beid);
            assistantNames.add(name);
            if (null != bopList && bopList.size() > 0)
            {
                assistantIds.add(bopList.get(0).getOperatorId());
            }
            else
            {
                BasOperationPeople operationPeople = new BasOperationPeople();
                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                operationPeople.setCode(null);
                operationPeople.setName(name);
                operationPeople.setEnable(1);
                operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                operationPeople.setBeid(beid);
                basOperationPeopleDao.insert(operationPeople);
                
                assistantIds.add(operationPeople.getOperatorId());
            }
        }
        if (StringUtils.isNotBlank(row.getSecondAssistant()))
        {
            String name = row.getSecondAssistant();
            List<BasOperationPeople> bopList = basOperationPeopleDao.selectByName(name, beid);
            assistantNames.add(name);
            if (null != bopList && bopList.size() > 0)
            {
                assistantIds.add(bopList.get(0).getOperatorId());
            }
            else
            {
                BasOperationPeople operationPeople = new BasOperationPeople();
                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                operationPeople.setCode(null);
                operationPeople.setName(name);
                operationPeople.setEnable(1);
                operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                operationPeople.setBeid(beid);
                basOperationPeopleDao.insert(operationPeople);
                
                assistantIds.add(operationPeople.getOperatorId());
            }
        }
        if (StringUtils.isNotBlank(row.getThirdAssistant()))
        {
            String name = row.getThirdAssistant();
            List<BasOperationPeople> bopList = basOperationPeopleDao.selectByName(name, beid);
            assistantNames.add(name);
            if (null != bopList && bopList.size() > 0)
            {
                assistantIds.add(bopList.get(0).getOperatorId());
            }
            else
            {
                BasOperationPeople operationPeople = new BasOperationPeople();
                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                operationPeople.setCode(null);
                operationPeople.setName(name);
                operationPeople.setEnable(1);
                operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                operationPeople.setBeid(beid);
                basOperationPeopleDao.insert(operationPeople);
                
                assistantIds.add(operationPeople.getOperatorId());
            }
        }
        if (StringUtils.isNotBlank(row.getFourthAssistant()))
        {
            String name = row.getFourthAssistant();
            List<BasOperationPeople> bopList = basOperationPeopleDao.selectByName(name, beid);
            assistantNames.add(name);
            if (null != bopList && bopList.size() > 0)
            {
                assistantIds.add(bopList.get(0).getOperatorId());
            }
            else
            {
                BasOperationPeople operationPeople = new BasOperationPeople();
                operationPeople.setOperatorId(GenerateSequenceUtil.generateSequenceNo());
                operationPeople.setCode(null);
                operationPeople.setName(name);
                operationPeople.setEnable(1);
                operationPeople.setPinYin(PingYinUtil.getFirstSpell(name));
                operationPeople.setBeid(beid);
                basOperationPeopleDao.insert(operationPeople);
                
                assistantIds.add(operationPeople.getOperatorId());
            }
        }
        regOpt.setAssistantId(StringUtils.getStringByList(assistantIds));
        regOpt.setAssistantName(StringUtils.getStringByList(assistantNames));
        
        //麻醉方法
        if (StringUtils.isNotBlank(row.getAnesthesiaMethod()))
        {
            String[] anaesMethods = row.getAnesthesiaMethod().split("-");
            String code = anaesMethods[0];
            String name = anaesMethods[1];
            BasAnaesMethod anaesMethod = basAnaesMethodDao.selectByCode(code, beid);
            if (null != anaesMethod)
            {
                regOpt.setDesignedAnaesMethodCode(anaesMethod.getAnaMedId());
                regOpt.setDesignedAnaesMethodName(name);
            }
            else
            {
                anaesMethod = new BasAnaesMethod();
                anaesMethod.setAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                anaesMethod.setCode(code);
                anaesMethod.setName(name);
                anaesMethod.setBeid(beid);
                anaesMethod.setIsLocalAnaes(0);
                anaesMethod.setIsValid(1);
                anaesMethod.setPinYin(PingYinUtil.getFirstSpell(name));
                regOpt.setDesignedAnaesMethodCode(anaesMethod.getAnaMedId());
                regOpt.setDesignedAnaesMethodCode(name);
            }
        }
        else
        {
            //his如果没有传麻醉方法，则默认为全麻
            regOpt.setDesignedAnaesMethodCode("全身麻醉");
            List<BasAnaesMethod> anaesMethods = basAnaesMethodDao.selectByName("全身麻醉",beid);
            regOpt.setDesignedAnaesMethodCode(null != anaesMethods && anaesMethods.size() > 0 ? anaesMethods.get(0).getAnaMedId() : null);
        }
        
        //手术名称
        if (StringUtils.isNotBlank(row.getOperationCode()))
        {
            List<String> operationIds = new ArrayList<String>();
            List<String> operationNames = new ArrayList<String>();
            String[] operationCodes = row.getOperationCode().split(",");
            for (String s : operationCodes)
            {
                BasOperdef operdef = basOperdefDao.selectByCode(s, beid);
                if (null != operdef)
                {
                    operationIds.add(operdef.getOperdefId());
                    operationNames.add(operdef.getName());
                }
            }
            regOpt.setDesignedOptCode(StringUtils.getStringByList(operationIds));
            regOpt.setDesignedOptName(StringUtils.getStringByList(operationNames));
        }
        
        regOpt.setRemark(row.getNotesOnOperation());
        regOpt.setCreateTime(row.getReqDateTime());
        regOpt.setCreateUser(row.getEnteredBy());
        regOpt.setBeid(beid);
        getOperBaseInfo(regOpt);
    }

    private void getOperBaseInfo(BasRegOpt regOpt)
    {
        String inHead = getInHead("E003");
        String inBody = "<InBody><Key>病人ID</Key><Values>" + regOpt.getPatientId() + "</Values></InBody>";
        String req = "<HIS_MESSAGE>" + inHead + inBody + "</HIS_MESSAGE>";
        logger.info("============getOperBaseInfo请求报文：  " + req);
        HisOutputMessage response = null;
        String resp = "";
        try
        {
            // 调用his接口获取手术名称信息
            resp = getClsIntegrationPlatfromSoap().integrationPlatfrom(req);
            logger.info("============getOperBaseInfo响应报文报文：  " + resp);
            if (StringUtils.isNotBlank(resp))
            {
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(resp));
                
                if (null != response)
                {
                    if (!"1".equals(response.getReturnCode()))
                    {
                        throw new RuntimeException(response.getErrorMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取手术病人基本信息异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        if (null != response.getData() && null != response.getData().getRows()
            && null != response.getData().getRows().getRowList()
            && response.getData().getRows().getRowList().size() > 0)
        {
            Row row = response.getData().getRows().getRowList().get(0);
            regOpt.setHid(row.getInPatientNo());
            regOpt.setName(row.getPatientName());
            regOpt.setSex(row.getSex());
            String birthday = DateUtils.strToStr(row.getDateOfBirth(), "yyyy/MM/dd", "yyyy-MM-dd");
            regOpt.setBirthday(birthday);
            int[] age = DateUtils.getNaturalAge(birthday, DateUtils.getDate());
            if (0 != age[0])
            {
                regOpt.setAge(age[0]);
            }
            if(0 != age[1])
            {
                regOpt.setAgeMon(age[1]);
            }
            if(0 != age[2])
            {
                regOpt.setAgeDay(age[2]);
            }
            regOpt.setIdentityNo(row.getIdCardNo());
        }
    }

    private String getInHead(String s)
    {
        return "<InHead><BusinessNumber>" + s
            + "</BusinessNumber><TerminalNumber>1707</TerminalNumber><CooperationUnit>迪聚海思</CooperationUnit></InHead>";
    }
    
    /** 
     * 将对象转换成XML格式的文件
     * <功能详细描述>
     * @param object
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static <T> String getObjectToXml(T object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            // 将对象转变为xml Object------XML
            // 指定对应的xml文件
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xml头信息

            // 将对象转换为对应的XML文件
            marshaller.marshal(object, byteArrayOutputStream);
        } catch (JAXBException e) {

            e.printStackTrace();
        }
        // 转化为字符串返回
        String xmlContent = new String(byteArrayOutputStream.toByteArray(),
                "UTF-8");
        return xmlContent;
    }
    
    /**
     *实例化HIS webService接口
     */
    private ClsIntegrationPlatfromSoap getClsIntegrationPlatfromSoap()
    {
        ClsIntegrationPlatfrom service = new ClsIntegrationPlatfrom();
        ClsIntegrationPlatfromSoap soap = service.getClsIntegrationPlatfromSoap();
        return soap;
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
        
        //手术麻醉使用自费及高价耗材知情同意书
        DocSelfPayInstrumentAccede selfPayInstrumentAccede = new DocSelfPayInstrumentAccede();
        selfPayInstrumentAccede.setRegOptId(regOpt.getRegOptId());
        selfPayInstrumentAccede.setProcessState("NO_END");
        selfPayInstrumentAccede.setId(GenerateSequenceUtil.generateSequenceNo());
        DocSelfPayInstrumentAccedeDao docSelfPayInstrumentAccedeDao = SpringContextHolder.getBean(DocSelfPayInstrumentAccedeDao.class);
        docSelfPayInstrumentAccedeDao.insert(selfPayInstrumentAccede);
        
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
        
        //临床输血记录单
        DocBloodTransRecord docBloodTransRecord = new DocBloodTransRecord();
        docBloodTransRecord.setRegOptId(regOpt.getRegOptId());
        docBloodTransRecord.setProcessState("NO_END");
        docBloodTransRecord.setBloodTransId(GenerateSequenceUtil.generateSequenceNo());
        DocBloodTransRecordDao docBloodTransRecordDao = SpringContextHolder.getBean(DocBloodTransRecordDao.class);
        docBloodTransRecordDao.insert(docBloodTransRecord);
        
        //疑难病人讨论记录
        DocDifficultCaseDiscuss difficultCaseDiscuss = new DocDifficultCaseDiscuss();
        difficultCaseDiscuss.setRegOptId(regOpt.getRegOptId());
        difficultCaseDiscuss.setProcessState("NO_END");
        difficultCaseDiscuss.setId(GenerateSequenceUtil.generateSequenceNo());
        DocDifficultCaseDiscussDao docDifficultCaseDiscussDao = SpringContextHolder.getBean(DocDifficultCaseDiscussDao.class);
        docDifficultCaseDiscussDao.insert(difficultCaseDiscuss);
        
        //危重病人抢救记录
        DocPatRescurRecord patRescurRecord = new DocPatRescurRecord();
        patRescurRecord.setRegOptId(regOpt.getRegOptId());
        patRescurRecord.setProcessState("NO_END");
        patRescurRecord.setId(GenerateSequenceUtil.generateSequenceNo());
        DocPatRescurRecordDao docPatRescurRecordDao = SpringContextHolder.getBean(DocPatRescurRecordDao.class);
        docPatRescurRecordDao.insert(patRescurRecord);
        
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
