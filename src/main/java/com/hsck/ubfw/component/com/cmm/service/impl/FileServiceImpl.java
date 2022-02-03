package com.hsck.ubfw.component.com.cmm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.com.cmm.service.FileService;
import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.com.cmm.util.FileMngUtil;
import com.hsck.ubfw.component.com.cmm.vo.FileVO;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
	
@SuppressWarnings("deprecation")
@Service("fileService")
public class FileServiceImpl extends AbstractServiceImpl implements FileService {
	
	@Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;
	
	@Resource(name="commonMapper")
    private CommonMapper commonMapper; 
			
	// 파일 저장 한건 : 파일명까지 지정처리 (기존의 데이터가 있으면 덮어쓰기처리)
    public int fileInsert(HttpServletRequest request, String subPath, String fileNm, String fileFormId) throws Exception {
    	int result = 0;
	
    	/** validate request type */
		Assert.state(request instanceof MultipartHttpServletRequest, "request !instanceof MultipartHttpServletRequest");
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		/** extract files */
//		final Map<String, MultipartFile> files = multiRequest.getFileMap();
//		MultipartFile files = (MultipartFile) multiRequest.getFile("userfile2");
		MultipartFile files = (MultipartFile) multiRequest.getFile(fileFormId);
				
		Assert.notNull(files, "files is null");
//		Assert.state(files.size() > 0, "0 files exist");

		/** process files */
		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;

		File saveFolder = new File(uploadPath);

		// 디렉토리 생성 (보안 취약점 개선) 2009.04.02
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		
		if (!isDir) {
			String filePath = uploadPath + File.separator + fileNm;
			
			if (!"".equals(files.getOriginalFilename())) {
				files.transferTo(new File(filePath));
				result++;
			}			
		}  // if end
		

		return result;
    }
	
    // 첨부파일 저장
    public @ResponseBody Map<String, Object> saveApplyFile(Object paramObj, String subPath, String fileNm, String fileDelYn, LoginVO loginVo) throws Exception {
    	
    	String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath+File.separator+fileNm;
    	
    	
    	File attathFile = new File(uploadPath);
		attathFile.setWritable(true);		
		
		InputStream is = new FileInputStream(attathFile);
		byte[] atchmnflBytes = null;
		atchmnflBytes = IOUtils.toByteArray(is);
		
		is.close();

		// 파일 삭제
		if ("Y".equals(fileDelYn)) {
			FileMngUtil.fileDelete(uploadPath);
		}

    	Map<String, Object> resultMap = new HashMap<String, Object>();		
		resultMap.put("fileBytes", atchmnflBytes);
		
		return resultMap;
    }
    
    
    public int telcoFileInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception {
    	int result = 0;
	
    	/** validate request type */
		Assert.state(request instanceof MultipartHttpServletRequest,
				"request !instanceof MultipartHttpServletRequest");
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		/** extract files */
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		Assert.notNull(files, "files is null");
		Assert.state(files.size() > 0, "0 files exist");

		/** process files */
		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;

		File saveFolder = new File(uploadPath);

		// 디렉토리 생성 (보안 취약점 개선) 2009.04.02
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		if (!isDir) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
					.iterator();
			MultipartFile file;

			List fileInfoList = new ArrayList();
			String filePath;

			if (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();

				file = entry.getValue();
				if (!"".equals(file.getOriginalFilename())) {
					filePath = uploadPath + File.separator + fileNm;

					file.transferTo(new File(filePath));

					result++;
				}
			}
		}

		return 0;
    }
    
    
    public int telcoFileDRMInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception {
    	int DRMcode = 0;
	
    	/** validate request type */
		Assert.state(request instanceof MultipartHttpServletRequest,
				"request !instanceof MultipartHttpServletRequest");
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		/** extract files */
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		Assert.notNull(files, "files is null");
		Assert.state(files.size() > 0, "0 files exist");

		/** process files */
		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;

		File saveFolder = new File(uploadPath);

		// 디렉토리 생성 (보안 취약점 개선) 2009.04.02
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		if (!isDir) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
					.iterator();
			MultipartFile ofile;

			List fileInfoList = new ArrayList();
			String filePath;

			if (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();

				ofile = entry.getValue();
				if (!"".equals(ofile.getOriginalFilename())) {
					filePath = uploadPath + File.separator + fileNm;
					
					//원본파일 생성
					ofile.transferTo(new File(filePath));
					
					//DRM dec
					DRMcode = ContentUtil.encDRM(uploadPath, fileNm, uploadPath, "dec_"+fileNm);
					
					//result++;
				}
			}
		}

		return DRMcode;
    }

	// 파일 저장 한건 : 파일명까지 지정처리 (기존의 데이터가 있으면 덮어쓰기처리)
    public int useFileInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception {
    	int result = 0;
	
    	/** validate request type */
		Assert.state(request instanceof MultipartHttpServletRequest, "request !instanceof MultipartHttpServletRequest");
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		/** extract files */
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
				
		Assert.notNull(files, "files is null");

		/** process files */
		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;

		File saveFolder = new File(uploadPath);

		// 디렉토리 생성 (보안 취약점 개선) 2009.04.02
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		
		if (!isDir) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
					.iterator();
			MultipartFile file;

			String filePath;

			if (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();

				file = entry.getValue();
				if (!"".equals(file.getOriginalFilename())) {
					filePath = uploadPath + File.separator + fileNm;

					file.transferTo(new File(filePath));

					result++;
				}
			}
		}
		return result;
    }
    
    // 파일 정보 조회
    public FileVO selectFileInfo(FileVO vo) throws Exception {		
		return (FileVO) commonMapper.getSelect("file.selectFileInfo", vo);
	}
    
    // 파일 정보 조회
    public EgovMap selectNoticeFileInfo(FileVO vo) throws Exception {		
		return (EgovMap) commonMapper.getSelect("file.selectNoticeFileInfo", vo);
	}
    
}