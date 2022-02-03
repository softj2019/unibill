<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>

<script type="text/javascript" src="<c:url value='/extLib/jquery.tablednd.js'/>"></script>

<script type="text/javascript">

    var _grid01_id = "myGrid01";
    var _grid01_pagerId = "myGrid01Page";
    var _grid01_url_dataLoad = CONTEXT_ROOT + "/fwform/listFwformJqgrid.json";
    var _grid01_url_dataSave = CONTEXT_ROOT + "/fwform/saveFwform.json";
    var _grid01_url_dataDelete = CONTEXT_ROOT + "/fwform/deleteFwform.json";
    
    var _grid01_height = "500";
    var _grid01_width = "700";
    var _grid01_recordCountPerPage = 25 /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid01_colModel = [
   { label : '메뉴ID',     	name : 'menuId',         		index : 'menuId',         		width : 100  , hidden: false}
 , { label : '화면ID',     	name : 'scrinId',         		index : 'scrinId',         		width : 100  , hidden: false}
 , { label : '객체ID',     	name : 'objId',         		index : 'objId',         		width : 100  , hidden: false}
 , { label : '메뉴명',     	name : 'menuNm',         		index : 'menuNm',         		width : 100  , hidden: false}
 , { label : '화면명',     	name : 'scrinNm',         		index : 'scrinNm',         		width : 100  , hidden: false}
 , { label : '객체명',     	name : 'objNm',         		index : 'objNm',         		width : 100  , hidden: false}
 , { label : '자료순번',     	name : 'ubseq',         		index : 'ubseq',         		width : 100  , hidden: true}
 , { label : '상위객체ID',     	name : 'upObjId',         		index : 'upObjId',         		width : 100  , hidden: false}
 , { label : 'xmlID',     	name : 'xmlId',         		index : 'xmlId',         		width : 100  , hidden: true}
 , { label : '검색항목가로',     	name : 'findItemW',         		index : 'findItemW',         		width : 100  , hidden: true}
 , { label : '검색행위치',     	name : 'rowPos',         		index : 'rowPos',         		width : 100  , hidden: false}
 , { label : '검색열위치',     	name : 'colPos',         		index : 'colPos',         		width : 100  , hidden: false}
 , { label : '검색객체넓이',     	name : 'w',         		index : 'w',         		width : 100  , hidden: true}
 , { label : '검색출력여부',     	name : 'findShowYn',         		index : 'findShowYn',         		width : 100  , hidden: true}
 , { label : '검색필수여부',     	name : 'mustYn',         		index : 'mustYn',         		width : 100  , hidden: true}
 , { label : '상세출력여부',     	name : 'dtlShowYn',         		index : 'dtlShowYn',         		width : 100  , hidden: true}
 , { label : '상세객체넓이',     	name : 'dtlObjW',         		index : 'dtlObjW',         		width : 100  , hidden: true}
 , { label : '상세행위치',     	name : 'dtlRowPos',         		index : 'dtlRowPos',         		width : 100  , hidden: false}
 , { label : '상세열위치',     	name : 'dtlColPos',         		index : 'dtlColPos',         		width : 100  , hidden: false}
 , { label : '상세행병합',     	name : 'dtlRowMgr',         		index : 'dtlRowMgr',         		width : 100  , hidden: true}
 , { label : '상세필수여부',     	name : 'dtlMustYn',         		index : 'dtlMustYn',         		width : 100  , hidden: true}
 , { label : '그리드출력순번',     	name : 'showSn',         		index : 'showSn',         		width : 100  , hidden: false}
 , { label : '그리드출력여부',     	name : 'showType',         		index : 'showType',         		width : 100  , hidden: true}
 , { label : '그리드셀가로',     	name : 'cellW',         		index : 'cellW',         		width : 100  , hidden: true}
 , { label : '그리드셀정렬',     	name : 'cellSort',         		index : 'cellSort',         		width : 100  , hidden: true}
 , { label : '그리드컬러',     	name : 'color',         		index : 'color',         		width : 100  , hidden: true}
 , { label : '그리드폰트',     	name : 'font',         		index : 'font',         		width : 100  , hidden: true}
 , { label : '그리드배경색',     	name : 'bgColor',         		index : 'bgColor',         		width : 100  , hidden: true}
 , { label : '등록자',     	name : 'regId',         		index : 'regId',         		width : 100  , hidden: true}
 , { label : '변경자',     	name : 'updId',         		index : 'updId',         		width : 100  , hidden: true}
 , { label : '등록시간',     	name : 'regTm',         		index : 'regTm',         		width : 100  , hidden: true}
 , { label : '변경시간',     	name : 'updTm',         		index : 'updTm',         		width : 100  , hidden: true}
 , { label : '일자검색여부',     	name : 'dayFindYn',         		index : 'dayFindYn',         		width : 100  , hidden: true}
 , { label : '상세권한행병합',     	name : 'dtlRoleRowMgr',         		index : 'dtlRoleRowMgr',         		width : 100  , hidden: true}
        , { label : 'SQL',     	name : 'objSql',         		index : 'objSql',         		 hidden: false}
        , { label : '객체타입',     	name : 'objType',         		index : 'objType',         		width : 100  , hidden: false}
        , { label : '암호화여부',     	name : 'cryptoYn',         		index : 'cryptoYn',         		width : 100  , hidden: false}
        , { label : '실행함수',     	name : 'extFun',         		index : 'extFun',         		width : 100  , hidden: false}
        , { label : '검색타입',     	name : 'findType',         		index : 'findType',         		width : 100  , hidden: false}
    ];

    // _grid01_option 에 jqGrid 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요.
    var _grid01_option = {
        caption: "화면 객체 정보 관리" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid01_height + "px"
        , width: _grid01_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid01_url_dataLoad
        , rowNum: _grid01_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
		, shrinkToFit : false
        , loadComplete: function (data) {
            // 그리드 데이터 총 갯수
//            var sTotalRecords = $("#" + _grid01_id).jqGrid("getGridParam", "records");
//            $("#id_totalRecords").text(sTotalRecords);
            // 버튼 disabled 처리
            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);

            var ids = $("#" + _grid01_id).getDataIDs();
            // Grid Data Get!
            $.each(
                ids,function(idx, rowId){
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



    //###### 객체 ID 선택 Grid ############################################################################

    var _grid02_id = "myGrid02";
    var _grid02_pagerId = "myGrid02Page";
    var _grid02_url_dataLoad = CONTEXT_ROOT + "/fwobj/listFwobjJqgrid.json";
    //    var _grid02_url_dataLoad = null; // 자동 data load 막기위함.
    var _grid02_url_dataSave = CONTEXT_ROOT + "/fwobj/saveFwobj.json";
    var _grid02_url_dataDelete = CONTEXT_ROOT + "/fwobj/deleteFwobj.json";
    var _grid02_height = "250";
    var _grid02_width = "570";
    var _grid02_recordCountPerPage = 7 /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid02_colModel = [
        { label : '테이블',     	name : 'tableNameFrom',         		index : 'tableNameFrom',         		 hidden: true}
        , { label : '컬럼',     	name : 'objIdFrom',         		index : 'objIdFrom',         		 hidden: true}
        , { label : '컬럼 주석',     	name : 'objNmFrom',         		index : 'objNmFrom',         		 hidden: true}
        , { label : '추출 ColId 컬럼',     	name : 'colIdFrom',         		index : 'colIdFrom',         		 hidden: true}
        , { label : '객체ID',     	name : 'objId',         		index : 'objId',         		 hidden: false}
        , { label : '객체명',     	name : 'objNm',         		index : 'objNm',         		 hidden: false}
        , { label : '컬럼ID',     	name : 'colId',         		index : 'colId',         		 hidden: false}
        , { label : '객체 형식',     	name : 'objType',         		index : 'objType',         		 hidden: false}
        , { label : '객체 위치',     	name : 'objPos',         		index : 'objPos',         		 hidden: true}
        , { label : 'xml ID',     	name : 'xmlId',         		index : 'xmlId',         		 hidden: false}
        , { label : '그룹코드',     	name : 'grpCd',         		index : 'grpCd',         		 hidden: true}
        , { label : '설명',     	name : 'remark',         		index : 'remark',         		 hidden: true}
        , { label : '자동완성여부',     	name : 'autoGenCol',         		index : 'autoGenCol',         		 hidden: true}
        , { label : '등록자',     	name : 'regId',         		index : 'regId',         		 hidden: true}
        , { label : '변경자',     	name : 'updId',         		index : 'updId',         		 hidden: true}
        , { label : '등록시간',     	name : 'regTm',         		index : 'regTm',         		 hidden: true}
        , { label : '변경시간',     	name : 'updTm',         		index : 'updTm',         		 hidden: true}
        , { label : '자료순번',     	name : 'ubseq',         		index : 'ubseq',         		 hidden: true}
        , { label : '그룹타입',     	name : 'grpType',         		index : 'grpType',         		 hidden: true}
        , { label : 'SQL',     	name : 'objSql',         		index : 'objSql',         		 hidden: false}
    ];

    // _grid02_option 에 jqGrid 의 loadComplete 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요. ( grid.js 에서 loadComplete를 옵션으로 설정하고있어서 loadComplete는 예외적으로 _grid02_option에서 정의해야함.)
    var _grid02_option = {
        caption: "객체 Id 조회" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid02_height + "px"
        , width: _grid02_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid02_url_dataLoad
        , rowNum: _grid02_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
//		, postData: $("#frm_searchFwobj").serializeArray()
        , loadComplete: function (data) {
            // 그리드 데이터 총 갯수
//            var sTotalRecords = $("#" + _grid02_id).jqGrid("getGridParam", "records");
//            $("#id_totalRecords").text(sTotalRecords);
            // 버튼 disabled 처리
            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);

            var ids = $("#" + _grid02_id).getDataIDs();
            // Grid Data Get!
            $.each(
                ids,function(idx, rowId){
                    rowData = $("#" + _grid02_id).getRowData(rowId);
                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
                    if (rowData.objId == '') {
                        $("#" + _grid02_id).setRowData(rowId, false, { background:"#9AFC95" });
                    }
                }
            );


        }
    };

    var _grid02_treeOption = {
//        ExpandColumn: 'grpName'
//        , treeReader:{
//            parent_id_field: "upGrpId"
//            , level_field: "menuDepth"
//        }/* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!! */
//        , localReader:{
//            id: "grpId"
//        }
    };



    //###### 화면 ID 선택 Grid ############################################################################

    var _grid03_id = "myGrid03";
    var _grid03_pagerId = "myGrid03Page";
    var _grid03_url_dataLoad = CONTEXT_ROOT + "/fwscrin/listFwscrinJqgrid.json";
    var _grid03_url_dataSave = CONTEXT_ROOT + "/fwscrin/saveFwscrin.json";
    var _grid03_url_dataDelete = CONTEXT_ROOT + "/fwscrin/deleteFwscrin.json";

    var _grid03_height = "250";
    var _grid03_width = "570";
    var _grid03_recordCountPerPage = 7 /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid03_colModel = [
        { label : '화면ID',     	name : 'scrinId',         		index : 'scrinId',         		width : 100  , hidden: false}
        , { label : '자료순번',     	name : 'ubseq',         		index : 'ubseq',         		width : 100  , hidden: true}
        , { label : '화면명',     	name : 'scrinNm',         		index : 'scrinNm',         		width : 100  , hidden: false}
        , { label : '테이블명',     	name : 'tblNm',         		index : 'tblNm',         		width : 100  , hidden: false}
        , { label : 'xmlID',     	name : 'xmlId',         		index : 'xmlId',         		width : 100  , hidden: false}
        , { label : '등록자',     	name : 'regId',         		index : 'regId',         		width : 100  , hidden: true}
        , { label : '변경자',     	name : 'updId',         		index : 'updId',         		width : 100  , hidden: true}
        , { label : '등록시간',     	name : 'regTm',         		index : 'regTm',         		width : 100  , hidden: true}
        , { label : '수정시간',     	name : 'updTm',         		index : 'updTm',         		width : 100  , hidden: true}
        , { label : '상세가로',     	name : 'dtlW',         		index : 'dtlW',         		width : 100  , hidden: true}
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
                ids,function(idx, rowId){
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





    $(document).ready(function() {
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


    function initSheet(){


//        GRID.fn_createTreeGrid( _grid01_id, _grid01_option , _grid01_colModel , _grid01_treeOption , _grid01_url_dataLoad ); // 트리형태의 그리드 생성.( Tree에서 필터 기능이 동작안함! )
        GRID.fn_createGridPageNav( _grid01_id, _grid01_option , _grid01_colModel , _grid01_pagerId , _grid01_url_dataLoad ); // 페이징의 그리드 생성.
//        GRID.fn_createGrid( _grid01_id, _grid01_option , _grid01_colModel ); // 일반 그리드 생성.

        GRID.fn_createGridPageNav( _grid02_id, _grid02_option , _grid02_colModel , _grid02_pagerId , _grid02_url_dataLoad ); // 페이징의 그리드 생성.
//        GRID.fn_createTreeGrid( _grid02_id, _grid02_option , _grid02_colModel , _grid02_treeOption , _grid02_url_dataLoad ); // 트리형태의 그리드 생성.
        GRID.fn_createGridPageNav( _grid03_id, _grid03_option , _grid03_colModel , _grid03_pagerId , _grid03_url_dataLoad ); // 페이징의 그리드 생성.


        $("#" + _grid01_id).jqGrid("filterToolbar"); // 필터 적용.
        $("#" + _grid02_id).jqGrid("filterToolbar"); // 필터 적용.
        $("#" + _grid03_id).jqGrid("filterToolbar"); // 필터 적용.

        $("#" + _grid01_id).jqGrid('setGridParam', {
            onSelectRow: function(rowId, status, e) {
                lastSel=rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#myGrid01").jqGrid('getRowData' ,rowId);
                    if( !UTILE.fn_isBlank( rowData['ubseq'] ) ){
                        for( var iCol=0; iCol < cm.length; iCol++ ){
                            var colNm = cm[iCol].name;
                            if ( colNm == "deleteYn" ){				//radio
                                $("#deleteYn"+rowData[colNm]).prop("checked", true);
                            }else if( 1 < colNm.indexOf('Yn') ){
                                $("#frm_" + colNm ).selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            }else if( 1 < colNm.indexOf('Type') ){
                                UTILE.fn_log("####### colNm : " + colNm );
                                $("#frm_" + colNm ).selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            }else{
                                $("#frm_" + colNm).val( rowData[colNm] );
                            }
                        }
                    }
                }

            }
        });



        $("#" + _grid02_id).jqGrid('setGridParam', {
            onSelectRow: function(rowId, status, e) {
                lastSel=rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#" + _grid02_id).jqGrid('getRowData' ,rowId);
                    if( !UTILE.fn_isBlank( rowData['ubseq'] ) ){
                        for( var iCol=0; iCol < cm.length; iCol++ ){
                            var colNm = cm[iCol].name;
                            if ( colNm == "deleteYn" ){				//radio
//                                $("#deleteYn"+rowData[colNm]).prop("checked", true);
                            }else if ( colNm == "grpType" ){				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
//                                $("#frm_grpType").selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            }else{
                                $("#frm_" + colNm).val( rowData[colNm] );
                            }
                        }
                    }
                }

            }
        });


        $("#" + _grid03_id).jqGrid('setGridParam', {
            onSelectRow: function(rowId, status, e) {
                lastSel=rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#" + _grid03_id).jqGrid('getRowData' ,rowId);
                    if( !UTILE.fn_isBlank( rowData['ubseq'] ) ){
                        for( var iCol=0; iCol < cm.length; iCol++ ){
                            var colNm = cm[iCol].name;
                            if ( colNm == "deleteYn" ){				//radio
//                                $("#deleteYn"+rowData[colNm]).prop("checked", true);
                            }else if ( colNm == "grpType" ){				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
//                                $("#frm_grpType").selectBox('value', rowData[colNm] ); // jquery.selectbox.js
                            }else{
                                $("#frm_" + colNm).val( rowData[colNm] );
                            }
                        }
                    }
                }

            }
        });


    }

	/* 각종 이벤트 초기화 */
    function initEvent() {

        //$("#searchScrinId").change(function() {    fn_search();    }); /** 화면ID */
        //$("#searchObjId").change(function() {    fn_search();    }); /** 객체ID */
        //$("#searchUbseq").change(function() {    fn_search();    }); /** 자료순번 */
        //$("#searchUpObjId").change(function() {    fn_search();    }); /** 상위객체ID */
        //$("#searchFindItemW").change(function() {    fn_search();    }); /** 검색항목가로 */
        //$("#searchRowPos").change(function() {    fn_search();    }); /** 검색행위치 */
        //$("#searchColPos").change(function() {    fn_search();    }); /** 검색열위치 */
        //$("#searchW").change(function() {    fn_search();    }); /** 검색객체넓이 */
        //$("#searchXmlId").change(function() {    fn_search();    }); /** xmlID */
        //$("#searchFindShowYn").change(function() {    fn_search();    }); /** 검색출력여부 */
        //$("#searchMustYn").change(function() {    fn_search();    }); /** 검색필수여부 */
        //$("#searchDtlShowYn").change(function() {    fn_search();    }); /** 상세출력여부 */
        //$("#searchDtlObjW").change(function() {    fn_search();    }); /** 상세객체넓이 */
        //$("#searchDtlRowPos").change(function() {    fn_search();    }); /** 상세행위치 */
        //$("#searchDtlColPos").change(function() {    fn_search();    }); /** 상세열위치 */
        //$("#searchDtlRowMgr").change(function() {    fn_search();    }); /** 상세행병합 */
        //$("#searchDtlMustYn").change(function() {    fn_search();    }); /** 상세필수여부 */
        //$("#searchShowSn").change(function() {    fn_search();    }); /** 그리드출력순번 */
        //$("#searchShowType").change(function() {    fn_search();    }); /** 그리드출력구분 */
        //$("#searchCellW").change(function() {    fn_search();    }); /** 그리드셀가로 */
        //$("#searchCellSort").change(function() {    fn_search();    }); /** 그리드셀정렬 */
        //$("#searchColor").change(function() {    fn_search();    }); /** 그리드컬러 */
        //$("#searchFont").change(function() {    fn_search();    }); /** 그리드폰트 */
        //$("#searchBgColor").change(function() {    fn_search();    }); /** 그리드배경색 */
        //$("#searchRegId").change(function() {    fn_search();    }); /** 등록자 */
        //$("#searchUpdId").change(function() {    fn_search();    }); /** 변경자 */
        //$("#searchRegTm").change(function() {    fn_search();    }); /** 등록시간 */
        //$("#searchUpdTm").change(function() {    fn_search();    }); /** 변경시간 */
        //$("#searchDayFindYn").change(function() {    fn_search();    }); /** 일자검색여부 */
        //$("#searchDtlRoleRowMgr").change(function() {    fn_search();    }); /** 상세권한행병합 */

        //$("#searchScrinId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 화면ID */
        //$("#searchObjId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 객체ID */
        //$("#searchUbseq").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 자료순번 */
        //$("#searchUpObjId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상위객체ID */
        //$("#searchFindItemW").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색항목가로 */
        //$("#searchRowPos").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색행위치 */
        //$("#searchColPos").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색열위치 */
        //$("#searchW").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색객체넓이 */
        //$("#searchXmlId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** xmlID */
        //$("#searchFindShowYn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색출력여부 */
        //$("#searchMustYn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 검색필수여부 */
        //$("#searchDtlShowYn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세출력여부 */
        //$("#searchDtlObjW").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세객체넓이 */
        //$("#searchDtlRowPos").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세행위치 */
        //$("#searchDtlColPos").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세열위치 */
        //$("#searchDtlRowMgr").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세행병합 */
        //$("#searchDtlMustYn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세필수여부 */
        //$("#searchShowSn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드출력순번 */
        //$("#searchShowType").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드출력구분 */
        //$("#searchCellW").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드셀가로 */
        //$("#searchCellSort").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드셀정렬 */
        //$("#searchColor").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드컬러 */
        //$("#searchFont").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드폰트 */
        //$("#searchBgColor").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그리드배경색 */
        //$("#searchRegId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 등록자 */
        //$("#searchUpdId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 변경자 */
        //$("#searchRegTm").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 등록시간 */
        //$("#searchUpdTm").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 변경시간 */
        //$("#searchDayFindYn").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 일자검색여부 */
        //$("#searchDtlRoleRowMgr").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 상세권한행병합 */
    }

	/* 화면이 로드된후 처리 초기화 */
    function initHtml() {

//        $("#searchRecordCountPerPage").val(_grid01_recordCountPerPage);
//        fn_gridSearch(pageIndex);

//        $('.table_wrapper').css('height', $(window).height() - 50 );
//        $('.table_wrapper').css('height', (_grid01_height*1) + 110 );
		var _changeHeight = (_grid01_height*1) + 110; // 그리드 크기에 맞춤.
		if( _changeHeight < $('#fwformViewDiv_r').height() ){
            _changeHeight = $('#fwformViewDiv_r').height();
		}
        UTILE.fn_log("$('#fwformViewDiv_r').height() : " + $('#fwformViewDiv_r').height() );
        UTILE.fn_log("_changeHeight" + _changeHeight );

        $("#" + _grid01_id).jqGrid('setGridHeight', (_changeHeight-120));

        $('#fwformViewDiv_l .table_wrapper').css('height', _changeHeight + 20 );

        $("#menuSelectDiv").css('height','100%');

        $('.table_wrapper td').css('height', '0' );
    }



	/*====================================================================
	 조회버튼이나 페이지 클릭시 실행되는 함수 fn_xxxxxx();
	 ====================================================================*/
    function fn_search(){
		/* Grid 검색 */
        fn_g_search("myGrid01");

        fn_formReset()
    }

    function fn_formReset(){
		/* 입력 필드 초기화 */
        $("#frm_fwform").find("input:text,input:hidden,select,textarea").val("");

        $("#frm_ubseq").val(0);
        $("#frm_grpType").selectBox('value', "" ); // jquery.selectbox.js
        $("#frm_useYn").selectBox('value', "" ); // jquery.selectbox.js

        $("#useYsnoY").prop("checked", true);					//사용여부
    }

    function fn_formSave_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataSave;
        var param = $("#frm_fwform").serializeArray();

        UTILE.fn_ajax4confirm("<spring:message code="common.save.msg" />", reqUrl, param, function (obj, resultData, textStatus, jqXHR) {
            if (200 == jqXHR.status) {

//                fn_search();
                fn_g_search("myGrid01");

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

    function fn_formDelete_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */


//        var selectedArray = GRID.fn_getCheckedArray( _grid01_id );
        var selectedStr = GRID.fn_getCheckedStr( _grid01_id , "ubseq");

        $("#frm_delUbseq").val(selectedStr);

        var _ubsdq = $("#frm_ubseq").val();


        var reqUrl = _grid01_url_dataDelete;
        var param = $("#frm_fwform").serializeArray();

        if( UNI.UTILE.fn_isBlank( _ubsdq ) && UNI.UTILE.fn_isBlank( selectedStr ) ){
            alert("삭제 항목을 선택해 주세요.")
		}else{

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
    function fn_g_search(gridId){

        $("#frm_rows").val(_grid01_recordCountPerPage);

        GRID.fn_searchGrid( gridId , _grid01_url_dataLoad , $("#frm_searchFwform").serializeArray() , function(obj, resultData, textStatus, jqXHR){}, {} );
    }


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 화면 구성 객체 정보 관리</h2><!--북마크페이지 class="on"추가-->--%>

<div>
	<h2 id="id_menu"></h2>
</div>

<form id="frm_searchFwform" name="frm_searchFwform" method="post" action="">
	<input type="hidden" title="rows" id="frm_rows"       name="rows"		/>
	<%--<input type="hidden" title="" id="searchRecordCountPerPage" name="recordCountPerPage"/>--%>
	<%--<fieldset>--%>
		<%--<!--폼양식-->--%>
		<%--<legend>Search</legend>--%>
		<%--<div class="srh_wrap">--%>
			<%--<div>--%>
				<%--<label>화면ID</label>--%>
				<%--<input type="text" title="" id="searchScrinId" name="searchScrinId"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>객체ID</label>--%>
				<%--<input type="text" title="" id="searchObjId" name="searchObjId"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상위객체ID</label>--%>
				<%--<input type="text" title="" id="searchUpObjId" name="searchUpObjId"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색항목가로</label>--%>
				<%--<input type="text" title="" id="searchFindItemW" name="searchFindItemW"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색행위치</label>--%>
				<%--<input type="text" title="" id="searchRowPos" name="searchRowPos"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색열위치</label>--%>
				<%--<input type="text" title="" id="searchColPos" name="searchColPos"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색객체넓이</label>--%>
				<%--<input type="text" title="" id="searchW" name="searchW"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>xmlID</label>--%>
				<%--<input type="text" title="" id="searchXmlId" name="searchXmlId"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색출력여부</label>--%>
				<%--<input type="text" title="" id="searchFindShowYn" name="searchFindShowYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>검색필수여부</label>--%>
				<%--<input type="text" title="" id="searchMustYn" name="searchMustYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세출력여부</label>--%>
				<%--<input type="text" title="" id="searchDtlShowYn" name="searchDtlShowYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세객체넓이</label>--%>
				<%--<input type="text" title="" id="searchDtlObjW" name="searchDtlObjW"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세행위치</label>--%>
				<%--<input type="text" title="" id="searchDtlRowPos" name="searchDtlRowPos"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세열위치</label>--%>
				<%--<input type="text" title="" id="searchDtlColPos" name="searchDtlColPos"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세행병합</label>--%>
				<%--<input type="text" title="" id="searchDtlRowMgr" name="searchDtlRowMgr"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세필수여부</label>--%>
				<%--<input type="text" title="" id="searchDtlMustYn" name="searchDtlMustYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드출력순번</label>--%>
				<%--<input type="text" title="" id="searchShowSn" name="searchShowSn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드출력여부</label>--%>
				<%--<input type="text" title="" id="searchShowYn" name="searchShowYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드셀가로</label>--%>
				<%--<input type="text" title="" id="searchCellW" name="searchCellW"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드셀정렬</label>--%>
				<%--<input type="text" title="" id="searchCellSort" name="searchCellSort"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드컬러</label>--%>
				<%--<input type="text" title="" id="searchColor" name="searchColor"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드폰트</label>--%>
				<%--<input type="text" title="" id="searchFont" name="searchFont"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>그리드배경색</label>--%>
				<%--<input type="text" title="" id="searchBgColor" name="searchBgColor"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>일자검색여부</label>--%>
				<%--<input type="text" title="" id="searchDayFindYn" name="searchDayFindYn"/>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<label>상세권한행병합</label>--%>
				<%--<input type="text" title="" id="searchDtlRoleRowMgr" name="searchDtlRoleRowMgr"/>--%>
			<%--</div>--%>
			<%--<span class="btn_wrap">--%>
                    <%--<button type="button" onclick="javascript:fn_search();" class="btn_srh">조회</button>--%>
                <%--</span>--%>
		<%--</div>--%>
	<%--</fieldset>--%>
</form>

<!-- table -->


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
		<div id="fwformViewDiv" class="pop_layout_wrap">
			<div id="fwformViewDiv_l" class="pop_layout_l" style="width:60%;"> <!-- margin-right=전체width/2+30 -->
				<div class="table_wrapper">
					<%--<h4>조회결과 <span>(<span id="id_totalRecords"></span>건)</span>--%>
					<table id="myGrid01"></table>
					<table id="myGrid01Page"></table>
				</div>
			</div>
			<div id="fwformViewDiv_r" class="pop_layout_r" style="width:40%;padding:0px;"> <!-- width=전체width/2+30 -->
				<!-- 버튼 -->
				<%--<div class="btngroup">--%>
				<%--<button type="button" onclick="" class="btn_pop_add2">추가</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_remove2">삭제</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_add3">전체추가</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_remove3">전체삭제</button>--%>
				<%--</div>--%>
				<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
					<form id="frm_fwform" name="frm_fwform" method="post" action="">
						<input type="hidden" title="자료순번" id="frm_ubseq"       name="ubseq"		value="${cmCfggrpidVO.ubseq}" />
						<input type="hidden" title="다중삭제" id="frm_delUbseq"       name="delUbseq"/>
						<table summary="등록/상세정보" class="tb02">
							<caption>등록/상세정보</caption>
							<colgroup>
								<col width="90">
								<col>
								<col width="90">
								<col>
							</colgroup>
							<tbody>
							<tr>
								<th scope="row">화면ID <span class="asterisk"></span> </th>
								<td><input type="text" title="화면ID" id="frm_scrinId" name="scrinId" value="${fwformVO.scrinId}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">객체ID <span class="asterisk"></span> </th>
								<td><input type="text" title="객체ID" id="frm_objId" name="objId" value="${fwformVO.objId}"/></td>
							</tr>
							<tr>
								<th scope="row">상위객체ID   </th>
								<td><input type="text" title="상위객체ID" id="frm_upObjId" name="upObjId" value="${fwformVO.upObjId}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">검색항목가로   </th>
								<td><input type="text" title="검색항목가로" id="frm_findItemW" name="findItemW" value="${fwformVO.findItemW}"/></td>
							</tr>
							<tr>
								<th scope="row">검색행위치   </th>
								<td><input type="text" title="검색행위치" id="frm_rowPos" name="rowPos" value="${fwformVO.rowPos}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">검색열위치   </th>
								<td><input type="text" title="검색열위치" id="frm_colPos" name="colPos" value="${fwformVO.colPos}"/></td>
							</tr>
							<tr>
								<th scope="row">검색객체넓이   </th>
								<td><input type="text" title="검색객체넓이" id="frm_w" name="w" value="${fwformVO.w}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">xmlID   </th>
								<td><input type="text" title="xmlID" id="frm_xmlId" name="xmlId" value="${fwformVO.xmlId}"/></td>
							</tr>
							<tr>
								<th scope="row">검색출력여부   </th>
								<td>
									<%--<input type="text" title="검색출력여부" id="frm_findShowYn" name="findShowYn" value="${fwformVO.findShowYn}"/>--%>
									<select id="frm_findShowYn" name="findShowYn" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.findShowYn}"> selected="selected"</c:if> ></option>
										<option value="N" <c:if test="${'N' eq fwformVO.findShowYn}"> selected="selected"</c:if> >사용 안함</option>
										<option value="Y" <c:if test="${'Y' eq fwformVO.findShowYn}"> selected="selected"</c:if> >사용</option>
									</select>
								</td>
								<th scope="row">검색필수여부   </th>
								<td>
									<%--<input type="text" title="검색필수여부" id="frm_mustYn" name="mustYn" value="${fwformVO.mustYn}"/>--%>
									<select id="frm_mustYn" name="mustYn" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.mustYn}"> selected="selected"</c:if> ></option>
										<option value="N" <c:if test="${'N' eq fwformVO.mustYn}"> selected="selected"</c:if> >사용 안함</option>
										<option value="Y" <c:if test="${'Y' eq fwformVO.mustYn}"> selected="selected"</c:if> >사용</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">상세출력여부   </th>
								<td>
									<%--<input type="text" title="상세출력여부" id="frm_dtlShowYn" name="dtlShowYn" value="${fwformVO.dtlShowYn}"/>--%>
									<select id="frm_dtlShowYn" name="dtlShowYn" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.dtlShowYn}"> selected="selected"</c:if> ></option>
										<option value="N" <c:if test="${'N' eq fwformVO.dtlShowYn}"> selected="selected"</c:if> >사용 안함</option>
										<option value="Y" <c:if test="${'Y' eq fwformVO.dtlShowYn}"> selected="selected"</c:if> >사용</option>
									</select>
								</td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">상세객체넓이   </th>
								<td><input type="text" title="상세객체넓이" id="frm_dtlObjW" name="dtlObjW" value="${fwformVO.dtlObjW}"/></td>
							</tr>
							<tr>
								<th scope="row">상세행위치   </th>
								<td><input type="text" title="상세행위치" id="frm_dtlRowPos" name="dtlRowPos" value="${fwformVO.dtlRowPos}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">상세열위치   </th>
								<td><input type="text" title="상세열위치" id="frm_dtlColPos" name="dtlColPos" value="${fwformVO.dtlColPos}"/></td>
							</tr>
							<tr>
								<th scope="row">상세행병합   </th>
								<td><input type="text" title="상세행병합" id="frm_dtlRowMgr" name="dtlRowMgr" value="${fwformVO.dtlRowMgr}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">상세필수여부   </th>
								<td>
									<%--<input type="text" title="상세필수여부" id="frm_dtlMustYn" name="dtlMustYn" value="${fwformVO.dtlMustYn}"/>--%>
									<select id="frm_dtlMustYn" name="dtlMustYn" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.dtlMustYn}"> selected="selected"</c:if> ></option>
										<option value="N" <c:if test="${'N' eq fwformVO.dtlMustYn}"> selected="selected"</c:if> >사용 안함</option>
										<option value="Y" <c:if test="${'Y' eq fwformVO.dtlMustYn}"> selected="selected"</c:if> >사용</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">그리드출력순번   </th>
								<td><input type="text" title="그리드출력순번" id="frm_showSn" name="showSn" value="${fwformVO.showSn}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">그리드출력여부   </th>
								<td>
									<%--<input type="text" title="그리드출력여부" id="frm_showType" name="showType" value="${fwformVO.showType}"/>--%>
									<select id="frm_showType" name="showType" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.showType}"> selected="selected"</c:if> ></option>
										<option value="0" <c:if test="${'0' eq fwformVO.showType}"> selected="selected"</c:if> >[0]미출력</option>
										<option value="1" <c:if test="${'1' eq fwformVO.showType}"> selected="selected"</c:if> >[1]그리드 및 액셀 다운로드 출력</option>
										<option value="2" <c:if test="${'2' eq fwformVO.showType}"> selected="selected"</c:if> >[2]그리드 만 출력</option>
										<option value="3" <c:if test="${'3' eq fwformVO.showType}"> selected="selected"</c:if> >[3]액셀 다운로드 만 출력</option>
										<option value="4" <c:if test="${'4' eq fwformVO.showType}"> selected="selected"</c:if> >[4]그리드 hidden 출력</option>
										<option value="5" <c:if test="${'5' eq fwformVO.showType}"> selected="selected"</c:if> >[5]그리드 hidden 출력 및 엑셀 출력</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">그리드셀가로   </th>
								<td><input type="text" title="그리드셀가로" id="frm_cellW" name="cellW" value="${fwformVO.cellW}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">그리드셀정렬   </th>
								<td><input type="text" title="그리드셀정렬" id="frm_cellSort" name="cellSort" value="${fwformVO.cellSort}"/></td>
							</tr>
							<tr>
								<th scope="row">그리드컬러   </th>
								<td><input type="text" title="그리드컬러" id="frm_color" name="color" value="${fwformVO.color}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">그리드폰트   </th>
								<td><input type="text" title="그리드폰트" id="frm_font" name="font" value="${fwformVO.font}"/></td>
							</tr>
							<tr>
								<th scope="row">그리드배경색   </th>
								<td><input type="text" title="그리드배경색" id="frm_bgColor" name="bgColor" value="${fwformVO.bgColor}"/></td>
							<%--</tr>--%>
							<%--<tr>--%>
								<th scope="row">일자검색여부   </th>
								<td>
									<%--<input type="text" title="일자검색여부" id="frm_dayFindYn" name="dayFindYn" value="${fwformVO.dayFindYn}"/>--%>
									<select id="frm_dayFindYn" name="dayFindYn" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.dayFindYn}"> selected="selected"</c:if> ></option>
										<option value="N" <c:if test="${'N' eq fwformVO.dayFindYn}"> selected="selected"</c:if> >사용 안함</option>
										<option value="Y" <c:if test="${'Y' eq fwformVO.dayFindYn}"> selected="selected"</c:if> >사용</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">상세권한행병합   </th>
								<td><input type="text" title="상세권한행병합" id="frm_dtlRoleRowMgr" name="dtlRoleRowMgr" value="${fwformVO.dtlRoleRowMgr}"/></td>
								<th scope="row">객체타입</th>
								<td>
									<%--<input type="text" id="frm_objType"       name="objType"		value="${fwformVO.objType}"  style="width:100%;" />	--%>

										<select id="frm_objType" name="objType" style="width: 130px;">
											<option value="" <c:if test="${'' eq fwformVO.objType}"> selected="selected"</c:if> ></option>
											<option value="text" <c:if test="${'text' eq fwformVO.objType}"> selected="selected"</c:if> >text</option>
											<option value="label" <c:if test="${'label' eq fwformVO.objType}"> selected="selected"</c:if> >label</option>
											<option value="select" <c:if test="${'select' eq fwformVO.objType}"> selected="selected"</c:if> >select</option>
											<option value="radio" <c:if test="${'radio' eq fwformVO.objType}"> selected="selected"</c:if> >radio</option>
											<option value="btn_n" <c:if test="${'btn_n' eq fwformVO.objType}"> selected="selected"</c:if> >btn_n</option>
											<option value="button_d" <c:if test="${'button_d' eq fwformVO.objType}"> selected="selected"</c:if> >button_d</option>
											<option value="button_g" <c:if test="${'button_g' eq fwformVO.objType}"> selected="selected"</c:if> >button_g</option>
											<option value="button_p" <c:if test="${'button_p' eq fwformVO.objType}"> selected="selected"</c:if> >button_p</option>
											<option value="button_s" <c:if test="${'button_s' eq fwformVO.objType}"> selected="selected"</c:if> >button_s</option>
											<option value="button_t" <c:if test="${'button_t' eq fwformVO.objType}"> selected="selected"</c:if> >button_t</option>
											
											
											<option value="date" <c:if test="${'date' eq fwformVO.objType}"> selected="selected"</c:if> >date</option>
											<option value="month_date" <c:if test="${'month_date' eq fwformVO.objType}"> selected="selected"</c:if> >month_date</option>										
											<option value="output" <c:if test="${'output' eq fwformVO.objType}"> selected="selected"</c:if> >output</option> 
											<option value="password" <c:if test="${'password' eq fwformVO.objType}"> selected="selected"</c:if> >password</option>
											<option value="select_month" <c:if test="${'select_month' eq fwformVO.objType}"> selected="selected"</c:if> >select_month</option>
											<option value="select_year" <c:if test="${'select_year' eq fwformVO.objType}"> selected="selected"</c:if> >select_year</option> 
											<option value="textarea" <c:if test="${'textarea' eq fwformVO.objType}"> selected="selected"</c:if> >textarea</option>
											<option value=wave <c:if test="${'wave' eq fwformVO.objType}"> selected="selected"</c:if> >wave</option>	 
											<option value="hidden" <c:if test="${'hidden' eq fwformVO.objType}"> selected="selected"</c:if> >hidden</option> 
										</select>
								</td>
							</tr>

							<tr>
								<th scope="row">암호화여부   </th>
								<td>
									<%--<input type="text" id="frm_cryptoYn"       name="cryptoYn"		value="${fwformVO.cryptoYn}"  style="width:100%;" />--%>
										<select id="frm_cryptoYn" name="cryptoYn" style="width:130px;">
											<option value="" <c:if test="${'' eq fwformVO.cryptoYn}"> selected="selected"</c:if> ></option>
											<option value="N" <c:if test="${'N' eq fwformVO.cryptoYn}"> selected="selected"</c:if> >사용 안함</option>
											<option value="Y" <c:if test="${'Y' eq fwformVO.cryptoYn}"> selected="selected"</c:if> >사용</option>
										</select>
								</td>
								<th scope="row">실행함수</th>
								<td><input type="text" id="frm_extFun"       name="extFun"		value="${fwformVO.extFun}"  style="width:100%;" /></td>
							</tr>

							<tr>
								<th scope="row">검색 타입</th>
								<td colspan="3">
									<select id="frm_findType" name="findType" style="width:130px;">
										<option value="" <c:if test="${'' eq fwformVO.findType}"> selected="selected"</c:if> ></option>
										<option value="eq" <c:if test="${'eq' eq fwformVO.findType}"> selected="selected"</c:if> >equal 검색</option>
										<option value="like" <c:if test="${'like' eq fwformVO.findType}"> selected="selected"</c:if> >LIKE '%파라메터%'</option>
										<option value="f_like" <c:if test="${'f_like' eq fwformVO.findType}"> selected="selected"</c:if> >LIKE '파라메터%'</option>
										<option value="r_like" <c:if test="${'r_like' eq fwformVO.findType}"> selected="selected"</c:if> >LIKE '%파라메터'</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">SQL문</th>
								<td colspan="3">
									<%--<input type="text" id="frm_objSql" name="objSql" value="${fwformVO.objSql}" style="width:100%;"/>--%>
									<textarea id="frm_objSql" name="objSql" style="width: 332px;" rows="10">${fwformVO.objSql}</textarea>
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



