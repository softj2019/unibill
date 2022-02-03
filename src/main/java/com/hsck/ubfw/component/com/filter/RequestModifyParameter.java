package com.hsck.ubfw.component.com.filter;

import com.hsck.ubfw.component.comm.vo.LoginInfo;

import egovframework.com.cmm.service.Globals;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by LSW on 2016-12-27.
 */
public class RequestModifyParameter extends HttpServletRequestWrapper {
	 
	 Map<String, String[]> params;
	        
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public RequestModifyParameter(HttpServletRequest request) {
	  super(request);
	  this.params = new HashMap(request.getParameterMap());
	                // 중요했던 것은 다 해놓고 request.getParameterMap으로
	                // 선언하면 readOnly 상태가 됨으로 수정이 불가능 했습니다..
	                // 그래서 다시 HashMap으로 받아 입력하면 수정이 가능했습니다!
	 }

	 @Override
	 public String getParameter(String name) {
	  String[] paramArray = getParameterValues(name);

	  if (paramArray != null && paramArray.length > 0) {
	   return paramArray[0];
	  } else {
	   return null;
	  }
	 }

	 @Override
	 public Map<String, String[]> getParameterMap() {
	  return Collections.unmodifiableMap(params);
	 }

	 @Override
	 public Enumeration<String> getParameterNames() {
	  return Collections.enumeration(params.keySet());
	 }

	 @Override
	 public String[] getParameterValues(String name) {

	  String[] result = null;
	  String[] temp = params.get(name);

	  if (temp != null) {
	   result = new String[temp.length];
	   System.arraycopy(temp, 0, result, 0, temp.length);
	  }

	  return result;
	 }

	 public void setParameter(String name, String value) {
	  String[] oneParam = {value};
	  setParameter(name, oneParam);
	 }

	 public void setParameter(String name, String[] values) {
	  params.put(name, values);
	 }

	}
