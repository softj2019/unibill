package com.hsck.ubfw.component.remoteConn.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class queryList{
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	String testinsert = "insert into T1 (userid, reg_date, reg_user_ID) values (?,?,?)";
	String insertkspa = 
		       "insert into TB_LINE_IF_KPSA_INFO(" 
		     + " if_div               "
		     + ",work_div            "
		     + ",acc_yyyymm          "
		     + ",rep_cust_cd         " 
		     + ",cust_cd             "
		     + ",charge_plan_date    "
		     + ",irs_num             "
		     + ",irs_sub_num         "
		     + ",vat_cd              "
		     + ",sales_amt           "
		     + ",vat_amt             "
		     + ",acc_attr            "
		     + ",trade_type          " 
		     + ",deposit_plan_date   "
		     + ",pay_terms           "
		     + ",charge_contents     "
		     + ",acc_code            "
		     + ",if_status           "    
		     + ",reg_date            "
		     + ",reg_user_id         " 
		     + ",mod_date            "
		     + ",mod_user_id)        "
		     + "values(              "
		     + "?                    "
		     + ",?                   "
		     + ",?                   "
		     + ",?                   "
		     + ",?                   "
		     + ",to_date(?,'YYYYMMDD') "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",to_date(?,'YYYYMMDD') "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",?                     "
		     + ",sysdate               "
		     + ",? "
		     + ",sysdate               "
		     + ",? "
			 + ")";
	
    String updateRKspa = "update TB_LINE_IF_KPSA_INFO set charge_plan_date = ?,irs_num = ?,irs_sub_num= ?,vat_cd = ?,sales_amt = ?,vat_amt = ?,acc_attr = ?,trade_type = ?,deposit_plan_date = ?,pay_terms = ?" +
    ",charge_contents = ?,acc_code = ?,if_status = 'L',reg_date = sysdate ,reg_user_id = ?,mod_date = sysdate ,mod_user_id = ? where if_status = 'R' and if_div = ? and work_div = ? and acc_yyyymm = ? and rep_cust_cd = ? and cust_cd = ?";

    String insertCust = "insert into TB_LINE_IF_CUST_INFO(if_div,irs_num,irs_sub_num,if_status,pay_cd,cust_nm,irs_sub_nm,company_reg_num,company_type,zip_cd,addr1,addr2,business_type,industry_type,ceo_user_name,shin_yn,reg_date,reg_user_id,mod_date,mod_user_id)" +
    "values('001',?,?,'C',NULL,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?)";
	
    String insertgl399t = "insert into GL399T(compcd,accunt,jurdat,jurno,jurser,jscd1,jscd2,indpcd,inempno,remark,accdat,acmndiv,jurno1,acctcd,bgitcd" + 
    		",strmkcd,strmknm,addrmk,dbamt,cdamt,rtacut,rtdpcd,osaccunt,osjurdat,osjurno,osjurser,mnitcd1,mnit1,mnitcd2,mnit2,mnitcd3,mnit3,mnitcd4,mnit4,ifyn)" +
    		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'N')";
    
    String inserttemptx201t = "insert into TEMPTX201T(compcd,accunt,jurdat,jurno,jurser,indpcd,inempno,rtacut,rtdpcd,prodno1,prodno2,prodno3,compno1,compno2,compno3,resino,custnm,reprnm,addres,indutype,indukind,busidiv,taxdiv1,taxdiv2" + 
    		",gubun1,wrtdat,gonqty,suptamt,totvat,saldat1,itemnm1,supamt1,vat1,saldat2,itemnm2,supamt2,vat2,saldat3,itemnm3,supamt3,vat3,accdat,agryn,addyn,yymm,wrtno,userid,wrkstnid,adddate,upddate,einvoice,ectaxno,mpbno)"
    		+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, sysdate , sysdate ,?,?,?)";
    
    String deletegl399t = "delete from GL399T where jurdat= ? and inempno = ?   and jurno = ? and jurser = ? and ifyn='N'";
    
    String deletetemptx201t = "delete from TEMPTX201T where jurdat=? and inempno = ? and jurno = ? and jurser = ?";
    
    String selectglif = "select jurdat,jurno,jurser,inempno from gl399t where ifyn='Y'";

    public String getQueryList(String queryNm) throws Exception {
		
		queryList queryList = new queryList();
		
		return queryList.getClass().getDeclaredField
				(queryNm).get(queryList).toString();
	} 
	
	
	
	
	
	
	
}
