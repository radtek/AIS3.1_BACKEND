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
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocLaborAnalgesiaAccede;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocLaborAnalgesiaAccedeController", description = "分娩镇痛同意书处理类")
public class DocLaborAnalgesiaAccedeController extends BaseController {
	
	/**
     * 
     * @discription 根据手术ID获取麻醉同意书
     * @author liukui
     * @created 2018-6-7 下午5:13:48
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchLaborAnalgesiaByRegOptId")
    @ResponseBody
    @ApiOperation(value = "根据手术ID获取分娩镇痛同意书", httpMethod = "POST", notes = "根据手术ID获取分娩镇痛同意书")
    public String searchAccedeByRegOptId(
            @ApiParam(name = "map", value = "统计查询参数") @RequestBody Map map) {
        logger.info("-----------------start searchAccedeByRegOptId-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId")
                .toString() : "";
        DocLaborAnalgesiaAccede laborAccede = docLaborAnalgesiaAccedeService.searchLaborAccedeByRegOptId(regOptId);
        if (laborAccede == null) {
            resp.setResultCode("30000002");
            resp.setResultMessage("分娩镇痛同意书不存在");
            return resp.getJsonStr();
        }

//        if (null == laborAccede.getAnaestheitistId())
//        {
//            DispatchFormbean dispatchPeople = basDispatchService.getDispatchOperByRegOptId(regOptId);
//            if (dispatchPeople != null)
//            {
//            	laborAccede.setAnaestheitistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId()
//                    : "");
//            }
//        }
//        if (null == laborAccede.getAnaestheitistSignTime())
//        {
//        	laborAccede.setAnaestheitistSignTime(DateUtils.getDate());
//        }

        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService
                .searchApplicationById(map.get("regOptId").toString());
        resp.put("laborAccede", laborAccede);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        logger.info("-----------------end searchLaborAnalgesiaByRegOptId-----------------");
        return resp.getJsonStr();
    }
	/**
	 * 
	 * @discription 修改分娩镇痛同意书
	 * @author chengwang
	 * @created 2015-10-20 上午9:33:40
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/saveLaborAccede")
	@ResponseBody
	@ApiOperation(value = "修改分娩镇痛同意书", httpMethod = "POST", notes = "修改分娩镇痛同意书")
	public String saveLaborAccede(
			@ApiParam(name = "DocLaborAnalgesiaAccede", value = "修改参数") @RequestBody DocLaborAnalgesiaAccede record) {
		logger.info("-----------------start saveLaborAccede-----------------");
		ResponseValue resp = new ResponseValue();
		docLaborAnalgesiaAccedeService.saveLaborAccede(record);
		logger.info("-----------------end saveLaborAccede-----------------");
		return resp.getJsonStr();
	}
}
