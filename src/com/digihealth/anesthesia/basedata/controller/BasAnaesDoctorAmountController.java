package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesDoctorAmount;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasAnaesDoctorAmountController", description = "麻醉医生数量处理类")
public class BasAnaesDoctorAmountController extends BaseController
{
    @RequestMapping(value = "/selectAllAnaesDoctorAmount")
    @ResponseBody
    @ApiOperation(value = "查询所有月份麻醉医生数量", httpMethod = "POST", notes = "查询所有月份麻醉医生数量")
    public String selectAllAnaesDoctorAmount(@ApiParam(name = "searchBean", value = "参数") @RequestBody SystemSearchFormBean systemSearchFormBean) {
        logger.info("begin selectAllAnaesDoctorAmount");
        ResponseValue resp = new ResponseValue();
        List<BasAnaesDoctorAmount> anaesDoctorAmountList = basAnaesDoctorAmountService.selectAllAnaesDoctorAmount(systemSearchFormBean);
        int total = basAnaesDoctorAmountService.selectAnaesDoctorAmountTotal(systemSearchFormBean);
        resp.put("resultList", anaesDoctorAmountList); 
        resp.put("total", total);
        logger.info("end selectAllAnaesDoctorAmount");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateAnaesDoctorAmount")
    @ResponseBody
    @ApiOperation(value = "更新麻醉医生数量", httpMethod = "POST", notes = "更新麻醉医生数量")
    public String updateAnaesDoctorAmount(@ApiParam(name = "anaesDoctorAmount", value = "参数") @RequestBody BasAnaesDoctorAmount anaesDoctorAmount)
    {
        logger.info("begin updateAnaesDoctorAmount");
        ResponseValue resp = new ResponseValue();
        basAnaesDoctorAmountService.updateAnaesDoctorAmount(anaesDoctorAmount);
        logger.info("end updateAnaesDoctorAmount");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/deleteAnaesDoctorAmount")
    @ResponseBody
    @ApiOperation(value = "删除麻醉医生数量记录", httpMethod = "POST", notes = "删除麻醉医生数量记录")
    public String deleteAnaesDoctorAmount(@ApiParam(name = "anaesDoctorAmount", value = "参数") @RequestBody BasAnaesDoctorAmount anaesDoctorAmount)
    {
        logger.info("begin deleteAnaesDoctorAmount");
        ResponseValue resp = new ResponseValue();
        basAnaesDoctorAmountService.deleteAnaesDoctorAmount(anaesDoctorAmount);
        logger.info("end deleteAnaesDoctorAmount");
        return resp.getJsonStr();
    }
}
