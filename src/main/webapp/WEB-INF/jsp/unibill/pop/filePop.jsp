<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/filePopup.css" />
<script type="text/javascript">
	var isRun = false;
	var returnID = null;
	
	var xhr = null;
	
	
	$( document ).ready(function() {
		// 그리드 초기화
		COMMON.fn_clear_grid_data("fileUploadgrid");
		
		// 그리드 구성
		$("#fileUploadgrid").jqGrid({
			  url : CONTEXT_ROOT + '/content/selectFileList.json'
			, datatype : "local"			
			, colNames : ["파일번호", "파일명"]		
		    , colModel : [
					       {name:'file_sn',  index:'file_sn',  jsonmap:'fileSn',    align:'center', width:20, cellattr:jsFormatterCell}
					       ,{name:'nm',  index:'nm',  jsonmap:'nm',    align:'center', cellattr:jsFormatterCell}
						 ]			    
//			, sortname  : 'lineId'
//		    , sortorder : "DESC"
			, width  : 1015
			, autowidth:true
			, height : 100
	        , rowNum : 10	        
		 	, rowList: [10,100,500]
		    , viewrecords: true
		    , jsonReader : { 
		    	repeatitems : false
		    }
		    , pager : "#fileUploadgridPager"
		    , gridview : true
		    , rownumbers : true
		    , loadtext : "검색 중입니다."
			, emptyrecords : "데이터가 없습니다."
			, recordtext : "총 {2} 건 데이터 ({0}-{1})"
			, shrinkToFit : true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, multiselect : true
			, multiboxonly : true	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제			
			, ondblClickRow: function(rowId) {
			}
			, loadComplete : function(data) { 
				fn_scrin_block_ui("UB");
	        }		
		}).navGrid("#fileUploadgridPager",{edit:false,add:false,del:false,search:false,refresh:false});
		 
		$(window).resize(function() {
			var w = $("#file_pop .confirm_cont").width();
			var resizeW = w - 135;
			$("#fileUploadgrid").setGridWidth(resizeW);
		
		}); 
		
		
		// 그리드 초기화
		COMMON.fn_clear_grid_data("fileSearchgrid");
		
		// 그리드 구성
		$("#fileSearchgrid").jqGrid({
			  url : CONTEXT_ROOT + '/content/selectFileList.json'
			, datatype : "local"			
			, colNames : ["파일번호", "파일명"]		
		    , colModel : [
					       {name:'file_sn',  index:'file_sn',  jsonmap:'fileSn',    align:'center', width:20, cellattr:jsFormatterCell}
					       ,{name:'nm',  index:'nm',  jsonmap:'nm',    align:'center', cellattr:jsFormatterCell}
						 ]			    
//			, sortname  : 'lineId'
//		    , sortorder : "DESC"
			, width  : 1015
			, autowidth:true
			, height : 100
	        , rowNum : 10	        
		 	, rowList: [10,100,500]
		    , viewrecords: true
		    , jsonReader : { 
		    	repeatitems : false
		    }
		    , pager : "#fileSearchgridPager"
		    , gridview : true
		    , rownumbers : true
		    , loadtext : "검색 중입니다."
			, emptyrecords : "데이터가 없습니다."
			, recordtext : "총 {2} 건 데이터 ({0}-{1})"
			, shrinkToFit : true        // true 인경우 그리드 width에 맞춰 자동으로 맞춰짐
			, multiselect : true
			, multiboxonly : true	// 	multiselect : true 일 경우 체크박스외 클릭시 선택해제			
			, ondblClickRow: function(rowId) {
			}
			, loadComplete : function(data) { 
				fn_scrin_block_ui("UB");
	        }		
		}).navGrid("#fileSearchgridPager",{edit:false,add:false,del:false,search:false,refresh:false});
		 
		$(window).resize(function() {
			var w = $("#file_pop .confirm_cont").width();
			var resizeW = w - 135;
			$("#fileSearchgrid").setGridWidth(resizeW);
		
		});
		
	});
	
	
	
	
	
</script>
<form id="frm_fileDetail" name="frm_fileDetail" method="post" enctype="multipart/form-data">
<input type="hidden" id="buttonFlag" value="A" />
<input type="hidden" id="sfileSeq" value="" />
<div class="dim" onclick=""></div>
    <div class="confirm_cont" id="id_detail_width" style="margin-top:-350px;"><!-- 팝업창 크기에 맞게 위치변경 -->
    <h4 class="search" id="id_tabNm" class="file" >삭제 파일 관리</h4>
    <h4 class="add" id="id_tabNm" class="file" >추가 파일 관리</h4>
    <div class="type01"></div>
         <table summary="등록/상세정보" class="tb02">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="120">
                <col >
                <col width="120">
                <col>
            </colgroup>
            <tbody>
            	
            	
               <tr class="search">
               		<td colspan="4">
               			<button type="button" onclick="UPFILE.fn_fileDel()" class="btn_pop_m" style="float: right;">파일삭제</button>
                		<!-- <button type="button" onclick="UPFILE.fn_fileSearch()" class="btn_pop_m" style="float: right; margin-right: 3px;">조회</button> -->
                	</td>
               </tr>    
                   
                    
               
               
               <tr class="add">
                   <th scope="row">경로</th>
                   <td colspan="3" style="padding-left:6px;">
                		<input type="text" id="upfilenm" title="" style="width:55%;" readonly="readonly"/>
                		
                   </td>
               </tr>
               <tr class="add">
               		<td colspan="4">
               			<button type="button" onclick="UPFILE.fn_fileRemove()" class="btn_fminus" style="float: right;"></button>
                		<button type="button" onclick="UPFILE.fn_fileAdd()" class="btn_fadd" style="float: right; margin-right: 3px;"></button>
                		
                	</td>
               </tr> 
               
               <tr class="sadd">
               	   <th scope="row">경로</th>
                   <td colspan="3" style="padding-left:6px;">
                		<input type="text" id="upsfilenm" title="" style="width:55%;" readonly="readonly"/>
                		<button type="button" onclick="UPFILE.fn_sfileAdd()" class="btn_pop_file">파일찾기...</button>
                   </td>
               </tr>
              
               <tr class="dsadd">
                   <th scope="row" class="search">첨부 파일</th>
                   <th scope="row" class="add">업로드파일</th>
                   <td colspan="3" id="custList" height="100px">
                		<div style="padding-left:5px;">
							<table id="fileUploadgrid" class="mailgrid"></table>
						</div>
						<div id="fileUploadgridPager" style="padding: 0px; display:none;"></div>
						
						<div style="padding-left:5px;">
							<table id="fileSearchgrid" class="mailgrid"></table>
						</div>
						<div id="fileSearchgridPager" style="padding: 0px; display:none;"></div>
				   </td>
                   
                   
                    
               </tr>
              
            </tbody>
        </table>
      
        
        
                
        <div class="btn_pop_wrap">
           	 <span id="id_detail_button"></span>           	
           	 <%-- //상세화면 하단 버튼 --%>
        	<button id="fileExecute" type="button" class="btn_pop" onclick="UPFILE.fn_makeUploadFile();">확인</button>
        	<button id="sfileExecute" type="button" class="btn_pop" onclick="UPFILE.fn_makeUploadsFile();">확인</button>
        	<button type="button" class="btn_pop" onclick="document.getElementById('file_pop').style.display = 'none'">닫기</button>
        </div>
     </div>
 </form>


