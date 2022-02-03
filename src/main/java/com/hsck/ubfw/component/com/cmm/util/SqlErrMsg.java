package com.hsck.ubfw.component.com.cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hsck.ubfw.component.content.service.ContentService;

public class SqlErrMsg {
	
	// log print를 위한 선언
	private static final Logger log = LoggerFactory.getLogger(ContentUtil.class);
	
	/** ContentService */
//	@Resource(name = "contentService")
	@Autowired
	private static ContentService contentService;	
	public static String m_errmsg = "";
	
	public SqlErrMsg() {
		
	}
	
	public String getErrmsg(String sErrorMsg) throws Exception{
		String retmsg = "";
		if(sErrorMsg.indexOf("Incorrect integer value") > -1) {
			retmsg  = "숫자를 입력할 자리에 문자가 입력되었습니다. 입력값 확인 후 재 진행바랍니다." ;
		}else if(sErrorMsg.indexOf("Duplicate") > -1) {
				retmsg = "중복된 값이 있습니다. 입력값 확인 후 재 진행바랍니다.";
		}else if(sErrorMsg.indexOf("Unknown character set use") > -1) {
			retmsg = "알지 못하는 캐릭터셋 입니다.";
		}else if(sErrorMsg.indexOf("Index column size too large") > -1) {
			retmsg = "입력된 컬럼 사이즈가 너무 큽니다. 입력값 확인 후 재 진행바랍니다.";
		}else if(sErrorMsg.indexOf("doesn't have a default value") > -1){
			retmsg  = "필수 입력값을  입력하여야 합니다. 입력값 확인 후 재 진행바랍니다." ;
		}else {
			retmsg  = "SQL 에러 입니다 ==> " + sErrorMsg.substring(0, sErrorMsg.length()-1).replace("\'", "").replaceAll("\\r|\\n", " ");
		}
		return retmsg;
	}
	
	
}