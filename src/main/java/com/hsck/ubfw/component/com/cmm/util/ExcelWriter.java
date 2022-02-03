package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
		
	public String filePaths = "";
	
	public ExcelWriter() {
		try {
			InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
			Properties props = new Properties();
			props.load(in);
			
			if (filePaths == null || "".equals(filePaths)) {
				filePaths = props.getProperty("system.uploadpath");		
			}
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public static void excelWriter(String file_name, String fileExt, String subPath, String excelHeader, String errorData) throws IOException {
		
		ExcelWriter filePath = new ExcelWriter();
		
		String full_name = filePath.filePaths + "/" + subPath + "/" + file_name;		
		
		// Workbook 생성
		Workbook xlsWb = null;
		
        // XLSX 파일 일 경우
    	if ("xlsx".equals(fileExt)) {
    		xlsWb = new XSSFWorkbook(); // Excel 2007 이상
    	// XLS 파일 일 경우
    	} else if ("xls".equals(fileExt)) {
    		xlsWb = new HSSFWorkbook();  // Excel 2007 이전 버전
    	}
    		
        // *** Sheet-------------------------------------------------
        // Sheet 생성
        Sheet sheet1 = xlsWb.createSheet("Sheet0");
 	
        // 컬럼 너비 설정
//        sheet1.setColumnWidth(0, 10000);
//        sheet1.setColumnWidth(9, 10000);
        // ----------------------------------------------------------
         
        // *** Style--------------------------------------------------
        // Cell 스타일 생성
        CellStyle cellStyle = xlsWb.createCellStyle();
/*         
        // 줄 바꿈
        cellStyle.setWrapText(true);
         
        // Cell 색깔, 무늬 채우기
        cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
        cellStyle.setFillPattern(CellStyle.BIG_SPOTS);
*/         
        Row row = null;
        Cell cell = null;
        //----------------------------------------------------------
         
        // 첫번째 줄 헤더
        row = sheet1.createRow(0);        
        String [] header = excelHeader.split("@");
        
        for (int i=0; i<header.length; i++) {
        	cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle); // 셀 스타일 적용
        }
/*         
        // 첫 번째 줄에 Cell 설정하기-------------
        cell = row.createCell(0);
        cell.setCellValue("1-1");
        cell.setCellStyle(cellStyle); // 셀 스타일 적용
         
        cell = row.createCell(1);
        cell.setCellValue("1-2");
         
        cell = row.createCell(2);
        cell.setCellValue("1-3 abccdefghijklmnopqrstuvwxyz");
        cell.setCellStyle(cellStyle); // 셀 스타일 적용
        //---------------------------------
*/         
        // 두번째 줄부터 에러 데이터        
        String [] errData = errorData.split("#"); 
        
        for (int i=0; i<errData.length; i++) {
        	row = sheet1.createRow(i+1);
        	
        	String[] data = errData[i].split("@");
        	for (int j=0; j<data.length; j++) {
        		cell = row.createCell(j);
	            cell.setCellValue(data[j]);
        	}
        }
        
/*        
        // 두 번째 줄에 Cell 설정하기-------------
        cell = row.createCell(0);
        cell.setCellValue("2-1");
         
        cell = row.createCell(1);
        cell.setCellValue("2-2");
         
        cell = row.createCell(2);
        cell.setCellValue("2-3");
        cell.setCellStyle(cellStyle); // 셀 스타일 적용
        //---------------------------------
*/        	
		// excel 파일 저장
        try {
            File xlsFile = new File(full_name);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsWb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}