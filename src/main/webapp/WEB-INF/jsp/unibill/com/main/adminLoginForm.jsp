<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  /**
  * @Class Name : adminLoginForm.jsp
  * @Description : 관리자 로그인화면
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
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
  <title>${title}</title>
    <!-- Google Font: Source Sans Pro -->
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
  <!-- summernote -->
  <!-- <link rel="stylesheet" href="/dinamicweb/plugins/summernote/summernote-bs4.min.css"> --> 
  <style>
         .admin_login {
         background-image: url(/dinamicweb/dist/img/admin_bg.png);
         background-position: center center;
         background-size: cover;
         }
         .login-logo {text-align: left!important; margin-bottom: 0.2rem; }
         .admin_login_bg { background-image: url(/dinamicweb/dist/img/admin_login_bg.png);}
         .login-card-body {background-color:transparent !important; }
         [class*=icheck-]>input:first-child+input[type=hidden]+label::before, [class*=icheck-]>input:first-child+label::before {
              content: "";
              display: inline-block;
              position: absolute;
              width: 18px;
              height: 18px;
              border: 1px solid #D3CFC8;
              border-radius: 0;
              margin-left: -25px;
          }
  </style>
  
  <!-- jQuery -->
	<script src="/dinamicweb/plugins/jquery/jquery.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="/dinamicweb/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- AdminLTE App -->
	<script src="/dinamicweb/dist/js/adminlte.min.js"></script>

  <% String contextRoot = request.getContextPath(); %>
  <script type="text/javascript">
      var CONTEXT_ROOT = '<%=contextRoot%>';
      var isLiveMode = false;
//    var isLiveMode = true;

      var sFormId = "frm_login";       // form id
      var msg = '<%=request.getParameter("msg")%>';
  	  var failCnt    = '<%=request.getParameter("failCnt")%>';
  	  var failMaxCnt = '<%=request.getParameter("failMaxCnt")%>';
  	  
      if ("loginfail" == msg) {
          alert("일치하는 사용자가 없습니다.");
          //$.unibillDialog.alert('알림', '일치하는 사용자가 없습니다.');
      	//$.unibillDialog.alert('알림', '아이디와 암호를 확인하세요.');
      } else if ("passfail" == msg) {
    	  alert('아이디와 암호를 확인하세요.');
      	//$.unibillDialog.alert('알림', '비밀번호가 일치하지 없습니다.<br>동일 아이디로 로그인 '+failCnt+'회 실패 하였습니다.');
      	//$.unibillDialog.alert('알림', '아이디와 암호를 확인하세요.');
      } else if ("passFailMax" == msg) {
    	  alert('동일 아이디로 로그인 최대실패 회수('+failMaxCnt+')를<br>초과 하였습니다.');
      	//$.unibillDialog.alert('알림', '동일 아이디로 로그인 최대실패 회수('+failMaxCnt+')를<br>초과 하였습니다.');
      } else if ("passChangeYn" == msg) {
          var passChangeYn = '<%=request.getParameter("passChangeYn")%>';

          if ("Y" == passChangeYn) {
              UTILE.fn_confirm("비밀번호 사용기한이 만료되었습니다.<br/>비밀번호를 변경하여야 계속 이용할 수 있습니다.", function () {
//          window.location = '/main/passChangeForm.do';
                  COMMON.fn_openPassChange_pop();
//                  $('#passChange_pop').show();
                  $("#changeUserId").val( $("#userid").val() );
              }, {
                  falseCallback: function () {
                      window.location = '/main/logout.do';
                  }
              });


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

          }

      } else if ("logincnt" == msg) {
          //alert("마지막 로그인일로 부터 90일이 지났습니다.\n관리자에게 문의 바랍니다.");
          alert('장기미사용으로 이용할 수 없습니다.</br>관리자에게 문의 바랍니다.');
          //$.unibillDialog.alert('알림', '장기미사용으로 이용할 수 없습니다.</br>관리자에게 문의 바랍니다.');
      } else if ("ipCheck" == msg) {
          alert('접근할 수 없는 IP입니다.');
          //$.unibillDialog.alert('알림', '접근할 수 없는 IP입니다.');
      } else if ("isNotAuthentic" == msg) {
          //$.unibillDialog.alert('알림', '접근 권한이 없는 사용자입니다.');
      } else if ("logout" == msg) {
    	  alert("로그아웃 되었습니다.");   
    	  //$.unibillDialog.alert('알림', '로그아웃 되었습니다.');
      } else if("dupLogout" == msg){
    	alert('동일 사용자가 로그인 하였습니다.');
      	//$.unibillDialog.alert('알림', '동일 사용자가 로그인 하였습니다.');
      }

      $(document).ready(function() {

          if( !UTILE.fn_isBlank( Cookies.get('cookieSaveUserId') ) ){
              $("#isIdSave").prop('checked', true);
              $("#userid").val( Cookies.get('cookieSaveUserId') );
          }else{
              $("#isIdSave").prop('checked', false);
              $("#userid").val( "" );
              $("#pass").val( "" );
          }

          $('#pass').keypress(function(event) {
              if ( event.which == 13 ) {
//                event.preventDefault();
                  doLogin();

              }
          });

          $("#isIdSave").click(function () {

              var chk = $("#isIdSave").is(":checked");//.attr('checked');
              var _userid = $("#userid").val();

              if(chk){
                  // checked
                  UTILE.fn_log("isIdSave).click ==>> 2");
                  Cookies.set('cookieSaveUserId', _userid, { expires:365 , path:'/' });
              }else{
                  UTILE.fn_log("isIdSave).click ==>> 3");
                  // unchecked
                  Cookies.remove('cookieSaveUserId');
              }
          })
      });

      function doLogin(){
          var chk = $("#customCheckbox5").is(":checked");//.attr('checked');
          var _userid = $("#userid").val();
          if(chk){
              // checked
              Cookies.set('cookieSaveUserId', _userid, { expires:365 , path:'/' });
          }else{
              // unchecked
              Cookies.remove('cookieSaveUserId');
          }

          var todayDate = new Date();
          todayDate.setDate( todayDate.getDate() + 1 );
          UTILE.fn_log2("Cookies.get('cookieSaveUserId') " , Cookies.get('cookieSaveUserId') );
          UTILE.fn_log2("todayDate.toGMTString() " , todayDate.toGMTString() );

          $("#" + sFormId).submit();
      }
  </script>
</head>
<body class="hold-transition login-page  admin_login">
<form id="frm_login" name="frm_login" action="<c:url value='/main/login.do'/>" method="POST">
<div class="login-box" style="width:800px !important;">
  <div class="login-logo">
    <img src="/dinamicweb/dist/img/simbol.png"><img src="/dinamicweb/dist/img/logo.png">
    <!--<span style="color:#d40b00; font-size:20px; letter-spacing: -1px; padding-left:10px;"> SMART 청약시스템 </span>-->
  </div>
  <!-- /.login-logo -->
  <div class="card admin_login_bg" style="height:400px;">
    <div class="card-body login-card-body">
    <div row>
        <div class="col-6 float-left">
      
        </div>
        <div class="col-6 float-right ">
          <div class="col-11 mt-5">
            <h5 class="float-left mb-3  text-bold">관리자 로그인</h5>

            <%-- <form action="../../index3.html" method="post"> --%>
              <div class="mb-1 ">
                <input type="ID" class="form-control text-sm" placeholder="ID" id="userid" name="userid">
               
              </div>
              <div class=" mb-1 ">
                <input type="password" class="form-control text-sm" placeholder="Password" id="pass" name="pass">
                
              </div>
              <div class="row">
                <div class="col-12 mb-2">
                  <div class="custom-control custom-checkbox float-right">
                    <input class="custom-control-input custom-control-input-danger custom-control-input-outline" type="checkbox" id="customCheckbox5" checked>
                    <label for="customCheckbox5" class="custom-control-label text-xs pt-1">아이디 저장</label>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-12 mb-4">
                  <button type="submit" class="btn btn-danger btn-block text-bold text-md " onclick="javascript:doLogin();">로그인</button>
                </div>
                <!-- /.col -->
              </div>
            <%-- </form> --%>
      
            <div class="row  mb-5">
              <div class="col-12">
                  <div class="col-6 float-left pl-0"><a href="<c:url value='/main/findIdPw.do'/>" class="btn btn-sm  btn-block btn-outline-secondary text-xs">아이디 &middot; 비밀번호 찾기</a></div>
                  <div class="col-6 float-left pr-0 pl-0"><a href="<c:url value='/main/applyAuthority.do'/>" class="btn btn-sm  btn-block btn-outline-secondary text-xs ">권한신청</a></div>
              </div>
            </div>
            <div class="row">
              <div class="col-12 text-center" style="font-size:10px;">Copyright &copy; 2022 SHINSEGAE I&C INC. All Rights Reserved.</div>

            </div>
            <!-- /.social-auth-links -->
      
      
           <!-- <label class="link mt-3 small">
              <a href="#" class="mr-1"> 이용약관 </a> ❘
              <a href="#" class="ml-1 mr-1"> 개인정보처리방침 </a> ❘
              <a href="#" class="ml-1"> 매뉴얼 </a>
            </label>-->
          </div>
        </div>
    </div>





    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->
</form>
</body>
</html>