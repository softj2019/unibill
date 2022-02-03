<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="/css/ebill_1.css?after" />

<style type="text/css">    

 	
</style>

<script type="text/javascript">
$(document).ready(function(){
	//푸터 위치 조정
	$(".cbp-spmenu-push").height("2000px");
	//이밴트 초기화
	//$("#searchCustId").keypress(function(event) {    if ( event.which == 13 ) {    CUSTOM.fn_custBillSearch();    }    });
	
	
		

		

})



	


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="${sClass}" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 그룹코드관리</h2><!--북마크페이지 class="on"추가-->--%>
<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> SQL 조회</h2><!--북마크페이지 class="on"추가-->

<form id="searchFrm" name="searchFrm" method="post" action="">
<input type="hidden" id="scrin_code" name="scrin_code" value="<c:out value=""/>" />
<input type="hidden" id="tableName" name="tableName" value="" />
<input type="hidden" id="dept_main" name="formid" value="" />
<input type="hidden" id="fileName" name="fileName" value="" />
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />
	<fieldset>
		<!--폼양식-->
		<legend>Search</legend>
		<div class="srh_wrap">
			<div id="div_search_connType">
				<label>연결정보<span class="asterisk"></span></label>  
				<select id="connType" name="connType" style="width:100px;padding-left: 5px;padding-right: 0px;" class="input ON_연동타입 ES_ select"  onchange="">
				   <option value="I" selected="selected">내부(I)</option>             			   
				   <c:forEach var="list" items="${conList}" varStatus="status">
				   		<option value='<fmt:formatNumber value="${list.conSn}"></fmt:formatNumber>'>${list.name}(${list.conSn})</option> 
				   </c:forEach>
				</select>	
			</div>
			
			<span class="btn_wrap">
				<button type="button" onclick="javascript:CUSTOM.fn_querySearch();" class="btn_srh">조회</button>
				<button type="button" onclick="javascript:CUSTOM.fn_queryExcel();" class="btn_srh">엑셀</button>
            	<button type="button" onclick="javascript:CUSTOM.fn_queryClear();" class="btn_srh">클리어</button>
            	<button type="button" onclick="javascript:CUSTOM.fn_queryProcedure();" class="btn_srh">프로시저생성</button>
            </span>
			<div id="sqldiv">
				<label>SQL<span class="asterisk"></span></label>
				<textarea name="sqlquery" id="sqlquery" cols="190"></textarea>
				
			</div>
			
            
		</div>
	</fieldset>
	
	<!-- Search result -->
    <div class="ui_both">
        <div class="fl">
    		<h4>조회결과 <span>(<span id="id_totalRecords"></span>건)</span></h4>
        </div>
            
	    <div class="fr">
	    </div>
	    <div style="height:40px;">
	    </div>
	    <table id="gridMain"></table>
	    <div id="gridMainPager"></div>
	    <div id="error"></div>
    </div>  
</form>




