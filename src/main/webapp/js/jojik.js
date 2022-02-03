/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.JOJIK
 */
UNI.JOJIK = (function(_mod_jojik, $, undefined){

	
	
	/**
	* @method
	* @description 주소 정보
	* @author 홍성원(2016.12.23)
	*/

	
	// 조직 화면 변경
	fn_changejojik = function(){
		if($("#frm_detail").find("#scrin_code").val() == "S0196"){
			$("#frm_detail").find("#btn_save").hide();
			$("#frm_detail").find("#btn_insert").show();
			$("#frm_detail").find("#btn_update").show();
		}
		
		
		
		
	}
	
	//조직 저장전에 수행할 로직
	fn_presavejojik = function(){
		// 이전에 등록 되어 있는 조직정보 업데이트
		var formData = new FormData();
		formData.append("sGrpCd" , $("#frm_detail").find("#grp_cd").val());
		formData.append("sSday" , $("#frm_detail").find("#sday").val().split('-').join(''));
		
		xhr = $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jojik/updateOldData.do',
			datatype : "json",
			processData: false,
			contentType: false, 
			async: false,
			data: formData,
			success : function(data) { 
				//alert("data.rs : " + data.rs.resultMsg);
				//alert("data.rs : " + data.rs.errorMsg);
				
			},
			error: function(xhr, status, error) {}
		});
	}
	
	
	//조직 저장 이후에 처리할 로직
	fn_postsavejojik = function(){
		// 변경된 조직 정보를 참조 하는 path update
		var formData = new FormData();
		formData.append("sGrpCd" , $("#frm_detail").find("#grp_cd").val());
		formData.append("sday" , $("#frm_detail").find("#sday").val());
			
		xhr = $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jojik/updatePath.do',
			datatype : "json",
			processData: false,
			contentType: false, 
			async: false,
			data: formData,
			success : function(data) { 
				//alert("data.rs : " + data.rs.resultMsg);
				//alert("data.rs : " + data.rs.errorMsg);
				
			},
			error: function(xhr, status, error) {}
		});

	}
	
	//조직 저장 이후에 처리할 로직
	fn_postsavejojik2 = function(){
		// 변경된 조직 정보를 참조 하는 path update
		var formData = new FormData();
		formData.append("sGrpCd" , $("#frm_detail").find("#grp_cd").val());
		formData.append("sday" , $("#frm_detail").find("#sday").val());
			
		xhr = $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jojik/updatePath2.do',
			datatype : "json",
			processData: false,
			contentType: false, 
			async: false,
			data: formData,
			success : function(data) { 
				//alert("data.rs : " + data.rs.resultMsg);
				//alert("data.rs : " + data.rs.errorMsg);
				
			},
			error: function(xhr, status, error) {}
		});

	}
	
	//엑셀 일괄업로드 이후에 처리할 로직
	fn_postsavejojik3 = function(){
		// 변경된 조직 정보를 참조 하는 path update
		var formData = new FormData();
		//formData.append("sGrpCd" , $("#frm_detail").find("#grp_cd").val());
			
		xhr = $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jojik/updatePath3.do',
			datatype : "json",
			processData: false,
			contentType: false, 
			async: false,
			data: formData,
			success : function(data) { 
				//alert("data.rs : " + data.rs.resultMsg);
				//alert("data.rs : " + data.rs.errorMsg);
				
			},
			error: function(xhr, status, error) {}
		});

	}
	
	fn_insertJojik = function(){
		var valMsg      = ' (은)는 필수입력항목입니다';
		var ret=COMMON.fn_columnValidate("frm_detail", valMsg); 
		if (ret==false) {
			//alert("fn_columnValidate fail");
			return;
		}
		
		$.unibillDialog.confirm('확인', "등록하시겠습니까", 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						$("#frm_detail").find("#actionFlag").val("INSERT");
						COMMON.fn_submit_fileForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/jojik/jojiksaveContent.do", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _spost != "undefined" && _spost != ""){
								var callback_function = new Function(_spost);
								callback_function();
							}
						}, 1000);
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
					//닫기 버튼 기능 pjh
					document.getElementById('ly_pop').style.display = 'none';
				}
			);	
		
		
		
		
	}
	
	
	fn_updateJojik = function(){
		
		$.unibillDialog.confirm('확인', "수정하시겠습니까", 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						$("#frm_detail").find("#actionFlag").val("UPDATE");
						COMMON.fn_submit_fileForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/jojik/jojiksaveContent.do", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _spost != "undefined" && _spost != ""){
								var callback_function = new Function(_spost);
								callback_function();
							}
						}, 1000);
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
					//닫기 버튼 기능 pjh
					document.getElementById('ly_pop').style.display = 'none';
				}
			);	
		
		
		
		
	}
	
	fn_jojikexcelupload = function(){
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '<spring:message code="common.required.msg" />';
		if (dtlRadioVal == undefined) dtlRadioVal = "I";
		
		if(dtlRadioVal == "E"){
			var excel_file  = $("#"+sDetailFormId).find("#userfile2").val();    // 첨부파일
			var msg = "";
			
			if (excel_file == "") {
				msg += "[파일] " + valMsg + "</br>";
			}
			
			if (msg != "") {
				$.unibillDialog.alert('알림', msg);
				return;
			}
			
			// 업로드 확장자 체크
			var extArr = ["xls","xlsx"];
			if (!COMMON.fn_upload_file_check(excel_file, extArr, "엑셀")) return;
			
			var sXmlId = $("#frm_main").find("#xmlId").val();
			if (sXmlId == undefined || sScrinCode=="S0031") sXmlId = '';
			
			$("#"+sDetailFormId).find("#xmlId").remove();
			$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);
			
			$.unibillDialog.confirm('확인', '<spring:message code="common.mregist.msg" />', 
					function (isTrue) {
						// 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B"); 
		
						//COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/bundleUploadContent.do'/>", "fn_bundle_upload_callback");
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/jojik/excelUploadContent.do'/>", "fn_bundle_upload_callback");					
						
						
						//닫기 버튼 기능 pjh
						fn_close_dtlscrin();
					}
				);	
			
		}
	}
	
	
	fn_insertTelno = function(){
		var valMsg      = ' (은)는 필수입력항목입니다';
		var ret=COMMON.fn_columnValidate("frm_detail", valMsg); 
		if (ret==false) {
			//alert("fn_columnValidate fail");
			return;
		}
		$.unibillDialog.confirm('확인', "등록하시겠습니까", 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						$("#frm_detail").find("#actionFlag").val("INSERT");
						COMMON.fn_submit_fileForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/jojik/telnosaveContent.do", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _spost != "undefined" && _spost != ""){
								var callback_function = new Function(_spost);
								callback_function();
							}
						}, 1000);
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
					//닫기 버튼 기능 pjh
					document.getElementById('ly_pop').style.display = 'none';
				}
			);	
		
		
		
		
	}
	
	fn_updateTelno = function(){
		var valMsg      = ' (은)는 필수입력항목입니다';
		var ret=COMMON.fn_columnValidate("frm_detail", valMsg); 
		if (ret==false) {
			//alert("fn_columnValidate fail");
			return;
		}
		
		$.unibillDialog.confirm('확인', "수정하시겠습니까", 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						$("#frm_detail").find("#actionFlag").val("UPDATE");
						COMMON.fn_submit_fileForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/jojik/telnosaveContent.do", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _spost != "undefined" && _spost != ""){
								var callback_function = new Function(_spost);
								callback_function();
							}
						}, 1000);
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
					//닫기 버튼 기능 pjh
					document.getElementById('ly_pop').style.display = 'none';
				}
			);	
		
		
		
		
	}
	
	//교환기 전화번호관리 엑셀업로드
	fn_btn_telnosave = function() {
		var valMsg      = ' (은)는 필수입력항목입니다';
		var excel_file  = $("#"+sDetailFormId).find("#userfile2").val();    // 첨부파일
		
		var msg = "";

		if (excel_file == "") {
			msg += "[파일] " + valMsg + "</br>";
		}
		
		if (msg != "") {
			$.unibillDialog.alert('알림', msg);
			return;
		}
		
		// 업로드 확장자 체크
		var extArr = ["xls","xlsx"];
		if (!COMMON.fn_upload_file_check(excel_file, extArr, "엑셀")) return;

		var sXmlId = $("#frm_main").find("#xmlId").val();
		if (sXmlId == undefined || sScrinCode=="S0031") sXmlId = '';

		$("#"+sDetailFormId).find("#xmlId").remove();
		$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);
		
		$.unibillDialog.confirm('확인', '일괄 등록하시겠습니까?', 
			function (isTrue) {
				// 처리중 메시지 show
				$("#id_loader").show();	 
				COMMON.fn_scrin_block_ui("B"); 
				//COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/bundleUploadContent.do'/>", "fn_bundle_upload_callback");
				COMMON.fn_submit_fileForm("frm_detail", "proc_frm", "/jojik/excelUploadContent.do", "fn_bundle_upload_callback");					
				//닫기 버튼 기능 pjh
				fn_close_dtlscrin();
				
			}
		);	
		
		
	}
	

	
	
	
	//수동인사연동
	fn_manualSync = function(){
		var formData = new FormData();
			
		xhr = $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jojik/manualSync.json',
			processData: false,
			contentType: false, 
			data: formData,
			success : function(data) { 
				if(data.isRuuning){
					$.unibillDialog.alert('알림', '이미 실행된 인사연동이 있습니다.');
				}else{
					if(!data.sucessconn){
						$.unibillDialog.alert('알림', data.msg);
						
					}else{
						$.unibillDialog.alert('알림', '수동인사연동 연결을 확인하세요.');
					}
				}
				$("#id_loader").hide();
				
			},
			error: function(xhr, status, error) {}
		});
		
		
		
	}
	
	
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_jojik.fn_changejojik						= fn_changejojik;
	_mod_jojik.fn_presavejojik 						= fn_presavejojik;
	_mod_jojik.fn_postsavejojik 					= fn_postsavejojik;
	_mod_jojik.fn_postsavejojik2 					= fn_postsavejojik2;
	_mod_jojik.fn_postsavejojik3 					= fn_postsavejojik3;
	_mod_jojik.fn_insertJojik 						= fn_insertJojik;
	_mod_jojik.fn_updateJojik 						= fn_updateJojik;
	
	_mod_jojik.fn_insertTelno 						= fn_insertTelno;
	_mod_jojik.fn_updateTelno 						= fn_updateTelno;
	_mod_jojik.fn_btn_telnosave 					= fn_btn_telnosave;
	
	
	_mod_jojik.fn_manualSync 						= fn_manualSync;
	
	
	
	return _mod_jojik;

}(UNI.JOJIK || {}, jQuery));