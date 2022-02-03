<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var="list" items="${searchInfo}" varStatus="status">                        	                        		                       		                       		
 	<!-- 버튼이 아닌 경우 -->
    <c:if test = "${list.objtype != 'button_s'}">
 
		<!-- 검색 항목명을 앞에 출력한다. -->
		<c:if test = "${list.objpos == 'p'}">
			${list.name}
		</c:if>
	
		<!-- 검색 폼 생성 -->
        <%@ include file="/WEB-INF/jsp/unibill/content/contentSearchArea.jsp"%>
			
		<!-- 검색 항목명을 뒤에 출력한다. -->
		<c:if test = "${list.objpos == 'n'}">
			${list.name}
		</c:if>
		
	</c:if>
	
</c:forEach>