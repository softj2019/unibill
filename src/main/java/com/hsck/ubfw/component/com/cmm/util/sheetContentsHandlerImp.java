package com.hsck.ubfw.component.com.cmm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.transaction.PlatformTransactionManager;

import com.hsck.ubfw.component.content.service.ContentService;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public class sheetContentsHandlerImp implements SheetContentsHandler {
	private int rowCnt = 1;
	private int colCnt = 1;
	
	
	private int columnCnt;
	private HashMap<Integer,String> dbHeaderLoadList;
	private List updateDataList;
	private ContentService service;
	private HashMap parameterObject;
	private PlatformTransactionManager transactionManager;
	private String flag;
	
	
	
	// 컬럼 인서트 쿼리 생성 
	private List insertColumsList = new ArrayList();
	private List returnList = new ArrayList();
	private List insertDataList = new ArrayList();
	
	
	
	
	
	public sheetContentsHandlerImp(int columnCnt, HashMap<Integer,String> dbHeaderLoadList, List updateDataList, ContentService service,  HashMap parameterObject, PlatformTransactionManager transactionManager, String flag) {
		this.columnCnt = columnCnt;
		this.dbHeaderLoadList = dbHeaderLoadList;
		this.updateDataList = updateDataList;
		this.service = service;
		this.parameterObject = parameterObject;
		this.transactionManager =transactionManager;
		this.flag = flag;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cell(String columnNum, String value, XSSFComment arg2) {
		
		if(rowCnt > 3){
			colCnt++;
			
			if(colCnt < columnCnt ){
				insertColumsList.add(dbHeaderLoadList.get(colCnt-1));
				insertDataList.add(value);
				parameterObject.put(dbHeaderLoadList.get(colCnt-1), value);
				
			}else{
				insertColumsList.add(dbHeaderLoadList.get(colCnt-1));
				insertDataList.add(value);
				parameterObject.put(dbHeaderLoadList.get(colCnt-1), value);
				
				parameterObject.put("insertColumsList", insertColumsList);  // insert 컬럼 정보
				parameterObject.put("insertDataList", insertDataList);  	// insert 데이터 정보
				parameterObject.put("updateDataList", updateDataList);  	// update 데이터 정보

				
				insertColumsList.clear();
				insertDataList.clear();
			}
   				
		}
		
		
	}

	@Override
	public void endRow(int arg0) {
		rowCnt ++;
		colCnt = 0;
		
	}

	@Override
	public void headerFooter(String arg0, boolean arg1, String arg2) {
		
	}

	@Override
	public void startRow(int arg0) {
		
	}
	
	
	
	
	
	
}