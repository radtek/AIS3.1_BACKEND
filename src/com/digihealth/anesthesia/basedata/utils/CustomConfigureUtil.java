package com.digihealth.anesthesia.basedata.utils;

import java.util.Map;

import net.sf.json.JSONObject;

import com.digihealth.anesthesia.basedata.dao.BasCustomConfigureDao;
import com.digihealth.anesthesia.basedata.po.BasCustomConfigure;
import com.digihealth.anesthesia.common.task.MsgProcessHolder;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;

public class CustomConfigureUtil {

	public static final String CONFIGURE_CACHE = "configureCache";
	
	public static final String MODULAR_TYPE_OPERATE = "1";//手术流程
	public static final String MODULAR_TYPE_DISPATCH = "2";//手术排班
	public static final String MODULAR_TYPE_SYN = "3";//数据同步
	
	private static BasCustomConfigureDao basCustomConfigureDao = SpringContextHolder.getBean(BasCustomConfigureDao.class);
	
	
	public static Map getObjectByConfigure(String modularType,String beid){
		Map configureValue = (Map) CacheUtils.get(CONFIGURE_CACHE, beid+"_"+modularType);
		if (configureValue ==  null){
			BasCustomConfigure conf = basCustomConfigureDao.getConfigureValueByModularType(modularType,beid);
			String 	objectStr = conf.getConfigureValue();
			if(StringUtils.isNotBlank(objectStr)){
				JSONObject jsonObject=JSONObject.fromObject(objectStr);
				configureValue = (Map)JSONObject.toBean(jsonObject, Map.class);
			}else{
				return null;
			}
			CacheUtils.put(CONFIGURE_CACHE, beid+"_"+modularType , configureValue);
		}
		return configureValue;
	}
	
	public static String getValueByConfigureName(String modularType,String beid,String configName){
		Map configureObj = getObjectByConfigure(modularType, beid);
		String configureValue = "";
		if(null != configureObj){
			configureValue = configureObj.get(configName)+"";
		}
		return configureValue;
	}
	
	//是否进行数据库同步,syncValue为2的情况下不需要保存拦截的sql语句
	public static Boolean isSync(){
		String syncValue = getValueByConfigureName(MODULAR_TYPE_SYN, MsgProcessHolder.findCurBeid(), "isSync");
		if(StringUtils.isNotBlank(syncValue) && syncValue.equals("2")){
			return true;
		}else{
			return false;
		}
	}
	
	//是否审核
	public static String isRatify(){
		return getValueByConfigureName(MODULAR_TYPE_OPERATE, MsgProcessHolder.findCurBeid(), "isRatify");
	}
	
	//是否需要麻醉医生
	public static String isNeedDoct(){
		return getValueByConfigureName(MODULAR_TYPE_OPERATE, MsgProcessHolder.findCurBeid(), "isNeedDoct");
	}
	
	//同日同手术间是否默认安排相同护士
	public static String isSameNurs(){
		return getValueByConfigureName(MODULAR_TYPE_DISPATCH, MsgProcessHolder.findCurBeid(), "isSameNurs");
	}
	
	//同日同手术间是否默认安排相同麻醉医生
	public static String isSameDoct(){
		return getValueByConfigureName(MODULAR_TYPE_DISPATCH, MsgProcessHolder.findCurBeid(), "isSameDoct");
	}
	
}
