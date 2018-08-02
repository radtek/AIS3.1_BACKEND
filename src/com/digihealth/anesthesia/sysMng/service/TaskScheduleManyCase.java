package com.digihealth.anesthesia.sysMng.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;
import com.digihealth.anesthesia.sysMng.utils.TaskUtils;

/**
 * 定时任务并发执行
 * <功能详细描述>
 * 
 * @author  姓名 工号
 */
public class TaskScheduleManyCase implements Job
{

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException
    {
        BasTaskSchedule scheduleJob = (BasTaskSchedule) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
    
}
