package com.hsck.ubfw.component.main.web;



import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.com.cmm.util.CryptoUtils;
import com.hsck.ubfw.component.com.cmm.util.DateUtil;
import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.comm.vo.LoginInfo;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.drm.ssg.drm;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.model.Menu;
import com.hsck.ubfw.component.main.service.MainService;
import com.hsck.ubfw.component.remoteConn.job.remoteIns;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.EgovMap;
/*
import nets.sso.agent.v2020.authcheck.AuthCheck;
import nets.sso.agent.v2020.common.enums.AuthStatus;
*/
	
	
/**
 * 
 * $com.hsck.ubfw.component.main.web.MainController.java
 * 클래스 설명 : 메인 Controller Class
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

@Controller	
public class MainController {

//	protected Log log = (Log) LogFactory.getLog(this.getClass());
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	public static  List m_userid_list =null;
	public static List useridInfo = null;

	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** ContentService */
	@Resource(name = "mainService")
	private MainService mainService;
	
	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;
	
	
	
	/**
	 * 로그인 화면을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value="/main/loginForm.do")
	public String loginForm(ModelMap model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		log.debug("pgauth : " + request.getParameter("pgauth"));
		//itext 테스트
		//buildListPdf(response);
		
		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		
		// 타이틀 조회
		parmObj.put("cdGun",   "grp_cd");
	    parmObj.put("grp_cd",   "WEB_CONF");
//	    parmObj.put("dtl_cd",   "TITLE");
	    	
		List<EgovMap> codeList = contentService.selectDynamicDataList(parmObj);
		
		String title = "유니빌";
		if (codeList.size() > 0) {
			for(int i=0; i<codeList.size(); i++){
				if(codeList.get(i).get("dtlCd").equals("TIMEOUT")){
					request.getSession().setAttribute("unibillTimeOut", codeList.get(i).get("code"));
					//request.getSession().setAttribute("unibillTimeOut", "40");
				}else if(codeList.get(i).get("dtlCd").equals("HIS_USER_PASS_CNT")){
					request.getSession().setAttribute("unibillPassCnt", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("TIMEOUT_EXTENT")){
					request.getSession().setAttribute("unibillTimeOutExtent", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("TITLE")){
					request.getSession().setAttribute("unibillTitle", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("SITE")){
					request.getSession().setAttribute("unibillSite", codeList.get(i).get("code"));
				}
			}
			
		}
		title = (String)request.getSession().getAttribute("unibillTitle");
		String site = (String)request.getSession().getAttribute("unibillSite");
		model.addAttribute("title",  title);  	// 화면 title	
		model.addAttribute("site",  site);  	// 화면 site	
		
		
		// 암호화 DB함수 사용 여부
		parmObj.put("cdGun",   "grp_cd");
	    parmObj.put("grp_cd",   "UNIBILLCONFIG");
	    //parmObj.put("dtl_cd",   "FUNC_ENC");
		List<EgovMap> funcCode = contentService.selectDynamicDataList(parmObj);
		//default
		request.getServletContext().setAttribute("ACEESSLOG_USE", "N"); 
		for(int i=0; i<funcCode.size(); i++){
			if(funcCode.get(i).get("dtlCd").equals("FUNC_PASSWD")){ // 패스워드 암호함수
				request.getSession().setAttribute("unibillFuncPassEnc", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("FUNC_ENC")){ // 컬럼 암호함수
				request.getSession().setAttribute("unibillFuncEnc", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("ACEESSLOG_USE")){
				request.getServletContext().setAttribute("ACEESSLOG_USE", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("MASK_LEN")) { // 번호마스킹길이
				request.getServletContext().setAttribute("MASK_LEN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("TELNO_NM_USEYN")) { //번호명칭사용여부
				request.getServletContext().setAttribute("TELNO_NM_USEYN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("CDR_ENCYN")) { //CDR 암호화여부
				request.getServletContext().setAttribute("CDR_ENCYN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("UNIBILL_ADDRAPI")) {
				request.getServletContext().setAttribute("UNIBILL_ADDRAPI", funcCode.get(i).get("code"));
			}
		}
		
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		if( null != loginVO ){
			return "redirect:/main/main.do";
		}else{

			model.addAttribute("reqUri",    request.getParameter("reqUri"));     // 1 dept 메뉴
			System.out.println("reqUri : " + request.getParameter("reqUri"));
			System.out.println("url : " + request.getRequestURL());
			System.out.println("url2 : " + request.getRequestURI());
			System.out.println("context : " + request.getContextPath());
			System.out.println("000000 : " + request.getParameter("pgauth"));
			
			if(request.getParameter("pgauth") == null || request.getParameter("pgauth").equals("")) {
				return "unibill/com/main/loginForm";
			}else if(request.getParameter("pgauth").equals("admin")){
				return "unibill/com/main/adminLoginForm";
			}else {
				return "unibill/com/main/loginForm";
			}
			
		}
	}
	
	
	
	/*
	@RequestMapping(value="/main/ssologinForm.do")
	public String ssologinForm(ModelMap model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		System.out.println("sso test");
		String loginUrl = "";        // 로그인 URL
        String logoutUrl = "";       // 로그아웃 URL
        String returnUrl = "";       // 되돌아올 URL
        String logoutScript = "";    // 로그오프 해야 할 경우 사용할 스크립트
        String errorCode = "";       // 에러코드
        String errorMessage = "";    // 에러 메시지
        String siteID = "";          // 사이트 식별자
        
        // 인증 객체 선언(Request와 Response 인계)
        AuthCheck auth = new AuthCheck(request, response);
        // 인증 체크(인증 상태 값 리턴)
        AuthStatus status = auth.checkLogon();
        System.out.println("1111 : " + status);
        // 인증 체크 후 상세 에러코드 조회
        errorCode = String.valueOf(auth.getErrCode());
        System.out.println("2222 : " + errorCode);
        errorMessage = auth.getErrMsg();
        System.out.println("3333 : " + errorMessage);
		
		return "";
	}
	*/
	
	@RequestMapping(value="/main/adminloginForm.do")
	public String adminloginForm(ModelMap model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		log.debug("pgauth : " + request.getParameter("pgauth"));
		//itext 테스트
		//buildListPdf(response);

		
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		
		
		// 타이틀 조회
		parmObj.put("cdGun",   "grp_cd");
	    parmObj.put("grp_cd",   "WEB_CONF");
//	    parmObj.put("dtl_cd",   "TITLE");
	    	
		List<EgovMap> codeList = contentService.selectDynamicDataList(parmObj);
		
		String title = "유니빌";
		if (codeList.size() > 0) {
			for(int i=0; i<codeList.size(); i++){
				if(codeList.get(i).get("dtlCd").equals("TIMEOUT")){
					request.getSession().setAttribute("unibillTimeOut", codeList.get(i).get("code"));
					//request.getSession().setAttribute("unibillTimeOut", "40");
				}else if(codeList.get(i).get("dtlCd").equals("HIS_USER_PASS_CNT")){
					request.getSession().setAttribute("unibillPassCnt", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("TIMEOUT_EXTENT")){
					request.getSession().setAttribute("unibillTimeOutExtent", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("TITLE")){
					request.getSession().setAttribute("unibillTitle", codeList.get(i).get("code"));
				}else if(codeList.get(i).get("dtlCd").equals("SITE")){
					request.getSession().setAttribute("unibillSite", codeList.get(i).get("code"));
				}
			}
			
		}
		title = (String)request.getSession().getAttribute("unibillTitle");
		String site = (String)request.getSession().getAttribute("unibillSite");
		model.addAttribute("title",  title);  	// 화면 title	
		model.addAttribute("site",  site);  	// 화면 site	
		
		
		// 암호화 DB함수 사용 여부
		parmObj.put("cdGun",   "grp_cd");
	    parmObj.put("grp_cd",   "UNIBILLCONFIG");
	    //parmObj.put("dtl_cd",   "FUNC_ENC");
		List<EgovMap> funcCode = contentService.selectDynamicDataList(parmObj);
		//default
		request.getServletContext().setAttribute("ACEESSLOG_USE", "N"); 
		for(int i=0; i<funcCode.size(); i++){
			if(funcCode.get(i).get("dtlCd").equals("FUNC_PASSWD")){ // 패스워드 암호함수
				request.getSession().setAttribute("unibillFuncPassEnc", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("FUNC_ENC")){ // 컬럼 암호함수
				request.getSession().setAttribute("unibillFuncEnc", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("ACEESSLOG_USE")){
				request.getServletContext().setAttribute("ACEESSLOG_USE", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("MASK_LEN")) { // 번호마스킹길이
				request.getServletContext().setAttribute("MASK_LEN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("TELNO_NM_USEYN")) { //번호명칭사용여부
				request.getServletContext().setAttribute("TELNO_NM_USEYN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("CDR_ENCYN")) { //CDR 암호화여부
				request.getServletContext().setAttribute("CDR_ENCYN", funcCode.get(i).get("code"));
			}else if(funcCode.get(i).get("dtlCd").equals("UNIBILL_ADDRAPI")) {
				request.getServletContext().setAttribute("UNIBILL_ADDRAPI", funcCode.get(i).get("code"));
			}
		}
		
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		if( null != loginVO ){
			return "redirect:/main/main.do";
		}else{

			model.addAttribute("reqUri",    request.getParameter("reqUri"));     // 1 dept 메뉴
			return "unibill/com/main/adminLoginForm";
		}
	}

	
	/**
	 * 경고창
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value = "/alert" )
    @ResponseBody
    public void AlertBox(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();        
        out.println("<script>alert('"+msg+"'); location.href='/main/logout.do'; </script>");
        out.flush(); 
    }


	/**
	 * 로그인 처리를 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value="/main/login.do")
	public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//외부 접속 테스트 
//		System.out.println("외부접속 테스트");
//		remoteIns ins = new remoteIns();
//		if(!remoteIns.isRuuning()) {
//			ins.setQueryID("testinsert");
//			ins.setRdbType("spas");
//			HashMap parameterObject = new HashMap();
//			ins.setXmlId("content.test", parameterObject);
//			ins.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
//			ins.setMethod("satExe");
//			ins.start();
//		}
		
		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		Properties props = new Properties();
		props.load(in);
		String path = "";
		
		if(File.separator.equals("\\")){
			path = props.getProperty("WINsystem.uploadpath") + File.separator + "excel_download";
		}else{
			path = props.getProperty("system.uploadpath") + File.separator + "excel_download";
		}
		
		//path = File.separator + "app" + File.separator + "unibill" + File.separator + "tomcat" + File.separator + "webapps" + File.separator + "fileupload" + File.separator + "excel_download";
		
//		try {
//			//DRM 테스트
//			System.out.println("DRM 테스트 시작");
//			drm drm = new drm();
//			System.out.println("DRM DEC 시작");
//			System.out.println("mpath : " + path);
//			//drm.decDRM(path + File.separator + "test.xls", path + File.separator + "test_Dec.xls");
//			drm.decDRM(path + File.separator + "test_Dec_test", path + File.separator + "test_Dec.xls");
//			//drm.decDRM("\\egovframework\\softcamp\\test.xls", "\\egovframework\\softcamp\\test_Dec.xls");
//			System.out.println("DRM DEC 완료");
//		} catch (Exception e) {
//			System.out.println("DRM DEC 에러");
//		}
		
		
		
		//검색조건 체크
		HttpServletRequest Rrequest = ContentUtil.modifyparam(request);
		request = Rrequest;
		String userid = StringUtil.isNullToString(request.getParameter("userid"));  // 사용자ID
		String pass   = StringUtil.isNullToString(request.getParameter("pass"));    // 사용자PW
	
		// 접속IP
		String sIp = StringUtil.getHostIp(request);
			
		// 로그인 vo
		LoginVO vo = new LoginVO();		
		vo.setUserId(userid);
		vo.setPass(pass); 
		String curDate = DateUtil.getCurrentDateString();
		vo.setCurDate(curDate);
		
		
		// 사용자 정보 확인
		LoginVO loginVO = mainService.searchUserInfo2(vo);
		// 사용자 접속 이력 INSERT 정보
		HashMap paramObj =  new HashMap();		
		paramObj.put("ip",  sIp);   // 사용자접속IP

		// 가입여부 검증.(user id를 이용하여 가입여부 검증.)
		if (null == loginVO) {
			// 회원정보가 없는 경우.
			int result = mainService.loginFailProcess(userid , sIp , "4");
			// 리턴
			return "redirect:/main/loginForm.do?msg=loginfail";
			
		} else {

			// 로그인 실패횟수 초과여부 처리.
//			if (mainService.loginFailMaxCountProcess(loginVO)) return "redirect:/main/loginForm.do?msg=loginfail";
			
			List<EgovMap> PassRule_jsonData = contentService.selectPassRule(null);	
			String failMaxCnt = (String) PassRule_jsonData.get(0).get("passFail").toString();
		 
			if( Integer.parseInt(failMaxCnt) <= Integer.parseInt(loginVO.getFailcnt()) ){
				return "redirect:/main/loginForm.do?msg=passFailMax&failMaxCnt="+failMaxCnt;
			}
			//if (mainService.loginFailMaxCountProcess(loginVO)) return "redirect:/main/loginForm.do?msg=passFailMax&failMaxCnt="+failMaxCnt;


			// 비밀번호 검증.
			String passEncrypted = "";
			if( StringUtils.isNoneBlank( pass ) ){
				HashMap<String, String> parmObj =  new HashMap<String, String>();
				
				// 암호화 DB함수 사용 여부
				parmObj.put("cdGun",   "grp_cd");
			    parmObj.put("grp_cd",   "UNIBILLCONFIG");
			    parmObj.put("dtl_cd",   "FUNC_PASSWD");
				List<EgovMap> funcCode = contentService.selectDynamicDataList(parmObj);
				request.getSession().setAttribute("unibillFuncPassEnc", funcCode.get(0).get("code"));
				
				if(request.getSession().getAttribute("unibillFuncPassEnc").equals("-")){
					CryptoUtils cryptoUtils = new CryptoUtils();
					passEncrypted = cryptoUtils.encryptSHA256Hex(pass);
				}else{
					ContentUtil contentUtil = new ContentUtil();
					passEncrypted = contentUtil.getCryptoValDB("ENC", pass, "pass", "", (String)request.getSession().getAttribute("unibillFuncPassEnc"));
				}
				
				
			}

			if( !passEncrypted.equals( loginVO.getPass() ) ){
				
				// 비밀번호가 틀린 경우.
				int result = mainService.loginFailProcess(userid , sIp , "4");
				
				// 비밀번호 실패 횟수 조회
				int passFailCnt = mainService.loginPassFailCnt(userid);
				
				// 리턴
				return "redirect:/main/loginForm.do?msg=passfail&failCnt="+passFailCnt;

			} else {
				String login_yn=loginVO.getLoginYn();
				if("Y".equals(login_yn)) {//pjh 중복사용자 로그인 
					log.info("############# 이미 로그인한 사용자 접속을 해제합니다 #################");
					//KillLoginSession()					
					//Update set cm_cfguser login_yn='Y' where user_id=userid;
				}
				 

				// 비밀번호가 맞는 경우.
				log.info("##################################################################");
				log.info("########## 사용자 ID [ " + loginVO.getUserId().replaceAll("[\r\n]","") + " ]");
				log.info("########## 사용자 명 [ " + loginVO.getUserNm().replaceAll("[\r\n]","") + " ]");
				log.info("########## 사용자 권한 [ " + loginVO.getRoleId().replaceAll("[\r\n]","") + " ]");
				log.info("########## 사용자 접속IP [ " + sIp.replaceAll("[\r\n]","") + " ]");
				log.info("##################################################################");

				// 마지막 로그인이 90일이 경과 된 경우
				if (!"0".equals(loginVO.getDayCnt())) {

					return "redirect:/main/loginForm.do?msg=logincnt";
				}

				if( "Y".equals( loginVO.getIpchkYn() ) ){
					HashMap<String, String> paramObj2 = new HashMap<String, String>();
					paramObj2.put("loginIp",sIp);
					int checkIpcnt = mainService.selectContcheckIp(paramObj2);
					if( 0 == checkIpcnt ){
						return "redirect:/main/loginForm.do?msg=ipCheck";
					}
				}

				if( "/admin".equals( request.getContextPath() ) ){

					if( 0 == StringUtils.indexOf( loginVO.getRoleId() ,"D") || 0 == StringUtils.indexOf( loginVO.getRoleId() ,"T") ){
					}else{

						return "redirect:/main/loginForm.do?msg=isNotAuthentic";
					}
				}

				// 로그인여부(로그이아웃:N, 로그인:Y)
				vo.setLoginYn("Y");
				
				// 마지막 로그인시간 변경
				mainService.updateUserInfo(vo);

				// 사용자 접속 이력
				
				String returnVal = "";
				String ubseq = KeyUtil.getUbseq("cm_hisloginout", "ubseq"); 
				
				
				paramObj.put("ubseq",      ubseq);  // 사용자ID
				paramObj.put("user_id",      loginVO.getUserId());  // 사용자ID
				paramObj.put("user_nm",      loginVO.getUserNm());  // 사용자ID
				paramObj.put("login_status", "1");   // 로그인상태 (1:로그인, 2:로그아웃, 3:자동로그아웃, 4:로그인실패, 5:강제로그아웃)
				mainService.insertUserHisLogin(paramObj);
				
				//vo.setUbseq(contentService.selectInsertUbseqNoKey(paramObj));
				//loginVO.setHisubseq(contentService.selectInsertUbseqNoKey(paramObj));
				loginVO.setHisubseq(ubseq);

//				request.getSession().setAttribute("loginVO", loginVO);

				HttpSession session = request.getSession(true);

				session.setAttribute(Globals.CONNECTION_USER_ID, userid);
				session.setAttribute(Globals.SESSION_USER_ID, loginVO.getUserId());
				session.setAttribute(Globals.SESSION_USER_NAME, loginVO.getUserNm());
				session.setAttribute(Globals.SESSION_LAST_LOGIN_DT, loginVO.getLastloginDt());
				session.setAttribute(Globals.SESSION_HISLOGIN_UBSEQ, loginVO.getHisubseq());
				

				// session time out 조회
				int sessionMaxInactiveIntervalTime = Integer.parseInt( Globals.SESSION_MAX_INACTIVE_INTERVAL_TIME ); // Session 유지 시간(초 , 1시간==3600초 )
				
				sessionMaxInactiveIntervalTime = Integer.parseInt( (String)request.getSession().getAttribute("unibillTimeOut") );
				
				session.setAttribute(Globals.SESSION_MAX_INACTIVE_INTERVAL, sessionMaxInactiveIntervalTime);

				session.setMaxInactiveInterval( sessionMaxInactiveIntervalTime );

				session.setAttribute(Globals.SESSION_CONTEXT_PATH, request.getContextPath() );

				session.setAttribute(Globals.SESSION_USER_VO, loginVO);


				Object maxTime = session.getAttribute(Globals.SESSION_MAX_INACTIVE_INTERVAL);

				
				if(m_userid_list==null) m_userid_list= new ArrayList();
				if(useridInfo==null) useridInfo = new ArrayList<HashMap<String, String>>();
				
				
				if(m_userid_list.contains(userid)) {			
					session = request.getSession(true);
					String sessionId= session.getId();
					request.getServletContext().setAttribute("dupFlag", true);
					
					List mUserList = (List)request.getServletContext().getAttribute("mUserList");
					
					// 현재시간
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					//cal.add(Calendar.MINUTE, -1);
					//하루전 년도, 월, 일
					int year = cal.get ( cal.YEAR );
					int month = cal.get ( cal.MONTH ) + 1 ;
					int date = cal.get ( cal.DATE ) ;
					
					//현재 (시,분,초)
					int hour = cal.get ( cal.HOUR_OF_DAY ) ;
					int min = cal.get ( cal.MINUTE );
					int sec = cal.get ( cal.SECOND );
					String str = String.valueOf(year) + StringUtils.leftPad(String.valueOf(month),2,"0") + StringUtils.leftPad(String.valueOf(date),2,"0") + StringUtils.leftPad(String.valueOf(hour),2,"0") + StringUtils.leftPad(String.valueOf(min),2,"0") + StringUtils.leftPad(String.valueOf(sec),2,"0");
					
					// 장시간이용없는 세션 공유메모리 정리
					for(int i= 0; i<mUserList.size(); i++){
						if( userid.equals(( (HashMap<String, String>)mUserList.get(i) ).get("userId")) &&  ( (HashMap<String, String>)mUserList.get(i) ).get("exitFlag") == "Y"){
							String LastAccTime = ( (HashMap<String, String>)mUserList.get(i) ).get("LastAccTime");
							// 하루 동안 이용이 없으면 정리
							if(Long.valueOf(LastAccTime ) < Long.valueOf(str )){
								// 사용자 접속 이력 update
								HashMap forceLogoutParam =  new HashMap();
								forceLogoutParam.put("forceUserId",userid);
								forceLogoutParam.put("loginStatus","5");
								
								Calendar forcetm = Calendar.getInstance();
								forcetm.set(Calendar.YEAR, Integer.parseInt(LastAccTime.substring(0,4)));
								forcetm.set(Calendar.MONTH, Integer.parseInt(LastAccTime.substring(4,6)) -1);
								forcetm.set(Calendar.DATE, Integer.parseInt(LastAccTime.substring(6,8)));
								forcetm.set(Calendar.HOUR, Integer.parseInt(LastAccTime.substring(8,10)));
								forcetm.set(Calendar.MINUTE, Integer.parseInt(LastAccTime.substring(10,12)));
								forcetm.set(Calendar.SECOND, Integer.parseInt(LastAccTime.substring(12,14)));
								forcetm.add(Calendar.SECOND, 10);
								String forceStr = String.valueOf(forcetm.get ( forcetm.YEAR )) + StringUtils.leftPad(String.valueOf(forcetm.get ( cal.MONTH ) + 1),2,"0") + StringUtils.leftPad(String.valueOf(forcetm.get ( cal.DATE )),2,"0") + StringUtils.leftPad(String.valueOf(forcetm.get ( cal.HOUR_OF_DAY )),2,"0") + StringUtils.leftPad(String.valueOf(forcetm.get ( forcetm.MINUTE )),2,"0") + StringUtils.leftPad(String.valueOf(forcetm.get ( forcetm.SECOND )),2,"0");
								forceLogoutParam.put("logoutTm", forceStr);
								
								mainService.updateForceLogOut(forceLogoutParam);
								mUserList.remove(i);
							}
						}
					}
										
					// 삭제 세션 공유메모리 설정
					for(int i= 0; i<mUserList.size(); i++){
						if(userid.equals(( (HashMap<String, String>)mUserList.get(i) ).get("userId"))){
							( (HashMap<String, String>)mUserList.get(i) ).put("exitFlag", "Y");
						}
					}
					HashMap<String, String> info = new HashMap<String, String>();
					info.put("userId", userid);
					info.put("sessionId", session.getId());
					info.put("exitFlag", "N");
					info.put("dupYn", loginVO.getDupYn());
					SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date lastAccessTimeF = new Date(request.getSession().getLastAccessedTime());
        	        String lastAccessTimeStr = sFormat.format(lastAccessTimeF);
        	        info.put("LastAccTime", lastAccessTimeStr);
					
					mUserList.add(info);
					request.getServletContext().setAttribute("mUserList", mUserList);
					
					
					
					
				}else{
					m_userid_list.add(userid);
					// 유저 세션 정보
					HashMap<String, String> info = new HashMap<String, String>();
					info.put("userId", userid);
					info.put("sessionId", session.getId());
					info.put("exitFlag", "N");
					info.put("dupYn", loginVO.getDupYn());
					SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date lastAccessTimeF = new Date(request.getSession().getLastAccessedTime());
        	        String lastAccessTimeStr = sFormat.format(lastAccessTimeF);
        	        info.put("LastAccTime", lastAccessTimeStr);
        	        					
					
					
					List<HashMap<String, String>> museridInfo = new ArrayList();
					if(request.getServletContext().getAttribute("mUserList") == null){
						museridInfo.add(info);
						request.getServletContext().setAttribute("mUserList", museridInfo);
					}else{
						museridInfo = (List<HashMap<String, String>>) request.getServletContext().getAttribute("mUserList");
						museridInfo.add(info);
						request.getServletContext().setAttribute("mUserList", museridInfo);
					}
					
					request.getServletContext().setAttribute("dupFlag", false);
					
				}
				
				
				
				
				// 사용자 접속 insert
				//mainService.insertUserHisLoginout(paramObj);
				
 
				String reqUri = StringUtil.isNullToString(request.getParameter("reqUri"));
				if(StringUtil.isNotBlank( reqUri ) ){
					return "redirect:" + reqUri;
				}else{
					
//				return "redirect:/content/selectContentList.do?menu_code=M010103";   // M020101 (고객관리) M010103 (기초코드관리) M010101 (그룹그분관리)
					return "redirect:/main/main.do";
				}
			}
		}

	}

	/**
	 * 로그아웃 처리를 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/main/logout.do")
	public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
			
		// 로그아웃 구분
		String logoutMode = StringUtil.isNullToString(request.getParameter("logoutMode"));
		
		// 중복 로그아웃 
		String dupFlag = StringUtil.isNullToString(request.getParameter("dupFlag"));
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        // 접속IP
     	String sIp = StringUtil.getHostIp(request);
     	
     	if(m_userid_list!=null && dupFlag.equals("N")) {
     		m_userid_list.remove(loginVO.getUserId());
     		log.debug("#### logout delete user = " + loginVO.getUserId().replaceAll("[\r\n]",""));
     	}
     	else log.debug("#### logout no delete user = " + loginVO.getUserId().replaceAll("[\r\n]",""));
     	
     	
     	// 로그인상태
     	String sLoginStatus = "";
     	if ("logOut".equals(logoutMode)) {
     		sLoginStatus = "2";  // 로그아웃   		
     	} else { 
     		sLoginStatus = "3";  // 자동로그아웃
     	}     	

     	// 로그인여부(로그이아웃:N, 로그인:Y)
     	loginVO.setLoginYn("N");
		// 사용자 접속 이력
		HashMap paramObj =  new HashMap();						
		paramObj.put("user_id",      loginVO.getUserId());  // 사용자ID
		paramObj.put("user_nm",      loginVO.getUserNm());  // 사용자ID
		paramObj.put("ip",           sIp);                  // 사용자접속IP
		paramObj.put("login_status", sLoginStatus);         // 로그인상태 (1:로그인, 2:로그아웃, 3:자동로그아웃, 4:로그인실패)
		paramObj.put("ubseq", loginVO.getHisubseq()); 
		
		// 로그아웃시 로그인여부 변경
		//mainService.updateUserInfo(loginVO);
				
		// 로그인 이력 등록
		//mainService.updateLogOut(paramObj);
		
		mainService.insertUserHisLoginout(paramObj);
		
		request.getSession().removeAttribute(Globals.SESSION_USER_VO);	// 사용자정보
		request.getSession().removeAttribute("1deptMenuSession");  		// 1 dept 메뉴
		request.getSession().removeAttribute("2deptMenuSession");  		// 2 dept 메뉴
		request.getSession().removeAttribute("3deptMenuSession");  		// 3 dept 메뉴
		request.getSession().removeAttribute("4deptMenuSession");  		// 4 dept 메뉴
		request.getSession().invalidate();

		cookieRemove(request,response);				

		if(loginVO.getRoleId().equals("9")) {
			if(dupFlag.equals("Y")){
				return "redirect:/main/adminloginForm.do?msg=dupLogout";
			}else{
				return "redirect:/main/adminloginForm.do?msg=logout";
			}
		}else {
			if(dupFlag.equals("Y")){
				return "redirect:/main/loginForm.do?msg=dupLogout";
			}else{
				return "redirect:/main/loginForm.do?msg=logout";
			}
		}
		
		
	}
	

	/**
	 * Cooki 정보를 제거한다.
	 * @param request
	 * @param response
	 * void
	 */
	private void cookieRemove( HttpServletRequest request, HttpServletResponse response ) {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (!cookie.getName().equals("cookieSaveUserId")) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}

	/**
	 * 비밀번호 변경 페이지로 이동.
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/passChangeForm.do")
	public String passChangeForm(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {

		return "unibill/com/main/passChangeForm";
	}


	@RequestMapping(value="/main/checkPreviousPassword.json")
	public @ResponseBody Map<String, Object> checkPreviousPasswordJson(@ModelAttribute LoginVO loginVO, @RequestParam Map<String, Object> commandMap, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws Exception {

		String isPreviousPassword = "N";
		int previousPasswordCnt = 0; // 몇회 이전 비밀번호는 다시 사용하지 못하게하는 정책에 따른 값.

		// 세션 사용자정보
		LoginVO sessionLoginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		loginVO.setUserId(sessionLoginVO.getUserId());

		EgovMap m = mainService.checkPreviousPassword(loginVO);
		if( null != m ){
			isPreviousPassword = "Y";
			if( null != m.get("previousPasswordCnt") ){

				previousPasswordCnt = Integer.parseInt( String.valueOf( m.get("previousPasswordCnt") ) );
			}
		}

		Map<String , Object> returnMap = new HashMap<String , Object>();

		returnMap.put("isPreviousPassword", isPreviousPassword);
		returnMap.put("previousPasswordCnt", previousPasswordCnt);

		return returnMap;
	}
	/**
	 * 비밀번호 변경 처리.
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/passChangeSave.do")
	public String passChangeSave(@ModelAttribute LoginVO loginVO, HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap, HttpSession session) throws Exception {


		// 세션 사용자정보
		LoginVO sessionLoginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		loginVO.setUserId(sessionLoginVO.getUserId());
		// 사용자 정보 확인
		int updateCnt = 0;
		if( StringUtils.isBlank( sessionLoginVO.getUserId() ) ){
			// 로그인해주세요.
			return "redirect:/main/passChangeForm.do?msg=notLogin";
		}

		if( StringUtils.isBlank( loginVO.getOldPass() ) ){
			// 이전 비밀번호를 입력해주세요.
			return "redirect:/main/passChangeForm.do?msg=notOldPass";
		}

		if( StringUtils.isBlank( loginVO.getNewPass() ) ){
			// 신규 비밀번호를 입력해주세요.
			return "redirect:/main/passChangeForm.do?msg=notNewPass";
		}

		if( !(loginVO.getNewPass()).equals(loginVO.getNewPass2())){

			return "redirect:/main/passChangeForm.do?msg=notEqPass";
		}

		updateCnt = mainService.updateUserPass(loginVO);


		return "redirect:/main/main.do";
	}
	
	 /* 비밀번호 변경 취소 처리.
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/main/passChangeCancel.do")
	public String passChangeCancel(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		// 세션 사용자정보
		LoginVO sessionLoginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
		request.getSession().setAttribute("passChangeYn", "N");
		sessionLoginVO.setFirstloginYn("N");
		
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/main/passChangeSaveOrg.json")
	public @ResponseBody Map<String, Object> passChangeSaveJsonOrg(@ModelAttribute LoginVO loginVO, HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap, HttpSession session) throws Exception {

		String msg = "";
		String code = "";
		Map<String , Object> returnMap = new HashMap<String , Object>();

		// 세션 사용자정보
		LoginVO sessionLoginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		if( null != sessionLoginVO ){

			loginVO.setUserId(sessionLoginVO.getUserId());
			// 사용자 정보 확인
			int updateCnt = 0;
			if( StringUtils.isBlank( sessionLoginVO.getUserId() ) ){
				msg = msg + "로그인이 필요합니다.<br/>";
				code = "isNotLogin";
				returnMap.put("msg", msg);
				returnMap.put("code", code);
				return returnMap;
			}
		}

		if( StringUtils.isBlank( loginVO.getOldPass() ) ){
			msg = msg + "이전 비밀번호를 입력해주세요.<br/>";
			code = "oldPassEmpty";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( StringUtils.isBlank( loginVO.getNewPass() ) ){
			msg = msg + "신규 비밀번호를 입력해주세요.<br/>";
			code = "newPassEmpty";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( !(loginVO.getNewPass()).equals(loginVO.getNewPass2())){
			msg = msg + "변경할 비밀번호가 일치하지 않습니다.<br/>";
			code = "newPassMissMatched";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( (loginVO.getOldPass()).equals(loginVO.getNewPass())){
			msg = msg + "이전 비밀번호와 신규 비밀번호가 같은 경우 등록할 수 없습니다.<br/>";
			code = "oldAndNewPassMatched";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		String targetPasswd = loginVO.getNewPass();
		 
		
		HashMap LparmObj =  new HashMap();
		HashMap PparmObj =  new HashMap();
		
		// 패스워드 길이 조회
		LparmObj.put("cdGun",   "grp_cd");
    	LparmObj.put("grp_cd",   "UNIBILLCONFIG");
    	LparmObj.put("dtl_cd",   "PASS_LENGTH");
    	
    	
    	List<EgovMap> LjsonData = contentService.selectDynamicDataList(LparmObj);
    	String passLength = "";
    	if(null != LjsonData && 0 < LjsonData.size()){
    		if(!LjsonData.get(0).get("code").toString().equals("0")){
    			passLength = "{" + LjsonData.get(0).get("code").toString() + ",}$";
    			msg = msg + "최소 " + LjsonData.get(0).get("code").toString() + "자리 ";
    		}else{
    			passLength = "$";
    		}
    	}
    	
    	
    	// 패턴규칙 조회
    	PparmObj.put("cdGun",   "grp_cd");
    	PparmObj.put("grp_cd",   "UNIBILLCONFIG");
    	PparmObj.put("dtl_cd",   "PASS_PATTERN");
    	
    	List<EgovMap> PjsonData = contentService.selectDynamicDataList(PparmObj);
    	String passPattern = "";
    	
    	if(null != PjsonData && 0 < PjsonData.size()){
    		if(PjsonData.get(0).get("code").toString().equals("1")){
    			passPattern = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]";
    			msg = msg + ", 영문자, 숫자 조합 필요함.<br/>";
    		}else if(PjsonData.get(0).get("code").toString().equals("2")){
    			passPattern = "^(?=.*[a-zA-Z])(?=.*[@!#$%^&+=_?*])[a-zA-Z0-9!@#$%^&*]";
    			msg = msg + ", 영문자, 특수문자 조합 필요함.<br/>";
    		}else if(PjsonData.get(0).get("code").toString().equals("3")){
    			passPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@!#$%^&+=_?*])[a-zA-Z0-9!@#$%^&*]";
    			msg = msg + ", 영문자, 숫자, 특수문자 3가지 모두 조합 필요함.<br/>";
    		}else{
    			passPattern = "^[a-zA-Z0-9!@#$%^&*]";
    		}
    		
    	}
    	
    	String pattern = passPattern + passLength;

		if(!targetPasswd.matches(pattern)){
			code = "passwordCharacterConditionCheck";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}else{
			msg = "";
		}

		
		// 비밀번호 이력 사용여부 조회
		HashMap HparmObj =  new HashMap();
		HparmObj.put("cdGun",   "grp_cd");
    	HparmObj.put("grp_cd",   "WEB_CONF");
    	HparmObj.put("dtl_cd",   "HIS_USER_PASS_CNT");
    	
    	List<EgovMap> HjsonData = contentService.selectDynamicDataList(HparmObj);
    	if(HjsonData.get(0).get("code").toString().equals("0")){
    		returnMap.put("his", "hisNoUse");
    	}else{
    		returnMap.put("his", "hisUse");
    	}
    	
        EgovMap m = mainService.checkPreviousPassword(loginVO);
 
        if( null != m ){

            int previousPasswordCnt = 0; // 몇회 이전 비밀번호는 다시 사용하지 못하게하는 정책에 따른 값.

            if( null != m.get("previousPasswordCnt")){

                previousPasswordCnt = Integer.parseInt( String.valueOf( m.get("previousPasswordCnt") ) );
                
                msg = msg + "최근" + previousPasswordCnt +" 회 안에 동일한 비밀번호를 사용한적이 있습니다. 다른 비밀번호를 사용해주세요.";
                code = "previousPassword";
                returnMap.put("msg", msg);
                returnMap.put("code", code);
                return returnMap;
                
            }else if(m.get("previousPasswordCnt").equals("0")){
            	
            }else if(m.get("previousPasswordCnt") == null){
            }
        }

        
		int updateCnt = 0;
		updateCnt = mainService.updateUserPass(loginVO);
		if( 0 == updateCnt ){
			msg = "현재 비밀번호가 일치하지 않습니다.";
			code = "oldPassMissMatched";
		}else{
			request.getSession().setAttribute("passChangeYn", "N");	
			code = "SUCCESS";
		}


		returnMap.put("msg", msg);
		returnMap.put("code", code);
		return returnMap;
	}

	
 
    
    
    



	@SuppressWarnings("unchecked")
	@RequestMapping(value="/main/passChangeSave.json")
	public @ResponseBody Map<String, Object> passChangeSaveJson(@ModelAttribute LoginVO loginVO, HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap, HttpSession session) throws Exception {

		String msg = "";
		String code = "";
		Map<String , Object> returnMap = new HashMap<String , Object>();

		// 세션 사용자정보
		LoginVO sessionLoginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		if( null != sessionLoginVO ){

			loginVO.setUserId(sessionLoginVO.getUserId());
			// 사용자 정보 확인
			int updateCnt = 0;
			if( StringUtils.isBlank( sessionLoginVO.getUserId() ) ){
				msg = msg + "로그인이 필요합니다.<br/>";
				code = "isNotLogin";
				returnMap.put("msg", msg);
				returnMap.put("code", code);
				return returnMap;
			}
		}

		if( StringUtils.isBlank( loginVO.getOldPass() ) ){
			msg = msg + "이전 비밀번호를 입력해주세요.<br/>";
			code = "oldPassEmpty";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( StringUtils.isBlank( loginVO.getNewPass() ) ){
			msg = msg + "신규 비밀번호를 입력해주세요.<br/>";
			code = "newPassEmpty";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( !(loginVO.getNewPass()).equals(loginVO.getNewPass2())){
			msg = msg + "변경할 비밀번호가 일치하지 않습니다.<br/>";
			code = "newPassMissMatched";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		if( (loginVO.getOldPass()).equals(loginVO.getNewPass())){
			msg = msg + "이전 비밀번호와 신규 비밀번호가 같은 경우 등록할 수 없습니다.<br/>";
			code = "oldAndNewPassMatched";
			returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
		}

		String targetPasswd = loginVO.getNewPass();		
		List<EgovMap> PassRule_jsonData = contentService.selectPassRule(null);		
		
		
    	code = "passwordCharacterConditionCheck";
    	
    	String errmsg = "비밀번호는 최소 " + PassRule_jsonData.get(0).get("minLen").toString() +"자리 "
    			+ "최대 "+ PassRule_jsonData.get(0).get("maxLen").toString() +"자리 이며 " 
    			+ "순차문자는  " + PassRule_jsonData.get(0).get("seqLen").toString() +"자리 "
    			+ "반복문자는  " + PassRule_jsonData.get(0).get("sameLen").toString() +"자리 제한됩니다 " 
    			+ "주민번호는 포함되지 않아야 하며 "
    			;
    	
    	int failflag=0; 
    	Map<String, Object> vriPass = ContentUtil.vriPass(sessionLoginVO.getUserId(), PassRule_jsonData, targetPasswd);
    	if(vriPass != null) {
    		if(!vriPass.get("failflag").equals("")) {failflag = Integer.valueOf(vriPass.get("failflag").toString());}
    		if(!vriPass.get("msg").equals("")) {msg = StringUtil.isNullToString(vriPass.get("msg").toString());}
    		if(!vriPass.get("code").equals("")) {code = StringUtil.isNullToString(vriPass.get("code").toString());}
    		System.out.println("vriPass : [" + vriPass.get("failflag") + "], [" + vriPass.get("msg") + "], [" + vriPass.get("code") + "]");
    	}
     
    	if(failflag>0) {
    		//returnMap.put("msg", errmsg+msg+" 필요합니다");
    		returnMap.put("msg", msg);
			returnMap.put("code", code);
			return returnMap;
    	}
		msg = "";		
	 
    	if(!PassRule_jsonData.get(0).get("hisPassCnt").toString().equals("0")){    
    	   
    	   returnMap.put("his", "hisUse"); 
    	   EgovMap m = mainService.checkPreviousPassword(loginVO);
	        if( null != m ){
                msg = msg + "최근 " + PassRule_jsonData.get(0).get("hisPassCnt").toString() +" 회 안에 동일한 비밀번호를 사용한적이 있습니다. 다른 비밀번호를 사용해주세요.";
                code = "previousPassword";
                returnMap.put("msg", msg);
                returnMap.put("code", code);
                return returnMap;  
	        }
    	}
    	else{
    		returnMap.put("his", "hisNoUse");
    	}  
        
		int updateCnt = 0;
		updateCnt = mainService.updateUserPass(loginVO);
		if( 0 == updateCnt ){
			msg = "현재 비밀번호가 일치하지 않습니다.";
			code = "oldPassMissMatched";
		}else{
			request.getSession().setAttribute("passChangeYn", "N");	
			code = "SUCCESS";
			
			sessionLoginVO.setFirstloginYn("N");
		}


		returnMap.put("msg", msg);
		returnMap.put("code", code);
		returnMap.put("floginYn", sessionLoginVO.getFirstloginYn());
		return returnMap;
	}
	
	

	/**
	 * 메인 화면 헤더 및 메뉴 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/main/header.do")
	public String header(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		System.out.println("gubun : " + request.getParameter("gubun"));
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);

		if(request.getSession().getAttribute("passChangeYn")==null){
			model.addAttribute("passChangeYn",    loginVO.getPassChangeYn());
		}else{
			model.addAttribute("passChangeYn",   request.getSession().getAttribute("passChangeYn") );
		}
		
		// 메뉴id
		String menu_code = StringUtil.isNullToString(request.getParameter("menu_code"));  // 메뉴id
		String gubun     = StringUtil.isNullToString(request.getParameter("gubun"));      // 구분
		
		if ("M".equals(gubun) && "".equals(menu_code)) {			
			response.setContentType("text/html;charset=euc-kr");
	        PrintWriter printwriter = response.getWriter();
	        printwriter.println("<html>");        
	        printwriter.println("<script language='javascript'>");
	        printwriter.println("function fn_init() {");
	        printwriter.println("   alert('잘못된 접근 경로입니다.\\n메뉴를 클릭해서 접속해 주십시오.');");
	        printwriter.println("   f.action='/main/main.do';");
	        printwriter.println("   f.submit();");
	        printwriter.println("}");
	        printwriter.println("</script>");
	        printwriter.println("<body onload='fn_init();'>");
	        printwriter.println("<form name='f' method='post'>");
	        printwriter.println("</form>");
	        printwriter.println("</body>");
	        printwriter.println("</html>");
	        printwriter.flush();
	        printwriter.close();
	        
	        return null;
		}
		// 즐겨찾기 여부 조회
		HashMap<String, String> parameterObject =  new HashMap<String, String>();

		String menuId = "";
		String menuNm = "";
		String bmark  = "";
		
		// 메뉴정보 조회
		parameterObject.put("menu_code",  menu_code);            // 메뉴ID
		parameterObject.put("user_id",    loginVO.getUserId());  // 사용자ID
		parameterObject.put("role_code",  loginVO.getRoleId());  // 권한ID	
		
		if(menu_code == null || menu_code.equals("")){
			request.getSession().removeAttribute("menuCodeInfo");
			request.getSession().removeAttribute("menuNmInfo");
			request.getSession().removeAttribute("bmarkInfo");
		}else if(request.getSession().getAttribute("menuCodeInfo") == null){
			List<EgovMap> menuInfo = contentService.selectMenuInfo(parameterObject);
			for (int i=0; i<menuInfo.size(); i++) {
	    		if (i == 0) {
	    			menuId  = (String) menuInfo.get(i).get("menuId");
		    		menuNm  = (String) menuInfo.get(i).get("menuNm");
		    		bmark   = (String) menuInfo.get(i).get("bmark");	    		
	    		}
	    	}
			
			request.getSession().setAttribute("menuCodeInfo",menuId);
			request.getSession().setAttribute("menuNmInfo",menuNm);
			request.getSession().setAttribute("bmarkInfo",bmark);
			
		}else if(menu_code.equals((String)request.getSession().getAttribute("menuCodeInfo")) ){
			menuId = (String)request.getSession().getAttribute("menuCodeInfo");
			menuNm = (String)request.getSession().getAttribute("menuNmInfo");
			bmark = (String)request.getSession().getAttribute("bmarkInfo");
		}else{
			List<EgovMap> menuInfo = contentService.selectMenuInfo(parameterObject);
			for (int i=0; i<menuInfo.size(); i++) {
	    		if (i == 0) {
	    			menuId  = (String) menuInfo.get(i).get("menuId");
		    		menuNm  = (String) menuInfo.get(i).get("menuNm");
		    		bmark   = (String) menuInfo.get(i).get("bmark");	    		
	    		}
	    	}
			
			request.getSession().setAttribute("menuCodeInfo",menuId);
			request.getSession().setAttribute("menuNmInfo",menuNm);
			request.getSession().setAttribute("bmarkInfo",bmark);
		}
		
		
	   	// 현재 메뉴의 상위 메뉴 정보 조회
	   	parameterObject.put("menu_id", menuId);
		String menu_id_1 = "";
		String menu_id_2 = "";
		String menu_id_3 = "";
		String menu_id_4 = "";
		
		
		
		if("H".equals(gubun)){
//			List<EgovMap> upMenuInfo = mainService.selectUpMenuInfoList(parameterObject);
			
			List<EgovMap> menuDepthList = (List<EgovMap>)request.getSession().getAttribute("menuDepthList");
			
			if(menuDepthList == null){
				List<EgovMap> menuInfos= mainService.selectMenuDepthList(parameterObject);
				
				request.getSession().setAttribute("menuDepthList", menuInfos);
				
				menuDepthList = (List<EgovMap>)request.getSession().getAttribute("menuDepthList");
				
			}
			
			for(int i=0; i<menuDepthList.size(); i++){
				if(menuDepthList.get(i).get("depth3").equals(menuId)){
					menu_id_1 = (String)menuDepthList.get(i).get("depth1");
					menu_id_2 = (String)menuDepthList.get(i).get("depth2");
					menu_id_3 = (String)menuDepthList.get(i).get("depth3");
					menu_id_4 = (String)menuDepthList.get(i).get("depth4");
				}
			}
			
	    	/*for (int i=0; i<upMenuInfo.size(); i++) {
	
	    		String depth = StringUtil.isNullToString(upMenuInfo.get(i).get("depth"));
	    		if ("1".equals(depth)) {
	    			menu_id_1 = (String) upMenuInfo.get(i).get("menuId");
	    		} else if ("2".equals(depth)) {
	    			menu_id_2 = (String) upMenuInfo.get(i).get("menuId");
	    		} else if ("3".equals(depth)) {
	    			menu_id_3 = (String) upMenuInfo.get(i).get("menuId");
	    		} else if ("4".equals(depth)) {
	    			menu_id_4 = (String) upMenuInfo.get(i).get("menuId");
	    		}
	    		 
	    	}*/
	    	
	    	request.getSession().setAttribute("menu_id_1", menu_id_1);
	    	request.getSession().setAttribute("menu_id_2", menu_id_2);
	    	request.getSession().setAttribute("menu_id_3", menu_id_3);
	    	request.getSession().setAttribute("menu_id_4", menu_id_4);
    	
    	}
    	
    	log.info("################### [현재 메뉴   [" + menuId.replaceAll("[\r\n]","") + "][" + menuNm.replaceAll("[\r\n]","") + "] ####################");    	
    	log.info("################### [상위 메뉴 1 [" + String.valueOf(request.getSession().getAttribute("menu_id_1")).replaceAll("[\r\n]","") + "] ####################");
    	log.info("################### [상위 메뉴 2 [" + String.valueOf(request.getSession().getAttribute("menu_id_2")).replaceAll("[\r\n]","") + "] ####################");
    	log.info("################### [상위 메뉴 3 [" + String.valueOf(request.getSession().getAttribute("menu_id_3")).replaceAll("[\r\n]","") + "] ####################");
    	log.info("################### [상위 메뉴 4 [" + String.valueOf(request.getSession().getAttribute("menu_id_4")).replaceAll("[\r\n]","") + "] ####################");
    	
    	// 작업대기건 조회
//    	int jobCnt = mainService.getJobWaitCnt(parameterObject);
//    	int enterCnt = mainService.getEnterWaitCnt(parameterObject);
    	
    	int jobCnt = 10;
    	int enterCnt = 10;
		
		
		model.addAttribute("menu_code",   menuId);     // 메뉴ID
    	model.addAttribute("menu_nm",     menuNm);     // 메뉴명
    	model.addAttribute("menu_id_1",   request.getSession().getAttribute("menu_id_1"));  // 1 DEPT MENU ID
    	model.addAttribute("menu_id_2",   request.getSession().getAttribute("menu_id_2"));  // 2 DEPT MENU ID
    	model.addAttribute("menu_id_3",   request.getSession().getAttribute("menu_id_3"));  // 3 DEPT MENU ID
    	model.addAttribute("menu_id_4",   request.getSession().getAttribute("menu_id_4"));  // 4 DEPT MENU ID
    	model.addAttribute("bmark",       bmark);      // 즐겨찾기 여부
//		model.addAttribute("userNm",      loginVO.getDeptGrpNm() + " : " + loginVO.getUserNm() + "("+loginVO.getUserId()+")");  // 사용자명
		model.addAttribute("userNm",      loginVO.getUserNm() + "("+loginVO.getUserId()+")");  // 사용자명
		model.addAttribute("bookmarkPos", loginVO.getBookmarkPos());                           // 즐겨찾기위치
		model.addAttribute("user_id",     loginVO.getUserId());  // 사용자ID
		model.addAttribute("rep_cust_id", loginVO.getRepCustId());  // 고객ID
		model.addAttribute("cust_id",     loginVO.getCustId());  // 고객ID
		model.addAttribute("deptGrpId",   loginVO.getDeptGrpId());  // 로그인한 파트너
		model.addAttribute("jobCnt",      jobCnt);               // 작업대기건
		model.addAttribute("roleId",      loginVO.getRoleId());  //권한ID
		model.addAttribute("enterCnt",    enterCnt);  //승인대기건
		model.addAttribute("roleLevel",   loginVO.getRoleLevel());  //권한레벨
		model.addAttribute("grp_cd",   	  loginVO.getGrpCd());  //조직
		
		// 세션 메뉴정보 조회
		Object sMenu1 = request.getSession().getAttribute("1deptMenuSession");
		Object sMenu2 = request.getSession().getAttribute("2deptMenuSession");
		Object sMenu3 = request.getSession().getAttribute("3deptMenuSession");		
		Object sMenu4 = request.getSession().getAttribute("4deptMenuSession");
		
		// 메뉴정보 조회
		if (sMenu1 == null || sMenu2 == null || sMenu3 == null || sMenu4 == null) {
			
			Menu mVO = new Menu();
			
			mVO.setRoleId(loginVO.getRoleId());  // 사용자 권한id
			List<Menu> selectMenuList = mainService.selectMenuList(mVO);
			
			List<Menu> infoList1 = new ArrayList<Menu>();
			List<Menu> infoList2 = new ArrayList<Menu>();
			List<Menu> infoList3 = new ArrayList<Menu>();
			List<Menu> infoList4 = new ArrayList<Menu>();
			
			String premenu1 = "";
			String premenu2 = "";
			String premenu3 = "";
			String premenu4 = "";
			
			for (int i = 0; i<selectMenuList.size(); i++) {
				// 1 deph 
				String menuid1 = selectMenuList.get(i).getMenuId1();
				String name1   = selectMenuList.get(i).getMenuNm1();
				String depth1  = selectMenuList.get(i).getDepth1();
				
				Menu vo = new Menu();
				
				if (!premenu1.equals(nullToVoid(menuid1))) {
					vo.setMenuId1(menuid1);
					vo.setMenuNm1(name1);
					vo.setDepth1(depth1);
					premenu1 = menuid1;
					infoList1.add(vo);
				}
				
				// 2 deph 
				String menuid2   = selectMenuList.get(i).getMenuId2();
				String upmenuid2 = selectMenuList.get(i).getUpMenuId2();
				String name2     = selectMenuList.get(i).getMenuNm2();
				String depth2    = selectMenuList.get(i).getDepth2();
				String upmenuyn2 = selectMenuList.get(i).getUpMenuYn2();
				
				Menu vo2 = new Menu();
				
				if (!premenu2.equals(nullToVoid(menuid2))) {
					vo2.setMenuId2(menuid2);
					vo2.setUpMenuId2(upmenuid2);
					vo2.setMenuNm2(name2);
					vo2.setDepth2(depth2);
					vo2.setUpMenuYn2(upmenuyn2);
					premenu2 = menuid2;
					
					infoList2.add(vo2);
				}				
				// 3 deph 
				String menuid3   = selectMenuList.get(i).getMenuId3();
				String upmenuid3 = selectMenuList.get(i).getUpMenuId3();
				String name3     = selectMenuList.get(i).getMenuNm3();
				String depth3    = selectMenuList.get(i).getDepth3();
				String upmenuyn3 = selectMenuList.get(i).getUpMenuYn3();
				String url3      = selectMenuList.get(i).getUrl();
				String scrinYn3  = selectMenuList.get(i).getScrinYn();
				String roleYn3   = selectMenuList.get(i).getRoleYn();				
				
				Menu vo3 = new Menu();
				
				if (menuid3 != null) {
					if (!premenu3.equals(nullToVoid(menuid3))) {
						vo3.setMenuId3(menuid3);
						vo3.setUpMenuId3(upmenuid3);
						vo3.setMenuNm3(name3);
						vo3.setDepth3(depth3);
						vo3.setUpMenuYn3(upmenuyn3);
						vo3.setUrl(url3);
						vo3.setScrinYn(scrinYn3);
						vo3.setRoleYn(roleYn3);
						premenu3 = menuid3;
						
						infoList3.add(vo3);
					}
				}
				// 4 deph 
				String menuid4   = selectMenuList.get(i).getMenuId4();
				String upmenuid4 = selectMenuList.get(i).getUpMenuId4();
				String name4     = selectMenuList.get(i).getMenuNm4();
				String depth4    = selectMenuList.get(i).getDepth4();
				String url4      = selectMenuList.get(i).getUrl();
				String scrinYn4  = selectMenuList.get(i).getScrinYn();
				String roleYn4   = selectMenuList.get(i).getRoleYn();
				
				Menu vo4 = new Menu();
				
				if (menuid4 != null) {					
					if (!premenu4.equals(nullToVoid(menuid4))) {
						vo4.setMenuId4(menuid4);
						vo4.setUpMenuId4(upmenuid4);
						vo4.setMenuNm4(name4);
						vo4.setDepth4(depth4);
						vo4.setUrl(url4);
						vo4.setScrinYn(scrinYn4);
						vo4.setRoleYn(roleYn4);
						premenu4 = menuid4;
						
						infoList4.add(vo4);
					}
				}
				
			}			
			request.getSession().setAttribute("1deptMenuSession", infoList1);
			request.getSession().setAttribute("2deptMenuSession", infoList2);
			request.getSession().setAttribute("3deptMenuSession", infoList3);
			request.getSession().setAttribute("4deptMenuSession", infoList4);
			
//			sMenu1 = request.getSession().getAttribute("1deptMenuSession");
//			sMenu2 = request.getSession().getAttribute("2deptMenuSession");
//			sMenu3 = request.getSession().getAttribute("3deptMenuSession");
//			sMenu4 = request.getSession().getAttribute("4deptMenuSession");
			
		}
		model.addAttribute("menuInfo",    request.getSession().getAttribute("1deptMenuSession"));     // 1 dept 메뉴
		model.addAttribute("menuInfo2",   request.getSession().getAttribute("2deptMenuSession"));     // 2 dept 메뉴
		model.addAttribute("menuInfo3",   request.getSession().getAttribute("3deptMenuSession"));     // 3 dept 메뉴
		model.addAttribute("menuInfo4",   request.getSession().getAttribute("4deptMenuSession"));     // 4 dept 메뉴
		
		// 상단 헤더
		String url = "layout/header";
		if ("M".equals(gubun)) {  // 상단 헤더 밑 메뉴 영역
			url = "layout/menu";
		}

		// time out 처리 여부
		String timeoutExtent = "N";
		
		timeoutExtent = (String)request.getSession().getAttribute("unibillTimeOutExtent");
		
		// session time out 조회
		int sessionMaxInactiveIntervalTime = Integer.parseInt( Globals.SESSION_MAX_INACTIVE_INTERVAL_TIME ); // Session 유지 시간(초 , 1시간==3600초 )
		sessionMaxInactiveIntervalTime = Integer.parseInt( (String)request.getSession().getAttribute("unibillTimeOut") );

		model.addAttribute("sessionMaxInactiveIntervalTime",   sessionMaxInactiveIntervalTime);
		model.addAttribute("timeoutExtent",   timeoutExtent);     // 4 dept 메뉴
		
		return url;
	}
	
	/**
	 * 메인 화면을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@RequestMapping(value="/main/main.do")
	public String main(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
		HashMap<String, String> paramObj =  new HashMap<String, String>();
		
		// 오늘의 서비스 알림 현황
		// 만기알림
		
		int expbfCnt=0;
		int expafCnt=0;
		int almdayCnt=0;
		int odCnt=0;
		int jobCnt=0;
		int contCnt=0;
		
		//메인 화면 계약 
		if(false){
			paramObj.put("alm_gubun",  "1");
			expbfCnt = mainService.getToDaySvcAlmCnt(paramObj); 
			
			paramObj.put("alm_gubun",  "2");
			expafCnt = mainService.getToDaySvcAlmCnt(paramObj);
			
			paramObj.put("alm_gubun",  "3");
			almdayCnt = mainService.getToDaySvcAlmCnt(paramObj);		
			
			paramObj.put("alm_gubun",  "4");
			odCnt = mainService.getToDaySvcAlmCnt(paramObj); 
			
			paramObj.put("alm_gubun",  "5");
			jobCnt = mainService.getToDaySvcAlmCnt(paramObj);
			
			paramObj.put("alm_gubun",  "6");
			contCnt = mainService.getToDaySvcAlmCnt(paramObj);
			
			// 계약현황
//			List<EgovMap> conList = mainService.selectContCurstatList();
			//List<EgovMap> conList = new ArrayList<EgovMap>();
			/* 유니빌 고도화 pjh
			List<EgovMap> almContList = mainService.selectAlmContList();
			List<EgovMap> odList = mainService.selectOdList();
			*/
		}
		
		

		
		// 공지사항
		List<EgovMap> noticeList = mainService.selectNoticeList();
//		List<EgovMap> noticeList = new ArrayList<EgovMap>();
		
		// 공지사항 팝업 정보
		List<EgovMap> noticePopList = mainService.selectNoticePopList();
		
		
		// FAQ
		List<EgovMap> faqList = mainService.selectFAQList();
		
		// 계약현황, FAQ 및 공지사항 더보기 권한
		paramObj.put("role_id",  loginVO.getRoleId());		
		paramObj.put("menu_id",  "M020320");  
		String contMoreAuth = mainService.selectMoreAuth(paramObj);    // 계약현황 권한
		
		paramObj.put("menu_id",  "M010306"); 
		String noticeMoreAuth = mainService.selectMoreAuth(paramObj);  // 공지사항 권한
		
		paramObj.put("menu_id",  "M010310"); 
		String faqMoreAuth = mainService.selectMoreAuth(paramObj);  // 공지사항 권한
		
		//권한별 시작 메뉴 
		String startMenu = mainService.selectstartMenu(paramObj);		// 권한별 시작페이지 
		System.out.println("startMenu : " + startMenu);
		
		model.addAttribute("userNm",          loginVO.getUserNm() + "("+loginVO.getUserId()+")");  	// 사용자명
		model.addAttribute("expbfCnt",       expbfCnt);       									// 만기30전알림
		model.addAttribute("expafCnt",       expafCnt);       										// 만기미해지
		model.addAttribute("almdayCnt",      almdayCnt);         									// 지정알림
		model.addAttribute("odCnt",          odCnt);       									// 신청
		model.addAttribute("jobCnt",         jobCnt);       										// 작업진행중
		model.addAttribute("contCnt",        contCnt);         									// 계약마감
		
		model.addAttribute("passChangeYn",    loginVO.getPassChangeYn() );
//		model.addAttribute("almContList",     almContList);         									// 계약현황
//		model.addAttribute("odList",         odList);         	
		model.addAttribute("noticeList",      noticeList);      									// 공지사항
		model.addAttribute("noticePopList",   noticePopList);      									// 공지사항 팝업
		model.addAttribute("faqList",      	  faqList);      										// FAQ
		model.addAttribute("contMoreAuth",    contMoreAuth);    									// 계약현황 권한
		model.addAttribute("noticeMoreAuth",  noticeMoreAuth);  									// 공지사항 권한
		model.addAttribute("faqMoreAuth",     faqMoreAuth);     									// FAQ 권한
		model.addAttribute("agreeYn",		  loginVO.getAgreeYn());								// 동의여부
		model.addAttribute("firstloginYn",	  loginVO.getFirstloginYn());							// 첫로그인여부
		
		model.addAttribute("startMenu",	  startMenu);							// 시작페이지
		
		return "index_tiles/com/main/main";
	}

	@RequestMapping(value = "/main/pollingCheck.json")
	public @ResponseBody Map<String, Object>  pollingCheck(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> requestParamMap, ModelMap model) throws Exception {

//		log.debug("#### sessionNullForAjaxReturn");
//		printRequest(request);
//
//		long unibillIdGenTestId = mainService.unibillIdGenTest();
//		log.debug("#### unibillIdGenTestId ==>> " + unibillIdGenTestId );
//
//
//		long unibillIdGenTestId2 = mainService.unibillIdGenTest2();
//		log.debug("#### unibillIdGenTestId2 ==>> " + unibillIdGenTestId2 );

		HttpSession session = request.getSession(true);


		// session time out 조회
		int sessionMaxInactiveIntervalTime = Integer.parseInt( Globals.SESSION_MAX_INACTIVE_INTERVAL_TIME ); // Session 유지 시간(초 , 1시간==3600초 )
		HashMap<String, String> parmObj =  new HashMap<String, String>();
		parmObj.put("cdGun",   "grp_cd");
		parmObj.put("grp_cd",   "WEB_CONF");
		sessionMaxInactiveIntervalTime = Integer.parseInt( (String)contentService.getDynamicCodeData(parmObj , "TIMEOUT").get("code") );

		session.setAttribute(Globals.SESSION_MAX_INACTIVE_INTERVAL, sessionMaxInactiveIntervalTime);

		session.setMaxInactiveInterval( sessionMaxInactiveIntervalTime );


//		Object maxTime = session.getAttribute(Globals.SESSION_MAX_INACTIVE_INTERVAL);
//		log.debug("#### SESSION_MAX_INACTIVE_INTERVAL = " + String.valueOf(maxTime) );



//		Date sessionCreateDate = new Date(session.getCreationTime());
//		Date sessionCreateSecond = DateUtils.round(sessionCreateDate, Calendar.SECOND);
//		String sessionCreateSecondStr = DateFormatUtils.format(sessionCreateSecond, "yyyy-MM-dd HH:mm:ss");
//		log.debug("#### sessionCreateSecondStr = " + sessionCreateSecondStr);


//		Date now = new Date();
//		// Get nearest second
//		Date nearestSecond = DateUtils.round(now, Calendar.SECOND);
//		String nearestSecondStr = DateFormatUtils.format(nearestSecond, "yyyy-MM-dd HH:mm:ss");
//		log.debug("#### nearest Second = " + nearestSecondStr);

		LoginInfo loginInfo = new LoginInfo();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isLogin" , loginInfo.isLogin() );
		log.debug("#### resultMap:" + resultMap);

		model.addAttribute("result", resultMap);

		return resultMap;

	}

	@RequestMapping(value = "/main/sessionNullForAjaxReturn.json")
	public @ResponseBody Map<String, Object>  sessionNullForAjaxReturn(HttpServletRequest request, HttpServletResponse pResponse, @RequestParam Map<String, Object> requestParamMap, ModelMap model) {

		log.debug("#### sessionNullForAjaxReturn");
		printRequest(request);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("retCd", "SESSION_FINISH");
		resultMap.put("retMsg", "연결이 종료되었습니다. 다시 로그인 하십시오");
		log.debug("#### resultMap:" + resultMap);

		model.addAttribute("result", resultMap);

//		return "jsonView";
		return resultMap;

	}

	public static void printRequest(HttpServletRequest pReq )
	{

		Enumeration<?> eParam = pReq.getParameterNames();
		while (eParam.hasMoreElements()) {
			String pName = (String)eParam.nextElement();
			String pValue = pReq.getParameter(pName);

			log.info("#### RequestParamMap = " + pName.replaceAll("[\r\n]","") + " : " + pValue.replaceAll("[\r\n]","") );
		}

	}
	/**
	 * 타일즈 레이아웃 화면을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@RequestMapping(value="/main/layout.do")
	public String layout(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		System.out.println("layout gubun : " + request.getParameter("gubun"));
		String gubun = StringUtil.isNullToString(request.getParameter("gubun"));      // 구분
				
		String url = "layout/indexlayout";
		if ("C".equals(gubun)) {  // 상단 헤더 밑 메뉴 영역
			//url = "layout/layout";
			url = "layout/indexlayout";
		}
		
		// 타이틀 조회
		model.addAttribute("title",  request.getSession().getAttribute("unibillTitle"));  // 화면 title

		return url;
	}
	
	/**
	 * 공지사항 상세정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/main/selectNoticeDetail.json")
    public @ResponseBody Map<String, Object> selectNoticeDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap paramObj =  new HashMap();
		
		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){		    
		    String key = en.nextElement().toString();
		    String val = request.getParameter(key).replaceAll("<","&lt;").replaceAll(">","&gt;");
		    paramObj.put(key, val);
		}
		
	    List<EgovMap> jsonData = mainService.selectNoticeDetail(paramObj);

	    Map<String , Object> returnMap = new HashMap<String , Object>();				
		returnMap.put("rows", jsonData);
        return returnMap;
	}
	
	
	public String nullToVoid(Object param) throws Exception{
		String str = new String();
		  
		if (param == null) {
			return "";
		}else{
			str =  (String)param;
		}
		
		return str;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/main/passHisUsecheck.json")
	public @ResponseBody Map<String, Object> passHisUsecheckJson() throws Exception {

		HashMap parmObj =  new HashMap();
		
		// 패스워드 길이 조회
		parmObj.put("cdGun",   "grp_cd");
    	parmObj.put("grp_cd",   "WEB_CONF");
    	parmObj.put("dtl_cd",   "HIS_USER_PASS_CNT");
    	
    	List<EgovMap> jsonData = contentService.selectDynamicDataList(parmObj);

		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		if(jsonData != null){
			if(jsonData.get(0).get("code").equals("0")){
				returnMap.put("passHisUse","hisNoUse");
			}else{
				returnMap.put("passHisUse","hisUse");
			}
			
		}else{
			returnMap.put("passHisUse","hisNoUse");
		}

		return returnMap;
	}
	
	//FAQ조회
	/**
	 * FAQ 상세정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/main/selectFaqDetail.json")
    public @ResponseBody Map<String, Object> selectFaqDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap paramObj =  new HashMap();
		
		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){		    
		    String key = en.nextElement().toString();
			String val = request.getParameter(key);
		    paramObj.put(key, val);
		}
		
	    List<EgovMap> jsonData = mainService.selectFaqDetail(paramObj);

	    Map<String , Object> returnMap = new HashMap<String , Object>();				
		returnMap.put("rows", jsonData);

        return returnMap;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/main/selectAnserList.json")
	public @ResponseBody Map<String, Object> selectAnserList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap parameterObject =  new HashMap();
		parameterObject.put("qseq", request.getParameter("qseq"));
		List<EgovMap> jsonData = mainService.selectAnserList(parameterObject);
		
		if(jsonData.size() == 0){
			EgovMap rs = new EgovMap();
			rs.put("ubseq", "1");
			rs.put("qseq", "");
			rs.put("aseq", "");
			rs.put("qtype", "");
			rs.put("msg", " 답변없음");
			jsonData.add(rs);
		}
		int total_count = mainService.selectAnserListCnt(request.getParameter("qseq"));
        
        // 페이징 처리
      	int page = StringUtil.strToInt(request.getParameter("page"));
    	int rows = StringUtil.strToInt(request.getParameter("rows"));
    	int firstIndex = rows * page - rows; 
    	
    	parameterObject.put("firstIndex", firstIndex);
	    parameterObject.put("lastIndex", (firstIndex+rows));
	    
    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));
    	
    	if (sidx.equals("")) {

    	} else {		
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (sord.equals("")) {

		} else {
			parameterObject.put("OrderByType",sord);
		}
        
        
        int total_page = 0;
        if (total_count > 0) {
        	total_page = (int) Math.ceil( (float)total_count/(float)rows );
        }
        
        HashMap<String , Object> returnMap = new HashMap<String , Object>();
        returnMap.put("rows", jsonData);
		returnMap.put("total", total_page);
        returnMap.put("records", total_count);
		
		return returnMap;
		
		
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/main/updateAgreeYn.json")
	public @ResponseBody Map<String, Object> updateAgreeYn(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap parameterObject =  new HashMap();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        String agree =  StringUtil.isNullToString(request.getParameter("agree"));
        
        parameterObject.put("userID", loginVO.getUserId());
        parameterObject.put("agreeYn", agree);
        
        int result = mainService.updateAgreeYn(parameterObject);
        
        if(result > -1){
        	loginVO.setAgreeYn("Y");
        	HttpSession session = request.getSession(true);
        	session.setAttribute(Globals.SESSION_USER_VO, loginVO);
        }
        
        Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("result", result > -1?"suceess":"fail");
		
		return returnMap;
	}
	
	public void buildListPdf(HttpServletResponse response) throws Exception {
		// Apply preferences and build metadata.
	    Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
	    PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	    
	    response.setHeader("Content-Disposition", "attachment; filename=\"test.pdf\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setContentType("application/pdf");
		document.open();
		Font fontCorpoTableO= FontFactory.getFont("HYSMyeongJo-Medium", "UniKS-UCS2-H", BaseFont.EMBEDDED ,7.5f);
		
		//한글지원폰트 설정..
		//BaseFont bf = BaseFont.createFont("HYSMyeongJo-Medium", "UniKS-UCS2-H", BaseFont.NOT_EMBEDDED);
		//Font font2  = new Font(bf, 14, Font.BOLD);
		
		//타이틀
		Phrase title = new Phrase("2009년도 billing",fontCorpoTableO);
		Paragraph title2 = new Paragraph(title);
	    //중간정렬
		title2.setAlignment(Element.ALIGN_CENTER);
	
		document.add(title2);
		document.add(new Paragraph("\r\n"));
		
		
		
		PdfPTable table = new PdfPTable(4);
		
		ElementList elements = new ElementList();
		elements.add(new Phrase("111111111111111111111111111tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",fontCorpoTableO));
		elements.add(new Phrase("한글지원 여부",fontCorpoTableO));
		elements.add(new Phrase("깨지나?",fontCorpoTableO));
		elements.add(new Phrase("ㅇㅇ",fontCorpoTableO));
        ElementHandlerPipeline end = new ElementHandlerPipeline(elements, null);
       
        
        for (Element element : elements) {
        	PdfPCell cell = new PdfPCell();
        	cell.addElement(element);
            table.addCell(cell);
            
        }
        
        document.add(table);
        
        document.close();
        writer.close();
				
	}
	
	//권한신청
	@RequestMapping(value="/main/applyAuthority.do")
	public String applyAuthority() throws Exception {

		return "unibill/com/main/applyAuthority";
	}
		
	//비밀번호 변경
	@RequestMapping(value = "/main/passChangePop.do")
	public String passChangePop() throws Exception {
			
		return "unibill_pop/pop/passChangePop";
	}
		
	//아이디.비밀번호찾기
	@RequestMapping(value="/main/findIdPw.do")
	public String findIdPw() throws Exception {

		return "unibill/com/main/findIdPw";
	}
	//청약 신청
	@RequestMapping(value="/main/applySubscription.do")
	public String applySubscription() throws Exception {
		return "unibill_new_tiles/com/main/applySubscription";
	}
	//청약 서비스이용약관
	@RequestMapping(value="/main/applyTermsOfUse.do")
	public String applyTermsOfUse() throws Exception {
		return "unibill_new_tiles/com/main/applyTermsOfUse";
	}

	//청약 서비스이용약관 내용
	@RequestMapping(value="/main/applyTermsOfUsePolicy.do")
	public String applyTermsOfUsePolicy() throws Exception {
		return "unibill_new_tiles/com/main/applyTermsOfUsePolicy";
	}
}
