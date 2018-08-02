package com.digihealth.anesthesia.basedata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasSelfPayInstrument;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasSelfPayInstrumentController", description = "自费耗材处理类")
public class BasSelfPayInstrumentController extends BaseController
{
    /** 
     * 查询自费耗材信息
     * <功能详细描述>
     * @param baseQuery
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchSelfPayInstrument")
    @ResponseBody
    @ApiOperation(value = "查询自费耗材信息", httpMethod = "POST", notes = "查询自费耗材信息")
    public String searchSelfPayInstrument(@ApiParam(name = "baseQuery", value = "系统查询对象") @RequestBody BaseInfoQuery baseQuery)
    {
        logger.info("begin searchSelfPayInstrument");
        ResponseValue resp = new ResponseValue();
        List<BasSelfPayInstrument> resultList = basSelfPayInstrumentService.searchSelfPayInstrument(baseQuery);
        resp.put("resultList", resultList);
        logger.info("end searchSelfPayInstrument");
        return resp.getJsonStr();
    }
    
    /** 
     * 根据条件查询自费耗材
     * <功能详细描述>
     * @param systemSearchFormBean
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/querySelfPayInstrumentList")
    @ResponseBody
    @ApiOperation(value = "根据条件查询自费耗材信息", httpMethod = "POST", notes = "根据条件查询自费耗材信息")
    public String querySelfPayInstrumentList(@ApiParam(name = "systemSearchFormBean", value = "系统查询对象") @RequestBody SystemSearchFormBean systemSearchFormBean)
    {
        logger.info("begin querySelfPayInstrumentList");
        ResponseValue resp = new ResponseValue();
        List<BasSelfPayInstrument> resultList = basSelfPayInstrumentService.querySelfPayInstrumentList(systemSearchFormBean);
        int total = basSelfPayInstrumentService.querySelfPayInstrumentTotal(systemSearchFormBean);
        resp.put("resultList", resultList);
        resp.put("total", total);
        logger.info("end querySelfPayInstrumentList");
        return resp.getJsonStr();
    }
    
    /** 
     * 查询单个自费耗材信息
     * <功能详细描述>
     * @param selfPayInstrument
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/querySelfPayInstrumentById")
    @ResponseBody
    @ApiOperation(value = "查询单个自费耗材信息", httpMethod = "POST", notes = "查询单个自费耗材信息")
    public String querySelfPayInstrumentById(
        @ApiParam(name = "selfPayInstrument", value = "自费耗材对象") @RequestBody BasSelfPayInstrument selfPayInstrument)
    {
        logger.info("begin querySelfPayInstrumentById");
        ResponseValue resp = new ResponseValue();
        BasSelfPayInstrument result = basSelfPayInstrumentService.searchSelfPayInstrumentById(selfPayInstrument.getId());
        resp.put("selfPayInstrument", result);
        logger.info("end querySelfPayInstrumentById");
        return resp.getJsonStr();
    }
    
    /** 
     * 更新自费耗材信息
     * <功能详细描述>
     * @param selfPayInstrument
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateSelfPayInstrument")
    @ResponseBody
    @ApiOperation(value = "更新自费耗材信息", httpMethod = "POST", notes = "更新自费耗材信息")
    public String updateSelfPayInstrument(@ApiParam(name = "selfPayInstrument", value = "自费耗材对象") @RequestBody BasSelfPayInstrument selfPayInstrument)
    {
        logger.info("begin updateSelfPayInstrument");
        ResponseValue resp = new ResponseValue();
        basSelfPayInstrumentService.updateInstrument(selfPayInstrument);
        logger.info("end updateSelfPayInstrument");
        return resp.getJsonStr();
    }
    
    
    /** 
     * 删除自费耗材信息
     * <功能详细描述>
     * @param selfPayInstrument
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/deleteSelfPayInstrument")
    @ResponseBody
    @ApiOperation(value = "删除自费耗材信息", httpMethod = "POST", notes = "删除自费耗材信息")
    public String deleteSelfPayInstrument(@ApiParam(name = "selfPayInstrument", value = "自费耗材对象") @RequestBody BasSelfPayInstrument selfPayInstrument)
    {
        logger.info("begin deleteSelfPayInstrument");
        ResponseValue resp = new ResponseValue();
        basSelfPayInstrumentService.deleteSelfPayInstrument(selfPayInstrument);
        logger.info("end deleteSelfPayInstrument");
        return resp.getJsonStr();
    }
}
