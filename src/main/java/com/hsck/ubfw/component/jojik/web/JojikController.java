package com.hsck.ubfw.component.jojik.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hsck.ubfw.component.UCsync.UCsyncExcute;
import com.hsck.ubfw.component.UCsync.schedule.UCsyncScheExecute;
import com.hsck.ubfw.component.com.cmm.service.FileService;
import com.hsck.ubfw.component.com.cmm.util.FileMngUtil;
import com.hsck.ubfw.component.com.cmm.util.FileUploadContent;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.jojik.service.JojikService;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.service.MainService;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 
 * @Class Name : ContentController.java
 * 클래스 설명 : 공통 컨텐츠 Controller Class
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
public class JojikController  {

	private static final Object EgovMap = null;

	protected Log log = (Log) LogFactory.getLog(this.getClass());		
	
	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;
	
	/** FileService */
	@Resource(name = "fileService")
	private FileService fileService;
	
	@Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;
	
	/** MainService */
	@Resource(name = "mainService")
	private MainService mainService;
	
	@Resource(name = "jojikService")
	private JojikService jojikService;
	
	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
	
	@Autowired private PlatformTransactionManager transactionManager;
	
	
	@RequestMapping(value="/jojik/updateOldData.do")
    public @ResponseBody Map<String, Object> updateOldData(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		HashMap param =  new HashMap();
		param.put("sgrpcd", request.getParameter("sGrpCd"));
		param.put("ssday", request.getParameter("sSday"));
		
		//이전  등록된 grp_cd update
		EgovMap resultMap = jojikService.updateOldData(param);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rs", resultMap);
		return returnMap;
	}
	
	@RequestMapping(value="/jojik/updatePath.do")
    public @ResponseBody Map<String, Object> updatePath(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		HashMap param =  new HashMap();
		param.put("sgrpcd", request.getParameter("sGrpCd"));
		param.put("sday", request.getParameter("sday").replace("-", ""));
		param.put("xmlid", "jojik.updatePath");
		//저장 이후 path 업데이트
		EgovMap resultMap = jojikService.updatePath(param);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rs", resultMap);
		return returnMap;
	}
	
	
	
	@RequestMapping(value="/jojik/updatePath2.do")
    public @ResponseBody Map<String, Object> updatePath2(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		HashMap param =  new HashMap();
		param.put("sgrpcd", request.getParameter("sGrpCd"));
		param.put("sday", request.getParameter("sday").replace("-", ""));
		param.put("xmlid", "jojik.updatePath2");
		//저장 이후 path 업데이트
		EgovMap resultMap = jojikService.updatePath(param);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rs", resultMap);
		return returnMap;
	}
	
	@RequestMapping(value="/jojik/updatePath3.do")
    public @ResponseBody Map<String, Object> updatePath3(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		HashMap param =  new HashMap();
		param.put("xmlid", "jojik.updatePath3");
		//저장 이후 path 업데이트
		EgovMap resultMap = jojikService.updatePath(param);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rs", resultMap);
		return returnMap;
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/jojik/manualSync.json")
    public @ResponseBody Map<String, Object> manualSync(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
				
		boolean sucessconn = true;
		int rsNum = 0;
		if(!UCsyncScheExecute.isRuuning()){
			UCsyncExcute ucExe = new UCsyncExcute();
			//UC 커넥션정보 및 스케줄링 정보 가져오기
			ArrayList<HashMap> connInfoRs = ucExe.getConInfo();
			int cnt = 0;
			for(HashMap info : connInfoRs) {
				if(sucessconn) {
					try {
						//즉시 인사디비 실행
						UCsyncScheExecute.getInstance().setInfo(info);
						UCsyncScheExecute.getInstance().setautoFlag(false, loginVO.getUserId());
						rsNum = UCsyncScheExecute.getInstance().start();
						//정상 진행시 다음 리스트 진행 하지 않고 정지
						cnt++;
						sucessconn = false;
					} catch (Exception e) {
						sucessconn = true;
					}
				}
			}
			
			
		}

		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("isRuuning", UCsyncScheExecute.isRuuning());
		returnMap.put("sucessconn", sucessconn);
		returnMap.put("msg", UCsyncScheExecute.getInstance().getMsg());
		returnMap.put("rsNum", rsNum);
		
		return returnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/jojik/jojiksaveContent.do")
    public String jojiksaveContent(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap param =  new HashMap();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
        String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag"));  // 처리 상태
        String ubseq      = StringUtil.isNullToString(request.getParameter("ubseq"));       // 자료순번
        param.put("ubseq", ubseq);
        
        Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){    			
			String key = en.nextElement().toString();
			String val = "";
			if(key.equals("sday") || key.equals("eday")){
				val = request.getParameter(key).replace("-", "");
			}else{
				val = request.getParameter(key);
			}
			log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
		    
		    param.put(key,  val);
		}
		
		param.put("userid", loginVO.getUserId());
		EgovMap resultMap = new EgovMap();
        
        if(actionFlag.equals("INSERT")) {
        	//프로시저 실행
    		param.put("xmlid", "jojik.insertjojik");
    		resultMap = jojikService.selectProcedure(param);
    	}else if(actionFlag.equals("UPDATE")){
    		//프로시저 실행
    		param.put("xmlid", "jojik.updatejojik");
    		resultMap = jojikService.selectProcedure(param);
    	}
		
		String keyCol = "grp_cd@sday";	
    	String OID = "S0196" + "|" + param.get("ubseq") + "|" + actionFlag + "|" + keyCol + "|" + "";
    	String resultMsg = "";
       	String errorMsg = "";
       	try {
       		if(resultMap.get("resultMsg").equals("true")){
           		resultMsg = "SAVE_SUCCESS";
           	}else {
           		resultMsg = "SQL_ERROR";
           		errorMsg = resultMap.get("errorMsg").toString();
           	}
		} catch (Exception e) {
			resultMsg = "SQL_ERROR";
			errorMsg = "프로시저 실행중 에러가 발생";
		}
       	
       	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
       	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg  : " + errorMsg.replaceAll("[\r\n]","")); 
       	log.info("처리후 호출 함수  (callBackFunction)    : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수 2(extCallBackFunction) : " + StringUtil.isNullToString(request.getParameter("extCallBackFunction")).replaceAll("[\r\n]",""));
       		
		return  "/unibill/com/cmm/commonMsg";
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/jojik/telnosaveContent.do")
    public String telnosaveContent(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap param =  new HashMap();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
        String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag"));  // 처리 상태
        String ubseq      = StringUtil.isNullToString(request.getParameter("ubseq"));       // 자료순번
        param.put("ubseq", ubseq);
        
        Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){    			
			String key = en.nextElement().toString();
			String val = "";
			if(key.equals("sday") || key.equals("eday")){
				val = request.getParameter(key).replace("-", "");
			}else{
				val = request.getParameter(key);
			}
			log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
		    
		    param.put(key,  val);
		}
		
		param.put("userid", loginVO.getUserId());
		EgovMap resultMap = new EgovMap();
        
        if(actionFlag.equals("INSERT")) {
        	//프로시저 실행
    		param.put("xmlid", "jojik.inserttelno");
    		resultMap = jojikService.selectProcedure(param);
    	}else if(actionFlag.equals("UPDATE")){
    		//프로시저 실행
    		param.put("xmlid", "jojik.updatetelno");
    		resultMap = jojikService.selectProcedure(param);
    	}
		
		String keyCol = "sday@s_no@use_yn";	
    	String OID = "S0027" + "|" + param.get("ubseq") + "|" + actionFlag + "|" + keyCol + "|" + "";
    	String resultMsg = "";
       	String errorMsg = "";
       	try {
       		if(resultMap.get("resultMsg").equals("true") && actionFlag.equals("INSERT")){
           		resultMsg = "SAVE_SUCCESS";
           	}else if(resultMap.get("resultMsg").equals("true") && actionFlag.equals("UPDATE")) {
           		resultMsg = "UPDATE_SUCCESS";
           	}else {
           		resultMsg = "SQL_ERROR";
           		errorMsg = resultMap.get("errorMsg").toString();
           	}
		} catch (Exception e) {
			resultMsg = "SQL_ERROR";
			errorMsg = "프로시저 실행중 에러가 발생";
		}
       	
       	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
       	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg  : " + errorMsg.replaceAll("[\r\n]","")); 
       	log.info("처리후 호출 함수  (callBackFunction)    : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수 2(extCallBackFunction) : " + StringUtil.isNullToString(request.getParameter("extCallBackFunction")).replaceAll("[\r\n]",""));
       		
		return  "/unibill/com/cmm/commonMsg";
		
	}
	
	
	/**
	 * 공통 컨텐츠 일괄등록(엑셀 업로드) 처리 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/jojik/excelUploadContent.do")
    public String excelUploadContent(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		long startTime = System.currentTimeMillis();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));  // 테이블명
        String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));      // xml_id
        String scrinCode  = StringUtil.isNullToString(request.getParameter("scrin_code"));  // scrin_id
        String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
        String resultMsg    = "";
       	String errorMsg     = "";
       	
        int excel_sheet = StringUtil.strToInt(request.getParameter("excel_sheet")); // 엑셀시트
        
        
        
        
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile files = (MultipartFile) multiRequest.getFile("userfile2"); // 업로드파일정보 추출
        
        
        
        String fileName = files.getOriginalFilename();  // 파일명
		long fileSize   = files.getSize();              // 파일사이즈
		
		
		
   		int index = fileName.lastIndexOf(".");
   		String fileExt = fileName.substring(index + 1); // 확장자 추출
   		
   		
   		String newFile  = FileMngUtil.newFileName() + "." + fileExt;
   		String ErrorFile_name = "ERROR_"+ newFile; 		// new 파일명
   		
   		//원본 파일 업로드
   		fileService.fileInsert(request, "content_upload", newFile, "userfile2");  // 파라메터 <HttpServletRequest>, <파일올라갈 위치>, <파일명>
   		
   		
   		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator;	//업로드 패스
   		log.info("############ uploadPath : " + uploadPath.replaceAll("[\r\n]",""));
   		
   		
   		String excelFile = uploadPath + "content_upload" + File.separator + newFile;				//서버원본파일 패스 + 이름
   		File inputFile = new File(excelFile);
   		
   		
   		
   		// 파라미터 
		HashMap parameterObject =  new HashMap();
		parameterObject.put("tableName",       tableName);   // 테이블명
		parameterObject.put("masterTableName", tableName);   // 테이블명
		parameterObject.put("reg_id",          reg_id);      // 등록자id
		parameterObject.put("upd_id",          upd_id);      // 수정자id
		parameterObject.put("xmlId",           xmlId);       // xml_id
		parameterObject.put("scrinCode",       scrinCode);   // scrin_id
		
		
		List<EgovMap> keyCol = contentService.selectKeyColList(parameterObject);			// key 컬럼정보 조회
//    	parameterObject.put("keyCol", contentService.selectKeyColList(parameterObject));
    	
    	List<EgovMap> dbHeader = contentService.getFwformExcelheader(parameterObject);		// HEADER 컬럼 정보 조회
   		
       	HashMap fileUploadProcessRs = null; 												//확장자 체크와 엑셀 업로드
   		
    	if(fileExt.equals("xlsx") || fileExt.equals("xls")){
   			//fileUploadProcessRs= FileUploadContent.telnoexcelUpLoadProcess(excelFile, excel_sheet, fileExt, parameterObject, dbHeader, keyCol, "com", transactionManager, ErrorFile_name);
    		resultMsg = "SAVE_PROCESS";
   	   		errorMsg  = (String)fileUploadProcessRs.get("errorMsg");
   	   		
   	   		if(Integer.valueOf(fileUploadProcessRs.get("successCnt").toString()) > 0 ){
	   			parameterObject.put("REG_ID",          reg_id);      // 등록자id
	   			//saveContentHis( parameterObject,  tableName,  "",  request, "EE" , null );
	   		}
   	   		
   		}else if(fileExt.equals("text")){
   			//fileUploadProcessRs= contentService.textUpLoadProcess();
   		}else if(fileExt.equals("dat")){
   			//fileUploadProcessRs= contentService.datUpLoadProcess();
   		}else{
   			fileUploadProcessRs = new HashMap();
   			fileUploadProcessRs.put("excelTotCnt", 0);
   			fileUploadProcessRs.put("successCnt", 0);
   			fileUploadProcessRs.put("failCnt", 0);
   			
			resultMsg = "SAVE_PROCESS";
			errorMsg  = "지원하지 않는 파일형식 입니다.";
			
   		}
   		
   		String OID = fileUploadProcessRs.get("excelTotCnt")+"|"+fileUploadProcessRs.get("successCnt")+"|"+fileUploadProcessRs.get("failCnt")+"|"+ ErrorFile_name;
   		model.addAttribute("OID", OID);
   		
     	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
     	
   		
   		model.addAttribute("resultMsg", resultMsg);
    	model.addAttribute("errorMsg",  errorMsg);
     	
   		return  "/unibill/com/cmm/commonMsg";
        
     
	}
	
		
}