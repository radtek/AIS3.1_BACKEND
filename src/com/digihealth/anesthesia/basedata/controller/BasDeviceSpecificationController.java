package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BasDeviceSpecificationFormBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasDeviceSpecification;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * Title: AnaesMonitorConfigController.java Description:监测点设置Controller
 * 
 * @author liukui
 * @created 2015-9-15 下午1:56:40
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasDeviceSpecificationController", description = "设备规格处理类")
public class BasDeviceSpecificationController extends BaseController
{
    
    @RequestMapping(value = "/queryDeviceSpecificationList")
    @ResponseBody
    @ApiOperation(value = "查询设备规格", httpMethod = "POST", notes = "查询设备规格")
    public String queryDeviceSpecificationList(@ApiParam(name = "SystemSearchFormBean", value = "查询设备规格信息") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryDeviceSpecificationList");
        ResponseValue resp = new ResponseValue();
        List<BasDeviceSpecification> resultList = basDeviceSpecificationService.queryDeviceSpecificationList(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", basDeviceSpecificationService.queryDeviceSpecificationListTotal(systemSearchFormBean));
        logger.info("end queryDeviceSpecificationList");
        return resp.getJsonStr();
    }
    
    
    @RequestMapping(value = "/getDeviceSpecificationList")
    @ResponseBody
    @ApiOperation(value = "查询设备规格", httpMethod = "POST", notes = "查询设备规格")
    public String getDeviceSpecificationList()
    {
        logger.info("begin getDeviceSpecificationList");
        ResponseValue resp = new ResponseValue();
        List<BasDeviceSpecification> resultList = basDeviceSpecificationService.getDeviceSpecificationList();
        resp.put("resultList", resultList);
        logger.info("end getDeviceSpecificationList");
        return resp.getJsonStr();
    }
    
    
    
    @RequestMapping(value = "/saveDeviceSpecification")
    @ResponseBody
    @ApiOperation(value = "保存设备规格", httpMethod = "POST", notes = "保存设备规格")
    public String saveDeviceSpecification(@ApiParam(name = "BasDeviceSpecification", value = "设备规格信息") @RequestBody BasDeviceSpecification record)
    {
        logger.info("begin saveDeviceSpecification");
        ResponseValue resp = new ResponseValue();
        basDeviceSpecificationService.saveDeviceSpecification(record);
        logger.info("end saveDeviceSpecification");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/queryBasDeviceListByBeid")
    @ResponseBody
    @ApiOperation(value = "查询设备规格", httpMethod = "POST", notes = "根据局点id获取对应局点设备配置信息")
    public String queryBasDeviceListByBeid(@RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryBasDeviceListByBeid");
        ResponseValue resp = new ResponseValue();
        List<BasDeviceSpecificationFormBean> resultList = basDeviceSpecificationService.queryBasDeviceListByBeid(systemSearchFormBean);
        Integer total = basDeviceSpecificationService.queryDeviceSpecificationListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryBasDeviceListByBeid");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/delDeviceSpecification")
    @ResponseBody
    @ApiOperation(value = "删除设备规格", httpMethod = "POST", notes = "删除设备规格")
    public String delDeviceSpecification(@ApiParam(name = "BasDeviceSpecification", value = "删除设备规格信息") @RequestBody BasDeviceSpecification record)
    {
        logger.info("begin delDeviceSpecification");
        ResponseValue resp = new ResponseValue();
        basDeviceSpecificationService.deleteByPrimaryKey(record);
        logger.info("end delDeviceSpecification");
        return resp.getJsonStr();
    }
    
    
    
}
