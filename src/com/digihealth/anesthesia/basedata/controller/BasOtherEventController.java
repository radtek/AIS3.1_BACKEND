package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasOtherEvent;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasOtherEventController", description = "其他事件处理类")
public class BasOtherEventController extends BaseController {

	@RequestMapping(value = "/updateOtherEvent")
	@ResponseBody
    @ApiOperation(value = "修改其他事件定义", httpMethod = "POST", notes = "修改其他事件定义")
	public String updateOtherEvent(@ApiParam(name = "basOtherEvent", value = "修改参数") @RequestBody BasOtherEvent basOtherEvent) {
		logger.info("begin updateOtherEventType");
		ResponseValue resp = new ResponseValue();
        basOtherEventService.updateOtherEvent(basOtherEvent);
        logger.info("end updateOtherEventType");
        return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/selectOtherEventById")
    @ResponseBody
    @ApiOperation(value = "根据id查询其他事件定义", httpMethod = "POST", notes = "根据id查询其他事件定义")
    public String selectOtherEventById(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) {
        logger.info("begin selectOtherEventById");
        ResponseValue resp = new ResponseValue();
        BasOtherEvent basOtherEvent = basOtherEventService.selectOtherEventById((String)map.get("id"));
        resp.put("result", basOtherEvent); 
        logger.info("end selectOtherEventById");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/selectALlOtherEvent")
    @ResponseBody
    @ApiOperation(value = "查询其他事件定义", httpMethod = "POST", notes = "查询其他事件定义")
    public String selectALlOtherEvent(@ApiParam(name = "searchBean", value = "参数") @RequestBody SystemSearchFormBean systemSearchFormBean) {
        logger.info("begin selectALlOtherEvent");
        ResponseValue resp = new ResponseValue();
        List<BasOtherEvent> basOtherEventList = basOtherEventService.selectAllOtherEvent(systemSearchFormBean);
        int total = basOtherEventService.selectOtherEventTotal(systemSearchFormBean);
        resp.put("resultList", basOtherEventList); 
        resp.put("total", total);
        logger.info("end selectALlOtherEvent");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/deleteOtherEventById")
    @ResponseBody
    @ApiOperation(value = "根据id查询其他事件定义", httpMethod = "POST", notes = "根据id查询其他事件定义")
    public String deleteOtherEventById(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) {
        logger.info("begin deleteOtherEventById");
        ResponseValue resp = new ResponseValue();
        basOtherEventService.deleteOtherEventById((String)map.get("id"));
        logger.info("end deleteOtherEventById");
        return resp.getJsonStr();
    }
}
