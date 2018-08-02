package com.digihealth.anesthesia.sysMng.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param taskSchedule
	 */
	public static void invokMethod(BasTaskSchedule taskSchedule) {
		Object object = null;
		Class clazz = null;
		if (StringUtils.isNotBlank(taskSchedule.getSpringId())) 
		{
			object = SpringUtils.getBean(taskSchedule.getSpringId());
		}
		else if (StringUtils.isNotBlank(taskSchedule.getBeanClass())) 
		{
			try {
				clazz = Class.forName(taskSchedule.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
			    log.error("启动定时任务错误=================:  " + Exceptions.getStackTraceAsString(e));
			    throw new RuntimeException(Exceptions.getStackTraceAsString(e));
			}
		}
		if (object == null) {
			log.error("任务名称 = [" + taskSchedule.getTaskName() + "]---------------未启动成功，请检查实现类是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(taskSchedule.getMethodName());
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + taskSchedule.getTaskName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
		    log.error("任务名称 = [" + taskSchedule.getTaskName() + "]---------------" + e.getMessage());
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (Exception e) {
			    log.info("执行定时任务[" + taskSchedule.getTaskName() + "]失败：    "+Exceptions.getStackTraceAsString(e));
	            throw new RuntimeException(Exceptions.getStackTraceAsString(e));
			}
		}
		System.out.println("任务名称 = [" + taskSchedule.getTaskName() + "]----------启动成功");
	}
}
