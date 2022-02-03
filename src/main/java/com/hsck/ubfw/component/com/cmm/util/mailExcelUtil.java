package com.hsck.ubfw.component.com.cmm.util;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * Created by LSW on 2017-05-15.
 */
@Component("mailExcelUtil")
public class mailExcelUtil {

    private static final Logger LOG = LoggerFactory.getLogger(mailExcelUtil.class);

    public void createExcel(Object parameterObject, List<EgovMap> rptKeynoList, List<EgovMap> selectColumnsList, HashMap sumList) throws Exception {
    	// Workbook 생성
        Workbook xlsWb = null;
        short palIndex = 0;
        // Cell 스타일 생성
        CellStyle Hcsh = null;
        CellStyle HcshSum = null;
        XSSFCellStyle Xcsh = null;
        XSSFCellStyle XcshSum = null;
        Font font = null;
        
        
        // XLSX 파일 일 경우
        if ("xlsx".equals("xls")) {
            
        	xlsWb = new XSSFWorkbook(); // Excel 2007 이상
        	// 폰트 생성
            font = xlsWb.createFont();
            font.setFontName("맑은 고딕");
            font.setFontHeightInPoints((short)9);        // 폰트 크기

            Xcsh = (XSSFCellStyle)xlsWb.createCellStyle();
        	Xcsh.setAlignment(CellStyle.ALIGN_CENTER);
        	Xcsh.setBorderTop(CellStyle.BORDER_THIN);
        	Xcsh.setBorderLeft(CellStyle.BORDER_THIN);
        	Xcsh.setBorderRight(CellStyle.BORDER_THIN);
        	Xcsh.setBorderBottom(CellStyle.BORDER_THIN);
        	Xcsh.setFont(font);
        	Xcsh.setVerticalAlignment((short)1);            						// 세로 정렬 중단
        	Xcsh.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// 셀 색상
        	Xcsh.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        	XcshSum = (XSSFCellStyle)xlsWb.createCellStyle();
        	XcshSum.setAlignment(CellStyle.ALIGN_RIGHT);
        	XcshSum.setBorderTop(CellStyle.BORDER_THIN);
        	XcshSum.setBorderLeft(CellStyle.BORDER_THIN);
        	XcshSum.setBorderRight(CellStyle.BORDER_THIN);
        	XcshSum.setBorderBottom(CellStyle.BORDER_THIN);
        	XcshSum.setFont(font);
        	XcshSum.setVerticalAlignment((short)1);            						// 세로 정렬 중단
        	XcshSum.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153)));	// 셀 색상
        	XcshSum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	
        	
            // XLS 파일 일 경우
        } else if ("xls".equals("xls")) {
        	xlsWb = new HSSFWorkbook();  // Excel 2007 이전 버전
        	
        	// 폰트 생성
            font = xlsWb.createFont();
            font.setFontName("맑은 고딕");
            font.setFontHeightInPoints((short)9);        // 폰트 크기
            
        	HSSFWorkbook HSSwb =  (HSSFWorkbook)xlsWb;
            HSSFPalette palette = HSSwb.getCustomPalette();
            HSSFColor myColor = palette.findSimilarColor(255, 230, 153);
            palIndex = myColor.getIndex();
            
        	//헤더 스타일 
            Hcsh = (CellStyle)xlsWb.createCellStyle();
            Hcsh.setAlignment(CellStyle.ALIGN_CENTER);
            Hcsh.setBorderTop(CellStyle.BORDER_THIN);
            Hcsh.setBorderLeft(CellStyle.BORDER_THIN);
            Hcsh.setBorderRight(CellStyle.BORDER_THIN);
            Hcsh.setBorderBottom(CellStyle.BORDER_THIN);
            Hcsh.setFont(font);
            Hcsh.setVerticalAlignment((short)1);            						// 세로 정렬 중단
            Hcsh.setFillForegroundColor(palIndex);
            Hcsh.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            
            HcshSum = (CellStyle)xlsWb.createCellStyle();
            HcshSum.setAlignment(CellStyle.ALIGN_RIGHT);
            HcshSum.setBorderTop(CellStyle.BORDER_THIN);
            HcshSum.setBorderLeft(CellStyle.BORDER_THIN);
            HcshSum.setBorderRight(CellStyle.BORDER_THIN);
            HcshSum.setBorderBottom(CellStyle.BORDER_THIN);
            HcshSum.setFont(font);
            HcshSum.setVerticalAlignment((short)1);            						// 세로 정렬 중단
            HcshSum.setFillForegroundColor(palIndex);
            HcshSum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            
        }

        // *** Sheet-------------------------------------------------
        // Sheet 생성
        Sheet sheet1 = xlsWb.createSheet("Sheet0");
        
        //data 스타일
        CellStyle csd = xlsWb.createCellStyle();
        csd.setBorderTop(CellStyle.BORDER_THIN);
        csd.setBorderLeft(CellStyle.BORDER_THIN);
        csd.setBorderRight(CellStyle.BORDER_THIN);
        csd.setBorderBottom(CellStyle.BORDER_THIN);
        csd.setFont(font);

        Row row = null;
        Cell cell = null;
        
        
        String excelHeader = "";
        String excelKey    = "";
        for(int i=0; i<selectColumnsList.size(); i++){
        	selectColumnsList.get(i).get("objNm");
        	if(i==0){
        		excelHeader += selectColumnsList.get(i).get("objNm");
        		excelKey += CamelUtil.convert2CamelCase(selectColumnsList.get(i).get("colId").toString()); 
        		
        	}else{
        		excelHeader += "@" + selectColumnsList.get(i).get("objNm");
        		excelKey += "@" + CamelUtil.convert2CamelCase(selectColumnsList.get(i).get("colId").toString());
        	}
        }
        
        String [] headers = excelHeader.split("@");
        String [] keys = excelKey.split("@");
        
        // 첫째줄 제목
        Font font0 = xlsWb.createFont();
        font0.setBoldweight(Font.BOLDWEIGHT_BOLD);     // 볼드체
        font0.setFontHeightInPoints((short)16);        // 폰트 크기
        font0.setUnderline(Font.U_SINGLE);		       // 밑줄
        
        CellStyle cs0 = xlsWb.createCellStyle();
        cs0.setAlignment(CellStyle.ALIGN_CENTER);
        cs0.setBorderTop(CellStyle.BORDER_THIN);
        cs0.setBorderLeft(CellStyle.BORDER_THIN);
        cs0.setBorderRight(CellStyle.BORDER_THIN);
//        cs0.setBorderBottom(CellStyle.BORDER_THIN);
        cs0.setFont(font0);
        cs0.setAlignment((short)2);                    // 가로 정렬 중간
        cs0.setVerticalAlignment((short)1);            // 세로 정렬 중단
        
        row = sheet1.createRow(0);
        //타이틀 병합
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length-1));
        
        //타이틀 
        row.setHeightInPoints(35);        
        for (int i=0; i<headers.length; i++) {
        	cell = row.createCell(i);
        	cell.setCellStyle(cs0);    // 셀 스타일 적용
        	cell.setCellValue("청구서 번호별 상세요금");
        }
        
        // 서머리
        Font font1 = xlsWb.createFont();
        font1.setColor(IndexedColors.BLACK.getIndex());
        font1.setFontHeightInPoints((short)9);        // 폰트 크기
        
        CellStyle cs1 = xlsWb.createCellStyle();
        cs1.setFont(font1);
        cs1.setAlignment((short)2);                    // 가로 정렬 중간
        cs1.setVerticalAlignment((short)1);            // 세로 정렬 중단
        
        HashMap sumL = (HashMap)sumList;
        row = sheet1.createRow(2);
        Map dmap = ((List<EgovMap>)sumL.get("selectDataSumList")).get(0);
        	
        for(int i=0; i<((List)sumL.get("selectHdSumList")).size(); i++){
        	Map hmap = ((List<EgovMap>)sumL.get("selectHdSumList")).get(i);
        		
        	cell = row.createCell(i);
        	cell.setCellStyle(cs1);
        	cell.setCellValue(hmap.get("scolNm").toString() + " : " + dmap.get(hmap.get("scolId")));
        		
        		
        }
               
        
        //헤더 생성
        row = sheet1.createRow(4);
        for (int i=0; i<headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            sheet1.setColumnWidth(i, (sheet1.getColumnWidth(i))+2048 );
            if ("xlsx".equals("xls")) {
            	 cell.setCellStyle(Xcsh); // 셀 스타일 적용
            } else if ("xls".equals("xls")) {
            	 cell.setCellStyle(Hcsh); // 셀 스타일 적용
            }
           
        }
        String str = "";
        // 두번째 줄부터 데이터
        for(int i=0; i<rptKeynoList.size();i++){
            EgovMap billdtlMap = rptKeynoList.get(i);

            row = sheet1.createRow(i+5);
            for(int j=0;j<headers.length;j++){

                cell = row.createCell(j);
                Object v = billdtlMap.get(keys[j]);
                cell.setCellStyle(csd); // 셀 스타일 적용
                String enColumns = keys[j].toString();
                enColumns = CamelUtil.convert2CamelCase(enColumns);
                if(null == billdtlMap.get(keys[j])){
                	str = "";
                }else{
                	str = billdtlMap.get(keys[j]).toString();
                }
				
				if(enColumns.matches("^c[0-9]*$") || enColumns.endsWith("cost") || enColumns.endsWith("Cost") || enColumns.endsWith("cnt") || enColumns.endsWith("Cnt")){
                    csd.setDataFormat(xlsWb.createDataFormat().getFormat("#,##0"));
                    csd.setAlignment(CellStyle.ALIGN_RIGHT);
                    cell.setCellStyle(csd);    // 셀 스타일 적용
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
       				if(str.replace(",", "").equals("")){str = "0";}
       				cell.setCellValue(Long.parseLong(str.replace(",", "")));
                }else{
                	cell.setCellValue( str );
                }
                   
                
            }
        }
        
        //하단 합계
        int lastcnt = rptKeynoList.size() + 5;
        row = sheet1.createRow(lastcnt);
        for (int i=0; i<headers.length; i++) {
    		String subAlpabet = "";
	        switch (i%26) {
	        case 0:
	        	subAlpabet = "A";
	        	break;
	        case 1:
	        	subAlpabet = "B";
				break;
			case 2:
				subAlpabet = "C";
				break;
			case 3:
				subAlpabet = "D";
				break;
			case 4:
				subAlpabet = "E";
				break;
			case 5:
				subAlpabet = "F";
				break;
			case 6:
				subAlpabet = "G";
				break;
			case 7:
				subAlpabet = "H";
				break;
			case 8:
				subAlpabet = "I";
				break;
			case 9:
				subAlpabet = "J";
				break;
			case 10:
				subAlpabet = "K";
				break;
			case 11:
				subAlpabet = "L";
				break;
			case 12:
				subAlpabet = "M";
				break;
			case 13:
				subAlpabet = "N";
				break;
			case 14:
				subAlpabet = "O";
				break;
			case 15:
				subAlpabet = "P";
				break;
			case 16:
				subAlpabet = "Q";
				break;
			case 17:
				subAlpabet = "R";
				break;
			case 18:
				subAlpabet = "S";
				break;
			case 19:
				subAlpabet = "T";
				break;
			case 20:
				subAlpabet = "U";
				break;
			case 21:
				subAlpabet = "V";
				break;
			case 22:
				subAlpabet = "W";
				break;
			case 23:
				subAlpabet = "X";
				break;
			case 24:
				subAlpabet = "Y";
				break;
			case 25:
				subAlpabet = "Z";
				break;
			}
	        	
	        String mainAlpabet = "";
	        switch (i/26) {
	        case 1:
	        	mainAlpabet = "A";
			break;
	        case 2:
	        	mainAlpabet = "B";
			break;
			case 3:
				mainAlpabet = "C";
				break;
			case 4:
				mainAlpabet = "D";
				break;
			case 5:
				mainAlpabet = "E";
				break;
			case 6:
				mainAlpabet = "F";
				break;
			case 7:
				mainAlpabet = "G";
				break;
			case 8:
				mainAlpabet = "H";
				break;
			case 9:
				mainAlpabet = "I";
				break;
			case 10:
				mainAlpabet = "J";
				break;
			case 11:
				mainAlpabet = "K";
				break;
			case 12:
				mainAlpabet = "L";
				break;
			case 13:
				mainAlpabet = "M";
				break;
			case 14:
				mainAlpabet = "N";
				break;
			case 15:
				mainAlpabet = "O";
				break;
			case 16:
				mainAlpabet = "P";
				break;
			case 17:
				mainAlpabet = "Q";
				break;
			case 18:
				mainAlpabet = "R";
				break;
			case 19:
				mainAlpabet = "S";
				break;
			case 20:
				mainAlpabet = "T";
				break;
			case 21:
				mainAlpabet = "U";
				break;
			case 22:
				mainAlpabet = "V";
				break;
			case 23:
				mainAlpabet = "W";
				break;
			case 24:
				mainAlpabet = "X";
				break;
			case 25:
				mainAlpabet = "Y";
				break;
			case 26:
				mainAlpabet = "Z";
				break;
			}
	        
	        String enColumns = keys[i];	
	        enColumns = CamelUtil.convert2CamelCase(enColumns);
	        if(enColumns.matches("^c[0-9]*$") || enColumns.endsWith("cost") || enColumns.endsWith("Cost") || enColumns.endsWith("cnt") || enColumns.endsWith("Cnt")){
				cell = row.createCell(i);
				cell.setCellType(cell.CELL_TYPE_FORMULA);
 	        	if ("xlsx".equals("xls")) {
 	        		XcshSum.setDataFormat(xlsWb.createDataFormat().getFormat("#,##0"));
 	        		XcshSum.setAlignment(CellStyle.ALIGN_RIGHT);
 	            	cell.setCellStyle(XcshSum); // 셀 스타일 적용
 	            } else if ("xls".equals("xls")) {
 	            	HcshSum.setDataFormat(xlsWb.createDataFormat().getFormat("#,##0"));
 	            	HcshSum.setAlignment(CellStyle.ALIGN_RIGHT);
 	            	cell.setCellStyle(HcshSum); // 셀 스타일 적용
 	            }
 	        	cell.setCellFormula("sum(" + mainAlpabet + subAlpabet + "6:" + mainAlpabet + subAlpabet + lastcnt + ")");
			}else{
				cell = row.createCell(i);
				cell.setCellType(cell.CELL_TYPE_STRING);
				if ("xlsx".equals("xls")) {
 	        		cell.setCellStyle(Xcsh); // 셀 스타일 적용
 	            } else if ("xls".equals("xls")) {
 	            	cell.setCellStyle(Hcsh); // 셀 스타일 적용
 	            }
 	        	if((mainAlpabet + subAlpabet).equals("A") ){
 	        		cell.setCellValue("합계");
 	        	}else{
 	        		cell.setCellValue("");
 	        	}
			}
	        
        }
        
        // excel 파일 저장
        try {
            String targetFilePathAndNameExcel = ((EgovMap)parameterObject).get("attachFilePath") + ".xls";

            File xlsFile = new File(targetFilePathAndNameExcel);
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsWb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
