/**
 *  MB_TC_TONG 테이블을 사용하는 화면에서 필요한 이벤트
 * @constructor
 * @namespace {Object} UNI.MBTCTONG
 */
UNI.MBTCTONG = function (_mod_mail, $, undefined) {

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

    fn_searchStdr_callback = function () {
    }

    // fn_searchStdr = function() {
    fn_searchStdr = function(sFormId, sColId) {
        // var _val = $("#"+sFormId).find("#" + sColId).val(); // select box
        var _val 		= $("#"+sFormId).find(':radio[name=' + sColId + ']:checked').val(); // radio box

        if("1" == _val ){
            // 1 : 키번호

            $("#"+sFormId).find("#key_no").attr("disabled",false);
            $("#"+sFormId).find("#div_search_key_no").show();

            $("#"+sFormId).find("#cust_id").val("");
            $("#"+sFormId).find("#cust_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_id").hide();

            $("#"+sFormId).find("#cust_dept_id").val("");
            $("#"+sFormId).find("#cust_dept_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_dept_id").hide();

        }else if("2" == _val ){
            //2 : 고객

            $("#"+sFormId).find("#key_no").val("");
            $("#"+sFormId).find("#key_no").attr("disabled",true);
            $("#"+sFormId).find("#div_search_key_no").hide();

            $("#"+sFormId).find("#cust_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_id").show();

            $("#"+sFormId).find("#cust_dept_id").val("");
            $("#"+sFormId).find("#cust_dept_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_dept_id").hide();

        }else if("3" == _val ){
            //3 : 고객부서

            $("#"+sFormId).find("#key_no").val("");
            $("#"+sFormId).find("#key_no").attr("disabled",true);
            $("#"+sFormId).find("#div_search_key_no").hide();

            $("#"+sFormId).find("#cust_id").val("");
            $("#"+sFormId).find("#cust_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_id").hide();

            $("#"+sFormId).find("#cust_dept_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_dept_id").show();
        }


        // COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/mbMailBillmakehist/saveMailBillMakeHist.do", "MBTCTONG.fn_searchStdr_callback");
    }

    fn_searchStdrS0095_callback = function () {
    }

    // 고객 그룹별 총괄 통계
    // fn_searchStdrS0095 = function() {
    fn_searchStdrS0095 = function(sFormId, sColId) {
        // var _val = $("#"+sFormId).find("#" + sColId).val(); // select box
        var _val 		= $("#"+sFormId).find(':radio[name=' + sColId + ']:checked').val(); // radio box

        if("1" == _val || "2" == _val || "3" == _val || "4" == _val ){
            // 1 : 고객부서

            $("#"+sFormId).find("#cust_id").val("");
            $("#"+sFormId).find("#cust_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_id").hide();

            $("#"+sFormId).find("#cust_dept_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_dept_id").show();

        }else if("5" == _val ){
            //2 : 고객

            $("#"+sFormId).find("#cust_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_id").show();

            $("#"+sFormId).find("#cust_dept_id").val("");
            $("#"+sFormId).find("#cust_dept_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_dept_id").hide();

        }


        // COMMON.fn_submit_createForm("frm_detail", "proc_frm", CONTEXT_ROOT + "/mbMailBillmakehist/saveMailBillMakeHist.do", "MBTCTONG.fn_searchStdrS0095_callback");
    }



    fn_searchStdrS0116 = function(sFormId, sColId) {
        // var _val = $("#"+sFormId).find("#" + sColId).val(); // select box
        var _val 		= $("#"+sFormId).find(':radio[name=' + sColId + ']:checked').val(); // radio box

        if("1" == _val || "2" == _val || "3" == _val || "4" == _val ){
            // 1 : 고객부서

            $("#"+sFormId).find("#svc_id").val("");
            $("#"+sFormId).find("#svc_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_svc_id").hide();

            $("#"+sFormId).find("#svc_nm").val("");
            $("#"+sFormId).find("#svc_nm").attr("disabled",true);
            $("#"+sFormId).find("#div_search_svc_nm").hide();

            $("#"+sFormId).find("#cust_dept_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_cust_dept_id").show();

        }else if("5" == _val ){
            //2 : 서비스

            $("#"+sFormId).find("#svc_id").attr("disabled",false);
            $("#"+sFormId).find("#div_search_svc_id").show();

            $("#"+sFormId).find("#svc_nm").attr("disabled",false);
            $("#"+sFormId).find("#div_search_svc_nm").show();

            $("#"+sFormId).find("#cust_dept_id").val("");
            $("#"+sFormId).find("#cust_dept_id").attr("disabled",true);
            $("#"+sFormId).find("#div_search_cust_dept_id").hide();

        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //## public 메소드
    // public 메소드 으로 등록되지 않았을때 아래와같이 스크립트 오류가 발생
    //     function anonymous() {
    //         MBTCTONG.fn_searchStdr()
    //     }
    //------------------------------------------------------------------------------------------------------------------
    _mod_mail.fn_searchStdr = fn_searchStdr;
    _mod_mail.fn_searchStdr_callback = fn_searchStdr_callback;

    _mod_mail.fn_searchStdrS0095 = fn_searchStdrS0095; // 고객 그룹별 총괄 통계
    _mod_mail.fn_searchStdrS0095_callback = fn_searchStdrS0095_callback;

    _mod_mail.fn_searchStdrS0116 = fn_searchStdrS0116; // 조직별 년(월)별 서비스별 총괄 통계

    return _mod_mail;

}(UNI.MBTCTONG || {}, jQuery);