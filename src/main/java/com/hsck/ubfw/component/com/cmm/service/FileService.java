package com.hsck.ubfw.component.com.cmm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hsck.ubfw.component.com.cmm.vo.FileVO;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : EgovSampleService.java
 * @Description : EgovSampleService Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀 *
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public interface FileService {

	// 파일 업로드 (파일명 지정)
    int fileInsert(HttpServletRequest request, String subPath, String fileNm, String fileFormId) throws Exception;
    	
    // 첨부파일 저장
    public Map<String, Object> saveApplyFile(Object paramObj, String subPath, String fileNm, String fileDelYn, LoginVO loginVo) throws Exception;

    // 통신사 파일 업로드
    int telcoFileInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception;
    int telcoFileDRMInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception;

    // 미수납 파일 업로드
    int useFileInsert(HttpServletRequest request, String subPath, String fileNm) throws Exception;
    
    // 파일 정보 조회
    FileVO selectFileInfo(FileVO vo) throws Exception;
    
    // 파일 정보 조회
    public EgovMap selectNoticeFileInfo(FileVO vo) throws Exception;
}