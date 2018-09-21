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

import com.digihealth.anesthesia.basedata.dao.BasAnaesMethodDao;
import com.digihealth.anesthesia.basedata.dao.BasBusEntityDao;
import com.digihealth.anesthesia.basedata.dao.BasDiagnosedefDao;
import com.digihealth.anesthesia.basedata.dao.BasDispatchDao;
import com.digihealth.anesthesia.basedata.dao.BasOperationPeopleDao;
import com.digihealth.anesthesia.basedata.dao.BasOperdefDao;
import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.formbean.OperDefFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.utils.BeanHelper;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.dao.DocAnaesRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptCareRecordDao;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.evt.dao.EvtOptRealOperDao;
import com.digihealth.anesthesia.evt.dao.EvtRealAnaesMethodDao;
import com.digihealth.anesthesia.evt.formbean.EvtAnaesMethodFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.interfaceParameters.yxrm.tempuri.Operation;
import com.digihealth.anesthesia.interfaceParameters.yxrm.tempuri.OperationSoap;
import com.digihealth.anesthesia.interfacedata.formbean.HisResponse;
import com.digihealth.anesthesia.interfacedata.formbean.yxrm.EmergencyOperFormbean;
import com.digihealth.anesthesia.interfacedata.po.yxrm.HisDispatch;
import com.digihealth.anesthesia.interfacedata.po.yxrm.HisDispatchFormbean;
import com.digihealth.anesthesia.interfacedata.po.yxrm.HisRegOpt;
import com.digihealth.anesthesia.interfacedata.po.yxrm.OperList;
import com.digihealth.anesthesia.interfacedata.po.yxrm.StateObj;
import com.google.common.base.Objects;

/**
 * 永兴人民医院HIS接口处理类
 */
@Service
public class HisInterfaceServiceYXRM 
{
	
	/**
     * 日志对象
    */
    protected Logger logger = LoggerFactory.getLogger(getClass());

	private BasBusEntityDao basBusEntityDao = SpringContextHolder.getBean(BasBusEntityDao.class);
	private BasOperdefDao basOperdefDao = SpringContextHolder.getBean(BasOperdefDao.class);
	private BasDiagnosedefDao basDiagnosedefDao = SpringContextHolder.getBean(BasDiagnosedefDao.class);
	private BasOperationPeopleDao basOperationPeopleDao = SpringContextHolder.getBean(BasOperationPeopleDao.class);
	private BasAnaesMethodDao basAnaesMethodDao = SpringContextHolder.getBean(BasAnaesMethodDao.class);
	private BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
	private BasDispatchDao basDispatchDao = SpringContextHolder.getBean(BasDispatchDao.class);
	private DocAnaesRecordDao docAnaesRecordDao = SpringContextHolder.getBean(DocAnaesRecordDao.class);
	private EvtRealAnaesMethodDao evtRealAnaesMethodDao = SpringContextHolder.getBean(EvtRealAnaesMethodDao.class);
	private EvtOptRealOperDao evtOptRealOperDao =  SpringContextHolder.getBean(EvtOptRealOperDao.class); 
	private DocOptCareRecordDao docOptCareRecordDao = SpringContextHolder.getBean(DocOptCareRecordDao.class); 
	
	/**
     *手术排班信息回传HIS
     */
    public String sendDispatchInfo(BasDispatch record,BasRegOpt regOpt)
    {
        logger.info("正在同步"+regOpt.getName()+"的排班信息!!!");
        
        if (StringUtils.isBlank(regOpt.getPreengagementnumber()))
        {
            return null;
        }
        
        HisDispatchFormbean dis = new HisDispatchFormbean();
        BeanHelper.copyProperties(record, dis);
        //String beid = basBusEntityDao.getBeid();
        String anesthetistId = record.getAnesthetistId();
        if(StringUtils.isNotBlank(anesthetistId))
        {
        	dis.setAnesthetistId(anesthetistId);
        }else
        {
        	dis.setAnesthetistId("");
        }
        dis.setCircunurseId1(record.getCircunurseId1());
        dis.setCircunurseId2(record.getCircunurseId2());
        dis.setInstrnurseId1(record.getInstrnurseId1());
        dis.setInstrnurseId2(record.getInstrnurseId2());
        dis.setReservenumber(regOpt.getPreengagementnumber());
        String stime = "";
        if(StringUtils.isNotEmpty(record.getStartTime())){
        	stime = regOpt.getOperaDate()+" "+record.getStartTime();
        }
        dis.setStartTime(stime);
        
        String respMsg = "";
        try {
            String asXml = getObjectToXml(dis);
            logger.info("sendDispatchInfo请求参数为=========="+asXml);

            //将排程的数据同步给HIS
            respMsg = getSoap().updateScheduling(asXml);
            
            if(null != respMsg && !"".equals(respMsg))
            {
                JAXBContext context = JAXBContext.newInstance(HisResponse.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();  
                HisResponse response = (HisResponse)unmarshaller.unmarshal(new StringReader(respMsg));
                    
                if(null != response)
                {
                     if(!"0".equals(response.getResultCode())){
                         throw new RuntimeException(response.getResultMessage()); 
                     }
                }else{
                    logger.info("sendDispatchInfo时his端无响应");
                }
            }
            
            logger.info("sendDispatchInfo响应参数为=========="+respMsg);
        } catch (Exception e) {
            logger.info("同步"+regOpt.getName()+"的排班异常:"+Exceptions.getStackTraceAsString(e));
            //throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("完成同步"+regOpt.getName()+"的排班信息!!!");
        return respMsg; 
    }
    
    //将手麻的手术状态同步给HIS
    public String updateState(String regOptId,String state)
    {
    	String respMsg = "";
    	String beid = basBusEntityDao.getBeid();
    	//排班信息
    	DispatchFormbean dispatch = basDispatchDao.getDispatchOperByRegOptId(regOptId, beid);
    	
    	//患者基本信息
    	BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
    	if(null != regOpt)
    	{
    		StateObj stateObj = new StateObj();
    		String reservenumber = "";
    		if(StringUtils.isNotBlank(regOpt.getPreengagementnumber()))
    		{
    			reservenumber = regOpt.getPreengagementnumber();
    		}
    		stateObj.setReservenumber(reservenumber);
    		stateObj.setState(state);
    		String incisionLevel = "";
    		if (Objects.equal(regOpt.getCutLevel(), 1))
    	    {
    			incisionLevel = "Ⅰ";
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 2))
    	    {
    	    	incisionLevel = "Ⅱ";
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 3))
    	    {
    	    	incisionLevel = "Ⅲ";
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 4))
    	    {
    	    	incisionLevel = "Ⅳ";
    	    }
    		stateObj.setIncisionLevel(incisionLevel);
    		String assistantName= regOpt.getAssistantName();
    		String name1 = "";
    		String name2 = "";
    		if(StringUtils.isNotBlank(assistantName))
    		{
    			String [] asNameArray = assistantName.split(",");
    			if(null != asNameArray && asNameArray.length == 1)
    			{
    				name1 = assistantName;
    			}else if(null != asNameArray && asNameArray.length > 1)
    			{
    				name1 = asNameArray[0];
    				name2 = asNameArray[1];
    			}
    		}
    		stateObj.setAssistantName(name1);
			stateObj.setAssistantName2(name2);
			String operatorName = "";
			if(StringUtils.isNotBlank(regOpt.getOperatorName()))
			{
				operatorName = regOpt.getOperatorName();
			}
    		stateObj.setOperatorName(operatorName);
    		
    		String anesthetistName = "";
    		if(StringUtils.isNotBlank(dispatch.getAnesthetistName()))
    		{
    			anesthetistName = dispatch.getAnesthetistName();
    		}
    		stateObj.setAnesthetistName(anesthetistName);
    		
    		Integer emergency = regOpt.getEmergency();
    		if(null != emergency)
    		{
    			stateObj.setEmergency(emergency);
    		}else
    		{
    			stateObj.setEmergency(0);
    		}
    		
    		
    		String anaesMethodName = "";
    		String operStartTime = "";
    		String operEndTime = "";
    		String realOperationCode = "";
    		String realOperationName = "";
    		
    		Integer isLocalAnaes = regOpt.getIsLocalAnaes();
    		//局麻 
    		if(null != isLocalAnaes && isLocalAnaes.intValue() == 1)
    		{
    			DocOptCareRecord optCareRecord = docOptCareRecordDao.selectByRegOptId(regOptId);
    			if(null != optCareRecord)
    			{
    				if(StringUtils.isNotBlank(optCareRecord.getAnaesMethodName()))
    				{
    					anaesMethodName = optCareRecord.getAnaesMethodName();
    				}
    				if(StringUtils.isNotBlank(optCareRecord.getInOperRoomTime()))
    				{
    					operStartTime = optCareRecord.getInOperRoomTime();
    				}
    				if(StringUtils.isNotBlank(optCareRecord.getOutOperRoomTime()))
    				{
    					operEndTime = optCareRecord.getOutOperRoomTime();
    				}
    				
            		//转换成HIS的code
            		List<String> operationCode = new ArrayList<String>();
            		String codes = optCareRecord.getOperationCode();
            		if(StringUtils.isNotBlank(codes))
            		{
            			String [] codeArray = codes.split(",");
            			for(int i = 0;i<codeArray.length;i++)
            			{
            				BasOperdef basOperdef = basOperdefDao.selectByCode(codeArray[i], beid);
            				if(null != basOperdef)
            				{
            					operationCode.add(basOperdef.getCode());
            				}
            			}
            		}
            		if(null != operationCode && operationCode.size()>0)
            		{
            			realOperationCode = StringUtils.getStringByList(operationCode);
            		}
            		if(StringUtils.isNotBlank(optCareRecord.getOperationName()))
            		{
            			realOperationName = optCareRecord.getOperationName();
            		}
    			}	
    		}else{
    			//全麻
    			//麻醉记录单
    	    	DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
        		List<String> anaesMethodNames = new ArrayList<String>();
        		SearchFormBean searchBean = new SearchFormBean();
        		searchBean.setRegOptId(regOptId);
        		searchBean.setDocId(anaesRecord.getAnaRecordId());
        		searchBean.setBeid(beid);
        		List<EvtAnaesMethodFormBean> realAnaesMethodList = evtRealAnaesMethodDao.getSelectRealAnaesMethodList(searchBean);
        		if(null != realAnaesMethodList && realAnaesMethodList.size()>0)
        		{
        			for( int i = 0;i<realAnaesMethodList.size();i++)
        			{
        				EvtAnaesMethodFormBean evtAnaesMethodFormBean = realAnaesMethodList.get(i);
        				anaesMethodNames.add(evtAnaesMethodFormBean.getName());
        			}
        		}
        		if(null != anaesMethodNames && anaesMethodNames.size()>0)
				{
        			anaesMethodName = StringUtils.getStringByList(anaesMethodNames);
				}
        		if(StringUtils.isNotBlank(anaesRecord.getOperStartTime()))
				{
        			operStartTime = anaesRecord.getOperStartTime();
				}
        		if(StringUtils.isNotBlank(anaesRecord.getOperEndTime()))
				{
        			operEndTime = anaesRecord.getOperEndTime();
				}
        		
        		List<String> operDefNames = new ArrayList<String>();
        		List<String> operDefCodes = new ArrayList<String>();
                List<OperDefFormBean> operDefs = evtOptRealOperDao.getSelectOptRealOperList(searchBean);
                if (null != operDefs && operDefs.size() > 0)
                {
                    for (OperDefFormBean operDefFormBean : operDefs)
                    {
                        operDefNames.add(operDefFormBean.getName());
                        operDefCodes.add(operDefFormBean.getCode());
                    }
                }
                if(null != operDefCodes && operDefCodes.size()>0)
                {
                	realOperationCode = StringUtils.getStringByList(operDefCodes);
                }
                if(null != operDefNames && operDefNames.size()>0)
                {
                	realOperationName = StringUtils.getStringByList(operDefNames);
                }
                
    		}
    		stateObj.setAnaesMethodName(anaesMethodName);
    		stateObj.setOperStartTime(operStartTime);
			stateObj.setOperEndTime(operEndTime);
    		stateObj.setRealOperationCode(realOperationCode);
    		stateObj.setRealOperationName(realOperationName);
    		
    		try
			{
				String asXml = getObjectToXml(stateObj);
				logger.info("updateState请求参数为=========="+asXml);
				respMsg = getSoap().updateState(asXml);
				if(null != respMsg && !"".equals(respMsg))
				{
					JAXBContext context = JAXBContext.newInstance(HisResponse.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					HisResponse response = (HisResponse) unmarshaller.unmarshal(new StringReader(respMsg));

					if (null != response)
					{
						if (!"0".equals(response.getResultCode()))
						{
							throw new RuntimeException(response.getResultMessage());
							//logger.info("updateState时his端响应异常"+response.getResultMessage());
						}
					} else
					{
						logger.info("updateState时his端无响应");
					}
				}
				logger.info("updateState响应参数为=========="+respMsg);
			} catch (Exception e)
			{
				logger.info("同步"+regOpt.getName()+"的手术状态异常:"+Exceptions.getStackTraceAsString(e));
	            //throw new RuntimeException(Exceptions.getStackTraceAsString(e));
			}
    	}
    	
    	return respMsg;
    }
    
    //修改手术通知单
    public String updateOperationNotice(String regOptId)
    {
    	String respMsg = "";
    	BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
    	if(null != regOpt)
    	{
    		OperList oper = new OperList();
    		if(null != regOpt.getAge())
    		{
    			oper.setAge(String.valueOf(regOpt.getAge()));
    		}
    		if(null != regOpt.getAgeMon())
    		{
    			oper.setAgeMon(String.valueOf(regOpt.getAgeMon()));
    		}
    		if(null != regOpt.getAgeDay())
    		{
    			oper.setAgeDay(String.valueOf(regOpt.getAgeDay()));
    		}
    		oper.setAnaesId(regOpt.getDesignedAnaesMethodCode());
    		oper.setAnaesName(regOpt.getDesignedAnaesMethodName());
    		oper.setAssistantId(regOpt.getAssistantId());
    		oper.setAssistantName(regOpt.getAssistantName());
    		oper.setBed(regOpt.getBed());
    		oper.setBirthday(regOpt.getBirthday());
    		oper.setCid(regOpt.getCid());
    		oper.setCreateUser(regOpt.getCreateUser());
    		oper.setDeptId(regOpt.getDeptId());
    		oper.setDeptName(regOpt.getDeptName());
    		oper.setDiagCode(regOpt.getDiagnosisCode());
    		oper.setDiagName(regOpt.getDiagnosisName());
    		oper.setDragAllergy(regOpt.getHyperSusceptiBility());
    		oper.setFrontOperForbidTake(regOpt.getFrontOperForbidTake());
    		oper.setFrontOperSpecialCase(regOpt.getFrontOperSpecialCase());
    		oper.setHbsag(regOpt.getHbsag());
    		oper.setHcv(regOpt.getHcv());
    		oper.setHeight(regOpt.getHeight());
    		oper.setHid(regOpt.getHid());
    		oper.setHiv(regOpt.getHiv());
    		oper.setHp(regOpt.getHp());
    		if (Objects.equal(regOpt.getCutLevel(), 1))
    	    {
    			oper.setIncisionLevel("Ⅰ");
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 2))
    	    {
    	    	oper.setIncisionLevel("Ⅱ");
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 3))
    	    {
    	    	oper.setIncisionLevel("Ⅲ");
    	    }
    	    else if (Objects.equal(regOpt.getCutLevel(), 4))
    	    {
    	    	oper.setIncisionLevel("Ⅳ");
    	    }   
    		oper.setMedicalType(regOpt.getMedicalType());
    		oper.setName(regOpt.getName());
    		oper.setOperCode(regOpt.getOperatorId());
    		oper.setOperDate(regOpt.getOperaDate());
    		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
    		oper.setOperEndTime(anaesRecord.getOperEndTime());
    		oper.setOperLevel(regOpt.getOptLevel());
    		
    		oper.setOperName(regOpt.getOperatorName());
    		oper.setOperSource(regOpt.getOperSource());
    		oper.setOperStartTime(anaesRecord.getOperStartTime());
    		oper.setOperType(regOpt.getEmergency());
    		oper.setRegionId(regOpt.getRegionId());
    		oper.setRegionName(regOpt.getRegionName());
    		oper.setReservenumber(regOpt.getPreengagementnumber());
    		oper.setSex(regOpt.getSex());
    		//获取主刀医生code 将原id转成his对应的code
            String operationPeopleCode = regOpt.getOperatorId(); 
            if(StringUtils.isNotBlank(operationPeopleCode)){
                BasOperationPeople ope = basOperationPeopleDao.queryOperationPeopleById(operationPeopleCode);
                if(null!=ope){
                	oper.setSurgeryDoctorId(ope.getCode());
                }
            }
            oper.setSurgeryDoctorName(regOpt.getOperatorName());
    		oper.setWeight(regOpt.getWeight());
    		
			try
			{
				String asXml = getObjectToXml(oper);
				logger.info("UpdateOperationNotice 请求参数为=========="+asXml);
				respMsg = getSoap().updateOperationNotice(asXml);
				if(null != respMsg && !"".equals(respMsg))
				{
					JAXBContext context = JAXBContext.newInstance(HisResponse.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					HisResponse response = (HisResponse) unmarshaller.unmarshal(new StringReader(respMsg));

					if (null != response)
					{
						if (!"0".equals(response.getResultCode()))
						{
							throw new RuntimeException(response.getResultMessage());
						}
					} else
					{
						logger.info("updateOperationNotice时his端无响应");
					}
				}
				logger.info("updateOperationNotice 响应参数为=========="+respMsg);
				
			} catch (Exception e)
			{
				logger.info("术后同步HIS手术信息异常："+e.getMessage());
				e.printStackTrace();
			}
    	}
    	
    	return respMsg;
    }
    
    //创建getObjectToXml方法（将对象转换成XML格式的文件）
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
     *急诊手术同步给HIS
     */
    public HisResponse sendEmergencyOperation(BasRegOpt regOpt,BasDispatch record)
    {
        logger.info("正在同步"+regOpt.getName()+"的急诊患者基础信息!!!");
        
        HisRegOpt hisRegOpt = new HisRegOpt();
        BeanHelper.copyProperties(regOpt, hisRegOpt); 
        hisRegOpt.setOperDate(regOpt.getOperaDate());
        hisRegOpt.setOperType(1);
        hisRegOpt.setAnaesType(String.valueOf(regOpt.getIsLocalAnaes()));
        hisRegOpt.setDragAllergy(regOpt.getHyperSusceptiBility());
        hisRegOpt.setDeptId(null == regOpt.getDeptId() ? null : Integer.parseInt(regOpt.getDeptId()));
        hisRegOpt.setRegionId(null == regOpt.getRegionId() ? null : Integer.parseInt(regOpt.getRegionId()));
        hisRegOpt.setOperLevel(regOpt.getOptLevel());
        //hisRegOpt.setIncisionLevel(null == regOpt.getCutLevel() ? null : regOpt.getCutLevel()+"");
        hisRegOpt.setFrontOperSpecialCase(regOpt.getFrontOperSpecialCase());
        hisRegOpt.setFrontOperForbidTake(regOpt.getFrontOperForbidTake());
        hisRegOpt.setCreateUser(regOpt.getCreateUser());
        //切口等级
        if (Objects.equal(regOpt.getCutLevel(), 1))
	    {
        	hisRegOpt.setIncisionLevel("Ⅰ");
	    }
	    else if (Objects.equal(regOpt.getCutLevel(), 2))
	    {
	    	hisRegOpt.setIncisionLevel("Ⅱ");
	    }
	    else if (Objects.equal(regOpt.getCutLevel(), 3))
	    {
	    	hisRegOpt.setIncisionLevel("Ⅲ");
	    }
	    else if (Objects.equal(regOpt.getCutLevel(), 4))
	    {
	    	hisRegOpt.setIncisionLevel("Ⅳ");
	    }   
        
        String age = "";
        if(null!=regOpt.getAge() && regOpt.getAge() > 0){
            age = regOpt.getAge()+"岁";
        }
        if(null!=regOpt.getAgeMon() && regOpt.getAgeMon() > 0){
            age += regOpt.getAgeMon()+"月";
        }
        if(null!=regOpt.getAgeDay() && regOpt.getAgeDay() > 0){
            age += regOpt.getAgeDay()+"天";
        }
        hisRegOpt.setAge(age);
        
        //诊断名称
        if (StringUtils.isNotBlank(regOpt.getDiagnosisCode()))
        {
            String diagCode = "";
            String[] diags = regOpt.getDiagnosisCode().split(",");
            for (String s : diags)
            {
                BasDiagnosedef diagnosedef = basDiagnosedefDao.searchDiagnosedefById(s);
                if (null != diagnosedef)
                {
                    String code = diagnosedef.getCode();
                    if ("".equals(diagCode))
                    {
                        diagCode = null == code ? "" : code;
                    }
                    else
                    {
                        diagCode = diagCode + "," + code;
                    }
                }
            }
            hisRegOpt.setDiagCode(diagCode);
        }
        hisRegOpt.setDiagName(regOpt.getDiagnosisName());
        
        //手术名称
        if (StringUtils.isNotBlank(regOpt.getDesignedOptCode()))
        {
            String operCode = "";
            String[] designedOptCodes = regOpt.getDesignedOptCode().split(",");
            for (String s : designedOptCodes)
            {
                BasOperdef operDef = basOperdefDao.queryOperdefById(s);
                if (null != operDef)
                {
                    String code = operDef.getCode();
                    if ("".equals(operCode))
                    {
                        operCode = null == code ? "" : code;
                    }
                    else
                    {
                        operCode = operCode + "," + code;
                    }
                }
            }
            hisRegOpt.setOperCode(operCode);
        }
        hisRegOpt.setOperName(regOpt.getDesignedOptName());
        
        
        
        //拟实施麻醉方法 将原id转成his对应的code
        String anaesMethodCode = "";
        if (StringUtils.isNotBlank(regOpt.getDesignedAnaesMethodCode()))
        {
            String[] anaesMethodCodeList = regOpt.getDesignedAnaesMethodCode().split(",");
            for (int i = 0; i < anaesMethodCodeList.length; i++)
            {
                BasAnaesMethod def = basAnaesMethodDao.searchAnaesMethodById(anaesMethodCodeList[i]);
                if (null != def)
                {
                    String code = def.getCode();
                    if ("".equals(anaesMethodCode))
                    {
                        anaesMethodCode = null == code ? "" : code;
                    }
                    else
                    {
                        anaesMethodCode = anaesMethodCode + "," + code;
                    }
                }
            }
            hisRegOpt.setAnaesID(anaesMethodCode);
        }
        hisRegOpt.setAnaesName(regOpt.getDesignedAnaesMethodName());
        
        //手术开始时间
        //hisRegOpt.setOperstarttime(regOpt.getStartTime());
        
        //获取主刀医生code 将原id转成his对应的code
        String operationPeopleCode = regOpt.getOperatorId(); 
        if(StringUtils.isNotBlank(operationPeopleCode)){
            BasOperationPeople ope = basOperationPeopleDao.queryOperationPeopleById(operationPeopleCode);
            if(null!=ope){
                hisRegOpt.setSurgeryDoctorId(ope.getCode());
            }
        }
        hisRegOpt.setSurgeryDoctorName(regOpt.getOperatorName());
        
        //助手医生处理
        String assistantId = "";
        if (StringUtils.isNotBlank(regOpt.getAssistantId()))
        {
            String[] assistantsList = regOpt.getAssistantId().split(",");
            for (String id : assistantsList)
            {
                BasOperationPeople ope = basOperationPeopleDao.queryOperationPeopleById(id);
                if (null != ope)
                {
                    String code = ope.getCode();
                    if ("".equals(assistantId))
                    {
                        assistantId = null == code ? "" : code;
                    }
                    else
                    {
                        assistantId = assistantId + "," + code;
                    }
                }
            }
            hisRegOpt.setAssistantId(assistantId);
        }
        hisRegOpt.setAssistantName(regOpt.getAssistantName());
        
        logger.info("正在同步"+regOpt.getName()+"的急诊患者排班信息!!!");
        
        HisDispatch dis = new HisDispatch();
        BeanHelper.copyProperties(record, dis);
        String stime = record.getStartTime();
        stime = regOpt.getOperaDate()+" "+stime;
        dis.setStartTime(stime);
        String respMsg = "";
        HisResponse response = null;
        try {
            EmergencyOperFormbean emg = new EmergencyOperFormbean();
            emg.setDispatch(dis);
            emg.setRegopt(hisRegOpt);
            
            String asXml = getObjectToXml(emg);
            logger.info("sendEmergencyOperation请求参数为=========="+asXml);
            
            
            //respMsg = getSoap().updateOperationNotice(asXml);
            
            
            if(null != respMsg && !"".equals(respMsg))
            {
                JAXBContext context = JAXBContext.newInstance(HisResponse.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();  
                response = (HisResponse)unmarshaller.unmarshal(new StringReader(respMsg));
                    
                if(null != response)
                {   
                     if(!"0".equals(response.getResultCode())){
                         throw new RuntimeException(response.getResultMessage()); 
                     }
                }else{
                    logger.info("sendEmergencyOperation时his端无响应");
                }
            }
            
            logger.info("sendEmergencyOperation响应参数为=========="+respMsg);
        } catch (Exception e) {
            logger.info("同步"+regOpt.getName()+"的急诊信息异常:"+Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("完成同步"+regOpt.getName()+"的急诊信息!!!");
        return response;    
    }
    
    /**
     *实例化HIS webService接口
     */
    private OperationSoap getSoap()
    {
    	Operation service = new Operation();
    	OperationSoap soap = service.getOperationSoap();
        return soap;
    }
}
