<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%
  /**
  * @Class Name : contentList.jsp
  * @Description : 공통 컨텐츠 목록 화면
  * @Modification Information
  *
  *  수정일      수정자      수정내용
  *  ----------  --------    ---------------------------
  *  2016.10.04  홍길동      최초 생성
  *
  * author 한싹시스템
  * since 2016.10.04
  *
  * Copyright (C) 2016 by http://www.hanssak.co.kr  All right reserved.
  */
%> 
<link rel="stylesheet" type="text/css" href="../../css/zip_popup.css" />
<%
	String addrApiyn = (String)request.getAttribute("addrApiyn");
	if(addrApiyn == null || addrApiyn.equals("")){addrApiyn = "N";}
	if(addrApiyn.equals("Y")){
%>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%
	}
%>
<script type="text/javascript">

	var sFormId        = "frm_main";       // form id
	var sDetailFormId  = "frm_detail";     // 상세 form id
	var sTabFormId     = "frm_tab";        // tab form id
	var sGridMainId    = "gridMain";       // 상단 grid id
	var sGridMainPager = "gridMainPager";  // 상단 grid page id	
	var sGridSubId     = "gridSub";        // 하단 grid id
	var sGridSubPager  = "gridSubPager";   // 하단 grid page id

	var sTptype        = '<c:out value="${tptype}"/>';      // 화면 테플릿 타입
	var sDtlTpType     = '<c:out value="${dtlTmpType}"/>';  // 상세 화면 테플릿 타입
	var sScrinCode     = '<c:out value="${scrin_code}"/>';  // 화면ID
//	var chkcell={cellId:undefined, chkval:undefined}; //cell rowspan 중복 체크

	var camelCase = (function () {
	    var DEFAULT_REGEX = /[-_]+(.)?/g;
	
	    function toUpper(match, group1) {
	        return group1 ? group1.toUpperCase() : '';
	    }
	
	    return function (str, delimiters) {
	        return str.replace(delimiters ? new RegExp('[' + delimiters + ']+(.)', 'g') : DEFAULT_REGEX, toUpper);
	    };
	})();
	
	$( document ).ready(function() {
		/* jqgrid 반응형 */
		$(window).on('resize.jqGrid', function(){
			//alert($(".content").width());
			$("#gridMain").jqGrid("setGridWidth", $("#srh_wrap").width());
		})
		
		var ipCheck = '<c:out value="${ipCheck}"/>';
		if(ipCheck == "false"){
		    // 처리중 메시지 show
			//$("#id_loader").show();	 
			COMMON.fn_scrin_block_ui("B");
			
			$.unibillDialog.alert('확인', "보안단말에서만 사용가능한 메뉴입니다.", 
				    function (isTrue) {
				COMMON.fn_scrin_block_ui("UB");
				location.href = "/main/main.do";
			});		
			
		}/* else{
			$.unibillDialog.alert('허용', '허용');
		}
		 */
		
		// 그리드 컬럼 정보
		var sColNames  = [];
	    var sColModels = [];
	    var sColWidth  = [];
	    var sColAlign  = [];

	    var sMenuCode      = '<c:out value="${menu_code}"/>';	    
	    var sColName       = '<c:out value="${colNameList}"/>';
	    var sColModel  	   = '<c:out value="${colModelList}"/>';
	    var sColWidth  	   = '<c:out value="${colWidth}"/>';
	    var sColAlign  	   = '<c:out value="${colAlign}"/>';
	    var sColShowType   = '<c:out value="${colShowType}"/>';
	    var sColMaskList   = '<c:out value="${colMaskingList}"/>';
	    var sGridHg    	   = '<c:out value="${gridhg}"/>';	  
	    var sAutoSearchYn  = '<c:out value="${auto_search_yn}"/>';
	    var sExtFun        = '<c:out value="${extFun}"/>';
	    var sViewExtFun	   = '<c:out value="${dextFun}"/>';
	    var sDblclickYn    = '<c:out value="${dblclickYn}"/>';
		var sDblclickFun   = '<c:out value="${dblclickFun}"/>';
		var sClickFun      = '<c:out value="${clickFun}"/>';
		var sGridMakeFun   = '<c:out value="${gridMakeFun}"/>';
		var sMainGridSelYn = '<c:out value="${mainGridSelYn}"/>';
	    var sColNameArr    = sColName.split("|");
	    var sColModelArr   = sColModel.split("|");
	    var sColWidthArr   = sColWidth.split("|");
	    var sColAlignArr   = sColAlign.split("|");
	    var sColShowTypeArr= sColShowType.split("|");
	    var sColMaskListArr= sColMaskList.split("|"); 
	    var sColTotWidth   = 0;

	    sColNames.push("순번");
	    sColModels.push({name:'ubseq', index:'ubseq', jsonmap:'ubseq', align:'center', width:'100px', key:true, hidden:true});
/* 	    
	    sColNames.push("PK컬럼");
	    sColModels.push({name:'pkCol', index:'pkCol', jsonmap:'pkCol', align:'center', width:'120px', hidden:true});
 */	    
	    for (var i=0; i<sColNameArr.length; i++) {

	    	var sStrList, sJsonMap, sJsontMap; 
	    	var sWidth  = parseInt(sColWidthArr[i]);
	    	var sHidden = false;
	    	if(sScrinCode == 'M040104'){
	    		$("#job_type").html("<option value='5'>서비스 정산 (5)</option>");
	    	}else if(sScrinCode == 'M040102'){
	    		$("#job_type").html("<option value='1'>통화요금재계산 (1)</option>");
	    	}
	    	// 그리드 셀 width
	    	if (sColWidthArr[i] == 0) {
	    		sWidth = 120;
	    	}

	    	// 그리드 hidden 여부
	    	if (sColShowTypeArr[i] == "4" || sColShowTypeArr[i] == "5") {
	    		sHidden = true;
	    		sWidth  = 100;
	    	}
	    	
	    	sColTotWidth = sColTotWidth + parseInt(sWidth);	    		    	

	    	// jsonmap id 명
	    	if (sColModelArr[i].indexOf("_") > 0 ) {
	    		sJsonMap = camelCase(sColModelArr[i]);
	    	} else {
	    		sJsonMap = sColModelArr[i].toLowerCase();
	    	}
	    	
			
	    	// 화면ID(scrin_id) 별 특화기능
	    	if(sScrinCode == "S0214"){
	    		sColNames.push(sColNameArr[i]);
	    		
	        	if (sJsonMap == "fileSn") {  // 첨부파일
	        		sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth, hidden:sHidden,  formatter: fileButton});
	        	} else {		        	
			    	sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth, hidden:sHidden,  cellattr:jsFormatterCell});
	        	}	    		
	    	}else if (sScrinCode == "S0065" || sScrinCode == "S0103") {  // 개통신청
	    		// 기간 defult 값 셋팅
	    		if(sScrinCode == "S0065"){
	    			$("#"+sFormId).find("#div_search_find_day span").eq(0).text('주문일자');
	    			$("#"+sFormId).find("#find_day option:eq(2)").attr('selected','selected');	
	    		}

	        	sColNames.push(sColNameArr[i]);
	        	
	        	if (sJsonMap == "fileNm") {  // 첨부파일
	        		sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth, hidden:sHidden,  formatter: fileButton});
	        	} else {		        	
			    	sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth, hidden:sHidden,  cellattr:jsFormatterCell});
	        	}
	        	
	        } else {
	        	
		    	sColNames.push(sColNameArr[i]);
		    	sColModels.push({name:sColModelArr[i], index:sColModelArr[i], jsonmap:sJsonMap, align:sColAlignArr[i],  width:sWidth, hidden:sHidden,  cellattr:jsFormatterCell});
		    	//sColNames.push(sColtNameArr[i]);
		    	//sColModels.push({name:sColtModelArr[i], index:sColtModelArr[i], jsonmap:sJsontMap, align:sColAlignArr[i],  width:sWidth, hidden:true,  cellattr:jsFormatterCell});
	        }
	    } 
 		
	
 /* 
 		if (sTptype == "C") {
			sColNames.push("조회");
			sColModels.push({name:'subView', index:'subView', jsonmap:'subView', align:'center', width:'120px', formatter: viewButton});
 		}
 */         
 
        // 템플릿이 D 타입(그리드 edit)인 경우
        if (sTptype == "D") {

			CONTENT.fn_editGridMake(sFormId, sGridMainId, sGridMainPager, sColNames, sColModels, sGridHg, sGridMakeFun);
			
        } else {
        	
			// 그리드 더블클릭 함수
			if (sDblclickFun != "") {
				sDblclickFun = COMMON.fn_replaceAll(sDblclickFun, "&#039;", "'");
			}
        	CONTENT.fn_mainGridMake(sMenuCode, sFormId, sDetailFormId, sTabFormId, sGridMainId, sGridMainPager, sColNames, sColModels, sGridHg, sTptype, sColTotWidth, sDblclickYn, sDblclickFun, sClickFun, sMainGridSelYn, sViewExtFun, sColMaskListArr);
        }

		// 검색 폼 디폴트 set
		COMMON.fn_form_default_set(sFormId);
 
        // 함수 open 실행함수
        if (sExtFun != "") {
        	sExtFun = COMMON.fn_replaceAll(sExtFun, "&#039;", "'");
        	
        	var callback_function = new Function(sExtFun);		
    		callback_function();
        }
 
		// 검색
		if (sAutoSearchYn == "Y") {
			COMMON.fn_search_mainGrid(sFormId, sGridMainId);
		}
		
/* 
		// 엔터 시 검색 처리
		$("input[type=text]").keyup(function (evt) {
	        if (evt.keyCode == 13)
	        	COMMON.fn_search_mainGrid(sFormId, sGridMainId);
	            return false;
	    });				
 */
		
	});
	
	// 엑셀다운로드 후 처리
	function fn_close_dtlscrin() {
		//document.getElementById('ly_pop').style.display = 'none';
	}
	
	
	// 저장 버튼 클릭시
	function fn_btn_save(_sExtFun, _sPreFun, _sPostFun){
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '<spring:message code="common.required.msg" />';
		
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 개별등록일 경우
		if (dtlRadioVal == "I") {
			//저장 이전처리
			if(typeof _sPreFun != "undefined" && _sPreFun != ""){
				(new Function(_sPreFun))();
			}
			var ret = true;		
				
				// 필수항목 체크 / KEY 컬럼 수정 여부에 따른 등록,수정 모드 셋팅			
				if (sScrinCode == "S0138") {  //보증금 관리일때 필수체크 
					if(!EAI.fn_columnValidate()) return; 
				}else { 
					ret = COMMON.fn_columnValidate(sDetailFormId, valMsg);
					if (ret==false) {
						//alert("fn_columnValidate fail");
					return;
					}
					
					//alert("fn_columnValidate ok");
				}
				
			var msg = '<spring:message code="common.regist.msg" />';
			var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
			if (actionFlag == "UPDATE") {
				msg = '<spring:message code="common.update.msg" />';
			}

			var sXmlId = $("#frm_main").find("#xmlId").val();
			// 미납현황조회 M040523 하단탭  클릭해서 저장시 xmlid 있으면 자동으로 xml이름생성해서 처리하려고 하는것방지
			// 추후 구조변경 필요 hsw
			if (sXmlId == undefined || sScrinCode=="S0031" || sScrinCode=="M040523" ) sXmlId = '';

			$("#"+sDetailFormId).find("#xmlId").remove();
			$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);
			
			 //저장 이후 처리
			$("#"+sDetailFormId).find("#sPostFun").remove();
			if(typeof _sPostFun != "undefined" && _sPostFun != ""){
				$('<input/>').attr({type:'hidden',id:"sPostFun",name:"sPostFun",value:_sPostFun}).appendTo("#"+sDetailFormId);
				//(new Function(_sPostFun))();
			} 
			
			
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
						if (_sExtFun != undefined || _sExtFun != "") {
							var callback_function = new Function(_sExtFun);		
							callback_function();
                        }
                        // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");

						//COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						//닫기 버튼 기능 pjh 
						fn_close_dtlscrin();
						
						
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);						
			
		// 일괄등록일 경우
		} else if (dtlRadioVal == "E") {
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
			
			//저장 이전처리
			if(typeof _sPreFun != "undefined" && _sPreFun != ""){
				var callback_function = new Function(_sPreFun);
				callback_function();
			}
			
			$.unibillDialog.confirm('확인', '<spring:message code="common.mregist.msg" />', 
				function (isTrue) {
					// 처리중 메시지 show
					$("#id_loader").show();	 
					COMMON.fn_scrin_block_ui("B"); 
	
					//COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/bundleUploadContent.do'/>", "fn_bundle_upload_callback");
					COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/excelUploadContent.do'/>", "fn_bundle_upload_callback");					
					
					setTimeout(function(){
						//저장 이후 처리
						if(typeof _sPostFun != "undefined" && _sPostFun != ""){
							var callback_function = new Function(_sPostFun);
							callback_function();
						}
					}, 1500);
					//닫기 버튼 기능 pjh
					fn_close_dtlscrin();
				}
			);	
		}
	}
	
	function fn_btn_save2 (_sExtFun, _sPreFun, _sPostFun) {		
		
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		var valMsg      = '<spring:message code="common.required.msg" />';
		
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 개별등록일 경우
		if (dtlRadioVal == "I") {
			//저장 이전처리
			if(typeof _sPreFun != "undefined" && _sPreFun != ""){
				var callback_function = new Function(_sPreFun);
				callback_function();
			}
			var ret = true;		
			setTimeout(function(){
				
				// 필수항목 체크 / KEY 컬럼 수정 여부에 따른 등록,수정 모드 셋팅			
				if (sScrinCode == "S0138") {  //보증금 관리일때 필수체크 
					if(!EAI.fn_columnValidate()) return; 
				}else { 
					ret = COMMON.fn_columnValidate(sDetailFormId, valMsg);
					if (ret==false) {
						//alert("fn_columnValidate fail");
					return;
					}
					
					//alert("fn_columnValidate ok");
				}
				
			var msg = '<spring:message code="common.regist.msg" />';
			var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
			if (actionFlag == "UPDATE") {
				msg = '<spring:message code="common.update.msg" />';
			}

			var sXmlId = $("#frm_main").find("#xmlId").val();
			// 미납현황조회 M040523 하단탭  클릭해서 저장시 xmlid 있으면 자동으로 xml이름생성해서 처리하려고 하는것방지
			// 추후 구조변경 필요 hsw
			if (sXmlId == undefined || sScrinCode=="S0031" || sScrinCode=="M040523" ) sXmlId = '';

			$("#"+sDetailFormId).find("#xmlId").remove();
			$('<input/>').attr({type:'hidden',id:'xmlId',name:'xmlId',value:sXmlId}).appendTo("#"+sDetailFormId);
			
			
			
			
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
						if (_sExtFun != undefined || _sExtFun != "") {
							var callback_function = new Function(_sExtFun);		
							callback_function();
                        }
                        // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");

						//COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _sPostFun != "undefined" && _sPostFun != ""){
								var callback_function = new Function(_sPostFun);
								callback_function();
							}
						}, 1500);
						
						//닫기 버튼 기능 pjh 
						fn_close_dtlscrin();
						
						
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);						
			}, 500);
		// 일괄등록일 경우
		} else if (dtlRadioVal == "E") {
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
			
			//저장 이전처리
			if(typeof _sPreFun != "undefined" && _sPreFun != ""){
				var callback_function = new Function(_sPreFun);
				callback_function();
			}
			
			$.unibillDialog.confirm('확인', '<spring:message code="common.mregist.msg" />', 
				function (isTrue) {
					// 처리중 메시지 show
					$("#id_loader").show();	 
					COMMON.fn_scrin_block_ui("B"); 
	
					//COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/bundleUploadContent.do'/>", "fn_bundle_upload_callback");
					COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/excelUploadContent.do'/>", "fn_bundle_upload_callback");					
					
					setTimeout(function(){
						//저장 이후 처리
						if(typeof _sPostFun != "undefined" && _sPostFun != ""){
							var callback_function = new Function(_sPostFun);
							callback_function();
						}
					}, 1500);
					//닫기 버튼 기능 pjh
					fn_close_dtlscrin();
				}
			);	
		}

	}
	
	function fn_btn_update(_sExtFun, _sPreFun, _sPostFun){
		if (sScrinCode == "M010304") {
			var actionFlag = "UPDATE";
			var msg = '<spring:message code="common.update.msg" />';
			
			//저장 이전처리
			if(typeof _sPreFun != "undefined" && _sPreFun != ""){
				var callback_function = new Function(_sPreFun);
				callback_function();
			}
			
			$.unibillDialog.confirm('확인', msg, 
			    function (isTrue) {
					try {
						if (_sExtFun != undefined || _sExtFun != "") {
							var callback_function = new Function(_sExtFun);		
							callback_function();
                        }
                        // 처리중 메시지 show
						$("#id_loader").show();	 
						COMMON.fn_scrin_block_ui("B");

						//COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						COMMON.fn_submit_fileForm(sDetailFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");
						
						setTimeout(function(){
							//저장 이후 처리
							if(typeof _sPostFun != "undefined" && _sPostFun != ""){
								var callback_function = new Function(_sPostFun);
								callback_function();
							}
						}, 1500);
						
						//닫기 버튼 기능 pjh 
						fn_close_dtlscrin();
						
						
						
					} catch (E) {
						$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
					}
				}
			);	
			
			
		}else{
			alert("저장버튼 구현중입니다.");
		}
		
	}
	
	// 등록 및 삭제 버튼 클릭시
	function fn_action(_sColId, _sFormId, _sScrin_code, _sGridId, _sExtFun, s_dExtFun) {
		$("#id_loader").show();
		COMMON.fn_scrin_block_ui("B");
		$("#frm_detail").find("#scrin_code").val(_sScrin_code);  
		$("#frm_detail").find("#dFilesn").val("");
		if (_sExtFun != undefined && _sExtFun != "" && _sScrin_code !="S0031") {
			var callback_function = new Function(_sExtFun);		
			callback_function(_sColId, _sFormId, _sScrin_code, _sGridId);
			
		} else {
			// 등록
			if (_sColId.indexOf("regist") > 0) {
				COMMON.fn_reset_form(sDetailFormId); // 폼 초기화						
				if (_sScrin_code == "") {
					COMMON.fn_view_content(_sFormId, sDetailFormId, "", "INSERT");
				} else {
					COMMON.fn_view_content(_sFormId, sDetailFormId, "", "INSERT", _sScrin_code);
				}
	
			
		    }
			// 삭제
			else if (_sColId.indexOf("delete") > 0) {
		    	//조건 이괄삭제
		    	if(_sColId.indexOf("_deletecond") > 0){
		    		alert("조건일괄삭제 구현중입니다.");
		    	}else{
		    		var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
					var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
								
					if (rowIds.length == 0) {
						$.unibillDialog.alert('알림', '<spring:message code="info.norowdata.msg" />');
			    		$("#id_loader").hide();
			    		COMMON.fn_scrin_block_ui("UB");
						return;
					}									
					
					$.unibillDialog.confirm('확인', '<spring:message code="common.delete.msg" />', 
						function (isTrue) {
										
							$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
							$("#"+_sFormId).find("#actionFlag").val("DELETE");  // 처리상태
							
							try {
								COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");						
								
							} catch (E) {
								$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
							}
						}
					);
		    	}
		    		 
		    } 
			// 승인
			else if (_sColId.indexOf("confirm") > 0) {
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '<spring:message code="info.norowdata.msg" />');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '<spring:message code="common.approve.msg" />', 
					function (isTrue) {
									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("CONFIRM");  // 처리상태
						
						try {
							COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");						
							
						} catch (E) {
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
		    } 
			// 상세
			else if (_sColId.indexOf("detail") > 0) {
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selrow");
		    	var sRowData = $("#"+_sGridId).jqGrid("getRowData");
		    	var sUbseq   = rowIds;
		    	
		    	//if (rowIds.length == 0) {
				if (rowIds == null) {
		    		$.unibillDialog.alert('알림', '<spring:message code="info.norowdata.msg" />');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
		    	} 
				/*
		    	if (rowIds.length > 1) {
					$.unibillDialog.alert('알림', '<spring:message code="info.onerowdata.msg" />');
					return;
				}
				*/
		    		    	
	//	    	$("#"+_sFormId).find("#scrin_code").val(_sScrin_code);
				
				if (sScrinCode == "S0065" || sScrinCode == "S0103") {  // 서비스 관리 > 신청관리 > 개통신청, 변경신청
					SVCMGR.fn_svc_detail_view("UPDATE", "frm_reqst", sGridMainId, sUbseq);
				} else if(sScrinCode == "S0104"  || sScrinCode == "S0142"){		
					JOBMASTER.fn_view_job(_sFormId, sDetailFormId, _sGridId);
				} else {
					COMMON.fn_view_content(_sFormId, sDetailFormId, sUbseq, "UPDATE",sScrinCode, s_dExtFun);
				}				
		    }
		/*	else if (_sColId.indexOf("_insod") > 0) { //변경접수 저장
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
				var sUbseq   = rowIds;
							
				if (rowIds.length == 0 || rowIds == null) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				//COMMON.fn_view_content(_sFormId, sDetailFormId, sUbseq, "UPDATE",sScrinCode);
			 
				$.unibillDialog.confirm('확인', '접수 하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("INSERT");  // 처리상태
						try {							
							sScrinCode="M020325"; //pjh							
							COMMON.fn_view_content_scrin(_sFormId, sDetailFormId, "", "INSERT","M020325", "mb_cfgod", "","","변경접수관리");
							 
							//CUSTOM.fn_insod(rowIds); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
				 
		    } */
			else if (_sColId.indexOf("_chcont") > 0) { //접수등록
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
				var sUbseq   = rowIds;
							
				if (rowIds.length == 0 || rowIds == null) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}	 
			 /*
				$.unibillDialog.confirm('확인', '변경접수 하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("INSERT");  // 처리상태	 
						
						try {
							sScrinCode="M020325"; //pjh
							COMMON.fn_view_content_scrin(_sFormId, sDetailFormId, sUbseq,"Y", "INSERT",sScrinCode, "mb_cfgod", "","","변경접수관리"); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
			 */
			 
			    $("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
				$("#"+_sFormId).find("#actionFlag").val("INSERT");  // 처리상태	 
				
				try {
					sScrinCode="M020325"; //pjh
					COMMON.fn_view_content_scrin(_sFormId, sDetailFormId, sUbseq,"Y", "INSERT",sScrinCode, "mb_cfgod", "","","변경접수관리"); 
				} catch (E) { 
					$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
				}
				 
		    }
		    	else if (_sColId.indexOf("reqst") > 0) {
	
		    	SVCMGR.fn_svc_reqst();
	
		    }
			else if (_sColId.indexOf("_inscont") > 0) { //계약적용
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '계약에 적용하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("INSCONT");  // 처리상태						
						try {
							//COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/receipt/inscont.do'/>", "fn_save_callback_content");
							CUSTOM.fn_inscont(rowIds); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
		    }
			
			else if (_sColId.indexOf("_insjobstep") > 0) { //계약적용
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '작업전달 하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("INSJOBSTEP");  // 처리상태						
						try {
							//COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/receipt/inscont.do'/>", "fn_save_callback_content");
							CUSTOM.fn_insjobstep(rowIds); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
		    }
			else if (_sColId.indexOf("_jobreject") > 0) { //계약적용
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '작업반려 하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("JOBREJECT");  // 처리상태						
						try {
							//COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/receipt/inscont.do'/>", "fn_save_callback_content");
							CUSTOM.fn_jobreject(rowIds); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
		    }
			else if (_sColId.indexOf("_insjob") > 0) { //계약적용
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '적용할 대상을 선택하여 주세요');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '작업등록 하시겠습니까?', 
					function (isTrue) {									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("INSJOB");  // 처리상태						
						try {
							//COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/receipt/inscont.do'/>", "fn_save_callback_content");
							CUSTOM.fn_insjob(rowIds); 
						} catch (E) { 
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	 
		    }
		    	
			else if (_sColId.indexOf("cancel") > 0) {
		    	
		    	var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
				var rowDatas = $("#"+_sGridId).jqGrid("getRowData");
							
				if (rowIds.length == 0) {
					$.unibillDialog.alert('알림', '<spring:message code="info.norowdata.msg" />');
		    		$("#id_loader").hide();
		    		COMMON.fn_scrin_block_ui("UB");
					return;
				}									
				
				$.unibillDialog.confirm('확인', '<spring:message code="common.cancel.msg" />', 
					function (isTrue) {
									
						$("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 삭제대상 ubseq
						$("#"+_sFormId).find("#actionFlag").val("CANCEL");  // 처리상태
						
						try {
							CUSTOM.fn_odcancel(rowIds); 
							//COMMON.fn_submit_createForm(_sFormId, "proc_frm", "<c:url value='/content/saveContent.do'/>", "fn_save_callback_content");						
							
						} catch (E) {
							$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
						}
					}
				);	
		    
			// 상세
		    }
		}
		COMMON.fn_scrin_block_ui("UB");
		$("#id_loader").hide();
	}
	
	// 저장 후 처리	 
	function fn_save_callback_content(_sOID, sPostFun) {
		//저장후 함수 호출
		if(typeof sPostFun != "undefined" && sPostFun != ""){
			(new Function(sPostFun))();
		}
		
		try {
			//_sOID       : M020321|1907310914480000040|DELETE||			
			var strSpl          = _sOID.split("|");
			var scrin_id        = strSpl[0];			
			var sGridId         = sGridMainId;
			//var sGridId         = sGridSubId;
			 
			var sFormId         = sFormId;
			var sCallBackFun    = strSpl[2];
			var sKeyId          = strSpl[3];
			var sExtCallBackFun = strSpl[4];
			 
			//하단탭 재로드시 사용
			if (sTptype == "C") {
				sGridId = "gridSub_"+scrin_id;
				sFormId = "frm_tab";
			}
 

			
//alert("fn_save_callback_content> sFormId : " + sFormId + "\n scrin_id : " + scrin_id + "\n sGridId : " + sGridId + "\n sTptype : " + sTptype  );
			// 현재 그리드 페이지 번호
			var sCurrentPage = $("#"+sGridId).getGridParam("page");

			// 현재 그리드 스크롤 위치
			var sPosition = $("#"+sGridId).closest(".ui-jqgrid-bdiv").scrollTop();

			// 그리드 reload
			if (sCallBackFun == "INSERT" || sCallBackFun == "UPDATE") {
				CONTENT.fn_autoGridPageMove(strSpl[1], sGridId, sFormId, sCurrentPage, sPosition,  1000);
				COMMON.fn_search_mainGrid("frm_main", "gridMain");
			} else {
				COMMON.fn_search_mainGrid("frm_main", "gridMain");
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
			//파일업로드 그리드 초기화
			$("#frm_fileDetail").find("#upfilenm").val('');
			$("#frm_detail").find("#fileuploadnm").val('');
			
			$("#fileUploadgrid").clearGridData();
				
			$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
				$(this).remove();
			});
			
			// 두번째 저장 후 처리
			if (sExtCallBackFun != "") {				
				var callback_function = new Function(sExtCallBackFun);		
				callback_function();
			}
			
			// 처리중 메시지 hide
			$("#id_loader").hide();			
			COMMON.fn_scrin_block_ui("UB");

		} catch (E) {			
			$.unibillDialog.alert('오류', E);
		}
	}

	// 엑셀다운로드 후 처리
	function fn_excelDownload_callback(_sOID) {
		try {
			var strSpl   = _sOID.split("|");	
			// 엑셀파일 정상 생성 시 다운로드
			if (strSpl[0] != "false") {
				COMMON.fn_download_file('excel_download', strSpl[0], 'Y');
			}
		} catch (E) {
			$.unibillDialog.alert('오류', E);
			
		}
	}
	
	// 템플릿 엑셀다운로드 후 처리
	function fn_TempexcelDownload_callback(_sOID) {
		try {
			var strSpl   = _sOID.split("|");	
			// 엑셀파일 정상 생성 시 다운로드
			if (strSpl[0] != "false") {
				COMMON.fn_download_file('template', strSpl[0], 'Y');
			}
		} catch (E) {
			$.unibillDialog.alert('오류', E);
			
		}
	}
	
	// 일괄등록(엑셀업로드) 후 처리
	function fn_bundle_upload_callback(_sOID) {
		try {
			var strSpl   = _sOID.split("|");		
		
			// 업로드건수		
			$("#"+sDetailFormId).find("#id_excel_upload_tot_cnt").text(COMMON.fn_getCommaString(strSpl[1]));  // 전체
			$("#"+sDetailFormId).find("#id_excel_upload_ok_cnt").text(COMMON.fn_getCommaString(strSpl[2]));   // 성공
			$("#"+sDetailFormId).find("#id_excel_upload_err_cnt").text(COMMON.fn_getCommaString(strSpl[3]));  // 오류
			
			// 작업메시지
			var resultMsg = strSpl[0];
						
			if (strSpl[3] != "0") {  // 오류내역다운로드 표시
				var errView = ' <span style="font-weight:bold"><a href="#" onclick="COMMON.fn_download_file(\'EXCEL_UPLOAD\', \''+strSpl[4]+'\')">[오류내역 엑셀파일 다운로드]</a></span>';
				resultMsg += errView;
			}			
			$("#"+sDetailFormId).find("#id_excel_upload_result").html(resultMsg);
			
			// 처리중 메시지 hide
			$("#id_loader").hide();			
			COMMON.fn_scrin_block_ui("UB");
			
			//파일첨부 초기화
			var agent = navigator.userAgent.toLowerCase();
			if( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
				$("#"+sDetailFormId).find("#userfile2").replaceWith($("#userfile2").clone(true) );
			}else{ // other browser 일때 input[type=file] init. $("#filename").val(""); 
				$("#userfile2").val("");
			}
			

			$("#"+sDetailFormId).find("#userfile2").val();

			// 팝업화면 hide
            //$("#ly_pop").hide();

            // 그리드 조회
			COMMON.fn_search_mainGrid(sFormId, sGridMainId);			

		} catch (E) {
			$.unibillDialog.alert('오류', E);
		}
	}
	
	// 그리드 셀에 첨부파일 버튼 생성
	function fileButton(cellvalue, options, rowObject) {
		var rowId = options.rowId;
		
		if(sScrinCode == "S0065" || sScrinCode == "S0103"){
			var html = '<a href="#" onclick="SVCMGR.fn_svc_file_download(\'G\',\'gridMain\',\''+rowId+'\');"><img src="../images/common/ico_clip.png" /></a>';			
		}else{
			var html = '<a href="#" onclick="JIRO.fn_jiro_file_download(\''+sScrinCode+'\',\'G\',\'gridMain\',\''+rowId+'\');"><img src="../images/common/ico_clip.png" /></a>';			
		}
		
		if (cellvalue == undefined) {
			return "";
		} else {
			return html;
		}
	}

	function jsFormatterCell(rowId, tv, rawObject, cm, rdata) {		
		//var result = "id='"+cm.name+"" + rowId + "'";
		var result = "id='"+cm.name+"" + rowId + "'" +" title='" + tv + "'";
		return result;
    }
	
	function jshiddenFormatterCell(rowId, tv, rawObject, cm, rdata) {		
		//var result = "id='"+cm.name+"" + rowId + "'";
		var result = "id='tooltip_"+cm.name+"" + rowId + "'" +" title='" + tv + "'";
		return result;
    }
	
	function search_variableMainGrid() {
		// 기존 그리드 제거
		COMMON.fn_gridRemove('gridMain');
		
		// 그리드 헤더 데이터 생성
		COMMON.fn_variableMainGridMake('<c:out value="${menu_code}"/>', sFormId, sDetailFormId, sTabFormId, sGridMainId, sGridMainPager, '<c:out value="${gridhg}"/>', sTptype, 0, '<c:out value="${dblclickYn}"/>', '<c:out value="${dblclickFun}"/>', '<c:out value="${clickFun}"/>', '<c:out value="${mainGridSelYn}"/>')
	}
	

</script>

<form id="frm_main" name="frm_main">
<input type="hidden" id="scrin_code" name="scrin_code" value="<c:out value="${scrin_code}"/>" />
<input type="hidden" id="tableName" name="tableName" value="${tableName}" />
<input type="hidden" id="formid" name="formid" value="frm_main" />
<input type="hidden" id="fileName" name="fileName" value="${menu_nm}" />
<input type="hidden" id="ubSeqArr" name="ubSeqArr" />
<input type="hidden" id="actionFlag" name="actionFlag" value="" />
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" /> 
<input type="hidden" id="xmlId" name="xmlId" value="${xmlId}" />
<input type="hidden" id="scrinSqlYn" name="scrinSqlYn" value="${scrinSqlYn}" />
<input type="hidden" id="tptype" name="tptype" value="${tptype}" />
<input type="hidden" id="searchYn" name="searchYn" value="" />
<input type="hidden" id="mgSearchYn" name="mgSearchYn" value="" />
<input type="hidden" id="mgTotCnt" name="mgTotCnt" value="" />
<input type="hidden" id="template" name="template" value="" />
<input type="hidden" id="hdYn" name="hdYn" value="${hdYn}" />
<input type="hidden" id="excelUseReasonYn" name="excelUseReasonYn" value="${excelUseReasonYn}" />
<input type="hidden" id="sumYn" name="sumYn" value="${sumYn}" />
<input type="hidden" id="reasonMsg" name="reasonMsg" value="" />
<input type="hidden" id="mon" name="mon" value="${mon}" />



<c:forEach var="list" items="${guidInfo}" varStatus="status"> 		
	<c:if test="${list.posType == 'T'}">
	    <c:if test="${list.sn == '1'}">			
			<input type="hidden" id="guidTop" name="guidTop" value="${list.txt}" />
		</c:if>
	</c:if>
</c:forEach>    
        <!-- Search  -->
        <div>
        <h2 id="id_menu"></h2>        
        </div>                  
            <fieldset>
                <legend>Search</legend>                
                <div class="srh_wrap" id="srh_wrap">                
			<c:set var="idx" value="1" />
          	<c:forEach var="list" items="${searchInfo}" varStatus="status">
                      
               <c:set var="sBtn" value="${fn:startsWith(list.objtype, 'button')}" />               
               <c:if test = "${!sBtn}">
                                    	
				<!-- <c:choose>
                    <c:when test="${idx == list.rowpos}">
                    	<c:set var="idx" value="${list.rowpos}" />                            		
				    </c:when>
				    <c:otherwise>
				    	<c:if test="${idx != list.maxxpos}">
					    	<p style="word-break:break-all;"></p>
					   		<c:set var="idx" value="${idx+1}" />
					   	</c:if>
				    </c:otherwise>
			    </c:choose> -->

							<c:if test = "${list.objtype == 'hidden'}">
								<input type="hidden" id="${list.colid}" name="${list.colid}" value="${list.findIdSql}" />
							</c:if>
							<c:if test = "${list.objtype != 'hidden'}">
							
		                  	    <div style="width:${list.width}px" id="div_search_${list.objid}">
		                     		<%-- 검색 항목명 --%>
		                     		<label>${list.name}<c:if test="${list.mustyn eq 'Y'}"><span class="asterisk"></span></c:if></label>                       		
		
		                     		<%-- 검색 폼 생성 --%>
		                     		<%@ include file="/WEB-INF/jsp/unibill/content/contentSearchArea.jsp"%>
		                     		
		                     		<%-- 하위폼 생성 --%>
		                     		<c:if test = "${list.subformyn != '0'}">                     		
			                      		<jsp:include page="/content/selectContentList.do" flush="true">															
											<jsp:param name="scrin_code" value="${scrin_code}"/>
											<jsp:param name="objid" value="${list.objid}"/>
										</jsp:include>
		                     		</c:if>
	                    		</div>

                    		</c:if>

                   		</c:if>                       		                       		

                   	</c:forEach>
                   	
                   	<%-- 공통(객체이용) 이외 작업승인(명의변경) 검색 객체 생성 --%>	                
					<div style="display:none" id="div_search_trnsfr_cont_no">
						<label>양도 관리번호</label>
						<input type="text" id="trnsfr_cont_no" name="trnsfr_cont_no" />
					</div>	                    		

					<div style="display:none" id="div_search_trnsfr_cust_id">
						<label>양도 고객번호</label>
						<input type="text" id="trnsfr_cust_id" name="trnsfr_cust_id" onclick="COMMON.fn_auto_complete('frm_main','mb_cfgcust=cust_nm','cust_id','','','trnsfr_cust_id');" onkeydown="if (event.keyCode == 13) {COMMON.fn_search_mainGrid('frm_main', 'gridMain');};" />
					</div>

					<div style="display:none" id="div_search_trnsfr_cust_nm">
						<label>양도 고객명</label>
						<input type="text" id="trnsfr_cust_nm" name="trnsfr_cust_nm" onclick="COMMON.fn_auto_complete('frm_main','mb_cfgcust=cust_nm','cust_nm','','','trnsfr_cust_nm');" onkeydown="if (event.keyCode == 13) {COMMON.fn_search_mainGrid('frm_main', 'gridMain');};" />	
					</div>

					<div style="display:none" id="div_search_rcept_cont_no">
						<label>양수 관리번호</label>
						<input type="text" id="rcept_cont_no" name="rcept_cont_no" />
					</div>	                    		

					<div style="display:none" id="div_search_rcept_cust_id">
						<label>양수 고객번호</label>  				
						<input type="text" id="rcept_cust_id" name="rcept_cust_id" onclick="COMMON.fn_auto_complete('frm_main','mb_cfgcust=cust_nm','cust_id','','','rcept_cust_id');" onkeydown="if (event.keyCode == 13) {COMMON.fn_search_mainGrid('frm_main', 'gridMain');};" />
					</div>
									
					<div style="display:none" id="div_search_rcept_cust_nm">
						<label>양수 고객명</label>
						<input type="text" id="rcept_cust_nm" name="rcept_cust_nm" onclick="COMMON.fn_auto_complete('frm_main','mb_cfgcust=cust_nm','cust_nm','','','rcept_cust_nm');" onkeydown="if (event.keyCode == 13) {COMMON.fn_search_mainGrid('frm_main', 'gridMain');};" />	
					</div>
                   	
                   	<%-- 검색영역 버튼 --%>
                   	<span class="btn_wrap">
                   	<!-- <button type="button" id="id_button" onclick="javascript:alert('조회 중입니다.');" style="display:none;font-size:14px;padding:3px 17px 5px;color:#5674b9;border:1px solid #5674b9;background:#fff;font-weight:700;">조회</button> -->
                   	<c:forEach var="list" items="${searchInfo}" varStatus="status">                   	
                   	    <c:set var="sBtn" value="${fn:startsWith(list.objtype, 'button_s')}" />      
                        <c:if test = "${sBtn}">                            
                     		<button type="button" class="btn_srh" id="${list.colid}" onclick="COMMON.fn_com_click('${list.colid}','${list.extFun}');">${list.name}</button>                     		
               	    	</c:if>               	    	
                 	 </c:forEach>
                 	 </span>
                 	 <%-- //검색영역 버튼 --%>
                                            
                </div>

            </fieldset>
            
        <!-- Search result -->
        <div class="ui_both">
            <div class="fl">
            	<h4>조회결과 <span>(<span id="id_totalRecords"></span>건)</span>            	
             	<c:forEach var="list" items="${guidInfo}" varStatus="status">                	             	            		
          			<c:if test="${list.posType == 'M'}">
          			    <span class="add">
          				<c:if test="${list.sn == '1'}">
         					${list.txt}
         				</c:if>
         				</span>
	          		</c:if>
	          	</c:forEach>	          	
         		</h4>
            </div>
            
            <div class="fr">
                <%-- 상단 그리드 우측상단 버튼 --%>
                <%-- 화면 템플릿 타입이 통계(E) 및 이력(B)이 아닌 경우 --%>
                  <c:if test="${tptype != 'E' and tptype != 'B' and tptype != 'V'}">   
                <%-- <c:if test="${tptype == 'A'} "> --%> 
                	<c:if test="${dblclickYn == 'Y'}">
                		<button type="button" class="btn_m" id="btn_detail" onclick="fn_action('btn_detail','frm_main','${scrin_code}','gridMain','','${dextFun}');">상세보기</button>
                	</c:if>
                </c:if>
                <c:if test="${tptype eq 'A' or tptype eq 'C' or tptype eq 'D'}">
                	<c:forEach var="list" items="${searchInfo}" varStatus="status">                   		                   	    
                	    <c:set var="sBtn" value="${fn:startsWith(list.objtype, 'button_g')}" />      
                        <c:if test = "${sBtn}">                            
                  	        <button type="button" class="btn_m" id="${list.colid}" onclick="fn_action('${list.colid}','frm_main','${scrin_code}','gridMain','${list.extFun}');">${list.name}</button>                       
            	    	</c:if>	               	    	
                	 </c:forEach>                	
                </c:if>
            </div>
        </div>
        <table id="gridMain"></table>
        <div id="gridMainPager"></div>
        </form>        
        
        <%-- 하단 grid --%>
        <c:if test="${tptype == 'C'}">
            <span class="division"></span> <!-- 점선 -->
        <%-- 탭메뉴 form --%>
		<form id="frm_tab" name="frm_tab">
		<input type="hidden" id="masterTableName" name="masterTableName" value="${tableName}"/>
		<input type="hidden" id="masterMenuCode" name="masterMenuCode" value="${menu_code}"/>
		<input type="hidden" id="tableName" name="tableName" />
		<input type="hidden" id="scrin_code" name="scrin_code" value="<c:out value="${scrin_code2}"/>" />
		<input type="hidden" id="tabName" name="tabName" />
		<input type="hidden" id="ubseq" name="ubseq" />
		<input type="hidden" id="ubSeqArr" name="ubSeqArr" />
		<input type="hidden" id="actionFlag" name="actionFlag" value="" />
		<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />  
		         
        <%-- 탭메뉴 --%>
        <div class="tab">
             <ul class="tabs">
                 <c:forEach var="list" items="${tabList}" varStatus="status">
                  <c:choose>
                     	<c:when test="${status.count == 1}"  >
                     		<li class="on" rel="tab_${list.tabId}_${list.menuId}"><a href="#">${list.tabNm}</a></li>
                     	</c:when>
                     	<c:otherwise>
                     		<li rel="tab_${list.tabId}_${list.menuId}"><a href="#">${list.tabNm}</a></li>
                     	</c:otherwise>
                     </c:choose>	                        
                 </c:forEach>
             </ul>
        </div>
        <%--// 탭메뉴 --%>

        <%-- 하단 탭메뉴 그리드 --%>
		<div>
		<c:forEach var="list" items="${tabList}" varStatus="status">
		    <c:set var="sDsp" value="none" />					    
		    <c:if test="${status.count == 1}">
		    	<c:set var="sDsp" value="" />
		    </c:if>

			<div id="tab_${list.tabId}_${list.menuId}" class="tab_content" style="display:${sDsp};">
			    
			    <%-- 하단 그리드 우측 상단 버튼 시작 --%>
			    <div class="ui_both" id="subGridBtn_${list.tabId}" style="display:none;">
			    	<div class="fl" style="margin-bottom:7px;">
			    		<span id="id_totalRecords_${list.tabId}"></span>
			    	</div>
				    <div class="fr">
				    
				    <%--
				       <script type="text/javascript">
					       var tptype = "${tptype}"; 
					     	alert("tptype : " +tptype);	
					     </script> 
					 --%>    
					    <%-- 하단 그리드 우측상단 버튼  --%>
					    <c:if test="${tptype != 'E' and tptype != 'B' and tptype != 'V'}">    
		                	<c:if test="${dblclickYn == 'Y'}">
		                		<button type="button" class="btn_m" id="btn_detail" onclick="fn_action('btn_detail','frm_tab','${btnlist.tabId}','gridSub_${list.tabId}');">상세보기</button>
		                	</c:if>
		                </c:if>
                  
		                <c:if test="${tptype eq 'A' or tptype eq 'C'  }">
		                	<c:forEach var="btnlist" items="${tabBtnList}" varStatus="status">                   		                   	    
		                	    <c:set var="sBtn" value="${fn:startsWith(btnlist.objType, 'button_g')}" />  <%-- pjh 탭버튼 여부 --%>		                	    
		                	    <c:if test="${list.tabId eq btnlist.tabId}">   
			                        <c:if test="${sBtn}">
			                  	        <button type="button" class="btn_m" id="${btnlist.colId}" onclick="fn_action('${btnlist.colId}','frm_tab','${btnlist.tabId}','gridSub_${list.tabId}','${btnlist.extFun}');">${btnlist.objNm}</button>                       
			            	    	</c:if>
			            	    </c:if>
		                	 </c:forEach>                	
		                </c:if>				    	  
				    </div>
			    </div>
			    <%-- 하단 그리드 우측 상단 버튼 끝 --%>
			    <script type="text/javascript">
				    if($("#frm_tab").length > 0){
						$("#gridSub_"+${list.tabId}).jqGrid("setGridWidth", $("#srh_wrap").width());
					}
			    </script>
				<table id="gridSub_${list.tabId}"></table>
	         	<div id="gridSubPager_${list.tabId}"></div>
          	</div>		            	
        </c:forEach>
        </div>
        <%--// 하단 탭메뉴 그리드 --%>


		</form>
		<%--// 탭메뉴 form --%>

        </c:if>
        <%--// 하단 grid --%>
        
        <c:set var="guidIdx" value="0" />
        <c:forEach var="list" items="${guidInfo}" varStatus="status">            	            		
       		<c:if test="${list.posType == 'B'}">
      			<c:set var="guidIdx" value="${guidIdx+1}" />
       		</c:if>
       	</c:forEach>
        
        <!-- Notice -->
        <c:if test="${guidIdx != '0'}">
         <div class="box_gray">
             <dl class="box_info">
                 <dt>주의사항</dt>
                 <dd>
                     <ul class="bul_list">
                     <c:forEach var="list" items="${guidInfo}" varStatus="status">            	            		
       						<c:if test="${list.posType == 'B'}">
                       <li>${list.txt}</li>			                        
                   </c:if>
               </c:forEach>
                     </ul>
                 </dd>
             </dl>
         </div>
     </c:if>
<!-- //Notice -->

<%-- 상세(등록,수정) 팝업 --%>
<style type="text/css">    
  select{padding:0 0px;}  /* 상세팝업 콤보 css 변경 */
</style>
	
<form id="frm_detail" name="frm_detail" method="post" enctype="multipart/form-data">
<input type="hidden" id="scrin_code" name="scrin_code" value="<c:out value="${scrin_code}"/>" />
<input type="hidden" id="tableName" name="tableName" value="${tableName}" />
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />
<input type="hidden" id="actionFlag" name="actionFlag" value="" />
<input type="hidden" id="html" name="html" /> <%-- 하위객체 --%>
<input type="hidden" id="dtlWidth" name="dtlWidth" value="<c:out value="${dtlWidth}"/>"/>  <%-- 화면id별 상세화면width --%>
<input type="hidden" id="keyList" name="keyList" value=""/>
<input type="hidden" id="userDocNo" name="userDocNo" value="" />
<input type="hidden" id="extFun" name="extFun" />
<input type="hidden" id="dFilesn" name="dFilesn" value="" />
<input type="hidden" id="addFileExt" name="addFileExt" value="${addFileExt}"/> 

<div id="ly_pop" class="ui_confirm" style="display:none">
    <!-- <div class="dim" onclick="document.getElementById('ly_pop').style.display = 'none'"></div> 모달 형태로 띄우기 위해 주석 처리 -->
    <!-- <div class="confirm_cont" id="id_detail_width" style="margin-top:-350px;"> --><!-- 팝업창 크기에 맞게 위치변경 -->
    <div class="confirm_cont" id="id_detail_width">
        <h4 id="id_tabNm">${menu_nm}</h4>
        <button type="button" class="close" onclick="document.getElementById('ly_pop').style.display = 'none'" title="닫기" alt="닫기">Close</button>        
        <c:if test = "${dtlTmpType eq 'M'}">
       		<div class="type01">등록유형 : &nbsp;&nbsp; <input type="radio" id="dtl_tmp_type" name="dtl_tmp_type" value="I" onclick="COMMON.fn_dtl_regist_type('I');" checked="checked" /> 개별등록 &nbsp;&nbsp;&nbsp; <input type="radio" id="dtl_tmp_type" name="dtl_tmp_type" value="E" onclick="COMMON.fn_dtl_regist_type('E');" /> 일괄등록&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="dtl_tmp_del" name="dtl_tmp_del" />&nbsp;전체삭제 
        </c:if>
        <p class="commentre"><span class="asterisk"></span> 필수입력 항목입니다.</p>
        <c:if test = "${dtlTmpType eq 'M'}">
        	</div>
        </c:if>
        <div id= "imgfile"></div>

        <%-- 개별등록 --%>
        <table summary="등록/상세정보" class="tb02" id="MainTable">
            <caption>등록/상세정보</caption>
            <tbody id="id_detail">
            </tbody>
        </table>
        <%-- // 개별등록 --%>
        
        <%-- 일괄등록(엑셀) --%>
        <table summary="등록/상세정보" class="tb02" style="display:none;" id="UploadTable">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="120">
                <col>
                <col width="120">
                <col>
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row">경로<span class="asterisk"></span></th>
                    <td colspan="3">
                    	<input type="file" id="userfile2" name="userfile2" style="width:100%;background:#fafafa;"/>                    	
                    </td>
                </tr>
                <tr>
                    <th scope="row"></th>
                    <td>
                    	<input id="excel_sheet" name="excel_sheet" type="hidden" value="0" /> 
                    </td>
                    <th scope="row">전체/성공/오류</th>
                    <td class="tar"><span id="id_excel_upload_tot_cnt">0</span> / <span id="id_excel_upload_ok_cnt">0</span> / <span class="t_red"><span id="id_excel_upload_err_cnt">0</span></span></td>
                </tr>
                <tr>
                    <th scope="row">작업메시지</th>
                    <td colspan="3">
                    	<span id="id_excel_upload_result"></span>
                    </td>                  
                </tr>
                <tr>
                    <th>주의사항</th>
                    <td colspan="3">
                    
                    </td>
                </tr>
            </tbody>
        </table>
        <%-- //일괄등록(엑셀) --%>
                
        <div class="btn_pop_wrap">        
           	 <span id="id_detail_button"></span>           	
           	 <%-- //상세화면 하단 버튼 --%>
        	<button type="button" class="btn_pop" onclick="document.getElementById('ly_pop').style.display = 'none'">닫기</button>
        </div>
    </div>
</div>

</form>

<c:if test = "${scrin_code eq 'M020114'}">
	<%-- 분개 생성 팝업 --%>
	<div id="bungae_pop" class="ui_confirm" style="display:none">
		<%@ include file="/WEB-INF/jsp/unibill/pop/bungaePop.jsp"%>
	</div>
</c:if>

<c:if test = "${scrin_code eq 'M020115'}">
	<%-- 분개 전송 팝업 --%>
	<div id="bungaesend_pop" class="ui_confirm" style="display:none">
		<%@ include file="/WEB-INF/jsp/unibill/pop/bungaeSendPop.jsp"%>
	</div>
</c:if>

<c:if test = "${excelUseReasonYn eq 'Y'}">
	<%-- 엑셀다운 사유 입력 팝업 --%>
	<%@ include file="/WEB-INF/jsp/unibill/pop/excelUseReasonPop.jsp"%>
</c:if>

<%-- 고객청구지 관리 주소 팝업 --%>
<%@ include file="/WEB-INF/jsp/unibill/zip/zipPop.jsp"%>

<div id="file_pop" class="ui_confirm" style="display:none">
	<%@ include file="/WEB-INF/jsp/unibill/pop/filePop.jsp"%>
</div>




