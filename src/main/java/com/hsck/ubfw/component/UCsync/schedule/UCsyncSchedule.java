package com.hsck.ubfw.component.UCsync.schedule;


import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class UCsyncSchedule implements Job {
	@Override
	public void execute(JobExecutionContext param) throws JobExecutionException {
		JobDataMap gobDataMap = param.getJobDetail().getJobDataMap();
		HashMap info = (HashMap)gobDataMap.get("info");
		UCsyncScheExecute UCExcute = new UCsyncScheExecute(info);
		try {
			UCExcute.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
