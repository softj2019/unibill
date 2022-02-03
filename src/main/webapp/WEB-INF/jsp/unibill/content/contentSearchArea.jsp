<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="sWidth" value="${list.objwidth}" />

<c:set var="sMustYn" value="ES_${list.mustyn}" />   <%-- 검색필수여부 --%>
<c:set var="sName" value="ON_${list.name}" />       <%-- 객체명 --%>
<c:set var="sObjNm" value="${sName} ${sMustYn}" />



<c:if test = "${list.objwidth == ''}">
	<c:set var="sWidth" value="" />
</c:if>

<%-- text --%>
<c:if test = "${list.objtype == 'text'}">
	<input type="text" id="${list.colid}" name="${list.colid}" maxlength="${list.maxlen}" style="width:${sWidth}px;" class="input ${sObjNm}" onclick="COMMON.fn_auto_complete('frm_main','${list.autogencol}','${list.colid}','');" onkeydown="if (event.keyCode == 13) {COMMON.fn_search_mainGrid('frm_main', 'gridMain');};" />
</c:if>

<%-- password --%>
<c:if test = "${list.objtype == 'password'}">
	<input type="password" id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}" />	                       			
</c:if>

<%-- 콤보 --%>
<c:if test = "${list.objtype == 'select'}"> 	                       														
    <c:set var="dynData" value="${fn:split(list.dynData,'@')}" />   
	<select id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm} select" onchange="COMMON.fn_selectbox_chanage('frm_main','${list.colid}','${scrin_code}','${list.extFun}');">
	   <option value="">선택</option>             			   
	   <c:forEach var="selectData" items="${dynData}">			                        			   
	   		<c:set var="dataVal" value="${fn:split(selectData,'|')}" />
	   		<option value="${dataVal[0]}">${dataVal[1]}</option> 
	   </c:forEach>
	</select>	                       		
</c:if>

<%-- 체크박스 --%>
<c:if test = "${list.objtype == 'checkbox'}">
    <c:set var="dynData" value="${fn:split(list.dynData,'@')}" />        			    
    <c:forEach var="selectData" items="${dynData}" varStatus="status">
    	<c:set var="dataVal" value="${fn:split(selectData,'|')}" />
		<input type="checkbox" id="${list.colid}" name="${list.colid}" value="${dataVal[0]}" class="checkbox" onclick="COMMON.fn_isCheckedById('${list.formname}','${list.colid}')" />${dataVal[1]}
		<c:if test="${status.count == 1}">
			<input type="hidden" id="mult_check_${list.colid}" name="mult_check_${list.colid}" value="" />
		</c:if>
	</c:forEach>
</c:if>

<%-- 라디오버튼 --%>
<c:if test = "${list.objtype == 'radio'}">
	<c:set var="dynData" value="${fn:split(list.dynData,'@')}" />                       			    
    <c:set var="rcnt" value="1" />
    <c:forEach var="selectData" items="${dynData}">
    	<c:set var="dataVal" value="${fn:split(selectData,'|')}" />
		<c:choose>
			<c:when test="${rcnt == '1'}">
				<input type="radio" id="${list.colid}" name="${list.colid}" value="${dataVal[0]}" class="input ${sObjNm} radio" onclick="COMMON.fn_radio_onclick('frm_main','${list.colid}','${scrin_code}','${list.extFun}');" checked="checked"/><ralab>${dataVal[1]}</ralab>
			</c:when>
			<c:otherwise>
				<input type="radio" id="${list.colid}" name="${list.colid}" value="${dataVal[0]}" class="input ${sObjNm} radio" onclick="COMMON.fn_radio_onclick('frm_main','${list.colid}','${scrin_code}','${list.extFun}');" /><ralab>${dataVal[1]}</ralab>		
			</c:otherwise>
		</c:choose>
		<c:set var="rcnt" value="${rcnt*1 + 1}" />
	</c:forEach>	                       		    
</c:if>

<%-- 일선택 날짜 --%>
<c:if test = "${list.objtype == 'date'}">
	<c:choose>
		<c:when test="${list.dayfindyn == 'Y'}"> <%-- 일자(날짜) from ~ to 검색 일 경우 --%>
            <input maxlength="10" type="text" id="from_${list.colid}" name="from_${list.colid}" style="width:110px" class="input ${sObjNm} datepicker" />
            ~
            <input maxlength="10" type="text" id="to_${list.colid}" name="to_${list.colid}" style="width:110px" class="input ${sObjNm} datepicker" />
	    </c:when>
	    <c:otherwise>
            <input maxlength="10" type="text" id="${list.colid}_datepicker" name="${list.colid}_datepicker" style="width:110px" class="input ${sObjNm} datepicker" />	  	
	    </c:otherwise>
	</c:choose>
</c:if>

<%-- 월선택 날짜 --%>
<c:if test = "${list.objtype == 'month_date'}">
	<input maxlength="10" type="text" id="${list.colid}_datepicker" name="${list.colid}_datepicker" style="width:70px" class="input ${sObjNm} monthPicker" />
</c:if>

<%-- textarea --%>
<c:if test = "${list.objtype == 'textarea2'}">
	<input type="text" id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}"  };" />
	<%--
	<textarea id="${list.colid}" name="${list.colid}" rows="4" style="width:${sWidth}px;" class="input ${sObjNm}" class="textarea" ></textarea>  
	--%>
</c:if>



<%-- textarea --%>
<c:if test = "${list.objtype == 'textarea'}">
	<input type="text" id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}"  };" />
	<!--
	<textarea id="${list.colid}" name="${list.colid}" rows="4" style="width:${sWidth}px;" class="input ${sObjNm}" class="textarea" ></textarea>  
	-->
</c:if>



<%-- 팝업 검색 버튼 --%>
<c:if test = "${list.objtype == 'button_p'}">
	<button type="button" onclick="" class="btn_pop" title="검색"></button>
</c:if>

<%-- 상세보기 팝업 버튼 --%>
<c:if test = "${list.objtype == 'button_i'}">
	<button type="button" onclick="" class="btn_detailPop" title="상세보기"></button>	
</c:if>

<%-- 지우개 버튼 --%>
<c:if test = "${list.objtype == 'button_e'}">
	<button type="button" onclick="COMMON.fn_eraser('${list.formname}','${list.objid}','${list.pobjid}');" class="btn_eraser" title="지우기"></button>	
</c:if>

<%-- 물결(~) --%>
<c:if test = "${list.objtype == 'wave'}">
	~
</c:if>

<%-- 하이픈(-) --%>
<c:if test = "${list.objtype == 'hyphen'}">
	-
</c:if>

<%-- 좌괄로 ( --%>
<c:if test = "${list.objtype == 'bracket_l'}">
	(
</c:if>

<%-- 우괄로 ) --%>
<c:if test = "${list.objtype == 'bracket_r'}">
	)
</c:if>

<%-- 슬래쉬 (/) ) --%>
<c:if test = "${list.objtype == 'slash'}">
	/
</c:if>

<%-- 년도 콤보 --%>
<c:if test = "${list.objtype == 'select_year'}">       
	<select id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}">
	    <option value="">선택</option>	 
        <c:set var="y_today" value="<%=new java.util.Date()%>" />
        <fmt:formatDate value="${y_today}" pattern="yyyy" var="start"/> 
        <c:forEach begin="0" end="15" var="idx2" step="1">
        	<option value="<c:out value="${(start + 1) - idx2}" />"><c:out value="${(start + 1) - idx2}" />년</option>
        </c:forEach>	
   </select>
</c:if>

<%-- 월 콤보 --%>
<c:if test = "${list.objtype == 'select_month'}">       
	<select id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}">
	    <option value="">선택</option>
	    <c:set var="m_today" value="<%=new java.util.Date()%>" />
	    <fmt:formatDate value="${m_today}" pattern="M" var="month"/> 
	    <c:forEach var="i" begin="1" end="12" step="1" >	      
	    	<option value="<fmt:formatNumber value="${i}" minIntegerDigits="2"/>"> ${i}월</option>
	    </c:forEach>
    </select>  
</c:if>

<%-- 년,월 콤보 --%>
<c:if test = "${list.objtype == 'select_ym'}">       
	<select id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}" >
	    <c:set var="ym_today" value="<%=new java.util.Date()%>" />
        <fmt:formatDate value="${ym_today}" pattern="yyyyMM" var="start"/>
        <c:set var="startyy" value="${fn:substring(start,0,4)}" /> 
        <c:set var="startmm" value="${fn:substring(start,4,6)}" /> 
        <fmt:parseNumber value="${startmm}" var="startmm_num"/>
        
        <c:forEach begin="0" end="10" var="idx3" step="1">
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
</c:if>

<%-- 년,월 콤보 기간 --%>
<c:if test = "${list.objtype == 'select_ftym'}">       
	<select id="start_${list.colid}" name="start_${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}" >
	    <c:set var="start_ym_today" value="<%=new java.util.Date()%>" />
        <fmt:formatDate value="${start_ym_today}" pattern="yyyyMM" var="start_start"/>
        <c:set var="start_startyy" value="${fn:substring(start_start,0,4)}" /> 
        <c:set var="start_startmm" value="${fn:substring(start_start,4,6)}" /> 
        <fmt:parseNumber value="${start_startmm}" var="start_startmm_num"/>
        
        <c:forEach begin="0" end="10" var="idx3" step="1">
        	<c:forEach begin="1" end="12" var="idx4" step="1">
        		<c:choose>
		        	<c:when test="${idx4 eq 1}"><c:set var="start_mm" value="12" /></c:when>
		        	<c:when test="${idx4 eq 2}"><c:set var="start_mm" value="11" /></c:when>
		        	<c:when test="${idx4 eq 3}"><c:set var="start_mm" value="10" /></c:when>
		        	<c:when test="${idx4 eq 4}"><c:set var="start_mm" value="09" /></c:when>
		        	<c:when test="${idx4 eq 5}"><c:set var="start_mm" value="08" /></c:when>
		        	<c:when test="${idx4 eq 6}"><c:set var="start_mm" value="07" /></c:when>
		        	<c:when test="${idx4 eq 7}"><c:set var="start_mm" value="06" /></c:when>
		        	<c:when test="${idx4 eq 8}"><c:set var="start_mm" value="05" /></c:when>
		        	<c:when test="${idx4 eq 9}"><c:set var="start_mm" value="04" /></c:when>
		        	<c:when test="${idx4 eq 10}"><c:set var="start_mm" value="03" /></c:when>
		        	<c:when test="${idx4 eq 11}"><c:set var="start_mm" value="02" /></c:when>
		        	<c:when test="${idx4 eq 12}"><c:set var="start_mm" value="01" /></c:when>
		        </c:choose>
		        
		        <c:set var="monthList" value="${start_startyy - idx3}${start_mm}" />
		        <c:choose>
		        	<c:when test="${idx3 eq 0 && 13 - idx4 <= start_startmm_num }">
		        		<option value="<c:out value="${start_startyy - idx3}${start_mm}" />" <c:if test="${monthList eq start_start }">selected</c:if>><c:out value="${start_startyy - idx3}년&nbsp;${start_mm}월" escapeXml="false"/></option>
		        	</c:when>
		        	<c:when test="${idx3 ne 0}">
		        		<option value="<c:out value="${start_startyy - idx3}${start_mm}" />" <c:if test="${monthList eq start_start }">selected</c:if>><c:out value="${start_startyy - idx3}년&nbsp;${start_mm}월" escapeXml="false"/></option>
		        	</c:when>
		        </c:choose> 
		    </c:forEach>
        </c:forEach>
   </select>
   ~
   <select id="end_${list.colid}" name="end_${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}" >
	    <c:set var="end_ym_today" value="<%=new java.util.Date()%>" />
        <fmt:formatDate value="${end_ym_today}" pattern="yyyyMM" var="end_start"/>
        <c:set var="end_startyy" value="${fn:substring(end_start,0,4)}" /> 
        <c:set var="end_startmm" value="${fn:substring(end_start,4,6)}" /> 
        <fmt:parseNumber value="${end_startmm}" var="end_startmm_num"/>
        
        <c:forEach begin="0" end="10" var="idx3" step="1">
        	<c:forEach begin="1" end="12" var="idx4" step="1">
        		<c:choose>
		        	<c:when test="${idx4 eq 1}"><c:set var="end_mm" value="12" /></c:when>
		        	<c:when test="${idx4 eq 2}"><c:set var="end_mm" value="11" /></c:when>
		        	<c:when test="${idx4 eq 3}"><c:set var="end_mm" value="10" /></c:when>
		        	<c:when test="${idx4 eq 4}"><c:set var="end_mm" value="09" /></c:when>
		        	<c:when test="${idx4 eq 5}"><c:set var="end_mm" value="08" /></c:when>
		        	<c:when test="${idx4 eq 6}"><c:set var="end_mm" value="07" /></c:when>
		        	<c:when test="${idx4 eq 7}"><c:set var="end_mm" value="06" /></c:when>
		        	<c:when test="${idx4 eq 8}"><c:set var="end_mm" value="05" /></c:when>
		        	<c:when test="${idx4 eq 9}"><c:set var="end_mm" value="04" /></c:when>
		        	<c:when test="${idx4 eq 10}"><c:set var="end_mm" value="03" /></c:when>
		        	<c:when test="${idx4 eq 11}"><c:set var="end_mm" value="02" /></c:when>
		        	<c:when test="${idx4 eq 12}"><c:set var="end_mm" value="01" /></c:when>
		        </c:choose>
		        
		        <c:set var="monthList" value="${end_startyy - idx3}${end_mm}" />
		        <c:choose>
		        	<c:when test="${idx3 eq 0 && 13 - idx4 <= end_startmm_num }">
		        		<option value="<c:out value="${end_startyy - idx3}${end_mm}" />" <c:if test="${monthList eq end_start }">selected</c:if>><c:out value="${end_startyy - idx3}년&nbsp;${end_mm}월" escapeXml="false"/></option>
		        	</c:when>
		        	<c:when test="${idx3 ne 0}">
		        		<option value="<c:out value="${end_startyy - idx3}${end_mm}" />" <c:if test="${monthList eq end_start }">selected</c:if>><c:out value="${end_startyy - idx3}년&nbsp;${end_mm}월" escapeXml="false"/></option>
		        	</c:when>
		        </c:choose> 
		    </c:forEach>
        </c:forEach>
   </select>
</c:if>



<%-- 시간 콤보 --%>
<c:if test = "${list.objtype == 'select_time'}">       
	<select id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}">
	   <option value="">선택</option>
	   <c:forEach var="index" begin="0" end="24" step="1">
	       <c:set var="val" value="${index}" />
	       <c:if test="${val < 10}">          	 
          	 <c:set var="val" value="0${val}" />
           </c:if>
           <option value="${val}">${val}</option>
	   </c:forEach>
	</select>
</c:if>

<%-- 라벨 (등록자 및 수정자) --%>
<c:if test = "${list.objtype == 'label'}">
	<c:if test = "${list.colid == 'reg_id' or list.colid == 'upd_id'}">	  
		<input type="text" id="${list.colid}" name="${list.colid}" style="width:${sWidth}px;" class="input ${sObjNm}" />
	</c:if>
</c:if>

<%-- 일반 버튼 --%>
<c:if test = "${list.objtype == 'btn_n'}">
	<button type="button" onclick="COMMON.fn_set_find_day('${list.formname}','${list.objid}');" class="btn_s">${list.name}</button>
</c:if>