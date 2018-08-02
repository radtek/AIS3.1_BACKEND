package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BasChargeAmountDetailRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasConsumablesOutRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasConsumablesStorageFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesStorage;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata") 
@Api(value = "BasConsumablesStorageController", description = "耗材库存处理类")
public class BasConsumablesStorageController extends BaseController
{
	
	/**
     * 
     * @discription 按药品名字，厂家，规格 查询耗材库存记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesListGroupByNFS")
    @ResponseBody
    @ApiOperation(value = "按药品名字，厂家，规格 查询耗材库存记录", httpMethod = "POST", notes = "按药品名字，厂家，规格 查询耗材库存记录")
    public String queryConsumablesListGroupByNFS(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesListGroupByNFS");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesStorage> resultList = basConsumablesStorageService.queryConsumablesListGroupByNFS(systemSearchFormBean);
        int total = basConsumablesStorageService.queryConsumablesListGroupByNFSTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesListGroupByNFS");
        return resp.getJsonStr();
    }
    
	/**
     * @discription 根据条件查询耗材库存记录
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryConsumablesList")
    @ResponseBody
    @ApiOperation(value = "根据条件查询耗材库存记录", httpMethod = "POST", notes = "根据条件查询耗材库存记录")
    public String queryConsumablesList(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesList");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesStorage> resultList = basConsumablesStorageService.queryConsumablesList(systemSearchFormBean);
        int total = basConsumablesStorageService.queryConsumablesListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesList");
        return resp.getJsonStr();
    }
    
	/**
     * 
     * @discription 按月统计库存情况
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryConsumablesByMonth")
    @ResponseBody
    @ApiOperation(value = "按月统计库存情况", httpMethod = "POST", notes = "按月统计库存情况")
    public String queryConsumablesByMonth(
        @ApiParam(name = "map", value = "库存查询参数")@RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesList");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesStorageFormbean> resultList = basConsumablesStorageService.queryConsumablesByMonth(systemSearchFormBean,resp);
        int total = basConsumablesStorageService.queryConsumablesByMonthTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesList");
        return resp.getJsonStr();
    }
    
    
	/**
     * 
     * @discription 统计个人用药情况
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryConsumablesByPersonal")
    @ResponseBody
    @ApiOperation(value = "统计个人用药情况", httpMethod = "POST", notes = "统计个人用药情况")
    public String queryConsumablesByPersonal(
        @ApiParam(name = "map", value = "库存查询参数")@RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryConsumablesList");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesOutRecordFormBean> resultList = basConsumablesStorageService.queryConsumablesByPersonal(systemSearchFormBean);
        int total = basConsumablesStorageService.queryConsumablesByPersonalTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end queryConsumablesList");
        return resp.getJsonStr();
    }
    
	/**
     * @discription 根据库存统计中的计价用量，查询术中收费项目信息
     * @author pengqing
     * @created 2017年6月13日 下午3:01:40
     * @param systemSearchFormBean
     * @return
     */
    @RequestMapping(value = "/queryChargeAmountDetailByChargeId")
    @ResponseBody
    @ApiOperation(value = "根据库存统计中的计价用量，查询术中收费项目信息", httpMethod = "POST", notes = "根据库存统计中的计价用量，查询术中收费项目信息")
    public String queryChargeAmountDetailByChargeId(
        @ApiParam(name = "systemSearchFormBean", value = "系统查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin queryChargeAmountDetailByChargeId");
        ResponseValue resp = new ResponseValue();
        List<BasChargeAmountDetailRecordFormBean> resultList = basConsumablesStorageService.queryChargeAmountDetailByChargeId(systemSearchFormBean);
        //int total = basConsumablesStorageService.queryConsumablesListTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        //resp.put("total", total);
        logger.info("end queryChargeAmountDetailByChargeId");
        return resp.getJsonStr();
    }
    
    
    /**
     * @discription 根据时间统计记账高值耗材总数
     * @author liukui
     * @created 2018年7月13日 下午3:01:40
     * @param map
     * @return
     */
    @RequestMapping(value = "/cntInstrumentUseByOperat")
    @ResponseBody
    @ApiOperation(value = "根据时间统计记账高值耗材总数", httpMethod = "POST", notes = "根据时间统计记账高值耗材总数")
    public String cntInstrumentUseByOperat(
        @ApiParam(name = "map", value = "根据时间统计记账高值耗材总数")@RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin cntInstrumentUseByOperat");
        ResponseValue resp = new ResponseValue();
        List<BasConsumablesOutRecordFormBean> resultList = basConsumablesStorageService.cntInstrumentUseByOperat(systemSearchFormBean);
        int total = basConsumablesStorageService.cntInstrumentUseByOperatTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end cntInstrumentUseByOperat");
        return resp.getJsonStr();
    }
    
}
