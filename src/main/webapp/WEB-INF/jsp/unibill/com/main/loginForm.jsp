<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  /**
  * @Class Name : loginForm.jsp
  * @Description : 로그인화면
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
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
  <title>${title}</title>
  
	<!-- Google Font: Source Sans Pro -->
  <!-- Font Awesome -->
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
    <!-- Google Font: Source Sans Pro -->
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/dinamicweb/plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
  <!-- <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"> -->
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
    .content-header {padding: 15px 0.5rem 0px;}
    .table td, .table th {
    padding: 0.3rem 0.75rem;
    vertical-align: top;
    border-bottom: 1px solid #dee2e6;}
    .link a {color: #212529;text-decoration: none;background-color: transparent;}
    .link a:hover { color: #ff2329; text-decoration: none;}
    .logo_sinc { width: 182px; height: 23px; margin-bottom:8px;}
    .scrollable {max-height:430px; overflow-y: auto;}
    ul li {list-style: none; font-weight: bold; line-height: 22px;}
  </style>
  <!-- jQuery -->
	<script src="plugins/jquery/jquery.min.js"></script>
	<!-- Bootstrap 4 -->
	<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- AdminLTE App -->
	<script src="dist/js/adminlte.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="<c:url value='/extLib/jquery-ui-themes-1.12.1/themes/base/theme.css'/>" />
  
  <script type="text/javascript" src="<c:url value='/extLib/js-cookie/src/js.cookie.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-1.11.1.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/extLib/jquery/plugins/blockUI/jquery.blockUI.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.unibillDialog.js'/>"></script>
  
  <script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/js/utile.js'/>"></script>

  <% String contextRoot = request.getContextPath(); %>
  <% String passChangeYn = (String)request.getParameter("passChangeYn"); %>
  <script type="text/javascript">
      var CONTEXT_ROOT = '<%=contextRoot%>';
      var isLiveMode = false;
//    var isLiveMode = true;

      var sFormId = "frm_login";       // form id
      
      
      $('#frm_login').serialize()
      function doLogin(){
          var chk = $("#isIdSave").is(":checked");//.attr('checked');
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
          if($("#userid").val()==""){
        	  $.unibillDialog.alert('알림', '아이디를 입력하세요.');
        	  $("#userid").focus();
        	  return false;
          }
          if($("#pass").val()==""){
        	  $.unibillDialog.alert('알림', '비밀번호를 입력하세요.');
        	  $("#pass").focus();
        	  return false;
          }
          $("#" + sFormId).submit();
      }


      function passChangePopClose(){
      <c:choose>
        <c:when test="${'Y' eq passChangeYn}">
          window.location = '/main/logout.do';
        </c:when>
        <c:otherwise>
          $('#passChange_pop').hide();
        </c:otherwise>
      </c:choose>
      }
      
      var msg        = '<%=request.getParameter("msg")%>';
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

          $('#applySubscription').on('click',function(){
            location.href='/main/applyTermsOfUse.do'
          })

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
          
          $('#userid').keypress(function(event) {
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
          });
          
/* 			if (navigator.userAgent.toUpperCase().indexOf("WOW64") != -1 || navigator.userAgent.toUpperCase().indexOf("WIN64") != -1){	// 64bit
				$('#isChromeDown').show();
				$(".link_wrap > span.join > a").prop("href", "/ieDown/ChromeStandaloneSetup64.exe");
				$(".link_wrap > span.join > a").prop("title", "크롬 브라우저 다운로드(64비트용)");
				$(".link_wrap > span.join > a").prop("text", "크롬 브라우저 다운로드(64비트용)");
			} else {	// 32bit
				$('#isChromeDown').show();
				$(".link_wrap > span.join > a").prop("href", "/ieDown/ChromeStandaloneSetup.exe");
				$(".link_wrap > span.join > a").prop("title", "크롬 브라우저 다운로드(32비트용)");
				$(".link_wrap > span.join > a").prop("text", "크롬 브라우저 다운로드(32비트용)");
			} */
			
      });

  </script>
</head>
<body class="hold-transition sidebar-mini">
<form id="frm_login" name="frm_login" action="<c:url value='/main/login.do'/>" method="POST">
<div class="wrapper">
  <!-- Navbar -->

  <!-- /.navbar -->

  <!-- Main Sidebar Container -->


  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper" style="margin-left:0 !important; background-color:#fff !important;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-12">
            <div class="m-0"><a href=""><img src="/dinamicweb/dist/img/logo_sinc_main.png " class="  float-left"></a> </div>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <div style="width:100%; padding:0 !important;">
      <div class="col-12 mb-3" style=" padding:0 !important;">
        
        <div style="position:relative;  height:300px !important; 
        background-image: url(/dinamicweb/dist/img/main_bg_01.jpg);
        background-position: center center;
        background-size: cover;">
         <div style="position:absolute; width:100%; height:100%; background:#5c3d3e ; opacity: .83;/* Old browsers */
         background: linear-gradient(to top right,  #721518 0%,#070707 100%);  /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
         background: -moz-linear-gradient(right,  #721518 0%,#070707 100%);  /* FF3.6-15 */
         background: -webkit-linear-gradient(right,  #721518 0%,#070707 50%);  /* Chrome10-25, Safari5.1-6 */
         filter: progid: DXImageTransform.Microsoft.gradient( startColorstr='#5c3d3e', endColorstr='#070707',GradientType=0 );  /* IE6-9 */">
        </div>
        <div style="position:absolute; width:100%; text-align:center; margin-top:100px; color:#ededed;">
          <h4 class=""> <!--문자를 한번에 쓱-. 전송하세요.--> </h4>
          <h1> <!--편리함의 끝, 문자쓱--></h1>
        </div>
        
      </div>
    </div>


    <!-- Main content -->
    <div class="content pb-3">
      <div class="container-fluid">
        <div class="row">



          <div class="col-lg-3 ">
            <div class="card " style="height:170px;">
              <div class="card-body" >
                <div class="row">
                <h6 class="col-12 text-bold m-0 mb-3  ">전화 &middot; 인터넷 청약 신청</h6>
                  <div class="form-group col-12">
                    <label class="col-12 text-sm  mb-2 ">신규 신청 &middot; 명의변경 신청 </label>
                    <button type="button" id="applySubscription" class="btn btn btn-danger  col-12 text-bold mb-3">신청하기</button>
                  </div>
                </div>
              </div>
            </div>


            <div class="card" style="height:280px;">
              <div class="card-body" >
                <div class="row">
                  <h6 class="col-12 text-bold m-0 mb-3  ">로그인</h6>
                  <span class="col-12 text-xs mb-3">
                    가입정보확인 &middot; 요금조회 &middot; 정보변경<br> (담당자/매장이전/서비스추가/계좌변경/해지신청)
                  </span>
                  <div class="form-group col-12">
                      <input type="id" class="form-control form-control-sm  col-12 mb-1" id="inputName" placeholder="아이디" id="userid" name="userid">
                      <input type="Password" class="form-control form-control-sm col-12 mb-1" id="inputEmail" placeholder="비밀번호" id="pass" name="pass">
                      <button type="submit" class="btn btn btn-secondary col-12 text-bold mb-2 " onclick="doLogin()">로그인</button>
                      <div class="row">
                        <div class="col-4">
                            <input class="check-input" type="checkbox" id="isIdSave">
                            <label class="text-xs">아이디 </label>
                        </div>
                        <div class="col-8 text-right">
                            <label class="text-xs link">
                              <a href="#" class="mr-1"> 아이디 </a> &#10072;
                              <a href="#" class="ml-1"> 비밀번호 찾기</a>
                            </label>
                        </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col-md-4 -->



          <div class="col-lg-6">
            <div class="card"  style="height:280px;">
              <div class="card-body">
                <div class="row">
                  <div class="col-12 link">
                    <h6 class=" mb-4 text-bold   float-left">공지사항</h6>
                    <a href="" class=" mt-1 text-bold float-right small"> 바로가기<i class="fas fa-caret-right ml-1"></i></a>
                  </div>
                    <div class="form-group col-12">
                      <table class="table d table-hover small">
                        <tbody>
                          <tr>
                            <td>신규, 명의변경 청약신청 방법 안내</td>
                            <td> 2021.03.23  </td>
                          </tr>
                          <tr>
                            <td>자동이체 신청시 [신청] 버튼을 클릭 후 반응이 없는 경우</td>
                            <td>  2021.02.08  </td>
                          </tr>
                          <tr>
                            <td>통신가입증명서 요청방법</td>
                            <td>  2021.04.21  </td>
                          </tr>
                          <tr>
                            <td>통화연결음 신청 방법</td>
                            <td>  2021.04.21  </td>
                          </tr>
                          <tr>
                            <td>담당자 정보 업데이트 요청 안내</td>
                            <td>  2020.12.24  </td>
                          </tr>
                          <tr>
                            <td>보증금 환불 방법 및 (일정) 절차 안내</td>
                            <td>  2021.07.22  </td>
                          </tr>                          
                        </tbody>
                      </table>
                    </div>
                </div>
              </div>
            </div>
            <!-- /.card -->
            <div class="card"  style="height:170px;">
              <div class="card-body">
                <div class="row">
                  <h6 class="col-12 mb-3 text-bold  ">상담문의</h6>
                </div>
                <div class="row small" style=" color:rgb(78, 78, 78);">
                    <div class="col-5  col-sm-3 pl-2" >
                        <label>02-3397-1480</label><br>
                        <label>02-2064-5311</label>
                    </div>
                    <div class="col-7  col-sm-5">
                        <label>평일 : 09:00 ~ 18:00</label><br>
                        <label>점심 : 12:00 ~ 13:00</label>
                    </div>
                    <div class="col-12 col-sm-4">
                        <button class="btn btn-block btn-outline-danger col-12 text-sm text-bold pt-2 pb-2"> 
                        <i class="fas fa-arrow-alt-circle-right mr-1"></i> 1 : 1 문의
                        </button>
                    </div>
                 </div>
                
              </div>
                <!-- /.d-flex -->
            </div>
          </div>
          <!-- /.col-md-4 -->
          <div class="col-lg-3">
            <div class="card" style="height:465px;">
              <div class="card-body scrollable">
                <div class="row">
                <h6 class="col-12 mb-3 text-bold  ">백화점 통신실 연락처</h6>
                <table class="w-100 small ml-1">
                  <tr>
                    <td>본점/면세점/명동점</td>
                    <td>02-310-1110</td>
                  </tr>
                  <tr>
                    <td>강남점</td>
                    <td>02-3479-1110</td>
                  </tr>
                  <tr>
                    <td>타임스퀘어점</td>
                    <td>02-2639-1110</td>
                  </tr>
                  <tr>
                    <td>마산점</td>
                    <td>055-240-1110</td>
                  </tr>
                  <tr>
                    <td>경기점</td>
                    <td>031-695-1110</td>
                  </tr>
                  <tr>
                    <td>센텀점/센텀시티몰</td>
                    <td>051-745-1110</td>
                  </tr>
                  <tr>
                    <td>면세점 센텀점</td>
                    <td>051-745-1110</td>
                  </tr>
                  <tr>
                    <td>의정부점</td>
                    <td>031-8082-1110</td>
                  </tr>
                  <tr>
                    <td>김해점</td>
                    <td>055-272-1110</td>
                  </tr>
                  <tr>
                    <td>광주점</td>
                    <td>062-360-1110</td>
                  </tr>
                  <tr>
                    <td>대구점</td>
                    <td>053-661-1110</td>
                  </tr>
                  <tr>
                    <td>스타일마켓</td>
                    <td>02-3397-1480</td>
                  </tr>
                  <tr>
                    <td>스타필드 하남</td>
                    <td>031-8072-1110</td>
                  </tr> 
                  <tr>
                    <td>스타필드 고양</td>
                    <td>031-5173-1110</td>
                  </tr>
                  <tr>
                    <td>대전백화점</td>
                    <td>042-607-1110</td>
                  </tr> 
                  
                </table>
                <!-- /.d-flex -->
              </div>
            </div>
            <!-- /.card -->
            <div class="card-footer" >
              <div class="text-xs text-danger text-bold">
                인터넷 장애접수 &middot; 문의 02-2283-3400
              </div>
            </div>

          </div>
          <!-- /.col-md-4 -->          
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <footer class="main-footer" style="margin-left: 0 !important;" >
    <div class="float-lg-right small">
      <label class=" link mb-2">
        <a href="#" class="mr-1"> 이용약관 </a> ❘
        <a href="#" class="ml-1 mr-1"> 개인정보처리방침 </a> ❘
        <a href="#" class="ml-1"> 매뉴얼 </a>
      </label>
    </div>
    <div class="d-none text-xs d-sm-inline-block small ">
      <b style="color:#000;" class="pb-1">(주)신세계아이앤씨</b><br>
      서울특별시 중구 남대문시장10길2 메사빌딩 21층  &#10072; 대표자: 손정현
      통신판매업 신고번호: 제 2015-서울중구-0927<br>
      고객센터 : 02-3397-1480 &#10072; 팩스 : 02-3397-1398 &#10072; Email: billing@shinsegae.com <br>
      Copyrightⓒ 2022 SHINSEGAE I&amp;C INC. All Rights Reserved.<br>
    </div>
   
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->

  <!-- Main Footer -->
 <!-- <footer class="main-footer" style="margin-left:0 !important;">
    <strong>Copyright &copy; 2014-2021 <a href="https://adminlte.io">AdminLTE.io</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
      <b>Version</b> 3.1.0
    </div>
  </footer>-->
</div>
<!-- ./wrapper -->
</div>
</div>
</form>

</body>
</html>