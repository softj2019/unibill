<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">

	$( document ).ready(function() {
		
	
		
	
		
});
	
	
		

	
	
	

	
</script>



<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Log in</title>

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
  
  <link rel="stylesheet" type="text/css" href="/dinamicweb/dist/css_1/common.css" />
</head>
<body class="hold-transition login-page">
<div class="login-box" style="width: 500px !important;">
  <!--<div class="login-logo">
    <a href="index2.html"><b>Admin</b>LTE</a>
  </div>-->
  <!-- /.login-logo -->
  <div class="card card-danger">

        <div class="card-header">
            <div class="card-title text-md text-bold">SMS 인증</div>
        </div>

    <div class="card-body login-card-body">
      <ul class="mb-3 text-center mt-2 ">
        <li class="text-danger font-weight-bold">등록된 휴대폰 번호로 SMS를 발송했습니다.</li>
        <li>본인 확인을 위해 SMS로 발송된 인증번호를 입력해 주세요.</li>
      </ul>

      <form action="../../index3.html" method="post">
        <table summary="등록/상세정보" class="tb02 pt-2 pb-2" style="background-color: rgb(247, 247, 247);">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="130px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                <td scope="row" class="text-right border-0 pr-0 font-weight-bold"><i class="fas fa-lock mr-2 text-md"></i>인증번호</td>
                <td class=" border-0">
                    <input type="text" class="form-control form-control-sm col-6 col-sm-6 float-left mr-2 small " id="inputName" placeholder="">
                    <a href="#" class="btn btn-sm  btn-default col-3 font-weight-bold" onclick="fn_confOTP()">확인</a>
                </td>
                </tr>
            </tbody>
        </table>
      </form>

      <div class="text-center text-sm mt-4 mb-3">
        <a href="#" class="btn  btn-danger col-4  text-bold">인증번호 재요청</a>
        <a href="#" class="btn  btn-dark col-4 mr-1  text-bold">뒤로 가기</a>
      </div>
      <!-- /.social-auth-links -->
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="/dinamicweb/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="/dinamicweb/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="/dinamicweb/dist/js/adminlte.min.js"></script>
</body>
</html>





			    