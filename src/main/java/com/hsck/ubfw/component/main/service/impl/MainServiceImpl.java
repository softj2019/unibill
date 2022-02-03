package com.hsck.ubfw.component.main.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.com.cmm.util.CryptoUtils;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.model.Menu;
import com.hsck.ubfw.component.main.service.MainService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : MainServiceImpl.java
 * @Description : 메인 Business Implement Class	
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

@Service("mainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {

	private static final Logger log = LoggerFactory.getLogger(ContentUtil.class);
	@Resource(name="commonMapper")
    private CommonMapper commonMapper;

	/** ID Generation */
	//@Resource(name="ddTestIdGnrService")
	//private EgovIdGnrService ddTestIdGnrService;

	/** ID Generation */
	//@Resource(name="ddTestIdGnrService2")
	//private EgovIdGnrService ddTestIdGnrService2;

	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;

	/**
	 * 사용자 정보를 조회한다.
	 * @return ""
	 * @exception Exception
	 */
	public LoginVO searchUserInfo(LoginVO vo) throws Exception {

		String pass = vo.getPass();
		if( StringUtils.isNoneBlank( pass ) ){
			CryptoUtils cryptoUtils = new CryptoUtils();
			pass = cryptoUtils.encryptSHA256Hex(pass);
			vo.setPass(pass);
		}

		LoginVO resultVO = (LoginVO) commonMapper.getSelect("main.searchUserInfo", vo);
		String a = "{nm : 111}";
		return resultVO;
	}
	/**
	 * 사용자 정보를 조회한다( user id 로만 조회 )
	 * @return ""
	 * @exception Exception
	 */
	@Override
	public LoginVO searchUserInfo2(LoginVO vo) throws Exception {

		LoginVO resultVO = (LoginVO) commonMapper.getSelect("main.searchUserInfo2", vo);
		return resultVO;
	}
	
	/**
	 * 메뉴 정보를 조회한다.
	 * @return ""
	 * @exception Exception		
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Menu> selectMenuList(Menu vo) throws Exception {
		return (List<Menu>) commonMapper.getSelectList("main.selectMenuList", vo);
	}

	/**
	 * 로그인 시 사용자 정보를 변경한다.
	 * @return ""
	 * @exception Exception
	 */
	public int  updateUserInfo(LoginVO vo) throws Exception {
		int result = 0;

		try {
			result = commonMapper.updateData("main.updateUserInfo", vo);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 로그인 실패시 암호 실패 횟수 증가
	 * @return
	 * @throws Exception
	 */
	@Override
	public int  updateFailcnt(HashMap paramObj) throws Exception {
		int result = 0;

		try {
			result = commonMapper.updateData("main.updateFailcnt", paramObj);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}


	@Override
	public int  loginFailProcess(String userid, String sIp, String loginStatus) throws Exception {
		int result = 0;

		HashMap paramObj =  new HashMap();
		paramObj.put("ip",  sIp);   // 사용자접속IP
		paramObj.put("user_id",      userid);  // 사용자ID
		paramObj.put("login_status", loginStatus);     // 로그인상태 (1:로그인, 2:로그아웃, 3:자동로그아웃, 4:로그인실패)
		try {
			//result = result + commonMapper.insertData("main.insertUserHisLogin", paramObj);
			//result = result + commonMapper.insertData("main.insertUserHisLoginout", paramObj);
			result = result + commonMapper.updateData("main.updateFailcnt", paramObj);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	@Override
	public int  updateUserPass(LoginVO vo) throws Exception {
		int result = 0;

		/*
		CryptoUtils cryptoUtils = new CryptoUtils();

		String oldPass = vo.getOldPass();
		if( StringUtils.isNoneBlank( oldPass ) ){

			oldPass = cryptoUtils.encryptSHA256Hex(oldPass);

			vo.setOldPass(oldPass);
		}

		String newPass = vo.getNewPass2();
		if( StringUtils.isNoneBlank( newPass ) ){

			newPass = cryptoUtils.encryptSHA256Hex(newPass);

			vo.setNewPass(newPass);
		}

		result = commonMapper.updateData("main.updateUserPass", vo);

		if( 0 <  result ){

			insertCmHisuserpass(vo);

			deletePreviousPassword(vo);
		}
		*/
		
		
		String oldPass = vo.getOldPass();
		String newPass = vo.getNewPass2();
		log.info("TTTTTTTTTT oldPass : " + oldPass + " new pass : "+ newPass);	 

		result = commonMapper.updateData("main.updateUserPass", vo);
		if( result >0 ){
			insertCmHisuserpass(vo);
			deletePreviousPassword(vo);
		}

		return result;
	}
	
	
	@Override
	public int  updateUserPass2(LoginVO vo) throws Exception {
		int result = 0;

		CryptoUtils cryptoUtils = new CryptoUtils();

		String oldPass = vo.getOldPass();
		if( StringUtils.isNoneBlank( oldPass ) ){

			oldPass = cryptoUtils.encryptSHA256Hex(oldPass);

			vo.setOldPass(oldPass);
		}

		String newPass = vo.getNewPass2();
		if( StringUtils.isNoneBlank( newPass ) ){

			newPass = cryptoUtils.encryptSHA256Hex(newPass);

			vo.setNewPass(newPass);
		}

		result = commonMapper.updateData("main.updateUserPass", vo);

		if( 0 <  result ){

			insertCmHisuserpass(vo);

			deletePreviousPassword(vo);
		}

		return result;
	}


	@Override
	public int insertCmHisuserpass(LoginVO vo) throws Exception{

		int result = 0;
		result = commonMapper.insertData("main.insertCmHisuserpass", vo);
		return result;
	}


	@Override
	public int deletePreviousPassword(LoginVO loginVO) throws Exception{

		String previousPasswordCnt = "0";
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		parmObj.put("cdGun",   "grp_cd");
		parmObj.put("grp_cd",   "WEB_CONF");
		previousPasswordCnt = (String)contentService.getDynamicCodeData(parmObj , "HIS_USER_PASS_CNT").get("code");
		loginVO.setPreviousPasswordCnt(previousPasswordCnt);

		int result = 0;
		result = commonMapper.deleteData("main.deletePreviousPassword", loginVO);
		return result;
	}

	@Override
	public EgovMap checkPreviousPassword(LoginVO loginVO) throws Exception{


		String previousPasswordCnt = "0";
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		parmObj.put("cdGun",   "grp_cd");
		parmObj.put("grp_cd",   "WEB_CONF");
		previousPasswordCnt = (String)contentService.getDynamicCodeData(parmObj , "HIS_USER_PASS_CNT").get("code");
		loginVO.setPreviousPasswordCnt(previousPasswordCnt);



		String newPass = loginVO.getNewPass();
		if( StringUtils.isNoneBlank( newPass ) ){
			/*
			CryptoUtils cryptoUtils = new CryptoUtils();
			newPass = cryptoUtils.encryptSHA256Hex(newPass);
			*/
			loginVO.setNewPass(newPass);
		}

		return (EgovMap)commonMapper.getSelect("main.checkPreviousPassword", loginVO);
	}



	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertUserHisLogin(HashMap paramObj) throws Exception{
		return commonMapper.insertData("main.insertUserHisLogin", paramObj);
	}

	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertUserHisLoginout(HashMap paramObj) throws Exception{
		return commonMapper.insertData("main.insertUserHisLoginout", paramObj);
	}
	
	/**
	 * 사용자 접속 이력 inert
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateLogOut(HashMap paramObj) throws Exception{
		return commonMapper.insertData("main.updateLogOut", paramObj);
	}

	@Override
	public int updateForceLogOut(HashMap paramObj) throws Exception {
		return commonMapper.insertData("main.updateForceLogOut", paramObj);
	}


	@Override
	public boolean loginFailMaxCountProcess(LoginVO loginVO) throws Exception {
		HashMap<String,Object> failCntParam =  new HashMap<String,Object>();
		failCntParam.put("cdGun", "grp_cd");
		failCntParam.put("grp_cd", "LOGIN");
		failCntParam.put("dtl_cd", "FAILCNT");
		List<EgovMap> baseCodFailMaxCntMap = contentService.selectDynamicDataList(failCntParam); // cm_cfgbasecd Table에서 설정 정보 조회.
		String baseCodFailMaxCnt = "5";
		if( null != baseCodFailMaxCntMap && 0 < baseCodFailMaxCntMap.size() ){
			baseCodFailMaxCnt = (String)baseCodFailMaxCntMap.get(0).get("code");
			baseCodFailMaxCnt = StringUtils.defaultIfBlank( baseCodFailMaxCnt , "5");
		}
		if( Integer.parseInt(baseCodFailMaxCnt) <= Integer.parseInt(loginVO.getFailcnt()) ){

			return true;
		}
		return false;
	}
/*
	@Override
	public boolean loginFailMaxCountProcess(LoginVO loginVO) throws Exception {
		HashMap<String,Object> failCntParam =  new HashMap<String,Object>();
		failCntParam.put("cdGun", "grp_cd");
		failCntParam.put("grp_cd", "LOGIN");
		failCntParam.put("dtl_cd", "FAILCNT");
		List<EgovMap> baseCodFailMaxCntMap = contentService.selectDynamicDataList(failCntParam); // cm_cfgbasecd Table에서 설정 정보 조회.
		String baseCodFailMaxCnt = "5";
		if( null != baseCodFailMaxCntMap && 0 < baseCodFailMaxCntMap.size() ){
			baseCodFailMaxCnt = (String)baseCodFailMaxCntMap.get(0).get("code");
			baseCodFailMaxCnt = StringUtils.defaultIfBlank( baseCodFailMaxCnt , "5");
		}
		if( Integer.parseInt(baseCodFailMaxCnt) <= Integer.parseInt(loginVO.getFailcnt()) ){

			return true;
		}
		return false;
	}
	*/
	
	/**
	 * 메인화면 계약현황 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectContCurstatList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectContCurstatList", "");
	}

	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectNoticeList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectNoticeList", "");
	}
	
	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectNoticePopList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectNoticePopList", "");
	}
	
	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectOdList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectOdList", "");
	}
	/**
	 * 메인화면 공지사항 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectAlmContList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectAlmContList", "");
	}
	/**
	 * 오늘의 서비스 알림 현황 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getToDaySvcAlmCnt(HashMap<String, String> paramObj) throws Exception{
		return commonMapper.getSelectCnt("main.getToDaySvcAlmCnt", paramObj);
	}
	
	/**
	 * 작업대기건 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getJobWaitCnt(HashMap<String, String> paramObj) throws Exception{
		return commonMapper.getSelectCnt("main.getJobWaitCnt", paramObj);
	}

	/**
	 * 승인대기건 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public int getEnterWaitCnt(HashMap<String, String> paramObj) throws Exception{
		return commonMapper.getSelectCnt("main.getEnterWaitCnt", paramObj);
	}
	
	/**
	 * 접속된 ip가 CM_CFGUSER_IP테이블에 등록된 횟수만 첵크
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectContcheckIp(HashMap<String, String> paramObj) throws Exception {
		return commonMapper.getSelectCnt("main.checkIp", paramObj);
	}
	
	/**
	 * 계약현황 및 공지사항 더보기 권한
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	public String selectMoreAuth(HashMap<String, String> paramObj) throws Exception {
		return (String) commonMapper.getSelect("main.selectMoreAuth", paramObj);
	}

	public String selectstartMenu(HashMap<String, String> paramObj) throws Exception {
		return (String) commonMapper.getSelect("main.selectstartMenu", paramObj);
	}
	
	
	/**
	 * 공지사항 상세정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectNoticeDetail(HashMap paramObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("main.selectNoticeDetail", paramObj);
	}
	
	@Override
	public List<EgovMap> selectFAQList() throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectFAQList", "");
	}
	
	/**
	 * FAQ 상세정보를 조회한다.
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EgovMap> selectFaqDetail(HashMap paramObj) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectFaqDetail", paramObj);
	}
	
	/**
	 * FAQ 답변리스트 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectAnserList(HashMap paramObj) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectAnserList", paramObj);
	}
	
	
	/**
	 * FAQ 답변리스트 카운트 조회
	 * @param loginVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectAnserListCnt(String qseq) throws Exception {
		return commonMapper.getSelectCnt("main.selectAnserListCnt", qseq);
	}
	
	/**
	 * 로그인 비밀번호 실패 횟수 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@Override
	public int loginPassFailCnt(String user_id) throws Exception {
		return commonMapper.getSelectCnt("main.loginPassFailCnt", user_id);
	}
	
	/**
	 * 현재 메뉴의 상위 메뉴 정보 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectUpMenuInfoList(HashMap paramObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("main.selectUpMenuInfoList", paramObj);
	}
	
	/**
	 * 모든 메뉴의 상위 메뉴 정보 조회
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectMenuDepthList(HashMap paramObj) throws Exception {		
		return (List<EgovMap>) commonMapper.getSelectList("main.selectMenuDepthList", paramObj);
	}
	
	
	
	/**
	 * 메뉴 접근 이력 inert
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertHisAccess(HashMap paramObj, HttpServletRequest request, String mode) throws Exception{
		paramObj.put("menu_id", StringUtil.isNullToString(request.getParameter("menu_code")));
		paramObj.put("scrin_id", StringUtil.isNullToString(request.getParameter("scrin_code")));
		paramObj.put("tblNm", StringUtil.isNullToString(request.getParameter("tableName")));
		paramObj.put("tblKey", StringUtil.isNullToString(request.getParameter("tableKey")));
		paramObj.put("exesql", StringUtil.isNullToString(request.getParameter("exesql")));
		paramObj.put("cond", StringUtil.isNullToString(request.getParameter("cond")));
		paramObj.put("ACCESS_TYPE", mode);
		paramObj.put("CUST_ID", ""); //검색조건상의 고객ID
        if(!StringUtil.isNullToString(request.getParameter("rep_cust_id")).equals("")){
        	paramObj.put("CUST_ID", StringUtil.isNullToString(request.getParameter("rep_cust_id"))); //검색조건상의 고객ID
        }
        if(!StringUtil.isNullToString(request.getParameter("cust_id")).equals("")){
        	paramObj.put("CUST_ID", StringUtil.isNullToString(request.getParameter("cust_id"))); //검색조건상의 고객ID
        }
        
        String ip = request.getHeader("X-Forwarded-For");
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("Proxy-Client-IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("WL-Proxy-Client-IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("HTTP_CLIENT_IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getRemoteAddr(); 
		 }
		 paramObj.put("IP", ip);  // 사용자IP
		 paramObj.put("REMARK", StringUtil.isNullToString(request.getParameter("reasonMsg")));  // 엑셀다운 사유
        
		
		return commonMapper.insertData("main.insertFwHisAccess", paramObj);
	}
	
	/**
	 * SCRIN_ID를 MENU_ID로 변환
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EgovMap> selectMenuScrinList(HashMap paramObj) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("main.selectMenuScrinList", paramObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectFwexcel(Object parameterObject) throws Exception {
		return (List<EgovMap>) commonMapper.getSelectList("excelTemplete.selectFwexcel", parameterObject);        
    }
	
	@Override
	public int  updateAgreeYn(HashMap paramObj) throws Exception {
		int result = 0;

		try {
			result = commonMapper.updateData("main.updateAgreeYn", paramObj);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}
	
}