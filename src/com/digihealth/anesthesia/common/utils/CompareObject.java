package com.digihealth.anesthesia.common.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.task.MsgProcessReceiver;
import com.digihealth.anesthesia.evt.formbean.ChangeValueFormbean;
import com.digihealth.anesthesia.evt.po.EvtMedicalEvent;
import com.google.common.base.Objects;

public class CompareObject{
	
	private final static Logger logger = Logger.getLogger(MsgProcessReceiver.class);
	
	public final static String DRIVER = Global.getConfig("jdbc.driver").toString();
	public final static String URL = Global.getConfig("jdbc.url").toString();
	public final static String USERNAME = Global.getConfig("jdbc.username").toString();
	public final static String PASSWORD = Global.getConfig("jdbc.password").toString();
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public  Map<String,String> getColumnListByTableName(String tableName){
		
		String sql = "SELECT COLUMN_NAME,COLUMN_COMMENT FROM information_schema.COLUMNS where table_name = '"+tableName+"'";
		Map<String,String> hisMap = new HashMap<String,String>();
		try {
			conn = ConnectionUtils.getConnection(DRIVER, URL, USERNAME, PASSWORD); 
			if (null != conn) {
				pstmt = (PreparedStatement)conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String column = rs.getString("column_name");
					String columnContent = rs.getString("column_comment");
					//如果字段未写备注时则取column的值
					if(StringUtils.isBlank(columnContent)){
						columnContent = column;
					}
					//基线id这种字段就直接屏蔽
					if(!"beid".equals(column)){
						hisMap.put(column, columnContent);
					}
				}
			}
		}catch (Exception e) {
			logger.error("ERROR:getColumnList=" + Exceptions.getStackTraceAsString(e));
		} finally {
			try {
				if (null != pstmt) {// 释放 PreparedStatement 对象
					pstmt.close();
				}
				if (null != rs) {// 释放 ResultSet
					rs.close();
				}
				if (null != conn) {// 释放 connection
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return hisMap;
		}
		
	}
	
//	public  Map<String,String> getColumnListByTableName(String tableName){
//		Map<String,String> hisMap = new HashMap<String,String>();
//		List<Map> columnList = evtAnaesModifyRecordDao.getColumnListByTableName(tableName);
//		if(null!=columnList && columnList.size()>0){
//			for (Map map : columnList) {
//				hisMap.put(map.get("columnName").toString(), map.get("columnComment").toString());
//			}
//		}
//		return hisMap;
//	}
	
	
	/**
	 * 根据字段名称返回字段的备注
	 * @param tableName
	 * @param property
	 * @return
	 * @throws Exception
	 */
	public String getColumnContentByProperty(String tableName,String property)throws Exception{
		Map<String,String> hisMap = getColumnListByTableName(tableName);
		String columnContent = "";
		if(hisMap.containsKey(property)){
			columnContent =  hisMap.get(property);
		}
		return columnContent;
	}
	
    /** 属性字段名、值、数据类型 */  
	/**
	 * 比较获取两个相同对象的值得差异，并返回
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 */
    public static List<ChangeValueFormbean> getCompareResultByFields(Object oldObj ,Object newObj)  throws Exception {  
    	
    	List<ChangeValueFormbean> changeList = new ArrayList<ChangeValueFormbean>();
    	
    	if(null == oldObj || null ==newObj){
    		return null;
    	}
    	
    	//获取
    	Field[] fields = newObj.getClass().getDeclaredFields();  
        for (Field field : fields) {  
            field.setAccessible(true); 
            
            //System.out.println("oldObj:"+field.getName()+"---value:"+field.get(oldObj));
            
            
            Field[] fields1 = oldObj.getClass().getDeclaredFields();  
            for (Field field1 : fields1) {  
            	field1.setAccessible(true);
            	
            	 //System.out.println("newObj:"+field.getName()+"---value:"+field1.get(newObj));
            	
            	if(field1.getName().equals(field.getName())){
            		//System.out.println("newObj:"+field1.getName()+"---value:"+field1.get(newObj));
            		//System.out.println("oldObjValue:"+field.get(oldObj)+"---newObjValue:"+field1.get(newObj));
            		
            		//这里在对比的时候如 null 跟字符串空""是true
            		if(!Objects.equal(field1.get(oldObj), field.get(newObj))){
            			
            			String oldValue = field1.get(oldObj)!=null?field1.get(oldObj)+"":"";
            			String newValue = field.get(newObj)!=null?field.get(newObj)+"":"";
            			//里面再做一次判断
            			if(!oldValue.equals(newValue)){
            			
	            			ChangeValueFormbean changePo = new ChangeValueFormbean();
	            			changePo.setModProperty(field.getName());
	            			if("java.util.Date".equals(field.getType().getName())){
	            				changePo.setOldValue(field1.get(oldObj)!=null?DateUtils.formatDateTime((Date)field1.get(oldObj)):"");
	                			changePo.setNewValue(field.get(newObj)!=null?DateUtils.formatDateTime((Date)field.get(newObj)):"");
	            			}else{
	            				changePo.setOldValue(field1.get(oldObj)!=null?field1.get(oldObj)+"":"");
	            				changePo.setNewValue(field.get(newObj)!=null?field.get(newObj)+"":"");
	            			}
	            			changeList.add(changePo);
            			}
            		}
            		
            		
            		break;
            	}
            }
        } 
    	
        return changeList;
    } 
	
    public static void main(String[] args)throws Exception{
    	
    	EvtMedicalEvent evt = new EvtMedicalEvent();
    	evt.setStartTime(new Date());
    	
    	EvtMedicalEvent evt1 = new EvtMedicalEvent();
    	
    	getCompareResultByFields(evt,evt1);
    }
    

}
