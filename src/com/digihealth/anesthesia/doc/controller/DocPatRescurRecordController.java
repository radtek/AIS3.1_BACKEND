package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocPatRescurRecord;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocPatRescurRecordController",description="危重病人抢救记录处理类")
public class DocPatRescurRecordController extends BaseController
{
    /** 
     * 根据手术ID获取危重病人抢救记录
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchPatRescurRecord")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取危重病人抢救记录",httpMethod="POST",notes="根据手术ID获取危重病人抢救记录")
    public String searchPatRescurRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map map)
    {
        logger.info("===========================begin searchPatRescurRecord=============================");
        ResponseValue req = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        req.put("result", docPatRescurRecordService.selectByRegOptId(regOptId));
        req.put("regOpt", basRegOptService.searchApplicationById(regOptId));
        logger.info("===========================end searchPatRescurRecord===============================");
        return req.getJsonStr();
    }
    
    /** 
     * 更新危重病人抢救记录
     * <功能详细描述>
     * @param patRescurRecord
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updatePatRescurRecord")
    @ResponseBody
    @ApiOperation(value="更新危重病人抢救记录",httpMethod="POST",notes="更新危重病人抢救记录")
    public String updatePatRescurRecord(@ApiParam(name="patRescurRecord", value ="危重病人抢救记录对象") @RequestBody DocPatRescurRecord patRescurRecord)
    {
        logger.info("===========================begin searchPatRescurRecord=============================");
        ResponseValue req = new ResponseValue();
        if (null == patRescurRecord)
        {
            req.setResultCode("100000000");
            req.setResultMessage("危重病人抢救记录文书不存在");
            logger.info("=============end updatePatRescurRecord ：危重病人抢救记录文书不存在================");
            return req.getJsonStr();
        }
        docPatRescurRecordService.updatePatRescurRecord(patRescurRecord);
        logger.info("===========================end searchPatRescurRecord===============================");
        return req.getJsonStr();
    }
}
