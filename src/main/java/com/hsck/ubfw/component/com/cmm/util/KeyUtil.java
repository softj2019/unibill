package com.hsck.ubfw.component.com.cmm.util;

import java.util.HashMap;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hsck.ubfw.component.content.service.ContentService;
	
public class KeyUtil {
	
	public static String getUbseq(String tbl_nm, String col_nm) throws Exception {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		parmObj.put("tbl_nm", tbl_nm);
		parmObj.put("col_nm", col_nm);
				
		return service.getUbseqGen(parmObj);
	}
	
	
	public static String getUbseqCnt(String tbl_nm, String col_nm, String seq_cnt) throws Exception {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		parmObj.put("tbl_nm", tbl_nm);
		parmObj.put("col_nm", col_nm);
		parmObj.put("seq_cnt", seq_cnt);
				
		return service.getUbseqGen(parmObj);
	}
	
	
	public static String getSeqStr(String key_nm) throws Exception {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		parmObj.put("key_nm", key_nm); 
				
		return service.getSeqStrGen(parmObj);
	}
	 
	public static String getCustId(String custType) throws Exception {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		parmObj.put("custType", custType); 
				
		return service.getCustIdGen(parmObj);
	}
}