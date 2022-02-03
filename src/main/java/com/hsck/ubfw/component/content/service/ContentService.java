package com.hsck.ubfw.component.content.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softcamp.cryptoLinker.Hash;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

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
	
public interface ContentService {
	
	/**
	 * 메뉴명 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public List<EgovMap> selectMenuInfo(Object parameterObject) throws Exception;

	/**
	 * 화면 안내문구 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public List<EgovMap> selectGuidList(Object parameterObject) throws Exception;
	
	/**
	 * 그리드 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */	
	public List<EgovMap> selectColumnsList(Object parameterObject) throws Exception;

	/**
	 * 그리드 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */	
	public List<EgovMap> selectDynamicColumnsList(Object parameterObject) throws Exception;
	
	/**
	 * 메뉴별 권한을 확인한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public int selectRoleMenu(Object parameterObject) throws Exception;
	
	/**
	 * 검색 조건 정보 조회를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */	
	public List<EgovMap> selectSearchObjectList(Object parameterObject) throws Exception;
	
	/**
	 * SQL String으로 데이터를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectDynamicDataList(Map map) throws Exception;

	@SuppressWarnings("rawtypes")
	public EgovMap getDynamicCodeData(Map map, String key) throws Exception;

	/**
	 * 데이터 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	public List<EgovMap> selectDataList(Object parameterObject, String xmlId, String tptype) throws Exception;
	
	/**
	 * 데이터 목록 총갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	public int selectDataListCnt(Object parameterObject, String xmlId, String tptype) throws Exception;

	/**
	 * 엑셀 다운로드 데이터를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectDataExcel(Object parameterObject, String xmlId) throws Exception;
	
	public void selectDataExcelHandler(Object parameterObject, String xmlId, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) throws Exception;
	
	public void selectSQLDataExcelHandler(Object parameterObject, String xmlId, List excel_title, String newfileName, String uploadPath, String fullName, String file_name) throws Exception;

	/**
	 * 데이터를 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap insertContent(HashMap parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public EgovMap insertContentHis(HashMap parameterObject) throws Exception;
	
	/**
	 * 데이터를 취소한다
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int insertHisResult(HashMap parameterObject) throws Exception;
	
	/**
	 * 데이터를 수정한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap updateContent(HashMap parameterObject) throws Exception;

	
	@SuppressWarnings("rawtypes")
	public EgovMap updateContentHis(HashMap parameterObject) throws Exception;
	
	/**
	 * 데이터를 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int deleteContent(HashMap parameterObject) throws Exception;
	
	
	/**
	 * 데이터를 취소한다
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int cancelContent(HashMap parameterObject) throws Exception;
	
	
	/**
	 * 데이터를 승인한다
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	@SuppressWarnings("rawtypes")
	public int confirmContent(HashMap parameterObject) throws Exception;
	
	
	/**
	 * 공통 컨텐츠 상세정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectContentDetail(Object parameterObject, String xmlId) throws Exception;

	/**
	 * 지우개 클릭시 초기화 대상 컬럼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectEraserTargetList(Object parameterObject) throws Exception;

	/**
	 * 즐겨찾기 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectBookMarkList(Object parameterObject) throws Exception;
	
	/**
	 * 즐겨찾기 위치 정보를 변경한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public int updateBookMarkPos(Object parameterObject) throws Exception;
	
	/**
	 * 즐겨찾기 정보를 등록한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public int insertBookMark(Object parameterObject) throws Exception;

	/**
	 * 즐겨찾기 정보를 삭제한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public int deleteBookMark(Object parameterObject) throws Exception;

	/** 탭메뉴 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectTabList(Object parameterObject) throws Exception;

	/**
	 * 탭메뉴에 해당되는 그리드 컬럼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	//public List<EgovMap> selectTabObjList(Object parameterObject) throws Exception;

	/**
	 * 테이블의 key 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectKeyColList(HashMap parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectTabKeyColList(HashMap parameterObject) throws Exception;
	
	
	/**
	 * 테이블의 insert한 ubseq 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String selectInsertUbseq(HashMap parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public String selectInsertUbseqNoKey(HashMap parameterObject) throws Exception;

	/**
	 * key 컬럼에 해당되는 키값을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
//	public List<EgovMap> selectKeyColValList(Object parameterObject) throws Exception;

	/**
	 * 탭 메뉴에 해당되는 그리드 DATA를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public List<EgovMap> selectTabDataList(Object parameterObject, String xmlId) throws Exception;
	
	/**
	 * 탭 메뉴에 해당되는 그리드 데이터 목록 총갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */	
	public int selectTabDataListCnt(Object parameterObject, String xmlId) throws Exception;

	/** 탭메뉴에 해당되는 버튼 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectTabBtnList(Object parameterObject) throws Exception;

	/** ubseq 번호를 생성하여 조회 한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */

	public List<EgovMap> selectAutoCompleteList(Object parameterObject) throws Exception;

	/**
	 * 객체id로 update 할 컬럼 및 파라메타 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap selectUpdateColInfo(Object parameterObject) throws Exception;
	
	/**
	 * 객체(컬럼)별 정보를 변경한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap updateObjColInfo(Object parameterObject) throws Exception;

	/**
	 * 공통 컨텐츠 일괄등록(엑셀 업로드) 처리 한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public EgovMap insertBundleUploadContent(HashMap parameterObject) throws Exception;

	/**
	 * obj_id 에 해당하는 sql 문 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap selectObjSqlList(Object parmObj) throws Exception;

	/**
	 * 화면id에 해당하는 sql 문 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public EgovMap getScrinSql(Object parameterObject) throws Exception;

    HashMap getScrinSqlClob(Object parmObj) throws Exception;

    /**
	 * 그리드 편집 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public List<EgovMap> selectGridEditInfoList(Object parmObj) throws Exception;

	/**
	 * 일괄변경 정보를 저장한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings("rawtypes")
	public int saveBndeApplc(HashMap paramObj) throws Exception;

	/**
	 * 공통 조회 인지 xml_id 조회인지 확인
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings("rawtypes")
	public String getScrinXmlIdSelect(HashMap parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public String getWhereCond(HashMap parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public String getMonWhereCond(HashMap parameterObject) throws Exception;

	/**
	 * DB 암/복호화
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	public String getCryptoData(HashMap<String, String> parmObj) throws Exception;
	
	public String getCryptoValDB(HashMap parmObj) throws Exception;

	/**
	 * ubseq 생성
	 * @param model
	 * @return ""
	 */
	public String getUbseqGen(HashMap<String, String> parmObj) throws Exception;
	
	
	public List<EgovMap> getFwformExcelheader(Object parmObj) throws Exception;
		
	public List<EgovMap> getRawInfo(Object parmObj) throws Exception;
	
	public List<EgovMap> getHiscolInfo(Object parmObj) throws Exception;
	
	public List<EgovMap> getRawDataList(Object parmObj) throws Exception;
	
	public List<EgovMap> getRawNm(Object parmObj) throws Exception;
	
	public List<EgovMap> selectHdList(Object parameterObject, String xmlId) throws Exception;
	
	public List<EgovMap> selectFixHDList(Object parameterObject) throws Exception;
	
	public EgovMap updateDerestrict(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectDerestrictResultList(HashMap parameterObject) throws Exception;

	 /* 즐겨찾기 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception	
	 */
	public List<EgovMap> selectPassRule(Object parameterObject) throws Exception;	
	
	public List<EgovMap> selectHdSumList(Object parameterObject, String xmlId) throws Exception;
	
	public List<EgovMap> selectDataSumList(Object parameterObject, String xmlId) throws Exception;
	
	public List<EgovMap> getXml(Object parameterObject, String xmlId) throws Exception;
	
	public int getRoleLevel(String xmlId, Object parameterObject) throws Exception;
	
	public int getMaxDepth(String xmlId, Object parameterObject) throws Exception;
	
	public List<EgovMap> selectcfgmail(String xmlId, Object parameterObject) throws Exception;

	public String getSeqStrGen(HashMap<String, String> parmObj) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public int insertHisuser(HashMap parameterObject) throws Exception;
	
	public EgovMap selectcfguser(HashMap parameterObject) throws Exception; 
	
	public EgovMap selectFileSeq(Object parmObj) throws Exception;
	
	public HashMap getcmfile(HashMap parameterObject) throws Exception;
	
	public int insertcmfile(HashMap parameterObject) throws Exception;
	public int insertodcontfile(HashMap parameterObject) throws Exception;
	
	public int insertcmnotice(HashMap parameterObject) throws Exception;
	
	public int filedetail(HashMap parameterObject) throws Exception;
	
	public int 	fileCnt(HashMap parameterObject) throws Exception;
	
	public int 	contfileCnt(HashMap parameterObject) throws Exception;
	
	public EgovMap getfileInfo(HashMap parameterObject) throws Exception;
	
	public EgovMap getimgfileInfo(HashMap parameterObject) throws Exception;
	
	public EgovMap getubseqfileInfo(HashMap parameterObject) throws Exception;

	public EgovMap selectFileGRP(Object parmObj) throws Exception;
	
	public List<EgovMap> selectFileList(HashMap paramObj) throws Exception;
	
	public int selectFileListCnt(HashMap paramObj) throws Exception;
	
	public List<EgovMap> getcmfileinfo(HashMap paramObj) throws Exception;
	
	public int deletecmfile(HashMap paramObj) throws Exception;
	
	public int deleteOdfile(HashMap paramObj) throws Exception;
	
	public int deleteContfile(HashMap paramObj) throws Exception;
	
	public int selectContfile(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectContfileInfo(HashMap paramObj) throws Exception;
	
	public int getinterval() throws Exception;

	/**
	 * 가변컬럼 여부 확인.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String selectDynamicColTbl(HashMap parameterObject) throws Exception;

	/**
	 * 상품 확인.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public String selectGd(HashMap parameterObject) throws Exception;
	
	public EgovMap getImgubseqInfo(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> getExt(HashMap paramObj) throws Exception;
	
	public List<EgovMap> getNoticeList(HashMap paramObj) throws Exception;
	
	public int getNoticeListCnt(HashMap paramObj) throws Exception;
	
	public EgovMap selectNoticeDetail(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> getFAQList(HashMap paramObj) throws Exception;
	
	public int getFAQListCnt(HashMap paramObj) throws Exception;
	
	public EgovMap selectFAQDetail(HashMap parameterObject) throws Exception;
	
	public EgovMap updateSync(HashMap parameterObject) throws Exception;
	
	public HashMap selectQuery (HashMap parameterObject) throws Exception;
	
	public HashMap ddlQuery (HashMap parameterObject) throws Exception;
	
	public HashMap selectQueryCnt (HashMap parameterObject) throws Exception;
	
	//인가된 단말에서만 접속 가능
	public int selectIpCnt(HashMap parameterObject) throws Exception;
	 
	//고객 ID 발부
	public String getCustIdGen(HashMap<String, String> parmObj) throws Exception;
	
	//blob파일 그룹 가져오기
	public HashMap getBlobfile(HashMap parameterObject) throws Exception;
	
	//통신사신청메일관리 저장
	public int insertBlobFile(HashMap parameterObject) throws Exception;

	//파읿순번 부여
	public String getFilesn(HashMap<String, Object> parmObj) throws Exception;
	
	//파일이름 가져오기
	public String getFilenm(HashMap<String, Object> parmObj) throws Exception;
	
	//Blob파일 정보 가져오기
	public HashMap getBlobfileInfo(HashMap parameterObject) throws Exception;
	
	//mail정보 검색
	public EgovMap selectmailInfo(HashMap parameterObject) throws Exception;
	
	//파일그룹 가져오기
	public String getFilegrp(HashMap<String, Object> parmObj) throws Exception;
	
	//메일데이터 삭제
	public int deletemailData(HashMap paramObj) throws Exception;
	
	//파일그룹 수정
	public EgovMap updatefilegrp(HashMap paramObj) throws Exception;
	
	//담당자 정보 검색
	public EgovMap selectManagerInfo(HashMap parameterObject) throws Exception;
	
	//수신자 검색
	public EgovMap selectFromEmail(HashMap parameterObject) throws Exception;
	
	//메일서버 정보 검색
	public EgovMap selectcfgmailInfo();
	
	public EgovMap getfunc(HashMap parameterObject) throws Exception;
	
	public List<HashMap> getproc(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectcheonggu(HashMap parameterObject) throws Exception;
	
	public int insertcheonggu(HashMap parameterObject) throws Exception;
	
	public EgovMap selectspbrn();
	
	public EgovMap updateGBlobFile(HashMap parameterObject) throws Exception;
	
	public EgovMap selectBmailBody(HashMap parameterObject) throws Exception;
	
	String sendSmsProc(Object parameterObject)  throws Exception;
	
	public int inserthisbill(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectMisu(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> remoteInsertData(String xmlId, HashMap parameterObject) throws Exception;
	
	public EgovMap updateStatus(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> kpsaSend(HashMap parameterObject) throws Exception;
	
	public EgovMap updategl(HashMap parameterObject) throws Exception;
	
	public EgovMap updategl2(HashMap parameterObject) throws Exception;
	
	public EgovMap updatetemp(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectgl(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selecttemp(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectgl2(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selecttemp2(HashMap parameterObject) throws Exception;
	
	public EgovMap updateglif(HashMap parameterObject) throws Exception;
	
	public int insertexcelReason(HashMap parameterObject) throws Exception;
	
	public List<EgovMap> selectRavs(HashMap parameterObject) throws Exception;
	
	public EgovMap updateRavs(HashMap parameterObject) throws Exception;
	
	public EgovMap updateRavs2(HashMap parameterObject) throws Exception;
	
	public EgovMap updateRavs3(HashMap parameterObject) throws Exception;
	
	public int 	tdaySeqCnt(HashMap parameterObject) throws Exception;
	
	public int 	depositCnt(HashMap parameterObject) throws Exception;
	
	public EgovMap updateDeposit(HashMap parameterObject) throws Exception;
}