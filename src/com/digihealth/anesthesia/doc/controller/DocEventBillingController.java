/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.ModifyBillingCheckUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.BatchOperCostFormBean;
import com.digihealth.anesthesia.doc.formbean.EventBillingFormBean;
import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.digihealth.anesthesia.interfacedata.formbean.syzxyy.CostRow;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: EventBillingController.java Description: 描述
 * 
 * @author liukui
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocEventBillingController",description="费用统计单处理类")
public class DocEventBillingController extends BaseController {
	
	/**
	 * 根据手术id查询账单明细信息
	 * @param baseInfo
	 * @return
	 */
	@RequestMapping(value = "/searchBillGroupByMedicode")
	@ResponseBody
	@ApiOperation(value="根据手术id查询账单明细信息",httpMethod="POST",notes="根据手术id查询账单明细信息")
	public String searchBillGroupByMedicode(@ApiParam(name="systemSearchFormBean", value ="查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean) {
		logger.info("-----------------begin searchEventBillingList-----------------");
		ResponseValue resp = new ResponseValue();
		List<EventBillingFormBean> rsList= docEventBillingService.searchBillGroupByMedicode(systemSearchFormBean);
		resp.put("rsList", rsList);
		resp.setResultCode("1");
		resp.setResultMessage("查询病人账单信息成功!");
		logger.info("-----------------end searchEventBillingList-----------------");
		return resp.getJsonStr();
	}
	

	/**
	 * 根据手术id查询账单明细信息
	 * @param baseInfo
	 * @return
	 */
	@RequestMapping(value = "/searchEventBillingList")
	@ResponseBody
	@ApiOperation(value="根据手术id查询账单明细信息",httpMethod="POST",notes="根据手术id查询账单明细信息")
	public String searchEventBillingList(@ApiParam(name="systemSearchFormBean", value ="查询参数") @RequestBody SystemSearchFormBean systemSearchFormBean) {
		logger.info("-----------------begin searchEventBillingList-----------------");
		ResponseValue resp = new ResponseValue();
		List<DocEventBilling> rsList= docEventBillingService.searchEventBillingList(systemSearchFormBean);
		Integer total = docEventBillingService.searchEventBillingListTotal(systemSearchFormBean);
		resp.put("rsList", rsList);
		resp.put("total", total);
		resp.setResultCode("1");
		resp.setResultMessage("查询病人账单信息成功!");
		logger.info("-----------------end searchEventBillingList-----------------");
		return resp.getJsonStr();
	}
	
	/**
	 * 同步术中药品及入量信息至账单表
	 * @param baseInfo
	 * @return
	 */
	@RequestMapping(value = "/synMedicTakeInfoList")
	@ResponseBody
	@ApiOperation(value="同步术中药品及入量信息至账单表",httpMethod="POST",notes="同步术中药品及入量信息至账单表")
	public String synMedicTakeInfoList(@ApiParam(name="baseInfo", value ="同步参数") @RequestBody BaseInfoQuery baseInfo) {
		logger.info("-----------------begin synMedicTakeInfoList-----------------");
		ResponseValue resp = new ResponseValue();
		docEventBillingService.synMedicTakeInfoList(baseInfo,resp);
		logger.info("-----------------end synMedicTakeInfoList-----------------");
		return resp.getJsonStr();
	}
	/**
	 * 保存入账信息
	 * @param eventBilling
	 * @return
	 */
	@RequestMapping(value = "/saveEventBilling")
	@ResponseBody
	@ApiOperation(value="保存入账信息",httpMethod="POST",notes="保存入账信息")
	public String saveEventBilling(@ApiParam(name="eventBillingList", value ="入账信息参数") @RequestBody List<DocEventBilling> eventBillingList){
		logger.info("-----------------begin saveEventBilling-----------------");
		ResponseValue resp = new ResponseValue();
		docEventBillingService.saveEventBilling(eventBillingList,resp);
		resp.setResultCode("1");
		resp.setResultMessage("保存入账信息成功!");
		logger.info("-----------------end saveEventBilling-----------------");
		return resp.getJsonStr();
	}
	/**
	 * 保存入账信息
	 * @param eventBilling
	 * @return
	 */
	@RequestMapping(value = "/deleteBilling")
	@ResponseBody
	@ApiOperation(value="删除入账信息",httpMethod="POST",notes="删除入账信息")
	public String deleteBilling(@ApiParam(name="eventBillingList", value ="入账信息参数") @RequestBody DocEventBilling eventBilling){
		logger.info("-----------------begin deleteBilling-----------------");
		ResponseValue resp = new ResponseValue();
		
		DocEventBilling evt = docEventBillingService.searchEventBillingList(eventBilling.getEbId());
		if(null != evt){
	    	Boolean modFlag = ModifyBillingCheckUtil.checkModifyBill(evt.getRegOptId(),evt.getCostType(),resp);
			if(!modFlag){
				resp.put("resultCode", resp.getResultCode());
				resp.put("resultMessage", resp.getResultMessage());
				return resp.getJsonStr();
			}
		}
		
		docEventBillingService.deleteBilling(eventBilling.getEbId());
		resp.setResultCode("1");
		resp.setResultMessage("删除入账信息成功!");
		logger.info("-----------------end deleteBilling-----------------");
		return resp.getJsonStr();
	}
	
	/**
	 * 批量删除明细数据
	 * @return
	 */
	@RequestMapping(value = "/batchDeleteCostDetail")
	@ResponseBody
	@ApiOperation(value="批量删除入账信息",httpMethod="POST",notes="批量删除入账信息")
	public String batchDeleteCostDetail(@RequestBody List<BatchOperCostFormBean> evList){
		logger.info("begin batchDeleteCostDetail");
		ResponseValue resp = new ResponseValue();
		
		if(null != evList && evList.size()>0){
	    	String regOptId = "";
			String costType = "";
	    	if("1".equals(evList.get(0).getOperCostType())){
				DocEventBilling evt = docEventBillingService.searchEventBillingList(evList.get(0).getPrimaryKeyId());
				if(null!=evt){
					regOptId = evt.getRegOptId();
					costType = evt.getCostType();
				}
			}else{
				DocPackagesItem evt = docPackagesItemService.searchPackagesItemById(evList.get(0).getPrimaryKeyId());
				if(null!=evt){
					regOptId = evt.getRegOptId();
					costType = evt.getCostType();
				}
			}
	    	Boolean modFlag = ModifyBillingCheckUtil.checkModifyBill(regOptId,costType,resp);
			if(!modFlag){
				return resp.getJsonStr();
			}
		}
		
		docEventBillingService.batchDeleteCostDetail(evList);
		logger.info("end batchDeleteCostDetail");
		return resp.getJsonStr();
	}
	
	
	/**
	 * 当现场出现发送收费数据到his无响应时，则调用此方法完成收费状态的修改
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/confirChargeByRegOptId")
	@ResponseBody
	public String confirChargeByRegOptId(@RequestBody Map map) {
		logger.info("begin confirChargeByRegOptId");
		ResponseValue resp = new ResponseValue();
		
		String regOptId = map.get("regOptId").toString();
		String costType = map.get("costType").toString(); //收费类型 护士收费 麻醉医生收费
		
		if(StringUtils.isNotBlank(regOptId) && StringUtils.isNotBlank(costType)){
			BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
			
			logger.info("begin confirChargeByRegOptId =====姓名为："+regOpt.getName()+" 正在确认收费中....");
			
			List<CostRow> operCost = docPackagesItemService.queryUnCostListByRegOptId(regOptId,costType);
			List<CostRow> operMedCost = docEventBillingService.queryUnCostListByRegOptId(regOptId,costType);
			
			if((null == operCost || operCost.size()<1) && (null == operMedCost || operMedCost.size()<1) ){
				resp.setResultCode("40000001");
				resp.setResultMessage("没有需要确认收费的数据，请核查!");
				return resp.getJsonStr();
			}
			
			if(null!=regOpt){
			
				if("1".equals(costType) && !Objects.equals(9, regOpt.getDocPayState())){//麻醉费用单收费标志
					resp.setResultCode("50000001");
					resp.setResultMessage("该患者的麻醉费用结算单收费状态不为结算中，无需确认收费操作!");
					return resp.getJsonStr();
				}
				if("2".equals(costType) && !Objects.equals(9, regOpt.getNurPayState())){//手术核算单收费标志
					resp.setResultCode("50000002");
					resp.setResultMessage("该患者的手术核算单收费状态不为结算中，无需确认收费操作!");
					return resp.getJsonStr();
				}
				
				if(null != operCost && operCost.size()>0){
					docPackagesItemService.updateChargeState(operCost);
				}
				if(null != operMedCost && operMedCost.size()>0){
					docEventBillingService.updateChargeState(operMedCost);
				}
			}
			logger.info("end confirChargeByRegOptId =====姓名为："+regOpt.getName()+" 已完成确认收费....");
		}
		
		return resp.getJsonStr();
	}
	
	
}
