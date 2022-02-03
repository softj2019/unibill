/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.CUSTOM = (function(_mod_custom, $, undefined){

	
	
	/**
	* @method
	* @description 통신사파일 등록
	* @author 홍성원(2016.12.23)
	*/
	fn_btn_telcofile_regist = function(){
		//파일 순번리스트 가져오기
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/telcoFile/filesnList.json',
			datatype : "json",
			jsonp : "callback",
			success : function(data) {
				$("#filesn option").remove();
				$("#filesn").append("<option value=''>선택</option>");
				for(var i=0; i<data.fsList.length; i++){
					$("#filesn").append("<option value='" + data.fsList[i].code + "'>" + data.fsList[i].name + "(" + data.fsList[i].code + ")" + "</option>");	
				}
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
		
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#cu_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#cu_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#cu_pop").find("#buttonFlag").val("R");
		$(".delSheet").hide();
		$(".del").show();
		$("#cu_pop").show();  // 레이어 팝업 open
	
	}
	
	
	/**
	* @method
	* @description 통신사파일 삭제
	* @author 홍성원(2016.12.23)
	*/
	fn_btn_telcofile_del = function(){
		
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#cu_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#cu_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#cu_pop").find("#buttonFlag").val("D");
		$(".del").hide();
		$(".delSheet").show();
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/telcoFile/sheetList.do',
			datatype : "json",
			jsonp : "callback",
			success : function(data) {

				var sheetList =  data.sheetList;
				
				if(sheetList != null){
					var sheetLists = sheetList.split("@");
					
					var sOptionArr = {};
					//sOptionArr[""] = "선택";
					
					if(sheetLists.length > 0){
						for(var i=0; i<sheetLists.length; i++){
							var code = sheetLists[i];
							var name = "파일(" + sheetLists[i] + ")";
							
							sOptionArr[code] = name;
							
							$("#cu_pop").find("#delSheetSn").selectBox('options', sOptionArr); 
						}
					}
					$("#cu_pop").find("#delSheetSn").selectBox('value', sheetLists[0]);
				}

				
				
				
				
				
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});	
		
		$("#cu_pop").show();  // 레이어 팝업 open
		
		
	}
	
	fn_btn_telcofile_del_bk = function(){
		
		
		var tx = ($(window).width()-$("#cu_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		
		//$("#cu_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		//$("#cu_pop").find("#buttonFlag").val("D");
		//$(".del").hide();
		//$(".delSheet").show();
		
		var dlgWidth = 950;
		var dlgHeight = 600;
		x = (window.screen.availWidth - dlgWidth) / 2;
		y = (window.screen.availHeight - dlgHeight) / 2;
		var win = window.open(CONTEXT_ROOT + '/telcoFile/sheetList.do', 'TELDEL_POPUP', "left=" + tx + ",top=" + ty + ",width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=1,status=1");
		win.focus();
		
//		$(document).ready(function () {
//		    
//		        $("#cu_pop").dialog({
//		            modal: true,
//		            height: 590,
//		            width: 1005
//		        });
//		    
//		});
		
		
		
		//COMMON.fn_form_submit('frm_main', '/telcoFile/sheetList.do');
		
		//$.ajax({
		//	type: 'POST',
		//	url: CONTEXT_ROOT + '/telcoFile/sheetList.do',
		//	datatype : "json",
		//	jsonp : "callback",
		//	success : function(data) {
// alert("return");
				//var sheetList =  data.sheetList;
				//var sheetLists = sheetList.split("@");
				
				//var sOptionArr = {};
				//sOptionArr[""] = "선택";
				
				//if(sheetLists.length > 0){
				//	for(var i=0; i<sheetLists.length; i++){
				//		var code = sheetLists[i];
				//		var name = "파일(" + sheetLists[i] + ")";
						
				//		sOptionArr[code] = name;
						
				//		$("#cu_pop").find("#delSheetSn").selectBox('options', sOptionArr); 
				//	}
				//}
				
				//$("#cu_pop").find("#delSheetSn").selectBox('value', sheetLists[0]);
				
				
				
				
	//		},
	//		error: function(xhr, status, error) {
	//			alert("에러");
	//		}
	//	});	
		
	//	$("#cu_pop").show();  // 레이어 팝업 open
		
		
	}
	
	fn_insRequest = function(){
		var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
		if (rowIds.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			return;
		}
		var rowDatas = $("#gridMain").jqGrid("getRowData");
		if (rowDatas.length == 0) return true;
		
		var contSeq ="";
		var regStatus = true;
		
		for (var i=0; i<rowDatas.length; i++) {
			for(var j=0; j<rowIds.length; j++){
				if(rowDatas[i]["ubseq"] == rowIds[j]){
					contSeq += ";" + rowDatas[i]["cont_seq"];
					//이미 신청된 데이터 확인
//					alert("rowDatas[i]['reg_status']"+rowDatas[i]["reg_status"]);
					if(rowDatas[i]["reg_status"] == "접수대기 (R)"){
						regStatus = false;
					}
				}
				
			}
		}
		
		contSeq = contSeq.substring(1);
		
		if(regStatus){
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/contSeq.do',
				datatype : "json",
				data: "contSeq=" + contSeq,
				success : function(data) {
					
					if(data.insResult == true){
						//그리드 값 변경
						for (var i=0; i<rowDatas.length; i++) {
							for(var j=0; j<rowIds.length; j++){
								if(rowDatas[i]["ubseq"] == rowIds[j]){
									$("#gridMain").jqGrid("setRowData", rowIds[j], {"reg_status" : "접수대기 (R)"});
								}
								
							}
						}
						$.unibillDialog.alert('알림', '접수가 완료 되었습니다..');
					}else{
						$.unibillDialog.alert('알림', '신청중에 에러가 발생하였습니다 등록된 값을 확인해 주십시오.');
					}
					
				},
				error: function(xhr, status, error) {
					alert("에러");
				}
			});
		}else{
			$.unibillDialog.alert('알림', '이미 접수된 데이터가 있습니다.');
		}
		
			
		

		
	}
	
	fn_regRequest = function(){
		var rolecode = $("#topForm").find("#role_code").val();
		
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '을 확인해주세요.';
		
		if (dtlRadioVal === undefined) dtlRadioVal = "I";
		
//		 필수항목 체크 / KEY 컬럼 수정 여부에 따른 등록,수정 모드 셋팅			
		if (!COMMON.fn_columnValidate("frm_detail", valMsg)) return;
		var msg = '등록 하시겠습니까?';
		
		var actionFlag = $("#frm_detail").find("#actionFlag").val();
		

		
		if (actionFlag == "UPDATE") {
			msg = '수정 하시겠습니까?';
		}
		$.unibillDialog.confirm('확인', msg, 
		    function (isTrue) {
				try {
					// 처리중 메시지 show
					$("#id_loader").show();	 
					COMMON.fn_scrin_block_ui("B");

					if(actionFlag != "UPDATE"){
						//유저ID
						if(rolecode == 'D001' || rolecode == 'H001' || rolecode == 'H002'){
							
						}else{
							var uid = $("#topForm").find("#user_id").val();
							$("#frm_detail").find("#user_id").val(uid);
						}
//						
//						//등록일
//						var _date = new Date();
//						var year = _date.getFullYear()
//						var month = new String(_date.getMonth()+1) 
//						var day = new String(_date.getDate());
//						
//						if(month.length == 1){
//							month = "0" + month;
//						}
//						if(day.length ==1){
//							day = "0" + day;
//						}
////						alert(year+month+day);
//						$("#frm_detail").find("#reg_date").val(year+month+day);
						
						
//						
						$.ajax({
							type: 'POST',
							url: CONTEXT_ROOT + '/receipt/getseq.do',
							datatype : "json",
							data: "line_cnt=" + $("#frm_detail").find("#line_cnt").val(),
							jsonp : "callback",
							success : function(data) {
								var seq =  data.seq;
								$("#frm_detail").find("#cont_seq").val("");
								$("#frm_detail").find("#cont_seq").val(seq);
								COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/content/saveContent.do", "fn_save_callback_content");
								
							},
							error: function(xhr, status, error) {
								alert("에러");
							}
						});	
					}else{
						COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/content/saveContent.do", "fn_save_callback_content");
						
					}
					
					
				} catch (E) {
					$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
				}
			}
		);
		
	}
	
	
	/**
	* @method
	* @description 폼 생성 후 submit
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*        {string] _sGridId : 선택된 grid ID
	*        {int}    _sPage   : grid 현재 페이지 번호
	*/
	fn_submit_createReciptForm = function(_sFormId, _sTargetIframe, _sAction, _sCallback, _userId) {
		if (_sCallback !== undefined) {
			$("#"+_sFormId).find("#callBackFunction").val(_sCallback);	// 처리후 복귀 함수
		}
		
		var uniq_id = fn_get_randomId(10);
		var $iframe = fn_get_iframeBody($("#"+_sTargetIframe)[0]);		
		var $form = $("<form/>").attr( "action", _sAction ).attr( "id", uniq_id ).attr("method", "post");
		
		try {
			var input_type = "";
			var input_val = "";
			var $ctrl = "";

			$iframe.append( $form );

			$("#"+_sFormId).find("input[type='hidden'], input[type='text'],input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea").each( function(){
				// disabled 된것은 배제
				if( $(this).is(':enabled') ) {

					input_type = $(this).attr("type");

					try {	// 마스크 등 제거
						input_val = $(this).cleanVal();
					}catch(E){
						input_val = $(this).val();
					}
			
					/*if ($(this).get(0).tagName == "TEXTAREA"){
						$ctrl = $('<TEXTAREA/>').attr({name:$(this).attr("name"), value: input_val });
					} else {
						$ctrl = $('<input/>').attr({ type: 'text', name:$(this).attr("name"), value: input_val });
					}*/
					$ctrl = $('<input/>').attr({ type: 'text', name:$(this).attr("name"), value: input_val });
					$iframe.find("#"+uniq_id).append($ctrl);
					//alert($(this).attr("name") + " : " + input_val);
				}
			});
			//user_id
			//alert("_userId : " + _userId);
			$ctrl= "";
			$ctrl = $('<input/>').attr({ type: 'text', name:'user_id', value: _userId });
			$iframe.find("#"+uniq_id).append($ctrl);
			//alert($iframe.find("input[name='user_id']").val());
			
			//reg_date
			
		} catch(E) {}

		try {
			$iframe.find("#"+uniq_id).submit();
		} catch(E) {
			$.unibillDialog.alert('오류', E);
		}
		
	}
	
	/**
	 * @method
	 * @description 접속한 유저 히든 검색
	 * @author 홍길동
	 * @param
	 */
	fn_hidden_userid = function(){
		var rolecode = $("#topForm").find("#role_code").val();
		if(rolecode == 'D001' || rolecode == 'H001' || rolecode == 'H002'){
			
		}else{
			$(".srh_wrap").find("#cust_id").val($("#topForm").find("#cust_id").val());
			$(".srh_wrap").find("#cust_id").attr("readonly",true);
			
			$(".srh_wrap").find("#request_date_datepicker").val("");
			$(".srh_wrap").find("#plan_date_datepicker").val("");
			
		}

		$(".srh_wrap").find("#from_request_date_datepicker").val("");
		$(".srh_wrap").find("#to_request_date_datepicker").val("");
		
		
		
	}
	
	/**
	 * @method
	 * @description 일시중지 리스트만 검색
	 * @author 홍길동
	 * @param
	 */
	fn_stopRequest = function(){
		$(".srh_wrap").find("#stop").val("S");
		
		$(".srh_wrap").find("#request_date_datepicker").val("");
		$(".srh_wrap").find("#plan_date_datepicker").val("");
		$(".srh_wrap").find("#reg_date_datepicker").val("");
		
		
		
	}
	
	/**
	 * @method
	 * @description 일시중지 리스트만 검색
	 * @author 홍길동
	 * @param
	 */
	fn_openRequest = function(){
		$(".srh_wrap").find("#stop").val("U");
		$(".srh_wrap").find("#status").val("O");
		
		$(".srh_wrap").find("#use_yn").selectBox('value', 'Y');
		
	}
	
	
	
	
	
	
	
	// ==============================================================================
	// =======================	통신사 파일 업로드 ==================================
	// ==============================================================================
	//등록 실행
	fn_excute = function (){
		if($("#buttonFlag").val() == "R")
			CUSTOM.fn_telRegist();
		if($("#buttonFlag").val() == "D")
			CUSTOM.fn_del();
	}
	
	//파일등록
	fn_telRegist = function (){
		if ($("#filesn").val() == "") {
			alert("파일 순번을 선택하여 주세요.");
			return false;
		}
		if ($("#upfile").val() == "") {
			alert("업로드할 파일을 선택하여 주세요.");
			return false;
		}
		
		if(returnID == null){
			isRun = true;
			CUSTOM.fn_setRunning();
			
			$("#execute").hide();
			$("#jobmsg").text("통신사파일 등록을 시작합니다.");
			$(".progressbar").css("width",0+"%");
			$(".progressbar").text(0 + "%");
			$("#error").html(0 + "/ <span class='t_red'>" + 0 + "</span>");
			
			CUSTOM.fn_excelUpload();
			setTimeout("fn_telSetTime()",5000);
			
		}else{
			alert("현재 작업 중입니다.");
		}
	}
	
	// 엑셀 업로드
	fn_excelUpload = function (){
		isRun = true;
		var formData = new FormData();
			
		$.each($('#file-upload')[0].files, function(i, file) {          
            formData.append('file-' + i, file);
		});
		var year = $("#pyear option:selected").val();
		var month = $("#pmonth option:selected").val();
		formData.append("billym" , year + month);
		formData.append("gubun" , $("[name='gubun']:checked").val());
		formData.append("filesn" , $("#filesn option:selected").val());
		
		xhr = $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/telcoFile/excelUpload.do',
				processData: false,
				contentType: false, 
				data: formData,
				success : function(data) { 
					isRun = false; 
				},
				error: function(xhr, status, error) { isRun = false;}
			});
		
	}
		
	fn_telSetTime = function (){
		CUSTOM.fn_telCheckRun();
		returnID = setInterval(CUSTOM.fn_telCheckRun,5000);
		
	}
	
	// 엑셀 업로드 작업 상황 체크
	fn_telCheckRun = function(){
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/telcoFile/checkRun.do',
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				UTILE.fn_autoLogout_reset();
				isRun =  data.reqStatus;
				if(isRun == false){
					clearInterval(returnID);
					returnID = null;
					//alert("data.progress : " + data.progress);
					$("#jobmsg").text(data.jobmsg);
					$("#newmsg").text(data.newmsg);
					$("#colvali").html("요금항목 미설정 : " + data.collistCnt + " 상품ID 미설정 : " + data.colvalilistCnt + " 고객ID 미설정 : " + data.colcustCnt);
					$("#colvalimsg").html(data.colmsg + "<br>" + data.colcustmsg + "<br>" + data.colvalimsg);
					$(".progressbar").css("width",data.progress+"%");
					$(".progressbar").text(data.progress + "%");
					$("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
					
					
					
					$("#execute").show();
					isRun = false;
					
					
					//파일첨부 초기화
					var agent = navigator.userAgent.toLowerCase();
					if( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
						$("#frm_cuDetail").find("#file-upload").replaceWith($("#file-upload").clone(true) );
						$("#frm_cuDetail").find("#upfile").val("");
					}else{ // other browser 일때 input[type=file] init. $("#filename").val(""); 
						$("#file-upload").val("");
						$("#frm_cuDetail").find("#upfile").val("");
					}
					
					return false;
				}
				
				$("#jobmsg").text(data.jobmsg);
				$("#newmsg").text(data.newmsg);
				$("#colvalimsg").html(data.colmsg + "<br>" + data.colvalimsg);
				$(".progressbar").css("width",data.progress+"%");
				$(".progressbar").text(data.progress + "%");
				$("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
				
			},
			error: function(xhr, status, error) {
				alert("작업상황체크 에러");
			}
		});		
	}
	
	
	fn_stop = function(xhr){
		if(isRun == false){
			alert("실행중인 작업이 없습니다.");
		}else{
			xhr.abort();
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/telcoFile/stop.do',
				datatype : "json",
				jsonp : "callback",
				success : function(data) {
					
					isRun =  data.reqStatus;
					if(isRun == false){
						clearInterval(returnID);
						returnID = null;
						//alert("data.progress : " + data.progress);
						$("#jobmsg").text(data.jobmsg);
						$(".progressbar").css("width",data.progress+"%");
						$(".progressbar").text(data.progress + "%");
						$("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
						
						$("#execute").show();
						isRun = false;
						
						//파일첨부 초기화
						var agent = navigator.userAgent.toLowerCase();
						if( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
							$("#frm_cuDetail").find("#file-upload").replaceWith($("#file-upload").clone(true) );
							$("#frm_cuDetail").find("#upfile").val("");
						}else{ // other browser 일때 input[type=file] init. $("#filename").val(""); 
							$("#file-upload").val("");
							$("#frm_cuDetail").find("#upfile").val("");
						}
						
						return false;
					}
				},
				error: function(xhr, status, error) {
					alert("작업상황체크 에러");
				}
			});	
		}
		
	}
	
	//업로드 플레그
	fn_setRunning = function(){
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/telcoFile/uploadFlag.do',
			processData: false,
			contentType: false, 
			data: "",
			async: false,
			success : function(data) { isRun = true;},
			error: function(xhr, status, error) { }
		});
	}
	
	//파일삭제
	fn_del = function(){
		if(returnID == null){
			$("#execute").hide();
			CUSTOM.fn_tongFileDel();
			CUSTOM.fn_telSetTime();	
		}else{
			alert("현재 작업 중입니다.");
		}
	}
	
	fn_tongFileDel = function(){
		var formData = new FormData();
		
		var year = $("#pyear option:selected").val();
		var month = $("#pmonth option:selected").val();
		var delSheetSn = $("#delSheetSn option:selected").val();
		
		if($("#delSheetSn").val() == "" || $("#delSheetSn").val() == null){
			return;
		}
		
		formData.append("billym" , year + month);
		formData.append("delSheetSn" , delSheetSn);
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/telcoFile/tongFileDel.do',
			processData: false,
			contentType: false, 
			data: formData,
			success : function(data) {},
			error: function(xhr, status, error) {}
		});
	}
	
	
	//파일 찿기
	fn_searchFile = function(){
		$("#file-upload").click();
	}
	
	//파일경로 input 박스 표시
	fn_getPath = function (value){
		$("#upfile").val(value);
	}
	
	
	//데이터신청 접수관리 회선ID 조회
	fn_getCurTell = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#ly_receipt_pop .confirm_cont").width())/2;
		var ty = ($(window).height())/15;
		if(ty < 0){
			ty = 15;
		}
		$("#ly_receipt_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		
		$("#ly_receipt_pop").show();  // 레이어 팝업 open
	}
	
	
	
	fn_custBillSearch =function (){
		if($("#searchFrm").find("#rep_cust_id").val() == ""){
			$.unibillDialog.alert('오류', "고객ID를 입력하세요.");
			return;
		}
		$("#id_loader").show();	 
		COMMON.fn_scrin_block_ui("B");
		
		var searchMonth = $("#searchFrm").find("#s_billYm").val();
		var param = {"searchMonth": searchMonth,  "searchCustId": $("#searchFrm").find("#rep_cust_id").val()};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/custbill/getSearch.do',
			datatype : "json",
			data: param ,
			success : function(data) {
				if(data.custList.length == 1){
					var bill_ym = data.billYm;
					//헤더 날짜
					$("#hbillym").html(bill_ym.substring(0,4) + "." + bill_ym.substring(4));
					//납부정보
					$("#repCustId").html(data.custList[0].repCustId);
					$("#repCustNm").html(data.custList[0].repCustNm);
					$("#makeDd").html(bill_ym.substring(0,4) + "." + bill_ym.substring(4) + "." + data.custList[0].makeDd);
					$("#deadlineDd").html(bill_ym.substring(0,4) + "." + bill_ym.substring(4) + "." + data.custList[0].deadlineDd);
					
					//납부기본내역
					$("#bbillym").html(bill_ym.substring(0,4) + "년  <span>" + bill_ym.substring(4) + "월</span> 납부하실 금액은");
					$("#bcost").html(data.custList[0].cost);
					
					$("#giboncost").html(data.rptsaleList[0].giboncost + " 원 ");
					$("#sinecost").html(data.rptsaleList[0].sinecost + " 원 ");
					$("#siwecost").html(data.rptsaleList[0].siwecost + " 원 ");
					$("#idongcost").html(data.rptsaleList[0].idongcost + " 원 ");
					$("#kooknaecost").html(data.rptsaleList[0].kooknaecost + " 원 ");
					$("#injubcost").html(data.rptsaleList[0].injubcost + " 원 ");
					$("#internetcost").html(data.rptsaleList[0].internetcost + " 원 ");
					$("#kukjecost").html(data.rptsaleList[0].kukjecost + " 원 ");
					$("#kitacost").html(data.rptsaleList[0].kitacost + " 원 ");
					$("#supCost").html(data.custList[0].supCost + " 원 ");
					$("#vsupCost").html(data.custList[0].vsupCost + " 원 ");
					$("#lateCost").html(data.custList[0].lateCost + " 원 ");
					$("#vatCost").html(data.custList[0].vatCost + " 원 ");
					$("#cost").html(data.custList[0].cost + " 원 ");
					
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}else if(data.custList.length == 0){
					$.unibillDialog.alert('오류', "데이터가 존재하지 않습니다.");
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}else{
					$.unibillDialog.alert('오류', "복수의 데이터가 존재합니다.");
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}
				
				
			},
			error: function(xhr, status, error) {
				alert("에러");
				$("#id_loader").hide();
				
				COMMON.fn_scrin_block_ui("UB");
			}
		});
	}

	fn_custDBillSearch =function (){
		if($("#searchCustId").val() == ""){
			$.unibillDialog.alert('오류', "고객ID를 입력하세요.");
			return;
		}
		
		
		$("#id_loader").show();	 
		COMMON.fn_scrin_block_ui("B");
		
		var searchMonth = $("#pyear").val() + $("#pmonth").val();
		var param = {"searchMonth": searchMonth,  "searchCustId": $("#searchCustId").val()};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/custbill/getDSearch.do',
			datatype : "json",
			data: param ,
			success : function(data) {
				
				if(data.dList.length == 1){
					$("#billNm").text("");
					$("#billNm").text($("#pyear").val() + "년 " + $("#pmonth").val() + "월 통신비 청구서");
					$("#makebillym").text(data.dList[0].makebillym);
					
					$("#custNm").text(data.dList[0].custNm);
					
					$("#custId").text(data.dList[0].custId);
					$("#period").text(data.dList[0].period);
					//alert($("#gibon").text(fn_costwon(data.dList[0].gibon))) ;
					
					$("#gibon").text(fn_costwon(data.dList[0].gibon));
					$("#sinae").text(fn_costwon(data.dList[0].sinae));
					$("#sioei").text(fn_addcomma(data.dList[0].sioei));
					$("#idong").text(fn_addcomma(data.dList[0].idong));
					$("#kukje").text(fn_addcomma(data.dList[0].kukje));
					$("#kooknae").text(fn_addcomma(data.dList[0].kooknae));
					$("#injub").text(fn_addcomma(data.dList[0].injub));
					$("#internet").text(fn_addcomma(data.dList[0].internet));
					$("#data").text(fn_addcomma(data.dList[0].data));
					$("#kita").text(fn_addcomma(data.dList[0].kita));
//					$("#unyungcost").text(data.dList[0].unyungcost);
//					$("#telcost").text(data.dList[0].telcost);
					$("#usecost").text(fn_addcomma(data.dList[0].usecost));
					$("#cnvcost").text(fn_addcomma(data.dList[0].cnvcost));
					$("#trunccost").text(fn_addcomma(data.dList[0].trunccost));
					$("#supplycost").text(fn_addcomma(data.dList[0].supplycost));
					$("#addcost").text(fn_addcomma(data.dList[0].addcost));
					$("#dangcost").text(fn_addcomma(data.dList[0].dangcost));
					$("#totcost").text(fn_addcomma(data.dList[0].totcost));
					
					
					$("#dsupplycost").text(fn_addcomma(data.dList[0].supplycost));
					$("#daddcost").text(fn_addcomma(data.dList[0].addcost));
					$("#dtotcost").text(fn_addcomma(data.dList[0].totcost));
					
					
					
					
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}else if(data.dList.length == 0){
					$.unibillDialog.alert('오류', "데이터가 존재하지 않습니다.");
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}else{
					$.unibillDialog.alert('오류', "복수의 데이터가 존재합니다.");
					$("#id_loader").hide();
					COMMON.fn_scrin_block_ui("UB");
				}
				
				
			},
			error: function(xhr, status, error) {
				alert("에러");
				$("#id_loader").hide();
				
				COMMON.fn_scrin_block_ui("UB");
			}
		});
	}
	
	fn_addcomma = function(str){
		str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}
	
	fn_costwon = function(str){
		str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,')+ " 원";
	}
	
	
	
	// ==============================================================================
	// ============================= 목표/원가대비 ==================================
	// ==============================================================================
	

	fn_goal_adjust = function(){
		// 그리드 구성
		$("#gridMain").jqGrid({
			  url : CONTEXT_ROOT + '/receipt/selectGoalAdjustList.json'
			, datatype : "local"			
			, colNames : ["ubseq","상태코드","상태","청구월","대표고객명","서비스","조정금액","선택","목표금액","원가비고","목표비고"]		
		    , colModel : [
	       {name:'ubseq',    	index:'ubseq',    	jsonmap:'ubseq',    align:'center',   key:true, hidden:true} 
	       ,{name:'status',    	index:'status',    	jsonmap:'status',     align:'center',   width:100,  hidden:true}      // 상태코드
	       ,{name:'status_nm', 	index:'status_nm', 	jsonmap:'statusNm',   align:'center',   width:100,  hidden:false}  // 상태
	       ,{name:'bill_ym',   	index:'bill_ym',   	jsonmap:'billYm',  	align:'center',   cellattr:jsFormatterCell, width:100}
		   ,{name:'rcust_id',   index:'rcust_id',   jsonmap:'rcustId',	align:'center',   cellattr:jsFormatterCell, width:100}
		   ,{name:'svc_id',   	index:'svc_id',   	jsonmap:'svcId',  	align:'center',   cellattr:jsFormatterCell, width:100}
		   ,{name:'cost',  		index:'cost',  		jsonmap:'cost',   	align:'center',   cellattr:jsFormatterCell, width:80,
			   editable:true, edittype:"text",
			   editoptions:{dataInit: function(element) {
	                  $(element).keyup(function(){
	                       // 그리드 상태
	                       if (element.value != null) {
	                       	var rowId = $("#gridMain").getGridParam("selrow");
	                       	$("#gridMain").jqGrid("setRowData", rowId, {"status" : "U", "status_nm" : "수정"});
	                       }
	                   });
	                }
			   },
			   afterEditCell:function(id,name,val,iRow,iCol){
		   			$("#"+iRow+"_"+name).bind('blur',function(){
		   				$('#gridMain').saveCell(iRow,iCol);
		   			});
		   		}
		   }
		   ,{name:'cost_type',  index:'cost_type',  jsonmap:'costType', align:'center',   cellattr:jsFormatterCell, width:100, 
			   editable:true, edittype:"select", 
			   editoptions:{value:":선택;SUN:순액;TOT:총액",
				   dataEvents:[{                                // selectbox에 이벤트 바인딩할때 사용
					   type:'change',
					   fn:function(e){
						   var comboValue = $(e.target).val();
						   // 품목명에 대한 품목코드 set				       	  			
						   $("#gridMain").jqGrid("setRowData", this.id[0], {"cost_type" : comboValue});				       	  					
						   
						   // 그리드 상태	
						   var rowId = $("#gridMain").getGridParam("selrow");
						   $("#gridMain").jqGrid("setRowData", rowId, {"status" : "U", "status_nm" : "수정"});

					   } 
					}]
				   
			   	},
			   	formatter : 'select',
			   	afterEditCell:function(id,name,val,iRow,iCol){
		   			$("#"+iRow+"_"+name).bind('blur',function(){
		   				$('#gridMain').saveCell(iRow,iCol);
		   			});
		   		}
		   
		   }
		   ,{name:'goalcost',  	index:'goalcost',  	jsonmap:'goalcost',   align:'center',   cellattr:jsFormatterCell, width:80, editable:true, edittype:"text",
			   editoptions:{dataInit: function(element) {
				                   $(element).keyup(function(){
				                        // 그리드 상태
				                        if (element.value != null) {
				                        	var rowId = $("#gridMain").getGridParam("selrow");
				                        	$("#gridMain").jqGrid("setRowData", rowId, {"status" : "U", "status_nm" : "수정"});
				                        }
				                    });
				                 }
			   }
		   }
		   ,{name:'remark',  	index:'remark',  	jsonmap:'remark',   align:'center',   cellattr:jsFormatterCell, width:200, editable:true, edittype:"text",
			   editoptions:{dataInit: function(element) {
				                   $(element).keyup(function(){
				                        // 그리드 상태
				                        if (element.value != null) {
				                        	var rowId = $("#gridMain").getGridParam("selrow");
				                        	$("#gridMain").jqGrid("setRowData", rowId, {"status" : "U", "status_nm" : "수정"});
				                        }
				                    });
				                 }
			   },
			   afterEditCell:function(id,name,val,iRow,iCol){
		   			$("#"+iRow+"_"+name).bind('blur',function(){
		   				$('#gridMain').saveCell(iRow,iCol);
		   			});
		   		}
		   }
		   ,{name:'remark2',  	index:'remark2',  	jsonmap:'remark2',   align:'center',   cellattr:jsFormatterCell, width:200, editable:true, edittype:"text",
			   editoptions:{dataInit: function(element) {
				                   $(element).keyup(function(){
				                        // 그리드 상태
				                        if (element.value != null) {
				                        	var rowId = $("#gridMain").getGridParam("selrow");
				                        	$("#gridMain").jqGrid("setRowData", rowId, {"status" : "U", "status_nm" : "수정"});
				                        }
				                    });
				                 }
			   },
			   afterEditCell:function(id,name,val,iRow,iCol){
		   			$("#"+iRow+"_"+name).bind('blur',function(){
		   				$('#gridMain').saveCell(iRow,iCol);
		   			});
		   		}
		   	}
			]			    
			, width  : 1180
			, height : 350
	        , rowNum : 10	        
		 	, rowList: [10,100,500]
		    , viewrecords: true
		    , jsonReader : { 
		    	repeatitems : false
		    }
		    , pager : "#gridMainPager"
		    , gridview : true
		    , rownumbers : true
		    , loadtext : "검색 중입니다."
			, emptyrecords : "검색된 데이터가 없습니다."
			, recordtext : "총 {2} 건 데이터 ({0}-{1})"
			, shrinkToFit : true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, multiselect : true
			, multiboxonly : true	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제
			, cellEdit : true		       // 셀 수정 기능 사용여부
			, cellsubmit : 'clientArray'   // 'remote' / 'clientArray'	
			, ondblClickRow: function(rowId) {
			}
			, loadComplete : function(data) {
				// 그리드 데이터 총 갯수
		        var sTotalRecords = COMMON.fn_getCommaString($("#gridMain").jqGrid("getGridParam","records"));                				
				$("#id_totalRecords").text(sTotalRecords);
				
		        fn_scrin_block_ui("UB");
            				
 	
	        }		
		}).navGrid("#gridMainPager",{edit:false,add:false,del:false,search:false,refresh:false});
		
		
		
		
		
		
		
	}
	
	fn_goal_regist = function(){
		$.unibillDialog.confirm('확인', "등록하시겠습니까?", 
				function (isTrue) {
					try {
						// 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						
						CUSTOM.fn_grid_edit_data_create("gridMain", "frm_main", "gridEdit", "A");
						COMMON.fn_submit_createForm("frm_main", "proc_frm", CONTEXT_ROOT + "/receipt/saveEditContent.do", "CUSTOM.fn_delete_callback_updatedata");
			
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);
	}
	
	fn_goal_delete = function(){
		$.unibillDialog.confirm('확인', "선택한 행을 삭제하시겠습니까?", 
				function (isTrue) {
					try {
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						
						CUSTOM.fn_grid_edit_deldata_create("gridMain", "frm_main", "gridEditDel", "A");
						COMMON.fn_submit_createForm("frm_main", "proc_frm", CONTEXT_ROOT + "/receipt/saveEditDelContent.do", "CUSTOM.fn_delete_callback_deldata");
						
						$("#id_loader").hide();
						COMMON.fn_scrin_block_ui("UB");
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);
		
	}
	
	/**
	 * @method
	 * @description 그리드 DATA 생성
	 * @author 홍길동(2016.10.04)
	 */
	fn_grid_edit_data_create = function(_sGridId, _sFormId, _sId, _sGun) {

		var rowDatas   = $("#"+_sGridId).jqGrid("getRowData");
		var colNames   = $("#"+_sGridId).jqGrid("getGridParam","colNames");
		var allRowData = "";		
		var sExcept    = ["rn","cb","status_nm","gub_nm"];
		for (var i=0; i<rowDatas.length; i++) {
			
		}
		

		for (var i=0; i<rowDatas.length; i++) {
			var rowData  = "";

			for (var j=0; j<colNames.length; j++) {
				var colModels = $("#"+_sGridId).jqGrid("getGridParam","colModel");
				var colModel = colModels[j].name;

				if (!COMMON.fn_chekExcept(sExcept, colModel)) {

					// 그리드 값만 추출
					if (_sGun == "A") {
						rowData += "!" + rowDatas[i][colModel];
					} else if (_sGun == "B") {  // 그리드 col id와 값 추출
						if (colModel.indexOf("_NM") == -1) {
							rowData += "!" + colModel + "=" + rowDatas[i][colModel].replace("&nbsp;","");
						}
					}

				}
			}
			allRowData += "@" + rowData.substring(1);
		}
		allRowData = allRowData.substring(1);
		
		if (allRowData != "") {
			$("#"+_sFormId).find("#"+_sId).remove();
			$('<input/>').attr({type:'hidden',id:_sId,name:_sId,value:allRowData}).appendTo("#"+_sFormId);
		}

	}
	
	fn_grid_edit_deldata_create = function(_sGridId, _sFormId, _sId, _sGun){
		var rowIds = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
		var str = "";
		var allRowData = "";
		
		if (rowIds.length == 0) {	
			$.unibillDialog.alert('알림', '항목을 선택해 주십시오.');
			return;
		}
		
		var colModels = $("#"+_sGridId).jqGrid("getGridParam","colModel");
		
		for(var i=0; i<rowIds.length; i++){
			var sRowData = $("#"+_sGridId).getRowData(rowIds[i]);
			
			for(var j=0; j<colModels.length; j++){
				var colModel = colModels[j].name;
				str += "!" + sRowData[colModel];
			}
			allRowData += "@" + str.substring(1);
		}
		allRowData = allRowData.substring(1);
		
		if (allRowData != "") {
			$("#"+_sGridId).find("#"+_sId).remove();
			$('<input/>').attr({type:'hidden',id:_sId,name:_sId,value:allRowData}).appendTo("#"+_sFormId);
		}
	}
	
	fn_delete_callback_updatedata = function(_sOID){
		// 그리드 조회
		COMMON.fn_search_mainGrid('frm_main', 'gridMain');
	}
	
	fn_delete_callback_deldata = function(_sOID) {
		// 그리드 조회
		COMMON.fn_search_mainGrid('frm_main', 'gridMain');
	}
	
	
	
	fn_btn_erp_make = function(){
		if($("#frm_detail").find("#bill_ym_yyyy option:selected").val() == ""){
			$.unibillDialog.alert('알림', '청구년을 선택해 주십시오.');
			return;
		}
		if($("#frm_detail").find("#bill_ym_mm option:selected").val() == ""){
			$.unibillDialog.alert('알림', '청구월을 선택해 주십시오.');
			return;
		}
		
		var _sFormId = "frm_detail";

        $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

        COMMON.fn_submit_createForm( _sFormId, "proc_frm", CONTEXT_ROOT + "/erp/makeERP.do", "CUSTOM.fn_makeERP_callback");
	}
	
	fn_makeERP_callback = function(){
		$("#ly_pop").hide();
        $("#frm_main").find("#btn_search").click();
	}
	
	
	fn_btn_erp_send = function(){
		var _sFormId = "frm_detail";

        $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

        COMMON.fn_submit_createForm( _sFormId, "proc_frm", CONTEXT_ROOT + "/erp/sendERP.do", "CUSTOM.fn_sendERP_callback");
	}
	
	fn_sendERP_callback = function(){
		$("#ly_pop").hide();
        $("#frm_main").find("#btn_search").click();
	}
	
	
	
	
	
	
	
	

	
	
	fn_dataRegist = function(){
		COMMON.fn_reset_form("frm_detail"); // 폼 초기화						
		
		
			COMMON.fn_view_content("frm_main", "frm_detail", "", "INSERT");
		
	}
	
	fn_recipt = function(){
		COMMON.fn_reset_form("frm_detail"); // 폼 초기화		
		COMMON.fn_view_content("frm_main", "frm_detail", "", "INSERT");
		
		setTimeout(function(){
			//$("#ly_pop").find("#od_no").val("주문번호생성을 누르세요");
			$("#ly_pop").find("#od_no").attr("readonly",true);
		},1000)
	}
	
	fn_recipt_com = function(){
		var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
		
		if (rowIds.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			return;
		}
		var ids = "";
		for(var i=0; i<rowIds.length; i++){
			if(i==0){
				ids += rowIds[i];
			}else{
				ids += ";" + rowIds[i];
			}
		}
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/contSeq.do',
			datatype : "json",
			traditoinal: true,
			data: 'odnos=' + ids,
			success : function(data) {
				
				if(data.updateFlagResult == true){
					// 리로드
					COMMON.fn_search_mainGrid("frm_main", "gridMain");
					
					$.unibillDialog.alert('알림', '접수가 완료 되었습니다');
				}else{
					$.unibillDialog.alert('알림', '선택된 접수중에 완료처리가 되지 않은 것이 있습니다. 확인해 주세요');
				}
				
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '접수완료 처리가 되지않았습니다.');
			}
		});
		
		
	}

	/* 상세화면에서 값전달시 */
	fn_insodcont = function(){
		 
		var odno = $("#ly_pop").find("#od_no").val();
		var odcnt = $("#ly_pop").find("#od_cnt").val();
		var odtype = $("#ly_pop").find("#od_type").val();
		var contseq = $("#ly_pop").find("#cont_seq").val(); 
		
		
		var param = {"odno": odno,  "odcnt": odcnt,  "odtype": odtype,  "contseq": contseq };		
 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/insodcont.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			success : function(data) {  
				/*
				if(data.issuccess == true){ 
					$.unibillDialog.alert('알림', '접수가 완료 되었습니다');
				}else{
					$.unibillDialog.alert('알림', '선택된 접수중에 완료처리가 되지 않은 것이 있습니다. 확인해 주세요');
				}		
				*/		 
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '접수완료 처리가 되지않았습니다.');
			}
		});
		
		
	}


	/* 상세화면에서 값전달시 */
	fn_sellid = function(){		 
		var srn = $("#ly_pop").find("#srn").val();
		var jrn = $("#ly_pop").find("#jrn").val();
		var brn = $("#ly_pop").find("#brn").val(); 
		
		var param = {"srn": srn,  "jrn": jrn,  "brn": brn  };		
 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/insodcont.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			success : function(data) {  
				/*
				if(data.issuccess == true){ 
					$.unibillDialog.alert('알림', '접수가 완료 되었습니다');
				}else{
					$.unibillDialog.alert('알림', '선택된 접수중에 완료처리가 되지 않은 것이 있습니다. 확인해 주세요');
				}		
				*/		 
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '접수완료 처리가 되지않았습니다.');
			}
		});
		
		
	}

	fn_chcont = function(ubSeqArr){ 
			var test="11";
			/*
			var stop_type = $("#ly_pop").find("#stop_type").val();
			var req_day = $("#ly_pop").find("#req_day").val();
			var od_type = $("#ly_pop").find("#od_type").val();
			var ncust_id = $("#ly_pop").find("#ncust_id").val(); 
			var nkey_no = $("#ly_pop").find("#nkey_no").val();
			var zip_no = $("#ly_pop").find("#zip_no").val();
			var addr1 = $("#ly_pop").find("#addr1").val();
			var addr2 = $("#ly_pop").find("#addr2").val();
			var remark = $("#ly_pop").find("#remark").val(); 
			*/
			
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/insod.do',
				datatype : "json",
				traditoinal: true,
			//	data: "ubSeqArr="+ubSeqArr+"&test="+test+"&stop_type="+stop_type+"&req_day="+req_day+"&od_type="+od_type+"&ncust_id="+ncust_id+"&nkey_no="+nkey_no+"&zip_no="+zip_no+"&addr1="+addr1+"&addr2="+addr2+"&remark="+remark ,
				data: "ubSeqArr="+ubSeqArr+"&test="+test ,
				success : function(data) {   
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					$.unibillDialog.alert('알림', '마감 처리가 완료 되지않았습니다.');
				}
			});
			
			
		}
	

	fn_getmasterkey = function(masterKeyList){   
				
		var sRowId   = $("#gridMain").getGridParam("selrow");
		var sRowData = $("#gridMain").jqGrid("getRowData",sRowId);
		var keyArr="";

		 
			for(var i=0; i < masterKeyList.length; i++) { 
				 var keyone = sRowData[masterKeyList[i]];
				 if(i>0) keyArr += ",";
				 keyArr += keyone;
			}
			
			//alert("keyArr="+keyArr+" masterKeyList="+masterKeyList);
		 
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/insyccust.do',
				datatype : "json",
				traditoinal: true,
				data: "ubSeqArr="+ubSeqArr+"&custIdArr="+custIdArr ,
				success : function(data) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', '연체자 등록이 완료 되지않았습니다.');
				}
			});
			
			
		}


	fn_insyccust = function(){   
			var ubSeqArr = $("#gridMain").jqGrid("getGridParam","selarrrow");
			var custIdArr ="";
			
			for(var i=0; i < ubSeqArr.length; i++) {
				 var rowData = $("#gridMain").getRowData(ubSeqArr[i]);
				 var cust_id = rowData["cust_id"];
				 if(i>0) custIdArr += ",";
				 custIdArr += cust_id;
			}
			
			//alert("custIdArr="+custIdArr+" ubSeqArr="+ubSeqArr);
		 
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/insyccust.do',
				datatype : "json",
				traditoinal: true,
				data: "ubSeqArr="+ubSeqArr+"&custIdArr="+custIdArr ,
				success : function(data) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', '연체자 등록이 완료 되지않았습니다.');
				}
			});
			
			
		}

	fn_delyccust = function(){   
			var ubSeqArr = $("#gridMain").jqGrid("getGridParam","selarrrow");
			var custIdArr ="";
			
			for(var i=0; i < ubSeqArr.length; i++) {
				 var rowData = $("#gridMain").getRowData(ubSeqArr[i]);
				 var cust_id = rowData["cust_id"];
				 if(i>0) custIdArr += ",";
				 custIdArr += cust_id;
			}
			
			//alert("custIdArr="+custIdArr+" ubSeqArr="+ubSeqArr);
		 
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/delyccust.do',
				datatype : "json",
				traditoinal: true,
				data: "ubSeqArr="+ubSeqArr+"&custIdArr="+custIdArr ,
				success : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', '연체자 삭제 완료 되지않았습니다.');
				}
			});
			
			
		}
	
	// 수동수납 & 분할수납
	fn_msunap = function(_sColId, _sFormId, _sScrin_code, _sGridId){   
		 
		//alert($("#frm_main").find("#btn_msunap").attr("id") + ", " + $("#frm_tab").find("#btn_msunap").attr("id"));
		/*
		var sRowId   = $("#gridMain").getGridParam("selrow");
		var sRowData = $("#gridMain").jqGrid("getRowData",sRowId);		
		var sym = sRowData["sym"];
		var eym = sRowData["eym"];
		var cost = sRowData["cost"];
		var rep_cust_id = sRowData["rep_cust_id"];
		alert("fn_msunap> sym="+sym+" rep_cust_id="+rep_cust_id); */
		//frm_main
		//alert("fn_msunap> sFormId="+sFormId );
		//alert("fn_msunap> _sColId="+_sColId+" _sScrin_code="+_sScrin_code);
		var sUbseq="1";
		COMMON.fn_view_content_scrin("frm_main", "frm_detail", sUbseq,"Y", "INSERT","M040514", "mb_hissunap", "","","미납수동처리"); 
		  
			/*
			var ubSeqArr = $("#gridMain").jqGrid("getGridParam","selarrrow");
			var custIdArr ="";
			
			for(var i=0; i < ubSeqArr.length; i++) {
				 var rowData = $("#gridMain").getRowData(ubSeqArr[i]);
				 var cust_id = rowData["cust_id"];
				 if(i>0) custIdArr += ",";
				 custIdArr += cust_id;
			}
			*/
			//alert("custIdArr="+custIdArr+" ubSeqArr="+ubSeqArr);
		 
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/msunap.do',
				datatype : "json",
				traditoinal: true,
				data: "ubSeqArr="+ubSeqArr+"&custIdArr="+custIdArr ,
				success : function(data) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', '연체자 등록이 완료 되지않았습니다.');
				}
			});
			
			
		}
 
	fn_insod = function(){ 
			
			
			var test="11";
			var od_type = $("#ly_pop").find("#od_type").val(); 
			var stop_type = $("#ly_pop").find("#stop_type").val();
			var req_day = $("#ly_pop").find("#req_day").val(); 
			var od_day = $("#ly_pop").find("#od_day").val();
			var ncust_id = $("#ly_pop").find("#ncust_id").val(); 
			var nkey_no = $("#ly_pop").find("#nkey_no").val();
			var zip_no = $("#ly_pop").find("#zip_no").val();
			var addr1 = $("#ly_pop").find("#addr1").val();
			var addr2 = $("#ly_pop").find("#addr2").val();
			var remark = $("#ly_pop").find("#remark").val(); 
		    var ubSeqArr = $("#gridMain").jqGrid("getGridParam","selarrrow");
		 //   alert("fn_insod> ubSeqArr="+ubSeqArr); 
		//	alert("fn_insod> od_type="+od_type);
		    if (!COMMON.fn_columnValidate("frm_detail", "(은)는 필수입력항목입니다.")) return;
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/receipt/insod.do',
				datatype : "json",
				traditoinal: true,
				data: "ubSeqArr="+ubSeqArr+"&test="+test+"&stop_type="+stop_type+"&req_day="+req_day+"&od_day="+od_day+"&od_type="+od_type+"&ncust_id="+ncust_id+"&nkey_no="+nkey_no+"&zip_no="+zip_no+"&addr1="+addr1+"&addr2="+addr2+"&remark="+remark ,
				//data: "ubSeqArr="+ubSeqArr+"&test="+test+"&stop_type="+stop_type+"&req_day="+req_day+"&od_type="+od_type+"&ncust_id="+ncust_id+"&nkey_no="+nkey_no+"&zip_no="+zip_no+"&addr1="+addr1+"&addr2="+addr2+"&remark="+remark ,
			//	data: "ubSeqArr="+ubSeqArr+"&test="+test ,			 
				success : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				fail : function(data) {   
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', data.resultMsg);
				},
				error: function(xhr, status, error) {
					// 그리드 조회
					COMMON.fn_search_mainGrid('frm_main', 'gridMain');
					$.unibillDialog.alert('알림', '마감 처리가 완료 되지않았습니다.');
				}
				
			});
			
			
		}
	
	
	
fn_inscont = function(ubSeqArr){
		//alert("ubSeqArr : "+ ubSeqArr[0]);  
		//alert("ubSeqArr : "+ ubSeqArr );
		//var aaa = ubSeqArr.replace(/,/gi,';');
		//alert(aaa);
		//var param = {"ubSeqArr": ubSeqArr  };		
//		var param = {"ubSeqArr": ubSeqArr  };
		var test="11";
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/inscont.do',
			datatype : "json",
			traditoinal: true,
			data: "ubSeqArr="+ubSeqArr+"&test="+test ,
			success : function(data) {   
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			fail : function(data) {   
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '마감 처리가 완료 되지않았습니다.');
			}
		});
		
		
	}

fn_odcancel = function(ubSeqArr){ 
		var test="11";
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/odcancel.do',
			datatype : "json",
			traditoinal: true,
			data: "ubSeqArr="+ubSeqArr+"&test="+test ,
			success : function(data) {   
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			fail : function(data) {   
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '취소 처리가 완료 되지않았습니다.');
			}
		});
		
		
	}



fn_insjob = function(ubSeqArr){  
	var test="11";
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/insjob.do',
			datatype : "json",
			traditoinal: true,
			data: "ubSeqArr="+ubSeqArr+"&test="+test ,
			success : function(data) {   
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '작업등록이 실패되었습니다.');
			}
		});
		
		
	}
	

fn_insjobstep = function(ubSeqArr){  
	var test="11";
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/insjobstep.do',
			datatype : "json",
			traditoinal: true,
			data: "ubSeqArr="+ubSeqArr+"&test="+test ,
			success : function(data) {
				// 그리드 조회
				COMMON.fn_search_mainGrid('frm_main', 'gridMain');
				
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '작업전달이 실패되었습니다.');
			}
		});
		
		
	}

fn_jobreject = function(ubSeqArr){  
	var test="11";
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/jobreject.do',
			datatype : "json",
			traditoinal: true,
			data: "ubSeqArr="+ubSeqArr+"&test="+test ,
			success : function(data) {   
				// 그리드 조회
				COMMON.fn_search_mainGrid('frm_main', 'gridMain');
				
				$.unibillDialog.alert('알림', data.resultMsg);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '작업반려가 실패되었습니다.');
			}
		});
		
		
	}
	

fn_getodno = function(){
		 
		var param = {"key_nm": "od_no"};		
 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/getseqstr.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			async: false ,
			success : function(data) {   
				$("#ly_pop").find("#od_no").val(data.odno); 
				//$("#frm_detail").find("#org_od_no").val(data.odno);  // 수정전 값
				//$("#frm_detail").find("#od_no").val(data.odno);      // 수정후 값
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '주문번호 생성이 오류가 발생하였습니다');
			}
		});
		
		
	}



fn_setcontday = function(){
		
		var odno = $("#ly_pop").find("#od_no").val();
		var contseq = $("#ly_pop").find("#cont_seq").val();
		var openday = $("#ly_pop").find("#open_day").val();
		var termday = $("#ly_pop").find("#term_day").val();
		var keyno = $("#ly_pop").find("#key_no").val();
		var param = {"odno": odno,  "contseq": contseq , "keyno": keyno ,   "termday": termday , "openday": openday };		
		// alert("contseq="+contseq);
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/setcontday.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			success : function(data) {   
				$("#ly_pop").find("#od_no").val(data.odno);
				$.unibillDialog.alert('알림', '변경 되었습니다.');
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '계약요금함목 일자 수정처리 중 오류가 발생하였습니다');
			}
		});
		
		
	}
 
 fn_mod_od_type = function(){
	 var combo = $("#frm_detail").find("#od_type");
	 var scrin_id = $("#frm_detail").find("#scrin_code");
	 if(scrin_id=="M020325") {
		 combo.remove(0);
		 combo.remove(1);
	 }
 }
 
fn_odtypechange2 = function(){
	
	var val= $("#frm_detail").find("#od_type option:selected").val();
	 //alert("변경 fn_odtypechange." + val);
	
	$("#frm_detail").find("#od_no").attr("readonly",true); //keyno sql 반영 readonly
	$("#frm_detail").find("#od_no").css({ 'background-color':'rgb(229, 229, 180)'});
	
	document.getElementById("cont_gd_id").style.visibility = 'hidden';
	document.getElementById("cust_id").style.visibility = 'hidden';
	document.getElementById("cont_year").style.visibility = 'hidden';
	document.getElementById("nkey_no").style.visibility = 'hidden';
	document.getElementById("ncust_id").style.visibility = 'hidden';
	document.getElementById("stop_type").style.visibility = 'hidden';
	document.getElementById("zip_no").style.visibility = 'hidden';
	document.getElementById("addr1").style.visibility = 'hidden';
	document.getElementById("addr2").style.visibility = 'hidden';
	//$("#frm_detail").find("#btn_addr_search").attr("disabled",true);
	document.getElementById('btn_addr_search').style.visibility = 'hidden';
	
	if (val=='1'){ 
		document.getElementById("cont_gd_id").style.visibility = 'visible';
		document.getElementById("cust_id").style.visibility = 'visible';
		document.getElementById("cont_year").style.visibility = 'visible';
		document.getElementById("zip_no").style.visibility = 'visible';
		document.getElementById("addr1").style.visibility = 'visible';
		document.getElementById("addr2").style.visibility = 'visible';
		document.getElementById("btn_addr_search").style.visibility = 'visible'; 
	} 
	else { 
		$("#frm_detail").find("#od_cnt").val("1");
		
		if (val=='2'){  
		} 
		else if (val=='3'){  
			document.getElementById("stop_type").style.visibility = 'visible';
		} 
		else if (val=='4'){  
		} 
		else if (val=='5'){  
			document.getElementById("zip_no").style.visibility = 'visible';
			document.getElementById("addr1").style.visibility = 'visible';
			document.getElementById("addr2").style.visibility = 'visible';
			document.getElementById("btn_addr_search").style.visibility = 'visible'; 
		} 
		else if (val=='6'){
			document.getElementById("ncust_id").style.visibility = 'visible';  
		} 
		else if (val=='7' || val=='10'){  
			document.getElementById("nkey_no").style.visibility = 'visible';  
		} 
		 
	}
	  
}


fn_odtypechange  = function(){
	
	var scrin_code = $("#frm_detail").find("#scrin_code").val();
//	alert("fn_odtypechange>  scrin_code="+scrin_code);
	
	if(scrin_code !='M020319' && scrin_code !='M020301' && scrin_code !='M020325') return;
	
	var val= $("#frm_detail").find("#od_type option:selected").val();
	// alert("변경 입니다." + val);
	
	$("#frm_detail").find("#od_no").attr("readonly",true); //keyno sql 반영 readonly
	$("#frm_detail").find("#od_no").css({ 'background-color':'rgb(229, 229, 180)'});	
	
	
	$("#frm_detail").find("#cont_gd_id").attr("disabled",true);
	$("#frm_detail").find("#cust_id").attr("disabled",true);
	$("#frm_detail").find("#cont_year").attr("disabled",true);
	$("#frm_detail").find("#nkey_no").attr("disabled",true);
	$("#frm_detail").find("#ncust_id").attr("disabled",true);
	$("#frm_detail").find("#stop_type").attr("disabled",true);
	$("#frm_detail").find("#jobend_yn").attr("disabled",true);
	$("#frm_detail").find("#cont_yn").attr("disabled",true);
	$("#frm_detail").find("#job_yn").attr("disabled",true);
	$("#frm_detail").find("#confirm_user").attr("disabled",true);
	$("#frm_detail").find("#zip_no").attr("disabled",true);
	$("#frm_detail").find("#addr1").attr("disabled",true);
	$("#frm_detail").find("#addr2").attr("disabled",true); 
	$("#frm_detail").find("#btn_addr_search").attr("disabled",true);
	document.getElementById('btn_addr_search').style.visibility = 'hidden';
	
	if (val=='1'){ 
		$("#frm_detail").find("#cont_gd_id").attr("disabled",false);
		$("#frm_detail").find("#cust_id").attr("disabled",false);
		$("#frm_detail").find("#cont_year").attr("disabled",false);
		$("#frm_detail").find("#zip_no").attr("disabled",false);
		$("#frm_detail").find("#addr1").attr("disabled",false);
		$("#frm_detail").find("#addr2").attr("disabled",false);
		$("#frm_detail").find("#btn_addr_search").attr("disabled",false);
		document.getElementById('btn_addr_search').style.visibility = 'visible';
	} 
	else { 
		$("#frm_detail").find("#od_cnt").val("1");
		
		if (val=='2'){  
		} 
		else if (val=='3'){  
			$("#frm_detail").find("#stop_type").attr("disabled",false); 
		} 
		else if (val=='4'){  
		} 
		else if (val=='5'){  
			$("#frm_detail").find("#zip_no").attr("disabled",false);
			$("#frm_detail").find("#addr1").attr("disabled",false);
			$("#frm_detail").find("#addr2").attr("disabled",false); 
			$("#frm_detail").find("#btn_addr_search").attr("disabled",false);
			document.getElementById('btn_addr_search').style.visibility = 'visible';
		} 
		else if (val=='6'){  
			$("#frm_detail").find("#ncust_id").attr("disabled",false); 
		} 
		else if (val=='7' || val=='10'){  
			$("#frm_detail").find("#nkey_no").attr("disabled",false); 
		} 
		 
	}
	  
}

fn_detail_init = function(scrin_id){
	//alert("fn_detail_init");
	fn_dateControl();
	//fn_mod_od_type();	
	fn_odtypechange();
}



fn_dateControl = function(){
	//alert("fn_dateControl");
	$(".srh_wrap").find("#from_request_date_datepicker").val("");
	$(".srh_wrap").find("#to_request_date_datepicker").val("");
	
	$(".srh_wrap").find("#from_open_date_datepicker").val("");
	$(".srh_wrap").find("#to_open_date_datepicker").val("");
	
	$(".srh_wrap").find("#from_plan_date_datepicker").val("");
	$(".srh_wrap").find("#to_plan_date_datepicker").val("");
	
	$(".srh_wrap").find("#req_day_datepicker").val("");
	$(".srh_wrap").find("#open_day_datepicker").val(""); 
	
}



fn_scrin_init = function(){
	
	var mon = $("#frm_main").find("#mon").val();
	var scrin_code = $("#frm_main").find("#scrin_code").val();
	 
	//alert("fn_scrin_init> mon="+mon+" scrin_code="+scrin_code);
	fn_dateControl();
	
	if(scrin_code=='M020320') {//계약관리
		if(mon=='1') {
			COMMON.fn_set_menu('M020320','만기30일전 대상조회','I');
		}
		else if(mon=='2') {
			COMMON.fn_set_menu('M020320','만기미해지 대상조회','I');
		}
		else if(mon=='3') {
			COMMON.fn_set_menu('M020320','지정알림 대상조회','I');
		}
		//document.getElementById("zip_no").style.visibility = 'visible';
	}
	else if(scrin_code=='M020301') {//신청관리
		if(mon=='4') {
			COMMON.fn_set_menu('M020301','신규신청 대상조회','I');
		}
		else if(mon=='5') {
			COMMON.fn_set_menu('M020301','작업진행 대상조회','I');
		}
		else if(mon=='6') {
			COMMON.fn_set_menu('M020301','계약마감대기 대상조회','I');
		}
		
		if(mon!='') {
			//alert("fn_scrin_init> mon="+mon+" scrin_code="+scrin_code);
		
			/*
			document.getElementById("cont_yn").style.visibility = 'hidden';
			document.getElementById("job_yn").style.visibility = 'hidden';
			document.getElementById("jobend_yn").style.visibility = 'hidden';
			document.getElementById("req_day_datepicker").style.visibility = 'hidden';
			document.getElementById("od_type").style.visibility = 'hidden';
		
			document.getElementById("btn_insjob").style.visibility = 'hidden'; 
			document.getElementById("btn_inscont").style.visibility = 'hidden';
			document.getElementById("btn_cancel").style.visibility = 'hidden';
			document.getElementById("btn_delete").style.visibility = 'hidden';
			*/
			$(".srh_wrap").find("#cont_yn").attr("disabled",true);
			$(".srh_wrap").find("#job_yn").attr("disabled",true);
			$(".srh_wrap").find("#jobend_yn").attr("disabled",true);
			$(".srh_wrap").find("#req_day_datepicker").attr("disabled",true);
			$(".srh_wrap").find("#od_type").attr("disabled",true);
			
  
			document.getElementById("btn_insjob").attr("disabled",true);
			document.getElementById("btn_inscont").attr("disabled",true);
			document.getElementById("btn_cancel").attr("disabled",true);
			document.getElementById("btn_delete").attr("disabled",true);
			
			
			document.getElementById("btn_insjob").style.visibility = 'hidden'; 
			document.getElementById("btn_inscont").style.visibility = 'hidden';
			document.getElementById("btn_cancel").style.visibility = 'hidden';
			document.getElementById("btn_delete").style.visibility = 'hidden';
		}
	}else if(scrin_code=='M060107'){
		$("#frm_main").find("#btn_detail").val("조치사항등록");
		$("#frm_main").find("#btn_detail").text("조치사항등록");
	} 
	 
}

fn_preOdno = function(){
	var od_no=$("#frm_detail").find("#od_no").val();
	//alert("fn_preOdno> od_no="+od_no);
	if(od_no== "" || od_no == "undefined"){
		CUSTOM.fn_getodno();
	 }
}



fn_imgextchk = function(){
	//alert("img : " + $("#imgfileuploadnm").val());
	if($("#imgfileuploadnm").val() == ""){
	}else{
		var path = $("#imgfileuploadnm").val();
		var fnm = path.split(".");
		var ext = fnm[1];
		if (!(ext == "gif" || ext == "jpg" || ext == "png")) {
			$.unibillDialog.alert('알림', '이미지파일 (.jpg, .png, .gif ) 만 업로드 가능합니다.');
			return false;
		}
	
	}
	
}

	

	getSelectionText = function() {
		//document.searchFrm.sqlquery.focus();
		var text = "";
		var selected = document.getSelection();
	    if(selected != ""){
	    	text = selected.toString();
	    }else{
	    	text = $("#searchFrm").find("#sqlquery").val()
	    }
	
	    return text;
	
	}


	fn_querySearch = function(){
		var sendQuery= getSelectionText();
		if(sendQuery == null || sendQuery == ""){
			$.unibillDialog.alert('알림', '쿼리문을 입력해주세요.');
			return;
		}
		var connType = $("#searchFrm").find("#connType").val();
		if(connType != "I" && sendQuery.substr(0,6) != "select"){
			$.unibillDialog.alert('알림', '외부연결은 조회만 가능합니다.');
			return;
		}
		
		var iserr = false;
		var colNames  = [];
		var colCamelNames  = [];
		var colModels = [];
		var tcnt=0;
	    //그리드 해제
	    $('#gridMain').jqGrid("GridUnload");
	    // 쿼리 조회
	    var param = {"exeQuery": sendQuery, "connType": connType};
	    $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/querycolumnList.json',
			data: param ,
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				$("#error").text("");
				
				if(data.queryType == "ddl"){
					$("#id_totalRecords").text(data.ddlcnt);
					$("#error").text(data.ddlMsg);
				}else{
					if(data.errMsg != null){
						iserr = true;
						$("#id_totalRecords").text("0");
						$("#error").text(data.errMsg);
					}else{
						// 쿼리 결과 그리드 
						colNames = data.keyList;
						colCamelNames = data.keyCamelList;
						if(colNames == null){
							colNames = ["no data"];
						}
						
						for(var i=0; i<colNames.length; i++){
							colModels.push({name:colNames[i], index:colNames[i], jsonmap:colNames[i], align:'center', width:'100px'});
						}

						var _sPage = 1;
						jQuery("#gridMain").jqGrid("clearGridData").jqGrid({
							 url : CONTEXT_ROOT + '/content/queryResultList.json'	
							, datatype : 'json'		
						    , colNames : colNames
						    , colModel : colModels
						    , postData: param
							, sortname  : ''
						    , sortorder : ""
							, width  : 1180
							, height : 200
							, rowNum : 20	        
						 	, rowList: [20,100,500]
						    , viewrecords: true
						    , jsonReader : { 
						    	repeatitems : false
						    }
							, pager : "#gridMainPager"
							, pginput : true	
						    , gridview : true
						    , rownumbers : true
						    , loadtext : "검색 중입니다."
							, emptyrecords : "검색된 데이터가 없습니다."
							, recordtext : "총 {2} 건 데이터 ({0}-{1})"
							, shrinkToFit : false
					  		,afterInsertRow: function (rowid, rowdata, rowelem) { // 데이터를 로드할때의 액션
					          	// 이력이 있는 경우 색상 처리
					          	if (rowdata.histYn == "Y") {
					           	   $("#" + rowid).css("background", "#E8D9FF");
					          	}
					      	}
					      	, loadComplete : function(data) {
						      	var sTotalRecords = COMMON.fn_getCommaString($("#gridMain").jqGrid("getGridParam","records"));
						      	$("#id_totalRecords").text(sTotalRecords);
								COMMON.fn_scrin_block_ui("UB");				
								// JqGrid 내비게이션 rowList 변경시 이벤트 부여.
								$(".ui-pg-selbox").change(function() {
									var searchYn = $("#"+_sFormId).find("#searchYn").val();
									if (searchYn == "Y") {
										COMMON.fn_scrin_block_ui("B");
									}
								});
								

					      	}
					      	, onPaging : function(action){}
						}).trigger("reloadGrid",[{page:_sPage}]);  //grid refresh;
						
						
					}
				}
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});	
	}
	
	fn_queryExcel = function(){
		var sendQuery= getSelectionText();
		if(sendQuery == null || sendQuery == ""){
			$.unibillDialog.alert('알림', '쿼리문을 입력해주세요.');
			return;
		}
		// 쿼리 조회
	    var param = {"exeQuery": sendQuery};
	    $("#id_loader").show();
	    $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/queryResultExcel.json',
			data: param ,
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				$("#error").text("");
				$("#id_loader").hide();
				if(data.queryType == "ddl"){
					$("#id_totalRecords").text("0");
					$("#error").text(data.excelMsg);
				}else{
					if(data.sucess == true){
						COMMON.fn_download_file('excel_download', data.OID, 'Y');
					}else{
						$("#id_totalRecords").text("0");
						$("#error").text(data.excelMsg);
					}
				}
				
					
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});	
		
		
	}
	
	fn_queryClear = function(){
		$("#searchFrm").find("#sqlquery").text("");
		$("#searchFrm").find("#sqlquery").focus();
	}
	
	fn_queryProcedure = function(){
		var sendQuery= getSelectionText();
		if(sendQuery == null || sendQuery == ""){
			$.unibillDialog.alert('알림', '쿼리문을 입력해주세요.');
			return;
		}
		var connType = $("#searchFrm").find("#connType").val();
		if(connType != "I"){
			$.unibillDialog.alert('알림', '외부연결은 프로시저생성을 지원하지 않습니다.');
			return;
		}
		// 쿼리 조회
	    var param = {"exeQuery": sendQuery};
	    $("#id_loader").show();
	    $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/queryResultProcedure.json',
			data: param ,
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				$("#error").text("");
				$("#id_loader").hide();
				if(data.queryType == "ddl"){
					//그리드 해제
				    $('#gridMain').jqGrid("GridUnload");
					if(data.sucess == true){
						//COMMON.fn_download_file('excel_download', data.OID, 'Y');
						$("#id_totalRecords").text("0");
					}else{
						$("#id_totalRecords").text("0");
						$("#error").text(data.procedureMsg);
					}
				}else{
					//그리드 해제
				    $('#gridMain').jqGrid("GridUnload");
					$("#id_totalRecords").text("0");
					$("#error").text(data.procedureMsg);
					
				}
				
				
					
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});	
	    
		
	}
	
	
	fn_changeDayYype = function(){
		if($(".srh_wrap").find("#search_day_type:checked").val() == "1"){
			$(".srh_wrap").find("#div_search_from_eday1 > label").html('종화시간<span class="asterisk"></span>');
		}else{
			$(".srh_wrap").find("#div_search_from_eday1 > label").html('시화시간<span class="asterisk"></span>');
		}
		
		
	}
	
	/**
	 * 홍충현(2021.08.20)
	 * 고객 ID 발부
	 */
	fn_gen_custid = function(){
		var val = $("#frm_detail").find("#cust_type option:selected").val();
		
		var sendData = {"val":val}
		
		if(val == ""){
			$.unibillDialog.alert('알림', '별정/관계사구분을 선택해 주십시오.');
			return;
		}
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getCustId.json',
			data: sendData,
			success: function(resp){
				$("#frm_detail").find("#cust_id").attr('value', resp.val);
				$("#frm_detail").find("#rep_cust_id").attr('value', resp.val);
			}
		});
	}
	
	/**
	 * 홍충현(2021.08.25)
	 * 
	 */
		
	fn_getmailsn = function(){
		 
		var param = {"key_nm": "mail_sn"};		
 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/getseqstr.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			async: false ,
			success : function(data) {   
				$("#ly_pop").find("#mail_sn").val(data.mailsn); 
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '메일번호 생성이 오류가 발생하였습니다');
			}
		});	
	}
	
	fn_premailsn = function(){
		var mail_sn=$("#frm_detail").find("#mail_sn").val();
		
		if(mail_sn== "" || mail_sn == "undefined"){
			CUSTOM.fn_getmailsn();
		}
	}
	
	fn_getjyno = function(){
		 
		var param = {"key_nm": "jy_no"};		
 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/getseqstr.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			async: false ,
			success : function(data) {   
				$("#ly_pop").find("#jy_no").val(data.jyno); 
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '전용선번호 생성 오류가 발생하였습니다');
			}
		});	
	}
	
	fn_prejyno = function(){
		var jyno=$("#frm_detail").find("#jy_no").val();
		
		if(jyno== "" || jyno == "undefined"){
			CUSTOM.fn_getjyno();
		}
	}
	
	fn_applysend = function(){
		$("#frm_main").find("#scrin_code").val("M070107");
		$("#frm_main").find("#tableName").val("mb_cfgsendmail");
		
		var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
		var rowDatas = $("#gridMain").jqGrid("getRowData");
		
		if (rowIds.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			$("#frm_main").find("#scrin_code").val("M070109");
			$("#frm_main").find("#tableName").val("mb_cfgtc_apply");
			return;
		}else if(rowIds.length > 5){
			$.unibillDialog.alert('알림', '최대 5개까지 선택할 수 있습니다.');
			$("#frm_main").find("#scrin_code").val("M070109");
			$("#frm_main").find("#tableName").val("mb_cfgtc_apply");
			return;
		}
			
		fn_action('btn_regist','frm_main','M070107','gridMain','','');
	
		$("#frm_main").find("#scrin_code").val("M070109");
		$("#frm_main").find("#tableName").val("mb_cfgtc_apply");
	}
	
	fn_applyData = function(){
		if ($("#frm_main").find("#scrin_code").val() == 'M070109') {
			var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
			var rowDatas = $("#gridMain").jqGrid("getRowData");
			
			var data = new Array();
			
			for (var i=0; i<rowDatas.length; i++) {
				for(var j=0; j<rowIds.length; j++){
					if(rowDatas[i]["ubseq"] == rowIds[j]){
						data.push(rowDatas[i]);
					}	
				}
			}
		}
		return data;
	}
	
	/**
	 * 홍충현(2021.10.06)
	 * 메일 작성 통신사 담당자 정보 넣기
	 */
	fn_setManagerInfo = function(){
		var val = $("#frm_detail").find("#tc_sn option:selected").val();
		
		if(val == 1 || val == 2 || val == 3)
			var sendData = {"val":val};
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/setManagerInfo.json',
			data: sendData,
			success: function(resp){
//				alert(JSON.stringify(resp));
				$("#frm_detail").find("#head").val(resp.content);
				$("#frm_detail").find("#to_email").attr('value', resp.email);
				$("#frm_detail").find("#title").attr('value', resp.title);
				$("#frm_detail").find("#ref").attr('value', resp.ref);
				$("#frm_detail").find("#tail").val(resp.tail);
				$("#frm_detail").find("#from_email").attr('value', resp.fromEmail);
			}
		});
	}
	
//	fn_createbungae = function (){
//    	$("#frm_bungae").find("#in_billym").val("");
//    	$("#frm_bungae").find("#in_jurdat").val("");
//    	
//    	// 레이어 팝업 띄우기				 
//		var tx = ($(window).width()-$("#bungae_pop .confirm_cont").width())/5;
//		var ty = ($(window).height() - 400)/2;
//		if(ty < 0){
//			ty = 15;
//		}
//		$("#bungae_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
//		$("#bungae_pop").find("#buttonFlag").val("R");
//		
//		if($(".srh_wrap").find("#job_type").find("option:selected").val() == "101"){
//			$(".send").show();
//			$(".make").hide();
//		}else{
//			$(".send").hide();
//			$(".make").show();
//		}
//		
//		$("#bungae_pop").show();  // 레이어 팝업 open
	
//	$("#frm_sendbungae").find("#send_billym").val("");
//	$("#frm_sendbungae").find("#send_jurdat").val("");
//	
//	// 레이어 팝업 띄우기				 
//	var tx = ($(window).width()-$("#bungaesend_pop .confirm_cont").width())/5;
//	var ty = ($(window).height() - 400)/2;
//	if(ty < 0){
//		ty = 15;
//	}
//	$("#bungaesend_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
//	$("#bungaesend_pop").find("#buttonFlag").val("R");
//	
//	if($(".srh_wrap").find("#job_type").find("option:selected").val() == "101"){
//		$(".send").show();
//		$(".make").hide();
//	}else{
//		$(".send").hide();
//		$(".make").show();
//	}
//	
//	$("#bungaesend_pop").show();  // 레이어 팝업 open
//    }
	
	fn_sendbungae = function (){
		var jurdat = $("#frm_main").find("#jurdat_datepicker").val();
	//	alert("jurdat1="+jurdat);
		if(jurdat=="" || jurdat==null || jurdat=="undefined" )  jurdat = $("#frm_main").find("#jurdat").val();
		//alert("jurdat2="+jurdat);
		
		var inempno = $("#frm_main").find("#inempno").val();
		var jurno = $("#frm_main").find("#jurno").val();
		var jurser = $("#frm_main").find("#jurser").val();
		
		var Array = jurdat.split('-');
		jurdat = "";
		for(var i = 0; i < Array.length; i++){
			jurdat += Array[i];
		}
		
		//alert("jurdat: " + jurdat + "inempno: " + inempno + "jurno: " + jurno + "jurser: " + jurser);
		var sendData = {"jurdat":jurdat,"inempno":inempno,"jurno":jurno,"jurser":jurser};
		
		if($(".srh_wrap").find("#jurdat_datepicker").val() == ""){
    		$.unibillDialog.alert('오류', "기표일자를 입력하세요.");
			return false;
    	}
		 
		var msg = "조건으로 분개이관 하시겠습니까?"
		
		$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					$.ajax({
						type: 'POST',
						url: CONTEXT_ROOT + '/content/sendbungae.do',
						async: false ,
						data : sendData,
						success : function(data){
							////alert("tf : " + data.tf);
							if(data.tf == true){
								$.unibillDialog.alert('알림', '분개이관이 완료되었습니다.');	
								return;
							}else if(data.tf == false){
								$.unibillDialog.alert('알림', '분개이관을 실패하였습니다.');	
								return;
							}
						},
						error : function(data) {
							$.unibillDialog.alert('알림', '분개이관을 실패하었습니다.');		
							return;
						}
				 });
				}
			);
		 return true;
    }
	
	fn_cancelbungae = function (){
		var jurdat = $("#frm_main").find("#jurdat_datepicker").val();
		//	alert("jurdat1="+jurdat);
			if(jurdat=="" || jurdat==null || jurdat=="undefined" )  jurdat = $("#frm_main").find("#jurdat").val();
			//alert("jurdat2="+jurdat); 
		var inempno = $("#frm_main").find("#inempno").val();
		var jurno = $("#frm_main").find("#jurno").val();
		var jurser = $("#frm_main").find("#jurser").val();
		
		var Array = jurdat.split('-');
		jurdat = "";
		for(var i = 0; i < Array.length; i++){
			jurdat += Array[i];
		}
		
		//alert("jurdat: " + jurdat + "inempno: " + inempno + "jurno: " + jurno + "jurser: " + jurser);
		var sendData = {"jurdat":jurdat,"inempno":inempno,"jurno":jurno,"jurser":jurser};
		
		if($(".srh_wrap").find("#jurdat_datepicker").val() == ""){
    		$.unibillDialog.alert('오류', "기표일자를 입력하세요.");
			return false;
    	}
		 
		var msg = "조건으로 분개이관 취소 하시겠습니까?"
		
		$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					$.ajax({
						type: 'POST',
						url: CONTEXT_ROOT + '/content/cancelbungae.do',
						async: false ,
						data : sendData,
						success : function(data){
							////alert("tf : " + data.tf);
							if(data.tf == true){
								$.unibillDialog.alert('알림', '분개이관 취소되었습니다.');	
								return;
							}else if(data.tf == false){
								$.unibillDialog.alert('알림', '분개이관 취소를 실패하였습니다.');	
								return;
							}
						},
						error : function(data) {
							$.unibillDialog.alert('알림', '분개이관 취소를 실패하었습니다.');		
							return;
						}
				 });
				}
			);
		 return true;
    }
	
	fn_genFun = function(userYn, quMsg, proc, retcol, form, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30 ){
		var param = "";
		var vals = new Array();
		vals[0] = fn_veryParam(p1, form);
		vals[1] = fn_veryParam(p2, form);
		vals[2] = fn_veryParam(p3, form);
		vals[3] = fn_veryParam(p4, form);
		vals[4] = fn_veryParam(p5, form);
		vals[5] = fn_veryParam(p6, form);
		vals[6] = fn_veryParam(p7, form);
		vals[7] = fn_veryParam(p8, form);
		vals[8] = fn_veryParam(p9, form);
		vals[9] = fn_veryParam(p10, form);
		vals[10] = fn_veryParam(p11, form);
		vals[11] = fn_veryParam(p12, form);
		vals[12] = fn_veryParam(p13, form);
		vals[13] = fn_veryParam(p14, form);
		vals[14] = fn_veryParam(p15, form);
		vals[15] = fn_veryParam(p16, form);
		vals[16] = fn_veryParam(p17, form);
		vals[17] = fn_veryParam(p18, form);
		vals[18] = fn_veryParam(p19, form);
		vals[19] = fn_veryParam(p20, form);
		vals[20] = fn_veryParam(p21, form);
		vals[21] = fn_veryParam(p22, form);
		vals[22] = fn_veryParam(p23, form);
		vals[23] = fn_veryParam(p24, form);
		vals[24] = fn_veryParam(p25, form);
		vals[25] = fn_veryParam(p26, form);
		vals[26] = fn_veryParam(p27, form);
		vals[27] = fn_veryParam(p28, form);
		vals[28] = fn_veryParam(p29, form);
		vals[29] = fn_veryParam(p30, form);
		
		
		var idnms = new Array();
		idnms[0] = fn_getColidNm(p1, form);
		idnms[0] = fn_getColidNm(p1, form);
		idnms[1] = fn_getColidNm(p2, form);
		idnms[2] = fn_getColidNm(p3, form);
		idnms[3] = fn_getColidNm(p4, form);
		idnms[4] = fn_getColidNm(p5, form);
		idnms[5] = fn_getColidNm(p6, form);
		idnms[6] = fn_getColidNm(p7, form);
		idnms[7] = fn_getColidNm(p8, form);
		idnms[8] = fn_getColidNm(p9, form);
		idnms[9] = fn_getColidNm(p10, form);
		idnms[10] = fn_getColidNm(p11, form);
		idnms[11] = fn_getColidNm(p12, form);
		idnms[12] = fn_getColidNm(p13, form);
		idnms[13] = fn_getColidNm(p14, form);
		idnms[14] = fn_getColidNm(p15, form);
		idnms[15] = fn_getColidNm(p16, form);
		idnms[16] = fn_getColidNm(p17, form);
		idnms[17] = fn_getColidNm(p18, form);
		idnms[18] = fn_getColidNm(p19, form);
		idnms[19] = fn_getColidNm(p20, form);
		idnms[20] = fn_getColidNm(p21, form);
		idnms[21] = fn_getColidNm(p22, form);
		idnms[22] = fn_getColidNm(p23, form);
		idnms[23] = fn_getColidNm(p24, form);
		idnms[24] = fn_getColidNm(p25, form);
		idnms[25] = fn_getColidNm(p26, form);
		idnms[26] = fn_getColidNm(p27, form);
		idnms[27] = fn_getColidNm(p28, form);
		idnms[28] = fn_getColidNm(p29, form);
		idnms[29] = fn_getColidNm(p30, form);
		
		
		var pmsg = "";
		for(var i=0; i<vals.length; i++){
			if(vals[i] != "NOP"){
				if(i == 0){
					param = vals[i];
					pmsg = idnms[i] + " : " + vals[i];
				}else{
					param += "," + vals[i];
					pmsg += "," + idnms[i] + ": " + vals[i]
				}
				
			}
		}
		if(quMsg != ""){
			var msg = pmsg + " " + quMsg;
			$.unibillDialog.confirm('확인', msg, 
				    function (isTrue) {
						fn_ajaxsend(userYn, proc, param, retcol, form, "/content/genFun.json");
					}
				);
			
		}else{
			fn_ajaxsend(userYn, proc, param, retcol, form, "/content/genFun.json");
		}
		
	}
	
	
	fn_genProc = function(userYn, quMsg, proc, retcol, form, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30 ){
		//alert(proc + ", " + retcol + ", " + p1 + ", " + p2+ ", " + p3+ ", " + p4+ ", " + p5 + ", " + p6+ ", " + p7+ ", " + p8+ ", " + p9+ ", " + p10);
		var param = "";
		var vals = new Array();
		vals[0] = fn_veryParam(p1, form);
		vals[1] = fn_veryParam(p2, form);
		vals[2] = fn_veryParam(p3, form);
		vals[3] = fn_veryParam(p4, form);
		vals[4] = fn_veryParam(p5, form);
		vals[5] = fn_veryParam(p6, form);
		vals[6] = fn_veryParam(p7, form);
		vals[7] = fn_veryParam(p8, form);
		vals[8] = fn_veryParam(p9, form);
		vals[9] = fn_veryParam(p10, form);
		vals[10] = fn_veryParam(p11, form);
		vals[11] = fn_veryParam(p12, form);
		vals[12] = fn_veryParam(p13, form);
		vals[13] = fn_veryParam(p14, form);
		vals[14] = fn_veryParam(p15, form);
		vals[15] = fn_veryParam(p16, form);
		vals[16] = fn_veryParam(p17, form);
		vals[17] = fn_veryParam(p18, form);
		vals[18] = fn_veryParam(p19, form);
		vals[19] = fn_veryParam(p20, form);
		vals[20] = fn_veryParam(p21, form);
		vals[21] = fn_veryParam(p22, form);
		vals[22] = fn_veryParam(p23, form);
		vals[23] = fn_veryParam(p24, form);
		vals[24] = fn_veryParam(p25, form);
		vals[25] = fn_veryParam(p26, form);
		vals[26] = fn_veryParam(p27, form);
		vals[27] = fn_veryParam(p28, form);
		vals[28] = fn_veryParam(p29, form);
		vals[29] = fn_veryParam(p30, form);
		//alert(proc + ", " + retcol + ", " + vals[0] + ", " + vals[1]+ ", " + vals[2]+ ", " + vals[3]+ ", " + vals[4] + ", " + vals[5]+ ", " + vals[6]+ ", " + vals[7]+ ", " + vals[8]+ ", " + vals[9]);
		
		var idnms = new Array();
		idnms[0] = fn_getColidNm(p1, form);
		idnms[1] = fn_getColidNm(p2, form);
		idnms[2] = fn_getColidNm(p3, form);
		idnms[3] = fn_getColidNm(p4, form);
		idnms[4] = fn_getColidNm(p5, form);
		idnms[5] = fn_getColidNm(p6, form);
		idnms[6] = fn_getColidNm(p7, form);
		idnms[7] = fn_getColidNm(p8, form);
		idnms[8] = fn_getColidNm(p9, form);
		idnms[9] = fn_getColidNm(p10, form);
		idnms[10] = fn_getColidNm(p11, form);
		idnms[11] = fn_getColidNm(p12, form);
		idnms[12] = fn_getColidNm(p13, form);
		idnms[13] = fn_getColidNm(p14, form);
		idnms[14] = fn_getColidNm(p15, form);
		idnms[15] = fn_getColidNm(p16, form);
		idnms[16] = fn_getColidNm(p17, form);
		idnms[17] = fn_getColidNm(p18, form);
		idnms[18] = fn_getColidNm(p19, form);
		idnms[19] = fn_getColidNm(p20, form);
		idnms[20] = fn_getColidNm(p21, form);
		idnms[21] = fn_getColidNm(p22, form);
		idnms[22] = fn_getColidNm(p23, form);
		idnms[23] = fn_getColidNm(p24, form);
		idnms[24] = fn_getColidNm(p25, form);
		idnms[25] = fn_getColidNm(p26, form);
		idnms[26] = fn_getColidNm(p27, form);
		idnms[27] = fn_getColidNm(p28, form);
		idnms[28] = fn_getColidNm(p29, form);
		idnms[29] = fn_getColidNm(p30, form);
		
		var outids = new Array();
		outids = fn_getoutColidList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30);
		
		var pmsg = "";
		for(var i=0; i<vals.length; i++){
			if(vals[i] != "NOP"){
				if(i == 0){
					param = vals[i];
					if(idnms[i] != "" && vals[i]!="" ){
						pmsg = idnms[i] + " : " + vals[i];
					}
					
				}else{
					param += "Ω" + vals[i];
					if(idnms[i] != "" && vals[i]!="" ){
						pmsg += ", " + idnms[i] + " : " + vals[i]
					}
				}
				
			}
		}
		//alert("param : " + param);
		if(quMsg != ""){
			var msg = "";
			if(form == "frm_detail"){
				msg =  quMsg
			}else if(form == "frm_main"){
				msg = pmsg + " " + quMsg;
			}
			
			$.unibillDialog.confirm('확인', msg, 
				    function (isTrue) {
				fn_ajaxPrcsend(userYn, proc, param, retcol, form, "/content/genProc.json", outids);
					}
				);
			
		}else{
			fn_ajaxPrcsend(userYn, proc, param, retcol, form, "/content/genProc.json", outids);
		}
		
		
		
	}
	
	fn_ajaxsend = function(userYn, proc, param, retcol, form, url){
		var sendData = {"userYn":userYn, "proc":proc, "param" : param};
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + url,
			data: sendData,
			success: function(resp){
				if(retcol != ""){
					$("#"+ form).find("#" + retcol).val(resp.retval);
				}else{
					if(resp.retval == "success"){
						$.unibillDialog.alert('알림', "함수 수행이 성공하였습니다.");
					}else{
						$.unibillDialog.alert('알림', resp.retval);
					}
					
				}
				
			}
		});
	}
	
	fn_ajaxPrcsend = function(userYn, proc, param, retcol, form, url, outids){
		var sendData = {"userYn":userYn, "proc":proc, "param" : param};
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + url,
			data: sendData,
			success: function(resp){
				if(retcol != ""){
					if(resp.retval.RESULT_MSG == "success"){
						for(var i=0; i<outids.length; i++){
							$("#"+ form).find("#" + outids[i]).val(resp.retval[outids[i]]);
						}
					}else{
						$.unibillDialog.alert('알림', resp.retval.RESULT_MSG);
					}
				}else{
					if(resp.retval.RESULT_MSG == "success"){
						$.unibillDialog.alert('알림', "작업이 성공하였습니다.");
					}else{
						$.unibillDialog.alert('알림', resp.retval.RESULT_MSG);
					}
					
				}
				
			}
		});
	}
	
	fn_veryParam = function(param, form){
		if(form == null || form == ""){
			form = "frm_detail";
		}
		var retstr = "";
		if(param == null || typeof param == "undefined"){
			return "NOP";
		}
		if(param == ""){
			return retstr;
		}
		
		//변수인지 아닌지 확인
		if(param.indexOf("#") > -1){
			if($(param).prop("tagName") == "INPUT"){
				var paramclass = $("#"+form).find(param).attr("class");
				if(param == "#jurdat"){alert("jurdat : " + paramclass);}
				if(paramclass == null || typeof paramclass == "undefined"){
					paramclass = "";
				}
				if(paramclass.indexOf("datepicker") > -1){
						var tmp = $("#"+form).find(param).val();
						retstr = tmp.replace(/-/gi, "");
				}else{
					retstr = $("#"+form).find(param).val();
				}
			}else if($(param).prop("tagName") == "SELECT"){
				retstr = $("#" + form).find(param + " option:selected").val();
			}else{
				//파라미터값과 ID값이 불일치 할때 datepicker 서치
				var tmp = $("#"+form).find(param + "_datepicker").val();
				if(typeof tmp != "undefined" && tmp != null && tmp != ""){
					retstr = tmp.replace(/-/gi, "");
				}
			}
		}else{
			retstr = param;
		}
		
		return retstr;
	}
	
	fn_getColidNm = function(param, form){
		var nm = "";
		if(typeof param == "undefined" || param == null  ){
			return nm;
		}
		
		if(param.indexOf("#") > -1){
			if(form == "frm_main"){
				//alert("param : " + param);

				
				if(param.match("datepicker") == "datepicker"){
					nm = $("#" + form).find("#div_search_" + param.replace("#","").replace("_datepicker","")).find('label').text();
				}else{
					nm = $("#" + form).find("#div_search_" + param.replace("#","")).find('label').text();
					//alert("nm : " + nm);
				}
			}else if(form == "frm_detail"){
				var paramclass = $("#"+form).find(param).attr("class") + "";
				nm = paramclass.substr(paramclass.indexOf("ON_")+3, paramclass.indexOf("KEY_") - (paramclass.indexOf("ON_")+3)-1);
			}
		}
		
		return nm;
	}
	
	fn_getoutColidList = function(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30){
		var retval = new Array();
		var retcnt = 0;
		
		if(p1 != null && p1.match("@")){retval[retcnt] = p1.replace("@","");	retcnt++;}
		if(p2 != null && p2.match("@")){retval[retcnt] = p2.replace("@","");	retcnt++;}
		if(p3 != null && p3.match("@")){retval[retcnt] = p3.replace("@","");	retcnt++;}
		if(p4 != null && p4.match("@")){retval[retcnt] = p4.replace("@","");	retcnt++;}
		if(p5 != null && p5.match("@")){retval[retcnt] = p5.replace("@","");	retcnt++;}
		if(p6 != null && p6.match("@")){retval[retcnt] = p6.replace("@","");	retcnt++;}
		if(p7 != null && p7.match("@")){retval[retcnt] = p7.replace("@","");	retcnt++;}
		if(p8 != null && p8.match("@")){retval[retcnt] = p8.replace("@","");	retcnt++;}
		if(p9 != null && p9.match("@")){retval[retcnt] = p9.replace("@","");	retcnt++;}
		if(p10 != null && p10.match("@")){retval[retcnt] = p10.replace("@","");	retcnt++;}
		if(p11 != null && p11.match("@")){retval[retcnt] = p11.replace("@","");	retcnt++;}
		if(p12 != null && p12.match("@")){retval[retcnt] = p12.replace("@","");	retcnt++;}
		if(p13 != null && p13.match("@")){retval[retcnt] = p13.replace("@","");	retcnt++;}
		if(p14 != null && p14.match("@")){retval[retcnt] = p14.replace("@","");	retcnt++;}
		if(p15 != null && p15.match("@")){retval[retcnt] = p15.replace("@","");	retcnt++;}
		if(p16 != null && p16.match("@")){retval[retcnt] = p16.replace("@","");	retcnt++;}
		if(p17 != null && p17.match("@")){retval[retcnt] = p17.replace("@","");	retcnt++;}
		if(p18 != null && p18.match("@")){retval[retcnt] = p18.replace("@","");	retcnt++;}
		if(p19 != null && p19.match("@")){retval[retcnt] = p19.replace("@","");	retcnt++;}
		if(p20 != null && p20.match("@")){retval[retcnt] = p20.replace("@","");	retcnt++;}
		if(p21 != null && p21.match("@")){retval[retcnt] = p21.replace("@","");	retcnt++;}
		if(p22 != null && p22.match("@")){retval[retcnt] = p22.replace("@","");	retcnt++;}
		if(p23 != null && p23.match("@")){retval[retcnt] = p23.replace("@","");	retcnt++;}
		if(p24 != null && p24.match("@")){retval[retcnt] = p24.replace("@","");	retcnt++;}
		if(p25 != null && p25.match("@")){retval[retcnt] = p25.replace("@","");	retcnt++;}
		if(p26 != null && p26.match("@")){retval[retcnt] = p26.replace("@","");	retcnt++;}
		if(p27 != null && p27.match("@")){retval[retcnt] = p27.replace("@","");	retcnt++;}
		if(p28 != null && p28.match("@")){retval[retcnt] = p28.replace("@","");	retcnt++;}
		if(p29 != null && p29.match("@")){retval[retcnt] = p29.replace("@","");	retcnt++;}
		if(p30 != null && p30.match("@")){retval[retcnt] = p30.replace("@","");	retcnt++;}
		
		
		return retval;
	}
	
	//청구서 생성/전송
	 fn_make_cheonggu = function(){
		 if(!CUSTOM.fn_mailParamValidate()){
	    		return;
	    	}
		 $("#id_loader").show();	 
		 COMMON.fn_scrin_block_ui("B");
		 
		 var repCustID = $("#frm_main").find("#rep_cust_id").val();
		 var billYM = $("#frm_main").find("#bill_ym").val();
		 var sendData = {"repCustID":repCustID,"billYM":billYM};
		 
		 //생성
		 if($(".srh_wrap").find("#job_type option:selected").val() == "100"){
			 $.ajax({
					type: 'POST',
					url: CONTEXT_ROOT + '/content/makecheonggu.do',
					async: false ,
					data : sendData,
					success : function(data){
						////alert("tf : " + data.tf);
						if(data.tf == true){
							$.unibillDialog.alert('알림', '청구서 생성이 완료되었습니다.');	0
							$("#id_loader").hide();	
							COMMON.fn_scrin_block_ui("UB");
							return;
						}else if(data.tf == false){
							$.unibillDialog.alert('알림', '청구서 생성을 실패하었습니다.');	
							$("#id_loader").hide();	
							COMMON.fn_scrin_block_ui("UB");
							return;
						}else if(data.tf == "zero"){
							$.unibillDialog.alert('알림', '청구서 생성 데이터가 없습니다.');
							$("#id_loader").hide();	
							COMMON.fn_scrin_block_ui("UB");
							return;
						}
					},
					error : function(data) {
						$.unibillDialog.alert('알림', '청구서 생성을 실패하었습니다.');		
						return;
					}
			 });
			 
		 }
		//전송
		 if($(".srh_wrap").find("#job_type option:selected").val() == "101"){
			 $.ajax({
					type: 'POST',
					url: CONTEXT_ROOT + '/content/sendCheonggu.do',
					async: false ,
					data : sendData,
					success : function(data){
						//alert("tf : " + data.tf);
						if(data.tf == true){
							$.unibillDialog.alert('알림', '청구서 전송이 완료되었습니다.');	
							return;
						}else if(data.tf == false){
							$.unibillDialog.alert('알림', '청구서 전송을 실패하었습니다.');	
							return;
						}else if(data.empty == "empty"){
							$.unibillDialog.alert('알림', '대표고객ID: ' + data.repCustID + '청구월: ' + data.billYM + ' 이메일이 등록되지 않았습니다.');	
						}else if(data.syntax == "syntax"){
							$.unibillDialog.alert('알림', '대표고객ID: ' + data.repCustID + '청구월: ' + data.billYM + ' 이메일 형식이 적합하지않습니다.');	
						}
					},
					error : function(data) {
						$.unibillDialog.alert('알림', '청구서 전송을 실패하었습니다.');		
						return;
					}
			 });
		 }
	 }
	 
	 fn_mailParamValidate = function(){
	    	if($(".srh_wrap").find("#bill_ym").val() == ""){
	    		$.unibillDialog.alert('오류', "청구월을 선택하세요.");
				return false;
	    	}
	    	if($(".srh_wrap").find("#job_type").find("option:selected").val() == ""){
	    		$.unibillDialog.alert('오류', "작업유형을 선택하세요.");
				return false;
	    	}
	    	 
	    	return true;
	    }
	 
	 fn_kpsa_send = function(){
		 	var repCustID = $("#frm_main").find("#rep_cust_id").val();
		 	var accyyyymm = $("#frm_main").find("#acc_yyyymm").val();
		 	var workDiv = $("#frm_main").find("#work_div").val();
		 	var custCD = $("#frm_main").find("#cust_cd").val();
		 	var sendData = {"repCustID":repCustID,"accyyyymm":accyyyymm,"workDiv":workDiv, "custCD":custCD};
		 
	    	if($(".srh_wrap").find("#acc_yyyymm").val() == ""){
	    		$.unibillDialog.alert('오류', "정산월을 선택하세요.");
				return false;
	    	}
	    	if($(".srh_wrap").find("#work_div").find("option:selected").val() == ""){
	    		$.unibillDialog.alert('오류', "업무구분을 선택하세요.");
				return false;
	    	}
	    	
	    	
	    	 $.ajax({
					type: 'POST',
					url: CONTEXT_ROOT + '/content/sendKpsa.do',
					async: false ,
					data : sendData,
					success : function(data){
						//alert("tf : " + data.tf);
						if(data.tf == true){
							$.unibillDialog.alert('알림', '수주품의 전송이 완료되었습니다.');	
							return;
						}else if(data.tf == false){
							$.unibillDialog.alert('알림', '수주품의 전송을 실패하였습니다.');	
							return;
						}else if(data.tf == "already"){
							$.unibillDialog.alert('알림', '수주품의 전송이 진행중입니다.');
						}
					},
					error : function(data) {
						$.unibillDialog.alert('알림', '수주품의 전송을  실패하었습니다.');		
						return;
					}
			 });
	    	 
	    	return true;
	    }
	 
	 fn_sellid_register = function(){
		 var srn = $("#ly_pop").find("#srn").val();
		 var jrn = $("#ly_pop").find("#jrn").val();
		 //alert("srn: " + srn);
		 var sendData = {"srn":srn,"jrn":jrn};
		 
		 $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/content/sellidRegister.do',
				async: false ,
				data : sendData,
				success : function(data){
					//alert("tf : " + data.tf);
					if(data.tf == true){
						$.unibillDialog.alert('알림', '매출처등록이 완료되었습니다.');	
						return;
					}else if(data.tf == false){
						$.unibillDialog.alert('알림', '매출처등록을 실패하였습니다.');	
						return;
					}
				},
				error : function(data) {
					$.unibillDialog.alert('알림', '매출처등록을 실패하었습니다.');		
					return;
				}
		 });
 	 
		 return true;
	 }
	
	fn_passcheck = function(){
		var pass=$("#frm_detail").find("#pass").val();

		//alert("fn_passcheck pass="+pass);
		if(pass.length<8) {
		//	return " 영문 대/소문자 1개이상 영문자/특수문자/숫자중 적어도 2개 이상 포함되어야 하고 2개 조합 최소길이는 10 이고 3개 조합 최소길이는 8 입니다. ";
		}
		
		let reg_num = /^[0-9]+$/; // 숫자		
		let reg_elow = /^[a-z]+$/; // 영문 소문자만
		let reg_eup = /^[A-Z]+$/; // 영문 대문자만		
		
		//let reg_name1 = /^[가-힣]+$/; // 한글만
		//let reg_name5 = /^[가-힣a-zA-Z]+$/; // 한글 + 영문만
		
		var numcnt = 0;
		var spcnt = 0;
		var engcnt = 0;
		var Upengcnt = 0;
		var johap = 0;
		//var errmsg="영문 대/소문자 1개이상 영문자/특수문자/숫자중 적어도 2개 이상 포함되어야 하고 2개 조합 최소길이는 10 이고 3개 조합 최소길이는 8 입니다." ;
		var errmsg="영문자/특수문자/숫자중 적어도 2개 이상 포함되어야 하고 2개 조합 최소길이는 10 이고 3개 조합 최소길이는 8 입니다." ;
		for(var i=0; i<pass.length; i++){ 
			var chkval = pass[i];
			if(reg_num.test(chkval)) numcnt++;
			else if(reg_elow.test(chkval)) engcnt++;
			else if(reg_eup.test(chkval)) Upengcnt++; 
			else spcnt++; 
		}
		engcnt = engcnt + Upengcnt; //대소문자 구분안하는 경우
		if(numcnt>0) johap++;
		if(engcnt>0) johap++;
		if(spcnt>0) johap++;
		//alert ("XXXX Upengcnt="+Upengcnt+ " engcnt="+engcnt+ " numcnt="+numcnt + " spcnt="+spcnt + " johap="+johap) ;
		
		if(johap <2   ) {
			return errmsg;
		}
		else if(johap ==2 && pass.length <10) {
			return errmsg;
		}
		else if(johap >=3 && pass.length <8) {
			return errmsg;
		}
		return "";
		
	}
	
	
	fn_genGridProc = function(userYn, quMsg, proc){
		var param = "";
		var rownumber = "";
		var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
		if (rowIds.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			return;
		}
		
		var contSeq ="";
		var regStatus = true;
		for(var i=0; i<rowIds.length; i++){
			var rnum = $("#gridMain").jqGrid("getInd", rowIds[i]);
			if(i==0){
				param = rowIds[i];
				rownumber = rnum;
			}else{
				param += "Ω" + rowIds[i];
				rownumber += "Ω" + rnum;
			}
		}
		
		if(quMsg != ""){
			$.unibillDialog.confirm('확인', quMsg,  function (isTrue) { 
				fn_ajaxGridPrcsend(userYn, proc, param, rownumber, "/content/genGridProc.json");
			});
			
		}else{
			fn_ajaxGridPrcsend(userYn, proc, param, rownumber, "/content/genGridProc.json");
		}
		
	}
	
	fn_ajaxGridPrcsend = function(userYn, proc, param, rownumber, url){
		var sendData = {"userYn":userYn, "proc":proc, "param":param, "rownumber":rownumber};
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + url,
			data: sendData,
			success: function(resp){
				if(resp.retval.RESULT_MSG == "success"){
					$.unibillDialog.alert('알림', resp.retval.RESULT_MSG);
				}else{
					var errmsg = resp.retval.ERR_NUMBER + "번째 행 : " + resp.retval.RESULT_MSG 
					$.unibillDialog.alert('알림', errmsg);
				}
				
				
				
			}
		});
	}
	
	fn_genProcSave = function(){
		alert("111");
	}
	
	fn_changevoiceType = function(){
		var svc_tc = $("#frm_detail").find("#svc_tc option:selected").val();
		var reg_id = $("#frm_detail").find("#reg_id").val();
		if((svc_tc == "0" || svc_tc == "1") && reg_id != null){
			$("#frm_detail").find('th:contains("음원코드")').html('음원코드<span id="ast" class="asterisk" value="1"></span>');
		}else if(svc_tc == "2" && reg_id != null){
			$("#frm_detail").find('th:contains("음원코드")').html('음원코드<span id="ast" class="asterisk" value="1"></span>');
		}else if((svc_tc == "0" || svc_tc == "1") && reg_id == null){
			$("#frm_detail").find('th:contains("음원코드")').html('음원코드<span id="ast" class="asterisk" value="1"></span>');
		}else if(svc_tc == "2" && reg_id == null){
			$("#frm_detail").find('th:contains("음원코드")').html('음원코드');
		}
	}
		
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_custom.fn_editGridMake     				= fn_editGridMake;
	_mod_custom.fn_btn_telcofile_regist 			= fn_btn_telcofile_regist;
	_mod_custom.fn_btn_telcofile_del 				= fn_btn_telcofile_del;
	_mod_custom.fn_insRequest						= fn_insRequest;
	_mod_custom.fn_regRequest						= fn_regRequest;
	_mod_custom.fn_submit_createReciptForm			= fn_submit_createReciptForm;
	_mod_custom.fn_hidden_userid					= fn_hidden_userid;
	_mod_custom.fn_stopRequest						= fn_stopRequest;
	_mod_custom.fn_openRequest						= fn_openRequest;
	
	_mod_custom.fn_excute     						= fn_excute;
	_mod_custom.fn_telRegist 						= fn_telRegist;
	_mod_custom.fn_excelUpload 						= fn_excelUpload;
	_mod_custom.fn_telSetTime							= fn_telSetTime;
	_mod_custom.fn_telCheckRun						= fn_telCheckRun;
	_mod_custom.fn_del								= fn_del;
	_mod_custom.fn_tongFileDel						= fn_tongFileDel;
	_mod_custom.fn_searchFile						= fn_searchFile;
	_mod_custom.fn_getPath							= fn_getPath;
	_mod_custom.fn_stop								= fn_stop;
	_mod_custom.fn_setRunning						= fn_setRunning;
	
	_mod_custom.fn_getCurTell						= fn_getCurTell;
	
	
	_mod_custom.fn_custBillSearch					= fn_custBillSearch;
	_mod_custom.fn_custDBillSearch					= fn_custDBillSearch;
	_mod_custom.fn_addcomma							= fn_addcomma;
	_mod_custom.fn_costwon							= fn_costwon;
	
	
	_mod_custom.fn_goal_adjust						= fn_goal_adjust;
	_mod_custom.fn_goal_regist						= fn_goal_regist;
	_mod_custom.fn_grid_edit_data_create			= fn_grid_edit_data_create;
	_mod_custom.fn_goal_delete						= fn_goal_delete;
	_mod_custom.fn_grid_edit_deldata_create			= fn_grid_edit_deldata_create;
	
	_mod_custom.fn_delete_callback_updatedata		= fn_delete_callback_updatedata;
	_mod_custom.fn_delete_callback_deldata			= fn_delete_callback_deldata;
	
	
	_mod_custom.fn_btn_erp_make						= fn_btn_erp_make;
	_mod_custom.fn_makeERP_callback					= fn_makeERP_callback;
	_mod_custom.fn_btn_erp_send						= fn_btn_erp_send;
	_mod_custom.fn_sendERP_callback					= fn_sendERP_callback;
	
	_mod_custom.fn_dateControl						= fn_dateControl;
	
	_mod_custom.fn_dataRegist						= fn_dataRegist;
	
	_mod_custom.fn_recipt							= fn_recipt;
	_mod_custom.fn_recipt_com						= fn_recipt_com;
	_mod_custom.fn_insodcont  					    = fn_insodcont;
	_mod_custom.fn_inscont  					    = fn_inscont;
	_mod_custom.fn_insjob  					    	= fn_insjob;
	_mod_custom.fn_insjobstep				    	= fn_insjobstep;
	_mod_custom.fn_chcont					    	= fn_chcont; 
	_mod_custom.fn_getodno  					    = fn_getodno;
	_mod_custom.fn_setcontday  					    = fn_setcontday;
	_mod_custom.fn_insod	  					    = fn_insod;
	_mod_custom.fn_odcancel  					    = fn_odcancel;
	_mod_custom.fn_jobreject					    = fn_jobreject;
	_mod_custom.fn_odtypechange					   	= fn_odtypechange;
	_mod_custom.fn_mod_od_type					   	= fn_mod_od_type;
	_mod_custom.fn_detail_init					   	= fn_detail_init; 
	_mod_custom.fn_scrin_init					   	= fn_scrin_init;
	_mod_custom.fn_insyccust  					    = fn_insyccust;
	_mod_custom.fn_delyccust  					    = fn_delyccust; 
	_mod_custom.fn_preOdno 							= fn_preOdno;
	
	_mod_custom.fn_imgextchk 						= fn_imgextchk;
	_mod_custom.fn_msunap 							= fn_msunap;
	
	_mod_custom.fn_querySearch 						= fn_querySearch;
	_mod_custom.fn_queryExcel 						= fn_queryExcel;
	_mod_custom.fn_queryClear 						= fn_queryClear;
	_mod_custom.fn_queryProcedure 					= fn_queryProcedure;
	
	_mod_custom.fn_changeDayYype 					= fn_changeDayYype;

	_mod_custom.fn_gen_custid    					= fn_gen_custid;
	
	_mod_custom.fn_getmailsn						= fn_getmailsn;
	_mod_custom.fn_premailsn						= fn_premailsn;
	
	_mod_custom.fn_getjyno							= fn_getjyno;
	_mod_custom.fn_prejyno							= fn_prejyno;
	
	_mod_custom.fn_applysend						= fn_applysend;
	_mod_custom.fn_applyData						= fn_applyData;
	_mod_custom.fn_setManagerInfo					= fn_setManagerInfo;
	//_mod_custom.fn_createbungae					= fn_createbungae;
	_mod_custom.fn_sendbungae						= fn_sendbungae;
	_mod_custom.fn_cancelbungae						= fn_cancelbungae;
	
	_mod_custom.fn_genFun 							= fn_genFun;
	_mod_custom.fn_genProc 							= fn_genProc;
	
	_mod_custom.fn_make_cheonggu					= fn_make_cheonggu;
	_mod_custom.fn_mailParamValidate				= fn_mailParamValidate;

	_mod_custom.fn_kpsa_send						= fn_kpsa_send;
	_mod_custom.fn_sellid_register					= fn_sellid_register;


	_mod_custom.fn_veryParam 						= fn_veryParam;
	_mod_custom.fn_getColidNm 						= fn_getColidNm;
	
	_mod_custom.fn_passcheck 						= fn_passcheck;
	
	_mod_custom.fn_genGridProc 						= fn_genGridProc;
	_mod_custom.fn_genProcSave						= fn_genProcSave;
	_mod_custom.fn_changevoiceType					= fn_changevoiceType;
	return _mod_custom;

}(UNI.CUSTOM || {}, jQuery));