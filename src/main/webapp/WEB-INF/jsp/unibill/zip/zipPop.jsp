<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/zip_popup.css" />
<script type="text/javascript">

	$( document ).ready(function() {
		
	
	 
	// 그리드 초기화
	COMMON.fn_clear_grid_data("zipGrid");
	
	// 그리드 구성
	$("#zipGrid").jqGrid({
		  url : CONTEXT_ROOT + '/zip/selectZipList.json'
		, datatype : "local"			
		, colNames : ["우편번호","주소"]		
	    , colModel : [
				       {name:'zip_no',  index:'zip_no',  jsonmap:'zipNo',    align:'center',   cellattr:jsFormatterCell}
					   ,{name:'addr',   index:'addr',   jsonmap:'addr',    align:'left',   width:300,   cellattr:jsFormatterCell}
					 ]			    
//		, sortname  : 'lineId'
//	    , sortorder : "DESC"
		, width  : 755
		, height : 300
        , rowNum : 10	        
	 	, rowList: [10,100,500]
	    , viewrecords: true
	    , jsonReader : { 
	    	repeatitems : false
	    }
	    , pager : "#gridZipPager"
	    , gridview : true
	    , rownumbers : true
	    , loadtext : "검색 중입니다."
		, emptyrecords : "검색된 데이터가 없습니다."
		, recordtext : "총 {2} 건 데이터 ({0}-{1})"
		, shrinkToFit : true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
		, multiselect : true
		, multiboxonly : true	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제			
		, ondblClickRow: function(rowId) {
			var sRowData = $("#zipGrid").getRowData(rowId);				
			var zipNo = sRowData["zip_no"];
			var addr = sRowData["addr"];
			var addrs = addr.split("<br>");
			
			$(".confirm_cont").find("#zip_no").val(zipNo);
			
			$(".confirm_cont").find("#addr1").val(addrs[0].replace("(도로명) ", ""));
			//$(".confirm_cont").find("#addr2").val(addrs[1]);
			
			document.getElementById('ly_zip_pop').style.display = 'none' 
		}
		, loadComplete : function(data) {  
        }		
	}).navGrid("#gridZipPager",{edit:false,add:false,del:false,search:false,refresh:false});
	 
		
	
	
});
	
	
	
	function search_zip(){
		// 그리드 초기화
		COMMON.fn_clear_grid_data("zipGrid");
		
		COMMON.fn_search_mainGrid("frm_zip", "zipGrid");
		
		$("#gridZipPager"). find(".selectBox-dropdown").css("width",50);
		$("#gridZipPager"). find(".selectBox-label").css("width",60);
		$("#gridZipPager"). find(".selectBox-arrow").css({"top" : "-5px"});
		
		COMMON.fn_scrin_block_ui("UB");	
		$("#id_loader").hide();
	}

	
</script>


<form id="frm_zip" name="frm_zip" method="post">
<input type="hidden" id="tableName" name="tableName" value="cm_cfgzipcd" />  <%-- 고객ID, 고객명 자동완성기능 위함 --%>
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />
<div class="zippop">
<div id="ly_zip_pop" class="ui_confirm" style="display:none;">
    <div class="confirm_cont" style="width:800px;"><!-- 팝업창 크기에 맞게 위치변경 -->
         <h4>우편번호 검색</h4>
      <!--   <h4><lable id="tellId_title"></lable></h4>  -->
        <br>
        <!-- <div style="width:480px;height:250px;overflow-y: scroll;"> -->
        
        
        	<h5><lable id="tellId__title2"></lable> 주소 검색 </h5>        
			<table class="tb02" style="padding:0px;">
			    <colgroup>
			       <col width="150" ></col>
			       <col width="250" ></col>
			       <col></col>
			       
			    </colgroup>
			    
			    <tr>
					<th scope="row" >동명/도로명/건물명</th>
					<td>
						<%-- 현재회선 --%>
						<input maxlength="20" type="text" id="s_zip" name="s_zip" value="" style="width:200px;" class="input ES ON_동명/도로명/건물명 " onkeypress="if(event.keyCode==13){search_zip();}"/>
					</td>
					<td>
						<button type="button" onclick="search_zip();" class="btn_m" style="float: right; margin-right: 30px;">검색</button>
					</td>
	            </tr>
	        </table>
	                
        <!-- </div> -->
		<div style="padding-left:20px;">
			<table id='zipGrid' ></table>
		</div>
		<div id="gridZipPager" style="padding: 0px;"></div>
		
        <div class="btn_pop_wrap"><button type="button" onclick="document.getElementById('ly_zip_pop').style.display = 'none'" class="btn_pop">닫기</button></div>
    </div>
</div>
</div>
</form>


			    