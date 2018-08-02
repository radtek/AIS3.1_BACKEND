package com.digihealth.anesthesia.doc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesMethodChangeRecord;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocAnaesMethodChangeRecordController",description="麻醉方法变更处理类")
public class DocAnaesMethodChangeRecordController extends BaseController
{
    /** 
     * 查询麻醉计划
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchAnaesMethodChangeRecord")
    @ResponseBody
	@ApiOperation(value="查询麻醉方法变更记录",httpMethod="POST",notes="查询麻醉方法变更")
    public String searchAnaesMethodChangeRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        String regOptId = null != map.get("regOptId") ? map.get("regOptId").toString() : "";
        logger.debug("------------------searchAnaesMethodChangeRecord begin------------------");
        ResponseValue resp = new ResponseValue();
        DocAnaesMethodChangeRecord methodChangeRecord = docAnaesMethodChangeRecordService.searchAnaesMethodChangeRecord(regOptId);
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        if (null == methodChangeRecord) {
        	methodChangeRecord = new DocAnaesMethodChangeRecord();
        }
        resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
        resp.put("methodChangeRecord", methodChangeRecord);
        resp.setResultMessage("麻醉方法变更查询成功!");
        logger.debug("------------------searchAnaesMethodChangeRecord end------------------");
        return resp.getJsonStr();
    }
    
    /** 
     * 更新保存麻醉前讨论记录
     * <功能详细描述>
     * @param anaesPlan
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveAnaesMethodChange")
    @ResponseBody
	@ApiOperation(value="更新保存麻醉方法变更",httpMethod="POST",notes="更新保存麻醉方法变更论记录")
    public String saveAnaesMethodChange(@ApiParam(name="anaesPlan", value ="麻醉麻醉方法变更参数") @RequestBody DocAnaesMethodChangeRecord record) {
        logger.debug("------------------saveAnaesMethodChange begin------------------");
        ResponseValue resp = new ResponseValue();
        docAnaesMethodChangeRecordService.updateAnaesMethodChange(record);
        resp.setResultCode("1");
        resp.setResultMessage("麻醉方法变更单更新成功!");
        logger.debug("------------------saveAnaesMethodChange end------------------");
        return resp.getJsonStr();
    }
}
