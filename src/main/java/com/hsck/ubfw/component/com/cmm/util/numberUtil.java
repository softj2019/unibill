package com.hsck.ubfw.component.com.cmm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;




/**
*
* $unibill.com.util.StringUtil.java
* 클래스 설명 :
*
* @author 홍길동
* @since 2014.11.03
* @version 1.0.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*  수정일		 		수정자			수정내용
*  -------    		--------    ---------------------------
*  2014.11.03		박현우		최초 생성
* </pre>
*/
public class numberUtil {
	public static String fullnumber(String keyno, String tcsn, String padtype ){
		String result = "";
		keyno=keyno.trim();
		String checkkeyno = keyno.replace("-", "");
		String ddd="",prefix="",number=""; 
		
		//1. - 로 분리해서 전화 번호인지 확인
		//02-1111-2222
		int count = StringUtils.countMatches(keyno,"-");    
		if(count >0){ //2개가 아닌 경우, 특수문자로 인식
			if(count != 2) return keyno; //원본 리턴
			//배열에 left 0 채워서 저장
			List<String> keyList = new ArrayList<String>(Arrays.asList(keyno.split("-"))) ; 
			ddd =  keyList.get(0);
			prefix = keyList.get(1);
			number = keyList.get(2); 
			
			if(padtype.equals("R")) {
				if(ddd.equals("0200")) ddd = "0002";
				else if (ddd.compareTo("0310") >= 0 && ddd.compareTo("0640") <= 0 ) {
					ddd =  ddd.substring(0,3);
				}
			}
    	}
		else {
			boolean isNumberFlag = true;
	    	try {
	    		Long.valueOf(checkkeyno);
	    	} catch (Exception e) {
				isNumberFlag = false;
			}
	    	if(!isNumberFlag) return keyno; //특수문자 있으면 원본 리턴
	    	
	    	if(keyno.length() == 9){ 	//025551111    	
	    		ddd    = keyno.substring(0,2);
				prefix = keyno.substring(2,5);
				number = keyno.substring(5);				
				
	    	}
	    	else if(keyno.length() == 10 && keyno.substring(0,2).equals("02") ){ //0255551111    		    	
	    		ddd    = keyno.substring(0,2);
				prefix = keyno.substring(2,6);
				number = keyno.substring(6); 
	    	}
	    	else if(keyno.length() == 10 /*&& keyno.substring(0,3).compareTo("031") >= 0 && keyno.substring(0,3).compareTo("064") <= 0 */ ){ 
	    		//0315551111    		    	
	    		ddd    = keyno.substring(0,3);
				prefix = keyno.substring(3,6);
				number = keyno.substring(6); 
	    	}
	    	else if(keyno.length() == 11){ //03155551111    		    	
	    		ddd    = keyno.substring(0,3);
				prefix = keyno.substring(3,7);
				number = keyno.substring(7); 
	    	}
	    	else if(keyno.length() == 12){ 	//003155551111  , LG는 ddd Rpad  	    	
		    	ddd    = keyno.substring(0,4);
				prefix = keyno.substring(4,8);
				number = keyno.substring(8); 
	    	}
	    	else { 	//025551111    	
	    		return keyno; //9자리 보다 작으면 원본 리턴
	    	}
		}
		if(padtype.equals("R") && ddd.length() == 4 && keyno.length() == 12) {
			if(ddd.equals("0200")) ddd = "0002";
			else if (ddd.compareTo("0310") >= 0 && ddd.compareTo("0640") <= 0 && !ddd.substring(2,3).equals("0")) {
				ddd =  ddd.substring(0,3);
			}
			else if (ddd.substring(3).equals("0")) {
				ddd =  ddd.substring(0,3);
			}  
		}
		
		ddd    = StringUtils.leftPad(ddd    , 4, "0");
		prefix = StringUtils.leftPad(prefix , 4, "0");
		number = StringUtils.leftPad(number , 4, "0");
		result = ddd+prefix+number;
		
		if(padtype.equals("N")) {
			result = keyno;
		}
		
    	return result;
    }
	 
	
	
	//엑셀 숫자형 0제거 처리
	public static String fullnumberNumber(String keyno, String tcsn, String padtype ){ 
		String nkeyno = "0"+keyno;
    	return  fullnumber(nkeyno, tcsn, padtype); 
    }

	// 년월을 입력 받아서 한달전 년월 구하기
	public static String getOnemonthAgo(String billym){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(billym.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(billym.substring(4,6)) -1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH , -1);
		StringBuffer str = new StringBuffer();
		str.append(cal.get(Calendar.YEAR));
		
		//String month =  String.valueOf(cal.get(Calendar.MONTH)+1);
		if(String.valueOf(cal.get(Calendar.MONTH)+1).length() == 1){
			str.append("0" + String.valueOf(cal.get(Calendar.MONTH)+1));
			
		}else{
			str.append(String.valueOf(cal.get(Calendar.MONTH)+1));
		}
		return str.toString();
	}
}
