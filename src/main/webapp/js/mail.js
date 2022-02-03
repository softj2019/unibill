/**
 *  컨텐츠에 필요한 이벤트
 * @constructor
 * @namespace {Object} UNI.MAIL
 */
UNI.MAIL = function (_mod_mail, $, undefined) {

    /** @constructor */

    var _vConsole;
    if (typeof console == "undefined") {
        //window.console = {log: function() {}};
        _vConsole = window.console || {
                log: function () {
                }
            };
    } else {
        _vConsole = window.console;
    }
    // /**
    //  * 빈값 첵크
    //  * com.isBlank(" ") //true
    //  * com.isBlank("") //true
    //  * com.isBlank("\n") //true
    //  * com.isBlank("a") //false
    //  * com.isBlank(null) //true
    //  * com.isBlank(undefined) //true
    //  * com.isBlank(false) //true
    //  * com.isBlank([]) //true
    //  */
    // fn_isBlank = function(obj){
    // 	return(!obj || $.trim(obj) === "" || 'null' == obj );
    // }
    //
    //
    // fn_defaultIfBlank = function(str, defaultStr){
    // 	if( this.isBlank(str) ){
    // 		return defaultStr;
    // 	}else{
    // 		return str;
    // 	}
    // }
    //
    // fn_log = function(obj){
    // if(!isLiveMode){
    // 	_vconsole.log("[LOG][console]", obj);
    // }
    // }
    //
    // /**
    //  * 확인창(confirm) 띄움.
    //  * @param {string} msg - 메시지
    //  * @param {function} callback - 콜백함수 (동의한 경우에만 호출됨)
    //  * @param {object} options - 추가옵션 (추후 확장용)
    //  * 	<ul>
    //  *      <li>cancelCalllback : 콜백함수 - null(default)</li>
    //  * 	</ul>
    //  */
    // fn_confirm = function(msg, callback, options) {
    //
    // 	var okCallback = callback;
    //
    // 	if (confirm( msg ) ){
    // 		okCallback();
    // 	}
    // }
    //
    // /**
    //  * 경고창(alert) 띄움.
    //  * @param {string|array} msg - 메시지
    //  * @param {string} type - 유형 (default - warning)
    //  * @param {object} options - 추가옵션 (추후 확장용)
    //  */
    // fn_alert = function(msg, type, options) {
    //
    // 	alert( msg );
    //
    // }

    // fn_objToStringLog = function(obj){
    // 	if( UTILE.fn_isBlank(obj) ){
    // 		fn_log2("[MAIL] obj=", obj );
    //
    // 	}else{
    // 		if ( obj.constructor == Array ){
    // 			var array = $.map( obj , function(value, index) {
    // 				fn_log2("[MAIL] obj.value=", value );
    // 				return [value];
    // 			});
    // 		}else{
    // 			;
    // 			fn_log2("[MAIL] obj.toString()=", obj.toString() );
    // 		}
    // 	}
    // }


//     /**
//      * @method
//      * @description 해당 폼내부 파라메터 값을 배열로 추출
//      * @author 홍길동(2016.10.04)
//      * @param {string} _sFormId : 선택된 form ID
//      */
//     fn_get_serialize = function(_sFormId) {
//
//         var o   = {};
//         var nm  = "";
//         var val = "";
//
//         $("#"+_sFormId).find("input[type='hidden'], input[type='text'], input[type='password'], input[type='checkbox']:checked, input[type='radio']:checked, select,textarea").each( function(){
//             nm = $('#'+_sFormId).find($(this)).attr('id');
//             // mask 처리 된 경우, cleanVal 함수를 통해 마스크 제거된 값을 가져오게 처리
//             try {
//                 val = $('#'+_sFormId).find($(this)).cleanVal();
//             }catch(E){
//                 val = $('#'+_sFormId).find($(this)).val();
//             }
//
// //			alert(_sFormId + "\n" + nm + " : " + val);
//             // class 값으로 int, float, doublt 인 경우, 콤마 제거
//             /*var cs = $('#'+_sFormId).find($(this)).getClasses();
//
//              if( cs.indexOf("DT_INT")!=-1 || cs.indexOf("DT_DOUBLE")!=-1 || cs.indexOf("DT_FLOAT")!=-1 ) {
//              val = val.replace(/[^0-9\.\-]/g,'');
//              }*/
//
//             /*
//              alert("nm ====> " + nm);
//              if (nm.indexOf("mult_check_") > -1) {
//
//              var cnt = $("#"+_sFormId).find("#"+nm).val();
//              alert(nm + " : "  + cnt);
//              if (cnt == "") {
//              var nm2 = nm.replace("mult_check_", "");
//              alert("nm2 : " + nm2);
//              o[nm].push(val || '');
//              }
//              }
//              */
//
//             if (o[nm] !== undefined) {
//                 if (!o[nm].push) {
//                     o[nm] = [o[nm]];
//                 }
//                 o[nm].push(val || '');
//             } else {
//                 o[nm] =val || '';
//             }
//
//         });
//
//         var chkNm = "";
//         $("#"+_sFormId).find("input[type='hidden']").each( function(){
//
//             chkNm = $('#'+_sFormId).find($(this)).attr('id');
//
//             //alert("["+chkNm+"]체크 갯수 : " +  cnt);
//
//             //alert(chkNm + " : " + chkNm.indexOf("mult_check_"));
//             if (chkNm.indexOf("mult_check_") > -1) {
//
//                 var cnt = $("#"+_sFormId).find("#"+chkNm).val();
//
//                 //alert("["+chkNm+"]체크 갯수 : " +  cnt);
//
//                 if (cnt == "") {
//                     var nm2 = chkNm.replace("mult_check_", "");
//                     o[nm2] = '' || '';
//                 }
//             }
//         });
//
//         return o;
//     }

    var _defaultAJAXMethodType = "POST";
    var _defaultGridMethodType = "post"; // 서버 호출 방식 : post , get
    var _defaultGridDataType = "json"; // jqgrid 기본값은 xml 이다.
    var _defaultGridTreeDataType = "jsonstring";

    fn_saveMailBillMakeHist_callback = function () {

        $("#ly_pop").hide();
        $("#frm_main").find("#btn_search").click();
    }

    fn_saveMailBillMakeHist = function() {
        // fn_saveMailBillMakeHist = function(_sFormId, _sMsg, _sUpMsg, _sValiMsg) {

        // alert("fn_saveMailBillMakeHist");

        var _sFormId = "frm_detail";

        $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

        COMMON.fn_submit_createForm( _sFormId, "proc_frm", CONTEXT_ROOT + "/mbMailBillmakehist/saveMailBillMakeHist.do", "MAIL.fn_saveMailBillMakeHist_callback");
    }

    fn_deleteMailBillMakeHist_callback = function () {

        $("#ly_pop").hide();
        $("#frm_main").find("#btn_search").click();
    }

    fn_deleteMailBillMakeHist = function() {
        // fn_deleteMailBillMakeHist = function(_sFormId, _sMsg, _sUpMsg, _sValiMsg) {

        var _sFormId = "frm_detail";
        var _val = $("#"+_sFormId).find("#ubseq").val();

        $("#"+_sFormId).find("#actionFlag").val("DELETE");	// 처리 종류
        $("#"+_sFormId).find("#ubSeqArr").remove();
        $('<input/>').attr({type:'hidden',id:'ubSeqArr',name:'ubSeqArr',value:_val}).appendTo("#"+_sFormId);

        COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/mbMailBillmakehist/saveMailBillMakeHist.do", "MAIL.fn_deleteMailBillMakeHist_callback");
    }

    fn_executeBillingMailDelivery_callback = function () {

        var _sFormId = "frm_main";
        var _sGridId    = "gridMain";       // 상단 grid id

        $("#ly_pop").hide();
        $("#"+_sFormId).find("#ubSeqArr").val("");
        $("#frm_main").find("#btn_search").click();
    }

    fn_executeBillingMailDelivery = function() {
        // fn_executeBillingMailDelivery = function(_sFormId, _sMsg, _sUpMsg, _sValiMsg) {

        // alert("fn_executeBillingMailDelivery");

        var _sFormId = "frm_main";

        var _sGridId    = "gridMain";       // 상단 grid id
        var rowIds   = $("#"+_sGridId).jqGrid("getGridParam","selarrrow");
        var rowDatas = $("#"+_sGridId).jqGrid("getRowData");

        $("#"+_sFormId).find("#ubSeqArr").val(rowIds);      // 대상 ubseq

        $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

        COMMON.fn_submit_createForm( _sFormId, "proc_frm", CONTEXT_ROOT + "/mbMailBillmakehist/executeBillingMailDelivery.do", "MAIL.fn_executeBillingMailDelivery_callback");
    }
    /**
     * @description 그리드 조회( 내부적으로 Ajax 를 호출하는 방식 ).
     * @method fn_searchGrid
     * @param {string} gridId - 대상 Grid ID
     * @param {string} reqUrl - 호출 서버 URL
     * @param {object} param - 전송할 데이터
     * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수
     * @param {object} ajaxOptions - 추가 옵션
     * @example MAIL.fn_searchGrid("myGrid01","/cmCfggrpid/listCmCfggrpidJson.do", $("#searchFrm").serializeArray() , null, null);
     * @example MAIL.fn_searchGrid("myGrid01","/cmCfggrpid/listCmCfggrpidJson.do", $("#searchFrm").serializeArray() , function(obj, resultData, textStatus, jqXHR){alert(textStatus);}, { async : true, type  : "POST"} );
     */
    fn_searchGrid = function (gridId, reqUrl, param, callback, ajaxOptions) {

        UTILE.fn_log("[MAIL.fn_searchGrid] [" + gridId + "] reqUrl=" + reqUrl + " ================Start=============");
        UTILE.fn_log2("[MAIL.fn_searchGrid] [" + gridId + "] reqUrl=", reqUrl);
        //_vconsole.log("[MAIL.fn_searchGrid] [" + gridId + "] param=", UTILE.fn_objToStringLog(param));
        UTILE.fn_log2("[MAIL.fn_searchGrid] [" + gridId + "] callback=", callback);
        UTILE.fn_log2("[MAIL.fn_searchGrid] [" + gridId + "] ajaxOptions=", ajaxOptions);

        if (UTILE.fn_isBlank(gridId)) {
            UTILE.fn_alert("그리드 ID를 지정해주세요.");
        } else {


            // var _vGridId = "#" + gridId;
            var $_vGridId = $("#" + gridId);

            /* 조회 */
            // var reqUrl = "/cmCfggrpid/listCmCfggrpidJson.do";
            // var param = $("#searchFrm").serializeArray();

            var _thisGridOptions = $("#"+gridId).jqGrid('getGridParam');
            // UTILE.fn_objToStringLog2("[MAIL.fn_searchGrid]" , _thisGridOptions );
            UTILE.fn_log2("[MAIL.fn_searchGrid] _thisGridOptions_id ==>> " , _thisGridOptions.id );
            UTILE.fn_log2("[MAIL.fn_searchGrid] _thisGridOptions_datatype ==>> " , _thisGridOptions.datatype );
            UTILE.fn_log2("[MAIL.fn_searchGrid] _thisGridOptions_url ==>> " , _thisGridOptions.url );



            ajaxOptions = $.extend({}, {
                async: true,
                type: _defaultAJAXMethodType,
                traditional: true,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                resultTypeCheck: true,
                spinner: true,
                dimmed: false,
                errorCallback: null,
                timeout: 30000			// 기본 30초
            }, ajaxOptions);

            UTILE.fn_ajax(reqUrl, param, function (obj, resultData, textStatus, jqXHR) {

                UTILE.fn_log2("[MAIL.fn_searchGrid] [" + gridId + "] jqXHR.status = " , jqXHR.status );

                if (200 == jqXHR.status) {
                    var gridArrayData = [];
                    var itemList = resultData.rows;

                    // data 셋팅 방법 1( 서버로 ajax 재호출후에는 datatype이 local로 바뀌므로 셋팅 방법2와 같이 datatype : 'json'을 해주어야 ajax로 서버로 부터 받은 값을 표현할 수 있다. )
                    // $_vGridId[0].addJSONData({rows: {}});
                    // $_vGridId[0].addJSONData({rows: itemList});

                    // data 셋팅 방법 2
                    $_vGridId.jqGrid('clearGridData');
                    // $("#grid").jqGrid("setGridParam",{postData:{}});     // http://www.trirand.com/blog/?page_id=393/bugs/jqgrid-4-0-replacement-for-setpostdata-incorrect
                    // $("#grid").jqGrid("setGridParam",{postData: null }); // http://www.trirand.com/blog/?page_id=393/bugs/jqgrid-4-0-replacement-for-setpostdata-incorrect
                    $_vGridId.jqGrid('setGridParam', {datatype:_defaultGridDataType});
                    $_vGridId[0].addJSONData({
                        rows: itemList
                        , page: resultData.page
                        , total: resultData.total
                        , records: resultData.records
                    });

                    // data 셋팅 방법 3(스크립트 오류는 없으나 grid에 data가 들어가지 않음!! )
                    // $_vGridId.jqGrid('setGridParam', {
                    //     datatype: 'local',
                    //     rows:itemList
                    // }).trigger("reloadGrid");


                    // 그리드 데이터 총 갯수
                    var sTotalRecords = $_vGridId.jqGrid("getGridParam", "records");
                    $("#id_totalRecords").text(sTotalRecords);

                    // UTILE.fn_log2("[MAIL.fn_searchGrid] [" + gridId + "]", "getDataIDs : " + $_vGridId.jqGrid('getDataIDs')); //return Ids

                    if (!UTILE.fn_isBlank(callback)) {
                        if (typeof callback == 'function') {
                            UTILE.fn_log("[MAIL.fn_searchGrid] [" + gridId + "][Data Callback]");
                            // callback.call(this, data, jqXHR, textStatus);
                            // callback.call(this, data, textStatus, jqXHR);
                            eval(callback(obj, resultData, textStatus, jqXHR));
                        }

                    }

                    UTILE.fn_log("[MAIL.fn_searchGrid] [" + gridId + "] ================End=============");
                }else{

                    UTILE.fn_log("[MAIL.fn_searchGrid] [" + gridId + "] ================End=============");
                }
            }, ajaxOptions);
        }
    }
    
    
    
    fn_makeMail = function (){
    	if(!MAIL.fn_mailParamValidate()){
    		return;
    	}
    	$("#mail_pop").find("#s_custid").val("");
    	$("#frm_mailDetail").find("#jobmsg").text("");
		$("#frm_mailDetail").find(".progressbar").css("width",0+"%");
		$("#frm_mailDetail").find(".progressbar").text(0 + "%");
		$("#frm_mailDetail").find("#error").html(0 + "/ <span class='t_red'>" + 0 + "</span>");
		
    	$("#custMailgrid").clearGridData();
    	
    	// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#mail_pop .confirm_cont").width())/5;
		var ty = ($(window).height())/2;
		if(ty < 0){
			ty = 15;
		}
		$("#mail_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#mail_pop").find("#buttonFlag").val("R");
		
		if($(".srh_wrap").find("#job_type").find("option:selected").val() == "101"){
			$(".send").show();
			$(".make").hide();
		}else{
			$(".send").hide();
			$(".make").show();
		}
		
		if(returnID == null){
			//$("#mailExecute").show();
			/*$("#mailStop").hide();*/
		}else{
			//$("#mailExecute").hide();
			/*$("#mailStop").show();*/
		}
		
		$("#mail_pop").show();  // 레이어 팝업 open
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
    
    fn_mailSetTime = function(){
    	MAIL.fn_MailCheckRunInit();
    	MAIL.fn_MailCheckRun();
    	
		returnID = setInterval(MAIL.fn_MailCheckRun,2000);
    }
    
    fn_MailCheckRunInit = function(){
    	$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/mail/checkRunInit.do',
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				
			},
			error: function(xhr, status, error) {
				alert("작업상황초기화 에러");
			}
		});	
    }
    
    fn_MailCheckRun = function(){
    	//메일생성 작업 상황 체크
    	$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/mail/checkRun.do',
			datatype : "json",
			jsonp : "callback",
			async: true,
			success : function(data) {
				UTILE.fn_autoLogout_reset();
				isRun =  data.reqStatus;
				if(isRun == false){
					//진행률표시
					//alert(data.jobmsg +  " : " + data.progress +  " : " + data.totalcnt +  " : " + data.errorcnt);
					$("#frm_mailDetail").find("#jobmsg").text(data.jobmsg);
					$("#frm_mailDetail").find(".progressbar").css("width",data.progress+"%");
					$("#frm_mailDetail").find(".progressbar").text(data.progress + "%");
					$("#frm_mailDetail").find("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
					
				}else{
					clearInterval(returnID);
					returnID = null;
					$("#frm_mailDetail").find("#jobmsg").text(data.jobmsg);
					$("#frm_mailDetail").find(".progressbar").css("width",data.progress+"%");
					$("#frm_mailDetail").find(".progressbar").text(data.progress + "%");
					$("#frm_mailDetail").find("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
					//$("#mailExecute").show();
					/*$("#mailStop").hide();*/
					
					
				}
				
				/*$("#jobmsg").text(data.jobmsg);
				$(".progressbar").css("width",data.progress+"%");
				$(".progressbar").text(data.progress + "%");
				$("#error").html(data.totalcnt + "/ <span class='t_red'>" + data.errorcnt + "</span>");
				*/
			},
			error: function(xhr, status, error) {
				//alert("작업상황체크 에러");
			}
		});	
    }
    
    fn_Mailmake = function(){
    	var formData = new FormData();
		
		var bill_ym = $(".srh_wrap").find("#bill_ym option:selected").val();
		var job_type = $(".srh_wrap").find("#job_type option:selected").val();
		
		var ids = jQuery("#custMailgrid").jqGrid('getDataIDs');
		var rowDatas = $("#custMailgrid").jqGrid("getRowData");
		var rCustIdList = [];
		if(ids.length == 0){
			formData.append("isRcustid" , "false");
		}else{
			for (var i=0; i<rowDatas.length; i++) {
				rCustIdList.push(rowDatas[i]["rep_cust_id"]);
			}
			formData.append("isRcustid" , "true");
			formData.append("rCustIdList" , rCustIdList);
			
		}
		formData.append("billym" , bill_ym);
		formData.append("jobType" , job_type);
		
		
		
		xhr = $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/mail/mailMake.do',
				async: true,
				processData: false,
				contentType: false, 
				data: formData,
				success : function(data) { 
					
				},
				error: function(xhr, status, error) { alert("prcessError");}
			});
    }
    
    fn_MailSend = function(){
    	var formData = new FormData();
		
		var bill_ym = $(".srh_wrap").find("#bill_ym option:selected").val();
		var job_type = $(".srh_wrap").find("#job_type option:selected").val();
		var ids = jQuery("#custMailgrid").jqGrid('getDataIDs');
		var rowDatas = $("#custMailgrid").jqGrid("getRowData");
		var rCustIdList = [];
		if(ids.length == 0){
			formData.append("isRcustid" , "false");
		}else{
			for (var i=0; i<rowDatas.length; i++) {
				rCustIdList.push(rowDatas[i]["rep_cust_id"]);
			}
			formData.append("isRcustid" , "true");
			formData.append("rCustIdList" , rCustIdList);
			
		}
		formData.append("billym" , bill_ym);
		formData.append("jobType" , job_type);
		
		
		xhr = $.ajax({
				type: 'POST',
				url: CONTEXT_ROOT + '/mail/mailSend.do',
				async: true,
				processData: false,
				contentType: false, 
				data: formData,
				success : function(data) { 
					
				},
				error: function(xhr, status, error) { alert("prcessError");}
			});
    }
    
    fn_mailExcute = function(){
    	//메일 생성이 되고 있으면 메세지 표시
    	if(returnID == null){
    		//$("#mailExecute").hide();
        	/*$("#mailStop").show();*/
        	
        	if($(".srh_wrap").find("#job_type option:selected").val() == "100"){
        		//메일생성
        		MAIL.fn_Mailmake();
        		//타이머 시작
            	MAIL.fn_mailSetTime();
        	}else{
        		//메일전송
        		MAIL.fn_MailSend();
            	//타이머 시작
            	MAIL.fn_mailSetTime();
        	}
        }else{
        	$.unibillDialog.alert('오류', "수행하는 작업이 있습니다.");
    	}
    	
    } 
    
    //test
    fn_BillView = function() { 
/*
		var str = ""; 
		str +=  "<table>"; 
		str +=  "<tr>"; 
		str +=  "<td>"; 
		str +=  "<a href=\"/data/files/bill/mail/201902/bill201902_632_10.html\" onclick=\"\" > </a> "; 
		str +=  "</td>"; 
		str +=  "</tr>"; 
		str +=  "</table>"; 
		document.getElementById("inHere").innerHTML = str; 
   */
    	 
		//var sFormId = "frm_main";
		
		// 검색 시 객체 필수 입력값 검증
		//if (!COMMON.fn_form_obj_validate(sFormId)) return;
				
		var specs = "left=300,top=10,width=800,height=700";
		specs += ",toolbar=no,menubar=no,status=no,scrollbars=yes,resizable=yes, location=no";
		
		//window.open("/delivery/board/UserAgreeDoc.do", "_blank", specs);
		window.open("d://data/files/bill/mail/201902/bill201902_632_10.html", "_blank", specs);	
		//window.open("file:///D:/data/files/bill/mail/201902/bill201902_632_10.html", "_blank", specs);		 
		
	}
    
    fn_mailStop = function(){ 
    	if(returnID == null){
    		$.unibillDialog.alert('오류', "중지할 작업이 없습니다.");
    	//	$("#mailExecute").show();
		//	$("#mailStop").hide();
    	}else{
    		$.ajax({
    			type: 'POST',
    			url: CONTEXT_ROOT + '/mail/mailStop.do',
    			datatype : "json",
    			jsonp : "callback",
    			async: true,
    			success : function(data) {
    				isStop =  data.reqStatus;
    				if(isStop == true){
    					//alert("정지가 완료 되었습니다.");
    					clearInterval(returnID);
    					returnID = null;
    					//$("#mailExecute").show();
    					//$("#mailStop").hide();
    				}else{
    					//alert("정지중입니다.");
    					//$("#mailExecute").hide();
    					//$("#mailStop").show();
    					
    				}
    			},
    			error: function(xhr, status, error) {
    				alert("작업중지 에러");
    			}
    		});	
    		
    		
    		
    	}
    }
    
    
    fn_custAdd = function(){
    	if($("#frm_mailDetail").find("#s_custid").val() == ""){
    		$.unibillDialog.alert('오류', "고객을 입력하세요.");
			return false;
    	}
    	var ids = jQuery("#custMailgrid").jqGrid('getDataIDs');
    	var id = 1;
    	
    	for(var i=0; i<ids.length; i++){
    		if(i == 0 ){
    			id = ids[i]*1
    		}else{
    			if(id < ids[i]*1) id = ids[i]*1;
    		}
    		id++;
    	}

    	if($("#frm_mailDetail").find("#s_custid").val().indexOf(",") > 0){
    		var custidList = $("#frm_mailDetail").find("#s_custid").val().split(",");
    		for(var i=0; i<custidList.length; i++){
    			var rowdata = {"rep_cust_id":custidList[i]};
            	$("#custMailgrid").jqGrid("addRowData", id, rowdata, 'last');
            	id++;
    		}
    	}else{
    		var rowdata = {"rep_cust_id":$("#frm_mailDetail").find("#s_custid").val()};
        	$("#custMailgrid").jqGrid("addRowData", id, rowdata, 'last');
    	}
    	
    	
    	$("#frm_mailDetail").find("#s_custid").val("");
    	
    }
    
    fn_custDel = function(){
    	var selects = $( "#custMailgrid" ).jqGrid('getGridParam', 'selarrrow');
    	var sellen = new Array();
    	
    	for(var i=0; i<selects.length; i++){
    		sellen[i] = selects[i];
    	}
    	for(var i=0; i<sellen.length; i++){
    		$('#custMailgrid').jqGrid('delRowData',sellen[i]);
    	}
    }
    
    // 고객 정보 가져오기
    fn_searchCust = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#ly_cust_pop .confirm_cont").width())/2;
		var ty = ($(window).height())/7;
		if(ty < 0){
			ty = 15;
		}
		$("#ly_cust_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		
		$("#ly_cust_pop").show();  // 레이어 팝업 open
	}
    
    search_cust = function(){
    	var bill_ym = $(".srh_wrap").find("#bill_ym option:selected").val();
    	$("#frm_cust").find("#s_billYm").val(bill_ym);
    	
    	// 그리드 초기화
		COMMON.fn_clear_grid_data("custGrid");
		
		COMMON.fn_search_mainGrid("frm_cust", "custGrid");
		
		$("#gridCustPager"). find(".selectBox-dropdown").css("width",50);
		$("#gridCustPager"). find(".selectBox-label").css("width",60);
		$("#gridCustPager"). find(".selectBox-arrow").css({"top" : "-5px"});
		
		COMMON.fn_scrin_block_ui("UB");	
		$("#id_loader").hide();
    }
    
   
    //------------------------------------------------------------------------------------------------------------------
    //## public 메소드
    // public 메소드 으로 등록되지 않았을때 아래와같이 스크립트 오류가 발생
    //     function anonymous() {
    //         MAIL.fn_saveMailBillMakeHist()
    //     }
    //------------------------------------------------------------------------------------------------------------------
    _mod_mail.fn_saveMailBillMakeHist = fn_saveMailBillMakeHist;
    _mod_mail.fn_saveMailBillMakeHist_callback = fn_saveMailBillMakeHist_callback;
    _mod_mail.fn_executeBillingMailDelivery = fn_executeBillingMailDelivery;
    _mod_mail.fn_executeBillingMailDelivery_callback = fn_executeBillingMailDelivery_callback;
    _mod_mail.fn_deleteMailBillMakeHist = fn_deleteMailBillMakeHist;
    _mod_mail.fn_deleteMailBillMakeHist_callback = fn_deleteMailBillMakeHist_callback;

    
    _mod_mail.fn_makeMail 			= 	fn_makeMail;
    _mod_mail.fn_mailExcute			=	fn_mailExcute;
    _mod_mail.fn_mailParamValidate	=	fn_mailParamValidate;
    _mod_mail.fn_mailStop			=	fn_mailStop;
    _mod_mail.fn_mailSetTime		=	fn_mailSetTime;
    _mod_mail.fn_MailCheckRunInit 	= 	fn_MailCheckRunInit;
    _mod_mail.fn_MailCheckRun		=	fn_MailCheckRun;
    _mod_mail.fn_Mailmake			=	fn_Mailmake;
    _mod_mail.fn_MailSend 			= 	fn_MailSend;
    
    _mod_mail.fn_custAdd 			= 	fn_custAdd;
    _mod_mail.fn_custDel 			=	fn_custDel;
    _mod_mail.fn_searchCust 		= 	fn_searchCust;
    _mod_mail.search_cust 			= 	search_cust;
    
    return _mod_mail;

}(UNI.MAIL || {}, jQuery);