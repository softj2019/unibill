package com.hsck.ubfw.component.excontent.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : ContentService.java
 * @Description : 공통 컨텐츠 interface Class
 * @Modification Information
 * @author 홍길동
 * @since 2016.10.04
 * @version 1.0.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일	     수정자		 수정내용
 *  ----------   --------    ---------------------------
 *  2016.10.04   홍길동	   	 최초 생성
 * </pre>	
 */
	
public interface ExcontentService {
	public int selectcfgIdCnt(HashMap paramObj) throws Exception;
	
	public int selectcfgUserCnt(HashMap parameterObject) throws Exception;
	
	public int insertcfgUserReg(HashMap parameterObject) throws Exception;
	
	public String selectRoleId(HashMap parameterObject) throws Exception;
	
	public String selectId(HashMap parameterObject) throws Exception;
	
	public int insertSendMMS(HashMap parameterObject) throws Exception;
	
	public String selectPass(HashMap parameterObject) throws Exception;
	
	public int selectcfgUserCntPw(HashMap parameterObject) throws Exception;
}