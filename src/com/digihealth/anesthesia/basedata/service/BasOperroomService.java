package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.OperRoomFormBean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasCollectConfig;
import com.digihealth.anesthesia.basedata.po.BasDeviceConfig;
import com.digihealth.anesthesia.basedata.po.BasDeviceMonitorConfig;
import com.digihealth.anesthesia.basedata.po.BasMonitorConfigFreq;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;

@Service
public class BasOperroomService extends BaseService {

	public List<BasOperroom> selectEntityList(BasOperroom params) {
        return basOperroomDao.selectEntityList(params);
    }

	public BasOperroom selectEntityByPrimaryKey(String operRoomId) {
		return basOperroomDao.queryRoomListById(operRoomId, getBeid());
	}

	@Transactional
	public int updateEntity(BasOperroom entity) {
		return basOperroomDao.updateByPrimaryKey(entity);
	}

	@Transactional
	public int deleteByPrimaryKey(String id) {
		int cnt =  basOperroomDao.deleteByPrimaryKey(id,getBeid());
		CacheUtils.remove(ROOM_CACHE, ROOM_CACHE_ID);
		return cnt;
	}
	
	@Transactional
	public void insertEntity(BasOperroom entity) {
		entity.setOperRoomId(GenerateSequenceUtil.generateSequenceNo());
		entity.setBeid(super.getBeid());
		basOperroomDao.insert(entity);
	}

	public List<OperRoomFormBean> findList(BaseInfoQuery baseQuery) {
		String beid = baseQuery.getBeid();
		if (StringUtils.isEmpty(beid)) {
			beid = getBeid();
		}
		baseQuery.setBeid(beid);
		return basOperroomDao.findList(baseQuery == null?new BaseInfoQuery():baseQuery);
	}
	
	public List<BasOperroom> queryRoomList(SystemSearchFormBean systemSearchFormBean) {
	    if (StringUtils.isEmpty(systemSearchFormBean.getBeid()))
        {
	        systemSearchFormBean.setBeid(getBeid());
        }
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("operRoomId");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		String filter = "";
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(filters!=null&&filters.size()>0){
			for(int i = 0;i<filters.size();i++){
				if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
					filter = filter + " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
				}
			}
		}
		return basOperroomDao.queryRoomList(filter,systemSearchFormBean);
	}
	
	public int queryRoomListTotal(SystemSearchFormBean systemSearchFormBean){
	    if (StringUtils.isEmpty(systemSearchFormBean.getBeid()))
        {
            systemSearchFormBean.setBeid(getBeid());
        }
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("operRoomId");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		String filter = "";
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(filters!=null&&filters.size()>0){
			for(int i = 0;i<filters.size();i++){
				if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
					filter = filter + " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
				}
			}
		}
		return basOperroomDao.queryRoomListTotal(filter, systemSearchFormBean);
	}
	
	public BasOperroom queryRoomListById(String regOptId) {
		return basOperroomDao.queryRoomListById(getCurRoomId(regOptId), getBeid());
	}
	
	@Transactional
	public int updateHealthnurse(String operRoomId,String healthnurse,String beid){
		return basOperroomDao.updateHealthnurse(operRoomId, healthnurse,beid);
	}
	
	@Transactional
	public void saveOperroom(BasOperroom operroom){
	    if (StringUtils.isEmpty(operroom.getBeid()))
        {
	        operroom.setBeid(getBeid());
        }
        if(operroom.getOperRoomId()!=null){
            basOperroomDao.updateByPrimaryKey(operroom);
        }else{
            operroom.setOperRoomId(basOperroomDao.getMaxOperroomIdByBeid(operroom.getBeid()));
            
            if ("01".equals(operroom.getRoomType()))
            {
                /**
                 * 如果是不做同步时，则需要在新增手术室的时候初始化设备配置信息
                 */
                if (CustomConfigureUtil.isSync())
                {
                    
                    List<BasDeviceConfig> devList = basDeviceConfigDao.getDeviceConfigList(getBeid(), "0");
                    for (BasDeviceConfig basDeviceConfig : devList)
                    {
                        basDeviceConfig.setRoomId(operroom.getOperRoomId());
                        basDeviceConfigDao.insertSelective(basDeviceConfig);
                    }
                    
                    List<BasDeviceMonitorConfig> devMonitorList =
                        basDeviceMonitorConfigDao.getRoomDeviceMonitorConfigByBeid(getBeid(), null, "0");
                    for (BasDeviceMonitorConfig basDeviceMonitorConfig : devMonitorList)
                    {
                        basDeviceMonitorConfig.setRoomId(operroom.getOperRoomId());
                        basDeviceMonitorConfigDao.insertSelective(basDeviceMonitorConfig);
                    }
                    
                    List<Filter> filters = new ArrayList<Filter>();
                    Filter f = new Filter();
                    f.setField("beid");
                    f.setValue(getBeid());
                    filters.add(f);
                    
                    f = new Filter();
                    f.setField("roomId");
                    f.setValue("0");
                    filters.add(f);
                    SystemSearchFormBean systemSearchFormBean = new SystemSearchFormBean();
                    systemSearchFormBean.setOrderBy("asc");
                    systemSearchFormBean.setSort("eventId");
                    List<BasMonitorConfig> monitorList =
                        basMonitorConfigDao.queryBasMonitorConfigList(filters, systemSearchFormBean);
                    for (BasMonitorConfig basMonitorConfig : monitorList)
                    {
                        basMonitorConfig.setRoomId(operroom.getOperRoomId());
                        basMonitorConfigDao.insertSelective(basMonitorConfig);
                    }
                    
                    systemSearchFormBean.setBeid(getBeid());
                    systemSearchFormBean.setRoomId("0");
                    systemSearchFormBean.setSort("id");
                    List<BasMonitorConfigFreq> monitorFreqList =
                        basMonitorConfigFreqDao.queryMonitorConfigFreqList("", systemSearchFormBean);
                    for (BasMonitorConfigFreq basMonitorConfigFreq : monitorFreqList)
                    {
                        basMonitorConfigFreq.setId(GenerateSequenceUtil.generateSequenceNo());
                        basMonitorConfigFreq.setBeid(operroom.getBeid());
                        basMonitorConfigFreq.setRoomId(operroom.getOperRoomId());
                        basMonitorConfigFreqDao.insertSelective(basMonitorConfigFreq);
                    }
                }
            }
      		basOperroomDao.insertSelective(operroom);
        }
        
        //判断该手术室是否初始化了氧流量的监测项，存在则跳过不存在则添加
		BasMonitorConfig basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", operroom.getBeid(), operroom.getOperRoomId());
		if(null==basMonitorO2){
			basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", "0", "0");
			basMonitorO2.setBeid(operroom.getBeid());
			basMonitorO2.setRoomId(operroom.getOperRoomId());
			basMonitorConfigDao.insertSelective(basMonitorO2);
		}
        
        
        CacheUtils.remove(ROOM_CACHE+"_"+getBeid(), ROOM_CACHE_ID+"_"+getBeid());
        
    }
	
	@Transactional
    public void saveOperroomQNZZY(BasOperroom operroom){
        if (StringUtils.isEmpty(operroom.getBeid()))
        {
            operroom.setBeid(getBeid());
        }
        if(operroom.getOperRoomId()!=null){
            basOperroomDao.updateByPrimaryKey(operroom);
            
            BasCollectConfig collectConfig = basCollectConfigDao.selectByPrimaryKey(operroom.getOperRoomId(), operroom.getBeid());
            if (null == collectConfig)
            {
                collectConfig = new BasCollectConfig();
                collectConfig.setRoomId(operroom.getOperRoomId());
                collectConfig.setBeid(operroom.getBeid());
                collectConfig.setIp(operroom.getRemotehost());
                basCollectConfigDao.insert(collectConfig);
            }
            else if (!operroom.getRemotehost().equals(collectConfig.getIp()))
            {
                collectConfig.setIp(operroom.getRemotehost());
                basCollectConfigDao.updateByPrimaryKey(collectConfig);
            }
        }else{
            operroom.setOperRoomId(basOperroomDao.getMaxOperroomIdByBeid(operroom.getBeid()));
            
            if ("01".equals(operroom.getRoomType()))
            {
                /**
                 * 如果是不做同步时，则需要在新增手术室的时候初始化设备配置信息
                 */
                if (CustomConfigureUtil.isSync())
                {
                    
                    List<BasDeviceConfig> devList = basDeviceConfigDao.getDeviceConfigList(getBeid(), "0");
                    for (BasDeviceConfig basDeviceConfig : devList)
                    {
                        basDeviceConfig.setRoomId(operroom.getOperRoomId());
                        basDeviceConfigDao.insertSelective(basDeviceConfig);
                    }
                    
                    List<BasDeviceMonitorConfig> devMonitorList =
                        basDeviceMonitorConfigDao.getRoomDeviceMonitorConfigByBeid(getBeid(), null, "0");
                    for (BasDeviceMonitorConfig basDeviceMonitorConfig : devMonitorList)
                    {
                        basDeviceMonitorConfig.setRoomId(operroom.getOperRoomId());
                        basDeviceMonitorConfigDao.insertSelective(basDeviceMonitorConfig);
                    }
                    
                    List<Filter> filters = new ArrayList<Filter>();
                    Filter f = new Filter();
                    f.setField("beid");
                    f.setValue(getBeid());
                    filters.add(f);
                    
                    f = new Filter();
                    f.setField("roomId");
                    f.setValue("0");
                    filters.add(f);
                    SystemSearchFormBean systemSearchFormBean = new SystemSearchFormBean();
                    systemSearchFormBean.setOrderBy("asc");
                    systemSearchFormBean.setSort("eventId");
                    List<BasMonitorConfig> monitorList =
                        basMonitorConfigDao.queryBasMonitorConfigList(filters, systemSearchFormBean);
                    for (BasMonitorConfig basMonitorConfig : monitorList)
                    {
                        basMonitorConfig.setRoomId(operroom.getOperRoomId());
                        basMonitorConfigDao.insertSelective(basMonitorConfig);
                    }
                    
                    systemSearchFormBean.setBeid(getBeid());
                    systemSearchFormBean.setRoomId("0");
                    systemSearchFormBean.setSort("id");
                    List<BasMonitorConfigFreq> monitorFreqList =
                        basMonitorConfigFreqDao.queryMonitorConfigFreqList("", systemSearchFormBean);
                    for (BasMonitorConfigFreq basMonitorConfigFreq : monitorFreqList)
                    {
                        basMonitorConfigFreq.setId(GenerateSequenceUtil.generateSequenceNo());
                        basMonitorConfigFreq.setBeid(operroom.getBeid());
                        basMonitorConfigFreq.setRoomId(operroom.getOperRoomId());
                        basMonitorConfigFreqDao.insertSelective(basMonitorConfigFreq);
                    }
                }
            }
            basOperroomDao.insertSelective(operroom);
            
            //将手术室信息插入到采集配置表中
            BasCollectConfig collectConfig = new BasCollectConfig();
            collectConfig.setRoomId(operroom.getOperRoomId());
            collectConfig.setBeid(operroom.getBeid());
            collectConfig.setIp(operroom.getRemotehost());
            basCollectConfigDao.insert(collectConfig);
        }
        
        //判断该手术室是否初始化了氧流量的监测项，存在则跳过不存在则添加
        BasMonitorConfig basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", operroom.getBeid(), operroom.getOperRoomId());
        if(null==basMonitorO2){
            basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", "0", "0");
            basMonitorO2.setBeid(operroom.getBeid());
            basMonitorO2.setRoomId(operroom.getOperRoomId());
            basMonitorConfigDao.insertSelective(basMonitorO2);
        }
        
        
        CacheUtils.remove(ROOM_CACHE+"_"+getBeid(), ROOM_CACHE_ID+"_"+getBeid());
        
    }
	
	public static final String ROOM_CACHE = "operRoomCache";
	public static final String ROOM_CACHE_ID = "roomList";
	
	public List<BasOperroom> searchOperRoomList(String beid){
		
		List<BasOperroom> list = (List<BasOperroom>) CacheUtils.get(ROOM_CACHE+"_"+beid, ROOM_CACHE_ID+"_"+beid);
		
		if(list == null || list.size()<=0){
			SystemSearchFormBean systemSearchFormBean = new SystemSearchFormBean();
			systemSearchFormBean.setBeid(beid);
			systemSearchFormBean.setSort("operRoomId");
			systemSearchFormBean.setOrderBy("asc");
			list = basOperroomDao.queryRoomList("", systemSearchFormBean);
			
			CacheUtils.put(ROOM_CACHE+"_"+beid, ROOM_CACHE_ID+"_"+beid , list);
		}
		return list;
	}
	
	public String getRoomIdByIp(String ip,String beid){
		List<BasOperroom> roomList = searchOperRoomList(beid);
		for (BasOperroom basOperroom : roomList) {
			
			if(ip.equals(basOperroom.getRemotehost())){
				return basOperroom.getOperRoomId();
			}
		}
		String error = "请求地址对应的IP："+ip+",找不到对应的手术室信息!!!";
        logger.warn(error + ", time:" + DateUtils.formatDateTime(new Date()));
		return "0";
	}
	
	public String getBeidByIpPort(String ip,int port){
		List<BasOperroom> rslist = basOperroomDao.queryRoomListByIpPort(ip, port);
		String beid = "";
		if(null!=rslist && rslist.size()>0){
			beid = rslist.get(0).getBeid();
		}else{
			String error = "请求地址对应的IP："+ip+";PORT:"+port+",找不到对应的手术室信息!!!";
			logger.warn(error + ", time:" + DateUtils.formatDateTime(new Date()));
		}
		return beid;
	}
	
	
}
