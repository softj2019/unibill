<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

	})

</script>

