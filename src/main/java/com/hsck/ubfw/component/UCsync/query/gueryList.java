package com.hsck.ubfw.component.UCsync.query;

import java.lang.reflect.Field;




public class gueryList{
	String getconnInfo = "select * from cm_cfgdbcon where con_yn = 'Y' order by con_sn";
	String getconnSnInfo = "select * from cm_cfgdbcon where con_sn = ? order by con_sn";
	String dbconnsql = "select * from cm_cfgdbcontbl where con_sn = ? order by sub_sn";
	String delTmp = "delete from ";
	String delJojik = "delete from pb_cfgjojik where man_yn = 'N'";
	String insertJojik = "insert into pb_cfgjojik select * from ";
	String delTelno = "delete from pb_cfgtelno where man_yn = 'N'";
	String insertTelno = "insert into pb_cfgtelno select * from ";
	String callJojik = "CALL prc_cfgjojik('system')";
	String callTelno = "CALL prc_cfgtelno('system')";
		
	String insertHislog = "insert into cm_hislog(ymd, hms, reg_id, work_nm, msg) values(?,?,?,?,?)";

		 
  
  
	
	
	public gueryList()throws Exception{
	}
	
	public String getQueryList(String queryNm) throws Exception {
		
		gueryList gueryList = new gueryList();
		
		return gueryList.getClass().getDeclaredField
				(queryNm).get(gueryList).toString();
	} 
	
	
	
	
	
	
	
}
