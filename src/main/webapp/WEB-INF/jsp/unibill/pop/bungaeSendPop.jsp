<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/faqPopup.css" />	
<script type="text/javascript">

	function bungae_send(){
		var billym = $("#frm_sendbungae").find("#send_billym option:selected").val();
		var jurdat = $("#frm_sendbungae").find("#send_jurdat").val();
		
		if(billym == null && jurdat == ""){
			$.unibillDialog.alert('오류', "청구월을 입력하세요." + "</br>" + "전표기표일자를 입력하세요.");
		}else if(billym == null){
			$.unibillDialog.alert('오류', "청구월을 입력하세요.");
		}else if(jurdat == ""){
			$.unibillDialog.alert('오류', "전표기표일자를 입력하세요.");
		}
		
		CUSTOM.fn_genProc('prc_sendbungae','','frm_sendbungae','#send_billym','#send_jurdat', '#send_user_id');
	}
</script>

<%-- bungae 상세정보 --%>
<form id="frm_sendbungae" name="frm_sendbungae" method="post">
<input type="hidden" id="tableName" name="tableName" value="mb_sendbungae" /> 
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />
<input type="hidden" id="qtype" name="qtype" value="" />
<input type="hidden" id="qseq" name="qseq" value="" />
<input type="hidden" id="send_user_id" name="send_user_id" value="${userId}" />
<div class="confirm_cont" style="width:350px;height:200px;"><!-- 팝업창 크기에 맞게 위치변경 -->

        <h4>분개전송</h4>
        <div style="heigth:10px;">&nbsp;</div>
        <table summary="추가정보(선택)" class="tb02" style="padding:0px;">
            <caption>추가정보(선택)</caption>
            <colgroup>
                <col width="91px">
                <col >                
            </colgroup>
            <tbody> 
                <tr>
                    <th scope="row" style="height:">청구월</th>                    
                    <td class="paddingTd" >
						<div>
							<select id="send_billym" name="send_billym" style="width:${sWidth}px;" class="input ${sObjNm}" >
							    <c:set var="ym_today" value="<%=new java.util.Date()%>" />
						        <fmt:formatDate value="${ym_today}" pattern="yyyyMM" var="start"/>
						        <c:set var="startyy" value="${fn:substring(start,0,4)}" /> 
						        <c:set var="startmm" value="${fn:substring(start,4,6)}" /> 
						        <fmt:parseNumber value="${startmm}" var="startmm_num"/>
						        
						        <c:forEach begin="0" end="5" var="idx3" step="1">
						        	<c:forEach begin="1" end="12" var="idx4" step="1">
						        		<c:choose>
								        	<c:when test="${idx4 eq 1}"><c:set var="mm" value="12" /></c:when>
								        	<c:when test="${idx4 eq 2}"><c:set var="mm" value="11" /></c:when>
								        	<c:when test="${idx4 eq 3}"><c:set var="mm" value="10" /></c:when>
								        	<c:when test="${idx4 eq 4}"><c:set var="mm" value="09" /></c:when>
								        	<c:when test="${idx4 eq 5}"><c:set var="mm" value="08" /></c:when>
								        	<c:when test="${idx4 eq 6}"><c:set var="mm" value="07" /></c:when>
								        	<c:when test="${idx4 eq 7}"><c:set var="mm" value="06" /></c:when>
								        	<c:when test="${idx4 eq 8}"><c:set var="mm" value="05" /></c:when>
								        	<c:when test="${idx4 eq 9}"><c:set var="mm" value="04" /></c:when>
								        	<c:when test="${idx4 eq 10}"><c:set var="mm" value="03" /></c:when>
								        	<c:when test="${idx4 eq 11}"><c:set var="mm" value="02" /></c:when>
								        	<c:when test="${idx4 eq 12}"><c:set var="mm" value="01" /></c:when>
								        </c:choose>
								        
								        <c:set var="monthList" value="${startyy - idx3}${mm}" />
								        <c:choose>
								        	<c:when test="${idx3 eq 0 && 13 - idx4 <= startmm_num }">
								        		<option value="<c:out value="${startyy - idx3}${mm}" />" <c:if test="${monthList eq start }">selected</c:if>><c:out value="${startyy - idx3}년&nbsp;${mm}월" escapeXml="false"/></option>
								        	</c:when>
								        	<c:when test="${idx3 ne 0}">
								        		<option value="<c:out value="${startyy - idx3}${mm}" />" <c:if test="${monthList eq start }">selected</c:if>><c:out value="${startyy - idx3}년&nbsp;${mm}월" escapeXml="false"/></option>
								        	</c:when>
								        </c:choose> 
								    </c:forEach>
						        </c:forEach>
						   </select>
							
						</div>
					</td>
                </tr>
                <tr>
                    <th scope="row">전표기표일</th>                    
                    <td class="paddingTd" >
						<input type="text" id="send_jurdat" name="send_jurdat" class="datepicker input ES ON_전표기표일" maxlength="10" style="width:110px"/>
					</td>
                </tr>
            </tbody>
        </table>
        
        <div class="btn_pop_wrap">
        	<button type="button" onclick="bungae_send()" class="btn_pop">확인</button>
        	<button type="button" class="btn_pop" onclick="document.getElementById('bungaesend_pop').style.display = 'none'">닫기</button>
        </div>
    </div>
</form>