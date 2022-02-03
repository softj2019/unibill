package com.hsck.ubfw.component.UCsync.schedule;


import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class UCsyncScheUtil {
	public String getTimeRule(HashMap info) throws Exception {
		String timeRule = "";
		
		String workDay = info.get("work_tm").toString().substring(0,2).replace("0", "");
		String workHour = info.get("work_tm").toString().substring(2,4).replace("0", "");
		String workMin = "";
		
		if(info.get("work_tm").toString().substring(4,6).equals("00")){
			workMin = "0";
		}else{
			workMin = info.get("work_tm").toString().substring(4,6);
		}
		
		// D:매일, H:매시간, M:매월
		if(info.get("work_cycle").equals("D")){
			timeRule = "0 " + workMin + " " + workHour + " * * ?"; // 0 0 9 * * ?
		}else if(info.get("work_cycle").equals("H")){
			timeRule = "0 " + workMin + " * * * ?";
		}else if(info.get("work_cycle").equals("M")){
			timeRule = "0 " + workMin + " " + workHour + " " + workDay + " * ?"; // 0 0 9 10 * ?
		}
		
		return timeRule;
	}
}
