package com.digihealth.anesthesia.doc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.SelfPayInstrumentAccedeFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentItem;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocSelfPayInstrumentAccedeController",description="手术麻醉使用自费及高价耗材知情同意书处理类")
public class DocSelfPayInstrumentAccedeController extends BaseController
{
    @RequestMapping(value = "/searchSelfPayInstrumentAccede")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术麻醉使用自费及高价耗材知情同意书",httpMethod="POST",notes="根据手术ID获取手术麻醉使用自费及高价耗材知情同意书")
    public String searchSelfPayInstrumentAccede(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map)
    {
        logger.info("-----------------start searchSelfPayInstrumentAccede-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        SelfPayInstrumentAccedeFormbean selfPayInstrumentAccedeFormbean = docSelfPayInstrumentAccedeService.selectByRegOptId(regOptId);
        DocSelfPayInstrumentAccede selfPayInstrumentAccede = selfPayInstrumentAccedeFormbean.getDocSelfPayInstrumentAccede();
        List<DocSelfPayInstrumentItem> selfPayInstrumentItems = selfPayInstrumentAccedeFormbean.getDocSelfPayInstrumentItems();
        if (null == selfPayInstrumentAccede)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术麻醉使用自费及高价耗材知情同意书不存在");
            return resp.getJsonStr();
        }
        
        
        if (null == selfPayInstrumentAccede.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople =
                basDispatchService.getDispatchOperByRegOptId(regOptId);
            selfPayInstrumentAccede.setAnaestheitistId(dispatchPeople.getAnesthetistId());
        }
        
        if ("NO_END".equals(selfPayInstrumentAccede.getProcessState()))
        {
            DocAnaesRecord ansRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            String docId = ansRecord.getAnaRecordId();
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                selfPayInstrumentAccede.setAnaestheitistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        if (null == selfPayInstrumentAccede.getDate())
        {
            selfPayInstrumentAccede.setDate(DateUtils.getDate());
        }
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("selfPayInstrumentAccede", selfPayInstrumentAccede);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        resp.put("selfPayInstrumentItems", selfPayInstrumentItems);
        logger.info("-----------------end searchSelfPayInstrumentAccede-----------------");
        return resp.getJsonStr();
    }
    
    
    @RequestMapping(value = "/updateSelfPayInstrumentAccede")
    @ResponseBody
    @ApiOperation(value = "修改手术麻醉使用自费及高价耗材知情同意书", httpMethod = "POST", notes = "修改手术麻醉使用自费及高价耗材知情同意书")
    public String updateSelfPayInstrumentAccede(@ApiParam(name = "selfPayInstrumentAccede", value = "更新参数") @RequestBody DocSelfPayInstrumentAccede selfPayInstrumentAccede)
    {
        logger.info("-----------------start updateSelfPayInstrumentAccede-----------------");
        ResponseValue resp = new ResponseValue();
        if (null == selfPayInstrumentAccede)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术麻醉使用自费及高价耗材知情同意书不存在");
            return resp.getJsonStr();
        }
        docSelfPayInstrumentAccedeService.updateSelfPayInstrumentAccede(selfPayInstrumentAccede);
        logger.info("-----------------end updateSelfPayInstrumentAccede-----------------");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/updateSelfPayInstrumentItem")
    @ResponseBody
    @ApiOperation(value = "修改手术麻醉使用自费及高价耗材项目", httpMethod = "POST", notes = "修改手术麻醉使用自费及高价耗材项目")
    public String updateSelfPayInstrumentItem(@ApiParam(name = "selfPayInstrumentAccede", value = "更新参数") @RequestBody DocSelfPayInstrumentItem docSelfPayInstrumentItem)
    {
        logger.info("-----------------start updateSelfPayInstrumentItem-----------------");
        ResponseValue resp = new ResponseValue();
        if (null == docSelfPayInstrumentItem)
        {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术麻醉使用自费及高价耗材项目不存在");
            return resp.getJsonStr();
        }
        docSelfPayInstrumentAccedeService.updateSelfPayInstrumentItem(docSelfPayInstrumentItem);
        logger.info("-----------------end updateSelfPayInstrumentItem-----------------");
        return resp.getJsonStr();
    }
    
    
    
    @RequestMapping(value = "/initSelfPayInstrumentItem")
    @ResponseBody
    @ApiOperation(value = "初始化手术麻醉使用自费及高价耗材项目", httpMethod = "POST", notes = "初始化手术麻醉使用自费及高价耗材项目")
    public String initSelfPayInstrumentItem(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map)
    {
        logger.info("-----------------start initSelfPayInstrumentItem-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        SelfPayInstrumentAccedeFormbean formbean = docSelfPayInstrumentAccedeService.init(regOptId);
        resp.put("selfPayInstrumentItems", formbean.getDocSelfPayInstrumentItems());
        resp.put("selfPayInstrumentAccede", formbean.getDocSelfPayInstrumentAccede());
        logger.info("-----------------end initSelfPayInstrumentItem-----------------");
        return resp.getJsonStr();
    }
}
