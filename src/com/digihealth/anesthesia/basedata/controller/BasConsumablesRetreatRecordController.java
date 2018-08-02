package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.CommonRetreatRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.OperationRetreatRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata") 
@Api(value = "BasConsumablesRetreatRecordController", description = "耗材退药处理类")
public class BasConsumablesRetreatRecordController extends BaseController
{
	/**
     * 
     * @discription 查询耗材普通退药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesCommonRetreatRecordList")
    @ResponseBody
    @ApiOperation(value = "查询耗材普通退药记录", httpMethod = "POST", notes = "查询耗材普通退药记录")
    public String queryConsumablesCommonRetreatRecordList(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesCommonRetreatRecordList");
        ResponseValue resp = new ResponseValue();
        List<CommonRetreatRecordFormBean> resultList = basConsumablesRetreatRecordService.queryCommonRetreatRecordList(systemSearchFormBean);
        int total = basConsumablesRetreatRecordService.queryCommonRetreatRecordListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesCommonRetreatRecordList");
        return resp.getJsonStr();
    }
    
	/**
     * 
     * @discription 查询耗材手术退药记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesOperationRetreatRecordList")
    @ResponseBody
    @ApiOperation(value = "查询耗材手术退药记录", httpMethod = "POST", notes = "查询耗材手术退药记录")
    public String queryConsumablesOperationRetreatRecordList(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesOperationRetreatRecordList");
        ResponseValue resp = new ResponseValue();
        List<OperationRetreatRecordFormBean> resultList = basConsumablesRetreatRecordService.queryOperationRetreatRecordList(systemSearchFormBean);
        int total = basConsumablesRetreatRecordService.queryOperationRetreatRecordListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesOperationRetreatRecordList");
        return resp.getJsonStr();
    }
}
