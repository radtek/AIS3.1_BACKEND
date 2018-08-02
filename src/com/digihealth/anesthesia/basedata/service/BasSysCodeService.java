package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.StringUtils;


@Service
public class BasSysCodeService extends BaseService {
	public static final String SYS_CODE_CACHE = "sysCodeCache";
	public static final String SYS_CODE_CACHE_ID = "id_";
	
	@SuppressWarnings("unchecked")
	public List<SysCodeFormbean> searchSysCodeByGroupId(String groupId,String beid){
		if(StringUtils.isBlank(beid)){
			beid = getBeid();
		}
		List<SysCodeFormbean> list = (List<SysCodeFormbean>) CacheUtils.get(SYS_CODE_CACHE, SYS_CODE_CACHE_ID+groupId+beid);
		if(list == null || list.size()<=0){
			list = basDictItemDao.searchSysCodeByGroupId(StringUtils.zhuanData(groupId),beid);
			CacheUtils.put(SYS_CODE_CACHE, SYS_CODE_CACHE_ID +groupId+beid, list);
		}
		return list;
	}
	
	public List<SysCodeFormbean> searchSysCodeByGroupIdAndCodeValue(String groupId,String codeValue,String beid){
		if(StringUtils.isBlank(beid)){
			beid = getBeid();
		}
		groupId = StringUtils.zhuanData(groupId);
		return basDictItemDao.searchSysCodeByGroupIdAndCodeValue(groupId, codeValue,beid);
	}
	
	public List<SysCodeFormbean> searchSysCodeByGroupIdAndCodeName(String groupId,String codeName,String beid){
		if(StringUtils.isBlank(beid)){
			beid = getBeid();
		}
		groupId = StringUtils.zhuanData(groupId);
		return basDictItemDao.searchSysCodeByGroupIdAndCodeName(groupId, codeName,beid);
	}
}
