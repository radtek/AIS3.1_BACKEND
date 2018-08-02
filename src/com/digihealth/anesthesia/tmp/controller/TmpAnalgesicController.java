package com.digihealth.anesthesia.tmp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.evt.po.EvtMedicalEvent;
import com.digihealth.anesthesia.tmp.formbean.TmpAnalgesicFromBean;
import com.digihealth.anesthesia.tmp.po.TmpAnalgesic;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "TmpAnalgesicController", description = "镇痛方式模板处理类")
public class TmpAnalgesicController extends BaseController
{

	@RequestMapping(value = "/saveTmpAnalgesic")
	@ResponseBody
	@ApiOperation(value = "保存镇痛方式模版", httpMethod = "POST", notes = "保存镇痛方式模版")
	public String saveTmpAnalgesic(@ApiParam(name = "tmpAnalgesicFromBean", value = "镇痛方式模板保存对象") @RequestBody TmpAnalgesicFromBean tmpAnalgesicFromBean)
	{
		logger.info("begin saveTmpAnalgesic");
		ResponseValue resp = new ResponseValue();
		tmpAnalgesicService.saveTmpAnalgesic(tmpAnalgesicFromBean);
		logger.info("end saveTmpAnalgesic");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/delTmpAnalgesicByType")
	@ResponseBody
	@ApiOperation(value = "删除镇痛方式模版", httpMethod = "POST", notes = "删除镇痛方式模版")
	public String delTmpAnalgesicByType(@ApiParam(name = "tmpAnalgesicFromBean", value = "镇痛方式模板保存对象") @RequestBody TmpAnalgesicFromBean tmpAnalgesicFromBean)
	{
		logger.info("begin delTmpAnalgesicByType");
		ResponseValue resp = new ResponseValue();
		String analgesicType = tmpAnalgesicFromBean.getAnalgesicType();
		tmpAnalgesicService.delTmpAnalgesicByType(analgesicType);
		logger.info("end delTmpAnalgesicByType");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/selectTmpAnalgesicByType")
	@ResponseBody
	@ApiOperation(value = "查询镇痛方式模版", httpMethod = "POST", notes = "查询镇痛方式模版")
	public String selectTmpAnalgesicByType(@ApiParam(name = "tmpAnalgesicFromBean", value = "镇痛方式模板保存对象") @RequestBody TmpAnalgesicFromBean tmpAnalgesicFromBean)
	{
		logger.info("begin selectTmpAnalgesicByType");
		ResponseValue resp = new ResponseValue();
		String analgesicType = tmpAnalgesicFromBean.getAnalgesicType();
		List<TmpAnalgesic> tmpAnalgesicList = tmpAnalgesicService.selectTmpAnalgesicByType(analgesicType);
		resp.put("tempList", tmpAnalgesicList);
		logger.info("end selectTmpAnalgesicByType");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/useTmpAnalgesicByType")
	@ResponseBody
	@ApiOperation(value = "使用镇痛方式模版", httpMethod = "POST", notes = "使用镇痛方式模版")
	public String useTmpAnalgesicByType(@ApiParam(name = "tmpAnalgesicFromBean", value = "镇痛方式模板保存对象") @RequestBody TmpAnalgesicFromBean tmpAnalgesicFromBean)
	{
		logger.info("begin useTmpAnalgesicByType");
		ResponseValue resp = new ResponseValue();
		EvtMedicalEvent evtMedicalEvent = null;
		String analgesicType = "";
		if(null != tmpAnalgesicFromBean)
		{
			evtMedicalEvent = tmpAnalgesicFromBean.getMedicalevent();
			analgesicType = tmpAnalgesicFromBean.getAnalgesicType();
		}else
		{
			resp.setResultCode("10000000");
			resp.setResultMessage("参数不能为空！");
            return resp.getJsonStr();
		}
		ValidatorBean validatorBean = beanValidator(evtMedicalEvent);
		if (!(validatorBean.isValidator())) {
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
		
		List<EvtMedicalEvent> evtMedicalEventList = tmpAnalgesicService.useTmpAnalgesicByType(analgesicType,evtMedicalEvent);
		//把原来的镇痛方式的用药删除
		evtMedicaleventService.deleteByDocIdorType(evtMedicalEvent.getDocId(),3);
		//把选择的模板用药插入用药事件表
		if(null != evtMedicalEventList && evtMedicalEventList.size()>0)
		{
			for(EvtMedicalEvent newEvtMedicalEvent : evtMedicalEventList)
			{
				evtMedicaleventService.saveMedicalevent(newEvtMedicalEvent, resp);
			}
		}
		
		logger.info("end useTmpAnalgesicByType");
		return resp.getJsonStr();
	}
}
