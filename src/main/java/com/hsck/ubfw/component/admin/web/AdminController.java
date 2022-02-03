package com.hsck.ubfw.component.admin.web;



import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.main.model.LoginVO;
import com.hsck.ubfw.component.main.service.MainService;

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
public class AdminController {

//	protected Log log = (Log) LogFactory.getLog(this.getClass());
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
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
	@RequestMapping(value="/admin/loginForm.do")
	public String loginForm(ModelMap model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		System.out.println("관리자 로그인 입니다.");
		return "redirect:/main/loginForm.do?pgauth=admin";
	}
	
	
	
}
