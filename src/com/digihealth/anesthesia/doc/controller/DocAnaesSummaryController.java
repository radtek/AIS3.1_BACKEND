/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author liukui       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.AnaesSummaryItemFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptLatterDiag;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: AnaesSummaryController.java Description: 描述
 * 
 * @author liukui
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocAnaesSummaryController",description="麻醉总结处理类")
public class DocAnaesSummaryController extends BaseController {
	
	/**
	 * 查询麻醉总结单
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/searchAnaesSummaryList")
	@ResponseBody
	@ApiOperation(value="查询麻醉总结单",httpMethod="POST",notes="查询麻醉总结单")
	public String searchAnaesSummaryList(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean) {
		logger.info("-------------begin searchAnaesSummaryList-------------");
		ResponseValue resp = new ResponseValue();
		List<DocAnaesSummary> anaesSummaryList = docAnaesSummaryService.searchAnaesSummaryList(searchBean);
		resp.put("resultList", anaesSummaryList);
		logger.info("-------------end searchAnaesSummaryList-------------");
		return resp.getJsonStr();
	}
	/**
	 * 获取麻醉总结明细信息
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/searchAnaesSummaryDetail")
	@ResponseBody
	@ApiOperation(value="获取麻醉总结明细信息",httpMethod="POST",notes="获取麻醉总结明细信息")
	public String searchAnaesSummaryDetail(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean){
		logger.info("-------------begin searchAnaesSummaryDetail-------------");
		ResponseValue result = new ResponseValue();

		//手术信息表
		BasRegOpt opt = basRegOptService.searchRegOptById(searchBean.getRegOptId());
	
		//麻醉总结数据
		result.put("anaesSummaryFormbean", docAnaesSummaryService.getAnaesSummaryDetail(searchBean.getRegOptId()));
		result.put("regOpt", opt);	
		//血型
		String blood = "";
		//出入量数据
		DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(searchBean.getRegOptId());
		if(null != anaesRecord)
		{
			docAnaesSummaryService.getAmountDetail(result, anaesRecord.getAnaRecordId());
			blood = evtInEventService.getBloodByDocId(anaesRecord.getAnaRecordId());
			searchBean.setDocId(anaesRecord.getAnaRecordId());
		}
		result.put("blood", blood);	
		
		List<SysCodeFormbean> puncturePointList =  basSysCodeService.searchSysCodeByGroupId("puncture_point", searchBean.getBeid());
        List<SysCodeFormbean> laryngealMaskList =  basSysCodeService.searchSysCodeByGroupId("laryngeal_mask_model", searchBean.getBeid());
        List<SysCodeFormbean> anesthesiaModelList =  basSysCodeService.searchSysCodeByGroupId("anesthesia_model", searchBean.getBeid());
        List<SysCodeFormbean> needleBevelList =  basSysCodeService.searchSysCodeByGroupId("needle_bevel", searchBean.getBeid());
        List<SysCodeFormbean> negativePressList =  basSysCodeService.searchSysCodeByGroupId("negative_press", searchBean.getBeid());
        List<SysCodeFormbean> analgesiaPumpList =  basSysCodeService.searchSysCodeByGroupId("analgesia_pump", searchBean.getBeid());
        
        result.put("puncturePointList", puncturePointList);//穿刺点
        result.put("laryngealMaskList", laryngealMaskList);//喉罩型号
        result.put("anesthesiaModelList", anesthesiaModelList);//全麻型号
        result.put("needleBevelList", needleBevelList);//针斜面
        result.put("negativePressList", negativePressList);//负压测试
        result.put("analgesiaPumpList", analgesiaPumpList);//病人自控镇痛泵
        
		logger.info("-------------end searchAnaesSummaryDetail-------------");
		return result.getJsonStr();
	}
	
	/**
     * 获取麻醉总结明细信息
     * @param searchBean
     * @return
     */
    @RequestMapping(value = "/searchAnaesSummaryDetailYXRM")
    @ResponseBody
    @ApiOperation(value="获取麻醉总结明细信息",httpMethod="POST",notes="获取麻醉总结明细信息")
    public String searchAnaesSummaryDetailForNHFE(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean){
        logger.info("-------------begin searchAnaesSummaryDetail YXRM-------------");
        ResponseValue result = new ResponseValue();

        //手术信息表
        BasRegOpt opt = basRegOptService.searchRegOptById(searchBean.getRegOptId());
    
        result.put("regOpt", opt);  
        DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(searchBean.getRegOptId());
        if(null != anaesRecord)
        {
            searchBean.setDocId(anaesRecord.getAnaRecordId());
        }
        //术后诊断
        List<EvtOptLatterDiag> optLatterDiagList = evtOptLatterDiagService.searchOptLatterDiagList(searchBean);
        
        String optLatterDiagStr = "";
        for (EvtOptLatterDiag optLatterDiag : optLatterDiagList) {
            optLatterDiagStr += optLatterDiag.getName()+",";
        }
        if(StringUtils.isNotBlank(optLatterDiagStr)){
            optLatterDiagStr =  optLatterDiagStr.substring(0, optLatterDiagStr.length()-1);
        }
        
        //实施手术
        List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
        String optRealOperStr = "";
        for (EvtOptRealOper optRealOper : optRealOperList) {
            optRealOperStr += optRealOper.getName()+",";
        }
        if(StringUtils.isNotBlank(optRealOperStr)){
            optRealOperStr =  optRealOperStr.substring(0, optRealOperStr.length()-1);
        }
        
        // 实际麻醉方法
        List<EvtRealAnaesMethod> realAnaesList = evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
        String realAnaesStr = "";
        for (EvtRealAnaesMethod realAnaesMethod : realAnaesList) {
            realAnaesStr += realAnaesMethod.getName()+",";
        }
        if(StringUtils.isNotBlank(realAnaesStr)){
            realAnaesStr =  realAnaesStr.substring(0, realAnaesStr.length()-1);
        }
        
        //麻醉总结数据
        List<DocAnaesSummary> anaSumList = docAnaesSummaryService.searchAnaesSummaryList(searchBean);
        DocAnaesSummary anaesSummary = anaSumList.size()>0?anaSumList.get(0):new DocAnaesSummary();
        searchBean.setAnaSumId(anaesSummary.getAnaSumId());
        //椎管内穿刺
        List<DocSpinalCanalPuncture> spinaCanalList = docSpinalCanalPunctureService.searchSpinalCanalPunctureList(searchBean);
        //全身麻醉参数
        List<DocGeneralAnaes> genAnasList = docGeneralAnaesService.searchGeneralAnaesList(searchBean);
        //神经阻滞
        List<DocNerveBlock> nerveBlockList = docNerveBlockService.searchNerveBlockList(searchBean);
        //严重过敏反应
        //List<DocAnaesSummaryAllergicReaction> reacList =  docAnaesSummaryAllergicReactionService.searchAllergicReactionList(searchBean);
        //中心静脉穿刺并发症
        //List<DocAnaesSummaryVenipuncture> veniList =  docAnaesSummaryVenipunctureService.searchVenipunctureList(searchBean);
        //随访记录
        DocAnaesSummaryAppendixVisit docAnaesSummaryAppendixVisit = null;
        AnaesSummaryItemFormbean summaryItem = docAnaesSummaryService.getAnaesSummaryDetail(searchBean.getRegOptId());
        if(null != summaryItem)
        {
        	docAnaesSummaryAppendixVisit = summaryItem.getAnaesSummaryAppendixVisit();
        }
        
        //麻醉医生列表
        List<String> anaesDocList = new ArrayList<String>();
        if (null == anaesSummary.getAnesthetistId())
        {
            DispatchFormbean dispatchPeople = basDispatchService.getDispatchOperByRegOptId(opt.getRegOptId());
            if (dispatchPeople != null)
            {
                anaesSummary.setAnesthetistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId()
                    : "");
            }
        }
        
        if ("NO_END".equals(anaesSummary.getProcessState()))
        {
            String docId = anaesRecord.getAnaRecordId();
            SearchFormBean sb = new SearchFormBean();
            sb.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(sb);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                anaesSummary.setAnesthetistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        anaesSummary.setAnaesDocList(anaesDocList);
        List<SysCodeFormbean> puncturePointList =  basSysCodeService.searchSysCodeByGroupId("puncture_point", searchBean.getBeid());
        result.put("puncturePointList", puncturePointList);//穿刺点
        
        result.put("anaesRecord", anaesRecord);
        result.put("anaesSummary", anaesSummary);
        result.put("optLatterDiagStr", optLatterDiagStr);
        result.put("realAnaesStr", realAnaesStr);
        result.put("optRealOperStr", optRealOperStr);
        result.put("spinalCanalPuncture", spinaCanalList !=null && spinaCanalList.size() > 0 ? spinaCanalList.get(0) : new DocSpinalCanalPuncture());
        result.put("nerveBlock", nerveBlockList != null && nerveBlockList.size() > 0 ? nerveBlockList.get(0): new DocNerveBlock());
        result.put("generalAnaes", genAnasList != null && genAnasList.size() > 0 ? genAnasList.get(0) : new DocGeneralAnaes()); 
        //result.put("allergicReact", reacList != null && reacList.size() > 0 ? reacList.get(0) : new DocAnaesSummaryAllergicReaction()); 
        //result.put("venipuncture", veniList != null && veniList.size() > 0 ? veniList.get(0) : new DocAnaesSummaryVenipuncture());
        result.put("anaesSummaryAppendixVisit", docAnaesSummaryAppendixVisit );
        
        logger.info("-------------end searchAnaesSummaryDetail YXRM-------------");
        return result.getJsonStr();
    }
	
	
	/**
     * 获取麻醉总结明细信息
     * @param searchBean
     * @return
     */
    @RequestMapping(value = "/searchAnaesSummaryDetailForQNZZY")
    @ResponseBody
    @ApiOperation(value="获取麻醉总结明细信息",httpMethod="POST",notes="获取麻醉总结明细信息")
    public String searchAnaesSummaryDetailForQNZZY(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean){
        logger.info("-------------begin searchAnaesSummaryDetail-------------");
        ResponseValue result = new ResponseValue();

        //手术信息表
        BasRegOpt opt = basRegOptService.searchRegOptById(searchBean.getRegOptId());
    
        result.put("regOpt", opt);  
        DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(searchBean.getRegOptId());
        if(null != anaesRecord)
        {
            searchBean.setDocId(anaesRecord.getAnaRecordId());
        }
        //术后诊断
        List<EvtOptLatterDiag> optLatterDiagList = evtOptLatterDiagService.searchOptLatterDiagList(searchBean);
        
        String optLatterDiagStr = "";
        for (EvtOptLatterDiag optLatterDiag : optLatterDiagList) {
            optLatterDiagStr += optLatterDiag.getName()+",";
        }
        if(StringUtils.isNotBlank(optLatterDiagStr)){
            optLatterDiagStr =  optLatterDiagStr.substring(0, optLatterDiagStr.length()-1);
        }
        
        //实施手术
        List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
        String optRealOperStr = "";
        for (EvtOptRealOper optRealOper : optRealOperList) {
            optRealOperStr += optRealOper.getName()+",";
        }
        if(StringUtils.isNotBlank(optRealOperStr)){
            optRealOperStr =  optRealOperStr.substring(0, optRealOperStr.length()-1);
        }
        
        // 实际麻醉方法
        List<EvtRealAnaesMethod> realAnaesList = evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
        String realAnaesStr = "";
        for (EvtRealAnaesMethod realAnaesMethod : realAnaesList) {
            realAnaesStr += realAnaesMethod.getName()+",";
        }
        if(StringUtils.isNotBlank(realAnaesStr)){
            realAnaesStr =  realAnaesStr.substring(0, realAnaesStr.length()-1);
        }
        
        //麻醉总结数据
        List<DocAnaesSummary> anaSumList = docAnaesSummaryService.searchAnaesSummaryList(searchBean);
        DocAnaesSummary anaesSummary = anaSumList.size()>0?anaSumList.get(0):new DocAnaesSummary();
        searchBean.setAnaSumId(anaesSummary.getAnaSumId());
        //椎管内穿刺
        List<DocSpinalCanalPuncture> spinaCanalList = docSpinalCanalPunctureService.searchSpinalCanalPunctureListQNZZY(searchBean);
        //全身麻醉参数
        List<DocGeneralAnaes> genAnasList = docGeneralAnaesService.searchGeneralAnaesList(searchBean);
        //神经阻滞
        List<DocNerveBlock> nerveBlockList = docNerveBlockService.searchNerveBlockList(searchBean);
        
        List<DocAnaesSummaryAllergicReaction> reacList =  docAnaesSummaryAllergicReactionService.searchAllergicReactionList(searchBean);
        
        List<DocAnaesSummaryVenipuncture> veniList =  docAnaesSummaryVenipunctureService.searchVenipunctureList(searchBean);
        //麻醉医生列表
        List<String> anaesDocList = new ArrayList<String>();
        if (null == anaesSummary.getAnesthetistId())
        {
            DispatchFormbean dispatchPeople = basDispatchService.getDispatchOperByRegOptId(opt.getRegOptId());
            if (dispatchPeople != null)
            {
                anaesSummary.setAnesthetistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId()
                    : "");
            }
        }
        
        if ("NO_END".equals(anaesSummary.getProcessState()))
        {
            String docId = anaesRecord.getAnaRecordId();
            SearchFormBean sb = new SearchFormBean();
            sb.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(sb);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                anaesSummary.setAnesthetistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        anaesSummary.setAnaesDocList(anaesDocList);
        List<SysCodeFormbean> puncturePointList =  basSysCodeService.searchSysCodeByGroupId("puncture_point", searchBean.getBeid());
        result.put("puncturePointList", puncturePointList);//穿刺点
        
        result.put("anaesRecord", anaesRecord);
        result.put("anaesSummary", anaesSummary);
        result.put("optLatterDiagStr", optLatterDiagStr);
        result.put("realAnaesStr", realAnaesStr);
        result.put("optRealOperStr", optRealOperStr);
        result.put("spinalCanalPuncture", spinaCanalList.get(0));
        result.put("nerveBlock", nerveBlockList != null && nerveBlockList.size() > 0 ? nerveBlockList.get(0): new DocNerveBlock());
        result.put("generalAnaes", genAnasList != null && genAnasList.size() > 0 ? genAnasList.get(0) : new DocGeneralAnaes()); 
        result.put("allergicReact", reacList != null && reacList.size() > 0 ? reacList.get(0) : new DocAnaesSummaryAllergicReaction()); 
        result.put("venipuncture", veniList != null && veniList.size() > 0 ? veniList.get(0) : new DocAnaesSummaryVenipuncture());
        
        logger.info("-------------end searchAnaesSummaryDetail-------------");
        return result.getJsonStr();
    }
    
    /**
     * 获取麻醉总结明细信息
     * @param searchBean
     * @return
     */
    @RequestMapping(value = "/searchAnaesSummaryDetailForSYZXYY")
    @ResponseBody
    @ApiOperation(value="获取麻醉总结明细信息",httpMethod="POST",notes="获取麻醉总结明细信息")
    public String searchAnaesSummaryDetailForSYZXYY(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean){
        logger.info("-------------begin searchAnaesSummaryDetail-------------");
        ResponseValue result = new ResponseValue();

        //手术信息表
        BasRegOpt opt = basRegOptService.searchRegOptById(searchBean.getRegOptId());
    
        result.put("regOpt", opt);  
        DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(searchBean.getRegOptId());
        if(null != anaesRecord)
        {
            searchBean.setDocId(anaesRecord.getAnaRecordId());
        }
        //术后诊断
        List<EvtOptLatterDiag> optLatterDiagList = evtOptLatterDiagService.searchOptLatterDiagList(searchBean);
        
        String optLatterDiagStr = "";
        for (EvtOptLatterDiag optLatterDiag : optLatterDiagList) {
            optLatterDiagStr += optLatterDiag.getName()+",";
        }
        if(StringUtils.isNotBlank(optLatterDiagStr)){
            optLatterDiagStr =  optLatterDiagStr.substring(0, optLatterDiagStr.length()-1);
        }
        
        //实施手术
        List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
        String optRealOperStr = "";
        for (EvtOptRealOper optRealOper : optRealOperList) {
            optRealOperStr += optRealOper.getName()+",";
        }
        if(StringUtils.isNotBlank(optRealOperStr)){
            optRealOperStr =  optRealOperStr.substring(0, optRealOperStr.length()-1);
        }
        
        // 实际麻醉方法
        List<EvtRealAnaesMethod> realAnaesList = evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
        String realAnaesStr = "";
        for (EvtRealAnaesMethod realAnaesMethod : realAnaesList) {
            realAnaesStr += realAnaesMethod.getName()+",";
        }
        if(StringUtils.isNotBlank(realAnaesStr)){
            realAnaesStr =  realAnaesStr.substring(0, realAnaesStr.length()-1);
        }
        
        //麻醉总结数据
        List<DocAnaesSummary> anaSumList = docAnaesSummaryService.searchAnaesSummaryList(searchBean);
        DocAnaesSummary anaesSummary = anaSumList.size()>0?anaSumList.get(0):new DocAnaesSummary();
        searchBean.setAnaSumId(anaesSummary.getAnaSumId());
        //椎管内穿刺
        List<DocSpinalCanalPuncture> spinaCanalList = docSpinalCanalPunctureService.searchSpinalCanalPunctureListSYZXYY(searchBean);
        //全身麻醉参数
        List<DocGeneralAnaes> genAnasList = docGeneralAnaesService.searchGeneralAnaesListSYZXYY(searchBean);
        //神经阻滞
        List<DocNerveBlock> nerveBlockList = docNerveBlockService.searchNerveBlockList(searchBean);
        
        List<DocAnaesSummaryAllergicReaction> reacList =  docAnaesSummaryAllergicReactionService.searchAllergicReactionList(searchBean);
        
        List<DocAnaesSummaryVenipuncture> veniList =  docAnaesSummaryVenipunctureService.searchVenipunctureList(searchBean);
        //麻醉医生列表
        List<String> anaesDocList = new ArrayList<String>();
        if (null == anaesSummary.getAnesthetistId())
        {
            DispatchFormbean dispatchPeople = basDispatchService.getDispatchOperByRegOptId(opt.getRegOptId());
            if (dispatchPeople != null)
            {
                anaesSummary.setAnesthetistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId()
                    : "");
            }
        }
        
        if ("NO_END".equals(anaesSummary.getProcessState()))
        {
            String docId = anaesRecord.getAnaRecordId();
            SearchFormBean sb = new SearchFormBean();
            sb.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(sb);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                anaesSummary.setAnesthetistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        anaesSummary.setAnaesDocList(anaesDocList);
        List<SysCodeFormbean> puncturePointList =  basSysCodeService.searchSysCodeByGroupId("puncture_point", searchBean.getBeid());
        result.put("puncturePointList", puncturePointList);//穿刺点
        
        result.put("anaesRecord", anaesRecord);
        result.put("anaesSummary", anaesSummary);
        result.put("optLatterDiagStr", optLatterDiagStr);
        result.put("realAnaesStr", realAnaesStr);
        result.put("optRealOperStr", optRealOperStr);
        result.put("spinalCanalPuncture", spinaCanalList.get(0));
        result.put("nerveBlock", nerveBlockList != null && nerveBlockList.size() > 0 ? nerveBlockList.get(0): new DocNerveBlock());
        result.put("generalAnaes", genAnasList != null && genAnasList.size() > 0 ? genAnasList.get(0) : new DocGeneralAnaes()); 
        result.put("allergicReact", reacList != null && reacList.size() > 0 ? reacList.get(0) : new DocAnaesSummaryAllergicReaction()); 
        result.put("venipuncture", veniList != null && veniList.size() > 0 ? veniList.get(0) : new DocAnaesSummaryVenipuncture());
        
        logger.info("-------------end searchAnaesSummaryDetail-------------");
        return result.getJsonStr();
    }
	
	
	/**
	 * 修改麻醉总结单
	 * @param anaesSummary
	 * @return
	 */
	@RequestMapping(value = "/saveAnaesSummaryDetail")
	@ResponseBody
	@ApiOperation(value="修改麻醉总结单",httpMethod="POST",notes="修改麻醉总结单")
	public String saveAnaesSummaryDetail(@ApiParam(name="anaesSummaryItemFormbean", value ="修改参数") @RequestBody AnaesSummaryItemFormbean anaesSummaryItemFormbean) {
		logger.info("-------------begin saveAnaesSummaryDetail-------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(anaesSummaryItemFormbean.getAnaesSummary());
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		docAnaesSummaryService.saveAnaesSummaryDetail(anaesSummaryItemFormbean);
		logger.info("-------------end saveAnaesSummaryDetail-------------");
		return resp.getJsonStr();
	}
	
	/**
     * 修改麻醉总结单
     * @param anaesSummary
     * @return
     */
    @RequestMapping(value = "/saveAnaesSummaryDetailForQNZZY")
    @ResponseBody
    public String saveAnaesSummaryDetailForQNZZY(@RequestBody AnaesSummaryItemFormbean anaesSummaryItemFormbean)
    {
        logger.info("-------------begin saveAnaesSummaryDetail-------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(anaesSummaryItemFormbean.getAnaesSummary());
        if (!(validatorBean.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean2 = beanValidator(anaesSummaryItemFormbean.getNerveBlock());
        if (!(validatorBean2.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean2.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean3 = beanValidator(anaesSummaryItemFormbean.getSpinalCanalPuncture());
        if (!(validatorBean3.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean3.getMessage());
            return resp.getJsonStr();
        }
        
        ValidatorBean validatorBean4 = beanValidator(anaesSummaryItemFormbean.getGeneralAnaes());
        if (!(validatorBean4.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean4.getMessage());
            return resp.getJsonStr();
        }
        
        docAnaesSummaryService.saveAnaesSummaryDetailForQNZZY(anaesSummaryItemFormbean);
        logger.info("-------------end saveAnaesSummaryDetail-------------");
        return resp.getJsonStr();
    }
    
	/**
     * 修改麻醉总结单
     * @param anaesSummary
     * @return
     */
    @RequestMapping(value = "/saveAnaesSummaryDetailForSYZXYY")
    @ResponseBody
    public String saveAnaesSummaryDetailForSYZXYY(@RequestBody AnaesSummaryItemFormbean anaesSummaryItemFormbean)
    {
        logger.info("-------------begin saveAnaesSummaryDetail-------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(anaesSummaryItemFormbean.getAnaesSummary());
        if (!(validatorBean.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean2 = beanValidator(anaesSummaryItemFormbean.getNerveBlock());
        if (!(validatorBean2.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean2.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean3 = beanValidator(anaesSummaryItemFormbean.getSpinalCanalPuncture());
        if (!(validatorBean3.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean3.getMessage());
            return resp.getJsonStr();
        }
        
        ValidatorBean validatorBean4 = beanValidator(anaesSummaryItemFormbean.getGeneralAnaes());
        if (!(validatorBean4.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean4.getMessage());
            return resp.getJsonStr();
        }
        
        docAnaesSummaryService.saveAnaesSummaryDetailForSYZXYY(anaesSummaryItemFormbean);
        logger.info("-------------end saveAnaesSummaryDetail-------------");
        return resp.getJsonStr();
    }
    
    
	/**
     * 修改麻醉总结单(永兴人民医院)
     * @param anaesSummary
     * @return
     */
    @RequestMapping(value = "/saveAnaesSummaryDetailForYXRM")
    @ResponseBody
    public String saveAnaesSummaryDetailForYXRM(@RequestBody AnaesSummaryItemFormbean anaesSummaryItemFormbean)
    {
        logger.info("-------------begin saveAnaesSummaryDetailYXRM-------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(anaesSummaryItemFormbean.getAnaesSummary());
        if (!(validatorBean.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean2 = beanValidator(anaesSummaryItemFormbean.getNerveBlock());
        if (!(validatorBean2.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean2.getMessage());
            return resp.getJsonStr();
        }
        ValidatorBean validatorBean3 = beanValidator(anaesSummaryItemFormbean.getSpinalCanalPuncture());
        if (!(validatorBean3.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean3.getMessage());
            return resp.getJsonStr();
        }
        
        ValidatorBean validatorBean4 = beanValidator(anaesSummaryItemFormbean.getGeneralAnaes());
        if (!(validatorBean4.isValidator()))
        {
            resp.put("resultCode", "10000001");
            resp.put("resultMessage", validatorBean4.getMessage());
            return resp.getJsonStr();
        }
        
        docAnaesSummaryService.saveAnaesSummaryDetailForYXRM(anaesSummaryItemFormbean);
        logger.info("-------------end saveAnaesSummaryDetailYXRM-------------");
        return resp.getJsonStr();
    }
}
