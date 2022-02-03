/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.OTP = (function(_mod_otp, $, undefined){
	
	
	fn_getOTP = function(){
		var mobile = $("#frm_detail").find("#mobile").val();
		mobile = mobile.replace(/-/gi, "");
		
		var tx = ($(window).width()-$("#cu_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		
		var dlgWidth = 500;
		var dlgHeight = 325;
		x = (window.screen.availWidth - dlgWidth) / 2;
		y = (window.screen.availHeight - dlgHeight) / 2;
		//var win = window.open(CONTEXT_ROOT + '/content/getSMS.do', 'SMS_POPUP', "width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=1,status=1");
		var win = window.open(CONTEXT_ROOT + '/content/getSMS.do?mobile=' + mobile, 'TELDEL_POPUP', "left=" + tx + ",top=" + ty + ",width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=1,status=1");
		win.focus();
	}
	
	fn_confOTP = function(){
		
	}
		
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_otp.fn_getOTP     				= fn_getOTP;
	_mod_otp.fn_confOTP     			= fn_confOTP;
	
	return _mod_otp;

}(UNI.OTP || {}, jQuery));