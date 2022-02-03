package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import egovframework.com.cmm.service.Globals;
import au.com.bytecode.opencsv.CSVReader;


public class CsvUtils {
	
	protected static Log log = (Log) LogFactory.getLog(CsvUtils.class);
/*	
	// csv 파일 data 추출
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String[]> CsvRead(String filename) {
				
		List<String[]> data = new ArrayList<String[]>();		
					
        data = readCsv(filename);
 
        Iterator<String[]> it = data.iterator();
        while (it.hasNext()) {
            String[] array = (String[]) it.next();
            for (String s : array) {
                System.out.print(s + " ");        	
            }
            System.out.print("\n");
        }

		return data;
	}	
	*/
	// csv 파일 읽기
	public static List<String []> readCsv(String filename) {
		 
    	List<String[]> data = new ArrayList<String[]>();
    	
        try {
//            CSVReader reader = new CSVReader(new FileReader(filename));
            // UTF-8
//             CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), Globals.CHARSET), ';');
        	CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "EUC-KR"), ';');
            String[] s;
            while ((s = reader.readNext()) != null) {
                data.add(s);
            }
            
            reader.close();
            
            // csv file delete
            FileMngUtil.fileDelete(filename);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 	    
        
        return data;
    }

	// 엑셀파일을 csv 파일로 변환
	public static void xlsToCsv(File inputFile, File outputFile, int sheetNum, String fileExt, int jobtype, int data_start_line) {

		  StringBuffer data = new StringBuffer();
		  String  cust_id="";
		  try {

			   // xlsx 파일 스트림
			   FileOutputStream fos = new FileOutputStream(outputFile);
			   
			   Iterator<Row> rowIterator = null;

			   // XLSX 파일 일 경우
			   if ("xlsx".equals(fileExt)) {
	
 				   // 파일 스트림을 XSSFWorkbook 객체로 생성
				   XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));			   
		
	  			   // XSSFWorkbook 의 첫번째 시트를 가져옴
				   XSSFSheet sheet = wBook.getSheetAt(sheetNum);
				   
				   rowIterator = sheet.iterator();
				   
				// XLS 파일 일 경우
				} else if ("xls".equals(fileExt)) {
					
	  			   // 파일 스트림을 HSSFWorkbook 객체로 생성
					HSSFWorkbook wBook = new HSSFWorkbook(new FileInputStream(inputFile));			   

	  			    // HSSFSheet 의 첫번째 시트를 가져옴
					HSSFSheet sheet = wBook.getSheetAt(sheetNum);
					
					rowIterator = sheet.iterator();
				   
			   }
			   
			   Row row;
			   Cell cell;
			   int cellCnt = 0; 
			   int cust_id_pos=0;
			   int nextrowflag=0; 
			   int linecnt=0;

			   // 시트의 row
//			   Iterator<Row> rowIterator = sheet.iterator();
			   
			   // row 수 만큼
			   while (rowIterator.hasNext()) {				   
			      row = rowIterator.next();			      
			      linecnt++; 
			      // row 의 cell들
			      Iterator<Cell> cellIterator = row.cellIterator();
			      
			      int cells = row.getLastCellNum();  // cell 갯수 가져오기
			      int current = 0, next = 1;
			      
			      if (row.getRowNum() == 0) {
			    	  cellCnt = cells;
			      }
			      nextrowflag=1;
			      // cell 수 만큼
			      while (nextrowflag==1 && cellIterator.hasNext()) {			    	 			    	 
				     cell = cellIterator.next();				     
				     
				     current = cell.getColumnIndex();
				     
				     //미수납 업로드 상태
				     if(jobtype==1 &&   data_start_line >=1 && linecnt >=data_start_line ) {
				    	 if(cell.getCellType() == Cell.CELL_TYPE_STRING && current==0 ) {
				    		 cust_id=cell.getStringCellValue();
				    		 if("5".equals(cust_id.substring(6, 7))) {
				    		 }
				    		 else {
				    			 nextrowflag=0;
				    			 continue;
				    		 }
				    	 }
				    		 
				     }
				     
				     if (current<next) {			        	   
			               
			         } else  {
			        	   
			               int loop = current-next;			              
			               for (int k=0; k<loop+1; k++) {
			            	   
			            	   data.append("@" + "");			                   
			                   next = next + 1;
			               }
			         }
				  
				     switch (cell.getCellType()) {
					     case Cell.CELL_TYPE_BOOLEAN:
						      data.append("@" + cell.getBooleanCellValue());			
						      break;
					     case Cell.CELL_TYPE_NUMERIC:
					    	 if (HSSFDateUtil.isCellDateFormatted(cell)){ // 숫자- 날짜 타입이다.
					    		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					    		 data.append("@" + sf.format(cell.getDateCellValue()).replace("-", ""));
					    	}else{
					    		 Double doubleCell = new Double(cell.getNumericCellValue());
							     data.append("@" + doubleCell.longValue());		
					    	}
						      break;
					     case Cell.CELL_TYPE_STRING:
						      data.append("@" + cell.getStringCellValue());
						      break;			
					     case Cell.CELL_TYPE_BLANK:
						      data.append("@" + "");
						      break;
					     case Cell.CELL_TYPE_ERROR:
						      data.append("@" + cell.getErrorCellValue());
						      break;
					     default:
					    	 data.append("@" + cell);
				     } // switch end
				     
				     next = next + 1;
				     
			      }  // cell 수 만큼 end
			      if(nextrowflag==0) continue;
			      if (cellCnt > next) {
				      for (int i=current; i<next; i++) {
				    	  data.append("@" + "");
				      }
			      }
			      
			      if (cellCnt == next) {			    	 
			    	  data.append("@" + "");
			      }
   		      
			      data.append("\r\n");
			      			      
			   }  // row 수 만큼 end
			      
			   fos.write(data.toString().getBytes());
			   fos.close();

			   // excel file delete
		       FileMngUtil.fileDelete(inputFile.toString());

		  } catch (Exception ioe) {
			  ioe.printStackTrace();
		  }
	}
}