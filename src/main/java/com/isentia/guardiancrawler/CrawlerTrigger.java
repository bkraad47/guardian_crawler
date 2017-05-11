/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

/**
 *
 * @author bulkg (Badruddin Kamal - Raad)
 */
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class CrawlerTrigger
{
    public void start() throws Exception
    {
    	JobDetail job = new JobDetail();
    	job.setName("Crawler");
    	job.setJobClass(CrawlerJob.class);

        // TriggerScheduler at 2 am everyday
    	CronTrigger trigger = new CronTrigger();
    	trigger.setName("CrawlerTrigger");
    	trigger.setCronExpression("0 0 2 * * ?");

    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);

    }
}