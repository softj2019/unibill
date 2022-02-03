<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>비밀번호 변경</title>

	<!-- Font Awesome -->
	<link rel="stylesheet" href="/dinamicweb/plugins/fontawesome-free/css/all.min.css">
	<!-- Ionicons -->
	<!-- Tempusdominus Bootstrap 4 -->
	<link rel="stylesheet" href="/dinamicweb/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
	<!-- iCheck -->
	<link rel="stylesheet" href="/dinamicweb/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
	<!-- JQVMap -->
	<link rel="stylesheet" href="/dinamicweb/plugins/jqvmap/jqvmap.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="/dinamicweb/dist/css/adminlte.css">
	<!-- overlayScrollbars -->
	<link rel="stylesheet" href="/dinamicweb/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
	<!-- Daterange picker -->
	<link rel="stylesheet" href="/dinamicweb/plugins/daterangepicker/daterangepicker.css">
	
	<link rel="stylesheet" type="text/css" href="/dinamicweb/dist/css_1/common.css" />
	
	<link rel="stylesheet" type="text/css" href="/dinamicweb/dist/js_1/jquery-ui.min.css" />
	<script type="text/javascript" src="/dinamicweb/dist/js_1/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/dinamicweb/dist/js_1/jquery.selectbox.js"></script>
	 
	<!-- jQuery -->
	<script src="/dinamicweb/plugins/jquery/jquery.min.js"></script>
	<!-- jQuery UI 1.11.4 -->
	<script src="/dinamicweb/plugins/jquery-ui/jquery-ui.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="/dinamicweb/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- ChartJS -->
	<script src="/dinamicweb/plugins/chart.js/Chart.min.js"></script>
	<!-- Sparkline -->
	<script src="/dinamicweb/plugins/sparklines/sparkline.js"></script>
	<!-- JQVMap -->
	<script src="/dinamicweb/plugins/jqvmap/jquery.vmap.min.js"></script>
	<script src="/dinamicweb/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
	<!-- jQuery Knob Chart -->
	<script src="/dinamicweb/plugins/jquery-knob/jquery.knob.min.js"></script>
	<!-- daterangepicker -->
	<script src="/dinamicweb/plugins/moment/moment.min.js"></script>
	<script src="/dinamicweb/plugins/daterangepicker/daterangepicker.js"></script>
	<!-- Tempusdominus Bootstrap 4 -->
	<script src="/dinamicweb/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
	<!-- overlayScrollbars -->
	<script src="/dinamicweb/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE App -->
	<script src="/dinamicweb/dist/js/adminlte.js"></script>
	<link type="text/css" rel="stylesheet" href="<c:url value='/extLib/jquery-ui-themes-1.12.1/themes/base/theme.css'/>" /><!-- jqgrid 필터에 필요함 -->

	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-1.11.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>	
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.form.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.unibillDialog.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/free-jqgrid/4_13_5/js/jquery.jqgrid.src.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/free-jqgrid/4_13_5/js/i18n/grid.locale-kr.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/extLib/jquery/plugins/blockUI/jquery.blockUI.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/utile.js'/>"></script>
	<style type="text/css">  
		html {overflow:hidden;}  
	</style>  
	<script>
		var isLiveMode = false;
		var topMenuCloseYn = "N";
	</script>
</head>
<script type="text/javascript">
$( document ).ready(function() {
    $('#newPass').keypress(function(event) {
        if ( event.which == 13 ) {
            if( ( $('#oldPass').val() ) == ( $('#newPass').val() ) ){
                UTILE.fn_alert("이전 비밀번호와 동일합니다.<br/> 이전 비밀번호와 다른 비밀번호를 사용하여주세요.");
            }else{
                checkPass();
            }
        }
    });
    $('#newPass2').keypress(function(event) {
        if ( event.which == 13 ) {
            if( ( $('#newPass').val() ) != ( $('#newPass2').val() ) ){
                UTILE.fn_alert("신규 비밀번호가 일치하지 않습니다.<br/> 비밀번호를 확인해주세요.")
            }else{
                doChangePass();
            }
        }
    });


    $("button").on("click", function() {
        <c:if test="${'Y' eq timeoutExtent}">
        UTILE.fn_autoLogout_init( ${sessionMaxInactiveIntervalTime} );
        </c:if>
    });
});

function checkPass(){
    UTILE.fn_ajax("/main/checkPreviousPassword.json",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
        if( fn_isNotBlank( resultData ) ){
            if( "Y" == resultData.isPreviousPassword ){
                fn_alert( "최근" + resultData.previousPasswordCnt +" 회 안에 동일한 비밀번호를 사용한적이 있습니다. 다른 비밀번호를 사용해주세요.");
                $("#newPass").val("");
                $("#newPass2").val("");
            }else{
                fn_alert( "사용가능한 비밀번호 입니다.");
            }
        }
    });
}

function doChangePass(){

    UTILE.fn_ajax("/main/passChangeSave.json",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
        if( fn_isNotBlank( resultData ) ){
            if( fn_isNotBlank( resultData.msg ) ){
                fn_alert( resultData.msg );
            }
            if( fn_isNotBlank( resultData.code ) ){
                if( "isNotLogin" == resultData.code ){

                }else if( "oldPassEmpty" == resultData.code ){
                    $("#oldPass").focus();

                }else if( "newPassEmpty" == resultData.code ){
                    $("#newPass").focus();

                }else if( "newPassMissMatched" == resultData.code || "oldAndNewPassMatched" == resultData.code || "passwordCharacterConditionCheck" == resultData.code ){
                    $("#newPass").val("");
                    $("#newPass2").val("");
                    $("#newPass").focus();
                }else if( "oldPassMissMatched" == resultData.code ){
                    $("#oldPass").val("");
                    $("#oldPass").focus();
                }else if( "previousPassword" == resultData.code ){
                    $("#newPass").val("");
                    $("#newPass2").val("");
                }else if( "SUCCESS" == resultData.code ){
                    UTILE.fn_alert("수정되었습니다.");
                    $('#passChange_pop').hide();
					
                    $("#oldPass").val("");
                    $("#newPass").val("");
                    $("#newPass2").val("");
                    
                    $("#mainForm").find("#firstloginYn").val("N");
                }

            }
        }
    });
}


</script>
<body class="hold-transition login-page">
<!-- 비밀번호 변경 팝업 -->
<div id="passChange_pop" class="login-box" style="width:600px;!important;" >
  <!--<div class="login-logo">
    <a href="index2.html"><b>Admin</b>LTE</a>
  </div>-->
  <!-- /.login-logo -->
  <div class="card card-danger">

        <div class="card-header">
            <div class="card-title text-md text-bold">비밀번호 변경하기</div>
        </div>

    <div class="card-body login-card-body">
      <ul class="mb-4 text-center mt-2 text-md">
        <li class="text-bold text-danger">3개월간 비밀번호 변경이 없었습니다.</li>
        <li>안전한 비밀번호 관리를 위해 비밀번호를 변경해주세요.</li>
      </ul>

      <ul class="mb-3">
          <li class=" border-bottom border-top text-bold pt-1 pb-1" style="background-color: #f5f5f5; padding-left:20px"><span >비밀번호 변경 규칙</span></li>
          <li class=" ml-3 pt-2">&middot; 영문+숫자+특수문자 8자리이상 또는 영문+숫자 10자리 이상 (대소문자 구분)</li>
          <li class=" ml-3">&middot; 연속된/반복된 숫자, 문자 사용 불가</li>
          <li class=" ml-3">&middot; 이전 비밀번호(3회전) 사용 불가, 아이디 포함 불가</li>
      </ul>
      <form id="frm_changePass" name="frm_changePass" action="<c:url value='/main/passChangeSave.do'/>">
        <table summary="등록/상세정보" class="tb02">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="180px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                <th scope="row">기존 비밀번호<span class="asterisk"></span></th>
                <td><input type="password" id="oldPass" name="oldPass" class="form-control form-control-sm col-12 col-sm-12 float-left mr-1  small "></td>
                </tr>
                <tr>
                <th scope="row">신규 비밀번호<span class="asterisk"></span></th>
                <td><input type="password" id="newPass" name="newPass" class="form-control form-control-sm col-12 col-sm-12 float-left mr-1  small "></td>
                </tr>
                <tr>
                <th scope="row">신규 비밀번호 확인<span class="asterisk"></span></th>
                <td><input type="password" id="newPass2" name="newPass2" class="form-control form-control-sm col-12 col-sm-12 float-left mr-1 text-xs "></td>
                </tr>
            </tbody>
        </table>
      </form>
      <div class="text-center text-sm mt-4 mb-3">
        <button type="button" onclick="window.close();" class="btn  btn-dark col-3 mr-1  text-bold">나중에 변경하기</button>
        <button type="button" onclick="javascript:doChangePass();" class="btn  btn-danger col-3  text-bold">확인</button>
      </div>
      <!-- /.social-auth-links -->
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
</body>
</html>
