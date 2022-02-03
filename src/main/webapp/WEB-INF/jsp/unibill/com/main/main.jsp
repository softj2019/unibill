<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  /**
  * @Class Name : main.jsp
  * @Description : 메인화면
  * @Modification Information
  *
  *   수정일     수정자      수정내용
  *  ----------  --------    ---------------------------
  *  2016.03.31  홍길동      최초 생성
  *
  * author 한싹시스템
  * since 2016.10.04
  *
  * Copyright (C) 2016 by http://www.hanssak.co.kr  All right reserved.
  */
%>

<script>
	$(document).ready(function() {
		var firstloginYn = $("#firstloginYn").val();
		if(firstloginYn == "Y"){
			//COMMON.fn_openPassChange_pop();
		}
		
		//스타트 메뉴 설정
		var startMenu= '${startMenu}';
		if(startMenu != ""){
			COMMON.fn_menu_click(startMenu,'Y','Y','','topForm');
		}
		
		
	});
</script>
<!-- visual -->
<form id="mainForm" name="mainForm" method="POST">
	<input type="hidden" id="firstloginYn" name="firstloginYn" value="${firstloginYn}" />
          
		  <!-- Content Wrapper. Contains page content -->
		    main page
		  <!-- /.content-wrapper -->
		  
		  
		  
		 
  
    
<!-- //visual -->
</form>



