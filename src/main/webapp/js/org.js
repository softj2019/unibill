/**
 *  기관 관리용
 * @namespace {Object} UNI.ORG
 */
UNI.ORG = (function(_mod_org, $, undefined){

	
	
	/**
	* @method
	* @description 기관 관리 엑셀 다운 
	* @author 홍성원(2019.04.12)
	*/

	fn_orgManageExcel = function(){
		alert("1111 : " + $("#excelUseReasonYn").val());
		// 엑셀 사용 사유
		if($("#excelUseReasonYn").val() == "Y"){
			COMMON.fn_execel_use_reason($("#excelUseReasonYn").val())
		}else{
			// 처리중 메시지 show
			$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/org/orgExcelDown.do", "ORG.fn_orgManageExcel_callback").submit();
		}
		
	}
	
	fn_orgManageExcel_callback = function(){
		alert("22222");
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_org.fn_orgManageExcel					= fn_orgManageExcel;
	_mod_org.fn_orgManageExcel_callback			= fn_orgManageExcel_callback;
	
	
	return _mod_org;

}(UNI.ORG || {}, jQuery));