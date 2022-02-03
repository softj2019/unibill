/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.CONTENT = (function(_mod_content, $, undefined){
	
	/**
	 * @method
	 * @description 그리드 자동 선택
	 * @author 홍길동(2016.10.04)
	 */
	fn_autoGridSetSelection = function(_sUbSeq, _sGridId, _sFormId, _sPosition, _sTime) {
		window.setTimeout(function() {
		  $("#"+_sGridId).jqGrid("setSelection", _sUbSeq);
		  $("#"+_sGridId).closest(".ui-jqgrid-bdiv").scrollTop(_sPosition);  // 해당 위치로 스크롤 이동
		}, _sTime);
	}
	
	/**
	 * @method
	 * @description 그리드 페이지 이동
	 * @author 홍길동(2016.10.04)
	 */
	fn_autoGridPageMove = function(_sUbSeq, _sGridId, _sFormId, _sNum, _sPosition, _sTime) {
		window.setTimeout(function() {
			$("#"+_sGridId).jqGrid("setGridParam", {page:_sNum}).trigger("reloadGrid");
			CONTENT.fn_autoGridSetSelection(_sUbSeq, _sGridId, _sFormId, _sPosition, _sTime);
		}, _sTime);
	}

   /**
	* @method
	* @description 공통컨텐츠 그리드 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId 데이터가지고 있는 폼아이디
	* @param {String} _sDetailFormId 데이터가지고 있는 상세 폼아이디 
	* @param {String} _sGridId 그리드 id
	* @param {String} _sGridMainPager 그리드 페이지 id
	* @param {String} _sColNames 그리드 col name
	* @param {String} _sColModels 그리드 model name
	* @param {String} _sGridHg 그리드 높이
	*/
	fn_mainGridMake = function (_sMenuCode, _sFormId, _sDetailFormId, _sTabFormId, _sGridId, _sGridMainPager, _sColNames, _sColModels, _sGridHg, _sTptype, _sColTotWidth, _sDblclickYn, _sDblclickFun, _sClickFun, sMainGridSelYn, sViewExtFun, sColMaskListArr) {

		// 그리드 default height
		if (_sGridHg == "0" || _sGridHg == "") _sGridHg = "350";

		// 멀티체크 여부
        /*var sMultChk = false;		
		if (_sTptype == "A" || _sTptype == "B" || _sTptype == "C") {
			sMultChk = true;
		}*/
		
		// 멀티체크 여부
		var sMultChk = false;
		if (sMainGridSelYn == "Y") sMultChk = true;		

		//alert("fn_mainGridMake ondblClickRow="+_sDblclickFun+" _sDblclickYn="+_sDblclickYn);
		// 그리드 구성
		$("#"+_sGridId).jqGrid({
			  url : CONTEXT_ROOT + '/content/selectContentList.json'
			, datatype : "local"			
			, colNames : _sColNames
			, colModel : _sColModels
//		    , colNames : ["테스트","키번호","서비스","상품","항목","원본금액","청구금액"]
//		    , colModel : [
//                        {name:'G2_ID',    index:'G2_ID',    jsonmap:'g2Id',     align:'center',   width:100, key:true, hidden:false}   
//					   ,{name:'key_no',   index:'key_no',   jsonmap:'keyNo',    align:'center',   width:100, cellattr:jsFormatterCell}
//					   ,{name:'svc_id',   index:'svc_id',   jsonmap:'svcId',    align:'center',   width:200, cellattr:jsFormatterCell}
//					   ,{name:'gd_id',    index:'gd_id',    jsonmap:'gdId',     align:'center',   width:200, cellattr:jsFormatterCell}
//					   ,{name:'item_id',  index:'item_id',  jsonmap:'itemId',   align:'center',   width:200, cellattr:jsFormatterCell}
//					   ,{name:'ocost',    index:'ocost',    jsonmap:'ocost',    align:'center',   width:100, cellattr:jsFormatterCell}
//					   ,{name:'cost',     index:'cost',     jsonmap:'cost',     align:'center',   width:100, cellattr:jsFormatterCell}
//				  ]
			//, sortname  : 'ubseq'
		    //, sortorder : "DESC"
			, sortname  : ''
		    , sortorder : ""
		    , sortable:false
			,cmTemplate:{sortable:false}
		    ,autowidth:true
			// , width  : 759
			, height : _sGridHg
			, rowNum : 20	        
		 	, rowList: [20,100]
		    , viewrecords: true
		    , jsonReader : { 
		    	repeatitems : false
		    }
		    , pager : "#"+_sGridMainPager
		    , gridview : true
		    , rownumbers : true
		    , loadtext : "검색 중입니다."
			, emptyrecords : "검색된 데이터가 없습니다."
			, recordtext : "총 {2} 건 데이터 ({0}-{1})"
			//, shrinkToFit : true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, shrinkToFit : ((_sColTotWidth>1180)?false:true)        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, multiselect : sMultChk
			, multiboxonly : sMultChk	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제
//			, onSelectRow:function(rowId, status, eventObject) {							
			, ondblClickRow: function(rowId) {
				//alert("fn_mainGridMake ondblClickRow="+_sDblclickFun+" _sDblclickYn="+_sDblclickYn);
				if (_sTptype == "A" || _sTptype == "C") {
					var sRowData = $("#"+_sGridId).getRowData(rowId);	

					// 더블클릭여부가 'N'인 경우 더블클릭 금지
					if (_sDblclickYn != "Y") return;
					var sRowData = $("#"+_sGridId).getRowData(rowId);	
					var sUbseq = sRowData["ubseq"];

					// 더블클릭 함수
					if (_sDblclickFun != "") {
						var callback_function = new Function(_sDblclickFun);        
						callback_function();
					} else if (sScrinCode == "S0065" || sScrinCode == "S0103") {  // 서비스 관리 > 신청관리 > 개통신청, 변경신청
						SVCMGR.fn_svc_detail_view("UPDATE", "frm_reqst", sGridMainId, sUbseq);
					} else {
						COMMON.fn_view_content(_sFormId, _sDetailFormId, sUbseq, "UPDATE",sScrinCode, sViewExtFun);
					}
					
					// 레이어 팝업 show 시 body 스크롤 hidden
					$("body").css({overflow:'hidden'});
				}else{
					// 더블클릭여부가 'N'인 경우 더블클릭 금지
					if (_sDblclickYn != "Y") return;
					// 더블클릭 함수
					if (_sDblclickFun != "") {
						var callback_function = new Function(_sDblclickFun);		
						callback_function();
					}else if(sScrinCode == "S0077"){
						var sRowData = $("#"+_sGridId).getRowData(rowId);	
						var smonth = sRowData["eday1"].replace("-","").substring(0,6);
						var sCdrseq = sRowData["cdrseq"];
						
						COMMON.fn_cdr_detail_view(smonth, sCdrseq);
					}else if(sScrinCode == "S0038"){
						var sRowData = $("#"+_sGridId).getRowData(rowId);	
						var billYm = sRowData["bill_ym"].replace("-","");
						var sheetSn = sRowData["sheet_sn"];
						var rowno = sRowData["rowno"];
						
						COMMON.fn_raw_detail_view(billYm, sheetSn, rowno);
					}
					
				}				
			  }
		    /* 더블클릭이벤트와 중첩 하단탭을 누르면 갱신
		    , onSelectRow:function(rowId) {
				if (_sTptype == "C") {	
					// 화면ID
					var sScrinCode = $("#"+_sTabFormId).find("#scrin_code").val();
					if (_sClickFun != "") {
						var callback_function = new Function(_sClickFun);		
						callback_function();
					} else {
						COMMON.fn_tabMenu(sGridMainId, sFormId, _sTabFormId, sScrinCode, rowId);
					}				
				}else if(_sTptype == 'A'){
					if (_sClickFun != "") {
						var callback_function = new Function(_sClickFun);		
						callback_function();
					}
				}
			  } 
			  */
		    /* test
		    afterInsertRow: function (rowid, rowdata, rowelem) { // 데이터를 로드할때의 액션
                // 이력이 있는 경우 색상 처리
                if (rowdata.histYn == "Y") {
                    $("#" + rowid).css("background", "#E8D9FF");
                }
            }*/
		    
            , loadComplete : function(data) {
            	//초기 그리드 가로값 세팅
            	$("#gridMain").jqGrid("setGridWidth", $("#container").width());
            	
            	if(false){ //masking,툴팁 사용시 true로 변경  
	            	var rowDatas = $("#gridMain").jqGrid("getRowData");
	            	var colnames = $("#gridMain").jqGrid("getGridParam", 'colNames');
	            	var colModels = $("#gridMain").jqGrid("getGridParam", 'colModel');
	            	//alert("sColMaskListArr : " + sColMaskListArr.length);
	            	//alert("colModels : " + colModels.length);
	            	for (var i=0; i<rowDatas.length; i++) {
	            		for(j=3; j<colModels.length; j++){
	            			var colNm = colModels[j].name;
	            			var maskYn = sColMaskListArr[j-3];
	            			if(maskYn == "Y"){
	            				//$("#gridMain").setCell(rowDatas[i]["ubseq"], colNm, rowDatas[i][colNm]);
	            				$("#gridMain").setCell(rowDatas[i]["ubseq"], colNm, fn_maskval('3', rowDatas[i][colNm]), _sMenuCode);
	            				$("#"+colNm+rowDatas[i]["ubseq"]).attr("title", rowDatas[i][colNm]);
	            			}
	            		}
	            	}
            	}
            	
            	$("#frm_main").find("#mgSearchYn").val("Y");
            	if(data.qResult == "FAIL"){
            		$.unibillDialog.alert('알림', '자료가 없거나 오류가 발생하였습니다.');
            	}else if(data.qResult == "PFAIL"){
            		$.unibillDialog.alert('알림', '조회 조건에 입력 할수 없는 특수 문자가 입력 되었습니다.');
            	}
            	
            	// 그리드 데이터 총 갯수
		        var sTotalRecords = COMMON.fn_getCommaString($("#"+_sGridId).jqGrid("getGridParam","records"));                				
				$("#id_totalRecords").text(sTotalRecords);

				COMMON.fn_scrin_block_ui("UB");				
				
				// JqGrid 내비게이션 rowList 변경시 이벤트 부여.
				$(".ui-pg-selbox").change(function() {
					var searchYn = $("#"+_sFormId).find("#searchYn").val();
					if (searchYn == "Y") {
						COMMON.fn_scrin_block_ui("B");
					}
				});
				
				/*// 화면 템플릿이 통계인 경우
				if (_sTptype == "E") {					
					var sScrinCode = $("#"+_sFormId).find("#scrin_code").val();
					STATS.fn_stats_grid(_sMenuCode, sScrinCode, _sGridId, _sFormId);				
				}*/
				//조회 후 메뉴코드 초기화
            	$("#frm_main").find("#menu_code").remove();
            	
            }		
            , onPaging: function (pgButton) {
            	
            	$("#frm_main").find("#mgSearchYn").val("Y");
            	// 페이지 이동시 mgSearchYn 파라미터 Y세팅
            	var newPage = 1;
            	var reccount = 10;
            	var tot = 0;
            	
            	//test
            	var sTotalRecords = COMMON.fn_getCommaString($("#"+_sGridId).jqGrid("getGridParam","records"));     
            	var mgSearchYn = $("#frm_main").find("#mgSearchYn").val();
            	var searchYn = $("#frm_main").find("#searchYn").val();
            //	 $("#frm_main").find("#mgSearchYn").val("Y");
 		         		        
 			//	$("#id_totalRecords").text(sTotalRecords);
 			//	$("#frm_main").find("#mgTotCnt").val(sTotalRecords);
            	/*
 				tot = $("#"+_sGridId).jqGrid("getGridParam","records");
 				$("#frm_main").find("#mgSearchYn").val("Y");
            	$("#frm_main").find("#mgTotCnt").val(tot);
            	*/
            	//reccount :화면에 표시된수 rowNum:출력할개수
            	//reccount = $("#"+_sGridId).getGridParam("rowNum");
             
            	//alert("onPaging aaa sTotalRecords= "+sTotalRecords +" reccount="+reccount+" mgSearchYn="+mgSearchYn+" searchYn="+searchYn);
            	setTimeout(executeTo, 0); 
/* 				
            	tot = $("#"+_sGridId).jqGrid("getGridParam","records");
            	setTimeout(function() { reccount = $("#"+_sGridId).getGridParam("rowNum"); }, 0);
            	$("#frm_main").find("#mgSearchYn").val("Y");
            	$("#frm_main").find("#mgTotCnt").val(tot);
  */          	
            	 
            	function executeTo(){ 
            		tot = $("#"+_sGridId).jqGrid("getGridParam","records");
     				$("#frm_main").find("#mgSearchYn").val("Y");
                	$("#frm_main").find("#mgTotCnt").val(tot);
            		var nreccount = $("#"+_sGridId).getGridParam("rowNum");  
            //		alert("onPaging bf sTotalRecords= "+sTotalRecords +" reccount="+reccount+" nreccount="+nreccount+" mgSearchYn="+mgSearchYn); 
	 				if(pgButton.indexOf("record") >=0){ 				
	 					/*
	            		newPage = $("#"+_sGridId).getGridParam('page');
	            		//alert("onPaging  newPage="+newPage);
	            		if(tot != 0){
		            		$("#"+_sGridId).jqGrid("setGridParam", {
					      		  datatype: "json"
					      		,page: newPage
					      		,rowNum : nreccount 
					      		, postData: COMMON.fn_get_serialize(_sFormId)
					      		//, postData: {mgSearchYn:flag, mgTotCnt:tot}
					      		, mtype: "POST"
					      	}).trigger("reloadGrid");
		            	}	            		 
		            	*/
	            	}
	 				else   if(pgButton.indexOf("next") >= 0){
	 					
	            		newPage = $("#"+_sGridId).getGridParam('page') + 1;
	            		if(tot != 0){
		            		$("#"+_sGridId).jqGrid("setGridParam", {
					      		  datatype: "json"
					      		,page: newPage
					      		, postData: COMMON.fn_get_serialize(_sFormId)
					      		, mtype: "POST"
					      	}).trigger("reloadGrid");
		            	}
	            	}else if(pgButton.indexOf("prev") >= 0){
	            		
	            		newPage = $("#"+_sGridId).getGridParam('page') - 1;
	            		if(tot != 0){
		            		$("#"+_sGridId).jqGrid("setGridParam", {
					      		  datatype: "json"
					      		,page: newPage
					      		, postData: COMMON.fn_get_serialize(_sFormId)
					      		, mtype: "POST"
					      	}).trigger("reloadGrid");
		            	}
	            	}else if(pgButton.indexOf("last") >= 0){
	            		
	            		newPage = Math.ceil(tot / reccount);
	            		if(tot != 0){
		            		$("#"+_sGridId).jqGrid("setGridParam", {
					      		  datatype: "json"
					      		,page: newPage
					      		, postData: COMMON.fn_get_serialize(_sFormId)
					      		, mtype: "POST"
					      	}).trigger("reloadGrid");
		            	}
	            	}else if(pgButton.indexOf("first") >= 0){
	            		
	            		newPage = 1;
	            		if(tot != 0){
		            		$("#"+_sGridId).jqGrid("setGridParam", {
					      		  datatype: "json"
					      		,page: newPage
					      		, postData: COMMON.fn_get_serialize(_sFormId)
					      		, mtype: "POST"
					      	}).trigger("reloadGrid");
		            	}
	            	}
            	}
            } //onpaging
 
		}).navGrid("#"+_sGridMainPager,{edit:false,add:false,del:false,search:false,refresh:false});	

	}
	
   /**
	* @method
	* @description 공통컨텐츠 하단 그리드 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sTabFormId 데이터가지고 있는 폼아이디
	* @param {String} _sGridId 그리드 id
	* @param {String} _sGridSubPager 그리드 페이지 id
	* @param {String} _sColNames 그리드 col name
	* @param {String} _sColModels 그리드 model name
	* @param {String} _sGridHg 그리드 높이
	*/
	fn_subGridMake = function (_sTabFormId, _sGridId, _sGridSubPager, _sColNames, _sColModels, _sGridHg, _sScrinCode, _sDblclickYn, _sDblclickFun, _sClickFun, _sSubGridSelYn) {
		// 그리드 height
		if (_sGridHg == "0" || _sGridHg == "") _sGridHg = "350";

		// 멀티체크 여부
		var sMultChk = false;
		if (_sSubGridSelYn == "Y") sMultChk = true;
		//alert("_sDblclickFun="+_sDblclickFun+" _sDblclickYn="+_sDblclickYn);
		//alert("_sColNames="+_sColNames+" _sDblclickYn="+_sDblclickYn);
		/*
		var sRowId   = $("#gridMain").getGridParam("selrow");
		var sRowData = $("#gridMain").jqGrid("getRowData",sRowId);
		var gd_id   = sRowData["gd_id"];
		var od_no   = sRowData["od_no"];	
		*/
		// alert("sRowData>  "+sRowData);
		// 그리드 구성
		$("#"+_sGridId).jqGrid({
			  url : CONTEXT_ROOT + '/content/selectTabDataList.json'
			, datatype : "local" 
		//	, data: "sRowData="+sRowData+"&gd_id="+gd_id+"&od_no="+od_no
			, colNames : _sColNames			
			, colModel : _sColModels		   
			, sortname  : 'ubseq'
		    , sortorder : "DESC"
			, sortable:false
			,cmTemplate:{sortable:false}
			// , width  : 1180
			,autowidth:true
			, height : _sGridHg
	        , rowNum : 20	        
		 	, rowList: [20,100]
		    , viewrecords: true
		    , jsonReader : { 
		    	repeatitems : false
		    }
		    , pager : "#"+_sGridSubPager
		    , gridview : true
		    , rownumbers : true
		    , loadtext : "검색 중입니다."
			, emptyrecords : "검색된 데이터가 없습니다."
			, recordtext : "총 {2} 건 데이터 ({0}-{1})"
			, shrinkToFit : false        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, multiselect : sMultChk
			, multiboxonly : sMultChk			
			, ondblClickRow: function(rowId) {				
				//alert("ondblClickRow _sDblclickFun="+_sDblclickFun+" _sDblclickYn="+_sDblclickYn);
				// 더블클릭여부가 'N'인 경우 더블클릭 금지
				if (_sDblclickYn == "N") return;
				
				var sRowData = $("#"+_sGridId).getRowData(rowId);				
				var sUbseq = sRowData["ubseq"];

				// 더블클릭 함수
				if (_sDblclickFun == undefined || _sDblclickFun == "") {
					COMMON.fn_view_content("frm_tab", "frm_detail", sUbseq, "UPDATE", _sScrinCode);
//					COMMON.fn_view_content_dbClick("frm_tab", "frm_detail", sUbseq, "UPDATE", "sub", _sScrinCode);
				} else {					
					var callback_function = new Function(_sDblclickFun);		
					callback_function();
				}
				
			  }
            , loadComplete : function(data) {  
            	//초기 그리드 가로값 세팅
            	$("#gridMain").jqGrid("setGridWidth", $("#container").width());
            	
            	// 그리드 데이터 총 갯수
				$("#id_totalRecords_"+_sScrinCode).html("<h4>조회결과 <span>("+sTotalRecords+"건)</span></h4>");
				 //접수관리 선택 하단 탭 클릭 처리
				var sTotalRecords = COMMON.fn_getCommaString($("#"+_sGridId).jqGrid("getGridParam","records"));  
				if(_sScrinCode=="S0184"||_sScrinCode=="S0185"){
					_sScrinCode="S0143";
				}
				if(_sScrinCode=="S0187"||_sScrinCode=="S0188"){
					_sScrinCode="S0186";
				}
				$("#id_totalRecords_"+_sScrinCode).html("<h4>조회결과 <span>("+sTotalRecords+"건)</span></h4>");

				COMMON.fn_scrin_block_ui("UB");

				// 탭메뉴 그리드 우측상단 버튼 show
				$("#subGridBtn_"+_sScrinCode).show();
				
				// JqGrid 내비게이션 rowList 변경시 이벤트 부여.
				$(".ui-pg-selbox").change(function() {
					COMMON.fn_scrin_block_ui("B");
				});
            }		
		}).navGrid("#"+_sGridSubPager,{edit:false,add:false,del:false,search:false,refresh:true});				
		//alert("fn_subGridMake> 22222222");
	}
	
   /**
	* @method
	* @description 공통컨텐츠 그리드 생성
	* @author 홍길동(2016.10.04)
	* @param {String} _sFormId 데이터가지고 있는 폼아이디
	* @param {String} _sDetailFormId 데이터가지고 있는 상세 폼아이디 
	* @param {String} _sGridId 그리드 id
	* @param {String} _sGridMainPager 그리드 페이지 id
	* @param {String} _sColNames 그리드 col name
	* @param {String} _sColModels 그리드 model name
	* @param {String} _sGridHg 그리드 높이
	*/
	fn_editGridMake = function (_sFormId, _sGridId, _sGridMainPager, _sColNames, _sColModels, _sGridHg, _sGridMakeFun) {
		
		if (_sGridMakeFun != undefined || _sGridMakeFun != "") {
			var callback_function = new Function(_sGridMakeFun);		
			callback_function();
		} else {
			$.unibillDialog.alert('오류', '그리드를 생성할 수 없습니다.');
			return false;
		}
		
	}
	
	fn_user_init = function (){
		var rolecode = $("#topForm").find("#role_code").val();
		var roleLevel = $("#topForm").find("#roleLevel").val();
		
		if(roleLevel >= 9){
			$(".srh_wrap").find("#role_id").val(rolecode);
			$(".srh_wrap").find("#role_id option:not(option:selected)").remove();
		}
		
		
	}
	
	fn_maskval = function(in_masklen, in_val){
		var i_masklen = 0;
		
		if(in_masklen=='0'){
			return in_val;
		}   
		
		var v_showtype=3; //국번 마스킹 뒤에서 4자리 앞
		var v_ret_val = '****';
		var  v_tlen = in_val.length;
		
		if( in_masklen == "0") {
			i_masklen = 0;
		}else if(in_masklen == '%') {i_masklen = Math.trunc(v_tlen/2,0);
		}else{ i_masklen = in_masklen * 1 ;}
		
		var v_showlen = v_tlen-i_masklen;
		if(v_showlen<=0 ){ v_showlen=0;}
		
		
		// 앞에서 처리
		if(v_showtype==0){
			//v_ret_val = rpad(substr(in_val,1+i_masklen),v_tlen,'*') ;
		}else if(v_showtype==1){
				// 뒤에서 처리	    
			    //set v_ret_val = rpad(substr(in_val,1,v_showlen),v_tlen,'*') ;
		}else if(v_showtype==3){ //국번자리 마스킹
				if(v_tlen<=4){ 
					v_ret_val=in_val;
				}else if(v_tlen<=8) { 
					v_ret_val = in_val.substr(0,v_tlen-4).replace(/[0-9]/g,'*').concat(in_val.substr(v_tlen-4));
				}else if(v_tlen==9 && in_val.substr(0,2)=='02') { 
					v_ret_val ='02'.concat(in_val.substr(2,3).replace(/[0-9]/g,'*').concat(in_val.substr(v_tlen-4)));
				}else if(v_tlen==10 && in_val.substr(0,2)=='02') { 
					v_ret_val ='02'.concat(in_val.substr(2,4).replace(/[0-9]/g,'*').concat(in_val.substr(v_tlen-4)));
				}else if(v_tlen==10 && in_val.substr(0,2)!='02') { 
					v_ret_val =in_val.substr(0,2).concat(in_val.substr(2,4).replace(/[0-9]/g,'*')).concat(in_val.substr(v_tlen-4));
				}else {
					v_ret_val = in_val.substr(0,v_tlen-8).concat(in_val.substr(v_tlen-8,4).replace(/[0-9]/g,'*').concat(in_val.substr(v_tlen-4)));
				}	
			    
			  
		}else{   // 중간 한자리
//			i_masklen =truncate(v_tlen/2,0);
//			v_ret_val = in_val.substr(1,i_masklen).concat('*'.concat(in_val.substr(i_masklen+2)));
		}
	    
		return v_ret_val;
	}

	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_content.fn_autoGridPageMove     = fn_autoGridPageMove;
	_mod_content.fn_autoGridSetSelection = fn_autoGridSetSelection;
	_mod_content.fn_mainGridMake         = fn_mainGridMake;
	_mod_content.fn_subGridMake          = fn_subGridMake;
	_mod_content.fn_editGridMake         = fn_editGridMake;
	_mod_content.fn_user_init         	 = fn_user_init;
		
	return _mod_content;

}(UNI.CONTENT || {}, jQuery));