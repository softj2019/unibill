layout.jsp<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<%-- <tiles:insertAttribute name="title" ignore="true" /> --%>
		${title}
	</title>
	<% String contextRoot = request.getContextPath(); %>
	<script>
        var CONTEXT_ROOT = '<%=contextRoot%>';
        var isLiveMode = false;
//        var isLiveMode = true;

        var topMenuCloseYn = "N";
	</script>
	<style type="text/css">
	    /* 자동완성기능 css */
	    .ui-autocomplete {
	        /* width: 150px; */
	        height: 300px;
	        overflow-y: auto; /* prevent horizontal scrollbar */
	        overflow-x: hidden;
	        position: absolute;
	        top: 0px;
	        left: 0px;
	        /* padding:0 5px 0 5px; */
	        padding:0 0 0 5px;
	        display: none;
	    }
	    .ui-helper-hidden-accessible {display:none;}
	    .ui-state-focus {width:300px;}
	    
	    /* body {
	    	overflow: hidden;
	    } */
	</style>
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/common.css'/>"/>

	<link type="text/css" rel="stylesheet" href="<c:url value='/extLib/jquery-ui-themes-1.12.1/themes/base/theme.css'/>" /><!-- jqgrid 필터에 필요함 -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/extLib/free-jqgrid/4_13_5/css/ui.jqgrid.min.css'/>" />

	<script type="text/javascript" src="<c:url value='/extLib/js-cookie/src/js.cookie.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-1.11.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>	
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.form.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.selectbox.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/selectBox.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/extLib/free-jqgrid/4_13_5/js/jquery.jqgrid.src.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/free-jqgrid/4_13_5/js/i18n/grid.locale-kr.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/plugins/blockUI/jquery.blockUI.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.unibillDialog.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/content.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/utile.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/grid.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/mail.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/hiswebreq.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/mbTcTong.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/custom.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/stats.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/dynamic.select.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/dynamic.radio.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/dynamic.check.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/zip.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tong.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jiro.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/org.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/upfile.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/notice.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/faq.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jojik.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bill.js'/>"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {	
			
			$(this).click(function () {
				// 레이어 팝업 show 시 body 스크롤 hidden, 레이어 팝업 hide 시 body 스크롤 auto
				var sClass = $('.ui_confirm');
				
				if ($(sClass).is(':visible')) {
					$("body").css({overflow:'hidden'});
				} else {
					$("body").css({overflow:'auto'});
				}
			});

		});

	</script>
</head>

<body class="cbp-spmenu-push">
<div class="loader_wrap" id="id_loader" style="z-index:99999;display:none">
    <div class="loader"></div>
    <p>Loading...</p>
</div>
<!-- bookmark -->
<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left" id="cbp-spmenu-s1"><!-- left -->
    <!-- <h3>Bookmark <span style="position:absolute;right:10px;color:#000000;"><input type="checkbox" id="bookposL" name="bookposL" value="R" onclick="COMMON.fn_bookMarkPos('bookposL','R','cbp-spmenu-s1','cbp-spmenu-s2');"> 우측</span></h3> -->
    <h3>Bookmark <button href="#" class="b_right" title="우측으로 고정" onclick="COMMON.fn_bookMarkPos('bookposL','R','cbp-spmenu-s1','cbp-spmenu-s2','J');">우측으로 고정</button></h3> 
    <ul id="leftBookMark"></ul>
    <div class="swipe-area">
        <button href="#" id="showLeft" class="swipebutton active" onfocus="this.blur()">즐겨찾기 버튼</button>
    </div>
</nav>
<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right" id="cbp-spmenu-s2" style="display:none;"><!-- right -->
    <!-- <h3>Bookmark <span style="position:absolute;right:10px;color:#000000;"><input type="checkbox" id="bookposR" name="bookposR" value="L" onclick="COMMON.fn_bookMarkPos('bookposR','L','cbp-spmenu-s1','cbp-spmenu-s2');"> 좌측</span></h3> -->
    <h3>Bookmark <button href="#" class="b_left" title="좌측으로 고정" onclick="COMMON.fn_bookMarkPos('bookposR','L','cbp-spmenu-s1','cbp-spmenu-s2','J');">좌측으로 고정</button></h3>
    <ul id="rightBookMark"></ul>
    <div class="swipe-area2">
        <button href="#" id="showRight" class="swipebutton active" onfocus="this.blur()">즐겨찾기 버튼</button>
    </div>
</nav>
<!-- // bookmark -->
<div id="header">  
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
</div>
<div id="wrap">	
	<div id="container">
		<div id="content">
			<tiles:insertAttribute name="content" />
		</div>
	</div>
</div>
<tiles:insertAttribute name="footer" />
</body>
</html>