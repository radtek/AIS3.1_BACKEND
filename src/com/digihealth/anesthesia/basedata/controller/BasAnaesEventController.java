package com.digihealth.anesthesia.basedata.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesEvent;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasAnaesEventController", description = "麻醉事件处理类")
public class BasAnaesEventController extends BaseController {

	@RequestMapping(value = "/updateAnaesEvent")
	@ResponseBody
    @ApiOperation(value = "修改麻醉事件定义", httpMethod = "POST", notes = "修改麻醉事件定义")
	public String updateAnaesEvent(@ApiParam(name = "basAnaesEvent", value = "修改参数") @RequestBody BasAnaesEvent basAnaesEvent) {
		logger.info("begin updateAnaesEventType");
		ResponseValue resp = new ResponseValue();
        basAnaesEventService.updateAnaesEvent(basAnaesEvent, resp);
        logger.info("end updateAnaesEventType");
        return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/selectAnaesEventById")
    @ResponseBody
    @ApiOperation(value = "根据id查询麻醉事件定义", httpMethod = "POST", notes = "根据id查询麻醉事件定义")
    public String selectAnaesEventById(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) {
        logger.info("begin selectAnaesEventById");
        ResponseValue resp = new ResponseValue();
        BasAnaesEvent basAnaesEvent = basAnaesEventService.selectAnaesEventById((String)map.get("id"));
        resp.put("result", basAnaesEvent); 
        logger.info("end selectAnaesEventById");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/selectALlAnaesEvent")
    @ResponseBody
    @ApiOperation(value = "查询麻醉事件定义", httpMethod = "POST", notes = "查询麻醉事件定义")
    public String selectALlAnaesEvent(@ApiParam(name = "searchBean", value = "参数") @RequestBody SystemSearchFormBean systemSearchFormBean) {
        logger.info("begin selectALlAnaesEvent");
        ResponseValue resp = new ResponseValue();
        List<BasAnaesEvent> basAnaesEventList = basAnaesEventService.selectAllAnaesEvent(systemSearchFormBean);
        int total = basAnaesEventService.selectAnaesEventTotal(systemSearchFormBean);
        resp.put("resultList", basAnaesEventList); 
        resp.put("total", total);
        logger.info("end selectALlAnaesEvent");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/deleteAnaesEventById")
    @ResponseBody
    @ApiOperation(value = "根据id查询麻醉事件定义", httpMethod = "POST", notes = "根据id查询麻醉事件定义")
    public String deleteAnaesEventById(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) {
        logger.info("begin deleteAnaesEventById");
        ResponseValue resp = new ResponseValue();
        basAnaesEventService.deleteAnaesEventById((String)map.get("id"));
        logger.info("end deleteAnaesEventById");
        return resp.getJsonStr();
    }
}
