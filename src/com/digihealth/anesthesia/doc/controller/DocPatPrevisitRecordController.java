/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocPatPrevisitRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptLatterDiag;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: AnaesPostopController.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocAnaesPostopController",description="麻醉后随访处理类")
public class DocPatPrevisitRecordController extends BaseController {

	/**
	 * 
	 * @discription 根据手术ID获取患者随访
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	@RequestMapping(value = "/searchPatPrevisitRecordByRegOptId")
	@ResponseBody
	@ApiOperation(value="根据手术ID获取患者随访",httpMethod="POST",notes="根据手术ID获取患者随访")
	public String searchPatPrevisitRecordByRegOptId(@ApiParam(name="map", value ="查询参数") @RequestBody Map map) {
		logger.info("---------------------start searchPatPrevisitRecordByRegOptId--------------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId")!=null?map.get("regOptId").toString():"";
		DocPatPrevisitRecord docPatPrevisitRecord = docPatPrevisitRecordService.searchPatPrevisitRecordByRegOptId(regOptId);
		if(docPatPrevisitRecord == null){
			resp.setResultCode("80000001");
			resp.setResultMessage("患者访视单不存在!");
			return resp.getJsonStr();
		}
//		DispatchPeopleNameFormBean dispatchPeople = basDispatchService.searchPeopleNameByRegOptId(regOptId);
//		if(dispatchPeople!=null){
//			docPatPrevisitRecord.setAnaestheitistName(dispatchPeople.getAnesthetistName()!=null?dispatchPeople.getAnesthetistName():"");
//		}
		SearchRegOptByIdFormBean bean = basRegOptService
				.searchApplicationById(map.get("regOptId").toString());
//		anaesPostop.setAnesthetistIdList(com.digihealth.anesthesia.common.utils.StringUtils.getListByString(anaesPostop.getAnaestheitistId()));
		
		DocAnaesRecord anaesRecord = docAnaesRecordService
				.searchAnaesRecordByRegOptId(regOptId);
		SearchFormBean searchBean = new SearchFormBean();
		searchBean.setDocId(anaesRecord.getAnaRecordId());
		List<EvtRealAnaesMethod> realAnaMdList = evtRealAnaesMethodService
				.searchRealAnaesMethodList(searchBean);
		
		String designedAnaesMethodName = "";
		if (realAnaMdList.size() > 0 && realAnaMdList != null) {
		    bean.setDesignedAnaesMethodName("");
			for (int i = 0; i < realAnaMdList.size(); i++) {
				designedAnaesMethodName += realAnaMdList.get(i).getName() + ",";
			}
			designedAnaesMethodName = designedAnaesMethodName.substring(0, designedAnaesMethodName.length()-1);
		}
		if(StringUtils.isBlank(designedAnaesMethodName)){
			designedAnaesMethodName = bean.getDesignedAnaesMethodName();
		}
		bean.setDesignedAnaesMethodName(designedAnaesMethodName);

		List<EvtOptLatterDiag> optLDList = evtOptLatterDiagService
				.searchOptLatterDiagList(searchBean);
		String diagnosisName = "";
		if (optLDList.size() > 0 && optLDList != null) {
		    bean.setDiagnosisName("");
			for (int i = 0; i < optLDList.size(); i++) {
				diagnosisName += optLDList.get(i).getName() + ",";
			}
			diagnosisName = diagnosisName.substring(0,diagnosisName.length()-1);
		}
		
		if(org.apache.commons.lang3.StringUtils.isEmpty(diagnosisName)){
			diagnosisName = bean.getDiagnosisName();
		}
		bean.setDiagnosisName(diagnosisName);

		List<EvtOptRealOper> optROList = evtOptRealOperService
				.searchOptRealOperList(searchBean);
		String designedOptName = "";
		if (optROList.size() > 0 && optROList != null) {
			for (int i = 0; i < optROList.size(); i++) {
				designedOptName += optROList.get(i).getName() + ",";
			}
			designedOptName = designedOptName.substring(0,designedOptName.length()-1);
		}
		if(org.apache.commons.lang3.StringUtils.isEmpty(designedOptName)){
			designedOptName = bean.getDesignedOptName();
		}
		bean.setDesignedOptName(designedOptName);
		
		resp.put("docPatPrevisitRecord", docPatPrevisitRecord);
		resp.put("searchRegOptByIdFormBean", bean);

		logger.info("---------------------end searchPatPrevisitRecordByRegOptId--------------------");
		return resp.getJsonStr();
	}

	/**
	 * 
	 * @discription 修改患者随访
	 * @author chengwang
	 * @created 2015-10-20 上午9:33:40
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/updateDocPatPrevisitRecord")
	@ResponseBody
	@ApiOperation(value="修改患者随访",httpMethod="POST",notes="修改患者随访")
	public String updateDocPatPrevisitRecord(@ApiParam(name="anaesPostop", value ="统计查询参数") @RequestBody DocPatPrevisitRecord record) {
		logger.info("----------------begin updateAnaesPostop----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(record);
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		docPatPrevisitRecordService.updateDocPatPrevisitRecord(record);
		logger.info("----------------end updateAnaesPostop----------------");
		return resp.getJsonStr();
	}

	
}
