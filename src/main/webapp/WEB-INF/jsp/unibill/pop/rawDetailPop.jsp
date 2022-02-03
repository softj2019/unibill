<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$( document ).ready(function() {
		if($("#raw_pop .confirm_cont").height() < 700){
			window.resizeTo($("#raw_pop .confirm_cont").width(), $("#raw_pop .confirm_cont").height() + 85);	
		}
		
	});
	
	
</script>

<link rel="stylesheet" type="text/css" href="../../css/reset.css" />
<link rel="stylesheet" type="text/css" href="../../css/popup3.css" />

<script type="text/javascript" src="<c:url value='../../js/namespace.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.unibillDialog.js'/>"></script>

<form id="frm_rawDetail" name="frm_rawDetail" method="post" enctype="multipart/form-data">
<input type="hidden" id="buttonFlag" value="A" />
<div id="raw_pop" class="ui_confirm">
<div class="dim" onclick="document.getElementById('raw_pop').style.display = 'block'"></div>
    <div class="confirm_cont" id="id_detail_width" style="left: 0px; top: 0px; right: 0px; margin-top: 0px; margin-bottom: 0px; position: absolute;" ><!-- 팝업창 크기에 맞게 위치변경 -->
    <h4 id="id_tabNm" class="cdr_subtitle" >매입 파일 상세정보</h4>
    <div class="type01"></div>
         <table summary="등록/상세정보" class="tb02">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="300">
                <col>
                <col width="300">
                <col>
                <col width="300">
                <col>
            </colgroup>
            <tbody>
                <c:set var="curRowCnt" value="0" />
            	<fmt:formatNumber var="lastRowCnt" value="${dataList.size()/3}" maxFractionDigits="0"/>
            	<c:set var="lastThCnt" value="${dataList.size()%3}" />
            	<c:set var="tdRCnt" value="1" />
                <c:forEach var="dataList" items="${dataList}">
                	<c:if test="${tdRCnt == '1'}">
                		<tr class="del">
                	</c:if>  
                	<c:choose>
                		<c:when test="${curRowCnt ==  lastRowCnt}">
                			<c:if test="${lastThCnt == '1'}">
		                		<th scope="row" class="cdr_th" >${dataList.colNm}</th>
	                    		<td id="jobmsg" class="cdr_td" colspan="5">${dataList.colVal}</td>
		                	</c:if> 
		                	<c:if test="${lastThCnt == '2'}">
		                		<th scope="row" class="cdr_th" >${dataList.colNm}</th>
	                    		<td id="jobmsg" class="cdr_td" colspan="3">${rawCdrList.colVal}</td>
		                	</c:if> 
		                	<c:if test="${lastThCnt == '3'}">
		                		<th scope="row" class="cdr_th" >${dataList.colNm}</th>
	                    		<td id="jobmsg" class="cdr_td" >${dataList.colVal}</td>
		                	</c:if>  
                		</c:when>
                		<c:otherwise>
                			<th scope="row" class="cdr_th" >${dataList.colNm}</th>
	                    	<td id="jobmsg" class="cdr_td" >${dataList.colVal}</td>	
                		</c:otherwise>
                	</c:choose>
					<c:if test="${tdRCnt == '3'}">
                		</tr>
                	</c:if>
                	<c:set var="tdRCnt" value="${tdRCnt + 1}" />
                	<c:if test="${tdRCnt == '4'}">
                		<c:set var="tdRCnt" value="1" />
                		<c:set var="curRowCnt" value="${curRowCnt + 1}" />
                	</c:if>
                </c:forEach>
                
                
                
                
            </tbody>
        </table>
        
     </div>
     
     
 </div>
 </form>

