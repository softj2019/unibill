<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	 
	 <% String contextRoot = request.getContextPath(); %> 
	<script>
        var CONTEXT_ROOT = '<%=contextRoot%>';
        var isLiveMode = false;
        var topMenuCloseYn = "N";
	</script>
	
	<style>
	    .small { font-size: 80% !important;}
	    .form-group {margin-bottom: .2rem !important;}
	    .card-title {float: left;font-size: 1rem !important;font-weight: 700 !important;margin: 0;}
	    li {line-height:22px !important;}
	    /* .card-body {padding:0.5rem 2rem!important;}  */
	    .card-header {padding:0.5rem 2rem!important;}
	    .content-header{padding:0rem !important;} 
	    /*  .content-header{padding:1rem 2.5rem 0.5rem!important;} */ 
	    ul {padding-inline-start: 10px;}
	    .nav-link {display: block; padding:.3rem 1rem;}
	    .dm {background-color: rgba(2, 2, 2, 0.27); display: block;} /* 메뉴  배경컬러*/
	    .myinfo table th,.myinfo table td {padding: 0.5rem 0.7rem 0.5rem 1rem !important; vertical-align:middle !important; border-top: 1px solid #dee2e6 !important;}
	    .myinfo table th {background-color:#f9f9f9!important;}
	    button {font-family: 'NotoSansR','NotoSansM', 'NotoSansB', 'NotoSansL', Verdana,'MalgunGothic', '맑은 고딕',Tahoma, sans-serif; letter-spacing: -.5px;}
	    .checktable table th,.checktable table td {padding: 0.2rem !important; vertical-align:top !important; border: 0 !important;}
	    .checktable table th {color: #dc3545 !important; font-weight:bold; text-align: center;}
	    .menu-open > .nav-link > p > i{color:#ff5c6c !important; font-weight: bold !important;}
	  </style>
	  
	<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/css/common.css'/>"/> --%>

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
	<script type="text/javascript" src="<c:url value='/js/main.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/utile.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/grid.js'/>"></script>	
	
	<script type="text/javascript" src="<c:url value='/js/custom.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/custag.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/otp.js'/>"></script>			
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

<tiles:insertAttribute name="header" />

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
          <div class="row">
            <!-- SSS-->
         <div id="header">
            <div id="sticky-anchor"></div> 
        </div>
        <!--EEE-->
            
          </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
         <!-- Default box -->
      <div class="card card-solid">
        <div class="card-body">
          <div class="row">
            <div class="col-12">
             	<tiles:insertAttribute name="content" />
						</div>
          </div>
        </div>
        <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<tiles:insertAttribute name="footer" />

</div>
<!-- ./wrapper -->
</body>
</html>