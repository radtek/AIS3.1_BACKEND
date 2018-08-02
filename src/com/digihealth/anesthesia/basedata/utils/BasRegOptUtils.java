package com.digihealth.anesthesia.basedata.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.digihealth.anesthesia.basedata.dao.BasAnaesMethodDao;
import com.digihealth.anesthesia.basedata.dao.BasDiagnosedefDao;
import com.digihealth.anesthesia.basedata.dao.BasOperationPeopleDao;
import com.digihealth.anesthesia.basedata.dao.BasOperdefDao;
import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.digihealth.anesthesia.basedata.formbean.DiagnosisCodes;
import com.digihealth.anesthesia.basedata.po.BasAnaesMethod;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;

public class BasRegOptUtils extends BaseService {

	private static BasAnaesMethodDao basAnaesMethodDao = SpringContextHolder.getBean(BasAnaesMethodDao.class);
	private static BasOperationPeopleDao basOperationPeopleDao = SpringContextHolder.getBean(BasOperationPeopleDao.class);
	private static BasDiagnosedefDao basDiagnosedefDao = SpringContextHolder.getBean(BasDiagnosedefDao.class);
	private static BasOperdefDao basOperdefDao = SpringContextHolder.getBean(BasOperdefDao.class);

	/**
	 * 根据患者麻醉方法，设置是否局麻字段
	 * 
	 * @param regOpt
	 * @param anaesMethodCode
	 * @param beid
	 */
	public static void IsLocalAnaesSet(BasRegOpt regOpt) {
		int isLocalAnaes = 0;
		List<String> anaesMethodCodes = regOpt.getDesignedAnaesMethodCodes();
        if (null != anaesMethodCodes && anaesMethodCodes.size() == 1)
        {
            BasAnaesMethod basAnaesMethod = basAnaesMethodDao.searchAnaesMethodById(anaesMethodCodes.get(0));
            if (null != basAnaesMethod && null != basAnaesMethod.getIsLocalAnaes())
            {
                isLocalAnaes = basAnaesMethod.getIsLocalAnaes();
            }
        }
        regOpt.setIsLocalAnaes(isLocalAnaes);
	}

	/**
	 * 处理拟施手术、拟施诊断、麻醉方法等字段的值。
	 * 
	 * @param regOpt
	 * @return
	 */
	public static BasRegOpt getOtherInfo(BasRegOpt regOpt) {
		// 助手医生
		String assistantId = "";
		String assistantName = "";
		List<String> assistants = regOpt.getAssistants();
		if (assistants != null) {
			for (String id : assistants) {
				BasOperationPeople basOperationPeople = basOperationPeopleDao.queryOperationPeopleById(id);
				if (StringUtils.isBlank(assistantId)) {
					assistantId = id;
					assistantName = basOperationPeople.getName();
				} else {
					assistantId += "," + id;
					assistantName += "," + basOperationPeople.getName();
				}
			}
		}
		regOpt.setAssistantId(assistantId);
		regOpt.setAssistantName(assistantName);

		// 主治医生
		String operatorId = regOpt.getOperatorId();
		BasOperationPeople basOperationPeople = basOperationPeopleDao.queryOperationPeopleById(operatorId);
		regOpt.setOperatorName(basOperationPeople.getName());

	
		 /**
         * 当诊断为手工输入时，将信息插入到基础表
         * 拟实施诊断
         */
        getDiagnosisId(regOpt);
        /**
         * 当手术为手工输入时，将信息插入到基础表
         * 拟实施手术
         */
        getOperDefId(regOpt);
		
		String designedAnaesMethodCodes = "";
		String designedAnaesMethodNames = "";
		List<String> designedAnaesMethodCodeIds = regOpt.getDesignedAnaesMethodCodes();
		if (designedAnaesMethodCodeIds != null) {
			for (String id : designedAnaesMethodCodeIds) {
				BasAnaesMethod basAnaesMethod = basAnaesMethodDao.searchAnaesMethodById(id);
				if (StringUtils.isBlank(designedAnaesMethodCodes)) {
					designedAnaesMethodCodes = id;
					designedAnaesMethodNames = basAnaesMethod.getName();
				} else {
					designedAnaesMethodCodes += "," + id;
					designedAnaesMethodNames += "," + basAnaesMethod.getName();
				}
			}
		}
		regOpt.setDesignedAnaesMethodCode(designedAnaesMethodCodes);
		regOpt.setDesignedAnaesMethodName(designedAnaesMethodNames);
		return regOpt;
	}
	
	
	public static void getDiagnosisId(BasRegOpt regOpt)
	{
	    List<DiagnosisCodes> diagnosisCodes = regOpt.getDiagnosisCodes();
	    String diagcode = "";
	    String diagName = "";
        if(null != diagnosisCodes && diagnosisCodes.size() > 0){
            for (DiagnosisCodes diagnosisCode : diagnosisCodes) {
                if (StringUtils.isBlank(diagnosisCode.getDiagDefId()))
                {
                    BasDiagnosedef diagnosedef = new BasDiagnosedef();
                    String diagDefId = GenerateSequenceUtil.generateSequenceNo(GenerateSequenceUtil.getRoomId(regOpt.getRegOptId()));
                    diagnosedef.setDiagDefId(diagDefId);
                    diagnosedef.setName(diagnosisCode.getName());
                    diagnosedef.setPinYin(PingYinUtil.getFirstSpell(diagnosisCode.getName()));
                    diagnosedef.setEnable(1);
                    diagnosedef.setBeid(regOpt.getBeid());
                    basDiagnosedefDao.insert(diagnosedef);
                    if (StringUtils.isBlank(diagcode))
                    {
                        diagcode = diagDefId;
                    }
                    else
                    {
                        diagcode = diagcode + "," + diagDefId;
                    }
                }
                else
                {
                    if (StringUtils.isBlank(diagcode))
                    {
                        diagcode = diagnosisCode.getDiagDefId();
                    }
                    else
                    {
                        diagcode = diagcode + "," + diagnosisCode.getDiagDefId();
                    }
                }
                
                if (StringUtils.isBlank(diagName))
                {
                    diagName = diagnosisCode.getName();
                }
                else
                {
                    diagName = diagName + "," + diagnosisCode.getName();
                }
            }
        }
        regOpt.setDiagnosisCode(diagcode);
        regOpt.setDiagnosisName(diagName);
	}
	
    public static void getOperDefId(BasRegOpt regOpt)
    {
        List<DesignedOptCodes> designedOptList = regOpt.getDesignedOptCodes();
        String designedOptCode = "";
        String designedOptName = "";
        if (null != designedOptList && designedOptList.size() > 0)
        {
            for (DesignedOptCodes operDef : designedOptList)
            {
                if (StringUtils.isBlank(operDef.getOperDefId()))
                {
                    BasOperdef operdef = new BasOperdef();
                    String operdefId = GenerateSequenceUtil.generateSequenceNo(GenerateSequenceUtil.getRoomId(regOpt.getRegOptId()));
                    operdef.setOperdefId(operdefId);
                    operdef.setName(operDef.getName());
                    operdef.setPinYin(PingYinUtil.getFirstSpell(operDef.getName()));
                    operdef.setEnable(1);
                    operdef.setBeid(regOpt.getBeid());
                    basOperdefDao.insert(operdef);
                    if (StringUtils.isBlank(designedOptCode))
                    {
                        designedOptCode = operdefId;
                    }
                    else
                    {
                        designedOptCode = designedOptCode + "," + operdefId;
                    }
                }
                else
                {
                    if (StringUtils.isBlank(designedOptCode))
                    {
                        designedOptCode = operDef.getOperDefId();
                    }
                    else
                    {
                        designedOptCode = designedOptCode + "," + operDef.getOperDefId();
                    }
                }
                

                if (StringUtils.isBlank(designedOptName))
                {
                    designedOptName = operDef.getName();
                }
                else
                {
                    designedOptName = designedOptName + "," + operDef.getName();
                }
            }
        }
        regOpt.setDesignedOptCode(designedOptCode);
        regOpt.setDesignedOptName(designedOptName);
    }
	
	public static List<DiagnosisCodes> getDiagnosisList(String diagnosisId)
    {
	    List<DiagnosisCodes> diagnosisCodeList = new ArrayList<DiagnosisCodes>();
	    if (StringUtils.isNotBlank(diagnosisId))
	    {
	        String[] diagnosisAry = diagnosisId.split(",");
	        for (int i = 0; i < diagnosisAry.length; i++)
	        {
	            BasDiagnosedef diagnosedef = basDiagnosedefDao.searchDiagnosedefById(diagnosisAry[i]);
	            DiagnosisCodes diagnosisCode = new DiagnosisCodes();	  
	            if (null != diagnosedef)
	            {
    	            diagnosisCode.setDiagDefId(diagnosedef.getDiagDefId());
    	            diagnosisCode.setName(diagnosedef.getName());
    	            diagnosisCode.setPinYin(diagnosedef.getPinYin());
    	            diagnosisCodeList.add(diagnosisCode);
	            }
	        }
	    }
	    return diagnosisCodeList;
    }
	
	public static List<DesignedOptCodes> getOperDefList(String operDefId)
	{
	    List<DesignedOptCodes> operDefList = new ArrayList<DesignedOptCodes>();
	    if (StringUtils.isNotBlank(operDefId))
	    {
	        String[] operDefAry = operDefId.split(",");
	        for (int i = 0; i < operDefAry.length; i++)
	        {
	            BasOperdef operdef = basOperdefDao.queryOperdefById(operDefAry[i]);
	            if (null != operdef)
	            {
    	            DesignedOptCodes designedOptCode = new DesignedOptCodes();
    	            designedOptCode.setOperDefId(operdef.getOperdefId());
    	            designedOptCode.setName(operdef.getName());
    	            designedOptCode.setPinYin(operdef.getPinYin());
    	            operDefList.add(designedOptCode);
	            }
	        }
	    }
	    return operDefList;
	}
}
