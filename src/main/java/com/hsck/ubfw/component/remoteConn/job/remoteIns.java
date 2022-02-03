package com.hsck.ubfw.component.remoteConn.job;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.remoteConn.db.remotedbconn;
import com.hsck.ubfw.component.remoteConn.query.queryList;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public class remoteIns extends Thread {
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
	private static boolean running = false;
	private static remoteIns instance = null;
	
	private static String queryid = "";
	private static String RdbType = "";
	private static String xmlID = "";
	private static String className = "";
	private static String Method = "";
	private static HashMap param = null; 
	
	public remoteIns(){
		
	}
	
	public static synchronized remoteIns getInstance(){
		if(instance == null ){
			instance = new remoteIns();
		}
		return instance;
	}
	
	public void loading(){
		this.start(); 
	}
	
	public void shutdown(){
		try {
			if(instance != null) {
				instance.shutdown();
			}
		} catch (Exception e) {
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
	
	public static void setQueryID(String qid) {
		queryid = qid;
	}
	
	public static void setRdbType(String RType) {
		RdbType = RType;
	}
	
	public static void setXmlId(String xmlId, HashMap parameterObject) {
		xmlID = xmlId;
		param = parameterObject;
	}
	
	public static void setClassName(String cName) {
		className = cName;
	}
	
	public static void setMethod(String method) {
		Method = method;
	}
	
	public void run(){
		running = true;
		boolean sucessconn = true;
		try {
			log.debug("remote insert thred start");
			log.debug("xmlID : " + xmlID.replaceAll("[\r\n]","") + ", RdbType : " + RdbType.replaceAll("[\r\n]","") + ", queryid : " + queryid.replaceAll("[\r\n]","") + ", className : " + className.replaceAll("[\r\n]","") + ", Method : " +  Method.replaceAll("[\r\n]",""));
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
			ContentService service = (ContentService) webApplicationContext.getBean("contentService");
			List<EgovMap> sendList = service.remoteInsertData(xmlID, param);
			remotedbconn remoteconn = new remotedbconn(RdbType);
			queryList queryList = new queryList();
			String query = queryList.getQueryList(queryid);
			remoteconn.getselectList(remoteconn, query, sendList, className, Method);
			
			
			running = false;
			log.debug("remote insert thred end");
			
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
			log.debug("remote insert thred Exeception");
		}finally {
			log.debug("remote insert thred final");
		}
	}
	
	

	
}
 