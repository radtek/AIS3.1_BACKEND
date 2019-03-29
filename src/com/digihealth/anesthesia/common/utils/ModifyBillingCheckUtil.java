package com.digihealth.anesthesia.common.utils;

import java.util.Objects;

import org.apache.log4j.Logger;

import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;

public class ModifyBillingCheckUtil {
	
	private static Logger logger = Logger.getLogger(ConnectionManager.class);
	private static BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
	
	/**
	 * 用于判断是否可以修改手术核算、麻醉费用单的数据
	 * @param regOptId
	 * @return
	 */
	public static Boolean checkModifyBill(String regOptId,String costType,ResponseValue resp){
		return checkModifyBill(regOptId,costType,resp,true);
	}
	
	public static Boolean checkModifyBill(String regOptId,String costType,ResponseValue resp,Boolean isCheckBillState){
		
		logger.info("checkModifyBill====start======= "+regOptId+"===============");
		
		if(StringUtils.isNotBlank(regOptId)){
			BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
			
			if(null != regOpt && "08".equals(regOpt.getState())){
				resp.setResultCode( "40000001");
				resp.setResultMessage("已取消的手术，不允许同步计费数据到his系统!");
				return false;
			}
			
			if(isCheckBillState){
				if(null!=regOpt){
					Integer payState = 9; //结算中
					if("1".equals(costType)){//麻醉费用单收费标志
						if(Objects.equals(payState, regOpt.getDocPayState())){
							resp.setResultCode( "40000001");
							resp.setResultMessage("该患者的麻醉费用单正在his系统结算中,请等待结算完成后修改麻醉费用单信息!");
							return false;
						}
					}else{//手术核算单收费标志
						if(Objects.equals(payState, regOpt.getNurPayState())){
							resp.setResultCode( "40000001");
							resp.setResultMessage("该患者的手术核算单正在his系统结算中,请等待结算完成后修改手术核算单信息!");
							return false;
						}
					}
				}
			}
		}
		
		logger.info("checkModifyBill====end===============");
		return true;
		
	}
	
	/**
	 * 如果收费状态为结算中时，则不允许修改手术核算、麻醉费用单的明细数据
	 * @param regOptId
	 * @param costType
	 * @return
	 */
	public static boolean CheckUpdateBillState(String regOptId,String costType){
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		Boolean changeFlag = true; 
		if(null!=regOpt){
			Integer payState = 9; //结算中
			if("1".equals(costType)){//麻醉费用单收费标志
				if(Objects.equals(payState, regOpt.getDocPayState())){
					changeFlag = false;
				}
			}else{//手术核算单收费标志
				if(Objects.equals(payState, regOpt.getNurPayState())){
					changeFlag = false;
				}
			}
		}
		return changeFlag;
	}
	
}
