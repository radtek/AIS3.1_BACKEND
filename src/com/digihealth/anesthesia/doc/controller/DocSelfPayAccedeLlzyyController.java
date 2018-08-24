/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocSelfPayAccedeLlzyy;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: PreVisitController.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocSelfPayAccedeLlzyyController",description="麻醉科自费耗材知情同意书处理类")
public class DocSelfPayAccedeLlzyyController extends BaseController {

	/**
	 * 
	 * @discription 根据手术ID获取医保病人麻醉科自费项目同意书
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param selfPayAccede
	 * @return
	 */
	@RequestMapping(value = "/searchSelfPayAccedeLlzyyByRegOptId")
	@ResponseBody
	@ApiOperation(value="根据手术ID获取麻醉科自费耗材知情同意书",httpMethod="POST",notes="根据手术ID获取麻醉科自费耗材知情同意书")
	public String searchSelfPayAccedeLlzyyByRegOptId(@ApiParam(name="selfPayAccede", value ="查询参数") @RequestBody DocSelfPayAccedeLlzyy selfPayAccede) {
		logger.info("begin searchSelfPayAccedeLlzyyByRegOptId");
		ResponseValue req = new ResponseValue();
		BasRegOpt regOpt = basRegOptService.searchRegOptById(selfPayAccede.getRegOptId());
		DocSelfPayAccedeLlzyy selfPayAccedeLlzyy = docSelfPayAccedeLlzyyService.searchSelfPayAccedeByRegOptId(regOpt.getRegOptId());
		if(selfPayAccedeLlzyy == null) {
			req.setResultCode("30000001");
			req.setResultMessage("麻醉科自费耗材知情同意书不存在!");
            return req.getJsonStr();
		}
		
		req.put("regOpt", regOpt);
		req.put("selfPayAccedeLlzyy", selfPayAccedeLlzyy);
		logger.info("end searchSelfPayAccedeLlzyyByRegOptId");
		return req.getJsonStr();
	}

	
	@RequestMapping(value = "/updateSelfPayAccedeLlzyy")
	@ResponseBody
	@ApiOperation(value="修改麻醉科自费耗材知情同意书",httpMethod="POST",notes="修改麻醉科自费耗材知情同意书")
	public String updateSelfPayAccedeLlzyy(@ApiParam(name="selfPayAccede", value ="修改参数") @RequestBody DocSelfPayAccedeLlzyy selfPayAccede) {
		logger.info("begin updateSelfPayAccedeLlzyy");
		ResponseValue req = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(selfPayAccede);
		if(!(validatorBean.isValidator())){
			req.setResultCode("10000001");
			req.setResultMessage(validatorBean.getMessage());
			return req.getJsonStr();
		}
		req = docSelfPayAccedeLlzyyService.updateSelfPayAccede(selfPayAccede);
		logger.info("end updateSelfPayAccedeLlzyy");
		return req.getJsonStr();
	}
	
	@RequestMapping(value = "/deleteSelfPayAccedeLlzyyById")
	@ResponseBody
	@ApiOperation(value="删除麻醉科自费耗材知情同意书",httpMethod="POST",notes="删除麻醉科自费耗材知情同意书")
	public String deleteSelfPayAccedeLlzyyById(@ApiParam(name="selfPayAccede", value ="删除参数") @RequestBody DocSelfPayAccedeLlzyy selfPayAccede){
		logger.info("begin deleteSelfPayAccedeLlzyyById");
		ResponseValue req = new ResponseValue();
		int result = docSelfPayAccedeService.deleteByPrimaryKey(selfPayAccede.getId());
		if(result!=1){
			req.setResultCode("10000000");
			req.setResultMessage("删除成功！");
		}
		logger.info("end deleteSelfPayAccedeLlzyyById");
		return req.getJsonStr();
	}

}
