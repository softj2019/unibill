package com.hsck.ubfw.component.UCsync.schedule;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsck.ubfw.component.UCsync.UCsyncManager;
import com.hsck.ubfw.component.UCsync.db.dbconn;
import com.hsck.ubfw.component.UCsync.db.remotedbconn;
import com.hsck.ubfw.component.UCsync.query.gueryList;
import com.hsck.ubfw.component.com.cmm.util.DateUtil;



public class UCsyncScheExecute  {
	protected Log log = (Log) LogFactory.getLog(this.getClass());		
	
	HashMap info = null;
	private static UCsyncScheExecute instance = null;
	private static boolean running = false;
	String msg = "";
	String rsmsg = "";
	
	boolean autoFlag = true;
	String regId = "system";
	
    public UCsyncScheExecute(HashMap info) {
    	this.info = info;
    }
    
    public void setInfo(HashMap info) {
    	this.info = info;
    }
    
    public void setautoFlag(boolean auto, String regId) {
    	this.autoFlag = auto;
    	this.regId = regId;
    }
    
	public static UCsyncScheExecute getInstance(){
		if(instance == null ){
			HashMap info = new HashMap();
			instance = new UCsyncScheExecute(info);
		}
		
		return instance;
	}
    
	public static boolean isRuuning(){
		return running;
	}
	
	public static void excute(){
		try {
			instance.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMsg() {
		return msg;
	}
	
    public int start() throws Exception{
    	gueryList qList = new gueryList();
    	try {
    		running = true;
        	
        	
        	//디비접속
            dbconn dbconn1 = new dbconn();
            String conSn = info.get("con_sn").toString();
            //원격접속정보 및 select 쿼리 가져오기
            String dbconnsql = qList.getQueryList("dbconnsql");
            PreparedStatement dbconnStat = dbconn1.getstatement(dbconnsql);
            dbconnStat.setString(1, conSn);
            ArrayList<HashMap> dbconnStatRs = dbconn1.getselectList(dbconnStat);
            
            if(dbconnStatRs.size() == 0){
            	conSn = "0";
            	dbconn1 = new dbconn();
            	dbconnStat = dbconn1.getstatement(dbconnsql);
            	dbconnStat.setString(1, conSn);
            	dbconnStatRs = dbconn1.getselectList(dbconnStat);
            }
            
            //윈격 접속하여 정보 가져오기
            for(HashMap conndb : dbconnStatRs){
            	String sql = conndb.get("sel_sql").toString();
            	//원격디비 정보 가져오기
            	//원격디비 접속
            	msg = "원격디비 접속을 확인하세요.";
            	log.debug("[인사디비] 원격접속");
            	remotedbconn rdbconn = new remotedbconn(info.get("con_type").toString(), info.get("ip").toString(), info.get("port").toString(), info.get("db_nm").toString(), info.get("userid").toString(), info.get("passwd").toString() );
            	log.debug("[인사디비] 임시테이블삭제");            	
            	//임시 테이블 delete
            	String delTmpSQL = qList.getQueryList("delTmp") + conndb.get("tbl_nm").toString() + " where grp_cd != 'HSROOT' and grp_cd != '~'";
            	dbconn deInsaconn = new dbconn();
            	PreparedStatement deInsaconnstat = deInsaconn.getstatement(delTmpSQL);
            	msg = conndb.get("tbl_nm").toString() + "테이블 삭제중 에러가 발생하였습니다.";
            	int delJojikRs = deInsaconnstat.executeUpdate();
            	log.debug("[인사디비] 임시테이블삭제 완료");
            	//원격지 테이블에 select 임시테이블에insert 
            	log.debug("[인사디비] 임시 테이블에 입력");
            	String insTmpSQL = "insert into ";
            	insTmpSQL += conndb.get("tbl_nm").toString() + " (" + conndb.get("tbl_cols").toString() + ") values (" ;
            	PreparedStatement rdStat = rdbconn.getstatement(sql);
            	msg = conndb.get("tbl_nm").toString() + " 테이블에 삽입중 에러가 발생하였습니다.";
            	ArrayList<HashMap> rdStatRs = rdbconn.getselectList(rdStat, insTmpSQL);
            	log.debug("[인사디비] 임시 테이블에 입력 완료");
            	
            	//임시테이블에서 정식 테이블에 insert
            	log.debug("[인사디비] 정식테이블에 입력");
            	dbconn getInsaTempconn = new dbconn();
            	String getInsaTempsql = "";
            	CallableStatement getInsaStat = null;
            	
            	log.debug("[인사디비] 프로시저수행");
        		getInsaTempsql = "CALL " + conndb.get("proc_nm").toString() + "(?)";
            	getInsaStat = getInsaTempconn.getdbconn().prepareCall(getInsaTempsql);
        		getInsaStat.setString(1, regId);
            	msg = conndb.get("proc_nm").toString() + " 프로시저 수행중 에러가 발생하였습니다.";
            	ResultSet rs = getInsaStat.executeQuery();
            	
            	while (rs.next()) {
            		String prcnm = conndb.get("proc_nm").toString();
            		if(prcnm.equals("prc_cfgjojik")){
            			rsmsg = "[성공] 조직연동이 성공하였습니다.";
            		}else if(prcnm.equals("prc_cfgtelno")) {
            			rsmsg = rsmsg + " [성공] 회선연동이 성공하였습니다.";
            		}else {
            			rsmsg += "[성공] 연동이 성공하였습니다.";
            		}
				}
            	log.debug("[인사디비] 프로시저수행완료");
            	log.debug("[인사디비] 정식테이블에 입력완료");
            	
            }
            
//            //조직 PATH 계산 프로시저 호출
//            System.out.println("====================================================================");
//            System.out.println("조직 패스 계산 프로시저 시작");
//            dbconn getPathTempconn = new dbconn();
//        	String getPathTempsql = "";
//        	CallableStatement getPathStat = null;
//        	
//        	getPathTempsql = "CALL prc_jojiknm()";
//        	getPathStat = getPathTempconn.getdbconn().prepareCall(getPathTempsql);
//        	msg = "prc_jojiknm 프로시저 수행중 에러가 발생하였습니다.";
//        	ResultSet rs = getPathStat.executeQuery();
//        	
//        	while (rs.next()) {
//        		//rsmsg = rs.getString(1);
//			}
//        	
//        	System.out.println("조직 패스 계산 프로시저 종료");
//            System.out.println("====================================================================");
//            
            
            System.out.println("[인사디비] 성공이력 테이블 insert 시작");
        	//성공이력 테이블(cm_hislog) insert
            dbconn logdbconn = new dbconn();
            //원격접속정보 및 select 쿼리 가져오기
            String logdbconnsql = qList.getQueryList("insertHislog");
            PreparedStatement logdbconnStat = logdbconn.getstatement(logdbconnsql);
            //날짜
            DateUtil curdate = new DateUtil();
            
            logdbconnStat.setString(1, curdate.getCurrentDateString());
            logdbconnStat.setString(2, curdate.getCurrentTimeString());
            logdbconnStat.setString(3, regId);
            if(autoFlag) {
            	logdbconnStat.setString(4, "자동동기화");
            }else {
            	logdbconnStat.setString(4, "수동동기화");
            }
            
            logdbconnStat.setString(5, rsmsg);
            msg = "성공 이력테이블 생성중 에러가 발생하였습니다.";
            //insert 수행
            logdbconn.insert(logdbconnStat);
            System.out.println("[인사디비] 성공이력 테이블 insert 완료");
            System.out.println("====================================================================");
            
            msg = "동기화가 완료 되었습니다.";
        	//Thread.sleep(5000);
        	running = false;
            return 1;
    		
    		
    		
    		
    		
		} catch (Exception e) {
			//실패이력 이력테이블(cm_hislog)에 insert
			dbconn flogdbconn = new dbconn();
            //원격접속정보 및 select 쿼리 가져오기
            String logdbconnsql = qList.getQueryList("insertHislog");
            PreparedStatement flogdbconnStat = flogdbconn.getstatement(logdbconnsql);
            //날짜
            DateUtil curdate = new DateUtil();
            
            flogdbconnStat.setString(1, curdate.getCurrentDateString());
            flogdbconnStat.setString(2, curdate.getCurrentTimeString());
            flogdbconnStat.setString(3, regId);
            if(autoFlag) {
            	flogdbconnStat.setString(4, "자동동기화");
            }else {
            	flogdbconnStat.setString(4, "수동동기화");
            }
            flogdbconnStat.setString(5, "[실패] " + msg);
            
            //insert 수행
            flogdbconn.insert(flogdbconnStat);
            System.out.println("실패이력 테이블 insert 완료");
            System.out.println("====================================================================");
			
			running = false;
			e.printStackTrace();
			
			
			return -1;
		}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

	
	
}
