package com.digihealth.anesthesia.doc.controller;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.NurseInterviewFormBean;
import com.digihealth.anesthesia.doc.formbean.NurseInteviewRecordFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/document")
@Api(value="DocNurseInterviewRecordController",description="手术室护理工作访视记录处理类")
public class DocNurseInterviewRecordController extends BaseController {
	
	@RequestMapping("/searchNurseInterviewRecord")
	@ResponseBody
	@ApiOperation(value="查询手术室护理工作访视记录信息",httpMethod="POST",notes="查询手术室护理工作访视记录信息")
	public String searchNurseInterviewRecord(@RequestBody JSONObject jsonObject){
        logger.info("------------------start searchNurseInterviewRecord------------------------");
        ResponseValue res = new ResponseValue();
        String regOptId = jsonObject.get("regOptId").toString();
        if (StringUtils.isNotBlank(regOptId))
        {
            NurseInteviewRecordFormBean bean = docNurseInterviewRecordService.searchNurseInterviewRecord(regOptId);
            SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
            res.put("regOptItem", searchRegOptByIdFormBean);
            res.put("bean", bean);
            res.setResultCode("1");
            res.setResultMessage("查询成功！");
        }
        else
        {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
        logger.info("------------------end searchNurseInterviewRecord------------------------");
        return res.getJsonStr();
	}
	
	@RequestMapping("/updateNurseInterviewRecord")
	@ResponseBody
	@ApiOperation(value="更新手术室护理工作访视记录信息",httpMethod="POST",notes="更新手术室护理工作访视记录信息")
	public String updateNurseInterviewRecord(@RequestBody NurseInterviewFormBean interviewRecord){
		logger.info("------------------start updateNurseInterviewRecord------------------------");
        ResponseValue res = new ResponseValue();
        if (null != interviewRecord)
        {
            docNurseInterviewRecordService.updateNurseInterviewRecord(interviewRecord);
            res.setResultCode("1");
            res.setResultMessage("修改成功！");
        }
        else
        {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
		logger.info("------------------end updateNurseInterviewRecord------------------------");
		return res.getJsonStr();
	}
	
	
	/*@RequestMapping("/submitNurseInterviewRecord")
	@ResponseBody
	public String submitNurseInterviewRecord(@RequestBody NurseInterviewRecord interviewRecord){
		logger.info("------------------start submitNurseInterviewRecord------------------------");
		ResponseValue res = new ResponseValue();
		try {
			if(null != interviewRecord){
				docNurseInterviewRecordService.submitNurseInterviewRecord(interviewRecord);
				res.setResultCode("1");
				res.setResultMessage("提交成功！");
			}else{
				res.setResultCode("70000000");
				res.setResultMessage(Global.getRetMsg(res.getResultCode()));
			}
		} catch (Exception e) {
			logger.error("Error:submitNurseInterviewRecord---"+e.getMessage());
			res.setResultCode("10000000");
			res.setResultMessage("系统错误，请与系统管理员联系!");
		}
		logger.info("------------------end submitNurseInterviewRecord------------------------");
		return res.getJsonStr();
	}*/
	
}
