package com.hsck.ubfw.component.com.cmm.util;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class SqlExcelHandlerDownLoadContent implements ResultHandler {
	
	
    private Row rowh = null;
    private Sheet worksheet = null;
    private CellStyle cs2h = null;
    private CellStyle cs3h = null;
    private CellStyle cs4h = null;
    private Cell ch = null;
    private List excel_titleh = null;
    private String newfileName = null;
    private String uploadPath = null;
    private String fullName = null; 
    private SXSSFWorkbook workbook = null;
    private Object parameter = null;
    private int rowcnt = 0;
    
    
    public void ExcelHandlerDownLoadContent(Sheet worksheet, CellStyle cs2, Cell c, List excel_title, String newfileName, String uploadPath, String fullName, SXSSFWorkbook workbook, Object parameter){
    	this.worksheet = worksheet;
    	this.cs2h = cs2;
    	//this.cs3h = cs2;
    	this.cs4h = cs2;
    	this.ch = c;
    	this.excel_titleh = excel_title;
    	this.newfileName = newfileName;
    	this.uploadPath = uploadPath; 
    	this.fullName = fullName;
    	this.workbook = workbook;
    	this.parameter = parameter;
    	
    };
    
    

	@Override
	public void handleResult(ResultContext context) {
		HashMap parm = (HashMap)parameter;
		EgovMap rs = (EgovMap)context.getResultObject();
		String tmp = "";
		
			rowh = worksheet.createRow(rowcnt+5);
			Map map = (Map) rs;
			
			for( int column=0; column<excel_titleh.size(); column++) {
				Map mapExcel = (Map) excel_titleh.get(column);
				String enColumns = mapExcel.get("colId").toString();					
				enColumns = CamelUtil.convert2CamelCase(enColumns);
				tmp = ""+map.get( enColumns );
				if( "null".equals(tmp) )
					tmp = "";
				ch = rowh.createCell(column);
				
				ch.setCellStyle(cs2h);    // 셀 스타일 적용
				ch.setCellType(ch.CELL_TYPE_STRING);
				ch.setCellValue(tmp);
				
				
		        	
			}
			rowcnt++;
			
			
			parm.put("lastCnt", rowcnt+5);
			
	}
	
	
}