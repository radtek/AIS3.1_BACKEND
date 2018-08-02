package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.formbean.DispatchPeopleNameFormBean;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.AccedeFormBean;
import com.digihealth.anesthesia.doc.po.DocAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/document")
@Api(value = "DocAccedeController", description = "麻醉同意书处理类")
public class DocAccedeController extends BaseController {
	
	/**
     * 
     * @discription 根据手术ID获取麻醉同意书
     * @author chengwang
     * @created 2015-10-10 下午5:13:48
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchAccedeByRegOptId")
    @ResponseBody
    @ApiOperation(value = "根据手术ID获取麻醉同意书", httpMethod = "POST", notes = "根据手术ID获取麻醉同意书")
    public String searchAccedeByRegOptId(
            @ApiParam(name = "map", value = "统计查询参数") @RequestBody Map map) {
        logger.info("-----------------start searchAccedeByRegOptId-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId")
                .toString() : "";
        DocAccede accede = docAccedeService.searchAccedeByRegOptId(regOptId);
        if (accede == null) {
            resp.setResultCode("30000002");
            resp.setResultMessage("麻醉同意书不存在");
            return resp.getJsonStr();
        }

        if (null == accede.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople = basDispatchService.getDispatchOperByRegOptId(regOptId);
            if (dispatchPeople != null)
            {
                accede.setAnaestheitistId(dispatchPeople.getAnesthetistId() != null ? dispatchPeople.getAnesthetistId()
                    : "");
            }
        }
        
        if ("NO_END".equals(accede.getProcessState()))
        {
            DocAnaesRecord ansRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            String docId = ansRecord.getAnaRecordId();
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(docId);
            List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
            if (null != shiftChangeList && shiftChangeList.size() > 0)
            {
                accede.setAnaestheitistId(shiftChangeList.get(shiftChangeList.size() - 1).getShiftChangePeopleId());
            }
        }
        
        if (null == accede.getAnaestheitistSignTime())
        {
            accede.setAnaestheitistSignTime(DateUtils.getDate());
        }

        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService
                .searchApplicationById(map.get("regOptId").toString());
        resp.put("result", "true");
        resp.put("accedeItem", accede);
        resp.put("regOptItem",searchRegOptByIdFormBean);
        resp.put("accedeInformedList", docAccedeService.searchAccedeInformedListById(accede.getAccedeId()));

        logger.info("-----------------end searchAccedeByRegOptId-----------------");
        return resp.getJsonStr();
    }

	/**
	 * 
	 * @discription 修改麻醉同意书
	 * @author chengwang
	 * @created 2015-10-20 上午9:33:40
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/updateAccede")
	@ResponseBody
	@ApiOperation(value = "修改麻醉同意书", httpMethod = "POST", notes = "修改麻醉同意书")
	public String updateAccedeByDocId(
			@ApiParam(name = "accedeFormBean", value = "修改参数") @RequestBody AccedeFormBean accedeFormBean) {
		logger.info("-----------------start updateAccede-----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(accedeFormBean);
		if (!(validatorBean.isValidator())) {
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		//getAnaesMethod(accedeFormBean);
		DocAccede accede = accedeFormBean.getAccede();
		accede.setAnaseMethod(StringUtils.getStringByList(accede.getAnaesMethodList()));
		accede.setSelected(StringUtils.getStringByList(accede.getSelectedList()));
		accedeFormBean.setAccede(accede);
		resp = docAccedeService.updateAccede(accedeFormBean);
		logger.info("-----------------end updateAccede-----------------");
		return resp.getJsonStr();
	}

	/**
	 * 
	 * 
	 * @discription 提交麻醉同意书
	 * @author chengwang
	 * @created 2015-10-20 上午9:33:40
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/submitAccede")
	@ResponseBody
	@ApiOperation(value = "提交麻醉同意书", httpMethod = "POST", notes = "提交麻醉同意书")
	public String submitAccedeByDocId(
			@ApiParam(name = "accedeFormBean", value = "保存参数") @RequestBody AccedeFormBean accedeFormBean) {
		logger.info("-----------------start submitAccede-----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(accedeFormBean);
		if (!(validatorBean.isValidator())) {
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		DocAccede accede = accedeFormBean.getAccede();
		accede.setAnaseMethod(StringUtils.getStringByList(accede.getAnaesMethodList()));
        accede.setSelected(StringUtils.getStringByList(accede.getSelectedList()));
		accede.setProcessState("END");
		accedeFormBean.setAccede(accede);
		resp = docAccedeService.updateAccede(accedeFormBean);
		logger.info("-----------------end submitAccede-----------------");
		return resp.getJsonStr();
	}

/*	private void getAnaesMethod(AccedeFormBean accede) {
		if (null != accede.getAccede().getAnaseMethodList()
				&& accede.getAccede().getAnaseMethodList().size() > 0) {
			accede.getAccede().setAnaesMethodCode("");
			accede.getAccede().setAnaesMethod("");
			for (Map anaesMethodMap : accede.getAccede().getAnaseMethodList()) {

				accede.getAccede().setAnaesMethodCode(accede.getAccede().getAnaesMethodCode()
						+ anaesMethodMap.get("anaMedId") + ",");
				accede.getAccede().setAnaesMethod(accede.getAccede().getAnaesMethod()
						+ anaesMethodMap.get("name") + ",");
			}

			if (!StringUtils.isEmpty(accede.getAccede().getAnaesMethodCode())
					&& !StringUtils.isEmpty(accede.getAccede().getAnaesMethod())) {
				accede.getAccede().setAnaesMethodCode(accede.getAccede().getAnaesMethodCode()
						.substring(0, accede.getAccede().getAnaesMethodCode().length() - 1));

				accede.getAccede().setAnaesMethod(accede.getAccede().getAnaesMethod().substring(0,
						accede.getAccede().getAnaesMethod().length() - 1));
			}
		}
	}

	private void getAnaesMethod(DocAccede accede) {
		if (null != accede.getAnaseMethodList()
				&& accede.getAnaseMethodList().size() > 0) {
			accede.setAnaesMethodCode("");
			accede.setAnaesMethod("");
			for (Map anaesMethodMap : accede.getAnaseMethodList()) {

				accede.setAnaesMethodCode(accede.getAnaesMethodCode()
						+ anaesMethodMap.get("anaMedId") + ",");
				accede.setAnaesMethod(accede.getAnaesMethod()
						+ anaesMethodMap.get("name") + ",");
			}

			if (!StringUtils.isEmpty(accede.getAnaesMethodCode())
					&& !StringUtils.isEmpty(accede.getAnaesMethod())) {
				accede.setAnaesMethodCode(accede.getAnaesMethodCode()
						.substring(0, accede.getAnaesMethodCode().length() - 1));

				accede.setAnaesMethod(accede.getAnaesMethod().substring(0,
						accede.getAnaesMethod().length() - 1));
			}
		}
	}*/
}
