package com.digihealth.anesthesia.interfacedata.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.ModifyBillingCheckUtil;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.interfacedata.formbean.syzxyy.CostRow;
import com.digihealth.anesthesia.interfacedata.formbean.syzxyy.HisOptcostFormBean;
import com.digihealth.anesthesia.interfacedata.po.OperCost;
import com.digihealth.anesthesia.interfacedata.service.HisInterfaceServiceQNZZY;
import com.digihealth.anesthesia.interfacedata.service.HisInterfaceServiceSYZXYY;

@Controller
@RequestMapping(value = "/interfacedata")
public class HisInterfaceController extends BaseController
{
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping("/syncHisInspectionQNZZY")
    @ResponseBody
    public String queryHisInspectionQNZZY(@RequestBody Map<String,Object> map)
    {
        logger.info("syncHisInspectionQNZZY======检验数据开始同步!!!");
        ResponseValue resp = new ResponseValue();
        if(null !=map){
            if(null != map.get("regOptId")){
                HisInterfaceServiceQNZZY hisInterfaceService = SpringContextHolder.getBean(HisInterfaceServiceQNZZY.class);
                hisInterfaceService.syncHisInspection(map.get("regOptId").toString());
            }else{
                resp.setResultCode("10000000");
                resp.setResultMessage("手术ID为空");
            }
        }
        logger.info("syncHisInspectionQNZZY======检验数据同步完成!!!");
        return resp.getJsonStr();
    }
    
    /**
     *永兴人民获得患者检验结果
     */
    @RequestMapping("/queryCheckListMasterYXRM")
    @ResponseBody
    public String queryCheckListMasterYXRM(@RequestBody Map<String,Object> map)
    {
        logger.info("queryCheckListMasterYXRM======获得患者检验");
        ResponseValue resp = new ResponseValue();
        if(null !=map){
            if(null != map.get("regOptId")){
            	operBaseDataServiceYXRM.queryCheckListMasterYXRM(map.get("regOptId").toString());
            }else{
                resp.setResultCode("10000000");
                resp.setResultMessage("queryCheckListMaster====检验数据请求参数错误");
            }
        }
        logger.info("queryCheckListMasterYXRM======获得患者检验完成!!!");
        return resp.getJsonStr();
    }
    
    /**
     * 邵阳中心医院计费接口
     * @param map
     * @return
     */
    @RequestMapping("/sendOperCostDataToHis")
	@ResponseBody
	public String sendOperCostDataToHis(@RequestBody Map map)
	{
		logger.info("sendOperCostDataToHis======费用开始同步!!!");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId").toString();
		String costType = map.get("costType").toString(); //收费类型 护士收费 麻醉医生收费
		
		HisOptcostFormBean formbean = new HisOptcostFormBean();
		List<CostRow>  costList = new ArrayList<CostRow>();
		
		logger.info("sendOperCostDataToHis==="+regOptId+"===术中收费项目开始同步!!!");
		
		List<CostRow> operCost = docPackagesItemService.queryUnCostListByRegOptId(regOptId,costType);
		List<CostRow> operMedCost = docEventBillingService.queryUnCostListByRegOptId(regOptId,costType);
		if(null!=operCost && operCost.size()>0){
			costList.addAll(operCost);
		}
		if(null!=operMedCost && operMedCost.size()>0){
			costList.addAll(operMedCost);
		}
		formbean.setRow(costList);
		if(null == costList || costList.size()<1){
			resp.setResultCode("40000001");
			resp.setResultMessage("没有可以同步到his的收费数据!");
			return resp.getJsonStr();
		}
		
		BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
		if(null != regOpt && "03".equals(regOpt.getState())){
			resp.setResultCode("40000001");
			resp.setResultMessage("手术状态为术前时，不允许同步计费数据到his系统!");
			return resp.getJsonStr();
		}
		
		Boolean modFlag = ModifyBillingCheckUtil.checkModifyBill(regOptId,costType,resp,false);
		if(!modFlag){
			return resp.getJsonStr();
		}
		
		//是否调用his接口
		String isConnectionFlag = Global.getConfig("isConnectionHis").trim();
		if((null != operCost && operCost.size()>0) || (null != operMedCost && operMedCost.size()>0)){
			logger.info("sendOperCostDataToHis===患者姓名为："+regOpt.getName()+"===costType:"+costType+"的费用开始同步!!!");
			try {
				if(StringUtils.isEmpty(isConnectionFlag) || "true".equals(isConnectionFlag)){
					//修改收费标志为结算中
					basRegOptService.updatePayState(regOptId,costType,new Integer(9));
					HisInterfaceServiceSYZXYY hisInterfaceService = SpringContextHolder.getBean(HisInterfaceServiceSYZXYY.class);
					hisInterfaceService.sendOptCostInteface(formbean, regOptId ,costType,resp);
				}
			} catch (Exception e) {
				resp.setResultCode("1000000000");
				resp.setResultMessage("单项收费项目费用同步异常！err:"+Exceptions.getStackTraceAsString(e));
				logger.error("sendOperCostDataToHis===单项收费项目费用同步异常:"+Exceptions.getStackTraceAsString(e));
				basRegOptService.updatePayState(regOptId,costType,new Integer(0));
				return resp.getJsonStr();
			}
		}
		logger.info("sendOperCostDataToHis======费用同步完成!!!");
		return resp.getJsonStr();
	}
}
