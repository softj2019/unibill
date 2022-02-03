package com.hsck.ubfw.component.com.cmm.util;

import java.net.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.*;

import egovframework.rte.psl.dataaccess.util.CamelUtil;

public class ExcelViewContent extends AbstractExcelViewCustom {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void buildExcelDocument(Map<String,Object> ModelMap, SXSSFWorkbook workbook,

			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			String file_name = (String) ModelMap.get("file_name");
			//file_name = URLEncoder.encode(file_name,"UTF-8");

			List<Map>  excel_title =  (List<Map>) ModelMap.get("excel_title");
			//String[] excel_column = (String[]) ModelMap.get("excel_column");
			List<Map> data_list = (List<Map>)ModelMap.get("data_list");

	        Sheet worksheet = null;
	        Row row = null;

	        worksheet = workbook.createSheet();

	        CellStyle cs1 = workbook.createCellStyle();
	        cs1.setAlignment(CellStyle.ALIGN_CENTER);
	        cs1.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());

	        CellStyle cs2 = workbook.createCellStyle();
	        cs2.setAlignment(CellStyle.ALIGN_RIGHT);

	        Cell c;

	        // 헤더 생성
	        row = worksheet.createRow(0);
	        for( int i=0; i<excel_title.size(); i++) {
	        	c = row.createCell(i);
	        	c.setCellStyle(cs1);
	        	Map map = (Map) excel_title.get(i);
	        	
				c.setCellValue(map.get("objNm").toString()+"("+map.get("colId").toString()+")");				
	        }

	        // DATA
	        String tmp = "";
	        for(int i=0;i<data_list.size();i++){
				row = worksheet.createRow(i+1);
				Map map = (Map) data_list.get(i);

				for( int column=0; column<excel_title.size(); column++) {

					Map mapExcel = (Map) excel_title.get(column);
					String enColumns = mapExcel.get("colId").toString();					

					enColumns = CamelUtil.convert2CamelCase(enColumns);
										
					tmp = ""+map.get( enColumns );
					if( "null".equals(tmp) )
						tmp = "";
					c = row.createCell(column);
					c.setCellValue(tmp);
				}
	        }

			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(file_name,"UTF-8")+".xlsx");
		}

}