/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.BILL = (function(_mod_bill, $, undefined){
	
	fn_excel_sunap = function() {
		var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		if (!COMMON.fn_form_obj_validate(sFormId)) return;
		
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;
		
		// 엑셀 사용 사유
		if($("#excelUseReasonYn").val() == "Y"){
			COMMON.fn_execel_use_reason($("#excelUseReasonYn").val())
		}else{
			// 처리중 메시지 show
			$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/bill/sunapExcelDown.do", "fn_excelDownload_callback").submit();
		}
	}
	
	fn_excel_reldatacost = function(){
		var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		//if (!COMMON.fn_form_obj_validate(sFormId)) return;
		
		// 관계사 데이터 회선별 매출요금화면 전용 필수 검증
		var rep_cust_id = $(".srh_wrap").find("#rep_cust_id").val();
		if(rep_cust_id == ""){
			$.unibillDialog.alert('알림', "메일그룹고객ID를 입력하세요.");
			return;
		}
		
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;
		
		// 엑셀 사용 사유
		if($("#excelUseReasonYn").val() == "Y"){
			COMMON.fn_execel_use_reason($("#excelUseReasonYn").val())
		}else{
			// 처리중 메시지 show
			$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/bill/reldataExcelDown.do", "fn_excelDownload_callback").submit();
		}
	}
	
	fn_excel_reltelcost = function(){
		var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		if (!COMMON.fn_form_obj_validate(sFormId)) return;
		
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;
		
		// 엑셀 사용 사유
		if($("#excelUseReasonYn").val() == "Y"){
			COMMON.fn_execel_use_reason($("#excelUseReasonYn").val())
		}else{
			// 처리중 메시지 show
			$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/bill/reltelExcelDown.do", "fn_excelDownload_callback").submit();
		}
	}
	
	
	
	fn_excel_relall = function(){
		var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		if (!COMMON.fn_form_obj_validate(sFormId)) return;
		
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;
		
		// 엑셀 사용 사유
		if($("#excelUseReasonYn").val() == "Y"){
			COMMON.fn_execel_use_reason($("#excelUseReasonYn").val())
		}else{
			// 처리중 메시지 show
			$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/bill/relallExcelDown.do", "fn_excelDownload_callback").submit();
		}
	}
	
	fn_telsend= function(){
		var bill_ym = $(".srh_wrap").find("#bill_ym").val()
		var param = {"bill_ym": bill_ym};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/bill/telsend.json',
			data: param ,
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				if(data.isSucess){
					$.unibillDialog.alert('알림', '전사자료생성전송이 완료되었습니다.');
				}
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});	
		
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_bill.fn_excel_sunap						= fn_excel_sunap; // 별정 수납별 고지서자료 엑셀다운로드
	_mod_bill.fn_excel_reldatacost 					= fn_excel_reldatacost;
	_mod_bill.fn_excel_reltelcost 					= fn_excel_reltelcost;
	_mod_bill.fn_excel_relall	 					= fn_excel_relall;
	_mod_bill.fn_telsend 							= fn_telsend;
	
	
	return _mod_bill;

}(UNI.BILL || {}, jQuery));