<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<!DOCTYPE html>  
<html>    
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>
		<tiles:insertAttribute name="title" ignore="true" /> 
	</title>
	
	<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery-1.11.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='../../extLib/js-cookie/src/js.cookie.js'/>"></script>

	
	<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.form.js'/>"></script>

	<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.selectbox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='../../js/selectBox.js'/>"></script>

	<script type="text/javascript" src="<c:url value='../../extLib/free-jqgrid/4_13_5/js/jquery.jqgrid.src.js'/>"></script>
	<script type="text/javascript" src="<c:url value='../../extLib/free-jqgrid/4_13_5/js/i18n/grid.locale-kr.js'/>"></script>
	<script type="text/javascript" src="<c:url value='../../extLib/jquery/plugins/blockUI/jquery.blockUI.js'/>"></script>
	<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.unibillDialog.js'/>"></script>
	

	
	<% String contextRoot = request.getContextPath(); %>
	<script>
        var CONTEXT_ROOT = '<%=contextRoot%>';

	</script>	
</head>
<body class="popupwrap jui" style="overflow-x:auto; overflow-y:auto;">
	<div id="content">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>