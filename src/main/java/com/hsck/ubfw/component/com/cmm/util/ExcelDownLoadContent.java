package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.hsck.ubfw.component.content.service.ContentService;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ExcelDownLoadContent {
	
	public String filePaths     = "";
	
	public ExcelDownLoadContent() {
		try {
			InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
			Properties props = new Properties();
			props.load(in);
	
			if (filePaths == null || "".equals(filePaths)) {
				if(File.separator.equals("\\")){
					filePaths = props.getProperty("WINsystem.uploadpath");
				}else{
					filePaths = props.getProperty("system.uploadpath");
				}
						
			}
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	@SuppressWarnings({ "rawtypes", "resource" })
	@Autowired(required = false)
	public static String ExcelDownLoadMake(String file_name, String sub_path, List excel_title, List<EgovMap> data_list, HttpServletRequest request, HttpServletResponse response) throws Exception {

		file_name = file_name.replace("/", "");		
		
		String result    = "";

        Sheet worksheet = null;
        Row row = null;
       
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        worksheet = workbook.createSheet(file_name);
        
        // 1. 타이틀 셀 병합
        // 1.1. 타이틀
        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excel_title.size()-1));
        
        // 1.2. 출력일
        worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, excel_title.size()-1));
        
        // 2. font 객제
        // 2.1. 타이틀 Font 객체 생성
        Font font1 = workbook.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);     // 볼드체
        font1.setFontHeightInPoints((short)16);        // 폰트 크기
        font1.setUnderline(Font.U_SINGLE);		       // 밑줄

        // 2.2. 헤더 Font 객체 생성
        Font font2 = workbook.createFont();
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font2.setColor(IndexedColors.BLUE.getIndex());
        
        // 3. 스타일 지정
        // 3.1. 타이틀 스타일 지정
        CellStyle cs0 = workbook.createCellStyle();
        cs0.setAlignment(CellStyle.ALIGN_CENTER);
        cs0.setBorderTop(CellStyle.BORDER_THIN);
        cs0.setBorderLeft(CellStyle.BORDER_THIN);
        cs0.setBorderRight(CellStyle.BORDER_THIN);
//        cs0.setBorderBottom(CellStyle.BORDER_THIN);
        cs0.setFont(font1);
        cs0.setAlignment((short)2);                    // 가로 정렬 중간
        cs0.setVerticalAlignment((short)1);            // 세로 정렬 중단
        
        // 3.2. 출력일 스타일 지정
        CellStyle cs3 = workbook.createCellStyle();
        cs3.setAlignment(CellStyle.ALIGN_RIGHT);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        	
        // 3.3. 헤더 스타일 지정
        CellStyle cs1 = workbook.createCellStyle();
//        cs1.setFillForegroundColor(HSSFColor.YELLOW.index);
//        cs1.setFillBackgroundColor(HSSFColor.YELLOW.index);
//        cs1.setFillPattern(CellStyle.BIG_SPOTS);
        cs1.setAlignment(CellStyle.ALIGN_CENTER);
        cs1.setBorderTop(CellStyle.BORDER_THIN);
        cs1.setBorderLeft(CellStyle.BORDER_THIN);
        cs1.setBorderRight(CellStyle.BORDER_THIN);
        cs1.setBorderBottom(CellStyle.BORDER_THIN);
        cs1.setFont(font2);
        
        // 3.4. DATA 스타일 지정
        CellStyle cs2 = workbook.createCellStyle();
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);

        // 4. 데이터 생성
        Cell c;
        
        // 4.1. 타이틀 생성
        row = worksheet.createRow(0);
        row.setHeightInPoints(35);        
        for (int i=0; i<excel_title.size(); i++) {
        	c = row.createCell(i);
        	c.setCellStyle(cs0);    // 셀 스타일 적용
        	c.setCellValue(file_name);
        }
        
        // 4.2. 출력일 생성
        String printDay = DateUtil.getNowDateTimeString();
        row = worksheet.createRow(1);        
        for (int i=0; i<excel_title.size(); i++) {
        	c = row.createCell(i);
        	c.setCellStyle(cs3);    // 셀 스타일 적용
        	c.setCellValue("출력일 : " + printDay + " ");
        }

        // 4.3. 헤더 생성
        row = worksheet.createRow(2);
        for (int i=0; i<excel_title.size(); i++) {

        	c = row.createCell(i);
        	c.setCellStyle(cs1);    // 셀 스타일 적용
        	Map map = (Map) excel_title.get(i);
        	
//			c.setCellValue(map.get("objNm").toString()+"("+map.get("colId").toString()+")");	//타이틀에서 컬럼ID넣기(공통업로드시 사용)
			c.setCellValue(map.get("objNm").toString());
        }

        // 4.3. DATA 생성
        
        for(int i=0; i<excel_title.size(); i++){
        	//worksheet.autoSizeColumn(i);
        	worksheet.setColumnWidth(i, (worksheet.getColumnWidth(i))+2048 );
        }
        
        String tmp = "";
        for (int i=0; i<data_list.size(); i++) {

			row = worksheet.createRow(i+3);
			Map map = (Map) data_list.get(i);

			for( int column=0; column<excel_title.size(); column++) {

				Map mapExcel = (Map) excel_title.get(column);
				String enColumns = mapExcel.get("colId").toString();					

				enColumns = CamelUtil.convert2CamelCase(enColumns);
									
				tmp = ""+map.get( enColumns );
				if( "null".equals(tmp) )
					tmp = "";
				c = row.createCell(column);
				c.setCellStyle(cs2);    // 셀 스타일 적용
				c.setCellValue(tmp);
	        	
			}
        }

        ExcelDownLoadContent filePath = new ExcelDownLoadContent();
        String newfileName = file_name + "_" + FileMngUtil.newFileName() + ".xlsx";
        String uploadPath  = filePath.filePaths + File.separator + sub_path;
        String fullName    = uploadPath + File.separator + newfileName;
        
        
        File saveFolder = new File(uploadPath);
        
        if (!saveFolder.exists()) {
			saveFolder.mkdirs();		
		}
                
        // excel 파일 저장
        try {        	
            File xlsFile = new File(fullName);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workbook.write(fileOut);
//            workbook.close();

            result = newfileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = "false";
        } catch (IOException e) {
            e.printStackTrace();
            result = "false";
        }
        
        return result;
	}
	
	
	@SuppressWarnings({ "rawtypes", "resource" })
	@Autowired(required = false)
	public static String ExcelDownLoadMakeHandler(String file_name, String sub_path, List excel_title, HttpServletRequest request, HttpServletResponse response, HashMap parameterObject, String xmlId, ContentService contentService) throws Exception {

		ExcelDownLoadContent filePath = new ExcelDownLoadContent();
        String newfileName = file_name + "_" + FileMngUtil.newFileName() + ".xlsx";
        String uploadPath  = "";
        String fullName    = "";
        
        if(File.separator.equals("\\")){
          uploadPath  = filePath.filePaths + "\\" + sub_path;
          fullName    = uploadPath + "\\" + newfileName;
        }else{
          uploadPath  = filePath.filePaths + File.separator + sub_path;
          fullName    = uploadPath + File.separator + newfileName;
        }
        
        contentService.selectDataExcelHandler((Object) parameterObject, xmlId, excel_title, newfileName, uploadPath, fullName, file_name);
        
        
        
        return newfileName;
	}
	
	@SuppressWarnings({ "rawtypes", "resource" })
	@Autowired(required = false)
	public static String ExcelDownLoadMakeData(List excel_title, List<EgovMap> data_list,CellStyle cs2, String enColumns[],   Sheet worksheet , int fetchpos ) throws Exception {

		int i,j; 
		/*
		for(j=0; j<excel_title.size(); j++) {
			worksheet.autoSizeColumn(j);
			worksheet.setColumnWidth(j, (worksheet.getColumnWidth(j))+2048 );		
		}
		*/
 
        Map map=null;
        String tmp = "";
        String result = "";
        Row row = null;
        Cell c;
        for (i=0; i<data_list.size(); i++) {
			row = worksheet.createRow(i+3+fetchpos); 
			map = (Map) data_list.get(i);

			for(j=0; j<excel_title.size(); j++) { 
				tmp = ""+map.get( enColumns[j] );				
				if( "null".equals(tmp) )
					tmp = "";
				c = row.createCell(j);
				c.setCellStyle(cs2);    // 셀 스타일 적용
				c.setCellValue(tmp);
	        	
			}
        } 
     
        return result;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Autowired(required = false)
	public static String ExcelDownLoadMakeHead(String file_name, String sub_path, List excel_title,CellStyle cs2, String enColumns[],SXSSFWorkbook workbook, Sheet worksheet, HttpServletRequest request, HttpServletResponse response) throws Exception {

		 
		String result    = ""; 
        Row row = null;       
        
        // 1. 타이틀 셀 병합
        // 1.1. 타이틀
        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excel_title.size()-1));
        
        // 1.2. 출력일
        worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, excel_title.size()-1));
        
        // 2. font 객제
        // 2.1. 타이틀 Font 객체 생성
        Font font1 = workbook.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);     // 볼드체
        font1.setFontHeightInPoints((short)16);        // 폰트 크기
        font1.setUnderline(Font.U_SINGLE);		       // 밑줄

        // 2.2. 헤더 Font 객체 생성
        Font font2 = workbook.createFont();
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font2.setColor(IndexedColors.BLUE.getIndex());
        
        // 3. 스타일 지정
        // 3.1. 타이틀 스타일 지정
        CellStyle cs0 = workbook.createCellStyle();
        cs0.setAlignment(CellStyle.ALIGN_CENTER);
        cs0.setBorderTop(CellStyle.BORDER_THIN);
        cs0.setBorderLeft(CellStyle.BORDER_THIN);
        cs0.setBorderRight(CellStyle.BORDER_THIN);
//        cs0.setBorderBottom(CellStyle.BORDER_THIN);
        cs0.setFont(font1);
        cs0.setAlignment((short)2);                    // 가로 정렬 중간
        cs0.setVerticalAlignment((short)1);            // 세로 정렬 중단
        
        // 3.2. 출력일 스타일 지정
        CellStyle cs3 = workbook.createCellStyle();
        cs3.setAlignment(CellStyle.ALIGN_RIGHT);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        	
        // 3.3. 헤더 스타일 지정
        CellStyle cs1 = workbook.createCellStyle();
//        cs1.setFillForegroundColor(HSSFColor.YELLOW.index);
//        cs1.setFillBackgroundColor(HSSFColor.YELLOW.index);
//        cs1.setFillPattern(CellStyle.BIG_SPOTS);
        cs1.setAlignment(CellStyle.ALIGN_CENTER);
        cs1.setBorderTop(CellStyle.BORDER_THIN);
        cs1.setBorderLeft(CellStyle.BORDER_THIN);
        cs1.setBorderRight(CellStyle.BORDER_THIN);
        cs1.setBorderBottom(CellStyle.BORDER_THIN);
        cs1.setFont(font2);
        
       
        // 4. 데이터 생성
        Cell c;
        
        // 4.1. 타이틀 생성
        row = worksheet.createRow(0);
        row.setHeightInPoints(35);        
        for (int i=0; i<excel_title.size(); i++) {
        	c = row.createCell(i);
        	c.setCellStyle(cs0);    // 셀 스타일 적용
        	c.setCellValue(file_name);
        }
        
        // 4.2. 출력일 생성
        String printDay = DateUtil.getNowDateTimeString();
        row = worksheet.createRow(1);        
        for (int i=0; i<excel_title.size(); i++) {
        	c = row.createCell(i);
        	c.setCellStyle(cs3);    // 셀 스타일 적용
        	c.setCellValue("출력일 : " + printDay + " ");
        }

        // 4.3. 헤더 생성
        row = worksheet.createRow(2); 
        Map map=null;
        
        for (int i=0; i<excel_title.size(); i++) {

        	c = row.createCell(i);
        	c.setCellStyle(cs1);    // 셀 스타일 적용
        	map = (Map) excel_title.get(i);
        	
//			c.setCellValue(map.get("objNm").toString()+"("+map.get("colId").toString()+")");	//타이틀에서 컬럼ID넣기(공통업로드시 사용)
			c.setCellValue(map.get("objNm").toString());

			//data
			Map mapExcel = (Map) excel_title.get(i); 
			enColumns[i] = mapExcel.get("colId").toString();	
			enColumns[i] = CamelUtil.convert2CamelCase(enColumns[i]);	 
        }
         
         
        return result;
	}
	
	public static String ExcelDownLoadSend(String file_name, String sub_path 
			, SXSSFWorkbook workbook , HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        ExcelDownLoadContent filePath = new ExcelDownLoadContent();
        String newfileName = file_name + "_" + FileMngUtil.newFileName() + ".xlsx";
        String uploadPath  = filePath.filePaths + File.separator + sub_path;
        String fullName    = uploadPath + File.separator + newfileName;
        
        
        File saveFolder = new File(uploadPath);
        
        if (!saveFolder.exists()) {
			saveFolder.mkdirs();		
		}
         
        String result    = "";
        // excel 파일 저장
        try {        	
            File xlsFile = new File(fullName);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workbook.write(fileOut);
//            workbook.close();

            result = newfileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = "false";
        } catch (IOException e) {
            e.printStackTrace();
            result = "false";
        }
        
        return result;
	}
}