package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CreateTemplete {
	// log print를 위한 선언
	private static final Logger log = LoggerFactory.getLogger(ContentUtil.class);
		
	int sheetcnt = 0;
	int[] sheetType;
	String[] xmlids = null;
	String fullPath = "";
	String fullOutPath = "";
	String fileNm = "";
	int fixcnt = 0;
	
	public CreateTemplete(String fullPath, String fullOutPath) {
		this.fullPath = fullPath;
		this.fullOutPath = fullOutPath;
	}
	public CreateTemplete(int sheetcnt, int[] sheetType, String fileNm, String fullPath, String fullOutPath) {
		this.sheetcnt = sheetcnt;
		for(int i=0; i<sheetcnt; i++) {
			this.sheetType[i] = sheetType[i];
		}
		this.fileNm = fileNm;
		this.fullPath = fullPath;
		this.fullOutPath = fullOutPath;
	}
	
	public void setSheetCnt(int sheetcnt) {
		this.sheetcnt = sheetcnt;
	}
	
	public void setSheetType(int[] sheetType) {
		this.sheetType = sheetType;
	}
	public void setXmlId(String[] xmlids) {
		this.xmlids = xmlids;
	}
	
	public void setFileName(String fileNm) {
		this.fileNm = fileNm;
	}
	
	public void setFixcell(int fixcnt) {
		this.fixcnt = fixcnt;
	}
	
	public void transData(XSSFWorkbook wb, int sheetNum, int sheetType) {
		int rowindex = 0;
		int columnindex = 0;
		int cells =0;
		XSSFSheet worksheet=((XSSFWorkbook)wb).getSheetAt(sheetNum);
		//행 갯수
		int rowcnt = worksheet.getLastRowNum()+1;
		
		for(rowindex=0; rowindex<rowcnt; rowindex++){
			//행을읽는다
    		XSSFRow row= worksheet.getRow(rowindex);
    		if(row !=null){
    			//컬럼 갯수
    			cells = row.getLastCellNum();
    			for(columnindex=0; columnindex<cells; columnindex++){
    				
    			}
    		}
		}
	}
	
	
}