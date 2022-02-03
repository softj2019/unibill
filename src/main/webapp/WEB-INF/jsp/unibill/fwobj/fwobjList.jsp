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
    var _grid01_pagerId = "myGrid02";
    var _grid01_url_dataLoad = CONTEXT_ROOT + "/fwobj/listFwobjTableJqgrid.json";
//    var _grid01_url_dataLoad = null; // 자동 data load 막기위함.
    var _grid01_url_dataSave = CONTEXT_ROOT + "/fwobj/saveFwobj.json";
    var _grid01_url_dataDelete = CONTEXT_ROOT + "/fwobj/deleteFwobj.json";
    var _grid01_height = "350";
    var _grid01_width = "800";
    var _grid01_recordCountPerPage = 1000 /* 페이징 : 한 페이지에 보일 row 수 */

    var _grid01_colModel = [
          { label : '테이블',     	name : 'tableNameFrom',         		index : 'tableNameFrom',         		 hidden: false}
        , { label : '컬럼',     	name : 'objIdFrom',         		index : 'objIdFrom',         		 hidden: false}
        , { label : '컬럼 주석',     	name : 'objNmFrom',         		index : 'objNmFrom',         		 hidden: false}
        , { label : '추출 ColId 컬럼',     	name : 'colIdFrom',         		index : 'colIdFrom',         		 hidden: true}
        , { label : '객체ID',     	name : 'objId',         		index : 'objId',         		 hidden: false}
        , { label : '객체명',     	name : 'objNm',         		index : 'objNm',         		 hidden: false}
        , { label : '컬럼ID',     	name : 'colId',         		index : 'colId',         		 hidden: false}
        , { label : '객체 형식',     	name : 'objType',         		index : 'objType',         		 hidden: false}
        , { label : '객체 위치',     	name : 'objPos',         		index : 'objPos',         		 hidden: false}
        , { label : 'xml ID',     	name : 'xmlId',         		index : 'xmlId',         		 hidden: false}
        , { label : '그룹코드',     	name : 'grpCd',         		index : 'grpCd',         		 hidden: false}
        , { label : '설명',     	name : 'remark',         		index : 'remark',         		 hidden: true}
        , { label : '자동완성여부',     	name : 'autoGenCol',         		index : 'autoGenCol',         		 hidden: false}
        , { label : '등록자',     	name : 'regId',         		index : 'regId',         		 hidden: true}
        , { label : '변경자',     	name : 'updId',         		index : 'updId',         		 hidden: true}
        , { label : '등록시간',     	name : 'regTm',         		index : 'regTm',         		 hidden: true}
        , { label : '변경시간',     	name : 'updTm',         		index : 'updTm',         		 hidden: true}
        , { label : '자료순번',     	name : 'ubseq',         		index : 'ubseq',         		 hidden: true}
        , { label : '그룹타입',     	name : 'grpType',         		index : 'grpType',         		 hidden: false}
        , { label : 'SQL',     	name : 'objSql',         		index : 'objSql',         		 hidden: true}
    ];

    // _grid01_option 에 jqGrid 의 loadComplete 이벤트는 정의해도 적용이 안되므로 GRID.fn_createGrid 한 후에 setGridParam 를 이용하여 이벤트를 등록하세요. ( grid.js 에서 loadComplete를 옵션으로 설정하고있어서 loadComplete는 예외적으로 _grid01_option에서 정의해야함.)
    var _grid01_option = {
        caption: "" /* 웹접근성을 위한 TABLE CAPTION 추가 */
        , height: _grid01_height + "px"
        , width: _grid01_width + "px" /* 그룹명 의 width 와 동일하게함. */
        , url: _grid01_url_dataLoad
		, rowNum: _grid01_recordCountPerPage /* 페이징 : 한 페이지에 보일 row 수 */
        , isCheckboxAble: true /* 공통 grid.js 에서만 사용하기 위한 용도로 임의로 추가하였음. */
//		, postData: $("#frm_searchFwobj").serializeArray()
		, loadComplete: function (data) {
            // 그리드 데이터 총 갯수
            var sTotalRecords = $("#" + _grid01_id).jqGrid("getGridParam", "records");
            $("#id_totalRecords").text(sTotalRecords);
            // 버튼 disabled 처리
            // COMMON.fn_button_disabled(sBtnId, false, sBtnType);

            var ids = $("#" + _grid01_id).getDataIDs();
            // Grid Data Get!
            $.each(
                ids,function(idx, rowId){
                    rowData = $("#" + _grid01_id).getRowData(rowId);
                    // 만약 rowName 컬럼의 데이터가 공백이라면 해당 Row의 색상을 변경!
                    if (rowData.objId == '') {
                        $("#" + _grid01_id).setRowData(rowId, false, { background:"#9AFC95" });
                    }
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

    $(document).ready(function() {
// 		loadValidator();
        loadPage();
    });



	/*====================================================================
	 화면 초기화
	 ====================================================================*/
    function loadPage() {
        //initSheet();
        initEvent();
        initHtml();
    }


    function initSheet(){

//        GRID.fn_createGridPageNav( _grid01_id, _grid01_option , _grid01_colModel , _grid01_treeOption , _grid01_url_dataLoad ); // 트리형태의 그리드 생성.
        GRID.fn_createGridPageNav( _grid01_id, _grid01_option , _grid01_colModel , _grid01_pagerId , _grid01_url_dataLoad ); // 트리형태의 그리드 생성.

        $("#" + _grid01_id).jqGrid('setGridParam', {
            onSelectRow: function(rowId, status, e) {

                fn_formReset();

                lastSel=rowId; // jqg1 , jqg2 , jqg3
                if (rowId != null) {
                    var cm = $(this).jqGrid('getGridParam', 'colModel');
                    var rowData = $("#myGrid01").jqGrid('getRowData' ,rowId);

                    UTILE.fn_log("rowData['ubseq'] ==>>  " + rowData['ubseq'] );

                    if( !UTILE.fn_isBlank( rowData['ubseq'] ) && 0 != rowData['ubseq'] ){

                        UTILE.fn_log("!UTILE.fn_isBlank(rowData['ubseq'])" );

                        if( 0 == rowData['ubseq'] ){
                            var _objIdFromVal = rowData['objIdFrom'];
                            var _objNmFromVal = rowData['objNmFrom'];

                            $("#frm_objId").val( _objIdFromVal );
                            $("#frm_objNm").val( _objNmFromVal );
                            $("#frm_remark").val( _objNmFromVal );
                            $("#frm_objType").selectBox('value', 'text' );
                            $("#frm_objPos").val('N');

						}else{

                            for( var iCol=0; iCol < cm.length; iCol++ ){
                                var _colNm = cm[iCol].name;
                                var _colVal = rowData[_colNm];

                                if ( _colNm == "deleteYn" ){				//radio
                                    $("#deleteYn"+_colVal).prop("checked", true);
                                }else if ( _colNm == "objType" ){				//select
                                    $("#frm_objType").selectBox('value', _colVal ); // jquery.selectbox.js
                                }else if ( _colNm == "autoGenCol" ) {				//select
                                    $("#frm_autoGenCol").selectBox('value', _colVal); // jquery.selectbox.js
                                }else if ( _colNm == "grpType" ) {				//select
                                    $("#frm_grpType").selectBox('value', _colVal); // jquery.selectbox.js
                                }else{
                                    $("#frm_" + _colNm).val( _colVal );
                                }
                            }
                        }

                    }else{
                        var _objIdFrom = rowData['objIdFrom'];
                        var _objNmFrom = rowData['objNmFrom'];
                        $("#frm_objId").val( _objIdFrom );
                        $("#frm_colId").val( _objIdFrom );
                        $("#frm_objNm").val( _objNmFrom );
                        $("#frm_remark").val( _objNmFrom );
                        $("#frm_objType").selectBox('value', "text" ); // jquery.selectbox.js
					}
                }

            }
        });
    }

	/* 각종 이벤트 초기화 */
    function initEvent() {

        $("#searchObjType").change(function() {    fn_search();    }); /** 객체 형식 */
		//$("#searchObjPos").change(function() {    fn_search();    }); /** 객체 위치 */
		//$("#searchGrpCd").change(function() {    fn_search();    }); /** 그룹코드 */
        $("#searchAutoGenCol").change(function() {    fn_search();    }); /** 자동완성여부 */
		$("#searchGrpType").change(function() {    fn_search();    }); /** 그룹타입 */
		//$("#searchUpdColInfo").change(function() {    fn_search();    }); /** 변경컬럼정보 */

        $("#searchTableName").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 테이블명 */
        $("#searchObjId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 객체ID */
        $("#searchObjNm").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 객체명 */
        $("#searchColId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 컬럼ID */
		//$("#searchObjType").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 객체 형식 */
        $("#searchObjPos").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 객체 위치 */
        $("#searchXmlId").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** xml ID */
        $("#searchGrpCd").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그룹코드 */
        $("#searchRemark").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 설명 */
		//$("#searchAutoGenCol").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 자동완성여부 */
		//$("#searchGrpType").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 그룹타입 */
		//$("#searchObjSql").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** SQL문 */
		//$("#searchUpdColInfo").keypress(function(event) {    if ( event.which == 13 ) {    fn_search();    }    }); /** 변경컬럼정보 */
    }

	/* 화면이 로드된후 처리 초기화 */
    function initHtml() {

        $("#searchRecordCountPerPage").val(_grid01_recordCountPerPage);
//        fn_gridSearch(pageIndex);

//        $('.table_wrapper').css('height', $(window).height() - 50 );
//        $('.table_wrapper').css('height', (_grid01_height*1) + 110 );
		var _changeHeight = (_grid01_height*1) + 110; // 그리드 크기에 맞춤.

        UTILE.fn_log("_grid01_height" + _grid01_height);
        UTILE.fn_log("$('.pop_layout_r').height()" + $('.pop_layout_r').height());

		if( _changeHeight < $('.pop_layout_r').height() ){
            _changeHeight = $('.pop_layout_r').height();
            $("#" + _grid01_id).jqGrid('setGridHeight', (_changeHeight-120));
		}
        $('.table_wrapper').css('height', _changeHeight );

    }



	/*====================================================================
	 조회버튼이나 페이지 클릭시 실행되는 함수 fn_xxxxxx();
	 ====================================================================*/
    function fn_search(){
		/* Grid 검색 */
		initSheet();
		initEvent();
        initHtml();
        fn_g_search("myGrid01");

        fn_formReset();
    }

    function fn_formReset(){
		/* 입력 필드 초기화 */
        $("#frm_fwobj").find("input:text,input:hidden,select,textarea").val("");

        $("#frm_ubseq").val(0);

        $("#frm_objType").selectBox('value', "" ); // jquery.selectbox.js
        $("#frm_grpType").selectBox('value', "" ); // jquery.selectbox.js
        $("#frm_useYn").selectBox('value', "" ); // jquery.selectbox.js

        $("#useYsnoY").prop("checked", true);					//사용여부
    }

    /**
     * 화면 유효성 첵크
     */
    function fn_formValidate() {
        if(UTILE.fn_isBlank($("#frm_objId").val())){
            UTILE.fn_alert("객체ID(objId)는 필수값 입니다.");
            return false;
        }
//        if (UTILE.fn_isBlank($("#frm_objNm").val())) {
//            UTILE.fn_alert("객체명(objNm)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_colId").val())) {
//            UTILE.fn_alert("컬럼ID(colId)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_objType").val())) {
//            UTILE.fn_alert("객체 형식(objType)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_objPos").val())) {
//            UTILE.fn_alert("객체 위치(objPos)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_xmlId").val())) {
//            UTILE.fn_alert("xml ID(xmlId)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_grpCd").val())) {
//            UTILE.fn_alert("그룹코드(grpCd)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_remark").val())) {
//            UTILE.fn_alert("설명(remark)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_autoGenCol").val())) {
//            UTILE.fn_alert("자동완성여부(autoGenCol)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_ubseq").val())) {
//            UTILE.fn_alert("자료순번(ubseq)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_grpType").val())) {
//            UTILE.fn_alert("그룹타입(grpType)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_objSql").val())) {
//            UTILE.fn_alert("SQL문(objSql)는 필수값 입니다.");
//            return false;
//        }
//        if (UTILE.fn_isBlank($("#frm_updColInfo").val())) {
//            UTILE.fn_alert("변경컬럼정보(updColInfo)는 필수값 입니다.");
//            return false;
//        }
        return true;
    }

    function fn_formSave_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataSave;
        var param = $("#frm_fwobj").serializeArray();

        if (fn_formValidate()) {

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
    }

    function fn_formDelete_AJAX(){
		/* HTML Form 신규, 수정, 삭제 대상 레코드 적용 */
        var reqUrl = _grid01_url_dataDelete;
        var param = $("#frm_fwobj").serializeArray();

        if(UTILE.fn_isBlank($("#frm_objId").val())){
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
        UTILE.fn_objToStringLog( $("#frm_searchFwobj").serializeArray() );

//        var ajaxParams = $("#frm_searchFwobj").serializeArray(); // $('#frm1, #frm2').serializeArray();
//        ajaxParams.push({name: 'rows', value: _grid01_recordCountPerPage });
//        GRID.fn_searchGrid( gridId , _grid01_url_dataLoad , ajaxParams , function(obj, resultData, textStatus, jqXHR){}, {} );

//        _grid01_url_dataLoad = "/fwobj/listFwobjTableJqgrid.json";
//        $("#"+gridId).jqGrid('setGridParam',{url:_grid01_url_dataLoad, page:1});
//        $("#"+gridId).trigger('reloadGrid');

        GRID.fn_searchGridBasic( gridId , "frm_searchFwobj" , 1 );
    }


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 객체 ID 관리</h2><!--북마크페이지 class="on"추가-->--%>
<div>
	<h2 id="id_menu"></h2>
</div>

<form id="frm_searchFwobj" name="frm_searchFwobj" method="post" action="">
	<input type="hidden" title="" id="searchRecordCountPerPage" name="recordCountPerPage"/>
	<fieldset>
		<!--폼양식-->
		<legend>Search</legend>
		<div class="srh_wrap">
			<div>
				<label>테이블 이름</label>
				<input type="text" title="" id="searchTableName" name="searchTableName"/>
			</div>
			<div>
				<label>객체ID</label>
				<input type="text" title="" id="searchObjId" name="searchObjId"/>
			</div>
			<div>
				<label>객체명</label>
				<input type="text" title="" id="searchObjNm" name="searchObjNm"/>
			</div>
			<div>
				<label>컬럼ID</label>
				<input type="text" title="" id="searchColId" name="searchColId"/>
			</div>
			<div>
				<label>객체 형식</label>
				<%--<input type="text" title="" id="searchObjType" name="searchObjType"/>--%>
				<select id="searchObjType" name="searchObjType" style="width: 157px;">
					<option value="" selected="selected"></option>
					<option value="select"       >select      </option>
					<option value="select_year"  >select_year </option>
					<option value="select_month" >select_month</option>
					<option value="text"         >text        </option>
					<option value="textarea"     >textarea    </option>
					<option value="wave"         >wave        </option>
					<option value="date"         >date        </option>
					<option value="label"        >label       </option>
					<option value="radio"        >radio       </option>
					<option value="button_s"     >button_s    </option>
					<option value="button_g"     >button_g    </option>
					<option value="button_d"     >button_d    </option>
					<option value="button_p"     >button_p    </option>
					<option value="btn_n"        >btn_n       </option>
					<option value="password"     >password    </option>
				</select>
			</div>
			<div>
				<label>객체 위치</label>
				<input type="text" title="" id="searchObjPos" name="searchObjPos"/>
			</div>
			<div>
				<label>xml ID</label>
				<input type="text" title="" id="searchXmlId" name="searchXmlId"/>
			</div>
			<div>
				<label>그룹코드</label>
				<input type="text" title="" id="searchGrpCd" name="searchGrpCd"/>
			</div>
			<div>
				<label>설명</label>
				<input type="text" title="" id="searchRemark" name="searchRemark"/>
			</div>
			<div>
				<label>자동완성여부</label>
				<%--<input type="text" title="" id="searchAutoGenCol" name="searchAutoGenCol"/>--%>
				<select id="searchAutoGenCol" name="searchAutoGenCol" style="width: 157px;">
					<option value="" selected="selected"></option>
					<option value="Y">사용</option>
					<option value="N">사용 안함</option>
				</select>
			</div>
			<div>
				<label>그룹타입</label>
				<%--<input type="text" title="" id="searchGrpType" name="searchGrpType"/>--%>
				<select id="searchGrpType" name="searchGrpType" style="width: 157px;">
					<option value="" selected="selected"></option>
					<c:forEach var="cfggrptypes" items="${cmCfggrptypeList}" varStatus="status">
						<option value="${cfggrptypes.grpType }" >${cfggrptypes.grpNm }</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label>Sql</label>
				<input type="text" title="" id="searchObjSql" name="searchObjSql"/>
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
					<h4>조회결과 <span>(<span id="id_totalRecords"></span>건)</span>
					<table id="myGrid01"></table>
					<table id="myGrid02"></table>
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
					<form id="frm_fwobj" name="frm_fwobj" method="post" action="">
						<table summary="등록/상세정보" class="tb02">
							<caption>등록/상세정보</caption>
							<colgroup>
								<col width="90">
								<col>
							</colgroup>
							<tbody>
							<tr>
								<th scope="row">객체ID <span class="asterisk"></span> </th>
								<td>
									<input type="text" title="객체ID" id="frm_objId" name="objId" value="${fwobjVO.objId}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">객체명   </th>
								<td><input type="text" title="객체명" id="frm_objNm" name="objNm" value="${fwobjVO.objNm}"/></td>
							</tr>
							<tr>
								<th scope="row">컬럼ID <span class="asterisk"></span> </th>
								<td>
									<input type="text" title="컬럼ID" id="frm_colId" name="colId" value="${fwobjVO.colId}"/>
								</td>
							</tr>
							<tr>
								<th scope="row">객체 형식   </th>
								<td>
									<%--<input type="text" title="객체 형식" id="frm_objType" name="objType" value="${fwobjVO.objType}"/>--%>

										<select id="frm_objType" name="objType" style="width: 232px;">
											<option value=""             <c:if test="${            '' eq fwobjVO.objType}"> selected="selected"</c:if>></option>
											<option value="select"       <c:if test="${      'select' eq fwobjVO.objType}"> selected="selected"</c:if>>select      </option>
											<option value="select_year"  <c:if test="${ 'select_year' eq fwobjVO.objType}"> selected="selected"</c:if>>select_year </option>
											<option value="select_month" <c:if test="${'select_month' eq fwobjVO.objType}"> selected="selected"</c:if>>select_month</option>
											<option value="text"         <c:if test="${        'text' eq fwobjVO.objType}"> selected="selected"</c:if>>text        </option>
											<option value="textarea"     <c:if test="${    'textarea' eq fwobjVO.objType}"> selected="selected"</c:if>>textarea    </option>
											<option value="wave"         <c:if test="${        'wave' eq fwobjVO.objType}"> selected="selected"</c:if>>wave        </option>
											<option value="date"         <c:if test="${        'date' eq fwobjVO.objType}"> selected="selected"</c:if>>date        </option>
											<option value="label"        <c:if test="${       'label' eq fwobjVO.objType}"> selected="selected"</c:if>>label       </option>
											<option value="radio"        <c:if test="${       'radio' eq fwobjVO.objType}"> selected="selected"</c:if>>radio       </option>
											<option value="button_s"     <c:if test="${    'button_s' eq fwobjVO.objType}"> selected="selected"</c:if>>button_s    </option>
											<option value="button_g"     <c:if test="${    'button_g' eq fwobjVO.objType}"> selected="selected"</c:if>>button_g    </option>
											<option value="button_d"     <c:if test="${    'button_d' eq fwobjVO.objType}"> selected="selected"</c:if>>button_d    </option>
											<option value="button_p"     <c:if test="${    'button_p' eq fwobjVO.objType}"> selected="selected"</c:if>>button_p    </option>
											<option value="btn_n"        <c:if test="${       'btn_n' eq fwobjVO.objType}"> selected="selected"</c:if>>btn_n       </option>
											<option value="password"     <c:if test="${    'password' eq fwobjVO.objType}"> selected="selected"</c:if>>password    </option>
										</select>
								</td>
							</tr>
							<tr>
								<th scope="row">객체 위치   </th>
								<td><input type="text" title="객체 위치" id="frm_objPos" name="objPos" value="${fwobjVO.objPos}"/></td>
							</tr>
							<tr>
								<th scope="row">xml ID   </th>
								<td><input type="text" title="xml ID" id="frm_xmlId" name="xmlId" value="${fwobjVO.xmlId}"/></td>
							</tr>
							<tr>
								<th scope="row">그룹코드   </th>
								<td><input type="text" title="그룹코드" id="frm_grpCd" name="grpCd" value="${fwobjVO.grpCd}"/></td>
							</tr>
							<tr>
								<th scope="row">설명   </th>
								<td><input type="text" title="설명" id="frm_remark" name="remark" value="${fwobjVO.remark}"/></td>
							</tr>
							<tr>
								<th scope="row">자동완성여부   </th>
								<td>
									<%--<input type="text" title="자동완성여부" id="frm_autoGenCol" name="autoGenCol" value="${fwobjVO.autoGenCol}"/>--%>
									<select id="frm_autoGenCol" name="autoGenCol" style="width: 232px;">
										<option value="" <c:if test="${'' eq fwobjVO.autoGenCol}"> selected="selected"</c:if> ></option>
										<option value="Y" <c:if test="${'Y' eq fwobjVO.autoGenCol}"> selected="selected"</c:if> >사용</option>
										<option value="N" <c:if test="${'N' eq fwobjVO.autoGenCol}"> selected="selected"</c:if> >사용 안함</option>
									</select>
								</td>
							</tr>
							<%--<tr>--%>
								<%--<th scope="row">자료순번 <span class="asterisk"></span> </th>--%>
								<%--<td>--%>
									<input type="hidden" title="자료순번" id="frm_ubseq" name="ubseq" value="${fwobjVO.ubseq}"/>
								<%--</td>--%>
							<%--</tr>--%>
							<tr>
								<th scope="row">그룹타입   </th>
								<td>
									<%--<input type="text" title="그룹타입" id="frm_grpType" name="grpType" value="${fwobjVO.grpType}"/>--%>
									<select id="frm_grpType" name="grpType" style="width: 232px;">
										<option value="" selected="selected"></option>
										<c:forEach var="cfggrptypes" items="${cmCfggrptypeList}" varStatus="status">
											<option value="${cfggrptypes.grpType }"  <c:if test="${cfggrptypes.grpType eq cmCfggrpidVO.grpType}"> selected="selected"</c:if> >${cfggrptypes.grpNm }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row">SQL</th>
								<td>
									<%--<input type="text" title="" id="frm_objSql" name="objSql" value="${fwobjVO.objSql}"/>--%>
									<textarea id="frm_objSql" name="objSql" style="width: 232px;" rows="10">${fwobjVO.objSql}</textarea>
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



