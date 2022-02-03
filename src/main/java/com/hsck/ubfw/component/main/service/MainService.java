package com.hsck.ubfw.component.main.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.model.Menu;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name :MainService.java
 * @Description : 메인 interface Class
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
	
public interface MainService {
		
	/**
	 * 사용자 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public LoginVO searchUserInfo(LoginVO vo) throws Exception;

    LoginVO searchUserInfo2(LoginVO vo) throws Exception;

    /**
	 * 메뉴 정보를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public List<Menu> selectMenuList(Menu vo) throws Exception;

	/**
	 * 로그인 시 사용자 정보를 변경한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model
	 * @return ""
	 * @exception Exception		
	 */
	public int updateUserInfo(LoginVO vo) throws Exception;

    int  updateFailcnt(HashMap paramObj) throws Exception;

	/**
	 * 로그인 실패시 처리.
	 * @param userid 로그인 ID
	 * @param sIp 접속한 사용자 PC의  IP
	 * @param loginStatus 로그인상태 (1:로그인, 2:로그아웃, 3:자동로그아웃, 4:로그인실패)
	 * @return
	 * @throws Exception
	 */
    int  loginFailProcess(String userid, String sIp, String loginStatus) throws Exception;

    /**
	 * 사용자 비밀번호를 변경한다.
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateUserPass(LoginVO vo) throws Exception;

	/**
	 * 이전 비밀번호 사용 여부 첵크
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	
	public int updateUserPass2(LoginVO vo) throws Exception;

	/**
	 * 이전 비밀번호 사용 여부 첵크
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	
	public EgovMap checkPreviousPassword(LoginVO loginVO) throws Exception;

	/**
	 * 사용자 비밀번호 변경 이력 Table insert
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int insertCmHisuserpass(LoginVO vo) throws Exception;

	/**
	 * 사용자 비밀번호 변경 이력 Table 에서 이력 저장 횟수( CM_CFGBASECD 테이블에 저장되어있음. )를 초과하는 비밀번호 이력 삭제 처리. ( id , password 가 키로 되어있음. )
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deletePreviousPassword(LoginVO vo) throws Exception;


	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int insertUserHisLogin(HashMap paramObj) throws Exception;
	
	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int insertUserHisLoginout(HashMap paramObj) throws Exception;

	
	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int updateLogOut(HashMap paramObj) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public int updateForceLogOut(HashMap paramObj) throws Exception;
	

	/**
	 * 로그인 최대 실패횟수 첵크
	 * cm_cfgbasecd:grp_cd='LOGIN' and DTL_CD='FAILCNT' 의 val 참조
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	boolean loginFailMaxCountProcess(LoginVO loginVO) throws Exception;

	/**
	 * 메인화면 계약현황 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectContCurstatList() throws Exception;

	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectNoticeList() throws Exception;
	
	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectNoticePopList() throws Exception;
	
	/**
	 * 메인화면 접수 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectOdList() throws Exception;
	/**
	 * 메인화면 계약알람 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectAlmContList() throws Exception;
	
	/**
	 * 메인화면 FAQ 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectFAQList() throws Exception;
	
	/**
	 * 오늘의 서비스 알림 현황 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getToDaySvcAlmCnt(HashMap<String, String> paramObj) throws Exception;

	/**
	 * 작업대기건 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getJobWaitCnt(HashMap<String, String> paramObj) throws Exception;

	/**
	 * 승인대기건 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getEnterWaitCnt(HashMap<String, String> paramObj) throws Exception;
	
	int selectContcheckIp(HashMap<String, String> paramObj) throws Exception;

	/**
	 * 계약현황 및 공지사항 더보기 권한
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public String selectMoreAuth(HashMap<String, String> paramObj) throws Exception;
	
	public String selectstartMenu(HashMap<String, String> paramObj) throws Exception;

	/**
	 * 공지사항 상세정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectNoticeDetail(HashMap paramObj) throws Exception;
	
	/**
	 * FAQ 조회상세정보를 조회한다.
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectFaqDetail(HashMap paramObj) throws Exception;
	
	
	/**
	 * FAQ 답변리스트를 조회한다.
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectAnserList(HashMap paramObj) throws Exception;
	
	
	/**
	 * FAQ 답변리스트 카운트를 조회한다.
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int selectAnserListCnt(String qseq) throws Exception;
	
	/**
	 * 로그인 비밀번호 실패 횟수 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	public int loginPassFailCnt(String userid) throws Exception;

	/**
	 * 현재 메뉴의 상위 메뉴 정보 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectUpMenuInfoList(HashMap paramObj) throws Exception;
	
	/**
	 * 모든 메뉴의 상위 메뉴 정보 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectMenuDepthList(HashMap paramObj) throws Exception;
	
	/**
	 * 메뉴 접근 이력 inert
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int insertHisAccess(HashMap paramObj, HttpServletRequest request, String mode) throws Exception;
	
	/**
	 * SCRIN_ID를 MENU_ID로 변환
	 */
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectMenuScrinList(HashMap paramObjj) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<EgovMap> selectFwexcel(Object parameterObject) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public int  updateAgreeYn(HashMap paramObj) throws Exception;

}