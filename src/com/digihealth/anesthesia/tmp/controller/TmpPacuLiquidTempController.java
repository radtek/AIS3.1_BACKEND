package com.digihealth.anesthesia.tmp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.tmp.po.TmpPacuLiquidTemp;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/basedata")
@Api(value = "TmpPacuLiquidTempController", description = "PACU观察记录加模版处理类")
public class TmpPacuLiquidTempController extends BaseController
{
	
    @RequestMapping(value = "/queryPacuLiquidTempList")
    @ResponseBody
    @ApiOperation(value = "查询PACU观察记录加模版", httpMethod = "POST", notes = "查询PACU观察记录加模版")
    public String queryPacuLiquidTempList(@ApiParam(name = "searchLiquidTempFormBean", value = "模板查询对象") @RequestBody SearchLiquidTempFormBean searchLiquidTempFormBean)
    {
        logger.info("----------------begin queryPacuLiquidTempList----------------------");
        ResponseValue resp = new ResponseValue();
        List<TmpPacuLiquidTemp> pacuLiquidTempList = tmpPacuLiquidTempService.queryLiquidTempList(searchLiquidTempFormBean);
        Integer total = tmpPacuLiquidTempService.queryLiquidTempTotal(searchLiquidTempFormBean);
        resp.put("pacuLiquidTempList", pacuLiquidTempList);
        resp.put("total", total);
        logger.info("----------------end queryPacuLiquidTempList----------------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateLiquidTemp")
    @ResponseBody
    @ApiOperation(value = "更新PACU观察记录加模版", httpMethod = "POST", notes = "更新PACU观察记录加模版")
    public String updateLiquidTemp(@ApiParam(name = "TmpPacuLiquidTemp", value = "模板更新对象") @RequestBody TmpPacuLiquidTemp TmpPacuLiquidTemp)
    {
        logger.info("----------------begin updateLiquidTemp----------------------");
        ResponseValue resp = new ResponseValue();
        tmpPacuLiquidTempService.updateLiquidTemp(TmpPacuLiquidTemp);
        logger.info("----------------end updateLiquidTemp----------------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/deleteLiquidTemp")
    @ResponseBody
    @ApiOperation(value = "删除PACU观察记录加模版", httpMethod = "POST", notes = "删除PACU观察记录加模版")
    public String deleteLiquidTemp(@ApiParam(name = "TmpPacuLiquidTemp", value = "模板删除对象") @RequestBody TmpPacuLiquidTemp pacuLiquidTemp)
    {
        logger.info("----------------begin deleteLiquidTemp----------------------");
        ResponseValue resp = new ResponseValue();
        tmpPacuLiquidTempService.deleteLiquidTemp(pacuLiquidTemp);
        logger.info("----------------end deleteLiquidTemp----------------------");
        return resp.getJsonStr();
    }
}
