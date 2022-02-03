package com.hsck.ubfw.component.scheduler.query;

public class queryList{
	
	String insertInamt = "insert into TB_LINE_IF_INAMT_INFO(ACCT_TXDAY,ACCT_TXDAY_SEQ,ACCT_NO,CUST_CD,TX_AMT,DEPOSIT_YN,ACC_YN,DEL_FLAG,REG_DATE,REG_USER_ID,MOD_DATE,MOD_USER_ID) values (?,?,?,?,?,?,'N','N',sysdate,?,sysdate,?)";
	
	String updateInamt = "update TB_LINE_IF_INAMT_INFO set ACCT_NO = ?,CUST_CD = ?,TX_AMT = ?,DEPOSIT_YN = ?,REG_DATE = sysdate, REG_USER_ID = ?, MOD_DATE = sysdate, MOD_USER_ID = ? where ACCT_TXDAY = ?";

	public String getQueryList(String queryNm) throws Exception {
		
		queryList gueryList = new queryList();
		
		return gueryList.getClass().getDeclaredField
				(queryNm).get(gueryList).toString();
	} 
}
