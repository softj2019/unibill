/**
 *  정산 관리에 필요한 이벤트
 * @constructor
 * @namespace {Object} UNI.HISWEBREQ
 */
UNI.HISWEBREQ = function (_mod_mail, $, undefined) {

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

    var _defaultAJAXMethodType = "POST";
    var _defaultGridMethodType = "post"; // 서버 호출 방식 : post , get
    var _defaultGridDataType = "json"; // jqgrid 기본값은 xml 이다.
    var _defaultGridTreeDataType = "jsonstring";

    // fn_hiswebreqFindday = function() {
    fn_hiswebreqFindday = function(sFormId, sColId) {
        var _val = $("#"+sFormId).find("#" + sColId).val();
        // alert("fn_hiswebreqFindday _val=" + _val);

        // var aa1 = $("#"+sFormId).find("#" + sColId).parents("div").hidden();

        if("1" == _val ){
            // 1 : 요청일자(조회작업)
            $("#"+sFormId).find("#bill_ym_mm").selectBox('value', '' );
            $("#"+sFormId).find("#bill_ym_mm").selectBox('disable');

            $("#"+sFormId).find("#bill_ym_yyyy").selectBox('value', '' );
            $("#"+sFormId).find("#bill_ym_yyyy").selectBox('disable');

            $("#"+sFormId).find("#div_search_bill_ym_yyyy").hide();
            $("#"+sFormId).find("#div_search_bill_ym_mm").hide();

            $("#"+sFormId).find("#sday").attr("disabled",false);
            $("#"+sFormId).find("#eday").attr("disabled",false);
            $("#"+sFormId).find("#div_search_sday1").show();
            $("#"+sFormId).find("#div_search_eday1").show();

            $("#"+sFormId).find("#btn_search").show();
            $("#"+sFormId).find("#btn_hiswebreqMakeRun").hide();

            $("#"+sFormId).find("#req_status").selectBox("enable");
            $("#"+sFormId).find("#div_search_req_status").show();

        }else if("2" == _val ){
            //2 : 정산월(정산작업)
            $("#"+sFormId).find("#sday").val("");
            $("#"+sFormId).find("#sday").attr("disabled",true);

            $("#"+sFormId).find("#eday").val("");
            $("#"+sFormId).find("#eday").attr("disabled",true);

            $("#"+sFormId).find("#div_search_sday1").hide();
            $("#"+sFormId).find("#div_search_eday1").hide();

            $("#"+sFormId).find("#bill_ym_yyyy").selectBox("enable");
            $("#"+sFormId).find("#bill_ym_mm").selectBox("enable");

            $("#"+sFormId).find("#div_search_bill_ym_yyyy").show();
            $("#"+sFormId).find("#div_search_bill_ym_mm").show();


            // $("#"+sFormId).find("#btn_search").hide();
            $("#"+sFormId).find("#btn_search").show();
            $("#"+sFormId).find("#btn_hiswebreqMakeRun").show();

            $("#"+sFormId).find("#req_status").selectBox('value', '' );
            $("#"+sFormId).find("#req_status").selectBox('disable');
            $("#"+sFormId).find("#div_search_req_status").hide();

        }
    }

    /**
     * 작업 유형에대한 처리.
     * @param sFormId
     * @param sColId
     */
    fn_hiswebreqJobType = function(sFormId, sColId) {
        var _val = $("#"+sFormId).find("#" + sColId).val();
//         alert("fn_hiswebreqFindday _val=" + _val);

        // var aa1 = $("#"+sFormId).find("#" + sColId).parents("div").hidden();

/**        if("5" == _val || "11" == _val ){
            $("#"+sFormId).find("#cust_id_param").val("");
            $("#"+sFormId).find("#cust_id_param").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_id_param").show();

        }else **/
        if("10" == _val){
            $("#"+sFormId).find("#bill_ym_mm").selectBox('value', '' );
            $("#"+sFormId).find("#bill_ym_mm").selectBox('disable');

            $("#"+sFormId).find("#bill_ym_yyyy").selectBox('value', '' );
            $("#"+sFormId).find("#bill_ym_yyyy").selectBox('disable');
            
        	$("#"+sFormId).find("#div_search_bill_ym_yyyy").hide();
            $("#"+sFormId).find("#div_search_bill_ym_mm").hide();
            $("#"+sFormId).find("#sday_datepicker").attr("disabled",false);
            $("#"+sFormId).find("#div_search_sday").show();
        }else{
            $("#"+sFormId).find("#bill_ym_yyyy").selectBox("enable");
            $("#"+sFormId).find("#bill_ym_mm").selectBox("enable");
            $("#"+sFormId).find("#div_search_bill_ym_yyyy").show();
            $("#"+sFormId).find("#div_search_bill_ym_mm").show();

            $("#"+sFormId).find("#sday_datepicker").val("");
            $("#"+sFormId).find("#sday_datepicker").attr("disabled",true);
            $("#"+sFormId).find("#div_search_sday").hide();
        }
    }

    fn_hiswebreqMakeRun_callback = function () {

        UTILE.fn_alert("정산작업 실행 되었습니다.", "", {});

        $("#frm_main").find("#btn_search").click();
    }

    fn_hiswebreqMakeRun = function() {
        // fn_hiswebreqMakeRun = function(_sFormId, _sMsg, _sUpMsg, _sValiMsg) {

        var _sFormId = "frm_detail";
        var _val = $("#"+_sFormId).find("#ubseq").val();


        var _hiswebreq_findday = $("#"+sFormId).find("#hiswebreq_findday").val();
        if( "2" != _hiswebreq_findday ){
            UTILE.fn_alert("기간 유형을 입력해주세요.\n(정산월을 선택해주세요.)", "W", {});
            return false;
        }


        var _job_type = $("#"+sFormId).find("#job_type").val();
        if( "4" != _job_type ){
            UTILE.fn_alert("작업 유형을 입력해주세요.\n(통신사파일 정산만 가능한 메뉴입니다.)", "W", {});
            return false;
        }


        var _bill_ym_yyyy = $("#"+sFormId).find("#bill_ym_yyyy").val();
        var _bill_ym_mm = $("#"+sFormId).find("#bill_ym_mm").val();
        if( UTILE.fn_isNotBlank(_bill_ym_yyyy) && UTILE.fn_isNotBlank(_bill_ym_mm) ){

            var _bill_ym = _bill_ym_yyyy + _bill_ym_mm;

            $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류
            $('<input/>').attr({type:'hidden',id:'bill_ym_yyyy',name:'bill_ym_yyyy',value:_bill_ym_yyyy}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'bill_ym_mm',name:'bill_ym_mm',value:_bill_ym_mm}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'bill_ym',name:'bill_ym',value:_bill_ym}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'job_type',name:'job_type',value:_job_type}).appendTo("#"+_sFormId);

            COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/cmHiswebreq/saveCmHiswebreq.do", "HISWEBREQ.fn_hiswebreqMakeRun_callback");

            $("#frm_main").find("#btn_search").click();
        }else{
            UTILE.fn_alert("정산 작업 년 월을 입력해주세요.", "W", {});
        }

    }

    fn_hiswebreqMakeRunValidation = function () {
        var msg = "";

        return msg;
    }

    fn_hiswebreqIIACMakeRun_callback = function () {

        UTILE.fn_alert("정산작업 실행 되었습니다.", "", {});

        $("#frm_main").find("#btn_search").click();


        // var aa = parent.location;
        // parent.location.reload();
        // document.location.reload();
    }

    /**
     * fn_hiswebreqMakeRun 와 차이점은 submit URL만 틀림.(2017.04.03)
     * @returns {boolean}
     */
    fn_hiswebreqIIACMakeRun = function() {
        // fn_hiswebreqIIACMakeRun = function(_sFormId, _sMsg, _sUpMsg, _sValiMsg) {
 
        var _sFormId = "frm_detail";
        var _val = $("#"+_sFormId).find("#ubseq").val();
        var _bill_ym = $("#"+sFormId).find("#bill_ym").val();
        var _job_type = $("#"+sFormId).find("#job_type").val();
        var _svc_id = $("#"+sFormId).find("#svc_id").val();
        var _rep_cust_id = $("#"+sFormId).find("#rep_cust_id").val(); 
        var _autotrans_month = $("#"+sFormId).find("#autotrans_month").val();
        
        if( "" == _job_type ){
            UTILE.fn_alert("작업 유형을 입력해주세요.", "W", {});
            return false;
        }
        
        if( "" == _svc_id ){
            UTILE.fn_alert("서비스ID를 입력해주세요.", "W", {});
            return false;
        }
        
        if( "" == _bill_ym ){
            UTILE.fn_alert("청구월을 입력해주세요.", "W", {});
            return false;
        }
        
        $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류
        $("#"+_sFormId).find("#bill_ym").remove();
        $("#"+_sFormId).find("#billYm").remove();
        $("#"+_sFormId).find("#job_type").remove();
        $("#"+_sFormId).find("#rep_cust_id").remove();
        $("#"+_sFormId).find("#svc_id").remove();
        $("#"+_sFormId).find("#autotrans_month").remove();
        $('<input/>').attr({type:'hidden',id:'billYm',name:'billYm',value:_bill_ym}).appendTo("#"+_sFormId);
        $('<input/>').attr({type:'hidden',id:'job_type',name:'job_type',value:_job_type}).appendTo("#"+_sFormId);
        $('<input/>').attr({type:'hidden',id:'svc_id',name:'svc_id',value:_svc_id}).appendTo("#"+_sFormId);
        $('<input/>').attr({type:'hidden',id:'rep_cust_id',name:'rep_cust_id',value:_rep_cust_id}).appendTo("#"+_sFormId);
        $('<input/>').attr({type:'hidden',id:'autotrans_month',name:'autotrans_month',value:_autotrans_month}).appendTo("#"+_sFormId);
        
        
        /*if( "" == _job_type ){
            UTILE.fn_alert("작업 유형을 입력해주세요.", "W", {});
            return false;
        }else {
             if("10" == _job_type){
                 if("" == _date){
                     UTILE.fn_alert("정산일을 입력해 주세요.", "W", {});
                     return false;
                 }
                 _bill_ym_yyyy = _date.substr(0, 4);
                 _bill_ym_mm = _date.substr(5, 2);
                 _sDate = _date.substr(0, 4)+_bill_ym_mm+_date.substr(8, 2);
             }else{            	 
	             if("" == _bill_ym_yyyy){
	                 UTILE.fn_alert("년도 입력해 주세요.", "W", {});
	                 return false;
	             }
	             if("" == _bill_ym_mm){
	                 UTILE.fn_alert("월을 입력해 주세요.", "W", {});
	                 return false;
	             }
             }
        }*/
        //_bill_ym = _bill_ym_yyyy + _bill_ym_mm;
       // var _cust_id_param = $("#"+sFormId).find("#rep_cust_id").val();
       /* if("10" == _job_type){
            $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

            //$("#"+_sFormId).find("#bill_ym_yyyy").remove();
            //$("#"+_sFormId).find("#bill_ym_mm").remove();
            $("#"+_sFormId).find("#bill_ym").remove();
            //$("#"+_sFormId).find("#sday_datepicker").remove();
            //$("#"+_sFormId).find("#sday").remove();
            $("#"+_sFormId).find("#job_type").remove();
            $("#"+_sFormId).find("#rep_cust_id").remove();
            
            $('<input/>').attr({type:'hidden',id:'bill_ym_yyyy',name:'bill_ym_yyyy',value:_bill_ym_yyyy}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'bill_ym_mm',name:'bill_ym_mm',value:_bill_ym_mm}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'billYm',name:'billYm',value:_bill_ym}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'sday',name:'sday',value:_sDate}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'job_type',name:'job_type',value:_job_type}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'cust_id_param',name:'cust_id_param',value:_cust_id_param}).appendTo("#"+_sFormId);
        }else{
            $("#"+_sFormId).find("#actionFlag").val("INSERT");	// 처리 종류

            $("#"+_sFormId).find("#bill_ym_yyyy").remove();
            $("#"+_sFormId).find("#bill_ym_mm").remove();
            $("#"+_sFormId).find("#bill_ym").remove();
            $("#"+_sFormId).find("#job_type").remove();
            $("#"+_sFormId).find("#cust_id_param").remove();

            $('<input/>').attr({type:'hidden',id:'bill_ym_yyyy',name:'bill_ym_yyyy',value:_bill_ym_yyyy}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'bill_ym_mm',name:'bill_ym_mm',value:_bill_ym_mm}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'bill_ym',name:'bill_ym',value:_bill_ym}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'job_type',name:'job_type',value:_job_type}).appendTo("#"+_sFormId);
            $('<input/>').attr({type:'hidden',id:'cust_id_param',name:'cust_id_param',value:_cust_id_param}).appendTo("#"+_sFormId);

        }*/
        COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/cmHiswebreq/saveCmHiswebreqIIAC.do", "HISWEBREQ.fn_hiswebreqIIACMakeRun_callback");
        	
    }

    fn_hiswebreqIIACMakeRunValidation = function () {
        var msg = "";

        return msg;
    }


    fn_hiswebreqIIACMakeCancel_callback = function () {

        // UTILE.fn_alert("정산작업 실행 되었습니다.", "", {});
        $("#frm_main").find("#btn_search").click();
    }

    fn_hiswebreqIIACMakeCancel = function() {

        // var _sFormId = "frm_main";
        // var _sChgFormId = "frm_detail";
        var _sGridId = "gridMain";


        var i, selRowIds = $("#" + _sGridId).jqGrid("getGridParam", "selarrrow"), n, rowData;
        var cm = $("#" + _sGridId).jqGrid('getGridParam', 'colModel');
        if (selRowIds.length == 0) {
            $.unibillDialog.alert('알림', '선택한 행이 없습니다.');
            return;
        }

        UTILE.fn_log("selRowIds :" + selRowIds );

        var ubSeqArr = [];
        for (i = 0, n = selRowIds.length; i < n; i++) {
            rowData = $("#" + _sGridId).getRowData(selRowIds[i]);
            // UTILE.fn_log("rowData : " + rowData);

            var _ubseq = "";
            var _reqstatus = "";
            var _progress = "";
            for( var iCol=0; iCol < cm.length; iCol++ ){
                var colNm = cm[iCol].name;
                var colVal = rowData[colNm];
                // UTILE.fn_log("colNm:" + colNm + "=" + colVal );

                if('req_status' == colNm ){
                    _reqstatus = colVal;
                } else if('progress' == colNm ){
                    _progress = colVal;
                } else if('ubseq' == colNm ){
                    _ubseq = colVal;
                }
            }
            if( "R" == _reqstatus || "P" == _reqstatus || "T" == _reqstatus ){
            	ubSeqArr.push(_ubseq);
            }
        }


        UTILE.fn_objToStringLog(ubSeqArr);

        var _sFormId = "frm_detail";
        $("#"+_sFormId).find("#actionFlag").val("WORK_CANCEL");	// 처리 종류
        $("#"+_sFormId).find("#req_status").remove();
        $("#"+_sFormId).find("#ubSeqArr").remove();
        $('<input/>').attr({type:'hidden',id:'req_status',name:'req_status',value:'R'}).appendTo("#"+_sFormId);
        $("#"+_sFormId).find("#ubSeqArr2").remove();
        $('<input/>').attr({type:'hidden',id:'ubSeqArr2',name:'ubSeqArr2',value:ubSeqArr}).appendTo("#"+_sFormId);

        COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/cmHiswebreq/saveCmHiswebreqIIAC.do", "HISWEBREQ.fn_hiswebreqIIACMakeCancel_callback");
    }

    fn_hiswebreqIIACMakeCancelValidation = function () {
        var msg = "";

        return msg;
    }
    
    fn_selectTerminationDtlList = function(){
    	var rowid   = $("#gridMain").getGridParam("selrow");
		var data = jQuery("#gridMain").getRowData(rowid);
		
		$("#frm_tab").find("#cust_id").remove();
		$('<input/>').attr({type:'hidden',id:'cust_id',name:'cust_id',value:data.cust_id}).appendTo("#frm_tab");
		
		COMMON.fn_tabMenu('gridMain','frm_main','frm_tab', 'S0153', rowid);
    }

    hotbill_proc = function(){
    	var rowId = $('#gridMain').getGridParam('selrow');
    	var data = $('#gridMain').getRowData(rowId);
    	
    	var url = CONTEXT_ROOT + '/cmHiswebreq/hotbill_proc.json';
    	var p_data = 'cust_id=' + data.cust_id;
    	
    	COMMON.fn_ajaxQueryString(url, p_data, function(result){
    		
    	
    		// 레이어 팝업 띄우기				 
    		var tx = ($(window).width()-$("#ly_instPay_pop .confirm_cont").width())/3;
    		var ty = ($(window).height())/15;
    		if(ty < 0){
    			ty = 15;
    		}
    		$("#ly_instPay_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
    		
    		$("#ly_instPay_pop").show();  // 레이어 팝업 open
    		
    		
    		COMMON.fn_ajaxQueryString(CONTEXT_ROOT + '/cmHiswebreq/getHotbill.json', 'cust_id=' + data.cust_id, function(sresult){
    			if(sresult.detailVO.length == 0){
    				//고객정보
        			$("#ly_instPay_pop .confirm_cont").find("#custNm").text(sresult.summuryVO.custNm);
        			$("#ly_instPay_pop .confirm_cont").find("#custId").text(sresult.summuryVO.custId);
        			$("#ly_instPay_pop .confirm_cont").find("#billYm").text("");
        			
        			//해지 즉시불 요금 내역
        			$("#ly_instPay_pop .confirm_cont").find("#cost").text("0");
        			$("#ly_instPay_pop .confirm_cont").find("#dcCost").text("0");
        			$("#ly_instPay_pop .confirm_cont").find("#totCost").text("0");
        			
        			//사용요금내역
        			$("#ly_instPay_pop .confirm_cont").find("#detail").html("");
        			
    			}else{
    				//고객정보
        			$("#ly_instPay_pop .confirm_cont").find("#custNm").text(sresult.summuryVO.custNm);
        			$("#ly_instPay_pop .confirm_cont").find("#custId").text(sresult.summuryVO.custId);
        			$("#ly_instPay_pop .confirm_cont").find("#billYm").text(sresult.summuryVO.billYm);
        			
        			//해지 즉시불 요금 내역
        			$("#ly_instPay_pop .confirm_cont").find("#cost").text(sresult.summuryVO.cost);
        			$("#ly_instPay_pop .confirm_cont").find("#dcCost").text(sresult.summuryVO.dcCost);
        			$("#ly_instPay_pop .confirm_cont").find("#totCost").text(sresult.summuryVO.totCost);
        			
        			//사용요금내역
        			var instag = "";
        			for(var i=0; i<sresult.detailVO.length; i++){
        				if(i == sresult.detailVO.length -1){
        					instag = instag
    						+ "<tr>"
    		    			+ "<td style='text-align: center;border-right:1px solid #e2e2e2' colspan='2'>"+ sresult.detailVO[i].priceplannm +"</td>" 
    		    			+ "<td style='text-align: center;'>"+ sresult.detailVO[i].useamt +"</td>"
    		    			+"</tr>";
        				}else{
        					instag = instag
    						+ "<tr>"
    		    			+ "<td style='text-align: center;border-right:1px solid #e2e2e2'>"+ sresult.detailVO[i].priceplannm +"</td>" 
    		    			+ "<td style='text-align: center;border-right:1px solid #e2e2e2'>"+ sresult.detailVO[i].itemnm +"</td>" 
    		    			+ "<td style='text-align: center;'>"+ sresult.detailVO[i].useamt +"</td>"
    		    			+"</tr>";
        				}
        				
        			}
        			
        			$("#ly_instPay_pop .confirm_cont").find("#detail").html(instag);
        			
    			}
    			
    			
    			
    			
    			
    		}, false);
			
		}, false);
    }

    hotbill_proc_commit = function(custId){
    	var url = CONTEXT_ROOT + '/cmHiswebreq/hotbill_proc_commit.json';
    	var p_data = 'cust_id=' + custId;
    	
    	COMMON.fn_ajaxQueryString(url, p_data, function(result){
    		$("#ly_instPay_pop").hide();  // 레이어 팝업 close
    		
    		$("#frm_main").find("#btn_search").click();
    		
    		if(result.successFlag == 0){
    			$.unibillDialog.alert('알림', '해지즉시불처리가 완료되었습니다.');
    		}else{
    			$.unibillDialog.alert('알림', '해지즉시불처리에 오류가 있습니다.');
    		}
    		
    	}, false);
    }

    //------------------------------------------------------------------------------------------------------------------
    //## public 메소드
    // public 메소드 으로 등록되지 않았을때 아래와같이 스크립트 오류가 발생
    //     function anonymous() {
    //         HISWEBREQ.fn_hiswebreqFindday()
    //     }
    //------------------------------------------------------------------------------------------------------------------
    _mod_mail.fn_hiswebreqFindday 				  = fn_hiswebreqFindday;
    _mod_mail.fn_hiswebreqJobType 				  = fn_hiswebreqJobType;
    _mod_mail.fn_hiswebreqMakeRun 				  = fn_hiswebreqMakeRun;
    _mod_mail.fn_hiswebreqMakeRun_callback 		  = fn_hiswebreqMakeRun_callback;
    _mod_mail.fn_hiswebreqIIACMakeRun 			  = fn_hiswebreqIIACMakeRun;
    _mod_mail.fn_hiswebreqIIACMakeRun_callback    = fn_hiswebreqIIACMakeRun_callback;
    _mod_mail.fn_hiswebreqIIACMakeCancel 		  = fn_hiswebreqIIACMakeCancel;
    _mod_mail.fn_hiswebreqIIACMakeCancel_callback = fn_hiswebreqIIACMakeCancel_callback;
    _mod_mail.fn_selectTerminationDtlList  		  = fn_selectTerminationDtlList;
    _mod_mail.hotbill_proc 						  = hotbill_proc;
    _mod_mail.hotbill_proc_commit 				  = hotbill_proc_commit;

    return _mod_mail;

}(UNI.HISWEBREQ || {}, jQuery);