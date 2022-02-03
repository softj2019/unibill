<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String contextRoot = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${title}</title>

	<!-- Google Font: Source Sans Pro -->
	<!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">-->
	<!-- Font Awesome -->
	<link rel="stylesheet" href="/dinamicweb/plugins/fontawesome-free/css/all.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
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
	<link rel="stylesheet" href="/dinamicweb/lugins/daterangepicker/daterangepicker.css">
	<!-- summernote -->
	<link rel="stylesheet" href=/dinamicweb/plugins/summernote/summernote-bs4.min.css">
	<link type="text/css" rel="stylesheet" href="<c:url value='/extLib/jquery-ui-themes-1.12.1/themes/base/theme.css'/>" /><!-- jqgrid 필터에 필요함 -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/extLib/free-jqgrid/4_13_5/css/ui.jqgrid.min.css'/>" />
	<style>
		.small { font-size: 80% !important;}
		/*label {margin-bottom: 0.2rem !important;}*/
		.form-group {margin-bottom: .2rem !important;}
		.card-title {float: left;font-size: 1rem !important;font-weight: 700 !important;margin: 0;}
		li {line-height:22px !important;}
		.card-body {padding:0.5rem 2rem!important;}
		.card-header {padding: 0.5rem 2rem!important;}
		.content-header{padding:1rem 2.5rem 0.5rem!important;}
		ul {padding-inline-start: 10px;}
		.nav-link {display: block; padding:.3rem 1rem;}
		.dm {background-color: rgba(2, 2, 2, 0.27); display: block;} /* 메뉴  배경컬러*/
		@media (min-width: 768px){
			.content-wrapper,.main-header ,.main-footer{transition: none; margin-left: 0px !important;}
		}
		table td {vertical-align: top !important; padding-bottom: 2px;}
	</style>
	<script>
		var CONTEXT_ROOT = '<%=contextRoot%>';
	</script>
</head>
<body class="sidebar-mini layout-fixed">
<%--스크립트 정의 안되어서 오류나는 목록--%>
<div class="main-sidebar" style="display: none"></div>
<div class="brand-link" style="display: none"></div>
<%--스크립트 정의 안되어서 오류나는 목록--%>
<div class="wrapper">

	<!-- Navbar -->
	<nav class="main-header navbar navbar-expand navbar-white navbar-light" style=" background-image: linear-gradient(120deg, #000000, #dc3545);">
		<!-- Left navbar links -->
		<ul class="navbar-nav">
			<li class="nav-item">
				<a class="nav-link" data-widget="pushmenu" href="#" role="button"></a>
			</li>
			<li class="nav-item d-sm-inline-block">
				<div class="m-0"> <img src="/dinamicweb/dist/img/simbol.png"><img src="/dinamicweb/dist/img/logo.png"></div>
			</li>
		</ul>

	</nav>
	<!-- /.navbar -->

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">


			<tiles:insertAttribute name="content" />

	</div>

	<footer class="main-footer text-xs text-center text-lg-left">
		<strong>Copyright &copy; 2022 <a href="https://adminlte.io">SHINSEGAE I&C INC.</a>.</strong>
		All Rights Reserved.
		<div class="float-right d-none d-sm-inline-block">
			<div class="float-lg-right">
				<label class=" link mb-2">
					<a href="#" class="mr-1"> 이용약관 </a> ❘
					<a href="#" class="ml-1 mr-1"> 개인정보처리방침 </a> ❘
					<a href="#" class="ml-1"> 매뉴얼 </a>
				</label>
			</div>
		</div>

	</footer>

	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark">
		<!-- Control sidebar content goes here -->
	</aside>
	<!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="/dinamicweb/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="/dinamicweb/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
	$.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="/dinamicweb/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Select -->
<script src="/dinamicweb/plugins/select2/js/select2.full.min.js"></script>
<!-- ChartJS -->
<script src="/dinamicweb/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="/dinamicweb/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="/dinamicweb/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="/dinamicweb/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>

<!-- daterangepicker -->
<script src="/dinamicweb/plugins/moment/moment.min.js"></script>
<script src="/dinamicweb/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="/dinamicweb/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="/dinamicweb/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="/dinamicweb/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="/dinamicweb/dist/js/adminlte.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="/dinamicweb/dist/js/demo.js"></script>



<!--new-->

<!-- Select2 -->
<script src="/dinamicweb/plugins/select2/js/select2.full.min.js"></script>
<!-- Bootstrap4 Duallistbox -->
<script src="/dinamicweb/plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
<!-- InputMask -->
<script src="/dinamicweb/plugins/inputmask/jquery.inputmask.min.js"></script>
<!-- date-range-picker -->
<!-- bootstrap color picker -->
<script src="/dinamicweb/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<!-- Bootstrap Switch -->
<script src="/dinamicweb/plugins/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<!-- BS-Stepper -->
<script src="/dinamicweb/plugins/bs-stepper/js/bs-stepper.min.js"></script>
<!-- dropzonejs -->
<script src="/dinamicweb/plugins/dropzone/min/dropzone.min.js"></script>
<!-- AdminLTE App -->
<!-- AdminLTE for demo purposes -->
<!-- Page specific script -->
<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.form.js'/>"></script>
<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.unibillDialog.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/custag.js'/>"></script>
<script>
	$(function () {

		//Initialize Select2 Elements
		$('.select2').select2()

		//Initialize Select2 Elements
		$('.select2bs4').select2({
			theme: 'bootstrap4'
		})

		//Datemask dd/mm/yyyy
		$('#datemask').inputmask('dd/mm/yyyy', { 'placeholder': 'dd/mm/yyyy' })
		//Datemask2 mm/dd/yyyy
		$('#datemask2').inputmask('mm/dd/yyyy', { 'placeholder': 'mm/dd/yyyy' })
		//Money Euro
		$('[data-mask]').inputmask()

		//Date picker
		$('#reservationdate').datetimepicker({
			format: 'L'
		});

		//Date picker
		$('#reservationdate1').datetimepicker({
			format: 'L'
		});


		//Date and time picker
		$('#reservationdatetime').datetimepicker({ icons: { time: 'far fa-clock' } });

		//Date range picker
		$('#reservation').daterangepicker()
		//Date range picker with time picker
		$('#reservationtime').daterangepicker({
			timePicker: true,
			timePickerIncrement: 30,
			locale: {
				format: 'MM/DD/YYYY hh:mm A'
			}
		})
		//Date range as a button
		$('#daterange-btn').daterangepicker(
				{
					ranges   : {
						'Today'       : [moment(), moment()],
						'Yesterday'   : [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
						'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
						'Last 30 Days': [moment().subtract(29, 'days'), moment()],
						'This Month'  : [moment().startOf('month'), moment().endOf('month')],
						'Last Month'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
					},
					startDate: moment().subtract(29, 'days'),
					endDate  : moment()
				},
				function (start, end) {
					$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
				}
		)

		//Timepicker
		$('#timepicker').datetimepicker({
			format: 'LT'
		})

		//Bootstrap Duallistbox
		$('.duallistbox').bootstrapDualListbox()

		//Colorpicker
		$('.my-colorpicker1').colorpicker()
		//color picker with addon
		$('.my-colorpicker2').colorpicker()

		$('.my-colorpicker2').on('colorpickerChange', function(event) {
			$('.my-colorpicker2 .fa-square').css('color', event.color.toString());
		})

		$("input[data-bootstrap-switch]").each(function(){
			$(this).bootstrapSwitch('state', $(this).prop('checked'));
		})
		$("#check_id").click(function () {
			CUSTAG.fn_checkId("user_id");
		});
	})

</script>
