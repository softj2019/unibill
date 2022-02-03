package com.hsck.ubfw.component.content.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hsck.ubfw.component.com.cmm.service.FileService;
import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.com.cmm.util.CryptoUtils;
import com.hsck.ubfw.component.com.cmm.util.CsvUtils;
import com.hsck.ubfw.component.com.cmm.util.DateUtil;
import com.hsck.ubfw.component.com.cmm.util.ExcelDownLoadContent;
import com.hsck.ubfw.component.com.cmm.util.ExcelDownLoadSheetContent;
import com.hsck.ubfw.component.com.cmm.util.ExcelWriter;
import com.hsck.ubfw.component.com.cmm.util.FileMngUtil;
import com.hsck.ubfw.component.com.cmm.util.FileUploadContent;
import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.com.cmm.util.SqlErrMsg;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.drm.ssg.drm;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.service.MainService;
import com.hsck.ubfw.component.remoteConn.job.remoteIns;
import com.hsck.ubfw.component.remoteConn.query.queryList;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
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
public class ContentController  {

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
	
	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
	
	@Autowired private PlatformTransactionManager transactionManager;
	
	/**
	 * 공통 컨텐츠 화면을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@RequestMapping(value="/content/selectContentList.do")
    public String selectContentList(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		HashMap<String, String> parmObjs =  new HashMap<String, String>();
		
		// 타이틀 조회
		parmObjs.put("cdGun",   "grp_cd");
	    parmObjs.put("grp_cd",   "ADDFILE_EXT");
	    	
		List<EgovMap> codeList = contentService.selectDynamicDataList(parmObjs);
		List<String> addFileExt = new ArrayList<>();
		
		if (codeList.size() > 0) {
			for(int i=0; i<codeList.size(); i++){
				addFileExt.add((String) codeList.get(i).get("code"));
				//log.info(codeList.get(i).get("code"));
			}
		}
		String [] arr = addFileExt.toArray(new String[addFileExt.size()]);
//		for(int i = 0; i < arr.length; i++) {
//			log.info(arr[i]);
//		}
		
		model.addAttribute("addFileExt", Arrays.toString(arr));
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		String addrApiyn = (String) request.getServletContext().getAttribute("UNIBILL_ADDRAPI");
		if(addrApiyn == null || addrApiyn.equals("")) {
			addrApiyn = "N";
		}
		model.addAttribute("addrApiyn",     	addrApiyn);              	// 주소 외부 API 사용 여부
		String sIp = StringUtil.getHostIp(request);		
		String formView   = "";
//		String userid = StringUtil.isNullToString(request.getParameter("userid"));			// userid
		String objid      = StringUtil.isNullToString(request.getParameter("objid"));       // 객체id
		String menu_code  = StringUtil.isNullToString(request.getParameter("menu_code"));   // 메뉴id
		
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
		String formName   = StringUtil.isNullToString(request.getParameter("formid"));      // form name
		String mon   = StringUtil.isNullToString(request.getParameter("mon"));      // form name
		log.info("selectContentList> AAAAAAA mon="+mon.replaceAll("[\r\n]",""));
		if ("".equals(formName)) {
			formName = "frm_main";
		}
		
		HashMap<String, String> parameterObject =  new HashMap<String, String>();
		parameterObject.put("sIp", sIp);
		parameterObject.put("menu_code",  menu_code);	         // 메뉴id
		parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
		parameterObject.put("user_id",  loginVO.getUserId());  // 권한id
		String masterTableName         		= "";
		
		if ("".equals(objid)) {
						
			// 메뉴 및 화면정보 조회
			String scrinId       		= "";
			String scrinId2      		= "";
			String menuNm        		= "";
			String tblNm         		= "";
			String gridhg        		= "";
			String tmpType       		= "";
			String dtlWidth      		= "";
			String dtlTmpType    		= "";
			String xmlId         		= "";
			String scrinSqlYn    		= "";
			String extFun        		= "";
			String dextFun				= "";
			String dblclickYn    		= "";
			String dblclickFun   		= "";
			String clickFun      		= "";
			String gridMakeFun   		= "";
			String mainGridSelYn 		= "";
			String hdYn 		 		= "";
			String excelUseReasonYn 	= "";
			String sumYn 		 		= "";
			String vdiYn 		 		= "";
			
			String posType       	= "";
	    	String sn        		= "";
	    	String txt         		= "";
			
	    	List<EgovMap> menuInfo = contentService.selectMenuInfo(parameterObject);
	    	
	    	for (int i=0; i<menuInfo.size(); i++) {

	    		scrinId       		= (String) menuInfo.get(i).get("scrinId");
	    		menuNm        		= (String) menuInfo.get(i).get("menuNm");
	    		tblNm         		= (String) menuInfo.get(i).get("tblNm");
	    		masterTableName		= (String) menuInfo.get(i).get("tblNm");
	    		gridhg        		= String.valueOf(menuInfo.get(i).get("gridhg"));
	    		dtlTmpType    		= String.valueOf(menuInfo.get(i).get("dtlTmpType"));
	    		tmpType       		= (String) menuInfo.get(i).get("tmpType");
    			scrinId2      		= (String) menuInfo.get(i).get("scrinId2");      		// 탭메뉴의 첫번째 화면ID
    			xmlId         		= (String) menuInfo.get(i).get("xmlId");         		// xml_id
    			scrinSqlYn    		= (String) menuInfo.get(i).get("scrinSqlYn");    		// 화면에서 sql사용여부
    			extFun        		= (String) menuInfo.get(i).get("extFun");        		// 화면open 시 실행함수
    			dextFun        		= (String) menuInfo.get(i).get("dextFun");        		// 상세화면open 시 실행함수
    			dblclickYn    		= (String) menuInfo.get(i).get("dblclickYn");    		// 그리드더블클릭여부    			
    			dblclickFun   		= (String) menuInfo.get(i).get("dblclickFun");   		// 그리드더블클릭함수
    			clickFun      		= (String) menuInfo.get(i).get("clickFun");      		// 그리드클릭함수
    			gridMakeFun   		= (String) menuInfo.get(i).get("gridMakeFun");   		// 그리드생성함수
    			mainGridSelYn 		= (String) menuInfo.get(i).get("mainGridSelYn"); 		// 상단그리드선택여부
    			hdYn		  		= (String) menuInfo.get(i).get("hdYn"); 		   		// 가변컬럼선택여부
    			excelUseReasonYn	= (String)menuInfo.get(i).get("excelUseReasonYn"); 		// 엑셀사유입력여부
    			sumYn		  		= (String) menuInfo.get(i).get("sumYn"); 		   		// 엑셀합계표현여부
    			vdiYn		  		= (String) menuInfo.get(i).get("vdiYn"); 		   		// 엑셀합계표현여부
    	    	//log.debug("TTTTTTTTT vdiYn="+vdiYn );
    			posType       		= (String) menuInfo.get(i).get("posType");
    			//log.debug("TTTTTTTTT posType="+posType );
    	    	sn        			= String.valueOf(menuInfo.get(i).get("sn"));
    	    	//log.debug("TTTTTTTTT sn="+sn );
    	    	txt         		= (String) menuInfo.get(i).get("txt");
    	    	//log.debug("TTTTTTTTT txt="+txt );
	    		if ("".equals(dtlWidth)) {
	    			dtlWidth = (String) menuInfo.get(i).get("scrinId") + "," + menuInfo.get(i).get("dtlW");
	    		} else {
	    			dtlWidth += "^|^" + (String) menuInfo.get(i).get("scrinId") + "," + menuInfo.get(i).get("dtlW");
	    		}
	    	}	    	
	    	
	    	model.addAttribute("scrin_code",     	scrinId);              	// 상단 기본화면의 화면ID
	    	model.addAttribute("scrin_code2",    	scrinId2);             	// 하단 화면의 화면ID (예, 탭메뉴의 첫번째 화면ID)
	    	model.addAttribute("menu_nm",        	menuNm);               	// 메뉴명
	    	model.addAttribute("tableName",      	tblNm);                	// 테이블명	    	
	    	model.addAttribute("gridhg",         	gridhg);               	// 그리드높이
	    	model.addAttribute("tptype",         	tmpType);              	// 템플릿타입   
	    	model.addAttribute("dtlWidth",       	dtlWidth);             	// 화면id별 상세화면 width	    	
	    	model.addAttribute("dtlTmpType",     	dtlTmpType);           	// 상세화면 템플릿구분(I:개별등록, M:개별등록 및 일괄등록)
	    	model.addAttribute("xmlId",          	xmlId);                	// xml_id
	    	model.addAttribute("scrinSqlYn",     	scrinSqlYn);           	// 화면sql 사용여부
	    	model.addAttribute("roleCode",       	loginVO.getRoleId());  	// 권한ID
	    	model.addAttribute("userId",         	loginVO.getUserId());  	// 사용자ID
	    	model.addAttribute("userNm",         	loginVO.getUserNm());  	// 사용자명
	    	model.addAttribute("extFun",         	extFun);               	// 실행함수	
	    	model.addAttribute("dextFun",         	dextFun);               // 상세실행함수
	    	model.addAttribute("mon",         	    mon);               	// 모니터링사용여부
	    	model.addAttribute("dblclickYn",     	dblclickYn);           	// 그리드더블클릭여부	    	
	    	model.addAttribute("dblclickFun",    	dblclickFun);          	// 그리드더블클릭함수
	    	model.addAttribute("clickFun",       	clickFun);             	// 그리드클릭함수
	    	model.addAttribute("gridMakeFun",    	gridMakeFun);          	// 그리드생성함수
	    	model.addAttribute("mainGridSelYn",  	mainGridSelYn);        	// 상단그리드선택여부
	    	model.addAttribute("hdYn",  		 	hdYn);        		 	// 가변컬럼그리드선택여부
	    	model.addAttribute("excelUseReasonYn", 	excelUseReasonYn);		// 엑셀사유입력여부
	    	model.addAttribute("sumYn",  		 	sumYn);        		 	// 엑셀합계표현여부
	    	model.addAttribute("vdiYn",    	    	vdiYn);          	// vdi사용여부
	    	
	    	
	    	
	    	// 화면 안내문구 조회
	    	/*
	    	List<EgovMap> selectGuidList = contentService.selectGuidList(parameterObject);
	    	
	    	List<EgovMap> guidList = new ArrayList<EgovMap>();
	    	
	    	for (int i = 0; i<selectGuidList.size(); i++) {
	    		
	    		EgovMap map = new EgovMap();
	    		
	    		map.put("pos_type",  selectGuidList.get(i).get("posType"));    		    	
	    		map.put("sn",        selectGuidList.get(i).get("sn"));
	    		map.put("txt",       selectGuidList.get(i).get("txt"));
	    		
	    		guidList.add(map);
	    	}
	    	
	    	*/
	    	log.debug("TTTTTTTTT vdiYn="+vdiYn.replaceAll("[\r\n]","") + " menuNm="+menuNm.replaceAll("[\r\n]",""));
	    	if(vdiYn.equals("Y")) {	    	
		    	int ipCnt = contentService.selectIpCnt(parameterObject);
		    	if(ipCnt >= 1) {
		    		model.addAttribute("ipCheck",   true);
		    	}else{
					model.addAttribute("ipCheck",   false);
				} 
	    	}
	    	 
	    	List<EgovMap> guidList = new ArrayList<EgovMap>();
	    	EgovMap map = new EgovMap();
    		map.put("pos_type",  posType);    		    	
    		map.put("sn",        sn);
    		map.put("txt",       txt); 
    		guidList.add(map); 
    		
	    	model.addAttribute("guidInfo",   guidList);
	    	 
	    	 
	    	parameterObject.put("scrin_code", scrinId); // 화면id
	    	parameterObject.put("show_yn", "2");        // 그리드출력옵션
	    	parameterObject.put("col_tbl", "Y");        // col_tbl 사용
        // 그리드출력옵션
	    	
	    	// 그리드 정보 조회
	    	String colNameList  	= "";
	    	String colModelList 	= "";
	    	String colWidth     	= "";
	    	String colAlign     	= "";
	    	String colShowType  	= "";
	    	String colMaskingList	= "";
	    	
	    	List<EgovMap> selectColumnsList = contentService.selectColumnsList(parameterObject);	    	
	    	session.removeAttribute("selectColumnsList");
	    	session.setAttribute("selectColumnsList", selectColumnsList);
	    	
	    	for (int i=0; i<selectColumnsList.size(); i++) {
	    		
	    		String sGubun = "";    		    		
	    		if (i < selectColumnsList.size()-1) sGubun = "|";
	    		
	    		String colName   = (String) selectColumnsList.get(i).get("objNm");
	    		String colModel  = (String) selectColumnsList.get(i).get("colId");
	    		String colwidth  = (String) StringUtil.isNullToString(selectColumnsList.get(i).get("cellW"));
	    		String colalign  = (String) selectColumnsList.get(i).get("cellSort");	    		
	    		String showType  = (String) selectColumnsList.get(i).get("showYn");
	    		
	    		colNameList   += colName  + sGubun;
	    		colModelList  += colModel + sGubun;
	    		colWidth      += colwidth + sGubun;
	    		colAlign      += colalign + sGubun;
	    		colShowType   += showType + sGubun;
	    		if(isGridMaskYn(colModel)) {
	    			colMaskingList+= "Y" + sGubun;
	    		}else {
	    			colMaskingList+= "N" + sGubun;
	    		}
	    		
	    	}
	    		    	
	    	model.addAttribute("colNameList",  colNameList);               // 그리드 name
	    	model.addAttribute("colModelList", colModelList);              // 그리드 model
	    	model.addAttribute("colWidth",     colWidth);                  // 그리드 셀 width
	    	model.addAttribute("colAlign",     colAlign);                  // 그리드 셀 정렬
	    	model.addAttribute("colShowType",  colShowType);               // 그리드 show 타입
	    	model.addAttribute("colMaskingList", colMaskingList);	  	   // 그리드 masking 여부
	    	
	    	// 하단 탭메뉴 정보 조회	    	
	    	if ("C".equals(tmpType)) {
	    		
	    		// 탭메뉴 정보
	    		parameterObject.put("role_code", loginVO.getRoleId());  // 권한id
	    		List<EgovMap> selectTabList = contentService.selectTabList(parameterObject);
	    		
	    		List<EgovMap> tabList = new ArrayList<EgovMap>();
	    		
	    		for (int j=0; j<selectTabList.size(); j++) {
		    		EgovMap tabMap = new EgovMap();
			    		
			    	tabMap.put("menuId",  selectTabList.get(j).get("menuId"));    		    	
			    	tabMap.put("tabId",   selectTabList.get(j).get("tabId"));
			    	tabMap.put("tabNm",   selectTabList.get(j).get("tabNm"));
			    	tabMap.put("tblNm",   selectTabList.get(j).get("tblNm"));
			    		
			    	tabList.add(tabMap);
	    			
		    	}
	    		
	    		model.addAttribute("tabList",   tabList);
	    			    		
	    	}
	    	
	    	// 상단화면 및 탭메뉴 하단에 해당되는 버튼 정보 조회
    		List<EgovMap> selectTabBtnList = contentService.selectTabBtnList(parameterObject);
    		model.addAttribute("tabBtnList",   selectTabBtnList);

	    	formView = "unibill_tiles/content/contentList";
	    	
		} else {
			
			formView = "unibill/content/contentSubForm";
		
			parameterObject.put("scrin_code",   scrin_code); // 화면id
		}
    	
    	// 검색 조건 정보 조회		
    	parameterObject.put("obj_id",       objid);      // 객체ID
    	parameterObject.put("find_show_yn", "Y");        // 검색조건 출력여부
    	
    	String owner = fileUploadProperties.getProperty("Globals.UserName");		
		parameterObject.put("owner", owner);
		parameterObject.put("masterTableName", masterTableName);
		List<EgovMap> selectSearchList = contentService.selectSearchObjectList(parameterObject);
		List<EgovMap> searchList = new ArrayList<EgovMap>(); 
    	
    	int idNum = 0;
    	for (int i = 0; i<selectSearchList.size(); i++) {
    		EgovMap map = new EgovMap();

    		map.put("objid",        selectSearchList.get(i).get("objId"));
    		map.put("pobjid",       selectSearchList.get(i).get("upObjId"));
    		map.put("name",         selectSearchList.get(i).get("objNm"));
    		map.put("pcolid",       selectSearchList.get(i).get("upColId"));    		
    		map.put("objpos",       selectSearchList.get(i).get("objPos"));
    		map.put("objtype",      selectSearchList.get(i).get("objType"));
    		map.put("width",        selectSearchList.get(i).get("findItemW"));
    		map.put("colpos",       selectSearchList.get(i).get("colPos"));
    		map.put("rowpos",       selectSearchList.get(i).get("rowPos"));
    		map.put("xmlid",        selectSearchList.get(i).get("xmlId"));
    		map.put("grpcd",        selectSearchList.get(i).get("grpCd"));      		
    		map.put("grptype",      selectSearchList.get(i).get("grpType"));
    		map.put("subformyn",    selectSearchList.get(i).get("subformyn"));    		
    		map.put("maxxpos",      selectSearchList.get(i).get("maxxpos"));    
    		map.put("colspan",      selectSearchList.get(i).get("dtlRowMgr"));
    		map.put("objwidth",     selectSearchList.get(i).get("w"));    
    		map.put("dayfindyn",    selectSearchList.get(i).get("dayFindYn"));
    		map.put("sql",          selectSearchList.get(i).get("objSql"));         // fwobj sql문
    		map.put("fwformobjsql", selectSearchList.get(i).get("fwformObjSql"));   // fwform sql문
    		map.put("autogencol",   selectSearchList.get(i).get("autoGenCol"));
    		map.put("mustyn",       selectSearchList.get(i).get("mustYn"));
    		map.put("extFun",       selectSearchList.get(i).get("extFun"));
    		map.put("findType",     selectSearchList.get(i).get("findType"));
    		map.put("maxlen",       selectSearchList.get(i).get("maxLen"));
    		map.put("findIdSql",    selectSearchList.get(i).get("findIdSql"));
    		map.put("formname",     formName);
    		String colid = (String) selectSearchList.get(i).get("colId");
    		if (colid.contains("_tm")) {  // 등록일시, 수정일시
    			
    			colid = idNum + "_" + colid;
    			idNum += 1;
    			
    		} 
    		/*
    		else if (colid.indexOf("_yn") > -1) {  // 코드여부일 경우 xmlid로 코드 조회
    			
    			HashMap<String, String> parmObj =  new HashMap<String, String>();
    			
    			parmObj.put("cdGun",   "xml_id");
    			parmObj.put("xmlid",   "selectYnCode");

    			map.put("dynData", ContentUtil.dynData(parmObj, (String) map.get("objtype"), ""));
    			
    		} 
    		*/   	
    		map.put("colid",     colid);
    		// 기준정보 및 기초코드(공통코드) 조회(콤보, 체크박스, 라디오버튼 등)
    		if (!StringUtils.isEmpty((String)map.get("xmlid")) || !StringUtils.isEmpty((String)map.get("grpcd")) || !StringUtils.isEmpty((String)map.get("grptype")) || !StringUtils.isEmpty((String)map.get("sql")) || !StringUtils.isEmpty((String)map.get("fwformobjsql")) ) {
    			if(StringUtils.isEmpty((String)map.get("sql")) && !StringUtils.isEmpty((String)map.get("fwformobjsql"))){
    				map.put("sql", map.get("fwformobjsql"));
    			}
    			map.put("dynData", ContentUtil.RequestDynDataMap((String) map.get("grptype"), (String) map.get("grpcd"), (String) map.get("xmlid"), (String) map.get("sql"), (String) map.get("objtype"), (String) map.get("fwformobjsql"), parameterObject));
    		}    		    			
    		else if (colid.indexOf("_yn") > -1) {  // 코드여부일 경우 xmlid로 코드 조회
    			HashMap<String, String> parmObj =  new HashMap<String, String>();
    			parmObj.put("cdGun",   "xml_id");
    			parmObj.put("xmlid",   "selectYnCode");
    			map.put("dynData", ContentUtil.dynData(parmObj, (String) map.get("objtype"), ""));
    		} 
    		searchList.add(map);	 
    		
    	}   
    	model.addAttribute("searchInfo",       searchList);    	
    	model.addAttribute("menu_code",        menu_code);
    	model.addAttribute("now_date",         DateUtil.getCurrentDateString("yyyy-MM-dd"));
    	model.addAttribute("auto_search_yn",   fileUploadProperties.getProperty("Globals.AutoSearchYn"));
    	
    	return formView;	 
	}
		

	/**
	 * 공통 컨텐츠 DATA를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectContentList.json")
    public @ResponseBody Map<String, Object> selectContentListJson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//특수문자 체크
		boolean chkparmRs = ContentUtil.chkparam(request);
		
		//검색조건 체크
		HttpServletRequest Rrequest = ContentUtil.modifyparam(request);
		request = Rrequest;
		
		

		
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
		String menu_code = StringUtil.isNullToString(request.getParameter("menu_code"));  // menu_id		
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
		String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));       // xml_id
		String scrinSqlYn = StringUtil.isNullToString(request.getParameter("scrinSqlYn"));  // 화면sql사용여부
		String tptype     = StringUtil.isNullToString(request.getParameter("tptype"));      // 템플릿타입
		String hdYn       = StringUtil.isNullToString(request.getParameter("hdYn"));      	// 가변컬럼사용여부
		String mon        = StringUtil.isNullToString(request.getParameter("mon"));      	// 가변컬럼사용여부
		
		log.info("########## /content/selectContentList.json ########## [scrin_code : " + scrin_code.replaceAll("[\r\n]","")+ "][menu_code : " + menu_code.replaceAll("[\r\n]","") +  "][xmlId : " + xmlId.replaceAll("[\r\n]","") + "][tptype : " + tptype.replaceAll("[\r\n]","") + "][scrinSqlYn : " + scrinSqlYn.replaceAll("[\r\n]","") + "][hdYn : " + hdYn.replaceAll("[\r\n]","") + "][mon : " + mon.replaceAll("[\r\n]","") + "]");
    	
    	HashMap parameterObject =  new HashMap();
    	
    	parameterObject.put("del_yn",   "T");    // 기본 머지세팅
    	parameterObject.put("scrin_code",  scrin_code);             // 메뉴ID
    	parameterObject.put("role_code",   loginVO.getRoleId());    // 권한ID
    	parameterObject.put("user_id",     loginVO.getUserId());    // 로그인ID
    	parameterObject.put("dept_grp_id", loginVO.getDeptGrpId()); // 부서코드
    	parameterObject.put("mon", mon); // 모니터링구분 

    	int roleYn = contentService.selectRoleMenu(parameterObject);
    	
    	if(roleYn==0){
    		return null;
    	}
    	
    	List selectColumnsList = new ArrayList<EgovMap>();
    	List selectHdList = new ArrayList<EgovMap>();
    	 
    	// xmlid 검색이 아니고 화면 템플릿이' 통계(E)이 아닌 경우
    	log.info("########## xmlid  :" + xmlId.replaceAll("[\r\n]",""));
    	log.info("########## tptype  :" + tptype.replaceAll("[\r\n]",""));
 
    	if ("".equals(xmlId) && !"E".equals(tptype)) {
    	
	    	// 검색 조건 정보 조회
    		String owner = fileUploadProperties.getProperty("Globals.UserName");		
			parameterObject.put("owner", owner);
	    	List<EgovMap> selectSearchList = contentService.selectSearchObjectList(parameterObject);
	    	log.info("########## 검색 조건 정보 조회 (selectSearchList) =====> " + String.valueOf(selectSearchList).replaceAll("[\r\n]",""));
	    		
	    	String tableName = StringUtil.isNullToString(request.getParameter("tableName"));
	    	parameterObject = ContentUtil.RequestToHashMap(request, selectSearchList, "R", tableName);
	    	log.info("########## 검색 항목만 저장 화면 ID 값 (parameterObject) =====> " + String.valueOf(parameterObject).replaceAll("[\r\n]",""));
	    	
	    	
	    	// 검색 컬럼 쿼리 생성
	        List searchColumnsList = ContentUtil.searchColumnsList(parameterObject, selectSearchList, request);
	        log.info("########## 검색 컬럼 쿼리 생성(searchColumnsList) =====> " + String.valueOf(searchColumnsList).replaceAll("[\r\n]",""));
    	
	    	// 조회 컬럼 항목
	        parameterObject.put("scrin_code", scrin_code);
	        parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
	        parameterObject.put("show_yn", "2");
	        parameterObject.put("col_tbl", "Y");        // col_tbl 사용
	        selectColumnsList = contentService.selectColumnsList(parameterObject);
	        log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
	           
	        // 조회 컬럼 항목 쿼리 생성
	        List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
	        parameterObject.put("selectColumnsList", selectColumnsRetList);
	        log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
	        
	        // ORDER BY 컬럼 항목 쿼리 생성
	        List selectColumnsListInit = new ArrayList();
	        for(Object egov: selectColumnsList){
	        	((EgovMap)egov).get("colId").toString();
	        	if( !(((EgovMap)egov).get("colId").toString().indexOf("from_") > -1 
	        			|| ((EgovMap)egov).get("colId").toString().indexOf("to_") > -1 
	        			|| ((EgovMap)egov).get("colId").toString().indexOf("msg") > -1 
	        			|| ((EgovMap)egov).get("colId").toString().indexOf("remark") > -1
	        		//	|| ((EgovMap)egov).get("findType").toString().equals("N")  //order by 제외       	
	        			)){	
	        		String colstr=((EgovMap)egov).get("colId").toString();
	        		selectColumnsListInit.add(colstr);
	        	}
	        	
	        	// role_id 컬럼 존재 여부 확인
	        	if(((EgovMap)egov).get("colId").toString().equals("role_id") && !loginVO.getRoleId().equals("D001")){
	        		searchColumnsList.add(" and ROLE_ID in ( select role_id from fwrole where role_level >= ( select role_level from fwrole where role_id= '"+loginVO.getRoleId()+"'  )  ");
	        		searchColumnsList.add("   and role_grp in (select role_grp from fwrole where role_id= '"+loginVO.getRoleId()+"' )  )");
	        	}
	        }
	        parameterObject.put("searchColumnsList", searchColumnsList);
	        parameterObject.put("selectColumnsListInit", selectColumnsListInit);
	        
	        
	    // 가변컬럼사용    
    	} else if(!"".equals(xmlId) && "V".equals(tptype) && "Y".equals(hdYn)){
    		log.info("#### 가변컬럼 ####");
    		
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    		    
    		    parameterObject.put(key,  val);
    		}
    		
    		// 컬럼이름 추출
		    selectHdList = contentService.selectHdList(parameterObject, xmlId);
		    
		    ArrayList<String> str = new ArrayList<String>();
			ArrayList<Map> str2 = new ArrayList<Map>();
			
			if(selectHdList.size() > 0){
				for(int i=0; i<selectHdList.size(); i++ ){
					str.add("c"+i);
					HashMap<String, String> list = new HashMap<String, String>();
					list.put("col", "c"+i);
					list.put("nm", ((EgovMap)selectHdList.get(i)).get("hdnm").toString());
					str2.add(list);
					
//					str2.add("sum(case when col_nm = '" + ((EgovMap)selectHdList.get(i)).get("hdNm") + "' then cost else 0 end) as hsCol" + i);
				}
				parameterObject.put("collist", str2);
				
				

				//jsonData = htongwidthService.selectUnyongList(parameterObject);
				//total_count = htongwidthService.selectUnyongListCnt(parameterObject);
					
			}
    		
    		
	    // xml id 검색인 경우
    	} else if(!"".equals(xmlId)) {
    		String val = "";
    		String[] vals = null;
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			// 파라미터가 여러개 처리
    			if(key.indexOf('[') > 1 && !key.equals("undefined[]")) {
    				vals = request.getParameterValues(key);
    				String rekey = key.replace("[", "").replace("]", "");
    				for(int i=0; i< vals.length; i++) {
    					log.info("############# xml id 검색 [key : " + rekey.replaceAll("[\r\n]","") + "_" + vals[i].replaceAll("[\r\n]","") + "][val : " + vals[i].replaceAll("[\r\n]","") + "]");
    					parameterObject.put(rekey + "_" + vals[i],  vals[i]); 
    				}
    			}else {
    				val = request.getParameter(key);
    				log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    				
    				if(key.equals("Ltelno") && val != null && val.length()>0 ) {
        		    	if(val.indexOf(',') <0) {
    	    		    	val += ",";
    	    		    }
        	    		    
    	    		    //telno, scrin_id
        		    	List lval = Arrays.asList(val.split(","));
        		    	List lstr = new ArrayList(); 
        		    	//리스트의 널값 제거
        		    	for(int i=0; i<lval.size(); i++) {
        		    		if(lval.get(i) != null && !lval.get(i).equals("")) {
        		    			String str = (String)lval.get(i);
        		    			lstr.add(str);
        		    		}
        		    	}
        		    	parameterObject.put(key,  lstr); 
        		    	log.info("############# ZZZZZZZZZZZZZ [key : " + key.replaceAll("[\r\n]","") + "][lstr : " + String.valueOf(lstr).replaceAll("[\r\n]","") + "]");
        		    }
        		    else {
        		    	parameterObject.put(key,  val); 
        		    }
    			}
    			
    			    			
    		    
    		    
    		    //변수의 값이 쉼표로 들어오는 경우 key값 앞에 문자L을 붙인다. Ltelno
    		     
    		   
    		    
    		    
    		    
    		}
    		
    		// 통계(E)인 경우
    		if ("E".equals(tptype)) {
    			
    			// 조회 컬럼 항목
//    	        parameterObject.put("scrin_code", scrin_code);
    	        parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
    	        parameterObject.put("show_yn", "2");
    	        selectColumnsList = contentService.selectColumnsList(parameterObject);
    	        log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));    			
    	        
    	        // 쿼리문 조회
				HashMap sqlMap = (HashMap) contentService.getScrinSqlClob(parameterObject);
    			String sql1 = StringUtils.defaultIfBlank( (String) sqlMap.get("scrinSqlClob1") , (String) sqlMap.get("scrinSql1") );
    			String sql2 = StringUtils.defaultIfBlank( (String) sqlMap.get("scrinSqlClob2") , (String) sqlMap.get("scrinSql2") );
    			String sql3 = StringUtils.defaultIfBlank( (String) sqlMap.get("scrinSqlClob3") , (String) sqlMap.get("scrinSql3") );

    			
    			parameterObject.put("sql",  sql1);
    			parameterObject.put("sql2", sql2);
    			parameterObject.put("sql3", sql3);
    			
    		}
    		
    	}
    	// 페이징 처리      	
		int page = StringUtil.strToInt(request.getParameter("page"));
    	int rows = StringUtil.strToInt(request.getParameter("rows"));
    	int firstIndex = rows * page - rows;    	
    	parameterObject.put("firstIndex", firstIndex);
 	    parameterObject.put("lastIndex", firstIndex+rows);
 	    
 	    // mysql
 	    int offsetSize = (page - 1) * rows;
 	    parameterObject.put("offsetSize", offsetSize);  //limit 사용시 개수
 	    parameterObject.put("limitSize", rows);  //limit 사용시 개수
	    
    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));
    	
    	if (!sidx.equals("")) {
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (!sord.equals("")) {
			parameterObject.put("OrderByType",sord);
		}
        
		parameterObject.put("mon", mon); // 모니터링구분 
		parameterObject.put("menu_code", menu_code); // 모니터링구분
		 
		String find_cond = StringUtil.isNullToString(contentService.getWhereCond(parameterObject));
        String mon_cond = StringUtil.isNullToString(contentService.getMonWhereCond(parameterObject));    
        String wheresql="";
        log.info("##################### pjh find_cond : " + find_cond.replaceAll("[\r\n]","") +" mon_cond="+mon_cond.replaceAll("[\r\n]",""));        
         
        if (!"".equals(mon_cond) && !"".equals(find_cond) ) {
        	wheresql = find_cond + " and "+mon_cond;    
        }
        else if (!"".equals(mon_cond) || !"".equals(find_cond) ) {
        	wheresql = find_cond + mon_cond;    
        }
        
        if (!"".equals(wheresql)) {
        	parameterObject.put("whereCol",wheresql);
        }
        
        // 데이터 조회
    	String tableName = StringUtil.isNullToString(request.getParameter("tableName"));
    	parameterObject.put("tableName", tableName);
    	String tmp_type = "";
    	
    	List<EgovMap> jsonData = null;
    	int total_count =0;
    	try {
    		jsonData = contentService.selectDataList(parameterObject, xmlId, tptype);
        	total_count = contentService.selectDataListCnt(parameterObject, xmlId, tptype);
		} catch (Exception e) {
			
		}
    	
    	String qResult = "";
    	if(jsonData == null || total_count == -1 ){
    		qResult = "FAIL";
    	}else if(chkparmRs == false) {
    		qResult = "PFAIL"; 	// 허용되지 않은 특수문자 체크
    	}else{
    		qResult = "SUCCESS";
    	}
    	
    	Map<String , Object> returnMap = new HashMap<String , Object>();
    	
    	returnMap.put("qResult", qResult);
        
        log.debug("########## parameterObject: " + String.valueOf(parameterObject).replaceAll("[\r\n]",""));
        log.debug("########## xmlId: " + xmlId.replaceAll("[\r\n]",""));
        log.debug("########## tptype: " + tptype.replaceAll("[\r\n]",""));
        log.debug("########## LINE: " + new Throwable().getStackTrace()[0].getLineNumber());
        int total_page = 0;
        if (total_count > 0) {
        	total_page = (int) Math.ceil( (float)total_count/(float)rows );
        }
        
        
        returnMap.put("page", page);
        returnMap.put("total", total_page);
        returnMap.put("records", total_count);
        // 화면SQL을 사용하는 경우
        if ("Y".equals(scrinSqlYn)) { 
        	log.debug("########## LINE: " + new Throwable().getStackTrace()[0].getLineNumber());
        	returnMap.put("rows", jsonData);
        }else if (!"".equals(xmlId)) {  // xml_id 를 사용하는 경우
        	log.debug("########## LINE: " + new Throwable().getStackTrace()[0].getLineNumber());
        	//조회 결과 값을 String으로 변환
        	List<EgovMap> convString = new ArrayList<EgovMap>();

        	if(jsonData == null){
        		returnMap.put("rows", jsonData);
        	}else{
        		for (int i=0; i<jsonData.size(); i++) {
            		try {
            			Map map = (Map) jsonData.get(i);
                    	Iterator<String> it =  map.keySet().iterator();
                    	EgovMap map2 = new EgovMap();

                    	while(it.hasNext()) {
                    		String keyNm = (String)it.next();
              	    		map2.put(keyNm, map.get(keyNm).toString());
              	    	}
                    	convString.add(map2);
                    	returnMap.put("rows", convString);
        			} catch (Exception e) {
        				returnMap.put("rows", jsonData);
        			}
                	
            	}
        	}
        	
        	
 //       	returnMap.put("rows", jsonData);
 //       	returnMap.put("rows", convString);
        } else {  // 그외인 경우
        	log.debug("########## LINE: " + new Throwable().getStackTrace()[0].getLineNumber());
        	returnMap.put("rows", ContentUtil.selectJsonDataList("G", jsonData, selectColumnsList, unibillFuncEnc, loginVO.getRoleId()));
        	
        } 
        log.debug("########## LINE: " + new Throwable().getStackTrace()[0].getLineNumber());
        
        
        //이력 조회 결과 입력
        parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID
        saveContentHis( parameterObject,  tableName,  "",  request, "R", jsonData ); 
    	return returnMap;
    }
	
	/**
	 * 엑셀 다운로드
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/contentExcelDown.do")
    public String contentExcelDown(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    	    	
    	// 세션 사용자정보
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);	
    	String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));
		String sTableName = StringUtil.isNullToString(request.getParameter("tableName"));    	
    	String sFileName  = StringUtil.isNullToString(request.getParameter("fileName")).replace(" ", "");
    	String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));       // xml_id
    	String tptype     = StringUtil.isNullToString(request.getParameter("tptype"));      // 템플릿타입
    	String hdYn       = StringUtil.isNullToString(request.getParameter("hdYn"));      	// 가변컬럼사용여부
    	String sumYn       = StringUtil.isNullToString(request.getParameter("sumYn"));      // 합계표현여부
    	String excelUseReasonYn = StringUtil.isNullToString(request.getParameter("excelUseReasonYn"));
    	String reasonMsg = StringUtil.isNullToString(request.getParameter("reasonMsg"));
    	
    	HashMap parameterObject =  new HashMap();
    	
    	List<EgovMap> menuScrinList = mainService.selectMenuScrinList(parameterObject);
		if(menuScrinList.size()>0){
			parameterObject.put("menu_id", (String)menuScrinList.get(0).get("menuId"));
		} else {
			parameterObject.put("menu_id", "-");
		}
    	
    	List selectColumnsList = new ArrayList<EgovMap>();
    	List selectHdList = new ArrayList<EgovMap>();
    	List selectHdSumList = new ArrayList<EgovMap>();
    	List selectDataSumList = new ArrayList<EgovMap>();
    	boolean checkRoleId = false;
    	
    	parameterObject.put("sumYn", sumYn);  // 합계표현여부
    	parameterObject.put("scrin_code", scrin_code);  // 메뉴ID
    	parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
    	parameterObject.put("user_id", loginVO.getUserId());
    	parameterObject.put("reasonMsg", reasonMsg);
    	 if("Y".equals(excelUseReasonYn)) {
         	contentService.insertexcelReason(parameterObject);
         }
    	
    	// xmlid 검색이 아니고 화면 템플릿이 통계(E)이 아닌 경우
    	if ("".equals(xmlId) && !"E".equals(tptype)) {
    	
	    	// 검색 조건 정보 조회
    		String owner = fileUploadProperties.getProperty("Globals.UserName");		
			parameterObject.put("owner", owner);
	    	List<EgovMap> selectSearchList = contentService.selectSearchObjectList(parameterObject);
	    	log.info("########## 검색 조건 정보 조회 (selectSearchList) =====> " + String.valueOf(selectSearchList).replaceAll("[\r\n]",""));
	    		
	    	// 검색 항목만 저장 화면 ID 값    	
	    	String cust_id="";
	    	parameterObject = ContentUtil.RequestToHashMap(request, selectSearchList, "E",sTableName);
	    	log.info("########## 검색 항목만 저장 화면 ID 값 (parameterObject) =====> " + String.valueOf(parameterObject).replaceAll("[\r\n]",""));
	     
	    	
	    	// 검색 컬럼 쿼리 생성
	        List searchColumnsList = ContentUtil.searchColumnsList(parameterObject, selectSearchList, request);
	        
	        parameterObject.put("searchColumnsList", searchColumnsList);
	        log.info("########## 검색 컬럼 쿼리 생성(searchColumnsList) =====> " + String.valueOf(searchColumnsList).replaceAll("[\r\n]",""));
    	
	    	// 조회 컬럼 항목
	        parameterObject.put("scrin_code", scrin_code);
	        parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
	        parameterObject.put("show_yn", "3");
	        selectColumnsList = contentService.selectColumnsList(parameterObject);
	        log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
	        
	        // 조회 컬럼 항목 쿼리 생성    	
	        List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
	        parameterObject.put("selectColumnsList", selectColumnsRetList);
	        log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
	        
	        // ORDER BY 컬럼 항목 쿼리 생성
	        List selectColumnsListInit = new ArrayList();
	        for(Object egov: selectColumnsList){
	        	((EgovMap)egov).get("colId").toString();
	        	if(!(((EgovMap)egov).get("colId").toString().indexOf("from_") > -1 || ((EgovMap)egov).get("colId").toString().indexOf("to_") > -1)){
	        		selectColumnsListInit.add(((EgovMap)egov).get("colId").toString());
	        	}
	        	
	        	// role_id 컬럼 존재 여부 확인
	        	if(((EgovMap)egov).get("colId").toString().equals("role_id") && !loginVO.getRoleId().equals("D001")){
	        		searchColumnsList.add(" and ROLE_ID in ( select role_id from fwrole where role_level >= ( select role_level from fwrole where role_id= '"+loginVO.getRoleId()+"'  )  ");
	        		searchColumnsList.add("   and role_grp in (select role_grp from fwrole where role_id= '"+loginVO.getRoleId()+"' )  )");
	        	}
	        	
	        }
	        parameterObject.put("searchColumnsList", searchColumnsList);
	        parameterObject.put("selectColumnsListInit", selectColumnsListInit);
	        parameterObject.put("sumYn", sumYn);  // 합계표현여부
	        
	        
	     // 가변컬럼사용    
    	} else if(!"".equals(xmlId) && "V".equals(tptype) && "Y".equals(hdYn)){
    		log.info("#### 가변컬럼 ####");
    		
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    		    
    		    parameterObject.put(key,  val);
    		}
    		
    		// 조회 컬럼 항목
            parameterObject.put("scrin_code", scrin_code);           // 화면id
            parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
            parameterObject.put("show_yn", "3");                     // 출력옵션
        	selectColumnsList = contentService.selectColumnsList(parameterObject);
            log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
                		
    		// 컬럼이름 추출
		    selectHdList = contentService.selectHdList(parameterObject, xmlId);
		    
		    ArrayList<Map> str2 = new ArrayList<Map>();
			// 헤더에 가변 컬럼ID, 컬럼이름 추가
		    if(selectHdList.size() > 0){
				for(int i=0; i<selectHdList.size(); i++ ){
					EgovMap list = new EgovMap();
					list.put("colId", "c"+i);
					list.put("objNm", ((EgovMap)selectHdList.get(i)).get("hdnm").toString());
					selectColumnsList.add(list);
				}
			}
		    // data 조회시 가변 컬럼 쿼리 생성
			if(selectHdList.size() > 0){
				for(int i=0; i<selectHdList.size(); i++ ){
					HashMap<String, String> list = new HashMap<String, String>();
					list.put("col", "c"+i);
					list.put("nm", ((EgovMap)selectHdList.get(i)).get("hdnm").toString());
					str2.add(list);
				}
				parameterObject.put("collist", str2);
			}
			// 합계 정보추출
			if("Y".equals(sumYn)){
				selectHdSumList = contentService.selectHdSumList(parameterObject, xmlId);
				selectDataSumList = contentService.selectDataSumList(parameterObject, xmlId);
				
				parameterObject.put("selectHdSumList", selectHdSumList);
				parameterObject.put("selectDataSumList", selectDataSumList);
			}
    		
	    // xml id 검색인 경우
    	}  else {   
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		String[] vals = null;
			String val = "";
			while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			// 파라미터가 여러개 처리
    			if(key.equals("search_col_type") && !key.equals("undefined[]")) {
    				vals = request.getParameterValues(key);
    				String rekey = key.replace("[", "").replace("]", "");
    				for(int i=0; i< vals.length; i++) {
    					log.info("############# xml id 검색 [key : " + rekey.replaceAll("[\r\n]","") + "_" + vals[i].replaceAll("[\r\n]","") + "][val : " + vals[i].replaceAll("[\r\n]","") + "]");
    					parameterObject.put(rekey + "_" + vals[i],  vals[i]); 
    				}
    			}else {
    				val = request.getParameter(key);  
    				log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
        			//변수의 값이 쉼표로 들어오는 경우 key값 앞에 문자L을 붙인다.
        		    if(val != null && val.indexOf(',') != -1) {
        		    	List lval = Arrays.asList(val.split(","));
        		    	List lstr = new ArrayList(); 
        		    	//리스트의 널값 제거
        		    	for(int i=0; i<lval.size(); i++) {
        		    		if(lval.get(i) != null && !lval.get(i).equals("")) {
        		    			String str = (String)lval.get(i);
        		    			lstr.add(str);
        		    		}
        		    	}
        		    	parameterObject.put("L"+key,  lstr);
        		    }else {
        		    	parameterObject.put(key,  val);
        		    }
    			}
    			
    			
    		}
			
			//맥스 뎁스 구하기
			if(scrin_code.equals("M050129")){
				int maxdepth = contentService.getMaxDepth("selectMonJojikMaxDepth", parameterObject);
				ArrayList maxdepthList = new ArrayList();
				for(int i=0; i<maxdepth; i++) {
					maxdepthList.add("depth" + i+1);
				}
				parameterObject.put("maxdepthList",  maxdepthList);
				parameterObject.put("maxdepth",  maxdepth);
			}
    		
        	// 조회 컬럼 항목
            parameterObject.put("scrin_code", scrin_code);           // 화면id
            parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
            parameterObject.put("show_yn", "3");                     // 출력옵션
        	selectColumnsList = contentService.selectColumnsList(parameterObject);
            log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
            
            //조회 컬럼 제거
            if(scrin_code.equals("M050129")){
	            for(int i=0; i<selectColumnsList.size(); i++) {
	            	Map mapColList = (Map) selectColumnsList.get(i);
					String enColumns = mapColList.get("colId").toString();	
					if(parameterObject.get("search_col_type_A") == null && enColumns.equals("cnt")) {
	            		selectColumnsList.remove(mapColList);
	            		i--;
	            	}else if(parameterObject.get("search_col_type_B") == null && enColumns.equals("cost")) {
	            		selectColumnsList.remove(mapColList);
	            		i--;
	            	}else if(parameterObject.get("search_col_type_C") == null && enColumns.equals("dosu")) {
	            		selectColumnsList.remove(mapColList);
	            		i--;
	            	}else if(parameterObject.get("search_col_type_D") == null && enColumns.equals("dur")) {
	            		selectColumnsList.remove(mapColList);
	            		i--;
	            	}
	            }
            }
            
            // 조회 컬럼 항목 쿼리 생성
            List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
            parameterObject.put("selectColumnsList", selectColumnsRetList);
            log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
            
            // 합계 정보추출
         	if("Y".equals(sumYn)){
         		selectHdSumList = contentService.selectHdSumList(parameterObject, xmlId);
         		selectDataSumList = contentService.selectDataSumList(parameterObject, xmlId);
         		if(selectDataSumList.get(0) == null) {
         			EgovMap data = new EgovMap();
         			data.put("tcost", "0");
         			selectDataSumList.set(0, data);
         		}
         		
         		parameterObject.put("selectHdSumList", selectHdSumList);
         		parameterObject.put("selectDataSumList", selectDataSumList);
         		
         	}
    	}
    	


    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));  
    	if (!sidx.equals("")) {
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (!sord.equals("")) {
			parameterObject.put("OrderByType",sord);
		}
		
        // 엑셀 DATA
        parameterObject.put("tableName", sTableName);
    	parameterObject.put("user_id",     loginVO.getUserId());    // 로그인ID
    	parameterObject.put("dept_grp_id", loginVO.getDeptGrpId()); // 부서코드 
    	parameterObject.put("tptype", tptype);						// 템플릿 타입

        log.info("########## 엑셀데이터 조회(data_list) =====> ");

        model.addAttribute("sub_path",    "excel_download");
        
        
        String retFileName = ExcelDownLoadContent.ExcelDownLoadMakeHandler(sFileName, "excel_download", selectColumnsList, request, response , parameterObject, xmlId, contentService);
        
        String resultMsg = "EXCEL_SUCCESS";
        if ("false".equals(retFileName)) {
        	resultMsg = "ERROR";
        }
        
        String OID = retFileName;
        model.addAttribute("OID", OID);
        model.addAttribute("resultMsg",  resultMsg);    // 엑셀다운로드 결과
    	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
    	
    	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수(callBackFunction) : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	
       	parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID      
        saveContentHis( parameterObject,  "",  "",  request, "E" , null );
		
    	return  "/unibill/com/cmm/commonMsg";
    }
    
    
    /**
	 * 멀티엑셀 다운로드
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/contentExcelDownSh.do")
    public String contentExcelDownSh(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    	    	
    	// 세션 사용자정보
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
    			
    	String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));
		String sTableName = StringUtil.isNullToString(request.getParameter("tableName"));    	
    	String sFileName  = StringUtil.isNullToString(request.getParameter("fileName")).replace(" ", "");
    	String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));       // xml_id
    	String tptype     = StringUtil.isNullToString(request.getParameter("tptype"));      // 템플릿타입
    	String hdYn       = StringUtil.isNullToString(request.getParameter("hdYn"));      	// 가변컬럼사용여부
    	String sumYn       = StringUtil.isNullToString(request.getParameter("sumYn"));      // 합계표현여부
    	
    	HashMap parameterObject =  new HashMap();
    	
    	List selectColumnsList = new ArrayList<EgovMap>();
    	List selectHdList = new ArrayList<EgovMap>();
    	List selectHdSumList = new ArrayList<EgovMap>();
    	List selectDataSumList = new ArrayList<EgovMap>();
    	
    	parameterObject.put("sumYn", sumYn);  // 합계표현여부
    	
    	parameterObject.put("scrin_code", scrin_code);  // 메뉴ID
    	parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
    	 
    	// xmlid 검색이 아니고 화면 템플릿이 통계(E)이 아닌 경우
    	if ("".equals(xmlId) && !"E".equals(tptype)) {
    	
	    	// 검색 조건 정보 조회
    		String owner = fileUploadProperties.getProperty("Globals.UserName");		
			parameterObject.put("owner", owner);
	    	List<EgovMap> selectSearchList = contentService.selectSearchObjectList(parameterObject);
	    	log.info("########## 검색 조건 정보 조회 (selectSearchList) =====> " + String.valueOf(selectSearchList).replaceAll("[\r\n]",""));
	    		
	    	// 검색 항목만 저장 화면 ID 값    	
	    	String cust_id="";
	    	parameterObject = ContentUtil.RequestToHashMap(request, selectSearchList, "E",sTableName);
	    	log.info("########## 검색 항목만 저장 화면 ID 값 (parameterObject) =====> " + String.valueOf(parameterObject).replaceAll("[\r\n]",""));
	     
	    	
	    	// 검색 컬럼 쿼리 생성
	        List searchColumnsList = ContentUtil.searchColumnsList(parameterObject, selectSearchList, request);
	        parameterObject.put("searchColumnsList", searchColumnsList);
	        log.info("########## 검색 컬럼 쿼리 생성(searchColumnsList) =====> " + String.valueOf(searchColumnsList).replaceAll("[\r\n]",""));
    	
	    	// 조회 컬럼 항목
	        parameterObject.put("scrin_code", scrin_code);
	        parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
	        parameterObject.put("show_yn", "3");
	        selectColumnsList = contentService.selectColumnsList(parameterObject);
	        log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));

	        // 조회 컬럼 항목 쿼리 생성    	
	        List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
	        parameterObject.put("selectColumnsList", selectColumnsRetList);
	        log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
	        
	        // ORDER BY 컬럼 항목 쿼리 생성
	        List selectColumnsListInit = new ArrayList();
	        for(Object egov: selectColumnsList){
	        	((EgovMap)egov).get("colId").toString();
	        	if(!(((EgovMap)egov).get("colId").toString().indexOf("from_") > -1 || ((EgovMap)egov).get("colId").toString().indexOf("to_") > -1)){
	        		selectColumnsListInit.add(((EgovMap)egov).get("colId").toString());
	        	}
	        }
	        parameterObject.put("selectColumnsListInit", selectColumnsListInit);
	        parameterObject.put("sumYn", sumYn);  // 합계표현여부
	        
	     // 가변컬럼사용    
    	} else if(!"".equals(xmlId) && "V".equals(tptype) && "Y".equals(hdYn)){
    		log.info("#### 가변컬럼 ####");
    		
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    		    
    		    parameterObject.put(key,  val);
    		}
    		
    		// 조회 컬럼 항목
            parameterObject.put("scrin_code", scrin_code);           // 화면id
            parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
            parameterObject.put("show_yn", "3");                     // 출력옵션
        	selectColumnsList = contentService.selectColumnsList(parameterObject);
            log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
                		
    		// 컬럼이름 추출
		    selectHdList = contentService.selectHdList(parameterObject, xmlId);
		    
		    ArrayList<Map> str2 = new ArrayList<Map>();
			
			// 헤더에 가변 컬럼ID, 컬럼이름 추가
		    if(selectHdList.size() > 0){
				for(int i=0; i<selectHdList.size(); i++ ){
					EgovMap list = new EgovMap();
					list.put("colId", "c"+i);
					list.put("objNm", ((EgovMap)selectHdList.get(i)).get("hdnm").toString());
					selectColumnsList.add(list);
				}
			}
		    
			// data 조회시 가변 컬럼 쿼리 생성
			if(selectHdList.size() > 0){
				for(int i=0; i<selectHdList.size(); i++ ){
					HashMap<String, String> list = new HashMap<String, String>();
					list.put("col", "c"+i);
					list.put("nm", ((EgovMap)selectHdList.get(i)).get("hdnm").toString());
					str2.add(list);
				}
				parameterObject.put("collist", str2);
			}
			
			// 합계 정보추출
			if("Y".equals(sumYn)){
				selectHdSumList = contentService.selectHdSumList(parameterObject, xmlId);
				selectDataSumList = contentService.selectDataSumList(parameterObject, xmlId);
				
				parameterObject.put("selectHdSumList", selectHdSumList);
				parameterObject.put("selectDataSumList", selectDataSumList);
			}
    		
	    // xml id 검색인 경우
    	}  else {   	
    		// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    
    		    parameterObject.put(key,  val);
    		}  
    		
        	// 조회 컬럼 항목
            parameterObject.put("scrin_code", scrin_code);           // 화면id
            parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
            parameterObject.put("show_yn", "3");                     // 출력옵션
        	selectColumnsList = contentService.selectColumnsList(parameterObject);
            log.info("########## 조회 컬럼 항목(selectColumnsList) =====> " + String.valueOf(selectColumnsList).replaceAll("[\r\n]",""));
            
            // 조회 컬럼 항목 쿼리 생성
            List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
            parameterObject.put("selectColumnsList", selectColumnsRetList);
            log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
            
            // 합계 정보추출
         	if("Y".equals(sumYn)){
         		selectHdSumList = contentService.selectHdSumList(parameterObject, xmlId);
         		selectDataSumList = contentService.selectDataSumList(parameterObject, xmlId);
         		
         		parameterObject.put("selectHdSumList", selectHdSumList);
         		parameterObject.put("selectDataSumList", selectDataSumList);
         	}
    	}
    	
    	// 엑셀 sheet별 추가
    	List<EgovMap> sheet1 = contentService.getXml(parameterObject, "tong.selectKukjeDetailSheet1");
    	List<EgovMap> sheet2 = contentService.getXml(parameterObject, "tong.selectKukjeDetailSheet2");
    	List<EgovMap> sheet3 = contentService.getXml(parameterObject, "tong.selectKukjeDetailSheet3");
    	
    	
    	parameterObject.put("sheet1", sheet1);
    	parameterObject.put("sheet2", sheet2);
    	parameterObject.put("sheet3", sheet3);
    	

    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));  
    	if (!sidx.equals("")) {
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (!sord.equals("")) {
			parameterObject.put("OrderByType",sord);
		}
		
        // 엑셀 DATA
        parameterObject.put("tableName", sTableName);
    	parameterObject.put("user_id",     loginVO.getUserId());    // 로그인ID
    	parameterObject.put("dept_grp_id", loginVO.getDeptGrpId()); // 부서코드 
    	parameterObject.put("tptype", tptype);						// 템플릿 타입

        log.info("########## 엑셀데이터 조회(data_list) =====> ");

        model.addAttribute("sub_path",    "excel_download");
        
        
        String retFileName = ExcelDownLoadSheetContent.ExcelDownLoadMakeHandler(sFileName, "excel_download", selectColumnsList, request, response , parameterObject, xmlId, contentService);
        
        //String retFileName = "false";
        String resultMsg = "EXCEL_SUCCESS";
        if ("false".equals(retFileName)) {
        	resultMsg = "ERROR";
        }
        
        String OID = retFileName;
        model.addAttribute("OID", OID);
        model.addAttribute("resultMsg",  resultMsg);    // 엑셀다운로드 결과
    	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
    	
    	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수(callBackFunction) : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	
       	parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID
       
		saveContentHis( parameterObject,  "",  "",  request, "E" , null );
		
    	return  "/unibill/com/cmm/commonMsg";
    }

    

    /**
	 * 공통 컨텐츠 정보를 등록 및 수정 처리한다.
	 * @param model
	 * @exception Exception
	 */	 
    public void saveContentHis(HashMap parameterObject, String tableName, String ubseq, HttpServletRequest request, String mode ,List<EgovMap> jsonData ) throws Exception {
    	boolean bsavehis = true;
    	//if(request.getServletContext().getAttribute("ACEESSLOG_USE").equals("Y")){
    		/*
    		String fwubseq = KeyUtil.getUbseq("fwhisaccess", "ubseq");
        	parameterObject.put("fwubseq",    fwubseq);
        	parameterObject.put("tableName", tableName );   // 테이블명
        	parameterObject.put("ubseq",    ubseq);
        	log.info("saveContentHis fwubseq="+fwubseq+" ubseq="+ubseq+" tableName="+tableName);
        	
        	mainService.insertHisAccess(parameterObject, request, mode);
        	
        	String acFlag = StringUtil.isNullToString(request.getParameter("actionFlag"));
        	if(acFlag.equals("INSERT") || acFlag.equals("UPDATE")){
        		contentService.updateSync(parameterObject);
        	}
        	
        	if(!bsavehis) return ;    	
        	if(!tableName.equals("mb_cfgod") && !tableName.equals("mb_cfgcont")   ) return ;
        	String hisubseq; 
        	if(mode.equals("U") ) {
    	    	hisubseq = KeyUtil.getUbseq(parameterObject.get("tableName").toString(), "ubseq");
    	    	parameterObject.put("hisubseq",    hisubseq);
    	    	contentService.insertContentHis(parameterObject);  
    	    	contentService.insertHisResult(parameterObject);   
        	}
        	else if(mode.equals("R") ) { 
        	}
        	*/
    		//cm_hisjob, cm_hisprivacy 적용
    	//}
    	
	}
	
	
    /**
	 * 공통 컨텐츠 정보를 등록 및 수정 처리한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/saveContent.do")
    public String saveContent(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
        
        String resultMsg  = "";
       	String errorMsg   = "";       	
       	String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
       	String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));   // 테이블명        	            	
       	String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag"));  // 처리 상태
       	String ubseq      = StringUtil.isNullToString(request.getParameter("ubseq"));       // 자료순번
       	String ubSeqArr   = StringUtil.isNullToString(request.getParameter("ubSeqArr"));    // 삭제대상 데이터
       	String mailsn     = StringUtil.isNullToString(request.getParameter("org_mail_sn"));    // mailsn'
       	String dFilesn	  = StringUtil.isNullToString(request.getParameter("dFilesn"));
       	//파일 타입
       	String fgrp = "";
       	String imgfgrp = "";
       	String filesn = "";
       	String popupimg = "";
       	String cfile_sn = "";
       	
       	if(request instanceof MultipartHttpServletRequest){
       		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;	
    		final Map<String, MultipartFile> files = multiRequest.getFileMap();
    		
    		if (!actionFlag.equals("DELETE")) {
    			for(String key : files.keySet()){
    				MultipartFile fval = files.get(key);
    				
    				if(key.contains("fileUpload")){
    					String[] fileSn = key.split("_");
    					HashMap resultMap = new HashMap<String, String>();		
    					resultMap.put("filesn", fileSn[0]);
    					resultMap.put("filename", fileSn[0] + "_" + fval.getOriginalFilename());
    					resultMap.put("filesize", fval.getSize());
    					
    					//파일타입 확인
    					resultMap.put("scrin_code", scrin_code);
    					HashMap fgrpRs = contentService.getcmfile(resultMap);
    					fgrp = fgrpRs.get("file_grp").toString();
    					if(fgrp.equals("N")){filesn = fileSn[0];}
    					
    					//현재월 구하기
    					Calendar calendar = Calendar.getInstance();
    		            Date date = calendar.getTime();
    		            String today = new SimpleDateFormat("yyyyMM").format(date);
    	
    		            //파일저장
    					Assert.notNull(files, "files is null");
    	
    					String uploadPath = fileUploadProperties.getProperty("system.fileuploadpath")+File.separator+today;
    					
    					
    					File saveFolder = new File(uploadPath);
    					//폴더가 존재하지 않을시 생성
    					boolean isDir = false;
    					if (!saveFolder.exists() || saveFolder.isFile()) {
    						saveFolder.mkdirs();
    					}
    					
    					if (!isDir) {
    						String filePath = uploadPath + File.separator + fileSn[0]+ "_" + fval.getOriginalFilename();
    						if (!"".equals(fval.getOriginalFilename())) {
    							fval.transferTo(new File(filePath));
    							
    						}			
    					}  // if end
    					
    					
    					resultMap.put("uploadPath", uploadPath);
    					
    					
    					resultMap.put("fgrp", fgrpRs.get("file_grp").toString());
    					
    					//DB inserrt
    					resultMap.put("regid", loginVO.getUserId());
    					resultMap.put("rolelevel", loginVO.getRoleLevel());
    					//cmfile
    					contentService.insertcmfile(resultMap);
    					//파일 타입에 따라 isnert
    					resultMap.put("odNo", request.getParameter("od_no"));
    					resultMap.put("contSeq", request.getParameter("cont_seq"));
    					contentService.filedetail(resultMap);
    					contentService.insertodcontfile(resultMap);
    					
    					
    				}
    				else if(key.contains("imgUpload")){
    					String[] fileSn = key.split("_");
    					HashMap resultMap = new HashMap<String, String>();		
    					resultMap.put("filesn", fileSn[0]);
    					resultMap.put("filename", fileSn[0] + "_" + fval.getOriginalFilename());
    					resultMap.put("filesize", fval.getSize());
    					
    					popupimg = fileSn[0];
    					imgfgrp = "I";
    					
    					
    					//현재월 구하기
    					Calendar calendar = Calendar.getInstance();
    		            Date date = calendar.getTime();
    		            String today = new SimpleDateFormat("yyyyMM").format(date);
    					
    					//파일저장
    					Assert.notNull(files, "files is null");
    	
    					String uploadPath = fileUploadProperties.getProperty("system.imgfileuploadpath")+File.separator+today;
    					
    					
    					File saveFolder = new File(uploadPath);
    					//폴더가 존재하지 않을시 생성
    					boolean isDir = false;
    					if (!saveFolder.exists() || saveFolder.isFile()) {
    						saveFolder.mkdirs();
    					}
    					
    					if (!isDir) {
    						String filePath = uploadPath + File.separator + fileSn[0]+ "_" + fval.getOriginalFilename();
    						if (!"".equals(fval.getOriginalFilename())) {
    							fval.transferTo(new File(filePath));
    							
    						}			
    					}  // if end
    					
    					
    					resultMap.put("uploadPath", uploadPath);
    					resultMap.put("fgrp", imgfgrp);
    					
    					//DB inserrt
    					resultMap.put("regid", loginVO.getUserId());
    					resultMap.put("rolelevel", loginVO.getRoleLevel());
    					//cmfile
    					contentService.insertcmfile(resultMap);
    					
    					
    				}
    				
    			}
    		
    		}
       	}
       	if(request instanceof MultipartHttpServletRequest){	
	       	final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;	
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			
			HashMap fileMap = new HashMap<String, String>();
			if (!actionFlag.equals("DELETE")) {
		       	for(String key : files.keySet()){
		       		MultipartFile fval = files.get(key);
					
					if(key.contains("t")){
						String[] fileSn = key.split("_");
						fileMap.put("filesn", fileSn[0]);
						//mail
						fileMap.put("nm", fileSn[0] + "_" + fval.getOriginalFilename());
						fileMap.put("filesz", fval.getSize());
						
						byte[] bytes;
						bytes = fval.getBytes();
						Blob blob = new SerialBlob(bytes);
						fileMap.put("file", blob);
						
						//파일타입 확인
						fileMap.put("scrin_code", scrin_code);
						HashMap fgrpRs = contentService.getBlobfile(fileMap);
						fgrp = fgrpRs.get("file_grp").toString();
						if(fgrp.equals("N")){filesn = fileSn[0];}
						
						fileMap.put("fgrp", fgrpRs.get("file_grp").toString());
						
						//DB inserrt
						fileMap.put("regid", loginVO.getUserId());
						fileMap.put("updid", loginVO.getUserId());
						
						
						if(fval.getSize() != 0 && blob != null) {
							//blobFile insert
							contentService.insertBlobFile(fileMap);
							cfile_sn += fileSn[0] + ",";
						}
					}		
				}	
			}
       	}

		
		String[] s2 = ubSeqArr.split(",");
		String r ="";
		for(int i=0; i<s2.length; i++){
			if(i < s2.length-1 ){
				r += "TO_NUMBER(" + s2[i] + "),";
			}else{
				r += "TO_NUMBER(" + s2[i] + ")";
			}
		}
       	
		ubSeqArr = r;
		
       	String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));       // xmlId
       	if(scrin_code.equals("S0031")) xmlId="";
       	String extCallBackFunction = StringUtil.isNullToString(request.getParameter("extCallBackFunction"));
       	String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	       	
       	log.info("########## 저장 (등록/수정/삭제) : [scrin_code : " + scrin_code.replaceAll("[\r\n]","") + "][actionFlag : " + actionFlag.replaceAll("[\r\n]","") + "][ubseq : " + ubseq.replaceAll("[\r\n]","") + "][ubSeqArr : " + ubSeqArr.replaceAll("[\r\n]","") + "][xmlId : " + xmlId.replaceAll("[\r\n]","") + "]");
       	
		HashMap parameterObject =  new HashMap();

		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key = en.nextElement().toString();
			String val = request.getParameter(key);
			parameterObject.put(key,  val);
		}

		// 저장 컬럼 조회
		parameterObject.put("scrin_code",   scrin_code); // 화면ID
		parameterObject.put("showyn",       "");         // 검색조건 출력여부
		parameterObject.put("find_show_yn", "");         // 검색조건 출력여부
    	parameterObject.put("dtl_show_yn",  "Y");        // 상세 출력여부
    	parameterObject.put("role_code",    loginVO.getRoleId());  // 권한id

    	String owner = fileUploadProperties.getProperty("Globals.UserName");		
		parameterObject.put("owner", owner);
    	List<EgovMap> selectColumnsList = contentService.selectSearchObjectList(parameterObject);
    	//단일 파일 업로드 존재 체크
    	Boolean issfileupload = false;
    	Boolean isimgfileupload = false;
    	for(int i = 0; i<selectColumnsList.size(); i++){
    		if(selectColumnsList.get(i).get("objId").toString().equals("sfileupload")){
    			issfileupload = true;
    		}
    		if(selectColumnsList.get(i).get("objId").toString().equals("imgfileupload")){
    			isimgfileupload = true;
    		}
    	}
    	List<EgovMap> keyColList = new ArrayList<EgovMap>();
    	
    	try {

    		String sResultMsg = "";
           	String sErrorMsg  = "";           	           	

    		if (actionFlag.equals("INSERT")) {
    			
    			parameterObject = ContentUtil.RequestToHashMap(request, selectColumnsList, "C",tableName);

    			// insert 컬럼 생성
    			List insertColumnsList = ContentUtil.insertColumnsList(request, selectColumnsList, "Columns");
    			log.info("################# 공통 컨텐츠 insert 컬럼 생성 (insertColumnsList) : " + String.valueOf(insertColumnsList).replaceAll("[\r\n]",""));

	            // insert 데이터 생성 
	            List insertDataList = ContentUtil.insertColumnsList(request, selectColumnsList, "Data");
	            log.info("################# 공통 컨텐츠 insert 데이터 생성 (insertDataList) : " + String.valueOf(insertDataList).replaceAll("[\r\n]",""));
	            
	            //파일순번 삽입
	            if(fgrp.equals("N")){
	            	insertColumnsList.add("file_sn");
	            	insertDataList.add(filesn);
	            }
	            
	            //이미지파일 정보 삽입
	            if(imgfgrp.equals("I")){
	            	insertColumnsList.add("popup_img");
	            	insertDataList.add(popupimg);
	            	
	            }
	            
	            //공통 파일업로드 파일순번 삽입
	            if(fgrp.equals("T") && cfile_sn != null && !cfile_sn.equals("")){
	            	insertColumnsList.add("file_sn");
	            	insertDataList.add(cfile_sn.substring(0, cfile_sn.length()-1));
	            }
	            
	            
	            log.info("################# 공통 컨텐츠 insert 컬럼 생성2 (insertColumnsList) : " + String.valueOf(insertColumnsList).replaceAll("[\r\n]",""));
	            log.info("################# 공통 컨텐츠 insert 데이터 생성2 (insertDataList) : " + String.valueOf(insertDataList).replaceAll("[\r\n]",""));
	            
	            // insertDataListd의 null 값을 ""으로 변경
	            Iterator<String> it = insertDataList.iterator();
	            int itcnt = 0;
	            while (it.hasNext()) {
					
	            	if(it.next() == null) {
						insertDataList.set(itcnt, "");
					}
	            	itcnt++;
				}
	            
	            List insertColumnsListF=new ArrayList();
	            List insertDataListF=new ArrayList();
	            
	            // 데이터가 없는 컬럼이 들어 올때 insert에서 제거(insert시 default값)
	            List insertRemoveColumnsList = new ArrayList();
	            List insertRemoveDataList = new ArrayList();
	        	for(int k=0; k<insertColumnsList.size(); k++){
	        		//주문번호가 없을때 db함수로 자동생성
	        		if(insertColumnsList.get(k).equals("od_no") && insertDataList.get(k).equals("") ) {
            			log.info("################# f_getseqstr : " + insertColumnsList.get(k).toString().replaceAll("[\r\n]",""));
            			
            			//xml 에서 함수 처리
	            		//insertColumnsListF.add(insertColumnsList.get(k));
	            		//insertDataListF.add("f_getseqstr('od_no')  ");  
	            		 // pjh 2020-10-22 xml 처리 방법 검토
                   		String gen_odno = KeyUtil.getSeqStr("od_no"); 
                   		insertRemoveColumnsList.add(insertColumnsList.get(k));
	            		insertRemoveDataList.add(gen_odno);  
                   		log.debug("############## gen_odno : " + gen_odno.replaceAll("[\r\n]","")); 
                   		parameterObject.put("od_no", gen_odno);   //
                   		  
	                   	 
            		}
	        		else if(!StringUtils.isEmpty(insertDataList.get(k).toString())){ 
	        			insertRemoveColumnsList.add(insertColumnsList.get(k));
	            		insertRemoveDataList.add(insertDataList.get(k)); 
	            		
	            	}
	            }
	        	log.info("################# 공통 컨텐츠 insert 데이터 생성3 (insertRemoveDataList) : " + String.valueOf(insertRemoveDataList).replaceAll("[\r\n]",""));
	            parameterObject.put("tableName", tableName);   // 테이블명
	        	parameterObject.put("reg_id", reg_id);         // 등록자id
	        	parameterObject.put("upd_id", upd_id);         // 수정자id
//	            parameterObject.put("insertColumnsList", insertColumnsList);
//	            parameterObject.put("insertDataList",    insertDataList);	 
	            parameterObject.put("insertColumnsList", insertRemoveColumnsList);
	            parameterObject.put("insertDataList",    insertRemoveDataList);
	            if(insertColumnsListF.size() >0) {
		            parameterObject.put("insertColumnsListF", insertColumnsListF);
		            parameterObject.put("insertDataListF",    insertDataListF);
	            }
	            parameterObject.put("masterTableName",   tableName);   // 테이블명 (key 컬럼 조회용)

	            // ubseq 생성
//	            ubseq = contentService.getUbSeq(parameterObject);
	            ubseq = KeyUtil.getUbseq(parameterObject.get("tableName").toString(), "ubseq");
	            parameterObject.put("ubseq",    ubseq);
	            parameterObject.put("xmlId",    xmlId);  // xmlId
	            //저장버튼 자료 등록
	            EgovMap info = contentService.insertContent(parameterObject);
	            //사용자 관리 화면일시 이력
	            if(scrin_code.equals("S0019")){
	            	parameterObject.put("workType", "I");
		            contentService.insertHisuser(parameterObject);
	            }
	            

	            sResultMsg = (String) info.get("resultMsg");
	            sErrorMsg  = (String) info.get("errorMsg");	            
	            
	            if ("true".equals(sResultMsg)) {
	            	
	            	resultMsg = "SAVE_SUCCESS";
	            	
	            	// 테이블의 key 컬럼 정보 조회
	            	keyColList = contentService.selectKeyColList(parameterObject);
	            	String ubseqsql = "";
	            	for(int i=0; i<insertColumnsList.size(); i++){
	            		ubseqsql = ubseqsql + " and "+ insertColumnsList.get(i) + " = '" + insertDataList.get(i) + "'";
	            	}
	            	parameterObject.put("ubseqsql", ubseqsql);
	            	parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID
	        			            	
	            	saveContentHis( parameterObject,  tableName,  ubseq, request, "C" ,null);
	            	
	            	//박준형 2021-10-17, save 버튼에 후처리 함수등록 extpo_fun: insert,update 모두 정의
	            	/*
	            	String extpo_fun="";
	            	if(extpo_fun.substring(0,3).equals("prc_")) {
	            		//프로시져수행
	            		//exeAfSaveProcedure( extpo_fun,  tableName,  ubseq );
	            	}
	            	else if(extpo_fun.substring(0,1).equals("f_")) {
	            		//함수수행
	            	}
	            	*/
	            	
	            } else if ("false".equals(sResultMsg)) {
	            	
	            	if ("".equals(sErrorMsg)) {
	            		resultMsg = "ERROR";
	            	} else {
	            		resultMsg = "SQL_ERROR";
	            		SqlErrMsg getErrMsg = new SqlErrMsg();
	            		errorMsg = getErrMsg.getErrmsg(sErrorMsg);
	            		
	            		
	            		
	            	}
	            }
	            	          
    		} else if (actionFlag.equals("UPDATE")) {

    			parameterObject = ContentUtil.RequestToHashMap(request, selectColumnsList, "U",tableName);
    			parameterObject.put("unibillFuncEnc", unibillFuncEnc);
    			
    	    	List updateColumnsList = ContentUtil.updateColumnsList(request, parameterObject, selectColumnsList);
    	    	log.info("########## 공통 컨텐츠 상세정보 수정 (updateColumnsList) =====> " + String.valueOf(updateColumnsList).replaceAll("[\r\n]",""));
    	    	
    	    	parameterObject.put("ubseq",     ubseq);      // ubseq
    	    	parameterObject.put("tableName", tableName);  // 테이블명
    	    	 
    	    	//기존의 파일 정보
    	    	EgovMap ofileinfo = null;
    	    	//싱글 파일순번 삽입
	            if(issfileupload && fgrp.equals("N")){
	            	ofileinfo = contentService.getubseqfileInfo(parameterObject);
	            	HashMap param = new HashMap();
	            	if(ofileinfo.size() != 0){
	    	    		//기존의 파일 삭제
	    	    		String fullpath = ofileinfo.get("filePath").toString() + File.separator + ofileinfo.get("nm").toString();
	    	    		//파일 삭제
	        			File file = new File(fullpath);
		            		    	    		
	    	    		//기존 DB삭제
	    	    		param.put("filesn", ofileinfo.get("fileSn").toString());
		    	    	//contentService.deletecmfile(param);
		    	    	
		    	    	
	    	    	}
	    	    	parameterObject.put("file_sn", filesn);
	            	updateColumnsList.add("file_sn = #{file_sn}");
	            	
	            }else if(issfileupload && (fgrp == null || fgrp.equals(""))){
	            	try {
	            		ofileinfo = contentService.getubseqfileInfo(parameterObject);
	            		
	            		HashMap param = new HashMap();
		            	if(ofileinfo != null){
		            		
		            		//기존의 파일 삭제
		    	    		String fullpath = ofileinfo.get("filePath").toString() + File.separator + ofileinfo.get("nm").toString();
		    	    		//파일 삭제
		        			File file = new File(fullpath);
		        			    	    		
		    	    		//기존 DB삭제
		    	    		param.put("filesn", ofileinfo.get("fileSn").toString());
			    	    	//contentService.deletecmfile(param);
			    	    	
			    	    	
		    	    	}
		            	
		            	
					} catch (Exception e) {
					}
	            	
	            	
	            	
	            }
	          //이미지 파일순번 삽입
	            if(isimgfileupload && imgfgrp.equals("I")){
	            	ofileinfo = contentService.getubseqfileInfo(parameterObject);
	            	HashMap param = new HashMap();
	            	if(ofileinfo != null){
	            		//기존의 파일 삭제
	    	    		String fullpath = ofileinfo.get("filePath").toString() + File.separator + ofileinfo.get("nm").toString();
	    	    		//파일 삭제
	        			File file = new File(fullpath);
		            		    	    		
	    	    		//기존 DB삭제
	    	    		param.put("filesn", ofileinfo.get("fileSn").toString());
		    	    	contentService.deletecmfile(param);
		    	    	
		    	    	
	    	    	}
	            	
	            	parameterObject.put("popup_img", popupimg);
	            	updateColumnsList.add("popup_img = #{popup_img}");
//	            	parameterObject.put("sizew", StringUtil.isNullToString(request.getParameter("sizew")));
//	            	updateColumnsList.add("sizew = #{sizew}");
//	            	parameterObject.put("sizeh", StringUtil.isNullToString(request.getParameter("sizeh")));
//	            	updateColumnsList.add("sizeh = #{sizeh}");
	            }else if(isimgfileupload && (imgfgrp == null || imgfgrp.equals(""))){
	            	try {
	            		ofileinfo = contentService.getubseqfileInfo(parameterObject);
	            		
	            		HashMap param = new HashMap();
		            	if(ofileinfo.size() != 0){
		    	    		//기존의 파일 삭제
		    	    		String fullpath = ofileinfo.get("filePath").toString() + File.separator + ofileinfo.get("nm").toString();
		    	    		//파일 삭제
		        			File file = new File(fullpath);
		        					
		    	    		//기존 DB삭제
		    	    		param.put("filesn", ofileinfo.get("fileSn").toString());
			    	    	contentService.deletecmfile(param);
			    	    	
			    	    	
		    	    	}
		        	} catch (Exception e) {
					}
	            	
	            	
	            	
	            }
	            
	            String [] dFilesns = dFilesn.split(",");
	            Arrays.sort(dFilesns);
	            HashMap fileObj = new HashMap();
	            fileObj.put("tableName", tableName);
	            fileObj.put("data", mailsn);
	            
	            //파일그룹이 T이고 파일을 추가하는 경우
	            if(fgrp.equals("T") && cfile_sn != null && !cfile_sn.equals("")) {
	            	String exFile_sn = contentService.getFilesn(fileObj);
	            	//기존 파일이 있는 경우
	            	if(exFile_sn != null && !exFile_sn.equals("")) {
		            	String [] exFilesn = exFile_sn.split(",");
		            	//파일 삭제 시
		            	if(!dFilesn.equals("") && dFilesn != null) {
		            		HashMap paramObj = new HashMap();
		            		paramObj.put("filegrp", "D");
		            		for(int i = 0; i < dFilesns.length; i++) {
				            	paramObj.put("dfilesn", dFilesns[i]);
				            	contentService.updatefilegrp(paramObj);
		            		}	 
		            		contentService.deletemailData(paramObj);
		            		for(int i = 0; i < exFilesn.length; i++) {
		            			for(int j = 0; j < dFilesns.length; j++) {
						            if(exFilesn[i].contains(dFilesns[j])) {
							            if(exFilesn.length == 1) {
							            	exFile_sn =  exFile_sn.replace(exFilesn[i] , "");
							            }
							            else if(i == exFilesn.length - 1 && exFilesn.length == dFilesns.length) {
							            	exFile_sn =  exFile_sn.replace(exFilesn[i] , "");
							            }
							            else if(i == exFilesn.length - 1) {
							            	exFile_sn =  exFile_sn.replace("," + exFilesn[i] , "");
							            }
							            else {
							            	exFile_sn = exFile_sn.replace(exFilesn[i] + ",", "");
							            }
							        }
		            			}
						    }
		            	}
		            	if(exFile_sn.equals("")) {
		            		parameterObject.put("file_sn", cfile_sn.substring(0, cfile_sn.length()-1));
		            	}else {
		            		parameterObject.put("file_sn", exFile_sn + "," + cfile_sn.substring(0, cfile_sn.length()-1));
		            	}
		            	updateColumnsList.add("file_sn = #{file_sn}");
	            	}
	            	//기존 파일이 없을 때 
	            	else {
		            	HashMap paramObj = new HashMap();
		            	paramObj.put("tableName", tableName);
		            	paramObj.put("data", mailsn);
		            	parameterObject.put("file_sn", cfile_sn.substring(0, cfile_sn.length()-1));
		            	updateColumnsList.add("file_sn = #{file_sn}");
	            	}
	            //삭제만 하는 경우
	            } else if(fgrp.equals("T") && (cfile_sn == null || cfile_sn.equals(""))){
	            	String exFile_sn = contentService.getFilesn(fileObj);
	            	String [] exFilesn = exFile_sn.split(",");
	            	if(!dFilesn.equals("") && dFilesn != null) {
	            		HashMap paramObj = new HashMap();
	            		paramObj.put("filegrp", "D");
	            		for(int i = 0; i < dFilesns.length; i++) {
			            	paramObj.put("dfilesn", dFilesns[i]);
			            	contentService.updatefilegrp(paramObj);
	            		}
	            		contentService.deletemailData(paramObj);
	            		for(int i = 0; i < exFilesn.length; i++) {
	            			for(int j = 0; j < dFilesns.length; j++) {
					            if(exFilesn[i].contains(dFilesns[j])) {
						            if(exFilesn.length == 1) {
						            	exFile_sn =  exFile_sn.replace(exFilesn[i] , "");
						            }
						            else if(i == exFilesn.length - 1 && exFilesn.length == dFilesns.length) {
						            	exFile_sn =  exFile_sn.replace(exFilesn[i] , "");
						            }
						            else if(i == exFilesn.length - 1) {
						            	exFile_sn =  exFile_sn.replace("," + exFilesn[i] , "");
						            }
						            else {
						            	exFile_sn = exFile_sn.replace(exFilesn[i] + ",", "");
						            }
						        }
	            			}
					    }
		            }
	            	parameterObject.put("file_sn", exFile_sn);
	            	updateColumnsList.add("file_sn = #{file_sn}");
	            }
	            
    	    	parameterObject.put("upd_id",    upd_id);     // 수정자id
    			
    			parameterObject.put("updateColumnsList", updateColumnsList);
    			parameterObject.put("masterTableName", tableName);   // 테이블명 (key 컬럼 조회용)
    			parameterObject.put("xmlId",     xmlId);      // xmlId
    			
    			EgovMap info = new EgovMap();
    			//업데이트시 자기 권한보다 상위의 롤로 업데이트 방지
    			if(parameterObject.get("role_id") != null){
    				int paramRoleLevel = contentService.getRoleLevel("content.getRoleLevel", parameterObject);
    				
    				if(Integer.valueOf(loginVO.getRoleLevel()) <= paramRoleLevel){
    					//권한이 있는 경우
    					info = contentService.updateContent(parameterObject);
    					//사용자 관리 화면일시 이력
    		            if(scrin_code.equals("S0019")){
    		            	parameterObject.put("workType", "U");
    		            	parameterObject.put("reg_id", reg_id);         // 등록자id
    			            contentService.insertHisuser(parameterObject);
    		            }
    				}else{
    					info.put("resultMsg", "false");
    					info.put("errorMsg",  "상위권한으로 수정 할 수 없습니다.");
    				}
    				
    			}else{
    				// role_id 컬럼이 없는 경우 권한 체크 없이 update
    				info = contentService.updateContent(parameterObject);
    				//사용자 관리 화면일시 이력
    				if(scrin_code.equals("S0019")){
		            	parameterObject.put("workType", "U");
		            	parameterObject.put("upd_id",    upd_id);     // 수정자id
		            	parameterObject.put("reg_id", reg_id);         // 등록자id
			            contentService.insertHisuser(parameterObject);
		            }
    			}
    			
    			sResultMsg = (String) info.get("resultMsg");
	            sErrorMsg  = (String) info.get("errorMsg");	            

	            if ("true".equals(sResultMsg)) {
	            	
	            	resultMsg = "UPDATE_SUCCESS";
	            	
	            	parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID 
	        		
	            	// 테이블의 key 컬럼 정보 조회
	            	keyColList = contentService.selectKeyColList(parameterObject);
	            	saveContentHis( parameterObject,  tableName,  ubseq , request, "U",null ); 
	            	
	            } else if ("false".equals(sResultMsg)) {
	            	if ("".equals(sErrorMsg)) {
	            		resultMsg = "ERROR";
	            	} else {
	            		resultMsg = "SQL_ERROR";
	            		SqlErrMsg getErrMsg = new SqlErrMsg();
	            		errorMsg = getErrMsg.getErrmsg(sErrorMsg);
	            	}
	            }

    		} else if (actionFlag.equals("DELETE")) {
    			parameterObject = ContentUtil.RequestToHashMap(request, selectColumnsList, "D",tableName);
    			
    			parameterObject.put("tableName", tableName);  // 테이블명
    			parameterObject.put("ubSeqArr",  ubSeqArr);   // ubseq 		
    			parameterObject.put("xmlId",     xmlId);      // xmlId

    			if(scrin_code.equals("S0214")){
    				try {	
    					String dir = "";
    					String fileName =  "";
    					/*for(int i = 0;i<fileInfo.size();i++){
    						dir = (String) fileInfo.get(i).get("fileDir");
    						fileName = (String) fileInfo.get(i).get("fileNm");
        					File dFile = new File(dir, fileName);
        					dFile.delete();	// 물리적 삭제
    					}*/
    				} catch(Exception e) {}
    			}
    			
    			//사용자 관리 화면일시 이력
    			if(scrin_code.equals("S0019")){
    				for(int i=0; i<s2.length; i++){
    					parameterObject.put("dubseq", s2[i]);
    					EgovMap cfguser = contentService.selectcfguser(parameterObject);
    					cfguser.put("workType", "D");
    					parameterObject.put("reg_id", reg_id);         // 등록자id
    		        	parameterObject.put("upd_id", upd_id);         // 수정자id
    					parameterObject.put("workType", "D");
    					parameterObject.put("user_id", cfguser.get("userId"));
        				parameterObject.put("user_nm", cfguser.get("userNm"));
        				parameterObject.put("use_yn", cfguser.get("useYn"));
        				parameterObject.put("role_id", cfguser.get("roleId"));
        				parameterObject.put("ipchk_yn", cfguser.get("ipchkYn"));
        				parameterObject.put("tel_no", cfguser.get("telNo"));
        				parameterObject.put("mobile", cfguser.get("mobile"));
        				parameterObject.put("email", cfguser.get("email"));
        				parameterObject.put("pass", cfguser.get("pass"));
        				parameterObject.put("failcnt", cfguser.get("failcnt"));
    					
    					contentService.insertHisuser(parameterObject);
    				}
    			}
    			contentService.deleteContent(parameterObject);
    			
    			parameterObject.put("REG_ID", loginVO.getUserId());  // 사용자ID
    			
    		 
        		saveContentHis( parameterObject,  tableName,  "",  request, "D" , null );
    			
    			resultMsg = "DELETE_SUCCESS";
    		}
    		
    		else if (actionFlag.equals("CANCEL")) { //신청취소 del_yn='Y'
    			parameterObject = ContentUtil.RequestToHashMap(request, selectColumnsList, "U",tableName);
    			
    			parameterObject.put("tableName", tableName);  // 테이블명
    			parameterObject.put("ubSeqArr",  ubSeqArr);   // ubseq 		
    			parameterObject.put("xmlId",     xmlId);      // xmlId 
    			contentService.cancelContent(parameterObject);

    			resultMsg = "신청취소가 처리되었습니다";
    		}
    		else if (actionFlag.equals("CONFIRM")) { //신청취소 del_yn='Y'
    			parameterObject = ContentUtil.RequestToHashMap(request, selectColumnsList, "U",tableName);
    			
    			parameterObject.put("tableName", tableName);  // 테이블명
    			parameterObject.put("ubSeqArr",  ubSeqArr);   // ubseq 		
    			parameterObject.put("xmlId",     xmlId);      // xmlId 
    			contentService.confirmContent(parameterObject);

    			resultMsg = "승인처리되었습니다";
    		}


    	} catch(Exception e) {    		
       		resultMsg = "ERROR";
       		errorMsg = "등록 오류 발생";
       	}
    	
    	// key 컬럼 추출
    	String keyCol = "";		
    	if (keyColList.size() > 0) {
			for (int i=0; i<keyColList.size(); i++) {

				if (i == 0) {
					keyCol = (String) keyColList.get(i).get("keyCol");
				} else {
					keyCol += "@" + (String) keyColList.get(i).get("keyCol");
				}

			}
    	}

    	String OID = scrin_code + "|" + ubseq + "|" + actionFlag + "|" + keyCol + "|" + extCallBackFunction;
    	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("sPostFun",  StringUtil.isNullToString(request.getParameter("sPostFun")));
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
       	
       	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg  : " + errorMsg.replaceAll("[\r\n]","")); 
       	log.info("처리후 호출 함수  (callBackFunction)    : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수 2(extCallBackFunction) : " + StringUtil.isNullToString(request.getParameter("extCallBackFunction")).replaceAll("[\r\n]",""));
       	
		return  "/unibill/com/cmm/commonMsg";
		
	}
	
	/**
	 * 공통 컨텐츠 상세화면 객체 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@RequestMapping(value="/content/selectContentDetailObjList.do")
    public @ResponseBody Map<String, Object> selectContentDetailObjList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code")); // 화면id 
		String formName   = StringUtil.isNullToString(request.getParameter("formid"));     // form name
		String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));  // 테이블명		
		String flag       = StringUtil.isNullToString(request.getParameter("flag"));       // 메인(main), 서브(sub) obj 조회 구분
		String ubseq       = StringUtil.isNullToString(request.getParameter("ubseq"));       // 메인(main), 서브(sub) obj 조회 구분		
		String gd_id           = StringUtil.isNullToString(request.getParameter("gr_cont_gd_id")); 
		String od_no           = StringUtil.isNullToString(request.getParameter("gr_od_no"));
		String cust_id         = StringUtil.isNullToString(request.getParameter("gr_cust_id"));

		if ("".equals(formName)) {
			formName = "frm_main";
		}
		
//		log.info("########## /content/selectContentDetailObjList.do : [scrin_code : " + scrin_code + "][formName : " + formName + "][tableName : " + tableName + "]");
		
		HashMap<String, String> parameterObject =  new HashMap<String, String>();
		parameterObject.put("userid", loginVO.getUserId());
		
		// 테이블의 key 컬럼 정보 조회
		parameterObject.put("masterTableName",    tableName);  // 테이블명
		List<EgovMap> keyColList = contentService.selectKeyColList(parameterObject);		

		// 상세화면 객정 정보 조회
		parameterObject.put("role_code",    loginVO.getRoleId());  // 권한id
		parameterObject.put("scrin_code",   scrin_code); // 화면id
		parameterObject.put("find_show_yn", "");         // 검색조건 출력여부
    	parameterObject.put("dtl_show_yn",  "Y");        // 상세 출력여부
    	parameterObject.put("flag",         flag);       // 메인(main), 서브(sub) obj 조회 구분
    	
    	String owner = fileUploadProperties.getProperty("Globals.UserName");		
		parameterObject.put("owner", owner);
		 
		
		//List<EgovMap> varlist; 
		String dyncmic_tbl = contentService.selectDynamicColTbl(parameterObject);
		if(!"".equals(dyncmic_tbl) && dyncmic_tbl != null){
			log.info("########## /content/selectContentDetailObjList gd_id : " + gd_id.replaceAll("[\r\n]",""));
			//if(dyncmic_tbl.equals("mb_cfgcont_etccol") && ( tableName.equals("mb_cfgod_etc") || tableName.equals("mb_cfgcont_etc"))) {
			
			if( gd_id.equals("undefined") ) {
				gd_id=""; 
			}
			if(dyncmic_tbl.equals("mb_cfgcont_etccol") && !gd_id.equals("") ) { 
				//gd_id = contentService.selectGd(parameterObject);
			//	String v = gd_id.substring( gd_id.lastIndexOf("(") +1);
				//log.info("TTTTTTTTTTTTTTTssss v="+v);
				//gd_id = v.substring(0, v.lastIndexOf(")")  );
				//log.info("TTTTTTTTTTTTTTTssss gd_id="+gd_id);
				parameterObject.put("ContGdId", gd_id);  // 상품id
			}
			//varlist = contentService.selectvarColumnsList(parameterObject);
		} 
		
        
    	List<EgovMap> selectDetailList = contentService.selectSearchObjectList(parameterObject);
    	
    	List<EgovMap> detailList = new ArrayList<EgovMap>(); 

    	for (int i = 0; i<selectDetailList.size(); i++) {
    		
    		EgovMap map = new EgovMap();
    		
    		map.put("pobjid",       StringUtil.isNullToString(selectDetailList.get(i).get("upObjId")));        // 상위객체id
    		/*
    		String s=StringUtil.isNullToString(selectDetailList.get(i).get("objNm"));
    		if(s.equals("DATA")) map.put("name",         "test");  //가변 객체명
    		else map.put("name",         StringUtil.isNullToString(selectDetailList.get(i).get("objNm")));          // 객체명
    		*/
    		map.put("name",         StringUtil.isNullToString(selectDetailList.get(i).get("objNm")));          // 객체명
    		map.put("pcolid",       StringUtil.isNullToString(selectDetailList.get(i).get("upColId")));        // 상위컬럼id
    		map.put("objpos",       StringUtil.isNullToString(selectDetailList.get(i).get("objPos")));         // 객체위치
    		map.put("objtype",      StringUtil.isNullToString(selectDetailList.get(i).get("objType")));        // 객체타입
    		map.put("fixyn",       StringUtil.isNullToString(selectDetailList.get(i).get("fixYn")));        // 객체타입
    		map.put("width",        StringUtil.isNullToString(selectDetailList.get(i).get("findItemW")));      // 검색항목가로
    		map.put("colpos",       StringUtil.isNullToString(selectDetailList.get(i).get("colPos")));         // 검색행위치
    		map.put("rowpos",       StringUtil.isNullToString(selectDetailList.get(i).get("rowPos")));         // 검색행위치
    		map.put("xmlid",        StringUtil.isNullToString(selectDetailList.get(i).get("xmlId")));          // xml id
    		map.put("grpcd",        StringUtil.isNullToString(selectDetailList.get(i).get("grpCd")));          // 그룹코드
    		map.put("grptype",      StringUtil.isNullToString(selectDetailList.get(i).get("grpType")));        // 그룹타입
    		map.put("subformyn",    StringUtil.isNullToString(selectDetailList.get(i).get("subformyn")));      // 서브객체존재여부	
    		map.put("maxxpos",      StringUtil.isNullToString(selectDetailList.get(i).get("maxxpos")));    	   // 	    		     		
    		map.put("objwidth",     StringUtil.isNullToString(selectDetailList.get(i).get("w")));              // 검색객체width
    		map.put("objpk",        StringUtil.isNullToString(selectDetailList.get(i).get("dtlMustYn")));      // 상세필수여부
    		map.put("dtlshowyn",    StringUtil.isNullToString(selectDetailList.get(i).get("dtlShowYn")));      // 상세출력여부
    		map.put("dtlobjw",      StringUtil.isNullToString(selectDetailList.get(i).get("dtlObjW")));        // 상세객체width
    		map.put("dtlrowpos",    StringUtil.isNullToString(selectDetailList.get(i).get("dtlRowpos")));      // 상세행위치
    		map.put("dtlcolpos",    StringUtil.isNullToString(selectDetailList.get(i).get("dtlColPos")));      // 상세열위치 
    		map.put("colmgrcnt",    StringUtil.isNullToString(selectDetailList.get(i).get("colMgrCnt")));      // 상세열위치 + 상세행병합
    		map.put("dtlmaxcol",    StringUtil.isNullToString(selectDetailList.get(i).get("dtlMaxCol")));      // 상세열위치 MAX
    		map.put("dtlmustyn",    StringUtil.isNullToString(selectDetailList.get(i).get("dtlMustYn")));      // 상세필수여부
    		map.put("sql",          StringUtil.isNullToString(selectDetailList.get(i).get("objSql")));         // fwobj sql문
    		map.put("fwformobjsql", StringUtil.isNullToString(selectDetailList.get(i).get("fwformObjSql")));   // fwform sql문
    		map.put("colspan",      StringUtil.isNullToString(selectDetailList.get(i).get("dtlRowMgr")));      // 셀병합갯수
    		map.put("rolecolspan",  StringUtil.isNullToString(selectDetailList.get(i).get("dtlRoleMgr")));     // 상세권한행병합갯수
    		map.put("extfun",       StringUtil.isNullToString(selectDetailList.get(i).get("extFun")));         // 실행함수
    		map.put("maxlen",       StringUtil.isNullToString(selectDetailList.get(i).get("maxLen")));         // 최대길이
    		map.put("autogencol",   StringUtil.isNullToString(selectDetailList.get(i).get("autoGenCol")));     // 자동완성컬럼    		
    		
    		map.put("extprfun",     StringUtil.isNullToString(selectDetailList.get(i).get("extprFun")));         // 최대길이
    		map.put("extpofun",   	StringUtil.isNullToString(selectDetailList.get(i).get("extpoFun")));     // 자동완성컬럼    		
    		
    		
    		String objid   = (String) selectDetailList.get(i).get("objId");      // 객체id		
    		String colid   = (String) selectDetailList.get(i).get("colId");      // 컬럼id
    		String robjid  = (String) selectDetailList.get(i).get("rObjId");     // 권한별 객체id
    		String rshowyn = (String) selectDetailList.get(i).get("rShowYn");    // 권한별 객체 출력여부
    		String rriteyn = (String) selectDetailList.get(i).get("rWriteYn");   // 권한별 객체 쓰기여부
    		
//    		// 권한별 객체id가 적용되어 있으면 행병합
//    		if (objid.equals(robjid) && "Y".equals(rshowyn)) {
//    			colspan = 3;
//    		} else {
//    			map.put("colspan",   selectDetailList.get(i).get("dtlRowMgr"));
//    		}
    		
    		map.put("objid",     objid);
    		map.put("robjid",    robjid);
    		map.put("rshowyn",   rshowyn);
    		map.put("rriteyn",   rriteyn);  
    		map.put("formname",  formName);
    		map.put("colid",     colid);

    		// 객체가 key 컬럼인지 확인
    		for (int k=0; k<keyColList.size(); k++) {
    			String keyCol = (String) keyColList.get(k).get("keyCol");
    			
    			if (map.get("colid").equals(keyCol)) {
    				map.put("keycolid", keyCol);
    			}
    		}
    		
    		// 기준정보 및 기초코드(공통코드) 조회(콤보, 체크박스, 라디오버튼 등)
    		if (StringUtils.isNotEmpty(map.get("xmlid").toString()) || StringUtils.isNotEmpty(map.get("grpcd").toString()) || StringUtils.isNotEmpty(map.get("grptype").toString()) || !map.get("sql").equals("") || StringUtils.isNotEmpty(map.get("fwformobjsql").toString())) {    			
    			map.put("dynData", ContentUtil.RequestDynDataMap((String) map.get("grptype"), (String) map.get("grpcd"), (String) map.get("xmlid"), (String) map.get("sql"), (String) map.get("objtype"), (String) map.get("fwformobjsql"), parameterObject));
    		}
    		// 코드여부일 경우 xmlid로 코드 조회
    		else if (colid.indexOf("_yn") > -1) {  // 코드여부일 경우 xmlid로 코드 조회
    			HashMap<String, String> parmObj =  new HashMap<String, String>();
    			
    			parmObj.put("cdGun",   "xml_id");
    			parmObj.put("xmlid",   "selectYnCode");
    			
    			map.put("dynData", ContentUtil.dynData(parmObj, (String) map.get("objtype"), ""));    			
    		}
    		//보안성검토 sql 제거
    		map.put("sql",  "");
    		map.put("fwformobjsql",  "");
    		 
    		detailList.add(map);

    	}

    	Map<String , Object> returnMap = new HashMap<String , Object>();
		
		log.info("######## 상세(객체정보) [ " + flag.replaceAll("[\r\n]","") + " ] : " + String.valueOf(detailList).replaceAll("[\r\n]",""));
		
		returnMap.put("rows", detailList);

        return returnMap;
	}

	/**
	 * 공통 컨텐츠 상세정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectContentDetail.do")
    public @ResponseBody Map<String, Object> selectContentDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 세션 사용자정보
	    LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
	    String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
				
		HashMap parameterObject =  new HashMap();				
                
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code")); // 화면id
		String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));  // 테이블명
		String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));      // xmlId
		String ubseq      = StringUtil.isNullToString(request.getParameter("ubseq"));      // 자료순번
		
		List<EgovMap> selectColumnsList = new ArrayList<EgovMap>();		

        parameterObject.put("scrin_code", scrin_code);  // 화면id
        parameterObject.put("masterTableName", tableName);
        parameterObject.put("tableName", tableName);        	
		parameterObject.put("ubseq", ubseq);

		// 상단 그리드 조회 컬럼 항목
    	parameterObject.put("dtl_show_yn", "Y");                  // 상세출력옵션
    	parameterObject.put("role_code",   loginVO.getRoleId());  // 권한id
    	selectColumnsList = contentService.selectColumnsList(parameterObject);
    	
    	// 조회 컬럼 항목 쿼리 생성    	
        List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "D", "N", "");
        log.info("######## 상세 컬럼 정보(selectColumnsRetList) : " + String.valueOf(selectColumnsRetList).replaceAll("[\r\n]",""));
        
        //업로드 파일 조회
		String fileGrp = ContentUtil.getfilegrp(selectColumnsList);
    	String imgfileGrp = ContentUtil.getimgfilegrp(selectColumnsList);
    	
        parameterObject.put("selectColumnsList", selectColumnsRetList);         // pjh test
        
		Map<String , Object> returnMap = new HashMap<String , Object>();
		if(ubseq != null && !ubseq.equals("")){
			parameterObject.put("col_tbl",""); //상세는 dynamic combox pjh 20210226
			List<EgovMap> jsonData = contentService.selectContentDetail(parameterObject, xmlId);
	    	
			//파일 처리
	    	HashMap fparam =  new HashMap();
	    	if(fileGrp.equals("O")){
	    		
	    		//첨부 파일 카운트 조회
	    		fparam.put("od_no",((Map)jsonData.get(0)).get("od_no"));
	    		int fCnt = contentService.fileCnt(fparam);
	    		((Map)jsonData.get(0)).put("fileupload", fCnt);
	    	}else if(fileGrp.equals("C")){
	    		fparam.put("cont_seq",((Map)jsonData.get(0)).get("cont_seq"));
	    		int fCnt = contentService.contfileCnt(fparam);
	    		((Map)jsonData.get(0)).put("fileupload", fCnt);
	    	}else if(fileGrp.equals("N")){
	    		fparam.put("ubseq", ubseq);
	    		fparam.put("tableName", tableName);
	    		EgovMap finfo = contentService.getubseqfileInfo(fparam);
	    		if(!(finfo == null || finfo.size() ==0)){
	    			((Map)jsonData.get(0)).put("sfileupload", finfo.get("nm"));
	    		}
	    	}
	    	
	    	if(imgfileGrp.equals("I")){
	    		fparam.put("ubseq", ubseq);
	    		fparam.put("tableName", tableName);
	    		EgovMap finfo = contentService.getimgfileInfo(fparam);
	    		if(!(finfo == null || finfo.size() == 0)){
	    			((Map)jsonData.get(0)).put("imgfileupload", finfo.get("nm"));
	    		}
	    	}
			
			// 상세보기 이력 삽입
	        HashMap paramObj =  new HashMap();
	        paramObj.put("REG_ID", loginVO.getUserId());  // 사용자ID
			
	         
    		saveContentHis( paramObj,  tableName,  "",  request, "R" , jsonData );
			
			returnMap.put("rows", ContentUtil.selectJsonDetailData(jsonData, selectColumnsList, unibillFuncEnc));
			
			
		}
		return returnMap;
	}
	
	/**
	 * 지우개 클릭시 초기화 대상 컬럼 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectEraserTargetList.do")
    public @ResponseBody Map<String, Object> selectEraserTargetList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap parameterObject =  new HashMap();				
                
		String objid   = StringUtil.isNullToString(request.getParameter("objid"));
		String pobjid  = StringUtil.isNullToString(request.getParameter("pobjid"));
				
		// 조회 컬럼 항목
        parameterObject.put("objid", objid);        
        parameterObject.put("pobjid", pobjid);
        
		List<EgovMap> jsonData = contentService.selectEraserTargetList(parameterObject);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		returnMap.put("rows", jsonData);
        
        return returnMap;  
	}
	
	/**
	 * 즐겨찾가 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectBookMarkList.do")
    public @ResponseBody Map<String, Object> selectBookMarkList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap parameterObject =  new HashMap();				
                
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        //플레그 조회
        String lFlag = request.getParameter("lFlag");
        
        // 조회 컬럼 항목
        List<EgovMap> jsonData = null;
        parameterObject.put("user_id", loginVO.getUserId());
        
        if(lFlag.equals("J")){
        	jsonData = contentService.selectBookMarkList(parameterObject);
        	request.getSession().removeAttribute("bookMarkList");
    		request.getSession().setAttribute("bookMarkList", jsonData);
        }else if(request.getSession().getAttribute("bookMarkList") == null){
        	jsonData = contentService.selectBookMarkList(parameterObject);
    		request.getSession().setAttribute("bookMarkList", jsonData);
        }else{
        	jsonData = (List<EgovMap>)request.getSession().getAttribute("bookMarkList");
        }
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		returnMap.put("rows", jsonData);
        
        return returnMap;  
	}
	
	/**
	 * 즐겨찾기 위치 정보를 변경한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/updateBookMarkPos.do")
    public @ResponseBody Map<String, Object> updateBookMarkPos(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		        
        String resultMsg     = "";
        int resultCount      = 0;
       	String bookmark_pos  = StringUtil.isNullToString(request.getParameter("bookmark_pos"));  // 즐겨찾기 위치
       	String user_id       = loginVO.getUserId();
       	String upd_id        = loginVO.getUserId();
       	String lFlag		 = request.getParameter("lFlag");
       	
		HashMap parameterObject =  new HashMap();
		parameterObject.put("bookmark_pos", bookmark_pos);  // 즐겨찾기 위치
		parameterObject.put("user_id", user_id);            // 사용자ID
		parameterObject.put("upd_id", upd_id);              // 수정자ID
		
    	try {    		    		    	   		
    			if(lFlag.equals("J")){
    				resultCount = contentService.updateBookMarkPos(parameterObject);
    			}else{
    				resultCount = 1;
    			}
    			loginVO.setBookmarkPos(bookmark_pos);
    			
    			if (resultCount > 0) {
    				resultMsg = "SUCCESS";
    			} else {
    				resultMsg = "FAIL";
    			}    		
    		
    	} catch(Exception e) {    		
    		resultMsg = "FAIL";
       	}    	
    	
    	Map<String , Object> returnMap = new HashMap<String , Object>();
    	returnMap.put("result", resultMsg);
        
        return returnMap;		
	}
	
	/**
	 * 즐겨찾기 등록 및 삭제 처리한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/saveBookMark.do")
    public @ResponseBody Map<String, Object> saveBookMark(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
        int resultCount   = 0;
        String resultMsg  = "";
		String flag       = StringUtil.isNullToString(request.getParameter("flag"));       // 구분
       	String menu_code  = StringUtil.isNullToString(request.getParameter("menu_code"));  // 메뉴id
       	String user_id    = loginVO.getUserId();
       	String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
		HashMap parameterObject =  new HashMap();
				
		parameterObject.put("menu_code", menu_code);   // 메뉴ID
		parameterObject.put("user_id", user_id);       // 사용자ID
		parameterObject.put("reg_id", reg_id);         // 등록자ID
		parameterObject.put("upd_id", upd_id);         // 수정자ID
		
    	try {    		    		
    	   	
    		if (flag.equals("I")) {
    			
    			// ubseq 생성
    			parameterObject.put("tableName", "fwbookmark");
	            //String ubseq = KeyUtil.getUbseq(parameterObject.get("tableName").toString(), "ubseq");
	            
	            // 등록
	            //parameterObject.put("ubseq", ubseq);
    			resultCount = contentService.insertBookMark(parameterObject);
    			
    			if (resultCount > 0) {
    				resultMsg = "SUCCESS";
    			} else {
    				resultMsg = "FAIL";
    			}
    			
    		} else if (flag.equals("D")) {
    			
    			resultCount = contentService.deleteBookMark(parameterObject);
    			
    			if (resultCount > 0) {
    				resultMsg = "SUCCESS";
    			} else {
    				resultMsg = "FAIL";
    			}
    		}
    		
    	} catch(Exception e) {    		
    		resultMsg = "FAIL";
       	}    	
    	
    	Map<String , Object> returnMap = new HashMap<String , Object>();
    	returnMap.put("result", resultMsg);
        
        return returnMap;		
	}
	
	/**
	 * 탭메뉴에 해당되는 그리드 컬럼 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectTabObjList.do")
    public @ResponseBody Map<String, Object> selectTabObjList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
				
		HashMap parameterObject =  new HashMap();				
                
		String menu_code  = StringUtil.isNullToString(request.getParameter("menu_code"));   // 메뉴id
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id 		
//		String gd_id = StringUtil.isNullToString(request.getParameter("gd_id"));  // 상품id
		String masterTableName = StringUtil.isNullToString(request.getParameter("masterTableName"));
		String tableName = StringUtil.isNullToString(request.getParameter("tableName"));
		String ubseq           = StringUtil.isNullToString(request.getParameter("ubseq"));
		String gd_id           = StringUtil.isNullToString(request.getParameter("gr_cont_gd_id")); 
		String od_no           = StringUtil.isNullToString(request.getParameter("gr_od_no"));
		String cust_id         = StringUtil.isNullToString(request.getParameter("gr_cust_id"));
		

		
        
		log.info("TTTTTTTTTTTTTTT gd_id="+gd_id.replaceAll("[\r\n]",""));
		log.info("########## /content/selectTabObjList.do [menu_code : " + menu_code.replaceAll("[\r\n]","") + "][scrin_code : " + scrin_code.replaceAll("[\r\n]","") + "]");
		log.info("########## /content/selectTabObjList.do [masterTableName : " + masterTableName.replaceAll("[\r\n]","") + "][tableName : " + tableName.replaceAll("[\r\n]","") + "]"); 

		parameterObject.put("menu_code", menu_code);    // 메뉴id        
		parameterObject.put("scrin_code", scrin_code);  // 화면id
		parameterObject.put("show_yn", "2");            // 그리드출력옵션
		parameterObject.put("dtl_show_yn", "");         // 상세출력옵션
		parameterObject.put("role_code",   loginVO.getRoleId());  // 권한id
        parameterObject.put("masterTableName", masterTableName);  // 마스터 테이블명
        parameterObject.put("ubseq",           ubseq);            // 데이터 순번
        
		List<EgovMap> jsonData;

		String dyncmic_tbl = contentService.selectDynamicColTbl(parameterObject);
		if(!"".equals(dyncmic_tbl) && dyncmic_tbl != null){
			log.info("########## /content/selectTabObjList.do gd_id : " + gd_id.replaceAll("[\r\n]","") + " tableName : " + tableName.replaceAll("[\r\n]","") + " dyncmic_tbl : " + dyncmic_tbl.replaceAll("[\r\n]","") );
			//if(dyncmic_tbl.equals("mb_cfgcont_etccol") && ( tableName.equals("mb_cfgod_etc") || tableName.equals("mb_cfgcont_etc"))) {
			
			if( gd_id.equals("undefined") ) {
				gd_id=""; 
			}
			if(dyncmic_tbl.equals("mb_cfgcont_etccol") && !gd_id.equals("") ) { 
				//gd_id = contentService.selectGd(parameterObject);
				//String v = gd_id.substring( gd_id.lastIndexOf("(") +1);
				//log.info("TTTTTTTTTTTTTTTssss v="+v);
				//gd_id = v.substring(0, v.lastIndexOf(")")  );
				//log.info("TTTTTTTTTTTTTTTssss gd_id="+gd_id);
				parameterObject.put("ContGdId", gd_id);  // 상품id
			}  
			//jsonData = contentService.selectDynamicColumnsList(parameterObject);			 
			jsonData = contentService.selectColumnsList(parameterObject);
		}else{
			jsonData = contentService.selectColumnsList(parameterObject);
		}
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rows", jsonData);
        
        return returnMap;
	}
	
	/**
	 * 탭 메뉴에 해당되는 그리드 DATA를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectTabDataList.json")
    public @ResponseBody Map<String, Object> selectTabDataListJson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		String unibillFuncEnc = (String)request.getSession().getAttribute("unibillFuncEnc");
				
		HashMap parameterObject =  new HashMap();				

		String scrin_code      = StringUtil.isNullToString(request.getParameter("scrin_code"));
		String masterTableName = StringUtil.isNullToString(request.getParameter("masterTableName"));
		String tableName       = StringUtil.isNullToString(request.getParameter("tableName"));
		String ubseq           = StringUtil.isNullToString(request.getParameter("ubseq"));
		String mon        	   = StringUtil.isNullToString(request.getParameter("mon"));      	// 가변컬럼사용여부
		String masterMenuCode  = StringUtil.isNullToString(request.getParameter("masterMenuCode"));
		String gd_id           = StringUtil.isNullToString(request.getParameter("gr_cont_gd_id"));
		String od_no           = StringUtil.isNullToString(request.getParameter("gr_od_no"));
		String cust_id         = StringUtil.isNullToString(request.getParameter("gr_cust_id")); //1개 js에서 여러개 안넘어오도록 삭제  
		
		parameterObject.put("scrin_code",      scrin_code);       // 화면id
        parameterObject.put("masterTableName", masterTableName);  // 마스터 테이블명
        parameterObject.put("tableName",       tableName);        // 서브 테이블명
        parameterObject.put("ubseq",           ubseq);            // 데이터 순번
        parameterObject.put("mon",           mon);            // 데이터 순번
        parameterObject.put("menu_code", masterMenuCode); //pjh test
        
		// 1. key 컬럼 정보 조회
        String keyval = "";
        String temp ="";
        String keycol = ContentUtil.selectKeyColList(parameterObject);
        String tabkeycol = ContentUtil.selectTabKeyColList(parameterObject);
        if("".equals(tabkeycol)) {
        	tabkeycol=keycol;
        	if(!"".equals(ubseq)) keyval = "(SELECT " +tabkeycol+" FROM  "+masterTableName+ " where ubseq="+ubseq+")" ;
        }
        else { //fwmenuscrin tabcol 이 정의된 컬럼순서대로 자료 생성
        	String[] collist = tabkeycol.split(",");
        	 
        	temp       = StringUtil.isNullToString(request.getParameter("gr_"+collist[0]));
        	//log.info("XXXXXXXXXXX>  temp : " + temp + " collist="+"gr_"+collist[0]+ " collist.length="+collist.length ); 
        	for(int i=0;i < collist.length;i++) {
        		temp           = StringUtil.isNullToString(request.getParameter("gr_"+collist[i]));
        		keyval += temp;
	        	if(i>0) keyval += ",";
        	}
        	 
        }
        
        parameterObject.put("keyCol", keycol);
        parameterObject.put("tabKeyCol", tabkeycol);  
    	parameterObject.put("keyVal",keyval); //pjh
    	parameterObject.put("OrderByColumn",tabkeycol); //pjh
        
        log.info("selectTabDataListJson>  keycol : " + keycol.replaceAll("[\r\n]","") + " tabkeycol="+tabkeycol.replaceAll("[\r\n]","")+ " keyval="+keyval.replaceAll("[\r\n]","") ); 			
        
        
		// 2. key 컬럼에 해당되는 키 값 조회
//		parameterObject.put("keyList", keyList);
//		List<EgovMap> keyColValList = contentService.selectKeyColValList(parameterObject);
        
        // 2. 공통 조회 인지 xml_id 조회인지 확인
        String xmlId = StringUtil.isNullToString(contentService.getScrinXmlIdSelect(parameterObject)); 
        List selectColumnsList = new ArrayList<EgovMap>();        
        log.info("##################### 공통 조회 인지 xml_id 조회인지 확인 xmlId : " + xmlId.replaceAll("[\r\n]","")); 
       
        
        
        String find_cond = StringUtil.isNullToString(contentService.getWhereCond(parameterObject));
        String mon_cond = StringUtil.isNullToString(contentService.getMonWhereCond(parameterObject));    
        String wheresql="";
        log.info("##################### pjh find_cond : " + find_cond.replaceAll("[\r\n]","") +" mon_cond="+mon_cond.replaceAll("[\r\n]",""));        
         
        if (!"".equals(mon_cond) && !"".equals(find_cond) ) {
        	wheresql = find_cond + " and "+mon_cond;    
        }
        else if (!"".equals(mon_cond) || !"".equals(find_cond) ) {
        	wheresql = find_cond + mon_cond;    
        }
        
        if (!"".equals(wheresql)) {
        	parameterObject.put("whereCol",wheresql);
        }
        
         
        if ("".equals(xmlId)) {
			
        	// 3. 조회 컬럼 항목   
			parameterObject.put("show_yn", "2");     // 출력옵션
			parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
 
			String dyncmic_tbl = contentService.selectDynamicColTbl(parameterObject);
			if(!"".equals(dyncmic_tbl) && dyncmic_tbl != null){ 
				
				log.info("########## /content/selectTabDataList.json gd_id : " + gd_id.replaceAll("[\r\n]","") + " tableName : " + tableName.replaceAll("[\r\n]","") + " dyncmic_tbl : " + dyncmic_tbl.replaceAll("[\r\n]","") );
				if(dyncmic_tbl.equals("mb_cfgcont_etccol") && ( tableName.equals("mb_cfgod_etc") || tableName.equals("mb_cfgcont_etc"))) {
					//gd_id = contentService.selectGd(parameterObject);
					String v = gd_id.substring( gd_id.lastIndexOf('(') +1);
					gd_id = v.substring( v.lastIndexOf(')') +1);
					parameterObject.put("ContGdId", gd_id);  // 상품id
				}  
				//selectColumnsList = contentService.selectDynamicColumnsList(parameterObject); 
				selectColumnsList = contentService.selectColumnsList(parameterObject);
			}else{
				selectColumnsList = contentService.selectColumnsList(parameterObject);	
			}			
			
			List selectColumnsRetList = ContentUtil.selectColumnsList(selectColumnsList, "L", "N", "");
		    parameterObject.put("selectColumnsList", selectColumnsRetList);
	//	    log.info("########## 조회 컬럼 항목 쿼리 생성(selectColumnsRetList) =====> " + selectColumnsRetList);
		    
        } else {
        	// 인자값을 출력해서 'term' parameter를 확인
    		Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    		    
    		    parameterObject.put(key,  val);
    		}
    		
        }
        // 페이징 처리
      	int page = StringUtil.strToInt(request.getParameter("page"));
    	int rows = StringUtil.strToInt(request.getParameter("rows"));
    	int firstIndex = rows * page - rows;    	
    	parameterObject.put("firstIndex", firstIndex);
 	    parameterObject.put("lastIndex", firstIndex+rows);
 	    parameterObject.put("limitSize", rows);  //limit 사용시 개수
	    
    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));
    	
    	if (sidx.equals("")) {
			parameterObject.put("OrderByColumn", "G2_ID");
    	} else {		
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (sord.equals("")) {
			parameterObject.put("OrderByType", "DESC");
		} else {
			parameterObject.put("OrderByType",sord);
		}
    	
    	parameterObject.put("OrderByColumn", sidx);
    	parameterObject.put("OrderByType",sord);
    	
    	
		
		// 4. tab 데이터 조회
    	    	 
    	List<EgovMap> jsonData = contentService.selectTabDataList(parameterObject, xmlId);
	    int total_count = contentService.selectTabDataListCnt(parameterObject, xmlId);
	    int total_page = 0;
        if (total_count > 0) {
        	total_page = (int) Math.ceil( (float)total_count/(float)rows );
        }
        
        Map<String , Object> returnMap = new HashMap<String , Object>();
        
        returnMap.put("page", page);
        returnMap.put("total", total_page);
        returnMap.put("records", total_count);     
        
        if (!"".equals(xmlId)) {  // xml_id 를 사용하는 경우
        	returnMap.put("rows", jsonData);
        } else {
        	returnMap.put("rows", ContentUtil.selectJsonDataList("G", jsonData, selectColumnsList, unibillFuncEnc, loginVO.getRoleId()));
        }
        return returnMap;
	}
	
	/**
	 * AutoComplete 기능으로 조회 조건을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/content/selectAutoComplete.json")
    public void selectAutoComplete(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tableName    = StringUtil.isNullToString(request.getParameter("tableName"));     // 테이블명
		String col_id       = StringUtil.isNullToString(request.getParameter("col_id"));        // 컬럼명
		String auto_gen_col = StringUtil.isNullToString(request.getParameter("auto_gen_col"));  // 자동완성컬럼
		String result       = StringUtil.isNullToString(request.getParameter("term"));          // 검색어
		
		// term parameter 값이 AutoComplete data 확인
		log.info("############# AutoComplete tableName : " + tableName.replaceAll("[\r\n]",""));
		log.info("############# AutoComplete col_id : " + col_id.replaceAll("[\r\n]",""));
		log.info("############# AutoComplete auto_gen_col : " + auto_gen_col.replaceAll("[\r\n]",""));
		log.info("############# AutoComplete result : " + result.replaceAll("[\r\n]",""));
		
		String auto_tbl = "";
		String auto_col = "";
		String key_col  = "";
		String where_col  = "";
		
		if (col_id.equals(auto_gen_col)) {			
			auto_col = "";			
		} else {

			String[] auto_col_gubun = auto_gen_col.split(",", 3);			
			String sAutoCol_1 = auto_col_gubun[0];
			if(auto_col_gubun.length >=3) where_col = auto_col_gubun[2];
			
			String[] auto_col_arr = sAutoCol_1.split("=");
			auto_tbl = auto_col_arr[0];
			auto_col = auto_col_arr[1];			
			
			
			log.info("############# AutoComplete auto_tbl : " + auto_tbl.replaceAll("[\r\n]",""));
			log.info("############# AutoComplete auto_col : " + auto_col.replaceAll("[\r\n]",""));
			log.info("############# AutoComplete where_col : " + where_col.replaceAll("[\r\n]",""));
			
			
			if (auto_col_gubun.length > 1) {
				key_col = auto_col_gubun[1];
			} else {
				key_col = col_id;
			}
		}
		
//		log.info("############# auto_tbl : " + auto_tbl);
//		log.info("############# auto_col : " + auto_col);
//		log.info("############# key_col  : " + key_col);
		
		HashMap<String, String> parameterObject =  new HashMap<String, String>();
		tableName=tableName.trim();
		col_id=col_id.trim();
		auto_tbl=auto_tbl.trim();
		auto_col=auto_col.trim();
		key_col=key_col.trim();
		where_col=where_col.trim();
		
		parameterObject.put("tableName",    tableName);     // 테이블명
		parameterObject.put("col_id",       col_id);	    // 컬럼명
		parameterObject.put("auto_tbl",     auto_tbl);      // 자동완성테이블
		parameterObject.put("auto_col",     auto_col);      // 자동완성컬럼
		parameterObject.put("where_col",     where_col);      // 자동완성컬럼
		parameterObject.put("key_col",      key_col);       // 자동완성 결과 [] 안에 들어갈 명칭을 조회하기 위한 where 조건 컬럼ID
		parameterObject.put("result",       result);	    // 검색어
		
		List<EgovMap> list = contentService.selectAutoCompleteList(parameterObject); //result값이 포함되어 있는 emp테이블의 네임을 리턴
		 
		 JSONArray ja = new JSONArray();
		 for (int i = 0; i < list.size(); i++) {
			 ja.add(list.get(i).get("colId"));
		 }
			 
		response.setContentType("application/json");
    	response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache"); 
        response.getWriter().print(ja.toString());
	}
	
	
	/**
	 * 객체(컬럼)별 정보를 변경한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/updateObjColInfo.do")
    public @ResponseBody Map<String, Object> updateObjColInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        String scrin_code = "";
        String tableName  = "";
    	int    ubseq      = 0;
        String resultMsg  = "";
        String errorFlag  = "";
        String errorMsg   = "";
        String updateSql  = "";
    	String obj_id     = StringUtil.isNullToString(request.getParameter("obj_id"));   // 객체id

    	HashMap parameterObject =  new HashMap();
    	
    	// 객체id로 update 할 컬럼 및 파라메타 정보 조회
    	parameterObject.put("obj_id", obj_id);
    	EgovMap upColInfo = contentService.selectUpdateColInfo(parameterObject);
    	
    	if (upColInfo != null) {
    		// update 대상 테이블 및 ubseq 추출	    	
	    	Object[] arrKeySet = request.getParameterMap().keySet().toArray();
	    	for (int j=0; j<arrKeySet.length; j++) {
	    		String   key = (String)arrKeySet[j];
	    		String[] val = request.getParameterValues(key);
	    		if ("tableName".equals(key)) {
	    			tableName = val[0];
	    		} else if ("ubseq".equals(key)) {
	    			ubseq = StringUtil.strToInt(val[0]);
	    		} else if ("scrin_code".equals(key)) {
	    			scrin_code = val[0];
	    		}	    			    		
	    	}
	    	String upColSplit[] = ((String) upColInfo.get("updColInfo")).split("!");
	    	for (int i=0; i<upColSplit.length; i++) {    		
	    		String[] sUpColInfo = upColSplit[i].split("=", -1);
	    		String upCol = sUpColInfo[0];
	    		String upVal = sUpColInfo[1];
	    		// 문자 "null" 일경우 테이블의 컬럼 정보를 '' 처리하고,
	    		if ("null".equals(StringUtil.lowerCase(upVal))) {
	                
	    			if("lastlogin_dt".equals(upCol)){
	    				updateSql += "," + upCol + " = null";
	    			}else{
	    				updateSql += "," + upCol + " = ''";
	    			}
	    			
	    			
	    		} else if (!"".equals(upVal)) {		// null이 아닌 경우는 해당 값으로 update 하고,
	    			
//	    			if ("sysdate".equals(StringUtil.lowerCase(upVal))) {
	    				updateSql += "," + upCol + " = " + upVal + "";
//	    			} else {
//	    				updateSql += "," + upCol + " = '" + upVal + "'";
//	    			}
	    			
	    		} else {  // 그외는 화면에서 입력(수정)한 컬럼의 값으로 테이블의 정보를 update 한다. 
	    			
	    			// 화면(테이블) 객제정보 조회
	    	    	Object[] arrKeySet2 = request.getParameterMap().keySet().toArray();
	    	    	for (int j=0; j<arrKeySet2.length; j++) {
	    	    		String   key = (String)arrKeySet2[j];
	    	    		String[] val = request.getParameterValues(key);
	    	    		
	    	    		if (upCol.equals(key)) {
	    	    			updateSql += "," + upCol + " = '" + val[0] + "'";
	    	    		}
	    	    	}
	    			
	    		}
	    		    		
	    	}
	    	// 객체(컬럼)별 update 처리
			parameterObject.put("upd_id",    loginVO.getUserId());  // 사용자id
			parameterObject.put("sql",       updateSql);            // update 문 (컬럼 = 값)
			parameterObject.put("tableName", tableName);            // 테이블명
			parameterObject.put("ubseq",     ubseq);                // ubseq
			EgovMap info = contentService.updateObjColInfo(parameterObject);
			
			resultMsg = (String) info.get("resultMsg");
			errorMsg  = (String) info.get("errorMsg");
			
			if ("true".equals(resultMsg)) {
				errorFlag  = "SUCCESS";
			} else {
				errorFlag  = "FAIL";
			}
			
    	} else {
    		
    		errorFlag  = "NODATA";
    		
    	}

    	List<EgovMap> resultInfo = new ArrayList<EgovMap>();
    	EgovMap map = new EgovMap();
    	
    	map.put("errorMsg",   errorMsg);   // 에러메시지
    	map.put("errorFlag",  errorFlag);  // 에러 구분
    	map.put("resultParm", scrin_code + "|" + ubseq + "|UPDATE");  // 저장 후 처리를 위한 값
    	
    	resultInfo.add(map);
    	Map<String , Object> returnMap = new HashMap<String , Object>();        
		returnMap.put("rows", resultInfo);
		
        return returnMap;
	}
	
	/**
	 * 공통 컨텐츠 일괄등록(엑셀 업로드) 처리 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/bundleUploadContent.do")
    public String bundleUploadContent(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));  // 테이블명
        String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));      // xml_id
        String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
        int excel_sheet = StringUtil.strToInt(request.getParameter("excel_sheet")); // 엑셀시트
        
        String uploadResult = "";
        String resultMsg    = "";
       	String errorMsg     = "";
       	int    excelTotCnt  = 0; 
       	int    successCnt   = 0;
       	int    failCnt      = 0;
       	String errorData    = "";
       	String file_name    = "";
       		
   		// 파일정보 추출
       	Map upFileInfo = FileMngUtil.getFileInfo(request, "userfile2");
       	String excel_file = (String) upFileInfo.get("fileName");  // 파일명
       	
   		// 확장자 추출
   		int index = excel_file.lastIndexOf('.');
   		String fileExt = excel_file.substring(index + 1);
   		
   		// new 파일명
   		String newFile  = FileMngUtil.newFileName(); 
   		String fileName = newFile + "." + fileExt;
   		
   		// 파일 업로드
   		fileService.fileInsert(request, "content_upload", fileName, "userfile2");  // 파라메터 <HttpServletRequest>, <파일올라갈 위치>, <파일명>
   		
   		// 업로드 파일
   		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator;
   		log.info("############ uploadPath : " + uploadPath.replaceAll("[\r\n]",""));
   		
   		
   		
//   		fileService.fileInsert(request, "content_upload", FileMngUtil.newFileName() + ".csv");  // 파라메터 <HttpServletRequest>, <파일올라갈 위치>, <파일명>
   		
   		// 엑셀파일을 csv 파일로 변환
   		String excelFile = uploadPath + "content_upload" + File.separator + fileName;          // 엑셀파일
   		String csvFile   = uploadPath + "content_upload" + File.separator + newFile + ".csv";  // csv 파일
   		
   		File inputFile = new File(excelFile);
   		File outputFile = new File(csvFile);
   		CsvUtils.xlsToCsv(inputFile, outputFile, excel_sheet, fileExt,0,0);
   		
   		// 파라메타
   		HashMap parameterObject =  new HashMap();

   		parameterObject.put("tableName",       tableName);   // 테이블명
   		parameterObject.put("masterTableName", tableName);   // 테이블명
    	parameterObject.put("reg_id",          reg_id);      // 등록자id
    	parameterObject.put("upd_id",          upd_id);      // 수정자id
    	parameterObject.put("xmlId",           xmlId);       // xml_id
    	
    	// key 컬럼정보 조회
    	parameterObject.put("keyCol", ContentUtil.selectKeyColList(parameterObject));
    	
   	    // csv파일 data 추출
   		String csvData = "";
   		String insertColumnsStr = "";
   		String keyColIdx = "";
   		
   		List<?> insertColumsList = new ArrayList();
   		
   		List<String[]> data = CsvUtils.readCsv(csvFile);
   		
   		if (data.size() == 0) {
   			resultMsg = "SAVE_PROCESS";
   			errorMsg  = "일괄등록 데이터가 없습니다.<br/>(엑셀파일 및 시트를 확인 해 주십시오.)";
   		} else {
   			
	        Iterator<String[]> it = data.iterator();	
	        int i = 0;
	        while (it.hasNext()) {
	            String[] array = (String[]) it.next();	            
	            for (String s : array) {
	            	csvData = s + " ";
	            	csvData = csvData.substring(1);                	                
	            }
	            	            
	            // insert 컬럼 정보 생성
	            if (i == 2) {
	            	insertColumnsStr = csvData;
	            	
	            	// insert 컬럼 추출
	    	   		String insertColumns = ContentUtil.excelUploadData(insertColumnsStr, "column");
	    	   		log.info("############ insert 컬럼 추출(insertColumnsStr) : " + String.valueOf(insertColumns).replaceAll("[\r\n]",""));
	    	   		
	    	   	    // insert 컬럼 생성
	    	   		insertColumsList = ContentUtil.excelUploadInsertColumnsList(insertColumns);
	    	   		log.info("############ insert 컬럼 생성(insertColumsList) : " + String.valueOf(insertColumsList).replaceAll("[\r\n]",""));
	    	   		parameterObject.put("insertColumsList", insertColumsList);  // insert 컬럼 정보
	    	   		
	    	   		// key 컬럼 index 정보	    	   		
	    	    	keyColIdx = ContentUtil.selectKeyColIndex(insertColumns, parameterObject.get("keyCol"));
	    	    	log.info("############ key 컬럼 index 정보(keyColIdx) : " + keyColIdx);
	    	   			    	   		
	        	}
	                            
	            
	            if (i > 2) {
	            		
	            	TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		            
		            log.info(String.format("TRANSACTION isCompleted:%s isNewTransaction:%s isRollbackOnly:%s", transactionStatus.isCompleted(), transactionStatus.isNewTransaction(), transactionStatus.isRollbackOnly()));
		            
		            // insert data 생성
		   			List<?> insertDataList = ContentUtil.excelUploadInsertColumnsList(csvData);
		   			log.info("############ insert data 생성(insertDataList) : " + String.valueOf(insertDataList).replaceAll("[\r\n]",""));
		        
		            // update data 생성
		   			List<?> updateDataList = ContentUtil.updateColumnsList(insertColumsList, insertDataList, keyColIdx);
		   			log.info("############ update data 생성(updateDataList) : " + String.valueOf(updateDataList).replaceAll("[\r\n]",""));
		   			
		   			// key 컬럼에 대한 값 추출
		   			String keyVal = "";
		   			for (int j=0; j<insertDataList.size(); j++) {
		   				
		   				// key 컬럼 index
		   				String [] keyIdx = keyColIdx.split(",");
		   				
		   				// 데이터 index 와 key 컬럼 index가 같으면 where 조건 컬럼임
		   				for (int k=0; k<keyIdx.length; k++) {
		   					
		       				if (j == StringUtil.strToInt(keyIdx[k])) {
		       					if ("".equals(keyVal)) {
		       						keyVal = insertDataList.get(j).toString().trim();
		       					} else {
		       						keyVal += insertDataList.get(j).toString().trim();
		       					}
		       				}
		       				
		   				}
		   				
		   			}
		   			
		
//		   			log.info("######################### where 조건 컬럼에 대한 값 : " + keyVal);
		   			parameterObject.put("keyVal", keyVal);
		   			
		   			// ubseq 생성
		   			if ("".equals(xmlId)) {
			   			String ubseq = KeyUtil.getUbseq(parameterObject.get("tableName").toString(), "ubseq");		   			
			   			parameterObject.put("ubseq", ubseq);                    // ubseq
		   			} else {
		   				
		   				for (int s=0; s<insertColumsList.size(); s++) {
		   					log.info("#######[엑셀일괄업로드 XML ID 사용]####### [key : " + String.valueOf(insertColumsList.get(s)).replaceAll("[\r\n]","") + "][val : " + String.valueOf(insertDataList.get(s)).replaceAll("[\r\n]","") + "]");
		   					parameterObject.put(insertColumsList.get(s),  insertDataList.get(s));
		   				}
		   				
		   			}
		   			
		   			parameterObject.put("insertDataList", insertDataList);  // insert 데이터 정보
		   			parameterObject.put("updateDataList", updateDataList);  // update 데이터 정보
		   			
		   			try {       				
		   				
		   				EgovMap info = contentService.insertBundleUploadContent(parameterObject);
		
		   				uploadResult = (String) info.get("uploadResult");
		   				errorMsg     = (String) info.get("errorMsg");
		
		   				if ("true".equals(uploadResult)) {
		   					transactionManager.commit(transactionStatus);
		   					successCnt += 1;
		   				} else {
		   					transactionManager.rollback(transactionStatus);

		   					if ("".equals(errorData)) {
		   						errorData = errorMsg + "@" + csvData;
		   					} else {
		   						errorData += "#" + errorMsg + "@" + csvData;
		   					}
		   				}
		
		   			} catch (Exception e) {
		   				transactionManager.rollback(transactionStatus);
		   			}
		   			
		   			excelTotCnt += 1;
	            	
	            }
	   			
	   			i += 1;
	        }
	        
	        log.info("################# 엑셀 총건수(excelTotCnt) : " + excelTotCnt);
	        log.info("################# 엑셀 성공건수(successCnt) : " + successCnt);
	        
	        
	        failCnt = excelTotCnt - successCnt;
	        
//	        if (successCnt > 0) {
	        	resultMsg = "SAVE_PROCESS";
		   		errorMsg  = "";
//	        }

	        log.info("################# 실패건수(failCnt) : " + (failCnt));
	        
	        // 업로드 오류건 엑셀 저장	   		
	   		if (failCnt > 0) { 
	   			String excelHeader = ContentUtil.excelUploadData(insertColumnsStr, "header");
	   			file_name = "ERROR_"+ newFile + "." + fileExt;
	
	   			log.info("###### 오류 엑셀 헤더   (excelHeader) : " + excelHeader.substring(1).replaceAll("[\r\n]","") + "]");
	   			log.info("###### 오류 엑셀 데이터 (errorData)   : " + errorData.replaceAll("[\r\n]","") + "]");
	   			ExcelWriter.excelWriter(file_name, fileExt, "content_upload", "오류메시지" + excelHeader, errorData);
	   		}
   		
   		}
   		
        String OID = excelTotCnt+"|"+successCnt+"|"+failCnt+"|"+file_name;
    	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수

    	log.info("OID        : " + OID.replaceAll("[\r\n]",""));
    	log.info("resultMsg  : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg   : " + errorMsg.replaceAll("[\r\n]",""));
       	log.info("일괄등록 처리후 호출 함수(callBackFunction) : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));

       	long endTime = System.currentTimeMillis();

       	log.info("[############################ 엑셀 업로드 execute TIME : " + (endTime - startTime) + "(ms) ##############################]");

		return  "/unibill/com/cmm/commonMsg";

	}
	
	/**
	 * 그룹코드(타입)에 대한 코드 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/commonCodeSelect.json")
    public @ResponseBody Map<String, Object> commonCodeSelect(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap parmObj =  new HashMap();				
                		
		String code_gubun = StringUtil.isNullToString(request.getParameter("code_gubun"));		
		String grp_cd     = StringUtil.isNullToString(request.getParameter("grp_cd"));
		String obj_id     = StringUtil.isNullToString(request.getParameter("obj_id"));
		String grid_id    = StringUtil.isNullToString(request.getParameter("grid_id"));
		String code_value = StringUtil.isNullToString(request.getParameter("code_value"));
		String form_id    = StringUtil.isNullToString(request.getParameter("form_id"));
		String code_id    = StringUtil.isNullToString(request.getParameter("code_id"));
		log.info("########## [code_gubun : " + code_gubun.replaceAll("[\r\n]","") + "][grp_cd : " + grp_cd.replaceAll("[\r\n]","") + "][obj_id : " + obj_id.replaceAll("[\r\n]","") + "][grid_id : " + grid_id.replaceAll("[\r\n]","") + "][code_value : " + code_value.replaceAll("[\r\n]","") + "][form_id : " + form_id.replaceAll("[\r\n]","") + "][code_id : " + code_id.replaceAll("[\r\n]","") + "]");
		
		// 그룹타입
	    if ("grp_type".equals(code_gubun)) {
	    	parmObj.put("cdGun",   "grp_type");
	    	parmObj.put("grp_type", grp_cd);
	    // 그룹코드
	    } else if ("grp_cd".equals(code_gubun)) {
	    	parmObj.put("cdGun",   "grp_cd");
	    	parmObj.put("grp_cd",   grp_cd);		  		      		    
	    // xmlid
	    } else if ("xml_id".equals(code_gubun)) {
	    	parmObj.put("cdGun",   "xml_id");
	    	parmObj.put("xmlid",    grp_cd);
	    // sql
	    } else if ("sql".equals(code_gubun)) {
	    	
	    	// obj_id 에 해당하는 sql 문 조회
	    	parmObj.put("obj_id",    obj_id);
	    	EgovMap objMap = contentService.selectObjSqlList(parmObj);
	    	log.info("#################### objMap : " + String.valueOf(objMap.get("objSql")).replaceAll("[\r\n]",""));
	    	
	    	parmObj.put("cdGun",   "sql");
	    	parmObj.put("sql",     objMap.get("objSql"));
	    }
	    
	    parmObj.put("val2",     code_value);

		List<EgovMap> jsonData = contentService.selectDynamicDataList(parmObj);
				
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rows",   jsonData);
		returnMap.put("obj_id", obj_id);
		returnMap.put("grid_id", grid_id);
		returnMap.put("form_id", form_id);
		returnMap.put("code_id", code_id);
        
        return returnMap;
	}
	
	/**
	 * 그리드 편집 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/selectGridEditInfoList.json")
    public @ResponseBody Map<String, Object> selectGridEditInfoList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap parmObj =  new HashMap();
		
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));
		log.info("#################### 그리드 편집 정보조회(scrin_code) : " + scrin_code.replaceAll("[\r\n]",""));
		
		parmObj.put("scrin_code",    scrin_code);
		
		List<EgovMap> jsonData = contentService.selectGridEditInfoList(parmObj);
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		returnMap.put("rows",   jsonData);	
        
        return returnMap;
		
	}
	
	/**
	 * 일괄변경 처리를 저장한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/content/saveBndeApplc.do")
    public String saveBndeApplc(Model model, @RequestParam(value="xmlId") String[] xmlId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        int    saveCount  = 0;
		String resultMsg  = "";
       	String errorMsg   = "";       	
       	String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
       	HashMap paramObj =  new HashMap();
		
		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){		    
		    String key = en.nextElement().toString();
			String val = request.getParameter(key);    			
			
		    paramObj.put(key, val);		    
		}
		paramObj.put("reg_id", reg_id);  // 등록자ID
		paramObj.put("upd_id", upd_id);  // 수정자ID
		
       	try {
       		
       		for (int i=0; i<xmlId.length; i++) {
       			String[] xmlIdSpl = xmlId[i].split(":");
       			String sType  = xmlIdSpl[0];  // I : insert, U : update, D : delete 구분
       			String sXmlId = xmlIdSpl[1];  // xml_id
       			
       			paramObj.put("type", sType);
       			paramObj.put("xmlId", sXmlId);
       			
       			saveCount += contentService.saveBndeApplc(paramObj);       		
       		}
       		
       		if (saveCount > 0) {
       			resultMsg = "SAVE_SUCCESS2";
       		} else {
       			resultMsg = "ERROR";
       		}
       		
       	} catch(Exception e) {    		
       		resultMsg = "ERROR";
       		errorMsg = "등록 오류 발생";
       	}
       	
       	String OID = "";
    	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
       	
       	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg  : " + errorMsg.replaceAll("[\r\n]","")); 
       	log.info("처리후 호출 함수(callBackFunction) : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	
		return  "/unibill/com/cmm/commonMsg";
	}
	
	/**
	 * 고객 비밀번호를 검증한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/getPassVrify.json")
    public @ResponseBody Map<String, Object> getPassVrify(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap paramObj =  new HashMap();
		
		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){		    
		    String key = en.nextElement().toString();
			String val = request.getParameter(key);
		    
		    paramObj.put(key, val);
		}
		
		String result       = "true";
		String msg          = "";
		String targetPasswd = (String) paramObj.get("pass");
		String userId       = (String) paramObj.get("user_id");
		
		List<EgovMap> PassRule_jsonData = contentService.selectPassRule(null);	 

		// 2. 몇회 이전 비밀번호 사용 검증
		LoginVO loginVO = new LoginVO();
	    loginVO.setUserId(userId);
	    loginVO.setNewPass(targetPasswd);
	    
		EgovMap m = mainService.checkPreviousPassword(loginVO);
		
		int previousPasswordCnt = 0; // 몇회 이전 비밀번호는 다시 사용하지 못하게하는 정책에 따른 값.
		
		Map<String, Object> vriPass = ContentUtil.vriPass(userId, PassRule_jsonData, targetPasswd);
        if(vriPass != null && result.equals("true") && Integer.valueOf(vriPass.get("failflag").toString()) != 0) {
        	result = "false";
			msg = "비밀번호는 최소 " + PassRule_jsonData.get(0).get("minLen").toString() +"자리 "
	    			+ "최대 "+ PassRule_jsonData.get(0).get("maxLen").toString() +"자리 이며 " 
	    			+ "순차문자는  " + PassRule_jsonData.get(0).get("seqLen").toString() +"자리 "
	    			+ "반복문자는  " + PassRule_jsonData.get(0).get("sameLen").toString() +"자리 제한됩니다 " 
	    			+ "주민번호는 포함되지 않아야 하며 "
	    			;
			msg = msg + StringUtil.isNullToString(vriPass.get("msg").toString()) + "필요합니다.";
    	}

        Map<String , Object> returnMap = new HashMap<String , Object>();				
		returnMap.put("result", result);
		returnMap.put("msg",    msg);

        return returnMap;
	}
	
	/**
	 * 암호화 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/getPassEncpt.json")
    public @ResponseBody Map<String, Object> getPassEncpt(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CryptoUtils cryptoTest = new CryptoUtils();
		
		HashMap paramObj =  new HashMap();
		
		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){		    
		    String key = en.nextElement().toString();
			String val = request.getParameter(key);
		    
		    paramObj.put(key, val);
		}

		String pass = (String) paramObj.get("pass");
		
		Map<String , Object> returnMap = new HashMap<String , Object>();				
		returnMap.put("encptData", cryptoTest.encryptSHA256Hex(pass));
		
		log.info("#################### [암호화 : " + cryptoTest.encryptSHA256Hex(pass).replaceAll("[\r\n]","") + "]");

        return returnMap;
        
	}
	
	@RequestMapping(value="/content/selectContentAjaxList.do")
    public String selectContentAjaxList(ModelMap model, HttpServletRequest request, HttpSession session) throws Exception {
		// 세션 사용자정보
				LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
						
				String formView   = "";
//				String userid = StringUtil.isNullToString(request.getParameter("userid"));			// userid
				String objid      = StringUtil.isNullToString(request.getParameter("objid"));       // 객체id
				String menu_code  = StringUtil.isNullToString(request.getParameter("menu_code"));   // 메뉴id
				String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
				String formName   = StringUtil.isNullToString(request.getParameter("formid"));      // form name
				if ("".equals(formName)) {
					formName = "frm_main";
				}
				
				HashMap<String, String> parameterObject =  new HashMap<String, String>();
				
				parameterObject.put("menu_code",  menu_code);	         // 메뉴id
				parameterObject.put("role_code",  loginVO.getRoleId());  // 권한id
				parameterObject.put("user_id",  loginVO.getUserId());  // 권한id
				
				
				if ("".equals(objid)) {
								
					// 메뉴 및 화면정보 조회
					String scrinId       = "";
					String scrinId2      = "";
					String menuNm        = "";
					String tblNm         = "";
					String gridhg        = "";
					String tmpType       = "";
					String dtlWidth      = "";
					String dtlTmpType    = "";
					String xmlId         = "";
					String scrinSqlYn    = "";
					String extFun        = "";
					String dblclickYn    = "";
					String dblclickFun   = "";
					String clickFun      = "";
					String gridMakeFun   = "";
					String mainGridSelYn = "";
					String hdYn 		 = "";
					String sumYn 		 = "";
					
			    	List<EgovMap> menuInfo = contentService.selectMenuInfo(parameterObject);
			    	
			    	
			    	
			    	for (int i=0; i<menuInfo.size(); i++) {

			    		scrinId       = (String) menuInfo.get(i).get("scrinId");
			    		menuNm        = (String) menuInfo.get(i).get("menuNm");
			    		tblNm         = (String) menuInfo.get(i).get("tblNm");
			    		gridhg        = String.valueOf(menuInfo.get(i).get("gridhg"));
			    		dtlTmpType    = String.valueOf(menuInfo.get(i).get("dtlTmpType"));
			    		tmpType       = (String) menuInfo.get(i).get("tmpType");
		    			scrinId2      = (String) menuInfo.get(i).get("scrinId2");      // 탭메뉴의 첫번째 화면ID
		    			xmlId         = (String) menuInfo.get(i).get("xmlId");         // xml_id
		    			scrinSqlYn    = (String) menuInfo.get(i).get("scrinSqlYn");    // 화면에서 sql사용여부
		    			extFun        = (String) menuInfo.get(i).get("extFun");        // 화면open 시 실행함수
		    			dblclickYn    = (String) menuInfo.get(i).get("dblclickYn");    // 그리드더블클릭여부    			
		    			dblclickFun   = (String) menuInfo.get(i).get("dblclickFun");   // 그리드더블클릭함수
		    			clickFun      = (String) menuInfo.get(i).get("clickFun");      // 그리드클릭함수
		    			gridMakeFun   = (String) menuInfo.get(i).get("gridMakeFun");   // 그리드생성함수
		    			mainGridSelYn = (String) menuInfo.get(i).get("mainGridSelYn"); // 상단그리드선택여부
		    			hdYn		  = (String) menuInfo.get(i).get("hdYn"); 		   // 가변컬럼선택여부
		    			sumYn		  = (String) menuInfo.get(i).get("sumYn"); 		   // 엑셀합계표현여부
			    	
			    		if ("".equals(dtlWidth)) {
			    			dtlWidth = (String) menuInfo.get(i).get("scrinId") + "," + menuInfo.get(i).get("dtlW");
			    		} else {
			    			dtlWidth += "^|^" + (String) menuInfo.get(i).get("scrinId") + "," + menuInfo.get(i).get("dtlW");
			    		}
			    	}	    	
			    	
			    	model.addAttribute("scrin_code",     scrinId);              // 상단 기본화면의 화면ID
			    	model.addAttribute("scrin_code2",    scrinId2);             // 하단 화면의 화면ID (예, 탭메뉴의 첫번째 화면ID)
			    	model.addAttribute("menu_nm",        menuNm);               // 메뉴명
			    	model.addAttribute("tableName",      tblNm);                // 테이블명	    	
			    	model.addAttribute("gridhg",         gridhg);               // 그리드높이
			    	model.addAttribute("tptype",         tmpType);              // 템플릿타입   
			    	model.addAttribute("dtlWidth",       dtlWidth);             // 화면id별 상세화면 width	    	
			    	model.addAttribute("dtlTmpType",     dtlTmpType);           // 상세화면 템플릿구분(I:개별등록, M:개별등록 및 일괄등록)
			    	model.addAttribute("xmlId",          xmlId);                // xml_id
			    	model.addAttribute("scrinSqlYn",     scrinSqlYn);           // 화면sql 사용여부
			    	model.addAttribute("roleCode",       loginVO.getRoleId());  // 권한ID
			    	model.addAttribute("userId",         loginVO.getUserId());  // 사용자ID
			    	model.addAttribute("userNm",         loginVO.getUserNm());  // 사용자명
			    	model.addAttribute("extFun",         extFun);               // 실행함수	    	
			    	model.addAttribute("dblclickYn",     dblclickYn);           // 그리드더블클릭여부	    	
			    	model.addAttribute("dblclickFun",    dblclickFun);          // 그리드더블클릭함수
			    	model.addAttribute("clickFun",       clickFun);             // 그리드클릭함수
			    	model.addAttribute("gridMakeFun",    gridMakeFun);          // 그리드생성함수
			    	model.addAttribute("mainGridSelYn",  mainGridSelYn);        // 상단그리드선택여부
			    	model.addAttribute("hdYn",  		 hdYn);					// 가변컬럼선택여부
			    	model.addAttribute("sumYn",  		 sumYn);				// 엑셀합계표현여부
			    	
			    	// 화면 안내문구 조회
			    	List<EgovMap> selectGuidList = contentService.selectGuidList(parameterObject);
			    	
			    	List<EgovMap> guidList = new ArrayList<EgovMap>();
			    	
			    	for (int i = 0; i<selectGuidList.size(); i++) {
			    		
			    		EgovMap map = new EgovMap();
			    		
			    		map.put("pos_type",  selectGuidList.get(i).get("posType"));    		    	
			    		map.put("sn",        selectGuidList.get(i).get("sn"));
			    		map.put("txt",       selectGuidList.get(i).get("txt"));
			    		
			    		guidList.add(map);
			    	}
			    	model.addAttribute("guidInfo",   guidList);
			    		    	
			    	parameterObject.put("scrin_code", scrinId); // 화면id
			    	parameterObject.put("show_yn", "2");        // 그리드출력옵션
			    	
			    	// 그리드 정보 조회
			    	String colNameList  = "";
			    	String colModelList = "";
			    	String colWidth     = "";
			    	String colAlign     = "";
			    	String colShowType  = "";
			    	
			    	List<EgovMap> selectColumnsList = contentService.selectColumnsList(parameterObject);	    	
			    	session.removeAttribute("selectColumnsList");
			    	session.setAttribute("selectColumnsList", selectColumnsList);
			    	
			    	for (int i=0; i<selectColumnsList.size(); i++) {
			    		
			    		String sGubun = "";    		    		
			    		if (i < selectColumnsList.size()-1) sGubun = "|";
			    		
			    		String colName   = (String) selectColumnsList.get(i).get("objNm");
			    		String colModel  = (String) selectColumnsList.get(i).get("colId");
			    		String colwidth  = (String) StringUtil.isNullToString(selectColumnsList.get(i).get("cellW"));
			    		String colalign  = (String) selectColumnsList.get(i).get("cellSort");	    		
			    		String showType  = (String) selectColumnsList.get(i).get("showYn");
			    		
			    		colNameList  += colName  + sGubun;
			    		colModelList += colModel + sGubun;
			    		colWidth     += colwidth + sGubun;
			    		colAlign     += colalign + sGubun;
			    		colShowType  += showType + sGubun;
			    	}
			    		    	
			    	model.addAttribute("colNameList",  colNameList);               // 그리드 name
			    	model.addAttribute("colModelList", colModelList);              // 그리드 model
			    	model.addAttribute("colWidth",     colWidth);                  // 그리드 셀 width
			    	model.addAttribute("colAlign",     colAlign);                  // 그리드 셀 정렬
			    	model.addAttribute("colShowType",  colShowType);               // 그리드 show 타입	
			    	
			    	// 하단 탭메뉴 정보 조회	    	
			    	if ("C".equals(tmpType)) {
			    		
			    		// 탭메뉴 정보
			    		parameterObject.put("role_code", loginVO.getRoleId());  // 권한id
			    		List<EgovMap> selectTabList = contentService.selectTabList(parameterObject);
			    		
			    		List<EgovMap> tabList = new ArrayList<EgovMap>();
			    		
			    		for (int j=0; j<selectTabList.size(); j++) {
				    		EgovMap tabMap = new EgovMap();
					    		
					    	tabMap.put("menuId",  selectTabList.get(j).get("menuId"));    		    	
					    	tabMap.put("tabId",   selectTabList.get(j).get("tabId"));
					    	tabMap.put("tabNm",   selectTabList.get(j).get("tabNm"));
					    	tabMap.put("tblNm",   selectTabList.get(j).get("tblNm"));
					    		
					    	tabList.add(tabMap);
			    			
				    	}
			    	
			    		model.addAttribute("tabList",   tabList);
			    			    		
			    	}
			    	
			    	// 상단화면 및 탭메뉴 하단에 해당되는 버튼 정보 조회
		    		List<EgovMap> selectTabBtnList = contentService.selectTabBtnList(parameterObject);
		    		model.addAttribute("tabBtnList",   selectTabBtnList);

			    	formView = "unibill_tiles/content/contentList";
			    	
				} else {
					
					formView = "unibill/content/contentSubForm";
				
					parameterObject.put("scrin_code",   scrin_code); // 화면id
				}
		    	
		    	// 검색 조건 정보 조회		
		    	parameterObject.put("obj_id",       objid);      // 객체ID
		    	parameterObject.put("find_show_yn", "Y");        // 검색조건 출력여부
		    	
		    	String owner = fileUploadProperties.getProperty("Globals.UserName");		
				parameterObject.put("owner", owner);
		    	List<EgovMap> selectSearchList = contentService.selectSearchObjectList(parameterObject);
		    	
		    	List<EgovMap> searchList = new ArrayList<EgovMap>(); 
		    	
		    	int idNum = 0;
		    	for (int i = 0; i<selectSearchList.size(); i++) {
		    		
		    		EgovMap map = new EgovMap();

		    		map.put("objid",        selectSearchList.get(i).get("objId"));
		    		map.put("pobjid",       selectSearchList.get(i).get("upObjId"));
		    		map.put("name",         selectSearchList.get(i).get("objNm"));
		    		map.put("pcolid",       selectSearchList.get(i).get("upColId"));    		
		    		map.put("objpos",       selectSearchList.get(i).get("objPos"));
		    		map.put("objtype",      selectSearchList.get(i).get("objType"));
		    		map.put("width",        selectSearchList.get(i).get("findItemW"));
		    		map.put("colpos",       selectSearchList.get(i).get("colPos"));
		    		map.put("rowpos",       selectSearchList.get(i).get("rowPos"));
		    		map.put("xmlid",        selectSearchList.get(i).get("xmlId"));
		    		map.put("grpcd",        selectSearchList.get(i).get("grpCd"));      		
		    		map.put("grptype",      selectSearchList.get(i).get("grpType"));
		    		map.put("subformyn",    selectSearchList.get(i).get("subformyn"));    		
		    		map.put("maxxpos",      selectSearchList.get(i).get("maxxpos"));    
		    		map.put("colspan",      selectSearchList.get(i).get("dtlRowMgr"));
		    		map.put("objwidth",     selectSearchList.get(i).get("w"));    
		    		map.put("dayfindyn",    selectSearchList.get(i).get("dayFindYn"));
		    		map.put("sql",          selectSearchList.get(i).get("objSql"));         // fwobj sql문
		    		map.put("fwformobjsql", selectSearchList.get(i).get("fwformObjSql"));   // fwform sql문
		    		map.put("autogencol",   selectSearchList.get(i).get("autoGenCol"));
		    		map.put("mustyn",       selectSearchList.get(i).get("mustYn"));
		    		map.put("extFun",       selectSearchList.get(i).get("extFun"));
		    		map.put("findType",     selectSearchList.get(i).get("findType"));
		    		map.put("maxlen",       selectSearchList.get(i).get("maxLen"));
		    		map.put("findIdSql",    selectSearchList.get(i).get("findIdSql"));
		    		map.put("formname",     formName);
		    		
		    		String colid = (String) selectSearchList.get(i).get("colId");
		    		
		    		if (colid.contains("_tm")) {  // 등록일시, 수정일시
		    			
		    			colid = idNum + "_" + colid;
		    			idNum += 1;
		    			
		    		} 
		    		/*
		    		else if (colid.indexOf("_yn") > -1) {  // 코드여부일 경우 xmlid로 코드 조회
		    			
		    			HashMap<String, String> parmObj =  new HashMap<String, String>();
		    			
		    			parmObj.put("cdGun",   "xml_id");
		    			parmObj.put("xmlid",   "selectYnCode");

		    			map.put("dynData", ContentUtil.dynData(parmObj, (String) map.get("objtype"), ""));
		    			
		    		} 
		    		*/   		
		    		map.put("colid",     colid);
		    		
		    		// 기준정보 및 기초코드(공통코드) 조회(콤보, 체크박스, 라디오버튼 등)
		    		if (!map.get("xmlid").equals("") || !map.get("grpcd").equals("") || !map.get("grptype").equals("") || !map.get("sql").equals("") || !map.get("fwformobjsql").equals("") ) {
		    			map.put("dynData", ContentUtil.RequestDynDataMap((String) map.get("grptype"), (String) map.get("grpcd"), (String) map.get("xmlid"), (String) map.get("sql"), (String) map.get("objtype"), (String) map.get("fwformobjsql"), parameterObject));
		    		}    		    			
		    		else if (colid.indexOf("_yn") > -1) {  // 코드여부일 경우 xmlid로 코드 조회
		    			
		    			HashMap<String, String> parmObj =  new HashMap<String, String>();
		    			
		    			parmObj.put("cdGun",   "xml_id");
		    			parmObj.put("xmlid",   "selectYnCode");

		    			map.put("dynData", ContentUtil.dynData(parmObj, (String) map.get("objtype"), ""));
		    		} 
		    		searchList.add(map);	    		
		    	}    	
		    	model.addAttribute("searchInfo",       searchList);    	
		    	model.addAttribute("menu_code",        menu_code);
		    	model.addAttribute("now_date",         DateUtil.getCurrentDateString("yyyy-MM-dd"));
		    	model.addAttribute("auto_search_yn",   fileUploadProperties.getProperty("Globals.AutoSearchYn"));
		
		
		
		log.info(formView);
		
    	return "unibill/content/contentList_test";	 
		
	}
	
	/**
	 * 공통 컨텐츠 일괄등록(엑셀 업로드) 처리 한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/excelUploadContent.do")
    public String excelUploadContent(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));  // 테이블명
        String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));      // xml_id
        String scrinCode  = StringUtil.isNullToString(request.getParameter("scrin_code"));  // scrin_id
        String dtltmpdel  = StringUtil.isNullToString(request.getParameter("dtl_tmp_del"));  // scrin_id
        System.out.println("dtltmpdel : " + dtltmpdel);
        String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
        String resultMsg    = "";
       	String errorMsg     = "";
       	
        int excel_sheet = StringUtil.strToInt(request.getParameter("excel_sheet")); // 엑셀시트
        
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile files = (MultipartFile) multiRequest.getFile("userfile2"); // 업로드파일정보 추출
        
        
        
        String fileName = files.getOriginalFilename();  // 파일명
		long fileSize   = files.getSize();              // 파일사이즈
		
		
		
   		int index = fileName.lastIndexOf('.');
   		String fileExt = fileName.substring(index + 1); // 확장자 추출
   		
   		
   		String newFile  = FileMngUtil.newFileName() + "." + fileExt;
   		String ErrorFile_name = "ERROR_"+ newFile; 		// new 파일명
   		
   		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		Properties props = new Properties();
		props.load(in);
		
		HashMap<String, String> parmObjs =  new HashMap<String, String>();
		
		// 타이틀 조회
		parmObjs.put("cdGun",   "grp_cd");
	    parmObjs.put("grp_cd",   "UNIBILLCONFIG");
	    parmObjs.put("dtl_cd", "UNIBILL_ADDRDRM");
	    	
		List<EgovMap> codeList = contentService.selectDynamicDataList(parmObjs);
		
		String addrDRM = ""; 
		if (codeList.size() > 0) {
			addrDRM = String.valueOf(codeList.get(0).get("code"));
		}
			
		if(addrDRM == null || addrDRM.equals("")) {
			addrDRM = "N";
		}
		
		if("Y".equals(addrDRM)) {
			String path = "";
			
			if(File.separator.equals("\\")){
				path = props.getProperty("WINsystem.uploadpath") + File.separator + "content_upload";
			}else{
				path = props.getProperty("system.uploadpath") + File.separator + "content_upload";
			}
			
			File convFile = new File(path + File.separator + fileName);
			files.transferTo(convFile);
			
			//원본 파일 업로드
	   		fileService.fileInsert(request, "content_upload", newFile, "userfile2");  // 파라메터 <HttpServletRequest>, <파일올라갈 위치>, <파일명>
			
	   		try {
				//DRM 테스트
//				log.info("DRM 테스트 시작");
				drm drm = new drm();
//				log.info("DRM DEC 시작");
				int drms = drm.decDRM(path + File.separator + fileName, path + File.separator + newFile);
				
				if(drms == 0) {
//					log.info("DRM DEC 완료");
				}else if(drms == -36) {
					log.info("암호화 문서 아님");
				}else {
					throw new Exception("에러코드: " + drms);
				}
				
			} catch (Exception e) {
//				log.info("DRM DEC 에러");
				errorMsg  =  e.toString().replace("java.lang.Exception:", "") + ", 파일 복호화에 실패하였습니다.";
				HashMap fileUploadProcessRs = new HashMap<>();
	   			fileUploadProcessRs.put("excelTotCnt", 0);
	   			fileUploadProcessRs.put("successCnt", 0);
	   			fileUploadProcessRs.put("failCnt", 1);
	   			
				resultMsg = "SAVE_PROCESS";
				String OID = StringUtil.isNullToString(fileUploadProcessRs.get("excelTotCnt"))+"|"+StringUtil.isNullToString(fileUploadProcessRs.get("successCnt"))+"|"+StringUtil.isNullToString(fileUploadProcessRs.get("failCnt"))+"|"+ ErrorFile_name;
		   		model.addAttribute("OID", OID);
		   		
		     	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
		   		model.addAttribute("resultMsg", resultMsg);
		    	model.addAttribute("errorMsg",  errorMsg);
		   		return  "/unibill/com/cmm/commonMsg";
			}
		}
   		
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
		
		parameterObject.put("dtltmpdel",       dtltmpdel);   // dtltmpdel
		
		
		List<EgovMap> keyCol = contentService.selectKeyColList(parameterObject);			// key 컬럼정보 조회
//    	parameterObject.put("keyCol", contentService.selectKeyColList(parameterObject));
    	
    	List<EgovMap> dbHeader = contentService.getFwformExcelheader(parameterObject);		// HEADER 컬럼 정보 조회
   		
       	HashMap fileUploadProcessRs = null; 												//확장자 체크와 엑셀 업로드
   		
    	if(fileExt.equals("xlsx") || fileExt.equals("xls")){
   			fileUploadProcessRs= FileUploadContent.excelUpLoadProcess(excelFile, excel_sheet, fileExt, parameterObject, dbHeader, keyCol, "com", transactionManager, ErrorFile_name);
    		resultMsg = "SAVE_PROCESS";
   	   		errorMsg  = (String)fileUploadProcessRs.get("errorMsg");
   	   		
   	   		if(Integer.valueOf(fileUploadProcessRs.get("successCnt").toString()) > 0 ){
	   			parameterObject.put("REG_ID",          reg_id);      // 등록자id
	   			saveContentHis( parameterObject,  tableName,  "",  request, "EE" , null );
	   		}
   	   		
   		}else{
   			fileUploadProcessRs = new HashMap();
   			fileUploadProcessRs.put("excelTotCnt", 0);
   			fileUploadProcessRs.put("successCnt", 0);
   			fileUploadProcessRs.put("failCnt", 0);
   			
			resultMsg = "SAVE_PROCESS";
			errorMsg  = "엑셀(xlsx, xls)만 업로드 가능 합니다.";
			
   		}
   		
   		String OID = StringUtil.isNullToString(fileUploadProcessRs.get("excelTotCnt"))+"|"+StringUtil.isNullToString(fileUploadProcessRs.get("successCnt"))+"|"+StringUtil.isNullToString(fileUploadProcessRs.get("failCnt"))+"|"+ ErrorFile_name;
   		model.addAttribute("OID", OID);
   		
     	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
     	
   		
   		model.addAttribute("resultMsg", resultMsg);
    	model.addAttribute("errorMsg",  errorMsg);
    	
//    	if(inputFile.exists()) {
//    		inputFile.delete();
//    	}
     	
   		return  "/unibill/com/cmm/commonMsg";
        
     
	}
	
	@RequestMapping(value = "/content/selectCdrDetail.do")
	public String sheetList(Model model, HttpServletRequest request) throws Exception {
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
		HashMap<String, String> rawParam =  new HashMap<String, String>();

		rawParam.put("smonth", request.getParameter("smonth"));
		rawParam.put("scdrseq", request.getParameter("scdrseq"));
		
		List<EgovMap> rRs = new ArrayList<EgovMap>();
		List<EgovMap> selectRawList = new ArrayList<EgovMap>();
		
		try {
			selectRawList = contentService.getRawInfo(rawParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(selectRawList.size() != 0){
			HashMap<String, String> rawNmParam =  new HashMap<String, String>();
			rawNmParam.put("pbxtype", selectRawList.get(0).get("pbxType").toString());
			
			List<EgovMap> selectRawNm = contentService.getRawNm(rawNmParam);
			
			String[] cdr = new String[selectRawNm.size()];
			if(selectRawList.get(0).get("cdr") == null || selectRawList.get(0).get("cdr").equals("")){
				for(int i=0; i<selectRawList.size(); i++){
					cdr[i] = "";
				}
			}else{
				cdr = StringUtil.isNullToString(selectRawList.get(0).get("cdr")).split(",");
			}
			//원시자료
			
			for(int i=0; i<selectRawNm.size(); i++){
				EgovMap rawRs = new EgovMap();
				String val="&nbsp;";
				try{
					if(cdr[i] != null && !cdr[i].equals("")) val = cdr[i];
				}catch(Exception e){}
				rawRs.put("rawNm", StringUtil.isNullToString(selectRawNm.get(i).get("colNm")));
				rawRs.put("rawCdr", val);
				rRs.add(rawRs);
			}
			//한글체크 패턴
			String krCheck = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
			
			model.addAttribute("roleid", loginVO.getRoleId());
			model.addAttribute("smonth", request.getParameter("smonth"));
			model.addAttribute("scdrseq", request.getParameter("scdrseq"));
			model.addAttribute("cdr", selectRawList.get(0));
			model.addAttribute("rawCdr", rRs);
			model.addAttribute("irtNonmkr", StringUtil.isNullToString(selectRawList.get(0).get("irtNonm")).matches(krCheck) == true? "kr" : "en");
			model.addAttribute("ortNonmkr", StringUtil.isNullToString(selectRawList.get(0).get("ortNonm")).matches(krCheck) == true? "kr" : "en");
			model.addAttribute("connTypenmkr", StringUtil.isNullToString(selectRawList.get(0).get("connTypenm")).matches(krCheck) == true? "kr" : "en");
			
		}
		
		
		return "unibill_pop/pop/cdrDetailPop";
		
		
	}
	
	@RequestMapping(value = "/content/recdr.do")
	public String recdr(Model model, HttpServletRequest request) throws Exception {
		HashMap<String, String> rawParam =  new HashMap<String, String>();

		String smonth = request.getParameter("smonth");
		String scdrseq = request.getParameter("scdrseq");
		
		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		Properties props = new Properties();
		props.load(in);
		
		String cmdPath = props.getProperty("Globals.serverBinPath");
		
		in.close();
		
		if(scdrseq != null && !"".equals(scdrseq)){
			try{
				String[] cmd = null; 
				Process process = null;
				
				//리눅스시스템이면 프로세스를 실행하고 아니면 경고 출력
				String command = "hsrcdr -m " + smonth +  " -r 1 -1 " + scdrseq + " -mc 100";
				//String command = "pwd";
				if(File.separator.equals("/")){
					cmd   = new String[]{"/bin/sh", "-c", cmdPath + File.separator + command};
					process= new ProcessBuilder(cmd).start();
					 
				 }
				
				// 스캐너클래스를 사용해 InputStream을 스캔함
				InputStream is = null;
				is = process.getInputStream();
				Scanner s = new Scanner(is);
				ArrayList<String> msg = new ArrayList<String>();
				while (s.hasNextLine() == true) {
					msg.add(s.nextLine()+"\n");
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		rawParam.put("smonth", request.getParameter("smonth"));
		rawParam.put("scdrseq", request.getParameter("scdrseq"));
		
		List<EgovMap> rRs = new ArrayList<EgovMap>();
		List<EgovMap> selectRawList = new ArrayList<EgovMap>();
		
		try {
			selectRawList = contentService.getRawInfo(rawParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(selectRawList.size() != 0){
			HashMap<String, String> rawNmParam =  new HashMap<String, String>();
			rawNmParam.put("pbxtype", selectRawList.get(0).get("pbxType").toString());
			
			List<EgovMap> selectRawNm = contentService.getRawNm(rawNmParam);
			
			
			String[] cdr = new String[selectRawNm.size()];
			
			if(selectRawList.get(0).get("cdr") == null || selectRawList.get(0).get("cdr").equals("")){
				for(int i=0; i<selectRawList.size(); i++){
					cdr[i] = "";
				}
			}else{
				cdr = selectRawList.get(0).get("cdr").toString().split(",");
			}
			
			//원시자료
			
			for(int i=0; i<selectRawNm.size(); i++){
				EgovMap rawRs = new EgovMap();
				String val="&nbsp;";
				try{
					if(cdr[i] != null && !cdr[i].equals("")) val = cdr[i];
				}catch(Exception e){}
				rawRs.put("rawNm", selectRawNm.get(i).get("colNm").toString());
				rawRs.put("rawCdr", val);
				rRs.add(rawRs);
			}
			
			//한글체크 패턴
			String krCheck = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
			
			model.addAttribute("smonth", request.getParameter("smonth"));
			model.addAttribute("scdrseq", request.getParameter("scdrseq"));
			
			model.addAttribute("cdr", selectRawList.get(0));
			model.addAttribute("rawCdr", rRs);
			
			model.addAttribute("irtNonmkr", selectRawList.get(0).get("irtNonm").toString().matches(krCheck) == true? "kr" : "en");
			model.addAttribute("ortNonmkr", selectRawList.get(0).get("ortNonm").toString().matches(krCheck) == true? "kr" : "en");
			model.addAttribute("connTypenmkr", selectRawList.get(0).get("connTypenm").toString().matches(krCheck) == true? "kr" : "en");
			
		}
		
		return "unibill_pop/pop/cdrDetailPop";
		
		
	}
	
	@RequestMapping(value = "/content/selectRawDetail.do")
	public String rawList(Model model, HttpServletRequest request) throws Exception {
		HashMap hiscolParam =  new HashMap();
		hiscolParam.put("billYm", request.getParameter("billYm"));
		hiscolParam.put("sheetSn", request.getParameter("sheetSn").substring(request.getParameter("sheetSn").lastIndexOf("(")+1, request.getParameter("sheetSn").lastIndexOf(")")) );
		hiscolParam.put("rowno", request.getParameter("rowno"));
		
		List<EgovMap> rRs = new ArrayList<EgovMap>();
		List<EgovMap> selectHiscolList = new ArrayList<EgovMap>();
		List<EgovMap> selectDataList = new ArrayList<EgovMap>();
		List<String> dataList = null;
		
		try {
			//헤더 정보
			selectHiscolList = contentService.getHiscolInfo(hiscolParam);
			//실제 데이터정보
			selectDataList = contentService.getRawDataList(hiscolParam);
			dataList = new ArrayList<String>(Arrays.asList(selectDataList.get(0).get("val").toString().split(selectDataList.get(0).get("deli").toString())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(selectHiscolList.size() <= dataList.size()){
			for(int i=0; i<selectHiscolList.size(); i++){
				EgovMap colRs = new EgovMap();
				String val="&nbsp;";
				try{
					if(selectHiscolList.get(i) != null && !StringUtil.isNullToString(selectHiscolList.get(i)).equals("")) {
						val = dataList.get(i);
					}
				}catch(Exception e){}
				colRs.put("colNm", selectHiscolList.get(i).get("colNm").toString());
				colRs.put("colVal", val);
				log.info("colNm : " + selectHiscolList.get(i).get("colNm").toString() + ", colVal : " + val);
				rRs.add(colRs);
			}
			
			
			
			
		}else if(selectHiscolList.size() > dataList.size()){
			for(int i=0; i<selectHiscolList.size(); i++){
				EgovMap colRs = new EgovMap();
				String val="&nbsp;";
				
				if(i < dataList.size()){
					try{
						if(selectHiscolList.get(i) != null && !StringUtil.isNullToString(selectHiscolList.get(i)).equals("")) val = dataList.get(i);
					}catch(Exception e){}
					colRs.put("colNm", selectHiscolList.get(i).get("colNm").toString());
					colRs.put("colVal", val);
					rRs.add(colRs);
					
				}else{
					colRs.put("colNm", selectHiscolList.get(i).get("colNm").toString());
					colRs.put("colVal", val);
					rRs.add(colRs);
					
				}
				
			}
		}
		
		model.addAttribute("dataList", rRs);
		
		return "unibill_pop/pop/rawDetailPop";
		
		
	}
	
	
	/**
	 * 가변컬럼헤더이름을 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/getVariableHDColumnList.json")
    public @ResponseBody Map<String, Object> getVariableHDColumnList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
				
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
		String xmlId      = StringUtil.isNullToString(request.getParameter("xmlId"));       // xml_id
		String scrinSqlYn = StringUtil.isNullToString(request.getParameter("scrinSqlYn"));  // 화면sql사용여부
		String tptype     = StringUtil.isNullToString(request.getParameter("tptype"));      // 템플릿타입
		String hdYn       = StringUtil.isNullToString(request.getParameter("hdYn"));      	// 가변컬럼사용여부
		log.info("########## /content/selectContentList.json ########## [scrin_code : " + scrin_code.replaceAll("[\r\n]","") + "][xmlId : " + xmlId.replaceAll("[\r\n]","") + "][tptype : " + tptype.replaceAll("[\r\n]","") + "][scrinSqlYn : " + scrinSqlYn.replaceAll("[\r\n]","") + "][hdYn : " + hdYn.replaceAll("[\r\n]","") + "]" );
		
    	HashMap parameterObject =  new HashMap();
    	
    	parameterObject.put("scrin_code",  scrin_code);             // 메뉴ID
    	parameterObject.put("role_code",   loginVO.getRoleId());    // 권한ID
    	parameterObject.put("user_id",     loginVO.getUserId());    // 로그인ID
    	parameterObject.put("dept_grp_id", loginVO.getDeptGrpId()); // 부서코드 
    	
    	List selectFixHdList = new ArrayList<EgovMap>();
    	List selectAvableHdList = new ArrayList<EgovMap>();
    	
    	// 인자값을 출력해서 'term' parameter를 확인
    	Enumeration en = request.getParameterNames();
    		while(en.hasMoreElements()){    			
    			String key = en.nextElement().toString();
    			String val = request.getParameter(key);    			
    		    log.info("############# xml id 검색 [key : " + key.replaceAll("[\r\n]","") + "][val : " + val.replaceAll("[\r\n]","") + "]");
    		    
    		    parameterObject.put(key,  val);
    	}
    	log.info("#### 가변컬럼 ####");		
    	
    	// 고정컬럼리스트 조회
    	selectFixHdList = contentService.selectFixHDList(parameterObject);
    	
		// 가변컬럼리스트 조회
    	selectAvableHdList = contentService.selectHdList(parameterObject, xmlId);
    	log.info("#### 조회 완료 ####");
	    ArrayList<Map> str2 = new ArrayList<Map>();
	    log.info("#### 가변컬럼헤더 계산 ####");
	    if(selectFixHdList.size() > 0 && selectAvableHdList.size() > 0){
			// 고정
			for(int i=0; i<selectFixHdList.size(); i++){
				HashMap<String, String> list = new HashMap<String, String>();
				list.put("colId", ((EgovMap)selectFixHdList.get(i)).get("colId").toString());
				list.put("colCamelId", CamelUtil.convert2CamelCase( ((EgovMap)selectFixHdList.get(i)).get("colId").toString() ));
				list.put("colNm", ((EgovMap)selectFixHdList.get(i)).get("colNm").toString());
				str2.add(list);
			}
			// 가변
			for(int i=0; i<selectAvableHdList.size(); i++){
				HashMap<String, String> list = new HashMap<String, String>();
				list.put("colId", "c"+i);
				list.put("colCamelId", "c"+i);
				list.put("colNm", ((EgovMap)selectAvableHdList.get(i)).get("hdnm").toString());
				str2.add(list);
				
			}
		}
		log.info("#### 가변컬럼헤더 계산결과 ####");
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		returnMap.put("collist", str2);
		
        return returnMap;
    }
	
	
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value="/content/derestrict.do")
    public String PrcCfgUserBtn(String wflag, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 세션 사용자정보 조회
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
        
        String resultMsg  = "";
       	String errorMsg   = "";       	
       	String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));  // 화면id
       	String tableName  = StringUtil.isNullToString(request.getParameter("tableName"));   // 테이블명        	            	
       	String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag"));  // 처리 상태
       	String ubseq      = StringUtil.isNullToString(request.getParameter("ubseq"));       // 자료순번
       	String ubSeqArr   = StringUtil.isNullToString(request.getParameter("ubSeqArr"));    // 삭제대상 데이터
       
    	String extCallBackFunction = StringUtil.isNullToString(request.getParameter("extCallBackFunction"));
       	String reg_id     = loginVO.getUserId();
       	String upd_id     = loginVO.getUserId();
       	
       	String[] s2 = ubSeqArr.split(",");
		String r ="";
		for(int i=0; i<s2.length; i++){
			if(i < s2.length-1 ){
				r += "TO_NUMBER(" + s2[i] + "),";
			}else{
				r += "TO_NUMBER(" + s2[i] + ")";
			}
		}
       	
       	List<EgovMap> keyColList = new ArrayList<EgovMap>();
       	       	       	
       	HashMap parameterObject =  new HashMap();

		// 인자값을 출력해서 'term' parameter를 확인
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key = en.nextElement().toString();
			String val = request.getParameter(key);
			parameterObject.put(key,  val);
		}

		// 저장 컬럼 조회
		parameterObject.put("role_code",    loginVO.getRoleId());  // 권한id 
		//버튼 구분
		if(wflag.equals("R")) parameterObject.put("reset",  "Y" );
       	else if(wflag.equals("N")) parameterObject.put("notuse",  "Y");
       	else return "";
		List<EgovMap> columnvalue = new ArrayList<EgovMap>();
    	try {

    		String sResultMsg = "";
           	String sErrorMsg  = "";           	           	

    		if (actionFlag.equals("UPDATE")) {
    			
    			EgovMap info  = contentService.updateDerestrict(parameterObject);
    			//사용자 관리 화면일시 이력
    			if(scrin_code.equals("S0019")){
    				
    				parameterObject.put("dubseq", ubseq);
    				EgovMap cfguser = contentService.selectcfguser(parameterObject);
    				parameterObject.put("reg_id", reg_id);         // 등록자id
    		        parameterObject.put("upd_id", upd_id);         // 수정자id
    				parameterObject.put("workType", "U");
    				parameterObject.put("user_id", cfguser.get("userId"));
    				parameterObject.put("user_nm", cfguser.get("userNm"));
    				parameterObject.put("use_yn", cfguser.get("useYn"));
    				parameterObject.put("role_id", cfguser.get("roleId"));
    				parameterObject.put("ipchk_yn", cfguser.get("ipchkYn"));
    				parameterObject.put("tel_no", cfguser.get("telNo"));
    				parameterObject.put("mobile", cfguser.get("mobile"));
    				parameterObject.put("email", cfguser.get("email"));
    				parameterObject.put("pass", cfguser.get("pass"));
    				parameterObject.put("failcnt", cfguser.get("failcnt"));
    					
    				contentService.insertHisuser(parameterObject);
    				
    			}
    			
    			sResultMsg = (String) info.get("resultMsg");
	            sErrorMsg  = (String) info.get("errorMsg");	            
	            
	            parameterObject.put("masterTableName", tableName);   // 테이블명 (key 컬럼 조회용)
	            
	            // 테이블의 key 컬럼 정보 조회
            	keyColList = contentService.selectKeyColList(parameterObject);
            	
	            if ("true".equals(sResultMsg)) {
	            	
	            	resultMsg = "UPDATE_SUCCESS";
	            	
	            } else if ("false".equals(sResultMsg)) {

	            	if ("".equals(sErrorMsg)) {
	            		resultMsg = "ERROR";
	            	} else {
	            		resultMsg = "SQL_ERROR";
	            		errorMsg  = "SQL 에러 입니다 ==> " + sErrorMsg.substring(0, sErrorMsg.length()-1).replace("\'", "").replaceAll("\\r|\\n", " ");
	            	}
	            }
	            
	            // 테이블 값 조회
	            columnvalue = contentService.selectDerestrictResultList(parameterObject);

    		} 

    	} catch(Exception e) {    		
       		resultMsg = "ERROR";
       		errorMsg = "등록 오류 발생";
       	}
    	
    	// key 컬럼 추출
    	String keyCol = "";		
    	if (keyColList.size() > 0) {
			for (int i=0; i<keyColList.size(); i++) {

				if (i == 0) {
					keyCol = (String) keyColList.get(i).get("keyCol");
				} else {
					keyCol += "@" + (String) keyColList.get(i).get("keyCol");
				}

			}
    	}
    	
    	// 상세화면 변경값 반영
    	String changeColumList = columnvalue.get(0).get("failcnt").toString() + "@" + columnvalue.get(0).get("lastloginDt").toString();  
    	

    	String OID = scrin_code + "|" + ubseq + "|" + actionFlag + "|" + keyCol + "|" + extCallBackFunction + "|" + changeColumList;
    	model.addAttribute("OID", OID);
    	model.addAttribute("resultMsg", resultMsg);
       	model.addAttribute("errorMsg",  errorMsg);
       	model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));	// 처리후 호출 함수
       	model.addAttribute("columnValue", columnvalue.get(0));
       	
       	log.info("OID       : " + OID.replaceAll("[\r\n]",""));
       	log.info("resultMsg : " + resultMsg.replaceAll("[\r\n]",""));
       	log.info("errorMsg  : " + errorMsg.replaceAll("[\r\n]","")); 
       	log.info("처리후 호출 함수  (callBackFunction)    : " + StringUtil.isNullToString(request.getParameter("callBackFunction")).replaceAll("[\r\n]",""));
       	log.info("처리후 호출 함수 2(extCallBackFunction) : " + StringUtil.isNullToString(request.getParameter("extCallBackFunction")).replaceAll("[\r\n]",""));
       	
		return  "/unibill/com/cmm/commonMsg";
		
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/derestrict.do")
    public String derestrict(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		return PrcCfgUserBtn("R", model,request,response); 
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/content/notuse.do")
    public String notuse(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception { 
		return PrcCfgUserBtn("N", model,request,response);
	}

	
	/**
	 * 즐겨찾가 정보를 조회한다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectPassRule.do")
    public @ResponseBody Map<String, Object> selectPassRule(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {				
		HashMap parameterObject =  new HashMap();				        
        // 조회 컬럼 항목
        List<EgovMap> jsonData = null;  
        jsonData = contentService.selectPassRule(parameterObject);
		Map<String , Object> returnMap = new HashMap<String , Object>();		
		returnMap.put("rows", jsonData);
        
        return returnMap;  
	}
	
	
	/**
	 * 파일시퀀스를 가지고 온다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectFileSeq.json")
    public @ResponseBody Map<String, Object> selectFileseq(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap parmObj =  new HashMap();	
		EgovMap objMap = new EgovMap();
		Map<String , Object> returnMap = new HashMap<String , Object>();
                		
		try{
			objMap = contentService.selectFileSeq(parmObj);
			returnMap.put("result", true);
			returnMap.put("fileSn", objMap.get("fileSn"));
			log.info("#################### objMap : " + String.valueOf(objMap.get("fileSn")).replaceAll("[\r\n]",""));
			
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("result", false);
		}
		
        return returnMap;
	}
	
	/**
	 * 파일시퀀스를 가지고 온다.
	 * @param model
	 * @return ""
	 * @exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/getFileGrp.json")
    public @ResponseBody Map<String, Object> getFileGrp(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		HashMap parmObj =  new HashMap();	
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		parmObj.put("scrin_code", request.getParameter("scrin_code"));
		EgovMap objMap = new EgovMap();
		
		try {
			objMap = contentService.selectFileGRP(parmObj);
			returnMap.put("result", true);
			returnMap.put("filegrp", objMap.get("fileGrp"));
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("result", false);
		}
		
		return returnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/selectFileList.json")
	public @ResponseBody Map<String, Object> selectFileList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap parameterObject =  new HashMap();
		
		parameterObject.put("sfilesn", StringUtil.isNullToString(request.getParameter("s_filesn")));
		parameterObject.put("sfilenm", StringUtil.isNullToString(request.getParameter("s_filenm")));
		
		parameterObject.put("filegrp", StringUtil.isNullToString(request.getParameter("filegrp")));
		parameterObject.put("odno", StringUtil.isNullToString(request.getParameter("od_no")));
		parameterObject.put("contseq", StringUtil.isNullToString(request.getParameter("cont_seq")));
		parameterObject.put("filesn", StringUtil.isNullToString(request.getParameter("file_sn")));
		
		
		List<EgovMap> jsonData = contentService.selectFileList(parameterObject);
		
		int total_count = contentService.selectFileListCnt(parameterObject);
        
        // 페이징 처리
      	int page = StringUtil.strToInt(request.getParameter("page"));
    	int rows = StringUtil.strToInt(request.getParameter("rows"));
    	int firstIndex = rows * page - rows; 
    	
    	parameterObject.put("firstIndex", firstIndex);
	    parameterObject.put("lastIndex", firstIndex+rows);
	    
    	// 정렬순서 (필수)
	    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
    	String sord = StringUtil.isNullToString(request.getParameter("sord"));
    	
    	if (!sidx.equals("")) {
    		parameterObject.put("OrderByColumn", sidx);
    	}
		if (!sord.equals("")) {
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
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/delFile.json")
    public @ResponseBody Map<String, Object> delFile(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filegrp = StringUtil.isNullToString(request.getParameter("filegrp"));
		String[] filesns = request.getParameterValues("filesns");
		HashMap parmObj =  new HashMap();	
		EgovMap objMap = new EgovMap();
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		//파일 정보
		parmObj.put("filegrp", filegrp);
		parmObj.put("filesns", filesns);
		//log.info("#################### filegrp = "+filegrp + " filesns : " + filesns);
		
		if(filegrp.equals("O")){ 
    			contentService.deleteOdfile(parmObj);
    			contentService.deleteContfile(parmObj);
		}
		else if (filegrp.equals("C")){ 
			contentService.deleteContfile(parmObj);    
		}
		returnMap.put("dbDelResult", "true");
		returnMap.put("delResult", "none"); 
		
        return returnMap;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/delFile2.json")
    public @ResponseBody Map<String, Object> delFile2(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filegrp = StringUtil.isNullToString(request.getParameter("filegrp"));
		String[] filesns = request.getParameterValues("filesns");
		HashMap parmObj =  new HashMap();	
		EgovMap objMap = new EgovMap();
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		//파일 정보
		parmObj.put("filegrp", filegrp);
		parmObj.put("filesns", filesns);
		
		
		
		List<EgovMap> cmfileInfo = contentService.getcmfileinfo(parmObj);
		for(int i=0; i<cmfileInfo.size(); i++){
			String fullpath = cmfileInfo.get(0).get("filePath").toString() + File.separator + cmfileInfo.get(0).get("nm").toString();
			parmObj.put("filesn", cmfileInfo.get(i).get("fileSn"));
			
			//db delete
        	if(filegrp.equals("O")){
        		int contcnt = contentService.selectContfile(parmObj);
        		if(contcnt <= 0 ){ //mb_cfgconf_file 에 파일이 존재 하지 않을 때 삭제
        			contentService.deleteOdfile(parmObj);        			
        			returnMap.put("dbDelResult", "true");
        			returnMap.put("delResult", "none");
        			
        			
        		}else{
        			returnMap.put("dbDelResult", "false");
        		}
        		
        	}else if(filegrp.equals("C")){
        		contentService.deleteContfile(parmObj); 
    			//파일 삭제
    			File file = new File(fullpath);
    			
    			if( file.exists() ){
    	            if(file.delete()){
    	            	
    	                returnMap.put("delResult", "sucess");
    	            }else{
    	            	returnMap.put("delResult", "fail");
    	            }
    	        }else{
    	        	returnMap.put("delResult", "none");
    	        }
             
        	//	contentService.deletecmfile(parmObj);
        		
        		
        	}else if(filegrp.equals("N")){
    			//파일 삭제
    			File file = new File(fullpath);
    			
    			if( file.exists() ){
    	            if(file.delete()){
    	            	
    	                returnMap.put("delResult", "sucess");
    	            }else{
    	            	returnMap.put("delResult", "fail");
    	            }
    	        }else{
    	        	returnMap.put("delResult", "none");
    	        }
        		
        	}
        	
			
			


	
		}
		
		
        return returnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/content/getinterval.json")
    public @ResponseBody Map<String, Object> getinterval(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		contentService.getinterval();
		
		returnMap.put("interval", true);


		
		
		

		
    	
        return returnMap;
	}
	
	
	@RequestMapping(value = "/content/initNotice.do")
	public String initDBill(ModelMap model) throws Exception {
		//공지사항		
		String formView = "unibill_tiles/notice/noticeList";
		
	return formView;
	}
	
	@RequestMapping(value = "/content/writeNotice.do")
	public String writeNotice(ModelMap model) throws Exception {
		//공지사항	글쓰기 	
		String formView = "unibill_tiles/notice/noticeWrite";
		
	return formView;
	}
	
	@RequestMapping(value = "/content/selectNoticePop.do")
	public String selectNoticePop(Model model, HttpServletRequest request) throws Exception {
		HashMap parmObj =  new HashMap();	
		
		parmObj.put("ubseq" ,StringUtil.isNullToString(request.getParameter("ubseq")));
		EgovMap imgFileInfo = contentService.getImgubseqInfo(parmObj);
		
		//이미지 크기 구하기
		BufferedImage bi = ImageIO.read(new File(imgFileInfo.get("filePath").toString()+ File.separator + imgFileInfo.get("nm").toString()));
		
		model.addAttribute("imgwidth", bi.getWidth());
		model.addAttribute("imgheight", bi.getHeight());
		
		model.addAttribute("nubseq", StringUtil.isNullToString(request.getParameter("ubseq")));
		
		String test = imgFileInfo.get("filePath").toString();
		String localDrive = fileUploadProperties.getProperty("system.ldriveLoaction");
		if("/".equals(File.separator)){
			model.addAttribute("imgwidth", bi.getWidth());
			model.addAttribute("imgheight", bi.getHeight() + 20);
			model.addAttribute("imgfile", imgFileInfo.get("filePath").toString().substring(imgFileInfo.get("filePath").toString().lastIndexOf("noticeimg")-1)+ File.separator + imgFileInfo.get("nm").toString());
		}else{
			model.addAttribute("imgwidth", bi.getWidth());
			model.addAttribute("imgheight", bi.getHeight());
			model.addAttribute("imgfile",  localDrive + imgFileInfo.get("filePath").toString().substring(imgFileInfo.get("filePath").toString().lastIndexOf("noticeimg")-1)+ File.separator + imgFileInfo.get("nm").toString());
		}
		model.addAttribute("popupYn", imgFileInfo.get("filePath").toString()+ File.separator + imgFileInfo.get("nm").toString());
		
		
		return "unibill_pop/notice/noticePop";
		
		
	}
	
	@RequestMapping(value = "/content/checkext.do")
	public @ResponseBody Map<String, Object> uploadFlag() throws Exception {
		Map<String , Object> data = new HashMap<String , Object>();
		HashMap parmObj =  new HashMap();	
		List<EgovMap> rExt = contentService.getExt(parmObj);
		
		
		data.put("reqStatus", rExt);
		return data;
		
		
	}
	
	@RequestMapping(value="/content/noticeSearch.json")
	public @ResponseBody Map<String, Object> noticeSearch(HttpServletRequest request) throws Exception {
		log.debug("################### ");	
		String notice_type  = request.getParameter("notice_type");
		String s_notice  = request.getParameter("s_notice");
		String s_page  = StringUtil.isNullToString(request.getParameter("s_page"));
		
		log.debug("############## notice param : " + notice_type.replaceAll("[\r\n]",""));
		log.debug("############## notice param : " + notice_type.replaceAll("[\r\n]","") + ", " + s_notice.replaceAll("[\r\n]","") + ", " + s_page.replaceAll("[\r\n]",""));
		
		HashMap parmObj =  new HashMap();	
		parmObj.put("notice_type", notice_type);
		parmObj.put("s_notice", s_notice);
		
		int nCnt = contentService.getNoticeListCnt(parmObj);
		//mysql page
		int allPage = 0; 
		if(nCnt % 10 == 0){
			allPage = nCnt / 10;
		}else{
			allPage = nCnt / 10 + 1;
		}
		
		int startPage = 0;
		int endPage = 0;
		if(StringUtil.strToInt(s_page) % 10 == 0){
			startPage = StringUtil.strToInt(s_page) - 9 ;
			endPage = StringUtil.strToInt(s_page);
		}else{
			startPage = (StringUtil.strToInt(s_page) / 10) * 10 + 1;
			if(startPage/10 == allPage/10){
				endPage = allPage;
			}else{
				endPage = startPage + 9;
			}
			
		}
		
		
		int offsetSize = (StringUtil.strToInt(s_page) - 1) * 10;
		parmObj.put("offsetSize", offsetSize);  //limit 사용시 개수
		parmObj.put("limitSize", 10);  //limit 사용시 개수
		
		List<EgovMap> nList = contentService.getNoticeList(parmObj);
		
		Map<String , Object> data = new HashMap<String , Object>();
		data.put("sucess", true);
		data.put("curPage", s_page);
		data.put("nCnt", nCnt);
		data.put("allPage", allPage);
		data.put("startPage", startPage);
		data.put("endPage", endPage);
		data.put("nList", nList);
		
		return data;
	}
	
	@RequestMapping(value="/content/selectNoticeDetail.json")
	public @ResponseBody Map<String, Object> selectNoticeDetail(HttpServletRequest request) throws Exception {
		log.debug("################### ");	
		String ubseq  = StringUtil.isNullToString(request.getParameter("ubseq"));
		
		log.debug("############## notice param : " + ubseq.replaceAll("[\r\n]",""));
		
		HashMap parmObj =  new HashMap();	
		parmObj.put("ubseq", ubseq);
		
		EgovMap noticeDetailInfo = contentService.selectNoticeDetail(parmObj);
		
		Map<String , Object> data = new HashMap<String , Object>();
		data.put("sucess", true);
		data.put("nDetailInfo", noticeDetailInfo);
		
		return data;
	}
	
	
	
	@RequestMapping(value = "/content/initFAQ.do")
	public String initFAQ(ModelMap model) throws Exception {
		//공지사항		
		String formView = "unibill_tiles/faq/faqList";
		
	return formView;
	}
	
	@RequestMapping(value="/content/faqSearch.json")
	public @ResponseBody Map<String, Object> faqSearch(HttpServletRequest request) throws Exception {
		log.debug("################### ");	
		String faq_type  = request.getParameter("faq_type");
		String s_faq  = request.getParameter("s_faq");
		String s_page  = StringUtil.isNullToString(request.getParameter("s_page"));
		
		log.debug("############## notice param : " + faq_type.replaceAll("[\r\n]","") + ", " + s_faq.replaceAll("[\r\n]","") + ", " + s_page.replaceAll("[\r\n]",""));
		
		HashMap parmObj =  new HashMap();	
		parmObj.put("faq_type", faq_type);
		parmObj.put("s_faq", s_faq);
		
		int nCnt = contentService.getFAQListCnt(parmObj);
		//mysql page
		int allPage = 0; 
		if(nCnt % 10 == 0){
			allPage = nCnt / 10;
		}else{
			allPage = nCnt / 10 + 1;
		}
		
		int startPage = 0;
		int endPage = 0;
		if(StringUtil.strToInt(s_page) % 10 == 0){
			startPage = StringUtil.strToInt(s_page) - 9 ;
			endPage = StringUtil.strToInt(s_page);
		}else{
			startPage = (StringUtil.strToInt(s_page) / 10) * 10 + 1;
			if(startPage/10 == allPage/10){
				endPage = allPage;
			}else{
				endPage = startPage + 9;
			}
			
		}
		
		
		int offsetSize = (StringUtil.strToInt(s_page) - 1) * 10;
		parmObj.put("offsetSize", offsetSize);  //limit 사용시 개수
		parmObj.put("limitSize", 10);  //limit 사용시 개수
		
		List<EgovMap> nList = contentService.getFAQList(parmObj);
		
		Map<String , Object> data = new HashMap<String , Object>();
		data.put("sucess", true);
		data.put("curPage", s_page);
		data.put("nCnt", nCnt);
		data.put("allPage", allPage);
		data.put("startPage", startPage);
		data.put("endPage", endPage);
		data.put("nList", nList);
		
		return data;
	}
	
	@RequestMapping(value="/content/selectFAQDetail.json")
	public @ResponseBody Map<String, Object> selectFAQDetail(HttpServletRequest request) throws Exception {
		log.debug("################### ");	
		String ubseq  = StringUtil.isNullToString(request.getParameter("ubseq"));
		
		log.debug("############## notice param : " + ubseq.replaceAll("[\r\n]",""));
		
		HashMap parmObj =  new HashMap();	
		parmObj.put("ubseq", ubseq);
		
		EgovMap noticeDetailInfo = contentService.selectFAQDetail(parmObj);
		
		Map<String , Object> data = new HashMap<String , Object>();
		data.put("sucess", true);
		data.put("nDetailInfo", noticeDetailInfo);
		
		return data;
	}
	
	@RequestMapping(value = "/content/initSql.do")
	public String initSql(ModelMap model) throws Exception {
		//콤보 박스
		HashMap parameterObject =  new HashMap();
		List<EgovMap> conList = contentService.selectDataList(parameterObject, "content.conSnList", "B");
		
		model.addAttribute("conList",  		 	conList);
		String formView = "unibill_tiles/sql/sqlList";
		
	return formView;
	}
	
	@RequestMapping(value="/content/querycolumnList.json")
	public @ResponseBody Map<String, Object> querycolumnList(HttpServletRequest request) throws Exception {
		String exeQuery  = StringUtil.isNullToString(request.getParameter("exeQuery"));
		String connType  = StringUtil.isNullToString(request.getParameter("connType"));
		log.debug("querycolumnList param ==== [exeQuery : " + exeQuery.replaceAll("[\r\n]","") + ", connType : " + connType.replaceAll("[\r\n]","") + "]");
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
    	
    	List<String> exeQuerySplit =  Arrays.asList(exeQuery.split(";"));
    	exeQuery = exeQuerySplit.get(0).trim().replace(";", "");
    	HashMap rsList = new HashMap();  
		
		parmObj.put("exeQuery", exeQuery);
		parmObj.put("command", exeQuery.substring(0, 6).toLowerCase());
		parmObj.put("sqlType", "S");
		
		if(exeQuery.substring(0, 6).toLowerCase().equals("select")){
			parmObj.put("isExcel", "N");
			parmObj.put("page", 0);
			
			if(connType.equals("I")) {
				//내부디비 접속
				rsList = contentService.selectQuery(parmObj);
			}else { //원격디비접속
				/*//원격디비 접속정보 조회
				UCsyncExcute ucExe = new UCsyncExcute();
				ArrayList<HashMap> connInfoRs = ucExe.getConSnInfo(connType);
				//원격디비 접속
            	remotedbconn rdbconn = new remotedbconn(connInfoRs.get(0).get("con_type").toString(), connInfoRs.get(0).get("ip").toString(), connInfoRs.get(0).get("port").toString(), connInfoRs.get(0).get("db_nm").toString(), connInfoRs.get(0).get("userid").toString(), connInfoRs.get(0).get("passwd").toString() );
            	if(connInfoRs.get(0).get("con_type").toString().equals("0")){
            		exeQuery = "select * from (" + exeQuery + ") a where rownum = 1";
            	}else if(connInfoRs.get(0).get("con_type").toString().equals("1")) {
            		exeQuery = "select * from (" + exeQuery + ") a limit 1";
            	}
            	//원격지 테이블 조회
            	rsList = rdbconn.getSqlList(rdbconn, exeQuery);*/
            }
			
			
			int total_count = 0;
			int total_page = 0;

			data.put("sucess", true);
			if(rsList.get("errMsg") != null) {
				data.put("errMsg", rsList.get("errMsg"));
			}else {
				data.put("keyList", rsList.get("keyList"));
				data.put("keyCamelList", rsList.get("keyCamelList"));
			}
			
		}else {
			data.put("queryType", "ddl");
			rsList = contentService.ddlQuery(parmObj);
			data.put("ddlMsg", rsList.get("errMsg"));
			
			if(rsList.get("errMsg").toString().matches("(.*)[SUCESS](.*)")   ) {
				data.put("ddlcnt", rsList.get("ddlcnt"));
			}else {
				data.put("ddlcnt", 0);
			}
		}
		
		
		return data;
	}
	
	
	@RequestMapping(value="/content/queryResultList.json")
	public @ResponseBody Map<String, Object> queryResultList(HttpServletRequest request) throws Exception {
		String exeQuery  = StringUtil.isNullToString(request.getParameter("exeQuery"));
		String connType  = StringUtil.isNullToString(request.getParameter("connType"));
		log.debug("queryResultList param ==== [exeQuery : " + exeQuery.replaceAll("[\r\n]","") + ", connType : " + connType.replaceAll("[\r\n]","") + "]");
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
		
		List<String> exeQuerySplit =  Arrays.asList(exeQuery.split(";"));
    	exeQuery = exeQuerySplit.get(0).trim().replace(";", "");
		HashMap rsListCnt = new HashMap();
		HashMap rsList = new HashMap();  
		
		parmObj.put("exeQuery", exeQuery);
		parmObj.put("sqlType", "S");
		
		
		if(exeQuery.substring(0, 6).toLowerCase().equals("select")){
		   	// 페이징 처리      	
			int page = StringUtil.strToInt(request.getParameter("page"));
			if(page == 0) page = 1;

	    	int rows = StringUtil.strToInt(request.getParameter("rows"));
	    	if(rows == 0) rows = 20;
	    	int firstIndex = rows * page - rows;
	    	parmObj.put("page", page);
	    	parmObj.put("firstIndex", firstIndex);
	    	parmObj.put("lastIndex", firstIndex+rows);
	    	
			log.debug("############## page : " + page);
			log.debug("############## rows : " + rows);
	    	
	 	    // mysql
	 	    int offsetSize = (page - 1) * rows;
	 	    parmObj.put("offsetSize", offsetSize);  //limit 사용시 개수
	 	    parmObj.put("limitSize", rows);  //limit 사용시 개수
		    
	    	// 정렬순서 (필수)
		    String sidx = StringUtil.isNullToString(request.getParameter("sidx"));
	    	String sord = StringUtil.isNullToString(request.getParameter("sord"));
	    	
	    	if (!sidx.equals("")) {
	    		parmObj.put("OrderByColumn", sidx);
	    	}
			if (!sord.equals("")) {
				parmObj.put("OrderByType",sord);
			}
			
			parmObj.put("isExcel", "N");
			
			if(connType.equals("I")) {
				//내부디비 접속
				rsListCnt = contentService.selectQueryCnt(parmObj);
				rsList = contentService.selectQuery(parmObj);
			}else {
				/*//원격디비접속
				//원격디비 접속정보 조회
				UCsyncExcute ucExe = new UCsyncExcute();
				ArrayList<HashMap> connInfoRs = ucExe.getConSnInfo(connType);
				//원격디비 접속
            	remotedbconn rdbconnCnt = new remotedbconn(connInfoRs.get(0).get("con_type").toString(), connInfoRs.get(0).get("ip").toString(), connInfoRs.get(0).get("port").toString(), connInfoRs.get(0).get("db_nm").toString(), connInfoRs.get(0).get("userid").toString(), connInfoRs.get(0).get("passwd").toString() );
            	//원격지 테이블 조회
            	rsListCnt = rdbconnCnt.getSqlListCnt(rdbconnCnt,exeQuery);
            	remotedbconn rdbconn = new remotedbconn(connInfoRs.get(0).get("con_type").toString(), connInfoRs.get(0).get("ip").toString(), connInfoRs.get(0).get("port").toString(), connInfoRs.get(0).get("db_nm").toString(), connInfoRs.get(0).get("userid").toString(), connInfoRs.get(0).get("passwd").toString() );
            	rsList = rdbconn.getSqlList(rdbconn,exeQuery);*/
			}
			
			int total_count = 0;
			if(rsListCnt.get("errMsg") == null){
				total_count = Integer.valueOf(rsListCnt.get("total_count").toString());
			}
			int total_page = 0;
	        if (total_count > 0) {
	        	total_page = (int) Math.ceil( (float)total_count/(float)rows );
	        }
	        
	        data.put("queryType", "select");
			if(rsList.get("errMsg") != null) {
				data.put("errMsg", rsList.get("errMsg"));
			}else {
				data.put("keyList", rsList.get("keyList"));
				data.put("keyCamelList", rsList.get("keyCamelList"));
				data.put("rows", rsList.get("rsList"));
				
				data.put("page", page);
				data.put("total", total_page);
				data.put("records", total_count);
			}
			
		}

		
		return data;
	}
	
	
	@RequestMapping(value="/content/queryResultProcedure.json")
	public @ResponseBody Map<String, Object> queryResultProcedure(HttpServletRequest request) throws Exception {
		String exeQuery  = StringUtil.isNullToString(request.getParameter("exeQuery"));
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
		
		HashMap rsListCnt = new HashMap();
		HashMap rsList = new HashMap();  
		
		parmObj.put("exeQuery", exeQuery);
		parmObj.put("command", exeQuery.substring(0, 6).toLowerCase());
		parmObj.put("sqlType", "P");
		
		
		if(exeQuery.substring(0, 6).toLowerCase().equals("select")){
			data.put("queryType", "select");
			data.put("procedureMsg", "프로시저및 함수 생성쿼리가 아닙니다.");
		}else {
			data.put("queryType", "ddl");
			rsList = contentService.ddlQuery(parmObj);
			data.put("ddlMsg", rsList.get("errMsg"));
			
			if(rsList.get("errMsg").toString().matches("(.*)[SUCESS](.*)")   ) {
				data.put("ddlcnt", rsList.get("ddlcnt"));
			}else {
				data.put("ddlcnt", 0);
			}
			
			if(rsList.get("errMsg") != null) {
				data.put("sucess", false);
				data.put("procedureMsg", rsList.get("errMsg"));
				data.put("sucessMsg", "false");
			}else {
				data.put("sucess", true);
				data.put("sucessMsg", "true");
			}
		}

		
		return data;
	}
	
	
	
	@RequestMapping(value="/content/queryResultExcel.json")
	public @ResponseBody Map<String, Object> queryResultExcel(HttpServletRequest request) throws Exception {
		String exeQuery  = StringUtil.isNullToString(request.getParameter("exeQuery"));
		HashMap parmObj =  new HashMap();
		List<String> exeQuerySplit =  Arrays.asList(exeQuery.split(";"));
    	exeQuery = exeQuerySplit.get(0).trim().replace(";", "");
		parmObj.put("exeQuery", exeQuery);
		parmObj.put("sqlType", "E");
		
		HashMap rsList = new HashMap();
		 Map<String , Object> data = new HashMap<String , Object>();
		
		if(exeQuery.substring(0, 6).toLowerCase().equals("select")){
			parmObj.put("isExcel", "N");
			parmObj.put("page", 0);
			
			rsList = contentService.selectQuery(parmObj);
			//엑셀다운로드
			ExcelDownLoadContent filePath = new ExcelDownLoadContent();
	        String newfileName = "SQL조회_" + FileMngUtil.newFileName() + ".xlsx";
	        String uploadPath  = "";
	        String fullName    = "";
	        
	        if(File.separator.equals("\\")){
	          uploadPath  = filePath.filePaths + "\\" + "excel_download";
	          fullName    = uploadPath + "\\" + newfileName;
	        }else{
	          uploadPath  = filePath.filePaths + File.separator + "excel_download";
	          fullName    = uploadPath + File.separator + newfileName;
	        }
	        
	        if(rsList.get("errMsg") == null){
	        	contentService.selectSQLDataExcelHandler((Object) parmObj, "content.exeQueryExcel", (List)rsList.get("colId"), newfileName, uploadPath, fullName, "SQL 조회");
		        
		        data.put("OID", newfileName);
	        }
	        
	        data.put("queryType", "select");
	        if(rsList.get("errMsg") != null) {
				data.put("sucess", false);
				data.put("excelMsg", rsList.get("errMsg"));
				data.put("sucessMsg", "false");
			}else {
				data.put("sucess", true);
				data.put("sucessMsg", "true");
			}
			
	        
		}else {
			data.put("queryType", "ddl");
			data.put("excelMsg", "엑셀다운로드는 조회 쿼리만 가능합니다.");
		}
		
		return data;
	}

	//고객 ID 발부
	@RequestMapping(value="/content/getCustId.json")
	public @ResponseBody Map<String,String> getCustId(String val) throws Exception{
		String getCustId = KeyUtil.getCustId(val); 		
		Map<String,String> map = new HashMap<>();
		
		map.put("val", getCustId);
		
		return map;
	}
	
	//파일 순번 발부
	@RequestMapping(value="/content/getFilesn.json")
	public @ResponseBody Map<String,Object> getFilesn(String data, String tblName) throws Exception{
    	HashMap<String, Object> tparam =  new HashMap<String,Object>();
		tparam.put("data", data);
		tparam.put("tableName", tblName);
		
		String filesn = contentService.getFilesn(tparam);
		tparam.put("filesn", filesn);
		return tparam;
	}
	
	//파일이름 가져오기
	@RequestMapping(value="/content/getFilenm.json")
	public @ResponseBody Map<String,Object> getFilenm(String data, String tblName) throws Exception{
		HashMap<String, Object> tparam =  new HashMap<String,Object>();
		tparam.put("data", data);
		tparam.put("tableName", tblName);
		//가공 안된 filesn
		String filesn = contentService.getFilesn(tparam);
		tparam.put("filesn", filesn);
		String[] tfilesn = filesn.split(",");
		
		List filenmList = new ArrayList<>();
		for(int i = 0; i < tfilesn.length; i++) {
			tparam.put("file_sn", tfilesn[i]);
			String nm = contentService.getFilenm(tparam);
			filenmList.add(nm);
		}
		tparam.put("nm", filenmList);
		return tparam;
	}
	
	//파일 다운로드(blob)
	@RequestMapping(value="/content/getFileblob.json")
	public @ResponseBody Map<String,Object> getFileblob(String data, String tblName, int i) throws Exception{
		String subPath = "cfileupload";
		String uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;
				
		HashMap<String, Object> tparam =  new HashMap<String,Object>();
		tparam.put("data", data);
		tparam.put("tableName", tblName);
		//가공 안된 filesn
		String filesn = contentService.getFilesn(tparam);
		String[] tfilesn = filesn.split(",");
		
		try {
			tparam.put("file_sn", tfilesn[i]);
			HashMap<String,Object> content = contentService.getBlobfileInfo(tparam);
			byte[] contents = (byte[])content.get("file");
			String nm = contentService.getFilenm(tparam);
			tparam.put("nm", nm);
			FileUtils.writeByteArrayToFile(new File(uploadPath+ File.separator +nm), contents);
			tparam.put("tf", true);
		}catch(Exception e) {}
		return tparam;
	}

	//메일 보내기
	@RequestMapping(value="/content/mailSend.do")
	 public @ResponseBody HashMap<String, Object> mailSend(String data, String tblName) throws Exception {
		HashMap<String, String> parameterObject =  new HashMap<String, String>();
		parameterObject.put("mailsn", data);
		parameterObject.put("tableName", tblName);
		EgovMap mailInfo = contentService.selectmailInfo(parameterObject);
		
		EgovMap cfgmailInfo = contentService.selectcfgmailInfo();
		
		String host = (String) cfgmailInfo.get("ip");
    	int port = (int) cfgmailInfo.get("port");
    	String email = (String) cfgmailInfo.get("email");
    	String pw = (String) cfgmailInfo.get("emailpw");
    	String auth_yn = (String) cfgmailInfo.get("authYn");
		
		JavaMailSenderImpl getMailSender = new JavaMailSenderImpl();
		
        getMailSender.setHost(host);
        getMailSender.setPort(port);
        
        Properties javaMailProperties = new Properties();
        
        if("Y".equals(auth_yn)) {
        	javaMailProperties.put("mail.smtp.auth", "true");
        	javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.smtp.ssl.enable", "true");
            getMailSender.setUsername(email);
            getMailSender.setPassword(pw);
        }else if("N".equals(auth_yn)){
        	javaMailProperties.put("mail.smtp.auth", "false");
        	getMailSender.setUsername(null);
            getMailSender.setPassword(null);
        }
        
        javaMailProperties.put("mail.smtp.host", host);
        javaMailProperties.put("mail.smtp.port", port);
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        getMailSender.setJavaMailProperties(javaMailProperties);
		
		final String subject = (String) mailInfo.get("title"); // 메일 제목
		final String head = (String) mailInfo.get("head");
		final String content = (String) mailInfo.get("body"); // 메일 내용
		final String ref = (String) mailInfo.get("ref"); 
		final String tail = (String) mailInfo.get("tail");
		// 발신자
		final String from = (String) mailInfo.get("fromEmail"); 
		// 수신자
		final String to = (String) mailInfo.get("toEmail"); 
		
		final HashMap<String, Object> tparam =  new HashMap<String,Object>();
		tparam.put("data", data);
		tparam.put("tableName", tblName);
		
		try {
			final ArrayList fileInfoList = new ArrayList<>();
			final MimeMessagePreparator preparator = new MimeMessagePreparator() {

				public void prepare(MimeMessage mimeMessage) throws Exception {
					String subPath = "cfileupload";
					String uploadPath = "";
					if(subPath.contains("../")|| subPath.contains("/") || subPath.contains("\\")) {
						subPath = subPath.replace("../", "").replace("/", "").replace("//", "");
						throw new Exception("file error");
					}
					if(File.separator.equals("\\")){
						uploadPath = fileUploadProperties.getProperty("WINsystem.uploadpath")+"\\"+subPath;
					}else{
						uploadPath = fileUploadProperties.getProperty("system.uploadpath")+File.separator+subPath;
					}
					
					// 멀티파트 메세지 허용
					final MimeMessageHelper mailHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					// 발신자
					mailHelper.setFrom(from);
					// 수신자
					mailHelper.setTo(to);
					// 참조자
					if(!ref.equals("") && ref.length() > 1) {
						mailHelper.setCc(ref.trim());
					}
					// 메일 제목
					mailHelper.setSubject(subject);
					// 메일 내용(html 허용)
					if(content != null && !content.equals("")&& tail != null && !tail.equals("")) {
						mailHelper.setText(head+"<br/>"+content+"<br/>"+tail, true);
					}else if(content != null && !content.equals("")) {
						mailHelper.setText(head+"<br/>"+content, true);
					}else if(tail != null && !tail.equals("")) {
						mailHelper.setText(head+"<br/>"+tail, true);
					}
					tparam.put("tf", true);
					
					if(contentService.getFilesn(tparam) != null && !contentService.getFilesn(tparam).equals("")) {
						final String filesn = contentService.getFilesn(tparam);
						final String[] tfilesn = filesn.split(",");
						ArrayList fileInfos = new ArrayList<>();
						fileInfos.add(0,uploadPath);
						for(int i = 0; i < tfilesn.length; i++) {
							tparam.put("file_sn", tfilesn[i]);
							HashMap<String,Object> content = contentService.getBlobfileInfo(tparam);
							byte[] contents = (byte[])content.get("file");
							String nm = contentService.getFilenm(tparam);
							tparam.put("nm", nm);
							FileUtils.writeByteArrayToFile(new File(uploadPath+ File.separator +nm), contents);
							// 파일 경로
							FileSystemResource file = new FileSystemResource(new File(uploadPath+ File.separator +nm));
							fileInfos.add(i+1,nm);
							// 업로드 파일 이름.형식
							mailHelper.addAttachment(nm, file);
						}
						fileInfoList.addAll(fileInfos);
					}
				}
			};
			getMailSender.send(preparator);
			//전송된 파일 삭제
			for(int i = 1; i < fileInfoList.size(); i++) {
				File dFile = new File(fileInfoList.get(0).toString(), fileInfoList.get(i).toString());
				dFile.delete();
			}
		} catch (Exception e) {
			log.info("메일 전송 실패" + e.getMessage().replaceAll("[\r\n]",""));
			tparam.put("tf", false);
		}
		return tparam;
	}
	
	//담당자 정보 세팅
	@RequestMapping(value="/content/setManagerInfo.json")
	public @ResponseBody EgovMap setManagerInfo(String val, HttpServletRequest request) throws Exception{
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		HashMap<String,String> map = new HashMap<>();
		map.put("userid", loginVO.getUserId());
		map.put("val", val);
		EgovMap managerInfo = contentService.selectManagerInfo(map);
		EgovMap setFromEmail = contentService.selectFromEmail(map);
		managerInfo.putAll(setFromEmail);
		return managerInfo;
	}
	
	@RequestMapping(value="/content/genFun.json")
	public @ResponseBody Map<String, Object> genFun(HttpServletRequest request) throws Exception {
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
				
		String func  = StringUtil.isNullToString(request.getParameter("proc"));
		String userYn = StringUtil.isNullToString(request.getParameter("userYn"));
		String param  = StringUtil.isNullToString(request.getParameter("param"));
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
		
		log.info("func : " + func.replaceAll("[\r\n]",""));
		log.info("param : " + param.replaceAll("[\r\n]",""));
		log.info("userYn : " + userYn.replaceAll("[\r\n]",""));
		
		String[] params = param.split(",");
		
		if(!param.equals("")) {
			parmObj.put("params", params);
		}
		
		parmObj.put("func", func);
		parmObj.put("userYn", userYn);
		parmObj.put("userid", loginVO.getUserId());
		
		EgovMap rs = contentService.getfunc(parmObj);
		
		data.put("retval", rs.get("retval"));
		
		return data;
	}
	
	@RequestMapping(value="/content/genProc.json")
	public @ResponseBody Map<String, Object> genProc(HttpServletRequest request) throws Exception {
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
		String proc  = StringUtil.isNullToString(request.getParameter("proc"));
		String userYn = StringUtil.isNullToString(request.getParameter("userYn"));
		String param  = request.getParameter("param");
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
		
		String[] params = param.split("Ω",-1); //빈칸도 같이 처리 20211018 pjh
		
		if(!param.equals("")) {
			parmObj.put("params", params);
		}
		
		parmObj.put("proc", proc);
		parmObj.put("userYn", userYn);
		parmObj.put("userid", loginVO.getUserId());
		
		List<HashMap> rs = contentService.getproc(parmObj);
		HashMap retval = new HashMap();
		
		if(rs.get(0).size() == 1) {
			Iterator<String> it =  rs.get(0).keySet().iterator();
			while(it.hasNext()) {
				String keyNm = (String)it.next();
				retval.put("RESULT_MSG", rs.get(0).get(keyNm));
			}
		}else {
			retval = rs.get(0);
		}
		
		log.info("retval : " + String.valueOf(retval).replaceAll("[\r\n]",""));
		
		data.put("retval", retval);
		
		return data;
	}
	
	@RequestMapping(value="/content/genGridProc.json")
	public @ResponseBody Map<String, Object> genGridProc(HttpServletRequest request) throws Exception {
		// 세션 사용자정보
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		
		String proc  = StringUtil.isNullToString(request.getParameter("proc"));
		String userYn = StringUtil.isNullToString(request.getParameter("userYn"));
		String param  = request.getParameter("param");
		String rownumber  = request.getParameter("rownumber");
		
		HashMap parmObj =  new HashMap();
		Map<String , Object> data = new HashMap<String , Object>();
		
		String[] params = param.split("Ω",-1); //빈칸도 같이 처리 20211018 pjh
		String[] rownumbers = rownumber.split("Ω",-1);
		
//    	TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//        
//        log.info(String.format("TRANSACTION isCompleted:%s isNewTransaction:%s isRollbackOnly:%s", transactionStatus.isCompleted(), transactionStatus.isNewTransaction(), transactionStatus.isRollbackOnly()));
        
		HashMap retval = new HashMap();
		
		exit_For :
		for(int i=0; i<params.length; i++){
			if(!param.equals("")) {
				if(i==0) {
					String[] par = {params[i]};
					parmObj.put("params", par);
				}else {
					String[] par = {params[i], "test"};
					parmObj.put("params", par);
				}
				
				
				
			}
			
			parmObj.put("proc", proc);
			parmObj.put("userYn", userYn);
			parmObj.put("userid", loginVO.getUserId());
			
			List<HashMap> rs = contentService.getproc(parmObj);
			
			if(rs.get(0).size() == 1) {
				Iterator<String> it =  rs.get(0).keySet().iterator();
				while(it.hasNext()) {
					String keyNm = (String)it.next();
					retval.put("RESULT_MSG", rs.get(0).get(keyNm));
				}
			}else {
				retval = rs.get(0);
			}
			
			if(!retval.get("RESULT_MSG").equals("success")) {
				retval.put("ERR_NUMBER", rownumbers[i]);
				break exit_For;
			}
		}
		log.info("retval : " + String.valueOf(retval).replaceAll("[\r\n]",""));
		
		data.put("retval", retval);
		
		return data;
	}
	
	public boolean isGridMaskYn(String colid) throws Exception {
		if(colid.equals("s_no") || colid.equals("e_no") || colid.equals("rep_no")|| colid.equals("ocaller") || colid.equals("ocallee") || colid.equals("rdn") || colid.equals("fcallee") || colid.equals("ncaller") || colid.equals("ncallee")
				 || colid.contains("billno") || colid.contains("telno") 
		  ) {
			return true;
		}else {
			return false;
		}
	} 

		@Transactional
		@RequestMapping(value="/content/sendKpsa.do")
		 public @ResponseBody HashMap<String, Object> sendKpsa(String repCustID, String accym, String workDiv, String custCD) throws Exception {
			HashMap parameterObject = new HashMap();
			remoteIns ins = new remoteIns();
			if(!ins.isRuuning()) {
				log.info("ins 시작: ");
				ins.setQueryID("insertkspa");
				ins.setRdbType("tonghap");
				parameterObject.put("rep_cust_id", repCustID);
				parameterObject.put("acc_ym", accym);
				parameterObject.put("cust_cd", custCD);
				parameterObject.put("work_div", workDiv);
				ins.setXmlId("content.kpsaSend", parameterObject);
				ins.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
				ins.setMethod("satInsert");
				ins.start();
				ins.join();
				parameterObject.put("tf", true);
			}else {
				parameterObject.put("tf", false);
			}
			remoteIns ins2 = new remoteIns();
			if(!ins2.isRuuning()) {
				ins2.setQueryID("updateRKspa");
				ins2.setRdbType("tonghap");
				ins2.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
				ins2.setMethod("satUpdate");
				ins2.start();
				ins2.join();
				parameterObject.put("tf", true);
			}else {
				parameterObject.put("tf", false);
			}
			List<EgovMap> kpsaSend = contentService.kpsaSend(parameterObject);
			for(int i = 0; i < kpsaSend.size(); i++) {
				parameterObject.put("ubseq", kpsaSend.get(i).get("ubseq"));
				contentService.updateStatus(parameterObject);
			}
			return parameterObject;
		}
		
		@Transactional
		@RequestMapping(value="/content/sellidRegister.do")
		 public @ResponseBody HashMap<String, Object> sellidRegister(String srn, String jrn) throws Exception {
			HashMap parameterObject = new HashMap();
			remoteIns ins = new remoteIns();
			if(!ins.isRuuning()) {
				ins.setQueryID("insertCust");
				ins.setRdbType("tonghap");
				parameterObject.put("srn", srn);
				parameterObject.put("jrn", jrn);
				ins.setXmlId("content.sellidRegister", parameterObject);
				ins.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
				ins.setMethod("custInsert");
				ins.start();
				parameterObject.put("tf", true);
			}else {
				parameterObject.put("tf", false);
			}
			return parameterObject;
		}
		
		@Transactional
		@RequestMapping(value="/content/sendbungae.do")
		 public @ResponseBody HashMap<String, Object> sendbungae(String jurdat, String inempno, String jurno, String jurser) throws Exception {
			com.hsck.ubfw.component.remoteConn.db.remotedbconn remotedbconn = new com.hsck.ubfw.component.remoteConn.db.remotedbconn("spas");
			queryList queryList = new queryList();
			String query = queryList.getQueryList("selectglif");
			HashMap paramObj = remotedbconn.getSqlList(remotedbconn, query);
			ArrayList<HashMap> list = (ArrayList<HashMap>)paramObj.get("rsList");
			for(int i = 0; i < list.size(); i++) {
				paramObj.put("jurdat", list.get(i).get("JURDAT"));
				paramObj.put("inempno", list.get(i).get("INEMPNO"));
				paramObj.put("jurno", list.get(i).get("JURNO"));
				paramObj.put("jurser", list.get(i).get("JURSER"));
				contentService.updateglif(paramObj);
			}
			
			HashMap parameterObject = new HashMap();
			remoteIns ins = new remoteIns();
			if(!ins.isRuuning()) {
				ins.setQueryID("insertgl399t");
				ins.setRdbType("spas");
				parameterObject.put("jurdat", jurdat);
				parameterObject.put("inempno", inempno);
				parameterObject.put("jurno", jurno);
				parameterObject.put("jurser", jurser);
				ins.setXmlId("content.selectgl", parameterObject);
				ins.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
				ins.setMethod("insertgl");
				ins.start();
				ins.join();
				parameterObject.put("tf", true);
			}else {
				parameterObject.put("tf", false);
			}
			
			List<EgovMap> selectgl399t = contentService.selectgl(parameterObject);
			for(int i = 0; i < selectgl399t.size(); i++) {
				contentService.updategl(parameterObject);
			}
			
/*			remoteIns ins2 = new remoteIns();
			if(!ins2.isRuuning()) {
				ins2.setQueryID("inserttemptx201t");
				ins2.setRdbType("spas");
				parameterObject.put("jurdat", jurdat);
				parameterObject.put("inempno", inempno);
				parameterObject.put("jurno", jurno);
				parameterObject.put("jurser", jurser);
				parameterObject.put("adddate", "sysdate"); //pjh 20211223
				parameterObject.put("upddate", "sysdate"); //pjh 20211223
				ins2.setXmlId("content.selecttemp", parameterObject);
				ins2.setClassName("com.hsck.ubfw.component.remoteConn.ssg.insertExecute");
				ins2.setMethod("inserttemp");
				ins2.start();
				ins2.join();
				parameterObject.put("tf", true);
			}else {
				parameterObject.put("tf", false);
			}
			
			List<EgovMap> selecttemptx201t = contentService.selecttemp(parameterObject);
			for(int i = 0; i < selecttemptx201t.size(); i++) {
				contentService.updatetemp(parameterObject);
			}*/
			return parameterObject;
		}

		
}