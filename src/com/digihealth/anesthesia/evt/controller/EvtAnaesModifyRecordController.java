package com.digihealth.anesthesia.evt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.evt.formbean.SearchConditionFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesModifyRecord;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/operation")
@Api(value = "EvtAnaesModifyRecordController", description = "术中麻醉记录单历史痕迹保存")
public class EvtAnaesModifyRecordController extends BaseController {

	@RequestMapping(value = "/queryEvtAnaesModifyRecordList")
	@ResponseBody
	@ApiOperation(value = "查询术中麻醉记录单历史痕迹", httpMethod = "POST", notes = "查询所有术中麻醉记录单历史痕迹")
	public String queryEvtAnaesModifyRecordList(@ApiParam(name = "searchBean", value = "参数") @RequestBody SearchConditionFormBean searchBean) {
		logger.info("begin queryEvtAnaesModifyRecordList");
		ResponseValue resp = new ResponseValue();
		List<EvtAnaesModifyRecord> resultList = evtAnaesModifyRecordService.queryEvtAnaesModifyRecordList(searchBean);
		Integer total = evtAnaesModifyRecordService.queryCountEvtAnaesModifyRecordList(searchBean);
		resp.put("resultList", resultList);
		resp.put("total", total);
		logger.info("end queryEvtAnaesModifyRecordList");
		return resp.getJsonStr();
	}
}
