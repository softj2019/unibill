package com.hsck.ubfw.component.com.cmm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import egovframework.rte.psl.dataaccess.util.EgovMap;



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
public class FileRead {

	public static List<List<String>> datReadList(String filePath, String jiroFile, List<EgovMap> header, List<EgovMap> data, List<EgovMap> trailer) throws Exception {
   		List<List<String>> resultStr = new ArrayList<List<String>>();
   					
   		FileInputStream fis = new FileInputStream(filePath);		
   		BufferedReader bReader = null;
   		bReader = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
   			   		
   	 		int rowno = 0;
   	   		String temp = "";
   	   		int rt = 0;
   	   		StringBuffer str = new StringBuffer();
   	   		numberUtil numutil = new numberUtil();
   			   		
   	   		while(bReader.ready()){
   	   			while((temp = bReader.readLine() ) != null){
   	  				List<String> bLine = new ArrayList<String>();
   			   				
   	   				if(jiroFile.equals("csv")){
   	   					bLine = new ArrayList<String>(Arrays.asList( (temp.trim() + "," + rowno).split(",") ));
	   				}else if(StringUtils.isEmpty(jiroFile)){
   			   					
	   				}else{
	   					bLine = new ArrayList<String>(Arrays.asList( (temp.trim() + rowno).split(";") ));
	   				}
   			   				
	   				resultStr.add(bLine);
	   				rowno++;
   			   				
	   			}
	   		}
   			   		
	   		if(bReader != null){ bReader.close(); }
	   		if(fis != null){ fis.close(); }
   			   
	   		return resultStr;
	}			
   				
}
   			
   			
   			
    	
