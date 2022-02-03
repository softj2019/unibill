/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.JIRO = (function(_mod_jiro, $, undefined){

	/**
	 * @method
	 * @description 첨부파일 다운로드
	 * @author 이남기(2019.04.17)
	 */
	fn_jiro_file_download = function(_sScrinCode,_sGun, _sGridId, _sRowId) {		

		// 그리드 클릭시
		if (_sGun == "G") {
			var sRowData = $("#"+_sGridId).jqGrid("getRowData",_sRowId);
			   _sFileNm   = sRowData["file_nm"];
			   _sFileDir   = sRowData["file_dir"];	
			   _sCustId   = sRowData["cust_id"];	
		}
		
		try {
			
			$("#download_frm").find("#file_nm").remove();
			$("#download_frm").find("#file_dir").remove();
			$("#download_frm").find("#cust_id").remove();
			$("#download_frm").find("#scrin_id").remove();
			
			$('<input/>').attr({type:'hidden',id:'file_nm',name:'file_nm',value:_sFileNm}).appendTo("#download_frm");
			$('<input/>').attr({type:'hidden',id:'file_dir',name:'file_dir',value:_sFileDir}).appendTo("#download_frm");
			$('<input/>').attr({type:'hidden',id:'cust_id',name:'cust_id',value:_sCustId}).appendTo("#download_frm");
			$('<input/>').attr({type:'hidden',id:'scrin_id',name:'scrin_id',value:_sScrinCode}).appendTo("#download_frm");

			$("#download_frm").attr("action","/file/jiroFileDownload.do");
			$("#download_frm").attr("target","proc_frm");
			$("#download_frm").submit();

		}catch(E){
			$.unibillDialog.alert('에러', E);
		}	
	}
	
	/**
	* @method
	* @description 장표지로파일 등록
	* @author 이남기(2019.2.13)
	*/
	fn_btn_jirofile_regist = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#jiro_pop.confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#jiro_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#jiro_pop").find("#buttonFlag").val("R");
		$(".delSheet").hide();
		$(".del").show();
		$("#jiro_pop").show();  // 레이어 팝업 open
	
	}

	/**
	* @method
	* @description 출금이체 신청
	* @author 이남기(2019.2.13)
	*/
	fn_btn_ec22_regist= function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#cms_pop.confirm_cont").width())/5;
		var ty = ($(window).height())/2;

		if(ty < 0){
			ty = 15;
		}

		$("#cms_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});	
		$("#cms_pop").find("#buttonFlag").val("R");
		$(".delSheet").hide();
		$(".del").show();
		$("#cms_pop").show();  // 레이어 팝업 open
	
	}
	// ==============================================================================
	// =======================	통신사 파일 업로드 ==================================
	// ==============================================================================
	//등록 실행
	fn_excute_gr = function (){
		if($("#buttonFlag").val() == "R")
			JIRO.fn_regist_gr();
		if($("#buttonFlag").val() == "D")
			JIRO.fn_del();
	}

	//ec22 등록 실행
	fn_excute_ec22 = function (){
		if($("#buttonFlag").val() == "R")
			JIRO.fn_regist_ec22();
		if($("#buttonFlag").val() == "D")
			JIRO.fn_del();
	}

	// 지로파일등록
	fn_regist_gr = function (){
		if ($("#upfile").val() == "") {
			alert("업로드할 파일을 선택하여 주세요.");
			return false;
		}
		if(returnID == null){
			isRun = true;
			JIRO.fn_setRunning();
			
			$("#execute").hide();
			$("#jobmsg").text("지로파일 등록을 시작합니다.");
			$(".progressbar").css("width",0+"%");
			$(".progressbar").text(0 + "%");
			$("#error").html(0 + "/ <span class='t_red'>" + 0 + "</span>");
			
			JIRO.fn_jiroUpload();
			setTimeout("fn_setTime()",5000);
			
		}else{
			alert("현재 작업 중입니다.");
		}
	}

	// ec22파일등록
	fn_regist_ec22 = function (){
		if ($("#upfile").val() == "") {
			alert("업로드할 파일을 선택하여 주세요.");
			return false;
		}
		if(returnID == null){
			isRun = true;
			JIRO.fn_setRunning();
			
			$("#execute").hide();
			$("#jobmsg").text("출금이체결과처리 파일 등록을 시작합니다.");
			$(".progressbar").css("width",0+"%");
			$(".progressbar").text(0 + "%");
			$("#error").html(0 + "/ <span class='t_red'>" + 0 + "</span>");
			
			JIRO.fn_ec22_Upload();
			setTimeout("fn_setTime()",5000);
			
		}else{
			alert("현재 작업 중입니다.");
		}
	}

	//업로드 플레그
	fn_setRunning = function(){
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jiroFile/uploadFlag.do',
			processData: false,
			contentType: false, 
			data: "",
			async: false,
			success : function(data) { isRun = true;},
			error: function(xhr, status, error) { }
		});
	}
	
	// 지로파일 업로드
	fn_jiroUpload = function (){
		isRun = true;
		var formData = new FormData();
			
		$.each($('#file-upload')[0].files, function(i, file) {          
            formData.append('file-' + i, file);
		});
		var year = $("#pyear option:selected").val();
		var month = $("#pmonth option:selected").val();
		formData.append("billym" , year + month);
		
		formData.append("gubun" , $("[name='gubun']:checked").val());
		
		
		xhr = $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/jiroFile/jiroUpload.do',
				processData: false,
				contentType: false, 
				data: formData,
				success : function(data) { 
					isRun = false; 
				},
				error: function(xhr, status, error) { isRun = false;}
			});
		
	}

	// ec22파일 업로드
	fn_ec22_Upload = function (){
		isRun = true;
		var formData = new FormData();
			
		$.each($('#file-upload')[0].files, function(i, file) {          
            formData.append('file-' + i, file);
		});
		var year = $("#pyear option:selected").val();
		var month = $("#pmonth option:selected").val();
		formData.append("billym" , year + month);
		
		formData.append("gubun" , $("[name='gubun']:checked").val());
		
		
		xhr = $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/jiroFile/ec22Upload.do',
				processData: false,
				contentType: false, 
				data: formData,
				success : function(data) { 
					isRun = false; 
				},
				error: function(xhr, status, error) { isRun = false;}
			});
		
	}
	
	fn_setTime = function (){
		fn_checkRun();
		returnID = setInterval(fn_checkRun,5000);
	}
	
	// 엑셀 업로드 작업 상황 체크
	fn_checkRun = function(){
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/jiroFile/checkRun.do',
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
				url: CONTEXT_ROOT + '/jiroFile/stop.do',
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
	
	fn_jiro_detailList = function(){

		var rowid   = $("#gridMain").getGridParam("selrow");
		var data = jQuery("#gridMain").getRowData(rowid);
		var scrin_id=$("#frm_tab").find("#scrin_code").val();
		var sub_grid_id="gridSub_"+$("#frm_tab").find("#scrin_code").val();
		
		$("#frm_tab").find("#"+sub_grid_id).jqGrid("GridUnload");
		
		$("#frm_tab").find("#file_nm").remove();
		$('<input/>').attr({type:'hidden',id:'file_nm',name:'file_nm',value:data.file_nm}).appendTo("#frm_tab");
		
		COMMON.fn_tabMenu('gridMain','frm_main','frm_tab', scrin_id, rowid);
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_jiro.fn_btn_jirofile_regist     = fn_btn_jirofile_regist;
	_mod_jiro.fn_btn_ec22_regist      = fn_btn_ec22_regist;
	_mod_jiro.fn_regist_ec22             = fn_regist_ec22;
	_mod_jiro.fn_excute_gr 				= fn_excute_gr;
	_mod_jiro.fn_excute_ec22 				= fn_excute_ec22;
	_mod_jiro.fn_regist_gr 					= fn_regist_gr;
	_mod_jiro.fn_del 							= fn_del;
	_mod_jiro.fn_setRunning				= fn_setRunning;
	_mod_jiro.fn_jiroUpload				= fn_jiroUpload;
	_mod_jiro.fn_ec22_Upload			= fn_ec22_Upload;
	_mod_jiro.fn_setTime					= fn_setTime;
	_mod_jiro.fn_checkRun					= fn_checkRun;
	_mod_jiro.fn_stop						= fn_stop;
	_mod_jiro.fn_jiro_detailList			= fn_jiro_detailList;
	_mod_jiro.fn_jiro_file_download	= 	fn_jiro_file_download;

	
	return _mod_jiro;

}(UNI.JIRO || {}, jQuery));