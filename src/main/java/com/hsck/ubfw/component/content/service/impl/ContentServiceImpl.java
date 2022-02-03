package com.hsck.ubfw.component.content.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.content.service.ContentService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : ContentServiceImpl.java
 * @Description : 공통 컨텐츠 Business Implement Class	
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

@Service("contentService")
public class ContentServiceImpl extends EgovAbstractServiceImpl implements ContentService {

	@Resource(name="commonMapper")
    private CommonMapper commonMapper; 
	
	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;
	
	@Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;
	
	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
		
	/**
	 * 메뉴명 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectMenuInfo(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectMenuInfo", parameterObject);        
    }
	
	/**
	 * 화면 안내문구 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectGuidList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectGuidList", parameterObject);
	}
	
	/**
	 * 그리드 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectColumnsList(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectColumnsList", parameterObject);
	}

	/**
	 * 그리드 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectDynamicColumnsList(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectDynamicColumnsList", parameterObject);
	}
	
	/**
	 * 화면 안내문구 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public int selectRoleMenu(Object parameterObject) throws Exception {		
		return commonMapper.getSelectCnt("content.selectRoleMenu", parameterObject);
	}
	
	/**
	 * 검색 조건 정보 조회를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectSearchObjectList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectSearchObjectList", parameterObject);
	}
	
	/**
	 * SQL String으로 데이터를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectDynamicDataList(Map map) throws Exception {
		String sXmlId = "";

		// 기준정보 조회
		if (map.get("cdGun") == "xml_id") {
			sXmlId = "common." + map.get("xmlid");
		} else if (map.get("cdGun") == "grp_cd") {  // 기초코드 조회
			sXmlId = "common.selectBaseCd";
		} else if (map.get("cdGun") == "grp_type") {  // 그룹코드 조회
			sXmlId = "common.selectGrpIdCd";
		} else if (map.get("cdGun") == "sql") {  // SQL 문
			sXmlId = "common.selectDynamicDataList";
		}
		
		List<EgovMap> result = new ArrayList<EgovMap>();
		List<EgovMap> re = new ArrayList<EgovMap>();
		EgovMap rmap = new EgovMap();
		EgovMap rmap2 = new EgovMap();
		
		if(map.get("cdGun") == "xml_id" && map.get("xmlid") == "selectYnCode"){
			rmap.put("code", "Y");
			rmap.put("name", "예");
			result.add(rmap);
			
			rmap2.put("code", "N");
			rmap2.put("name", "아니오");
			result.add(rmap2);
		}else{
			re = (List<EgovMap>) commonMapper.getSelectList(sXmlId, map);
			for(int i=0; i<re.size(); i++){
				result.add(re.get(i));
			}
		}
		
		return result;
	}
	
	@Override
	public EgovMap getDynamicCodeData(Map map, String key) throws Exception {
		EgovMap returnMap = new EgovMap();
		List<EgovMap> dynamicCoList = null;
		dynamicCoList = selectDynamicDataList(map);
		if( null != dynamicCoList ){

			for(EgovMap eMap : dynamicCoList ){

				if( ((String)eMap.get("dtlCd")).equals( key ) ){

					returnMap = eMap;
				}
			}
		}

		return returnMap;
	}

	/**
	 * 데이터 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectDataList(Object parameterObject, String xmlId, String tptype) throws Exception {
		try{					
			// 템플릿이 통계(E)인 경우
			if ("E".equals(tptype)) {
	
				return (List<EgovMap>) commonMapper.getSelectList("common.selectDynamicDataList", parameterObject);
						
			}else if("B".equals(tptype)) {
				String sXmlId = "content.selectBDataList";
				if (!"".equals(xmlId)) {
					sXmlId = xmlId;
				}
				
				return (List<EgovMap>) commonMapper.getSelectList(sXmlId, parameterObject);
			}else if (!"E".equals(tptype)) {  // 템플릿이 통계(E)이 아닌 경우
				
				String sXmlId = "content.selectDataList";
				if (!"".equals(xmlId)) {
					sXmlId = xmlId;
				}
				
				return (List<EgovMap>) commonMapper.getSelectList(sXmlId, parameterObject);
		
			}
		}catch(Exception e){
			return null;
		}
		
		return null;
				
	}

	/**
	 * 데이터 목록 총갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public int selectDataListCnt(Object parameterObject, String xmlId, String tptype) throws Exception {
		try{
			// 템플릿이 통계(E)인 경우
			if ("E".equals(tptype)) {
	
				return commonMapper.getSelectCnt("common.selectDynamicDataListCnt", parameterObject);
				
			} else if (!"E".equals(tptype)) {  // 템플릿이 통계(E)이 아닌 경우
				
	        	String sXmlId = "content.selectDataListCnt";
				if (!"".equals(xmlId)) {
					sXmlId = xmlId + "Cnt";
				}
				
				return commonMapper.getSelectCnt(sXmlId, parameterObject);
	        	
	        }
		}catch(Exception e){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 엑셀 다운로드 데이터를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectDataExcel(Object parameterObject, String xmlId) throws Exception {
		
		String sXmlId = "content.selectDataExcel";
		if (!"".equals(xmlId)) {
			sXmlId = xmlId + "Excel";
		}
		
		return (List<EgovMap>) commonMapper.getSelectList(sXmlId, parameterObject);
	}
	
	@SuppressWarnings("unchecked")
	public void selectDataExcelHandler(Object parameterObject, String xmlId, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) throws Exception {
		
		String sXmlId = "content.selectDataExcel";
		if (!"".equals(xmlId)) {
			sXmlId = xmlId + "Excel";
		}
		
		commonMapper.getSelectListHandler(sXmlId, parameterObject, excel_title, newfileName, uploadPath, fullName, file_name);
		
//		return (List<EgovMap>) commonMapper.getSelectListHandler(sXmlId, parameterObject);
	}
	
	@SuppressWarnings("unchecked")
	public void selectSQLDataExcelHandler(Object parameterObject, String xmlId, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) throws Exception {
		commonMapper.getSQLSelectListHandler(xmlId, parameterObject, excel_title, newfileName, uploadPath, fullName, file_name);
	}
	
	
	/**
	 * 데이터를 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap  insertContent(HashMap parameterObject) throws Exception {

	   	int result    = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.insertContent";
	   	
	   	EgovMap map = new EgovMap();

	   	try {	   			
	   			if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Insert";
	   			}
	   		
				result = commonMapper.insertData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
					map.put("errorMsg",  "");					
				} else {
					map.put("resultMsg", "false");
					map.put("errorMsg",  "");
				}

			} catch (Exception e) {
//				throw new Exception(e);
				if (e.getCause() != null && e.getCause().getCause() != null) {
		            if (e.getCause().getCause() instanceof SQLException) {
		                SQLException se = (SQLException) e.getCause().getCause();
		                
		                map.put("resultMsg", "false");
		                map.put("errorMsg",  se.getMessage());
		            }
				}
			} finally {}
	   	
	   	    return map;
	}
	
	
	/**
	 * 데이터를 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap  insertContentHis(HashMap parameterObject) throws Exception {

	   	int result    = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.insertContentHis";
	   	
	   	EgovMap map = new EgovMap();

	   	try {	   			
	   			if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Insert";
	   			}
	   		
				result = commonMapper.insertData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
					map.put("errorMsg",  "");	 
				} else {
					map.put("resultMsg", "false");
					map.put("errorMsg",  "");
				}

			} catch (Exception e) {

				if (e.getCause() != null && e.getCause().getCause() != null) {
		            if (e.getCause().getCause() instanceof SQLException) {
		                SQLException se = (SQLException) e.getCause().getCause();
		                
		                map.put("resultMsg", "false");
		                map.put("errorMsg",  se.getMessage());
		            }
				}
			} finally {}
	   	
	   	    return map;
	}

	
	@SuppressWarnings("rawtypes")
	public int  insertHisResult(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("main.insertHisResult", parameterObject);   
	}
	
	
	/**
	 * 데이터를 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap  updateContent(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.updateContent";

	   	EgovMap map = new EgovMap();
	   	
	   	try {
		   		if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Update";
	   			}
	   		
				result = commonMapper.updateData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
					map.put("errorMsg",  "");
				} else {
					map.put("resultMsg", "false");
					map.put("errorMsg",  "");
				}

	   	} catch (Exception e) {

			if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	
	/**
	 * 데이터를 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap  updateContentHis(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.updateContentHis";

	   	EgovMap map = new EgovMap();
	   	
	   	try {
		   		if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Update";
	   			}
	   		
				result = commonMapper.updateData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
					map.put("errorMsg",  "");
				} else {
					map.put("resultMsg", "false");
					map.put("errorMsg",  "");
				}

	   	} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	

	/**
	 * 데이터를 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int  cancelContent(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.cancelContent"; 
	   	try {
		   		if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Cancel";
	   			}
	   		
				result = commonMapper.updateData(sXmlId, parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	@SuppressWarnings("rawtypes")
	public int  confirmContent(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.confirmContent"; 
	   	try {
		   		if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Confirm";
	   			}
	   		
				result = commonMapper.updateData(sXmlId, parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	/**
	 * 데이터를 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int  deleteContent(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
		String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.deleteContent";

	   	try {
		   		if (!"".equals(xmlId)) {
	   				sXmlId = xmlId + "Delete";
	   			}
	   		
				result = commonMapper.deleteData(sXmlId, parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	
	/**
	 * 공통 컨텐츠 상세정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectContentDetail(Object parameterObject, String xmlId) throws Exception {
		String sXmlId = "content.selectContentDetail";		

		if (!"".equals(xmlId)) {
			sXmlId = xmlId + "Detail";
		}		
		
		return (List<EgovMap>) commonMapper.getSelectList(sXmlId, parameterObject);
	}
	
	/**
	 * 지우개 클릭시 초기화 대상 컬럼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectEraserTargetList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectEraserTargetList", parameterObject);
	}
	
	/**
	 * 즐겨찾가 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectBookMarkList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectBookMarkList", parameterObject);
	}
	
	/**
	 * 즐겨찾기 위치 정보를 변경한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public int  updateBookMarkPos(Object parameterObject) throws Exception {
	   	int result = 0;

	   	try {
				result = commonMapper.deleteData("content.updateBookMarkPos", parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	
	/**
	 * 즐겨찾기 정보를 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public int  insertBookMark(Object parameterObject) throws Exception {
	   	int result = 0;

	   	try {
				result = commonMapper.deleteData("content.insertBookMark", parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	
	/**
	 * 즐겨찾기 정보를 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public int  deleteBookMark(Object parameterObject) throws Exception {
	   	int result = 0;

	   	try {
				result = commonMapper.deleteData("content.deleteBookMark", parameterObject);

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	    return result;
	}
	
	/** 탭메뉴 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectTabList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectTabList", parameterObject);
	}
	
	/**
	 * 탭메뉴에 해당되는 그리드 컬럼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectTabObjList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectTabObjList", parameterObject);
	}
	*/
	
	/**
	 * 테이블의 key 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EgovMap> selectKeyColList(HashMap parameterObject) throws Exception {		
		String owner = fileUploadProperties.getProperty("Globals.UserName");		
		parameterObject.put("owner", owner);
		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectKeyColList", parameterObject);
	}
	
	/**
	 * key 컬럼에 해당되는 키값을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 *//*
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectKeyColValList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectKeyColValList", parameterObject);
	}*/
	
	/**
	 * 탭 메뉴에 해당되는 그리드 DATA를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectTabDataList(Object parameterObject, String xmlId) throws Exception {
		
		String sXmlId = "content.selectTabDataList";
		if (!"".equals(xmlId)) {
			sXmlId = xmlId;
		}
		
		return (List<EgovMap>) commonMapper.getSelectList(sXmlId, parameterObject);		
	}
	
	/**
	 * 탭 메뉴에 해당되는 그리드 데이터 목록 총갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	public int selectTabDataListCnt(Object parameterObject, String xmlId) throws Exception {
		
		String sXmlId = "content.selectTabDataListCnt";
		if (!"".equals(xmlId)) {
			sXmlId = xmlId + "Cnt";
		}
		
		return commonMapper.getSelectCnt(sXmlId, parameterObject);
	}
	
	/** 탭메뉴에 해당되는 버튼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectTabBtnList(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectTabBtnList", parameterObject);
	}
	


	/** AutoComplete 기능으로 조회 조건을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List selectAutoCompleteList(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectAutoCompleteList", parameterObject);
	}
	
	/**
	 * 객체id로 update 할 컬럼 및 파라메타 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public EgovMap selectUpdateColInfo(Object parameterObject) throws Exception {		
		return (EgovMap) commonMapper.getSelect("content.selectUpdateColInfo", parameterObject);
	}
	
	/**
	 * 객체(컬럼)별 정보를 변경한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap  updateObjColInfo(Object parameterObject) throws Exception {
	   	int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateObjColInfo", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}	
	
	/**
	 * 공통 컨텐츠 일괄등록(엑셀 업로드) 처리 한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap insertBundleUploadContent(HashMap parameterObject) throws Exception {
	   	int result = 0;
	   	String xmlId  = (String) parameterObject.get("xmlId");
	   	String sXmlId = "content.insertBundleUploadContent";

	   	EgovMap map = new EgovMap();
	   	
	   	try {
		   		if (xmlId !=null && !"".equals(xmlId)) {
	   				sXmlId = xmlId + "Upload";
	   			}

				result = commonMapper.insertData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("uploadResult", "true");
				} else {
					map.put("uploadResult", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("uploadResult", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
	   	
		return map;
	}
	
	/**
	 * obj_id 에 해당하는 sql 문 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap selectObjSqlList(Object parmObj) throws Exception {		
		return (EgovMap) commonMapper.getSelect("content.selectObjSqlList", parmObj);
	}
	
	/**
	 * 화면id에 해당하는 sql 문 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap getScrinSql(Object parmObj) throws Exception {
		return (EgovMap) commonMapper.getSelect("content.getScrinSql", parmObj);
	}
	@Override
	public HashMap getScrinSqlClob(Object parmObj) throws Exception {
		return (HashMap) commonMapper.getSelect("content.getScrinSqlClob", parmObj);
	}
	
	/**
	 * 그리드 편집 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List selectGridEditInfoList(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectGridEditInfoList", parameterObject);
	}
	
	/**
	 * 일괄변경 정보를 저장한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings("rawtypes")
	public int saveBndeApplc(HashMap paramObj) throws Exception {
	   	int result = 0;

	   	try {	   			
	   			String type  = (String) paramObj.get("type");
	   			String xmlId = (String) paramObj.get("xmlId");
	   			
	   			if (!"xmlId".equals(xmlId)) {
	   				if ("I".equals(type)) {
	   					result = commonMapper.insertData(xmlId, paramObj);
	   				} else if ("U".equals(type)) {
	   					result = commonMapper.updateData(xmlId, paramObj);
	   				} else if ("D".equals(type)) {
	   					result = commonMapper.deleteData(xmlId, paramObj);
	   				}
	   			} else { 
	   				result = 0;
	   			}	

			} catch (Exception e) {
				throw new Exception(e);
			}
	   	
	   	return result;
	}
	
	/**
	 * 공통 조회 인지 xml_id 조회인지 확인
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getScrinXmlIdSelect(HashMap parameterObject) throws Exception {		
    	return (String) commonMapper.getSelect("content.getScrinXmlIdSelect", parameterObject);
    }
	/**
	 * 공통 조회 인지 xml_id 조회인지 확인
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getWhereCond(HashMap parameterObject) throws Exception {		
    	return (String) commonMapper.getSelect("content.getWhereCond", parameterObject);
    }
	
	@SuppressWarnings("rawtypes")
	public String getMonWhereCond(HashMap parameterObject) throws Exception {		
    	return (String) commonMapper.getSelect("content.getMonWhereCond", parameterObject);
    }
	
	/**
	 * DB 암/복호화
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getCryptoData(HashMap parameterObject) throws Exception {		
    	return (String) commonMapper.getSelect("content.getCryptoData", parameterObject);
    }
	
	/**
	 * DB 암/복호화
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getCryptoValDB(HashMap parameterObject) throws Exception {		
    	return (String) commonMapper.getSelect("content.getCryptoValDB", parameterObject);
    }
	
	/**
	 * ubseq 생성
	 * @param model
	 * @return ""
	 */
	@SuppressWarnings("rawtypes")
	public String getUbseqGen(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.getUbseqGen", parmObj);
    }
	
	/**
	 * ubseq 생성
	 * @param model
	 * @return ""
	 */
	@SuppressWarnings("rawtypes")
	public String getSeqStrGen(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.getSeqStrGen", parmObj);
    }
	/**
	 * insert한 ubseq 조회
	 * @param model
	 * @return ""
	 */
	@SuppressWarnings("rawtypes")
	public String selectInsertUbseq(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.selectInsertUbseq", parmObj);
    }
	
	@SuppressWarnings("rawtypes")
	public String selectInsertUbseqNoKey(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.selectInsertUbseqNoKey", parmObj);
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getFwformExcelheader(Object parmObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectfwformHeaderList", parmObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getRawInfo(Object parmObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectRawCdr", parmObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getHiscolInfo(Object parmObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectHisColcd", parmObj);
	}	
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getRawDataList(Object parmObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.getRawDataList", parmObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getRawNm(Object parmObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectRawNm", parmObj);
	}
	
	/**
	 * 고정 헤더를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectFixHDList(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("content.selectFixHDList", parameterObject);
	}
	
	/**
	 * 가변컬럼 헤더를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectHdList(Object parameterObject, String xmlId) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList(xmlId+"Hd", parameterObject);
	}
	
	@SuppressWarnings("rawtypes")
	public EgovMap  updateDerestrict(HashMap parameterObject) throws Exception {
	   	
		int result = 0;
	   	String sXmlId = "content.updateDerestrict";

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData(sXmlId, parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
					map.put("errorMsg",  "");
				} else {
					map.put("resultMsg", "false");
					map.put("errorMsg",  "");
				}

	   	} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectDerestrictResultList(HashMap parameterObject) throws Exception {
	   	return (List<EgovMap>) commonMapper.getSelectList("content.selectDerestrictResultList", parameterObject);
	}
	
	
	/**
	 * 계정관리 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectPassRule(Object parameterObject) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("content.selectPassRule", parameterObject);
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectHdSumList(Object parameterObject, String xmlId) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList(xmlId+"HdSum", parameterObject);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectDataSumList(Object parameterObject, String xmlId) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList(xmlId+"DataSum", parameterObject);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> getXml(Object parameterObject, String xmlId) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList(xmlId, parameterObject);
	}
	
	@SuppressWarnings({ "unchecked" })
	public int getRoleLevel(String xmlId, Object parameterObject) throws Exception {
		return (int) commonMapper.getSelectCnt(xmlId, parameterObject);
	}
	
	@SuppressWarnings({ "unchecked" })
	public int getMaxDepth(String xmlId, Object parameterObject) throws Exception {
		int result = 0;
		try {
			result = commonMapper.getSelectCnt(xmlId, parameterObject);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectcfgmail(String xmlId, Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList(xmlId, parameterObject);
	}

	@Override
	public int insertHisuser(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("content.insertHisuser", parameterObject);
	}
	
	public EgovMap selectcfguser(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectcfguser", parameterObject);
	}
	
	public EgovMap selectFileSeq(Object parmObj) throws Exception{
		return commonMapper.selectOne("content.selectFileSeq", parmObj);
	}
	
	public HashMap getcmfile(HashMap parameterObject) throws Exception{
		return commonMapper.selectOne("content.getcmfile", parameterObject);
	}
	
	public int insertcmfile(HashMap parameterObject) throws Exception{
		return commonMapper.insertData("content.insertcmfile", parameterObject);
	}
	public int insertodcontfile(HashMap parameterObject) throws Exception{ 
		int result = 0;
		if(parameterObject.get("fgrp").equals("O")){
			result = commonMapper.insertData("content.insertodcontfile", parameterObject);
		}else if(parameterObject.get("fgrp").equals("C")){
			result = commonMapper.insertData("content.insertodcontfile", parameterObject);
		}else if(parameterObject.get("fgrp").equals("M")){
			result = commonMapper.insertData("content.mailfile", parameterObject);
		}
		return result;
	}
	
	public int insertcmnotice(HashMap parameterObject) throws Exception{
		return commonMapper.insertData("content.insertcmnotice", parameterObject);
	}
	
	public int filedetail(HashMap parameterObject) throws Exception{
		int result = 0;
		if(parameterObject.get("fgrp").equals("O")){
			result = commonMapper.insertData("content.odfile", parameterObject);
		}else if(parameterObject.get("fgrp").equals("C")||parameterObject.get("fgrp").equals("J")){
			result = commonMapper.insertData("content.contfile", parameterObject);
		}else if(parameterObject.get("fgrp").equals("M")){
			result = commonMapper.insertData("content.mailfile", parameterObject);
		}
		return result;
		
	};
	
	
	
	public int fileCnt(HashMap parameterObject) throws Exception{
		return (int) commonMapper.getSelectCnt("content.fileCnt", parameterObject);
		
	}
	
	public int contfileCnt(HashMap parameterObject) throws Exception{
		return (int) commonMapper.getSelectCnt("content.contfileCnt", parameterObject);
		
	}
	
	public EgovMap getfileInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.getfileInfo", parameterObject);
	}
	
	public EgovMap getimgfileInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.getubseqimgfileInfo", parameterObject);
	}
	
	public EgovMap getubseqfileInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.getubseqfileInfo", parameterObject);
	}
	
	public EgovMap selectFileGRP(Object parmObj) throws Exception{
		return commonMapper.selectOne("content.selectFileGRP", parmObj);
	}

	public List<EgovMap> selectFileList(HashMap paramObj) throws Exception{
		List<EgovMap> result = new ArrayList<EgovMap>();
		if(paramObj.get("filegrp").toString().equals("O")){
			result = commonMapper.getSelectList("content.selectOdFileList", paramObj);
		}else if(paramObj.get("filegrp").toString().equals("C")){
			result = commonMapper.getSelectList("content.selectContFileList", paramObj);
		}else if(paramObj.get("filegrp").toString().equals("N")){
			result = commonMapper.getSelectList("content.selectFileList", paramObj);
		}else{
			
		}
		
		return result;
		
	}
	
	public int selectFileListCnt(HashMap paramObj) throws Exception{
		int result = 0;
		if(paramObj.get("filegrp").toString().equals("O")){
			result = (int) commonMapper.getSelectCnt("content.selectOdFileListCnt", paramObj);
		}else if(paramObj.get("filegrp").toString().equals("C")){
			result = (int) commonMapper.getSelectCnt("content.selectContFileListCnt", paramObj);
		}else if(paramObj.get("filegrp").toString().equals("M")){
			
		}
		
		return result;
	}
	
	public List<EgovMap> getcmfileinfo(HashMap paramObj) throws Exception{
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectCmfileList", paramObj);
		
	}
	
	public int deletecmfile(HashMap paramObj) throws Exception{
		return commonMapper.deleteData("content.deleteCmfile", paramObj);
	}
	
	public int deleteOdfile(HashMap paramObj) throws Exception{
		return commonMapper.deleteData("content.deleteOdfile", paramObj);
	}
	
	public int deleteContfile(HashMap paramObj) throws Exception{
		return commonMapper.deleteData("content.deleteContfile", paramObj);
	}
	
	public int selectContfile(HashMap paramObj) throws Exception{
		return (int) commonMapper.getSelectCnt("content.chkContfile", paramObj);
	};
	
	public List<EgovMap> selectContfileInfo(HashMap paramObj) throws Exception{
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectContfileInfo", paramObj);
		
	}

	public int getinterval() throws Exception {
		HashMap paramObj = new HashMap();
		return (int) commonMapper.getSelectCnt("content.getinterval", paramObj);
	}

	/**
	 * 상품 확인
	 * @param model
	 * @return ""
	 */
	@SuppressWarnings("rawtypes")
	public String selectGd(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.selectGd", parmObj);
    }
	
	/**
	 * 가변컬럼 여부 확인
	 * @param model
	 * @return ""
	 */
	@SuppressWarnings("rawtypes")
	public String selectDynamicColTbl(HashMap parmObj) throws Exception {		
    	return (String) commonMapper.getSelect("content.selectDynamicColTbl", parmObj);
    }
	
	public EgovMap getImgubseqInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.getImgubseqInfo", parameterObject);
	}
	
	public List<EgovMap> getExt(HashMap paramObj) throws Exception{
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.getExt", paramObj);
		
	}
	
	public List<EgovMap> getNoticeList(HashMap paramObj) throws Exception{
		return commonMapper.getSelectList("content.getNoticeList", paramObj);
		
	}
	
	public int getNoticeListCnt(HashMap paramObj) throws Exception {
		return (int) commonMapper.getSelectCnt("content.getNoticeListCnt", paramObj);
	}
	
	public EgovMap selectNoticeDetail(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectNoticeDetail", parameterObject);
	}
	
	public List<EgovMap> getFAQList(HashMap paramObj) throws Exception{
		return commonMapper.getSelectList("content.getFAQList", paramObj);
		
	}
	
	public int getFAQListCnt(HashMap paramObj) throws Exception {
		return (int) commonMapper.getSelectCnt("content.getFAQListCnt", paramObj);
	}
	
	public EgovMap selectFAQDetail(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectFAQDetail", parameterObject);
	}

 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EgovMap> selectTabKeyColList(HashMap parameterObject) throws Exception {		
		return commonMapper.getSelectList("content.selectTabKeyColList", parameterObject); 
	}
		
	public EgovMap updateSync(HashMap parameterObject) throws Exception {
		EgovMap map = new EgovMap();
		int result = commonMapper.updateData("content.updateSync", parameterObject);
		
		if (result > 0) {
			map.put("resultMsg", "true");
			map.put("errorMsg",  "");
		} else {
			map.put("resultMsg", "false");
			map.put("errorMsg",  "");
		}

		return map;
	}
	
	
	public HashMap selectQueryCnt(HashMap parameterObject) throws Exception {
		HashMap rs = new HashMap();
		
		try {
			rs = commonMapper.selectOne("content.exeQueryCnt", parameterObject);
			if(rs.size() != 0 ){
				rs.put("total_count", rs.get("cnt"));
			}
			
		} catch (Exception e) {
			rs.put("errMsg", "[ERROR] " + e.toString());
		}
		
		return rs;
	}
	
	public HashMap selectQuery(HashMap parameterObject) throws Exception {
		HashMap rs = new HashMap();
		
		List<LinkedHashMap> rsList = new ArrayList<LinkedHashMap>();
		
		try {
			rsList = commonMapper.selectList("content.exeQuery", parameterObject);
			if(rsList.size() != 0 ){
				ArrayList keyList =  new ArrayList();
				ArrayList keyCamelList =  new ArrayList();
				ArrayList<HashMap> keyColnmList =  new ArrayList<HashMap>();
				Set key = rsList.get(0).keySet();
				
				for(Iterator iter = key.iterator(); iter.hasNext();){
					String keyNm = (String)iter.next();
					keyList.add(keyNm);
					keyCamelList.add(CamelUtil.convert2CamelCase(keyNm));
					HashMap tmp = new HashMap();
					tmp.put("colId", keyNm);
					keyColnmList.add(tmp);
				}
				rs.put("keyList", keyList);
				rs.put("keyCamelList", keyCamelList);
				rs.put("rsList", rsList);
				rs.put("colId", keyColnmList);
				
			}
		} catch (Exception e) {
			rs.put("errMsg", "[ERROR] " + e.toString());
		}
		
		return rs;
	}
	
	public HashMap ddlQuery(HashMap parameterObject) throws Exception {
		HashMap rs = new HashMap();
		String command = parameterObject.get("command").toString();
		String sqlType = parameterObject.get("sqlType").toString();
		
		List<LinkedHashMap> rsList = new ArrayList<LinkedHashMap>();
		int rscnt = 0;
		try {
			rscnt = commonMapper.update("content.exeQuery", parameterObject);
			if(rscnt == 0){
				if(command.equals("update") || command.equals("delete")) {
					rs.put("errMsg", "[SUCESS] 수정된 로우가 없습니다.");
				}else if(command.equals("create")) {
					if(sqlType.equals("P")) {
						rs.put("errMsg", "[SUCESS] 프로시저및 함수가 생성되었습니다.");
					}else {
						rs.put("errMsg", "[SUCESS] 테이블이 생성되었습니다.");
					}
					
				}else {
					rs.put("errMsg", "[ERROR] 쿼리가 실패하였습니다.");
				}
			}else {
				rs.put("errMsg", "[SUCESS] 쿼리가 성공하였습니다.");
				rs.put("ddlcnt", rscnt);
			}
			

		} catch (Exception e) {
			rs.put("errMsg", "[ERROR] " + e.toString());
		}
		
		return rs;
	}
	
	//인가된 단말에서만 접속 가능
	@SuppressWarnings("unchecked")
	@Override
	public int selectIpCnt(HashMap parameterObject) throws Exception {
		return (int) commonMapper.getSelectCnt("content.selectIpCnt", parameterObject);
	}
	
	//고객 ID 발부
	@Override
	public String getCustIdGen(HashMap<String, String> parmObj) throws Exception {
		return (String) commonMapper.getSelect("content.getCustIdGen", parmObj);
	}
	
	//blob파일 그룹 가져오기
	@Override
	public HashMap getBlobfile(HashMap parameterObject) throws Exception{
		return commonMapper.selectOne("content.getBlobfile", parameterObject);
	}
	
	//통신사신청메일관리 저장	
	@Override
	public int insertBlobFile(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("content.insertBlobFile", parameterObject);
	}
	
	//파읿순번 부여
	@Override
	public String getFilesn(HashMap<String, Object> parmObj) throws Exception {
		return (String) commonMapper.getSelect("content.getFilesn", parmObj);
	}
	
	//파일이름 가져오기
	public String getFilenm(HashMap<String, Object> parmObj) throws Exception {
		return (String) commonMapper.getSelect("content.getFilenm", parmObj);
	}
	
	//Blob파일 정보 가져오기
	@Override
	public HashMap getBlobfileInfo(HashMap parameterObject) throws Exception{
		return commonMapper.selectOne("content.getBlobfileInfo", parameterObject);
	}
	
	//mail정보 검색
	@Override
	public EgovMap selectmailInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectmailInfo", parameterObject);
	}
	
	//파일그룹 가져오기
	@Override
	public String getFilegrp(HashMap<String, Object> parmObj) throws Exception {
		return (String) commonMapper.getSelect("content.getFilegrp", parmObj);
	}
	
	//메일데이터 삭제
	@Override
	public int deletemailData(HashMap paramObj) throws Exception{
		return commonMapper.deleteData("content.deletemailData", paramObj);
	}
	
	//파일그룹 수정
	@Override
	public EgovMap updatefilegrp(HashMap paramObj) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updatefilegrp", paramObj);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	//담당자 정보 검색
	@Override
	public EgovMap selectManagerInfo(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectManagerInfo", parameterObject);
	}
	
	//수신자 검색
	@Override
	public EgovMap selectFromEmail(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectFromEmail", parameterObject);
	}
	
	//메일서버 정보 검색
	@Override
	public EgovMap selectcfgmailInfo(){
		return commonMapper.selectOne("content.selectcfgmailInfo");
	}
	
	public EgovMap getfunc(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.getfunc", parameterObject);
	}
	
	public List<HashMap> getproc(HashMap parameterObject) throws Exception {
		List<HashMap> rs = new ArrayList<HashMap>();
		try {
			rs = commonMapper.selectList("content.getproc", parameterObject);
			System.out.println("prc done");
		} catch (Exception e) {
			System.out.println("prc err");
			HashMap msg = new HashMap();
			msg.put("RESULT_MSG", e.getCause().toString());
			rs.add(msg);
		}
		return rs;
	}
	
	@Override
	public List<EgovMap> selectcheonggu(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectcheonggu", parameterObject);
	}
	
	@Override
	public int insertcheonggu(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("content.insertcheonggu", parameterObject);
	}
	
	@Override
	public EgovMap selectspbrn() {
		return commonMapper.selectOne("content.selectspbrn");
	}
	
	@Override
	public EgovMap updateGBlobFile(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateGBlobFile", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public EgovMap selectBmailBody(HashMap parameterObject) throws Exception {
		return commonMapper.selectOne("content.selectBmailBody", parameterObject);
	}
	
	@Override
	public String sendSmsProc(Object parameterObject) throws Exception {
		String result = commonMapper.selectOne("content.sendSmsProc", parameterObject);
		return result;
	}
	
	@Override
	public int inserthisbill(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("content.inserthisbill", parameterObject);
	}
	
	@Override
	public List<EgovMap> selectMisu(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectMisu", parameterObject);
	}
	
	public List<EgovMap> remoteInsertData(String xmlId, HashMap parameterObject) throws Exception{
		System.out.println("query content.test start ");
		return commonMapper.getSelectList(xmlId, parameterObject);
	}
	
	@Override
	public EgovMap updateStatus(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateStatus", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	public List<EgovMap> kpsaSend(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.kpsaSend", parameterObject);
	}
	
	@Override
	public EgovMap updategl(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updategl", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public EgovMap updategl2(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updategl2", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof SQLException) {
                SQLException se = (SQLException) e.getCause().getCause();
                
                map.put("resultMsg", "false");
                map.put("errorMsg",  se.getMessage());
			}
		}
   	    return map;
	}
	
	@Override
	public EgovMap updatetemp(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updatetemp", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public List<EgovMap> selectgl(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectgl", parameterObject);
	}
	
	@Override
	public List<EgovMap> selecttemp(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selecttemp", parameterObject);
	}
	
	@Override
	public List<EgovMap> selectgl2(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectgl2", parameterObject);
	}
	
	@Override
	public List<EgovMap> selecttemp2(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selecttemp2", parameterObject);
	}
	
	@Override
	public EgovMap updateglif(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateglif", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public int insertexcelReason(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("content.insertexcelReason", parameterObject);
	}
	
	@Override
	public List<EgovMap> selectRavs(HashMap parameterObject) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		return commonMapper.getSelectList("content.selectRavs", parameterObject);
	}
	
	@Override
	public EgovMap updateRavs(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateRavs", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public EgovMap updateRavs2(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateRavs2", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	@Override
	public EgovMap updateRavs3(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateRavs3", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
	
	public int tdaySeqCnt(HashMap parameterObject) throws Exception{
		return (int) commonMapper.getSelectCnt("content.tdaySeqCnt", parameterObject);
	}
	
	public int depositCnt(HashMap parameterObject) throws Exception{
		return (int) commonMapper.getSelectCnt("content.depositCnt", parameterObject);
	}
	
	@Override
	public EgovMap updateDeposit(HashMap parameterObject) throws Exception {
		int result = 0;

	   	EgovMap map = new EgovMap();
	   	
	   	try {
				result = commonMapper.updateData("content.updateDeposit", parameterObject);
				
				if (result > 0) {
					map.put("resultMsg", "true");
				} else {
					map.put("resultMsg", "false");
				}

	   	} catch (Exception e) {	                
	   		if (e.getCause() != null && e.getCause().getCause() != null) {
	            if (e.getCause().getCause() instanceof SQLException) {
	                SQLException se = (SQLException) e.getCause().getCause();
	                
	                map.put("resultMsg", "false");
	                map.put("errorMsg",  se.getMessage());
	            }
			}
		} finally {}
   	
   	    return map;
	}
}

