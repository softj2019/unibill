/**
 *  통계에 사용되는 함수
 * @namespace {Object} UNI.STATS
 */
UNI.STATS = (function(_mod_stats, $, undefined){

	/**
	 * @method
	 * @description 통계 화면별 기능 처리
	 * @author 홍길동(2016.10.04)
	 */
	fn_stats_grid = function(_sMenuCode, _sScrinCode, _sGridId, _sFormId) {

		// 통신사 번호별 요금내역 (메뉴ID : M050206)
//		if (_sMenuCode == "M050206") {
			
			STATS.fn_grid_cell_edit(_sScrinCode, _sGridId, _sFormId);
			
			// 그리드 row 병합
/*			
			var sCol = ["key_no","svc_id","gd_id"];
			STATS.fn_grid_row_merger(_sGridId, sCol);
		
			// 그리드 col 병합
			var sTextBold = ["ocost","cost"];  // 금액 bold 처리(아래 1, 2, 3 공통)
			
			// 1. 키번호 계 col 병합
			var sSvcIdColMerger = ["svc_id","gd_id","item_id"];
			var sSvcIdBgColor   = "#fff5f0";
			STATS.fn_grid_col_merger(_sGridId, sSvcIdColMerger, sTextBold);
			
			// 2. 서비스 계 col 병합
			var sGdIdColMerger = ["gd_id","item_id"];
			var sGdIdBgColor   = "#fff5f0";
			STATS.fn_grid_col_merger(_sGridId, sGdIdColMerger, sTextBold);
			
			// 3. 상품 계 col 병합
			var sItemIdColMerger = ["item_id"];
			var sItemIdBgColor   = "#fff5f0";
			STATS.fn_grid_col_merger(_sGridId, sItemIdColMerger, sTextBold);
			
			// 그리드 헤더 타이틀변경						
//			var searchStdrVal = $("#"+_sFormId).find(':radio[name="search_stdr"]:checked').val();
//			var searchStdrText = $("input:radio[name='search_stdr']:checked").next("ralab").text();
			
			var searchStdrVal  = $("#"+_sFormId).find("#search_stdr option:selected").val();
			var searchStdrText = $("#"+_sFormId).find("#search_stdr option:selected").text();

			if (searchStdrText != "") {
				var colModel = $("#"+_sGridId).jqGrid("getGridParam", "colModel"); 
				$("#"+_sGridId).jqGrid("setLabel", colModel[2]['name'], searchStdrText);
			}
*/			
//		}
/*		
		var colModels = $("#"+_sGridId).jqGrid("getGridParam","colModel");
		var rowIds    = $("#" + _sGridId + "").getDataIDs();
		
		for (var i=0; i<colModels.length; i++) {
			if (colModels[i].name == "svc_id" || colModels[i].name == "gd_id" || colModels[i].name == "item_id") {
				
				for (var j=0; j<rowIds.length; j++) {
					var sRowData = $("#"+_sGridId).jqGrid("getRowData", rowIds[j]);
					var sVal = sRowData["svc_id"];
					var sVal2 = sRowData["gd_id"];
					var sVal3 = sRowData["item_id"];
					
					if (sVal.indexOf("계") > 0) {						
						$("#" + _sGridId + "").setCell(rowIds[j], 'svc_id', '', {"background-color":"#fff5f0"}); 
					} else if (sVal2.indexOf("계") > 0) {
						$("#" + _sGridId + "").setCell(rowIds[j], 'gd_id', '', {"background-color":"#fff5f0"});
					} else if (sVal3.indexOf("계") > 0) {
						$("#" + _sGridId + "").setCell(rowIds[j], 'item_id', '', {"background-color":"#fff5f0"});	
					}
				}
			}
		}
		
		var sTextBold = ["ocost","cost"];  // 금액 bold 처리(아래 1, 2, 3 공통)
		
		var sSvcIdColMerger = ["svc_id","gd_id","item_id"];
		STATS.fn_grid_col_merger(_sGridId, sSvcIdColMerger, sTextBold);
		
		var sGdIdColMerger = ["gd_id","item_id"];
		STATS.fn_grid_col_merger(_sGridId, sGdIdColMerger, sTextBold);
		
		var sItemIdColMerger = ["item_id"];
		STATS.fn_grid_col_merger(_sGridId, sItemIdColMerger, sTextBold);
*/
	}

	/**
	 * @method
	 * @description 그리드 셀 편집
	 * @author 홍길동(2016.10.04)
	 */
	fn_grid_cell_edit = function(_sScrinCode, _sGridId, _sFormId) {

		var sRowMergerId   = [];  // row 병합대상 컬럼
		var sColMergerId   = [];  // col 병합대상 컬럼
		var sCellBgColId   = [];  // 셀 배경색상 대상 컬럼ID
		var sCellBgColor   = [];  // 셀 배경색상
		var sTextBoldId    = [];  // 텍스트 bold
		var sSearchObjId   = "";  // 검색기준 객체ID
		var sSearchObjType = "";  // 검색기준 객체타입
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectGridEditInfoList.json',
			data: 'scrin_code=' + _sScrinCode,
			datatype : "json",
			success : function(data) {
				
				if (data.rows.length > 0) {
				
					// 텍스트 bold 처리 및 셀 배경색상 정보 추출
					for (var j=0; j<data.rows.length; j++) {
						var opertType = data.rows[j]["opertType"];
						var colId     = data.rows[j]["colId"];
						var cellColor = data.rows[j]["cellColor"];
						var objType   = data.rows[j]["objType"];
						var findObjId = data.rows[j]["findObjId"];
					
						// 텍스트 bold
						if (opertType == "T") {						
							sTextBoldId.push(colId);
						} else if (opertType == "B") {  // 셀 배경색상
							sCellBgColId.push(colId);
							sCellBgColor.push(cellColor);
						} else if (opertType == "L") {  // 그리드 헤더 라벨명 변경
							sSearchObjType = objType;
							sSearchObjId   = findObjId;							
						}
					}
/*
					// 셀 병합 처리
					for (var i=0; i<data.rows.length; i++) {
						
						var opertType = data.rows[i]["opertType"];
						var opertSeq  = data.rows[i]["opertSeq"];
						var seq       = data.rows[i]["seq"];
						var colId     = data.rows[i]["colId"];
						var rowCnt    = data.rows[i]["rowCnt"];
						
						// row 병합대상 컬럼 추출
						if (opertType == "R") {
							sRowMergerId.push(colId);
						} else if (opertType == "C") {  // col 병합대상 컬럼 추출												
							
							if (seq <= rowCnt) {
								
								sColMergerId.push(colId);
								
								if (seq == rowCnt) {
									
									// 그리드 col 병합 처리
									if (sColMergerId.length > 0) {
										STATS.fn_grid_col_merger(_sGridId, sColMergerId, sTextBoldId);
									}
									
									sColMergerId = [];
								}
							}
							
						}
					}
					
					// 그리드 row 병합 처리
					if (sRowMergerId.length > 0) {
						STATS.fn_grid_row_merger(_sGridId, sRowMergerId);
					}
					
					// 그리드 셀 색상변경
					STATS.fn_grid_cell_bgcolr(_sGridId, sCellBgColId, sCellBgColor);
*/					
					// 검색기준에 따른 그리드 헤더명 변경
					if (sSearchObjType != "" && sSearchObjId) {
						
						var searchStdrVal  = "";
						var searchStdrText = "";
						
						if (sSearchObjType == "radio") {
							searchStdrVal  = $("#"+_sFormId).find(':radio[name="'+sSearchObjId+'"]:checked').val();
							searchStdrText = $("input:radio[name='"+sSearchObjId+"']:checked").next("ralab").text();
						} else if (sSearchObjType == "select") {
							searchStdrVal  = $("#"+_sFormId).find("#"+sSearchObjId+" option:selected").val();
							searchStdrText = $("#"+_sFormId).find("#"+sSearchObjId+" option:selected").text();							
						}

//                        var sSearchText = searchStdrText.substring(0, searchStdrText.indexOf("("));
                        var sSearchText = searchStdrText;     
                        
						for (var k=0; k<data.rows.length; k++) {
							var opertType = data.rows[k]["opertType"];
							var seq       = data.rows[k]["seq"];
							var colNo     = data.rows[k]["colNo"];
							
							if (opertType == "L") {  // 그리드 헤더 라벨명 변경								
								if (sSearchText != "" && searchStdrVal == seq) {
									var colModel = $("#"+_sGridId).jqGrid("getGridParam", "colModel"); 
									$("#"+_sGridId).jqGrid("setLabel", colModel[colNo]['name'], sSearchText);
								}
							}
						}
					}
					
				} // end if
				
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}

		});
	}
	
	/**
	 * @method
	 * @description 그리드 row 병합 
	 * @author 홍길동
	 * @param
	 */	
	fn_grid_row_merger = function(_sGridId, _sCol) {

		var mya = $("#" + _sGridId + "").getDataIDs();
        var length = mya.length;
        
        for (var k=0; k<_sCol.length; k++) {
        
	        for (var i = 0; i < length; i++) {
	            var before = $("#" + _sGridId + "").jqGrid('getRowData', mya[i]);
	
	            var rowSpanTaxCount = 1;
	            for (j = i + 1; j <= length; j++) {
	
	                var end = $("#" + _sGridId + "").jqGrid('getRowData', mya[j]);
	                if (before[_sCol[k]] == end[_sCol[k]]) {
	                    rowSpanTaxCount++;
	                    $("#" + _sGridId + "").setCell(mya[j], _sCol[k], '', {display: 'none'});
	                } else {
	                    rowSpanTaxCount = 1;
	                    break;
	                }
	                $("#" + _sCol[k] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
	            }
	        }
	        
        }
        
        /*
        for (var i = 0; i < length; i++) {
            var before = $("#" + _sGridId + "").jqGrid('getRowData', mya[i]);

            var rowSpanTaxCount = 1;
            for (j = i + 1; j <= length; j++) {

                var end = $("#" + _sGridId + "").jqGrid('getRowData', mya[j]);
                if (before[_sCol] == end[_sCol]) {
                    rowSpanTaxCount++;
                    $("#" + _sGridId + "").setCell(mya[j], _sCol, '', {display: 'none'});
                } else {
                    rowSpanTaxCount = 1;
                    break;
                }
                $("#" + _sCol + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
            }
        }*/
	}
	
	/**
	 * @method
	 * @description 그리드 col 병합 
	 * @author 홍길동
	 * @param
	 */	
	fn_grid_col_merger = function(_sGridId, _sColMerger, _sTextBold) {
	
		var colModels = $("#"+_sGridId).jqGrid("getGridParam","colModel");
		var rowIds    = $("#" + _sGridId + "").getDataIDs();
		
		var sSCol    = "";  // 시작 셀ID
		var sMerCol  = [];  // 병합대상 셀ID
		var sSpanCnt = 0;   // 병합col 갯수
		
		for (var c=0; c<_sColMerger.length; c++) {
			if (c == 0) {
				sSCol = _sColMerger[c];
			} else {
				sMerCol.push(_sColMerger[c]);
			}
		}

		// 병합 갯수		
		sSpanCnt = _sColMerger.length;
		
		// 병합 갯수가 한개 인경우 병합 시작셀ID를...
		if (sSpanCnt == 1) {
			sMerCol.push(sSCol);
		}

//		alert("시작셀ID(sSCol) : " + sSCol + "\n병합대상 셀ID(sMerCol) : " + sSpanCnt);

		for (var i=0; i<colModels.length; i++) {

			if (colModels[i].name == sSCol) {

				for (var j=0; j<rowIds.length; j++) {

					var sRowData = $("#"+_sGridId).jqGrid("getRowData", rowIds[j]);
					var sVal = sRowData[sSCol];
					
					// 그리드 셀값이 계인 경우만 병합
					if (sVal.indexOf("계") > 0) {
						
						// 계 문구 및 계 금액 bold 처리
						$("#" + _sGridId + "").setCell(rowIds[j], sSCol, '', {"font-weight":"bold"});  // 계 bold 처리
//						$("#" + _sGridId + "").setCell(rowIds[j], sSCol, '', {"font-weight":"bold","text-align":"left","padding-left":"100px"});  // 계 bold 처리
						
						for (var m=0; m<_sTextBold.length; m++) {
							$("#" + _sGridId + "").setCell(rowIds[j], _sTextBold[m], '', {"font-weight":"bold"});  // 계금액 bold 처리
						}

						$("#"+sSCol + "" + rowIds[j]).attr("colspan", sSpanCnt);
//						$("#"+_sECol + "" + rowIds[j]).attr("display: 'none'");
//						$("#"+sMerCol[j] + "" + rowIds[j]).attr("display: 'none'");
																		
						for (var k=0; k<sMerCol.length; k++) {
							if (sSpanCnt > 1) {								
								
								$("#" + _sGridId + "").setCell(rowIds[j], sMerCol[k], '', {display: 'none'});
								
							}
//							alert("sSpanCnt : " + sSpanCnt + "\nrowIds[j] : " + rowIds[j] + "\nsMerCol[k] : " + sMerCol[k]);
//							jQuery("#"+rowIds[j]).css("background", _sBgColor);  // 그리 계 row 배경색상
//							$("#" + _sGridId + "").setCell(rowIds[j], sMerCol[k], '', {"background-color":_sBgColor}); 
						}
					}
										
				}
			}
		}
        
	}
	
	/**
	 * @method
	 * @description 그리드 셀 색상 
	 * @author 홍길동
	 * @param
	 */	
	fn_grid_cell_bgcolr = function(_sGridId, _sCellBgColId, _sCellBgColor) {

		var sIdx = 0;
		var colModels = $("#"+_sGridId).jqGrid("getGridParam","colModel");
		var rowIds    = $("#" + _sGridId).getDataIDs();
		var sCellId   = "";
		var noCellId  = [];

		for (var i=0; i<colModels.length; i++) {

			for (var k=0; k<_sCellBgColId.length; k++) {

				if (colModels[i].name == _sCellBgColId[k]) {

					for (var j=0; j<rowIds.length; j++) {

						var sRowData = $("#"+_sGridId).jqGrid("getRowData", rowIds[j]);
						var sVal = sRowData[_sCellBgColId[k]];

						if (sVal.indexOf("계") > 0) {
							$("#" + _sGridId).setCell(rowIds[j], _sCellBgColId[k], '', {"background-color":_sCellBgColor[k]});
							
							if (sCellId != _sCellBgColId[k]) {
								noCellId.push(_sCellBgColId[k]);

								sCellId = _sCellBgColId[k];
							}
							sIdx = rowIds[j];						
						}

					}  // end for

				}  // end if
				
			} // end for									
			
		}  // end for
		
		// 계 이외 DB에서 지정한 그리드 컬럼의 셀 색상 변경
		var sIdArr = "";
		for (var i=0; i<noCellId.length; i++) {
			sIdArr += "|" + noCellId[i];
		}
		sIdArr = sIdArr.substring(1);
		
		var sIdBgArr = [];  // 계 이외 색상 변경대상 그리드 컬럼ID
		var sIdBgCol = [];  // 계 이외 색상 변경대상 그리드 컬럼ID의 색상코드
		for (var i=0; i<_sCellBgColId.length; i++) {
			if (sIdArr.indexOf(_sCellBgColId[i]) == -1) {
				sIdBgArr.push(_sCellBgColId[i]);
				sIdBgCol.push(_sCellBgColor[i]);
			}
		}

		for (var s=0; s<sIdBgArr.length; s++) {

			for (var i=0; i<colModels.length; i++) {								

				for (var j=0; j<rowIds.length; j++) {
					
					for (var k=0; k<_sCellBgColId.length; k++) {

						if (colModels[i].name == _sCellBgColId[k]) {

							var sRowData = $("#"+_sGridId).jqGrid("getRowData", rowIds[j]);
							var sVal = sRowData[_sCellBgColId[k]];

							if (sVal.indexOf("계") > 0) {
								sIdx = rowIds[j];
							}
								
//							$("#" + _sGridId).setCell(sIdx, sIdBgArr[s], '', {"background-color":_sCellBgColor[k]});
							$("#" + _sGridId).setCell(sIdx, sIdBgArr[s], '', {"background-color":sIdBgCol[s]});
						}

					}

				}

			}

		}

	}  // end fn_grid_cell_bgcolr
	
	/**
	* @method
	* @description 검색 버튼 클릭시
	* @author 홍길동(2016.10.04)
	* @param {string} _sFormId : 선택된 form ID
	*/
	fn_main_grid_search = function() {
		
		COMMON.fn_search_mainGrid("frm_main", "gridMain");
		
		var scrin_code = $("#frm_main").find("#scrin_code").val();
		
		STATS.fn_grid_cell_edit(scrin_code, "gridMain", "frm_main");
	}

	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_stats.fn_stats_grid           = fn_stats_grid;
	_mod_stats.fn_grid_cell_edit       = fn_grid_cell_edit;
	_mod_stats.fn_grid_row_merger      = fn_grid_row_merger;
	_mod_stats.fn_grid_col_merger      = fn_grid_col_merger;
    _mod_stats.fn_grid_cell_bgcolr     = fn_grid_cell_bgcolr;
    _mod_stats.fn_main_grid_search     = fn_main_grid_search;
		
	return _mod_stats;

}(UNI.STATS || {}, jQuery));