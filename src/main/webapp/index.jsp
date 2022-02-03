<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%
	String url = String.valueOf(request.getRequestURL());
	// UURL 페이지로 이동한다.
	if(url.indexOf("ssologinForm") > 0){
%>
		<jsp:forward page="/main/loginForm.do"/>
<%	}else{ %>
		<jsp:forward page="/main/ssologinForm.do"/>	
<%
	}
%>	 --%>
	
<jsp:forward page="/admin/loginForm.do"/>