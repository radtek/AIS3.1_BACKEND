package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.PreVisitFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocPatInspectItem;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocPrevisitAccessexam;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 术前访视单 <功能详细描述>
 * 
 * @author zhouyi
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocPreVisitController",description="术前访视单处理类")
public class DocPreVisitController extends BaseController {
    
    /**
     * @discription 根据手术ID获取术前访视单信息
     * @author zhouyi
     * @created 2016-9-7
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchPreVisitByRegOptId")
    @ResponseBody
	@ApiOperation(value="根据手术ID获取术前访视单信息",httpMethod="POST",notes="根据手术ID获取术前访视单信息")
    public String searchPreVisitByRegOptId(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("------------------begin searchPreVisitByRegOptId------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        PreVisitFormBean preVisitFormBean = docPreVisitService.searchPreVisitByRegOptId(regOptId);
        DocPreVisit preVisit = preVisitFormBean.getPreVisit();
        if (preVisit == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("术前访视单不存在!");
            return resp.getJsonStr();
        }
        
        //获取到麻醉医生名字
        if (null == preVisit.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople =
                basDispatchService.getDispatchOperByRegOptId(map.get("regOptId").toString());
            if (dispatchPeople != null)
            {
                preVisit.setAnaestheitistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId() : "");
                preVisit.setAnaestheitistName(dispatchPeople.getAnesthetistName() != null ? dispatchPeople.getAnesthetistName() : "");
            }
        }
        
        //获取手术基本信息
        SearchRegOptByIdFormBean searchRegOptByIdFormBean =
            basRegOptService.searchApplicationById(map.get("regOptId").toString());
        if (searchRegOptByIdFormBean == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("手术基本信息不存在!");
            return resp.getJsonStr();
        }
        
        List<String> anaseMethodList = new ArrayList<String>();
        String[] code = null;
        if (null == preVisit.getDesignedAnaesCode())
        {
            if (StringUtils.isNotBlank(searchRegOptByIdFormBean.getDesignedAnaesMethodCode()))
            {
                code = searchRegOptByIdFormBean.getDesignedAnaesMethodCode().split(",");
            }
            preVisit.setDesignedAnaesCode(searchRegOptByIdFormBean.getDesignedAnaesMethodCode());
            preVisit.setDesignedAnaes(searchRegOptByIdFormBean.getDesignedAnaesMethodName());
        }
        else
        {
            code = preVisit.getDesignedAnaesCode().split(",");
        }
        if (null != code && code.length > 0)
        {
            for (int i = 0; i < code.length; i++)
            {
                anaseMethodList.add(code[i]);
            }
        }
        preVisit.setDesignedAnaesList(anaseMethodList);
            
        //设置页面选择框的值
        setMapValue(preVisit);
        
		resp.put("result", "true");
		resp.put("preVisitItem", preVisit);
		resp.put("regOptItem", searchRegOptByIdFormBean);
        logger.info("-------------------end searchPreVisitByRegOptId-------------------");
        return resp.getJsonStr();
    }
    
    /**
     * @discription 根据手术ID获取术前访视单信息(南华局点)
     * @author zhouyi
     * @created 2016-9-7
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchPreVisitByRegOptIdForQNZZY")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取术前访视单信息",httpMethod="POST",notes="根据手术ID获取术前访视单信息")
    public String searchPreVisitByRegOptIdForQNZZY(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("------------------begin searchPreVisitByRegOptId------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        PreVisitFormBean preVisitFormBean = docPreVisitService.searchPreVisitByRegOptId(regOptId);
        DocPreVisit preVisit = preVisitFormBean.getPreVisit();
        if (preVisit == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("术前访视单不存在!");
            return resp.getJsonStr();
        }
        
        //获取到麻醉医生名字
        if (null == preVisit.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople =
                basDispatchService.getDispatchOperByRegOptId(map.get("regOptId").toString());
            if (dispatchPeople != null)
            {
                preVisit.setAnaestheitistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId() : "");
            }
        }
        
        if ("NO_END".equals(preVisit.getProcessState()))
        {
            DocAnaesRecord ansRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            String docId = ansRecord.getAnaRecordId();
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                preVisit.setAnaestheitistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        if (null == preVisit.getSignDate())
        {
            preVisit.setSignDate(DateUtils.getDate());
        }
        
        
        //获取手术基本信息
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = 
            basRegOptService.searchApplicationById(map.get("regOptId").toString());
        if (searchRegOptByIdFormBean == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("手术基本信息不存在!");
            return resp.getJsonStr();
        }
            
        //设置页面选择框的值
        setMapValue(preVisit);
        
        DocPrevisitAccessexam accessexam = preVisitFormBean.getPrevisitAccessexam();
        if ("NO_END".equals(preVisit.getProcessState()))
        {
            setInspectItem(regOptId, accessexam);
        }
        
        resp.put("result", "true");
        resp.put("preVisitItem", preVisit);
        resp.put("previsitPhyexam", preVisitFormBean.getPrevisitPhyexam());
        resp.put("accessexam", accessexam);
        resp.put("regOptItem", searchRegOptByIdFormBean);
        logger.info("-------------------end searchPreVisitByRegOptId-------------------");
        return resp.getJsonStr();
    }
    
    /**
     * @discription 根据手术ID获取 麻醉前评估和麻醉计划 文书信息 (永兴人民医院局点)
     * @author pengqing
     * @created 2017-12-25
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchPreVisitByRegOptIdYXRM")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取术前访视单信息",httpMethod="POST",notes="根据手术ID获取术前访视单信息")
    public String searchPreVisitByRegOptIdYXRM(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("------------------begin searchPreVisitByRegOptIdYXRM------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        PreVisitFormBean preVisitFormBean = docPreVisitService.searchPreVisitByRegOptId(regOptId);
        DocPreVisit preVisit = preVisitFormBean.getPreVisit();
        if (preVisit == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("术前访视单不存在!");
            return resp.getJsonStr();
        }
        
        //获取到麻醉医生名字
        if (null == preVisit.getAnaestheitistId() || StringUtils.isBlank(preVisit.getAnaestheitistName()))
        {
            DispatchFormbean dispatchPeople =
                basDispatchService.getDispatchOperByRegOptId(map.get("regOptId").toString());
            if (dispatchPeople != null)
            {
                preVisit.setAnaestheitistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId() : "");
                preVisit.setAnaestheitistName(dispatchPeople.getAnesthetistName() != null ? dispatchPeople.getAnesthetistName() : "");
            }
        }
        
        //获取手术基本信息
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = 
            basRegOptService.searchApplicationById(map.get("regOptId").toString());
        if (searchRegOptByIdFormBean == null) {
            resp.setResultCode("30000001");
            resp.setResultMessage("手术基本信息不存在!");
            return resp.getJsonStr();
        }
        
        List<String> anaseMethodList = new ArrayList<String>();
        String[] code = null;
        if (null == preVisit.getDesignedAnaesCode())
        {
            if (StringUtils.isNotBlank(searchRegOptByIdFormBean.getDesignedAnaesMethodCode()))
            {
                code = searchRegOptByIdFormBean.getDesignedAnaesMethodCode().split(",");
            }
            preVisit.setDesignedAnaesCode(searchRegOptByIdFormBean.getDesignedAnaesMethodCode());
            preVisit.setDesignedAnaes(searchRegOptByIdFormBean.getDesignedAnaesMethodName());
        }
        else
        {
            code = preVisit.getDesignedAnaesCode().split(",");
        }
        if (null != code && code.length > 0)
        {
            for (int i = 0; i < code.length; i++)
            {
                anaseMethodList.add(code[i]);
            }
        }
        preVisit.setDesignedAnaesList(anaseMethodList);
            
        //设置页面选择框的值
        setMapValue(preVisit);
        
        resp.put("result", "true");
        resp.put("preVisitItem", preVisit);
        resp.put("regOptItem", searchRegOptByIdFormBean);
        logger.info("-------------------end searchPreVisitByRegOptIdYXRM-------------------");
        return resp.getJsonStr();
    }
    
    /**
     * 
     * @discription 修改术前 麻醉前评估和麻醉计划 (永兴人民医院局点用)
     * @author chengwang
     * @created 2015-10-20 上午9:33:40
     * @param docId
     * @return
     */
    @RequestMapping(value = "/savePreVisitByDocIdYXRM")
    @ResponseBody
    public String savePreVisitByDocIdYXRM(@RequestBody PreVisitFormBean preVisitFormBean) {
        logger.info("begin savePreVisitByDocIdYXRM");
        ResponseValue resp = new ResponseValue();
        DocPreVisit preVisit = preVisitFormBean.getPreVisit();
        try 
        {
            ValidatorBean validatorBean = beanValidator(preVisit);
            if (!(validatorBean.isValidator())) 
            {
                resp.setResultCode("10000001");
                resp.setResultMessage(validatorBean.getMessage());
                return resp.getJsonStr();
            }

            resp = docPreVisitService.updatePreVisitByDocId(preVisit);
        } catch (Exception e) 
        {
            if (logger.isErrorEnabled()) 
            {
                logger.error(Exceptions.getStackTraceAsString(e));
            }
            resp.setResultCode("10000001");
            resp.setResultMessage("系统错误，请与系统管理员联系!");
        }
        logger.info("end savePreVisitByDocIdYXRM");
        return resp.getJsonStr();
    }

    private void setInspectItem(String regOptId, DocPrevisitAccessexam accessexam)
    {
        if (null != accessexam)
        {
            List<DocPatInspectItem> liverFunc = patInspectItemService.queryItemByInstruction(regOptId, "肝");
            if (null != liverFunc && liverFunc.size() > 0)
            {
                for (DocPatInspectItem item : liverFunc)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("谷丙转氨酶(ALT)"))
                    {
                        accessexam.setAlt(val);
                    }
                    else if (name.equals("碱性磷酸酶(ALP)"))
                    {
                        accessexam.setAkp(val);
                    }
                    else if (name.equals("谷草转氨酶(AST)"))
                    {
                        accessexam.setAst(val);
                    }
                    else if (name.equals("总胆红素(TBIL)"))
                    {
                        accessexam.setTotalBilirubin(val);
                    }
                    else if (name.equals("直接胆红素(DBIL)"))
                    {
                        accessexam.setDirectBilirubin(val);
                    }
                    else if (name.equals("白蛋白(ALB)"))
                    {
                        accessexam.setAlbumin(val);
                    }
                    else if (name.equals("总蛋白(TP)"))
                    {
                        accessexam.setTotalProtein(val);
                    }
                } 
            }
            
            List<DocPatInspectItem> renalFunc = patInspectItemService.queryItemByInstruction(regOptId, "肾功能");
            if (null != renalFunc && renalFunc.size() > 0)
            {
                for (DocPatInspectItem item : renalFunc)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("尿素(UREA)"))
                    {
                        accessexam.setUreaNitrogen(val);;
                    }
                    else if (name.equals("肌酐(CREA)"))
                    {
                        accessexam.setCreatinine(val);
                    }
                } 
            }
            
            List<DocPatInspectItem> coagulFunc = patInspectItemService.queryItemByInstruction(regOptId, "凝血检查");
            if (null != coagulFunc && coagulFunc.size() > 0)
            {
                for (DocPatInspectItem item : coagulFunc)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("凝血酶原时间(PT)"))
                    {
                        accessexam.setPt(val);
                    }
                    else if (name.equals("活化部分凝血酶原时间(APTT)"))
                    {
                        accessexam.setAptt(val);
                    }
                } 
            }
            
            List<DocPatInspectItem> routBloodTest = patInspectItemService.queryItemByInstruction(regOptId, "血常规");
            if (null != routBloodTest && routBloodTest.size() > 0)
            {
                for (DocPatInspectItem item : routBloodTest)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("白细胞(WBC)"))
                    {
                        accessexam.setWbc(val);
                    }
                    else if (name.equals("红细胞(RBC)"))
                    {
                        accessexam.setRbc(val);
                    }
                    else if (name.equals("血红蛋白(HGB)"))
                    {
                        accessexam.setHb(val);
                    }
                    else if (name.equals("血小板(PLT)"))
                    {
                        accessexam.setPlatelets(val);
                    }
                } 
            }
            
            List<DocPatInspectItem> electrolytic = patInspectItemService.queryItemByInstruction(regOptId, "电");
            if (null != electrolytic && electrolytic.size() > 0)
            {
                for (DocPatInspectItem item : electrolytic)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("钾(K)"))
                    {
                        accessexam.setK(val);
                    }
                    else if (name.equals("钠(NA)"))
                    {
                        accessexam.setNa(val);
                    }
                    else if (name.equals("氯(CL)"))
                    {
                        accessexam.setCl(val);
                    }
                    else if (name.equals("镁(MG)"))
                    {
                        accessexam.setMg(val);
                    }
                } 
            }
            
            List<DocPatInspectItem> bloodSugars = patInspectItemService.queryItemByInstruction(regOptId, "血清葡萄糖");
            if (null != bloodSugars && bloodSugars.size() > 0)
            {
                for (DocPatInspectItem item : bloodSugars)
                {
                    String val = item.getVal();
                    String name = item.getName();
                    if (name.equals("葡萄糖"))
                    {
                        accessexam.setBloodSugar(val);
                        break;
                    }
                } 
            }
        }
    }
    
    private void setMapValue(DocPreVisit preVisit)
    {
        JSONObject jasonObject1 = JSONObject.fromObject(preVisit.getBriefHis());
        preVisit.setBriefHisMap(null == jasonObject1 ? new HashMap<String, Object>() : jasonObject1);
        
        JSONObject jasonObject2 = JSONObject.fromObject(preVisit.getLungbreathCond());
        preVisit.setLungbreathCondMap(null == jasonObject2 ? new HashMap<String, Object>() : jasonObject2);
        
        JSONObject jasonObject3 = JSONObject.fromObject(preVisit.getBrainNerve());
        preVisit.setBrainNerveMap(null == jasonObject3 ? new HashMap<String, Object>() : jasonObject3);
        
        JSONObject jasonObject4 = JSONObject.fromObject(preVisit.getSpineLimb());
        preVisit.setSpineLimbMap(null == jasonObject4 ? new HashMap<String, Object>() : jasonObject4);
        
        JSONObject jasonObject5 = JSONObject.fromObject(preVisit.getBlood());
        preVisit.setBloodMap(null == jasonObject5 ? new HashMap<String, Object>() : jasonObject5);
        
        JSONObject jasonObject7 = JSONObject.fromObject(preVisit.getDigestion());
        preVisit.setDigestionMap(null == jasonObject7 ? new HashMap<String, Object>() : jasonObject7);
        
        JSONObject jasonObject8 = JSONObject.fromObject(preVisit.getEndocrine());
        preVisit.setEndocrineMap(null == jasonObject8 ? new HashMap<String, Object>() : jasonObject8);
        
        JSONObject jasonObject9 = JSONObject.fromObject(preVisit.getInfectious());
        preVisit.setInfectiousMap(null == jasonObject9 ? new HashMap<String, Object>() : jasonObject9);
        
        JSONObject jasonObject10 = JSONObject.fromObject(preVisit.getAirwayManage());
        preVisit.setAirwayManageMap(null == jasonObject10 ? new HashMap<String, Object>() : jasonObject10);
        
        JSONObject jasonObject11 = JSONObject.fromObject(preVisit.getSpecialHandle());
        preVisit.setSpecialHandleMap(null == jasonObject11 ? new HashMap<String, Object>() : jasonObject11);
        
        JSONObject jasonObject12 = JSONObject.fromObject(preVisit.getAnalgesicCond());
        preVisit.setAnalgesicMap(null == jasonObject12 ? new HashMap<String, Object>() : jasonObject12);
        
        JSONObject jasonObject13 = JSONObject.fromObject(preVisit.getMonitor());
        preVisit.setMonitorMap(null == jasonObject13 ? new HashMap<String, Object>() : jasonObject13);
        
        JSONObject jasonObject14 = JSONObject.fromObject(preVisit.getHeartBoolCond());
        preVisit.setHeartBoolCondMap(null == jasonObject14 ? new HashMap<String, Object>() : jasonObject14);
        
        JSONObject jasonObject15 = JSONObject.fromObject(preVisit.getToothAbnormalCond());
        preVisit.setToothAbnormalMap(null == jasonObject15 ? new HashMap<String, Object>() : jasonObject15);
        
        JSONObject jasonObject16 = JSONObject.fromObject(preVisit.getAssayAbnormalCond());
        preVisit.setAssayAbnormalMap(null == jasonObject16 ? new HashMap<String, Object>() : jasonObject16);
        
        JSONObject jasonObject17 = JSONObject.fromObject(preVisit.getSpecialTreatmentCond());
        preVisit.setSpecialTreatmentCondMap(null == jasonObject17 ? new HashMap<String, Object>() : jasonObject17);
        
        JSONObject jasonObject18 = JSONObject.fromObject(preVisit.getAnaesHis());
        preVisit.setAnaesCondMap(null == jasonObject18 ? new HashMap<String, Object>() : jasonObject18);
        
        JSONObject jasonObject19 = JSONObject.fromObject(preVisit.getOperHisCond());
        preVisit.setOperHisCondMap(null == jasonObject19 ? new HashMap<String, Object>() : jasonObject19);
        
        JSONObject jasonObject20 = JSONObject.fromObject(preVisit.getAnaesPunctureCond());
        preVisit.setAnaesPunctureCondMap(null == jasonObject20 ? new HashMap<String, Object>() : jasonObject20);
        
        JSONObject jasonObject21 = JSONObject.fromObject(preVisit.getAssistMeasure());
        preVisit.setAssistMeasureMap(null == jasonObject21 ? new HashMap<String, Object>() : jasonObject21);
        
        JSONObject jasonObject22 = JSONObject.fromObject(preVisit.getDrugAbuseCond());
        preVisit.setDrugAbuseCondMap(null == jasonObject22 ? new HashMap<String, Object>() : jasonObject22);
    }

    /**
     * 
     * @discription 修改术前访视单
     * @author zhouyi
     * @created 2016-9-7
     * @param docId
     * @return
     */
    @RequestMapping(value = "/updatePreVisit")
    @ResponseBody
	@ApiOperation(value="修改术前访视单",httpMethod="POST",notes="修改术前访视单")
    public String updatePreVisitByDocId(@ApiParam(name="preVisit", value ="修改参数") @RequestBody DocPreVisit preVisit) {
        logger.info("--------------------begin updatePreVisit--------------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(preVisit);
        if (!(validatorBean.isValidator())) {
            resp.setResultCode("10000001");
            resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        preVisit.setDesignedAnaesCode(StringUtils.getStringByList(preVisit.getDesignedAnaesList()));
        resp = docPreVisitService.updatePreVisitByDocId(preVisit);
        logger.info("--------------------end updatePreVisit--------------------");
        return resp.getJsonStr();
    }
    
    /**
     * 
     * @discription 修改术前访视单(南华局点用)
     * @author chengwang
     * @created 2015-10-20 上午9:33:40
     * @param docId
     * @return
     */
    @RequestMapping(value = "/savePreVisitByDocId")
    @ResponseBody
    public String savePreVisitByDocId(@RequestBody PreVisitFormBean preVisitFormBean) {
        logger.info("begin savePreVisitByDocId");
        ResponseValue resp = new ResponseValue();
        try {
            String preVisitId = preVisitFormBean.getPreVisit().getPreVisitId();
            String regOptId = preVisitFormBean.getPreVisit().getRegOptId();

            ValidatorBean validatorBean = beanValidator(preVisitFormBean.getPreVisit());
            if (!(validatorBean.isValidator())) {
                resp.setResultCode("10000001");
                resp.setResultMessage(validatorBean.getMessage());
                return resp.getJsonStr();
            }

            preVisitFormBean.getPrevisitAnaesplan().setPreVisitId(preVisitId);
            preVisitFormBean.getPrevisitAnaesplan().setRegOptId(regOptId);

            ValidatorBean validatorBean1 = beanValidator(preVisitFormBean.getPrevisitAnaesplan());
            if (!(validatorBean1.isValidator())) {
                resp.setResultCode("10000001");
                resp.setResultMessage(validatorBean1.getMessage());
                return resp.getJsonStr();
            }

            preVisitFormBean.getPrevisitAccessexam().setPreVisitId(preVisitId);
            preVisitFormBean.getPrevisitAccessexam().setRegOptId(regOptId);

            ValidatorBean validatorBean2 = beanValidator(preVisitFormBean.getPrevisitAccessexam());
            if (!(validatorBean2.isValidator())) {
                resp.setResultCode("10000001");
                resp.setResultMessage(validatorBean2.getMessage());
                return resp.getJsonStr();
            }

            preVisitFormBean.getPrevisitPhyexam().setPreVisitId(preVisitId);
            preVisitFormBean.getPrevisitPhyexam().setRegOptId(regOptId);

            ValidatorBean validatorBean3 = beanValidator(preVisitFormBean.getPrevisitPhyexam());
            if (!(validatorBean3.isValidator())) {
                resp.setResultCode("10000001");
                resp.setResultMessage(validatorBean3.getMessage());
                return resp.getJsonStr();
            }
            docPreVisitService.savePreVisitByDocId(preVisitFormBean, resp);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(Exceptions.getStackTraceAsString(e));
            }
            resp.setResultCode("10000001");
            resp.setResultMessage("系统错误，请与系统管理员联系!");
        }
        logger.info("end savePreVisitByDocId");
        return resp.getJsonStr();
    }
}
