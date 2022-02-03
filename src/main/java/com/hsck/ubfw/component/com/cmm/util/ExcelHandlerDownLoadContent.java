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

public class ExcelHandlerDownLoadContent implements ResultHandler {
	
	
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
				
				
				if(parm.get("scrin_code").toString().equals("S0077") || parm.get("scrin_code").toString().equals("S0082")){
					ch.setCellStyle(cs2h);    // 셀 스타일 적용
					ch.setCellType(ch.CELL_TYPE_STRING);
					ch.setCellValue(tmp);
				}else{
					if(enColumns.matches("^c[0-9]*$") || enColumns.endsWith("cost") || enColumns.endsWith("Cost") || enColumns.endsWith("cnt") || enColumns.endsWith("Cnt") || enColumns.endsWith("dur") || enColumns.endsWith("Dur") || enColumns.endsWith("dosu") || enColumns.endsWith("Dosu") || enColumns.endsWith("vat") || enColumns.endsWith("Vat")){
						
						if(tmp.indexOf(".") < 0){
							cs3h = workbook.createCellStyle();
							Font font5 = workbook.createFont();
					        
							font5.setFontName("맑은 고딕");
					        font5.setFontHeightInPoints((short)9);        // 폰트 크기
					        
							cs3h.setBorderTop(CellStyle.BORDER_THIN);
							cs3h.setBorderLeft(CellStyle.BORDER_THIN);
							cs3h.setBorderRight(CellStyle.BORDER_THIN);
							cs3h.setBorderBottom(CellStyle.BORDER_THIN);
							cs3h.setFont(font5);
							
							cs3h.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
							ch.setCellStyle(cs3h);    // 셀 스타일 적용
							
						}else{
							cs4h.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
							ch.setCellStyle(cs4h);    // 셀 스타일 적용
						}
						
						ch.setCellType(ch.CELL_TYPE_NUMERIC);
						if(tmp.replace(",", "").equals("")){tmp = "0";}
						ch.setCellValue(Double.parseDouble(tmp.replace(",", "")));
						
					}else{
						ch.setCellStyle(cs2h);    // 셀 스타일 적용
						ch.setCellType(ch.CELL_TYPE_STRING);
						ch.setCellValue(tmp);
					}
				}
				
				
		        	
			}
			rowcnt++;
			
			
			parm.put("lastCnt", rowcnt+5);
			
	}
	
	
}