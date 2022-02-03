package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hsck.ubfw.component.com.filter.RequestModifyParameter;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.drm.ssg.drm;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.service.MainService;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ContentUtil {
	
	// log print를 위한 선언
	private static final Logger log = LoggerFactory.getLogger(ContentUtil.class);
	
	/** ContentService */
//	@Resource(name = "contentService")
	@Autowired
	private static ContentService contentService;	
	public static String dbType  = "";
	public static String keyData = "";
	public static String m_errmsg = "";
	
	public ContentUtil() {
		try {
			
			InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
			Properties props = new Properties();
			props.load(in);
			
			if (dbType == null || "".equals(dbType)) {
				dbType = props.getProperty("Globals.DbType");		
			}
			
			if (keyData == null || "".equals(keyData)) {
				keyData = props.getProperty("Globals.dbCrypto.keyData");		
			}
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap RequestToHashMap(HttpServletRequest request, List<EgovMap> selectSearchList, String mode, String tblname){
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		MainService mainService = (MainService) webApplicationContext.getBean("mainService");
	
		HashMap map  = new HashMap();
		String findval="";
		String execelusereasonmsg = StringUtil.isNullToString(request.getParameter("reasonMsg"));
		
		int findidx=0;
		
//		CryptoUtils cryptoTest = new CryptoUtils();
			
		Object[] arrKeySet = request.getParameterMap().keySet().toArray();
		
    	for (int i=0; i<arrKeySet.length; i++) {
    		
    		String key = (String)arrKeySet[i];
    		
    		for (int j=0; j<selectSearchList.size(); j++) {
    			
    			String colId    = (String) selectSearchList.get(j).get("colId");     // 컬럼ID
//    			String cryptoYn = (String) selectSearchList.get(j).get("cryptoYn");  // 암호화여부
    			String objType  = (String) selectSearchList.get(j).get("objType");   // 객체타입 
    			String dataType  = (String) selectSearchList.get(j).get("dataType");   // 데이터타입
    			if (key.equals("rows") || key.equals("undefined[]") || key.equals("nd") || key.contains("_yyyy") || key.contains("_mm") || key.contains("_yyyymm")) {continue;}
    			 
    			if (key.equals(colId) || ((key.indexOf(colId) > -1) && objType.equals("select_ftym"))) {
    				if(map.containsKey(key) ){continue;} //  값이 이미 저장된 경우 미실행	    			
    				String[] values = request.getParameterValues(key);
		    		
		    		findidx++;
		    		if(key.equals("cust_id") || key.equals("cust_nm" )) {
		    			//findidx++;
		    			/*
		    			if(!("".equals(StringUtil.isNullToString(values[0])))){
			    			if(findidx==2){
			    				if(!("".equals(StringUtil.isNullToString(findval)))){
			    				findval += ",";
			    				}
			    			}
			    			findval += key + ":" + values[0];//고객ID/명칭으로 조회하는 경우 이력 남긴다
		    			}
		    			*/
		    			
		    			//대표고객ID와 고객ID가 있을시 고객ID만 이력에 남긴다
		    			if((key.equals("rep_cust_id")) && !("".equals(StringUtil.isNullToString(values[0])))){
			    			findval = values[0];	
		    			}
		    			if((key.equals("cust_id")) && !("".equals(StringUtil.isNullToString(values[0])))){
			    			findval = values[0];	
		    			}
		    			
		    		}
		    		
			    	if(findidx==2) {
			    		
			    		//insertFwHisAccess
			    		// 세션 사용자정보 조회
			    	    LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO); 
			    		HashMap paramObj =  new HashMap();
			    		//HashMap<String, String> paramObj =  new HashMap<String, String>();
			    		String ip = request.getHeader("X-Forwarded-For");
			    		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    		     ip = request.getHeader("Proxy-Client-IP"); 
			    		 } 
			    		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    		     ip = request.getHeader("WL-Proxy-Client-IP"); 
			    		 } 
			    		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    		     ip = request.getHeader("HTTP_CLIENT_IP"); 
			    		 } 
			    		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    		     ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
			    		 } 
			    		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    		     ip = request.getRemoteAddr(); 
			    		 }
			    		 
			    		paramObj.put("LOGIN_SEQ", loginVO.getHisubseq()); 
			    		paramObj.put("menu_id", StringUtil.isNullToString(request.getParameter("menu_code")));
			    		paramObj.put("scrin_id", StringUtil.isNullToString(request.getParameter("scrin_code")));
			    		paramObj.put("ACCESS_TYPE", mode);
			    		paramObj.put("CUST_ID", findval); //검색조건상의 고객ID
			    		paramObj.put("REG_ID", loginVO.getUserId());  // 사용자ID
			    		paramObj.put("IP", ip);  // 사용자IP
			    		paramObj.put("REMARK", execelusereasonmsg);  // 엑셀다운 사유
			    		log.info("########## execelusereasonmsg : " + execelusereasonmsg );
			    		
			    		try {
			    			log.info("########## selectMenuScrinList : paramObj =  " + paramObj );
			    			
			    			List<EgovMap> menuScrinList = mainService.selectMenuScrinList(paramObj);
			    			if(menuScrinList.size()>0){
			    				paramObj.put("menu_id", (String)menuScrinList.get(0).get("menuId"));
			    			} else {
			    				paramObj.put("menu_id", "-");
			    			}
							paramObj.remove("scrin_id");
						} catch (Exception e) {
							e.printStackTrace();
						}
			    		log.info("########## insertFwHisAccess : findval =  " +findval ); 
			    	} 
			    	if(findidx==2) {
			    		findidx++;
			    	}
			    	
		    		if(values.length == 0){ continue;} //  값이 없는 경우 미실행		    			
		    		
		    		if (values.length == 1) { // 단일 명
		    			
		    			String val = values[0];
		    			
		    			// 그리드 data 조회 경우만 실행됨
		    			if ("R".equals(mode) || "E".equals(mode)) {
		    				if(values[0] == null || values[0].equals("")){ continue;} //  값이 없는 경우 미실행	(화면에서 null 입력시 continue 화면 쿼리 str 생성이 안됨)
		    			}
		    			if (key.indexOf("mult_check_") > -1){ continue;}  // 체크박스 두개 이상 체크 한경우 미실행 (두개 이상인 경우 아라 동일명으로 값이 있는 경우.....에서 처리)
		    			
		    			if (key.indexOf("from_") > -1 || key.indexOf("to_") > -1) {
		    				map.put(key, val);
		    			}else if(key.indexOf("start_") > -1 || key.indexOf("end_") > -1){
		    				map.put(key, val);
		    			}else if(dataType.equals("2")) {
		    				map.put(key, val.replace(",", ""));
		    			}else {
		    				
		    				if ("date".equals(objType)) {
		    					val = val.replace("-", "");
		    				}
		    				map.put(colId, val);
		    			}
		    			
		    		} else {  // 동일명으로 값이 있는 경우 다중 select, check, radio		    					    			
		    			String sKey = colId;
		    			String arrval = "";
		    			String deli = "@";
		    			if(objType.equals("teltext")) {
		    				deli = "-";
		    			}
		    			for (int k=0; k<values.length; k++) {		    				
		    				//String sKey = colId+"@"+k+"#"+values.length;
		    				if(k == 0) {
		    					arrval = values[k];
		    				}else {
		    					arrval +=  deli + values[k];
		    				}
		    			}
		    			map.put(sKey, arrval);
		    			
		    		}		
		    				    				    		
	    		}
	    		
    		}
    		
    	}
	
		return map;

	}
	
	// 조회 컬럼 항목
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List selectColumnsList(List selectColumnsList, String listGubun, String excelyn, String dtltmptype) throws Exception {
		ContentUtil conUtil= new ContentUtil();
		List returnList = new ArrayList();
		
		
		for (int i=0; i<selectColumnsList.size(); i++) {
			
	   		Map map = (Map) selectColumnsList.get(i);	   		 

	   		if (!map.containsKey("colId")) {
	   			returnList.add(" '' ");
	   			return returnList;
	   		}
	   			   		
	   		String colNm = (String) map.get("colId");
	   		String sql   = (String) map.get("sql");
	   		String colTbl   = StringUtil.nullConvert((String) map.get("colTbl"));
			String fwformObjSql   = StringUtil.nullConvert((String) map.get("fwformObjSql"));
			String cryptoYn       = StringUtil.nullConvert((String) map.get("cryptoYn"));  // 암호화여부
			String objType		  = StringUtil.nullConvert((String) map.get("objType"));
			String grpType		  = StringUtil.nullConvert((String) map.get("grpType"));
			String grpCd		  = StringUtil.nullConvert((String) map.get("grpCd"));
			String objSql		  = StringUtil.nullConvert((String) map.get("objSql"));
			String dataType		  = StringUtil.nullConvert((String) map.get("dataType"));
			
	   		int colNmLen = colNm.length();
	   		int subColNmLen = colNmLen-3;
	   		if (subColNmLen < 0){
	   			subColNmLen = 1;
	   		}
	   		int cnvflag=0; 
	   		//리스트 그룹코드 조회 처리 pjh
	   	 
	   		if("D".equals(listGubun)!=true) {  
	   		 /*
	   			if(grpType!="") {//pjh test  
		   			returnList.add("concat( COALESCE((select grp_nm from cm_cfggrpid u where u.grp_type='"+grpType+"'"+" and GRP_ID=T."+colNm+"),'-'), ' (', " + colNm + " ,')') AS " + colNm);
		   			cnvflag=1;
		   		}
		   		else if(grpCd!="") {//pjh test  
		   			returnList.add("concat( COALESCE((select nm from cm_cfgbasecd u where u.grp_cd='"+grpCd+"'"+" and dtl_cd=T."+colNm+"),'-'), ' (', " + colNm + " ,')') AS " + colNm);
		   			cnvflag=1;
		   		}
		   		
		   		else if(objSql!="") {//pjh test  
		   			String nsql = objSql.replace("#{val}", "T."+colNm);
		   			returnList.add("concat( COALESCE("+nsql+",'-'), ' (', " + colNm + " ,')') AS " + colNm);
		   			cnvflag=1;
		   		}
		   		 
		   		else if(fwformObjSql!="") {//pjh test  
		   			String nsql = fwformObjSql.replace("#{val}", "T."+colNm);
		   			returnList.add("concat( COALESCE(("+nsql+"),'-'), ' (', " + colNm + " ,')') AS " + colNm);
		   			cnvflag=1;
		   		} */
	   		}	   		
	   		if(cnvflag==0) {
		   		if(conUtil.dbType.equals("Maria")){		   	
		   			convertMSQL(returnList, colNm, grpType, grpCd, colTbl,sql, fwformObjSql, cryptoYn, objType, colNmLen, subColNmLen, listGubun, dataType, excelyn, dtltmptype);
		   		}else if(conUtil.dbType.equals("Tibero")){
		   			convertTSQL(returnList, colNm, grpType, grpCd, colTbl,sql, fwformObjSql, cryptoYn, objType, colNmLen, subColNmLen, listGubun, dataType, excelyn, dtltmptype);
		   		}else if(conUtil.dbType.equals("Oracle")){
		   			convertOSQL(returnList, colNm, grpType, grpCd, colTbl,sql, fwformObjSql, cryptoYn, objType, colNmLen, subColNmLen, listGubun, dataType, excelyn, dtltmptype);
		   		}
	   		}
	   		
	   		
	   	}
		
		return returnList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertMSQL(List returnList, String colNm, String grpType, String grpCd, String colTbl, String sql, String fwformObjSql, String cryptoYn, String objType, int colNmLen, int subColNmLen, String listGubun, String dataType, String excelyn, String dtltmptype) {		
		log.info("colNm : " + colNm + ", excelyn : " + excelyn + ", dtltmptype : " + dtltmptype);
		if (colNm.contains("reg_id") || colNm.contains("upd_id")) {
   			// 등록자 및 수정자
   			returnList.add("concat( COALESCE((SELECT  to_char(f_enc('2','nm',user_nm)) FROM cm_cfguser u WHERE u.user_id = t."+colNm+"),'-'), ' (', " + colNm + " ,')') AS " + colNm);	   			
   			
   		} else if (colNm.contains("reg_tm") || colNm.contains("upd_tm")) {
   			// 등록 및 변경시간
   			returnList.add("to_char(concat(SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 1, 4) , '-' , SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 5, 2) , '-' , SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 7, 2) , ' ' , SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 9, 2) , ':' , SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 11, 2) , ':' , SUBSTR(date_format("+colNm+", '%Y%m%d%H%i%s'), 13, 2))) AS " + colNm);
			//returnList.add("to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2) , ' ' , SUBSTR("+colNm+", 9, 2) , ':' , SUBSTR("+colNm+", 11, 2) , ':' , SUBSTR("+colNm+", 13, 2))) AS " + colNm);
   			
   		// 금액
//		} else if (colNm.contains("_cost")) {
//		} else if ((colNmLen-5) > 0 && "_cost".equals(colNm.substring(colNmLen-5, colNmLen))) {

//			returnList.add("TRIM(to_costchar("+colNm+", 2)) AS " + colNm);
			
//		} else if (colNm.contains("cost") || colNm.contains("money") || colNm.contains("amount") || colNm.contains("charge")) {				
			
		} 
		 
		else if ( colNm.endsWith("entry_date")  ) {//pjh 청약 쿠콘 테이블	  14자리
   			returnList.add("CASE WHEN "+colNm+" IS NOT NULL and length("+colNm+")>13 THEN to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2)  , ' ' , SUBSTR("+colNm+", 9, 2), ':' , SUBSTR("+colNm+", 11, 2), ':' , SUBSTR("+colNm+", 13, 2)  )) WHEN "+colNm+" !='' THEN "+colNm+" ELSE ' ' END AS " + colNm);
   		}
   		else if ( colNm.endsWith("_date") || colNm.endsWith("in_deadline")    ) {//pjh 청약 쿠콘 테이블	 YYYYMMDD
   			returnList.add("CASE WHEN "+colNm+" IS NOT NULL and length("+colNm+")>7 THEN to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2))) WHEN "+colNm+" !='' THEN "+colNm+" ELSE ' ' END AS " + colNm);
   		}
   		else if (colNm.endsWith("hms")  || colNm.endsWith("histm") || colNm.endsWith("tr_time")  ) {//pjh	 
			returnList.add("f_hmsfmt ("+colNm+") AS " + colNm);
   		}
   		else if (colNm.endsWith("_ym")  ) {//pjh	 
			returnList.add("f_getdayfmt ("+colNm+") AS " + colNm);
   		}
   		else if(dataType.equals("2")) {
   			returnList.add("to_costchar ("+colNm+", 2) AS " + colNm);
   		/*}
   		else if (colNm.endsWith("cost") || colNm.endsWith("money") || colNm.endsWith("amount") || colNm.endsWith("charge")) {
			returnList.add("to_costchar ("+colNm+", 2) AS " + colNm);
		*/
		// 일자( DATATYPE 이 DATE 인 경우) : _dt
		} else if (colNmLen > 3 && "_dt".equals(colNm.substring(subColNmLen, colNmLen))) {	
			if ("od_dt".equals(colNm) || "point_dt".equals(colNm) || "req_dt".equals(colNm)) {
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL and "+colNm+" !='' THEN date_format(TO_DATE("+colNm+", '%Y%m%d'), '%Y-%m-%d') ELSE '' END AS " + colNm);
			} else {				
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL and "+colNm+" !='' THEN date_format("+colNm+", '%Y-%m-%d %H:%i:%s') ELSE '' END AS " + colNm);
			}
			
		// 일자 : day
		} 
		else if (colNmLen > 0 && (
				"day".equals(colNm.substring(subColNmLen, colNmLen))
				||"ymd".equals(colNm.substring(subColNmLen, colNmLen)) 
				)) {
			if(colNm.indexOf("from_") > -1 || colNm.indexOf("to_") > -1){
				returnList.add("");
			}else{
				//returnList.add("CASE WHEN "+colNm+" IS NOT NULL and "+colNm+" !='' THEN to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2))) ELSE ' ' END AS " + colNm);
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL and length("+colNm+")>6 THEN to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2))) WHEN "+colNm+" !='' THEN "+colNm+" ELSE ' ' END AS " + colNm);
			}
		}else if(colNm.equals("fileupload") || colNm.equals("sfileupload") || colNm.equals("imgfileupload")||colNm.equals("cfileupload")){
			returnList.add("");
		}	
		else if(objType.equals("ugrid")){
			returnList.add("");
		} 
		else {
			if(excelyn.equals("Y") && dtltmptype.equals("M")) {
				if ("Y".equals(cryptoYn)  ) {
					returnList.add("f_enc('2','"+ colNm+"', "+colNm+") AS " + colNm); //pjh
				}else {
					returnList.add(colNm); 
				}
			}else {
				//log.debug("convertMSQL fwformObjSql="+fwformObjSql + " colTbl="+colTbl);
				if (StringUtil.isNotBlank( colTbl ) && !fwformObjSql.equals("N")) {
					returnList.add("(" + colTbl + ") AS " + colNm); 
				}
				else if (StringUtil.isNotBlank( fwformObjSql )) {
					//returnList.add("(" + fwformObjSql + ") AS " + colNm);
					returnList.add(colNm);
				}
				else { 
					if ("Y".equals(cryptoYn)  ) {  // 상세화면에서 암호화 컬럼일 경우 암호화 처리
						//returnList.add("TRIM(to_char( UNIBILL_CRYPTO.DEC_AES256("+colNm+"))) AS " + colNm);
						//returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
						// 상세화면에서 암호화 컬럼일 경우 암호화 처리
						//log.debug("SSSSS listGubun="+listGubun );
						//if ("D".equals(listGubun)  ) returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
						//else 
						returnList.add("f_enc('2','"+ colNm+"', "+colNm+") AS " + colNm); //pjh
					}
					else if ("L".equals(listGubun) && !"".equals(grpCd) && grpCd!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리							
						returnList.add("concat ( coalesce((select nm from cm_cfgbasecd where upper(grp_cd)='"+grpCd+"' and val=to_char("+colNm+")),'미정의'),'(',"+colNm+",')')  AS "+colNm);
					}
					else if ("L".equals(listGubun) && !"".equals(grpType) && grpType!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리
						returnList.add("concat ( coalesce((select grp_nm from cm_cfggrpid where upper(grp_type)='"+grpType+"' and grp_id=to_char("+colNm+")),'미정의'),'(',"+colNm+",')')  AS "+colNm);											
					}
					else if ("L".equals(listGubun) && !"".equals(sql) && sql!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리
						returnList.add("concat ( coalesce((select grp_nm from cm_cfggrpid where upper(grp_type)='"+grpType+"' and grp_id=to_char("+colNm+")),'미정의'),'(',"+colNm+",')')  AS "+colNm);											
					}
					else if ("L".equals(listGubun) && grpCd==null && grpType==null &&  colNm.indexOf("_yn")>=0 ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리						
						returnList.add("(case when "+colNm+"='Y' then '예'  when "+colNm+"='N' then '아니오' else '미정의' end)||'('||"+colNm+"||')'  AS "+colNm);						
					}	
					else {
						returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
					}
				}
			}
			
   		}
		 
		
		return  returnList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertTSQL(List returnList, String colNm, String grpType, String grpCd,String colTbl, String sql, String fwformObjSql, String cryptoYn, String objType, int colNmLen, int subColNmLen, String listGubun, String dataType, String excelyn, String dtltmptype) {
		if (colNm.contains("reg_id") || colNm.contains("upd_id")) {
   			// 등록자 및 수정자
   			returnList.add("COALESCE((SELECT  to_char(f_enc('2','nm',user_nm)) FROM cm_cfguser u WHERE u.user_id = t."+colNm+"),'-')|| ' ('|| " + colNm + " ||')' AS " + colNm);	   			
   			
   		} else if (colNm.contains("reg_tm") || colNm.contains("upd_tm")) {
   			// 등록 및 변경시간
   			returnList.add("to_char(SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2) || ' ' || SUBSTR("+colNm+", 9, 2) || ':' || SUBSTR("+colNm+", 11, 2) || ':' || SUBSTR("+colNm+", 13, 2)) AS " + colNm);
			//returnList.add("to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2) , ' ' , SUBSTR("+colNm+", 9, 2) , ':' , SUBSTR("+colNm+", 11, 2) , ':' , SUBSTR("+colNm+", 13, 2))) AS " + colNm);
   			
   		// 금액
//		} else if (colNm.contains("_cost")) {
//		} else if ((colNmLen-5) > 0 && "_cost".equals(colNm.substring(colNmLen-5, colNmLen))) {

//			returnList.add("TRIM(to_costchar("+colNm+", 2)) AS " + colNm);
			
//		} else if (colNm.contains("cost") || colNm.contains("money") || colNm.contains("amount") || colNm.contains("charge")) {				
			
		}
   		else if (colNm.endsWith("hms")  ) {//pjh	 
			returnList.add("f_hmsfmt ("+colNm+") AS " + colNm);
   		}
   		else if (colNm.endsWith("_ym")  ) {//pjh	 
			returnList.add("f_getdayfmt ("+colNm+") AS " + colNm);
   		}
   		else if (colNm.endsWith("cost") || colNm.endsWith("money") || colNm.endsWith("amount") || colNm.endsWith("charge")) {
			returnList.add("to_costchar ("+colNm+", 2) AS " + colNm);
			
		// 일자( DATATYPE 이 DATE 인 경우) : _dt
		} else if (colNmLen > 3 && "_dt".equals(colNm.substring(subColNmLen, colNmLen))) {	
			if ("od_dt".equals(colNm) || "point_dt".equals(colNm) || "req_dt".equals(colNm)) {
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_datechar(TO_DATE("+colNm+", 'YYYYMMDD'), 'YYYY-MM-DD') ELSE '' END AS " + colNm);
			} else {				
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_datechar("+colNm+", 'YYYY-MM-DD HH24:MI:SS') ELSE '' END AS " + colNm);
			}
			
		// 일자 : day
		} else if (colNmLen > 0 && "day".equals(colNm.substring(subColNmLen, colNmLen))||"ymd".equals(colNm.substring(subColNmLen, colNmLen))) {
			if(colNm.indexOf("from_") > -1 || colNm.indexOf("to_") > -1){
				returnList.add("");
			}else{
				//returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_char(SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2)) ELSE ' ' END AS " + colNm);
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL and length("+colNm+")>6 THEN SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2) WHEN "+colNm+" !='' THEN "+colNm+" ELSE ' ' END AS " + colNm);
			}
		}
		else if(colNm.equals("fileupload") || colNm.equals("sfileupload")){
			returnList.add("");
		}	
		else if(objType.equals("ugrid")){
			returnList.add("");
		} else {
			if (StringUtil.isNotBlank( fwformObjSql )) {
				//returnList.add("(" + fwformObjSql + ") AS " + colNm);
				returnList.add(colNm);
			} else { 
				if ("Y".equals(cryptoYn)  ) {  // 상세화면에서 암호화 컬럼일 경우 암호화 처리
					//returnList.add("TRIM(to_char( UNIBILL_CRYPTO.DEC_AES256("+colNm+"))) AS " + colNm);
					//returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
					// 상세화면에서 암호화 컬럼일 경우 암호화 처리
					//log.debug("SSSSS listGubun="+listGubun );
					//if ("D".equals(listGubun)  ) returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
					//else 
						returnList.add("f_enc('2','"+ colNm+"', "+colNm+") AS " + colNm); //pjh
				}
				else if ("L".equals(listGubun) && !"".equals(grpCd) && grpCd!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리							
					returnList.add("coalesce((select nm from cm_cfgbasecd where upper(grp_cd)='"+grpCd+"' and val=to_char("+colNm+")),'미정의')||'('||"+colNm+"||')'  AS "+colNm);						//							
				}
				else if ("L".equals(listGubun) && !"".equals(grpType) && grpType!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리
					returnList.add("coalesce((select grp_nm from cm_cfggrpid where upper(grp_type)='"+grpType+"' and grp_id=to_char("+colNm+")),'미정의')||'('||"+colNm+"||')'  AS "+colNm);						
				}	
				else if ("L".equals(listGubun) && grpCd==null && grpType==null &&  colNm.indexOf("_yn")>=0 ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리						
					returnList.add("(case when "+colNm+"='Y' then '예'  when "+colNm+"='N' then '아니오' else '미정의' end)||'('||"+colNm+"||')'  AS "+colNm);						
				}	
				else {
					returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
				}
			}
   		}
		
		return  returnList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertOSQL(List returnList, String colNm, String grpType, String grpCd, String colTbl, String sql, String fwformObjSql, String cryptoYn, String objType, int colNmLen, int subColNmLen, String listGubun, String dataType, String excelyn, String dtltmptype) {
		if (colNm.contains("reg_id") || colNm.contains("upd_id")) {
   			// 등록자 및 수정자
   			returnList.add("COALESCE((SELECT  to_char(f_enc('2','nm',user_nm)) FROM cm_cfguser u WHERE u.user_id = t."+colNm+"),'-')|| ' ('|| " + colNm + " ||')' AS " + colNm);	   			
   			
   		} else if (colNm.contains("reg_tm") || colNm.contains("upd_tm")) {
   			// 등록 및 변경시간
   			returnList.add("to_char(SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2) || ' ' || SUBSTR("+colNm+", 9, 2) || ':' || SUBSTR("+colNm+", 11, 2) || ':' || SUBSTR("+colNm+", 13, 2)) AS " + colNm);
			//returnList.add("to_char(concat(SUBSTR("+colNm+", 1, 4) , '-' , SUBSTR("+colNm+", 5, 2) , '-' , SUBSTR("+colNm+", 7, 2) , ' ' , SUBSTR("+colNm+", 9, 2) , ':' , SUBSTR("+colNm+", 11, 2) , ':' , SUBSTR("+colNm+", 13, 2))) AS " + colNm);
   			
   		// 금액
//		} else if (colNm.contains("_cost")) {
//		} else if ((colNmLen-5) > 0 && "_cost".equals(colNm.substring(colNmLen-5, colNmLen))) {

//			returnList.add("TRIM(to_costchar("+colNm+", 2)) AS " + colNm);
			
//		} else if (colNm.contains("cost") || colNm.contains("money") || colNm.contains("amount") || colNm.contains("charge")) {				
			
		}
   		else if (colNm.endsWith("hms")  ) {//pjh	 
			returnList.add("f_hmsfmt ("+colNm+") AS " + colNm);
   		}
   		else if (colNm.endsWith("_ym")  ) {//pjh	 
			returnList.add("f_getdayfmt ("+colNm+") AS " + colNm);
   		}
   		else if (colNm.endsWith("cost") || colNm.endsWith("money") || colNm.endsWith("amount") || colNm.endsWith("charge")) {
			returnList.add("to_costchar ("+colNm+", 2) AS " + colNm);
			
		// 일자( DATATYPE 이 DATE 인 경우) : _dt
		} else if (colNmLen > 3 && "_dt".equals(colNm.substring(subColNmLen, colNmLen))) {	
			if ("od_dt".equals(colNm) || "point_dt".equals(colNm) || "req_dt".equals(colNm)) {
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_datechar(TO_DATE("+colNm+", 'YYYYMMDD'), 'YYYY-MM-DD') ELSE '' END AS " + colNm);
			} else {				
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_datechar("+colNm+", 'YYYY-MM-DD HH24:MI:SS') ELSE '' END AS " + colNm);
			}
			
		// 일자 : day
		} else if (colNmLen > 0 && "day".equals(colNm.substring(subColNmLen, colNmLen))||"ymd".equals(colNm.substring(subColNmLen, colNmLen))) {
			if(colNm.indexOf("from_") > -1 || colNm.indexOf("to_") > -1){
				returnList.add("");
			}else{
				//returnList.add("CASE WHEN "+colNm+" IS NOT NULL THEN to_char(SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2)) ELSE ' ' END AS " + colNm);
				returnList.add("CASE WHEN "+colNm+" IS NOT NULL and length("+colNm+")>6 THEN SUBSTR("+colNm+", 1, 4) || '-' || SUBSTR("+colNm+", 5, 2) || '-' || SUBSTR("+colNm+", 7, 2) WHEN "+colNm+" !='' THEN "+colNm+" ELSE ' ' END AS " + colNm);
			}
		}
		else if(colNm.equals("fileupload") || colNm.equals("sfileupload")){
			returnList.add("");
		}	
		else if(objType.equals("ugrid")){
			returnList.add("");
		} 
		else {
			if (StringUtil.isNotBlank( fwformObjSql )) {
				//returnList.add("(" + fwformObjSql + ") AS " + colNm);
				returnList.add(colNm);
			} else { 
				if ("Y".equals(cryptoYn)  ) {  // 상세화면에서 암호화 컬럼일 경우 암호화 처리
					//returnList.add("TRIM(to_char( UNIBILL_CRYPTO.DEC_AES256("+colNm+"))) AS " + colNm);
					//returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
					// 상세화면에서 암호화 컬럼일 경우 암호화 처리
					//log.debug("SSSSS listGubun="+listGubun );
					//if ("D".equals(listGubun)  ) returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
					//else 
						returnList.add("f_enc('2','"+ colNm+"', "+colNm+") AS " + colNm); //pjh
				}
				else if ("L".equals(listGubun) && !"".equals(grpCd) && grpCd!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리							
					returnList.add("coalesce((select nm from cm_cfgbasecd where upper(grp_cd)='"+grpCd+"' and val=to_char("+colNm+")),'미정의')||'('||"+colNm+"||')'  AS "+colNm);						//							
				}
				else if ("L".equals(listGubun) && !"".equals(grpType) && grpType!=null ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리
					returnList.add("coalesce((select grp_nm from cm_cfggrpid where upper(grp_type)='"+grpType+"' and grp_id=to_char("+colNm+")),'미정의')||'('||"+colNm+"||')'  AS "+colNm);						
				}	
				else if ("L".equals(listGubun) && grpCd==null && grpType==null &&  colNm.indexOf("_yn")>=0 ) {  // 상세화면에서 암호화 컬럼일 경우 복호화 처리						
					returnList.add("(case when "+colNm+"='Y' then '예'  when "+colNm+"='N' then '아니오' else '미정의' end)||'('||"+colNm+"||')'  AS "+colNm);						
				}	
				else {
					returnList.add("TRIM(to_char( "+colNm+")) AS " + colNm);
				}
			}
		}
		
		return  returnList;
	}
	
	
	
	// 검색 컬럼 쿼리 생성
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List searchColumnsList(HashMap parameterObject, List<EgovMap> selectSearchList, HttpServletRequest request) {

			LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
			String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
			
			ContentUtil contentUtil = new ContentUtil();
			
	    	parameterObject.put("login_role_code",   loginVO.getRoleId());    // 권한ID
	    	parameterObject.put("login_user_id",     loginVO.getUserId());    // 로그인ID
	    	parameterObject.put("login_dept_grp_id", loginVO.getDeptGrpId()); // 부서코드
	    	parameterObject.put("login_dept_grp_id", loginVO.getDeptGrpId()); // 부서코드 
	    	 
			
//			ContentUtil db = new ContentUtil();
		   	List returnList = new ArrayList();
		   	if (parameterObject.size() > 0) {
			    	Set key = parameterObject.keySet();
			    	
			    	int i = 0;
			    	for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			    		
			    		 String keyNm = (String)iterator.next();		    		 
			    		 String value = (String)parameterObject.get(keyNm);
			    		 int idx  = keyNm.indexOf("@");
			    		 int idx2 = keyNm.indexOf("#");
			    		 for (int j=0; j<selectSearchList.size(); j++) {
			    			 String colId     = (String) selectSearchList.get(j).get("colId");      // 컬럼ID
			    			 String objType   = (String) selectSearchList.get(j).get("objType");    // 객체타입
			    			 String findType  = (String) selectSearchList.get(j).get("findType");   // 검색타입
			    			 String cryptoYn  = (String) selectSearchList.get(j).get("cryptoYn");   // 암호화여부
			    			 String findIdSql = (String) selectSearchList.get(j).get("findIdSql");  // 검색IDSQL
			    			
			    			 //log.debug("11111========= keyNm:" +keyNm + " ==== value :"+value + " ==== colId :"+colId + " ==== findType :"+findType);
			    			/*
		    				if (findType.equals("N")) { //검색조건으로 쓰지 않을때
	    				 		break;
	    				 	} */	
			    			 if (keyNm.equals(colId) || ((keyNm.indexOf(colId) > -1) && objType.equals("select_ftym")) ) {		//검색조건이 들어가고 맞을때     				 
			    				 log.debug("searchColumnsList========= keyNm:" +keyNm + " ==== value :"+value + " ==== colId :"+colId + " ==== objType :"+objType+ " ==== findType :"+findType + " ==== idx :"+idx + " ==== idx2 :"+idx2);			    			
				    			// 객체 타입이 hidden 이 아니며, 검색조건의 값을 SQL을 사용하지 않을시...
				    			 if (StringUtils.isEmpty(findIdSql) && !"hidden".equals(objType)) {			    				 
				    				 	 String val = "";
				    				 	 String colsql=""; //pjh
						    			 String UpperkeyNm=""; //pjh
						    			 UpperkeyNm=keyNm;
						    			 
				    				 	if(objType.equals("text")) {
				    				 		 if (!findType.isEmpty() && findType.substring(0,1).equals("U")) {
							    				 UpperkeyNm = "UPPER(" + keyNm + ")"; 
							    			 }
					    				 	//pjh 암호 컬럼 조회시 처리시 			    				 	
					    				 	 if ("Y".equals(cryptoYn)) { 
					    				 		colsql= "AND f_enc('2', '"+keyNm+"' ,"+keyNm+")"; 
					    				 	 }
					    				 	 else {
					    				 		colsql= "AND " + UpperkeyNm ;
					    				 	 }
					    				 	if (findType.equals("f_like")) {
					    				 		colsql+= " like '" +value+"%' "; 
					    				 			
					    				 	} 
					    				 	else if (findType.equals("r_like")) {
					    				 		colsql+= " like '%" +value+"' "; 
					    				 	}
					    				 	else if (findType.equals("like")) {
					    				 		colsql+= " like '%" +value+"%' ";
					    				 	} 	
					    				 	else if (findType.equals("N")) { //검색조건으로 쓰지 않을때
					    				 		;
					    				 	} 	
					    				 	else {  //eq or null
					    				 		colsql+= "= '" + value + "'"; 
					    				 	}					    				 	
					    				 	
					    				 	log.debug("searchColumnsList========= text colsql:" +colsql);
				    				 	}
				    				 	else if(objType.equals("teltext")) {
				    				 		if (!findType.isEmpty() && findType.substring(0,1).equals("U")) {
							    				 UpperkeyNm = "UPPER(" + keyNm + ")"; 
							    			 }
					    				 	//pjh 암호 컬럼 조회시 처리시 			    				 	
					    				 	 if ("Y".equals(cryptoYn)) { 
					    				 		colsql= "AND f_enc('2', '"+keyNm+"' , replace("+keyNm+",'-',''))"; 
					    				 	 }
					    				 	 else {
					    				 		colsql= "AND replace(" + UpperkeyNm +",'-','')";
					    				 	 }
					    				 	if (findType.equals("f_like")) {
					    				 		colsql+= " like '" +value+"%' "; 
					    				 			
					    				 	} 
					    				 	else if (findType.equals("r_like")) {
					    				 		colsql+= " like '%" +value+"' "; 
					    				 	}
					    				 	else if (findType.equals("like")) {
					    				 		colsql+= " like '%" +value+"%' ";
					    				 	} 	
					    				 	else if (findType.equals("N")) { //검색조건으로 쓰지 않을때
					    				 		;
					    				 	} 	
					    				 	else {  //eq or null
					    				 		colsql+= "= '" + value + "'"; 
					    				 	}					    				 	
					    				 	
					    				 	log.debug("searchColumnsList========= text colsql:" +colsql);
				    				 	}
				    				 	else if(objType.equals("date")){ 
								    		// 일자(날짜) from ~ to 검색일 경우			    		
									    	if (keyNm.indexOf("from_") > -1  ) { 
									    		String fromCol = keyNm.replace("from_", "");
									    		colsql=" AND " + fromCol + " >= '" + value.replace("-", "") + "' ";
									    	}
									    	else if (keyNm.indexOf("to_") > -1  ) { 
								    	    	String toCol = keyNm.replace("to_", "");
								    	    	colsql=" AND " + toCol + " <= '" + value.replace("-", "") + "' ";
									    	}
				    				 	}	// 	월(날짜) start ~ end 검색일 경우
				    				 	else if ( objType.equals("select_ftym")){
				    				 	   if (keyNm.indexOf("start_") > -1  ){
									    		String startCol = keyNm.replace("start_", "");
									    		colsql=" AND " + startCol + " >= '" + value.replace("-", "") + "' "; 
									    	}else if (keyNm.indexOf("end_") > -1 ){	
									    		String endCol = keyNm.replace("end_", "");
									    		colsql=" AND " + endCol + " <= '" + value.replace("-", "") + "' ";
								    	    }
				    				 	}	
				    				 	else if (objType.equals("select") || objType.equals("radio") || objType.contains("select_")) {// 객체 타입이 콤보 또는 라디오 일경우 = 검색
				    				 		findType="eq";
				    				 		colsql=" AND "+ keyNm + "='"+ value+"'";
				    				 	}
				    				 	else {
								    		 if (idx > -1) { 
								    			 if (i == 0) {
								    				 colsql=" AND (" + keyNm.substring(0, idx) + " = '" + value + "'";
								    			 } else if ( i == (Integer.parseInt(keyNm.substring(idx2+1, idx2+2))-1) ) {
								    				 colsql=" OR " + keyNm.substring(0, idx) + " = '" + value + "')";
								    			 } else {
								    				 colsql=" OR " + keyNm.substring(0, idx) + " = '" + value + "'";
								    			 }												    			 
								    			 i += 1;
								    		 } 
								    		 		    		 
								    	}
				    				 	returnList.add(colsql);
				    			 } 
				    			 else {// 객체 타입이 hidden 이며 검색조건의 값을 SQL을 사용시...
				    				 returnList.add(" AND " + keyNm + " IN (" + findIdSql + ") "); 
    			    			 } 				    		 
				    			
				    		 } 
			    			 
			    		 } //for loop

			    	}
		   	}

		   	return returnList;
		}
		
	// 검색 컬럼 쿼리 생성
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List searchColumnsListBk(HashMap parameterObject, List<EgovMap> selectSearchList, HttpServletRequest request) {

		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
		
		ContentUtil contentUtil = new ContentUtil();
		
    	parameterObject.put("login_role_code",   loginVO.getRoleId());    // 권한ID
    	parameterObject.put("login_user_id",     loginVO.getUserId());    // 로그인ID
    	parameterObject.put("login_dept_grp_id", loginVO.getDeptGrpId()); // 부서코드
    	parameterObject.put("login_dept_grp_id", loginVO.getDeptGrpId()); // 부서코드 
    	 
		
//		ContentUtil db = new ContentUtil();
	   	List returnList = new ArrayList();
	   	if (parameterObject.size() > 0) {
		    	Set key = parameterObject.keySet();
		    	
		    	int i = 0;
		    	for (Iterator iterator = key.iterator(); iterator.hasNext();) {
		    		
		    		 String keyNm = (String)iterator.next();		    		 
		    		 String value = (String)parameterObject.get(keyNm);
		    		 int idx  = keyNm.indexOf("@");
		    		 int idx2 = keyNm.indexOf("#");
		    		 for (int j=0; j<selectSearchList.size(); j++) {
		    			 String colId     = (String) selectSearchList.get(j).get("colId");      // 컬럼ID
		    			 String objType   = (String) selectSearchList.get(j).get("objType");    // 객체타입
		    			 String findType  = (String) selectSearchList.get(j).get("findType");   // 검색타입
		    			 String cryptoYn  = (String) selectSearchList.get(j).get("cryptoYn");   // 암호화여부
		    			 String findIdSql = (String) selectSearchList.get(j).get("findIdSql");  // 검색IDSQL
		    			 
		    			// log.debug("11111========= keyNm:" +keyNm + " ==== value :"+value + " ==== colId :"+colId );
		    			
		    			 if (keyNm.equals(colId) || ((keyNm.indexOf(colId) > -1) && objType.equals("select_ftym")) ) {		//검색조건이 들어가고 맞을때     				 
		    				 //log.debug("222222========= keyNm:" +keyNm + " ==== value :"+value + " ==== colId :"+colId );			    			
			    			// 객체 타입이 hidden 이 아니며, 검색조건의 값을 SQL을 사용하지 않을시...
			    			 if (StringUtils.isEmpty(findIdSql) && !"hidden".equals(objType)) {			    				 
			    				 // log.debug("3333333========= keyNm:" +keyNm + " ==== value :"+value + " ==== colId :"+colId );
			    				 	String val = "";
			    				 	//pjh 암호 컬럼 조회시 처리시 			    				 	
			    				 	 if ("Y".equals(cryptoYn)) {
			    				 		// returnList.add("AND UPPER(" + keyNm + ") = f_enc('1', keyNm, #{" + keyNm + "} )  ");
			    				 		returnList.add("AND UPPER(" + keyNm + ") = f_enc('1', '" +keyNm+ "', #{" + keyNm + "}) "); 
			    				 		 continue;
			    				 	 }
			    				 	 
			    				 	 else if ("eq".equals(findType)) {
			    				 		/* pjh select시에 암호화 함수 사용
			    				 		// val = selectCrypto( unibillFuncEnc, cryptoYn, "ENC",   keyNm, value, colId,    keyData);
			    				 		  
			    				 		if ("Y".equals(cryptoYn)) {
			    				 			if(unibillFuncEnc.equals("-")){
			    				 				try {
													val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
												} catch (Exception e) {
													val = value;
												}  
			    				 			}else{
			    				 				try {
			    				 					val = contentUtil.getCryptoValDB("ENC", val, colId, keyData, unibillFuncEnc);
			    				 				} catch (Exception e) {
			    				 					val = value;
			    				 				}
			    				 			}
			    				 			 
			    				 			returnList.add("AND " + keyNm + " = '" + val + "' ");
			    				 			//returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER('" + val + "') ELSE " + keyNm + " END = UPPER(#{" + keyNm + "}) ");
			    				 		} else {
			    				 			returnList.add("AND UPPER(" + keyNm + ") = UPPER(#{" + keyNm + "}) ");
			    				 		}			    				 		
			    				 		*/
			    				 		returnList.add("AND UPPER(" + keyNm + ") = UPPER(#{" + keyNm + "}) ");
			    				 		
			    				 	} else if ("like".equals(findType)) {
			    				 		/* pjh
			    				 		if ("Y".equals(cryptoYn)) {
			    				 			if(unibillFuncEnc.equals("-")){
			    				 				try {
													val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
												} catch (Exception e) {
													val = value;
												}  
			    				 			}else{
			    				 				try {
			    				 					val = contentUtil.getCryptoValDB("ENC", val, colId, keyData, unibillFuncEnc);
			    				 				} catch (Exception e) {
			    				 					val = value;
			    				 				}
			    				 			}
			    				 			returnList.add("AND " + keyNm + " = '" + val + "' ");
//			    				 			returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER('" + val + "') ELSE " + keyNm + " END LIKE UPPER(concat('%',#{" + keyNm + "},'%')) ");
			    				 		} else {
			    				 			if(contentUtil.dbType.equals("Maria")){
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "},'%')) ");
			    				 			}else{
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER('%'|| #{" + keyNm + "} || '%') ");
			    				 			}
			    				 			
			    				 		}*/
			    				 		if(contentUtil.dbType.equals("Maria")){
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "},'%')) ");
		    				 			}else{
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER('%'|| #{" + keyNm + "} || '%') ");
		    				 			}
			    				 		
			    				 	} else if ("f_like".equals(findType)) {
			    				 		/*
			    				 		if ("Y".equals(cryptoYn)) {
			    				 			if(unibillFuncEnc.equals("-")){
			    				 				try {
													val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
												} catch (Exception e) {
													val = value;
												}  
			    				 			}else{
			    				 				try {
			    				 					val = contentUtil.getCryptoValDB("ENC", val, colId, keyData, unibillFuncEnc);
			    				 				} catch (Exception e) {
			    				 					val = value;
			    				 				}
			    				 			}
			    				 			returnList.add("AND " + keyNm + " = '" + val + "' ");
//			    				 			returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER(" + val + ") ELSE " + keyNm + " END LIKE UPPER(concat(#{" + keyNm + "},'%')) ");
			    				 		} else {
			    				 			if(contentUtil.dbType.equals("Maria")){
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat(#{" + keyNm + "},'%')) ");
			    				 			}else{
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(#{" + keyNm + "} || '%') ");
			    				 			}			    				 		
			    				 		}
			    				 		*/	
			    				 		if(contentUtil.dbType.equals("Maria")){
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat(#{" + keyNm + "},'%')) ");
		    				 			}else{
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(#{" + keyNm + "} || '%') ");
		    				 			}
			    				 			
			    				 	} else if ("r_like".equals(findType)) {
			    				 		/*
			    				 		if ("Y".equals(cryptoYn)) {
			    				 			if(unibillFuncEnc.equals("-")){
			    				 				try {
													val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
												} catch (Exception e) {
													val = value;
												}  
			    				 			}else{
			    				 				try {
			    				 					val = contentUtil.getCryptoValDB("ENC", val, colId, keyData, unibillFuncEnc);
			    				 				} catch (Exception e) {
			    				 					val = value;
			    				 				}
			    				 			}
			    				 			returnList.add("AND " + keyNm + " = '" + val + "' ");
//			    				 			returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER(" + val + ", ') ELSE " + keyNm + " END LIKE UPPER(concat('%',#{" + keyNm + "})) ");
			    				 		} else {
			    				 			if(contentUtil.dbType.equals("Maria")){
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "})) ");
			    				 			}else{
			    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER('%' || #{" + keyNm + "}) ");
			    				 			}
			    				 			
			    				 		}
			    				 		*/
			    				 		if(contentUtil.dbType.equals("Maria")){
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "})) ");
		    				 			}else{
		    				 				returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER('%' || #{" + keyNm + "}) ");
		    				 			}
			    				 		
			    				 	} else {
			    				 		// 일자(날짜) from ~ to 검색일 경우			    		
								    	if (keyNm.indexOf("from_") > -1 && objType.equals("date")) {
								    		String fromCol = keyNm.replace("from_", "");
								    		returnList.add("AND " + fromCol + " >= '" + value.replace("-", "") + "' ");
					
							    	    }
								    	 
								    	else if (keyNm.indexOf("to_") > -1 && objType.equals("date")) {
								    		String toCol = keyNm.replace("to_", "");
								    		returnList.add("AND " + toCol + " <= '" + value.replace("-", "") + "' ");
								    	
								    	// 	월(날짜) start ~ end 검색일 경우
								    	}else if (keyNm.indexOf("start_") > -1 && objType.equals("select_ftym")){
								    		String startCol = keyNm.replace("start_", "");
								    		returnList.add("AND " + startCol + " >= '" + value.replace("-", "") + "' ");
								    	}else if (keyNm.indexOf("end_") > -1 && objType.equals("select_ftym")){	
								    		String endCol = keyNm.replace("end_", "");
								    		returnList.add("AND " + endCol + " <= '" + value.replace("-", "") + "' ");
								    	// 등록자(수정자)명으로 검색 시
							    	    } else if (keyNm.contains("reg_id") || keyNm.contains("upd_id")) {
							    	    	returnList.add("AND UPPER(" + keyNm + ") IN (SELECT UPPER(u.user_id) FROM cm_cfguser u WHERE u.user_nm LIKE '%"+value+"%') ");		    	    	
					
								    	} else {
								    		 if (idx > -1) {
					 
								    			 if (i == 0) {
								    				 returnList.add("AND (" + keyNm.substring(0, idx) + " = '" + value + "'");
								    			 } else if ( i == (Integer.parseInt(keyNm.substring(idx2+1, idx2+2))-1) ) {
								    				 returnList.add("OR " + keyNm.substring(0, idx) + " = '" + value + "')");
								    			 } else {
								    				 returnList.add("OR " + keyNm.substring(0, idx) + " = '" + value + "'");
								    			 }
												    			 
								    			 i += 1;
								    		 } else {
								    			 
								    			 // 객체 타입이 콤보 또는 라디오 일경우 = 검색
								    			 if ("select".equals(objType) || "radio".equals(objType) || "select_ym".equals(objType)) {
								    				 
								    				 returnList.add("AND " + keyNm + " = #{" + keyNm + "} ");
								    				 
								    			 } else {
								    				 
	//							    				 if ("Oracle".equals(db.dbType)) {
								    				 
									    				 // _nm 의 컬럼의 경우
									    				 if (keyNm.indexOf("_nm") > -1) {		
									    					 /*
									    					 if ("Y".equals(cryptoYn)) {
									    						 if(unibillFuncEnc.equals("-")){
									    							 try {
																			val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
																		} catch (Exception e) {
																			val = keyNm;
																		}
									    							 
									    						 }else{
									    							 try {
									    				 					val = contentUtil.getCryptoValDB("ENC", value, colId, keyData, unibillFuncEnc);
									    				 				} catch (Exception e) {
									    				 					val = keyNm;
									    				 				}
									    						 }
									    						 
									    						 returnList.add("AND " + keyNm + " = '" + val + "' ");
//									    				 		 returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER(" + val + ", ') ELSE " + keyNm + " END LIKE UPPER(concat('%',#{" + keyNm + "})) ");
									    					 } else {
									    						if(contentUtil.dbType.equals("Maria")){
									    							returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "},'%')) ");
									    				 		}else{
									    				 			returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER('%'||#{" + keyNm + "}||'%') ");
									    				 		}
									    						 
									    					 }
									    					 */
									    					if(contentUtil.dbType.equals("Maria")){
									    							returnList.add("AND " + keyNm + " LIKE concat('%',#{" + keyNm + "},'%') ");
								    				 		}else{
								    				 			returnList.add("AND " + keyNm + " LIKE '%'||#{" + keyNm + "}||'%' ");
								    				 		}
									    					 
									    				 } else {  // 그외는 like 검색
									    					 /*
									    					 if ("Y".equals(cryptoYn)) {
									    						 if(unibillFuncEnc.equals("-")){
									    							 try {
																			val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
																		} catch (Exception e) {
																			val = keyNm;
																		}
									    							 
									    						 }else{
									    							 try {
									    				 					val = contentUtil.getCryptoValDB("ENC", value, colId, keyData, unibillFuncEnc);
									    				 				} catch (Exception e) {
									    				 					val = keyNm;
									    				 				}
									    						 }
									    						 returnList.add("AND " + keyNm + " = '" + val + "' ");
//									    				 			returnList.add("AND CASE WHEN " + keyNm + " IS NOT NULL THEN UPPER(" + val + ", ') ELSE " + keyNm + " END LIKE UPPER(concat('%',#{" + keyNm + "})) ");
									    					 } else {
									    						if(contentUtil.dbType.equals("Maria")){
									    							returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat(#{" + keyNm + "},'%')) ");
										    				 	}else{
										    				 		returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(#{" + keyNm + "} || '%') ");
										    				 	}									    						
									    					 }
									    					 */
									    					if(contentUtil.dbType.equals("Maria")){
								    							returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(concat(#{" + keyNm + "},'%')) ");
									    				 	}else{
									    				 		returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(#{" + keyNm + "} || '%') ");
									    				 	}	
									    					 
									    				 }
									    				 
								    				  /*} else if ("Maria".equals(db.dbType)) {  // http://blog.naver.com/khsmonad/220420616056
								    					  
								    					// _nm 의 컬럼의 경우
									    				 if (keyNm.indexOf("_nm") > -1) {
									    					 returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(CONCAT('%', #{" + keyNm + "}, '%')) ");
									    				 } else {  // 그외는 like 검색
									    					 returnList.add("AND UPPER(" + keyNm + ") LIKE UPPER(CONCAT(#{" + keyNm + "}, '%')) ");
									    				 }
										    				 
								    				  }*/
		
								    			 }
								    			 
								    		 }
								    		 
								    	}
							    	
			    				 	}
			    				 
			    		     // 객체 타입이 hidden 이며 검색조건의 값을 SQL을 사용시...
			    			 } else {
			    				 returnList.add("AND " + keyNm + " IN (" + findIdSql + ") ");
			    				 
			    			 }
		    				 	
		    			 }
					    	
		    		 }

		    	}
	   	}

	   	return returnList;
	}
	
	

	// update/insert 데이터 변환
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String ConvVal(String unibillFuncEnc, String cryptoYn, String col, String val, String objType, String dataType) {		
		 
		 String nval=""; 
		 
		 ContentUtil contentUtil = new ContentUtil();
		 if ("Y".equals(cryptoYn)) {
			 
			 if( unibillFuncEnc.equals("-")) {  // 암호화 컬럼인 경우 암호화 처리 
				try {
					nval = contentUtil.getCryptoValJava("ENC", val, keyData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			 }
			 else {
				 try {
					nval = contentUtil.getCryptoValDB("ENC", val, col, keyData, unibillFuncEnc);
				 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}		 
		else if ("date".equals(objType)) {
			nval = val.replace("-", "");
		}
		else if(dataType.equals("2")) {
			nval = val.replace(",", "");
		}
		/*else if (col.endsWith("cost") || col.endsWith("money") || col.endsWith("amount") || col.endsWith("charge")) {
			nval = val.replace(",", "");
		}*/
		/*else if (col.endsWith("_acc") ) { //은행계좌 - 제거
			nval = val.replace("-", "");
		}*/
		else if (col.endsWith("_ym") || col.endsWith("_ymd") || col.endsWith("_day")|| col.endsWith("_dt")) { // 날짜 - 제거
			nval = val.replace("-", "");
		}
		else {
			nval=val;
		}  
				
		return nval; 
	}


	
	// insert 컬럼 및 데이터
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List insertColumnsList(HttpServletRequest request, List<EgovMap> selectColumnsList, String gubun) throws Exception {
		String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		List returnList = new ArrayList();
		
		HashMap map = new HashMap();
		Object[] arrKeySet = request.getParameterMap().keySet().toArray(); 
		
    	for(int i=0; i<arrKeySet.length;i++){
    		
    		String key = (String)arrKeySet[i];
    		if ("pass_chg".equals(key)) {continue;}
    		
    		for (int j=0; j<selectColumnsList.size(); j++) {
    			
    			String colId    = (String) selectColumnsList.get(j).get("colId");     // 컬럼ID
    			String cryptoYn = (String) selectColumnsList.get(j).get("cryptoYn");  // 암호화여부
    			String objType  = (String) selectColumnsList.get(j).get("objType");   // 객체타입
    			String dataType  = (String) selectColumnsList.get(j).get("dataType");   // 데이터타입
    			
    			/* insert 검사
    			 if (colId.contains("role_id")  ) {
 				    //returnList.add(" and '"+loginVO.getRoleId()+"' in ( select role_id from fwrole where role_level >= ( select role_level from fwrole where role_id= '"+loginVO.getRoleId()+"'  ) ) ");
 				    //if(CheckRole(loginVO.getRoleId() ==false) return null;		    	    	
    			 }
    			 */
    			 
    			//if (key.contains(colId)) {
    			if (key.equals(colId)) {
    			
		    		if(map.containsKey(key) ){continue;}
		
		    		String[] values = request.getParameterValues(key);
				    		
		    		if (values.length ==1) { // 단일 명
		    			
		    			if ("Columns".equals(gubun)) {  // 컬럼
		    				returnList.add(colId);
		    			} else {  // 데이터
		    				String val = ConvVal( unibillFuncEnc,  cryptoYn, colId,  values[0], objType, dataType); 
		    				returnList.add(val);
		    			}
		    			
		    		} else {  //동일명으로 값이 있는 경우 다중 select, check, radio
		    			//System.out.println("colId : " + colId + ", gubun : " + gubun + ", values : " + values + ", objType : " + objType + ", dataType : " + dataType);
		    			String deli = "@";
		    			if(objType.equals("teltext")) {
		    				deli = "-";
		    			}
		    			
		    			if ("Columns".equals(gubun)) {  // 컬럼
		    				returnList.add(colId);
		    			} else {  // 데이터
		    				String strval = "";
		    				for(int k=0; k<values.length; k++) {
		    					if(k==0) {
		    						strval = values[k];
		    					}else {
		    						strval += deli + values[k];
		    					}
		    				}
		    				returnList.add(strval);
		    			}
		    		}
    			}
    		}
    	}

		return returnList;
	}
	
	// update 컬럼 및 데이터
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List updateColumnsList(HttpServletRequest request, HashMap parameterObject, List<EgovMap> selectColumnsList) {		
		String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		 
		List returnList = new ArrayList();
		
		if (parameterObject.size() > 0) {
			
	    	Set key = parameterObject.keySet();

	    	for (Iterator iterator = key.iterator(); iterator.hasNext();) {

	    		 String keyNm = (String)iterator.next();
	    		 String value = (String)parameterObject.get(keyNm);
	    		 //System.out.println("##### keyNm : " + keyNm + ", value : " + value);
	    		 if ("pass_chg".equals(keyNm)) {continue;}
    			 
    			 if (value=="") continue; //pjh 수치형 null 처리 컬럼빼기
    			 
    			 for (int i=0; i<selectColumnsList.size(); i++) {
    				 String colId    = (String) selectColumnsList.get(i).get("colId");     // 컬럼ID
    				 String cryptoYn = (String) selectColumnsList.get(i).get("cryptoYn");  // 암호화여부
    				 String objType  = (String) selectColumnsList.get(i).get("objType");   // 객체타입
    				 String dataType  = (String) selectColumnsList.get(i).get("dataType");   // 데이터타입
    				 
    				  if (keyNm.equals(colId)) { 
    					 String nval = ConvVal( unibillFuncEnc,  cryptoYn, keyNm,  value, objType, dataType); 
    					 parameterObject.put(keyNm, nval);
						 returnList.add(keyNm+ " = #{"+keyNm+"}"); 
    				 }
    			 
    			 }

	    	 }
   	    }

   	    return returnList;
	
	}

	// 그리드 json data 생성
		@SuppressWarnings({ "rawtypes", "unchecked", "null" })
		public static List selectJsonDataListOrg(String gubun, List<EgovMap> jsonData, List selectColumnsList, String unibillFuncEnc) throws Exception {
			    
//			CryptoUtils cryptoTest = new CryptoUtils();
			
			String left_bracket  = "(";
			String right_bracket = ")";
			
			// 엑셀다운로드 일 경우
			if ("E".equals(gubun)) {
				left_bracket  = "{";
				right_bracket = "}";
			}
			
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
			ContentService service = (ContentService) webApplicationContext.getBean("contentService");
				
			List<EgovMap> jsonList = new ArrayList<EgovMap>();
			
			for (int i=0; i<jsonData.size(); i++) {
	        	
	        	Map map = (Map) jsonData.get(i);
	        	
	        	Iterator<String> it =  map.keySet().iterator();
	        	EgovMap map2 = new EgovMap();
	        	
	        	while(it.hasNext()) {
	        		
	  	    		String keyNm = (String)it.next();
	  	    		String value = map.get(keyNm).toString();

	  	    		for (int j=0; j<selectColumnsList.size(); j++) {
		  	          	Map map3 = (Map) selectColumnsList.get(j);
		  	          	
		  	          	Iterator<String> it3 =  map3.keySet().iterator();
		  	            while(it3.hasNext()) {
		
	  	    	    		 String keyNm3 = (String)it3.next();
	  	    	    		 String value3 = map3.get(keyNm3).toString();

	                         if (!"".equals(value3)) {
	                        	 value3 = CamelUtil.convert2CamelCase(value3);
	                         }
		    	    		 
	  	    	    		 String grpCd    = (String) map3.get("grpCd");
	  	    		   		 String grpType  = (String) map3.get("grpType");
	  	    		   		 String xmlId    = (String) map3.get("xmlId");
	  	    		   		 String sql      = (String) map3.get("objSql");
	  	    		   	     String objType  = (String) map3.get("objType");
	  	    		   	     String cryptoYn = (String) map3.get("cryptoYn");  	    		   	
	  	    		   	     
	  	    		   		 if (keyNm.equals(value3)) {
	  	    	    			 
	  	    	    			 // fwform table에 객체타입이 null 인경우만... 
	  	    	    			 if (StringUtils.isEmpty(objType)) { 
			  	    	    		     // 코드 조회
			  		  	    	    	 if (!StringUtils.isEmpty(grpCd) || !StringUtils.isEmpty(grpType) || !StringUtils.isEmpty(xmlId) || !StringUtils.isEmpty(sql)) {
			  		  	    	    	
			  		  	    	    		HashMap<String, String> parmObj =  new HashMap<String, String>();
			  		  	    	    		
					  		      		    // 그룹타입
					  		      		    if (!StringUtils.isEmpty(grpType)) {
					  		      		    	parmObj.put("cdGun",   "grp_type");
					  		      		    	parmObj.put("grp_type", grpType); // 그룹타입
					  		      		    // 그룹코드
					  		      		    } else if (!StringUtils.isEmpty(grpCd)) {
					  		      		    	parmObj.put("cdGun",   "grp_cd");
					  		      		    	parmObj.put("grp_cd",   grpCd);   // 그룹코드		  		      		    
					  		      		    // xmlid
					  		      		    } else if (!StringUtils.isEmpty(xmlId)) {
					  		      		    	parmObj.put("cdGun",   "xml_id");
					  		      		    	parmObj.put("xmlid",    xmlId);   // xml id
					  		      		    // sql
					  		      		    } else if (!StringUtils.isEmpty(sql)) {
					  		      		    	parmObj.put("cdGun",   "sql");
					  		      		    	parmObj.put("sql",      sql);     // sql
					  		      		    }
					  		      		    
					  		      		    parmObj.put("val",  value);   // 코드값
					  		      			List<EgovMap> codeList = service.selectDynamicDataList(parmObj);
					  		      			String cdNm = "";
					  		      			
					  		      			if (codeList.size() > 0) {				  	
					  		      				if (codeList.get(0).get("name2") == null && !"E".equals(gubun)) {
						  		      				if (codeList.get(0).get("name2") == null ) {	//엑셀에서 코드값보이게(공통 업로드시 필요)
						  		      					for(int l=0; l<codeList.size(); l++){
						  		      						if(value.equals(codeList.get(l).get("code").toString())){
						  		      							cdNm = codeList.get(l).get("name").toString() + " "+left_bracket+"" + codeList.get(l).get("code").toString() + ""+right_bracket+"";
						  		      						}
						  		      					}
						  		      				} else {
							  		      				for(int l=0; l<codeList.size(); l++){
						  		      						if(value.equals(codeList.get(l).get("code").toString())){
						  		      							cdNm = codeList.get(l).get("name").toString();
						  		      						}
						  		      					}
						  		      				}
					  		      				}
					  		      			map2.put(keyNm, cdNm);
					  		      			}else{
					  		      				if (!"E".equals(gubun)) {
						  		      				cdNm = value + " "+left_bracket+"" + value + ""+right_bracket+"";
						  		      			}else{
					  		      					cdNm = value;
					  		      				}
						  		      		map2.put(keyNm, cdNm);
					  		      			}
			  		  	    	    	}
			  		  	    	    	/* YN check */
				  	    	    		else  if (keyNm.indexOf("Yn") > -1 && StringUtils.isEmpty(sql)) {   
				  	    	    				HashMap<String, String> parmObj =  new HashMap<String, String>();
				  	    	      			
					  	    	      			parmObj.put("cdGun",  "xml_id");
					  	    	      			parmObj.put("xmlid",  "selectYnCode");
					  	    	      		    parmObj.put("val",     value);   // 코드값
					  	    	      		    List<EgovMap> codeNmList = new ArrayList<EgovMap>();
					  	    	      		    EgovMap r1 = new EgovMap();
					  	    	      		    EgovMap r2= new EgovMap();
					  	    	      		    
					  	    	      		    if(value.equals("Y")){
					  	    	      		    	r1.put("code", "Y");
					  	    	      		    	r1.put("name", "예");
					  	    	      		    	codeNmList.add(r1);
					  	    	      			
					  	    	      		    }else{
					  	    	      		    	r2.put("code", "N");
					  	    	      		    	r2.put("name", "아니오");
					  	    	      		    	codeNmList.add(r2);
					  	    	      		    }
					  	    	      		    
					  	    	      		    String cdNm = "";
						  		      			if (codeNmList.size() > 0) {
						  		      				if (codeNmList.get(0).get("name2") == null && !"E".equals(gubun)) {
						  		      					cdNm = (String) codeNmList.get(0).get("name") + " "+left_bracket+"" + (String) codeNmList.get(0).get("code") + ""+right_bracket+"";
						  		      				} else {
						  		      					cdNm = (String) codeNmList.get(0).get("name");
						  		      				}
						  		      				
						  		      			}
					  	    	      			map2.put(keyNm, cdNm);
				  	    	    				 
				  	    	    		} 		  		  	    	    	 
		  		  	    	    	   else {	  	    	    				
		  		  	    	    		if (value.length() == 1 && " ".equals(value)) {		  		  	    	    		    
		  	    	    					map2.put(keyNm, "");
		  	    	    				} else {
		  	    	    					
		  	    	    					String val = value;
		  	    	    					map2.put(keyNm, val);
		  	    	    				}
		  		  	    	    	 } //-------- if (objType == null)-----
			  		  	    	     
	  	    	    			} else {
		  	    	    			
	  	    	    				if (value.length() == 1 && " ".equals(value)) {
	  	    	    					map2.put(keyNm, "");
	  	    	    				} else {  	    	    				
	  	    	    					map2.put(keyNm, value);
	  	    	    				}
	  	    	    				
	  	    	    			 }
	  	    	    			 
	  		  	    	    	 // 그리드 data 조회시
	  		  	    	    	 if ("G".equals(gubun)) {
	  		  	    	    		 map2.put("ubseq", map.get("ubseq").toString());
	  		  	    	    	 }
	  		  	    	    	 
	  	    	    		 }  // end if
	  	    	    		   	    	    	
	  	    	    		 
		  	          	}  // end while
		  	          	
		  	        }  // end for    		
	  	    		  	    		
	        	}  // end while
	        	
	        	jsonList.add(map2);
	        	
	        }
			
			return jsonList;
		}

	// 그리드 json data 생성 pjh
	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	public static List selectJsonDataList(String gubun, List<EgovMap> jsonData, List selectColumnsList, String unibillFuncEnc, String roleId) throws Exception {
		   
//		CryptoUtils cryptoTest = new CryptoUtils();
		
		String left_bracket  = "(";
		String right_bracket = ")";
		
		// 엑셀다운로드 일 경우
		if ("E".equals(gubun)) {
			left_bracket  = "{";
			right_bracket = "}";
		}
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		List<EgovMap> jsonList = new ArrayList<EgovMap>();
		boolean findflag=false;
		String cdNm = "";
		for (int i=0; i<jsonData.size(); i++) {
			Map map = (Map) jsonData.get(i);
        	
        	Iterator<String> it =  map.keySet().iterator();
        	EgovMap map2 = new EgovMap();
        	while(it.hasNext()) {
        		findflag=false;
  	    		String keyNm = (String)it.next();
  	    		String value = map.get(keyNm).toString();

  	    		if(keyNm.equals("ubseq") ) {
  	    			map2.put(keyNm, value); 
					continue;
				}
  	    		if (keyNm.indexOf("Nm") > -1 ||  keyNm.indexOf("nm") > -1 ) {   
  	    			map2.put(keyNm, value); 
  	    			continue;
  	    		}
  	    		for (int j=0; j<selectColumnsList.size(); j++) {
					Map map3 = (Map) selectColumnsList.get(j);	  	          	
					Iterator<String> it3 =  map3.keySet().iterator();
					
					String colId    = StringUtil.isNullToString((String) map3.get("colId"));
					String grpCd    = StringUtil.isNullToString((String) map3.get("grpCd"));
					String grpType  = StringUtil.isNullToString((String) map3.get("grpType"));
					String xmlId    = StringUtil.isNullToString((String) map3.get("xmlId"));
					String sql      = StringUtil.isNullToString((String) map3.get("objSql"));
					String colTbl   = StringUtil.isNullToString((String) map3.get("colTbl"));					
					String objType  = StringUtil.isNullToString((String) map3.get("objType"));
					String cryptoYn = (String) map3.get("cryptoYn"); 
					String sqlCancelYn = (String) map3.get("sqlCancelYn");
					
					String egovcol =   CamelUtil.convert2CamelCase(colId);
					if(!keyNm.equals(egovcol)   ) {
						continue;
					}
					if ("*".equals(value)) { //pjh 20201218 *인 경우 전체로 표기
						cdNm = "전체" + " "+left_bracket+"" + value + ""+right_bracket+""; 
						map2.put(keyNm, cdNm);
						findflag=true;
						continue;
					}
					if (!StringUtils.isEmpty(grpCd) || ! StringUtils.isEmpty(grpType)  || ! StringUtils.isEmpty(colTbl) ) { //앞에서 처리함
	    	    		map2.put(keyNm, value);	    
	    	    		continue;
					}
					if (keyNm.indexOf("Yn") > -1 && StringUtils.isEmpty(grpCd) && StringUtils.isEmpty(grpType) && StringUtils.isEmpty(sql)) {
    	      		    if(value.equals("Y")){
    	      		    	cdNm="예("+value+")";	    	      			
    	      		    }else{
    	      		    	cdNm="아니오("+value+")";	    
    	      		    }
	    	      		map2.put(keyNm, cdNm);
	    	      		findflag=true;
	    	      		continue;
					}
					if (StringUtils.isEmpty(grpCd) && StringUtils.isEmpty(grpType) 
  	    		   			&& StringUtils.isEmpty(xmlId) && StringUtils.isEmpty(sql)) {
  	    		   		continue;
  	    		   	}  
					if ("Y".equals(cryptoYn)) { 
						findflag=true;
						String val = value;

						map2.put(keyNm, val);
						break;
					} 
					// data select
					HashMap<String, String> parmObj =  new HashMap<String, String>();
					// 그룹타입
	      		    if (!StringUtils.isEmpty(grpType)) {
	      		    	parmObj.put("cdGun",   "grp_type");
	      		    	parmObj.put("grp_type", grpType); // 그룹타입
	      		    // 그룹코드
	      		    } else if (!StringUtils.isEmpty(grpCd)) {
	      		    	parmObj.put("cdGun",   "grp_cd");
	      		    	parmObj.put("grp_cd",   grpCd);   // 그룹코드		  		      		    
	      		    // xmlid
	      		    } else if (!StringUtils.isEmpty(xmlId)) {
	      		    	parmObj.put("cdGun",   "xml_id");
	      		    	parmObj.put("xmlid",    xmlId);   // xml id
	      		    // sql
	      		    } else if (!StringUtils.isEmpty(sql) /* && !sql.equals("N") */) { //pjh 자기테이블 obj_sql 명칭 안보이게처리
	      		    	parmObj.put("cdGun",   "sql");
	      		    	parmObj.put("sql",      sql);     // sql
	      		    	
	      		    	
	      		    }
	      		    parmObj.put("val",  value);   // 코드값

	      		    parmObj.put("role_code",  roleId); 
	      		    if(!sqlCancelYn.equals("Y")){
		      		  	if(!StringUtils.isEmpty(sql) && objType.equals("text") && colTbl.isEmpty()) {
//		      		  		List<EgovMap> codeList = service.selectDynamicDataList(parmObj);
//			      		  	if (codeList.size() > 0) {
//			      		  		cdNm = "";
//			      		  		findflag=true;
//			      		  		for(int l=0; l<codeList.size(); l++){
//			      		  			cdNm += codeList.get(l).get("sqlval").toString();
//			      		  		}
//			      		  		System.out.println("11111 : " + colId + ", " + keyNm + ", " + cdNm);
//			      		  		map2.put(keyNm, cdNm);
//			      		  	}else {
//			      		  		map2.put(keyNm, value);
//			      		  	}
		      		  		
		      		  	}else {
			      		  	if(sql.equals("N")!=true && colTbl.isEmpty()) {
			      		    	List<EgovMap> codeList = service.selectDynamicDataList(parmObj);
				      			
				      			
				      			if (codeList.size() > 0) {	
				      				cdNm = "";
				      				findflag=true;
				      				if (codeList.get(0).get("name2") == null && !"E".equals(gubun)) {
					      				if (codeList.get(0).get("name2") == null ) {	//엑셀에서 코드값보이게(공통 업로드시 필요)
					      					for(int l=0; l<codeList.size(); l++){
					      						if(value.equals(codeList.get(l).get("code").toString())){
					      							cdNm = codeList.get(l).get("name").toString() + " "+left_bracket+"" + codeList.get(l).get("code").toString() + ""+right_bracket+"";
					      						}
					      					}
					      				} else {
				  		      				for(int l=0; l<codeList.size(); l++){
					      						if(value.equals(codeList.get(l).get("code").toString())){
					      							cdNm = codeList.get(l).get("name").toString();
					      						}
					      					}
					      				}
				      				}
				      				
				      				map2.put(keyNm, cdNm);
					      		}else{
					      			cdNm = "";
					      			findflag=true;
					      			if (!"E".equals(gubun)) {
				  		      			cdNm = "미설정" + " "+left_bracket+"" + value + ""+right_bracket+""; 
				  		      		}else{
			  		      				cdNm = value;
			  		      			}
					      			map2.put(keyNm, cdNm);
			  		      		}
			      		  	}
		      		  	}
	      		    	
		      			break;
	      		    }
	      		    else{
	      		    
	      		    }
	      		     
	      			
	      		}  // end for    
  	    		
  	    		if(!findflag) { //찾는것 없을때 데이타 
  	    			if (!"E".equals(gubun)) {
  	    				cdNm = value;
		      			}
      				else{
      					cdNm = value;
      				}
      				map2.put(keyNm, cdNm); 
      			}
  	    		  	    		
        	}  // end while
        	jsonList.add(map2);
        }
		return jsonList;
	}

	// 그룹코드, 기준정보 및 기초코드(공통코드) 조회(콤보, 체크박스, 라디오버튼 등)
	@SuppressWarnings("rawtypes")
	public static String RequestDynDataMap(String grptype, String grpcd, String xmlid, String sql, String objType, String fwformObjSql, HashMap parameterObject) throws Exception {
		
		String sqlStr = "";
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		if (parameterObject.size() > 0) {
			Set key = parameterObject.keySet();
			
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {

	    		 String keyNm = (String)iterator.next();		    		 
	    		 String value = (String)parameterObject.get(keyNm);
	    		 
	    		 parmObj.put(keyNm, value);
			}
		}
		// 1. 그룹타입
		if (!StringUtils.isEmpty(grptype)) {
			parmObj.put("cdGun",   "grp_type");
			parmObj.put("grp_type", grptype);
			return ContentUtil.dynData(parmObj, objType, fwformObjSql);
		}
		// 2. 그룹코드
		if (!StringUtils.isEmpty(grpcd)) {
			parmObj.put("cdGun",   "grp_cd");
			parmObj.put("grp_cd",   grpcd);
			return ContentUtil.dynData(parmObj, objType, fwformObjSql);			
		}
		// 3. xml id
		if (!StringUtils.isEmpty(xmlid)) {
			parmObj.put("cdGun",   "xml_id");
			parmObj.put("xmlid",    xmlid);
			return ContentUtil.dynData(parmObj, objType, fwformObjSql);			
		}
		// 4. sql
		if (!StringUtils.isEmpty(sql)) {
			parmObj.put("cdGun",   "sql");
			parmObj.put("sql",      sql);
			System.out.println("sql : " + sql);
			System.out.println("fwformObjSql : " + fwformObjSql);
			
			if(objType.equals("text")) {
				return ContentUtil.dynDataText(parmObj, objType, fwformObjSql);
			}else {
				return ContentUtil.dynData(parmObj, objType, fwformObjSql);
			}
		}
		return sqlStr;
	}
	
	// 코드 생성
	@SuppressWarnings("rawtypes")
	public static String dynData(HashMap parmObj, String objtype, String fwformObjSql) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");

		//log.debug("XXXXXXTTT dynData, parmObj="+parmObj.toString() + " fwformObjSql="+fwformObjSql);
		String dataStr = "";
		if(fwformObjSql == null){fwformObjSql="";}
		if(fwformObjSql.equals("N")==true) {
			dataStr = "|";
			return dataStr;
		}
		List<EgovMap> codeList = service.selectDynamicDataList(parmObj);
		if (codeList.size() > 0) {    			
			for (int j=0; j<codeList.size(); j++) {
				String code  = String.valueOf(codeList.get(j).get("code"));
				String name  = String.valueOf(codeList.get(j).get("name"));
				
//				String txt   = String.valueOf(codeList.get(j).get("txt"));
//				if ( "label".equals(objtype) || "text".equals(objtype) ) {				
				
				if ("hidden".equals(objtype)) {
					dataStr = code;
				} else {
					
					if ("select".equals(objtype)) {					
						if (codeList.get(j).get("name2") == null) {
							name = name + " ("+code+")";					
						}
					}
					
					dataStr += "@" + code + "|" + name;
					
				}
			}
			
			if (dataStr.indexOf("@") > -1) {
				dataStr = dataStr.substring(1);
			}
			
		} else {
			dataStr = "|";
		}				
		return dataStr;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static String dynDataText(HashMap parmObj, String objtype, String fwformObjSql) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		//log.debug("XXXXXXTTT dynData, parmObj="+parmObj.toString() + " fwformObjSql="+fwformObjSql);
		String dataStr = "";
		if(fwformObjSql == null){fwformObjSql="";}
		if(fwformObjSql.equals("N")==true) {
			dataStr = "|";
			return dataStr;
		}
		
		//parmObj.put("sql", fwformObjSql);
		List<EgovMap> codeList = service.selectDynamicDataList(parmObj);
		if (codeList.size() > 0) {    			
			for (int j=0; j<codeList.size(); j++) {
				String name  = String.valueOf(codeList.get(j).get("sqlval"));
				if(!StringUtils.isEmpty(name) && !name.equals("null")) {
					dataStr += name;
				}
			}
			if(dataStr.equals("")) {
				dataStr = "|";
			}
		} else {
			dataStr = "|";
		}				
		return dataStr;
	}
	
	// 엑셀업로드 컬럼 및 data 추출
	public static String excelUploadData(String resultData, String gubun) throws Exception {		
		String returnStr = "";		
		String [] rowDataArr =  resultData.split("@");
		
		for(int i=0; i<rowDataArr.length; i++){ 
			
			String cellVal = rowDataArr[i];
			String insertCol = "";          // insert 컬럼
			
			if ("column".equals(gubun)) {
				int s_index = cellVal.indexOf("(");
				int e_index = cellVal.indexOf(")");
				insertCol = cellVal.substring(s_index+1, e_index);

				if ("".equals(returnStr)) {
					returnStr = insertCol;
				} else {
					returnStr += "@" + insertCol;
				}
			
			} else if ("header".equals(gubun)) {
			
				returnStr += "@" + cellVal;
			}
						
		}
		

		return returnStr;
	}

	// 이용정지 엑셀업로드 컬럼 및 data 추출
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List excelUploadUseInsertColumnsList(String insertColumnsStr, String column) {		
		
		List returnList = new ArrayList();
		String [] rowDataArr = insertColumnsStr.split("@");
		String [] col = column.split(";");
		
		int j = 0;
		for(int i=0; i<rowDataArr.length; i++){
			if(Integer.toString(i).equals(col[j])){
				String val = null;
				if(rowDataArr[i]!=null){
					val = rowDataArr[i];
				}
				returnList.add(val);
				j++;
			}
		}
		return returnList;
	}
	
	// 엑셀업로드 insert 컬럼 정보 생성
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List excelUploadInsertColumnsList(String insertColumnsStr) {		
		
		List returnList = new ArrayList();
		
		//String [] rowDataArr =  insertColumnsStr.split("|", -1);
		String [] rowDataArr = insertColumnsStr.split("@");
		
		for (int i=0; i<rowDataArr.length; i++) {
			String val = rowDataArr[i];
			int s_data = val.indexOf("{");
			int e_data = val.indexOf("}");
			
			if (s_data > -1) {
				val = val.substring(s_data+1, e_data);
			}
			returnList.add(val);
		}
		
		return returnList;
	}
	
	
	// key 컬럼 정보 생성
	@SuppressWarnings("rawtypes")
	public static String selectKeyColList(HashMap parameterObject) throws Exception {
		ContentUtil conUtil= new ContentUtil();
		
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		List<EgovMap> keyColList = service.selectKeyColList(parameterObject);
		//List<EgovMap> tabKeyColList = service.selectTabKeyColList(parameterObject);
		
		log.info("selectKeyColList>  keyColList : " + keyColList );
		//log.info("selectKeyColList>  tabKeyColList : " + tabKeyColList );
		 
		
		String keyCol = "";
		for (int i=0; i<keyColList.size(); i++) {			
			keyCol +=   (String) keyColList.get(i).get("keyCol");
			if (i >0) {
				keyCol += " , ";
			} 
		} 
		log.info("selectKeyColList>  keyCol : " + keyCol );  
		return keyCol;
	}
	
	// key 컬럼 정보 생성
	@SuppressWarnings("rawtypes")
	public static String selectTabKeyColList(HashMap parameterObject) throws Exception {
		ContentUtil conUtil= new ContentUtil(); 
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		 
		List<EgovMap> keyColList = service.selectTabKeyColList(parameterObject);
		
		//log.info("selectTabKeyColList>  keyColList : " + keyColList + "keyColList.size="+keyColList.size() );
		String keyCol =""; 
	 
		if(!keyColList.isEmpty()) { 
			if(keyColList.get(0) == null){ 
				return keyCol;
			}else{
				String v = keyColList.get(0).get("keyCol").toString(); 
				keyCol =   v ;
			} 
		}		  
		log.info("selectKeyColList>  keyCol : " + keyCol ); 			
		return keyCol;
	}


	// key 컬럼 index 정보 조회
	public static String selectKeyColIndex(String insertColumnsStr, Object parameterObject) throws Exception {
				
		String retIndex = "";
		String [] rowDataArr =  insertColumnsStr.split("@");  // insert or update 컬럼
		String keyCol = parameterObject.toString();           // key 컬럼
		
		for (int i=0; i<rowDataArr.length; i++) {

			if (keyCol.indexOf(rowDataArr[i]) > -1) {

				if ("".equals(retIndex)) {
					retIndex = Integer.toString(i);
				} else {
					retIndex += "," + i;
				}
			}
		}
		
		return retIndex;
	}

	// 엑셀 업로드 update 데이터 생성
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> updateColumnsList(List<?> insertColumsList, List<?> insertDataList, String keyColIdx) {

		List returnList = new ArrayList();

		if (insertColumsList.size() > 0) {

			// update 컬럼

			for (int i=0; i<insertColumsList.size(); i++) {

 				if (keyColIdx.indexOf(i+"") <= -1) { 
			    	// update data	
			    	String val = null;
			    	if(insertDataList.get(i) != null){
			    		val = (String) insertDataList.get(i);
			    	}
			    	
					int s_data = val.indexOf("{");
					int e_data = val.indexOf("}");
					
					if (s_data > -1) {
						val = val.substring(s_data+1, e_data);
					}
					
					returnList.add(insertColumsList.get(i) + " = '" + val + "'");
				}
			}

		}

		return returnList;
	}

	// 상세정보 json data 생성
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<HashMap> selectJsonDetailData(List<EgovMap> jsonData, List<EgovMap> selectColumnsList, String unibillFuncEnc) {
		
//		CryptoUtils cryptoTest = new CryptoUtils();
		
		List<HashMap> jsonList = new ArrayList<HashMap>();
		
		for (int i=0; i<jsonData.size(); i++) {
			Map map = (Map) jsonData.get(i);

        	Iterator it =  map.keySet().iterator();
        	
        	HashMap map2 = new HashMap();
        	
        	while(it.hasNext()) {

  	    		Object keyNm = it.next();
  	    		String value = map.get(keyNm).toString();

  	    		for (int j=0; j<selectColumnsList.size(); j++) {
	  	          	Map map3 = (Map) selectColumnsList.get(j);
	  	          	
	  	          	Iterator<String> it3 =  map3.keySet().iterator();
	  	          	int x = 0;
	  	          	while(it3.hasNext()) {
	
  	    	    		 String keyNm3 = (String)it3.next();
  	    	    		 String value3 = map3.get(keyNm3).toString();
  	    	    		 String cryptoYn = (String) map3.get("cryptoYn");  // 암호화여부
  	    	    		 String obj_type = (String) map3.get("objType");  // 객체타입
	    	    		 //System.out.println("keynm : " + keyNm3 + ", value3 : " + value3 + ", obj_type : " + obj_type);
  	    	    		 // DB data 쿼리 결과 컬럼에 값이 null 이 아닌 경우
  	    	    		 if (map.containsKey(value3)) {
  	    	    		 
	                         if (!"".equals(value3)) {
	                        	 value3 = CamelUtil.convert2CamelCase(value3);
	                         }
	                         
	                         String keyNm2 = CamelUtil.convert2CamelCase((String) keyNm);

//	                         String cryptoYn = (String) map3.get("cryptoYn");  // 암호화여부                                                  
	                         
	                         if (keyNm2.equals(value3)) {
	                        	 /*pjh select시에 암호화 함수 사용
	                        	 ContentUtil contentUtil = new ContentUtil();
	                        	 if ("Y".equals(cryptoYn) && unibillFuncEnc.equals("-")) {  // 암호화 컬럼일 경우 복호화 처리	                        		 
	                        		 try {
	    	    						map2.put(keyNm, contentUtil.getCryptoValJava("DEC", value, keyData));
	    	    					 } catch (Exception e) {
										map2.put(keyNm, value);
	    	    					 }
	                        		 
	    	    					 //map2.put(keyNm, cryptoUtils.decryptAES256(value));
	                        		 
	                        	 }else if("Y".equals(cryptoYn) && !unibillFuncEnc.equals("-")){
	                        		 try {
		    	    					//map2.put(keyNm, contentUtil.getCryptoValJava("DEC", value, keyData));
	                        			map2.put(keyNm,contentUtil.getCryptoValDB("DEC", value, (String)keyNm, keyData, unibillFuncEnc));
		    	    				 } catch (Exception e) {
										map2.put(keyNm, value);
		    	    				 }
	                        	 }else {
	                        		 map2.put(keyNm, value);
	                        	 }
	                        	 */
	                        	 map2.put(keyNm, value); 
	
	                         }

  	    	    		 } else {  // DB data 쿼리 결과 컬럼에 값이 null 이 아닌 경우
  	    	    			 
  	    	    			// 객체 컬럼 조회 결과 첫번째 col_id(개체ID)인 경우
  	    	    			if (x == 0) {
  	    	    				map2.put(value3, "");
  	    	    			}
  	    	    			
  	    	    		 }
                         
                         x += 1;

	  	          	} // end while
	  	          	
  	    		}  	    	  	    	
  	    		
        	}  // end while
        	
        	map2.put("ubseq", map.get("ubseq").toString());
        	
        	jsonList.add(map2);
        	
		}  // end for
		
		return jsonList;
		
	}  // end public
	
	
	// 암 복호화 java pjh
	public static String selectCrypto(String unibillFuncEnc, String cryptoYn, String crypto_gubun,  String keyNm,String value,String colId,  String crypto_key) throws Exception {
		
		return value; //f_enc 직접 처리
		/*
		String retval = crypto_val;
		 String val=value ;
		 ContentUtil contentUtil= new ContentUtil();
		 
		if ("Y".equals(cryptoYn)) {
			 return retval; //f_enc 직접 처리
			
		    if(unibillFuncEnc.equals("-")){
		        try {
		    		val = contentUtil.getCryptoValJava("ENC", value, keyData); // 파라메타 <암/복호화구분(ENC : 암호화, DEC : 복호화)>, <암/복호화 값>, <암/복호화 key data>
		    	} catch (Exception e) {
		    		val = keyNm;
		    	}
		     
		    }
		    else{
		     try {
		    		val = contentUtil.getCryptoValDB("ENC", value, colId, keyData, unibillFuncEnc);
		    	} catch (Exception e) {
		    		val = keyNm;
		    	}
		    }
		      
		 } 		
		selectCryptoWhere(contentUtil.dbType,cryptoYn, keyNm,val) ;
		return retval;
		*/	
		
	}
	
	// 암 복호화 java pjh returnList.add("AND UPPER(" + keyNm + ") = UPPER(#{" + keyNm + "}) ");
	public static String selectCryptoWhere( String dbType,  String cryptoYn,  String keyNm, String val) throws Exception {
		
		String retval ;
		if ("Y".equals(cryptoYn)) {
			retval = "AND " + keyNm + " = '" + val + "' ";		    
		}
		else {
			if(dbType.equals("Maria")){
		    	retval = "AND UPPER(" + keyNm + ") LIKE UPPER(concat('%',#{" + keyNm + "},'%')) ";
		    }else{
		    	retval = "AND UPPER(" + keyNm + ") LIKE UPPER('%'||#{" + keyNm + "}||'%') ";
		    }
		}
		return retval;   
	}

	// 암/복호화 디비 함수
	public static String getCryptoVal(String crypto_gubun, String crypto_val, String crypto_key) throws Exception {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		parmObj.put("crypto_gubun", crypto_gubun);	  // ENC : 암호화, DEC : 복호화
		parmObj.put("crypto_val",   crypto_val);
		parmObj.put("crypto_key",   crypto_key);
		
		return service.getCryptoData(parmObj);		
		
	}
	
	// 암 복호화 java, db에서 rawdata 읽어서 변환처리
	public static String getCryptoValJava(String crypto_gubun, String crypto_val, String crypto_key) throws Exception {
		CryptoUtils cryptoUtils = new CryptoUtils();

		cryptoUtils.setKEY(crypto_key);
		String str="";
		
		if(crypto_gubun.equals("ENC")){
			str = cryptoUtils.encryptAES256(crypto_val);
		}else{
			str = cryptoUtils.decryptAES256(crypto_val);
		}
		
		return str;		
			
	}
	
	// 암 복호화 디비
	public static String getCryptoValDB(String crypto_gubun, String crypto_val, String crypto_colID, String crypto_key, String unibillFuncEnc) throws Exception{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap parmObj =  new HashMap();
		
		// ENC : 암호화, DEC : 복호화
		if(crypto_gubun.equals("ENC")){
			parmObj.put("crypto_gubun", 1);
		}else if(crypto_gubun.equals("DEC")){
			parmObj.put("crypto_gubun", 2);
		}else{
			parmObj.put("crypto_gubun", 3); // 암호화 없이
		}
		parmObj.put("crypto_colID",   crypto_colID);
		parmObj.put("crypto_val",   crypto_val);
		parmObj.put("crypto_key",   crypto_key);
		parmObj.put("crypto_func", unibillFuncEnc);
		
		
		return service.getCryptoValDB(parmObj);		
		
	}
	
	public static String getfilegrp(List<EgovMap> selectColumnsList) throws Exception{
		String fileGrp = "";
		for(EgovMap egov : selectColumnsList){
			String colNm = (String) egov.get("colId");
			if(colNm.equals("fileupload") || colNm.equals("sfileupload") || colNm.equals("cfileupload")){
				fileGrp = egov.get("fileGrp").toString();
			}
		}
		return fileGrp;
	}
	
	public static String getimgfilegrp(List<EgovMap> selectColumnsList) throws Exception{
		String fileGrp = "";
		for(EgovMap egov : selectColumnsList){
			String colNm = (String) egov.get("colId");
			if(colNm.equals("imgfileupload")){
				fileGrp = egov.get("fileGrp").toString();
			}
		}
		return fileGrp;
	}
	
	public static HttpServletRequest modifyparam(HttpServletRequest request) throws Exception {
		RequestModifyParameter Rrequest= new RequestModifyParameter((HttpServletRequest) request);
		Enumeration transen = Rrequest.getParameterNames();
		while(transen.hasMoreElements()){    			
			String key = transen.nextElement().toString();
			String val = Rrequest.getParameter(key).replace("'", "").replace("\"", "").replace("/", "")
					.replace("\\", "").replace(";", "").replace(":","").replace("--", "").replace("%", "");    			
		    Rrequest.setParameter(key, val);
		    
		} 
		return Rrequest;
	}
	
	public static boolean chkparam(HttpServletRequest request) throws Exception {
		boolean chkRs = true;
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){    			
			String key = en.nextElement().toString();
			String val = request.getParameter(key);
			if(val.contains("&") || val.contains("|") || val.contains(";")) {
				chkRs = false;
			}
		    
		} 
		return chkRs;
	}
	
	public static Map<String, Object> vriPass(String userid, List<EgovMap> PassRule_jsonData, String targetPasswd) throws Exception{
		HashMap returnMap = new HashMap();
		int failflag=0;
		String msg = "";
		String code = "";
		int minlen=Integer.parseInt(PassRule_jsonData.get(0).get("minLen").toString());
		int minlen2=Integer.parseInt(PassRule_jsonData.get(0).get("minLen2").toString());
		int maxlen=Integer.parseInt(PassRule_jsonData.get(0).get("maxLen").toString());
		
		if(null != PassRule_jsonData && 0 < PassRule_jsonData.size()){    		
    		msg="";
    		if(IsJuminNo(targetPasswd)) {
    			log.debug("주민번호 포함 오류");
				failflag=1;		
			} 
    		else if(!IsPassLen(userid, targetPasswd,  minlen ,minlen2 ,maxlen )) {    			
    			log.debug("암호정책 오류 userid :"+userid+" minlen: "+minlen + " msg:"+m_errmsg);
    			msg=m_errmsg;
    			failflag=1;				
    		}
    		/*
			else if(!PassRule_jsonData.get(0).get("minLen").toString().equals("0")){
    			int passlen= Integer.parseInt(PassRule_jsonData.get(0).get("minLen").toString()); 
    			if(targetPasswd.length() < passlen) { 
    				failflag=1;
    				msg = msg + "최소 " + PassRule_jsonData.get(0).get("minLen").toString() + "자리 오류  ";
    			}
    		}
			else if(!PassRule_jsonData.get(0).get("maxLen").toString().equals("0")){
    			int passlen= Integer.parseInt(PassRule_jsonData.get(0).get("maxLen").toString());    			
    			if(targetPasswd.length() > passlen) {
    				failflag=1;
	    			//msg = msg + "최대 " + PassRule_jsonData.get(0).get("maxLen").toString() + "자리 오류  ";
    			} 
    		} 
    		*/
			else if(!PassRule_jsonData.get(0).get("seqLen").toString().equals("0")){    	
    			int samelen= Integer.parseInt(PassRule_jsonData.get(0).get("seqLen").toString()); 	
    			if(IsSeqStr(targetPasswd,samelen)) {
    				msg = msg + "순차문자제한 " + PassRule_jsonData.get(0).get("seqLen").toString() + "자리 오류 ";
	    			failflag=1;
    			} 
    		}
    		else if(!PassRule_jsonData.get(0).get("sameLen").toString().equals("0")){ 
    			int samelen= Integer.parseInt(PassRule_jsonData.get(0).get("sameLen").toString()); 	
    			if(IsRepeatStr(targetPasswd,samelen)) {
    				msg = msg + "반복문자제한 " + PassRule_jsonData.get(0).get("sameLen").toString() + "자리 오류 ";
    				failflag=1; 
    			} 
    		}
    		 /*
    		// 패턴규칙 조회  
    		if(PassRule_jsonData.get(0).get("lowcharYn").toString().equals("Y")){
    			msg = msg + "영문소문자";
    			if(!IsStrType(targetPasswd,"L")) {
    				failflag=1; 
    			} 
    		}
    		if(PassRule_jsonData.get(0).get("numYn").toString().equals("Y")){
    			msg = msg + ",숫자";
    			if(!IsStrType(targetPasswd,"N")) {
    				failflag=1; 
    			}     			 
    		}
    		if(PassRule_jsonData.get(0).get("upcharYn").toString().equals("Y")){
    			msg = msg + ",영문대문자 ";
    			if(!IsStrType(targetPasswd,"U")) {
    				code = "passwordCharacterConditionCheck";
    				failflag=1; 
    			}     			 
    		}  
    		
    		if(PassRule_jsonData.get(0).get("spYn").toString().equals("Y")){
    			msg = msg + ",특수문자";
    			if(!IsStrType(targetPasswd,"S")) {
    				failflag=1;		
    			}  
    		}
    		 */
    	}  
		
		returnMap.put("failflag", failflag);
		returnMap.put("msg", msg);
		returnMap.put("code", code);
		return returnMap;
	}
	
	public static boolean IsJuminNoPart(String s) { 
    	int v=0,k=2,i;
    	int len;
 
    	//길이검사
		len = s.length();	    
		if(len!=13) {
			return false;
		}
		int  sum=0;
    	
    	for(i = 0,k=2; i < len-1; i++,k++) {
        	if(s.charAt(i) <'0' || s.charAt(i) >'9') {
        		return false;
        	}
        	v = Integer.parseInt(s.substring(i,i+1) );
        	if(k==10) k=2;
        	sum+=v*k;
        }
        int r = sum%11;
        int nchk = 11-r;
        //2020/10 바뀐 주민번호는 검사 안딤
        int chkno=Integer.parseInt(s.substring(12,12+1) ); //맨끝자리
        if(chkno!=nchk) {
        	return false;
        }
        return true;  
    }
    
    /**
     * 반복문자 검사
     * 
     * @param word
     * @return
     */
    public static boolean IsJuminNo(String word) { 
    	int j,inc=0;
    	int len,idx;
    	len = word.length();
    	String tmp;
    	//중간에 주민번호 나올수 있다
    	for(j = 0; j < word.length(); j++) {    		
    		inc=0;
    		 tmp=word.substring(j);
    		 if(tmp.length()<13) {
    			 return false;
    		 }
    		 if(tmp.length()==13) {
    			 tmp=word.substring(j,j+13); 
    		 }
    		 else {
    			 tmp=word.substring(j,j+14); 
    		 }    		 
    		 idx =  tmp.indexOf("-"); 
    		
    		if(idx<0) { //미존재
    			tmp=word.substring(j,j+13); 
    		}
    		else if(tmp.length()==14 && idx ==6) {
    			tmp=tmp.replace("-", ""); 
    		}
    		else { 
    			continue;
    		}
    		//길이검사
    		len = tmp.length();	    
    		if(len!=13) {
    			return false;
    		}
    		if(IsJuminNoPart(tmp)!=true) {
    			continue;
    		}  
    		else {return true;}
    	}
    	return false;
    }
    
    /**
     * 순차문자 검사
     * 
     * @param word
     * @return
     */
    public static boolean IsSeqStr(String word, int slen) { 
    	char ch=0x00;
    	char tch=0x00; 
    	int ich=0;
    	int itch=0;
    	int chklen=0;
    	String tmp="";
        for(int i = 0; i < word.length(); i++) {   
        	ch = word.charAt(i); 
        	if((i+slen) > word.length()) break;        
        	tmp = word.substring(i,i+slen);        	
        	        	
        	if(!(( ch>='0' && ch<='9') || ( ch>='a' && ch<='z') || ( ch>='A' && ch<='Z'))) continue;
        	        	
        	chklen=1;
        	ch = tmp.charAt(0); 
        	ich= tmp.charAt(0);
        	
        	for(int j = 1; j < tmp.length(); j++) {
        		tch = tmp.charAt(j);
        		itch = tmp.charAt(j); 
        		if(!(( tch>='0' && tch<='9') || ( tch>='a' && tch<='z') || ( tch>='A' && tch<='Z'))) break;
        		if(  (ich+j)==itch) {
        			chklen++;
        		}
        		else break;
        	}
        	if(chklen==slen) {
        		return true;
        	}
        	 
        }    
        return false;         
    }
    
    /**
     * 반복문자 검사
     * 
     * @param word
     * @return
     */
    public static boolean IsRepeatStr(String word, int slen) { 
    	int ch=0x00;
    	int tch=0x00; 
    	int chklen=0;
    	String tmp="";
        for(int i = 0; i < word.length(); i++) {
        	if((i+slen) > word.length()) break;        
        	ch = word.charAt(i); 
        	tmp = word.substring(i,i+slen);        	
        	if(tmp.length()<slen) break;
        	chklen=1;
        	ch = tmp.charAt(0); 
        	for(int j = 1; j < tmp.length(); j++) {
        		tch = tmp.charAt(j);     
        		if(  ch==tch) {
        			chklen++;
        		}
        		else break;
        		 
        	}
        	 
        	if(chklen==slen) {
        		return true;
        	}        	 
        }    
        return false;         
    }
    
    
    /**
     * 반복문자 검사
     * 
     * @param word
     * @return
     *    
         성별(1자리) :
        1 - 1900년대 출생 남자
        2 - 1900년대 출생 여자
        3 - 2000년대 출생 남자
        4 - 2000년대 출생 여자
		지역번호(4자리) : 지역번호는 '시도 구분코드 2자리 + 읍면동 주민센터 고유번호 2자리'로 이루어져 있다.		
		        00~08 : 서울		
		        09~12 : 부산		
		        ...		
		        92~95 : 제주
		
		신고순서에 따른 일련번호(1자리)		
		검증번호(1자리) : 검증하는 방법은 아래와 같다.		
		        ① 주민번호 앞부터 12자리(지역번호까지)까지 각 자리를 2~9까지 곱한다.		
		        ② 나온 숫자를 모두 더한다.		
		        ③ 더한 숫자를 11로 나눈 나머지를 구한다.		
		        ④ 11에서 나머지를 뺀 숫자가 검증번호이다.
         
     */
    
    /**
     * 패스워드 3개조합 8자리 2개조합 10자리검사
     * 
     * @param word
     * @return
     */
    public static boolean IsPassLen(String id, String pass,   int minlen, int minlen2 , int maxlen ) { 
    	char ch=0x00;
     
    	int chkcnt=0;
    	int Upcnt=0;
    	int Lowcnt=0;
    	int Chcnt=0;
    	int Numcnt=0;
    	int Spcnt=0; 
    	
    	 if(maxlen < pass.length()) {
    		 m_errmsg = "암호 최대길이가  " + maxlen + " 입니다";
         	log.debug(m_errmsg); 
         	return false;
         }
    	 if(pass.indexOf(id)>=0) {
         	m_errmsg = "비밀번호에 사용자ID가 포함되어 있습니다";
         	log.debug(m_errmsg); 
         	return false;
         }
    	 
        for(int i = 0; i < pass.length(); i++) {   
        	ch = pass.charAt(i);         	
        	if( ch>='0' && ch<='9') Numcnt++;
        	else if( ch>='a' && ch<='z') Lowcnt++;
        	else if( ch>='A' && ch<='Z') Upcnt++;
        	else   Spcnt++;
        }
        
        /*
        if(Lowcnt<=0 || Upcnt <=0) {
        	m_errmsg = "대소문자 적어도 1개 포함되어야 합니다";
        	log.debug(m_errmsg); 
        	return false;
        }
        */
        chkcnt=0;
        if(Numcnt>0 ) chkcnt+=1;
        if(Spcnt>0 ) chkcnt+=1;
        if(Lowcnt>0 ||  Upcnt>0) chkcnt+=1;
        log.debug("TTTTT Lowcnt="+Lowcnt+ " Upcnt="+Upcnt+" Spcnt="+Spcnt+ " Numcnt="+Numcnt+ " chkcnt="+chkcnt);
        if(chkcnt==2 && pass.length() >= minlen2) ;
        else if(chkcnt>=3 && pass.length() >= minlen) ;
        else {
        	m_errmsg = "영문자/특수문자/숫자중 적어도 2개 이상 포함되어야 하고";
        	m_errmsg += " 2개 조합 최소길이는  "+ minlen2+ " 이고 ";
        	m_errmsg += " 3개 조합 최소길이는  "+ minlen+ " 입니다.";
        	log.debug(m_errmsg); 
        	return false;
        }        
        	  
        m_errmsg="성공";
        log.debug(m_errmsg);
        return true;         
    }
    
    /**
     * 문자의 영문,숫자, 한글 타입을 리턴한다
     * 
     * @param word
     * @return
     */
    public static boolean IsStrType(String word, String type ) { 
    	char ch;
    	String sp = "`~!@#$%)^&*()-_=+{}[]\\\\|;:,.<>/?'";
        for(int i = 0; i < word.length(); i++) {        	
        	ch = word.charAt(i);
        	 if(ch >='0'  && ch <='9' && type.equals("N") ) {
                 return  true;
             } 
        	 if(ch >='a'  && ch <='z' && type.equals("L") ) {
                 return  true;
             } 
        	 /*
        	 if(ch >='A'  && ch <='Z' && type.equals("A") ) {
                 return  true;
             } 
             */
        	 if(ch >='A'  && ch <='Z' && type.equals("U") ) {
                 return  true;
             } 
        	 if(ch >='0'  && ch <='9' && type.equals("N") ) {
                 return  true;
             }   
        	 if (!Character.isLetterOrDigit(ch) && type.equals("S")  ) {
        	      return true;
        	 }
        	 
        }    
        return false;         
    }
    
    public static String useDRM() throws Exception{
    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
    	ContentService contentService = (ContentService) webApplicationContext.getBean("contentService");
    	
    	String addrDRM = ""; 
    	HashMap<String, String> parmObjs =  new HashMap<String, String>();
		
		parmObjs.put("cdGun",   "grp_cd");
		parmObjs.put("grp_cd",   "UNIBILLCONFIG");
		parmObjs.put("dtl_cd", "UNIBILL_ADDRDRM");
		try {
			List<EgovMap> codeList = contentService.selectDynamicDataList(parmObjs);
			
			
			if (codeList.size() > 0) {
				addrDRM = String.valueOf(codeList.get(0).get("code"));
			}
					
			log.info("addrDRM: " + addrDRM);
			if(addrDRM == null || addrDRM.equals("")) {
				addrDRM = "N";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
    	return addrDRM;
    }
    
    public static int encDRM(String opath, String ofnamem, String dpath, String dfname) throws Exception{
    	drm drm = new drm();
    	
    	log.info("DRM DEC 시작");
		int drms = drm.decDRM(opath + File.separator + ofnamem, dpath + File.separator + dfname);
		if(drms == 0) {
			log.info("DRM DEC 완료");
			return drms;
		}else if(drms == -36) {
			log.info("암호화 문서 아님");
		}else {
			log.info("DRM ERROR : " + drms);
		}
    	
    	return drms;
		
    	
    }
}