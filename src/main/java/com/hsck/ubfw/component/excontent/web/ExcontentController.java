package com.hsck.ubfw.component.excontent.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.excontent.service.ExcontentService;
import com.hsck.ubfw.component.main.model.LoginVO;

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
public class ExcontentController  {
	private final Logger log = LoggerFactory.getLogger(this.getClass()); //logback
	
	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;
	
	/** ContentService */
	@Resource(name = "excontentService")
	private ExcontentService excontentService;
	
	//그룹 아이디 받기
	@RequestMapping(value="/excontent/getGrpType.json")
	public @ResponseBody Map<String,Object> getGrpType(String type) throws Exception{
    	HashMap<String, Object> tparam =  new HashMap<String,Object>();
    	//log.info("type: " + type);
		tparam.put("cdGun",   "grp_type");
		tparam.put("grp_type", type);
		
		List<EgovMap> typeList = contentService.selectDynamicDataList(tparam);
		tparam.put("typeList", typeList);
		//log.info("type: " + typeList.toString());
		return tparam;
	}
	
	//아아디 중복 확인
	@RequestMapping(value="/excontent/getId.json")
	public @ResponseBody Map<String,Object> getId(String id) throws Exception{
    	HashMap<String, Object> tparam =  new HashMap<String,Object>();
		tparam.put("user_id", id);
		
		int idCnt = excontentService.selectcfgIdCnt(tparam);
		if(idCnt == 0) {
			tparam.put("checkId", false);
			log.info("아이디 없음");
		}else if(idCnt > 0) {
			tparam.put("checkId", true);
			log.info("아이디 중복");
		}
		return tparam;
	}
	
	//권한신청 저장
	@RequestMapping(value="/excontent/cfgUserRegSave.do")
	public @ResponseBody Map<String,Object> cfgUserRegSave(@RequestParam HashMap sendData) throws Exception{
		HashMap<String, Object> tparam =  new HashMap<String,Object>();
		String user_id = (String) sendData.get("user_id");
		String pass = (String) sendData.get("pass");
		String reg_type = (String) sendData.get("reg_type");
		tparam.put("user_id", user_id);
		int idCnt = excontentService.selectcfgIdCnt(tparam);
		
		if(ContentUtil.IsPassLen(user_id, pass, 8, 10, 20) == false) {
			tparam.put("passtf", false);
			return tparam;
		}else if(idCnt != 0 && "U".equals(reg_type)) {
			try {
				excontentService.insertcfgUserReg(sendData);
				tparam.put("tf", true);
			}catch(DuplicateKeyException e) {
				tparam.put("tf", false);
				return tparam;
			}
		}else {
			try {
				excontentService.insertcfgUserReg(sendData);
				tparam.put("tf", true);
			}catch(DuplicateKeyException e) {
				tparam.put("tf", false);
				return tparam;
			}
		}
		return tparam;
	}
	
	//권한ID
	@RequestMapping(value="/excontent/getRoleId.json")
	public @ResponseBody Map<String,Object> getRoleId(HttpServletRequest request, HttpSession session) throws Exception{
    	HashMap<String, Object> tparam =  new HashMap<String,Object>();
    	LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		tparam.put("userId",loginVO.getUserId());
		
		String roleId = excontentService.selectRoleId(tparam);
		tparam.put("roleId", roleId);
		return tparam;
	}
	
	//아이디찾기
	@RequestMapping(value="/excontent/findId.do")
	public @ResponseBody Map<String,Object> findId(@RequestParam HashMap sendData) throws Exception{
		HashMap<String, Object> tparam =  new HashMap<String,Object>();
		String destine = (String) sendData.get("num");
		String role_id = (String) sendData.get("user_role");
		String branch = (String) sendData.get("branch");
		String name = (String) sendData.get("name");
		log.info(role_id);
		tparam.put("num", destine);
		tparam.put("role_id", role_id);
		tparam.put("branch", branch);
		tparam.put("name", name);
		
		String id = excontentService.selectId(tparam);
		String message = "아이디는 [" + id + "]입니다.";
		tparam.put("user_id", id);
		int idCnt = 0;
		try {
			idCnt = excontentService.selectcfgUserCnt(tparam);
		}catch(Exception e){
			tparam.put("checkId", false);
			return tparam;
		}
		
		tparam.put("userId", "user");
		tparam.put("destine", destine);
		tparam.put("barcode", 'Y');
		tparam.put("msgType", '3');
		tparam.put("subject", "[신세계아이앤씨]");
		tparam.put("message", message);
		
		if(idCnt == 0) {
			tparam.put("checkId", false);
		}else if(idCnt > 0) {
			excontentService.insertSendMMS(tparam);
			tparam.put("checkId", true);
		}
		return tparam;
	}
	
	//비밀번호찾기
	@RequestMapping(value="/excontent/findPw.do")
	public @ResponseBody Map<String,Object> findPw(@RequestParam HashMap sendData) throws Exception{
		HashMap<String, Object> tparam =  new HashMap<String,Object>();
		String user_id = (String) sendData.get("user_id");
		String num = (String) sendData.get("num");
		tparam.put("user_id", user_id);
		tparam.put("num", num);
		
		int idCnt = 0;
		try {
			idCnt = excontentService.selectcfgUserCntPw(tparam);
		}catch(Exception e){
			tparam.put("checkId", false);
			return tparam;
		}
		
		String pass = excontentService.selectPass(tparam);
		log.info(pass);
		String message = "비밀번호는 [" + pass + "]입니다.";
		tparam.put("userId", user_id);
		tparam.put("destine", num);
		tparam.put("barcode", 'Y');
		tparam.put("msgType", '3');
		tparam.put("subject", "[신세계아이앤씨]");
		tparam.put("message", message);
		if(idCnt == 0) {
			tparam.put("checkId", false);
		}else if(idCnt > 0) {
			excontentService.insertSendMMS(tparam);
			tparam.put("checkId", true);
		}
		return tparam;
	}
}