package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {		
	
	// 엑셀파일 DATA 읽기
	public static String ExcelRead(String ext, String file_path, String subPath, String file_name, int sheetNum) throws IOException {			
		
		String filePath = file_path + subPath + File.separator + file_name;
		
		String resultStr = ""; 				
	    
		try {
			
		    // XLSX 파일 일 경우
	    	if ("xlsx".equals(ext)) {
		    
	    		FileInputStream fis   = new FileInputStream(filePath);
	    	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    	    
	    	    int rowindex=0;
	    	    int columnindex=0;
	    	    
			    //시트 수 (첫번째에만 존재하므로 0을 준다)
			    //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			    XSSFSheet sheet=workbook.getSheetAt(sheetNum);
			    
			    //행의 수
			    int rows=sheet.getPhysicalNumberOfRows();
			    
			    for(rowindex=0;rowindex<=rows;rowindex++){
			    	
			        //행을읽는다
			        XSSFRow row=sheet.getRow(rowindex);
			        
			        if(row !=null){
			        	
			            //셀의 수
			            int cells=row.getLastCellNum();
			            for(columnindex=0; columnindex<=cells; columnindex++){
			                //셀값을 읽는다
			                XSSFCell cell=row.getCell(columnindex);
			                
			                String value="";
			                //셀이 빈값일경우를 위한 널체크			                
			                if (cell==null) {
			                	if (columnindex == cells) {			                		
			                		continue;
			                	} else {
			                		resultStr += "@" + rowindex + "!" + columnindex + "!!" + cells + "!" + rows;
			                		continue;
			                	}			                    
			                } else {
			                	//타입별로 내용 읽기
			                    switch (cell.getCellType()){
			                    case XSSFCell.CELL_TYPE_FORMULA:
			                        value=cell.getCellFormula();		                        
			                        break;
			                    case XSSFCell.CELL_TYPE_NUMERIC:
			                    	DecimalFormat df = new DecimalFormat("#,###");
			                        value=df.format(cell.getNumericCellValue())+"";
			                        break;
			                    case XSSFCell.CELL_TYPE_STRING:
			                        value=cell.getStringCellValue()+"";
			                        if ("".equals(value)) value = "";	
			                        break;
			                    case XSSFCell.CELL_TYPE_BLANK:
			                        value=cell.getBooleanCellValue()+"";
			                        if ("false".equals(value)) value="";
			                        break;
			                    case XSSFCell.CELL_TYPE_ERROR:
			                        value=cell.getErrorCellValue()+"";            
			                        break;
			                    }
			                }      	
			                resultStr += "@" + rowindex + "!" + columnindex + "!" + value + "!" + cells + "!" + rows;			                
			                
			            }
			        }
			    }
	    	
	    	// XLS 파일 일 경우
			} else if ("xls".equals(ext)) {
				
				//파일을 읽기위해 엑셀파일을 가져온다 
				FileInputStream fis=new FileInputStream(filePath);
				HSSFWorkbook workbook=new HSSFWorkbook(fis);
				int rowindex=0;
				int columnindex=0;
				
				//시트 수 (첫번째에만 존재하므로 0을 준다)
				//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
				HSSFSheet sheet=workbook.getSheetAt(sheetNum);
				//행의 수
				int rows=sheet.getPhysicalNumberOfRows();
				for(rowindex=1;rowindex<=rows;rowindex++){
				    //행을 읽는다
				    HSSFRow row=sheet.getRow(rowindex);
				    if(row !=null){
				        //셀의 수
				        int cells=row.getLastCellNum();
				        for(columnindex=0;columnindex<=cells;columnindex++){
				            //셀값을 읽는다
				            HSSFCell cell=row.getCell(columnindex);
				            String value="";
				            //셀이 빈값일경우를 위한 널체크
				            if(cell==null){
				            	if (columnindex == cells) {			                		
			                		continue;
			                	} else {
			                		resultStr += "@" + rowindex + "!" + columnindex + "!!" + cells + "!" + rows;
			                		continue;
			                	}
				            }else{
				                //타입별로 내용 읽기
				                switch (cell.getCellType()){
				                case HSSFCell.CELL_TYPE_FORMULA:
				                    value=cell.getCellFormula();
				                    break;
				                case HSSFCell.CELL_TYPE_NUMERIC:
				                	DecimalFormat df = new DecimalFormat("#,###");
			                        value=df.format(cell.getNumericCellValue())+"";
				                    break;
				                case HSSFCell.CELL_TYPE_STRING:
				                    value=cell.getStringCellValue()+"";
				                    if ("".equals(value)) value = " ";
				                    break;
				                case HSSFCell.CELL_TYPE_BLANK:
				                    value=cell.getBooleanCellValue()+"";
				                    break;
				                case HSSFCell.CELL_TYPE_ERROR:
				                    value=cell.getErrorCellValue()+"";
				                    break;
				                }
				            }
				            resultStr += "@" + rowindex + "!" + columnindex + "!" + value + "!" + cells + "!" + rows;
				        }
				    }
				}
				
			}
		    
		    resultStr = resultStr.substring(1);
		    
		    // 파일삭제
		    try {				
				File uFile = new File(file_path + subPath, file_name);

				uFile.delete();	// 물리적 삭제
				
			} catch(Exception e) {}
		    
		} catch (Exception e) {
			resultStr = "";
		}
	    
		return resultStr;
	}
		
}