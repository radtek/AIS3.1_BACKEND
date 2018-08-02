package com.digihealth.anesthesia.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnalgesicInformedConsent;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocAnalgesicInformedConsentController", description = "术后（术前）镇疼知情同意书处理类")
public class DocAnalgesicInformedConsentController extends BaseController
{

	/**
	 * 查询术后（术前）镇疼知情同意书
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/searchAnalgesicInformedConsent")
	@ResponseBody
	@ApiOperation(value="查询术后（术前）镇疼知情同意书",httpMethod="POST",notes="查询术后（术前）镇疼知情同意书")
	public String searchAnaesSummaryList(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean) {
		logger.info("-------------begin searchAnalgesicInformedConsent-------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = searchBean.getRegOptId();
		DocAnalgesicInformedConsent docAnalgesicInformedConsent = docAnalgesicInformedConsentService.selectInformedConsentByRegOptId(regOptId);
		BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
		resp.put("analgesicInformedConsent", docAnalgesicInformedConsent);
		resp.put("basRegOpt", regOpt);
		logger.info("-------------end searchAnalgesicInformedConsent-------------");
		return resp.getJsonStr();
	}
	
	/**
	 * 保存术后（术前）镇疼知情同意书
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/saveAnalgesicInformedConsent")
	@ResponseBody
	@ApiOperation(value="保存术后（术前）镇疼知情同意书",httpMethod="POST",notes="保存术后（术前）镇疼知情同意书")
	public String saveAnalgesicInformedConsent(@ApiParam(name="docAnalgesicInformedConsent", value ="镇疼知情同意书对象") @RequestBody DocAnalgesicInformedConsent docAnalgesicInformedConsent) {
		logger.info("-------------begin saveAnalgesicInformedConsent-------------");
		ResponseValue resp = new ResponseValue();
		docAnalgesicInformedConsentService.saveAnalgesicInformedConsent(docAnalgesicInformedConsent);
		logger.info("-------------end saveAnalgesicInformedConsent-------------");
		return resp.getJsonStr();
	}
}
