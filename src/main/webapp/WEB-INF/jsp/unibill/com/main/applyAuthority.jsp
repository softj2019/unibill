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
	
	$( document ).ready(function() {
		CUSTAG.fn_selectbox("branch","branch");
		
		$("#check_id").click(function () {
			CUSTAG.fn_checkId("user_id");
		});
		
		$("#btn_save").click(function () {
			var reg_type = $('input[name="reg_type"]:checked').val();
			var user_role = $('input[name="user_role"]:checked').val();
			var branch = $("#branch").val()
			var user_id = $("#user_id").val().trim();
			var pass = $("#pass").val().trim();
			var repass = $("#repass").val().trim();
			var user_nm = $("#user_nm").val().trim();
			var firstNum = $("#firstNum").val().trim();
			var middleNum = $("#middleNum").val().trim();
			var endNum = $("#endNum").val().trim();
			var startDatePicker = $("#startDatePicker").val().replace(/-/g,'');
			var endDatePicker = $("#endDatePicker").val().replace(/-/g,'');
			
			if($(':radio[name="reg_type"]:checked').length < 1){
				$.unibillDialog.alert('경고', '신청 구분을 선택해주세요.');
				$("#reg_type1").focus(); 
				return false; 
			}
			if($(':radio[name="user_role"]:checked').length < 1){
				$.unibillDialog.alert('경고', '권한을 선택해주세요.');
				$("#user_role1").focus(); 
				return false; 
			}
			if(branch == ""){
				$.unibillDialog.alert('경고', '지점을 선택해주세요.');
			    $("#branch").focus();
			    return false;
			}
			if(user_id == ""){
				$.unibillDialog.alert('경고', '아이디를 입력해주세요.');
			    $("#user_id").focus();
			    return false;
			}
			if(pass == ""){
				$.unibillDialog.alert('경고', '비밀번호를 입력해주세요.');
			    $("#pass").focus();
			    return false;
			}
			if(repass == ""){
				$.unibillDialog.alert('경고', '비밀번호 확인을 입력해주세요.');
			    $("#repass").focus();
			    return false;
			}
			if(pass != repass){
				$.unibillDialog.alert('경고', '비밀번호가 일치하지 않습니다.');
			    $("#pass").focus();
			    return false;
			}
			if(user_nm == ""){
				$.unibillDialog.alert('경고', '성명을 입력해주세요.');
			    $("#user_nm").focus();
			    return false;
			}
			if(firstNum == "" || middleNum == "" || endNum == ""){
				$.unibillDialog.alert('경고', '휴대폰 번호를 입력해주세요.');
			    $("#firstNum").focus();
			    return false;
			}
			if(startDatePicker == "" || endDatePicker == ""){
				$.unibillDialog.alert('경고', '사용기간을 입력해주세요.');
			    $("#startDatePicker").focus();
			    return false;
			}
			if(!$("input:checked[id='check_info']").is(":checked")){ 
				$.unibillDialog.alert('경고', '개인정보 수집 및 활용 동의를 체크해주세요.');
				$("#check_info").focus(); 
				return false; 
			}
			
			var sendData = {"reg_type":reg_type,"user_role":user_role,"branch":branch,"user_id":user_id,"pass":pass,"user_nm":user_nm,
					"num":firstNum+"-"+middleNum+"-"+endNum,"startDate":startDatePicker,"endDate":endDatePicker};
			
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/excontent/cfgUserRegSave.do',
				data: sendData,
				async: false ,
				success : function(data){
					if(data.passtf == false){
						$.unibillDialog.alert('경고', '비밀번호를 다시 확인해주세요.');
					}else if(data.tf == true){
						$.unibillDialog.alert('알림', '권한신청이 완료되었습니다.');
					}else{
						$.unibillDialog.alert('경고', '이미 신청된 아이디입니다.');
					}
				},
				error:function(error){
					$.unibillDialog.alert('경고', '[error]: ' + error);
				}
		 	});
		});
	
		CUSTAG.fn_datepicker("startDatePicker","endDatePicker");
	});
</script>
  <style>
      .input-group-text {background-color: #e9ecef !important; border: 1px solid #ced4da !important; }
  </style>
</head>
<body class="hold-transition login-page">
<div class="login-box" style="width: 800px !important;">
  <!--<div class="login-logo">
    <a href="index2.html"><b>Admin</b>LTE</a>
  </div>-->
  <!-- /.login-logo -->
  <div class="card card-danger">

        <div class="card-header">
            <div class="card-title text-md text-bold">권한신청</div>
        </div>

    <div class="card-body login-card-body">
    
      <form action="../../index3.html" method="post">
        <table summary="등록/상세정보" class="tb02 ">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="170px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row">신청 구분<span class="asterisk"></span></th>
                    <td>
                        <input type="radio" id="reg_type1" name="reg_type" value="I"> 신규 &nbsp;&nbsp;
                        <input type="radio" id="reg_type2" name="reg_type" value="U"> 연장 
                    </td>
                </tr>
                <tr>
                <th scope="row">권한<span class="asterisk"></span></th>
                <td>
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
                    <th scope="row">아이디<span class="asterisk "></span></th>
                    <td>
                        <input type="text" id="user_id" name="user_id" class="form-control form-control-sm  col-4 float-left ">
                        <button type="button" id="check_id" name="check_id" class="btn btn-danger btn-sm  col-3 col-sm-2 float-left text-xs ml-2">중복확인</button>
                    </td>
                </tr>
                <tr>
                    <th scope="row">비밀번호<span class="asterisk "></span></th>
                    <td>
                        <input type="password" id="pass" name="pass" class="form-control form-control-sm  col-4 ">
                    </td>
                </tr>
                <tr>
                    <th scope="row">비밀번호 확인<span class="asterisk "></span></th>
                    <td>
                        <input type="password" id="repass" name="repass" class="form-control form-control-sm  col-4 mb-2 ">
                        <span style="font-size: 12px;" class="text-danger">※ 영문+숫자+특수문자 8자리 이상 또는 영문+숫자 10자리 이상, 연속된 숫자/문자, 아이디 사용 불가</span>
                    </td>
                    
                </tr>
                <tr>
                    <th scope="row">성명<span class="asterisk "></span></th>
                    <td>
                        <input type="text" id="user_nm" name="user_nm" class="form-control form-control-sm  col-4 ">
                    </td>
                </tr>
                <tr>
                <th scope="row">휴대폰번호<span class="asterisk"></span></th>
                <td>
					  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="firstNum" maxlength='3' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
	                  <span class="float-left mr-1 mt-1">-</span>
	                  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="middleNum" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
	                  <span class="float-left mr-1 mt-1">-</span>
	                  <input type="text" class="form-control form-control-sm col-2 float-left mr-1  " id="endNum" maxlength='4' placeholder="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">

                </td>
                </tr>
                <tr>
                    <th scope="row">사용기간<span class="asterisk "></span></th>
                    <td>
                        <div class="f row text-xs">
                            <div class="input-group date col-4  float-left">
                              <input type="text" id="startDatePicker" readonly="readonly">
                            </div>
                            <div class="float-left ml-1 mr-1 mt-1">-</div>
                            <div class="input-group date col-4  float-left">
                              <input type="text" id="endDatePicker" readonly="readonly">
                            </div>
                            <div style="font-size: 12px;" class="mt-2 ">( 최대 1년까지 설정 가능 )</div>
                  		</div>
                    </td>
                </tr>
                <tr>
                    <th scope="row">개인정보 수집 활동<span class="asterisk "></span></th>
                    <td>
                        ■ 수집하는 기본 개인정보 항목 &nbsp;&nbsp;- 이름, 핸드폰번호, 지점 <br>
                        ■ 개인정보의 수집 및 이용목적 &nbsp;&nbsp;- 계정발급, 계정관리, SMS 문자발송<br>
                        ■ 개인정보의 보유 및 이용기간 &nbsp;&nbsp;- 계정 유효기간 (1년)<br>
                        ■ 개인정보 제공 동의 거부 권리 및 동의 거부 따른 불이익 내용 <br> 
                        <span class="pl-2">&nbsp;&nbsp;- 귀하는 개인정보 제공 동의를 거부할 권리가 있으며, 동의 거부에 따른 불이익은 없음.<br>
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다만 신청내역 및 안내에 대한 SMS문자를 받을 수 없음</span>   <br> 
                        <div class="row mb-1 mt-2">
                            <input type="checkbox" id="check_info" name="check_info" style="width: 20px !important; margin:3px 5px;"> 
                            <span class="mb-0 text-bold"  style="font-size: 13px;">개인정보 수집 및 활용에 동의합니다.</span>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
      </form>

      <div class="text-center text-sm mt-4 mb-3">
        <button type="button" id="btn_save" name="btn_save" class="btn  btn-danger col-2  text-bold">확인</button>
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
