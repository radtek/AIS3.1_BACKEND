package com.digihealth.anesthesia.tmp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.digihealth.anesthesia.tmp.formbean.TmpChargeMedTempFormBean;
import com.digihealth.anesthesia.tmp.formbean.TmpChargeTempOptFormBean;
import com.digihealth.anesthesia.tmp.po.TmpChargePayTemp;
import com.digihealth.anesthesia.tmp.po.TmpMedPayTemp;
import com.digihealth.anesthesia.tmp.po.TmpPriChargeMedTemp;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 麻醉收费单模板
 * @author dell
 *auth liuk
 */
@Controller
@RequestMapping(value = "/basedata")
@Api(value = "TmpMedicineController", description = "麻醉收费单模板处理类")
public class TmpChargeMedTempController extends BaseController{

	
	@RequestMapping(value = "/saveChargeMedTemp")
	@ResponseBody
	@ApiOperation(value = "保存用药模版", httpMethod = "POST", notes = "保存用药模版息")
	public String saveChargeMedTemp(@ApiParam(name = "chargeMedTempFormBean", value = "模板保存对象") @RequestBody TmpChargeMedTempFormBean chargeMedTempFormBean) {
	//public String saveChargeMedTemp(@RequestBody TmpChargeMedTempFormBean chargeMedTempFormBean){
		logger.info("begin saveChargeMedTemp");
		ResponseValue resp = new ResponseValue();
		tmpPriChargeMedTempService.saveChargeMedTempForm(chargeMedTempFormBean);
		logger.info("end saveChargeMedTemp");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/deleteChargeMedTempById")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String deleteChargeMedTempById(@ApiParam(name = "record", value = "删除请求对象") @RequestBody TmpPriChargeMedTemp record) {
	//public String deleteChargeMedTempById(@RequestBody TmpPriChargeMedTemp record){
		logger.info("begin deleteChargeMedTempById");
		ResponseValue resp = new ResponseValue();
		tmpPriChargeMedTempService.deleteChargeMedTempById(record);
		logger.info("end deleteChargeMedTempById");
		return resp.getJsonStr();
	}
	
	
	@RequestMapping(value = "/queryChargeMedTempList")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String queryChargeMedTempList(@RequestBody SearchLiquidTempFormBean searchLiquidTempFormBean) {
		logger.info("begin queryChargeMedTempList");
		ResponseValue resp = new ResponseValue();
		List<TmpPriChargeMedTemp> resultList = tmpPriChargeMedTempService.queryChargeMedTempList(searchLiquidTempFormBean);
		int total = tmpPriChargeMedTempService.queryCountChargeMedTempList(searchLiquidTempFormBean);
		resp.put("tempList", resultList);
		resp.put("total", total);
		logger.info("end queryChargeMedTempList");
		return resp.getJsonStr();
	}
	
	/**
	 * 获取模板信息，并保存到表里
	 * @param chargeMedTemp
	 * @return
	 */
	@RequestMapping(value = "/queryChargeMedTempById")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String queryChargeMedTempById(@RequestBody Map chargeMedTemp){
		logger.info("begin queryChargeMedTempById");
		ResponseValue resp = new ResponseValue();
		if(null!=chargeMedTemp){
	    	String chargeTempId = chargeMedTemp.get("chargeTempId").toString();
	    	String regOptId = chargeMedTemp.get("regOptId").toString();
	    	String costType = chargeMedTemp.get("costType").toString();
	    	String createUser = chargeMedTemp.get("createUser").toString(); 
	    	String userType = chargeMedTemp.get("userType").toString();
			List<DocPackagesItem> ls = tmpPriChargeMedTempService.queryPayListByChargeTempId(chargeTempId);
			if(null!=ls && ls.size()>0){
				for (DocPackagesItem packagesItem : ls) {
					packagesItem.setRegOptId(regOptId);
					packagesItem.setUserType(userType);
					packagesItem.setCreateUser(createUser);
					packagesItem.setCostType(costType);
					packagesItem.setCreateTime(DateUtils.getDate());
					docPackagesItemService.insertPackagesItem(packagesItem);
				}
			}
			List<DocEventBilling> evls = tmpPriChargeMedTempService.queryChargeMedTempById(chargeTempId);
			if(null!=evls && evls.size()>0){
				for (DocEventBilling eventBilling : evls) {
					eventBilling.setRegOptId(regOptId);
					eventBilling.setUserType(userType);
					eventBilling.setCreateUser(createUser);
					eventBilling.setCostType(costType);
				}
				docEventBillingService.saveEventBilling(evls,resp);
			}
    	}
		logger.info("end queryChargeMedTempById");
		return resp.getJsonStr();
	}
	
	
	/**
	 * 根据tempID获取模板明细数据
	 * @param chargeMedTemp
	 * @return
	 */
	@RequestMapping(value = "/searchTempInfoByTempId")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String searchTempInfoByTempId(@RequestBody Map map){
		logger.info("begin searchTempInfoByTempId");
		ResponseValue resp = new ResponseValue();
		if(null!=map){
	    	String chargeTempId = map.get("chargeTempId").toString();
	    	
	    	TmpPriChargeMedTemp tmpPriChargeMedTemp = tmpPriChargeMedTempService.searchChargeMedTemp(chargeTempId);
	    	
			List<TmpChargePayTemp> anaesChargeList = tmpPriChargeMedTempService.searchChargePayListById(chargeTempId,"1");//麻醉费用
			List<TmpChargePayTemp> materialList = tmpPriChargeMedTempService.searchChargePayListById(chargeTempId,"2");//耗材费
			List<TmpChargePayTemp> anaesOptList = tmpPriChargeMedTempService.searchChargePayListById(chargeTempId,"3");//麻醉操作费用
			List<TmpMedPayTemp> medList = tmpPriChargeMedTempService.searchMedChargeListById(chargeTempId,"1");//药品费
			List<TmpMedPayTemp> ioList = tmpPriChargeMedTempService.searchMedChargeListById(chargeTempId,"2");//输液费
			
			resp.put("chargeMedTemp", tmpPriChargeMedTemp);
			resp.put("anaesChargeList", anaesChargeList);
			resp.put("materialList", materialList);
			resp.put("anaesOptList", anaesOptList);
			resp.put("medList", medList);
			resp.put("ioList", ioList);
			
    	}
		logger.info("end searchTempInfoByTempId");
		return resp.getJsonStr();
	}
	
	/**
	 * 删除收费模板明细
	 * @param chargePayTemp
	 * @return
	 */
	@RequestMapping(value = "/deleteByChargePayId")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String deleteByChargePayId(@RequestBody TmpChargePayTemp chargePayTemp){
		logger.info("begin deleteByChargePayId");
		ResponseValue resp = new ResponseValue();
		tmpChargePayTempService.deleteByChargePayId(chargePayTemp);
		logger.info("end deleteByChargePayId");
		return resp.getJsonStr();
	}
	/**
	 * 修改收费模板明细
	 * @param chargePayTemp
	 * @return
	 */
	@RequestMapping(value = "/updateChargePayTemp")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String updateChargePayTemp(@RequestBody TmpChargePayTemp chargePayTemp){
		logger.info("begin updateChargePayTemp");
		ResponseValue resp = new ResponseValue();
		tmpChargePayTempService.updateChargePayTemp(chargePayTemp);
		logger.info("end updateChargePayTemp");
		return resp.getJsonStr();
	}
	
	/**
	 * 删除用药模板明细
	 * @param medChargeTemp
	 * @return
	 */
	@RequestMapping(value = "/deleteByMedChargeId")
	@ResponseBody
	@ApiOperation(value = "删除保存用药模版", httpMethod = "POST", notes = "删除保存用药模版息")
	public String deleteByMedChargeId(@RequestBody TmpMedPayTemp tmpMedPayTemp){
		logger.info("begin deleteByMedChargeId");
		ResponseValue resp = new ResponseValue();
		tmpMedPayTempService.deleteByMedChargeId(tmpMedPayTemp);
		logger.info("end deleteByMedChargeId");
		return resp.getJsonStr();
	}
	/**
	 * 修改用药模板明细
	 * @param medChargeTemp
	 * @return
	 */
	@RequestMapping(value = "/updateMedChargeTemp")
	@ResponseBody
	@ApiOperation(value = "修改用药模板明细", httpMethod = "POST", notes = "修改用药模板明细")
	public String updateMedChargeTemp(@RequestBody TmpMedPayTemp tmpMedPayTemp){
		logger.info("begin updateMedChargeTemp");
		ResponseValue resp = new ResponseValue();
		tmpMedPayTempService.updateMedChargeTemp(tmpMedPayTemp);
		logger.info("end updateMedChargeTemp");
		return resp.getJsonStr();
	}
	
	
	
	/**
	 * 查询手术包中无效的药品、价格、收费项目数据
	 * @param searchLiquidTempFormBean
	 * @return
	 */
	@RequestMapping(value = "/queryInvalidListByChargeTemp")
	@ResponseBody
	@ApiOperation(value = "查询手术包中无效的药品、价格、收费项目数据", httpMethod = "POST", notes = "查询手术包中无效的药品、价格、收费项目数据")
	public String queryInvalidListByChargeTemp() {
		logger.info("begin queryInvalidListByChargeTemp");
		ResponseValue resp = new ResponseValue();
		resp.put("chargeItemList", tmpPriChargeMedTempService.queryInvalidChargeItemList());
		resp.put("medicineList", tmpPriChargeMedTempService.queryInvalidMedOrPriceList());
		logger.info("end queryInvalidListByChargeTemp");
		return resp.getJsonStr();
	}
	
	
	/**
	 * 批量删除收费包里的无效数据
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/batchDelChargeTempDetaiInvalidData")
	@ResponseBody
	@ApiOperation(value = "批量删除收费包里的无效数据", httpMethod = "POST", notes = "批量删除收费包里的无效数据")
	public String batchDelChargeTempDetaiInvalidData(@RequestBody TmpChargeTempOptFormBean record) {
		logger.info("begin batchDelChargeTempDetaiInvalidData");
		ResponseValue resp = new ResponseValue();
		tmpPriChargeMedTempService.batchDelChargeTempDetaiInvalidData(record);
		logger.info("end batchDelChargeTempDetaiInvalidData");
		return resp.getJsonStr();
	}
	
	/**
	 * 批量替换收费包详细数据
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/batchReplaceChargeTempDetailData")
	@ResponseBody
	@ApiOperation(value = "批量替换收费包详细数据", httpMethod = "POST", notes = "批量替换收费包详细数据")
	public String batchReplaceChargeTempDetailData(@RequestBody TmpChargeTempOptFormBean record) {
		logger.info("begin batchReplaceChargeTempDetailData");
		ResponseValue resp = new ResponseValue();
		tmpPriChargeMedTempService.batchReplaceChargeTempDetailData(record);
		logger.info("end batchReplaceChargeTempDetailData");
		return resp.getJsonStr();
	}
	
	/**
	 * 获取手术包里对应的收费项目信息
	 * @param queryChargeTempPayItemByPy
	 * @return
	 */
	@RequestMapping(value = "/queryChargeTempPayItemByPy")
	@ResponseBody
	@ApiOperation(value = "获取手术包里对应的收费项目信息", httpMethod = "POST", notes = "获取手术包里对应的收费项目信息")
	public String queryChargeTempPayItemByPy(@RequestBody Map map) {
		logger.info("begin queryChargeTempPayItemByPy");
		ResponseValue resp = new ResponseValue();
		resp.put("rsList", tmpPriChargeMedTempService.queryChargeTempPayItemByPy(map.get("pinyin").toString()));
		logger.info("end queryChargeTempPayItemByPy");
		return resp.getJsonStr();
	}
	
	/**
	 * 获取手术包里对应的药品项目信息
	 * @param queryChargeTempPayItemByPy
	 * @return
	 */
	@RequestMapping(value = "/queryChargeTempMedicineItemByPy")
	@ResponseBody
	@ApiOperation(value = "获取手术包里对应的药品项目信息", httpMethod = "POST", notes = "获取手术包里对应的药品项目信息")
	public String queryChargeTempMedicineItemByPy(@RequestBody Map map) {
		logger.info("begin queryChargeTempMedicineItemByPy");
		ResponseValue resp = new ResponseValue();
		resp.put("rsList", tmpPriChargeMedTempService.queryChargeTempMedicineItemByPy(map.get("name").toString()));
		logger.info("end queryChargeTempMedicineItemByPy");
		return resp.getJsonStr();
	}
	
	/**
	 * 获取收费模板中的科室名称信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryRemarkByChargeTempList")
	@ResponseBody
	@ApiOperation(value = "获取收费模板中的科室名称信息", httpMethod = "POST", notes = "获取收费模板中的科室名称信息")
	public String queryRemarkByChargeTempList(@RequestBody Map map) {
		logger.info("begin queryRemarkByChargeTempList");
		ResponseValue resp = new ResponseValue();
		resp.put("deptList", tmpPriChargeMedTempService.queryRemarkByChargeTempList(map.get("tempType").toString()));
		logger.info("end queryRemarkByChargeTempList");
		return resp.getJsonStr();
	}
	
	
}
