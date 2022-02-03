package com.hsck.ubfw.component.com.cmm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileMngUtil {
		
	public static final int BUFF_SIZE = 2048;

	/**
	 *  NEW 파일명
	 */
	public static String newFileName() {
		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		rtnStr = sdfCurrent.format(ts.getTime());
		
		return rtnStr;
	}
		
	/**
	 * 업로드 첨부파일 파일명
	 */
	public @ResponseBody static Map<String, String> getFileInfo(HttpServletRequest request, String formId) {

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		String fileName = files.get(formId).getOriginalFilename();  // 파일명
		long fileSize   = files.get(formId).getSize();              // 파일사이즈
		
		Map<String, String> resultMap = new HashMap<String, String>();		
		resultMap.put("fileName", fileName);
		resultMap.put("fileSize", Long.toString(fileSize));
		
		return resultMap;
		

	}
			
	
	/**
	 * 첨부파일 삭제
	 */
	public static void fileDelete(String file_path) {
		// 파일삭제
	    try {				
			File uFile = new File(file_path);
	
			uFile.delete();	// 물리적 삭제
		} catch(Exception e) {}
	}
	
	/**
	 * 이전 업로드 파일 명
	 */
	public static String getFileName(HttpServletRequest request) {
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		
		Entry<String, MultipartFile> entry = itr.next();
		file = entry.getValue();
				
		return file.getOriginalFilename();
	}
	
	// 디렉토리 생성
	public static boolean createDirectory(String path) {
		boolean i = false;
		try {
			i = (new File(path)).mkdirs();
		} catch (Exception e) {
			i = false;
		}
		return i;
	}
	
	// BLOB 객체 읽기
	public static void makeBlobFileInWas(String filePath, String fileName, byte[] blobBytes) {
		FileOutputStream fos = null;
		try {	
			fos = new FileOutputStream(filePath + "/" + fileName);
			fos.write(blobBytes);
			fos.close();
		} catch (UnsupportedEncodingException e1) {

		} catch (FileNotFoundException e2) {

		} catch (IOException e3) {

		}finally{
		   try{
			    if(fos != null)fos.close();
		   }catch(IOException e){

		   }
		}
	}
}	