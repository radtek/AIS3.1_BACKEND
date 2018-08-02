package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocDifficultCaseDiscuss;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocDifficultCaseDiscussController",description="疑难病人讨论记录处理类")
public class DocDifficultCaseDiscussController extends BaseController
{
    /** 
     * 根据手术ID获取疑难病人讨论记录
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchDifficultCaseDiscuss")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取疑难病人讨论记录",httpMethod="POST",notes="根据手术ID获取疑难病人讨论记录")
    public String searchDifficultCaseDiscuss(@ApiParam(name="map", value ="查询参数") @RequestBody Map map)
    {
        logger.info("===========================begin searchDifficultCaseDiscuss=============================");
        ResponseValue req = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        req.put("result", docDifficultCaseDiscussService.selectByRegOptId(regOptId));
        req.put("regOpt", basRegOptService.searchApplicationById(regOptId));
        logger.info("===========================end searchDifficultCaseDiscuss===============================");
        return req.getJsonStr();
    }
    
    /** 
     * 更新疑难病人讨论记录
     * <功能详细描述>
     * @param difficultCaseDiscuss
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateDifficultCaseDiscuss")
    @ResponseBody
    @ApiOperation(value="更新疑难病人讨论记录",httpMethod="POST",notes="更新疑难病人讨论记录")
    public String updateDifficultCaseDiscuss(@ApiParam(name="difficultCaseDiscuss", value ="疑难病人讨论记录对象") @RequestBody DocDifficultCaseDiscuss difficultCaseDiscuss)
    {
        logger.info("===========================begin updateDifficultCaseDiscuss=============================");
        ResponseValue req = new ResponseValue();
        if (null == difficultCaseDiscuss)
        {
            req.setResultCode("100000000");
            req.setResultMessage("疑难病人讨论记录文书不存在");
            logger.info("=============end updateDifficultCaseDiscuss ：疑难病人讨论记录文书不存在================");
            return req.getJsonStr();
        }
        docDifficultCaseDiscussService.updateDifficultCaseDiscuss(difficultCaseDiscuss);
        logger.info("===========================end updateDifficultCaseDiscuss===============================");
        return req.getJsonStr();
    }
}
