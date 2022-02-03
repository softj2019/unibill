package com.hsck.ubfw.component.UCsync;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsck.ubfw.component.UCsync.db.dbconn;
import com.hsck.ubfw.component.UCsync.query.gueryList;

public class UCsyncExcute {
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
	public ArrayList<HashMap> getConInfo() throws Exception{
		dbconn dbconn = null;
		PreparedStatement connInfoStat = null;
		ArrayList<HashMap> connInfoStatRs = null;
		
		try {
			dbconn = new dbconn();
	    	gueryList qList = new gueryList();
	    	String connInfosql = qList.getQueryList("getconnInfo");
	    	//select 
	    	connInfoStat = dbconn.getstatement(connInfosql);
	    	connInfoStatRs = dbconn.getselectList(connInfoStat);
	    } catch (Exception e) {
			// TODO: handle exception
		}
		
    	
    	
    	return connInfoStatRs;
    	
	}
	
	public ArrayList<HashMap> getConSnInfo(String conSn) throws Exception{
		dbconn dbconn = null;
		PreparedStatement connInfoStat = null;
		ArrayList<HashMap> connInfoStatRs = null;
		
		System.out.println("인사디비 연결정보 조회");
		try {
			dbconn = new dbconn();
	    	gueryList qList = new gueryList();
	    	String connInfosql = qList.getQueryList("getconnSnInfo");
	    	//select 
	    	connInfoStat = dbconn.getstatement(connInfosql);
	    	connInfoStat.setString(1, conSn);
	    	connInfoStatRs = dbconn.getselectList(connInfoStat);
	    } catch (Exception e) {
			// TODO: handle exception
		}
		
    	
    	
    	return connInfoStatRs;
    	
	}

	
}
 