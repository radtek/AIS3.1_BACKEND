package com.digihealth.anesthesia.doc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.PostFollowRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptLatterDiag;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.digihealth.anesthesia.sysMng.po.BasUser;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 术后麻醉镇痛单
 * @author dell
 *
 */

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocPostFollowRecordController", description = "术后麻醉镇痛单处理类")
public class DocPostFollowRecordController extends BaseController {
	
	
	@RequestMapping(value = "/getPostFollowRecord")
    @ResponseBody
    @ApiOperation(value="术后麻醉镇痛单查询",httpMethod="POST",notes="术后麻醉镇痛单查询")
    public String getPostFollowRecord(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map){
        
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId").toString();
        PostFollowRecordFormBean postFollowRecordFormBean = docPostFollowRecordService.getPostFollowRecord(regOptId);
        
        resp.put("postFollowRecord", postFollowRecordFormBean);
        
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(docAnaesRecordService.getAnaesRecord(regOptId).getAnaRecordId());
        //实施手术
        List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
        String optRealOperStr = "";
        for (EvtOptRealOper optRealOper : optRealOperList) {
            optRealOperStr += optRealOper.getName()+",";
        }
        if(StringUtils.isNotBlank(optRealOperStr)){
            optRealOperStr =  optRealOperStr.substring(0, optRealOperStr.length()-1);
        }
        List<EvtRealAnaesMethod> realAnaesMethodList = evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
        String realAnaesMethodStr = "";
        for (EvtRealAnaesMethod realAnaesMethod : realAnaesMethodList)
        {
            realAnaesMethodStr +=  realAnaesMethod.getName()+",";
        }
        if (StringUtils.isNotBlank(realAnaesMethodStr))
        {
            realAnaesMethodStr = realAnaesMethodStr.substring(0, realAnaesMethodStr.length() - 1);
        }
        
        //术后诊断
        String optLatterDiag = "";
        List<EvtOptLatterDiag> evtLatterDiagList = evtOptLatterDiagService.searchOptLatterDiagList(searchBean);
        if(null != evtLatterDiagList && evtLatterDiagList.size()>0)
        {
        	for(EvtOptLatterDiag evtOptLatterDiag : evtLatterDiagList)
        	{
        		optLatterDiag += evtOptLatterDiag.getName()+",";
        	}
        }
        if (StringUtils.isNotBlank(optLatterDiag))
        {
        	optLatterDiag = optLatterDiag.substring(0, optLatterDiag.length() - 1);
        }
        
        BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
        BasUser user = basUserService.searchUserById(dispatch.getAnesthetistId(), getBeid());
        
        DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(regOptId);
        String anesthetistId = "";
        if (null == postFollowRecordFormBean.getPostFollowRecord().getAnesthetistId());
        {
            anesthetistId = dispatch.getAnesthetistId();
        }
        if ("NO_END".equals(postFollowRecordFormBean.getPostFollowRecord().getProcessState()))
        {
            DocAnaesRecord ansRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            String docId = ansRecord.getAnaRecordId();
            SearchFormBean sb = new SearchFormBean();
            sb.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(sb);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                anesthetistId = shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId();
            }
        }
        if (null == postFollowRecordFormBean.getPostFollowRecord().getDocDate())
        {
            postFollowRecordFormBean.getPostFollowRecord().setDocDate(DateUtils.getDate());
        }
        
        //AnaesSummaryItemFormbean formbean = anaesSummaryService.getAnaesSummaryDetail(regOptId);
        resp.put("analgesicType", anaesRecord.getPatAnalgesia());
        resp.put("analgesicMethod", anaesRecord.getAnalgesicMethod());
        // 根据患者id获取到手术基本信息
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        resp.put("regOpt", searchRegOptByIdFormBean);
        resp.put("anesthetistId", anesthetistId);
        resp.put("anesthetistName", user!=null?user.getName():"");
        resp.put("optRealOperStr", optRealOperStr);
        resp.put("realAnaesMethodStr", realAnaesMethodStr);
        resp.put("optLatterDiag", optLatterDiag);
        resp.put("operStartTime", anaesRecord.getOperStartTime());
        resp.put("operEndTime", anaesRecord.getOperEndTime()); 
        return resp.getJsonStr();
    }
    
	
	@RequestMapping(value = "/savePostFollowRecord")
	@ResponseBody
	@ApiOperation(value="保存术后麻醉镇痛单",httpMethod="POST",notes="保存术后麻醉镇痛单")
	public String savePostFollowRecord(@ApiParam(name="record", value ="保存参数") @RequestBody PostFollowRecordFormBean record){
		ResponseValue resp = new ResponseValue();
		docPostFollowRecordService.savePostFollowRecord(record);
		return resp.getJsonStr();
	}
}
