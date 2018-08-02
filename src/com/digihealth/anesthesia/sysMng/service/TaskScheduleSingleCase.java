package com.digihealth.anesthesia.sysMng.service;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;
import com.digihealth.anesthesia.sysMng.utils.TaskUtils;

/**
 * 定时任务非并发执行，只有前面的定时任务执行完成，才执行当前定时任务
 * <功能详细描述>
 * 
 * @author  姓名 工号
 */
@DisallowConcurrentExecution
public class TaskScheduleSingleCase implements Job
{

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException
    {
        // TODO Auto-generated method stub
        BasTaskSchedule scheduleJob = (BasTaskSchedule) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
    
}
