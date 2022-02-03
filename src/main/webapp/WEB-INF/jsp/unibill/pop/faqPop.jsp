<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/faqPopup.css" />	
<script type="text/javascript">

	$( document ).ready(function() {
		
	
		
	
	 
	// 그리드 초기화
	COMMON.fn_clear_grid_data("faqGrid");
	
	// 그리드 구성
	$("#faqGrid").jqGrid({
		  url : CONTEXT_ROOT + '/main/selectAnserList.json'
		, datatype : "local"			
		, colNames : ["ubseq","답변번호","답변내용"]		
	    , colModel : [
				       {name:'ubseq',    index:'ubseq',    	jsonmap:'ubseq',     align:'center',   key:true, hidden:true}   
					   ,{name:'aseq',   index:'aseq',   jsonmap:'aseq',    align:'center', width:90}
					   ,{name:'msg',  index:'msg',  jsonmap:'msg',    align:'left', width:500}
					 ]			    
		, width  : 593
		, height : 150
        , rowNum : 10	        
	 	, rowList: [10,50,100]
	    , viewrecords: false
	    , jsonReader : { 
	    	repeatitems : false
	    }
	    , pager : "#gridFaqPager"
	    , gridview : true
	    , rownumbers : false
	    , loadtext : "검색 중입니다."
		, emptyrecords : "검색된 데이터가 없습니다."
		, recordtext : "총 {2} 건 데이터 ({0}-{1})"
		, shrinkToFit : false        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
		, multiselect : false
		, multiboxonly : false	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제
		, onSelectRow: function(rowId) {
			var sRowData = $("#faqGrid").getRowData(rowId);	
			$("#ly_main_faq_pop").find("#main_faq_msg").html();
			$("#ly_main_faq_pop").find("#main_faq_msg").html(sRowData["msg"]);
			
		}
		, loadComplete : function(data) {
			fn_scrin_block_ui("UB");
        }		
	}).navGrid("#gridFaqPager",{edit:false,add:false,del:false,search:false,refresh:false});
	 
		
});
	
	
	
	function search_faq(){
		// 그리드 초기화
		COMMON.fn_clear_grid_data("faqGrid");
		
		$("#faqG").css("min-height", "0");
		
		COMMON.fn_search_mainGrid("frm_faq", "faqGrid");
	}

	

	
</script>

<%-- FAQ 상세정보 --%>
<form id="frm_faq" name="frm_faq" method="post">
<input type="hidden" id="tableName" name="tableName" value="mb_cfggd" />  <%-- 고객ID, 고객명 자동완성기능 위함 --%>
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />

<input type="hidden" id="qtype" name="qtype" value="" />
<input type="hidden" id="qseq" name="qseq" value="" />
<div id="faq_div" >
<div id="ly_main_faq_pop" class="ui_confirm" style="display:none;">
<div class="confirm_cont" style="width:600px;height:710px;"><!-- 팝업창 크기에 맞게 위치변경 -->
        <h4>FAQ</h4>
        <button type="button" class="close" onclick="document.getElementById('ly_main_faq_pop').style.display = 'none'" title="닫기">Close</button>
        
        <div style="heigth:10px;">&nbsp;</div>
        <table summary="추가정보(선택)" class="tb02" style="padding:0px;">
            <caption>추가정보(선택)</caption>
            <colgroup>
                <col width="91px">
                <col >                
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row">질문</th>                    
                    <td class="paddingTd" ><label id="main_faq_question"></label></td>
                </tr>
                <tr>
                    <th scope="row">등록일자</th>                    
                    <td class="paddingTd" ><label id="main_faq_regTm"></label></td>
                </tr>
                <tr>
                    <!-- <th scope="row">답변리스트</th> -->                    
                    <td id="faqG"  colspan="2">
                    	<table id='faqGrid' ></table>
						<div id="gridFaqPager" ></div>
                    </td>
                </tr>
                <tr>
                    <th scope="row">답변내용</th>                    
                    <td>
                    	<div class="ansewer">
                    		<label id="main_faq_msg"></label>
                    	</div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
       
  </div>
  </div>
</form>


			    