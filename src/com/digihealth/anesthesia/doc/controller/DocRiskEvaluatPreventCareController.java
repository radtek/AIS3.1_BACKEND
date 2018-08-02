package com.digihealth.anesthesia.doc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocRiskEvaluatPreventCare;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocRiskEvaluatPreventCareController",description="手术病人术前风险评估及预防护理记录单处理类")
public class DocRiskEvaluatPreventCareController extends BaseController
{
    @RequestMapping(value = "/searchRiskEvaluatPreventCare")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术病人术前风险评估及预防护理记录单",httpMethod="POST",notes="根据手术ID获取手术病人术前风险评估及预防护理记录单")
    public String searchRiskEvaluatPreventCare(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map)
    {
        logger.info("begin searchRiskEvaluatPreventCare");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        DocRiskEvaluatPreventCare docRiskEvaluatPreventCare = docRiskEvaluatPreventCareService.searchByRegOptId(regOptId);
        if (null == docRiskEvaluatPreventCare)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术病人术前风险评估及预防护理记录单不存在");
            return resp.getJsonStr();
        }
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        resp.put("docRiskEvaluatPreventCare", docRiskEvaluatPreventCare);
        
        logger.info("end searchRiskEvaluatPreventCare");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateRiskEvaluatPreventCare")
    @ResponseBody
    @ApiOperation(value="更新手术病人术前风险评估及预防护理记录单",httpMethod="POST",notes="更新手术病人术前风险评估及预防护理记录单")
    public String updateRiskEvaluatPreventCare(@ApiParam(name = "docRiskEvaluatPreventCare", value = "手术病人术前风险评估及预防护理记录单对象") @RequestBody DocRiskEvaluatPreventCare docRiskEvaluatPreventCare)
    {
        logger.info("begin updateRiskEvaluatPreventCare");
        ResponseValue resp = new ResponseValue();
        if (null == docRiskEvaluatPreventCare)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术病人术前风险评估及预防护理记录单不存在");
            return resp.getJsonStr();
        }
        docRiskEvaluatPreventCareService.updateRiskEvaluatPreventCare(docRiskEvaluatPreventCare);
        logger.info("end updateRiskEvaluatPreventCare");
        return resp.getJsonStr();
    }
}
