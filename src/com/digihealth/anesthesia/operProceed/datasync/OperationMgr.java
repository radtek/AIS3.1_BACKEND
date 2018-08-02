package com.digihealth.anesthesia.operProceed.datasync;

import io.netty.channel.ChannelFuture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.po.Device;
import com.digihealth.anesthesia.basedata.service.BasDispatchService;
import com.digihealth.anesthesia.basedata.service.BasOperroomService;
import com.digihealth.anesthesia.basedata.service.BasRegOptService;
import com.digihealth.anesthesia.basedata.service.ControllerService;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.SysUtil;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.service.DocAnaesRecordService;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtRescueevent;
import com.digihealth.anesthesia.evt.service.EvtRescueeventService;
import com.digihealth.anesthesia.operProceed.core.MyConstants;
import com.digihealth.anesthesia.operProceed.core.SocketClient;
import com.digihealth.anesthesia.operProceed.datasync.entity.DataMonitor;
import com.digihealth.anesthesia.operProceed.formbean.CmdMsg;
import com.digihealth.anesthesia.operProceed.formbean.IPAdressBean;
import com.digihealth.anesthesia.operProceed.formbean.ResultObj;
import com.digihealth.anesthesia.operProceed.po.Observe;

public class OperationMgr {
    private final static Logger logger = Logger.getLogger(OperationMgr.class);
    //private static String regOptId;
	//private static Operation operation;
    private static SocketClient client;
    
    public static Map<String, String> regOptIdMap = new HashMap<String, String>();
    
    private static Map<String, String> operStatusMap = new HashMap<String, String>();//手术状态
    private static Map<String, String> operModelMap = new HashMap<String, String>();//手术模式
    
 //   private static String operStatus = null; //手术状态
 //   private static String operModel = null; //手术模式
    
//    public static String getRegOptId() {
//		return regOptId;
//	}
//
//	public static void setRegOptId(String regOptId) {
//		OperationMgr.regOptId = regOptId;
//	}
    
//    public static void setOperStatus(String operStatus) {
//		OperationMgr.operStatus = operStatus;
//	}
//    
//	public static String getOperStatus() {
//		return operStatus;
//	}
//
//	public static void setOperModel(String operModel) {
//		OperationMgr.operModel = operModel;
//	}
//	
//	public static String getOperModel() {
//		return operModel;
//	}
//	
//    public static void setCurrentOperation(String operId) {
//        regOptId = operId;
//    }

//	public OperationMgr(String remoteHost, int remotePort) {
//    	
//    }
	
	
	public static boolean init(String operId) {
        logger.info("init------初始化开始");
        boolean rst = true;
        //判断手术的状态是否为结束的状态，如果state='06'，则直接手工抛出异常，webSocket Close 
        String currOperState = getCurrOperState(operId);
        if(null == currOperState){
        	logger.error("init---------手术状态获取有误or当前手术有问题，待开发人员检查问题！");
        	return false;
        }else{
        	if(("06").equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已结束！");
        		return false;
        	}else if("08".equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已取消！");
        		return false;
        	}else if("07".equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已中止！");
        		return false;
        	}
        }
        operModelMap.put(operId, OperationMgr.getCurrOperModel(operId));
        
        //OperationMgr.setOperModel(OperationMgr.getCurrOperModel(operId));
        //SocketClient client = socketMap.get(getCurrOperRoomId(operId));   
        
        IPAdressBean iPAdressBean = getIPAdressByOperId(operId);
        
        if (client == null) {
            logger.info("init------初始化Socket连接");
            
            client = new SocketClient();
            
            //socketMap.put(basOperroom.getOperRoomId(), client);
            
            rst = client.init();
        }
        
        if(!SocketClient.futureMap.containsKey(iPAdressBean.getIp()+":"+iPAdressBean.getPort())){
        	String key = iPAdressBean.getIp()+":"+iPAdressBean.getPort();
			logger.info("doConnect---"+key);
			//移除上一台正常结束的手术
			if(null != client.connectMap.get(key)){
				client.connectMap.remove(iPAdressBean.getIp()+":"+iPAdressBean.getPort());
			}
			client.doConnect(iPAdressBean.getIp(),iPAdressBean.getPort());
        }

        return rst;
    }
	
    
    /*public static boolean init(String operId) {
        logger.info("init------初始化开始");
        boolean rst = true;
        //判断手术的状态是否为结束的状态，如果state='06'，则直接手工抛出异常，webSocket Close 
        String currOperState = getCurrOperState(operId);
        if(null == currOperState){
        	logger.error("init---------手术状态获取有误or当前手术有问题，待开发人员检查问题！");
        	return false;
        }else{
        	if(("06").equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已结束！");
        		return false;
        	}else if("08".equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已取消！");
        		return false;
        	}else if("07".equals(currOperState)){
        		logger.error("当前手术状态="+currOperState+",手术已中止！");
        		return false;
        	}
        }
        OperationMgr.setOperModel(OperationMgr.getCurrOperModel(operId));
		//        if (operation == null) {
		//            logger.info("init------初始化Operation");
		//            operation = new Operation();
		//            operation.setOperModel(OperationMgr.getCurrOperModel(operId)); //初始化的时候，从数据库中获取当前模式   
		//        }
              
        if (client == null) {
            logger.info("init------初始化Socket连接");
            //String remoteHost = DataMonitor.getMonitorFreq("remotehost");
            //int remotePort = SysUtil.strParseToInt(DataMonitor.getMonitorFreq("remoteport"));
            
            String remoteHost;
            int remotePort;
            BasOperroomService basOperroomService = (BasOperroomService)SpringContextHolder.getBean(BasOperroomService.class);
            BasOperroom basOperroom = basOperroomService.queryRoomListById(OperationMgr.getRegOptId());
            if(basOperroom !=null){
            	remoteHost = basOperroom.getRemotehost();
            	remotePort = SysUtil.strParseToInt(basOperroom.getRemoteport());
            }else{
            	remoteHost = Global.getConfig("remoteHost").toString().trim();
                remotePort = SysUtil.strParseToInt(Global.getConfig("remotePort").toString().trim());
            }
            
            client = new SocketClient(remoteHost, remotePort);
        }
        
        if (client.getConnected() == -1)
        {
            rst = client.init();
        }

        return rst;
    }*/
    
    public static boolean isCurrentOperation(String operId) {
    	
    	String roomId = getCurrOperRoomId(operId);
    	String beid = getCurrOperBeid(operId);
    	
    	String regOptKey = beid+roomId;
    	
        logger.info("isCurrentOperation------判断是否为相同手术 " + " 当前手术ID "+ OperationMgr.regOptIdMap.get(regOptKey) + " 新手术ID " + operId);
        if (OperationMgr.regOptIdMap.get(regOptKey) != null && OperationMgr.regOptIdMap.get(regOptKey).equals(operId)) {
            return true;
        }
        
        return false;
    }
    
    public static ResultObj initialize(CmdMsg json) {
    	ResultObj resultObj = new ResultObj();
        logger.info("initialize------json: " + json.toString()); 
        String operId = json.getRegOptId();
        
        //获取房间
    	String roomId = getCurrOperRoomId(operId);
    	String beid = getCurrOperBeid(operId);
        initDevice(roomId, operId);
        
        String operStatus = OperationMgr.operStatusMap.get(operId);
        
        logger.info("initialize------当前手术状态： " + OperationMgr.regOptIdMap.get(beid+roomId) + operStatus);
        // 当前operId和sessionUtils中的regOptId相同 而且手术状态是start 
        if (OperationMgr.isCurrentOperation(operId) 
                && MyConstants.OPERATION_STATUS_START.equals(operStatus)) {
            logger.info("initOperation------手术已经开始，operID " + operId);           
            resultObj.setBool(true);
            resultObj.setResult(0);
            return resultObj;
        }
        
        return initOperation(operId);
    }
    
    public static ResultObj initOperation(String operId) {
    	ResultObj resultObj = new ResultObj();
        logger.info("initOperation------手术初始化operId:" + operId + OperationMgr.operStatusMap.get(operId));

        if (!OperationMgr.isCurrentOperation(operId) //regOptId 和 operId 不一样      又都是start状态  
                && MyConstants.OPERATION_STATUS_START.equals(OperationMgr.operStatusMap.get(operId))) {
        	logger.error("initOperation--"+MyConstants.ERROR_MSG_OPERATION_ALREADY_STARTED+"--不同手术，强制结束手术，regOptId "+OperationMgr.getOperationMgrRegOptId(operId));
            logger.info("initOperation------不同手术，强制结束手术，regOptId " + getOperationMgrRegOptId(operId));
			//if (operation != null) {
			//   operation.setOperStatus(MyConstants.OPERATION_STATUS_READY);
			//    operation = null;
			//   operation = new Operation();
			//  operation.setOperModel(getCurrOperModel(operId));//设置为当前的手术模式
			// }

            setCommand(MyConstants.COMMAND_OPERATION_END,operId);
            
            OperationMgr.operStatusMap.remove(operId);
            OperationMgr.operModelMap.remove(operId);
            OperationMgr.regOptIdMap.remove(operId);
            
            DataMonitor.clearMonitorRecord();
            
            IPAdressBean iPAdressBean = getIPAdressByOperId(operId);
    		String key = iPAdressBean.getIp()+":"+iPAdressBean.getPort();
    		
            client.closeFutureChannel(key);
        }
        //operId和regOptId相同，不是start状态 或者 operId和regOptId不相同，不是start状态，则开启
        if ((OperationMgr.isCurrentOperation(operId) && !MyConstants.OPERATION_STATUS_START.equals(OperationMgr.operStatusMap.get(operId)))
                || (!OperationMgr.isCurrentOperation(operId) && !MyConstants.OPERATION_STATUS_START.equals(OperationMgr.operStatusMap.get(operId)))) {
            logger.info("initOperation------手术设备初始化，operID " + operId);
            
            String beid = getCurrOperBeid(operId);
            String roomId = getCurrOperRoomId(operId);
            OperationMgr.regOptIdMap.put(beid+roomId, operId);
            //OperationMgr.setCurrentOperation(operId);
            //String roomId = getCurrOperRoomId(operId);
            if (!OperationMgr.initDevice(roomId, operId)) {
            	logger.error("initOperation---开始手术---"+MyConstants.ERROR_MSG_DEVICE_INIT_FAILED+"-------------");
                resultObj.setBool(false);
                resultObj.setResult(-1);
                resultObj.setMsg("initOperation---开始手术---"+MyConstants.ERROR_MSG_DEVICE_INIT_FAILED);
            	return resultObj;
            } else {
                //operation.setOperStatus(MyConstants.OPERATION_STATUS_INIT);
                //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_INIT);
                
                OperationMgr.operStatusMap.put(operId, MyConstants.OPERATION_STATUS_INIT);
                
                
                DataMonitor.clearMonitorRecord();
            }
        } else {
            logger.info("initOperation------手术设备已经完成初始化，operID " + operId);
        }
        
        resultObj.setBool(true);
        resultObj.setResult(0);
        resultObj.setMsg("initOperation------手术设备完成初始化，operID " + operId);
        return resultObj;
    }
    
    public static boolean initDevice(String roomId, String operId) {
        logger.info("initDevice------设备初始化，roomId:" + roomId + " operId:" + operId);   
        List<Device> devices = DataMonitor.getDevicesByRoom(roomId);
        JSONObject json = new JSONObject();
        json.put("msgType", 0);
        json.put("devices", devices);
        logger.info("initDevice------设备列表：" + json.toString()); 
        //修改设备状态为检查中
        DataCollMgr.updateDeviceList(devices);
        // return client.sendMsg(json.toString());
        // 修改发送设备的消息，为同步消息，等待500ms
        //SocketClient client = socketMap.get(roomId);
        if(null == client){
        	return false;
        }
        return client.sendDeviceMsg(json.toString(),getChannelFuture(operId));
    }
    
    
//    public static boolean initDevice(String roomId, String operId) {
//        logger.info("initDevice------设备初始化，roomId:" + roomId + " operId:" + operId);   
//        List<Device> devices = DataMonitor.getDevicesByRoom(roomId);
//        JSONObject json = new JSONObject();
//        json.put("msgType", 0);
//        json.put("devices", devices);
//        logger.info("initDevice------设备列表：" + json.toString()); 
//        //修改设备状态为检查中
//        DataCollMgr.updateDeviceList(devices);
//        // return client.sendMsg(json.toString());
//        // 修改发送设备的消息，为同步消息，等待500ms
//        if(null == client){
//        	return false;
//        }
//        return client.sendDeviceMsg(json.toString());
//    }
    
    public static void initMonitorItems(String operId) {
        logger.info("initMonitorItems------监控项列表始化， operId:" + operId);  
        Map<String, Observe> monitorItems = getOperMonitors(operId, OperationMgr.operModelMap.get(operId));
        logger.info("initMonitorItems------监控项列表：" + JSONArray.fromObject(monitorItems).toString()); 
        if (!setOperMonitor(monitorItems,operId))
        {
           // SessionUtil.broadcast(session, getErrorDesc(Constants.ERROR_MSG_MONITOR_INIT_FAILED));
        }
    }
    
    public static ResultObj startOperation(String operId) {
    	ResultObj resultObj = new ResultObj();
        logger.info("startOperation------手术开始， operId:" + operId);   
        boolean init = init(operId);
        
        if(!init){
        	resultObj.setBool(false);
        	resultObj.setResult(-1);
        	resultObj.setMsg("初始化失败，请检查！");
        	return resultObj;
        }
        
        String operStatus = OperationMgr.operStatusMap.get(operId);
        
        if (MyConstants.OPERATION_STATUS_END.equals(operStatus)) {
            logger.info("startOperation------手术已经结束， operId:" + operId);
            resultObj.setBool(false);
        	resultObj.setResult(-2);
        	resultObj.setMsg("手术已经结束,手术operId= " + operId);
        	return resultObj;
        } else if (MyConstants.OPERATION_STATUS_START.equals(operStatus)) {
            logger.info("startOperation------手术已经开始， operId:" + operId); 
            resultObj.setBool(false);
        	resultObj.setResult(-3);
        	resultObj.setMsg("手术已经开始，手术operId= " + operId);
        	return resultObj;
        } else {
            if  (!MyConstants.OPERATION_STATUS_INIT.equals(operStatus)) {
                logger.info("startOperation------手术未初始化，重新初始化， operId:" + operId); 
                if (!initOperation(operId).getBool()) {
                	resultObj.setBool(false);
                 	resultObj.setResult(-4);
                 	resultObj.setMsg("设备检查失败，请检查!");
                 	return resultObj;
                }
            }
            logger.info("startOperation------发送监测项配置指令， operId:" + operId); 
            initMonitorItems(operId);
            logger.info("startOperation------发送手术开始指令， operId:" + operId); 
            DataMonitor.clearMonitorRec();
            boolean start = setCommand(MyConstants.COMMAND_OPERATION_START,operId);
            if(null != client && start){
            	OperationMgr.operStatusMap.put(operId, MyConstants.OPERATION_STATUS_START);
            }
            //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_START);
            
			//if (!operation.isAlive()) {
			//    operation.start();
			//}
            
            resultObj.setBool(true);
            resultObj.setResult(0);
            resultObj.setMsg("开启手术"+operId+"成功!");
            return resultObj;
        }
    }
    
    public static void endOperation(String operId) {
        logger.info("endOperation------发送手术结束指令， operId:" + operId); 
        
        setCommand(MyConstants.COMMAND_OPERATION_END,operId);
        
        OperationMgr.operStatusMap.remove(operId);
        OperationMgr.operModelMap.remove(operId);
        OperationMgr.regOptIdMap.remove(operId);
        
        //OperationMgr.setOperStatus(null);
        //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_END);
        //operation = null;
        //OperationMgr.setRegOptId(null);
        //OperationMgr.setOperModel(null);
        DataMonitor.clearMonitorRecord();
//        String roomId = getCurrOperRoomId(operId);
        //SocketClient client = socketMap.get(roomId);
//        if(client != null){
//        	client.close();
//        }
//        if (socketMap.containsKey(roomId)) {
//			socketMap.remove(roomId);
//		}
        
        IPAdressBean iPAdressBean = getIPAdressByOperId(operId);
		String key = iPAdressBean.getIp()+":"+iPAdressBean.getPort();
		
		client.closeFutureChannel(key);
    }
    
    
//    public static void endOperation( String operId) {
//        logger.info("endOperation------发送手术结束指令， operId:" + operId); 
//        
//        setCommand(MyConstants.COMMAND_OPERATION_END);
//        OperationMgr.setOperStatus(null);
//        //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_END);
//        //operation = null;
//        OperationMgr.setRegOptId(null);
//        OperationMgr.setOperModel(null);
//        
//        DataMonitor.clearMonitorRecord();
//        
//        client.close();
//    }
    
    public static void forceEndOperation(String operId){
    	if(null != getOperationMgrRegOptId(operId)){
    		logger.info("forceEndOperation------强制结束手术结束指令， operId: " + operId+",operation.id= "+getOperationMgrRegOptId(operId)); 
    	}
    	logger.info("forceEndOperation------强制结束手术结束指令， operId: [" + operId+"]");
    	//SocketClient client = socketMap.get(getCurrOperRoomId(operId));
        if(null != client){ //判断client非空，则发送命令采集服务，为空，则不处理
        	setCommand(MyConstants.COMMAND_OPERATION_END,operId);
        	
        	OperationMgr.operStatusMap.remove(operId);
            OperationMgr.operModelMap.remove(operId);
            OperationMgr.regOptIdMap.remove(operId);
        	
        	//OperationMgr.setOperStatus(null);
            //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_END);
            //operation = null;
            //OperationMgr.setRegOptId(null);
            //OperationMgr.setOperModel(null);
            
            DataMonitor.clearMonitorRecord();
            
            IPAdressBean iPAdressBean = getIPAdressByOperId(operId);
    		String key = iPAdressBean.getIp()+":"+iPAdressBean.getPort();
    		client.closeFutureChannel(key);
        }
        
    }
//    public static void forceEndOperation(String operId){
//    	if(null != OperationMgr.getRegOptId()){
//    		logger.info("forceEndOperation------强制结束手术结束指令， operId: " + operId+",operation.id= "+OperationMgr.getRegOptId()); 
//    	}
//    	logger.info("forceEndOperation------强制结束手术结束指令， operId: [" + operId+"]");
//    	
//        if(null != client){ //判断client非空，则发送命令采集服务，为空，则不处理
//        	setCommand(MyConstants.COMMAND_OPERATION_END);
//        	
//        	OperationMgr.setOperStatus(null);
//            //OperationMgr.setOperStatus(MyConstants.OPERATION_STATUS_END);
//            //operation = null;
//            OperationMgr.setRegOptId(null);
//            OperationMgr.setOperModel(null);
//            
//            DataMonitor.clearMonitorRecord();
//            client.close();
//        }
//        
//    }
    
       
    public static boolean setOperMonitor(Map<String, Observe> observes,String operId) {
        logger.info("setOperMonitor------设置并发送监测项指令"); 
        JSONObject json = new JSONObject();
        json.put("msgType", MyConstants.COMMAND_OPERATION_MONITOR);
        json.put("observes", observes.values());
        //SocketClient client = socketMap.get(getCurrOperRoomId(operId));
        if(null == client){
        	return false;
        }
        return client.sendMsg(json.toString(),getChannelFuture(operId));
    }
    
//    public static boolean setOperMonitor(Map<String, Observe> observes) {
//        logger.info("setOperMonitor------设置并发送监测项指令"); 
//        JSONObject json = new JSONObject();
//        json.put("msgType", MyConstants.COMMAND_OPERATION_MONITOR);
//        json.put("observes", observes.values());
//        if(null == client){
//        	return false;
//        }
//        return client.sendMsg(json.toString());
//    }
    
    // 切换手术模式的时候，不需要设置为当前最新的模式
    public static void setCurOperModel(String operId, String operModel) {
        logger.info("setOperModel------设置手术类型，operId:" + operId + " operModel:" + operModel); 
        //if (operation == null) {
        //    operation = new Operation();
        //}
        
        if (null == OperationMgr.operModelMap.get(operId) || !OperationMgr.operModelMap.get(operId).equals(operModel)) {
            logger.info("setOperModel------模式不同，重新发送监测项指令");
            
            OperationMgr.operModelMap.put(operId, operModel);
            
            //OperationMgr.setOperModel(operModel);
            //operation.setFreqChgFlag(1);
            Map<String, Observe> observes = getOperMonitors(operId, operModel);
            
            setOperMonitor(observes,operId);
        }
    }
    
    
    //修改频率
    public static void updateOperFreq(String operId){
    	logger.info("updateOperFreq---修改当前模式下的频率，regOptId: "+operId);
    	//重新发送监测项命令给采集服务，这样采集服务会获取到对应的freq，存储到rec对象中；
    	logger.info("重新发送监测项命令给采集服务，这样采集服务会获取到对应的freq，存储到rec对象中----");
    	Map<String, Observe> observes = getOperMonitors(operId, OperationMgr.operModelMap.get(operId));
        setOperMonitor(observes,operId);
    }
    
    public static Map<String, Observe> getOperMonitors(String operId, String operModel) {
        logger.info("getOperMonitors------获取监测项数据"); 
        String roomId = getCurrOperRoomId(operId); 
        Map<String, Observe> monitors = DataMonitor.getObservesByRoom(roomId, operId);
        
        Map<String, Observe> observes = new HashMap<String, Observe>();
        for(String key : monitors.keySet()) {
            Observe ob = monitors.get(key);
            observes.put(key, (Observe)ob.clone());
        }
        
        Collection<Observe> collObserves = observes.values();
        Iterator<Observe> ite = collObserves.iterator();
        while (ite.hasNext()) {
            Observe observe = ite.next();
            observe.setFreq(observe.getFreq() * 
                    SysUtil.strParseToInt(DataMonitor.getMonitorFreq(operModel)));
        }
        
        return observes;
    }
    
    public static ResultObj updateDeviceMonitorItems(String operId) {
        logger.info("updateDeviceMonitorItems------更新监测项数据，包括设备及对应的监测项"); 
        ResultObj res = new ResultObj();
		//        if (operation == null) {
		//            logger.info("updateDeviceMonitorItems------无手术信息，不需要更新监测项"); 
		//            res.setResult(-1);
		//            res.setMsg("当前手术室暂未初始化，请重新初始化！");
		//            return res;
		//        }
        String roomId = getCurrOperRoomId(operId);
        initDevice(roomId, operId);
        initMonitorItems(operId);
        
        res.setResult(0);
        res.setMsg("修改设备配置，并重新发送监测项数据！");
        return res ;
    }
    
    public static boolean setCommand(String msgType,String operId) {
        JSONObject json = new JSONObject();
        json.put("msgType", msgType);
        //SocketClient client = socketMap.get(getCurrOperRoomId(operId));
        return client.sendMsg(json.toString(),getChannelFuture(operId));
    }
    
    public static boolean setCommand(String msgType, int freq,String operId) {
        JSONObject json = new JSONObject();
        json.put("msgType", msgType);
        json.put("freq", freq);
        //SocketClient client = socketMap.get(getCurrOperRoomId(operId));
        return client.sendMsg(json.toString(),getChannelFuture(operId));
    }
    
    
//    public static boolean setCommand(String msgType) {
//        JSONObject json = new JSONObject();
//        json.put("msgType", msgType);
//        return client.sendMsg(json.toString());
//    }
//    
//    public static boolean setCommand(String msgType, int freq) {
//        JSONObject json = new JSONObject();
//        json.put("msgType", msgType);
//        json.put("freq", freq);
//        return client.sendMsg(json.toString());
//    }
    
    public static String getErrorDesc(String msg) {
        JSONObject jsonInit = new JSONObject();
        jsonInit.put("msgType", "warn");
        jsonInit.put("msgBody", msg);
        return jsonInit.toString();
    }
    
    /**
     * 获取当前模式
     * @param operId
     * @return
     */
    public static String getCurrOperModel(String operId){
    	String operModel = MyConstants.OPERATION_MODEL_NORMAL;
    	DocAnaesRecordService anaesRecordService = SpringContextHolder.getBean(DocAnaesRecordService.class);
    	DocAnaesRecord anaesRecord = anaesRecordService.searchAnaesRecordByRegOptId(operId);
    	String anaRecordId = "";
    	if(null!=anaesRecord){
    		anaRecordId = anaesRecord.getAnaRecordId();
    		EvtRescueeventService rescueeventService = (EvtRescueeventService)SpringContextHolder.getBean(EvtRescueeventService.class);
        	SearchFormBean searchBean = new SearchFormBean();
        	searchBean.setDocId(anaRecordId);
        	searchBean.setCurrentState("1");
    		List<EvtRescueevent> list = rescueeventService.searchRescueeventList(searchBean);
    		if(list.size()>0){
    			operModel = list.get(0).getModel();
    			logger.info("当前的手术模式==="+operModel);
    		}
    		logger.info("返回当前的手术模式operModel==="+operModel+",list的size==="+list.size());
    	}else{
    		logger.error("getCurrOperModel-------根据regOptId返回的doc_id为空，请检查！");
    	}
    	
    	return operModel;
    }
    
    /**
     * 获取当前手术状态
     * @param operId
     * @return
     */
    public static String getCurrOperState(String operId){
    	String state = "";
    	ControllerService  controllerService = SpringContextHolder.getBean(ControllerService.class);
    	Controller controller = controllerService.getControllerById(operId);
    	if(null != controller){
    		String str = controller.getState();
    		if(null != str && !("").equals(str)){
    			state = str;
    			logger.info("regOptId==="+operId+",当前手术的state状态为"+state);
    		}else{
    			logger.info("regOptId==="+operId+",当前手术没有state状态");
    			state = null;
    		}
    		
    	}else{
    		logger.info("regOptId==="+operId+",数据库中没有找到当前手术");
    		state = null;
    	}
    	return state;
    }
    
    
    /**
     * 获取当前手术室信息
     * @param operId
     * @return
     */
    public static String getCurrOperRoomId(String operId){
    	String roomId = "";
    	BasDispatchService  basDispatchService = SpringContextHolder.getBean(BasDispatchService.class);
    	BasDispatch basDispatch = basDispatchService.getDispatchOper(operId);
    	if(null != basDispatch){
    		roomId = basDispatch.getOperRoomId();
    		logger.info("getCurrOperRoomId==="+operId+",对应手术室为："+roomId);
    	}else{
    		logger.info("getCurrOperRoomId==="+operId+",数据库中没有找到对应手术信息");
    	}
    	return roomId;
    }
    
    
    public static String getCurrOperBeid(String operId){
    	String beid = "";
    	BasRegOptService  basRegOptService = SpringContextHolder.getBean(BasRegOptService.class);
    	BasRegOpt basRegOpt = basRegOptService.searchRegOptById(operId);
    	if(null != basRegOpt){
    		beid = basRegOpt.getBeid();
    		logger.info("getCurrOperBeid==="+operId+",对应beid："+basRegOpt.getBeid());
    	}else{
    		logger.info("getCurrOperBeid==="+operId+",数据库中没有找到对应手术信息");
    	}
    	return beid;
    }
    
    public static String getOperationMgrRegOptId(String operId){
    	String beid = getCurrOperRoomId(operId);
    	String roomId = getCurrOperRoomId(operId);
    	String mgrRegOptId = "";
    	if(StringUtils.isNotBlank(beid) && StringUtils.isNotBlank(roomId)){
    		mgrRegOptId = OperationMgr.regOptIdMap.get(beid+roomId);
    	}else{
    		logger.info("getOperationMgrRegOptId==="+operId+",数据库中没有找到对应手术信息");
    	}
    	return mgrRegOptId;
    }
    
    
    
    /**
     * 获取ChannelFuture
     * @param operId
     * @return
     */
    public static ChannelFuture getChannelFuture(String operId){
    	IPAdressBean iPAdressBean = getIPAdressByOperId(operId);
    	String remoteHost = iPAdressBean.getIp();
        int remotePort = iPAdressBean.getPort();
        
        logger.info("getChannelFuture===operId:"+operId+"======="+remoteHost+":"+remotePort);
        
		ChannelFuture future = SocketClient.futureMap.get(remoteHost+":"+remotePort);		
		
		logger.info("getChannelFuture===future:"+future);
		
		return future;
	}
    
    /**
     * 根据手术id获取对应采集服务的ip地址及端口
     * @param operId
     * @return
     */
    public static IPAdressBean getIPAdressByOperId(String operId){
    	
    	IPAdressBean IPAdressBean = new IPAdressBean();
        BasOperroomService basOperroomService = (BasOperroomService)SpringContextHolder.getBean(BasOperroomService.class);
        BasOperroom basOperroom = basOperroomService.queryRoomListById(operId);
        if(basOperroom != null ){
        	IPAdressBean.setIp(basOperroom.getRemotehost());
        	IPAdressBean.setPort(SysUtil.strParseToInt(basOperroom.getRemoteport()));
        }else{
        	IPAdressBean.setIp(Global.getConfig("remoteHost").toString().trim());
        	IPAdressBean.setPort(SysUtil.strParseToInt(Global.getConfig("remotePort").toString().trim()));
        }
		return IPAdressBean;
	}
    
    
    
    
}
