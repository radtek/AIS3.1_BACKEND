package com.digihealth.anesthesia.basedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.po.BasDocument;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value="BasDocumentController",description="文书信息处理类")
public class BasDocumentController extends BaseController
{

	/**
	 * 查询局点下有多少文书
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/selectBasDocumentByBeid")
	@ResponseBody
	@ApiOperation(value="查询文书信息列表",httpMethod="POST",notes="查询文书信息列表")
	public String selectBasDocumentByBeid(@ApiParam(name="baseQuery", value ="系统查询参数") @RequestBody BaseInfoQuery baseQuery) {
		logger.info("begin selectBasDocumentByBeid");
		ResponseValue resp = new ResponseValue();
		String beid = baseQuery.getBeid();
		resp.put("resultList", basDocumentService.selectBasDocByBeid(beid));
		logger.info("end selectBasDocumentByBeid");
		return resp.getJsonStr();
	}
	
	/**
	 * 查询指定文书ID的文书信息
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/selectBasDocumentByDocId")
	@ResponseBody
	@ApiOperation(value="查询文书信息列表",httpMethod="POST",notes="查询文书信息列表")
	public String selectBasDocumentByDocId(@ApiParam(name="baseQuery", value ="系统查询参数") @RequestBody BaseInfoQuery baseQuery) {
		logger.info("begin selectBasDocumentByDocId");
		ResponseValue resp = new ResponseValue();
		String docId = baseQuery.getDocId();
		resp.put("resultList", basDocumentService.selectBasDocByDocId(docId));
		logger.info("end selectBasDocumentByDocId");
		return resp.getJsonStr();
	}
	
	/**
	 * 保存文书信息
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/saveBasDocument")
	@ResponseBody
	@ApiOperation(value="查询文书信息列表",httpMethod="POST",notes="查询文书信息列表")
	public String saveBasDocument(@ApiParam(name="basDocument", value ="BasDocument对象") @RequestBody BasDocument basDocument) {
		logger.info("begin saveBasDocument");
		ResponseValue resp = new ResponseValue();
		basDocumentService.saveBasDocument(basDocument);
		logger.info("end saveBasDocument");
		return resp.getJsonStr();
	}
}
