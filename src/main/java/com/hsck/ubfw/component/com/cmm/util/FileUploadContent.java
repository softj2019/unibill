package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.saxpath.SAXPathParseException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.hsck.ubfw.component.content.service.ContentService;


import egovframework.rte.psl.dataaccess.util.EgovMap;


public class FileUploadContent {
	
	
	
	
	public String filePaths     = "";
	
	public FileUploadContent() {
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
	
	@SuppressWarnings({ "unchecked", "resource", "rawtypes"})
	public static HashMap excelUpLoadProcess(String excelFile, int excel_sheet, String fileExt, HashMap parameterObject, List<EgovMap> dbHeader, List<EgovMap> keyCol, String flag, PlatformTransactionManager transactionManager, String ErrorFile_name) throws Exception{
		HashMap resultErrorDataMsg = null;
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
		try {
			//????????? ??????
			List updateDataList = new ArrayList();
			int loadRowCnt = 0;
			Iterator<Row> rowIterator = null;
			int headerRsCnt = 0;
			
			// ?????? ????????? ????????? ?????? ??????
			if(flag.equals("com")) loadRowCnt = 4;
		    else if(flag.equals("tel")) loadRowCnt = 0;
			//?????? ???????????? ??????
			HashMap<Integer,String> dbHeaderLoadList = new HashMap<Integer,String>();
			dbHeaderLoadList = headerLoad(dbHeader);
			//?????? ????????????
			HashMap<String,String> cryptoYnList = new HashMap<String,String>();
			cryptoYnList = cryptoynLoad(dbHeader);
			//????????? ?????? ??????
			HashMap<String,String> extCodeYnList = new HashMap<String,String>();
			extCodeYnList = extCode(dbHeader);
			//???????????? ?????? ??????
			updateDataList = getRemoveKeycol(keyCol, dbHeaderLoadList, cryptoYnList, extCodeYnList);
			
			//?????? ??????
			if(fileExt.equals("xlsx")){
				// ?????? ???????????? XSSFWorkbook ????????? ??????
				FileInputStream fis   = new FileInputStream(excelFile);
				OPCPackage opc = OPCPackage.open(fis);
				
				XSSFWorkbook wBook = new XSSFWorkbook(opc);
				// XSSFWorkbook ??? ????????? ????????? ?????????
				XSSFSheet sheet = wBook.getSheetAt(excel_sheet);
				rowIterator = sheet.iterator();
				//?????? ????????? ??????
				Row rowHeader = sheet.getRow(loadRowCnt);
				//???????????? ?????? ??????
				headerRsCnt = headerCheck(rowHeader);
				
				//????????? ??????
				if(dbHeaderLoadList.size() == headerRsCnt){
					resultErrorDataMsg = exceldataProgress(rowIterator, headerRsCnt, loadRowCnt, dbHeaderLoadList, updateDataList, parameterObject, transactionManager, flag, cryptoYnList, extCodeYnList);
				}else{
					resultErrorDataMsg = new HashMap();
					resultErrorDataMsg = returnMsg(new ArrayList(), "????????? ?????? ????????? ?????? ?????? ????????????.", 0, 0, 0);
					
					return resultErrorDataMsg;
				}
				//??????????????? ?????? ????????? 
				if((int)resultErrorDataMsg.get("failCnt") > 0){
					//?????? ?????? ?????? ?????????
					errorExcelWriter(ErrorFile_name, fileExt, "EXCEL_UPLOAD", resultErrorDataMsg);
				}
				
		
				
			}else if(fileExt.equals("xls")){
				// ?????? ???????????? HSSFWorkbook ????????? ??????
				FileInputStream fis   = new FileInputStream(excelFile);
				HSSFWorkbook wBook = new HSSFWorkbook(fis);			   
  			    // HSSFSheet ??? ????????? ????????? ?????????
				HSSFSheet sheet = wBook.getSheetAt(excel_sheet);
				rowIterator = sheet.iterator();
				
				//?????? ????????? ??????
				Row rowHeader = sheet.getRow(loadRowCnt);
				
				//???????????? ?????? ?????? 
				headerRsCnt = headerCheck(rowHeader);
				
				//????????? ??????
				if(dbHeaderLoadList.size() == headerRsCnt){
					resultErrorDataMsg = exceldataProgress(rowIterator, headerRsCnt, loadRowCnt, dbHeaderLoadList, updateDataList, parameterObject, transactionManager, flag, cryptoYnList, extCodeYnList);
				}else{
					resultErrorDataMsg = new HashMap();
					resultErrorDataMsg = returnMsg(new ArrayList(), "????????? ?????? ????????? ?????? ?????? ????????????.", 0, 0, 0);
					
					return resultErrorDataMsg;
				}
				
				//??????????????? ?????? ????????? 
				if((int)resultErrorDataMsg.get("failCnt") > 0){
					//?????? ?????? ?????? ?????????
					errorExcelWriter(ErrorFile_name, fileExt, "EXCEL_UPLOAD", resultErrorDataMsg);
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {
			// ?????? ?????????
			resultErrorDataMsg.put("errorMsg", "?????? ???????????? ????????? ?????????????????????.");
			return resultErrorDataMsg;
		}
		
		//inputFile.delete();
		
		return resultErrorDataMsg;
	}
	
	
	
	
	public static int headerCheck(Row rowHeader) throws Exception{
		int headerRsCnt = 0;
		int headerCnt= rowHeader.getLastCellNum(); //getPhysicalNumberOfCells=>getLastCellNum
		
		for(int i=0; i<headerCnt; i++){
			Cell headCell= rowHeader.getCell(i);
			boolean continueFlag = true;
			 	
			switch (headCell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
				  	headerRsCnt ++;
				  	break;			
				case Cell.CELL_TYPE_BLANK:
				   	 continueFlag = false;
				   	 break;
				case Cell.CELL_TYPE_ERROR:
				   	 continueFlag = false;
				     break;
				default:
				headerRsCnt ++;
			}
			if(!continueFlag) break; 
				
		}
		return headerRsCnt;
	}
	
	
	public static HashMap<Integer, String> headerLoad(List<EgovMap> excelHeader) throws Exception{
		HashMap<Integer, String> cellList = new HashMap<Integer, String>();
		try{
			int orderSn = 0;
			for(EgovMap h : excelHeader){
				cellList.put(orderSn, (String)h.get("objId"));
				orderSn++;
			}
		} catch (Exception e) {
			return null;
		}
		
		return cellList;
		
	}
	
	public static HashMap<String, String> cryptoynLoad(List<EgovMap> excelHeader) throws Exception{
		HashMap<String, String> cryptoList = new HashMap<String, String>();
		try{
			for(EgovMap h : excelHeader){
				cryptoList.put((String)h.get("objId"), (String)h.get("cryptoYn"));
			}
		}catch (Exception e) {
			return null;
		}
		
		return cryptoList;
	}
	
	public static HashMap<String, String> extCode(List<EgovMap> excelHeader) throws Exception{
		HashMap<String, String> extCodeList = new HashMap<String, String>();
		String chkYn = "N";
		try{
			for(EgovMap h : excelHeader){
				System.out.println("");
				if((String)h.get("xmlId") != null && !((String)h.get("xmlId")).equals("")) chkYn = "Y";
				if((String)h.get("grpCd") != null && !((String)h.get("grpCd")).equals("")) chkYn = "Y";
				if((String)h.get("grpType") != null && !((String)h.get("grpType")).equals("")) chkYn = "Y";
				if((String)h.get("objSql") != null && !((String)h.get("objSql")).equals("")) chkYn = "Y";
				if((String)h.get("colTbl") != null && !((String)h.get("colTbl")).equals("")) chkYn = "Y";
				
				extCodeList.put((String)h.get("objId"), chkYn);
			}
		}catch (Exception e) {
			return null;
		}
		
		return extCodeList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getRemoveKeycol(List<EgovMap> keyCol, HashMap<Integer,String> headerLoadList, HashMap<String,String> cryptoYnList, HashMap<String,String> extCodeYnList) throws Exception{
		List rsCol = new ArrayList(); 
		for(EgovMap col : keyCol){
			for(int i=0; i<headerLoadList.size(); i++){
				if(!headerLoadList.get(i).equals(col.get("keyCol")) ){
					if(cryptoYnList.get(headerLoadList.get(i)).equals("Y")) {
						rsCol.add(headerLoadList.get(i) + " = f_enc('1','telno', #{" + headerLoadList.get(i) + "})");
					}else {
						rsCol.add(headerLoadList.get(i) + " = #{" + headerLoadList.get(i) + "}");
					}
					
				}
			}
		}
		return rsCol;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static HashMap excelComonProcess(HashMap cellList, int headerRsCnt, HashMap dbHeaderLoadList, List updateDataList, HashMap parameterObject, PlatformTransactionManager transactionManager, int failCnt, HashMap<String,String> cryptoYnList, HashMap<String,String> extCodeYnList)  throws Exception{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		 
//		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//        
		String uploadResult = "";
		String errorMsg = "";
		HashMap errorData = new HashMap();
		
			// ?????? ????????? ?????? ?????? 
			List insertColumsList = new ArrayList();
			List returnList = new ArrayList();
			List insertDataList = new ArrayList();
			List insertEncryptYnList = new ArrayList();
			
			
			for(int i=0; i<dbHeaderLoadList.size(); i++){
				insertColumsList.add(dbHeaderLoadList.get(i));
				String cellstr = cellList.get(i).toString();
				if(extCodeYnList.get(dbHeaderLoadList.get(i)).equals("Y")) {
					cellstr = StringUtil.substrCode(cellList.get(i).toString());
				}
				if(cryptoYnList.get(dbHeaderLoadList.get(i)).equals("Y")) {
					insertDataList.add("f_enc('1','telno','" + tansCostDaytype(dbHeaderLoadList.get(i).toString(), cellstr) + "')");
				}else {
					insertDataList.add("'"+ tansCostDaytype(dbHeaderLoadList.get(i).toString(), cellstr) + "'" );
//					insertDataList.add(tansCostDaytype(dbHeaderLoadList.get(i).toString(), cellstr) );
				}
				parameterObject.put(dbHeaderLoadList.get(i), tansCostDaytype(dbHeaderLoadList.get(i).toString(), cellstr));
				
				
				
			}
			parameterObject.put("insertColumsList", insertColumsList);  // insert ?????? ??????
			parameterObject.put("insertDataList", insertDataList);  	// insert ????????? ??????
   			parameterObject.put("updateDataList", updateDataList);  	// update ????????? ??????
			
   			EgovMap info = service.insertBundleUploadContent(parameterObject);

   			uploadResult = (String) info.get("uploadResult");
   			errorMsg     = (String) info.get("errorMsg");
				
   			if (uploadResult == "true") {

   			}else{

   				failCnt++;
   			
   				errorData.put("errorMsg" , errorMsg);
   				errorData.put("insData" , insertDataList);
   				errorData.put("failCnt", failCnt);
   			}
			
		return errorData;
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap exceldataProgress(Iterator<Row> rowIterator, int headerRsCnt, int loadRowCnt, HashMap<Integer,String> dbHeaderLoadList, List updateDataList, HashMap parameterObject, PlatformTransactionManager transactionManager, String flag, HashMap<String,String> cryptoYnList, HashMap<String,String> extCodeYnList) throws Exception{
		Row row;
		Cell cell;
		int rowRsCnt = 0;
		List excelComonMsgs = new ArrayList();
		HashMap returnMsg = new HashMap();
		
		int failCnt = 0;
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
		ContentService service = (ContentService) webApplicationContext.getBean("contentService");
		
        String dtltmpdel = (String)parameterObject.get("dtltmpdel");
        if(dtltmpdel.equals("on")) {
        	//????????? ?????? 
        	System.out.println("?????? ????????? : " + parameterObject.get("tableName"));
        	HashMap param = new HashMap();
        	param.put("xmlId", "content.deleteExcelUpload");
        	param.put("tableName", parameterObject.get("tableName"));
        	service.deleteContent(param);
        }
		
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			HashMap cellList = new HashMap();
			int nullCell = 0; //?????? ?????? ??????
			
			if(rowRsCnt > 2){
				// header??? ??????
			    for(int i=0; i<headerRsCnt; i++){
			    	cell = row.getCell(i);
			    	
			    	if(cell == null){
			    		cellList.put(i, "");
			    		nullCell ++;
			    	}else{
			    		switch (cell.getCellType()) {
					     	case Cell.CELL_TYPE_BOOLEAN:
					     		cellList.put(i, cell.getBooleanCellValue());
					     		break;
					     	case Cell.CELL_TYPE_NUMERIC:
					     		if (HSSFDateUtil.isCellDateFormatted(cell)){ // ??????- ?????? ????????????.
					     			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					     			cellList.put(i, sf.format(cell.getDateCellValue()).replace("-", ""));
					     		}else{
					     			Double doubleCell = new Double(cell.getNumericCellValue());
					     			cellList.put(i, doubleCell.longValue());
					     		}
					     		break;
					     	case Cell.CELL_TYPE_STRING:
					     		cellList.put(i, cell.getStringCellValue().trim());
					     		break;			
					     	case Cell.CELL_TYPE_BLANK:
					     		cellList.put(i, "");
					     		break;
					     	case Cell.CELL_TYPE_ERROR:
					     		cellList.put(i, cell.getErrorCellValue());
					     		break;
					     	default:
					     		cellList.put(i, cell);
			    		} // switch end
			    	} // if end
			    	
			    	
			    } //for end
			    
			    if(nullCell == headerRsCnt) {
			    	break; 
			    }else{ 
			    	//????????? ??????
			    	if(dbHeaderLoadList.size() == headerRsCnt && rowRsCnt > 2){
			    		if(flag.equals("com")){
			    			HashMap excelComonMsg = excelComonProcess(cellList, headerRsCnt, dbHeaderLoadList, updateDataList, parameterObject, transactionManager, failCnt, cryptoYnList, extCodeYnList);
			    			if(excelComonMsg.size() != 0){
				    			failCnt = (Integer)excelComonMsg.get("failCnt");
					    	}
				    		excelComonMsgs.add(excelComonMsg);
				    		
				    	}else if(flag.equals("tel")){
				    		
				    	}
			    	}else{
			    		break;
			    	}
			    	
			    	
			    }
			    nullCell = 0;
		    }
		    rowRsCnt++;	
		    // ?????????????????? ????????? ??????
		    if(failCnt > 5){
		    	break;
		    }
		    
		    	
		}	// ?????? ??????????????? end
		if(failCnt > 0 ){
			transactionManager.rollback(transactionStatus);
			returnMsg = returnMsg(excelComonMsgs, "????????? ????????? ????????? ?????????????????????.", 0, 0, failCnt);
		}else{
			transactionManager.commit(transactionStatus);
			returnMsg = returnMsg(excelComonMsgs, "???????????? ???????????????.", rowRsCnt, rowRsCnt - failCnt, failCnt);
		}
		
		
		
		return returnMsg;	
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void errorExcelWriter(String ErrorFile_name, String fileExt, String subPath, HashMap resultErrorDataMsg) throws Exception{
		FileUploadContent filePath = new FileUploadContent();
		String full_name = filePath.filePaths + "/" + subPath + "/" + ErrorFile_name;
		
		Workbook xlsWb = null;
        Row row = null;
        Cell cell = null;
		
        // XLSX ?????? ??? ??????
    	if ("xlsx".equals(fileExt)) {
    		xlsWb = new XSSFWorkbook(); // Excel 2007 ??????
    	// XLS ?????? ??? ??????
    	} else if ("xls".equals(fileExt)) {
    		xlsWb = new HSSFWorkbook();  // Excel 2007 ?????? ??????
    	}
    		
        Sheet sheet1 = xlsWb.createSheet("Sheet0");
        CellStyle cellStyle = xlsWb.createCellStyle();
        
        // ????????? ??? ??????
        row = sheet1.createRow(0);        
        cell = row.createCell(0);
        cell.setCellValue("?????? ?????????");
        cell.setCellStyle(cellStyle); // ??? ????????? ??????
        
        // ?????? ??????
        for(int i=1; i<=resultErrorDataMsg.size(); i++){
        	row = sheet1.createRow(i);
        
        	cell = row.createCell(0);
        	cell.setCellValue(String.valueOf(((HashMap)((List) resultErrorDataMsg.get("excelComonMsg")).get(i-1)).get("errorMsg")));
        	cell.setCellStyle(cellStyle);
        
        	cell = row.createCell(1);
        	cell.setCellValue(String.valueOf(((HashMap)((List) resultErrorDataMsg.get("excelComonMsg")).get(i-1)).get("insData")));
        	cell.setCellStyle(cellStyle);
        }
        
        // excel ?????? ??????
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap returnMsg(List excelComonMsgs, String errorMsg, int excelTotCnt, int successCnt, int failCnt){
		HashMap rtMsg = new HashMap();
		if(failCnt > 0 ){
			rtMsg.put("excelComonMsg", excelComonMsgs);
			rtMsg.put("errorMsg" , errorMsg);
			rtMsg.put("excelTotCnt" , excelTotCnt);
			rtMsg.put("successCnt", successCnt);
			rtMsg.put("failCnt", failCnt);
		}else{
			rtMsg.put("errorMsg" , errorMsg);
			rtMsg.put("excelTotCnt" , excelTotCnt);
			rtMsg.put("successCnt", successCnt);
			rtMsg.put("failCnt", failCnt);
		}
		
		return rtMsg;
	}
	
	public static String tansCostDaytype(String checkstr, String str){
		if(checkstr.endsWith("cost") 
				|| checkstr.endsWith("money") 
				|| checkstr.endsWith("amount")
				|| checkstr.endsWith("charge")
		){
			str = str.replace(",", "");
		}else if(checkstr.toString().endsWith("day")){
			str = str.replace("-", "");
		}
		return str;
	}
	
}