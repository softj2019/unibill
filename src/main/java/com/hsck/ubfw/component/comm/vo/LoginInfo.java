package com.hsck.ubfw.component.comm.vo;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.com.cmm.service.Globals;

/**
 * 사용자 관련 정보를 조회하는 역활을 한다.
 *
 * @author reo
 * @since 2016. 12. 01.
 */
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = -6485746865042331505L;
    private static final Logger LOG = LoggerFactory.getLogger(LoginInfo.class);

    private final HttpSession session;
    private final String loginPageUsr;
    private final Map paramMap;

    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public final String reqUri;

    private FilterConfig config;

    public LoginInfo() {

        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpRequest = servletRequestAttribute.getRequest();
        HttpSession httpSession = httpRequest.getSession(false); // [false] : HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 새로이 생성하지 않고 그냥 null을 반환 , [true] :HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 새로이 세션을 생성

        this.request = httpRequest;
        this.response = null;
        this.session = httpSession;
        if( null != httpSession ){
            this.session.setAttribute("LANGUAGE", "KOR");
        }
        this.reqUri = httpRequest.getRequestURI();
        this.paramMap = httpRequest.getParameterMap();
        this.loginPageUsr = Globals.LOGIN_PAGE;

    }

    public LoginInfo(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(false); // [false] : HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 새로이 생성하지 않고 그냥 null을 반환 , [true] :HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 새로이 세션을 생성
        if( null != this.session ){

            this.session.setAttribute("LANGUAGE", "KOR");
        }
        this.reqUri = request.getRequestURI();
        this.paramMap = request.getParameterMap();
        this.loginPageUsr = Globals.LOGIN_PAGE;

    }

    public void putSessionToParameterMap(Map pMap) {
        pMap.put("sessionUserId", getUserId());
        pMap.put("sessionUserNm", getUserNm());

        LOG.debug("======== SESSION To MAP ================");
        LOG.debug(pMap.toString());
        LOG.debug("=================================");
    }

    public void putSessionToVo(BaseVO baseVo) {
        baseVo.setSessionUserId(getUserId());
        baseVo.setSessionUserNm(getUserNm());

        LOG.debug("======== SESSION To VO ================");
        LOG.debug(baseVo.toString());
        LOG.debug("=================================");

    }

    /**
     * session에서 사용자 정보를 조회한다.
     *
     * @return Object
     */
    public LoginVO getLoginInfo() {

        LoginVO vo = null;
        if (this.isLogin() && null != RequestContextHolder.getRequestAttributes()) {

            ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession httpSession = servletRequestAttribute.getRequest().getSession(false);

            if (null != httpSession) {
                if (httpSession.getAttribute(Globals.SESSION_USER_VO) instanceof LoginVO) {

                    LOG.debug("#### getLoginInfo() : instanceof LoginVO");
                    vo = (LoginVO) httpSession.getAttribute(Globals.SESSION_USER_VO);
                } else {

                    LOG.debug("#### getLoginInfo() : String ==>> return null");
                }
            }
        }
        return vo;
    }

    /**
     * 단순 session 체크
     *
     * @return boolean
     */
    public boolean isLogin() {
        String userId = "";
        boolean isLogin = false;
        try {
            userId = (String) session.getAttribute(Globals.SESSION_USER_ID);

            ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession httpSession = servletRequestAttribute.getRequest().getSession(false);

            if (null != httpSession) {
                if (StringUtil.isNotBlank(userId)
                        && httpSession.getAttribute(Globals.SESSION_USER_VO) instanceof LoginVO
                        ) {
                    String rContextPath = String.valueOf( servletRequestAttribute.getRequest().getContextPath() );
                    String sContextPath = String.valueOf( httpSession.getAttribute(Globals.SESSION_CONTEXT_PATH) ).replaceAll("null","");

                    rContextPath = StringUtils.defaultIfBlank( rContextPath , "");
                    sContextPath = StringUtils.defaultIfBlank( sContextPath , "");

//                    LOG.debug("#### rContextPath : " + rContextPath );
//                    LOG.debug("#### sContextPath : " + sContextPath );
                    if( rContextPath.equals( sContextPath ) ){
                        isLogin = true;
                    }else{
                        LOG.info("The paths 'Login session ContextPath' and 'Request ContextPath' are different.!!");
                    }
                }


            }

        } catch (Exception e) {
        }

        return isLogin;
    }

    /**
     * session 정보 return
     * @return String
     * @see #
     */
    public String getAttribute(String pAttributeName) {
        String rtnValue;
        if (session == null)
            return "";
        try {
            rtnValue = (String) session.getAttribute(pAttributeName);
            if (rtnValue == null)
                return "";
        } catch (Exception e) {
            return "";
        }

        return rtnValue;
    }

    /**
     * 사용자 ID return
     *
     * @return String
     * @see #
     */
    public String getUserId() {
        return getAttribute(Globals.SESSION_USER_ID);
    }

    /**
     * 사용자 NAME return
     *
     * @return String
     * @see #
     */
    public String getUserNm() {
        return getAttribute(Globals.SESSION_USER_NAME);
    }

}
