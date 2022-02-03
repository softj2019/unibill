<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<style>
body {width:350px; height:440px; border:1px solid #ddd; margin:0; font-size:12px; font-family:'NGB','NG','맑은 고딕','Malgun Gothic','dotum',sans-serif; }
.pop_cont{ width:100%; height:400px;}
.popup{ width:100%; height:30px; padding:8px 0px;}
.popup input{float:left; margin-left:10px;} 
.popup button{float:right; margin-right:10px; } 
.popup span{float:left; padding-top:5px;} 
.btn_p {display:inline-block;font-size:12px;border-radius:3px; padding:3px 10px 4px;color:#fff;background:#61b2ac; border:1px solid #4c8c87; font-family:'NGB','NG','맑은 고딕','Malgun Gothic','dotum',sans-serif;}
.btn_p:hover{color:#fff;background:#3d8b85; border:1px solid #4c8c87;}
</style>

<script type="text/javascript">
$(document).ready(function(){
	var nubseq = ${nubseq};
	var imgwidth = ${imgwidth};
	var imgheight = ${imgheight};
	
	var compare = nubseq + "=done";
	cookiedata = document.cookie; 
	if ( cookiedata.indexOf(compare) < 0 ){ 
		window.resizeTo(imgwidth + 18, imgheight + 105);
		$("body").width(imgwidth);
		$("body").height(imgheight);
		$(".pop_cont").width(imgwidth);
		$(".pop_cont").height(imgheight);
		$(".popup").width(imgwidth);
	} else {
		close();
	}
	
	
	
	
		
});

function setCookie( name, value, expiredays ) { 
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"

}

function fn_dayoff(){
	var nubseq = ${nubseq};
	setCookie( nubseq, "done" , 1 ); 
	
}

function fn_close(){
	close();
}




</script>

<form id="notice_searchFrm" name="notice_searchFrm" method="post" action="">

</form>



<div class="notice_confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
<body>
 	<div class="pop_cont">
		<img src="${imgfile }">
	</div>
	<div class="popup">
		<span><input type="checkbox" onclick="fn_dayoff()">오늘은 그만보기</span>
		<button class="btn_p" onclick="fn_close();">팝업창닫기</button>
	</div>
</body>


</div>



