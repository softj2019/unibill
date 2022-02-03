package com.hsck.ubfw.component.com.interceptor;

import com.hsck.ubfw.component.comm.vo.LoginInfo;
import com.hsck.ubfw.component.main.model.Menu;
import com.hsck.ubfw.component.main.service.MainService;

import egovframework.com.cmm.service.Globals;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by LSW on 2016-12-27.
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    /** ContentService */
	@Resource(name = "mainService")
	private MainService mainService;
	

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception
    {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        String uri = request.getRequestURI().toLowerCase();

        String strCurrentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

//        LOGGER.debug("");
//        LOGGER.debug("#####################################################################################################");
//        LOGGER.debug("#### LoginInterceptor #### strCurrentUrl : " + strCurrentUrl + " ####");
        LOGGER.debug("#### LoginInterceptor #### CHECK URI : " + originalURL.replaceAll("[\r\n]","") + " ####");
//        LOGGER.debug("#### LoginInterceptor #### request.getRequestURI() : " + request.getRequestURI() + " ####");
//        LOGGER.debug("#### LoginInterceptor #### request.getServletPath() : " + request.getServletPath() + " ####");
//        LOGGER.debug("#### LoginInterceptor #### request.getQueryString() : " + request.getQueryString() + " ####");
//        LOGGER.debug("#####################################################################################################");
//        LOGGER.debug("");

        String retMsg = "";

        String addParams = "";
        if( StringUtils.isNoneBlank( request.getQueryString() ) ){
            addParams = "?" + request.getQueryString();
        }

        LoginInfo loginInfo = new LoginInfo();
        
       
        
        List<HashMap<String, String>> useridInfo = (List<HashMap<String, String>>)request.getServletContext().getAttribute("mUserList");
        Boolean dupFlag = (Boolean) request.getServletContext().getAttribute("dupFlag");
        
        boolean dupCkeck = false;
        if(useridInfo != null){

        	for(int i=0; i<useridInfo.size();i++){
        		// 해당 세션 마지막 요청 시간 update
        		if(request.getSession().getId().equals(useridInfo.get(i).get("sessionId"))){
        			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    
        	        Date lastAccessTimeF = new Date(request.getSession().getLastAccessedTime());
        	        String lastAccessTimeStr = sFormat.format(lastAccessTimeF);
        	        
        	        useridInfo.get(i).put("LastAccTime", lastAccessTimeStr);
        		}
        		// 중복 로그인 로그아웃        		
	        	if(request.getSession().getId().equals(useridInfo.get(i).get("sessionId")) && useridInfo.get(i).get("exitFlag").equals("Y") && useridInfo.get(i).get("dupYn").equals("N")){
	        		
	        		response.sendRedirect("/main/logout.do?logoutMode=logOut&dupFlag=Y");
	        		useridInfo.remove(i);
	        		
	        		
	        	}
	        	
	        }
	        request.getServletContext().setAttribute("dupFlag",false);
        }
        
        //api호출인지 web호출인지 판단
        if(originalURL.indexOf(".api") > 0){
        	return true;
        }else{
        	/* Login 되어있는지 check */
            if(!loginInfo.isLogin()){

                LOGGER.debug("#### LoginInterceptor #### !loginInfo.isLogin() ####");
                //----------------------------------------------------------------
                // Login 되어있지 않으면 로그인화면으로 redirect 또는 로그인결과 json 으로 redirect
                //----------------------------------------------------------------
                /* pjh
                if( -1 < uri.indexOf(".json") || -1 < originalURL.indexOf(".json") ){

                    LOGGER.debug("#### LoginInterceptor -> IsSessionCheckAjax #### " + request.getRequestURI() + "==>" + request.getRequestURI().replace ( request.getServletPath(), "" ) );
                    response.sendRedirect(  request.getRequestURI().replace ( request.getServletPath(), "" ) + "/main/sessionNullForAjaxReturn.json");
                    return false;
                }else */
                LOGGER.debug("#### LoginInterceptor -> IsSessionCheck ####");
                
                if("/main/applyAuthority.do".equals(originalURL)){
                	LOGGER.info("#### applyUrl : " + originalURL );       
                	return true;
                }else if("/main/findIdPw.do".equals(originalURL)) {
                	LOGGER.info("#### findIdPwUrl : " + originalURL );       
                	return true;
                }
                else if(originalURL.startsWith("/excontent/")) {
                	LOGGER.info("#### excontent : " + originalURL );       
                	return true;
                }
                else {
                	String targetUrl = Globals.LOGIN_PAGE + "?reqUri=" + request.getServletPath() + URLEncoder.encode( addParams ,"UTF-8") + "&msg=SESSION_FINISH";

                    LOGGER.info("#### targetUrl : " + targetUrl.replaceAll("[\r\n]","") );
                	response.sendRedirect(  request.getRequestURI().replace ( request.getServletPath(), "" ) + targetUrl );
                	 return false;
                }
            }else{

            	// 메뉴권한 체크
            	boolean isMenuAuth = true; 
                if(originalURL.equals("/content/selectContentList.do")){
                	isMenuAuth = false;
                    
                    if(request.getSession().getAttribute("3deptMenuSession") != null){
                  	  List<Menu> menu3 = (List<Menu>) request.getSession().getAttribute("3deptMenuSession");
              		  	for(int i=0; i<menu3.size(); i++){
              		  		if(menu3.get(i).getMenuId3().equals(request.getParameter("menu_code"))){
              		  			isMenuAuth = true;
              		  			break;
              		  		}
              		  	}
              		  
                	  }

                }
                
                if(!isMenuAuth){
                	response.sendRedirect("/common/menuAuth.jsp");
                	return false;
                }else{
                	return true;
                }
            	
            	
                
            }
        }
        
    	
        
        
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    {
        try {
            super.postHandle(request, response, handler, modelAndView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        try {
            super.afterCompletion(request, response, handler, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
