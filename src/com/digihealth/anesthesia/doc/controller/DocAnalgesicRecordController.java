package com.digihealth.anesthesia.doc.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.AnalgesicRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.RegOptOperMedicaleventFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperMedicalevent;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
/**
 * 术后麻醉镇痛单
 * @author dell
 *
 */
@Api(value="DocAnalgesicRecordController",description="术后麻醉镇痛单处理类")
public class DocAnalgesicRecordController extends BaseController {
	
	@RequestMapping(value = "/getAnalgesicRecord")
	@ResponseBody
	@ApiOperation(value="查询术后麻醉镇痛单",httpMethod="POST",notes="查询术后麻醉镇痛单")
	public String getAnalgesicRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map map){
		logger.info("-------------start getAnalgesicRecord-------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId").toString();
		resp.put("analgesicRecord", docAnalgesicRecordService.getAnalgesicRecord(regOptId));
		
		SearchFormBean searchBean = new SearchFormBean();
		DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(regOptId);
		searchBean.setDocId(anaesRecord.getAnaRecordId());
		//实施手术
		List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
		String optRealOperStr = "";
		for (EvtOptRealOper optRealOper : optRealOperList) {
			optRealOperStr += optRealOper.getName()+",";
		}
		if(StringUtils.isNotBlank(optRealOperStr)){
			optRealOperStr =  optRealOperStr.substring(0, optRealOperStr.length()-1);
		}
		
		//实际麻醉方法
		List<EvtRealAnaesMethod> realAnaesList = evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
		String realAnaesStr = "";
		for (EvtRealAnaesMethod realAnaesMethod : realAnaesList) {
			realAnaesStr += realAnaesMethod.getName()+",";
		}
		if(StringUtils.isNotBlank(realAnaesStr)){
			realAnaesStr =  realAnaesStr.substring(0, realAnaesStr.length()-1);
		}
		
		//术中镇痛药  镇痛方式   从麻醉记录单获取     
		searchBean.setType("03");
        List<RegOptOperMedicaleventFormBean> analgesicMedEvtList = evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
		String analgesicMed = "";
		if (null != analgesicMedEvtList && analgesicMedEvtList.size() > 0)
		{
		    for (RegOptOperMedicaleventFormBean medical : analgesicMedEvtList)
		    {
		    	List<SearchOptOperMedicalevent> medicalEventList = medical.getMedicalEventList();
		    	
		    	BigDecimal dosageTotal = new BigDecimal(0);
		    	String dosageUnit = "";
		    	if(null != medicalEventList && medicalEventList.size()>0){
		    		for (SearchOptOperMedicalevent searchOptOperMedicalevent : medicalEventList) {
		    			dosageTotal = dosageTotal.add(new BigDecimal(searchOptOperMedicalevent.getDosage()));
					}
		    		dosageUnit = medicalEventList.get(0).getDosageUnit();
		    	}
		        analgesicMed = analgesicMed + medical.getName() + " " + dosageTotal + dosageUnit + ","; 
		    }
		    
		    if (StringUtils.isNotBlank(analgesicMed))
		    {
		        analgesicMed = analgesicMed.substring(0, analgesicMed.length() - 1);
		    }
		}
		resp.put("analgesicMed", analgesicMed);
		resp.put("analgesicMethod", anaesRecord.getAnalgesicMethod());
		
		//输液泵机型
		List<SysCodeFormbean> transPumpTypes =  basSysCodeService.searchSysCodeByGroupId("trans_pump_type", null);
		resp.put("transPumpTypes", transPumpTypes);
		
		//获取病人基本信息
		resp.put("regOpt", basRegOptService.getPostopOptInfo(regOptId));
		
		resp.put("optRealOperStr", optRealOperStr);
		resp.put("realAnaesStr", realAnaesStr);
		logger.info("-------------end getAnalgesicRecord-------------");
		return resp.getJsonStr();
	}
	
	
	@RequestMapping(value = "/saveAnalgesicRecord")
	@ResponseBody
	@ApiOperation(value="保存麻醉总结单",httpMethod="POST",notes="保存麻醉总结单")
	public String saveAnalgesicRecord(@ApiParam(name="record", value ="保存参数") @RequestBody AnalgesicRecordFormBean record){
		logger.info("-------------start saveAnalgesicRecord-------------");
		ResponseValue resp = new ResponseValue();
		docAnalgesicRecordService.saveAnalgesicRecord(record);
		logger.info("-------------end saveAnalgesicRecord-------------");
		return resp.getJsonStr();
	}
}
