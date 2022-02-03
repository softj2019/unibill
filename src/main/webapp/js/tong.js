/**
 *  통계에 사용되는 함수
 * @namespace {Object} UNI.TONG
 */
UNI.TONG = (function(_mod_tong, $, undefined){

	
	
	/**
	* @method
	* @description 통계 정보
	* @author 홍성원(2018.10.11)
	*/

	
	// 
	fn_dayTelCostTong_init = function(){
		var d = new Date();
//		alert("curDate : " + d.getFullYear() + "년 " + (d.getMonth() + 1) + "월 " + d.getDate() + "일");
		
		var curMonth = "";
		var curDay = "";
		// 현재 월
		if((d.getMonth() + 1) < 10){
			curMonth = "0" + (d.getMonth() + 1);
		}else{
			curMonth = (d.getMonth() + 1);
		}
		//현재 일
		if(d.getDate() < 10){
			curDay = "0" + d.getDate();
		}else{
			curDay = d.getDate();
		}
		
		var curDate = d.getFullYear() + "-" + curMonth + "-" + curDay;
		var fromDate = d.getFullYear() + "-" + curMonth + "-01";
		
		//검색조건 일자 설정
		$(".srh_wrap").find("#from_ymd_datepicker").val(fromDate);
		$(".srh_wrap").find("#to_ymd_datepicker").val(curDate);
		
		//검색조건 번호타입 설정
		$(".srh_wrap").find('#search_num_type:radio[value="1"]').prop('checked', true);
		
	}
	
	fn_monCostTong_init = function(){
		var d = new Date();
		var curyear ="";
		var curmonth = "";
		var preyear = "";
		var premonth = "";
		
		// 현재 월
		curyear = d.getFullYear();
		if((d.getMonth() + 1).length == 1){
			curmonth = "0" + (d.getMonth() + 1);
		}else{
			curmonth = (d.getMonth() + 1);
		}
		
		// 이전 년월
		d.setMonth(d.getMonth() - 1);
		preyear = d.getFullYear();
		if((d.getMonth() + 1) < 10){
			premonth = "0" + (d.getMonth() + 1);
		}else{
			premonth = (d.getMonth() + 1);
		}
		
		$(".srh_wrap").find("#bill_ym_yyyy").val(preyear);
		$(".srh_wrap").find("#bill_ym_mm").val(premonth);
		
		$(".srh_wrap").find("#bill_ym_yyyy1").val(curyear);
		$(".srh_wrap").find("#bill_ym_mm1").val(curmonth);
		
	}
	
	
	fn_dayCostTong_init = function(){
		var d = new Date();
		var curMonth = "";
		// 현재 월
		if((d.getMonth() + 1) < 10){
			curMonth = "0" + (d.getMonth() + 1);
		}else{
			curMonth = (d.getMonth() + 1);
		}
		
		var fromDate = d.getFullYear() + "-" + curMonth + "-01";
		$(".srh_wrap").find("#from_ymd_datepicker").val(fromDate);
		//검색조건 번호타입 설정
		$(".srh_wrap").find('#search_num_type:radio[value="1"]').prop('checked', true);
		
	}
	
	fn_MonTelCostTong_init = function(){
		var d = new Date();
		var curyear ="";
		var curmonth = "";
		var preyear = "";
		var premonth = "";
		
		// 현재 월
		curyear = d.getFullYear();
		if((d.getMonth() + 1) < 10){
			curmonth = "0" + (d.getMonth() + 1);
		}else{
			curmonth = (d.getMonth() + 1);
		}
		
		// 이전 년월
		d.setMonth(d.getMonth() - 1);
		preyear = d.getFullYear();
		if((d.getMonth() + 1) < 10){
			premonth = "0" + (d.getMonth() + 1);
		}else{
			premonth = (d.getMonth() + 1);
		}
		
		$(".srh_wrap").find("#bill_ym_yyyy").val(preyear);
		$(".srh_wrap").find("#bill_ym_mm").val(premonth);
		
		$(".srh_wrap").find("#bill_ym_yyyy1").val(curyear);
		$(".srh_wrap").find("#bill_ym_mm1").val(curmonth);
		
		//검색조건 번호타입 설정
		$(".srh_wrap").find('#search_num_type:radio[value="1"]').prop('checked', true);
		
		
	}
	
	
	
	fn_tongInOut_init = function(){
		var d = new Date();
		var curyear ="";
		var curmonth = "";
		var preyear = "";
		var premonth = "";
		
		// 현재 월
		curyear = d.getFullYear();
		if((d.getMonth() + 1).length == 1){
			curmonth = "0" + (d.getMonth() + 1);
		}else{
			curmonth = (d.getMonth() + 1);
		}
		
		// 이전 년월
		d.setMonth(d.getMonth() - 1);
		preyear = d.getFullYear();
		if((d.getMonth() + 1) < 10){
			premonth = "0" + (d.getMonth() + 1);
		}else{
			premonth = (d.getMonth() + 1);
		}
		
		$(".srh_wrap").find("#bill_ym_yyyy").val(preyear);
		$(".srh_wrap").find("#bill_ym_mm").val(premonth);
		
		$(".srh_wrap").find("#bill_ym_yyyy1").val(curyear);
		$(".srh_wrap").find("#bill_ym_mm1").val(curmonth);
		
	}
	
	fn_tongGrp_init = function(){
		var d = new Date();
		var curyear ="";
		var curmonth = "";
		var preyear = "";
		var premonth = "";
		
		// 현재 월
		curyear = d.getFullYear();
		if((d.getMonth() + 1).length == 1){
			curmonth = "0" + (d.getMonth() + 1);
		}else{
			curmonth = (d.getMonth() + 1);
		}
		
		// 이전 년월
		d.setMonth(d.getMonth() - 1);
		preyear = d.getFullYear();
		if((d.getMonth() + 1) < 10){
			premonth = "0" + (d.getMonth() + 1);
		}else{
			premonth = (d.getMonth() + 1);
		}
		
		$(".srh_wrap").find("#bill_ym_yyyy").val(preyear);
		$(".srh_wrap").find("#bill_ym_mm").val(premonth);
		
		$(".srh_wrap").find("#bill_ym_yyyy1").val(curyear);
		$(".srh_wrap").find("#bill_ym_mm1").val(curmonth);
	}
	
	
	fn_userCustidFix = function(){
		var rolecode = $("#topForm").find("#role_code").val();
		if(rolecode == 'D001'){
			
		}else{
			$(".srh_wrap").find("#cust_id").val($("#topForm").find("#cust_id").val());
			$(".srh_wrap").find("#cust_id").attr("readonly",true);
			
			$(".srh_wrap").find("#request_date_datepicker").val("");
			$(".srh_wrap").find("#plan_date_datepicker").val("");
			
		}
		
		
	}
	
	
	fn_MBMonTelCostTong_init = function(){
		var rolecode = $("#topForm").find("#role_code").val();
		
		if(rolecode.indexOf("U") > -1){
			$(".srh_wrap").find("#rep_cust_id").val($("#topForm").find("#cust_id").val());
			$(".srh_wrap").find("#rep_cust_id").attr("readonly",true);
			$(".srh_wrap").find("#cust_id").attr("readonly",true);
		}
	}
	
	fn_MBMonCustCostTong_init = function(){
		var rolecode = $("#topForm").find("#role_code").val();
		
		if(rolecode.indexOf("U") > -1){
			$(".srh_wrap").find("#rep_cust_id").val($("#topForm").find("#cust_id").val());
			$(".srh_wrap").find("#rep_cust_id").attr("readonly",true);
		}
	}
	
	
	fn_MonJojikSumTong_init =function(){
		$(".srh_wrap").find("#search_col_type:checkbox").each(function(){
			$(this).prop("checked",true);
		});
		
		var checked = $(".srh_wrap").find(":checkbox[name='search_col_type']:checked").length;
		  if (checked == 0) {			  
			  $(".srh_wrap").find("#mult_check_search_col_type").val("");
		  } else {
			  $(".srh_wrap").find("#mult_check_search_col_type").val(checked);
		  }	
		
		
	}
	
	
	fn_tooltip = function(){
//		alert("111");
//		$('#div_search_telno').mouseover(function(event) {
//			$('<div class="tooltip">test</div>').appendTo('body');
//			var tPosX = event.pageX + 20;
//		    var tPosY = event.pageY + 10;
//		    $('div.tooltip').css({'position': 'absolute', 'top': tPosY, 'left': tPosX, 'border' : '2px', 'line-height' : '2px', 'background-color' : 'white', 'height' : '20px' , 'width' : '30px', 'border' : 'green 10px'});             
//		}).mouseout(function(){
//			//$('div.tooltip').remove();
//			// create a hidefunction on the callback if you want
//		    //hideTooltip(); 
//		});
//		
//		$('#gridMain_ym').mouseover(function(event) {
//			$('<div class="tooltip">grid</div>').appendTo('body');
//			var tPosX = event.pageX - 10;
//		    var tPosY = event.pageY - 100;
//		    $('div.tooltip').css({'position': 'absolute', 'top': tPosY, 'left': tPosX});             
//		}).mouseout(function(){
//			$('div.tooltip').hide();
//			// create a hidefunction on the callback if you want
//		    //hideTooltip(); 
//		});
		
		
		
		
		
		
		
		
		
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_tong.fn_dayTelCostTong_init						= fn_dayTelCostTong_init;
	_mod_tong.fn_MonTelCostTong_init						= fn_MonTelCostTong_init;
	_mod_tong.fn_monCostTong_init							= fn_monCostTong_init;
	_mod_tong.fn_dayCostTong_init							= fn_dayCostTong_init;
	_mod_tong.fn_tongInOut_init								= fn_tongInOut_init;
	_mod_tong.fn_tongGrp_init								= fn_tongGrp_init;
	_mod_tong.fn_userCustidFix 								= fn_userCustidFix;
	_mod_tong.fn_MBMonTelCostTong_init 						= fn_MBMonTelCostTong_init;
	_mod_tong.fn_MBMonCustCostTong_init 					= fn_MBMonCustCostTong_init;
	_mod_tong.fn_MonJojikSumTong_init 						= fn_MonJojikSumTong_init;
	
	_mod_tong.fn_tooltip 									= fn_tooltip;
	
	return _mod_tong;

}(UNI.TONG || {}, jQuery));