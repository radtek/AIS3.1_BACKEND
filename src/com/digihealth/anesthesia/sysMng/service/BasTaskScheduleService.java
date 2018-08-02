package com.digihealth.anesthesia.sysMng.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;

@Service
public class BasTaskScheduleService extends BaseService
{
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_NOT_RUNNING = 0;
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
    
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    public List<BasTaskSchedule> searchAllTaskSchedule() {
        return basTaskScheduleDao.searchAllTaskSchedule(getBeid());
    }
    
    public BasTaskSchedule searchTaskScheduleById(String id) {
        return basTaskScheduleDao.selectByPrimaryKey(id);
    }
    
    @Transactional
    public void updateTaskSchedule(BasTaskSchedule taskSchedule, ResponseValue resp)
    {
        BasTaskSchedule oldTaskSchedule = basTaskScheduleDao.selectByPrimaryKey(taskSchedule.getId());
        if (null == oldTaskSchedule)
        {
            resp.setResultCode("100000000");
            resp.setResultMessage("找不到当前定时任务");
            return;
        }
        oldTaskSchedule.setUpdateTime(new Date());
        
        //更改定时任务状态
        if (null != taskSchedule.getTaskStatus())
        {
            if (STATUS_RUNNING == taskSchedule.getTaskStatus().intValue())
            {
                oldTaskSchedule.setTaskStatus(STATUS_RUNNING);
                try
                {
                    addJob(oldTaskSchedule);
                }
                catch (Exception e)
                {
                    resp.setResultCode("1000000");
                    resp.setResultMessage("开始定时任务" + oldTaskSchedule.getTaskName() + "失败");
                    throw new RuntimeException("开始定时任务" + oldTaskSchedule.getTaskName() + "失败:  " + e.getMessage());
                }
            }
            else
            {
                try
                {
                    deleteJob(oldTaskSchedule);
                }
                catch (Exception e)
                {
                    resp.setResultCode("1000000");
                    resp.setResultMessage("停止定时任务" + oldTaskSchedule.getTaskName() + "失败");
                    throw new RuntimeException("停止定时任务" + oldTaskSchedule.getTaskName() + "失败:  " + e.getMessage());
                }
            }
        }
        
        //更新定时任务时间配置
        if (StringUtils.isNotBlank(taskSchedule.getCronExpression()))
        {
            try 
            {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskSchedule.getCronExpression());
            } 
            catch (Exception e)
            {
                resp.setResultCode("1000000");
                resp.setResultMessage("定时任务" + oldTaskSchedule.getTaskName() + "cron表达式有误，不能被解析！ ");
                throw new RuntimeException("定时任务" + oldTaskSchedule.getTaskName() + "cron表达式有误，不能被解析！ ");
            }
            
            oldTaskSchedule.setCronExpression(taskSchedule.getCronExpression());
            if (null != oldTaskSchedule.getTaskStatus() && STATUS_RUNNING == oldTaskSchedule.getTaskStatus().intValue())
            {
                try
                {
                    updateCornExpression(oldTaskSchedule);
                }
                catch (Exception e)
                {
                    resp.setResultCode("1000000");
                    resp.setResultMessage("更新定时任务" + oldTaskSchedule.getTaskName() + "时间配置失败");
                    throw new RuntimeException("更新定时任务" + oldTaskSchedule.getTaskName() + "时间配置失败:  " + e.getMessage());
                }
            }
        }
        basTaskScheduleDao.updateByPrimaryKeySelective(taskSchedule);
    }
    
    /**
     * 更新job时间表达式
     * 
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateCornExpression(BasTaskSchedule scheduleJob) throws Exception
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    @Transactional
    public void deleteTaskSchedule(String id)
    {
        basTaskScheduleDao.deleteByPrimaryKey(id);
    }
    
    
    /**
     * 添加任务
     * 
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void addJob(BasTaskSchedule job) throws Exception{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        logger.debug("---------------调度器-------------------------： " + scheduler);
        //触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTaskName(), job.getTaskGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        logger.debug("---------------触发器-------------------------： " + trigger);
        // 不存在，创建一个
        if (null == trigger) {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //定时任务为单例还是多例
            Class clazz = CONCURRENT_IS.equals(job.getIsConcurrent()) ? TaskScheduleManyCase.class : TaskScheduleSingleCase.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getTaskName(), job.getTaskGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", job);
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getTaskName(), job.getTaskGroup()).withSchedule(scheduleBuilder).build();
            logger.debug("---------------添加触发器-------------------------： " + trigger);
            scheduler.scheduleJob(jobDetail, trigger);
        } 
    }
    
    /**
     * 删除一个job
     * 
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(BasTaskSchedule job) throws Exception
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getTaskName(), job.getTaskGroup());
        scheduler.deleteJob(jobKey);
    }
    
    @PostConstruct
    public void init()
    {
        // 这里获取任务信息数据
        List<BasTaskSchedule> jobList = basTaskScheduleDao.searchAllTaskSchedule(getBeid());
    
        for (BasTaskSchedule job : jobList)
        {
            try
            {
                if (null != job.getTaskStatus() && 1 == job.getTaskStatus().intValue())
                {
                    addJob(job);
                }
            }
            catch (Exception e)
            {
                logger.error("定时任务[" + job.getTaskName() + "]启动报错：   " + e.getMessage());
                e.printStackTrace();
                //throw new RuntimeException("定时任务[" + job.getTaskName() + "]启动报错：   " + e.getMessage());
            }
        }
    }
}
