/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperationPeople;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.SafeCheckFormBean;
import com.digihealth.anesthesia.doc.formbean.UpdateSafeCheckFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchSafeCheckRegOptFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptLatterDiag;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtParticipant;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.sysMng.po.BasUser;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: PreVisitController.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocSafeCheckController",description="安全核查处理类")
public class DocSafeCheckController extends BaseController {
	
	/**
     * 
     * @discription 根据手术ID获取手术核查单
     * @author chengwang
     * @created 2015年10月28日 上午9:51:41
     * @param map
     * @return
     */
    @RequestMapping(value = "/searchSafeCheckByRegOptId")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术核查单",httpMethod="POST",notes="根据手术ID获取手术核查单")
    public String searchSafeCheckByRegOptId(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("begin searchSafeCheckByRegOptId");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        DocSafeCheck safeCheck = docSafeCheckService.searchSafeCheckByRegOptId(regOptId);
        if (safeCheck == null) {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术核查单不存在");
            return resp.getJsonStr();
        }
        
        List<String> anesthetistIds = new ArrayList<String>();
        List<String> circunurseIds = new ArrayList<String>();
        List<String> operatorIds = new ArrayList<String>();
        
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        searchBean.setRole("A");
        List<EvtParticipant> anaesDocList = evtParticipantService.searchParticipantList(searchBean);
        searchBean.setRole("N");
        List<EvtParticipant> nurseDocList = evtParticipantService.searchParticipantList(searchBean);
        searchBean.setRole("O");
        List<EvtParticipant> operList = evtParticipantService.searchParticipantList(searchBean);
        
        //麻醉医生签名
        if (null == safeCheck.getAnesthetistId())
        {
            if (null != anaesDocList && anaesDocList.size() > 0)
            {
                for (EvtParticipant evtParticipant : anaesDocList)
                {
                    anesthetistIds.add(evtParticipant.getUserLoginName());
                }
            }
        }
        else
        {
            anesthetistIds = StringUtils.getListByString(safeCheck.getAnesthetistId());
        }
        safeCheck.setAnesthetistIdList(anesthetistIds);
        
        //护士签名
        if (null == safeCheck.getCircunurseId())
        {
            if (null != nurseDocList && nurseDocList.size() > 0)
            {
                for (EvtParticipant evtParticipant : nurseDocList)
                {
                    circunurseIds.add(evtParticipant.getUserLoginName());
                }
            }
        }
        else
        {
            circunurseIds = StringUtils.getListByString(safeCheck.getCircunurseId());
        }
        safeCheck.setCircunurseIdList(circunurseIds);
        
        //手术医师签名
        if (null == safeCheck.getOperatorId())
        {
            if (null != operList && operList.size() > 0)
            {
                for (EvtParticipant evtParticipant : operList)
                {
                    operatorIds.add(evtParticipant.getUserLoginName());
                }
            }
        }
        else
        {
            operatorIds = StringUtils.getListByString(safeCheck.getOperatorId());
        }
        safeCheck.setOperatorIdList(operatorIds);
        
        SearchSafeCheckRegOptFormBean searchRegOptByIdFormBean = basRegOptService
                .searchSafeCheckRegOptById(regOptId);
        SafeCheckFormBean bean = new SafeCheckFormBean();
        bean.setAge(searchRegOptByIdFormBean.getAge());
        bean.setBed(searchRegOptByIdFormBean.getBed());
        bean.setDeptName(searchRegOptByIdFormBean.getDeptName());
        bean.setDesignedAnaesMethodName(searchRegOptByIdFormBean
                .getDesignedAnaesMethodName());
        bean.setDesignedOptName(searchRegOptByIdFormBean.getDesignedOptName());
        bean.setDiagnosisName(searchRegOptByIdFormBean.getDiagnosisName());
        bean.setHid(searchRegOptByIdFormBean.getHid());
        bean.setName(searchRegOptByIdFormBean.getName());
        bean.setOperaDate(searchRegOptByIdFormBean.getOperaDate());
        bean.setRegionName(searchRegOptByIdFormBean.getRegionName());
        bean.setRegOptId(searchRegOptByIdFormBean.getRegOptId());
        bean.setSex(searchRegOptByIdFormBean.getSex());
        
        List<EvtRealAnaesMethod> realAnaMdList = evtRealAnaesMethodService
                .searchRealAnaesMethodList(searchBean);
        
        bean.setOperatorName("");
        if(operList!=null&&operList.size()>0){
            for(int i = 0 ; i <operList.size();i++){
                //User user = basUserService.searchUserById(Integer.parseInt(operList.get(i).getUserLoginName()));
                BasOperationPeople resultDept = basOperationPeopleService.queryOperationPeopleById(operList.get(i).getUserLoginName());
                bean.setOperatorName(bean.getOperatorName()+resultDept.getName()+",");
            }
        }
        if(!StringUtils.isEmpty(bean.getOperatorName())){
            bean.setOperatorName(bean.getOperatorName().substring(0,bean.getOperatorName().length()-1));
        }
        
        bean.setRealDesignedAnaesMethodName("");
        if (realAnaMdList.size() > 0 && realAnaMdList != null) {
            for (int i = 0; i < realAnaMdList.size(); i++) {
                bean.setRealDesignedAnaesMethodName(bean
                        .getRealDesignedAnaesMethodName()==null?realAnaMdList.get(i).getName() + ",":bean
                        .getRealDesignedAnaesMethodName()
                        + realAnaMdList.get(i).getName() + ",");
            }
        }
        
        if(!StringUtils.isEmpty(bean.getRealDesignedAnaesMethodName())){
            bean.setRealDesignedAnaesMethodName(bean.getRealDesignedAnaesMethodName().substring(0,bean.getRealDesignedAnaesMethodName().length()-1));
        }

        List<EvtOptLatterDiag> optLDList = evtOptLatterDiagService.searchOptLatterDiagList(searchBean);
        bean.setRealDiagnosisName("");
        if (optLDList.size() > 0 && optLDList != null) {
            for (int i = 0; i < optLDList.size(); i++) {
                bean.setRealDiagnosisName(bean.getRealDiagnosisName()==null?optLDList.get(i).getName() + ",":bean.getRealDiagnosisName()
                        + optLDList.get(i).getName() + ",");
            }
        }
        
        if(!StringUtils.isEmpty(bean.getRealDiagnosisName())){
            bean.setRealDiagnosisName(bean.getRealDiagnosisName().substring(0,bean.getRealDiagnosisName().length()-1));
        }

        List<EvtOptRealOper> optROList = evtOptRealOperService.searchOptRealOperList(searchBean);
        bean.setRealDesignedOptName("");
        if (optROList.size() > 0 && optROList != null) {
            for (int i = 0; i < optROList.size(); i++) {
                bean.setRealDesignedOptName(bean.getRealDesignedOptName()==null?optROList.get(i).getName() + ",":bean.getRealDesignedOptName()
                        + optROList.get(i).getName() + ",");
            }
        }
        if(!StringUtils.isEmpty(bean.getRealDesignedOptName())){
            bean.setRealDesignedOptName(bean.getRealDesignedOptName().substring(0,bean.getRealDesignedOptName().length()-1));
        }
        
        DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = docAnaesBeforeSafeCheckService.searchAnaBeCheckByRegOptId(regOptId);
        DocOperBeforeSafeCheck operBeforeSafeCheck = docOperBeforeSafeCheckService.searchOperBeCheckByRegOptId(regOptId);
        DocExitOperSafeCheck exitOperSafeCheck = docExitOperSafeCheckService.searchExitOperCheckByRegOptId(regOptId);
        
        SearchRegOptByIdFormBean regOptItem = basRegOptService.searchApplicationById(regOptId);
        
		// 实施手术
		searchBean = new SearchFormBean();
		searchBean.setRegOptId(regOptId);
		searchBean.setDocId(anaesRecord.getAnaRecordId());

		// 麻醉医生列表
		searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
		searchBean.setType("01");
		String anaesDoc = statisticsService.getNameStrByDocId(searchBean);
		// 手术医生列表
		searchBean.setRole(EvtParticipantService.ROLE_OPERAT);
		searchBean.setType("07");
		String operatDoc = statisticsService.getNameStrByDocId(searchBean);
		// 巡回护士列表
		searchBean.setRole(EvtParticipantService.ROLE_NURSE);
		searchBean.setType("05");
		String circunurse = statisticsService.getNameStrByDocId(searchBean);
		
		// 洗手护士列表
		searchBean.setRole(EvtParticipantService.ROLE_NURSE);
		searchBean.setType("04");
		String instrnurse = statisticsService.getNameStrByDocId(searchBean);

		//获取排程信息
        BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
		
		if ("1" == regOptItem.getIsLocalAnaes()) { // 局麻
			circunurse = basUserService.searchUserById(
					dispatch.getCircunurseId1(), getBeid()).getName();
			instrnurse = basUserService.searchUserById(
					dispatch.getInstrnurseId1(), getBeid()).getName();
			operatDoc = regOptItem.getOperatorName();
		}
        
		resp.put("anaesDoc", anaesDoc);
		resp.put("operatDoc", StringUtils.isNotBlank(operatDoc)?operatDoc:regOptItem.getOperatorName());
		resp.put("circunurse", circunurse);
		resp.put("instrnurse", instrnurse);
        resp.put("regOptItem",regOptItem);
        resp.put("safeCheck", safeCheck); 
        resp.put("safeCheckFormBean", bean);
        resp.put("anaesBeforeSafeCheck", anaesBeforeSafeCheck);
        resp.put("operBeforeSafeCheck", operBeforeSafeCheck);
        resp.put("exitOperSafeCheck", exitOperSafeCheck);
        logger.info("end searchSafeCheckByRegOptId");
        return resp.getJsonStr();
    }

	@RequestMapping(value = "/updateSafeCheck")
	@ResponseBody
	@ApiOperation(value="修改手术核查单",httpMethod="POST",notes="修改手术核查单")
	public String updateSafeCheck(@ApiParam(name="safeCheckFormbean", value ="修改参数") @RequestBody UpdateSafeCheckFormbean safeCheckFormbean){
		logger.info("begin updateOperBeforeSafeCheck");
		ResponseValue resp = new ResponseValue();
		
        if (null == safeCheckFormbean || null == safeCheckFormbean.getSafeCheck()) {
            resp.setResultCode("40000005");
            resp.setResultMessage("手术前核查单不存在!");
            return resp.getJsonStr();
        }
        DocSafeCheck safeCheck = safeCheckFormbean.getSafeCheck();
		DocOperBeforeSafeCheck operBeforeSafeCheck = safeCheckFormbean.getOperBeforeSafeCheck();
		DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = safeCheckFormbean.getAnaesBeforeSafeCheck();
		DocExitOperSafeCheck exitOperSafeCheck = safeCheckFormbean.getExitOperSafeCheck();
		
        ValidatorBean validatorBean = beanValidator(operBeforeSafeCheck);
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
        ValidatorBean validatorBean1 = beanValidator(anaesBeforeSafeCheck);
		if(!(validatorBean1.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
        ValidatorBean validatorBean2 = beanValidator(exitOperSafeCheck);
		if(!(validatorBean2.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		
		safeCheck.setAnesthetistId(StringUtils.getStringByList(safeCheck.getAnesthetistIdList()));
		safeCheck.setCircunurseId(StringUtils.getStringByList(safeCheck.getCircunurseIdList()));
		safeCheck.setOperatorId(StringUtils.getStringByList(safeCheck.getOperatorIdList()));
		docSafeCheckService.updateSafeCheck(safeCheck);
		
		resp = docOperBeforeSafeCheckService.updateOperBeforeSafeCheck(operBeforeSafeCheck);
		resp = docAnaesBeforeSafeCheckService.updateAnaesBeforeSafeCheck(anaesBeforeSafeCheck);
		resp = docExitOperSafeCheckService.updateExitOperSafeCheck(exitOperSafeCheck);
		
		logger.info("end updateOperBeforeSafeCheck");
		return resp.getJsonStr();
	}

}
