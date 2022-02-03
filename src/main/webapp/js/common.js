/**
 *  공통에 필요한 이벤트
 * @namespace {Object} UNI.COMMON
 */
UNI.COMMON = (function(_mod_common, $, undefined){
		
	/**
	 * @method
	 * @description 객체가 속해 있는 클래스 목록 가져오기
	 * @author
	* @param {String} _sFormId 선택된 form의 ID
	 */
	$.fn.getClasses = function(){
		var ca = this.attr('class');
		var rval = [];
		if(ca && ca.length && ca.split){
			ca = $.trim(ca);
			ca = ca.replace(/\s+/g,' '); /* remove doube spaces */
			rval = ca.split(' ');
		}
		return rval;
	}		
	
   /**
	* @method
	* @description 메인 그리드 조회
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*        {string] _sGridId : 선택된 grid ID
	*        {int}    _sPage   : grid 현재 페이지 번호
	*/
	fn_search_mainGrid = function(_sFormId, _sGridId, _sPage) {
		$("#frm_main").find("#menu_code").remove();
		var str = '<input type="hidden" id="menu_code" name="menu_code" value="'+ $("#topForm").find("#menu_code").val() +'" />';
		$("#frm_main").append(str);
		
		//alert("fn_search_mainGrid> aaaa ");
		//alert("fn_search_mainGrid> _sFormId XX : " + _sFormId + "\n_sGridId : " + _sGridId + "\n_sPage : " + _sPage);
		COMMON.fn_scrin_block_ui("B");
		if (_sPage == undefined) _sPage = 1;
		/*var postData = $('#'+_sGridId).jqGrid('getGridParam','postData');
        var str='';
	    for(var i in postData){
	        if(postData.hasOwnProperty(i)){
	          str += i + " = " + postData[i] + '\n';}
	    }
	    alert(str);*/

		$("#"+_sGridId).jqGrid("setGridParam", {
			  datatype: "json"
			, page: _sPage
			, postData: COMMON.fn_get_serialize(_sFormId)
			, mtype: "POST"
		}).trigger("reloadGrid",[{page:_sPage}]);
		
	}

   /**
	* @method
	* @description 화면 폼 리셋 : 초기화
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_reset_form = function(_sFormId) {

		$("#"+_sFormId).each(function() {
			this.reset();
			//$("#"+_sFormId)[0].reset();   // 첫번째 form reset
			//if(this.id == "TABLENAME") this.reset();  // 특정 id form reset
			//if(this.className  == "frmClass") this.reset();  // 특정 class form reset  
		});
	}
	
   /**
	* @method
	* @description 검색 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_search_main = function() {
		var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		if (!COMMON.fn_form_obj_validate(sFormId)) return;
				
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;		
		
		var tptType = $("#"+sFormId).find("#tptype").val();
		
		// 메인 그리드 조회
		if(tptType == "V"){
			search_variableMainGrid(sFormId, "gridMain");
		}else{
			COMMON.fn_search_mainGrid(sFormId, "gridMain");
		}
		
		
		// 하단 그리드 초기화
				
		if (tptType == "C") {

			$("table").each(function() {				
				var tableId = $(this).attr("id");

				if (tableId != undefined) {
					if (tableId.indexOf("gridSub_") > -1) {
						
						COMMON.fn_clear_grid_data(tableId);
					}
				}
			});
			
		}
		
	}
	
   /**
	* @method
	* @description 엑셀다운로드 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_excel_download = function() {
		//alert($("#tptype").val() + " : " + $("#xmlId").val());
		//return;
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
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/content/contentExcelDown.do", "fn_excelDownload_callback").submit();
		}
		
		
	}

	// 엑셀다운로드 후 처리
	function fn_excelTmpDownload_callback() {
		$("#id_loader").hide();	
	}
	/**
	* @method
	* @description 엑셀다운로드 버튼 클릭시(템플릿유형)
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_excel_download_template = function(_sUrl) {
		$("#id_loader").show();	
		COMMON.fn_scrin_block_ui("B");
		
		var sFormId = "frm_main";
		
	    var input_val = $("#topForm").find("#menu_code").val();
	    
		$("#frm_main").find("#template").val(input_val);
		
		// 검색 시 객체 필수 입력값 검증
		if (!COMMON.fn_form_obj_validate(sFormId)) return;
				
		// 년도 및 월 콤보 검증
		if (!COMMON.fn_form_date_select(sFormId)) return;
		
		// 화면(화면ID (scrin_id))별 검색시 특화기능
		if (!COMMON.fn_form_scrin_validate(sFormId)) return;
		
		COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + _sUrl, "fn_excelTmpDownload_callback").submit();
		
		
		$("#id_loader").hide();	
		COMMON.fn_scrin_block_ui("UB");
	}
	
	
	/**
	* @method
	* @description 멀티엑셀다운로드 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_excel_download_sheet = function() {
		//alert($("#tptype").val() + " : " + $("#xmlId").val());
		//return;
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
			
			COMMON.fn_get_form(sFormId, "proc_frm", CONTEXT_ROOT + "/content/contentExcelDownSh.do", "fn_excelDownload_callback").submit();
		}
		
		
	}
	
   /**
	* @method
	* @description 폼 생성 후 submit
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*        {string] _sGridId : 선택된 grid ID
	*        {int}    _sPage   : grid 현재 페이지 번호
	*/
	fn_submit_createForm = function(_sFormId, _sTargetIframe, _sAction, _sCallback) {

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
				}
			});
		} catch(E) {}

		try {
			$iframe.find("#"+uniq_id).submit();
		} catch(E) {
			$.unibillDialog.alert('오류', E);
		}
		
	}
	
   /**
	* @method
	* @description 해당 폼내부 파라메터 값을 배열로 추출
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_get_serialize = function(_sFormId) {

		var o   = {};
	    var nm  = "";
	    var val = "";

	    $("#"+_sFormId).find("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea").each( function(){
			nm = $('#'+_sFormId).find($(this)).attr('id');
			
			// mask 처리 된 경우, cleanVal 함수를 통해 마스크 제거된 값을 가져오게 처리
			try {
				val = $('#'+_sFormId).find($(this)).cleanVal();
			}catch(E){
				val = $('#'+_sFormId).find($(this)).val();
			}						

			
			
			// class 값으로 int, float, doublt 인 경우, 콤마 제거
			var cs = $('#'+_sFormId).find($(this)).getClasses();
			
			if ( cs.indexOf("datepicker") != -1 ) {				
				nm = nm.replace("_datepicker", "");				
			}
			
			if (o[nm] !== undefined) {
	            if (!o[nm].push) {
	                o[nm] = [o[nm]];
	            }
	            o[nm].push(val || '');
	        } else {
	            o[nm] = val || '';
	        }				
			//alert(_sFormId + "\n" + nm + " : " + val + "\no[nm] : " + o[nm]);
		});

	    var chkNm = "";
	    $("#"+_sFormId).find("input[type='hidden']").each( function(){
	    	
	    	chkNm = $('#'+_sFormId).find($(this)).attr('id');
	    		    	
	    	//alert("["+chkNm+"]체크 갯수 : " +  cnt);
	    	
	    	//alert(chkNm + " : " + chkNm.indexOf("mult_check_"));
	    	if (chkNm.indexOf("mult_check_") > -1) {
	    		
	    		var cnt = $("#"+_sFormId).find("#"+chkNm).val();
	    		
	    		//alert("["+chkNm+"]체크 갯수 : " +  cnt);
	    		
	    		if (cnt == "") {
	    			var nm2 = chkNm.replace("mult_check_", "");
	    			o[nm2] = '' || '';	    			
	    		}
	    	}
	    });

		return o;
	}
	
	fn_get_dataform = function(_sFormId) {
		var formData = new FormData();
	    var nm  = "";
	    var val = "";

	    $("#"+_sFormId).find("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea").each( function(){
			nm = $('#'+_sFormId).find($(this)).attr('id');
			
			// mask 처리 된 경우, cleanVal 함수를 통해 마스크 제거된 값을 가져오게 처리
			try {
				val = $('#'+_sFormId).find($(this)).cleanVal();
			}catch(E){
				val = $('#'+_sFormId).find($(this)).val();
			}						

			
			
			// class 값으로 int, float, doublt 인 경우, 콤마 제거
			var cs = $('#'+_sFormId).find($(this)).getClasses();
			
			if ( cs.indexOf("datepicker") != -1 ) {				
				nm = nm.replace("_datepicker", "");				
			}
			
			formData.append(nm , val|| '');
							
			//alert(_sFormId + "\n" + nm + " : " + val + "\no[nm] : " + o[nm]);
		});

	    var chkNm = "";
	    $("#"+_sFormId).find("input[type='hidden']").each( function(){
	    	
	    	chkNm = $('#'+_sFormId).find($(this)).attr('id');
	    		    	
	    	//alert("["+chkNm+"]체크 갯수 : " +  cnt);
	    	
	    	//alert(chkNm + " : " + chkNm.indexOf("mult_check_"));
	    	if (chkNm.indexOf("mult_check_") > -1) {
	    		
	    		var cnt = $("#"+_sFormId).find("#"+chkNm).val();
	    		
	    		//alert("["+chkNm+"]체크 갯수 : " +  cnt);
	    		
	    		if (cnt == "") {
	    			var nm2 = chkNm.replace("mult_check_", "");
	    			formData.append(nm2 , '' || '');
	    		}
	    	}
	    });

		return formData;
	}
	
   /**
	* @method
	* @description 날짜 계산 (당일, 1주일, 1개월 등)
	* @author 홍길동(2016.10.04)
	* @param {string} _sGubun : 검색 조건의 시작 및 끝 일자 input 구분 (pDay : 시작일자, cDay : 끝일자)
	*        {int}    _iN     : 증감 일수
	*        {int}    _M      : 0 
	*/
	fn_get_date = function(_sGubun, _iN, _iM) {
	
		var date = new Date();
		var sStart = new Date(Date.parse(date) - _iN * 1000 * 60 * 60 * 24);
		var sToday = new Date(Date.parse(date) - _iM * 1000 * 60 * 60 * 24);
		
		var sYear  = sStart.getFullYear();
		var sMonth = sStart.getMonth() + 1;
		var sDay   = sStart.getDate();
		
		if (parseInt(sMonth) <= 9) {		
			sMonth = "0" + sMonth;
		}
		if (parseInt(sDay) <= 9) {		
			sDay = "0" + sDay;
		}
		
		var sYYYY = sToday.getFullYear();
	    var sMM   = sToday.getMonth()+1;
		var sNN   = sToday.getDate();
		
		if (parseInt(sMM) <= 9) {		
			sMM = "0" + sMM;
		}
		if (parseInt(sNN) <= 9) {		
			sNN = "0" + sNN;
		}
		
		if (_sGubun == "pDay") {
			return sYear + "-" + sMonth + "-" + sDay;
		} else {
			return sYYYY + "-" + sMM + "-" + sNN;
		}		
	}	
	
   /**
	* @method
	* @description disabled 처리
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*        [string} _sInputId : input id
	*        {boolean} _bCheck  true/false  여부
	*/
    fn_form_disabled = function(_sFormId, _sInputId, _bCheck) {

		for (var i=0; i<_sInputId.length; i++) {
			$("#"+_sFormId).find("#"+_sInputId[i]).attr("disabled", _bCheck);
			if(_bCheck) {
				$("#"+_sFormId).find("#"+_sInputId[i]).val("");
			}
		}
	}
	 
   /**
	* @method
	* @description 버튼 disabled 처리
	* @author 홍길동(2016.10.04)
	* @param {string} _sButtonId : button ID 
	*        {boolean} _bCheck : true/false  여부
	*        {string} _sButtType : 버튼 종류 (C : 조회/엑셀 등, G : 등록, 삭제 등)
	*/
	fn_button_disabled = function(_sButtonId, _bCheck, _sButtType) {
		
		for (var i=0; i<_sButtonId.length; i++) {
			$("#"+_sButtonId[i]).attr("disabled", _bCheck);

			if (_sButtType[i] == "C") {
				if (_bCheck) {					
					$("#"+_sButtonId[i]).removeClass("btn_srh");
					$("#"+_sButtonId[i]).removeClass("btn_srh:hover");					
				} else {
					$("#"+_sButtonId[i]).addClass("btn_srh");
					$("#"+_sButtonId[i]).addClass("btn_srh:hover");
				}
			} else {
				if (_bCheck) {
					$("#"+_sButtonId[i]).css("color", "#BEBEBE");
				} else {
					$("#"+_sButtonId[i]).css("color", "#000000");
				}
			}
		}
	}
	
   /**
	* @method
	* @description 그리드 체크박스 선택 갯수에 따른 버튼 활성화/비활성화
	* @author 홍길동(2016.10.04)
	* @param {string}  _sGridId : 선택된 grid ID
	*        {string] _sButtonId : button ID
	*        {string} _sButtType : 버튼 종류 (C : 조회/엑셀 등, G : 등록, 삭제 등)
	*/
	fn_gridSelectButton = function(_sGridId, _sButtonId, _sButtType) {
		
		var selRowIds = $("#"+_sGridId).getGridParam("selarrrow");	
		
		if (selRowIds == "") {
			COMMON.fn_button_disabled(_sButtonId, true, _sButtType);
		} else {
			COMMON.fn_button_disabled(_sButtonId, false, _sButtType);
		}
	}
	
	/**
	* @method
	* @description 폼의 아이디를 임의로 생성
	* @author 홍길동(2016.10.04)
	* @param {number} _n  아이디의 자리수 생성
	*/
	fn_get_randomId = function(_nSize){

		 if(!_nSize)    _nSize = 5;

		 var sRandomId = '';
		 var charGroup = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

		 for(var i=0; i < _nSize; i++)
		 {
			 sRandomId += charGroup.charAt(Math.floor(Math.random() * charGroup.length));
		 }

		 return sRandomId;
	}
	
   /**
	* @method
	* @description iframe 을 쓸수 있는 객체 가져오기
	* @author 홍길동(2016.10.04)
	* @param {String} _sIframe  IFrame Id
	*/
	fn_get_iframeBody = function(_sIframe) {
		
			var doc = null;
			// IE8 cascading access check
			try {
				if (_sIframe.contentWindow) {
					doc = _sIframe.contentWindow.document;
				}
			} catch(err) {}

			if (doc) { // successful getting content
			}
			else {
				try {
					doc = _sIframe.contentDocument ? _sIframe.contentDocument : _sIframe.document;
				} catch(err) {
					doc = _sIframe.document;
				}
			}

			var $doc = doc;
			var $body = $($doc.body) ? $($doc.body) : $($doc.documentElement)
			$body.html('');

			return $body;
	}
	
   /**
	* @method
	* @description SUBMIT 처리 공통함수  : Dialog 창에서 Submit 하는 경우와 삭제를 하는 경우 사용
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId 데이터가지고 있는 폼아이디
	* @param {String} _sTargetIframe 데이터가지고 있는 폼아이디
	* @param {String} _sAction 처리한 action
	* @param {String} _sCallback 처리후 돌아올 함수명
	*/
	fn_get_form = function (_sFormId, _sTargetIframe, _sAction, _sCallback) {
		
		if (_sCallback !== undefined) {
			$('#'+_sFormId).find("#callBackFunction").val(_sCallback);	// 처리후 복귀 함수
		}
		
		var uniq_id = fn_get_randomId(10);
		var $iframe = fn_get_iframeBody($("#"+_sTargetIframe)[0]);
		var $form = $("<form/>").attr( "action", _sAction ).attr( "id", uniq_id ).attr("method", "post");

		try {
			var input_type = "";
			var input_val = "";
			var $ctrl = "";

			$iframe.append( $form );

			$('#'+_sFormId).find("input[type='hidden'], input[type='text'],input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select,textarea, .SPINNER").each( function(){
				
				// disabled 된것은 배제
				if( $(this).is(':enabled') ) {
					
					input_type = $(this).attr("type");
					
					try {	// 마스크 등 제거
						input_val = $(this).cleanVal();
					} catch(E) {
						input_val = $(this).val();
					}
					
					var cs = $('#'+_sFormId).find($(this)).getClasses();
					
					if ( cs.indexOf("datepicker") != -1 ) {
						$ctrl = $('<input/>').attr({ type: 'text', name:$(this).attr("id").replace("_datepicker", ""), value: input_val });
					} else {
						$ctrl = $('<input/>').attr({ type: 'text', name:$(this).attr("id"), value: input_val });
					}

					$iframe.find("#"+uniq_id).append($ctrl);
				}
			});
		} catch(E) {}
		return $iframe.find("#"+uniq_id);		
	}

	
   /**
	* @method
	* @description 컨텐츠 상세보기
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	* @param {String} _sDetailFormId  데이터가지고 있는 상세 폼아이디
	* @param {String} _sUbseq 자료순번
	*/
	fn_view_content_scrin = function (_sFormId, _sDetailFormId, _sUbseq, _sOtherYn, _sActionFlag, _sScrinCode, _sTbl, _sXml, _sWidth, _sTitle) { 
		sDtlWidth=_sWidth;
		if(sDtlWidth<="0") 		{
			sDtlWidth = "900"; // 상세창 기본크기
		} 
		
		//alert("fn_view_content_scrin> sDtlWidth="+sDtlWidth+ "_sWidth="+_sWidth);
		
		//alert("fn_view_content_scrin> _sUbseq="+_sUbseq);
		
		var left = $("#id_detail_width").css("left");
		//alert ("fn_view_content_scrin> left="+left);
		
		$("#id_detail_width").css("width", sDtlWidth+"px");
		//$("#id_detail_width").css("left", "10%");
		//$("#id_detail_width").css("right", "10%"); 
		 
		//상세화면 동작 세팅  
		$("#"+_sDetailFormId).find("#id_tabNm").text(_sTitle);       // 탭메뉴명
		$("#"+_sDetailFormId).find("#tableName").val(_sTbl);       // 테이블명
		$("#"+_sDetailFormId).find("#scrin_code").val(_sScrinCode);  // 화면id
		
		var odno = $("#"+_sDetailFormId).find("#od_no").val(); // odno 
	//	alert("fn_view_content_scrin> odno="+odno);
		
		 var ubseq =  _sUbseq;
		 if(_sOtherYn=="Y") ubseq=""; //같은 화면 ubseq가 아니기 때문에 필요없음 pjh
 
	//	alert("fn_view_content2> _sTbl="+_sTbl+" _sXml="+_sXml+" _sWidth="+_sWidth+" _sScrinCode="+_sScrinCode+" _sUbseq="+_sUbseq);
	//	var scrin_code = $("#"+_sFormId).find("#scrin_code").val();
	//	var idArr     = COMMON.fn_get_form_id(_sDetailFormId);
		//pjh
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectContentDetail.do',
			data: 'scrin_code=' + _sScrinCode + '&tableName=' + _sTbl + '&ubseq=' + ubseq + '&odno=' + odno +  '&xmlId=' + _sXml + '&menu_code=' + $("#topForm").find("#menu_code").val(),
			datatype : "json",
			success : function(data) { 
				// 처리상태
				$("#"+_sDetailFormId).find("#actionFlag").val(_sActionFlag);
				
				// 1. 하위 객체 생성 (먼저 실행 해야 함)
				COMMON.fn_get_detailFormObj(_sScrinCode, _sTbl, _sUbseq, data, _sDetailFormId, "sub")
				
				// 2. 폼 객체 생성
				COMMON.fn_get_detailFormObj(_sScrinCode, _sTbl, _sUbseq, data, _sDetailFormId, "main");
				
			},
			error: function(xhr, status, error) {

				$("#"+_sDetailFormId).find("#actionFlag").val("");  // 처리상태		
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
		COMMON.fn_scrin_block_ui("UB");
		$("#id_loader").hide();
	}
	
	

	
	   /**
		* @method
		* @description 컨텐츠 상세보기
		* @author 홍길동(2016.10.04)
		* @param {String} _sFormId  데이터가지고 있는 폼아이디
		* @param {String} _sDetailFormId  데이터가지고 있는 상세 폼아이디
		* @param {String} _sUbseq 자료순번
		*/
		fn_view_content = function (_sFormId, _sDetailFormId, _sUbseq, _sActionFlag, _sScrinCode, _sdExtFun) {  
//			$("body").css({overflow:'hidden'});
//			$("body").css({overflow:'auto'});
			//첨부파일 초기화
			$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
				$(this).remove();
			});

			COMMON.fn_dtl_regist_type('I');  // 일괄등록 layer hide
			
			$("#"+sDetailFormId).find("#ubseq").remove();

			var tabName = $("#"+_sFormId).find("#fileName").val();   // 메뉴명
			var tblName = $("#"+_sFormId).find("#tableName").val();  // 테이블명
			var xmlId   = $("#"+_sFormId).find("#xmlId").val();      // xmlId
			//alert("fn_view_content1> tabName="+tabName+ "_sScrinCode="+_sScrinCode);
			
			if (xmlId == undefined || _sScrinCode=="S0031"){
					//console.log("AA::");
					xmlId = "";
				}
			var subTitle, scrin_id;
			if(tabName != undefined) {
				subTitle=tabName;
				//scrin_id = $("#"+_sDetailFormId).find("#scrin_code").val();
			}
			else if (_sScrinCode == undefined) {
				if (_sFormId == "frm_tab") {
					subTitle = $("#"+_sFormId).find("#tabName").val();
				}
				//scrin_id = $("#"+_sDetailFormId).find("#scrin_code").val();
			}
			else {  // 탭메뉴
				subTitle = $("#"+_sFormId).find("#tabName").val();
				tblName = $("#"+_sFormId).find("#tableName").val();		
			} 
			//alert("fn_view_content1> subTitle="+subTitle);
			
			$("#"+_sDetailFormId).find("#id_tabNm").text(subTitle);       // 탭메뉴명
			$("#"+_sDetailFormId).find("#tableName").val(tblName);       // 테이블명
			$("#"+_sDetailFormId).find("#scrin_code").val(_sScrinCode);  // 화면id		
			
			//alert("fn_view_content> tblName="+tblName+" subTitle="+subTitle+" scrin_id="+scrin_id+" _sScrinCode="+_sScrinCode+" _sFormId="+_sFormId);
		//	alert("fn_view_content1> tabName="+tabName+" tblName="+tblName+" xmlId="+xmlId+" _sScrinCode="+_sScrinCode+" scrin_code="+scrin_code);
			//alert("fn_view_content1> subTitle="+subTitle);
			
			// 상세화면 show
			var sDtlWidth = "";		
			var dtlWidth  = $("#"+_sDetailFormId).find("#dtlWidth").val().split("^|^");
			for (var i=0; i<dtlWidth.length; i++) {
				var dtlW = dtlWidth[i].split(",");
				if (dtlW[0] == _sScrinCode) {
					sDtlWidth = dtlW[1]; 
				}
			}
			if (sDtlWidth == "" || sDtlWidth == "0" || sDtlWidth == "null") sDtlWidth = "1200"; // 상세창 기본크기
			
			$("#id_detail_width").css({"width":""+sDtlWidth+"px"});

			var odno = $("#"+_sDetailFormId).find("#od_no").val(); // odno 
			//alert("fn_view_content2> odno="+odno);
			
			var scrin_code = $("#"+_sFormId).find("#scrin_code").val();
			var idArr     = COMMON.fn_get_form_id(_sDetailFormId);
			
		//	alert("fn_view_content2> tabName="+tabName+" tblName="+tblName+" xmlId="+xmlId+" _sScrinCode="+_sScrinCode+" scrin_code="+scrin_code);
			 
			
			
			$.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/content/selectContentDetail.do',
				data: 'scrin_code=' + scrin_code + '&tableName=' + tblName + '&ubseq=' + _sUbseq + '&odno=' + odno + '&xmlId=' + xmlId + '&menu_code=' + $("#topForm").find("#menu_code").val(),
				datatype : "json",
				success : function(data) {
					// 처리상태
					$("#"+_sDetailFormId).find("#actionFlag").val(_sActionFlag);
					
					// 1. 하위 객체 생성 (먼저 실행 해야 함)
					COMMON.fn_get_detailFormObj(scrin_code, tblName, _sUbseq, data, _sDetailFormId, "sub")
					
					// 2. 폼 객체 생성
					COMMON.fn_get_detailFormObj(scrin_code, tblName, _sUbseq, data, _sDetailFormId, "main");
					
					// 3. 첨부 파일이 있으면 생성
					if(tblName == "mb_cfgsendmail"){
						var filesn = COMMON.fn_get_detailfilesn(tblName);
						if(filesn != null && filesn != ""){
							var filesnsplit = filesn.split(',');
							var filenm = COMMON.fn_get_detailfilenm(tblName);
							
							for(var i in filesnsplit){
								var str = '<p><input type="text" id="'+filesnsplit[i]+'" name="'+filesnsplit[i]+'" value="'+filenm[i]+'" style="width: 250px;" readonly><button type="button" id="download'+i+'" name="download'+i+'" class="btn_down" onclick="COMMON.fn_get_detailfileblob(\''+tblName+'\', \''+i+'\')"><button type="button" id="btn_remove'+i+'" name="btn_remove'+i+'" onclick="COMMON.fn_cfileRemove($(this), this.value)" class="btn_fminus" value="'+filesnsplit[i]+'" title="삭제"></p>';
								$("#frm_detail").find("#fileDiv").append(str);
							}
						}
					}
					CUSTOM.fn_detail_init(scrin_code);
					// 상세 화면  open시 실행함수
					if (typeof _sdExtFun != "undefined" && _sdExtFun != "") {
						_sdExtFun = COMMON.fn_replaceAll(_sdExtFun, "&#039;", "'");
						var callback_function = new Function(_sdExtFun);	
			        	callback_function();
			    		
			        }
					
					if($("#frm_detail").find("#scrin_code").val() == "S0196" || $("#frm_detail").find("#scrin_code").val() == "S0027"){
						$("#frm_detail").find("#btn_save").hide();
						$("#frm_detail").find("#btn_insert").show();
						$("#frm_detail").find("#btn_update").show();
					}
					
				},
				error: function(xhr, status, error) {

					$("#"+_sDetailFormId).find("#actionFlag").val("");  // 처리상태		
					$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				}

			});
			COMMON.fn_scrin_block_ui("UB");
			$("#id_loader").hide();
		}
		
		
	
	
   /**
	* @method from id 조회
	* @author 홍길동(2016.10.04)
	* @param {String} _sDetailFormId  데이터가지고 있는 상세 폼아이디
	*/
	fn_get_form_id = function (_sDetailFormId) {
		var idArr = Array();
		var nm    = "";
		var sExcept = ["menu_code","tableName","callBackFunction","actionFlag","bmark","html"];
		
		$('#'+_sDetailFormId).find("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea,.SPINNER").each( function(){
			
			nm = $('#'+_sDetailFormId).find($(this)).attr('id');
			
			if (!COMMON.fn_chekExcept(sExcept, nm)) {
			    idArr.push(nm);
			}
		});
		
		return idArr;
	}
	
   /**
	* @method 제외항목에 비교대상 항목이 존재 하는지 확인
	* @author 홍길동(2016.10.04)
	* @param {array} _sExcept  제외항목
	* @param {String} _sKeyNm  비교대상 항목
	*/
	fn_chekExcept = function (_sExcept, _sKeyNm) {
		
		for (var i=0; i<_sExcept.length; i++) {			
			if (_sExcept[i] == _sKeyNm) {
				return true;
			}
		}		
		return false;
	}
	
   /**
	* @method 지우개 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {array} _sExcept  제외항목
	* @param {String} _sKeyNm  비교대상 항목
	*/
	fn_eraser = function (_sFormId, _sObjid, _sPobjid) {
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectEraserTargetList.do',
			data: 'objid=' + _sObjid + '&pobjid=' + _sPobjid,
			datatype : "json",
			success : function(data) {				
				for (var j=0; j<data.rows.length; j++) {
					
					var val = data.rows[j]["colid"];
					$("#"+_sFormId).find("#"+val).val(""); 						
				}				
			},
			error: function(xhr, status, error) {
				//alert(xhr + "\n" + status + "\n" + error);
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				
			}

		});				
	}
	
   /**
	* @method 체크박스 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	* @param {String} _sId
	*/
	fn_isCheckedById = function (_sFormId, _sId) {			
		  var checked = $("#"+_sFormId).find(":checkbox[name='"+_sId+"']:checked").length;
		  if (checked == 0) {			  
			  $("#"+_sFormId).find("#mult_check_"+_sId).val("");
		  } else {
			  $("#"+_sFormId).find("#mult_check_"+_sId).val(checked);
		  }	
		  
		  //그리드 보여주고 숨기기
		  if(_sId == "search_col_type"){
			  $("#gridMain").jqGrid("showCol", ["acdrcnt","bcdrcnt","ccdrcnt","dcdrcnt","ecdrcnt","fcdrcnt","gcdrcnt","adur","bdur","cdur","ddur","edur","fdur","gdur","adosu","bdosu","cdosu","ddosu","edosu","fdosu","gdosu","acost","bcost","ccost","dcost","ecost","fcost","gcost"]);
			  $(".srh_wrap").find("#"+_sId + ":checkbox" ).each(function(){
					if($(this).is(":checked") == false){
						if($(this).val() == "A"){
							$("#gridMain").jqGrid("hideCol", "acdrcnt");
							$("#gridMain").jqGrid("hideCol", "bcdrcnt");
							$("#gridMain").jqGrid("hideCol", "ccdrcnt");
							$("#gridMain").jqGrid("hideCol", "dcdrcnt");
							$("#gridMain").jqGrid("hideCol", "ecdrcnt");
							$("#gridMain").jqGrid("hideCol", "fcdrcnt");
							$("#gridMain").jqGrid("hideCol", "gcdrcnt");
						}else if($(this).val() == "B"){
							$("#gridMain").jqGrid("hideCol", "adur");
							$("#gridMain").jqGrid("hideCol", "bdur");
							$("#gridMain").jqGrid("hideCol", "cdur");
							$("#gridMain").jqGrid("hideCol", "ddur");
							$("#gridMain").jqGrid("hideCol", "edur");
							$("#gridMain").jqGrid("hideCol", "fdur");
							$("#gridMain").jqGrid("hideCol", "gdur");
						}else if($(this).val() == "C"){
							$("#gridMain").jqGrid("hideCol", "adosu");
							$("#gridMain").jqGrid("hideCol", "bdosu");
							$("#gridMain").jqGrid("hideCol", "cdosu");
							$("#gridMain").jqGrid("hideCol", "ddosu");
							$("#gridMain").jqGrid("hideCol", "edosu");
							$("#gridMain").jqGrid("hideCol", "fdosu");
							$("#gridMain").jqGrid("hideCol", "gdosu");
						}else if($(this).val() == "D"){
							$("#gridMain").jqGrid("hideCol", "acost");
							$("#gridMain").jqGrid("hideCol", "bcost");
							$("#gridMain").jqGrid("hideCol", "ccost");
							$("#gridMain").jqGrid("hideCol", "dcost");
							$("#gridMain").jqGrid("hideCol", "ecost");
							$("#gridMain").jqGrid("hideCol", "fcost");
							$("#gridMain").jqGrid("hideCol", "gcost");
						}
					}
					
					
			  });
		  }
		  
		  
		  
		  
	}
	
   /**
	* @method 메뉴 클릭시
	* @author 홍길동(2016.10.04)
	* @param {String} _sMenuId 메뉴id
	* @param {String} _sUrl     해당 메뉴 url
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	*/
	fn_menu_click = function (_sMenuId, _sScrinYn, _sRoleYn, _sUrl, _sFormId) {
		//return;
		$("#"+_sFormId).find("#menu_code").val(_sMenuId);
		if (_sScrinYn == "N") {
			$.unibillDialog.alert('알림', 'URL 정보가 없습니다.');
			return;
		}
		if (_sRoleYn == "N") {
			$.unibillDialog.alert('알림', '권한이 없습니다.');
			return;
		}
		// URL이 없으면 공통 컨텐츠 화면
		if (_sUrl == "undefined" || _sUrl == "") {
			_sUrl = "/content/selectContentList.do";			
		}				
		
		COMMON.fn_form_submit(_sFormId, _sUrl);
//		COMMON.fn_form_load(_sFormId, _sUrl);
	}
	
	/**
	* @method submit 처리
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	* @param {String} _sUrl     해당 메뉴 url
	*/
	fn_form_submit = function (_sFormId, _sUrl) {
		$("#"+_sFormId).attr("action", CONTEXT_ROOT + _sUrl).attr("method", "post").submit();
	}
	
	fn_form_load = function (_sFormId, _sUrl) {
		$("#mainForm").load(CONTEXT_ROOT + "/content/selectContentAjaxList.do", "tilesTarget=content", 

		
				function(responseTxt, statusTxt, xhr){

	        if(statusTxt == "success")

	            alert("External content loaded successfully! : ");

	        if(statusTxt == "error")

	        	alert("Error: " + xhr.status + ": " + xhr.statusText);


	    }

		
		
		
		
		
		)
		//$("#"+_sFormId).attr("action", CONTEXT_ROOT + _sUrl).attr("method", "post").submit();
	}
	
	ttt = function () {
		alert("1111");
	}
	
   /**
	* @method 즐겨찾기 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	*/
	fn_bookmarkMake = function (_sFormId, _lFlag) {
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectBookMarkList.do',
			data: 'lFlag=' + _lFlag,
			datatype : "json",
			success : function(data) {
				var txt = "";
				for (var j=0; j<data.rows.length; j++) {					
					var menuId  = data.rows[j]["menuId"];
					var menuNm  = data.rows[j]["menuNm"];
					var url     = data.rows[j]["url"];
					var scrinYn = data.rows[j]["scrinYn"]; 
					var roleYn  = data.rows[j]["roleYn"];

					txt += '<li><a href="#" onclick="COMMON.fn_menu_click(\''+menuId+'\',\''+scrinYn+'\',\''+roleYn+'\',\''+url+'\',\'topForm\');">'+menuNm+'</a><button type="button" onclick="COMMON.fn_bookmark_save(\''+_sFormId+'\', \'D\', \''+menuId+'\');" class="del" title="즐겨찾기삭제"></button></li>';

				}

				$("#leftBookMark").html(txt);
				$("#rightBookMark").html(txt);
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
	}
	
   /**
	* @method 즐겨찾기 추가 및 삭제
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	* @param {array} _sFlag    구분
	* @param {array} _sMenuId  메뉴id
	*/
	fn_bookmark_save = function (_sFormId, _sFlag, _sMenuId) {
		
		var sMenuCode = $("#"+_sFormId).find("#menu_code").val();
		var sMenuName = $("#"+_sFormId).find("#menu_name").val();
		
		if (_sFlag == "") {
			_sFlag = $("#"+_sFormId).find("#bmark").val();
		}
		
		var sMsg = "즐겨찾기가 추가 되었습니다.";		
		if (_sFlag == "D") {
			sMsg = "즐겨찾기가 삭제 되었습니다.";		
		}
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/saveBookMark.do',
			data: 'flag=' + _sFlag + '&menu_code=' + _sMenuId,
			datatype : "json",
			success : function(data) {
				if (data.result == "SUCCESS") {
					$.unibillDialog.alert('확인', sMsg);
					COMMON.fn_bookmarkMake(_sFormId, "J");

					if (_sMenuId == sMenuCode) {
						if (_sFlag == "I") {
//							$("#"+_sFormId).find("#btn_bmark").addClass("btn_bmark on");  // 즐겨찾기 추가 class
							COMMON.fn_set_menu(_sMenuId, sMenuName, "D");
							$("#"+_sFormId).find("#bmark").val("D");
						} else {
//							$("#"+_sFormId).find("#btn_bmark").removeClass("btn_bmark on").addClass("btn_bmark");
							COMMON.fn_set_menu(_sMenuId, sMenuName, "I");
							$("#"+_sFormId).find("#bmark").val("I");							
						}
					}
			    } else {
			    	$.unibillDialog.alert('경고', '즐겨찾기(추가/삭제)을 실패하였습니다.');
			    } 
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
	}
	
	/**
	* @method 하단 그리드 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId  데이터가지고 있는 폼아이디
	* @param {array} _sFlag    구분
	* @param {array} _sMenuId  메뉴id
	*/
	fn_tabMenu2 = function(_sGridId, _sFormId, _sTabFormId, _sScrinCode, _sUbseq) {
		
		var sRowId   = $("#"+_sGridId).getGridParam("selrow");
//		var sRowData = $("#"+_sGridId).jqGrid("getRowData",sRowId);
//		var od_no   = sRowData["od_no"];	
//		var sGdId = gdId.substring(gdId.indexOf("(")+1,gdId.indexOf(")"));

		if (_sUbseq == undefined) {
			_sUbseq = sRowId;
		}

//		alert("_sGridId : " + _sGridId + "\n_sFormId : " + _sFormId + "\n_sTabFormId : " + _sTabFormId + "\n_sScrinCode : " + _sScrinCode + "\n_sUbseq : " + _sUbseq);
		
		$("#"+_sTabFormId).find("#scrin_code").val(_sScrinCode);  // 화면id
//		$("#"+_sTabFormId).find("#od_no").val(od_no);  // 상품id
		
		if (_sUbseq == null) {
			$.unibillDialog.alert('알림', '상단 그리드를 선택해 주십시오.');
			return;
		}
		
		var sMenuId = $("#topForm").find("#menu_code").val();  // 메뉴id	
		$("#"+_sTabFormId).find("#ubseq").val(_sUbseq);           // ubseq
		var masterTableName = $("#"+_sTabFormId).find("#masterTableName").val();
		var tableName = $("#"+_sTabFormId).find("#tableName").val();
		
		var sRowId   = $("#gridMain").getGridParam("selrow");
		var sRowData = $("#gridMain").jqGrid("getRowData",sRowId);
		var gd_id   = sRowData["cont_gd_id"];
		var od_no   = sRowData["od_no"];	
		var cont_seq   = sRowData["cont_seq"];
		var cust_id   = sRowData["cust_id"];
		var rep_cust_id   = sRowData["rep_cust_id"];
		var bill_ym   = sRowData["bill_ym"];
		
		//alert("fn_tabMenu> gd_id="+gd_id);
		if (typeof gd_id == "undefined") { 
			gd_id=""; 
			//alert("fn_tabMenu> gd_id="+gd_id);
		} 
		
		//alert("fn_tabMenu> subg_gd_id="+subg_gd_id);
		
		//alert("fn_tabMenu> 제대로 안됨 2번째 성공 tableName="+tableName);
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectTabObjList.do',
			data: 'scrin_code=' + _sScrinCode + '&menu_code=' + sMenuId + '&masterTableName=' + masterTableName+ '&tableName=' + tableName + '&ubseq=' + _sUbseq+ '&gd_id=' + gd_id+ '&od_no=' + od_no,
			datatype : "json",
			success : function(data) {
				
				var colNameList  = "";
				var colModelList = "";
				var colWidth     = "";
				var colAlign     = "";
				var gridHeight   = ""; 
				var tableName    = "";
				var tabName      = "";
				var dblclickYn   = "";
				var dblclickFun  = "";
				var clickFun     = "";
				var subGridSelYn = "";
				
				for (var i=0; i<data.rows.length; i++) {
					var sGubun = "";
					if (i < data.rows.length-1) sGubun = ",";
					var colModel = data.rows[i]["colId"];
					var colName  = data.rows[i]["objNm"];
					var colwidth = data.rows[i]["cellW"];
					var colalign = data.rows[i]["cellSort"];
					var gridhg   = data.rows[i]["gridhg"];
					var tblNm    = data.rows[i]["tblNm"];
					var tabNm    = data.rows[i]["tabNm"];						
												
					colNameList  += colName  + sGubun;
		    		colModelList += colModel + sGubun;
		    		colWidth     += colwidth + sGubun;
		    		colAlign     += colalign + sGubun;
		    		gridHeight    = gridhg;
		    		tableName     = tblNm;
					tabName       = tabNm;
					dblclickYn    = data.rows[i]["dblclickYn"];
					dblclickFun   = data.rows[i]["dblclickFun"];
					clickFun      = data.rows[i]["clickFun"];
					subGridSelYn  = data.rows[i]["subGridSelYn"];
				}

		//	alert("fn_tabMenu> colNameList : " + colNameList + "\ncolModelList : " + colModelList + "\ncolWidth : " + colWidth + "\ncolAlign : " + colAlign + "\ngridHeight : " + gridHeight);				
				
				$("#"+_sTabFormId).find("#tableName").val(tableName);   // 테이블명
				$("#"+_sTabFormId).find("#tabName").val(tabName);       // 탭명
				
				var subGridId ="";
				var sGridSubPager ="";
				if(_sScrinCode=="S0184"||_sScrinCode=="S0185"){
					subGridId = "gridSub_S0143";
					sGridSubPager = "gridSubPager_S0143";
					gridHeight = "0";
				}else if(_sScrinCode=="S0187"||_sScrinCode=="S0188"){
					subGridId = "gridSub_S0186";
					sGridSubPager = "gridSubPager_S0186";
					gridHeight = "0";
				}else{
					subGridId = "gridSub_"+_sScrinCode;
					sGridSubPager = "gridSubPager_"+_sScrinCode;
				}
				
				//그리드 초기화
				COMMON.fn_gridRemove(subGridId);
				//alert("77777 : " + subGridId + " _sTabFormId="+_sTabFormId + " _sScrinCode="+_sScrinCode);
				COMMON.fn_subGridInfo(_sTabFormId, subGridId, sGridSubPager, colNameList, colModelList, colWidth, colAlign, gridHeight, _sScrinCode, dblclickYn, dblclickFun, clickFun, subGridSelYn);				 
				fn_scrin_block_ui("UB"); 
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});

	}
	
	
	
	fn_tabMenu = function(_sGridId, _sFormId, _sTabFormId, _sScrinCode, _sUbseq) {
	      
	      var sRowId   = $("#"+_sGridId).getGridParam("selrow");
//	      var sRowData = $("#"+_sGridId).jqGrid("getRowData",sRowId);
//	      var od_no   = sRowData["od_no"];   
//	      var sGdId = gdId.substring(gdId.indexOf("(")+1,gdId.indexOf(")"));

	      if (_sUbseq == undefined) {
	         _sUbseq = sRowId;
	      }

//	      alert("_sGridId : " + _sGridId + "\n_sFormId : " + _sFormId + "\n_sTabFormId : " + _sTabFormId + "\n_sScrinCode : " + _sScrinCode + "\n_sUbseq : " + _sUbseq);
	      
	      $("#"+_sTabFormId).find("#scrin_code").val(_sScrinCode);  // 화면id
//	      $("#"+_sTabFormId).find("#od_no").val(od_no);  // 상품id
	      
	      if (_sUbseq == null) {
	         $.unibillDialog.alert('알림', '상단 그리드를 선택해 주십시오.');
	         return;
	      }
	      
	      var sMenuId = $("#topForm").find("#menu_code").val();  // 메뉴id   
	      $("#"+_sTabFormId).find("#ubseq").val(_sUbseq);           // ubseq
	      var masterTableName = $("#"+_sTabFormId).find("#masterTableName").val();
	      var tableName = $("#"+_sTabFormId).find("#tableName").val();
	      
	      var formData = new FormData();
	      formData = COMMON.fn_getSelectMainGridDataAjax();
	      
	      formData.append("scrin_code" , _sScrinCode);
	      formData.append("menu_code" , sMenuId);
	      formData.append("masterTableName" , masterTableName);
	      formData.append("ubseq" , _sUbseq);
	      
	      $.ajax({
	         type: 'POST',
	         url: CONTEXT_ROOT + '/content/selectTabObjList.do',
	         //data: 'scrin_code=' + _sScrinCode + '&menu_code=' + sMenuId + '&masterTableName=' + masterTableName + '&ubseq=' + _sUbseq,
	         processData: false,
	         contentType: false,
	         data: formData,
	         datatype : "json",
	         success : function(data) {
	            
	            var colNameList  = "";
	            var colModelList = "";
	            var colWidth     = "";
	            var colAlign     = "";
	            var gridHeight   = ""; 
	            var tableName    = "";
	            var tabName      = "";
	            var dblclickYn   = "";
	            var dblclickFun  = "";
	            var clickFun     = "";
	            var subGridSelYn = "";
	            
	            for (var i=0; i<data.rows.length; i++) {
	               var sGubun = "";
	               if (i < data.rows.length-1) sGubun = ",";
	               var colModel = data.rows[i]["colId"];
	               var colName  = data.rows[i]["objNm"];
	               var colwidth = data.rows[i]["cellW"];
	               var colalign = data.rows[i]["cellSort"];
	               var gridhg   = data.rows[i]["gridhg"];
	               var tblNm    = data.rows[i]["tblNm"];
	               var tabNm    = data.rows[i]["tabNm"];                  
	                                    
	               colNameList  += colName  + sGubun;
	                colModelList += colModel + sGubun;
	                colWidth     += colwidth + sGubun;
	                colAlign     += colalign + sGubun;
	                gridHeight    = gridhg;
	                tableName     = tblNm;
	               tabName       = tabNm;
	               dblclickYn    = data.rows[i]["dblclickYn"];
	               dblclickFun   = data.rows[i]["dblclickFun"];
	               clickFun      = data.rows[i]["clickFun"];
	               subGridSelYn  = data.rows[i]["subGridSelYn"];
	            }

	      //   alert("fn_tabMenu> colNameList : " + colNameList + "\ncolModelList : " + colModelList + "\ncolWidth : " + colWidth + "\ncolAlign : " + colAlign + "\ngridHeight : " + gridHeight);            
	            
	            $("#"+_sTabFormId).find("#tableName").val(tableName);   // 테이블명
	            $("#"+_sTabFormId).find("#tabName").val(tabName);       // 탭명
	            
	            var subGridId ="";
	            var sGridSubPager ="";
	            if(_sScrinCode=="S0184"||_sScrinCode=="S0185"){
	               subGridId = "gridSub_S0143";
	               sGridSubPager = "gridSubPager_S0143";
	               gridHeight = "0";
	            }else if(_sScrinCode=="S0187"||_sScrinCode=="S0188"){
	               subGridId = "gridSub_S0186";
	               sGridSubPager = "gridSubPager_S0186";
	               gridHeight = "0";
	            }else{
	               subGridId = "gridSub_"+_sScrinCode;
	               sGridSubPager = "gridSubPager_"+_sScrinCode;
	            }
	            
	            //그리드 초기화
	            COMMON.fn_gridRemove(subGridId);
	            //alert("77777 : " + subGridId + " _sTabFormId="+_sTabFormId + " _sScrinCode="+_sScrinCode);
	            COMMON.fn_subGridInfo(_sTabFormId, subGridId, sGridSubPager, colNameList, colModelList, colWidth, colAlign, gridHeight, _sScrinCode, dblclickYn, dblclickFun, clickFun, subGridSelYn);             
	            fn_scrin_block_ui("UB"); 
	         },
	         error: function(xhr, status, error) {
	            $.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
	         }

	      });

	   }
	
   /**
	* @method
	* @description 공통컨텐츠 하단 그리드 정보
	* @author 홍길동(2016.10.04)
	* @param {String} _sTabFormId 데이터가지고 있는 폼아이디
	* @param {String} _sGridId 그리드 id
	* @param {String} _sGridSubPager 그리드 페이지 id
	* @param {String} _sColNames 그리드 col name
	* @param {String} _sColModels 그리드 model name
	* @param {String} _sGridHg 그리드 높이
	*/
	fn_subGridInfo2 = function (_sTabFormId, _sGridId, _sGridSubPager, _sColNameList, _sColModelList, _sColWidth, _sColAlign, _sGridHg, _sScrinCode, _sDblclickYn, _sDblclickFun, _sClickFun, _sMultChk) {
	
		//alert("fn_subGridInfo> _sTabFormId : " + _sTabFormId + "\n_sGridId : " + _sGridId + "\n_sGridSubPager : " + _sGridSubPager + "\n_sColNameList : " + _sColNameList + "\n_sColModelList : " + _sColModelList + "\n_sColWidth : " + _sColWidth + "\n_sColAlign : " + _sColAlign + "\n_sGridHg : " + _sGridHg + "\n_sScrinCode : " + _sScrinCode);

		var sColNames  = [];
	    var sColModels = [];
	    var sColWidth  = [];
	    var sColAlign  = [];
	    
		var sColNameArr  = _sColNameList.split(",");
	    var sColModelArr = _sColModelList.split(",");
	    var sColWidthArr = _sColWidth.split(",");
	    var sColAlignArr = _sColAlign.split(",");

	    sColNames.push("순번");
	    sColModels.push({name:'ubseq', index:'ubseq', jsonmap:'ubseq', align:'center', width:'100px', key:true, hidden:true});
	    //alert("fn_subGridInfo> 111111111");
	    for (var i=0; i<sColNameArr.length; i++) {
	    	
	    	var sStrList, sJsonMap; 
	    	var sWidth = sColWidthArr[i];
	    	
	    	if (sColWidthArr[i] == 0) {
	    		sWidth = 120;
	    	}
	    	/* 홍성원 서브그리드 egovmap _ 컬럼 대문자 변환 메인그리드와 통일해서 공통 처리 */
	    	sStrList = sColModelArr[i].split('_');
	    	if (sStrList.length == 2 ) {
		    	sJsonMap = sStrList[0].toLowerCase() + sStrList[1].substring(0,1).toUpperCase() + sStrList[1].substring(1).toLowerCase();
	    	} else if(sStrList.length == 3) {
	    		sJsonMap = sStrList[0].toLowerCase() + sStrList[1].substring(0,1).toUpperCase() + sStrList[1].substring(1).toLowerCase() + sStrList[2].substring(0,1).toUpperCase() + sStrList[2].substring(1).toLowerCase();
	    	} else {
	    		sJsonMap = sColModelArr[i].toLowerCase();
	    	}
	    		    	
	    	sColNames.push(sColNameArr[i]);
	    	sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth});
	    } 
	    
	    //alert("fn_subGridInfo> 222222");
	    ////alert("fn_subGridInfo> 그리드생성 파라메타\n\n_sTabFormId : " + _sTabFormId + "\n_sGridId : " + _sGridId + "\n_sGridSubPager : " + _sGridSubPager + "\nsColNames : " + sColNames + "\nsColModels : " + sColModels + "\n_sGridHg : " + _sGridHg + "\n_sScrinCode : " + _sScrinCode);
	    
	    // 그리드 생성
		CONTENT.fn_subGridMake(_sTabFormId, _sGridId, _sGridSubPager, sColNames, sColModels, _sGridHg, _sScrinCode, _sDblclickYn, _sDblclickFun, _sClickFun, _sMultChk);
		//alert("fn_subGridInfo> 333333333");
		// 그리드 검색
		COMMON.fn_search_mainGrid(_sTabFormId, _sGridId);
		//alert("fn_subGridInfo> 4444444");
	}	  

	   fn_subGridInfo = function (_sTabFormId, _sGridId, _sGridSubPager, _sColNameList, _sColModelList, _sColWidth, _sColAlign, _sGridHg, _sScrinCode, _sDblclickYn, _sDblclickFun, _sClickFun, _sMultChk) {
		   
		      //alert("fn_subGridInfo> _sTabFormId : " + _sTabFormId + "\n_sGridId : " + _sGridId + "\n_sGridSubPager : " + _sGridSubPager + "\n_sColNameList : " + _sColNameList + "\n_sColModelList : " + _sColModelList + "\n_sColWidth : " + _sColWidth + "\n_sColAlign : " + _sColAlign + "\n_sGridHg : " + _sGridHg + "\n_sScrinCode : " + _sScrinCode);

		      var sColNames  = [];
		       var sColModels = [];
		       var sColWidth  = [];
		       var sColAlign  = [];
		       
		      var sColNameArr  = _sColNameList.split(",");
		       var sColModelArr = _sColModelList.split(",");
		       var sColWidthArr = _sColWidth.split(",");
		       var sColAlignArr = _sColAlign.split(",");

		       sColNames.push("순번");
		       sColModels.push({name:'ubseq', index:'ubseq', jsonmap:'ubseq', align:'center', width:'100px', key:true, hidden:true});
		       //alert("fn_subGridInfo> 111111111");
		       for (var i=0; i<sColNameArr.length; i++) {
		          
		          var sStrList, sJsonMap; 
		          var sWidth = sColWidthArr[i];
		          
		          if (sColWidthArr[i] == 0) {
		             sWidth = 120;
		          }
		          /* 홍성원 서브그리드 egovmap _ 컬럼 대문자 변환 메인그리드와 통일해서 공통 처리 */
		          sStrList = sColModelArr[i].split('_');
		          if (sStrList.length == 2 ) {
		             sJsonMap = sStrList[0].toLowerCase() + sStrList[1].substring(0,1).toUpperCase() + sStrList[1].substring(1).toLowerCase();
		          } else if(sStrList.length == 3) {
		             sJsonMap = sStrList[0].toLowerCase() + sStrList[1].substring(0,1).toUpperCase() + sStrList[1].substring(1).toLowerCase() + sStrList[2].substring(0,1).toUpperCase() + sStrList[2].substring(1).toLowerCase();
		          } else {
		             sJsonMap = sColModelArr[i].toLowerCase();
		          }
		                    
		          sColNames.push(sColNameArr[i]);
		          sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth});
		       } 
		       
		       //alert("fn_subGridInfo> 222222");
		       ////alert("fn_subGridInfo> 그리드생성 파라메타\n\n_sTabFormId : " + _sTabFormId + "\n_sGridId : " + _sGridId + "\n_sGridSubPager : " + _sGridSubPager + "\nsColNames : " + sColNames + "\nsColModels : " + sColModels + "\n_sGridHg : " + _sGridHg + "\n_sScrinCode : " + _sScrinCode);
		       
		       // 그리드 생성
		      CONTENT.fn_subGridMake(_sTabFormId, _sGridId, _sGridSubPager, sColNames, sColModels, _sGridHg, _sScrinCode, _sDblclickYn, _sDblclickFun, _sClickFun, _sMultChk);
		      
		      //키 컬럼 값 설정
		      COMMON.fn_getSelectMainGridData(_sTabFormId);
		      
		      //alert("fn_subGridInfo> 333333333");
		      // 그리드 검색
		      COMMON.fn_search_mainGrid(_sTabFormId, _sGridId);
		      //alert("fn_subGridInfo> 4444444");
		   }
	
   /**
	* @method
	* @description 상위 그리드 보기 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {String} _sTabFormId 데이터가지고 있는 폼아이디
	* @param {String} _sGridId 그리드 id
	* @param {String} _sGridSubPager 그리드 페이지 id
	* @param {String} _sColNames 그리드 col name
	* @param {String} _sColModels 그리드 model name
	* @param {String} _sGridHg 그리드 높이
	*/
	fn_subGridSearch = function(_sGridId, _sFormId, _sTabFormId, _sRowId) {		
		var sRowData = $("#"+_sGridId).getRowData(_sRowId);
		var sUbseq = sRowData["ubseq"];
		
//		var sTabId = $("#"+_sTabFormId).find("#tab_id").val();
//		if (sTabId == "") sTabId = "T0001";
		var sScrinCode = $("#"+_sTabFormId).find("#scrin_code").val();
		
		COMMON.fn_tabMenu(_sGridId, _sFormId, _sTabFormId, sScrinCode, sUbseq);
		
	}
	  
   /**
	* @method
	* @description 상세 form 객체 조회
	* @author 홍길동(2016.10.04)
	*/	
	fn_get_detailFormObj = function(_sScrin_code, _sTableName, _sUbseq, _sData, _sDetailFormId, _sFlag) {
		 
		var sRowId   = $("#gridMain").getGridParam("selrow");
		var sRowData = $("#gridMain").jqGrid("getRowData",sRowId);
		var gd_id   = sRowData["cont_gd_id"];
		var od_no   = sRowData["od_no"];	
		var cont_seq   = sRowData["cont_seq"];
		var cust_id   = sRowData["cust_id"];
		var rep_cust_id   = sRowData["rep_cust_id"];
		var bill_ym   = sRowData["bill_ym"];
		
		//alert("fn_tabMenu> gd_id="+gd_id);
		if (typeof gd_id == "undefined") { 
			gd_id=""; 
			//alert("fn_tabMenu> gd_id="+gd_id);
		} 
			
//		alert(_sScrin_code + " : " + _sTableName + " : " + _sUbseq + " : " + _sData + " : " + _sDetailFormId + " : " + _sFlag);
		
		$.ajax({
			type: 'POST',
			async: false,
			url: CONTEXT_ROOT + '/content/selectContentDetailObjList.do',  // main, sub 구분으로 쿼리조회
			data: 'scrin_code=' + _sScrin_code + '&tableName=' + _sTableName + '&ubseq=' + _sUbseq + "&formid=" + _sDetailFormId + '&gd_id=' + gd_id + "&flag=" + _sFlag,
			datatype : "json",
			success : function(data) {
				if (_sFlag == "main") {
					COMMON.fn_formObjMake(_sScrin_code, _sDetailFormId, data, _sData, _sTableName, _sUbseq);
					
					// 상세화면에 저장 버튼 생성
					var sBtnHtml = "";
					for (var i=0; i<data.rows.length; i++) {

						var colId   	= data.rows[i]["colid"];
						var objNm   	= data.rows[i]["name"];
						var objtype 	= data.rows[i]["objtype"];
						var extfun  	= data.rows[i]["extfun"];
						var extprfun  	= data.rows[i]["extprfun"];
						var extpofun  	= data.rows[i]["extpofun"];
						var prcnm  		= data.rows[i]["prcnm"];
						
						var r_show_yn  = data.rows[i]["rshowyn"];		//pjh fwcolctl에서 N로 등록한것은 제외
						
						if (objtype == "button_d" && r_show_yn != 'N') { 
							//sBtnHtml += '<button type="button" class="btn_pop" id="'+colId+'" onclick="fn_'+colId+'();">'+objNm+'</button>';
							sBtnHtml += '<button type="button" class="btn_pop" id="'+colId+'" onclick="COMMON.fn_com_click(\''+colId+'\',\''+extfun+'\',\''+extprfun+'\',\''+extpofun+'\',\''+prcnm+'\');">'+objNm+'</button>';
//							sBtnHtml += '<button type="button" class="btn_pop" id="'+colId+'" onclick="COMMON.fn_com_click(\'btn_xxx\');">'+objNm+'</button>';
							$("#frm_detail").find("#id_detail_button").html(sBtnHtml);
						}
					 
					}
					
					
				} else if (_sFlag == "sub") {
					var sHtml = COMMON.fn_formObjMake2(_sScrin_code, _sDetailFormId, data, _sData, _sTableName, _sUbseq);
					$("#"+_sDetailFormId).find("#html").val(sHtml);
				}
				
			},
			error: function(xhr, status, error) {
				$("#"+_sDetailFormId).find("#actionFlag").val("");  // 처리상태
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
							
	}
   
   /**
	* @method
	* @description 상세 화면 html 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sObjid  하위객체 조회용
	*/
	fn_formObjMake = function(_sScrin_code, _sDetailFormId, _sFormObj, _sFormData, _sTableName, _sUbseq) {
		var ubIdx = 1;
		var sHtml = "";
		var sSubHtml = $("#"+_sDetailFormId).find("#html").val(); 
		var tblcnt=0;
		//alert("fn_formObjMake> _sFormData>" + _sFormData);
		// form 객체
		for (var i=0; i<_sFormObj.rows.length  ; i++) {
					
			var objid       = _sFormObj.rows[i]["objid"];
			var pobjid      = _sFormObj.rows[i]["pobjid"];
			var name        = _sFormObj.rows[i]["name"];			
			var colid       = _sFormObj.rows[i]["colid"];
			var pcolid      = _sFormObj.rows[i]["pcolid"];
			var objpos      = _sFormObj.rows[i]["objpos"];
			var objtype     = _sFormObj.rows[i]["objtype"];
			var fixyn      = _sFormObj.rows[i]["fixyn"];
			var width       = _sFormObj.rows[i]["width"];
			var colpos      = _sFormObj.rows[i]["colpos"];
			var rowpos      = _sFormObj.rows[i]["rowpos"];
			var xmlid       = _sFormObj.rows[i]["xmlid"];
			var grpcd       = _sFormObj.rows[i]["grpcd"];			
			var grptype     = _sFormObj.rows[i]["grptype"];
			var subformyn   = _sFormObj.rows[i]["subformyn"];
			var maxxpos     = _sFormObj.rows[i]["maxxpos"];
			var colspan     = _sFormObj.rows[i]["colspan"];
			var objwidth    = _sFormObj.rows[i]["objwidth"];
			var objpk       = _sFormObj.rows[i]["objpk"];
			var dtlrowpos   = parseInt(_sFormObj.rows[i]["dtlrowpos"]);
			var dtlcolpos   = parseInt(_sFormObj.rows[i]["dtlcolpos"]);			
			var colmgrcnt   = parseInt(_sFormObj.rows[i]["colmgrcnt"]);
			var dtlmaxcol   = parseInt(_sFormObj.rows[i]["dtlmaxcol"]);			
			var formname    = _sFormObj.rows[i]["formname"];
			var dynData     = _sFormObj.rows[i]["dynData"];
			var dtlshowyn   = _sFormObj.rows[i]["dtlshowyn"];
			var dtlobjw     = _sFormObj.rows[i]["dtlobjw"];
			var dtlmustyn   = _sFormObj.rows[i]["dtlmustyn"];
			var keycolid    = _sFormObj.rows[i]["keycolid"];			
			var robjid      = _sFormObj.rows[i]["robjid"];
			var rshowyn     = _sFormObj.rows[i]["rshowyn"];
			var rriteyn     = _sFormObj.rows[i]["rriteyn"];
			var rolecolspan = _sFormObj.rows[i]["rolecolspan"];
			var extFun      = _sFormObj.rows[i]["extfun"];
			var maxlen      = _sFormObj.rows[i]["maxlen"];	
			var autogencol  = _sFormObj.rows[i]["autogencol"]; 
			//pjh 1로 시작하지 않으면 화면 생성 안됨
			if(i==0) { 
				dtlrowpos="1" ;
				dtlcolpos="1" ;
			}
		 
			if (objtype == "title") {				
    			sHtml +='<tr>\n';
    			sHtml += '<td colspan="'+colspan+'">\n';
    			sHtml +='<h4><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>'+name+'</h4></td>\n';
    			sHtml +='</tr>\n';
    			//alert( "fn_formHtmlMake> colid : " + colid + " colVal= "+colVal + " objtype="+objtype+ " name="+name);
    			//alert( "fn_formHtmlMake> sHtml : " + sHtml );
    			continue;
    		}
			 
			// 상세화면 data (key, value)	수정화면 구성
			if (_sFormData.rows === undefined) {
				sHtml += COMMON.fn_formHtmlMake(_sDetailFormId,"",objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,sSubHtml,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extFun,maxlen,autogencol);
				//alert("fn_formObjMake>1>" + sHtml);  
			} else {
				if (_sFormData.rows.length > 0) {
					
					$.each(_sFormData, function(key, value) {
						
						$.each(value, function(key, value) {
							
							$.each(value, function(key, value) {
								var colKey = key;
								var colVal = value;

								if (colKey == "ubseq" && ubIdx == 1) {
									sHtml += '<input type="hidden" id="ubseq" name="ubseq" value="'+colVal+'" />\n';
									ubIdx += 1;
								}
								if (colVal == " " && colVal.length == 1) {
									colVal = "";
								}
								// colKey 이 NULL 인경우 쿼리(ContentUtil.java)에서 스페이스 삽입								
								if (colid == colKey) {
	//								alert("[데이터 2]\n\n"+colKey+" : "+colVal+"\n\n[상세 ojb]\n\nobjid : " + objid + "\npobjid : " + pobjid + "\nname : " + name + "\ncolid : " + colid + "\npcolid : " + pcolid + "\nobjpos : " + objpos + "\nobjtype : " + objtype + "\nwidth : " + width + "\ncolpos : " + colpos + "\nrowpos : " + rowpos + "\nxmlid : " + xmlid + "\ngrpcd : " + grpcd + "\ngrptype : " + grptype + "\nsubformyn : " + subformyn + "\nmaxxpos : " + maxxpos + "\ncolspan : " + colspan + "\nobjwidth : " + objwidth + "\nobjpk : " + objpk + "\nformname : " + formname + "\ndynData : " + dynData);																
									// 화면 구성
									sHtml += COMMON.fn_formHtmlMake(_sDetailFormId,colVal,objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,sSubHtml,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extFun,maxlen,autogencol);
									//alert(sHtml);
								}
								
							});
						});
		
					});
					
				// 등록 화면 구성
				} else {		
					sHtml += COMMON.fn_formHtmlMake(_sDetailFormId,"",objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,sSubHtml,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extFun,maxlen,autogencol);				}
				//alert("fn_formObjMake> 2>" + sHtml);
			}
			

		}
		
		 
		//if(tblcnt>=1) sHtml += "</table>"
		//alert("fn_formObjMake sHtml=" + sHtml);
		 
		$("#"+_sDetailFormId).find("#id_detail").html(sHtml);
		
		// 레이어 팝업 띄우기				 
		var trCnt = $('#MainTable tr').length;  // tr 갯수
		var trCount = 105 + (parseInt(trCnt)*50) + 100 + 50;  // 상세화면 전체 heigth : 상단 height + (tr 갯수*50(tr heigth)) + 하단 height + 강제 50
		var tx = ($(window).width()-$("#ly_pop .confirm_cont").width())/2;
		
//	    var ty = ($(window).height()-$("#ly_pop .confirm_cont").height())/2;
		var ty = ($(window).height()-trCount)/2;
		if(ty < 0){
			ty = 15;
		}
		
		//pjh 팝업이 시작위치가 전체 크기 25%을 넘으면 10%로 재조정하고 그렇지 않으면 계산 크기 사용		
		if(tx >= $(window).width()*0.5)
			$("#ly_pop .confirm_cont").css({left:"10%",top:"20px", 'position':'absolute'});
		else  $("#ly_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});
		
	//	alert("fn_formObjMake>sHtml=" + sHtml);
		
		$("#ly_pop").show();  // 레이어 팝업 open
		
		// 객체 타입이 날짜(일자) 인 경우
		COMMON.fn_obj_date_datepicker(_sDetailFormId);
		
		// 상세화면 open 시 실행함수
		$("#frm_detail").find("#dFilesn").val("");
		var _sExtFun = $("#"+_sDetailFormId).find("#extFun").val();
		if (_sExtFun != "") {
            var callback_function = new Function(_sExtFun);		
			callback_function();
		}
		
	}
	
   /**
	* @method
	* @description 상세화면 html 생성 <tr> ~ </tr>
	* @author 홍길동(2016.10.04)
	* @param {String} _sObjid  하위객체 조회용
	*/
    fn_formHtmlMake = function(_sDetailFormId,colVal,objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,sSubHtml,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extFun,maxlen,autogencol) {    	    	
    	if ( (colid == "reg_id" || colid == "upd_id" || colid == "reg_tm" || colid == "upd_tm") && colVal == ""   ) return;
    	if ( objtype.indexOf("button_d") > -1 ) return;
    	if ( objtype.indexOf("button_g") > -1 ) return;

    	//if(objid.indexOf("title")>=0) alert( "fn_formHtmlMake> colid : " + colid + " colVal= "+colVal + " objtype="+objtype+ " name="+name);
    	// 권한이 적용된 객체일 경우 이전 또는 다음 객체에 대한 행병합 조회
    	if (rolecolspan != "0" && rolecolspan != "") {
    		colspan = rolecolspan;
    	}

    	var sHtml = ""; 
    	
    //		alert("XXX colVal ="+colVal);
    	if (objtype == "hidden") {
    		sHtml += '<input type="hidden" id="'+colid+'" name="'+colid+'" value="'+colVal+'" />\n';
    	}     
    	else { 
			 
    		if (dtlcolpos == 1) {
				sHtml += '<tr>\n';   
			}			
			
			//alert ("XX  objtype ="+objtype + " objid="+objid);
	    	// 권한별 객체 출력여부가 'N' 일 경우
	    	if (objid == robjid && rshowyn == "Y") {   
	    		sHtml += '';
	    	} 
	    	else {    
	    		 // 라벨생성
    			sHtml += '<th scope="row">\n';	
	    		
		
				if (objid == robjid && rshowyn == "Y") {
					sHtml += '';
				} 
				else {				
					if (name != null) {
						sHtml += name;
					}
					if (objtype != "") {  // objtype가 null 이 아닌 경우
						if (objpk == "Y") {
							sHtml += '<span class="asterisk"></span>\n';  // 필수입력 표시
						}
					}
				}		
				sHtml += '</th>\n';
				 
				//입력값 그리기
			 
				sHtml += '<td colspan="'+colspan+'">\n'; 				
				// 입력값 부분 생성 
				sHtml += COMMON.fn_makeForm(_sDetailFormId,colVal,objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extFun,maxlen,autogencol);
			 
				//alert( "fn_formHtmlMake> colid : " + colid + " colVal= "+colVal + " objtype="+objtype+ " name="+name);
				// 하위 폼 생성							
				if (subformyn != "0") {
					var sHtmlStr = sSubHtml.split("#");

					for (var i=0; i<sHtmlStr.length; i++) {
						var sVal = sHtmlStr[i].split("$");
						if (objid == sVal[0]) {
							sHtml += sVal[1];
						}
					}
					
				} 
				sHtml += '</td>\n';
				 
	    	}	
	    	   
			if (colmgrcnt == dtlmaxcol || colmgrcnt == dtlmaxcol*2) {
				sHtml += '</tr>\n';
			}  	    		
    	}
     
		return sHtml;

    }
	
   /**
	* @method
	* @description 상세 서브 화면 html 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sObjid  하위객체 조회용
	*/
	fn_formObjMake2 = function(_sScrin_code, _sDetailFormId, _sFormObj, _sFormData, _sTableName, _sUbseq) {
		
		var sHtml = "";
		
		// form 객체
		for (var i=0; i<_sFormObj.rows.length; i++) {
					
			var objid     = _sFormObj.rows[i]["objid"];
			var pobjid    = _sFormObj.rows[i]["pobjid"];
			var name      = _sFormObj.rows[i]["name"];			
			var colid     = _sFormObj.rows[i]["colid"];
			var pcolid    = _sFormObj.rows[i]["pcolid"];
			var objpos    = _sFormObj.rows[i]["objpos"];
			var objtype   = _sFormObj.rows[i]["objtype"];
			var fixyn   = _sFormObj.rows[i]["fixyn"];
			var width     = _sFormObj.rows[i]["width"];
			var colpos    = _sFormObj.rows[i]["colpos"];
			var rowpos    = _sFormObj.rows[i]["rowpos"];
			var xmlid     = _sFormObj.rows[i]["xmlid"];
			var grpcd     = _sFormObj.rows[i]["grpcd"];			
			var grptype   = _sFormObj.rows[i]["grptype"];
			var subformyn = _sFormObj.rows[i]["subformyn"];
			var maxxpos   = _sFormObj.rows[i]["maxxpos"];
			var colspan   = _sFormObj.rows[i]["colspan"];
			var objwidth  = _sFormObj.rows[i]["objwidth"];
			var objpk     = _sFormObj.rows[i]["objpk"];
			var dtlrowpos = parseInt(_sFormObj.rows[i]["dtlrowpos"]);
			var dtlcolpos = parseInt(_sFormObj.rows[i]["dtlcolpos"]);			
			var colmgrcnt = parseInt(_sFormObj.rows[i]["colmgrcnt"]);
			var dtlmaxcol = parseInt(_sFormObj.rows[i]["dtlmaxcol"]);			
			var formname  = _sFormObj.rows[i]["formname"];
			var dynData   = _sFormObj.rows[i]["dynData"];
			var dtlshowyn = _sFormObj.rows[i]["dtlshowyn"];
			var dtlobjw   = _sFormObj.rows[i]["dtlobjw"];
			var dtlmustyn = _sFormObj.rows[i]["dtlmustyn"];
			var keycolid  = _sFormObj.rows[i]["keycolid"];	
			var robjid    = _sFormObj.rows[i]["robjid"];
			var rshowyn   = _sFormObj.rows[i]["rshowyn"];
			var rriteyn   = _sFormObj.rows[i]["rriteyn"];
			var rolecolspan = _sFormObj.rows[i]["rolecolspan"];
			var extfun      = _sFormObj.rows[i]["extfun"];
			var maxlen      = _sFormObj.rows[i]["maxlen"];
			var autogencol  = _sFormObj.rows[i]["autogencol"];
			
			//alert("objid : " + objid + "\npobjid : " + pobjid + "\nname : " + name + "\ncolid : " + colid + "\npcolid : " + pcolid + "\nobjpos : " + objpos + "\nobjtype : " + objtype + "\nwidth : " + width + "\ncolpos : " + colpos + "\nrowpos : " + rowpos + "\nxmlid : " + xmlid + "\ngrpcd : " + grpcd + "\ngrptype : " + grptype + "\nsubformyn : " + subformyn + "\nmaxxpos : " + maxxpos + "\ncolspan : " + colspan + "\nobjwidth : " + objwidth + "\nobjpk : " + objpk + "\ndtlrowpos : " + dtlrowpos + "\ndtlcolpos : " + dtlcolpos + "\nformname : " + formname + "\ndynData : " + dynData);
			var checkData = "0";
			// 상세화면 data (key, value)
			$.each(_sFormData, function(key, value){
				checkData = "1"; 					
				$.each(value, function(key, value) {
		
					var colKey = key;
					var colVal = value;
					
					if (colid == colKey) {
						sHtml += "#" + pobjid + "$" + COMMON.fn_makeForm(_sDetailFormId,colVal,objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extfun,maxlen,autogencol);							
					}

				}); 
				
				// 객체정보와 테이블 데이터가 일치하지 않는 객체 생성 (버튼, ~, -, (, ), / 등)
				sHtml += "#" + pobjid + "$" + COMMON.fn_makeForm(_sDetailFormId,"",objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extfun,maxlen,autogencol);
				
			});					
		
			if(checkData == "0" && objtype == "button_p"){
				sHtml += "#" + pobjid + "$" + COMMON.fn_makeForm(_sDetailFormId,"",objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extfun,maxlen,autogencol);
			}
		
		}

		var str = sHtml.substring(0, 1);
		if (str == "#") {
			sHtml = sHtml.substring(1);
		}		
		return sHtml;
		
	}   
	
   /**
	* @method
	* @description form obj 생성
	* @author 홍길동(2016.10.04)
	*/
	fn_makeForm = function (_sDetailFormId,colVal,objid,pobjid,name,colid,pcolid,objpos,objtype,fixyn,width,colpos,rowpos,xmlid,grpcd,grptype,subformyn,maxxpos,colspan,objwidth,objpk,dtlrowpos,dtlcolpos,colmgrcnt,dtlmaxcol,formname,dynData,dtlshowyn,dtlobjw,dtlmustyn,keycolid,robjid,rshowyn,rriteyn,rolecolspan,extfun,maxlen,autogencol,_sScrinCode,_sTbl,_sXml) {

//if (objid == "border_no") {		
		
//		alert("fn_makeForm\n\n " + colVal + " : " + objid + " : " + pobjid + " : " + name + " : " + colid + " : " + pcolid + " : " + objpos + " : " + objtype + " : " + width + " : " + colpos + " : " + rowpos + " : " + xmlid + " : " + grpcd + " : " + grptype + " : " + subformyn + " : " + maxxpos + " : " + colspan + " : " + objwidth + " : " + objpk + " : " + dtlrowpos + " : " + dtlcolpos + " : " + colmgrcnt + " : " + dtlmaxcol + " : " + formname + " : " + dynData + " : " + dtlshowyn + " : " + dtlobjw + " : " + dtlmustyn + " : " + keycolid)
		
		var sHtml = "";
		
		// 상세화면 객체 width
		var sWidth = "";
		var sHeight = "";
		if (parseInt(dtlobjw) > 0) {
			sWidth = "width:"+dtlobjw +"px;";
			if (parseInt(dtlobjw) <= 1000) {
				sHeight = 'style="height:100px;"';
			}else if(parseInt(dtlobjw) <= 2000){
				sHeight = 'style="height:200px;"';
			}
		} else {
			// 콤보
			if (objtype == "select") {
				sWidth = "width:100%;";
			}
		}
		if(objtype == "textareaDiv"){
			var headStyle='style="text-align:center !important; font-weight: bold; color:#fff;  background:#777f98; padding:6px 20px;border-left:1px solid #e5e5e5;line-height:1.5em;min-height:0px;letter-spacing:-1px;"';
			var textareaStyle='style="background-color:white; -moz-appearance: textfield-multiline; -webkit-appearance: textarea; height: 200px; overflow: scroll; margin: 10px 0; padding: 3px 0 3px 4px; resize: none; width: 100%; border: 1px solid #b7b7b7; border-right-color: #e1e1e1; border-bottom-color: #e1e1e1; letter-spacing: -.5px; font-size: 12px; font-weight: normal; line-height: 18px;"';
			var textTitle ='style="border-top: 1px solid #e5e5e5;background: #f5f5f5;font-weight: bold;text-align: left;width: auto; padding:6px 20px;"';
			var textContent = 'style="border-top:1px solid #e5e5e5;border-left:1px solid #e5e5e5;line-height:1.5em;color:#4a4a4a;min-height:0px;letter-spacing:-1px; padding:6px 20px;"';
			var textareaStyle2='style="width:0px; height:0px; display:none;"';
			var textTitle2='style="border-top: 1px solid #e5e5e5;background: #fbf19d;font-weight: bold;text-align: left;width: auto; padding:6px 20px"';
		}
		
		var sObjClass = "";
		
		// class에 obj 명 지정
		var sObjNm = "ON_" + name;
		
		// 필수항목여부 class 지정
		var sEsYnClassNm = "";
		if (dtlmustyn == "Y") sEsYnClassNm = "ES";
		
		// class
		sObjClass = sEsYnClassNm + " " + sObjNm;
		
		// key 컬럼 및 권한이 적용된 객체 수정 불가
		var sReadonly = "";
		var sBgcolor  = "";
		/*if (keycolid !== undefined || rriteyn == "N") {
			if ( (colid == keycolid && colVal != "") || rriteyn == "N" ) {  // 객체 col_id와 해당 테이블의 key 컬럼이 같고, colVal 값이 있으면(수정일 경우) or 권한이 적용된 객체가 쓰기여부가 "N"인경 수정 불가
				sReadonly = "readonly";
				sBgcolor  = "background-color:#EEEEEE;";
			}
		}*/

		//if(objid=="cont_seq") alert("sReadonly:"+sReadonly+" objtype="+objtype+" fixyn="+fixyn+" objid="+objid);
		
		// pjh text, select 이외 모두 label 처리
		if( fixyn=="Y") {
			if(objtype=="date") { objtype="text"; }
			if(objtype!="text" && objtype!="select" )  objtype="label"; 
		}  
		
		// 권한이 적용된 객체 수정 불가
		if (rriteyn == "N" || fixyn=="Y") {   // 권한이 적용된 객체가 쓰기여부가 "N"인경 수정 불가
			if (objtype == "select") sReadonly = "disabled";
			else sReadonly = "readonly";
			//alert("fn_makeForm> sReadonly:"+sReadonly+" objtype="+objtype+" fixyn="+fixyn);
			sBgcolor  = "background-color:#EEEEEE;";		
		}
		
		// key 컬럼인 경우 원래값과 수정값을 비교하기 위함
		var sKeyClass = " KEY_N";
		
		if (keycolid !== undefined) {
			
			$("#"+_sDetailFormId).find("#org_"+keycolid).remove();
			$('<input/>').attr({type:'hidden',id:"org_"+keycolid,name:"org_"+keycolid,value:colVal}).appendTo("#"+_sDetailFormId);
			
			sKeyClass = " KEY_Y";
		}
		
		sObjClass += sKeyClass; 
		
		// 입력 최대길이
		if (maxlen == undefined) {
			maxlen = "";
		}

		// text
		if (objtype == "text") {			
			/*var sVal = colVal;						
			if (dynData != "null" && dynData !== undefined) {
				sVal = dynData;
			}*/
			// text타입이 obj_sql이 들어오면 쿼리 결과값으로 변경
			if (dynData !== undefined && dynData != "|") {
				if(extfun == "" )
					extfun = "null";
				sHtml += '<input type="text" id="'+colid+'" name="'+colid+'" value="'+dynData+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');"  />\n';
			}else{
				if(extfun == "" )
					extfun = "null";
				sHtml += '<input type="text" id="'+colid+'" name="'+colid+'" value="'+colVal+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');"  />\n';
			}
			//sHtml += '<input type="text" id="'+colid+'" name="'+colid+'" value="'+colVal+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+' onkeyup="COMMON.fn_text_chanage(\'frm_detail\', '+extfun+');" onclick="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');" />\n';	
					
		}
		
		// text
		if (objtype == "teltext") {			
			/*var sVal = colVal;						
			if (dynData != "null" && dynData !== undefined) {
				sVal = dynData;
			}*/
			// text타입이 obj_sql이 들어오면 쿼리 결과값으로 변경
			if (dynData !== undefined && dynData != "|") {
				if(extfun == "" )
					extfun = "null";
				sHtml += '<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+dynData+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');"  /></label><label>&nbsp;-&nbsp;</label>' + '<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+dynData+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');"  /><label><label>&nbsp;-&nbsp;</label>'+ '<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+dynData+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');"  /><label>\n';
			}else{
				var scolVal = colVal.split("-");
				if(typeof scolVal[0] == "undefined"){
					scolVal[0] = "";
				}
				if(typeof scolVal[1] == "undefined"){
					scolVal[1] = "";
				}
				if(typeof scolVal[2] == "undefined"){
					scolVal[2] = "";
				}
				if(extfun == "" )
					extfun = "null";
				sHtml += '<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+scolVal[0]+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');" size="5"/></label><label>&nbsp;-&nbsp;</label>' + '<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+scolVal[1]+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');" size="5" /></label><label>&nbsp;-&nbsp;</label>'+'<label><input type="text" id="'+colid+'" name="'+colid+'" value="'+scolVal[2]+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+'  onkeyup="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');" size="5" /></label>\n';
			}
			//sHtml += '<input type="text" id="'+colid+'" name="'+colid+'" value="'+colVal+'" maxlength="'+maxlen+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" '+sReadonly+' onkeyup="COMMON.fn_text_chanage(\'frm_detail\', '+extfun+');" onclick="COMMON.fn_auto_complete(\'frm_detail\',\''+autogencol+'\',\''+colid+'\',\'\');" />\n';	
					
		}

			
			
		// password
		if (objtype == "password") {
			sHtml += '<input type="password" id="'+colid+'" name="'+colid+'" value="'+colVal+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'" />\n';	
		}
		
		// 콤보
		if (objtype == "select") {
//			alert("colid : " + colid + "\ndynData : " + dynData);
			if (dynData !== undefined) {
				var sData = dynData.split("@");
/*				
				var sOptionArr = {};
				sOptionArr[""] = "선택";
*/				if(extfun == "" ) extfun = "null";
				sHtml += '<select id="'+colid+'" name="'+colid+'" style="'+sWidth+sBgcolor+';" class="selectBox input '+sObjClass+'" '+sReadonly+' onchange="COMMON.fn_det_selectbox_chanage(\'frm_detail\', '+extfun+');">\n';				
				sHtml += '<option value="">선택</option>\n'; 
					for (var i=0; i<sData.length; i++) {
						var sDataVal = sData[i].split("|");
						var sSelect  = "";
						if (sDataVal != "") {
							if (sDataVal[0] == colVal) sSelect = "selected";
					        sHtml += '     <option value="'+sDataVal[0]+'" '+sSelect+'>'+sDataVal[1]+'</option>\n';
						}
					}										
				sHtml += '</select>\n';

/*
				for (var i=0; i<sData.length; i++) {
					var sDataVal = sData[i].split("|");
					
					sOptionArr[sDataVal[0]] = sDataVal[1];						
				}				
				
				$("#"+colid).selectBox('options',  {
                    '1': 'Value 11111',
                    '2': 'Value 2',
                    '3': 'Value 3',
                    '4': 'Value 4',
                    '5': 'Value 5'
                });
*/
				
			} else {
				sHtml += '<select id="'+colid+'" name="'+colid+'" style="'+sWidth+sBgcolor+';padding:0 0px;" class="input '+sObjClass+'">\n';
				sHtml += '   <option value="">선택</option>\n';
				sHtml += '</select>\n';
			}
		}
		
		// 체크박스
		if (objtype == "checkbox") {
			var scolVal = colVal.split("@");
			if (dynData !== undefined) {
				sHtml += '<label id = "lab_'+colid+'">\n';
				var sData = dynData.split("@");
				for (var i=0; i<sData.length; i++) {
					var sDataVal = sData[i].split("|");
					if (sDataVal != "") {
						var sChecked  = "";	
						for(j=0; j<scolVal.length; j++){
							if (sDataVal[0] == scolVal[j]){
								sChecked = "checked";
							} 
						}
						
						if(extfun == "" ) extfun = "null";
							sHtml += '<label><input type="checkbox" id="'+colid+'" name="'+colid+'" value="'+sDataVal[0]+'" '+sChecked+' onclick="COMMON.fn_isCheckedById(\''+formname+'\',\''+colid+'\');COMMON.fn_com_click(\''+colid+'\', \''+extfun+'\');" style="'+sWidth+';" class="checkbox '+sObjClass+'" />'+sDataVal[1]+'</label>&nbsp;\n';
				        if (i == 0) {
				        	sHtml += '<input type="hidden" id="mult_check_'+colid+'" name="mult_check_'+colid+'" value="" />\n';
				        }
					}					
				}
				sHtml += '</label>\n';
			} else {
				sHtml += '';
			}

		}

		// 라디오버튼
		if (objtype == "radio") {
			if (dynData !== undefined) {
				var sData = dynData.split("@");
				for (var i=0; i<sData.length; i++) {
					var sDataVal = sData[i].split("|");
					if (sDataVal != "") {
						var sChecked2  = "";
						if (sDataVal[0] == colVal) sChecked2 = "checked";
						sHtml += '<input type="radio" id="'+colid+'" name="'+colid+'" value="'+sDataVal[0]+'" '+sChecked2+' class="radio '+sObjClass+'" /> '+sDataVal[1]+'\n';
					}
				}
			} else {
				sHtml += '';
			}
		}

		// 일선택 날짜
		if (objtype == "date") {
			sHtml += '<input maxlength="10" type="text" id="'+colid+'" name="'+colid+'" value="'+colVal+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+' datepicker" />\n';
		}
		
		// 월선택 날짜
		if (objtype == "month_date") {
			sHtml += '<input maxlength="6" type="text" id="'+colid+'" name="'+colid+'" value="'+colVal+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+' monthPicker" />\n';
		}

		// textarea
		if (objtype == "textarea") {
			sHtml += '<textarea id="'+colid+'" name="'+colid+'" rows="3" class="textarea '+sObjClass+'" '+sHeight+' " >'+colVal+'</textarea>';
		}	

		// 팝업 검색 버튼 (이미지)
		if (objtype == "button_p") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<button id="'+colid+'" name="'+colid+'" type="button" onclick="COMMON.fn_com_click(\''+colid+'\', \''+extfun+'\');" class="btn_search" title="검색"></button>';
		}

		// 팝업 버튼 (이미지)
		if (objtype == "button_i") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<button id="'+colid+'" name="'+colid+'" type="button" onclick="COMMON.fn_com_click(\''+colid+'\', \''+extfun+'\');" class="btn_search" title="검색"></button>';	  // 버튼 이미지 작업 후 class 변경
		}

		// 지우개 버튼 (이미지)
		if (objtype == "button_e") {
			sHtml += '<button id="'+colid+'" name="'+colid+'" type="button" onclick="COMMON.fn_eraser(\''+formname+'\','+objid+','+pobjid+');" class="btn_eraser" title="지우기"></button>';
		}

		// 버튼 (버튼 텍스트 입력)
		if (objtype == "button_t") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<button id="'+colid+'" name="'+colid+'" type="button" class="btn_m" id="'+objid+'" style="height:25px;padding:2px 13px 8px" onclick="COMMON.fn_com_click(\''+objid+'\',\''+extfun+'\');">'+name+'</button>';
		}
		
		// 파일 업로드
		if (objtype == "upfile") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<input type="hidden" id="'+objid+'cnt" name="'+objid+'cnt" value="'+colVal+'" style="width: 100px;" readonly><input type="text" id="'+objid+'nm" name="'+objid+'nm" class="input" value="첨부파일 '+colVal+'개" style="width: 100px;" readonly><button type="button" onclick="COMMON.fn_fileUpload(\''+colid+'\', \''+extfun+'\', \'A\');" class="btn_fadd" title="추가"></button><button type="button" onclick="COMMON.fn_fileSearch(\''+colid+'\', \''+extfun+'\', \'S\');" class="btn_fminus" title="삭제"></button>';
		}
		
		// 공통 파일 업로드
		if (objtype == "upcfile") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<div id="fileDiv"><button type="button" id="btn_add" name="btn_add" onclick="UPFILE.fn_cfileAdd()" class="btn_fadd" title="추가"></button><input type="hidden" id="lastfileid"></div>';
		}
		
		// 신청서 메일전송(본문내용 미리보기)
		if (objtype == "textareaDiv") {
			var keyNm = ['* 고객명(법인명)','접수일','* 작업내용','* 개통희망일(신규/변경/해지포함)','상태','구분1/계열사명','구분2/지점명','가입번호(TRUNK/ 회선번호)','서비스명','IP신청정보','부가서비스',
				'* 청구계정번호(청구번호) or 납부자번호','속도/대역폭','시내외구간','거리','* 계약기간(Y)','* 계약요금(원/, vat제외)','* 신청자(이름)','* 연락처(휴 대폰번호)','* 상위국 주소(장소-층수포함)','하위국 주소(장소-층수포함)',
				'* (상위국)개통담당자','* (상위국)개통담당자 연락처','(하위국)개통담당자','(하위국)개통담당자 연락처','추가요청사항(60자 이내)','비고(메모)'];
			//미리보기
			if($("#frm_main").find("#scrin_code").val() == 'M070109' ){
				var data = CUSTOM.fn_applyData();
				if(extfun == "" ) extfun = "null";
				sHtml += '<div id="'+colid+'" contenteditable="false" '+textareaStyle+'><table style="padding:0;border-collapse:collapse;border-spacing:0; font-size:13px;border: 1px solid #ddd; margin:10px; width:800px;">';
				sHtml += '<tr><th '+headStyle+'>NO.</th>';
				
				for(var i = 1; i < data.length + 1; i++){
					sHtml += '<td '+headStyle+'>'+i+'</td>';
				}
				sHtml += '</tr>';
				
				var jsonObjKey = [];
				
				var jsonObjVal = [];
				
				var dataString = JSON.stringify(data);
				
				for(var i = 1; i < 28; i ++){
					jsonObjKey.push(Object.keys(data[0])[i]);
				}
				
				for(var i = 0; i < data.length; i++){
					for(var j = 0; j < (Object.keys(data[i]).length); j++){
						jsonObjVal.push(data[i][Object.keys(data[i])[j]]);
					}
				}
				
				for(var j = 0; j < 27; j++){
					if(keyNm[j] == "상태" || keyNm[j] == "가입번호(TRUNK/ 회선번호)"){
						sHtml += '<tr><th '+textTitle2+'>'+keyNm[j]+'</th>';
					}else{
						sHtml += '<tr><th '+textTitle+'>'+keyNm[j]+'</th>';
					}
					for(var k = 0; k < data.length; k++){
						sHtml += '<td '+textContent+'>'+data[k][jsonObjKey[j]]+'</td>';
					}
					sHtml += '</tr>';
				}
				sHtml += '</table></div>';
			}
			//태그
			if($("#frm_main").find("#scrin_code").val() == 'M070109' ){
				var data = CUSTOM.fn_applyData();
				if(extfun == "" ) extfun = "null";
				sHtml += '<textarea id="'+colid+'" name="'+colid+'" class="textarea" '+textareaStyle2+'" ><table style="padding:0;border-collapse:collapse;border-spacing:0; font-size:13px;border: 1px solid #ddd; margin:10px; width:800px;">';
				sHtml += '<tr><th '+headStyle+'>NO.</th>';
				
				for(var i = 1; i < data.length + 1; i++){
					sHtml += '<td '+headStyle+'>'+i+'</td>';
				}
				sHtml += '</tr>';
				
				var jsonObjKey = [];
				
				var jsonObjVal = [];
				
				var dataString = JSON.stringify(data);
				
				for(var i = 1; i < 28; i ++){
					jsonObjKey.push(Object.keys(data[0])[i]);
				}
				
				for(var i = 0; i < data.length; i++){
					for(var j = 0; j < (Object.keys(data[i]).length); j++){
						jsonObjVal.push(data[i][Object.keys(data[i])[j]]);
					}
				}
				
				for(var j = 0; j < 27; j++){
					if(keyNm[j] == "상태" || keyNm[j] == "가입번호(TRUNK/ 회선번호)"){
						sHtml += '<tr><th '+textTitle2+'>'+keyNm[j]+'</th>';
					}else{
						sHtml += '<tr><th '+textTitle+'>'+keyNm[j]+'</th>';
					}
					for(var k = 0; k < data.length; k++){
						sHtml += '<td '+textContent+'>'+data[k][jsonObjKey[j]]+'</td>';
					}
					sHtml += '</tr>';
				}
				sHtml += '</table></textarea>';
			}
		}
		// 단건 파일 업로드
		if (objtype == "upsfile") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<input type="text" id="'+objid+'nm" name="'+objid+'nm" class="input" value="'+colVal+'" style="width: 200px;" readonly><button type="button" onclick="UPFILE.fn_sfileAdd(\''+colid+'\', \''+extfun+'\', \'SA\');" class="btn_fadd" title="추가"></button><button type="button" onclick="UPFILE.fn_sfileDel(\''+colid+'\', \''+extfun+'\', \'SS\');" class="btn_fminus" title="삭제"></button>';
		}
		
		// 이미지 파일 업로드
		if (objtype == "upimgfile") {
			if(extfun == "" ) extfun = "null";
			sHtml += '<input type="text" id="'+objid+'nm" name="'+objid+'nm" class="input" value="'+colVal+'" style="width: 200px;" readonly><button type="button" onclick="UPFILE.fn_ifileAdd(\''+colid+'\', \''+extfun+'\', \'SA\');" class="btn_fadd" title="추가"></button><button type="button" onclick="UPFILE.fn_ifileDel(\''+colid+'\', \''+extfun+'\', \'SS\');" class="btn_fminus" title="삭제"></button>';
		}

		// 물결(~)
		if (objtype == "wave") {
			sHtml += '~';
		}

		// 하이픈(-)
		if (objtype == "hyphen") {
			sHtml += '-';
		}

		// 좌괄로 (
		if (objtype == "bracket_l") {
			sHtml += '(';
		}

		// 우괄로 )
		if (objtype == "bracket_r") {
			sHtml += ')';
		}

		// 슬래쉬 (/) )
		if (objtype == "slash") {
			sHtml += '/';
		}
		
		// 년도 콤보
		if (objtype == "select_year") {
			var sDt = new Date();
			var y_year = parseInt(sDt.getFullYear());
			sHtml += '<select id="'+colid+'" name="'+colid+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'">\n';
			sHtml += '    <option value="">선택</option>\n';
			for (var i=0; i<15; i++) {
				sHtml += '<option value="'+(y_year-i)+'">'+(y_year-i)+'년</option>';
			}
			sHtml += '</select>';
		}
		
		// 월 콤보
		if (objtype == "select_month") {
			var sDt = new Date();
			var y_month = parseInt(sDt.getMonth() + 1);
			sHtml += '<select id="'+colid+'" name="'+colid+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'">\n';
			sHtml += '    <option value="">선택</option>\n';
			for (var i=1; i<=12; i++) {
				var mm = i;
				if (mm <= 9) {
					mm = "0" + i; 
				}
				var mm_selected = "";
//				if (mm == y_month) mm_selected = "selected";
				sHtml += '<option value="'+mm+'" '+mm_selected+'>'+mm+'</option>';
			}
			sHtml += '</select>';
		}
		
		// 시간 콤보
		if (objtype == "select_time") {
			sHtml += '<select id="'+colid+'" name="'+colid+'" style="'+sWidth+sBgcolor+'" class="input '+sObjClass+'">\n';
			for (var i=0; i<24; i++) {
				var time = i;
				if (time <= 9) {
					time = "0" + i; 
				}
				sHtml += '<option value="'+time+'">'+time+'</option>';
			}
			sHtml += '</select>';
		}
		
		// 라벨
		if (objtype == "label") {
			if (dynData !== undefined) {
				var sData = dynData.split("@");
				for (var i=0; i<sData.length; i++) {
					var sDataVal = sData[i].split("|");
					var sSelect  = "";
					if (sDataVal != "") {
						if (sDataVal[0] == colVal){ //sSelect = "selected";
					        sHtml += '<span id="'+colid+'">' + sDataVal[1] + '(' + colVal + ')' + '</span>';
						}
					}
				}
				
			}else{
				sHtml += '<span id="'+colid+'">' + colVal + '</span>';
			}
			
			
			/*if (dynData !== undefined) {
				sHtml += '<span id="'+colid+'">' + dynData + '</span>';
			} else {
				sHtml += '<span id="'+colid+'">' + colVal + '</span>';
			}*/
		}
		
		/*
		if (objtype == "title") { 
			// sHtml += '<span id="'+colid+'">' + colVal + '</span>';
			sHtml += '<span style="color:red !important">' + colVal + '</span>';
			
		//	sHtml += ' <div class="card-header">';
		//	sHtml += '<h3 class="card-title"><i class="fas fa-chevron-circle-right text-danger text-sm mr-2"></i>'+ colVal + '</h3>' ;
		//	sHtml += '</div>';
			  
		}
	*/
		
//alert(objid + " : end");
//}				
		
//		alert(sHtml);
		
		return sHtml;
	}

   /**
	* @method
	* @description 메뉴명
	* @author 홍길동(2016.10.04)
	*/
	fn_set_menu = function(_sMenuCode, _sMenuNm, _sBmark) {		

		var sHtml = "";
		var sClass = "btn_bmark";
		
		if (_sBmark == "D") {
			sClass = "btn_bmark on";
		}
		
		sHtml = '<button type="button" id="btn_bmark" class="'+sClass+'" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save(\'topForm\', \'\', \''+_sMenuCode+'\');"></button>'+_sMenuNm+'';
		var guidTxt = $("#guidTop").val();		
		if (guidTxt !== undefined) {
			sHtml += '<span class="add">'+guidTxt+'</span>';
		}
		
		$("#id_menu").html(sHtml);
	}
	
   /**
	* @method
	* @description 3자리 콤마(,) 찍어서 리턴
	* @author 홍길동(2016.10.04)
	* @param {String}
	*/
	fn_getCommaString = function(_sValues) {
		_sValues = String(_sValues);
	    return _sValues.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	}
	
   /**
	* @method
	* @description 북마크 위치 지정
	* @author 홍길동(2016.10.04)
	* @param {String}
	*/
	fn_bookMarkPos = function(_sId, _sPos, _sBookId1, _sBookId2, _lFlag) {
		
		var menuLeft  = document.getElementById('cbp-spmenu-s1');
		var menuRight = document.getElementById('cbp-spmenu-s2');
		var showLeft  = document.getElementById('showLeft');
		var showRight = document.getElementById('showRight');
		var body      = document.body;
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/updateBookMarkPos.do',
			data: 'bookmark_pos=' + _sPos + '&lFlag=' + _lFlag ,
			datatype : "json",
			success : function(data) {
				if (data.result == "SUCCESS") {
					$("#"+_sId).prop("checked", false);
					
					if (_sPos == "L") {			
						$("#"+_sBookId1).show();
						$("#"+_sBookId2).hide();			
						classie.toggle(menuRight, 'cbp-spmenu-open');
					    disableOther('showRight');			
					} else if (_sPos == "R") {			
						$("#"+_sBookId1).hide();
						$("#"+_sBookId2).show();			
						classie.toggle(menuLeft, 'cbp-spmenu-open');
					    disableOther('showLeft');
					}
				} else {
					$.unibillDialog.alert('에러', '에러가 발생하였습니다.');
				}
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});				
	}
	
   /**
	* @method
	* @description 등록 및 수정화면에서 저장 시 필수 항목 체크 / KEY 컬럼 수정 여부에 따른 등록,수정 모드 셋팅
	* @author 홍길동(2016.10.04)
	* @param {String}
	*/
	fn_columnValidate = function(_sDetailFormId, _sMsg, _sGubun) {
		var input_nm     = "";
		var input_id_nm  = "";
		var sExcept = ["scrin_code","tableName","callBackFunction","actionFlag","html"];
		var editCnt = 0;
		var nokeyCnt = 0;
		var input_type="";
		
		var checkimg = false;
		var extlist = "";
		var checkscript = false;
		var checknum = false;
		var checknumNm = "";
		var voice = $("#frm_detail").find("#voice_id").val() == undefined ? '' : $("#frm_detail").find("#voice_id").val().trim();
		voice = voice.replace(/ /g, '%20');
		var ast = $("#frm_detail").find("#ast").attr("value");
		if(($("#scrin_code").val() == "M110102" || $("#scrin_code").val() == "M030106") && (voice == "" || voice == null) && ast == "1"){
			$.unibillDialog.alert('알림', "[음원코드] (은)는 필수입력항목입니다.");
			return false;
		}
		
		//이미지 파일 업로드시 필수 항목 체크
		if($("#scrin_code").val() == "S0042"){
			if($("#frm_detail").find("#popup_yn:checked").val() == "Y"){
				var childrens = $("#imgfile").children();
				if(childrens.length <= 0){
					$.unibillDialog.alert('확인', "업로드 할 이미지를 선택하세요.");
					return false;
				}
				
				//확장자 체크
				$.ajax({
					type: 'POST',
					url: CONTEXT_ROOT + '/content/checkext.do',
					processData: false,
					contentType: false, 
					data: "",
					async: false,
					success : function(data) {
						var fnm = $("#imgfileuploadnm").val();
						var imgf = fnm.split(".");
						
						for(var i=0; i<data.reqStatus.length; i++){
							if(i == 0){
								extlist = data.reqStatus[i].val;
							}else{
								extlist = extlist + ', ' + data.reqStatus[i].val;
							}
							
							
							if(data.reqStatus[i].val == imgf[1]){
								checkimg = true;
							}
						}
						
					},
					error: function(xhr, status, error) { }
				});
				
			}else{
				checkimg = true;
			}
		}
		
		if($("#scrin_code").val() == "S0042" && checkimg == false){
			$.unibillDialog.alert('확인', "업로드 가능한(" + extlist + ") 확장자가 아닙니다.");
			return false;
		}
		
		$("#"+_sDetailFormId).find
		("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox'], input[type='radio'], select, textarea").each( 
		  function(){			
			var input_cs  = $('#'+_sDetailFormId).find($(this)).getClasses();
			input_nm      = $('#'+_sDetailFormId).find($(this)).attr('id');
			var input_val = $(this).val();
			var ON        = "";
			var KEY       = "";
			input_type      = $('#'+_sDetailFormId).find($(this)).attr('type');
			var chkflag = COMMON.fn_chekExcept(sExcept, input_nm);
			
			var disable       = $('#'+_sDetailFormId).find($(this)).attr('disabled');
			
			//if(input_nm =="od_no" || input_nm =="od_day"  ) alert("fn_columnValidate> disable="+disable+ " input_nm="+input_nm );
			 
		//	alert("fn_columnValidate> disable="+disable+ " input_nm="+input_nm );
			//return true; //pjh test 
			//스크립트 체크
		 	var reg = "script";
			var reg2 = "alert(";
			if(input_val!=""){
		 		if(input_val.indexOf(reg) >= 0 || input_val.indexOf(reg2) >= 0){
		 			checkscript = true;
			 		scriptmsg = "입력값에 script나 alert(을 입력할 수없습니다.";
			 		return false;
			 	}
		 	}
			
			var chkendswith = ["_no", "_sn", "_len", "_pos", "cnt", "cost", "_amt", "fee", "seq", "unit_val", "_step", "byte", "pid", "port", "proc_id", "PROGRESS", "_rate", 'dur', 'limit_val'];
			var chkendflag = true;
			var chknm = "";
			var skipword = ["BOOKMARK_POS","chk_type", "bookmark_pos","jy_no"];
			var skipflag = true; 
			
			for(var i=0; i<chkendswith.length; i++){
				var chkval = chkendswith[i];
				var position = input_nm.length - chkval.length;
				var lastindex = input_nm.indexOf(chkval, position);
				if(input_nm == "limit_val"){
					//alert("chkendflag : " + chkendflag + ", input_nm : " + input_nm + ", chkval : " + chkval + ", lastindex : " + lastindex + ", position : " + position);
					//alert("lastindex : " + lastindex + ", position : " + position);
				}
				if(chkendflag && (lastindex !==-1 && lastindex === position) ){
					chknm = input_nm;
					chkendflag = false;
				}
				
			}

			if(!chkendflag){
				var chkstr = input_val;
				if(input_val.indexOf(",") > -1){
					chkstr = chkstr.replace(/,/gi,"");
				}
				if(input_val.indexOf("-") > -1){
					chkstr = chkstr.replace(/-/gi,"");
				}
				if(input_val.indexOf(".") > -1){
					chkstr = chkstr.replace(/./gi,"");
				}
				if((input_nm != "ubseq" && input_nm !="dtl_tmp_type") && isNaN(Number(chkstr))){
					checknumNm = CUSTOM.fn_getColidNm("#"+chknm,'frm_detail');
					//예외처리 검사
					for(var i=0; i<skipword.length; i++){
						var chkval = skipword[i];
						var position = input_nm.length - chkval.length;
						var lastindex = input_nm.indexOf(chkval, position);							
						if( (lastindex !==-1 && lastindex === position) ){
							//alert("XXXXXXXXXX chkval="+chkval+ " input_nm= "+input_nm);
							return true;						
						}					
					}
					checknum = true;
					return false;
				}
			}
				
				
			
			
		 	if (!chkflag) {
			//	alert("fn_columnValidate> disable="+disable+ "[input_nm : " + input_nm + "][input_cs : " + input_cs + "][input_val : " + input_val + "]" + "][input_type : " + input_type + "]");		 
				$.each( input_cs, function(index,value){
					try {
						var class_nm = value;
						
						if( class_nm!='' ) {
							if( class_nm.match(/ON_/) ) {
								ON = class_nm.substr(3);
							} else if( class_nm.match(/KEY_/) ) {
								KEY = class_nm.substr(4);
							}
						}
						
					}catch(E){
						$.unibillDialog.alert('에러', E);
						return false;
					}
				});
				 
				if (disable == "disabled"   && KEY != "Y" ) {
				 	//alert("fn_columnValidate>  XXXX disable="+disable+ " input_nm="+input_nm + " KEY="+KEY ); 
			 		return true;
			 	} 
				 
				if (input_cs.indexOf("ES") > -1) {
					
					// radio 버튼 인 경우
					if (input_cs.indexOf("radio") > -1) {
					
						if ($("input:radio[id='"+input_nm+"']:checked").length <= 0) {
							input_id_nm += "^|^"+input_nm+"^!^[" +  ON + "] " + _sMsg;
						}
						
					// checkbox 버튼 인 경우
					} else if (input_cs.indexOf("checkbox") > -1) {
					
						if ($("input:checkbox[id='"+input_nm+"']:checked").length <= 0) {
							input_id_nm += "^|^"+input_nm+"^!^[" +  ON + "] " + _sMsg;
						}											
						
					} else {
						
						if (input_val == "") {
							if (input_id_nm == "") {
								input_id_nm = input_nm+"^!^[" +  ON + "] " + _sMsg;
							} else {
								input_id_nm += "^|^"+input_nm+"^!^[" + ON + "] " + _sMsg;
							}
						}
						
					}
				}
				
				// KEY 컬럼인 경우 KEY 컬럼 값 수정 여부
				if (KEY == "Y") {
					var orgVal  = $("#"+_sDetailFormId).find("#org_"+input_nm).val();  // 수정전 값
					var currVal = $("#"+_sDetailFormId).find("#"+input_nm).val();      // 수정후 값
					
					// 수정전 값과 수정후 값이 다르면 수정
					if (orgVal != currVal) {
						editCnt += 1;
					}
					nokeyCnt += 1;
				}

			}
			
		});
		
		//checknum=false;  //인천교육청 검사안함
		if(checknum){
			$.unibillDialog.alert('알림', checknumNm + "에 숫자를 입력하여 주세요.");
			return false;
		}
		
		

		if (_sGubun === undefined || _sGubun != "N") {
			// 등록 및 수정모드 셋팅
			if(nokeyCnt == 0){ // key 컬럼이 존재하지 않는 경우
				$("#"+_sDetailFormId).find("#actionFlag").val("INSERT");
			}else{ // key 컬럼이 존재하는 않는 경우
				if (editCnt > 0) {	// key 컬럼을 수정한 경우
					$("#"+_sDetailFormId).find("#actionFlag").val("INSERT");
				} else {  // key 컬럼을 수정하지 않은 경우
					$("#"+_sDetailFormId).find("#actionFlag").val("UPDATE");
				}
			}
		}
		
		// radio 및 checkbox 에 대한 중복 메시지 제거
		var sInputIdNm = "";
		var sColId = "";
		var sInputNmArr = input_id_nm.split("^|^");
		
		for (var i=0; i<sInputNmArr.length; i++) {
			var sInNmArr = sInputNmArr[i].split("^!^");
			
			if (sColId != sInNmArr[0]) {
				if (sInputIdNm == "") {
					sInputIdNm = sInNmArr[1];
				} else {
					sInputIdNm += "</br>" + sInNmArr[1];
				}
			}
			
			sColId = sInNmArr[0];
		}
		
		if (sInputIdNm != "") {
			$.unibillDialog.alert('알림', sInputIdNm);
			return false;
		}
		
		if(checkscript){
			$.unibillDialog.alert('알림', scriptmsg);
			return false;
		}
		
		//사용자 관리 암호화 룰 체크
		if($("#scrin_code").val() == "S0019"){
			var ispass = CUSTOM.fn_passcheck();
			if(ispass != ""){
				$.unibillDialog.alert('알림', ispass);
				return false;
			}
		}
		
		
		
		
		
	//	$.unibillDialog.alert('알림', "fn_columnValidate end ...");
		return true;
	}
	
	
	fn_columnValidate2 = function(_sDetailFormId, _sMsg, _sGubun) {
		var input_nm     = "";
		var input_id_nm  = "";
		var sExcept = ["scrin_code","tableName","callBackFunction","actionFlag","html"];
		var editCnt = 0;
		var nokeyCnt = 0;
		var input_type="";
		
		$("#"+_sDetailFormId).find
		("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox'], input[type='radio'], select, textarea").each( 
		  function(){			
			var input_cs  = $('#'+_sDetailFormId).find($(this)).getClasses();
			input_nm      = $('#'+_sDetailFormId).find($(this)).attr('id');
			var input_val = $(this).val();
			var ON        = "";
			var KEY       = "";
			input_type      = $('#'+_sDetailFormId).find($(this)).attr('type');
			var chkflag = COMMON.fn_chekExcept(sExcept, input_nm);
			
			var disable       = $('#'+_sDetailFormId).find($(this)).attr('disabled');
			 
			//if(input_nm =="od_no" || input_nm =="od_day"  ) alert("fn_columnValidate> disable="+disable+ " input_nm="+input_nm );
			 
		//	alert("fn_columnValidate> disable="+disable+ " input_nm="+input_nm );
		 	 
			
		 	if (!chkflag) {
		 		
		 		/*if(input_nm == "od_no"){
		 			alert("bbb : " + input_nm);
		 			alert("ccc : " + input_val);
		 		}*/
		 		
			//	alert("fn_columnValidate> disable="+disable+ "[input_nm : " + input_nm + "][input_cs : " + input_cs + "][input_val : " + input_val + "]" + "][input_type : " + input_type + "]");		 
				$.each( input_cs, function(index,value){
					try {
						var class_nm = value;
						if( class_nm!='' ) {
							if( class_nm.match(/ON_/) ) {
								ON = class_nm.substr(3);
							} else if( class_nm.match(/KEY_/) ) {
								KEY = class_nm.substr(4);
							}
						}
					}catch(E){
						$.unibillDialog.alert('에러', E);
						return false;
					}
				});
				 
				if (disable == "disabled"   && KEY != "Y" ) {
				 	//alert("fn_columnValidate>  XXXX disable="+disable+ " input_nm="+input_nm + " KEY="+KEY ); 
			 		return true;
			 	} 
				 
				if (input_cs.indexOf("ES") > -1) {
					
					// radio 버튼 인 경우
					if (input_cs.indexOf("radio") > -1) {
					
						if ($("input:radio[id='"+input_nm+"']:checked").length <= 0) {
							input_id_nm += "^|^"+input_nm+"^!^[" +  ON + "] " + _sMsg;
						}
						
					// checkbox 버튼 인 경우
					} else if (input_cs.indexOf("checkbox") > -1) {
					
						if ($("input:checkbox[id='"+input_nm+"']:checked").length <= 0) {
							input_id_nm += "^|^"+input_nm+"^!^[" +  ON + "] " + _sMsg;
						}											
						
					} else {
					
						if (input_val == "") {
							if (input_id_nm == "") {
								input_id_nm = input_nm+"^!^[" +  ON + "] " + _sMsg;
							} else {
								input_id_nm += "^|^"+input_nm+"^!^[" + ON + "] " + _sMsg;
							}
						}
						
					}
				}
				
				// KEY 컬럼인 경우 KEY 컬럼 값 수정 여부
				if (KEY == "Y") {
					var orgVal  = $("#"+_sDetailFormId).find("#org_"+input_nm).val();  // 수정전 값
					var currVal = $("#"+_sDetailFormId).find("#"+input_nm).val();      // 수정후 값
					// 수정전 값과 수정후 값이 다르면 수정
					if (orgVal != currVal) {
						editCnt += 1;
					}
					nokeyCnt += 1;
					
				}

			}
			
		});

		if (_sGubun === undefined || _sGubun != "N") {
			// 등록 및 수정모드 셋팅
			if(nokeyCnt == 0){ // key 컬럼이 존재하지 않는 경우
				$("#"+_sDetailFormId).find("#actionFlag").val("INSERT");
			}else{ // key 컬럼이 존재하는 경우
				if (editCnt > 0) {	// key 컬럼을 수정한 경우
					$("#"+_sDetailFormId).find("#actionFlag").val("INSERT");
				} else {  // key 컬럼을 수정하지 않은 경우
					$("#"+_sDetailFormId).find("#actionFlag").val("UPDATE");
				}
			}
		}
		
		// radio 및 checkbox 에 대한 중복 메시지 제거
		var sInputIdNm = "";
		var sColId = "";
		var sInputNmArr = input_id_nm.split("^|^");
		
		for (var i=0; i<sInputNmArr.length; i++) {
			var sInNmArr = sInputNmArr[i].split("^!^");
			
			if (sColId != sInNmArr[0]) {
				if (sInputIdNm == "") {
					sInputIdNm = sInNmArr[1];
				} else {
					sInputIdNm += "</br>" + sInNmArr[1];
				}
			}
			
			sColId = sInNmArr[0];
		}
		var ret = new Array();
		ret[0] = sInputIdNm;
		if (sInputIdNm != "") {
			//$.unibillDialog.alert('알림', sInputIdNm);
			ret[1] = false;
			return ret;
		}
	//	$.unibillDialog.alert('알림', "fn_columnValidate end ...");
		
		
		ret[1] = true;
		return ret;
	}
	
   /**
	* @method
	* @description 검색 시 객체 입력값 검증
	* @author 홍길동(2016.10.04)
	* @param {String}
	*/
	fn_form_obj_validate = function(_sFormId) {
		
		var result   = true;
		var input_nm = "";
		var msg      = "";
		
		$('#'+_sFormId).find("input[type='text'], input[type='radio'], select").each( function(){
			
			var input_cs  = $('#'+_sFormId).find($(this)).getClasses();
			input_nm      = $("#"+_sFormId).find($(this)).attr("id");
			var ON        = "";
			var ES        = "";
			var inputType = "";

			if (input_nm !== undefined) {
				$.each( input_cs, function(index,value){
					try {	
						
						var class_nm = value;
						if( class_nm!='' ) {
							if( class_nm.match(/ON_/) ) {
								ON = class_nm.substr(3);
							} else if( class_nm.match(/ES_/) ) {
								ES = class_nm.substr(3);
							}
							
							if ( class_nm.match(/radio/) ) {
								inputType = "radio";
							} else if ( class_nm.match(/select/) ) {
								inputType = "select";
							}
						}
					}catch(E){ 
						$.unibillDialog.alert('에러', E);
					}
				});

				if (ES == "Y") {

					var sFromId = "";					
					if (inputType == "radio") {
						var radioChk = $("#"+_sFormId).find("input:radio[id='"+input_nm+"']").is(":checked");
						if (radioChk) {
							sFromId = radioChk;	
						}
					} else if (inputType == "select") {						
						var selectVal = $("#"+_sFormId).find("#"+input_nm+" option:selected").val();
						
//						alert("inputType : " + inputType + "\nselectVal : " + selectVal + "\ninput_nm : " + input_nm + "\nON : " + ON);						
						
						if (!selectVal == "") {
							sFromId = selectVal;	
						}
					} else {					
						sFromId = $("#"+_sFormId).find("#"+input_nm).val();
					}

					if (sFromId == "") {
						msg += "[" + ON + "] 필수 검색항목입니다.</br>";
						result = false;
					}

				} else {
					if (input_nm.indexOf("from_") > -1) {
						
						var sObj  = input_nm.replace("from_", ""); 
						var sFrom = $("#"+_sFormId).find("#"+input_nm).val();
						var sTo   = $("#"+_sFormId).find("#to_"+sObj).val();
						
	//					msg += COMMON.fn_checkDateFomat(ON, sFrom);
	//		            msg += COMMON.fn_checkDateFomat(ON, sTo);		            
						
						var sFromMsg = COMMON.fn_checkDateFomat(ON, sFrom);
						var sToMsg   = COMMON.fn_checkDateFomat(ON, sTo);
						
						if (sFromMsg != "") msg += sFromMsg + "</br>";
						if (sToMsg   != "") msg += sToMsg + "</br>";
			          
						if (sFrom != "" && sTo == "") {
							msg += "["+ON+"] 종료일을 입력해 주십시오.</br>";
							result = false;
						} else if (sFrom == "" && sTo != "") {
							msg += "["+ON+"] 시작일을 입력해 주십시오.</br>";
							result = false;
							
						}
						
						if (sFrom != "" && sTo != "") {
							if (sFrom > sTo) {
								msg += "[" + ON + "] 시작일은 종료일보다 이전 이어야 합니다.</br>";
					    		result = false;
					    	}
						}
						
					} else if (input_nm.indexOf("to_") > -1) {

					} else if(input_nm.indexOf("start_") > -1){
						var sObj  = input_nm.replace("start_", ""); 
						var sFrom = $("#"+_sFormId).find("#"+input_nm).val();
						var sTo   = $("#"+_sFormId).find("#end_"+sObj).val();
						
						var sFromMsg = COMMON.fn_checkMonthFomat(ON, sFrom);
						var sToMsg   = COMMON.fn_checkMonthFomat(ON, sTo);
						
						if (sFromMsg != "") msg += sFromMsg + "</br>";
						if (sToMsg   != "") msg += sToMsg + "</br>";
						
						if (sFrom != "" && sTo == "") {
							msg += "["+ON+"] 종료일을 입력해 주십시오.</br>";
							result = false;
						} else if (sFrom == "" && sTo != "") {
							msg += "["+ON+"] 시작일을 입력해 주십시오.</br>";
							result = false;
							
						}
						
						if (sFrom != "" && sTo != "") {
							if (sFrom > sTo) {
								msg += "[" + ON + "] 시작일은 종료일보다 이전 이어야 합니다.</br>";
					    		result = false;
					    	}
						}
						
						
					} else if(input_nm.indexOf("end_") > -1){
						
					}
					/*
					else if(input_nm.indexOf("acc_cd") > -1){
						var num = $("#"+_sFormId).find("#"+input_nm).val();
						//var num=$("#frm_detail").find("#acc_cd").val();
						if(isNaN(num)){
							msg += "["+ON+"] spas 입력은 숫자를 입력해 주십시오.</br>";
							result = false;
						} 
					}*/

				}

			}	
			
		});

		 
		// 중복 메시지 제거
		var sMsgArr = msg.split("</br>");
		var sPreMsg = ""; 
		var sAlertMsg = "";
		
		for (var i=0; i<sMsgArr.length; i++) {
			if (sPreMsg != sMsgArr[i]) {
				sAlertMsg += sMsgArr[i] + "</br>";
				sPreMsg = sMsgArr[i];
			}

		}

		if (msg != "") {
			$.unibillDialog.alert('알림', sAlertMsg);
			return false;
		}
		
		return result;
	}
	
	/**
	 * @method
	 * @description 날짜 유효성 체크
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_checkDateFomat = function(_sNm, _sDate) {

		if (_sDate != "") {
		
			try {
	            _sDate = _sDate.replace(/-/g,'');
	            
	            // 자리수가 맞지않을때
	            if( isNaN(_sDate) || _sDate.length!=8 ) {
	            	return "["+_sNm + "] YYYY-MM-DD 형식으로 입력해 주십시오.";
	            }
	
	            var year = Number(_sDate.substring(0, 4));
	            var month = Number(_sDate.substring(4, 6));
	            var day = Number(_sDate.substring(6, 8));
	
	            var dd = day / 0;
	
	            if( month<1 || month>12 ) {
	            	return "["+_sNm + "] 날짜 형식이 맞지 않습니다.";
	            }
	
	            var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	            var maxDay = maxDaysInMonth[month-1];
	
	            // 윤년 체크
	            if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
	                maxDay = 29;
	            }
	
	            if( day<=0 || day>maxDay ) {
	            	return "["+_sNm + "] 날짜 형식이 맞지 않습니다.";
	            }
	            
	            return "";
	
	        } catch (err) {
	        	return "["+_sNm + "] 날짜 형식이 맞지 않습니다.";
	        }
	        
		} else {
			return "";
		}
	}
	
	fn_checkMonthFomat = function(_sNm, _sDate) {
		if (_sDate != "") {
			
			try {
	            _sDate = _sDate.replace(/-/g,'');
	            
	            // 자리수가 맞지않을때
	            if( isNaN(_sDate) || _sDate.length!=6 ) {
	            	return "["+_sNm + "] YYYYMM 형식으로 입력해 주십시오.";
	            }
	
	            var year = Number(_sDate.substring(0, 4));
	            var month = Number(_sDate.substring(4, 6));
	            
	
	            if( month<1 || month>12 ) {
	            	return "["+_sNm + "] 날짜 형식이 맞지 않습니다.";
	            }
	
	            return "";
	
	        } catch (err) {
	        	return "["+_sNm + "] 날짜 형식이 맞지 않습니다.";
	        }
	        
		} else {
			return "";
		}
	}
	
	/**
	 * @method
	 * @description 버튼 클릭 공통 이벤트
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_com_click = function(_sFn, _sExtFun, _sExtPre, _sExtPost, _sPrcNm) {
		var sPreCallBackFun ="";
		var sCallBackFun = "";
		var sPostCallBackFun = "";
		if (_sExtFun == "null" || _sExtFun == "") {			
			sCallBackFun = "fn_" + _sFn + "()";
			//$("#frm_main").find("#searchYn").val("Y"); //pjh
			$("#frm_main").find("#mgSearchYn").val("N"); //pjh
			
			//alert("fn_com_click ");
			
			//저장전처리
			if (_sExtPre != "null" && _sExtPre != "") {
				sPreCallBackFun = _sExtPre + "()";
			}
			sCallBackFun = sCallBackFun;
			//저장후처리
			if (_sExtPost != "null" && _sExtPost != "") {
				if(_sExtFun.indexOf("(") > -1){
					sPostCallBackFun = _sExtPost;
				}else{
					sPostCallBackFun = _sExtPost + "()";
				}
				
				//sCallBackFun = "fn_" + _sFn + "('','" + sPostCallBackFun  + "')"
			}
			
			if (_sFn == "btn_save") {  // 저장				
				/* pjh test
				var num=$("#frm_detail").find("#acc_cd").val();				 
				if(isNaN(num)){
				    //alert("spas 값은 숫자입니다");
				    $.unibillDialog.alert('확인', 'spas 값은 숫자입니다.');
				    return false;
				} 
				*/				
				//COMMON.fn_obj_check(); //숫자입력 검사, 길이제한, 필수여부 
				
				
			}else if(_sFn == "btn_update_reset"){
				
				COMMON.fn_obj_update_reset(_sFn); // 유저 제한해제
				return;
				
			}
			else if(_sFn == "btn_update_notuse"){
				COMMON.fn_obj_update_notuse(_sFn); // 유저 제한해제
				return;
				
			}
			else if (_sFn.indexOf("btn_update") > -1) {	// UPDATE만 사용
				//COMMON.fn_obj_update(_sFn);
				if((_sExtPre != "null" && _sExtPre != "") && (_sExtPost != "null" && _sExtPost != "")){
					sCallBackFun = "COMMON.fn_obj_update('','" + sPreCallBackFun  + "', '" + sPostCallBackFun + "')";
				}else if((_sExtPre != "null" && _sExtPre != "") && (_sExtPost == "null" || _sExtPost == "")){
					sCallBackFun = "COMMON.fn_obj_update('','" + sPreCallBackFun  + "','')";
				}else if((_sExtPre == "null" || _sExtPre == "") && (_sExtPost != "null" && _sExtPost != "")){
					sCallBackFun = "COMMON.fn_obj_update('','','" + sPostCallBackFun + "')";
				}else{
					sCallBackFun = "COMMON.fn_obj_update('','','')";
				}
				var callback_function = new Function(sCallBackFun);	
				callback_function();
				return;
				
			}else if (_sFn.indexOf("btn_insert") > -1){
				if((_sExtPre != "null" && _sExtPre != "") && (_sExtPost != "null" && _sExtPost != "")){
					var pre_callback_function = new Function(sPreCallBackFun);
					var post_callback_function = new Function(sPostCallBackFun);
					sCallBackFun = "COMMON.fn_obj_insert('','" + sPreCallBackFun  + "', '" + sPostCallBackFun + "')";
				}else if((_sExtPre != "null" && _sExtPre != "") && (_sExtPost == "null" || _sExtPost == "")){
					var pre_callback_function = new Function(sPreCallBackFun);	
					sCallBackFun = "COMMON.fn_obj_insert('','" + sPreCallBackFun  + "','')";
				}else if((_sExtPre == "null" || _sExtPre == "") && (_sExtPost != "null" && _sExtPost != "")){
					sCallBackFun = "COMMON.fn_obj_insert('','','" + sPostCallBackFun + "')";
				}else if((_sExtPre == "null" || _sExtPre == "") && (_sExtPost == "null" || _sExtPost == "")){
					sCallBackFun = "COMMON.fn_obj_insert('','','')";
				}
				var callback_function = new Function(sCallBackFun);	
				callback_function();
				
				//COMMON.fn_obj_insert(_sFn);
				return;
			}else if (_sFn == "btn_search") {  // 메인그리드 조회	
				
				COMMON.fn_search_main();
				
				// 검색 여부 (조회 버튼 클릭 후 그리드 조회 여부)
				$("#frm_main").find("#searchYn").val("Y");
				var searchYn = $("#frm_main").find("#searchYn").val();
			//	alert("btn_search searchYn="+searchYn	);
				return;
				
			} else if (_sFn == "btn_excelDown") {  // 엑셀다운로드 조회	
				//$("#frm_main").find("#mgSearchYn").val("Y");
				COMMON.fn_excel_download();
				return;
					
			}else if(_sFn == "btn_excelDownSh"){ // 엑셀 시트별 다운로드 조회
				COMMON.fn_excel_download_sheet();
				return;
				
			}
			 else if (_sFn == "btn_sql") {  // 메인그리드 조회
					COMMON.fn_search_main();
					
					// 검색 여부 (조회 버튼 클릭 후 그리드 조회 여부)
					$("#frm_main").find("#searchYn").val("Y");
					return;
					
				}
			 else if(_sFn == "btn_send"){
				 fn_mail_send();
				 return;
			 }
			else  {
				sCallBackFun = "CUSTOM."+sCallBackFun;
				
			}
		
		} else {
			sCallBackFun = _sExtFun;
			
			if (_sFn == "btn_save") {  // 저장
				//저장전처리
				if (_sExtPre != "null" && _sExtPre != "") {
					sPreCallBackFun = _sExtPre + "()";
				}
				sCallBackFun = _sExtFun;
				//저장후처리
				if (_sExtPost != "null" && _sExtPost != "") {
					sPostCallBackFun = _sExtPost + "()";
				}
			}else if(_sFn.indexOf("btn_insert") > -1){
				var callback_function = new Function(_sExtFun + "()");	
				callback_function();
				return;
			}else if(_sFn.indexOf("btn_update") > -1){
				var callback_function = new Function(_sExtFun + "()");	
				callback_function();
				return;
			}else{
				if(_sExtFun.indexOf("(") > -1){
					var callback_function = new Function(_sExtFun);	
					callback_function();
				}else{
					
					var callback_function = new Function(_sExtFun + "()");	
					callback_function();
				}
				
				return;
			}
		}
		
		
		
		if(_sFn == "btn_save" && (_sExtPre != "null" && _sExtPre != "") && (_sExtPost != "null" && _sExtPost != "")){
			var pre_callback_function = new Function(sPreCallBackFun);
			var post_callback_function = new Function(sPostCallBackFun);
			sCallBackFun = "fn_" + _sFn + "('','" + sPreCallBackFun  + "', '" + sPostCallBackFun + ", '" + _sPrcNm + "')";
			
		}else if(_sFn == "btn_save" && (_sExtPre != "null" && _sExtPre != "") && (_sExtPost == "null" || _sExtPost == "")){
			var pre_callback_function = new Function(sPreCallBackFun);	
			sCallBackFun = "fn_" + _sFn + "('','" + sPreCallBackFun  + "','','" + _sPrcNm + "')";
		}else if(_sFn == "btn_save" && (_sExtPre == "null" || _sExtPre == "") && (_sExtPost != "null" && _sExtPost != "")){
			sCallBackFun = "fn_" + _sFn + "('','','" + sPostCallBackFun + ", '" + _sPrcNm + "')";
		}else{
			if (_sExtFun == "null" || _sExtFun == "") {
				sCallBackFun = "fn_" + _sFn + "('','','','" + _sPrcNm + "')";
			}else{
				sCallBackFun = _sExtFun + "()";
			}
			
		}
		var callback_function = new Function(sCallBackFun);	
		callback_function();
		
		var callback_function = new Function(_sExtFun);	
		callback_function();

		
	}
	
	fn_PreSavePost = function(pre, center, post){
		(new Function(pre))();
		(new Function(center))();
		(new Function(post))();
	}
	
	fn_test1 = function(){
		alert("1111");
		alert("2222");
		
		
	}

	fn_test2 = function(){
		alert("3333");
		alert("4444");
	}
	
	/**
	 * @method
	 * @description 자동 완성 기능
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_auto_complete = function(_sFormId, _sAutoGenCol, _sColid, _sToColid, _sTableNm, _sAutoId) {

		//alert("자동완성\n\n" + _sFormId + "\n" + _sAutoGenCol + "\n" + _sColid + "\n" + _sToColid + "\n" + _sTableNm + "\n" + _sAutoId + "\n" + _sAutoGenCol.length);

		if (_sAutoGenCol == "" || _sAutoGenCol == "null") return;
		var sTableName = "";
	
		if (_sTableNm == undefined || _sTableNm == "") {
//			sTableName = $("#"+_sFormId).find("#tableName").val()	
			sTableName = _sAutoGenCol.substr(0,_sAutoGenCol.indexOf("="));	//auto_gen_col에 입력한 테이블을 보도록 수정
		} else {
			sTableName = _sTableNm;
		}
			
		var sAutoComId = "";
		if (_sAutoId == undefined || _sAutoId == "") {  // input id와 검색 테이블 컬럼id가 같은 경우
			sAutoComId = _sColid;
		} else {   // 아닌 경우
			sAutoComId = _sAutoId;
		}
		// alert("sAutoComId="+sAutoComId);
		$("#"+_sFormId).find("#"+sAutoComId).autocomplete({	 
			 source : function(request, response) {
				// alert("_sAutoGenCol="+_sAutoGenCol);
			 $.ajax({			 				 
				 type : "post",
				 url : CONTEXT_ROOT + "/content/selectAutoComplete.json?tableName="+sTableName+"&col_id="+_sColid+"&auto_gen_col="+_sAutoGenCol,
				 dataType : "json",
				 data: request,	
				 async: false,
				 success : function(data) {
					 	var result = data;
					 	response(result);
				  },				 
				  	error : function(data) {
				  	$.unibillDialog.alert('에러', '에러가 발생하였습니다.');
				  }
			   });
			},
			
//			autoFocus:true,         // 첫번째 값을 자동 focus한다.
			matchContains:true,     // true : 글자 순서대로 검색되게 한다. false : 입력한 글자가 들어간 모든 검색어를 보여준다. 
		    minLength:1,            // 1글자 이상 입력해야 autocomplete이 작동한다.
		    delay:100,              // milliseconds
		    select: function( event, ui ) {
	            // 만약 검색리스트에서 선택하였을때 선택한 데이터에 의한 이벤트발생
		    	var sTxt  = ui.item.value;
/*
		    	if (_sToColid == "") {
		    		$("#"+_sFormId).find("#"+sAutoComId).val(sTxt);
		    	} else {  // () 안에 명 또는 번호(코드)가 있을 경우
*/
			    	var sVal  = sTxt.substring(0, sTxt.indexOf("[")).trim();
			    	var sVal2 = sTxt.substring(sTxt.indexOf("[")+1, sTxt.indexOf("]")).trim();

			    	// [] 안에 명 또는 번호(코드)가 있을 경우
			    	if (sVal != "" && sVal2 != "") {
				    	$("#"+_sFormId).find("#"+sAutoComId).val(sVal);
				    	
				    	if (_sToColid != "") {
				    		$("#"+_sFormId).find("#"+_sToColid).val(sVal2);
				    	}
			    	} else {
			    		$("#"+_sFormId).find("#"+sAutoComId).val(sTxt);
			    	}
//		    	}
		    	
		    	return false;
	        },
			focus:function(event, ui){return false;} // 한글입력시 포커스이동하면 서제스트가 삭제되므로 focus처리
		});
		
	}
	
	fn_obj_update = function(_sObjId, _spre, _spost){
		var sDetailFormId= "frm_detail";
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '<spring:message code="common.required.msg" />';
		
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 개별등록일 경우
		if (dtlRadioVal == "I") {
			// 필수항목 체크 없음 / KEY 컬럼 수정 여부에 따르지 않고 무조건 수정 모드 셋팅			
			$("#"+sDetailFormId).find("#actionFlag").val("UPDATE"); 
			var msg = '수정하시겠습니까?';
			var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
			
			var sXmlId = $("#frm_main").find("#xmlId").val();

			if (sXmlId == undefined || sScrinCode=="S0031") sXmlId = '';
			$("#"+sDetailFormId).find("#xmlId").remove();
			$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);

			//저장 이전처리
			if(typeof _spre != "undefined" && _spre != ""){
				var callback_function = new Function(_spre);
				callback_function();
			}
			
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", CONTEXT_ROOT + "/content/saveContent.do", "fn_save_callback_content");
						
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
		
		// 일괄등록일 경우
		} else if (dtlRadioVal == "E") {
			$.unibillDialog.alert('알림', '일괄업로드는 구현이 되어 있지 않습니다.');
		}
	}
	
	
	fn_obj_insert = function(_sObjId, _spre, _spost){
		var sDetailFormId= "frm_detail";
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '<spring:message code="common.required.msg" />';
		
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 개별등록일 경우
		if (dtlRadioVal == "I") {
			// 필수항목 체크 없음 / KEY 컬럼 수정 여부에 따르지 않고 무조건 수정 모드 셋팅			
			$("#"+sDetailFormId).find("#actionFlag").val("INSERT"); 
			var msg = '등록하시겠습니까?';
			var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
			
			var sXmlId = $("#frm_main").find("#xmlId").val();

			if (sXmlId == undefined || sScrinCode=="S0031") sXmlId = '';
			$("#"+sDetailFormId).find("#xmlId").remove();
			$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);
			
			//저장 이전처리
			if(typeof _spre != "undefined" && _spre != ""){
				var callback_function = new Function(_spre);
				callback_function();
			}
			
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
		                // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", CONTEXT_ROOT + "/content/saveContent.do", "fn_save_callback_content");
						
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
		
		// 일괄등록일 경우
		} else if (dtlRadioVal == "E") {
			$.unibillDialog.alert('알림', '일괄업로드는 구현이 되어 있지 않습니다.');
		}
	}

	
	
	
	
	
	/**
	 * @method
	 * @description 상세(등록/수정)화면에서 개별 및 일괄등록 선택시
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_dtl_regist_type = function(sType) {
		//$("input:radio[name='dtl_tmp_type']:radio[value='"+sType+"']").attr("checked",true);
		// 개별등록
		if (sType == "I") {
			$("input:radio[name='dtl_tmp_type']:radio[value='I']").prop("checked",true);
			$("input:radio[name='dtl_tmp_type']:radio[value='E']").prop("checked",false);
			
			$("#MainTable").show();
			$("#UploadTable").hide();
			if($("#frm_detail").find("#scrin_code").val() == "S0196" || $("#frm_detail").find("#scrin_code").val() == "S0027"){
				$("#frm_detail").find("#btn_save").hide();
				$("#frm_detail").find("#btn_insert").show();
				$("#frm_detail").find("#btn_update").show();
			}
			
			COMMON.fn_reset_form("frm_detail");
			
			
			
		} else if (sType == "E") {  // 일괄등록(엑셀업로드)
			$("input:radio[name='dtl_tmp_type']:radio[value='I']").prop("checked",false);
			$("input:radio[name='dtl_tmp_type']:radio[value='E']").prop("checked",true);
			$("#MainTable").hide();
			$("#UploadTable").show();
			$("#excel_sheet").show();
			if($("#frm_detail").find("#scrin_code").val() == "S0196" || $("#frm_detail").find("#scrin_code").val() == "S0027"){
				$("#frm_detail").find("#btn_save").show();
				$("#frm_detail").find("#btn_insert").hide();
				$("#frm_detail").find("#btn_update").hide();
			}
			$("#id_excel_upload_result").text("");          // 작업메시지
			$("#frm_detail").find("#excel_sheet").val("");  // 시트구분
			$("#id_excel_upload_tot_cnt").text("0");        // 전체건수
			$("#id_excel_upload_ok_cnt").text("0");         // 성공건수
			$("#id_excel_upload_err_cnt").text("0");        // 오류건수
			
		}
		
//		UTILE.fn_log($(":input:radio[name=dtl_tmp_type]:checked").val());
	}
	
	/**
	 * @method
	 * @description 첨부파일 확장자 체크
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_upload_file_check = function(_sFile, _sExt, _sExtNm) {		
		var ext = _sFile.split(".").pop().toLowerCase();
		
		if ($.inArray(ext, _sExt) == -1) {
			$.unibillDialog.alert('알림', _sExtNm + ' 파일만 업로드 해 주십시오.');
			return false;
		}
		return true;
	}
	
	/**
	 * @method
	 * @description MULTIPART 폼인 경우
	 * @author 홍길동(2016.10.04)
	 * @param {String}
	 */
	fn_submit_fileForm =function(_sFormId, _sTargetIframe, _sAction, _sCallback) {
		$("#"+_sFormId).find("#callBackFunction").val(_sCallback);	// 처리후 복귀 함수
		$("#"+_sFormId).attr("action", _sAction);
		$("#"+_sFormId).attr("target", _sTargetIframe);
		try {
			var nm = Array();
			var val= Array();
		
//			$("#"+_sFormId).find("input[type='text'],input[type='password']").each( function(){
			$("#"+_sFormId).find("input[type='hidden'], input[type='text'],input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea").each( function(){
				nm.push( $(this).attr("name") );
				val.push( $(this).val() );
				try {	// 마스크 등 제거
					$(this).val( $(this).cleanVal() );
				}catch(E){}
			});
			$("#"+_sFormId).submit();
			/*for (var i=0; i < nm.length; i++) {
				("#"+nm[i]).val( val[i] );
			}*/

		} catch(E) {}
	}
	
	/**
	 * @method
	 * @description 파일다운로드
	 * @author 홍길동
	 * @param
	 */
	fn_download_file = function(_sFileCours, _sFileName, _sFileDelYn) {
		
		if (_sFileDelYn == undefined || _sFileDelYn == "") {
			_sFileDelYn = "";
		}
		
		try {
			$("#download_frm").find("#file_cours").val(_sFileCours);
			$("#download_frm").find("#file_name").val(_sFileName);	
			$("#download_frm").find("#file_del_yn").val(_sFileDelYn);
			$("#download_frm").attr("action","/file/downloadFile.do");
			$("#download_frm").attr("target","proc_frm");
			$("#download_frm").submit();
		}catch(E){
			$.unibillDialog.alert('에러', E);
		}
	}
	
	/**
	 * @method
	 * @description 검색 조건에 년도(YYYY) + 월(MM) 콤보 시 YYYYMM 형태로 변경 후 서브밋 
	 * @author 홍길동
	 * @param
	 */
	fn_form_date_select = function(_sFormId) {
		
		var result       = true;
		var input_nm     = "";
		var input_type   = "";
		var input_y_id   = "";
		var input_m_id   = "";
		var input_val    = "";
		var yearVal      = "";
		var monthVal     = "";
		var pre_input_id = "";
		
		$("#"+_sFormId).find("select").each( function(){
			var inputNm = $("#"+_sFormId).find($(this)).attr("id");
			
			if (inputNm !== undefined) {
				
				if (inputNm.indexOf("_yyyy") > -1 || inputNm.indexOf("_mm") > -1) {

					try {	// 마스크 등 제거
						input_val = $(this).cleanVal();
					} catch(E) {
						input_val = $(this).val();
					}
					
					if (inputNm.indexOf("_yyyy") > -1) {
						input_y_id = inputNm.replace("_yyyy", "");
						yearVal    = input_val;
					} else if (inputNm.indexOf("_mm") > -1) {
						input_m_id = inputNm.replace("_mm", "");
						monthVal   = input_val;					
					}

					if (input_y_id == input_m_id) {
						
						if (yearVal != "" && monthVal == "") {	
							$("#"+_sFormId).find("#"+input_m_id).remove();
							$('<input/>').attr({type:'hidden',id:input_m_id,name:input_m_id,value:''}).appendTo("#"+_sFormId);
							$.unibillDialog.alert('알림', '월을 선택해 주십시오.');
							result = false;
						}
						
						if (yearVal == "" && monthVal != "") {
							$("#"+_sFormId).find("#"+input_y_id).remove();
							$('<input/>').attr({type:'hidden',id:input_y_id,name:input_y_id,value:''}).appendTo("#"+_sFormId);
							
							$.unibillDialog.alert('알림', '년도를 선택해 주십시오.');
							result = false;
						}
						
						if (yearVal == "" && monthVal == "") {
							$("#"+_sFormId).find("#"+input_y_id).remove();
							$('<input/>').attr({type:'hidden',id:input_y_id,name:input_y_id,value:''}).appendTo("#"+_sFormId);
						}
					}
					
				}
				
			}
		});

		if (result) {

			$("#"+_sFormId).find("select").each( function(){
				input_nm = $("#"+_sFormId).find($(this)).attr("id");
				
				if (input_nm !== undefined) {				
					
					input_type = $(this).attr("type");
					try {	// 마스크 등 제거
						input_val = $(this).cleanVal();
					} catch(E) {
						input_val = $(this).val();
					}
					
					if (input_nm.indexOf("_yyyy") > -1) {
						input_y_id = input_nm.replace("_yyyy", "");
						yearVal    = input_val;
					} else if (input_nm.indexOf("_mm") > -1) {
						input_m_id = input_nm.replace("_mm", "");
						monthVal   = input_val;					
					}								
					
				}
				
	//			alert(yearVal + " : " + monthVal);
					
				if (yearVal != "" || monthVal != "") {
	
					// 검색 데이터 생성		
					var search_val = "";
					var search_id  = "";
					
					// 년도 + 월
					if (yearVal != "" && monthVal != "") {
						search_val = yearVal + monthVal;
						search_id  = input_y_id;
					} else if (yearVal != "" && monthVal == "") {	// 년도
						search_val = yearVal;
						search_id  = input_y_id;	
					} else if (yearVal == "" && monthVal != "") {	// 월
						search_val = monthVal;
						search_id  = input_m_id;
					}
					
//					alert("pre_input_id : " + pre_input_id + "\nid : " + search_id + "\nval : " + search_val);

					if (pre_input_id == search_id) {										
							
						if (search_val != "" && search_val.length == 6) {
							$("#"+_sFormId).find("#"+search_id).remove();
							$('<input/>').attr({type:'hidden',id:search_id,name:search_id,value:search_val}).appendTo("#"+_sFormId);
						}
	
					} else {
						pre_input_id = search_id;
					}				
									
				}
	
			});		
					
			return result;
			
		}
		
	}
	
	/**
	 * @method
	 * @description block UI 
	 * @author 홍길동
	 * @param
	 */
	fn_scrin_block_ui = function(_sGubun) {
		
		if (_sGubun == "B") {
			
			$.blockUI({
			     message : "", 
			     baseZ : 999999,
				overlayCSS:  {
					backgroundColor : 'rgba(0,0,0,0.0)',  // 배경투명하게
					border  : '0px solid #a00',           // 테두리 없앰
					opacity : 0.6,
					cursor  : 'wait'
				}
			});
			
		} else if (_sGubun == "UB") {

			$.unblockUI();

		}

	}
	
	/**
	 * @method
	 * @description 기간선택 버튼(당일, 1주일, 1개월, 3개월) 클릭시 
	 * @author 홍길동
	 * @param
	 */
	fn_set_find_day = function(_sFormId, _sObjId) {		
		// 당일
		if (_sObjId == "btn_today") {
			$("#"+_sFormId).find("#from_find_day_datepicker").val(COMMON.fn_get_date("pDay", 0, 0));
			$("#"+_sFormId).find("#to_find_day_datepicker").val(COMMON.fn_get_date("cDay", 0, 0));
		} else if (_sObjId == "btn_week") {    // 1주일
			$("#"+_sFormId).find("#from_find_day_datepicker").val(COMMON.fn_get_date("pDay", 7, 0));
			$("#"+_sFormId).find("#to_find_day_datepicker").val(COMMON.fn_get_date("cDay", 7, 0));
		} else if (_sObjId == "btn_month1") {  // 1개월
			$("#"+_sFormId).find("#from_find_day_datepicker").val(COMMON.fn_get_date("pDay", 30, 0));
			$("#"+_sFormId).find("#to_find_day_datepicker").val(COMMON.fn_get_date("cDay", 30, 0));
		} else if (_sObjId == "btn_month3") {  // 3개월
			$("#"+_sFormId).find("#from_find_day_datepicker").val(COMMON.fn_get_date("pDay", 91, 0));
			$("#"+_sFormId).find("#to_find_day_datepicker").val(COMMON.fn_get_date("cDay", 91, 0));
		}
	}

	/**
	 * @method
	 * @description 화면(화면ID (scrin_id))별 검색시 특화기능 
	 * @author 홍길동
	 * @param
	 */
	fn_form_scrin_validate = function(_sFormId) {

		var scrin_id = $("#"+_sFormId).find("#scrin_code").val();

		// 개통신청(S0065), 계약변경(S0103)
		if (scrin_id == "S0065" || scrin_id == "S0103") {
			var find_day      = $("#"+_sFormId).find("#find_day").val();                  // 검색기간 조건
			var from_find_day = $("#"+_sFormId).find("#from_find_day_datepicker").val();  // 검색기간 시작일
			var to_find_day   = $("#"+_sFormId).find("#to_find_day_datepicker").val();    // 검색기간 종료일

//			alert(find_day + " : " + from_find_day + " : " + to_find_day);

			if (find_day == "" && from_find_day != "" && to_find_day != "") {
				$.unibillDialog.alert('알림', '기간검색 구분을 선택해 주십시오.');
				return false;
			}

			if (find_day != "" && from_find_day == "" && to_find_day == "") {
				$.unibillDialog.alert('알림', '검색기간 시작일 및 종료일을 입력해 주십시오.');
				return false;
			}
		}
		if (scrin_id == "S0077" || scrin_id == "S0082" || scrin_id == "S0084" || scrin_id == "S0086" || scrin_id == "S0087" || scrin_id == "S0088" || scrin_id == "S0089") {
			var from_eday1      = $("#"+_sFormId).find("#from_eday1_datepicker").val().replace("-","").substring(0,5);
			var to_eday1      = $("#"+_sFormId).find("#to_eday1_datepicker").val().replace("-","").substring(0,5);

			if (from_eday1 != to_eday1) {
				$.unibillDialog.alert('알림', '조회기간의 월이 동일해야 합니다.');
				return false;
			}
		}
		
		return true;
	}
			
	/**
	 * @method
	 * @description 그리드 초기화
	 * @author 홍길동
	 * @param
	 */	
	fn_clear_grid_data = function(_sGridId) {
		$("#"+_sGridId).clearGridData();
	}
	
	/**
	 * @method
	 * @description select box 선택시 하위 콤보 조회
	 * @author 홍길동
	 * @param
	 */
	fn_selectbox_chanage = function(_sFormId, _sColId, _sScrinId, _sExtFun) {

		if (_sExtFun == "") {
//			if (_sScrinId == "S0065" && _sColId == "svc_id") {
			if (_sColId == "svc_id") {
				//SVCMGR.fn_svc_select_change(_sFormId,_sColId);
			}
			return;
		} else {
			
			var callback_function = new Function(_sExtFun);		
			callback_function();
			return;
		}
	}
	
	/**
	 * @method
	 * @description 일괄적용 버튼 클릭시
	 * @author 홍길동
	 * @param
	 */
	fn_bnde_applc = function(_sFormId, _sApplicFormId, _sGridId) {
		var rowIds = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
		
		if (rowIds.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			$("#id_loader").hide();	 
			return;
		}

		// ubseq set
		$("#"+_sApplicFormId).find("#ubseq").val(rowIds);
		
		// 팝업 show
		var tx = ($(window).width() - 500) / 2;
		var ty = ($(window).height() -470) / 2;
		if(ty < 0){
			ty = 15;
		}
		$("#ly_bnde_applc_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_bnde_applc_pop").show();
		
		// 팝업 타이틀
		var sPopTitle = $("#"+_sApplicFormId).find("#popTitel").val();
		$("#lab_pop_title").text(sPopTitle);
		
		// 해당 업무 화면 show
		var scrin_code = $("#"+_sFormId).find("#scrin_code").val();
		$("#ly_bnde_applc_"+scrin_code).show();
		
		// 개별 함수 호출
		var extFun = $("#"+_sApplicFormId).find("#extFun").val();

		if (extFun != "" || extFun != undefined) {
			var callback_function = new Function(extFun);		
			callback_function();
		}
	}
	
	/**
	 * @method
	 * @description 일괄적용 화면 구성
	 * @author 홍길동
	 * @param
	 */
	fn_bnde_app_detail = function(_sFormId) {

		// 주문상태 select box 생성
		dynamicSelect('grp_type', '/content/commonCodeSelect.json', _sFormId, 'od_status', 'OD_STATUS', '', 'Y', '');
		
		/* 메인화면 검색조건 데이터 추출 후 일괄적용 form 에 객채생성 (예시)
		    var sSvcId = $("#frm_main").find("#svc_id").val();   // 메인화면 값 추출  
		    $("#frm_bnde_applc").find("#svc_id").remove();       // 일괄적용 팝업화면 폼 객체 삭제
			$('<input/>').attr({type:'hidden',id:'svc_id',name:'svc_id',value:sSvcId}).appendTo("#frm_bnde_applc");  // 일괄적용 팝업화면 폼 객체 생성		       
		 */
	}
	
	/**
	 * @method
	 * @description 일괄적용 저장 버튼 클릭시
	 * @author 홍길동
	 * @param
	 */
	fn_applc_save = function(_sFormId, _sMsg) {
		
		var saveFun = $("#"+_sFormId).find("#saveFun").val();
		var valiFun = $("#"+_sFormId).find("#valiFun").val();

		if (saveFun == "" || saveFun == undefined) {
					
			// 필수입력 항목 체크
			if (!COMMON.fn_columnValidate(_sFormId, " 필수입력항목입니다.", "N")) return;
			
			$.unibillDialog.confirm('확인', _sMsg, 
				    function (isTrue) {
						try {
								
							// 처리중 메시지 show
							$("#id_loader").show();	 
							COMMON.fn_scrin_block_ui("B");
	
							COMMON.fn_submit_createForm(_sFormId, "proc_frm", CONTEXT_ROOT + "/content/saveBndeApplc.do", "fn_save_callback_bndeApplc");
				
						} catch (E) {
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);
		} else {
			var callback_function = new Function(saveFun);		
			callback_function();
		}
		
	}
	
	/**
	 * @method
	 * @description 일괄적용 저장 후 처리
	 * @author 홍길동
	 * @param
	 */
	fn_save_callback_bndeApplc = function(_sOID) {
		
		var sFormId = "frm_main";
		var sGridId = "gridMain";
		
		COMMON.fn_search_mainGrid(sFormId, sGridId);
		
		// 팝업화면 hide
		$("#ly_bnde_applc_pop").show();
	}	
	
	/**
	 * @method
	 * @description 공통상세화면에서 콤보 선택시 해당 함수 리턴
	 * @author 홍길동
	 * @param
	 */
	fn_det_selectbox_chanage = function(_sFormId, _sExtFun) {		
		if (_sExtFun == undefined) return;

		var callback_function = new Function(_sExtFun);		
		callback_function();
	}
	
	/**
	 * @method
	 * @description 동적 html 생성 시 datepicker 사용
	 * @author 홍길동
	 * @param
	 */
	fn_obj_date_datepicker = function(_sFormId) {
		
		$("#"+_sFormId).find("input[type='text']").each( function(){
			var input_cs  = $('#'+_sFormId).find($(this)).getClasses();
			var input_nm  = $('#'+_sFormId).find($(this)).attr('id');

//			alert("input_cs : " + input_cs + "\ninput_nm : " + input_nm);
			
			if (input_cs.indexOf("datepicker") > -1) {
				// 동적 html 생성 시 datepicker 사용
				$(document).find("input[name="+input_nm+"]").removeClass('hasDatepicker').datepicker();
			}

			if (input_cs.indexOf("monthPicker") > -1) {
				
				// 동적 html 생성 시 month datepicker 사용				
				$(".monthPicker").datepicker({
					closeText: '닫기',
					currentText: '적용',
			        dateFormat: 'yymm',
			        changeMonth: true,
			        changeYear: true,
			        showButtonPanel: true,
			 
			        onClose: function(dateText, inst) {			            
//			            var year  = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
//			            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
//		            	$(this).val($.datepicker.formatDate('yymm', new Date(year, month, 1)));			            
			        }			        

/*			        
			        onChangeMonthYear : function(year, month, inst) {
			        	alert("year : " + year + "\nmonth : " + month + "\ninst : " + inst);
			        	$(this).val($.datepicker.formatDate('yymm', new Date(year, month, -1)));
			        }
*/
			    });				
				
				// 적용버튼 클릭시
				jQuery.datepicker._gotoToday = function(id) {
		        	
		        	var target = jQuery(id);
		        	var inst = this._getInst(target[0]);		        	
		        	
					var year  = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();

			        $("#"+inst.id).val($.datepicker.formatDate('yymm', new Date(year, month, 1)));			        
			        this._hideDatepicker();
			        
		        };
				
			    $(".monthPicker").focus(function () {				
			        $(".ui-datepicker-calendar").hide();
			        $("#ui-datepicker-div").position({
			            my: "center top",
			            at: "center bottom",
			            of: $(this)
			        });
			    });

			}
		});
	}
	
	/**
	 * @method
	 * @description 특수문자 변환
	 * @author 홍길동
	 * @param
	 */
	fn_replaceAll = function(_sStr, _sSearchStr, _sReplaceStr) {
		while (_sStr.indexOf(_sSearchStr) != -1) {
			_sStr = _sStr.replace(_sSearchStr, _sReplaceStr);
		}
		return _sStr;
	}
	
	/**
	 * @method
	 * @description radio 버튼 클릭시
	 * @author 홍길동
	 * @param
	 */
	fn_radio_onclick = function(_sFormId, _sColId, _sScrinId, _sExtFun) {
		if (_sExtFun == "") {
			return;
		} else {
			var callback_function = new Function(_sExtFun);		
			callback_function();
			return;
		}
	}
	
	/**
	 * @method
	 * @description input text 입력 이벤트
	 * @author 홍길동
	 * @param
	 */
	fn_text_chanage = function(_sFormId, _sExtFun) {
		//alert("ddd");
		if (_sExtFun == undefined) return;
		//alert("aaa");
		var callback_function = new Function(_sExtFun);		
		callback_function();
		return;
	}	
	
	/**
	 * @method
	 * @description 사용자관리 화면 open load
	 * @author 홍길동
	 * @param
	 */
	fn_cust_init = function() {
		
		$("#frm_detail").find("#extFun").val("COMMON.fn_cust_init_detail()");		
	}
	
	/**
	 * @method
	 * @description 사용자관리 상세화면 open load
	 * @author 홍길동
	 * @param
	 */
	fn_cust_init_detail = function() {
		
		var sDetailFormId = "frm_detail";
		var actionFlag    = $("#"+sDetailFormId).find("#actionFlag").val();
		
		// 등록
		if (actionFlag == "INSERT") {
			
			$("#lab_pass_chg").hide();    // 암호변경 체크박스 hide
			$("#btn_pass_vrify").hide();  // 암호검증 버튼 hide
		} else if (actionFlag == "UPDATE") {  // 수정
			
			$("#lab_pass_chg").show();    // 암호변경 체크박스 show
			$("#btn_pass_vrify").show();  // 암호검증 버튼 show
			
			var sPass = $("#"+sDetailFormId).find("#pass").val();
			
			$("#"+sDetailFormId).find("#org_pass").remove();
			$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:sPass}).appendTo("#"+sDetailFormId);
			
			COMMON.fn_pass_chg_yn(sDetailFormId);
		}
	}
	
	/**
	 * @method
	 * @description 비밀번호변경 체크 시
	 * @author 홍길동
	 * @param
	 */
	fn_pass_chg_yn = function(_sDetailFormId) {
		
		var sVal  = $("#"+_sDetailFormId).find(":checkbox[name='pass_chg']:checked").val();
		var sPass = $("#"+_sDetailFormId).find("#pass").val();
		var sOrgPass = $("#"+_sDetailFormId).find("#org_pass").val();
		if (sOrgPass == "" || sOrgPass == "undefined"  ) sOrgPass=sPass;
		
		// 비밀번호 수정 가능
		if (sVal == "Y") {
			
			$("#"+_sDetailFormId).find("#pass").val("");
			$("#"+_sDetailFormId).find("#pass").prop("disabled", false);
			
			$("#"+_sDetailFormId).find("#org_pass").remove();
			$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:sPass}).appendTo("#"+_sDetailFormId);
			
			$("#btn_pass_vrify").show();  // 비밀번호검증 버튼 show (추후 비밀번호검증 기능 사용시 주석 제거)
			
		} else {  // 비밀번호 수정 블가능			
			
		//	$("#"+_sDetailFormId).find("#pass").prop("disabled", true);
			
			//var sOrgPass = $("#"+_sDetailFormId).find("#org_pass").val();
			$("#"+_sDetailFormId).find("#pass").val(sOrgPass);
			
		//	$("#btn_pass_vrify").hide();  // 암호검증 버튼 hide
		}
		//alert('sOrgPass='+sOrgPass + '  newpass ='+sPass );
	}
	
	/**
	 * @method
	 * @description 비밀번호 검증 버튼 클릭시
	 * @author 홍길동
	 * @param
	 */
	fn_pass_vrify = function() {
		var sDetailFormId = "frm_detail";

		var sUserId = $("#"+sDetailFormId).find("#user_id").val();
		var sPass   = $("#"+sDetailFormId).find("#pass").val();
		
		if (sPass == "") {
			$.unibillDialog.alert('알림', '검증 할 변경 비밀번호를 입력해 주십시오.');
			return false;
		}
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getPassVrify.json',
			data: 'pass=' + sPass + '&user_id=' + sUserId,
			datatype : "json",
			success : function(data) {

				if (data.result == "false") {
					$.unibillDialog.alert('알림', data.msg);
				} else {
					$.unibillDialog.alert('알림', '검증 되었습니다.');
				}
				
				$("#"+sDetailFormId).find("#pass_vrify_yn").remove();
				$('<input/>').attr({type:'hidden',id:'pass_vrify_yn',name:'pass_vrify_yn',value:data.result}).appendTo("#"+sDetailFormId);				
			},
			error: function(xhr, status, error) {				
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
	}
	
	/**
	 * @method
	 * @description 사용자관리 저장 버튼 클릭시
	 * @author 홍길동
	 * @param
	 */
	fn_user_save = function() {		
		COMMON.fn_user_pass_encpt();
		//fn_btn_save('COMMON.fn_user_pass_encpt()');
	}

	/**
	 * @method
	 * @description 사용자관리 저장 시 비밀번호 암호화
	 * @author 홍길동
	 * @param
	 */
	fn_user_pass_encpt = function() {

		var sDetailFormId = "frm_detail";
		var actionFlag    = $("#"+sDetailFormId).find("#actionFlag").val();
		var checkobj 	  = $("#"+sDetailFormId).find("#pass_chg").length;
		var sVal          = $("#"+sDetailFormId).find(":checkbox[name='pass_chg']:checked").val();
		var sPass         = $("#"+sDetailFormId).find("#pass").val();
		var sPassVrifyYn  = $("#"+sDetailFormId).find("#pass_vrify_yn").val();

		// 두번째 저장 후 처리 함수
		$("#"+sDetailFormId).find("#extCallBackFunction").remove();
		$('<input/>').attr({type:'hidden',id:'extCallBackFunction',name:'extCallBackFunction',value:'COMMON.fn_user_save_callback()'}).appendTo("#"+sDetailFormId);
		
		// 비밀번호검증
		if (actionFlag == "UPDATE" || actionFlag == "INSERT" ) {
			/*
			if (sPass != ""  && sVal == "Y"   && sPassVrifyYn != "true") {
				$.unibillDialog.alert('알림', '비밀번호 검증을 해주시기 바랍니다.');   //  (추후 비밀번호검증 기능 사용시 주석 제거)
				return false;
			}
*/
			fn_btn_save();
			$("#"+sDetailFormId).find("#pass_vrify_yn").remove(); 
		}
	  else{
			$("#"+sDetailFormId).find("#pass").val(sPass);
			$("#"+sDetailFormId).find("#org_pass").remove();
			$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:sPass}).appendTo("#"+sDetailFormId);
			$("#"+sDetailFormId).find("#pass").prop("disabled", false);
		} 
	}
	
	
	
	org_fn_user_pass_encpt = function() {

		var sDetailFormId = "frm_detail";
		var actionFlag    = $("#"+sDetailFormId).find("#actionFlag").val();
		var checkobj 	  = $("#"+sDetailFormId).find("#pass_chg").length;
		var sVal          = $("#"+sDetailFormId).find(":checkbox[name='pass_chg']:checked").val();
		var sPass         = $("#"+sDetailFormId).find("#pass").val();
		var sPassVrifyYn  = $("#"+sDetailFormId).find("#pass_vrify_yn").val();

		// 두번째 저장 후 처리 함수
		$("#"+sDetailFormId).find("#extCallBackFunction").remove();
		$('<input/>').attr({type:'hidden',id:'extCallBackFunction',name:'extCallBackFunction',value:'COMMON.fn_user_save_callback()'}).appendTo("#"+sDetailFormId);
		
		// 수정
		if (actionFlag == "UPDATE") {
			if (sPass != "" && sVal == "Y" && sPassVrifyYn != "true") {
				$.unibillDialog.alert('알림', '비밀번호 검증을 해주시기 바랍니다.');   //  (추후 비밀번호검증 기능 사용시 주석 제거)
				return false;
			}
			
			if (sVal == "Y") {

				$.ajax({
					async: false,
					type: 'POST',
					url: CONTEXT_ROOT + '/content/getPassEncpt.json',
					data: 'pass=' + sPass,
					datatype : "json",
					success : function(data) {
						var new_pass = $("#"+sDetailFormId).find("#pass").val();
						
						$("#"+sDetailFormId).find("#pass").val(data.encptData);
						
						$("#"+sDetailFormId).find("#org_pass").remove();
						$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:data.encptData}).appendTo("#"+sDetailFormId);

						$("#"+sDetailFormId).find("#pass").prop("disabled", false);
						
					},
					error: function(xhr, status, error) {				
						$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
					}

				});
			}
			fn_btn_save();
			$("#"+sDetailFormId).find("#pass_vrify_yn").remove();
		} else if (actionFlag == "INSERT") {
			if (checkobj == 0 || sVal == "Y") {
				$.ajax({
					async: false,
					type: 'POST',
					url: CONTEXT_ROOT + '/content/getPassEncpt.json',
					data: 'pass=' + sPass,
					datatype : "json",
					success : function(data) {
						var new_pass = $("#"+sDetailFormId).find("#pass").val();
						$("#"+sDetailFormId).find("#pass").val(data.encptData);
						$("#"+sDetailFormId).find("#org_pass").remove();
						$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:data.encptData}).appendTo("#"+sDetailFormId);
						$("#"+sDetailFormId).find("#pass").prop("disabled", false);
						
						fn_btn_save();
						if($("#"+sDetailFormId).find("#pass_vrify_yn").length > 0){
							$("#"+sDetailFormId).find("#pass_vrify_yn").remove();
						}
						
					},
					error: function(xhr, status, error) {				
						$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
					}

				});
			}else{
				$("#"+sDetailFormId).find("#pass").val(sPass);
				$("#"+sDetailFormId).find("#org_pass").remove();
				$('<input/>').attr({type:'hidden',id:'org_pass',name:'org_pass',value:sPass}).appendTo("#"+sDetailFormId);
				$("#"+sDetailFormId).find("#pass").prop("disabled", false);
			}
		}	
	
	}
	
	
	/**
	 * @method
	 * @description 사용자관리 저장 후 처리
	 * @author 홍길동
	 * @param
	 */
	fn_user_save_callback = function() {
		var sDetailFormId = "frm_detail";

		$("#"+sDetailFormId).find("#pass").prop("disabled", true);    // 암호입력 폼 비활성화
		$("#lab_pass_chg").show();                                    // 암호변경 체크박스 show
		$('input:checkbox[name="pass_chg"]').prop("checked", false);  // 암호변경 체크 해제
	}
	
	/**
	 * @method
	 * @description 검색 폼 디폴트 값 세팅
	 * @author 홍길동
	 * @param
	 */
	fn_form_default_set = function(_sFormId) {
		$("#"+_sFormId).find("input[type='hidden'], input[type='text'],input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select, textarea").each( function(){
			var input_cs  = $('#'+_sFormId).find($(this)).getClasses();
			var input_nm  = $('#'+_sFormId).find($(this)).attr('id');
			var input_objnm  = $('#'+_sFormId).find($(this)).attr('name');
		    var scrin_code = $("#"+_sFormId).find("#scrin_code").val();

		    //사용자관리에서 이용고객번호가 등록 되어 있고 로그레벨이 9이상이면 고객ID 세팅
		    if(input_nm == "rep_cust_id" && $("#topForm").find("#rep_cust_id").val() !="" && $("#topForm").find("#roleLevel").val()*1 > 8){
		    	 $("#"+_sFormId).find("#"+input_nm).val($("#topForm").find("#rep_cust_id").val()) ;
		    	 $("#"+_sFormId).find("#"+input_nm).prop("readonly",true);
		    }
		    //사용자관리에서 조직이 등록 되어 있고 로그레벨이 9이상이면 grp_cd 세팅
		    if(input_nm == "grp_cd" && $("#topForm").find("#grp_cd").val() !="" && $("#topForm").find("#roleLevel").val()*1 > 8){
		    	 $("#"+_sFormId).find("#"+input_nm).val($("#topForm").find("#grp_cd").val()) ;
		    	 $("#"+_sFormId).find("#"+input_nm).prop("readonly",true);
		    }//사용자관리에서 조직이 등록 되어 있고 로그레벨이 9이상이면 bothgrp_cd 세팅
		    if(input_nm == "bothgrp_cd" && $("#topForm").find("#grp_cd").val() !="" && $("#topForm").find("#roleLevel").val()*1 > 8){
		    	 $("#"+_sFormId).find("#"+input_nm).val($("#topForm").find("#grp_cd").val()) ;
		    	 $("#"+_sFormId).find("#"+input_nm).prop("readonly",true);
		    }
		    
			
		    // 일자
			if (input_cs.indexOf("select") > -1) {  
				//alert(input_nm); 
				//특정 오브젝트 날짜 제거
				if(input_nm.indexOf  ("find_day") >-1) { 
					//alert(input_nm); 
					$("#"+_sFormId).find("#"+input_nm).val="1" ; //pjh
				} 
			}
			
			// 일자
			if (input_cs.indexOf("datepicker") > -1) { 
				$("#"+_sFormId).find("#"+input_nm).val(COMMON.fn_get_now_date());  
				//alert(input_nm); 
				//특정 오브젝트 날짜 제거
				if(input_nm.indexOf  ("alm_day_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("alm_day1_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("alm_day2_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("fr_dt_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("sday_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("eday_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("sday2_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("eday2_datepicker") >-1) { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("from_find_day") >-1 && scrin_code =="S0103") { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("to_find_day") >-1 && scrin_code =="S0103") { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
				if(input_nm.indexOf  ("retdt") >-1 && scrin_code =="S0138") { 
					$("#"+_sFormId).find("#"+input_nm).val("") ;
				}
			}
			
		});
	}
	
	fn_ajaxForm = function(url, form, result, async){
		
		form.ajaxSubmit({
			url : url,
			type : "post",
			dataType : "json",
			traditional: true,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : result,
			async : async,
			beforeSend : function(xhr) {
				
			},
			complete : function(xhr,status) {
				
			},
			error : function(xhr, status, err) {
				//ajaxError(xhr, status, err)
				alert('error')
			}
		});
	}
	
	fn_ajaxJson = function(url, jsonData, result, async){
		
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data : JSON.stringify(jsonData),
			traditional: true,
			contentType : "application/json; charset=utf-8",
			success : result,
			async: async,
			beforeSend : function(xhr) {
			
			},
			complete : function(xhr,status) {
			},
			error : function(xhr, status, err) {
				//ajaxError(xhr, status, err, "post")
				alert('error\n' + err);
			}
		});
	}
	
	fn_ajaxQueryString = function(url, data, result, async, block) {
		//var param  = {};
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			traditional: true,
			data : data,
			success : result,
			async: async,
			beforeSend : function(xhr) {
				
			},
			complete : function(xhr,status) {
				
			},
			error : function(xhr, status, err) {
				//ajaxError(xhr, status, err, "post")
				alert('error\n' + err);
			}
		});
	}
	
	fn_collect_search = function(){
		$(".srh_wrap").find("#collstatus").selectBox('value', "1");
		
		
	}
	
	fn_openPassChange_pop = function(){
		 $("#passChange_pop").find("#oldPass").val("");
		 $("#passChange_pop").find("#newPass").val("");
		 $("#passChange_pop").find("#newPass2").val("");
		 $("#passChange_pop").css({
             "top": (($(window).height()-$("#popupDiv").outerHeight())/4+$(window).scrollTop())+"px",
             "left": (($(window).width()-$("#popupDiv").outerWidth())/3+$(window).scrollLeft())+"px"
             //팝업창을 가운데로 띄우기 위해 현재 화면의 가운데 값과 스크롤 값을 계산하여 팝업창 CSS 설정
          }); 
		 $('#passChange_pop').show();
		$.ajax({
			type: 'POST',
			url : CONTEXT_ROOT + '/main/passHisUsecheck.json',
			datatype : "json",
			success : function(data) {				
				if(data.passHisUse == "hisNoUse") $('#passChange_pop').find('#hisConfirm').hide();
				else $('#hisConfirm').show();	
			},
			error: function(xhr, status, error) {
				alert("err");
				
				
			}

		});	
		
		
		
	}
	
	fn_cdr_detail_view = function(smonth, sCdrseq){
		var url = CONTEXT_ROOT + "/content/selectCdrDetail.do";
		
		var tx = ($(window).width()-$("#cdr_pop .confirm_cont").width())/8;
		var ty = ($(window).height())/5;
		if(ty < 0){
			ty = 15;
		}
		var dlgWidth = 1450;
		var dlgHeight = 700;
		
		var win = window.open('', 'CDR_POPUP', "left=" + tx + ",top=" + ty + ",width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=0,status=1, location=1");
		win.focus();
		
		var $form = $('<form></form>');
		$form.attr('action', url);
		$form.attr('method', 'post');
		$form.attr('target', 'CDR_POPUP');
		$form.appendTo('body');
		     
		var ismonth = $('<input type="hidden" value="' + smonth + '" name="smonth">');
		var iscdrseq = $('<input type="hidden" value="'+ sCdrseq + '" name="scdrseq">');
		$form.append(ismonth).append(iscdrseq);
		
		$form.submit();
	

	}
	
	fn_cdr_detail_search = function(smonth, sCdrseq){
		var url = CONTEXT_ROOT + "/content/selectCdrDetail.do";
		
		if(smonth == "" || sCdrseq == ""){alert('조회월과 CDR순번을 입력해주세요'); return;}
		
		var $form = $('<form></form>');
		$form.attr('action', url);
		$form.attr('method', 'post');
		$form.attr('target', 'CDR_POPUP');
		$form.appendTo('body');
		     
		var ismonth = $('<input type="hidden" value="' + smonth + '" name="smonth">');
		var iscdrseq = $('<input type="hidden" value="'+ sCdrseq + '" name="scdrseq">');
		
		
		$form.append(ismonth).append(iscdrseq);
		
		$form.submit();
		
	}
	
	fn_cdr_detail_recdr = function(smonth, sCdrseq){
		var url = CONTEXT_ROOT + "/content/recdr.do";
		
		if(smonth == "" || sCdrseq == ""){alert('조회월과 CDR순번을 입력해주세요'); return;}
		
		var $form = $('<form></form>');
		$form.attr('action', url);
		$form.attr('method', 'post');
		$form.attr('target', 'CDR_POPUP');
		$form.appendTo('body');
		     
		var ismonth = $('<input type="hidden" value="' + smonth + '" name="smonth">');
		var iscdrseq = $('<input type="hidden" value="'+ sCdrseq + '" name="scdrseq">');
		
		
		$form.append(ismonth).append(iscdrseq);
		
		$form.submit();
		
	}
	
	
	
	fn_monitor_pop = function(){
		var url = CONTEXT_ROOT + "/monitor/selectMonitor.do";
		test = screen.height*1 -100
		
		var w = screen.availWidth -15; 
		var h = screen.availHeight - 70;
		
		var sh = screen.height;
		
		
		//$(".mwarp").css("height",900);
		
		var dlgWidth = 1900;
		var dlgHeight = 1010;
		//var win = window.open('', 'MONITOR_POPUP', 'height='+screen.height+', width='+screen.width + ",fullscreen=yes");
		//var win = window.open('','MONITOR_POPUP','height=' + screen.height + ',width=' + screen.width + ',toolbar=no, menubar=no, scrollbars=no, status=no, resizable=no, fullscreen=yes');
		var win = window.open('','MONITOR_POPUP', 'height=' + h + ', width=' + w + ', left=0, top=0, toolbar=no, menubar=no, scrollbars=1, status=no, resizable=yes, location=yes');
		//var win = window.open('','MONITOR_POPUP', 'height=500, width=500 , scrollbars=1');
		//win.focus();
		var $form = $('<form></form>');
		$form.attr('action', url);
		$form.attr('method', 'post');
		$form.attr('target', 'MONITOR_POPUP');
		$form.appendTo('body');
		
		$form.submit();
		
		
	}
	
	//원본파일 상세팝업
	fn_raw_detail_view = function(billYm, sheetSn, rowno){
		var url = CONTEXT_ROOT + "/content/selectRawDetail.do";
		
		var tx = ($(window).width()-$("#raw_pop .confirm_cont").width())/8;
		var ty = ($(window).height())/5;
		if(ty < 0){
			ty = 15;
		}
		var dlgWidth = 1450;
		var dlgHeight = 700;
		
		var win = window.open('', 'RAW_POPUP', "left=" + tx + ",top=" + ty + ",width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=0,status=1, location=1");
		win.focus();
		
		var $form = $('<form></form>');
		$form.attr('action', url);
		$form.attr('method', 'post');
		$form.attr('target', 'RAW_POPUP');
		$form.appendTo('body');
		     
		var isbillYm = $('<input type="hidden" value="' + billYm + '" name="billYm">');
		var issheetSn = $('<input type="hidden" value="'+ sheetSn + '" name="sheetSn">');
		var isRowno =  $('<input type="hidden" value="'+ rowno + '" name="rowno">');
		$form.append(isbillYm).append(issheetSn).append(isRowno);
		
		$form.submit();
	

	}
	
	// 그리드 제거
	fn_gridRemove = function(_sGridId){
		$('#'+_sGridId).jqGrid("GridUnload");
	}
	
	
	fn_variableMainGridMake = function(sMenuCode, sFormId, sDetailFormId, sTabFormId, sGridMainId, sGridMainPager, sGridHg, sTptype, sColTotWidth, sDblclickYn, sDblclickFun, sClickFun, sMainGridSelYn){
		//alert(sMenuCode + " : " + sFormId + " : " + sDetailFormId + " : " + sTabFormId + " : " + sGridMainId + " : " + sGridMainPager + " : " + sGridHg + " : " + sTptype + " : " + sColTotWidth + " : " + sDblclickYn + " : " + sDblclickFun + " : " + sClickFun + " : " + sMainGridSelYn);
		var sColNames  = [];
		var sColModels = [];
		
		// 가변 컬럼 정보 조회
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getVariableHDColumnList.json',
			processData: false,
			contentType: false, 
			data: COMMON.fn_get_dataform(sFormId),
			async: false,
			success : function(data) {
				//  그리드에 들어갈 컬럼이름 설정
				for(var i=0; i<data.collist.length; i++){
					sColNames.push(data.collist[i].colNm);
					sColModels.push({name:data.collist[i].colId, index:data.collist[i].colId,	jsonmap:data.collist[i].colCamelId,  align:'center',   width:100});
					sColTotWidth = sColTotWidth + (150*data.collist.length);
				}
			},
			error: function(xhr, status, error) { }
		});
		
		//그리드 생성
		CONTENT.fn_mainGridMake(sMenuCode, sFormId, sDetailFormId, sTabFormId, sGridMainId, sGridMainPager, sColNames, sColModels, sGridHg, sTptype, sColTotWidth, sDblclickYn, sDblclickFun, sClickFun, sMainGridSelYn);
		//그리드 조회
		COMMON.fn_search_mainGrid(sFormId, sGridMainId);
		
	}
	
	
	fn_obj_update_reset = function(_sFn){
		if($("#frm_detail").find("#doc_no").length > 0){
			// 팝업 show
			var tx = ($(window).width() -  $("#ly_userlock_pop .confirm_cont").innerWidth()) / 2;
			var ty = ($(window).height() - $("#ly_userlock_pop .confirm_cont").innerHeight()) / 2;
			if(ty < 0){
				ty = 15;
			}
			$("#ly_userlock_pop .confirm_cont").find("#docno").val("");
			$("#ly_userlock_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
			$("#ly_userlock_pop").show();
		}else{
			fn_district_reset();
		}
		
		
		
	}
	
	fn_district_reset = function(){
		var sDetailFormId= "frm_detail";
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 필수항목 체크 없음 / KEY 컬럼 수정 여부에 따르지 않고 무조건 수정 모드 셋팅			
		$("#"+sDetailFormId).find("#actionFlag").val("UPDATE");
		
		var msg = '수정하시겠습니까?';
		var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
		
		$.unibillDialog.confirm('확인', msg, 
		    function (isTrue) {
				try {
		            // 처리중 메시지 show
					$("#id_loader").show();	 
					COMMON.fn_scrin_block_ui("B");
					COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", CONTEXT_ROOT + "/content/derestrict.do", "COMMON.fn_reset_callback_content");
				} catch (E) {
					$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
				}
			}
		);
	}

	fn_obj_update_notuse = function(_sFn){
		var sDetailFormId= "frm_detail";
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		if (dtlRadioVal == undefined) dtlRadioVal = "I";
		// 개별등록일 경우
		if (dtlRadioVal == "I") {
			// 필수항목 체크 없음 / KEY 컬럼 수정 여부에 따르지 않고 무조건 수정 모드 셋팅			
			$("#"+sDetailFormId).find("#actionFlag").val("UPDATE");
			
			var msg = '미사용설정 하시겠습니까?';
			var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
			            // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");
						COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", CONTEXT_ROOT + "/content/notuse.do", "COMMON.fn_notuse_callback_content");
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);
		
			// 일괄등록일 경우
		} else if (dtlRadioVal == "E") {
			$.unibillDialog.alert('알림', '일괄업로드는 구현이 되어 있지 않습니다.');
		}
		
	}
	
	function fn_reset_callback_content(_sOID){
		// 저장 후 처리	 
		try {
			var strSpl          = _sOID.split("|");
			var scrin_id        = strSpl[0];			
			var sGridId         = sGridMainId;
			var sFormId         = sFormId;
			var sCallBackFun    = strSpl[2];
			var sKeyId          = strSpl[3];
			var sExtCallBackFun = strSpl[4];
			var changeColumn 	= strSpl[5].split("@");
	
			// 현재 그리드 페이지 번호
			var sCurrentPage = $("#"+sGridId).getGridParam("page");

			// 현재 그리드 스크롤 위치
			var sPosition = $("#"+sGridId).closest(".ui-jqgrid-bdiv").scrollTop();

			// 그리드 reload
			if (sCallBackFun == "INSERT" || sCallBackFun == "UPDATE") {
				CONTENT.fn_autoGridPageMove(strSpl[1], sGridId, sFormId, sCurrentPage, sPosition,  1000);
			} else {
				COMMON.fn_search_mainGrid(sFormId, sGridId);
			}

			// ubseq
			$("#"+sDetailFormId).find("#ubseq").remove();
			$('<input/>').attr({type:'hidden',id:"ubseq",name:"ubseq",value:strSpl[1]}).appendTo("#"+sDetailFormId);

			// key 컬럼 input 생성
			if (sKeyId != "") {
				var sKeyArr = sKeyId.split("@");
				for (var i=0; i<sKeyArr.length; i++) {					
					var sVal = $("#"+sDetailFormId).find("#"+sKeyArr[i]).val();
					$("#"+sDetailFormId).find("#org_"+sKeyArr[i]).remove();
					$('<input/>').attr({type:'hidden',id:"org_"+sKeyArr[i],name:"org_"+sKeyArr[i],value:sVal}).appendTo("#"+sDetailFormId);
				}
			}
			
			
			$("#"+sDetailFormId).find("#failcnt").val(changeColumn[0]);
			$("#"+sDetailFormId).find("#lastlogin_dt").text(changeColumn[1]);
			
			// 두번째 저장 후 처리
			if (sExtCallBackFun != "") {				
				var callback_function = new Function(sExtCallBackFun);		
				callback_function();
			}
			
			$("#id_loader").hide();
			COMMON.fn_scrin_block_ui("UB");		

		} catch (E) {			
			$.unibillDialog.alert('오류', E);
		}
		
	}
	

	function fn_notuse_callback_content(_sOID){
		// 저장 후 처리	 
		try {
			var strSpl          = _sOID.split("|");
			var scrin_id        = strSpl[0];			
			var sGridId         = sGridMainId;
			var sFormId         = sFormId;
			var sCallBackFun    = strSpl[2];
			var sKeyId          = strSpl[3];
			var sExtCallBackFun = strSpl[4];
			var changeColumn 	= strSpl[5].split("@");
	
			// 현재 그리드 페이지 번호
			var sCurrentPage = $("#"+sGridId).getGridParam("page");

			// 현재 그리드 스크롤 위치
			var sPosition = $("#"+sGridId).closest(".ui-jqgrid-bdiv").scrollTop();

			// 그리드 reload
			if (sCallBackFun == "INSERT" || sCallBackFun == "UPDATE") {
				CONTENT.fn_autoGridPageMove(strSpl[1], sGridId, sFormId, sCurrentPage, sPosition,  1000);
			} else {
				COMMON.fn_search_mainGrid(sFormId, sGridId);
			}

			// ubseq
			$("#"+sDetailFormId).find("#ubseq").remove();
			$('<input/>').attr({type:'hidden',id:"ubseq",name:"ubseq",value:strSpl[1]}).appendTo("#"+sDetailFormId);

			// key 컬럼 input 생성
			if (sKeyId != "") {
				var sKeyArr = sKeyId.split("@");
				for (var i=0; i<sKeyArr.length; i++) {					
					var sVal = $("#"+sDetailFormId).find("#"+sKeyArr[i]).val();
					$("#"+sDetailFormId).find("#org_"+sKeyArr[i]).remove();
					$('<input/>').attr({type:'hidden',id:"org_"+sKeyArr[i],name:"org_"+sKeyArr[i],value:sVal}).appendTo("#"+sDetailFormId);
				}
			}
			
			
			$("#"+sDetailFormId).find("#notuse_dt").val(changeColumn[0]);
			
			// 두번째 저장 후 처리
			if (sExtCallBackFun != "") {				
				var callback_function = new Function(sExtCallBackFun);		
				callback_function();
			}

		} catch (E) {			
			$.unibillDialog.alert('오류', E);
		}
		
	}
	
	
	fn_execel_use_reason = function(rflag){
		if(rflag == "Y"){
			// 팝업 show
			var tx = ($(window).width() -  $("#ly_exreason_pop .confirm_cont").innerWidth()) / 2;
			var ty = ($(window).height() - $("#ly_exreason_pop .confirm_cont").innerHeight()) / 2;
			if(ty < 0){
				ty = 15;
			}
			$("#ly_exreason_pop .confirm_cont").find("#reason_msg").text("");
			$("#ly_exreason_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
			$("#ly_exreason_pop").show();
			
			
			return false;
		}else{
			return false;
		}
		
		
	} 
	 
	 /**
		* @method
		* @description 검색 버튼 클릭시
		* @author 홍길동(2016.10.04)
		* @param {string} _sFormId : 선택된 form ID
		*/
	 fn_docview = function() {
			var sFormId = "frm_main";
			
			// 검색 시 객체 필수 입력값 검증
			if (!COMMON.fn_form_obj_validate(sFormId)) return;
					
			var specs = "left=50,top=50,width=800,height=700";
			specs += ",toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes, location=no";
			
			//window.open("/delivery/board/UserAgreeDoc.do", "_blank", specs);
			window.open("/doc/guide.pdf", "_blank", specs);	
			 
			
		}
	 
	 fn_fileSearch =function(){
		 //hidden 초기화 
		 $("#frm_fileDetail").find("#filegrp").each( function(){
				$(this).remove();
		 });
		 $("#frm_fileDetail").find("#od_no").each( function(){
				$(this).remove();
		 });
		 $("#frm_fileDetail").find("#cont_seq").each( function(){
				$(this).remove();
		 });
		 
		 var scrincode = $("#frm_detail").find("#scrin_code").val();
		 $.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getFileGrp.json',
			data: 'scrin_code=' + scrincode,
			datatype : "json",
			success : function(data) {
				if(data.filegrp == "O"){
					//접수관리 화면 일때 검색조건
					$("#frm_fileDetail").find(".od").show();
					$("#frm_fileDetail").find(".cont").hide();
					
					//filegrp hidden
					var sHtml = '<input type="hidden" id="filegrp" name="filegrp" value="'+ data.filegrp +'"/>\n';
					sHtml += '<input type="hidden" id="od_no" name="od_no" value="'+ $("#frm_detail").find("#od_no").val() +'"/>\n';
			    	$("#frm_fileDetail").append(sHtml);
					
				}else if(data.filegrp == "C"){
					//접수관리 화면 일때 검색조건
					$("#frm_fileDetail").find(".od").hide();
					$("#frm_fileDetail").find(".cont").show();
					
					//filegrp hidden
					var sHtml = '<input type="hidden" id="filegrp" name="filegrp" value="'+ data.filegrp +'"/>\n';
					sHtml += '<input type="hidden" id="cont_seq" name="cont_seq" value="'+ $("#frm_detail").find("#cont_seq").val() +'"/>\n';
			    	$("#frm_fileDetail").append(sHtml);
				}else{
					
				}
			},
			error: function(xhr, status, error) {				
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}
		});
		 
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#file_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#file_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});
			
		//화면 초기화
		$("#frm_fileDetail").find("#upfilenm").val('');
		$("#fileSearchgrid").clearGridData();
			
		$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$("#frm_fileDetail").find(".search").show();
		$("#frm_fileDetail").find(".dsadd").show();
		$("#frm_fileDetail").find(".add").hide();
		$("#frm_fileDetail").find(".sadd").hide();
		
		$("#fileExecute").hide();
		$("#sfileExecute").hide();

		
		$("#file_pop").show();
		
		
		// 그리드 초기화
		COMMON.fn_clear_grid_data("fileSearchgrid");
		
		COMMON.fn_search_mainGrid("frm_fileDetail", "fileSearchgrid");
		
		var w = $("#file_pop .confirm_cont").width();
		var resizeW = w - 135;
		$("#fileSearchgrid").setGridWidth(resizeW);
		
		$('#gbox_fileSearchgrid').show();
		$('#gbox_fileUploadgrid').hide();
		
			
	 }
	 
	 fn_fileUpload = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#file_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#file_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});
		
		//화면 초기화
		$("#frm_fileDetail").find("#upfilenm").val('');
		
		$('#gbox_fileUploadgrid').show();
		$('#gbox_fileSearchgrid').hide();
		
		$(".search").hide();
		$(".sadd").hide();
		$(".add").show();
		$(".dsadd").show();
		$("#fileExecute").show();
		$("#sfileExecute").hide();
		
		
		$("#file_pop").show();
		
		var w = $("#file_pop .confirm_cont").width();
		var resizeW = w - 135;
		$("#fileUploadgrid").setGridWidth(resizeW);
		
		
		
		
	 }
	 
	 fn_sfileSearch =function(){
		 //hidden 초기화 
		 $("#frm_fileDetail").find("#filegrp").each( function(){
				$(this).remove();
		 });
		 $("#frm_fileDetail").find("#od_no").each( function(){
				$(this).remove();
		 });
		 $("#frm_fileDetail").find("#cont_seq").each( function(){
				$(this).remove();
		 });
		 $("#frm_fileDetail").find("#file_sn").each( function(){
				$(this).remove();
		 });
		 
		 var scrincode = $("#frm_detail").find("#scrin_code").val();
		 $.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getFileGrp.json',
			data: 'scrin_code=' + scrincode,
			datatype : "json",
			success : function(data) {
				if(data.filegrp == "O"){
					//접수관리 화면 일때 검색조건
					$("#frm_fileDetail").find(".od").show();
					$("#frm_fileDetail").find(".cont").hide();
					
					//filegrp hidden
					var sHtml = '<input type="hidden" id="filegrp" name="filegrp" value="'+ data.filegrp +'"/>\n';
					sHtml += '<input type="hidden" id="od_no" name="od_no" value="'+ $("#frm_detail").find("#od_no").val() +'"/>\n';
			    	$("#frm_fileDetail").append(sHtml);
					
				}else if(data.filegrp == "C"){
					//접수관리 화면 일때 검색조건
					$("#frm_fileDetail").find(".od").hide();
					$("#frm_fileDetail").find(".cont").show();
					
					//filegrp hidden
					var sHtml = '<input type="hidden" id="filegrp" name="filegrp" value="'+ data.filegrp +'"/>\n';
					sHtml += '<input type="hidden" id="cont_seq" name="cont_seq" value="'+ $("#frm_detail").find("#cont_seq").val() +'"/>\n';
			    	$("#frm_fileDetail").append(sHtml);
				}else if(data.filegrp == "N"){
					//filegrp hidden
					var sHtml = '<input type="hidden" id="filegrp" name="filegrp" value="'+ data.filegrp +'"/>\n';
					sHtml += '<input type="hidden" id="file_sn" name="file_sn" value="'+ $("#frm_detail").find("#file_sn").val() +'"/>\n';
			    	$("#frm_fileDetail").append(sHtml);
				}else{
					
				}
			},
			error: function(xhr, status, error) {				
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}
		});
		 
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#file_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#file_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});
			
		//화면 초기화
		$("#frm_fileDetail").find("#upfilenm").val('');
		$("#fileSearchgrid").clearGridData();
			
		$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$("#frm_fileDetail").find(".search").show();
		$("#frm_fileDetail").find(".dsadd").show();
		$("#frm_fileDetail").find(".add").hide();
		$("#frm_fileDetail").find(".sadd").hide();
		
		$("#fileExecute").hide();
		$("#sfileExecute").hide();
		
		$("#file_pop").show();
		
		// 그리드 초기화
		COMMON.fn_clear_grid_data("fileSearchgrid");
		
		COMMON.fn_search_mainGrid("frm_fileDetail", "fileSearchgrid");
		
		var w = $("#file_pop .confirm_cont").width();
		var resizeW = w - 135;
		$("#fileSearchgrid").setGridWidth(resizeW);
		
		$('#gbox_fileSearchgrid').show();
		$('#gbox_fileUploadgrid').hide();
		
			
	 }
	 
	 fn_sfileUpload = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#file_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#file_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});
		
		//화면 초기화
		$("#frm_fileDetail").find("#upsfilenm").val('');
		//첨부파일 초기화
		$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		
		$('#gbox_fileUploadgrid').show();
		$('#gbox_fileSearchgrid').hide();
		
		$(".search").hide();
		$(".sadd").show();
		$(".add").hide();
		$(".dsadd").hide();
		$("#fileExecute").hide();
		$("#sfileExecute").show();
		
		
		$("#file_pop").show();
		
		var w = $("#file_pop .confirm_cont").width();
		var resizeW = w - 135;
		$("#fileUploadgrid").setGridWidth(resizeW);
		
		
		
		
	 }
	 
	 fn_noticePopup = function(){
		 alert("pop");
		 /*var url = CONTEXT_ROOT + "/content/selectCdrDetail.do";
			
			var tx = ($(window).width()-$("#cdr_pop .confirm_cont").width())/8;
			var ty = ($(window).height())/5;
			if(ty < 0){
				ty = 15;
			}
			var dlgWidth = 1450;
			var dlgHeight = 700;
			
			var win = window.open('', 'CDR_POPUP', "left=" + tx + ",top=" + ty + ",width=" + dlgWidth + ",height=" + dlgHeight + ",toolbar=0,menubar=0,resizable=0, scrollbars=0,status=1, location=1");
			win.focus();
			
			var $form = $('<form></form>');
			$form.attr('action', url);
			$form.attr('method', 'post');
			$form.attr('target', 'CDR_POPUP');
			$form.appendTo('body');
			     
			var ismonth = $('<input type="hidden" value="' + smonth + '" name="smonth">');
			var iscdrseq = $('<input type="hidden" value="'+ sCdrseq + '" name="scdrseq">');
			$form.append(ismonth).append(iscdrseq);
			
			$form.submit();*/
	 }
	  
	 fn_getSelectMainGridData = function(_sFormId){
	      //alert("_sFormId : " +_sFormId);
		 	var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
	       if(rowIds.length > 1){
	          $.unibillDialog.alert('알림', "상단 그리드에 하나의 정보만 선택하세요.");
	          return;
	       }
	       var sRowData = $("#gridMain").getRowData(rowIds[0]);   
	        //컬럼명을 배열 형태로 가져오기
	        var colModel = $("#gridMain").jqGrid('getGridParam', 'colModel');
	        
	        for(var i=2; i<colModel.length; i++){
	          var col = colModel[i]['name']; 
	          var realval = sRowData[colModel[i]['name']];
	          var subval= "";
	          
	          if(col.indexOf("_nm") < 0 && col.indexOf("_yn") && col.indexOf("_cnt") < 0 && col.indexOf("_cost") < 0 && col.indexOf("_ym") < 0 && col.indexOf("_month") < 0 && col.indexOf("_amt") < 0){
	             if(realval.lastIndexOf("(") > 0){
	                 subval = realval.substring(realval.lastIndexOf("(")+1,realval.lastIndexOf(")"))
	              }else{
	                 subval = realval;
	              }
	             $("#"+_sFormId).find("#gr_"+col).remove(); //같은변수명 자료 누적분 삭제
	             $('<input/>').attr({type:'hidden',id:"gr_"+col,value:subval}).appendTo("#"+_sFormId);
	             //alert("column=val : " +  colModel[i]['name'] + " = " + sRowData[colModel[i]['name']] + ", relaval : " + subval + "indexof : " + col.indexOf("_nm"));
	          }
	          
	          
	           
	        }
	       
	    }
	    
	    fn_getSelectMainGridDataAjax = function(){
	       var formData = new FormData();
	       var rowIds = $("#gridMain").jqGrid("getGridParam","selarrrow");
	       if(rowIds.length > 1){
	          $.unibillDialog.alert('알림', "상단 그리드에 하나의 정보만 선택하세요.");
	          return;
	       }
	       var sRowData = $("#gridMain").getRowData(rowIds[0]);   
	        //컬럼명을 배열 형태로 가져오기
	        var colModel = $("#gridMain").jqGrid('getGridParam', 'colModel');
	        
	        for(var i=2; i<colModel.length; i++){
	          var col = "gr_"+colModel[i]['name']; 
	          var realval = sRowData[colModel[i]['name']];
	          var subval= "";
	          
	          if(col.indexOf("_nm") < 0 && col.indexOf("_yn") && col.indexOf("_cnt") < 0 && col.indexOf("_cost") < 0 && col.indexOf("_ym") < 0 && col.indexOf("_month") < 0 && col.indexOf("_amt") < 0){
	             if(realval.lastIndexOf("(") > 0){
	                 subval = realval.substring(realval.lastIndexOf("(")+1,realval.lastIndexOf(")"))
	              }else{
	                 subval = realval;
	              }
	             
	             formData.append(col, subval);
	             //$('<input/>').attr({type:'hidden',id:"subg_"+col,value:subval}).appendTo("#"+_sFormId);
	             if(col.lastIndexOf("cust_id") > 0 || false){
	            	// alert("column=oval : " +  colModel[i]['name'] + " = " + sRowData[colModel[i]['name']] + ", val : " + subval + " indexof : " + col.indexOf("_nm"));
	             }
	          }
	          
	          
	           
	        }
	        return formData;
	       
	    }
	    
	 fn_test = function(){
		 //alert("test");
		 var tt = 1+1;
	 }
	 
	 //파일순번 가져오기
	 fn_get_detailfilesn = function(tblName){
		 var data = "";
		 if(tblName == "mb_cfgsendmail"){
			 data = $("#frm_detail").find("#mail_sn").val();
		 }
		 
		 var sendData = {"data":data,"tblName":tblName};
		 var filesn = "";
		 
		 $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getFilesn.json',
			async: false ,
			data : sendData,
			success : function(data) {
				filesn = data.filesn;
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '파일번호를 가져오는데 실패하였습니다');
				return;
			}
		 });
		 return filesn;
	 }
	 
	 //파일이름 가져오기
	 fn_get_detailfilenm = function(tblName){
		 var data = "";
		 if(tblName == "mb_cfgsendmail"){
			 data = $("#frm_detail").find("#mail_sn").val();
		 }
		 
		 var sendData = {"data":data,"tblName":tblName};
		 var filenm = "";
		 
		 $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getFilenm.json',
			async: false ,
			data : sendData,
			success : function(data) {
				filenm = data.nm;
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '파일이름을 가져오는데 실패하였습니다');
				return;
			}
		 });
		 return filenm;
	 }
	 
	 //blob파일 가져오기
	 fn_get_detailfileblob = function(tblName,i){
		 var data = "";
		 if(tblName == "mb_cfgsendmail"){
			 data = $("#frm_detail").find("#mail_sn").val();
		 }
		 
		 var sendData = {"data":data,"tblName":tblName,"i":i};
		 
		 $.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/getFileblob.json',
			async: false ,
			data : sendData,
			success : function(data) {
				if(data.tf == true){
					COMMON.fn_download_file('cfileupload', data.nm,'Y');
				}
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '파일을 가져오는데 실패하였습니다');
				return;
			}
		 });
	 }
	 
	 //메일 보내기
	 fn_mail_send = function(){
		 var tblName = $("#frm_detail").find("#tableName").val();
		 var data = $("#frm_detail").find("#mail_sn").val();
		 var sendData = {"data":data,"tblName":tblName};
		 
		 $("#id_loader").show();	 
		 COMMON.fn_scrin_block_ui("B");
		 if(data == null || data == ""){
			 $.unibillDialog.alert('알림', '저장 후 선택해주세요');
			 $("#id_loader").hide();	
			 COMMON.fn_scrin_block_ui("UB");
			 return;
		 }else{
			 $.ajax({
					type: 'POST',
					url: CONTEXT_ROOT + '/content/mailSend.do',
					async: false ,
					data : sendData,
					success : function(data){
						if(data.tf == true){
							$.unibillDialog.alert('알림', '메일 전송을 성공하었습니다.');	
							$("#id_loader").hide();	
							COMMON.fn_scrin_block_ui("UB");
						}else if(data.tf == false){
							$.unibillDialog.alert('알림', '메일 전송을 실패하었습니다.');	
							$("#id_loader").hide();	
							COMMON.fn_scrin_block_ui("UB");
							return;
						}
					},
					error : function(data) {
						$.unibillDialog.alert('알림', '메일 전송을 실패하었습니다.');		
						$("#id_loader").hide();	
						COMMON.fn_scrin_block_ui("UB");
						return;
					}
			 });
		 }
	 }
	 
	 //삭제 버튼(실제 파일 삭제x)
	 fn_cfileRemove = function(obj,value){
		var dFilesn = $("#frm_detail").find("#dFilesn").val();
		if(dFilesn == null ||  dFilesn == ""){
			$("#frm_detail").find("#dFilesn").attr("value", value);
		}else{
			$("#frm_detail").find("#dFilesn").attr("value", dFilesn + "," + value)
		}
		obj.parent().remove();
 	}
	 
	 
	 /**
		 * @method
		 * @description 현재일자 (YYYY-MM-DD)
		 * @author 홍길동(2016.10.04)
		 */
	fn_get_now_date = function() {		
			var now  = new Date();
			var year = now.getFullYear();
		    var mon  = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
		    var day  = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
		              
		    return year + '-' + mon + '-' + day;
	}  
	 
	 
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_common.fn_search_mainGrid         		= fn_search_mainGrid;
	_mod_common.fn_reset_form              		= fn_reset_form;
	_mod_common.fn_search_main             		= fn_search_main;
	_mod_common.fn_excel_download          		= fn_excel_download
	_mod_common.fn_excel_download_sheet         = fn_excel_download_sheet
	_mod_common.fn_excel_download_template 		= fn_excel_download_template
	_mod_common.fn_excelTmpDownload_callback 	= fn_excelTmpDownload_callback
	_mod_common.fn_submit_createForm       		= fn_submit_createForm;
	_mod_common.fn_get_serialize           		= fn_get_serialize;
	_mod_common.fn_get_date                		= fn_get_date;
	_mod_common.fn_form_disabled           		= fn_form_disabled;
	_mod_common.fn_button_disabled         		= fn_button_disabled;
	_mod_common.fn_gridSelectButton        		= fn_gridSelectButton;
	_mod_common.fn_get_randomId            		= fn_get_randomId;
	_mod_common.fn_get_iframeBody          		= fn_get_iframeBody;
	_mod_common.fn_get_form                		= fn_get_form;	
	_mod_common.fn_view_content            		= fn_view_content;
	_mod_common.fn_view_content_scrin      		= fn_view_content_scrin;
	
	_mod_common.fn_get_form_id             		= fn_get_form_id;
	_mod_common.fn_chekExcept              		= fn_chekExcept;
	_mod_common.fn_eraser                  		= fn_eraser;
	_mod_common.fn_isCheckedById           		= fn_isCheckedById;
	_mod_common.fn_menu_click              		= fn_menu_click;
	_mod_common.fn_form_submit             		= fn_form_submit;
	_mod_common.fn_form_load               		= fn_form_load;
	_mod_common.ttt               		   		= ttt;
	_mod_common.fn_bookmarkMake            		= fn_bookmarkMake;
	_mod_common.fn_bookmark_save           		= fn_bookmark_save;
	_mod_common.fn_tabMenu                 		= fn_tabMenu;
	_mod_common.fn_subGridInfo            	 	= fn_subGridInfo;	
	_mod_common.fn_subGridSearch           		= fn_subGridSearch;
	_mod_common.fn_get_detailFormObj       		= fn_get_detailFormObj;
	_mod_common.fn_formObjMake             		= fn_formObjMake;
	_mod_common.fn_formObjMake2            		= fn_formObjMake2;
	_mod_common.fn_makeForm                		= fn_makeForm;
	_mod_common.fn_set_menu                		= fn_set_menu;
	_mod_common.fn_formHtmlMake            		= fn_formHtmlMake;	
	_mod_common.fn_getCommaString          		= fn_getCommaString;
	_mod_common.fn_bookMarkPos             		= fn_bookMarkPos;
	_mod_common.fn_columnValidate          		= fn_columnValidate;
	_mod_common.fn_columnValidate2          	= fn_columnValidate2;
	_mod_common.fn_form_obj_validate       		= fn_form_obj_validate;
	_mod_common.fn_checkDateFomat          		= fn_checkDateFomat;
	_mod_common.fn_checkMonthFomat          	= fn_checkMonthFomat;
	_mod_common.fn_com_click               		= fn_com_click;
	_mod_common.fn_auto_complete           		= fn_auto_complete;
	_mod_common.fn_obj_update              		= fn_obj_update;
	_mod_common.fn_obj_insert              		= fn_obj_insert;
	_mod_common.fn_obj_update_reset             = fn_obj_update_reset;
	_mod_common.fn_obj_update_notuse            = fn_obj_update_notuse;	
	_mod_common.fn_reset_callback_content		= fn_reset_callback_content;
	_mod_common.fn_notuse_callback_content		= fn_notuse_callback_content;
	_mod_common.fn_dtl_regist_type         		= fn_dtl_regist_type;
	_mod_common.fn_upload_file_check       		= fn_upload_file_check;
	_mod_common.fn_submit_fileForm         		= fn_submit_fileForm;
	_mod_common.fn_download_file           		= fn_download_file;
	_mod_common.fn_form_date_select        		= fn_form_date_select;
	_mod_common.fn_scrin_block_ui          		= fn_scrin_block_ui;
	_mod_common.fn_set_find_day            		= fn_set_find_day;
	_mod_common.fn_form_scrin_validate     		= fn_form_scrin_validate;	
	_mod_common.fn_clear_grid_data         		= fn_clear_grid_data;
	_mod_common.fn_selectbox_chanage       		= fn_selectbox_chanage;
	_mod_common.fn_bnde_applc              		= fn_bnde_applc;
	_mod_common.fn_bnde_app_detail         		= fn_bnde_app_detail;
	_mod_common.fn_applc_save              		= fn_applc_save;
	_mod_common.fn_save_callback_bndeApplc 		= fn_save_callback_bndeApplc;
	_mod_common.fn_det_selectbox_chanage   		= fn_det_selectbox_chanage;
	_mod_common.fn_obj_date_datepicker     		= fn_obj_date_datepicker;
	_mod_common.fn_replaceAll              		= fn_replaceAll; 
	_mod_common.fn_radio_onclick           		= fn_radio_onclick;
	_mod_common.fn_text_chanage            		= fn_text_chanage;
	_mod_common.fn_pass_chg_yn             		= fn_pass_chg_yn;
	_mod_common.fn_pass_vrify              		= fn_pass_vrify;
	_mod_common.fn_cust_init               		= fn_cust_init;
	_mod_common.fn_cust_init_detail        		= fn_cust_init_detail;
	_mod_common.fn_user_save               		= fn_user_save;
	_mod_common.fn_user_pass_encpt         		= fn_user_pass_encpt;
	_mod_common.fn_user_save_callback      		= fn_user_save_callback;
	_mod_common.fn_form_default_set        		= fn_form_default_set;
	_mod_common.fn_ajaxForm				   		= fn_ajaxForm;
	_mod_common.fn_ajaxJson 			   		= fn_ajaxJson;
	_mod_common.fn_ajaxQueryString 		   		= fn_ajaxQueryString;
	_mod_common.fn_collect_search		   		= fn_collect_search;
	_mod_common.fn_openPassChange_pop	   		= fn_openPassChange_pop;
	_mod_common.fn_cdr_detail_view		   		= fn_cdr_detail_view;
	_mod_common.fn_cdr_detail_search	   		= fn_cdr_detail_search;
	_mod_common.fn_monitor_pop			   		= fn_monitor_pop;
	_mod_common.fn_raw_detail_view		   		= fn_raw_detail_view;
	_mod_common.fn_cdr_detail_recdr				= fn_cdr_detail_recdr;
	_mod_common.fn_gridRemove					= fn_gridRemove;
	_mod_common.fn_variableMainGridMake			= fn_variableMainGridMake;
	_mod_common.fn_get_dataform					= fn_get_dataform;
	_mod_common.fn_execel_use_reason			= fn_execel_use_reason;
	_mod_common.fn_docview		            	= fn_docview;
	
	_mod_common.fn_fileUpload 					= fn_fileUpload;
	_mod_common.fn_fileSearch 					= fn_fileSearch;
	_mod_common.fn_sfileUpload 					= fn_sfileUpload;
	_mod_common.fn_sfileSearch 					= fn_sfileSearch;
	
	_mod_common.fn_test 						= fn_test;
	
	_mod_common.fn_noticePopup 					= fn_noticePopup;
	_mod_common.fn_getSelectMainGridDataAjax 	= fn_getSelectMainGridDataAjax;
	_mod_common.fn_getSelectMainGridData 	    = fn_getSelectMainGridData;
	
	_mod_common.fn_get_detailfilesn 			= fn_get_detailfilesn;
	_mod_common.fn_get_detailfilenm 			= fn_get_detailfilenm;
	_mod_common.fn_get_detailfileblob			= fn_get_detailfileblob;
	_mod_common.fn_cfileRemove					= fn_cfileRemove;
	
	_mod_common.fn_get_now_date 				= fn_get_now_date;
	
	_mod_common.fn_PreSavePost 					= fn_PreSavePost;
	_mod_common.fn_test1 						= fn_test1;
	_mod_common.fn_test2 						= fn_test2;
	return _mod_common;

}(UNI.COMMON || {}, jQuery));