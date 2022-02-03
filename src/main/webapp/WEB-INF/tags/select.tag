<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>
<%@ tag dynamic-attributes="optionMap"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="selectDivAttr">class="form-control form-control-sm col-4 "</c:set>
<select id="${id}" name="${name}" ${selectDivAttr} >
	<c:forEach items="${optionMap}" var="option">
	<option value="${option.key}">${option.value}</option>	
	</c:forEach>
</select>