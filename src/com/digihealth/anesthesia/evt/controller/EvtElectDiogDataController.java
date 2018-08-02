package com.digihealth.anesthesia.evt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtElectDiogData;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * 
     * Title: ElectDiogDataController.java    
     * Description: 心电图数据保存
     * @author liukui       
     * @created 2015-9-15 下午1:56:40
 */
@Controller
@RequestMapping(value = "/operation")
public class EvtElectDiogDataController extends BaseController{

	@RequestMapping(value = "/searchElectDiogDataList")
	@ResponseBody
	@ApiOperation(value = "查询心电图数据", httpMethod = "POST", notes = "查询心电图数据")
	public String searchElectDiogDataList(@RequestBody SearchFormBean searchBean) {
		// 心电图数据			
		ResponseValue resValue = new ResponseValue();
		logger.info("begin searchElectDiogDataList");
		List<EvtElectDiogData> ElectDiogDataList = evtElectDiogDataService.searchElectDiogDataList(searchBean);
		resValue.put("electDiogDataList", ElectDiogDataList);
		logger.info("end searchElectDiogDataList");
		return resValue.getJsonStr();
		 
	}
	
	
	@RequestMapping(value = "/saveElectDiogData")
	@ResponseBody
	@ApiOperation(value = "心电图数据保存", httpMethod = "POST", notes = "心电图数据保存")
	public String saveElectDiogData(@RequestBody EvtElectDiogData electDiogData) {
		logger.info("begin saveElectDiogData");
		ResponseValue resValue = new ResponseValue();
		try {
			
			ValidatorBean validatorBean = beanValidator(electDiogData);
			if(!(validatorBean.isValidator())){
				resValue.setResultCode("10000001");
				resValue.setResultMessage(validatorBean.getMessage());
				return resValue.getJsonStr();
			}
			
			evtElectDiogDataService.saveElectDiogData(electDiogData);
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(electDiogData.getDocId());
			resValue.put("electDiogDataList", evtElectDiogDataService.searchElectDiogDataList(searchBean));
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(Exceptions.getStackTraceAsString(e));
			}
			resValue.setResultCode( "10000000");
			resValue.setResultMessage( "系统错误，请与系统管理员联系!");
		}
		logger.info("end saveElectDiogData");
		return resValue.getJsonStr();
	}
	
	
}
