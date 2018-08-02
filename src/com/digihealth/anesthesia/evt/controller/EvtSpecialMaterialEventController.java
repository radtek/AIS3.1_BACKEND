package com.digihealth.anesthesia.evt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtSpecialMaterialEvent;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/operation")
@Api(value = "EvtSpecialMaterialEventController", description = "术中特殊材料事件处理类")
public class EvtSpecialMaterialEventController extends BaseController
{

	@RequestMapping(value = "/selectSpecialMaterialEvent")
	@ResponseBody
	@ApiOperation(value = "查询特殊材料事件列表", httpMethod = "POST", notes = "查询特殊材料事件列表")
	public String selectSpecialMaterialEvent(@ApiParam(name = "searchBean", value = "参数") @RequestBody SearchFormBean searchBean) {
		// 其他事件
		logger.info("begin selectSpecialMaterialEvent");
		ResponseValue resp = new ResponseValue();
		List<EvtSpecialMaterialEvent> specialList = evtSpecialMaterialEventService.selectSpecialMaterialEvent(searchBean);
		resp.put("resultList", specialList);
		logger.info("end selectSpecialMaterialEvent");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/saveSpecialMaterialEvent")
    @ResponseBody
    @ApiOperation(value = "保存特殊材料事件", httpMethod = "POST", notes = "保存特殊材料事件")
    public String saveSpecialMaterialEvent(@ApiParam(name = "otherevent", value = "参数") @RequestBody EvtSpecialMaterialEvent evtSpecialMaterialEvent) {
        logger.info("begin saveSpecialMaterialEvent");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(evtSpecialMaterialEvent);
        if (!(validatorBean.isValidator())) {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean.getMessage());
            return resp.getJsonStr();
        }

        evtSpecialMaterialEventService.saveSpecialMaterialEvent(evtSpecialMaterialEvent);
        resp.put("specialMaterialEventId", evtSpecialMaterialEvent.getSpecialMaterialEventId());
        logger.info("end saveSpecialMaterialEvent");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/deleteSpecialMaterialEvent")
	@ResponseBody
	@ApiOperation(value = "删除特殊材料事件", httpMethod = "POST", notes = "删除特殊材料事件")
	public String deleteSpecialMaterialEvent(@ApiParam(name = "otherevent", value = "参数") @RequestBody EvtSpecialMaterialEvent evtSpecialMaterialEvent) {
		logger.info("begin deleteSpecialMaterialEvent");
		ResponseValue resp = new ResponseValue();
		evtSpecialMaterialEventService.deleteSpecialMaterialEvent(evtSpecialMaterialEvent);
		logger.info("end deleteSpecialMaterialEvent");
		return resp.getJsonStr();
	}
}
