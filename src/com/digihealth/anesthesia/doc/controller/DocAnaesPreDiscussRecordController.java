package com.digihealth.anesthesia.doc.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesPreDiscussRecord;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocAnaesPreDiscussRecordController",description="麻醉前讨论记录处理类")
public class DocAnaesPreDiscussRecordController extends BaseController
{
    /** 
     * 查询麻醉计划
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchDocAnaesPreDiscussRecord")
    @ResponseBody
	@ApiOperation(value="查询麻醉前讨论记录",httpMethod="POST",notes="查询麻醉前讨论记录")
    public String searchDocAnaesPreDiscussRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        String regOptId = null != map.get("regOptId") ? map.get("regOptId").toString() : "";
        logger.debug("------------------searchDocAnaesPreDiscussRecord begin------------------");
        ResponseValue resp = new ResponseValue();
        DocAnaesPreDiscussRecord preDiscussRecord = docAnaesPreDiscussRecordService.searchPreDiscussRecord(regOptId);
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
        resp.put("preDiscussRecord", preDiscussRecord);
        resp.setResultMessage("麻醉前讨论记录查询成功!");
        logger.debug("------------------searchDocAnaesPreDiscussRecord end------------------");
        return resp.getJsonStr();
    }
    
    /** 
     * 更新保存麻醉前讨论记录
     * <功能详细描述>
     * @param anaesPlan
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/savePreDiscussRecord")
    @ResponseBody
	@ApiOperation(value="更新保存麻醉前讨论记录",httpMethod="POST",notes="更新保存麻醉前讨论记录")
    public String savePreDiscussRecord(@ApiParam(name="anaesPlan", value ="麻醉前讨论记录参数") @RequestBody DocAnaesPreDiscussRecord record) {
        logger.debug("------------------savePreDiscussRecord begin------------------");
        ResponseValue resp = new ResponseValue();
        docAnaesPreDiscussRecordService.updatePreDiscussRecord(record);
        resp.setResultCode("1");
        resp.setResultMessage("麻醉前讨论记录单更新成功!");
        logger.debug("------------------savePreDiscussRecord end------------------");
        return resp.getJsonStr();
    }
}
