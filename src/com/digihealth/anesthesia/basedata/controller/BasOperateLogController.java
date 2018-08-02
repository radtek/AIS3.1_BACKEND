package com.digihealth.anesthesia.basedata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasOperateLog;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "BasOperateLogController", description = "日志处理类")
public class BasOperateLogController extends BaseController
{
    @RequestMapping(value = "/saveBasOperateLog")
    @ResponseBody
    @ApiOperation(value = "保存日志", httpMethod = "POST", notes = "保存日志")
    public String saveBasOperateLog(@ApiParam(name="operateLog", value ="系统查询参数") @RequestBody BasOperateLog operateLog)
    {
        logger.info("begin saveBasOperateLog");
        ResponseValue resp = new ResponseValue();
        basOperateLogService.saveBasOperateLog(operateLog);
        logger.info("end saveBasOperateLog");
        return resp.getJsonStr();
    }
}
