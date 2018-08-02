package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocVeinAccede;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocVeinAccedeController",description="静脉麻醉知情同意书处理类")
public class DocVeinAccedeController extends BaseController
{
    @RequestMapping(value = "/searchDocVeinAccedeByRegOptId")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取静脉麻醉知情同意书",httpMethod="POST",notes="根据手术ID获取静脉麻醉知情同意书")
    public String searchDocVeinAccedeByRegOptId(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map) 
    {
        logger.info("begin searchDocVeinAccedeByRegOptId");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        DocVeinAccede docVeinAccede = docVeinAccedeService.searchByRegOptId(regOptId);
        if (null == docVeinAccede)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("静脉麻醉知情同意书不存在");
            return resp.getJsonStr();
        }
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        resp.put("docVeinAccede", docVeinAccede);
        
        logger.info("end searchDocVeinAccedeByRegOptId");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateDocVeinAccede")
    @ResponseBody
    @ApiOperation(value="更新静脉麻醉知情同意书",httpMethod="POST",notes="更新静脉麻醉知情同意书")
    public String updateDocVeinAccede(@ApiParam(name = "docVeinAccede", value = "静脉麻醉知情同意书对象") @RequestBody DocVeinAccede docVeinAccede)
    {
        logger.info("begin updateDocVeinAccede");
        ResponseValue resp = new ResponseValue();
        if (null == docVeinAccede)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("静脉麻醉知情同意书不存在");
            return resp.getJsonStr();
        }
        docVeinAccedeService.updateDocVeinAccede(docVeinAccede);
        logger.info("end updateDocVeinAccede");
        return resp.getJsonStr();
    }
}
