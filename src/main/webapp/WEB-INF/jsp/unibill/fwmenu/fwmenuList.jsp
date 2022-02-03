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
    var _grid01_url_dataLoad = CONTEXT_ROOT + "/fwmenu/listFwmenuJqgrid.json";
    var _grid01_url_dataSave = CONTEXT_ROOT + "/fwmenu/saveFwmenu.json";
    var _grid01_url_dataDelete = CONTEXT_ROOT + "/fwmenu/deleteFwmenu.json";
    
    var _grid01_height = "340";
    var _grid01_width = "800";
    var _grid01_recordCountPerPage = 1000 /* 페이징 : 한 페이지에 보일 row 수 */
    var _grid01_colModel = [
  { label : '메뉴ID',     	name : 'menuId',         		index : 'menuId',         		width : 50  , hidden: false , key:true}
 , { label : '자료순번',     	name : 'ubseq',         		index : 'ubseq',         		width : 100  , hidden: true}
 , { label : '메뉴명',     	name : 'menuNm',         		index : 'menuNm',         		width : 200  , hidden: false}
 , { label : '레벨',     	name : 'depth',         		index : 'depth',         		width : 40  , hidden: false}
 , { label : '메뉴순서',     	name : 'menuSeq',         		index : 'menuSeq',         		width : 40  , hidden: false}
 , { label : '상위메뉴ID',     	name : 'upMenuId',         		index : 'upMenuId',         		width : 100  , hidden: false}
 , { label : 'isLeaf',     	name : 'isLeaf',         		index : 'isLeaf',         		width : 100  , hidden: true}
 , { label : '설명',     	name : 'remark',         		index : 'remark',         		width : 100  , hidden: true}
 , { label : 'url',     	name : 'url',         		index : 'url',         		width : 100  , hidden: false}
 , { label : '도움말',     	name : 'help',         		index : 'help',         		width : 100  , hidden: true}
 , { label : '템플릿구분',     	name : 'tmpType',         		index : 'tmpType',         		width : 100  , hidden: true}
 , { label : '테이블명',     	name : 'tblNm',         		index : 'tblNm',         		width : 100  , hidden: false}
 , { label : '등록자',     	name : 'regId',         		index : 'regId',         		width : 100  , hidden: true}
 , { label : '변경자',     	name : 'updId',         		index : 'updId',         		width : 100  , hidden: true}
 , { label : '등록시간',     	name : 'regTm',         		index : 'regTm',         		width : 100  , hidden: true}
 , { label : '변경시간',     	name : 'updTm',         		index : 'updTm',         		width : 100  , hidden: true}
    ];

    // _grid01_option 에 jqGrid 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요.
    var _grid01_option = {
        caption: "" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid01_height + "px"
        , width: _grid01_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid01_url_dataLoad
        , rowNum: _grid01_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
//        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
//        , loadComplete: function (data) {
//            // 그리드 데이터 총 갯수
//            var sTotalRecords = $("#" + _grid01_id).jqGrid("getGridParam", "records");
//            $("#id_totalRecords").text(sTotalRecords);
//            // 버튼 disabled 처리
//            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);
//
//            var ids = $("#" + _grid01_id).getDataIDs();
//            // Grid Data Get!
//            $.each(
//                ids,function(idx, rowId){
//                    rowData = $("#" + _grid01_id).getRowData(rowId);
//                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
////                    if (rowData.objId == '') {
////                        $("#" + _grid01_id).setRowData(rowId, false, { background:"#9AFC95" });
////                    }
//                }
//            );
//        }
    };

    var _grid01_treeOption = {
        ExpandColumn: 'menuNm'
        , treeReader:{
            parent_id_field: "upMenuId"
            , level_field: "depth"
			, leaf_field: "isLeaf"
			, expanded_field: true
        }/* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!! */
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

        GRID.fn_createTreeGrid( _grid01_id, _grid01_option , _grid01_colModel , _grid01_treeOption , _grid01_url_dataLoad ); // 트리형태의 그리드 생성.

//        $("#" + _grid01_id).jqGrid("filterToolbar",{stringResult: true,searchOnEnter : false}); // 필터 적용.

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
                            }else if ( colNm == "grpType" ){				//select
//                                $("#frm_grpType").val(rowData[colNm]).attr("selected", "selected"); // ext 값으로 select 하기
//                                $("#frm_grpType").val(rowData[colNm]); // value값으로 select 하기
                                $("#frm_grpType").selectBox('value', rowData[colNm] ); // jquery.selectbox.js
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
//        $("#searchGrpType").change(function() {    fn_search();    });

		/** 메뉴ID */
		$('#searchMenuId').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 메뉴명 */
		$('#searchMenuNm').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 레벨 */
		$('#searchDepth').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 메뉴순서 */
		$('#searchMenuSeq').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 상위메뉴ID */
		$('#searchUpMenuId').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 설명 */
		$('#searchRemark').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** url */
		$('#searchUrl').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 도움말 */
		$('#searchHelp').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 템플릿구분 */
		$('#searchTmpType').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });
		/** 테이블명 */
		$('#searchTblNm').keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    });

    }

	/* 화면이 로드된후 처리 초기화 */
    function initHtml() {

        $("#searchRecordCountPerPage").val(_grid01_recordCountPerPage);

//        fn_gridSearch(pageIndex);

//        $('.table_wrapper').css('height', $(window).height() - 50 );
//        $('.table_wrapper').css('height', (_grid01_height*1) + 110 );
		var _changeHeight = (_grid01_height*1) + 110; // 그리드 크기에 맞춤.
		if( _changeHeight < $('.pop_layout_r').height() ){
            _changeHeight = $('.pop_layout_r').height();
		}
        $('.table_wrapper').css('height', _changeHeight );

        $("#" + _grid01_id).jqGrid('setGridHeight', (_changeHeight-50));
//    	$("#" + _grid01_id).jqGrid('setGridHeight',$("#container").height() - $(".ui-jqgrid-titlebar").height() - $("#pmygrid").height());
    }



	/*====================================================================
	 조회버튼이나 페이지 클릭시 실행되는 함수 fn_xxxxxx();
	 ====================================================================*/
    function fn_search(){
		/* Grid 검색 */
        fn_g_search("myGrid01");
        fn_g_Expand("myGrid01");
        fn_formReset()
    }

    function fn_formReset(){
		/* 입력 필드 초기화 */
        $("#frm_fwmenu").find("input:text,input:hidden,select,textarea").val("");

        $("#frm_ubseq").val(0);
        $("#frm_grpType").selectBox('value', "" ); // jquery.selectbox.js
        $("#frm_useYn").selectBox('value', "" ); // jquery.selectbox.js

        $("#useYsnoY").prop("checked", true);					//사용여부
    }

    function fn_formSave_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataSave;
        var param = $("#frm_fwmenu").serializeArray();

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

    function fn_formDelete_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataDelete;
        var param = $("#frm_fwmenu").serializeArray();

        var _ubsdq = $("#frm_ubseq").val();
        if( UNI.UTILE.fn_isBlank( _ubsdq ) ){
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
        GRID.fn_searchGrid( gridId , _grid01_url_dataLoad , $("#frm_searchFwmenu").serializeArray() , function(obj, resultData, textStatus, jqXHR){}, {} );

//        GRID.fn_searchGridBasic( gridId , "frm_searchFwmenu" , 1 );

//        var _defaultGridMethodType = "post"; // 서버 호출 방식 : post , get
//        var _defaultGridDataType = "json"; // jqgrid 기본값은 xml 이다.
//        $("#"+gridId).jqGrid("setGridParam", {
//            datatype: _defaultGridDataType
//            , postData: COMMON.fn_get_serialize( "frm_searchFwmenu" )
//            , mtype: _defaultGridMethodType
//        }).trigger("reloadGrid");
    }

    function fn_g_Expand(gridId) {
//        var rows = $("#" + gridId ).jqGrid('getGridParam','data');
//        UTILE.fn_log("######### rows : " + rows );
//        for (var i = 0; i < rows.length; i++){
//            var childRows = $("#" + gridId ).jqGrid('getNodeChildren', rows[i]);
//            UTILE.fn_log("######### childRows : " +childRows );
//            $("#" + gridId ).jqGrid('expandNode', rows[i]);
//            $("#" + gridId ).jqGrid('expandRow', rows[i]);
//            for (var j = 0; j < childRows.length; j++) {
//                var childRows2 = $("#" + gridId ).jqGrid('getNodeChildren', childRows[j]);
//                UTILE.fn_log("######### ######### childRows2[j] : " +childRows2[j] );
//                $("#" + gridId ).jqGrid('expandNode', childRows2[j]);
//                $("#" + gridId ).jqGrid('expandRow', childRows2[j]);
//
//                for (var k = 0; k < childRows2.length; k++) {
//                    var childRows3 = $("#" + gridId ).jqGrid('getNodeChildren', childRows2[k]);
//                    UTILE.fn_log("######### ######### ######### childRows3[k] : " +childRows3[k] );
//                    $("#" + gridId ).jqGrid('expandNode', childRows3[k]);
//                    $("#" + gridId ).jqGrid('expandRow', childRows3[k]);
//                }
//            }
//        }
    }


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> fwmenu관리</h2><!--북마크페이지 class="on"추가-->--%>
<div>
	<h2 id="id_menu"></h2>
</div>

<form id="frm_searchFwmenu" name="frm_searchFwmenu" method="post" action="">
	<input type="hidden" title="" id="searchRecordCountPerPage" name="recordCountPerPage"/>
	<fieldset>
		<!--폼양식-->
		<legend>Search</legend>
		<div class="srh_wrap">
			<div>
				<label>메뉴ID</label>
				<input type="text" title="" id="searchMenuId" name="searchMenuId"/>
			</div>
			<div>
				<label>메뉴명</label>
				<input type="text" title="" id="searchMenuNm" name="searchMenuNm"/>
			</div>
			<div>
				<label>레벨</label>
				<input type="text" title="" id="searchDepth" name="searchDepth"/>
			</div>
			<div>
				<label>메뉴순서</label>
				<input type="text" title="" id="searchMenuSeq" name="searchMenuSeq"/>
			</div>
			<div>
				<label>상위메뉴ID</label>
				<input type="text" title="" id="searchUpMenuId" name="searchUpMenuId"/>
			</div>
			<div>
				<label>설명</label>
				<input type="text" title="" id="searchRemark" name="searchRemark"/>
			</div>
			<div>
				<label>url</label>
				<input type="text" title="" id="searchUrl" name="searchUrl"/>
			</div>
			<div>
				<label>도움말</label>
				<input type="text" title="" id="searchHelp" name="searchHelp"/>
			</div>
			<div>
				<label>템플릿구분</label>
				<input type="text" title="" id="searchTmpType" name="searchTmpType"/>
			</div>
			<div>
				<label>테이블명</label>
				<input type="text" title="" id="searchTblNm" name="searchTblNm"/>
			</div>
			<span class="btn_wrap">
                    <button type="button" onclick="javascript:fn_search();" class="btn_srh">조회</button>
                </span>
		</div>
	</fieldset>
</form>

<!-- table -->


<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
	<!-- 조회결과 -->
	<div class="table_wrap" style="padding:0px;">
		<div class="pop_layout_wrap">
			<div class="pop_layout_l" style="width:70%;"> <!-- margin-right=전체width/2+30 -->
				<div class="table_wrapper" style="height:380px;">
					<%--<h4>조회결과 <span>(<span id="id_totalRecords"></span>건)</span>--%>
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
					<form id="frm_fwmenu" name="frm_fwmenu" method="post" action="">
						<input type="hidden" title="자료순번" id="frm_ubseq"       name="ubseq"		value="${cmCfggrpidVO.ubseq}" />
						<table summary="등록/상세정보" class="tb02">
							<caption>등록/상세정보</caption>
							<colgroup>
								<col width="90">
								<col>
							</colgroup>
							<tbody>
							<tr>
								<th scope="row">메뉴ID <span class="asterisk"></span> </th>
								<td><input type="text" title="메뉴ID" id="frm_menuId" name="menuId" value="${fwmenuVO.menuId}"/></td>
							</tr>
							<tr>
								<th scope="row">메뉴명 <span class="asterisk"></span> </th>
								<td><input type="text" title="메뉴명" id="frm_menuNm" name="menuNm" value="${fwmenuVO.menuNm}"/></td>
							</tr>
							<tr>
								<th scope="row">레벨 <span class="asterisk"></span> </th>
								<td><input type="text" title="레벨" id="frm_depth" name="depth" value="${fwmenuVO.depth}"/></td>
							</tr>
							<tr>
								<th scope="row">메뉴순서 <span class="asterisk"></span> </th>
								<td><input type="text" title="메뉴순서" id="frm_menuSeq" name="menuSeq" value="${fwmenuVO.menuSeq}"/></td>
							</tr>
							<tr>
								<th scope="row">상위메뉴ID <span class="asterisk"></span> </th>
								<td><input type="text" title="상위메뉴ID" id="frm_upMenuId" name="upMenuId" value="${fwmenuVO.upMenuId}"/></td>
							</tr>
							<tr>
								<th scope="row">설명   </th>
								<td><input type="text" title="설명" id="frm_remark" name="remark" value="${fwmenuVO.remark}"/></td>
							</tr>
							<tr>
								<th scope="row">url   </th>
								<td><input type="text" title="url" id="frm_url" name="url" value="${fwmenuVO.url}"/></td>
							</tr>
							<tr>
								<th scope="row">도움말   </th>
								<td><input type="text" title="도움말" id="frm_help" name="help" value="${fwmenuVO.help}"/></td>
							</tr>
							<tr>
								<th scope="row">템플릿구분   </th>
								<td><input type="text" title="템플릿구분" id="frm_tmpType" name="tmpType" value="${fwmenuVO.tmpType}"/></td>
							</tr>
							<tr>
								<th scope="row">테이블명   </th>
								<td><input type="text" title="테이블명" id="frm_tblNm" name="tblNm" value="${fwmenuVO.tblNm}"/></td>
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



