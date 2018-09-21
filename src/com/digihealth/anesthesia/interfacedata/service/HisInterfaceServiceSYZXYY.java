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
import com.digihealth.anesthesia.basedata.dao.BasChargeItemDao;
import com.digihealth.anesthesia.basedata.dao.BasDeptDao;
import com.digihealth.anesthesia.basedata.dao.BasDiagnosedefDao;
import com.digihealth.anesthesia.basedata.dao.BasDispatchDao;
import com.digihealth.anesthesia.basedata.dao.BasDocumentDao;
import com.digihealth.anesthesia.basedata.dao.BasInstrumentDao;
import com.digihealth.anesthesia.basedata.dao.BasMedicineDao;
import com.digihealth.anesthesia.basedata.dao.BasOperationPeopleDao;
import com.digihealth.anesthesia.basedata.dao.BasOperdefDao;
import com.digihealth.anesthesia.basedata.dao.BasOperroomDao;
import com.digihealth.anesthesia.basedata.dao.BasPriceDao;
import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.dao.BasRegionDao;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasChargeItem;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasMedicine;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasPrice;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.BasRegion;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.BeanHelper;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.dao.DocAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesMedicineAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPlanDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPostopDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPreDiscussRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesQualityControlDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAllergicReactionDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixCanalDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixGenDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixVisitDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryVenipunctureDao;
import com.digihealth.anesthesia.doc.dao.DocEventBillingDao;
import com.digihealth.anesthesia.doc.dao.DocExitOperSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocGeneralAnaesDao;
import com.digihealth.anesthesia.doc.dao.DocLaborAnalgesiaAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocNerveBlockDao;
import com.digihealth.anesthesia.doc.dao.DocOperBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocOptCareRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptNurseDao;
import com.digihealth.anesthesia.doc.dao.DocOptRiskEvaluationDao;
import com.digihealth.anesthesia.doc.dao.DocPackagesItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatOutRangeAgreeDao;
import com.digihealth.anesthesia.doc.dao.DocPatShuttleTransferDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPreOperVisitDao;
import com.digihealth.anesthesia.doc.dao.DocPrePostVisitDao;
import com.digihealth.anesthesia.doc.dao.DocPreVisitDao;
import com.digihealth.anesthesia.doc.dao.DocSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayInstrumentAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocSpinalCanalPunctureDao;
import com.digihealth.anesthesia.doc.po.DocAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesPlan;
import com.digihealth.anesthesia.doc.po.DocAnaesPostop;
import com.digihealth.anesthesia.doc.po.DocAnaesPreDiscussRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesQualityControl;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixCanal;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixGen;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocLaborAnalgesiaAccede;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.doc.po.DocOptRiskEvaluation;
import com.digihealth.anesthesia.doc.po.DocPatOutRangeAgree;
import com.digihealth.anesthesia.doc.po.DocPatShuttleTransfer;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPreOperVisit;
import com.digihealth.anesthesia.doc.po.DocPrePostVisit;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.evt.dao.EvtOptRealOperDao;
import com.digihealth.anesthesia.interfaceParameters.syzxyy.cn.com.winning.WebInterface;
import com.digihealth.anesthesia.interfaceParameters.syzxyy.cn.com.winning.WebInterfaceSoap;
import com.digihealth.anesthesia.interfacedata.formbean.syzxyy.HisOptcostFormBean;
import com.digihealth.anesthesia.interfacedata.po.syzxyy.HisOutputMessage;
import com.digihealth.anesthesia.interfacedata.po.syzxyy.Row;
import com.digihealth.anesthesia.sysMng.dao.BasDictItemDao;
import com.digihealth.anesthesia.sysMng.dao.BasUserDao;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;
import com.google.common.base.Objects;


@Service
@Transactional(readOnly = true)
public class HisInterfaceServiceSYZXYY
{
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
    private BasUserDao basUserDao = SpringContextHolder.getBean(BasUserDao.class);
    private EvtOptRealOperDao evtOptRealOperDao = SpringContextHolder.getBean(EvtOptRealOperDao.class);
    private DocAnaesRecordDao docAnaesRecordDao = SpringContextHolder.getBean(DocAnaesRecordDao.class);
    private BasDispatchDao basDispatchDao = SpringContextHolder.getBean(BasDispatchDao.class);
    private BasOperroomDao basOperroomDao = SpringContextHolder.getBean(BasOperroomDao.class);
    private DocAnaesPreDiscussRecordDao docAnaesPreDiscussRecordDao = SpringContextHolder.getBean(DocAnaesPreDiscussRecordDao.class);
    private BasChargeItemDao basChargeItemDao = SpringContextHolder.getBean(BasChargeItemDao.class);
    private BasRegionDao basRegionDao = SpringContextHolder.getBean(BasRegionDao.class);
    private BasDictItemDao basDictItemDao = SpringContextHolder.getBean(BasDictItemDao.class);
    private DocPackagesItemDao docPackagesItemDao = SpringContextHolder.getBean(DocPackagesItemDao.class);
    private DocEventBillingDao docEventBillingDao = SpringContextHolder.getBean(DocEventBillingDao.class);
    private DocAnaesQualityControlDao docAnaesQualityControlDao = SpringContextHolder.getBean(DocAnaesQualityControlDao.class);
    
    
	/**
	 *实例化HIS webService接口
	 */
	private WebInterfaceSoap getWebInterfaceSoap()
	{
		WebInterface service = new WebInterface();
		WebInterfaceSoap soap = service.getWebInterfaceSoap();
		return soap;
	}
	
    //============================================基础数据同步  begin=================================================================
	/**
     * 获取手术医生列表
     * 
     */
    @Transactional
    public void syncHisOperDoctor(){
        logger.info("-------start syncHisOperDoctor-----------");
        
        String beid = basBusEntityDao.getBeid();
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("operat_doctor_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
			respMsg = getWebInterfaceSoap().sendEmr("OPERATION_DOCTOR", sendXml);
        	
            // 调用his接口获取手术名称信息
            logger.info("============syncHisOperDoctor响应报文报文：  " + respMsg);
            if (StringUtils.isNotBlank(respMsg))
            {
            	// 调用his接口获取科室信息
				JAXBContext context = JAXBContext
						.newInstance(HisOutputMessage.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				response = (HisOutputMessage) unmarshaller
						.unmarshal(new StringReader(respMsg));
	
				if (null != response) {
					if (!"T".equals(response.getResult().getStatus())) {
						throw new RuntimeException(response.getResult().getMSG());
					}
				}
                
				if (null != response.getRows()
						&& null != response.getRows().getRow()) {
                    List<Row> doctorList = response.getRows().getRow();
                    if (null != doctorList && doctorList.size() > 0)
                    {
                        for (Row row : doctorList)
                        {
                            
                            String code = row.getCode();
                            String name = row.getName();
                            Integer region = StringUtils.isNotBlank(row.getRegionId()) ? Integer.valueOf(row.getRegionId()) : null; 
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
                            basDict = basDictItemDao.getListByGroupIdandCodeName("operat_doctor_times", "时间戳序列", beid);
    	                    if(null!=basDict){
    	                    	basDict.setCodeValue(row.getTimetemp());
    	                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
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
     * 获取科室列表
     * 
     */
    @Transactional
    public void synHisDeptRoomList(){
        logger.info("-------start synHisDeptRoomList-----------");
        String beid = basBusEntityDao.getBeid();
        String respMsg = "";
        HisOutputMessage response = null;
		try 
		{
			BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("dept_room_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
			respMsg = getWebInterfaceSoap().sendEmr("DEPT_ROOM", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
				// 调用his接口获取科室信息
				JAXBContext context = JAXBContext
						.newInstance(HisOutputMessage.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				response = (HisOutputMessage) unmarshaller
						.unmarshal(new StringReader(respMsg));
	
				if (null != response) {
					if (!"T".equals(response.getResult().getStatus())) {
						throw new RuntimeException(response.getResult().getMSG());
					}
				}
	
				if (null != response.getRows()
						&& null != response.getRows().getRow()) {
					List<Row> deptList = response.getRows().getRow();
					if (null != deptList && deptList.size() > 0) {
						for (Row row : deptList) {
							String code = row.getDeptId();
							String name = row.getName();
							String pinyin = PingYinUtil.getFirstSpell(name);
							BasDept dt = basDeptDao.searchDeptById(code, beid);
	
							if (dt != null && !Objects.equal(dt.getName(), name)) {
								dt.setName(name);
								dt.setPinYin(pinyin);
								basDeptDao.updateByPrimaryKey(dt);
							}
	
							if (null == dt) {
								BasDept dept = new BasDept();
								dept.setDeptId(code);
								dept.setName(name);
								dept.setEnable(1);
								dept.setPinYin(pinyin);
								dept.setBeid(beid);
								basDeptDao.insert(dept);
							}
							
							basDict = basDictItemDao.getListByGroupIdandCodeName("dept_room_times", "时间戳序列", beid);
		                    if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
		                    }
						}
					}
				}
            }
		}
		catch (Exception e) 
		{
			logger.info("获取科室字典值异常:" + Exceptions.getStackTraceAsString(e));
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
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {  
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("operation_name_time", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("OPERATION_NAME",  sendXml);
        	//													logger.info("----------------1"+respMsg+"2-------------------");
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取手术名称信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> opernameList = response.getRows().getRow();
	                if (null != opernameList && opernameList.size() > 0)
	                {
	                    for (Row row : opernameList)
	                    {
	                        String code = row.getCode();
	                        String name = row.getName();
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
	                        
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("operation_name_time", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
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
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("diag_name_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("DIAG_NAME", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
				//logger.info("--------------"+respMsg+"-----------------");
	            // 调用his接口获取手术名称信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> diagnosedefList = response.getRows().getRow();
	                if (null != diagnosedefList && diagnosedefList.size() > 0)
	                {
	                    for (Row row : diagnosedefList)
	                    {
	                        String code = row.getCode();
	                        String name = row.getName();
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
	                        
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("diag_name_times", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
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
	 * 获取病区列表
	 * 视图：VIEW_CHARGE_ITEM
	 */
	@Transactional
	public void synHisRegionList(){
		logger.info("-------start synHisRegionList-----------");
        HisOutputMessage response = null;
        String respMsg = "";
        String beid = basBusEntityDao.getBeid();
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("region_room_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("REGION_ROOM", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取手术名称信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> regionList = response.getRows().getRow();
	                if (null != regionList && regionList.size() > 0)
	                {
	                    for (Row row : regionList)
	                    {
	                        String regionId = row.getRegionId();
	                        String name = row.getName();
	                        String pinyin = PingYinUtil.getFirstSpell(name);
	                        String enable = row.getEnable();
	                        List<BasRegion> basRegionList = basRegionDao.selectByCode(regionId, basBusEntityDao.getBeid());
	                        if (basRegionList != null && basRegionList.size()>0 && !Objects.equal(basRegionList.get(0).getName(), name))
	                        {
	                        	BasRegion basRegion = basRegionList.get(0);
	                        	basRegion.setEnable(Integer.parseInt(enable));
	                        	basRegion.setName(name);
	                        	basRegion.setPinYin(pinyin);
                                basRegionDao.updateByPrimaryKeySelective(basRegion);
	                        }
	                        if (null == basRegionList || basRegionList.size()<1)
	                        {
	                        	BasRegion basRegion = new BasRegion();
	                        	basRegion.setRegionId(regionId);
	                            basRegion.setEnable(Integer.parseInt(enable));
	                        	basRegion.setName(name);
	                        	basRegion.setPinYin(pinyin);
	                        	basRegion.setBeid(beid);
	                        	basRegionDao.insert(basRegion);
	                        }
	                        
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("region_room_times", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
		                    }
	                    }
	                }
	            }
            }
        }
        catch (Exception e)
        {
            logger.info("获取病区基础数据异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
		logger.info("-------end synHisRegionList-----------");
	}
    
    
    /**
	 * 获取收费项目列表
	 * 视图：VIEW_CHARGE_ITEM
	 */
	@Transactional
	public void synHisChargeItemList(){
		logger.info("-------start synHisChargeItemList-----------");
		
        HisOutputMessage response = null;
        String respMsg = "";
        String beid = basBusEntityDao.getBeid();
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("charge_item_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("CHARGE_ITEM", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取手术名称信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> chargeItemList = response.getRows().getRow();
	                if (null != chargeItemList && chargeItemList.size() > 0)
	                {
	                    for (Row row : chargeItemList)
	                    {
	                        String code = row.getChargeItemCode();
	                        String name = row.getChargeItemName();
	                        String pinyin = PingYinUtil.getFirstSpell(name);
	                        String spec = row.getSpec();
	                        String unit = row.getUnit();
	                        String price = row.getPrice();
	                        String enable = row.getEnable();
	                        String chargeType = row.getChargeType();
	                        String basicUnitAmout = row.getBasicUnitAmout();
	                        String type = row.getType();
	                        
	                        List<BasChargeItem> itemList = basChargeItemDao.selectByCode(code, basBusEntityDao.getBeid());
	                        
	                        if (itemList != null && itemList.size()>0)
	                        {
	                        	BasChargeItem chargeItem = itemList.get(0);
	                        	chargeItem.setChargeItemName(name);
                                chargeItem.setSpec(spec);
                                chargeItem.setPinYin(pinyin);
                                chargeItem.setUnit(unit);
                                chargeItem.setBasicUnitAmount(StringUtils.isBlank(basicUnitAmout)?0.0f:Float.valueOf(basicUnitAmout));
                                chargeItem.setPrice(Float.valueOf(price));
                                chargeItem.setType(type);
                                chargeItem.setChargeType(chargeType);
                                chargeItem.setEnable(Integer.parseInt(enable));
                                basChargeItemDao.updateByPrimaryKeySelective(chargeItem);
	                        }
	                        if (null == itemList || itemList.size()<1)
	                        {
	                        	
	                        	BasChargeItem chargeItem = new BasChargeItem();
	                            chargeItem.setChargeItemId(GenerateSequenceUtil.generateSequenceNo());
	                            chargeItem.setChargeItemCode(code);
	                            chargeItem.setChargeItemName(name);
	                            chargeItem.setSpec(spec);
	                            chargeItem.setPinYin(pinyin);
	                            chargeItem.setUnit(unit);
	                            chargeItem.setBasicUnitAmount(StringUtils.isBlank(basicUnitAmout)?0.0f:Float.valueOf(basicUnitAmout));
	                            chargeItem.setPrice(Float.valueOf(price));
	                            chargeItem.setType(type);
	                            chargeItem.setChargeType(chargeType);
	                            chargeItem.setEnable(Integer.parseInt(enable));
	                            chargeItem.setBeid(beid);
	                            basChargeItemDao.insert(chargeItem);
	                        }
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("charge_item_times", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
		                    }
	                    }
	                }
	            }
            }
        }
        catch (Exception e)
        {
            logger.info("获取收费项目异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
		logger.info("-------end synHisChargeItemList-----------");
	}
	
	
	/**
	 * 获取收费项目列表
	 * 视图：VIEW_CHARGE_ITEM
	 */
	@Transactional
	public void synHisDeleteChargeItemList(){
		logger.info("-------start synHisDeleteChargeItemList-----------");
		String beid = basBusEntityDao.getBeid();
        basChargeItemDao.updateEnable(beid);
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("charge_item_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>0</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("CHARGE_ITEM", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取手术名称信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> chargeItemList = response.getRows().getRow();
	                if (null != chargeItemList && chargeItemList.size() > 0)
	                {
	                    for (Row row : chargeItemList)
	                    {
	                        String code = row.getChargeItemCode();
	                        String name = row.getChargeItemName();
	                        String pinyin = PingYinUtil.getFirstSpell(name);
	                        String spec = row.getSpec();
	                        String unit = row.getUnit();
	                        String price = row.getPrice();
	                        String enable = row.getEnable();
	                        String chargeType = row.getChargeType();
	                        String basicUnitAmout = row.getBasicUnitAmout();
	                        String type = row.getType();
	                        
	                        List<BasChargeItem> itemList = basChargeItemDao.selectByCode(code, basBusEntityDao.getBeid());
	                        
	                        if (itemList != null && itemList.size()>0)
	                        {
	                        	BasChargeItem chargeItem = itemList.get(0);
	                        	chargeItem.setChargeItemName(name);
                                chargeItem.setSpec(spec);
                                chargeItem.setPinYin(pinyin);
                                chargeItem.setUnit(unit);
                                chargeItem.setBasicUnitAmount(StringUtils.isBlank(basicUnitAmout)?0.0f:Float.valueOf(basicUnitAmout));
                                chargeItem.setPrice(Float.valueOf(price));
                                chargeItem.setType(type); 
                                chargeItem.setChargeType(chargeType);
                                chargeItem.setEnable(Integer.parseInt(enable));
                                basChargeItemDao.updateByPrimaryKeySelective(chargeItem);
	                        }
	                        if (null == itemList || itemList.size()<1)
	                        {
	                        	
	                        	BasChargeItem chargeItem = new BasChargeItem();
	                            chargeItem.setChargeItemId(GenerateSequenceUtil.generateSequenceNo());
	                            chargeItem.setChargeItemCode(code);
	                            chargeItem.setChargeItemName(name);
	                            chargeItem.setSpec(spec);
	                            chargeItem.setPinYin(pinyin);
	                            chargeItem.setUnit(unit);
	                            chargeItem.setBasicUnitAmount(StringUtils.isBlank(basicUnitAmout)?0.0f:Float.valueOf(basicUnitAmout));
	                            chargeItem.setPrice(Float.valueOf(price));
	                            chargeItem.setType(type);
	                            chargeItem.setChargeType(chargeType);
	                            chargeItem.setEnable(Integer.parseInt(enable));
	                            chargeItem.setBeid(beid);
	                            basChargeItemDao.insert(chargeItem);
	                        }
	                        /*basDict = basDictItemDao.getListByGroupIdandCodeName("charge_item_times", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
		                    }*/
	                    }
	                }
	            }
            }
        }
        catch (Exception e)
        {
            logger.info("获取收费项目异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
		logger.info("-------end synHisDeleteChargeItemList-----------");
	}
    
    
    /**
     * 获取麻醉方法列表
     * 
     */
    @Transactional
    public void syncHisAnaesMethod(){
        logger.info("-------start syncHisAnaesMethod-----------");
        String beid = basBusEntityDao.getBeid();
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("anaes_method_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("ANAES_METHOD", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取麻醉方法信息
	        	JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> anaesMethodList = response.getRows().getRow();
	                if (null != anaesMethodList && anaesMethodList.size() > 0)
	                {
	                    for (Row row : anaesMethodList)
	                    {
	                        String code = row.getCode();
	                        String name = row.getName();
	                        String pinyin = PingYinUtil.getFirstSpell(name);
	                        //String type = row.getType();
	                        BasAnaesMethod anaesMethod = basAnaesMethodDao.selectByCode(code, basBusEntityDao.getBeid());
	                        
	                        if (anaesMethod != null && (!Objects.equal(anaesMethod.getName(), name)))
	                        {
	                            anaesMethod.setName(name);
	                            anaesMethod.setPinYin(pinyin);
	                            //anaesMethod.setCate1(type);
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
	                           // anaesMethod.setCate1(type);
	                            anaesMethod.setIsLocalAnaes(0);
	                            if ("局麻".equals(anaesMethod.getCate1()))
	                            {
	                                anaesMethod.setIsLocalAnaes(1);
	                            }
	                            anaesMethod.setBeid(beid);
	                            basAnaesMethodDao.insert(anaesMethod);
	                        }
	                        
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("anaes_method_times", "时间戳序列", beid);
	                        if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
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
     * 获取药品字典
     * 
     */
    @Transactional
    public void syncHisMedicine(){
        logger.info("-------start syncHisMedicine-----------");
        String beid = basBusEntityDao.getBeid();
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("drug_store_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("DRUG_STORE", sendXml);
			if (StringUtils.isNotBlank(respMsg))
            {
	            // 调用his接口获取药品信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> medicineList = response.getRows().getRow();
	                if (null != medicineList && medicineList.size() > 0)
	                {
	                    for (Row row : medicineList)
	                    {
	                        String code = row.getCode();
	                        String type = row.getType();
	                        String name = row.getName();
	                        String pinyin = PingYinUtil.getFirstSpell(name);
	                        String spec = row.getSpec();
	                        Integer enable = new Integer(row.getEnable());
	                        Float packageDosageAmount = StringUtils.isNotBlank(row.getPackageDosageAmount()) ? Float.valueOf(row.getPackageDosageAmount()) : null;
	                        String dosageUnit = row.getDosageUnit();
	                        BasMedicine medicine = basMedicineDao.selectByCode(code, basBusEntityDao.getBeid());
	                        
	                        if (medicine != null
	                            && (!Objects.equal(medicine.getName(), name)
	                                || !Objects.equal(medicine.getType(), type)
	                                || !Objects.equal(medicine.getSpec(), spec)
	                                || !Objects.equal(medicine.getDosageUnit(), dosageUnit)
	                                || !Objects.equal(medicine.getPackageDosageAmount(), packageDosageAmount)
	                                || !Objects.equal(medicine.getEnable(), enable)))
	                        {
	                            medicine.setName(name);
	                            medicine.setPinYin(pinyin);
	                            medicine.setType(type);
	                            medicine.setSpec(spec);
	                            medicine.setDosageUnit(dosageUnit);
	                            medicine.setPackageDosageAmount(packageDosageAmount);
	                            medicine.setEnable(enable);
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
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("drug_store_times", "时间戳序列", beid);
		                    if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
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
        logger.info("-------end syncHisMedicine-----------");
    }
    
    
    
    /**
     * 获取药品价格
     * 
     */
    @Transactional
    public void syncHisMedicinePrice(){
        logger.info("-------start syncHisMedicinePrice-----------");
        String beid = basBusEntityDao.getBeid();
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("medicine_price_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
        	respMsg = getWebInterfaceSoap().sendEmr("MEDICINE_PRICE", sendXml);
			if (StringUtils.isNotBlank(respMsg))
	        {
	            //调用his接口获取药品价格信息
	            JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
	            
	            if (null != response)
	            {
	                if (!"T".equals(response.getResult().getStatus()))
	                {
	                    throw new RuntimeException(response.getResult().getMSG());
	                }
	            }
	            if (null != response.getRows() && null != response.getRows().getRow())
	            {
	                List<Row> priceList = response.getRows().getRow();
	                if (null != priceList && priceList.size() > 0)
	                {
	                    for (Row row : priceList)
	                    {
	                        String code = row.getCode();
	                        String spec = row.getSpec();
	                        String firm = row.getFirm();
	                        String firmId = row.getFirmId();
	                        String minPackageUnit = row.getMinPackageUnit();
	                        Float price = StringUtils.isNotBlank(row.getPriceMinPackage()) ? Float.valueOf(row.getPriceMinPackage()) : null;
	                        Integer enable = StringUtils.isBlank(row.getEnable())?null:new Integer(row.getEnable());
	                        BasPrice basPrice = basPriceDao.selectByCode(code, beid);
	                        
	                        if (null != basPrice 
	                            && (!Objects.equal(basPrice.getSpec(), spec)
	                                || !Objects.equal(basPrice.getFirm(), firm)
	                                || !Objects.equal(basPrice.getFirmId(), firmId)
	                                || !Objects.equal(basPrice.getMinPackageUnit(), minPackageUnit) 
	                                || !Objects.equal(basPrice.getPriceMinPackage(), price)
	                                || !Objects.equal(basPrice.getEnable(), enable)
	                            	))
	                        {
	                            basPrice.setSpec(spec);
	                            basPrice.setFirm(firm);
	                            basPrice.setFirmId(firmId);
	                            basPrice.setMinPackageUnit(minPackageUnit);
	                            basPrice.setPriceMinPackage(price);
	                            basPrice.setEnable(enable);
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
	                            basPrice.setFirmId(firmId);
	                            basPrice.setMinPackageUnit(minPackageUnit);
	                            basPrice.setPriceMinPackage(price);
	                            basPriceDao.insert(basPrice);
	                        }
	                        
	                        basDict = basDictItemDao.getListByGroupIdandCodeName("medicine_price_times", "时间戳序列", beid);
		                    if(null!=basDict){
		                    	basDict.setCodeValue(row.getTimetemp());
		                    	basDictItemDao.updateByPrimaryKeySelective(basDict);
		                    }
	                    }
	                }
	            }
            }
        }
        catch (Exception e)
        {
            logger.info("获取药品价格异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        logger.info("-------end syncHisMedicinePrice-----------");
    }

    /**
     * 获取手术通知单
     * 
     */
    @Transactional
    public void getHisOperateNotice()
    {
        logger.info("---------------------begin getHisOperateNotice------------------------");
        HisOutputMessage response = null;
        String respMsg = "";
        String beid = basBusEntityDao.getBeid();
        try
        {
        	BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("operate_notice_times", "时间戳序列", beid);
        	String timestamp = ""; 
            if(null!=basDict){
            	timestamp = basDict.getCodeValue();
            }
            String sendXml = "<HIS><Request><timestamp>"+timestamp+"</timestamp></Request></HIS>";
            
            logger.info("---------------------getHisOperateNotice-------sendXml="+sendXml);
            
        	respMsg = getWebInterfaceSoap().sendEmr("OPERATION_QUERY", sendXml);
			if (StringUtils.isNotBlank(respMsg))
	        {
            // 调用his接口获取手术名称信息
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
                
                logger.info("---------------------getHisOperateNotice-------response="+getObjectToXml(response));
                
                if (null != response)
                {
                    if (!"T".equals(response.getResult().getStatus()))
                    {
                        throw new RuntimeException(response.getResult().getMSG());
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.info("获取手术通知单异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        if (null != response.getRows() && null != response.getRows().getRow())
        {
            List<Row> results = response.getRows().getRow();
            
            if (null != results && results.size() > 0)
            {
            	
            	logger.info("---------------------getHisOperateNotice-------results.size()="+results.size());
            	
                for (Row row : results)
                {
                    BasRegOpt regOpt = basRegOptDao.searchRegOptByReservenumber(row.getReservenumber(), beid);

                    //手术信息不存在，直接存入到手麻系统
                    if (null == regOpt)
                    {
                    	
                    	//logger.info("-----------1"+row.toString()+"2--------------");
                        regOpt = new BasRegOpt();
                        regOpt.setRegOptId(GenerateSequenceUtil.generateSequenceNo());
                        regOpt.setBeid(beid);
                        setRegOpt(regOpt, row, beid);
                        regOpt.setState(OperationState.NO_SCHEDULING);
                        regOpt.setRegOptId(regOpt.getRegOptId());
                        regOpt.setCostsettlementState("0");
                        regOpt.setOutMedicine(0);
                        regOpt.setOutInstrument(0);
                        basRegOptDao.insert(regOpt);
                        
//                        Controller controller = new Controller();
//                        controller.setRegOptId(regOpt.getRegOptId());
//                        controller.setCostsettlementState("0");
                        
                        //直接审核通过
                        creatDocument(regOpt);
                    }
                    //手术信息存在，但是申请单号不同，说明his那边对该手术做过修改，需要更新手术信息
                    else
                    {
                    	logger.info("-------getHisOperateNotice--同步手术通知信息时修改RegOpt表信息----");
                        setRegOpt(regOpt, row, beid);
                        basRegOptDao.updateByPrimaryKeySelective(regOpt);
                    }
                    BasDictItem basDict = basDictItemDao.getListByGroupIdandCodeName("operate_notice_times", "时间戳序列", beid);
                    if(null!=basDict){
                    	basDict.setCodeValue(row.getTimetemp());
                    	basDictItemDao.updateByPrimaryKey(basDict);
                    }
                    
                }
            }
        }
    }

    private void setRegOpt(BasRegOpt regOpt, Row row, String beid)
    {

    	BeanHelper.copyProperties(row, regOpt);
    	regOpt.setPreengagementnumber(row.getReservenumber());
    	/*regOpt.setDiagnosisCode(row.getDiagCode());
    	regOpt.setDiagnosisName(row.getDiagName());*/
    	regOpt.setOperaDate(row.getOperDate());
    	regOpt.setStartTime(row.getOperStartTime());
    	regOpt.setCreateTime(DateUtils.getDateTime());
        //regOpt.setOptLevel(row.getOperLevel());
        
        
        //手术等级
        if("Ⅰ级".equals(row.getOperLevel()) || "1".equals(row.getOperLevel()) || "一级".equals(row.getOperLevel()) || "一".equals(row.getOperLevel()))
        {
            regOpt.setOptLevel("一级");
        }
        else if("Ⅱ级".equals(row.getOperLevel()) || "2".equals(row.getOperLevel()) || "二级".equals(row.getOperLevel()) || "二".equals(row.getOperLevel()))
        {
            regOpt.setOptLevel("二级");
        }
        else if("Ⅲ级".equals(row.getOperLevel()) || "3".equals(row.getOperLevel()) || "三级".equals(row.getOperLevel()) || "三".equals(row.getOperLevel()))
        {
            regOpt.setOptLevel("三级");
        }
        else if("Ⅳ级".equals(row.getOperLevel()) || "4".equals(row.getOperLevel()) || "四级".equals(row.getOperLevel()) || "四".equals(row.getOperLevel()))
        {
            regOpt.setOptLevel("四级");
        }
        

        //regOpt.setRegionId(row.getRegionId().trim());
        
        //logger.info("-------------"+row.toString()+"-------------");
        //术前诊断
        logger.info("------row.getDiagCode():"+row.getDiagCode()+"------");
        if (StringUtils.isNotBlank(row.getDiagCode()))
        {
            List<String> diagnoseIds = new ArrayList<String>();
            List<String> diagnoseNames = new ArrayList<String>();
            String[] diagnoses = row.getDiagCode().split(",");
            logger.info("------diagnoses:"+diagnoses+"------");
            for (String s : diagnoses)
            {
            	
                BasDiagnosedef diagnosedefs = basDiagnosedefDao.selectByCode(s, beid);
                if (null != diagnosedefs)
                {
                	logger.info("------diagnosedefs.get(0:"+diagnosedefs.getDiagDefId()+"------");
                	logger.info("------s:"+s+"------");
                    diagnoseIds.add(diagnosedefs.getDiagDefId());
                    diagnoseNames.add(diagnosedefs.getName());
                }
            }
            logger.info("------diagnoseIds:"+diagnoseIds+"------");
            logger.info("------diagnoseNames:"+diagnoseNames+"------");
            regOpt.setDiagnosisCode(StringUtils.getStringByList(diagnoseIds));
            regOpt.setDiagnosisName(StringUtils.getStringByList(diagnoseNames));
        }
        
        //切口等级
        if(StringUtils.isNotBlank(row.getIncisionLevel())){
        	regOpt.setCutLevel(new Integer(row.getIncisionLevel()));
        }
        
        if(StringUtils.isNotBlank(row.getOpeSource())){
            regOpt.setOperSource(new Integer(row.getOpeSource()));
        }

        if(!StringUtils.isEmpty(row.getOperType())){
            regOpt.setEmergency(Integer.parseInt(row.getOperType().trim()));
        }
        
        //手术医生
        if (StringUtils.isNotBlank(row.getSurgeryDoctorId()))
        {
            BasOperationPeople bopList = basOperationPeopleDao.selectByCode(row.getSurgeryDoctorId(), beid);
            regOpt.setOperatorName(row.getSurgeryDoctorName());
            regOpt.setOperatorId((null != bopList ) ? bopList.getOperatorId() : null);
        }
        
        //助手医生处理
        regOpt.setAssistantId("");
        regOpt.setAssistantName("");
        //助手医生id是逗号分割的
        String assistants = row.getAssistantId();
        if(null != assistants && StringUtils.isNotBlank(assistants)){
            String[] assistantsList = assistants.split(",");
            if(null != assistantsList && assistantsList.length>0){
                for (String code : assistantsList) {
                	BasOperationPeople operPer = basOperationPeopleDao.selectByCode(code, basBusEntityDao.getBeid());
                    regOpt.setAssistantId( regOpt.getAssistantId()+((operPer != null)?operPer.getOperatorId()+",":""));
                    regOpt.setAssistantName(regOpt.getAssistantName()+((operPer != null)?operPer.getName()+",":""));
                }
            }
        }
        //助手医生code和name去掉最后一个逗号
        String assistantId = regOpt.getAssistantId();
        if(null != assistantId && StringUtils.isNotBlank(assistantId)){
            assistantId = assistantId.substring(0, assistantId.length()-1);
            regOpt.setAssistantId(assistantId);
        }
        String assistantName = regOpt.getAssistantName();
        if(null != assistantName && StringUtils.isNotBlank(assistantName)){
            assistantName = assistantName.substring(0, assistantName.length()-1);
            regOpt.setAssistantName(assistantName);
        }
        
        
        //麻醉方法
        String designedAnaesMethodName = row.getAnaesName();
        String designedAnaesMethodCode = "";
        if(null != designedAnaesMethodName && StringUtils.isNotBlank(designedAnaesMethodName)){
            String[] designedAnaesMethodNameList = designedAnaesMethodName.split(",");
            if(null != designedAnaesMethodNameList && designedAnaesMethodNameList.length>0){
                for (String name : designedAnaesMethodNameList) {
                    List<BasAnaesMethod> anaesMethodList = basAnaesMethodDao.selectByName(name, basBusEntityDao.getBeid());
                    //这里his给到的麻醉方法是code，我们用到的是id所以在保存的时候需要做下转换
                    if(null!=anaesMethodList && anaesMethodList.size()>0){
                        designedAnaesMethodCode +=anaesMethodList.get(0).getAnaMedId()+",";
                    }
                }
            }
        }
        // 麻醉方法code去掉最后一个逗号
        if(null != designedAnaesMethodCode && StringUtils.isNotBlank(designedAnaesMethodCode)){
            designedAnaesMethodCode = designedAnaesMethodCode.substring(0, designedAnaesMethodCode.length()-1);
            regOpt.setDesignedAnaesMethodCode(designedAnaesMethodCode);
        }
        regOpt.setDesignedAnaesMethodName(designedAnaesMethodName);
        
        //全麻局麻控制
		BasRegOptUtils.IsLocalAnaesSet(regOpt);
        
        //手术名称
		if(StringUtils.isNotBlank(row.getOperName())){
        	
        	String operName = row.getOperName();
        	String operCode = "";
        	
    		List<BasOperdef> operdefLs = basOperdefDao.selectByName(operName, beid);
    		if(null == operdefLs || operdefLs.size() == 0){
    			BasOperdef operdef = new BasOperdef();
                operdef.setOperdefId(GenerateSequenceUtil.generateSequenceNo());
                operdef.setCode(row.getOperCode());
                operdef.setName(operName);
                operdef.setEnable(1);
                operdef.setPinYin(PingYinUtil.getFirstSpell(operName));
                operdef.setBeid(beid);
                basOperdefDao.insert(operdef);
                
                operCode = operdef.getOperdefId();
    		}else{
    			BasOperdef operdef = operdefLs.get(0);
    			operCode = operdef.getOperdefId();
    		}
    		
        	regOpt.setDesignedOptCode(operCode);
            regOpt.setDesignedOptName(operName);
        	
        	
//            List<String> operationIds = new ArrayList<String>();
//            List<String> operationNames = new ArrayList<String>();
//            String[] operationCodes = row.getOperCode().split(",");
//            for (String s : operationCodes)
//            {
//                BasOperdef operdef = basOperdefDao.selectByCode(s, beid);
//                if (null != operdef)
//                {
//                    operationIds.add(operdef.getOperdefId());
//                    operationNames.add(operdef.getName());
//                }
//            }
//            regOpt.setDesignedOptCode(StringUtils.getStringByList(operationIds));
//            regOpt.setDesignedOptName(StringUtils.getStringByList(operationNames));
        }
        regOpt.setIdentityNo(row.getCredNumber());
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
        
        String beid = basBusEntityDao.getBeid();
        
        StringBuffer asXml = new StringBuffer();
        /*asXml.append("<timestamp>").append("").append("</timestamp>");*/
        asXml.append("<reservenumber>").append(regOpt.getPreengagementnumber()).append("</reservenumber>");
        asXml.append("<operRoomId>").append(dispatch.getOperRoomId()).append("</operRoomId>");
        asXml.append("<operRoomName>").append(basOperroomDao.queryRoomListById(dispatch.getOperRoomId(), beid).getName()).append("</operRoomName>");
        asXml.append("<circunurseName1>").append(basUserDao.selectNameByUserName(dispatch.getCircunurseId1(), beid)).append("</circunurseName1>");
        asXml.append("<circunurseName2>").append(basUserDao.selectNameByUserName(dispatch.getCircunurseId2(), beid)).append("</circunurseName2>");
        asXml.append("<instrnurseName1>").append(basUserDao.selectNameByUserName(dispatch.getInstrnurseId1(), beid)).append("</instrnurseName1>");
        asXml.append("<instrnurseName2>").append(basUserDao.selectNameByUserName(dispatch.getInstrnurseId2(), beid)).append("</instrnurseName2>");
        asXml.append("<anesthetistName>").append(basUserDao.selectNameByUserName(dispatch.getAnesthetistId(), beid)).append("</anesthetistName>");
        asXml.append("<startTime>").append(dispatch.getStartTime()).append("</startTime>");
        asXml.append("<pcs>").append(dispatch.getPcs()).append("</pcs>");
        String inBody = "<HIS><Request>" + asXml + "</Request></HIS>";
        HisOutputMessage response = null;
        String respMsg = "";
       /* try
        {
        	respMsg = getWebInterfaceSoap().sendEmr("OPERATION_DOCTORARRANGE", inBody);
			if (StringUtils.isNotBlank(respMsg))
	        {
				// 调用his接口回传手术排班记录
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
                
                if (null != response)
                {
                    if (!"T".equals(response.getResult().getStatus()))
                    {
                        throw new RuntimeException(response.getResult().getMSG());
                    }
                }
            }
        }
        catch (Exception e)
        {
        	logger.info("回传手术排班记录异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }*/
        
        logger.info("---------------------end sendDispatchToHis------------------------");
    }
    
    
    /** 
     * 创建文书
     * <功能详细描述>
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void creatDocument(BasRegOpt regOpt)
    {
    	BasDocumentDao basDocumentDao = SpringContextHolder.getBean(BasDocumentDao.class);
        List<String> tables = basDocumentDao.searchAllTables(basBusEntityDao.getBeid());
        
        if (tables.contains("doc_anaes_postop"))
        {
            DocAnaesPostop docAnaesPostop = new DocAnaesPostop();
            docAnaesPostop.setAnaPostopId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesPostop.setRegOptId(regOpt.getRegOptId());
            docAnaesPostop.setProcessState("NO_END");
            SpringContextHolder.getBean(DocAnaesPostopDao.class).insert(docAnaesPostop);
        }
        
        if (tables.contains("doc_pre_visit"))
        {
            DocPreVisit preVisit = new DocPreVisit();
            preVisit.setPreVisitId(GenerateSequenceUtil.generateSequenceNo());
            preVisit.setRegOptId(regOpt.getRegOptId());
            preVisit.setProcessState("NO_END");
            SpringContextHolder.getBean(DocPreVisitDao.class).insert(preVisit);
        }
        
        if (tables.contains("doc_labor_analgesia_accede"))
        {
            // 创建分娩镇痛同意书
            DocLaborAnalgesiaAccede laborAccede = new DocLaborAnalgesiaAccede();
            laborAccede.setLaborId(GenerateSequenceUtil.generateSequenceNo());
            laborAccede.setRegOptId(regOpt.getRegOptId());
            laborAccede.setProcessState("NO_END");
            SpringContextHolder.getBean(DocLaborAnalgesiaAccedeDao.class).insert(laborAccede);
        }
        
        
        if (tables.contains("doc_accede"))
        {
            // 创建麻醉同意书
            DocAccede accede = new DocAccede();
            accede.setAccedeId(GenerateSequenceUtil.generateSequenceNo());
            accede.setRegOptId(regOpt.getRegOptId());
            accede.setFlag("1");
            accede.setProcessState("NO_END");
            SpringContextHolder.getBean(DocAccedeDao.class).insert(accede);
        }
        if (tables.contains("doc_anaes_medicine_accede"))
        {
            //手术麻醉使用药品知情同意书
            DocAnaesMedicineAccede anaesMedicineAccede = new DocAnaesMedicineAccede();
            anaesMedicineAccede.setRegOptId(regOpt.getRegOptId());
            anaesMedicineAccede.setProcessState("NO_END");
            anaesMedicineAccede.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocAnaesMedicineAccedeDao.class).insert(anaesMedicineAccede);
        }
        if (tables.contains("doc_self_pay_instrument_accede"))
        {
            //手术麻醉使用自费及高价耗材知情同意书
            DocSelfPayInstrumentAccede selfPayInstrumentAccede = new DocSelfPayInstrumentAccede();
            selfPayInstrumentAccede.setRegOptId(regOpt.getRegOptId());
            selfPayInstrumentAccede.setProcessState("NO_END");
            selfPayInstrumentAccede.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocSelfPayInstrumentAccedeDao.class).insert(selfPayInstrumentAccede);
        }
        if (tables.contains("doc_anaes_record"))
        {
            //创建麻醉记录单
            DocAnaesRecord anaesRecord = new DocAnaesRecord();
            anaesRecord.setAnaRecordId(GenerateSequenceUtil.generateSequenceNo());
            anaesRecord.setOther("O2L/min");
            anaesRecord.setProcessState("NO_END");
            anaesRecord.setRegOptId(regOpt.getRegOptId());
            docAnaesRecordDao.insert(anaesRecord);
        }
        
        if (tables.contains("doc_anaes_summary"))
        {
            //麻醉总结单
            DocAnaesSummary anaesSummary = new DocAnaesSummary();
            anaesSummary.setRegOptId(regOpt.getRegOptId());
            anaesSummary.setProcessState("NO_END");
            anaesSummary.setAnaSumId(GenerateSequenceUtil.generateSequenceNo());
            DocAnaesSummaryDao docAnaesSummaryDao = SpringContextHolder.getBean(DocAnaesSummaryDao.class);
            docAnaesSummaryDao.insert(anaesSummary);
            //椎管内麻醉
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
        }
        if (tables.contains("doc_opt_care_record"))
        {
            //创建手术护理记录文书
            DocOptCareRecord optCareRecord = new DocOptCareRecord();
            optCareRecord.setRegOptId(regOpt.getRegOptId());
            optCareRecord.setId(GenerateSequenceUtil.generateSequenceNo());
            optCareRecord.setProcessState("NO_END");
            SpringContextHolder.getBean(DocOptCareRecordDao.class).insert(optCareRecord);
        }
        if (tables.contains("doc_opt_nurse"))
        {
            //创建手术清点记录
            DocOptNurse optNurse = new DocOptNurse();
            optNurse.setRegOptId(regOpt.getRegOptId());
            optNurse.setOptNurseId(GenerateSequenceUtil.generateSequenceNo());
            optNurse.setProcessState("NO_END");
            SpringContextHolder.getBean(DocOptNurseDao.class).insert(optNurse);
        }
        
        if (tables.contains("doc_pre_oper_visit"))
        {
            // 创建术前访视单
            DocPreOperVisit docPreOperVisit = new DocPreOperVisit();
            docPreOperVisit.setId(GenerateSequenceUtil.generateSequenceNo());
            docPreOperVisit.setRegOptId(regOpt.getRegOptId());
            docPreOperVisit.setProcessState("NO_END");
            SpringContextHolder.getBean(DocPreOperVisitDao.class).insert(docPreOperVisit);
        }
        
        if (tables.contains("doc_anaes_plan"))
        {
            //麻醉计划单
            DocAnaesPlan anaesPlan = new DocAnaesPlan();
            anaesPlan.setRegOptId(regOpt.getRegOptId());
            anaesPlan.setProcessState("NO_END");
            anaesPlan.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocAnaesPlanDao.class).insert(anaesPlan);
        }
        
        if (tables.contains("doc_pat_out_range_agree"))
        {
            //医疗保险病人超范围用药同意书
            DocPatOutRangeAgree patOutRangeAgree = new DocPatOutRangeAgree();
            patOutRangeAgree.setRegOptId(regOpt.getRegOptId());
            patOutRangeAgree.setProcessState("NO_END");
            patOutRangeAgree.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocPatOutRangeAgreeDao.class).insert(patOutRangeAgree);
        }
        
        if (tables.contains("doc_pre_post_visit"))
        {
            //手术病人术前术后访问记录单
            DocPrePostVisit prePostVisit = new DocPrePostVisit();
            prePostVisit.setRegOptId(regOpt.getRegOptId());
            prePostVisit.setProcessState("NO_END");
            prePostVisit.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocPrePostVisitDao.class).insert(prePostVisit);
        }
        
        if (tables.contains("doc_pat_shuttle_transfer"))
        {
            //手术患者接送交接单
            DocPatShuttleTransfer patShuttleTransfer = new DocPatShuttleTransfer();
            patShuttleTransfer.setRegOptId(regOpt.getRegOptId());
            patShuttleTransfer.setProcessState("NO_END");
            patShuttleTransfer.setId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocPatShuttleTransferDao.class).insert(patShuttleTransfer);
        }
        
        if (tables.contains("doc_opt_risk_evaluation"))
        {
            //创建手术风险评估单 
            DocOptRiskEvaluation optRiskEvaluatio = new DocOptRiskEvaluation();
            optRiskEvaluatio.setRegOptId(regOpt.getRegOptId());
            optRiskEvaluatio.setOptRiskEvaluationId(GenerateSequenceUtil.generateSequenceNo());
            optRiskEvaluatio.setProcessState("NO_END");
            optRiskEvaluatio.setFlag("1");
            SpringContextHolder.getBean(DocOptRiskEvaluationDao.class).insert(optRiskEvaluatio);
        }
        if (tables.contains("doc_post_follow_record"))
        {
            //术后随访记录单
            DocPostFollowRecord postFollowRecord = new DocPostFollowRecord();
            postFollowRecord.setRegOptId(regOpt.getRegOptId());
            postFollowRecord.setProcessState("NO_END");
            postFollowRecord.setPostFollowId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocPostFollowRecordDao.class).insert(postFollowRecord);
        }
        if (tables.contains("doc_safe_check"))
        {
            //创建手术核查单
            DocSafeCheck safeCheck = new DocSafeCheck();
            safeCheck.setRegOptId(regOpt.getRegOptId());
            safeCheck.setProcessState("NO_END");
            safeCheck.setSafCheckId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocSafeCheckDao.class).insert(safeCheck);
            DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = new DocAnaesBeforeSafeCheck();
            anaesBeforeSafeCheck.setRegOptId(regOpt.getRegOptId());
            anaesBeforeSafeCheck.setAnesBeforeId(GenerateSequenceUtil.generateSequenceNo());
            anaesBeforeSafeCheck.setProcessState("NO_END");
            SpringContextHolder.getBean(DocAnaesBeforeSafeCheckDao.class).insert(anaesBeforeSafeCheck);
            DocOperBeforeSafeCheck operBeforeSafeCheck = new DocOperBeforeSafeCheck();
            operBeforeSafeCheck.setRegOptId(regOpt.getRegOptId());
            operBeforeSafeCheck.setOperBeforeId(GenerateSequenceUtil.generateSequenceNo());
            operBeforeSafeCheck.setProcessState("NO_END");
            SpringContextHolder.getBean(DocOperBeforeSafeCheckDao.class).insert(operBeforeSafeCheck);
            DocExitOperSafeCheck exitOperSafeCheck = new DocExitOperSafeCheck();
            exitOperSafeCheck.setRegOptId(regOpt.getRegOptId());
            exitOperSafeCheck.setProcessState("NO_END");
            exitOperSafeCheck.setExitOperId(GenerateSequenceUtil.generateSequenceNo());
            SpringContextHolder.getBean(DocExitOperSafeCheckDao.class).insert(exitOperSafeCheck);
        }
        
        
        if (tables.contains("doc_anaes_pre_discuss_record"))
        {
            //麻醉科术前讨论记录单
            DocAnaesPreDiscussRecord docAnaesPreDiscussRecord = new DocAnaesPreDiscussRecord();
            docAnaesPreDiscussRecord.setRegOptId(regOpt.getRegOptId());
            docAnaesPreDiscussRecord.setProcessState("NO_END");
            docAnaesPreDiscussRecord.setPreDiscussId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesPreDiscussRecordDao.insert(docAnaesPreDiscussRecord);
        }
        
        DocAnaesQualityControl docAnaesQualityControl = new DocAnaesQualityControl();
        docAnaesQualityControl.setRegOptId(regOpt.getRegOptId());
        docAnaesQualityControl.setId(GenerateSequenceUtil.generateSequenceNo());
        docAnaesQualityControlDao.insert(docAnaesQualityControl);
        
        //在审核的时候  生成排程信息记录 
        int dispatchCount = basDispatchDao.searchDistchByRegOptId(regOpt.getRegOptId());
        if(dispatchCount<1){
            BasDispatch dispatch = new BasDispatch();
            dispatch.setRegOptId(regOpt.getRegOptId());
            dispatch.setBeid(basBusEntityDao.getBeid());
            if(StringUtils.isNotBlank(regOpt.getStartTime())){
            	dispatch.setStartTime(regOpt.getStartTime());
            }
            basDispatchDao.insert(dispatch);
        }
    }
    
    
    /** 
     * 手术状态同步
     * <功能详细描述>
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void sendOperateStateToHis(BasRegOpt regOpt)
    {
        logger.info("---------------------begin sendOperateStateToHis------------------------");
        
        StringBuffer asXml = new StringBuffer();
        asXml.append("<timestamp>").append("").append("</timestamp>");
        asXml.append("<reservenumber>").append(regOpt.getPreengagementnumber()).append("</reservenumber>");
        asXml.append("<state>").append(regOpt.getState()).append("</state>");
        String inBody = "<HIS><Request>" + asXml + "</Request></HIS>";
        HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	respMsg = getWebInterfaceSoap().sendEmr("OPERATION_DOCTORARRANGE", inBody);
			if (StringUtils.isNotBlank(respMsg))
	        {
				// 调用his接口回传手术排班记录
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
                
                if (null != response)
                {
                    if (!"T".equals(response.getResult().getStatus()))
                    {
                        throw new RuntimeException(response.getResult().getMSG());
                    }
                }
            }
        }
        catch (Exception e)
        {
        	logger.info("回传手术状态HIS异常:" + Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("---------------------end sendOperateStateToHis------------------------");
    }
    
    
    
    
    
    
	/**
	 * 手术费用同步HIS
	 * @param formbean 同步费用明细数据
	 * @param regOptId 
	 * @param chargeType 费用类型：药品、单项收费
	 * @param chargeWay 费用来源 手术费用、病房收费
	 * @param costType 护理费用  麻醉费用
	 * @param resp
	 */
    @Transactional
    public void sendOptCostInteface(HisOptcostFormBean formbean,String regOptId,String costType,ResponseValue resp)throws Exception
	{
		logger.info("sendOptCostInteface---开始执行！");
		String name = "";
		BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(regOptId);
		if(null!=basRegOpt){
			name = basRegOpt.getName();
			logger.info("sendOptCostInteface---患者姓名："+name+",收费类型为手术费用正在同步!!!");
		}
		HisOutputMessage response = null;
        String respMsg = "";
        try
        {
        	logger.info("----------enter:"+getObjectToXml(formbean)+"--------------");
        	respMsg = getWebInterfaceSoap().sendEmr("OPERATION_SSJZ", getObjectToXml(formbean));
			if (StringUtils.isNotBlank(respMsg))
	        {
				// 调用his接口回传手术排班记录
                JAXBContext context = JAXBContext.newInstance(HisOutputMessage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (HisOutputMessage)unmarshaller.unmarshal(new StringReader(respMsg));
                
                if (null != response)
                {
                	logger.info("----------out:"+respMsg+"--------------");
                	logger.info("----------out:"+respMsg+"--------------");
                    if (!"T".equals(response.getResult().getStatus()))
                    {
                    	logger.info("------------MSG="+response.getResult().getMSG());
                        throw new RuntimeException(response.getResult().getMSG());
                    }else{
                    	List<String> succList = new ArrayList<String>();
				        List<String> failList = new ArrayList<String>();
						if (null != response && null != response.getResult()
								&& response.getResult().getStatus().equals("T")) {
							List<Row> results = response.getRows().getRow();
							if (null != results && results.size() > 0) {
								List<Row> succPktIdList = new ArrayList<Row>();
								for (Row result : results) {
									if ("T".equals(result.getRet())) {
										succList.add(result.getOper_id());
										Row succRow = new Row();
										succRow.setOper_id(result.getOper_id());
										succRow.setIdm(result.getIdm());
										succPktIdList.add(succRow);
									} else {
										 //failList.add(result.getOper_id() +":" + result.getResultMessage());
										failList.add(result.getOper_id());
									}
								}
								if (null != succPktIdList
										&& succPktIdList.size() > 0) {
									for (Row succRow : succPktIdList) {
										if ("1".equals(succRow.getIdm())) {
											docPackagesItemDao
											.updateChargeState(succRow
													.getOper_id());
											
										} else {
											docEventBillingDao
											.updateChargeState(succRow
													.getOper_id());
										}
									}
								}
							}
			             	/**
			             	 * 修改收费标志
			             	 */
		             		BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		             		if(null!=regOpt){
		             			Integer payState = basRegOptDao.queryPayStatusByRegOptId(costType, regOptId);
		             			Boolean changeFlag = false; 
		             			if("1".equals(costType)){//麻醉费用单收费标志
		             				if(!java.util.Objects.equals(payState, regOpt.getDocPayState())){
		             					changeFlag = true;
		             				}
		             				regOpt.setDocPayState(payState);
		             			}else{//手术核算单收费标志
		             				if(!java.util.Objects.equals(payState, regOpt.getNurPayState())){
		             					changeFlag = true;
		             				}
		             				regOpt.setNurPayState(payState);
		             			}
		             			if(changeFlag){
		             				basRegOptDao.updatePayState(regOpt);
		             			}
		             		}
				        }
			            resp.put("succList", StringUtils.getStringByList(succList));
			            resp.put("failList", StringUtils.getStringByList(failList));
                    }
                }else{
                	logger.info("sendOptCostInteface请求接口时HIS端无响应,请检查HIS系统接口是否正常！");
					throw new RuntimeException("sendOptCostInteface请求接口时HIS端无响应,请检查HIS系统接口是否正常！");
                }
            }
        }
        catch (Exception e)
        {
        	logger.info("sendOptCostInteface---患者姓名："+name+"手术费用同步时出现异常:"+Exceptions.getStackTraceAsString(e));
			throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("sendOptCostInteface---患者姓名："+name+"的手术费用同步完成!!!");
		
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
    
    public static void main(String[] args)throws Exception {
//    	
//    	HisOutputMessage msg = new HisOutputMessage();
//    	
//    	
//    	Data rows = new Data();
//    	
////    	Rows rows = new Rows();
////    	
//    	List<Row> rowxx = new ArrayList<Row>();
////    	
//    	Row row2 = new Row();
//    	row2.setAbnormalIndicator("111");
//    	rowxx.add(row2);
//    	
//    	
//    	Row row1 = new Row();
//    	row1.setAbnormalIndicator("222");
//    	rowxx.add(row1);
//    	
//    	rows.setRow(rowxx);
////    	data.setRows(rows);
//    	msg.setRows(rows);
//    	
//    	Result result = new Result();
//    	result.setMSG("成功");
//    	result.setStatus("T");
//    	msg.setResult(result);
//    	
//    	
//    	System.out.println(getObjectToXml(msg));
    }
    
}


