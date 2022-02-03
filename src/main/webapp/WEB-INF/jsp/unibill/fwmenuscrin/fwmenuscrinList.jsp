<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<script type="text/javascript" src="<c:url value='/extLib/jquery.tablednd.js'/>"></script>


<script type="text/javascript">

    //###### 메뉴 , Scrin 관리 Grid Start ############################################################################
    var _grid01_id = "myGrid01";
    var _grid01_pagerId = "myGrid01Page";
    var _grid01_url_dataLoad = CONTEXT_ROOT + "/fwmenuscrin/listFwmenuscrinJqgrid.json";
    var _grid01_url_dataSave = CONTEXT_ROOT + "/fwmenuscrin/saveFwmenuscrin.json";
    var _grid01_url_dataDelete = CONTEXT_ROOT + "/fwmenuscrin/deleteFwmenuscrin.json";

    var _grid01_height = "220";
    var _grid01_width = "800";
    var _grid01_recordCountPerPage = 1000
    /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid01_colName = [
        '메뉴ID'
        , '화면ID'
        , '자료순번'
        , '화면출력순번'
        , '등록자'
        , '변경자'
        , '등록시간'
        , '수정시간'
        , '그리드높이'
    ];

    var _grid01_colModel01 = [
        {name: 'menuId', index: 'menuId', width: 100, hidden: false}
        , {name: 'scrinId', index: 'scrinId', width: 100, hidden: false}
        , {name: 'ubseq', index: 'ubseq', width: 100, hidden: false}
        , {name: 'showSn', index: 'showSn', width: 100, hidden: false}
        , {name: 'regId', index: 'regId', width: 100, hidden: false}
        , {name: 'updId', index: 'updId', width: 100, hidden: false}
        , {name: 'regTm', index: 'regTm', width: 100, hidden: false}
        , {name: 'updTm', index: 'updTm', width: 100, hidden: false}
        , {name: 'gridhg', index: 'gridhg', width: 100, hidden: false}
        , {name: 'dtlTmpType', index: 'dtlTmpType', width: 100, hidden: false}
    ];
    var _grid01_colModel = [
        {label: '메뉴ID', name: 'menuId', index: 'menuId', width: 100, hidden: false}
        , {label: '화면ID', name: 'scrinId', index: 'scrinId', width: 100, hidden: false}
        , {label: '메뉴 이름', name: 'menuNm', index: 'menuNm', width: 100, hidden: false}
        , {label: '화면 이름', name: 'scrinNm', index: 'scrinNm', width: 100, hidden: false}
        , {label: '자료순번', name: 'ubseq', index: 'ubseq', width: 100, hidden: false}
        , {label: '화면출력순번', name: 'showSn', index: 'showSn', width: 100, hidden: false}
        , {label: '등록자', name: 'regId', index: 'regId', width: 100, hidden: true}
        , {label: '변경자', name: 'updId', index: 'updId', width: 100, hidden: true}
        , {label: '등록시간', name: 'regTm', index: 'regTm', width: 100, hidden: true}
        , {label: '수정시간', name: 'updTm', index: 'updTm', width: 100, hidden: true}
        , {label: '그리드높이', name: 'gridhg', index: 'gridhg', width: 100, hidden: false}
        , {label: '상세템플릿구분', name: 'dtlTmpType', index: 'dtlTmpType', width: 100, hidden: false}

    ];

    // _grid01_option 에 jqGrid 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요.
    var _grid01_option = {
        caption: "메뉴와 화면 연결 정보" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid01_height + "px"
        , width: _grid01_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid01_url_dataLoad
        , rowNum: _grid01_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
        , loadComplete: function (data) {
            // 그리드 데이터 총 갯수
            var sTotalRecords = $("#" + _grid01_id).jqGrid("getGridParam", "records");
            $("#id_totalRecords").text(sTotalRecords);
            // 버튼 disabled 처리
            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);

            var ids = $("#" + _grid01_id).getDataIDs();
            // Grid Data Get!
            $.each(
                ids, function (idx, rowId) {
                    rowData = $("#" + _grid01_id).getRowData(rowId);
                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
//                    if (rowData.objId == '') {
//                        $("#" + _grid01_id).setRowData(rowId, false, { background:"#9AFC95" });
//                    }
                }
            );
        }
    };

    var _grid01_treeOption = {
//        ExpandColumn: 'grpName'
//        , treeReader:{
//            parent_id_field: "upGrpId"
//            , level_field: "menuDepth"
//        }/* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!! */
//        , localReader:{
//            id: "grpId"
//        }
    };

    //###### 메뉴 선택 Tree 선택 Grid ############################################################################

    var _grid02_id = "myGrid02";
    var _grid02_pagerId = "myGrid02Page";
    var _grid02_url_dataLoad = CONTEXT_ROOT + "/fwmenu/listFwmenuJqgrid.json";
    var _grid02_url_dataSave = CONTEXT_ROOT + "/fwmenu/saveFwmenu.json";
    var _grid02_url_dataDelete = CONTEXT_ROOT + "/fwmenu/deleteFwmenu.json";

    var _grid02_height = "250";
    var _grid02_width = "570";
    var _grid02_recordCountPerPage = 7
    /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid02_colModel = [
        {label: '메뉴ID', name: 'menuId', index: 'menuId', width: 100, hidden: false}
        , {label: '자료순번', name: 'ubseq', index: 'ubseq', width: 100, hidden: true}
        , {label: '메뉴명', name: 'menuNm', index: 'menuNm', width: 100, hidden: true}
        , {label: '메뉴명', name: 'lpadMenuNm', index: 'lpadMenuNm', width: 100, hidden: true}
        , {label: '메뉴명', name: 'lpadMenuNm2', index: 'lpadMenuNm2', width: 100, hidden: false}
//        http://localhost/fwmenuscrin/listFwmenuscrin.do
        , {label: '레벨', name: 'depth', index: 'depth', width: 30, hidden: false}
        , {label: '메뉴순서', name: 'menuSeq', index: 'menuSeq', width: 100, hidden: true}
        , {label: '상위메뉴ID', name: 'upMenuId', index: 'upMenuId', width: 100, hidden: true}
        , {label: '설명', name: 'remark', index: 'remark', width: 100, hidden: true}
        , {label: 'url', name: 'url', index: 'url', width: 100, hidden: false}
        , {label: '도움말', name: 'help', index: 'help', width: 100, hidden: true}
        , {label: '템플릿구분', name: 'tmpType', index: 'tmpType', width: 100, hidden: true}
        , {label: '테이블명', name: 'tblNm', index: 'tblNm', width: 100, hidden: true}
        , {label: '등록자', name: 'regId', index: 'regId', width: 100, hidden: true}
        , {label: '변경자', name: 'updId', index: 'updId', width: 100, hidden: true}
        , {label: '등록시간', name: 'regTm', index: 'regTm', width: 100, hidden: true}
        , {label: '변경시간', name: 'updTm', index: 'updTm', width: 100, hidden: true}
    ];

    // _grid02_option 에 jqGrid 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요.
    var _grid02_option = {
        caption: "메뉴 정보" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid02_height + "px"
        , width: _grid02_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid02_url_dataLoad
        , rowNum: _grid02_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
//        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
//        , loadComplete: function (data) {
//            // 그리드 데이터 총 갯수
//            var sTotalRecords = $("#" + _grid02_id).jqGrid("getGridParam", "records");
//            $("#id_totalRecords").text(sTotalRecords);
//            // 버튼 disabled 처리
//            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);
//
//            var ids = $("#" + _grid02_id).getDataIDs();
//            // Grid Data Get!
//            $.each(
//                ids,function(idx, rowId){
//                    rowData = $("#" + _grid02_id).getRowData(rowId);
//                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
////                    if (rowData.objId == '') {
////                        $("#" + _grid02_id).setRowData(rowId, false, { background:"#9AFC95" });
////                    }
//                }
//            );
//        }
    };

    var _grid02_treeOption = {
        ExpandColumn: 'menuNm'
        , treeReader: {
            parent_id_field: "upMenuId"
            , level_field: "depth"
        }/* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!! */
        , localReader: {
            id: "menuId"
        }
    };


    //###### 화면 ID 선택 Grid ############################################################################

    var _grid03_id = "myGrid03";
    var _grid03_pagerId = "myGrid03Page";
    var _grid03_url_dataLoad = CONTEXT_ROOT + "/fwscrin/listFwscrinJqgrid.json";
    var _grid03_url_dataSave = CONTEXT_ROOT + "/fwscrin/saveFwscrin.json";
    var _grid03_url_dataDelete = CONTEXT_ROOT + "/fwscrin/deleteFwscrin.json";

    var _grid03_height = "250";
    var _grid03_width = "570";
    var _grid03_recordCountPerPage = 7
    /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid03_colModel = [
        {label: '화면ID', name: 'scrinId', index: 'scrinId', width: 100, hidden: false}
        , {label: '자료순번', name: 'ubseq', index: 'ubseq', width: 100, hidden: true}
        , {label: '화면명', name: 'scrinNm', index: 'scrinNm', width: 100, hidden: false}
        , {label: '테이블명', name: 'tblNm', index: 'tblNm', width: 100, hidden: false}
        , {label: 'xmlID', name: 'xmlId', index: 'xmlId', width: 100, hidden: true}
        , {label: '등록자', name: 'regId', index: 'regId', width: 100, hidden: true}
        , {label: '변경자', name: 'updId', index: 'updId', width: 100, hidden: true}
        , {label: '등록시간', name: 'regTm', index: 'regTm', width: 100, hidden: true}
        , {label: '수정시간', name: 'updTm', index: 'updTm', width: 100, hidden: true}
        , {label: '상세가로', name: 'dtlW', index: 'dtlW', width: 100, hidden: true}
    ];

    // _grid03_option 에 jqGrid 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요.
    var _grid03_option = {
        caption: "화면 Id 정보" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid03_height + "px"
        , width: _grid03_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid03_url_dataLoad
        , rowNum: _grid03_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
        , loadComplete: function (data) {
            // 그리드 데이터 총 갯수
            var sTotalRecords = $("#" + _grid03_id).jqGrid("getGridParam", "records");
            $("#id_totalRecords").text(sTotalRecords);
            // 버튼 disabled 처리
            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);

            var ids = $("#" + _grid03_id).getDataIDs();
            // Grid Data Get!
            $.each(
                ids, function (idx, rowId) {
                    rowData = $("#" + _grid03_id).getRowData(rowId);
                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
//                    if (rowData.objId == '') {
//                        $("#" + _grid03_id).setRowData(rowId, false, { background:"#9AFC95" });
//                    }
                }
            );
        }
    };

    var _grid03_treeOption = {
//        ExpandColumn: 'grpName'
//        , treeReader:{
//            parent_id_field: "upGrpId"
//            , level_field: "menuDepth"
//        }/* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!! */
//        , localReader:{
//            id: "grpId"
//        }
    };


    $(document).ready(function () {
// 		loadValidator();
        loadPage();
    });


    /*====================================================================
     화면 초기화
     ====================================================================*/
    function loadPage() {
        initSheet();
        initEvent();
        initHtml();
    }


    function initSheet() {

//        GRID.fn_createTreeGrid( _grid01_id, _grid01_option , _grid01_colModel , _grid01_treeOption , _grid01_url_dataLoad ); // 트리형태의 그리드 생성.
        GRID.fn_createGridPageNav(_grid01_id, _grid01_option, _grid01_colModel, _grid01_pagerId, _grid01_url_dataLoad); // 페이징의 그리드 생성.
//        GRID.fn_createGrid( _grid01_id, _grid01_option , _grid01_colModel ); // 일반 그리드 생성.

        GRID.fn_createGridPageNav(_grid02_id, _grid02_option, _grid02_colModel, _grid02_pagerId, _grid02_url_dataLoad); // 페이징의 그리드 생성.
//        GRID.fn_createTreeGrid( _grid02_id, _grid02_option , _grid02_colModel , _grid02_treeOption , _grid02_url_dataLoad ); // 트리형태의 그리드 생성.
        GRID.fn_createGridPageNav(_grid03_id, _grid03_option, _grid03_colModel, _grid03_pagerId, _grid03_url_dataLoad); // 페이징의 그리드 생성.


        $("#" + _grid01_id).jqGrid("filterToolbar"); // 필터 적용.
        $("#" + _grid02_id).jqGrid("filterToolbar"); // 필터 적용.
        $("#" + _grid03_id).jqGrid("filterToolbar"); // 필터 적용.


        $("#" + _grid01_id).jqGrid('setGridParam', {
            onSelectRow: function (rowId, status, e) {
                lastSel = rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#" + _grid01_id).jqGrid('getRowData', rowId);
                    if (!UTILE.fn_isBlank(rowData['ubseq'])) {
                        for (var iCol = 0; iCol < cm.length; iCol++) {
                            var colNm = cm[iCol].name;
                            if (colNm == "deleteYn") {				//radio
                                $("#deleteYn" + rowData[colNm]).prop("checked", true);
                            } else if (colNm == "grpType") {				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
                                $("#frm_grpType").selectBox('value', rowData[colNm]); // jquery.selectbox.js

                            } else if (colNm == "dtlTmpType") {				//select
                                $("#frm_dtlTmpType").selectBox('value', rowData[colNm]); // jquery.selectbox.js

                            } else {
                                $("#frm_" + colNm).val(rowData[colNm]);
                            }
                        }
                    }
                }

            }
        });


        $("#" + _grid02_id).jqGrid('setGridParam', {
            onSelectRow: function (rowId, status, e) {
                lastSel = rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#" + _grid02_id).jqGrid('getRowData', rowId);
                    if (!UTILE.fn_isBlank(rowData['ubseq'])) {
                        for (var iCol = 0; iCol < cm.length; iCol++) {
                            var colNm = cm[iCol].name;
                            if (colNm == "deleteYn") {				//radio
//                                $("#deleteYn"+rowData[colNm]).prop("checked", true);
                            } else if (colNm == "grpType") {				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
//                                $("#frm_grpType").selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            } else {
                                $("#frm_" + colNm).val(rowData[colNm]);
                            }
                        }
                    }
                }

            }
        });


        $("#" + _grid03_id).jqGrid('setGridParam', {
            onSelectRow: function (rowId, status, e) {
                lastSel = rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#" + _grid03_id).jqGrid('getRowData', rowId);
                    if (!UTILE.fn_isBlank(rowData['ubseq'])) {
                        for (var iCol = 0; iCol < cm.length; iCol++) {
                            var colNm = cm[iCol].name;
                            if (colNm == "deleteYn") {				//radio
//                                $("#deleteYn"+rowData[colNm]).prop("checked", true);
                            } else if (colNm == "grpType") {				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
//                                $("#frm_grpType").selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            } else {
                                $("#frm_" + colNm).val(rowData[colNm]);
                            }
                        }
                    }
                }

            }
        });

    }

    /* 각종 이벤트 초기화 */
    function initEvent() {
        $("#searchGrpType").change(function () {
            fn_search();
        });

        $('#searchGrpNm').keypress(function (event) {
            if (event.which == 13) {
//                event.preventDefault();
                fn_search();
            }
        });

    }

    /* 화면이 로드된후 처리 초기화 */
    function initHtml() {

//        $("#searchRecordCountPerPage").val(_grid01_recordCountPerPage);
//        fn_gridSearch(pageIndex);
//        $('.table_wrapper').css('height', $(window).height() - 50 );
//        $('.table_wrapper').css('height', (_grid01_height*1) + 110 );
        var _changeHeight = (_grid01_height * 1) + 120; // 그리드 크기에 맞춤.
        if (_changeHeight < $('.pop_layout_r').height()) {
            _changeHeight = $('.pop_layout_r').height();
        }
        $('.table_wrapper').css('height', _changeHeight);

        $("#menuSelectDiv").css('height', '100%');

        $('.table_wrapper td').css('height', '0');

    }


    /*====================================================================
     조회버튼이나 페이지 클릭시 실행되는 함수 fn_xxxxxx();
     ====================================================================*/
    function fn_search() {
        /* Grid 검색 */
        fn_g_search("myGrid01");

        fn_formReset()
    }

    function fn_formReset() {
        /* 입력 필드 초기화 */
        $("#frm_fwmenuscrin").find("input:text,input:hidden,select,textarea").val("");

        $("#frm_ubseq").val(0);


        $("#frm_dtlTmpType").selectBox('value', ""); // jquery.selectbox.js
        $("#frm_grpType").selectBox('value', ""); // jquery.selectbox.js
        $("#frm_useYn").selectBox('value', ""); // jquery.selectbox.js

        $("#useYsnoY").prop("checked", true);					//사용여부
    }

    function fn_formSave_AJAX() {
        /* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataSave;
        var param = $("#frm_fwmenuscrin").serializeArray();

        UTILE.fn_ajax4confirm("<spring:message code="common.save.msg" />", reqUrl, param, function (obj, resultData, textStatus, jqXHR) {
            if (200 == jqXHR.status) {

                fn_search();

                if ("SUCCESS" == resultData.retCd) {
                    return;
                } else if ("FAILE" == resultData.retCd) {
                    return;
                }
            } else {
            }

        }, {
            async: true,
            type: "POST",
            spinner: true,
            errorCallback: null,
            timeout: 30000			// 기본 30초
        });
    }

    function fn_formDelete_AJAX() {
        /* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */

        var i, selRowIds = $("#" + _grid01_id).jqGrid("getGridParam", "selarrrow"), n, rowData;
        var cm = $("#" + _grid01_id).jqGrid('getGridParam', 'colModel');

        var delList = [];

        UTILE.fn_log("selRowIds :" + selRowIds);

        for (i = 0, n = selRowIds.length; i < n; i++) {
            rowData = $("#" + _grid01_id).getRowData(selRowIds[i]);
            // one can uses the data here
            for (var iCol = 0; iCol < cm.length; iCol++) {
                var colNm = cm[iCol].name;
                if ('ubseq' == colNm) {
                    delList.push(rowData[colNm]);
                }
            }
        }

        UTILE.fn_log("getDataIDs() :" + $("#" + _grid01_id).getDataIDs());

        $("#frm_delUbseq").val(delList.toString());

        var reqUrl = _grid01_url_dataDelete;
        var param = $("#frm_fwmenuscrin").serializeArray();

        var _ubsdq = $("#frm_ubseq").val();
        if (UNI.UTILE.fn_isBlank(_ubsdq)) {
            alert("삭제 항목을 선택해 주세요.")
        } else {

            UTILE.fn_ajax4confirm("<spring:message code="common.delete.msg" />", reqUrl, param, function (obj, resultData, textStatus, jqXHR) {
                if (200 == jqXHR.status) {

                    fn_search();

                    if ("SUCCESS" == resultData.retCd) {
                        return;
                    } else if ("FAILE" == resultData.retCd) {
                        return;
                    }
                } else {
                }

            }, {
                async: true,
                type: "POST",
                spinner: true,
                errorCallback: null,
                timeout: 30000			// 기본 30초
            });

        }

    }


    /*====================================================================
     grid 이벤트 처리 전용 함수 : fn_g_xxxxx()
     ====================================================================*/
    function fn_g_search(gridId) {
        GRID.fn_searchGrid(gridId, _grid01_url_dataLoad, $("#frm_searchFwmenuscrin").serializeArray(), function (obj, resultData, textStatus, jqXHR) {
        }, {});
    }


</script>
<!-- Search -->
<%--<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 메뉴 화면 연결 관리</h2><!--북마크페이지 class="on"추가-->--%>
<div>
    <h2 id="id_menu"></h2>
</div>

<%--<form id="frm_searchFwmenuscrin" name="frm_searchFwmenuscrin" method="post" action="">--%>
<%--<input type="hidden" title="" id="searchRecordCountPerPage" name="recordCountPerPage"/>--%>
<%--<fieldset>--%>
<%--<!--폼양식-->--%>
<%--<legend>Search</legend>--%>
<%--<div class="srh_wrap">--%>
<%--<div>--%>
<%--<label>메뉴ID</label>--%>
<%--<input type="text" title="" id="searchMenuId" name="searchMenuId"/>--%>
<%--</div>--%>
<%--<div>--%>
<%--<label>화면ID</label>--%>
<%--<input type="text" title="" id="searchScrinId" name="searchScrinId"/>--%>
<%--</div>--%>
<%--<div>--%>
<%--<label>자료순번</label>--%>
<%--<input type="text" title="" id="searchUbseq" name="searchUbseq"/>--%>
<%--</div>--%>
<%--<div>--%>
<%--<label>화면출력순번</label>--%>
<%--<input type="text" title="" id="searchShowSn" name="searchShowSn"/>--%>
<%--</div>--%>
<%--<div>--%>
<%--<label>그리드높이</label>--%>
<%--<input type="text" title="" id="searchGridhg" name="searchGridhg"/>--%>
<%--</div>--%>
<%--<span class="btn_wrap">--%>
<%--<button type="button" onclick="javascript:fn_search();" class="btn_srh">조회</button>--%>
<%--</span>--%>
<%--</div>--%>
<%--</fieldset>--%>
<%--</form>--%>

<!-- table -->
<table id="myGrid04"></table>

<div class="pop_layout_wrap">
    <div class="pop_layout_l" style="width:50%;"> <!-- margin-right=전체width/2+30 -->
        <div id="menuSelectDiv" class="table_wrapper" style="height:100%;">
            <table id="myGrid02"></table>
            <table id="myGrid02Page"></table>
        </div>
    </div>
    <div class="pop_layout_r" style="width:50%;padding:0px;"> <!-- width=전체width/2+30 -->
        <table id="myGrid03"></table>
        <table id="myGrid03Page"></table>
    </div>
</div>
<br/>
<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
    <!-- 조회결과 -->
    <div class="table_wrap" style="padding:0px;">
        <div class="pop_layout_wrap">
            <div class="pop_layout_l" style="width:70%;"> <!-- margin-right=전체width/2+30 -->
                <div class="table_wrapper" style="height:180px;">
                    <table id="myGrid01"></table>
                    <table id="myGrid01Page"></table>
                </div>
            </div>
            <div class="pop_layout_r" style="width:30%;padding:0px;"> <!-- width=전체width/2+30 -->
                <!-- 버튼 -->
                <%--<div class="btngroup">--%>
                <%--<button type="button" onclick="" class="btn_pop_add2">추가</button>--%>
                <%--<button type="button" onclick="" class="btn_pop_remove2">삭제</button>--%>
                <%--<button type="button" onclick="" class="btn_pop_add3">전체추가</button>--%>
                <%--<button type="button" onclick="" class="btn_pop_remove3">전체삭제</button>--%>
                <%--</div>--%>
                <div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
                    <form id="frm_fwmenuscrin" name="frm_fwmenuscrin" method="post" action="">
                        <input type="hidden" title="자료순번" id="frm_ubseq" name="ubseq" value="${cmCfggrpidVO.ubseq}"/>
                        <input type="hidden" title="삭제 대상 자료순번" id="frm_delUbseq" name="delUbseq" value=""/>

                        <table summary="등록/상세정보" class="tb02">
                            <caption>등록/상세정보</caption>
                            <colgroup>
                                <col width="90">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th scope="row">메뉴ID <span class="asterisk"></span></th>
                                <td><input type="text" title="메뉴ID" id="frm_menuId" name="menuId"
                                           value="${fwmenuscrinVO.menuId}"/></td>
                            </tr>
                            <tr>
                                <th scope="row">화면ID <span class="asterisk"></span></th>
                                <td><input type="text" title="화면ID" id="frm_scrinId" name="scrinId"
                                           value="${fwmenuscrinVO.scrinId}"/></td>
                            </tr>
                            <tr>
                                <th scope="row">화면출력순번</th>
                                <td><input type="text" title="화면출력순번" id="frm_showSn" name="showSn"
                                           value="${fwmenuscrinVO.showSn}"/></td>
                            </tr>
                            <tr>
                                <th scope="row">그리드높이</th>
                                <td><input type="text" title="그리드높이" id="frm_gridhg" name="gridhg"
                                           value="${fwmenuscrinVO.gridhg}"/></td>
                            </tr>
                            <tr>
                                <th scope="row">상세템플릿구분</th>
                                <td>
                                    <%--<input type="text" title="상세템플릿구분" id="frm_dtlTmpType" name="dtlTmpType" value="${fwmenuscrinVO.dtlTmpType}"/>--%>
                                    <select id="frm_dtlTmpType" name="dtlTmpType" style="width: 232px;">
                                        <option value="" <c:if
                                                test="${'' eq fwmenuscrinVO.dtlTmpType}"> selected="selected"</c:if> ></option>
                                        <option value="I" <c:if
                                                test="${'I' eq fwmenuscrinVO.dtlTmpType}"> selected="selected"</c:if> >
                                            개별 등록
                                        </option>
                                        <option value="M" <c:if
                                                test="${'M' eq fwmenuscrinVO.dtlTmpType}"> selected="selected"</c:if> >
                                            일괄 등록(Excel)
                                        </option>
                                    </select>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <div class="btn_pop_wrap">
            <button type="button" onclick="fn_formReset();" class="btn_pop">초기화</button>
            <button type="button" onclick="fn_formSave_AJAX();" class="btn_pop">저장</button>
            <button type="button" onclick="fn_formDelete_AJAX();" class="btn_pop">삭제</button>

        </div>
    </div>



