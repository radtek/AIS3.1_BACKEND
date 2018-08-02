package com.digihealth.anesthesia.basedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.po.BasRoutingAccessControl;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value="BasRoutingAccessController",description="接口路径处理类")
public class BasRoutingAccessController extends BaseController
{
	/**
	 * 查询局点下有多少文书
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/getBasRoutingAccessByBeid")
	@ResponseBody
	@ApiOperation(value="查询文书信息列表",httpMethod="POST",notes="查询文书信息列表")
	public String getBasRoutingAccessByBeid(@ApiParam(name="baseQuery", value ="系统查询参数") @RequestBody BaseInfoQuery baseQuery) {
		logger.info("begin getBasRoutingAccessByBeid");
		ResponseValue resp = new ResponseValue();
		String beid = baseQuery.getBeid();
		resp.put("resultList", basRoutingAccessService.getBasRoutingAccessByBeid(beid));
		logger.info("end getBasRoutingAccessByBeid");
		return resp.getJsonStr();
	}
	
	/**
	 * 保存文书信息
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/saveBasRoutingAccess")
	@ResponseBody
	@ApiOperation(value="查询文书信息列表",httpMethod="POST",notes="查询文书信息列表")
	public String saveBasRoutingAccess(@ApiParam(name="basDocument", value ="BasDocument对象") @RequestBody BasRoutingAccessControl basRoutingAccessControl) {
		logger.info("begin saveBasRoutingAccess");
		ResponseValue resp = new ResponseValue();
		basRoutingAccessService.saveBasRoutingAccess(basRoutingAccessControl,resp);
		logger.info("end saveBasRoutingAccess");
		return resp.getJsonStr();
	}
}
