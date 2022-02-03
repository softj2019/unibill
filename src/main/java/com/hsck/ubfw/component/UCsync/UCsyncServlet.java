package com.hsck.ubfw.component.UCsync;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.servlet.FrameworkServlet;

import com.hsck.ubfw.component.UCsync.schedule.UCsyncSchedule;



@SuppressWarnings("serial")
public class UCsyncServlet extends HttpServlet {

	public final void init(ServletConfig config) throws ServletException{
		super.init(config);
		
		String UCsync = null;
		try {
			
			InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
			Properties props = new Properties();
			props.load(in);
			
			UCsync = props.getProperty("Globals.UCsync");		
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(UCsync.equals("Y")) {
			System.out.println("UCsyncManager start");
			UCsyncManager.getInstance().loading();
		}else {
			System.out.println("UCsync State false");
		}
		
		
		
		

		
		
		
		
		
	}
	
}
 