<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
	<title>권한신청</title>
	<!-- Font Awesome -->
	<link rel="stylesheet" href="/dinamicweb/plugins/fontawesome-free/css/all.min.css">
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
	
	<script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/custag.js'/>"></script>	

<% String contextRoot = request.getContextPath(); %>

<script type="text/javascript">
var CONTEXT_ROOT = '<%=contextRoot%>';

$(function(){
	CUSTAG.fn_selectbox("branch","branch");
	
	$("#findPw1").click(function(){
		$("#idForm").css("display","none");
		$("#pwForm").css("display","block");
	});
	$("#findId2").click(function(){
		location.reload();
	});
	
	$("#btn_save1").click(function () {
		var user_role = $('input[name="user_role"]:checked').val();
		var branch = $("#branch").val();
		var name = $("#name").val().trim();
		var firstNum1 = $("#firstNum1").val().trim();
		var middleNum1 = $("#middleNum1").val().trim();
		var endNum1 = $("#endNum1").val().trim();
		
		if($(':radio[name="user_role"]:checked').length < 1){
			$.unibillDialog.alert('경고', '사용자 구분을 선택해주세요.');
			$("#user_role0").focus(); 
			return false; 
		}
		
		if(branch == ""){
			$.unibillDialog.alert('경고', '지점을 선택해주세요.');
		    $("#branch").focus();
		    return false;
		}
		
		if(name == ""){
			$.unibillDialog.alert('경고', '성명을 입력해주세요.');
		    $("#name").focus();
		    return false;
		}
		
		if(firstNum1 == "" || middleNum1 == "" || endNum1 == ""){
			$.unibillDialog.alert('경고', '휴대폰 번호를 입력해주세요.');
		    $("#firstNum1").focus();
		    return false;
		}
		
		var sendData = {"user_role":user_role,"branch":branch,"name":name,"num":firstNum1+"-"+middleNum1+"-"+endNum1};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/excontent/findId.do',
			data: sendData,
			async: false ,
			success : function(data){
				if(data.checkId == true){
					$.unibillDialog.alert('알림', '메세지 전송이 완료되었습니다.');
				}else if(data.checkId == false){
					$.unibillDialog.alert('경고', '존재하는 아이디가 없거나 정보가 다릅니다. 다시 확인해주세요.');
				}
			},
			error:function(error){
				$.unibillDialog.alert('경고', '[error]: ' + error);
			}
	 	});
	});
	
	$("#btn_save2").click(function () {
		var user_id = $("#user_id").val().trim();
		var firstNum2 = $("#firstNum2").val().trim();
		var middleNum2 = $("#middleNum2").val().trim();
		var endNum2 = $("#endNum2").val().trim();
		
		if(user_id == ""){
			$.unibillDialog.alert('경고', '아이디를 입력해주세요.');
		    $("#user_id").focus();
		    return false;
		}
		
		if(firstNum2 == "" || middleNum2 == "" || endNum2 == ""){
			$.unibillDialog.alert('경고', '휴대폰 번호를 입력해주세요.');
		    $("#firstNum2").focus();
		    return false;
		}
		
		var sendData = {"user_id":user_id,"num":firstNum2+"-"+middleNum2+"-"+endNum2};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/excontent/findPw.do',
			data: sendData,
			async: false ,
			success : function(data){
				if(data.checkId == true){
					$.unibillDialog.alert('알림', '메세지 전송이 완료되었습니다.');
				}else if(data.checkId == false){
					$.unibillDialog.alert('경고', '존재하는 아이디가 없습니다. 다시 확인해주세요.');
				}
			},
			error:function(error){
				$.unibillDialog.alert('경고', '[error]: ' + error);
			}
	 	});
	});
});
</script>
</head>
<body class="hold-transition login-page">
<div id="idForm" class="login-box" style="width: 650px !important;">
  <div class="card card-danger">

        <div class="card-header">
            <div class="card-title text-md text-bold">아이디 &middot; 비밀번호 찾기</div>
        </div>

    <div class="card-body login-card-body">
      <div class="mb-4 text-left mt-1 text-bold text-sm">
        <div class="agree">
            <input type="radio" id="findId1" name="findIdPw1" value="Y" checked="checked"> 아이디 찾기 &nbsp;&nbsp;
            <input type="radio" id="findPw1" name="findIdPw1" value="N"> 비밀번호 찾기
        </div>
      </div>

        <table summary="등록/상세정보" class="tb02 ">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="150px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                <th scope="row">사용자 구분<span class="asterisk"></span></th>
                <td>
                    <input type="radio" id="user_role0" name="user_role" value="0"> 아이앤씨 &nbsp;&nbsp;
                    <input type="radio" id="user_role1" name="user_role" value="1"> 대성 본사 &nbsp;&nbsp;
                    <input type="radio" id="user_role2" name="user_role" value="2"> 대성 점포

                </td>
                </tr>
                <tr>
                <th scope="row">지점<span class="asterisk"></span></th>
                 <td>
                    <tf:select id="branch" name="branch"  />
                </td>
                </tr>
                <tr>
                <th scope="row">성명<span class="asterisk "></span></th>
                <td>
                    <input type="text" id="name" class="form-control form-control-sm col-6 col-sm-6 ">
                </td>
                </tr>
                <tr>
                <th scope="row">휴대폰번호<span class="asterisk"></span></th>
                <td>
				  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="firstNum1" maxlength='3' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                  <span class="float-left mr-1 mt-1">-</span>
                  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="middleNum1" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                  <span class="float-left mr-1 mt-1">-</span>
                  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="endNum1" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                </td>
                </tr>
            </tbody>
        </table>

      <div class="text-center text-sm mt-4 mb-3">
        <button type="button" id="btn_save1" name="btn_save" class="btn  btn-danger col-2  text-bold">확인</button>
        <a href="<c:url value='/main/adminloginForm.do'/>" class="btn  btn-dark col-2 mr-1  text-bold">닫기</a>
      </div>
      <!-- /.social-auth-links -->
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->


<div id="pwForm" class="login-box" style="width: 650px !important;display: none;">
  <div class="card card-danger">

        <div class="card-header">
            <div class="card-title text-md text-bold">아이디 &middot; 비밀번호 찾기</div>
        </div>

    <div class="card-body login-card-body">
      <div class="mb-4 text-left mt-1 text-bold text-sm">
        <div class="agree">
            <input type="radio" id="findId2" name="findIdPw2" value="Y"> 아이디 찾기 &nbsp;&nbsp;
            <input type="radio" id="findPw2" name="findIdPw2" value="N" checked="checked"> 비밀번호 찾기
        </div>
      </div>

        <table summary="등록/상세정보" class="tb02 ">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="150px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                <th scope="row">아이디<span class="asterisk "></span></th>
                <td>
                    <input type="text" id="user_id" name="user_id" class="form-control form-control-sm col-6 col-sm-6 ">
                </td>
                </tr>
                <tr>
                <th scope="row">휴대폰번호<span class="asterisk"></span></th>
                <td>
				  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="firstNum2" maxlength='3' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
	              <span class="float-left mr-1 mt-1">-</span>
	              <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="middleNum2" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
	              <span class="float-left mr-1 mt-1">-</span>
	              <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="endNum2" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                </td>
                </tr>
            </tbody>
        </table>

      <div class="text-center text-sm mt-4 mb-3">
        <button type="button" id="btn_save2" name="btn_save" class="btn  btn-danger col-2  text-bold">확인</button>
        <a href="<c:url value='/main/adminloginForm.do'/>" class="btn  btn-dark col-2 mr-1  text-bold">닫기</a>
      </div>
      <!-- /.social-auth-links -->
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->
</body>
</html>
