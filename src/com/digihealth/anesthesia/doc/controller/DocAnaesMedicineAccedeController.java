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
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocAnaesMedicineAccedeController",description="手术麻醉使用药品知情同意书处理类")
public class DocAnaesMedicineAccedeController extends BaseController
{
    /** 
     * 根据手术ID获取手术麻醉使用药品知情同意书
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/searchAnaesMedicineAccede")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术麻醉使用药品知情同意书",httpMethod="POST",notes="根据手术ID获取手术麻醉使用药品知情同意书")
    public String searchAnaesMedicineAccede(@ApiParam(name = "map", value = "查询参数") @RequestBody Map map)
    {
        logger.info("-----------------start searchAnaesMedicineAccede-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        DocAnaesMedicineAccede anaesMedicineAccede = docAnaesMedicineAccedeService.selectByRegOptId(regOptId);
        if (anaesMedicineAccede == null) {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术麻醉使用药品知情同意书不存在");
            return resp.getJsonStr();
        }
        
        if (null == anaesMedicineAccede.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople =
                basDispatchService.getDispatchOperByRegOptId(regOptId);
            anaesMedicineAccede.setAnaestheitistId(dispatchPeople.getAnesthetistId());
        }
        
        if ("NO_END".equals(anaesMedicineAccede.getProcessState()))
        {
            DocAnaesRecord ansRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            String docId = ansRecord.getAnaRecordId();
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                anaesMedicineAccede.setAnaestheitistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        if (null == anaesMedicineAccede.getDate())
        {
            anaesMedicineAccede.setDate(DateUtils.getDate());
        }
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("anaesMedicineAccede", anaesMedicineAccede);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        
        logger.info("-----------------end searchAnaesMedicineAccede-----------------");
        return resp.getJsonStr();
    }
    
    
    /** 
     * 修改手术麻醉使用药品知情同意书
     * <功能详细描述>
     * @param anaesMedicineAccede
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateAnaesMedicineAccede")
    @ResponseBody
    @ApiOperation(value = "修改手术麻醉使用药品知情同意书", httpMethod = "POST", notes = "修改手术麻醉使用药品知情同意书")
    public String updateAnaesMedicineAccede(@ApiParam(name = "anaesMedicineAccede", value = "修改参数") @RequestBody DocAnaesMedicineAccede anaesMedicineAccede) 
    {
        logger.info("-----------------start updateAnaesMedicineAccede-----------------");
        ResponseValue resp = new ResponseValue();
        if (anaesMedicineAccede == null) {
            resp.setResultCode("30000002");
            resp.setResultMessage("手术麻醉使用药品知情同意书不存在");
            return resp.getJsonStr();
        }
        docAnaesMedicineAccedeService.updateDocAnaesMedicineAccede(anaesMedicineAccede);
        logger.info("-----------------end updateAnaesMedicineAccede-----------------");
        return resp.getJsonStr();
    }
}
