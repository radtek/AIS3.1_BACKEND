package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocPreAnaesTalkRecord;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocPreAnaesTalkRecordController",description="麻醉前访视和谈话记录处理类")
public class DocPreAnaesTalkRecordController extends BaseController
{
    /** 
     * 根据手术ID获取麻醉前访视和谈话记录
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchPreAnaesTalkRecord")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取麻醉前访视和谈话记录",httpMethod="POST",notes="根据手术ID获取麻醉前访视和谈话记录")
    public String searchPreAnaesTalkRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map map)
    {
        logger.info("===========================begin searchPreAnaesTalkRecord=============================");
        ResponseValue req = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        DocPreAnaesTalkRecord preAnaesTalkRecord =  docPreAnaesTalkRecordService.selectByRegOptId(regOptId);
        req.put("preAnaesTalkRecord", preAnaesTalkRecord == null ?new DocPreAnaesTalkRecord():preAnaesTalkRecord);
        req.put("regOpt", basRegOptService.searchApplicationById(regOptId));
        logger.info("===========================end searchPreAnaesTalkRecord===============================");
        return req.getJsonStr();
    }
    
    /** 
     * 麻醉前访视和谈话记录
     * <功能详细描述>
     * @param patRescurRecord
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updatePreAnaesTalkRecord")
    @ResponseBody
    @ApiOperation(value="麻醉前访视和谈话记录",httpMethod="POST",notes="麻醉前访视和谈话记录")
    public String updatePreAnaesTalkRecord(@ApiParam(name="patRescurRecord", value ="麻醉前访视和谈话记录对象") @RequestBody DocPreAnaesTalkRecord record)
    {
        logger.info("===========================begin updatePreAnaesTalkRecord=============================");
        ResponseValue req = new ResponseValue();
        if (null == record)
        {
            req.setResultCode("100000000");
            req.setResultMessage("麻醉前访视和谈话记录文书不存在");
            logger.info("=============end updatePreAnaesTalkRecord ：麻醉前访视和谈话记录文书不存在================");
            return req.getJsonStr();
        }
        docPreAnaesTalkRecordService.updatePreAnaesTalkRecord(record);
        logger.info("===========================end updatePreAnaesTalkRecord===============================");
        return req.getJsonStr();
    }
}
