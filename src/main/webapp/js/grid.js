/**
 *  컨텐츠에 필요한 이벤트
 * @constructor
 * @namespace {Object} UNI.GRID
 */
UNI.GRID = function (_mod_content, $, undefined) {

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
    // 		fn_log2("[GRID] obj=", obj );
    //
    // 	}else{
    // 		if ( obj.constructor == Array ){
    // 			var array = $.map( obj , function(value, index) {
    // 				fn_log2("[GRID] obj.value=", value );
    // 				return [value];
    // 			});
    // 		}else{
    // 			;
    // 			fn_log2("[GRID] obj.toString()=", obj.toString() );
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

    /**
     * @description jQuery의 serializeArray()는 폼 데이터를   {name:"name", value:"value" } 형태의 Array를 리턴한다. 각 Array를 순서대로 돌면서, name,value쌍을  javascript object {} 에 담아서 jqGrid 의 appendPostData 메서드로 파라미터를 추가한다.
     * @method
     * @returns {boolean}
     */
    function fn_addFormData( gridId , formId ) {
        var arr = $('#' + formId).serializeArray();

        var params = {};
        $.each(arr, function(){
            var jname;
            jQuery.each(this, function(i, val){
                if (i=="name") {
                    jname = val;
                } else if (i=="value") {
                    params[jname] = val;
                }
            });
        });
        $("#" + gridId).appendPostData(params);

        return true;
    }



    /**
     * @description 기본 그리드 생성
     * @method fn_createGrid
     * @param gridId {String} 그리드가 생성될 html ID 명
     * @param gridInitOption {Object} 그리드 세부 옵션
     * @param gridCol {Object} 그리드 컬럼 목록 객체
     * @example GRID.fn_createGrid( "myGrid01", _gridOption , _colModel);
     * @example $("#" + _grid01_id).jqGrid("filterToolbar"); // 필터 적용.
     */
    fn_createGrid = function (gridId, gridInitOption, gridCol) {

        UTILE.fn_log("[GRID.fn_createGrid] [" + gridId + "]================ Call =============");

        var gridObj = $("#" + gridId);

        gridInitOption = $.extend({}, {
			  caption: gridId+"그리드입니다." /* 웹접근성을 위한 TABLE CAPTION 추가 */
            , mtype: _defaultGridMethodType
            , datatype: _defaultGridDataType
            , colModel: gridCol
            , gridview: true
            // , height: '10px'
            // , width: '10px'
            , autoWidth: true
            , height: "auto"
            , cmTemplate: { width: 10,autoResizable: true, autoresizeOnLoad: true }
            , shrinkToFit: true

            // ,searching: {
            //     defaultSearch: "cn"
            // }
            // , searchoptions: { sopt: ["eq"] }
            // , stype: "select"
            // , searchoptions: { value: ":Any;FE:FedEx;TN:TNT;DH:DHL" }

            , loadError: function (jqXHR, textStatus, errorThrown) {
                // UTILE.fn_alert('HTTP status code: ' + jqXHR.status + '\n' + 'textStatus: ' + textStatus + '\n' + 'errorThrown: ' + errorThrown);
                // alert('HTTP message body (jqXHR.responseText): ' + '\n' + jqXHR.responseText);
                $("#" + gridId).html(jqXHR.responseText);
            }
        }, gridInitOption);

        if('local' != gridInitOption.datatype && UTILE.fn_isBlank( gridInitOption.url ) ){
            UTILE.fn_log("[GRID.fn_createGrid] [" + gridId + "] 그리드에서 호출할 URL 입력해주세요.");
            gridInitOption.datatype = 'local'; // url 없이 datatype이 'json' 로 선언 된경우 자기 자신 jsp 페이지 호출하는 액션을 반복적으로 호출 하는 현상이 있었음.
        }

        gridObj.jqGrid(gridInitOption);
        // gridObj.jqGrid(gridInitOption).jqGrid("filterToolbar"); // 필터 적용 서버로 컬럼 id 로 입력값이 넘어간다.
    }

    /**
     * @method fn_createGridPageNav
     * @param gridId {String} 그리드가 생성될 html ID 명
     * @param gridInitOption {Object} 그리드 세부 옵션
     * @param gridCol {Object} 그리드 컬럼 목록 객체
     * @param gridPagerId {String} 페이징이 생성될 html ID 명
     * @param reqUrl {String} 그리드 목록 조회에 사용되는 기본 URL ( parameter 값은 gridInitOption의 postData에 적용해야함. )
     * @description 페이징 처리가 되는 Grid 생성
     * searchGrpType는 처음 그리드에 적용될 파라메터 값으로 그리드에서 제공되는 페이징이나 한번에 볼 row 수가 변경되는경우에도 별다른 수정이 없으면 searchGrpType 값은 고정이다.
     * @example
     GRID.fn_createGridPageNav( "myGrid0A", {height: '340px'}, _colModel,"myGrid0B","/cmCfggrpid/listCmCfggrpidJson.do?searchGrpType="+$("#searchGrpType").val());
     * @example
     * GRID.fn_createGridPageNav( "myGrid0A", {
                height: '340px'
                , postData: {
                    searchGrpType: function() { return $("#searchGrpType").val(); }
                }
            }, _colModel,"myGrid0B","/cmCfggrpid/listCmCfggrpidJson.do"); // searchGrpType 값이 동적으로 바뀌는것에 대응할 수 있다.
     * @example
     * GRID.fn_createGridPageNav( "myGrid0A", {
                height: '340px'
                , url: "/cmCfggrpid/listCmCfggrpidJson.do"
                , postData: {
                    searchGrpType: function() { return $("#searchGrpType").val(); }
                }
            }, _colModel,"myGrid0B",null); // searchGrpType 값이 동적으로 바뀌는것에 대응할 수 있다.
     * @example
     * GRID.fn_createGridPageNav( "myGrid0A", {
                height: '340px'
                , url: "/cmCfggrpid/listCmCfggrpidJson.do"
                , postData: {
                    searchGrpType: function() { return $("#searchGrpType").val(); }
                }
            }, _colModel,"myGrid0B",{}); // searchGrpType 값이 동적으로 바뀌는것에 대응할 수 있다.
     * @author 이성원
     */
    fn_createGridPageNav = function (gridId, gridInitOption, gridCol, gridPagerId, reqUrl) {

        UTILE.fn_log("[GRID.fn_createGridPageNav] [" + gridId + "]================ Call =============");

        var gridObj = $("#" + gridId);

        // 멀티체크 여부
        var sMultChk = false;
        if( !UTILE.fn_isBlank( gridInitOption.isCheckboxAble ) ){
            UTILE.fn_log( "[GRID.fn_createGridPageNav] [" + gridId + "] gridInitOption.isCheckboxAble = " + gridInitOption.isCheckboxAble );
            sMultChk = gridInitOption.isCheckboxAble;
        }

        gridInitOption = $.extend({}, {
            mtype : _defaultGridMethodType
            // datatype: "local"
            , datatype: _defaultGridDataType
            , url: reqUrl
            // , sortname  : 'ubseq'
            , sortorder: "DESC"
            , colModel: gridCol
            // , height: '100px'
            // , width: '100px'
            , autoWidth: true
            , height: "auto"
            , rowNum: 10
            , rowList: [7, 10, 15, 25, 50, 100, 500, 1000 ]
            , viewrecords: true
            , jsonReader: {
                repeatitems: false
                , root: function(obj) {
                    // UTILE.fn_log( "[GRID.fn_createGridPageNav] [" + gridId + "]jsonReader.root = " + obj.rows );
                    return obj.rows;
                }
                , page: function(obj) {
                    UTILE.fn_log( "[GRID.fn_createGridPageNav] [" + gridId + "]jsonReader.page = " + obj.page );
                    return obj.page;
                }
                , total: function(obj) {
                    UTILE.fn_log( "[GRID.fn_createGridPageNav] [" + gridId + "]jsonReader.total = " + obj.total );
                    return obj.total;
                }
                , records: function(obj) {
                    UTILE.fn_log( "[GRID.fn_createGridPageNav] [" + gridId + "]jsonReader.records = " + obj.totalRecords );
                    return obj.totalRecords;
                }
            }
            , pager: "#" + gridPagerId
            , gridview: true
            , rownumbers: true
            , loadtext: "검색 중입니다."
            , emptyrecords: "검색된 데이터가 없습니다."
            , recordtext: "총 {2} 건 ({0}-{1})"
            , shrinkToFit: true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
            , multiselect: sMultChk
            , multiboxonly: sMultChk
            // , ondblClickRow: function (rowId) { /* 여기에 선언하면 사용하는 jsp에서는 적용( $("#" + _grid01_id).jqGrid('setGridParam', { ondblClickRow: function (rowId) {...} ......}); )이 안되는 구조임. 단 사용하는 jsp 에서 gridInitOption 에 ondblClickRow를 적용하면 사용가능함!  */
            //     var sRowData = $("#" + gridId).getRowData(rowId);
            //     var sUbseq = sRowData["ubseq"];
            //
            //     // COMMON.fn_view_content(_sFormId, _sDetailFormId, sUbseq);
            // }
            , loadComplete: function (data) {
                // 그리드 데이터 총 갯수
                var sTotalRecords = $("#" + gridId).jqGrid("getGridParam", "records");
                $("#id_totalRecords").text(sTotalRecords);
                // 버튼 disabled 처리
                // COMMON.fn_button_disabled(sBtnId, false, sBtnType);
            }
            , loadError: function (jqXHR, textStatus, errorThrown) {
                // UTILE.fn_alert('HTTP status code: ' + jqXHR.status + '\n' + 'textStatus: ' + textStatus + '\n' + 'errorThrown: ' + errorThrown);
                // alert('HTTP message body (jqXHR.responseText): ' + '\n' + jqXHR.responseText);
                $("#" + gridId).html(jqXHR.responseText);
            }
        }, gridInitOption);


        if('local' != gridInitOption.datatype && UTILE.fn_isBlank( gridInitOption.url ) ){
            UTILE.fn_log("[GRID.fn_createGridPageNav] [" + gridId + "] 그리드에서 호출할 URL 입력해주세요.");
            gridInitOption.datatype = 'local'; // url 없이 datatype이 'json' 로 선언 된경우 자기 자신 jsp 페이지 호출하는 액션을 반복적으로 호출 하는 현상이 있었음.
        }

        gridObj.jqGrid(gridInitOption).navGrid("#" + gridPagerId, {
            edit: false,
            add: false,
            del: false,
            search: false,
            refresh: false
        })
        ;
               // .jqGrid("gridResize")
               // .jqGrid("filterToolbar", {
               //      beforeSearch: function () {
               //          // startTime = new Date();
               //          // measureTime = true;
               //          return false; // allow filtering
               //      }
               //  });

    }

    /**
     * @description 트리 형태의 그리드 생성.
     * - 내부적으로 드래그 & 드롭 적용되므로 페이지 상단에 <script type="text/javascript" src="<c:url value='/extLib/jquery.tablednd.js'/>"></script> 설정이 필요함.
     *     - $("#" + _grid01_id).tableDnD({
     *          scrollAmount: 0
     *          , onDrop: function(table, row) {
     *              UTILE.fn_log("드롭2!!");
     *              UTILE.fn_log(jQuery("#" + _grid01_id).tableDnDSerialize() );
     *          }
     *          , onDragStart: function(table, row) {
     *              UTILE.fn_log("드래그 시작2!");
     *          }
     *      });
     * - "key:true" 설정이 되어있어야함.
     *     var _grid01_colModel = [{ label : '메뉴ID',     	name : 'menuId',         		index : 'menuId',         		width : 100  , hidden: false , key:true} ... ];
     * @method fn_createTreeGrid
     * @param gridId {String} 그리드가 생성될 html ID 명
     * @param gridInitOption {Object} 그리드 세부 옵션
     * @param gridCol {Object} 그리드 컬럼 목록 객체
     * @param gridTreeOption {Object} tree 형태로 보여질때 사용하는 옵션만 지정할 것.
     * @param reqUrl {String} 그리드 목록 조회에 사용되는 기본 URL ( parameter 값은 gridInitOption의 postData에 적용해야함. )
     * @example GRID.fn_createGrid( "myGrid01", _gridOption , _colModel);
     */
    fn_createTreeGrid = function (gridId, gridInitOption, gridCol, gridTreeOption, reqUrl) {

        UTILE.fn_log("[GRID.fn_createTreeGrid] [" + gridId + "]================ Call =============");

        var gridObj = $("#" + gridId);

        gridTreeOption = $.extend({}, {
              treeGrid: true
            , gridview: true
            , treeGridModel: 'adjacency'
            // , treedatatype: _defaultGridMethodType
            , treedatatype: _defaultGridTreeDataType
            , loadonce:false /* tree 를 동적으로 로딩 하기위해서는 false 로 셋팅해야함. */
            , ExpandColumn: 'name'
            , ExpandColClick: true
            // , localReader:{
            // 	id: "grpId"
            // }
            // , treeReader:{
            // 	level_field: "menuDepth"
            // 	, parent_id_field: "upGrpId"
            // }
        }, gridTreeOption);

        // gridObj.tableDnD({
        //     scrollAmount: 0 /* Auto-scoll has some problems with IE7  (it scrolls even when it shouldn't), work-around: set scrollAmount to 0 */
        //     , onDrop: function(table, row) {
        //         UTILE.fn_log("드롭!!");
        //         UTILE.fn_log(jQuery("#" + gridId).tableDnDSerialize() );
        //     }
        //     , onDragStart: function(table, row) {
        //         UTILE.fn_log("드래그 시작!");
        //     }
        // });

        gridInitOption = $.extend({}, {
            mtype : _defaultGridMethodType
            // datatype: "local"
            , datatype: _defaultGridDataType
            , url: reqUrl
            , colModel: gridCol
            , gridview: true
            , height: '100px'
            , width: '100px'
            , shrinkToFit: true
            , sortorder: "ASC"
            , loadonce : gridTreeOption.loadonce
            , sortname: gridTreeOption.sortname
            , treeGrid: gridTreeOption.treeGrid
            , treeGridModel: gridTreeOption.treeGridModel
            , treedatatype: gridTreeOption.treedatatype
            , ExpandColumn: gridTreeOption.ExpandColumn
            , ExpandColClick: gridTreeOption.ExpandColClick
            , treeReader: gridTreeOption.treeReader /* treeReader 설정 하지 않으면 TREE 펼침 및 접기가 안됨!!( expanded_field 값을 셋팅하면 이름이 나오지 않고 isleaf 값이 트리구조에 보임.) */
            // , localReader: gridTreeOption.localReader
            , gridComplete: function () {
                $("#_empty", "#" + gridId).addClass("nodrag nodrop");
                jQuery("#" + gridId).tableDnDUpdate();

                UTILE.fn_log("[GRID.fn_createTreeGrid] [" + gridId + "] gridComplete!!");
                UTILE.fn_log2("[GRID.fn_createTreeGrid] [" + gridId + "] tableDnDSerialize() ==>> " , jQuery("#" + gridId).tableDnDSerialize() );
            }
            , loadError: function (jqXHR, textStatus, errorThrown) {
                // UTILE.fn_alert('HTTP status code: ' + jqXHR.status + '\n' + 'textStatus: ' + textStatus + '\n' + 'errorThrown: ' + errorThrown);
                // alert('HTTP message body (jqXHR.responseText): ' + '\n' + jqXHR.responseText);
                $("#" + gridId).html(jqXHR.responseText);
            }
        }, gridInitOption);

        if('local' != gridInitOption.datatype && UTILE.fn_isBlank( gridInitOption.url ) ){
            UTILE.fn_log("[GRID.fn_createTreeGrid] [" + gridId + "] 그리드에서 호출할 URL 입력해주세요.");
            gridInitOption.datatype = 'local'; // url 없이 datatype이 'json' 로 선언 된경우 자기 자신 jsp 페이지 호출하는 액션을 반복적으로 호출 하는 현상이 있었음.
        }

        gridObj.jqGrid(gridInitOption);
    }

    /**
     * @description Function for resize the grid according to the width of the resized window
     * @method resizeJqGridWidth
     * @param string grid_id - jqGrid id used in current page
     * @param string div_id - parent div_id according to whom it will need to resize
     * @param string width - width of the grid that has been set during initialize the grid setup
     * @returns void
     */
    fn_resizeJqGridWidth = function (grid_id, div_id, width) {

        UTILE.fn_log("[GRID.fn_resizeJqGridWidth] [" + grid_id + "]================ Call =============");

        $(window).bind('resize', function() {
            $('#' + grid_id).setGridWidth(width, true); //Back to original width
            $('#' + grid_id).setGridWidth($('#' + div_id).width(), true); //Resized to new width as per window
        }).trigger('resize');
    }

    fn_resizeJqGridColumnWidths = function (gridName) {

        UTILE.fn_log("[GRID.fn_resizeJqGridColumnWidths] [" + gridName + "]================ Call =============");

        if ($('#' + gridName).attr('columnsSet')=='true') return;

        $('body').append('<span id="widthTest">WIDTHTEST</span>');
        $('#gbox_' + gridName + ' .ui-jqgrid-htable,#' + gridName).css('width', 'inherit');
        $('#' + gridName).parent().css('width', 'inherit');

        var containerWidth=$('#' + gridName).width() - 20;
        var columnNames = $("#" + gridName).jqGrid('getGridParam', 'colModel');
        var thisWidth;

        // Loop through Cols
        for (var itm = 0, itmCount = columnNames.length; itm < itmCount; itm++) {

            var curObj = $('[aria-describedby=' + gridName + '_' + columnNames[itm].name + ']');

            var thisCell = $('#' + gridName + '_' + columnNames[itm].name + ' div');
            $('#widthTest').html(thisCell.text()).css({
                'font-family': thisCell.css('font-family'),
                'font-size': thisCell.css('font-size'),
                'font-weight': thisCell.css('font-weight')
            });
            var maxWidth = $('#widthTest').width() + 40;
            //var maxWidth = 0;

            // Loop through Rows
            for (var itm2 = 0, itm2Count = curObj.length; itm2 < itm2Count; itm2++) {
                var thisCell = $(curObj[itm2]);

                $('#widthTest').html(thisCell.html()).css({
                    'font-family': thisCell.css('font-family'),
                    'font-size': thisCell.css('font-size'),
                    'font-weight': thisCell.css('font-weight')
                });

                thisWidth = $('#widthTest').width();
                if (thisWidth > maxWidth) maxWidth = thisWidth;
            }
            if (maxWidth > containerWidth) maxWidth=containerWidth;
            $("#" + gridName).jqGrid('setColProp','amount',{width: maxWidth});
            var gw = $("#" + gridName).jqGrid('getGridParam','width');
            $("#" + gridName).jqGrid('setGridWidth',gw,true);
            $('#' + gridName + ' .jqgfirstrow td:eq(' + itm + '), #' + gridName + '_' + columnNames[itm].name).width(maxWidth).css('min-width', maxWidth);

        }
        $('#widthTest').remove();
        $('#' + gridName).attr('columnsSet','true');
    }

    /**
     * @description 첵크된 row data 객체를 배열 형태로 반환한다.
     * @param grid_id
     * @returns {Array}
     */
    fn_getCheckedArray = function (grid_id) {

        UTILE.fn_log("[GRID.fn_getCheckedArray] [" + grid_id + "]================ Call =============");

        var i, selRowIds = $("#" + grid_id).jqGrid("getGridParam", "selarrrow"), n, rowData;
        var cm = $("#" + grid_id).jqGrid('getGridParam', 'colModel');

        UTILE.fn_log("[GRID.fn_getCheckedArray] [" + grid_id + "] selRowIds :" + selRowIds );

        var returnArrayObj = [];

        for (i = 0, n = selRowIds.length; i < n; i++) {
            rowData = $("#" + grid_id).getRowData(selRowIds[i]);
            returnArrayObj.push(rowData);

            UTILE.fn_objToStringLog2("[GRID.fn_getCheckedArray] [" + grid_id + "] " , rowData);
        }


        return returnArrayObj;
    }

    /**
     * @description 첵크된 row data 에서 지정한 컬럼의 값을 ',' 로 구분된 문자열로 반환한다.
     * @param grid_id
     * @param targetColName
     * @returns {*} ',' 로 구분된 문자열로 반환한다.
     */
    fn_getCheckedStr = function (grid_id,targetColName) {

        UTILE.fn_log("[GRID.fn_getCheckedStr] [" + grid_id + "]================ Call =============");

        var i, selRowIds = $("#" + grid_id).jqGrid("getGridParam", "selarrrow"), n, rowData, returnStr;
        var cm = $("#" + grid_id).jqGrid('getGridParam', 'colModel');

        UTILE.fn_log("[GRID.fn_getCheckedStr] [" + grid_id + "] selRowIds : " + selRowIds );

        for (i = 0, n = selRowIds.length; i < n; i++) {
            rowData = $("#" + grid_id).getRowData(selRowIds[i]);
            for( var iCol=0; iCol < cm.length; iCol++ ){
                var colNm = cm[iCol].name;
                if( colNm == targetColName ){
                    // UTILE.fn_log("colNm:" + colNm + "=" + rowData[colNm] );
                    if( UTILE.fn_isBlank( returnStr ) ){
                        returnStr = rowData[colNm];
                    }else{
                        returnStr = returnStr + "," + rowData[colNm];
                    }
                }
            }
        }

        UTILE.fn_log("[GRID.fn_getCheckedStr] [" + grid_id + "] returnStr : " + returnStr );

        return returnStr;
    }

    /**
     * @description 그리드 외부의 form으로 부터 입력받은 parameter 값으로 그리드 검색할 경우 form 의 parameter 값을 Grid로 전달해주어 검색하는 용도.
     * @param gridId search 할 grid ID
     * @param searchFormId form 으로 Grid 조회할 경우 사용하는 검색 form id
     * @param pageNo grid 현재 페이지 번호
     */
    fn_searchGridBasic = function(gridId , searchFormId , pageNo){

        UTILE.fn_log("[GRID.fn_searchGridBasic] [" + gridId + "] gridId ==>> " + gridId + " ================Start=============");
        UTILE.fn_log2("[GRID.fn_searchGridBasic] [" + gridId + "] searchFormId ==>> ", searchFormId);
        UTILE.fn_log2("[GRID.fn_searchGridBasic] [" + gridId + "] pageNo ==>> ", pageNo);
        UTILE.fn_objToStringLog2("[GRID.fn_searchGridBasic] [" + gridId + "] COMMON.fn_get_serialize( searchFormId ) ==>> ", COMMON.fn_get_serialize( searchFormId ));

        if( UTILE.fn_isBlank( pageNo )) {
            pageNo = 1;
        }

        $("#"+gridId).jqGrid("setGridParam", {
            datatype: _defaultGridDataType
            , page: pageNo
            , postData: COMMON.fn_get_serialize( searchFormId )
            , mtype: _defaultGridMethodType
        }).trigger("reloadGrid");

        var _thisGridOptions = $("#"+gridId).jqGrid('getGridParam');
        UTILE.fn_log2("[GRID.fn_searchGridBasic] _thisGridOptions_id ==>> " , _thisGridOptions.id );
        UTILE.fn_log2("[GRID.fn_searchGridBasic] _thisGridOptions_datatype ==>> " , _thisGridOptions.datatype );
        UTILE.fn_log2("[GRID.fn_searchGridBasic] _thisGridOptions_url ==>> " , _thisGridOptions.url );

    }

    /**
     * @description 그리드 조회( 내부적으로 Ajax 를 호출하는 방식 ).
     * @method fn_searchGrid
     * @param {string} gridId - 대상 Grid ID
     * @param {string} reqUrl - 호출 서버 URL
     * @param {object} param - 전송할 데이터
     * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수
     * @param {object} ajaxOptions - 추가 옵션
     * @example GRID.fn_searchGrid("myGrid01","/cmCfggrpid/listCmCfggrpidJson.do", $("#searchFrm").serializeArray() , null, null);
     * @example GRID.fn_searchGrid("myGrid01","/cmCfggrpid/listCmCfggrpidJson.do", $("#searchFrm").serializeArray() , function(obj, resultData, textStatus, jqXHR){alert(textStatus);}, { async : true, type  : "POST"} );
     */
    fn_searchGrid = function (gridId, reqUrl, param, callback, ajaxOptions) {

        UTILE.fn_log("[GRID.fn_searchGrid] [" + gridId + "] reqUrl=" + reqUrl + " ================Start=============");
        UTILE.fn_log2("[GRID.fn_searchGrid] [" + gridId + "] reqUrl=", reqUrl);
        //_vconsole.log("[GRID.fn_searchGrid] [" + gridId + "] param=", UTILE.fn_objToStringLog(param));
        UTILE.fn_log2("[GRID.fn_searchGrid] [" + gridId + "] callback=", callback);
        UTILE.fn_log2("[GRID.fn_searchGrid] [" + gridId + "] ajaxOptions=", ajaxOptions);

        if (UTILE.fn_isBlank(gridId)) {
            UTILE.fn_alert("그리드 ID를 지정해주세요.");
        } else {


            // var _vGridId = "#" + gridId;
            var $_vGridId = $("#" + gridId);

            /* 조회 */
            // var reqUrl = "/cmCfggrpid/listCmCfggrpidJson.do";
            // var param = $("#searchFrm").serializeArray();

            var _thisGridOptions = $("#"+gridId).jqGrid('getGridParam');
            // UTILE.fn_objToStringLog2("[GRID.fn_searchGrid]" , _thisGridOptions );
            UTILE.fn_log2("[GRID.fn_searchGrid] _thisGridOptions_id ==>> " , _thisGridOptions.id );
            UTILE.fn_log2("[GRID.fn_searchGrid] _thisGridOptions_datatype ==>> " , _thisGridOptions.datatype );
            UTILE.fn_log2("[GRID.fn_searchGrid] _thisGridOptions_url ==>> " , _thisGridOptions.url );



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

                UTILE.fn_log2("[GRID.fn_searchGrid] [" + gridId + "] jqXHR.status = " , jqXHR.status );

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

                    // UTILE.fn_log2("[GRID.fn_searchGrid] [" + gridId + "]", "getDataIDs : " + $_vGridId.jqGrid('getDataIDs')); //return Ids

                    if (!UTILE.fn_isBlank(callback)) {
                        if (typeof callback == 'function') {
                            UTILE.fn_log("[GRID.fn_searchGrid] [" + gridId + "][Data Callback]");
                            // callback.call(this, data, jqXHR, textStatus);
                            // callback.call(this, data, textStatus, jqXHR);
                            eval(callback(obj, resultData, textStatus, jqXHR));
                        }

                    }

                    UTILE.fn_log("[GRID.fn_searchGrid] [" + gridId + "] ================End=============");
                }else{

                    UTILE.fn_log("[GRID.fn_searchGrid] [" + gridId + "] ================End=============");
                }
            }, ajaxOptions);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //## public 메소드
    //------------------------------------------------------------------------------------------------------------------
    _mod_content.fn_createGrid = fn_createGrid;
    _mod_content.fn_createGridPageNav = fn_createGridPageNav;
    _mod_content.fn_createTreeGrid = fn_createTreeGrid;
    _mod_content.fn_resizeJqGridWidth = fn_resizeJqGridWidth;
    _mod_content.fn_resizeJqGridColumnWidths = fn_resizeJqGridColumnWidths;
    _mod_content.fn_getCheckedArray = fn_getCheckedArray;
    _mod_content.fn_getCheckedStr = fn_getCheckedStr;
    _mod_content.fn_addFormData = fn_addFormData;

    _mod_content.fn_searchGrid = fn_searchGrid;
    _mod_content.fn_searchGridBasic = fn_searchGridBasic;


    return _mod_content;

}(UNI.GRID || {}, jQuery);