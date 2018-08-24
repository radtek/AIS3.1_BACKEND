/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.List;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocPrePublicity;
import com.digihealth.anesthesia.doc.po.DocSelfPayAccede;
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
@Api(value="DocPrePublicityController",description="麻醉手术室术前宣教处理类")
public class DocPrePublicityController extends BaseController {

	/**
	 * 
	 * @discription 根据手术ID获取麻醉手术室术前宣教
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param selfPayAccede
	 * @return
	 */
	@RequestMapping(value = "/searchPrePublicityByRegOptId")
	@ResponseBody
	@ApiOperation(value="根据手术ID获取麻醉手术室术前宣教",httpMethod="POST",notes="根据手术ID获取麻醉手术室术前宣教")
	public String searchPrePublicityByRegOptId(@ApiParam(name="prePublicity", value ="查询参数") @RequestBody DocPrePublicity prePublicity) {
		logger.info("begin searchPrePublicityByRegOptId");
		ResponseValue req = new ResponseValue();
		BasRegOpt regOpt = basRegOptService.searchRegOptById(prePublicity.getRegOptId());
		DocPrePublicity docPrePublicity = docPrePublicityService.searchPrePublicityByRegOptId(regOpt.getRegOptId());
		if(docPrePublicity == null) {
			req.setResultCode("30000001");
			req.setResultMessage("麻醉手术室术前宣教不存在!");
            return req.getJsonStr();
		}
		
		req.put("regOpt", regOpt);
		req.put("prePublicity", docPrePublicity);
		logger.info("end searchPrePublicityByRegOptId");
		return req.getJsonStr();
	}
	
	@RequestMapping(value = "/updatePrePublicity")
	@ResponseBody
	@ApiOperation(value="修改麻醉手术室术前宣教",httpMethod="POST",notes="修改麻醉手术室术前宣教")
	public String updatePrePublicity(@ApiParam(name="prePublicity", value ="修改参数") @RequestBody DocPrePublicity prePublicity) {
		logger.info("begin updatePrePublicity");
		ResponseValue req = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(prePublicity);
		if(!(validatorBean.isValidator())){
			req.setResultCode("10000001");
			req.setResultMessage(validatorBean.getMessage());
			return req.getJsonStr();
		}
		req = docPrePublicityService.updatePrePublicity(prePublicity);
		logger.info("end updatePrePublicity");
		return req.getJsonStr();
	}
	
	@RequestMapping(value = "/deletePrePublicityById")
	@ResponseBody
	@ApiOperation(value="删除麻醉手术室术前宣教",httpMethod="POST",notes="删除麻醉手术室术前宣教")
	public String deletePrePublicityById(@ApiParam(name="prePublicity", value ="删除参数") @RequestBody DocPrePublicity prePublicity){
		logger.info("begin deletePrePublicityById");
		ResponseValue req = new ResponseValue();
		int result = docPrePublicityService.deleteByPrimaryKey(prePublicity.getId());
		if(result!=1){
			req.setResultCode("10000000");
			req.setResultMessage("删除成功！");
		}
		logger.info("end deletePrePublicityById");
		return req.getJsonStr();
	}

}
