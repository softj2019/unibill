/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsck.ubfw.component.com.cmm.dao;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hsck.ubfw.component.com.cmm.util.DateUtil;
import com.hsck.ubfw.component.com.cmm.util.ExcelHandlerDownLoadContent;
import com.hsck.ubfw.component.com.cmm.util.PdfUtil;
import com.hsck.ubfw.component.com.cmm.util.SqlExcelHandlerDownLoadContent;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : SampleDAO.java
 * @Description : Sample DAO Class
 * @Modification Information
 * @
 * @  ?????????      ?????????              ????????????
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           ????????????
 *
 * @author ????????????????????? ???????????? ?????????
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Repository("commonMapper")
public class CommonMapper extends EgovAbstractMapper {
	//private final Logger log = Logger.getLogger(this.getClass()); //log4j
	private final Logger log = LoggerFactory.getLogger(this.getClass()); //logback
	
	@Resource(name="pdfUtil")
    private PdfUtil pdfUtil;

	
	// ?????? ?????????
	@SuppressWarnings({ "rawtypes" })
	public String GetSql(String sqlMapId, Map<String, Object> paramMap ) {
		SqlSession sqlSession = getSqlSession();
		String sql = sqlSession.getConfiguration().getMappedStatement(sqlMapId).getBoundSql(paramMap).getSql();
        List<ParameterMapping> parameterMappings = sqlSession.getConfiguration().getMappedStatement(sqlMapId).getBoundSql(paramMap).getParameterMappings();
        
        for (ParameterMapping parameterMapping : parameterMappings) {
            String param = (String) paramMap.get(parameterMapping.getProperty()).toString();
            sql = sql.replaceFirst("\\?", "'" + param + "'");
        }
 
        
			return sql;
		}
		
		
	// ?????? ?????????
	@SuppressWarnings({ "rawtypes" })
	public List getSelectList(String sqlMapId, Object parameter) {
		List list;

		try {
			long startTime = System.currentTimeMillis();
			//pjh
		//	String sql =GetSql(sqlMapId,  parameter); 
			// log.debug("getSelectList> TTTTTTT ="+sqlMapId + " sql="+sql);
			
			list = selectList(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return list;
	}
	
	public void executeHandler(String sqlMapId, Object parameter, ResultHandler handler){
		getSqlSession().select(sqlMapId, parameter, handler);
	}
	
	public void getSelectListHandler(String sqlMapId, Object parameter, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) {

        try {
			long startTime = System.currentTimeMillis();
			
			
			file_name = file_name.replace("/", "");		
			
			String result    = "";

	        Row row = null;
	       
	        @SuppressWarnings("resource")
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        final SXSSFSheet[] sheets = new SXSSFSheet[4];

	        
	        
	        
	        // 2. font ??????
	        // 2.1. ????????? Font ?????? ??????
	        Font font1 = workbook.createFont();
	        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);     // ?????????
	        font1.setFontHeightInPoints((short)16);        // ?????? ??????
	        font1.setUnderline(Font.U_SINGLE);		       // ??????

	        // 2.2. ?????? Font ?????? ??????
	        Font font2 = workbook.createFont();
	        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        font2.setColor(IndexedColors.BLACK.getIndex());
	        font2.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 2.3. ?????? ?????? Font ?????? ??????
	        Font font3 = workbook.createFont();
	        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        font3.setColor(IndexedColors.BLACK.getIndex());
	        font3.setFontHeightInPoints((short)11);        // ?????? ??????
	        
	        Font font4 = workbook.createFont();
	        font4.setColor(IndexedColors.RED.getIndex());
	        font4.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 2.4 DATA Font ?????? ??????
	        Font font5 = workbook.createFont();
	        font5.setFontName("?????? ??????");
	        font5.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 3. ????????? ??????
	        // 3.1. ????????? ????????? ??????
	        XSSFCellStyle cs0 = (XSSFCellStyle)workbook.createCellStyle();
	        cs0.setAlignment(CellStyle.ALIGN_CENTER);
	        cs0.setBorderTop(CellStyle.BORDER_THIN);
	        cs0.setBorderLeft(CellStyle.BORDER_THIN);
	        cs0.setBorderRight(CellStyle.BORDER_THIN);
//	        cs0.setBorderBottom(CellStyle.BORDER_THIN);
	        cs0.setFont(font1);
	        cs0.setAlignment((short)2);                    // ?????? ?????? ??????
	        cs0.setVerticalAlignment((short)1);            // ?????? ?????? ??????
	        
	        // 3.2. ????????? ????????? ??????
	        XSSFCellStyle cs3 = (XSSFCellStyle)workbook.createCellStyle();
	        cs3.setAlignment(CellStyle.ALIGN_RIGHT);
	        cs3.setBorderLeft(CellStyle.BORDER_THIN);
	        cs3.setBorderRight(CellStyle.BORDER_THIN);
	        
	        // ?????? ????????? ??????
	        XSSFCellStyle cs4 = (XSSFCellStyle)workbook.createCellStyle();
	        cs4.setFont(font3);
	        cs4.setAlignment((short)2);                    // ?????? ?????? ??????
	        cs4.setVerticalAlignment((short)1);            // ?????? ?????? ??????
	        cs4.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        cs4.setBorderTop(CellStyle.BORDER_THIN);
	        cs4.setBorderLeft(CellStyle.BORDER_THIN);
	        cs4.setBorderRight(CellStyle.BORDER_THIN);
	        cs4.setBorderBottom(CellStyle.BORDER_THIN);
	        	
	        // 3.3. ?????? ????????? ??????
	        XSSFCellStyle cs1c = (XSSFCellStyle)workbook.createCellStyle();
//	        cs1.setFillForegroundColor(HSSFColor.YELLOW.index);
//	        cs1.setFillBackgroundColor(HSSFColor.YELLOW.index);
//	        cs1.setFillPattern(CellStyle.BIG_SPOTS);
	        cs1c.setAlignment(CellStyle.ALIGN_CENTER);
	        cs1c.setBorderTop(CellStyle.BORDER_THIN);
	        cs1c.setBorderLeft(CellStyle.BORDER_THIN);
	        cs1c.setBorderRight(CellStyle.BORDER_THIN);
	        cs1c.setBorderBottom(CellStyle.BORDER_THIN);
	        cs1c.setFont(font2);
	        cs1c.setVerticalAlignment((short)1);            						// ?????? ?????? ??????
	        cs1c.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs1c.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        
	        XSSFCellStyle cs1r = (XSSFCellStyle)workbook.createCellStyle();
	        cs1r.setAlignment(CellStyle.ALIGN_RIGHT);
	        cs1r.setBorderTop(CellStyle.BORDER_THIN);
	        cs1r.setBorderLeft(CellStyle.BORDER_THIN);
	        cs1r.setBorderRight(CellStyle.BORDER_THIN);
	        cs1r.setBorderBottom(CellStyle.BORDER_THIN);
	        cs1r.setFont(font2);
	        cs1r.setVerticalAlignment((short)1);            						// ?????? ?????? ??????
	        cs1r.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs1r.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        // 3.4. DATA ????????? ??????
	        CellStyle cs2 = workbook.createCellStyle();
	        cs2.setBorderTop(CellStyle.BORDER_THIN);
	        cs2.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2.setBorderRight(CellStyle.BORDER_THIN);
	        cs2.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2.setFont(font5);

	        // 4. ????????? ??????
	        Cell c = null;
	        HashMap parm = (HashMap)parameter;
	        String printDay = DateUtil.getNowDateTimeString();
	        
	        
	        if(parm.get("sheet1") != null){
	        	 // ?????? ?????? ??????
		        Row row1 = null;
		        
		        final Sheet worksheet1 = workbook.createSheet("??????????????????");
		        List<EgovMap> sheet1List= new ArrayList<EgovMap>();
		        sheet1List = (List<EgovMap>) parm.get("sheet1");
		        
		        if(sheet1List.size() == 0){
		        	 // 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
			        
			        // 1.2. ?????????
			        worksheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
			        
			        // 4.1. ????????? ??????
			        row1 = worksheet1.createRow(0);
			        row1.setHeightInPoints(35);        
			        for (int i=0; i<10; i++) {
			        	c = row1.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row1 = worksheet1.createRow(1);        
			        for (int i=0; i<10; i++) {
			        	c = row1.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
		        }else{
		        	Set key= sheet1List.get(0).keySet();
			        List header1 = new ArrayList();
			    
			        for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			        	String keyNm = (String)iterator.next();	
			        	header1.add(keyNm);
			    	}
			        
			        // 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, header1.size()-1));
			        
			        // 1.2. ?????????
			        worksheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, header1.size()-1));
			        
			        // 4.1. ????????? ??????
			        row1 = worksheet1.createRow(0);
			        row1.setHeightInPoints(35);        
			        for (int i=0; i<header1.size(); i++) {
			        	c = row1.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row1 = worksheet1.createRow(1);        
			        for (int i=0; i<header1.size(); i++) {
			        	c = row1.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
			        
			        // 5.1 ?????? ??????
			        row1 = worksheet1.createRow(4);
			        row1.setHeightInPoints(20); 
			        for (int i=0; i<header1.size(); i++) {
			        	c = row1.createCell(i);
			        	c.setCellStyle(cs1c);    // ??? ????????? ??????
						c.setCellValue(header1.get(i).toString());
			        }
			        
			        // 5.2 ????????? ??????
			        for(int i=0; i<sheet1List.size(); i++){
			        	row1 = worksheet1.createRow(i+5);
			        	EgovMap map = new EgovMap();
			        	map = sheet1List.get(i);
			        	
			        	for (int j=0; j<header1.size(); j++) {
			        		String tmp= map.get(header1.get(j)).toString();
			        		
			        		if( ((String)header1.get(j)).endsWith("??????") || ((String)header1.get(j)).endsWith("???") || ((String)header1.get(j)).endsWith("????????????") || ((String)header1.get(j)).endsWith("???") || ((String)header1.get(j)).endsWith("???") || ((String)header1.get(j)).endsWith("???") || ((String)header1.get(j)).endsWith("???")){
								c = row1.createCell(j);
				 	        	c.setCellType(c.CELL_TYPE_NUMERIC);
				 	        	cs2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
				 	        	c.setCellStyle(cs2);    // ??? ????????? ??????
				 	        	c.setCellValue(Long.parseLong(tmp.replace(",", "")));
				 	        	
				 	        	if(tmp.replace(",", "").equals("")){tmp = "0";}
								c.setCellValue(Long.parseLong(tmp.replace(",", "")));
							}else{
								c = row1.createCell(j);
								c.setCellType(c.CELL_TYPE_STRING);
								c.setCellStyle(cs2);    // ??? ????????? ??????
		
								c.setCellValue(tmp);
							}
			        		
				        }
			        	
			        }
		        }
		        
		        Row row2 = null;
		        final Sheet worksheet2 = workbook.createSheet("??????????????? ????????????");
		        
		        List<EgovMap> sheet2List= new ArrayList<EgovMap>();
		        sheet2List = (List<EgovMap>) parm.get("sheet2");
		        
		        if(sheet2List.size() == 0){
		        	// 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
			        
			        // 1.2. ?????????
			        worksheet2.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
			        
			        // 4.1. ????????? ??????
			        row2 = worksheet2.createRow(0);
			        row2.setHeightInPoints(35);        
			        for (int i=0; i<10; i++) {
			        	c = row2.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????? ????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row2 = worksheet2.createRow(1);        
			        for (int i=0; i<10; i++) {
			        	c = row2.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
		        }else{
		        	Set key2 = sheet2List.get(0).keySet();
			        List header2 = new ArrayList();
			        for (Iterator iterator = key2.iterator(); iterator.hasNext();) {
			        	String keyNm = (String)iterator.next();	
			        	header2.add(keyNm);
			    	}
			        
			        // 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, header2.size()-1));
			        
			        // 1.2. ?????????
			        worksheet2.addMergedRegion(new CellRangeAddress(1, 1, 0, header2.size()-1));
			        
			        // 4.1. ????????? ??????
			        row2 = worksheet2.createRow(0);
			        row2.setHeightInPoints(35);        
			        for (int i=0; i<header2.size(); i++) {
			        	c = row2.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????? ????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row2 = worksheet2.createRow(1);        
			        for (int i=0; i<header2.size(); i++) {
			        	c = row2.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
			        
			        // 5.1 ?????? ??????
			        row2 = worksheet2.createRow(4);
			        row2.setHeightInPoints(20); 
			        for (int i=0; i<header2.size(); i++) {
		
			        	c = row2.createCell(i);
			        	c.setCellStyle(cs1c);    // ??? ????????? ??????
						c.setCellValue(header2.get(i).toString());
			        }
			        
			        // 5.2 ????????? ??????
			        for(int i=0; i<sheet2List.size(); i++){
			        	row2 = worksheet2.createRow(i+5);
			        	EgovMap map = new EgovMap();
			        	map = sheet2List.get(i);
			        	
			        	for (int j=0; j<header2.size(); j++) {
			        		String tmp= map.get(header2.get(j)).toString();
			        		
			        		if( ((String)header2.get(j)).endsWith("??????") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("????????????") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???")){
								c = row2.createCell(j);
				 	        	c.setCellType(c.CELL_TYPE_NUMERIC);
				 	        	cs2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
				 	        	c.setCellStyle(cs2);    // ??? ????????? ??????
				 	        	c.setCellValue(Long.parseLong(tmp.replace(",", "")));
				 	        	
				 	        	if(tmp.replace(",", "").equals("")){tmp = "0";}
								c.setCellValue(Long.parseLong(tmp.replace(",", "")));
							}else if(((String)header2.get(j)).endsWith("?????????")){
								c = row2.createCell(j);
								c.setCellType(c.CELL_TYPE_STRING);
								c.setCellStyle(cs2);    // ??? ????????? ??????
								
								c.setCellValue(tmp.substring(5));
								
							}else{
								c = row2.createCell(j);
								c.setCellType(c.CELL_TYPE_STRING);
								c.setCellStyle(cs2);    // ??? ????????? ??????
		
								c.setCellValue(tmp);
							}
			        		
				        }
			        	
			        }
		        }
		        
		        Row row3 = null;
		        final Sheet worksheet3 = workbook.createSheet("??????????????? ????????????");
		        
		        List<EgovMap> sheet3List= new ArrayList<EgovMap>();
		        sheet3List = (List<EgovMap>) parm.get("sheet3");
		        
		        if(sheet3List.size() == 0){
		        	// 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet3.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
			        
			        // 1.2. ?????????
			        worksheet3.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
			        
			        // 4.1. ????????? ??????
			        row3 = worksheet3.createRow(0);
			        row3.setHeightInPoints(35);        
			        for (int i=0; i<10; i++) {
			        	c = row3.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????? ????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row3 = worksheet3.createRow(1);        
			        for (int i=0; i<10; i++) {
			        	c = row3.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
		        }else{
		        	Set key2 = sheet3List.get(0).keySet();
			        List header2 = new ArrayList();
			        for (Iterator iterator = key2.iterator(); iterator.hasNext();) {
			        	String keyNm = (String)iterator.next();	
			        	header2.add(keyNm);
			    	}
			        
			        // 1. ????????? ??? ??????
			        // 1.1. ?????????
			        worksheet3.addMergedRegion(new CellRangeAddress(0, 0, 0, header2.size()-1));
			        
			        // 1.2. ?????????
			        worksheet3.addMergedRegion(new CellRangeAddress(1, 1, 0, header2.size()-1));
			        
			        // 4.1. ????????? ??????
			        row3 = worksheet3.createRow(0);
			        row3.setHeightInPoints(35);        
			        for (int i=0; i<header2.size(); i++) {
			        	c = row3.createCell(i);
			        	c.setCellStyle(cs0);    // ??? ????????? ??????
			        	c.setCellValue("??????????????? ????????????");
			        }
			        
			        // 4.2. ????????? ??????
			        row3 = worksheet3.createRow(1);        
			        for (int i=0; i<header2.size(); i++) {
			        	c = row3.createCell(i);
			        	c.setCellStyle(cs3);    // ??? ????????? ??????
			        	c.setCellValue("????????? : " + printDay + " ");
			        }
			        
			        // 5.1 ?????? ??????
			        row3 = worksheet3.createRow(4);
			        row3.setHeightInPoints(20); 
			        for (int i=0; i<header2.size(); i++) {
		
			        	c = row3.createCell(i);
			        	c.setCellStyle(cs1c);    // ??? ????????? ??????
						c.setCellValue(header2.get(i).toString());
			        }
			        
			        // 5.2 ????????? ??????
			        for(int i=0; i<sheet3List.size(); i++){
			        	row3 = worksheet3.createRow(i+5);
			        	EgovMap map = new EgovMap();
			        	map = sheet3List.get(i);
			        	
			        	for (int j=0; j<header2.size(); j++) {
			        		String tmp= map.get(header2.get(j)).toString();
			        		
			        		if( ((String)header2.get(j)).endsWith("??????") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("????????????") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???") || ((String)header2.get(j)).endsWith("???")){
								c = row3.createCell(j);
				 	        	c.setCellType(c.CELL_TYPE_NUMERIC);
				 	        	cs2.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
				 	        	c.setCellStyle(cs2);    // ??? ????????? ??????
				 	        	c.setCellValue(Long.parseLong(tmp.replace(",", "")));
				 	        	
				 	        	if(tmp.replace(",", "").equals("")){tmp = "0";}
								c.setCellValue(Long.parseLong(tmp.replace(",", "")));
							}else if(((String)header2.get(j)).endsWith("?????????")){
								c = row3.createCell(j);
								c.setCellType(c.CELL_TYPE_STRING);
								c.setCellStyle(cs2);    // ??? ????????? ??????
								
								c.setCellValue(tmp.substring(5));
								
							}else{
								c = row3.createCell(j);
								c.setCellType(c.CELL_TYPE_STRING);
								c.setCellStyle(cs2);    // ??? ????????? ??????
		
								c.setCellValue(tmp);
							}
			        		
				        }
			        	
			        }
		        }
		        // ???????????? ??????
		        sheets[0] =  (SXSSFSheet) worksheet1;
		        sheets[1] =  (SXSSFSheet) worksheet2;
		        sheets[2] =  (SXSSFSheet) worksheet3;
	        }
	        
	        if(parm.get("sheet1") != null){
	        	file_name = "???????????? ??????????????? ????????????";
	        }
	        
	        final Sheet worksheet = workbook.createSheet(file_name);
	        
	        // 1. ????????? ??? ??????
	        // 1.1. ?????????
	        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excel_title.size()-1));
	        
	        // 1.2. ?????????
	        worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, excel_title.size()-1));
	     
	        row = worksheet.createRow(0);
	        row.setHeightInPoints(35);        
	        for (int i=0; i<excel_title.size(); i++) {
	        	c = row.createCell(i);
	        	c.setCellStyle(cs0);    // ??? ????????? ??????
	        	c.setCellValue(file_name);
	        }
	        
	        // 4.2. ????????? ??????
	        row = worksheet.createRow(1);        
	        for (int i=0; i<excel_title.size(); i++) {
	        	c = row.createCell(i);
	        	c.setCellStyle(cs3);    // ??? ????????? ??????
	        	c.setCellValue("????????? : " + printDay + " ");
	        }
	        
	        // ?????? ??????
	       
	        if(parm.get("sumYn").equals("Y")){
	        	row = worksheet.createRow(2);
	        	Map dmap = ((List<EgovMap>)parm.get("selectDataSumList")).get(0);
	        	
	        	int ccnt = 0;
	        	for(int i=0; i<((List)parm.get("selectHdSumList")).size(); i++){
	        		Map hmap = ((List<EgovMap>)parm.get("selectHdSumList")).get(i);
	        		
	        		c = row.createCell(ccnt);
		        	c.setCellStyle(cs4);
		        	c.setCellValue(hmap.get("scolNm").toString());
		        	ccnt ++;
		        		
		        	c = row.createCell(ccnt);
		        	c.setCellStyle(cs4);
		        	c.setCellValue(dmap.get(hmap.get("scolId")).toString());
		        	ccnt ++;
	        		
//	        		c = row.createCell(i);
//	        		c.setCellStyle(cs4);
//	        		c.setCellValue(hmap.get("scolNm").toString() + " : " + dmap.get(hmap.get("scolId")));
	        		
	        		//String val = hmap.get("scolNm").toString() + " : " + dmap.get(hmap.get("scolId"));
	        		//String val = "aaaaaaaaaaaaaaaaaaaaa";
	        		
	        		//HSSFRichTextString richString = new HSSFRichTextString( val );
	        		//richString.applyFont(0, val.indexOf(":"), IndexedColors.BLACK.getIndex());
	        		//richString.applyFont(val.indexOf(":"), val.length()-1, IndexedColors.RED.getIndex());
	        		
	        		//c.setCellValue(richString);
	        		
	        	}
	        }
	        
	        
	        

	        // 4.3. ?????? ??????
	        row = worksheet.createRow(4);
	        row.setHeightInPoints(20); 
	        for (int i=0; i<excel_title.size(); i++) {

	        	c = row.createCell(i);
	        	c.setCellStyle(cs1c);    // ??? ????????? ??????
	        	Map map = (Map) excel_title.get(i);
	        	
//				c.setCellValue(map.get("objNm").toString()+"("+map.get("colId").toString()+")");	//??????????????? ??????ID??????(?????????????????? ??????)
				c.setCellValue(map.get("objNm").toString());
	        }

	        // 4.3. DATA ??????
	        
	        for(int i=0; i<excel_title.size(); i++){
	        	//worksheet.autoSizeColumn(i);
	        	worksheet.setColumnWidth(i, (worksheet.getColumnWidth(i))+2048 );
	        }
	        
//	        ExcelDownLoadContent filePath = new ExcelDownLoadContent();
//	        String newfileName = file_name + "_" + FileMngUtil.newFileName() + ".xlsx";
//	        String uploadPath  = filePath.filePaths + File.separator + sub_path;
//	        String fullName    = uploadPath + File.separator + newfileName;
	        
	        File saveFolder = new File(uploadPath);
	        
	        if (!saveFolder.exists()) {
				saveFolder.mkdirs();		
			}
	        
	        ExcelHandlerDownLoadContent handler = new ExcelHandlerDownLoadContent();
	        handler.ExcelHandlerDownLoadContent(worksheet, cs2, c, excel_title, newfileName, uploadPath, fullName, workbook, parameter);
	        
	        getSqlSession().select(sqlMapId, parameter, handler);
	        
	        String lastCnt = StringUtil.isNullToString(((HashMap)parameter).get("lastCnt"));
	        
	        // ?????? fomula ??????
//	        if(parm.get("sumYn").equals("Y") && StringUtils.isNotEmpty(lastCnt)){
//	        	 row = worksheet.createRow((int)((HashMap)parameter).get("lastCnt"));
//	        	 for (int i=0; i<excel_title.size(); i++) {
//	        		String subAlpabet = "";
//	 	        	switch (i%26) {
//	 	        	case 0:
//	 	        		subAlpabet = "A";
//	 	        		break;
//	 	        	case 1:
//	 	        		subAlpabet = "B";
//						break;
//					case 2:
//						subAlpabet = "C";
//						break;
//					case 3:
//						subAlpabet = "D";
//						break;
//					case 4:
//						subAlpabet = "E";
//						break;
//					case 5:
//						subAlpabet = "F";
//						break;
//					case 6:
//						subAlpabet = "G";
//						break;
//					case 7:
//						subAlpabet = "H";
//						break;
//					case 8:
//						subAlpabet = "I";
//						break;
//					case 9:
//						subAlpabet = "J";
//						break;
//					case 10:
//						subAlpabet = "K";
//						break;
//					case 11:
//						subAlpabet = "L";
//						break;
//					case 12:
//						subAlpabet = "M";
//						break;
//					case 13:
//						subAlpabet = "N";
//						break;
//					case 14:
//						subAlpabet = "O";
//						break;
//					case 15:
//						subAlpabet = "P";
//						break;
//					case 16:
//						subAlpabet = "Q";
//						break;
//					case 17:
//						subAlpabet = "R";
//						break;
//					case 18:
//						subAlpabet = "S";
//						break;
//					case 19:
//						subAlpabet = "T";
//						break;
//					case 20:
//						subAlpabet = "U";
//						break;
//					case 21:
//						subAlpabet = "V";
//						break;
//					case 22:
//						subAlpabet = "W";
//						break;
//					case 23:
//						subAlpabet = "X";
//						break;
//					case 24:
//						subAlpabet = "Y";
//						break;
//					case 25:
//						subAlpabet = "Z";
//						break;
//					}
//	 	        	
//	 	        	String mainAlpabet = "";
//	 	        	switch (i/26) {
//	 	        	case 1:
//	 	        		mainAlpabet = "A";
//						break;
//	 	        	case 2:
//	 	        		mainAlpabet = "B";
//						break;
//					case 3:
//						mainAlpabet = "C";
//						break;
//					case 4:
//						mainAlpabet = "D";
//						break;
//					case 5:
//						mainAlpabet = "E";
//						break;
//					case 6:
//						mainAlpabet = "F";
//						break;
//					case 7:
//						mainAlpabet = "G";
//						break;
//					case 8:
//						mainAlpabet = "H";
//						break;
//					case 9:
//						mainAlpabet = "I";
//						break;
//					case 10:
//						mainAlpabet = "J";
//						break;
//					case 11:
//						mainAlpabet = "K";
//						break;
//					case 12:
//						mainAlpabet = "L";
//						break;
//					case 13:
//						mainAlpabet = "M";
//						break;
//					case 14:
//						mainAlpabet = "N";
//						break;
//					case 15:
//						mainAlpabet = "O";
//						break;
//					case 16:
//						mainAlpabet = "P";
//						break;
//					case 17:
//						mainAlpabet = "Q";
//						break;
//					case 18:
//						mainAlpabet = "R";
//						break;
//					case 19:
//						mainAlpabet = "S";
//						break;
//					case 20:
//						mainAlpabet = "T";
//						break;
//					case 21:
//						mainAlpabet = "U";
//						break;
//					case 22:
//						mainAlpabet = "V";
//						break;
//					case 23:
//						mainAlpabet = "W";
//						break;
//					case 24:
//						mainAlpabet = "X";
//						break;
//					case 25:
//						mainAlpabet = "Y";
//						break;
//					case 26:
//						mainAlpabet = "Z";
//						break;
//					}
//	 	        	
//	 	        	Map mapExcel = (Map) excel_title.get(i);
//					String enColumns = mapExcel.get("colId").toString();					
//			
//					enColumns = CamelUtil.convert2CamelCase(enColumns);
//					
//					if(enColumns.matches("^c[0-9]*$") || enColumns.endsWith("cost") || enColumns.endsWith("Cost") || enColumns.endsWith("cnt") || enColumns.endsWith("Cnt")){
//						c = row.createCell(i);
//		 	        	c.setCellType(c.CELL_TYPE_FORMULA);
//		 	        	cs1r.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
//		 	        	c.setCellStyle(cs1r);    // ??? ????????? ??????
//		 	        	c.setCellFormula("sum(" + mainAlpabet + subAlpabet + "6:" + mainAlpabet + subAlpabet + ((HashMap)parameter).get("lastCnt") + ")");
//					}else{
//						c = row.createCell(i);
//						c.setCellType(c.CELL_TYPE_STRING);
//						c.setCellStyle(cs1c);    // ??? ????????? ??????
//		 	        	if((mainAlpabet + subAlpabet).equals("A") ){
//		 	        		c.setCellValue("??????");
//		 	        	}else{
//		 	        		c.setCellValue("");
//		 	        	}
//					}
//	 	        	
//	 	        	
//	 	        	
//	 	        	
//	 	        }
//	        }
	        sheets[3] =  (SXSSFSheet) worksheet;
	        
	        
	        // excel ?????? ??????
	        try {        	
	        	File xlsFile = new File(fullName);
	        	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	            workbook.write(fileOut);
	            //workbook.close();
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	     
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		
	}
	
	
	
	public void getSQLSelectListHandler(String sqlMapId, Object parameter, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) {

        try {
			long startTime = System.currentTimeMillis();
			
			
			file_name = file_name.replace("/", "");		
			
			String result    = "";

	        Row row = null;
	       
	        @SuppressWarnings("resource")
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	        final SXSSFSheet[] sheets = new SXSSFSheet[4];

	        
	        
	        
	        // 2. font ??????
	        // 2.1. ????????? Font ?????? ??????
	        Font font1 = workbook.createFont();
	        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);     // ?????????
	        font1.setFontHeightInPoints((short)16);        // ?????? ??????
	        font1.setUnderline(Font.U_SINGLE);		       // ??????

	        // 2.2. ?????? Font ?????? ??????
	        Font font2 = workbook.createFont();
	        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        font2.setColor(IndexedColors.BLACK.getIndex());
	        font2.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 2.3. ?????? ?????? Font ?????? ??????
	        Font font3 = workbook.createFont();
	        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        font3.setColor(IndexedColors.BLACK.getIndex());
	        font3.setFontHeightInPoints((short)11);        // ?????? ??????
	        
	        Font font4 = workbook.createFont();
	        font4.setColor(IndexedColors.RED.getIndex());
	        font4.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 2.4 DATA Font ?????? ??????
	        Font font5 = workbook.createFont();
	        font5.setFontName("?????? ??????");
	        font5.setFontHeightInPoints((short)9);        // ?????? ??????
	        
	        // 3. ????????? ??????
	        // 3.1. ????????? ????????? ??????
	        XSSFCellStyle cs0 = (XSSFCellStyle)workbook.createCellStyle();
	        cs0.setAlignment(CellStyle.ALIGN_CENTER);
	        cs0.setBorderTop(CellStyle.BORDER_THIN);
	        cs0.setBorderLeft(CellStyle.BORDER_THIN);
	        cs0.setBorderRight(CellStyle.BORDER_THIN);
//	        cs0.setBorderBottom(CellStyle.BORDER_THIN);
	        cs0.setFont(font1);
	        cs0.setAlignment((short)2);                    // ?????? ?????? ??????
	        cs0.setVerticalAlignment((short)1);            // ?????? ?????? ??????
	        
	        // 3.2. ????????? ????????? ??????
	        XSSFCellStyle cs3 = (XSSFCellStyle)workbook.createCellStyle();
	        cs3.setAlignment(CellStyle.ALIGN_RIGHT);
	        cs3.setBorderLeft(CellStyle.BORDER_THIN);
	        cs3.setBorderRight(CellStyle.BORDER_THIN);
	        
	        // ?????? ????????? ??????
	        XSSFCellStyle cs4 = (XSSFCellStyle)workbook.createCellStyle();
	        cs4.setFont(font3);
	        cs4.setAlignment((short)2);                    // ?????? ?????? ??????
	        cs4.setVerticalAlignment((short)1);            // ?????? ?????? ??????
	        cs4.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        cs4.setBorderTop(CellStyle.BORDER_THIN);
	        cs4.setBorderLeft(CellStyle.BORDER_THIN);
	        cs4.setBorderRight(CellStyle.BORDER_THIN);
	        cs4.setBorderBottom(CellStyle.BORDER_THIN);
	        	
	        // 3.3. ?????? ????????? ??????
	        XSSFCellStyle cs1c = (XSSFCellStyle)workbook.createCellStyle();
//	        cs1.setFillForegroundColor(HSSFColor.YELLOW.index);
//	        cs1.setFillBackgroundColor(HSSFColor.YELLOW.index);
//	        cs1.setFillPattern(CellStyle.BIG_SPOTS);
	        cs1c.setAlignment(CellStyle.ALIGN_CENTER);
	        cs1c.setBorderTop(CellStyle.BORDER_THIN);
	        cs1c.setBorderLeft(CellStyle.BORDER_THIN);
	        cs1c.setBorderRight(CellStyle.BORDER_THIN);
	        cs1c.setBorderBottom(CellStyle.BORDER_THIN);
	        cs1c.setFont(font2);
	        cs1c.setVerticalAlignment((short)1);            						// ?????? ?????? ??????
	        cs1c.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs1c.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        
	        XSSFCellStyle cs1r = (XSSFCellStyle)workbook.createCellStyle();
	        cs1r.setAlignment(CellStyle.ALIGN_RIGHT);
	        cs1r.setBorderTop(CellStyle.BORDER_THIN);
	        cs1r.setBorderLeft(CellStyle.BORDER_THIN);
	        cs1r.setBorderRight(CellStyle.BORDER_THIN);
	        cs1r.setBorderBottom(CellStyle.BORDER_THIN);
	        cs1r.setFont(font2);
	        cs1r.setVerticalAlignment((short)1);            						// ?????? ?????? ??????
	        cs1r.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// ??? ??????
	        cs1r.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        // 3.4. DATA ????????? ??????
	        CellStyle cs2 = workbook.createCellStyle();
	        cs2.setBorderTop(CellStyle.BORDER_THIN);
	        cs2.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2.setBorderRight(CellStyle.BORDER_THIN);
	        cs2.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2.setFont(font5);

	        // 4. ????????? ??????
	        Cell c = null;
	        HashMap parm = (HashMap)parameter;
	        String printDay = DateUtil.getNowDateTimeString();
	        
	        final Sheet worksheet = workbook.createSheet("SQL");
	        
	        // 1. ????????? ??? ??????
	        // 1.1. ?????????
	        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excel_title.size()-1));
	        
	        // 1.2. ?????????
	        worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, excel_title.size()-1));
	     
	        row = worksheet.createRow(0);
	        row.setHeightInPoints(35);        
	        for (int i=0; i<excel_title.size(); i++) {
	        	c = row.createCell(i);
	        	c.setCellStyle(cs0);    // ??? ????????? ??????
	        	c.setCellValue(file_name);
	        }
	        
	        // 4.2. ????????? ??????
	        row = worksheet.createRow(1);        
	        for (int i=0; i<excel_title.size(); i++) {
	        	c = row.createCell(i);
	        	c.setCellStyle(cs3);    // ??? ????????? ??????
	        	c.setCellValue("????????? : " + printDay + " ");
	        }
	        
	        // 4.3. ?????? ??????
	        row = worksheet.createRow(4);
	        row.setHeightInPoints(20); 
	        for (int i=0; i<excel_title.size(); i++) {

	        	c = row.createCell(i);
	        	c.setCellStyle(cs1c);    // ??? ????????? ??????
	        	HashMap map = (HashMap)excel_title.get(i);
	        	//c.setCellValue(map.get("objNm").toString()+"("+map.get("colId").toString()+")");	//??????????????? ??????ID??????(?????????????????? ??????)
				c.setCellValue(map.get("colId").toString());
	        }

	        // 4.3. DATA ??????
	        for(int i=0; i<excel_title.size(); i++){
	        	//worksheet.autoSizeColumn(i);
	        	worksheet.setColumnWidth(i, (worksheet.getColumnWidth(i))+2048 );
	        }

	        File saveFolder = new File(uploadPath);
	        
	        if (!saveFolder.exists()) {
				saveFolder.mkdirs();		
			}
	        
	        SqlExcelHandlerDownLoadContent handler = new SqlExcelHandlerDownLoadContent();
	        handler.ExcelHandlerDownLoadContent(worksheet, cs2, c, excel_title, newfileName, uploadPath, fullName, workbook, parameter);
	        
	        getSqlSession().select(sqlMapId, parameter, handler);
	        
	        String lastCnt = StringUtil.isNullToString(((HashMap)parameter).get("lastCnt"));

	        // excel ?????? ??????
	        try {        	
	        	File xlsFile = new File(fullName);
	        	FileOutputStream fileOut = new FileOutputStream(xlsFile);
	            workbook.write(fileOut);
	            //workbook.close();
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	     
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		
	}
	
	//????????? ????????? ?????????
	public void selectMailMakeHandler(String sqlMapId, Object parameter) {
		
		
		//ExcelHandlerDownLoadContent handler = new ExcelHandlerDownLoadContent();
        //handler.ExcelHandlerDownLoadContent(worksheet, cs2, c, excel_title, newfileName, uploadPath, fullName, workbook, parameter);
		//makeBillContent handler = new makeBillContent();
		//handler.makeBillContent(pdfUtil, parameter);
		
		//getSqlSession().select(sqlMapId, parameter, handler);
	}

	// ?????? ????????? ?????????
	public int getSelectCnt(String sqlMapId, Object parameter){
		int result = 0;

		try {
			long startTime = System.currentTimeMillis();

			result = (Integer)selectOne(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return result;
	}

	// ?????? ????????? ?????????
	public Object getSelect(String sqlMapId, Object parameter){
		Object result = null;

		try {
			long startTime = System.currentTimeMillis();

			result = selectOne(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return result;
	}


	/**
	 * ?????? ??????
	 * @param vo - ????????? ????????? ?????? SampleVO
	 * @return ?????? ??????
	 * @exception Exception
	 */
    public int insertData(String sqlMapId, Object parameter) throws Exception {
    	int result = 0;

		try {
			long startTime = System.currentTimeMillis();

			result = insert(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return result;
    }

	/**
	 * ?????? ??????
	 * @param vo - ????????? ????????? ?????? SampleVO
	 * @return ?????? ??????
	 * @exception Exception
	 */
    public int updateData(String sqlMapId, Object parameter) throws Exception {
    	int result = 0;

		try {
			long startTime = System.currentTimeMillis();

			result = (Integer)update(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return result;
    }

    /**
	 * ?????? ????????????.
	 * @param vo - ????????? ????????? ?????? SampleVO
	 * @return void???
	 * @exception Exception
	 */
    public int deleteData(String sqlMapId, Object parameter) throws Exception {
    	int result = 0;

		try {
			long startTime = System.currentTimeMillis();

			result = (Integer)delete(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException (e);
		}

		return result;
    }
    
    public int reinsertData(String sqlMapId, Object parameter) throws Exception {
    	int result = 0;

		try {
			long startTime = System.currentTimeMillis();

			result = insert(sqlMapId, parameter);

			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("["+sqlMapId.replaceAll("[\r\n]","")+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
			}
		}
		catch(Exception e) {
			result = -1;
			e.printStackTrace();
			throw new RuntimeException (e);
			
		}

		return result;
    }

}
