package com.digihealth.anesthesia.doc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.DocOptCareOperNameFormbean;
import com.digihealth.anesthesia.doc.formbean.OptCareRecordFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


/**
 * 手术护理记录
 * <功能详细描述>
 * 
 * @author  zhouyi
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocOptCareRecordController",description="手术护理记录处理类")
public class DocOptCareRecordController extends BaseController {
	
	/** 
	 * 查询手术护理
	 * <功能详细描述>
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/searchOptCareRecordByRegOptId")
	@ResponseBody
	@ApiOperation(value="查询手术护理",httpMethod="POST",notes="查询手术护理")
	public String searchOptCareRecByRegOptId(@ApiParam(name="map", value ="手术护理参数") @RequestBody Map<String, Object> map) {
		logger.info("----------start searchOptCareRecordByRegOptId---------------");
		ResponseValue resp = docOptCareRecordService.searchOptCareRecordByRegOptId(map);
		logger.info("----------end searchOptCareRecordByRegOptId---------------");
		return resp.getJsonStr();
	}

    /** 
     * 保存手术护理单，用户还可以进行修改
     * <功能详细描述>
     * @param optCareRecord
     * @return
     * @throws ParseException 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateOptCareRecord")
    @ResponseBody
	@ApiOperation(value="保存手术护理单，用户还可以进行修改",httpMethod="POST",notes="保存手术护理单，用户还可以进行修改")
    public String updateOptCareRecord(@ApiParam(name="optCareRecordFormBean", value ="手术护理参数") @RequestBody OptCareRecordFormBean optCareRecordFormBean) throws ParseException {
        logger.info("----------start updateOptCareRecord---------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(optCareRecordFormBean);
        if (!(validatorBean.isValidator())) {
        	resp.setResultCode("10000001");
        	resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        if (null != optCareRecordFormBean.getInOperRoomTime() && !"".equals(optCareRecordFormBean.getInOperRoomTime())) {
            String inOperRoomTime = optCareRecordFormBean.getInOperRoomTime().replace("Z", " UTC");
            optCareRecordFormBean.setInOperRoomTime(inOperRoomTime);
        }
        
        if (null != optCareRecordFormBean.getOutOperRoomTime() && !"".equals(optCareRecordFormBean.getOutOperRoomTime())) {
            String outOperRoomTime = optCareRecordFormBean.getOutOperRoomTime().replace("Z", " UTC");
            optCareRecordFormBean.setOutOperRoomTime(outOperRoomTime);
        }
        resp = docOptCareRecordService.updateOptCareRecord(optCareRecordFormBean);
        logger.info("----------end updateOptCareRecord---------------");
        return resp.getJsonStr();
    }
    
    /** 
     * 保存手术护理单，用户还可以进行修改(永兴定制)
     * <功能详细描述>
     * @param optCareRecord
     * @return
     * @throws ParseException 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateOptCareRecordYXRM")
    @ResponseBody
	@ApiOperation(value="保存手术护理单，用户还可以进行修改",httpMethod="POST",notes="保存手术护理单，用户还可以进行修改")
    public String updateOptCareRecordYXRM(@ApiParam(name="optCareRecordFormBean", value ="手术护理参数") @RequestBody OptCareRecordFormBean optCareRecordFormBean) throws ParseException {
        logger.info("----------start updateOptCareRecord---------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(optCareRecordFormBean);
        if (!(validatorBean.isValidator())) {
        	resp.setResultCode("10000001");
        	resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        if (null != optCareRecordFormBean.getInOperRoomTime() && !"".equals(optCareRecordFormBean.getInOperRoomTime())) {
            String inOperRoomTime = optCareRecordFormBean.getInOperRoomTime().replace("Z", " UTC");
            optCareRecordFormBean.setInOperRoomTime(inOperRoomTime);
        }
        
        if (null != optCareRecordFormBean.getOutOperRoomTime() && !"".equals(optCareRecordFormBean.getOutOperRoomTime())) {
            String outOperRoomTime = optCareRecordFormBean.getOutOperRoomTime().replace("Z", " UTC");
            optCareRecordFormBean.setOutOperRoomTime(outOperRoomTime);
        }
        resp = docOptCareRecordService.updateOptCareRecordYXRM(optCareRecordFormBean);
        logger.info("----------end updateOptCareRecord---------------");
        return resp.getJsonStr();
    }
    
    /** 
     * 局麻可以在护理记录单修改手术名字，根据手术重新生成手术等级和切口等级(永兴定制)
     * <功能详细描述>
     * @param optCareRecord
     * @return
     * @throws ParseException 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateOptCareOperNameYXRM")
    @ResponseBody
	@ApiOperation(value="局麻可以在护理记录单修改手术名字",httpMethod="POST",notes="保局麻可以在护理记录单修改手术名字")
    public String updateOptCareOperNameYXRM(@ApiParam(name="docOptCareOperNameFormbean", value ="手术护理参数") @RequestBody DocOptCareOperNameFormbean docOptCareOperNameFormbean) throws ParseException {
        logger.info("----------start updateOptCareOperNameYXRM---------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(docOptCareOperNameFormbean);
        if (!(validatorBean.isValidator())) {
        	resp.setResultCode("10000001");
        	resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        if (null == docOptCareOperNameFormbean.getOperationNameList() || docOptCareOperNameFormbean.getOperationNameList().size() < 1) {
        	resp.setResultCode("10000002");
        	resp.setResultMessage("手术名字不能为空");
            return resp.getJsonStr();
        }
        docOptCareRecordService.updateOptCareOperNameYXRM(docOptCareOperNameFormbean,resp);
        
        logger.info("----------end updateOptCareOperNameYXRM---------------");
        return resp.getJsonStr();
    }
}
