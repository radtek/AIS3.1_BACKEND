package com.digihealth.anesthesia.interfacedata.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.BeanHelper;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.interfacedata.formbean.HisCancleOptFormBean;
import com.digihealth.anesthesia.interfacedata.formbean.HisResponse;
import com.digihealth.anesthesia.interfacedata.po.yxrm.OperList;

@WebService
@Component
public class RxmServiceImpl extends BaseService implements RxrmService
{
    /**
     * 取消手术通知 
     */
    @Override
    @Transactional
    public String cancleRegOpt(String request)
    {
        logger.info("begin cancleRegOpt");
        String response = "";
        HisResponse resp = new HisResponse();
        try
        {
            logger.info("-------------------------cancleRegOpt Request----------------------------:" + request);
            JAXBContext context = JAXBContext.newInstance(HisCancleOptFormBean.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            HisCancleOptFormBean hisCancleOptFormBean = (HisCancleOptFormBean)unmarshaller.unmarshal(new StringReader(request));
            
            if (StringUtils.isBlank(hisCancleOptFormBean.getReservenumber()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("手术预约号不能为空");
                return getObjectToXml(resp);
            }
            
            if (StringUtils.isBlank(hisCancleOptFormBean.getState()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("手术状态不能为空");
                return getObjectToXml(resp);
            }
            
            BasRegOpt regOpt =
                basRegOptDao.searchRegOptByReservenumber(hisCancleOptFormBean.getReservenumber(), getBeid());
            if (null != regOpt)
            {
            	//默认是取消状态
                String defaultState = "08" ;
                
                Controller controller = new Controller();
                controller.setRegOptId(regOpt.getRegOptId());
                controller.setCostsettlementState("0");
                String state = hisCancleOptFormBean.getState();
            	if("6".equals(state))
            	{
            		defaultState = "08";
            	}
            	controller.setState(defaultState);
                controllerDao.update(controller);
                resp.setResultCode("0");
                resp.setResultMessage("更新手术状态成功");
            }
            else
            {
                resp.setResultCode("1");
                resp.setResultMessage("没有找到预约号为" + hisCancleOptFormBean.getReservenumber() + "的手术");
            }
            response = getObjectToXml(resp);
            logger.info("-------------------------cancleRegOpt Response----------------------------:" + response);
        }
        catch (Exception e)
        {
            logger.info("更改手术状态时出现异常:"+Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("end cancleRegOpt");
        return response;
    }
    
    /**
     * 获取手术通知单
     * @param request
     * @return
     */
    @Override
    @Transactional
    public String getHisOperateNotice(String request)
    {
        logger.info("begin getHisOperateNotice");
        String response = "";
        HisResponse resp = new HisResponse();
        try
        {
            logger.info("-------------------------getHisOperateNotice Request----------------------------:" + request);
            JAXBContext context = JAXBContext.newInstance(OperList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            OperList operList = (OperList)unmarshaller.unmarshal(new StringReader(request));
            logger.info("-------------------------getHisOperateNotice operList = " + operList);
            resp = getRegOpt(operList);
            response = getObjectToXml(resp);
            logger.info("-------------------------getHisOperateNotice Response----------------------------:" + response);
        }
        catch (Exception e)
        {
            logger.info("手术信息同步时出现异常:"+Exceptions.getStackTraceAsString(e));
            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
        }
        
        logger.info("end getHisOperateNotice");
        
        return response;
    }

    private HisResponse getRegOpt(OperList operList)
    {
        HisResponse resp = new HisResponse();
        if (operList != null)
        {
            BasRegOpt regOpt = new BasRegOpt();
            String beid = getBeid();
            BeanHelper.copyProperties(operList, regOpt);
            
            if (StringUtils.isBlank(operList.getReservenumber()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("手术预约号不能为空");
                return resp;
            }
            
            // 判断患者是否已经存在，如果存在则更新基础数据
            BasRegOpt regPo = basRegOptDao.searchRegOptByReservenumber(operList.getReservenumber(), beid);
            
            //当前his同步手术信息流程为：his创建手术并且提交后，才会将手术信息同步到手麻系统，his提交手术后，手术信息不能再进行修改，因此，his不会调用手麻系统修改手术信息
            if (null != regPo)
            {
                resp.setResultCode("1");
                resp.setResultMessage("手术信息已存在,无需重复提交");
                return resp;
            }
            
            if (StringUtils.isBlank(operList.getHid()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("住院号不能为空");
                return resp;
            }
            
            String regOptname = operList.getName();
            if (StringUtils.isBlank(regOptname))
            {
                resp.setResultCode("1");
                resp.setResultMessage("姓名不能为空");
                return resp;
            }
            regOpt.setName(regOptname.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", ""));
            
            if (StringUtils.isBlank(operList.getOperDate()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("手术日期不能为空");
                return resp;
            }
            
            /***  HIS这个字段是非必填的
            if (StringUtils.isBlank(operList.getSurgeryDoctorId()))
            {
                resp.setResultCode("1");
                resp.setResultMessage("主刀医生不能为空");
                return resp;
            }*/
            
            // 永兴人民医院，HIS系统只能传诊断名称，HIS拿不到诊断编码，不会传过来
            String diagDef = "";
            String diagName = "";
            String[] diagnames = null;
            if (StringUtils.isNotEmpty(operList.getDiagName()))
            {
            	diagnames = operList.getDiagName().split(",");
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
            
            // 手术名称
            String operId = "";
            String operName = "";
            String[] operCodes = null;
            if (StringUtils.isNotEmpty(operList.getOperCode()))
            {
                operCodes = operList.getOperCode().split(",");
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
            if ("".equals(operName) && StringUtils.isNotBlank(operList.getOperName()))
            {
                String[] operNames = operList.getOperName().split(",");
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
            if (StringUtils.isNotEmpty(operList.getSurgeryDoctorId()))
            {
                operatorCodes = operList.getSurgeryDoctorId().split(",");
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
            if ("".equals(operatorName) && StringUtils.isNotBlank(operList.getSurgeryDoctorName()))
            {
                String[] operatorNames = operList.getSurgeryDoctorName().split(",");
                
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
            if (StringUtils.isNotEmpty(operList.getAssistantId()))
            {
                assistantCodes = operList.getAssistantId().split(",");
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
            if ("".equals(assistantName) && StringUtils.isNotBlank(operList.getAssistantName()))
            {
                String[] operatorNames = operList.getAssistantName().split(",");
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
            
            // 麻醉方法
            String designedAnaesMethodName = "";
            String designedAnaesMethodCode = "";
            String[] anaesCodes = null;
            
            if (StringUtils.isNotBlank(operList.getAnaesId()))
            {
                anaesCodes = operList.getAnaesId().split(",");
                
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
            if ("".equals(designedAnaesMethodName) && StringUtils.isNotBlank(operList.getAnaesName()))
            {
                String[] anaesNames = operList.getAnaesName().split(",");
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
            
            if (StringUtils.isBlank(regOpt.getDeptName()))
            {
                BasDept dept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
                regOpt.setDeptName(null != dept ? dept.getName() : "");
            }
            
            if (null == regOpt.getIsLocalAnaes())
            {
                BasRegOptUtils.IsLocalAnaesSet(regOpt);
            }
            regOpt.setBeid(beid);
            
            // 急诊 or 择期
            Integer operType = operList.getOperType();
            if(null != operType){
                regOpt.setEmergency(operType);
            }
            
            
            //身份证号
            String credNumber = operList.getCredNumber();
            if(StringUtils.isNotBlank(credNumber)){
                regOpt.setIdentityNo(credNumber);
            }
            // 手术日期
            String operDate = operList.getOperDate();
            if(StringUtils.isNotBlank(operDate)){
                regOpt.setOperaDate(operDate);
            }
            // 开始时间 结束时间
            String operStartTime = operList.getOperStartTime();
            String operEndTime = operList.getOperEndTime();
            if(StringUtils.isNotBlank(operStartTime)){
                regOpt.setStartTime(operStartTime);
            }
            if(StringUtils.isNotBlank(operEndTime)){
                regOpt.setEndTime(operEndTime);
            }
            //药物过敏
            String hyperSusceptiBility = operList.getDragAllergy();
            if(StringUtils.isNotBlank(hyperSusceptiBility)){
                regOpt.setHyperSusceptiBility(hyperSusceptiBility );
            }
            //手术级别
            String operLevel = operList.getOperLevel();
        	if(StringUtils.isNotBlank(operLevel))
        	{
        		operLevel = operLevel.trim();
        		if("小".equals(operLevel))
        		{
        			regOpt.setOptLevel("一级");
        		}else if("中".equals(operLevel))
        		{
        			regOpt.setOptLevel("二级");
        		}else if("大".equals(operLevel))
        		{
        			regOpt.setOptLevel("三级");
        		}else if("特".equals(operLevel))
        		{
        			regOpt.setOptLevel("四级");
        		}
        	}
            
            //手术创建者
            String createUser = operList.getCreateUser();
            if (StringUtils.isNotBlank(createUser))
            {
                regOpt.setCreateUser(createUser);
            }
            
            // 切口等级
            /*Integer incisionLevel = operList.getIncisionLevel();
            if(incisionLevel != null){
                regOpt.setCutLevel(incisionLevel);
            }*/
        	
            if(null != operList.getIncisionLevel()){ 
                Integer cutLevel = null;
                if("Ⅰ".equals(operList.getIncisionLevel()))
                    cutLevel = 1;
                if("Ⅱ".equals(operList.getIncisionLevel()))
                    cutLevel = 2;
                if("Ⅲ".equals(operList.getIncisionLevel()))
                    cutLevel = 3;
                if("Ⅳ".equals(operList.getIncisionLevel()))
                    cutLevel = 4;
                regOpt.setCutLevel(cutLevel);
            }
            
            //手术来源
            Integer operSource = operList.getOperSource();
            if(null != operSource ){
                regOpt.setOperSource(operSource);
            }
            
            //HIS传过来的年龄，月，天  都在 age 字段
            String hisAge = operList.getAge();
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
            
            String regOptId = GenerateSequenceUtil.generateSequenceNo();
            regOpt.setRegOptId(regOptId);
            regOpt.setCreateTime(DateUtils.formatDateTime(new Date()));
            //手日期和手术创建日期都是今天，把手术设置为急诊
    		checkEmergency(regOpt);
            regOpt.setPreengagementnumber(operList.getReservenumber());
            basRegOptDao.insert(regOpt);
            
            Controller controller = new Controller();
            controller.setRegOptId(regOpt.getRegOptId());
            controller.setCostsettlementState("0");
            
            //如果是急诊手术直接到术前，如果流程不需要批准手术 则直接将手术状态变为未排班，并且创建手术文书
            if(1 == regOpt.getEmergency().intValue())
            {
            	controller.setState(OperationState.PREOPERATIVE);
				creatDocument(regOpt);
            }
            else if (0 == regOpt.getEmergency().intValue() && "2".equals(CustomConfigureUtil.isRatify()))
            {
                controller.setState(OperationState.NO_SCHEDULING);
                creatDocument(regOpt);
            }
            else
            {
                controller.setState(OperationState.NOT_REVIEWED);
            }
            controllerDao.update(controller);
            
            resp.setResultCode("0");
            resp.setResultMessage("创建手术成功");
        }
        return resp;
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

}
