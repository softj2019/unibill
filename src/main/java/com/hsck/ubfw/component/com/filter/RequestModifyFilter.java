package com.hsck.ubfw.component.com.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.UrlPathHelper;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;

/**
 * Created by LSW on 2016-12-27.
 */
public class RequestModifyFilter implements Filter {
	 
	@Override
	 public void destroy() {}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri((HttpServletRequest)request);
        String uri = ((HttpServletRequest)request).getRequestURI().toLowerCase();
		RequestModifyParameter Rrequest= new RequestModifyParameter((HttpServletRequest) request);
		String uri2 = Rrequest.getRequestURI().toLowerCase();
		String gubun     = StringUtil.isNullToString(request.getParameter("gubun"));
		Enumeration en = Rrequest.getParameterNames();
		while(en.hasMoreElements()){    			
			String key = en.nextElement().toString();
			String val = request.getParameter(key).replace("(", "").replace(")", "").replace("<", "").replace(">", "").replace("'", "").replace("\"", "").replace("#", "").replace("//", "").replace("/*", "").replace("&", "").replace("alert", "").replace("alert(", "").replace("prompt", "").replace("confirm", "").replace("`", "").replace("#", "");    			
			Rrequest.setParameter(key, val);
		    
		} 
		
		chain.doFilter(Rrequest, response);
		
	}



	

	

}
