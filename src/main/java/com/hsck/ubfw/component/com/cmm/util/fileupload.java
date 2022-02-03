package com.hsck.ubfw.component.com.cmm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class fileupload {

	public List<List<String>> datReadList(String filePath, String excelFile, Object info) throws Exception {
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
   			   				
   	   				if(excelFile.equals("csv")){
   	   					bLine = new ArrayList<String>(Arrays.asList( (temp.trim() + "," + rowno).split(",") ));
	   				}else if(StringUtils.isEmpty(excelFile)){
   			   					
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
   			
   			
   			
    	
