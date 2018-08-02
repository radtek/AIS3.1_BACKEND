package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasBloodDefination;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
     * Title: BloodDefinationController.java    
     * Description: 血液基础信息表
     * @author liukui    
     * @created 2016-7-29
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasBloodDefinationController", description = "血液基础信息表处理类")
public class BasBloodDefinationController extends BaseController
{
    /**
     * 查询血液基础信息表
     */
	@RequestMapping(value = "/queryBloodList")
	@ResponseBody
	@ApiOperation(value = "查询血液基础信息表", httpMethod = "POST", notes = "查询血液基础信息表")
	public String queryBloodList() {
		logger.info("begin queryBloodList");
		ResponseValue resp = new ResponseValue();
		List<BasBloodDefination> resultList = basBloodDefinationService.queryAllList();
		resp.put("bloodList", resultList);
		logger.info("end queryBloodList");
		return resp.getJsonStr();
	}
}
