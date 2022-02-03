package com.hsck.ubfw.component.UCsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;

import com.hsck.ubfw.component.UCsync.schedule.UCsyncScheUtil;
import com.hsck.ubfw.component.UCsync.schedule.UCsyncSchedule;


public class UCsyncManager extends Thread {
	private static boolean running = false;
	private static UCsyncManager instance = null;
	private static Scheduler scheduler = null;
	
	private UCsyncManager(){
		
	}
	
	public static UCsyncManager getInstance(){
		if(instance == null ){
			instance = new UCsyncManager();
		}
		
		return instance;
	}
	
	public void loading(){
		this.start(); 
	}
	
	public static Scheduler getScheduler() {
		return scheduler;
	} 
	
	public void shutdown(){
		try {
			if(scheduler != null) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isRuuning(){
		return running;
	}
	
	public static boolean isChknull(){
		if(instance == null ){
			return true;
		}else {
			return false;
		}
		
	}
	
	public void run(){
		running = true;
		boolean sucessconn = true;
		try {
			System.out.println("UCsync thred start");
			//Thread.sleep(2000);
			UCsyncExcute ucExe = new UCsyncExcute();
			//UC 커넥션정보 및 스케줄링 정보 가져오기
			ArrayList<HashMap> connInfoRs = ucExe.getConInfo();
			int cnt = 0;
			for(HashMap info : connInfoRs) {
				if(sucessconn) {
					try {
						//실행시점 설정  -- D:매일, H:매시간, M:매월
						UCsyncScheUtil schedule = new UCsyncScheUtil();
						String timeRule = "";
						timeRule = schedule.getTimeRule(info);
						// 테스트용 실행 시점을 결정하는 Trigger 생성(10초 될때마다)
		            	//timeRule = "10 * * * * ?";
						
						// 실행 시점을 결정하는 Trigger 생성
		            	JobDetail job = new JobDetail();
		            	job.setName("UCsyncJob");
		                job.setJobClass(UCsyncSchedule.class);
		                Map dataMap = job.getJobDataMap();
		                dataMap.put("info", info);
		            	
		            	CronTrigger trigger = new CronTrigger();
		                trigger.setName("UCsyncTrigger");
		                trigger.setCronExpression(timeRule); //초 분 시 일 월 요일 년(선택사항)
		                
		                
		                
		                scheduler = new StdSchedulerFactory().getScheduler();
		                scheduler.start();
		                scheduler.scheduleJob(job,trigger);
		                
						//정상 진행시 다음 리스트 진행 하지 않고 정지
						cnt++;
						sucessconn = false;
					} catch (Exception e) {
						sucessconn = true;
					}
				}
			}
			
			
			System.out.println("UCsync thred end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
}
 