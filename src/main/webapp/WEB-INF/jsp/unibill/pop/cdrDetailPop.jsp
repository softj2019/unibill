<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	var isRun = false;
	var returnID = null;
	
	var xhr = null;
	
	$( document ).ready(function() {
		if($("#cdr_pop .confirm_cont").height() < 700){
			window.resizeTo($("#cdr_pop .confirm_cont").width(), $("#cdr_pop .confirm_cont").height() + 85);	
		}
		 
		
	});
	
	
</script>

<link rel="stylesheet" type="text/css" href="../../css/reset.css" />
<link rel="stylesheet" type="text/css" href="../../css/popup2.css" />

<script type="text/javascript" src="<c:url value='../../js/namespace.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.unibillDialog.js'/>"></script>

<form id="frm_cdrDetail" name="frm_cdrDetail" method="post" enctype="multipart/form-data">
<input type="hidden" id="buttonFlag" value="A" />
<div id="cdr_pop" class="ui_confirm">
<div class="dim" onclick="document.getElementById('cdr_pop').style.display = 'block'"></div>
    <div class="confirm_cont" id="id_detail_width" style="left: 0px; top: 0px; right: 0px; margin-top: 0px; margin-bottom: 0px; position: absolute;" ><!-- 팝업창 크기에 맞게 위치변경 -->
    <h4 id="id_tabNm" class="cdr_subtitle" ><span class="h_cdr">CDR</span> 상세정보 
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	
    	조회월&nbsp;<input type="text"  class="input" size="6" id="smonth" name="smonth" value="${smonth}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<span class="h_cdr">CDR</span>순번&nbsp;<input type="text" class="input" id="scdrseq" name="scdrseq" value="${scdrseq}"/>&nbsp;&nbsp;&nbsp
    	<button type="button" class="btn_srh" id="btn_search" onclick="COMMON.fn_cdr_detail_search($('#smonth').val(),$('#scdrseq').val());">조회</button>&nbsp;
    	<button type="button" class="btn_srh" id="btn_recdr" onclick="COMMON.fn_cdr_detail_recdr($('#smonth').val(),$('#scdrseq').val());">재계산</button>
    </h4>
    <div class="type01"></div>
         <table summary="등록/상세정보" class="tb02">
            <caption>등록/상세정보</caption>
            <colgroup>
                <col width="130">
                <col>
                <col width="130">
                <col>
                <col width="130">
                <col>
            </colgroup>
            <tbody>
                <tr class="del">
                    <th scope="row"><span class="d_cdr">CDR</span> 순번</th>
                    <td id="jobmsg" class="td_cost" >
                    	<c:choose>
                    		<c:when test="${cdr.cdrseq == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.cdrseq}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">교환기명</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.pbxSnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.pbxSnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">교환기구분</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.pbxTypenm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.pbxTypenm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                	<th scope="row">파일명</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.fileNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			<span >${cdr.fileNm}</span>
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">비과금종류</th>
                    <td class="td_kr">${cdr.nobill}</td>
                    <th scope="row">통화유형</th>
                    <td class="td_kr">
                    	<c:choose>
                    		<c:when test="${cdr.callTypenm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.callTypenm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row">회선종류</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.pstnTypenm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.pstnTypenm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">통화상태</th>
                    <td id="jobmsg" class="td_kr" >
                    	<c:choose>
                    		<c:when test="${cdr.callStatusnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.callStatusnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">CDR</span>수집 연결방법</th>
	                    <c:choose>
		                    <c:when test="${connTypenmkr == 'kr'}">
		                    	<td class="td_kr" >
		                    </c:when>
		                    <c:otherwise>
		                    	<td class="tar" >
		                    </c:otherwise>
	                    </c:choose>
                    	<c:choose>
                    		<c:when test="${cdr.connTypenm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.connTypenm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
               
                
                
                <tr class="del">
                    <th scope="row">시화시각<span class="asterisk"></span></th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.sdaynm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.sdaynm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">종화시각</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.edaynm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.edaynm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">통화시간(초)</th>
                    <td class="tar">${cdr.dur}</td>
                </tr>
                <tr class="del">
                    <th scope="row">원시 발신번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.ocaller == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ocaller}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">최종 전환번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.rdn == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.rdn}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">변환 발신번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.ncaller == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ncaller}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row">원시 착신번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.ocallee == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ocallee}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">최종 착신번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.fcallee == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.fcallee}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">변환 착신번호</th>
                    <td class="td_cost">
                    	<c:choose>
                    		<c:when test="${cdr.ncallee == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ncallee}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                
                <tr class="del">
                    <th scope="row"><span class="d_cdr">In</span> 사용자그룹</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.iugNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.iugNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">In</span> 라우트명</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.irtNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.irtNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">In Tenant</span></th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.itnNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.itnNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row"><span class="d_cdr">Out</span> 사용자그룹</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.ougNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ougNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">Out</span> 라우트명</th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.ortNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ortNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">Out Tenant</span></th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.otnNm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.otnNm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row">통계제외</th>
                    <td class="td_kr">
                    	<c:choose>
                    		<c:when test="${cdr.delYnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.delYnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">In</span> 변환라우터</th>
                    <c:choose>
                    	<c:when test="${irtNonmkr == 'kr'}">
                    		<td class="td_kr" >
                    	</c:when>
                    	<c:otherwise>
                    		<td class="tar" >
                    	</c:otherwise>
                    </c:choose>
                    
                    	<c:choose>
                    		<c:when test="${cdr.irtNonm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.irtNonm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row"><span class="d_cdr">Out</span> 변환라우터</th>
                    <c:choose>
                    	<c:when test="${ortNonmkr == 'kr'}">
                    		<td class="td_kr" >
                    	</c:when>
                    	<c:otherwise>
                    		<td class="tar" >
                    	</c:otherwise>
                    </c:choose>
                    	<c:choose>
                    		<c:when test="${cdr.ortNonm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.ortNonm}
                    		</c:otherwise>
                    	</c:choose>
                   	</td>
                </tr>
                <tr class="del">
                    <th scope="row"><span class="d_cdr">Call ID</span></th>
                    <td class="tar">
                    	<c:choose>
                    		<c:when test="${cdr.callid == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.callid}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">저장시간</th>
                    <td>
                    	<c:choose>
                    		<c:when test="${cdr.updTm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.updTm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">고액통화여부</th>
                    <td class="td_kr">
                    	<c:choose>
                    		<c:when test="${cdr.highYnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.highYnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row">장시간통화여부</th>
                    <td class="td_kr">
                    	<c:choose>
                    		<c:when test="${cdr.longYnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.longYnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">발신변작여부</th>
                    <td class="td_kr">
                    	<c:choose>
                    		<c:when test="${cdr.callerAlterYnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.callerAlterYnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">착신변작여부</th>
                    <td id="jobmsg" class="td_kr" >
                    	<c:choose>
                    		<c:when test="${cdr.calleeAlterYnnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.calleeAlterYnnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                	<th scope="row">과금 번호</th>
                    <td >
                    	<c:choose>
                    		<c:when test="${cdr.billno == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.billno}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                    <th scope="row">호구분</th>
                    <td class="td_kr_cost" >
                    	<c:choose>
	                    	<c:when test="${cdr.btypeNm == ''}">
	                    		&nbsp;
	                    	</c:when>
	                    	<c:otherwise>
	                    		<span class="pbx_kr_td">${cdr.btypeNm}</span>
	                    	</c:otherwise>
	                    </c:choose>
                    </td>
                    <th scope="row"></th>
                    <td >
                    </td>
                </tr>
                
                <c:if test="${cdr.btype > '0'}">
                	<tr class="del">
	                    <th scope="row">착신지역명</th>
	                    <td class="td_kr_cost">
	                    	<c:choose>
	                    		<c:when test="${cdr.calleeAreaNm == ''}">
	                    			&nbsp;
	                    		</c:when>
	                    		<c:otherwise>
	                    			<span class="pbx_kr_td">${cdr.calleeAreaNm}</span>
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <th scope="row">도수</th>
	                    <td class="td_kr_cost">
	                    	<c:choose>
	                    		<c:when test="${cdr.dosu == ''}">
	                    			&nbsp;
	                    		</c:when>
	                    		<c:otherwise>
	                    			${cdr.dosu}
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <th scope="row"></th>
	                    <td class="td_cost">
	                    </td>
	                </tr>
                	<tr class="del">
	                    <th scope="row">원본금액</th>
	                    <td class="td_cost">
	                    	<c:choose>
	                    		<c:when test="${cdr.ocost == ''}">
	                    			&nbsp;
	                    		</c:when>
	                    		<c:otherwise>
	                    			${cdr.ocost}
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <th scope="row">비교금액</th>
	                    <td id="jobmsg" class="td_cost" >
	                    	<c:choose>
	                    		<c:when test="${cdr.vcost == ''}">
	                    			&nbsp;
	                    		</c:when>
	                    		<c:otherwise>
	                    			${cdr.vcost}
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <th scope="row">청구금액</th>
	                    <td class="td_cost">
	                    	<c:choose>
	                    		<c:when test="${cdr.cost == ''}">
	                    			&nbsp;
	                    		</c:when>
	                    		<c:otherwise>
	                    			${cdr.cost}
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                </tr>
                </c:if>
                <tr class="del">
                    <th scope="row">조직구성(발신)</th>
                    <td class="td_kr" colspan="5">
                    	<c:choose>
                    		<c:when test="${cdr.grpcdnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.grpcdnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr class="del">
                    <th scope="row">조직구성(착신)</th>
                    <td class="td_kr" colspan="5">
                    	<c:choose>
                    		<c:when test="${cdr.dgrpcdnm == ''}">
                    			&nbsp;
                    		</c:when>
                    		<c:otherwise>
                    			${cdr.dgrpcdnm}
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                
            </tbody>
        </table>
      
        
        
        <c:if test="${roleid == 'D001'}">        
        <div class="btn_pop_wrap">
           	 <span id="id_detail_button" class="id_detail_button" >
           	 	원시자료
           	 </span>           	
        </div>
        
        <table summary="원시자료" class="tb02">
            <caption>원시자료</caption>
            <colgroup>
                <col width="230">
                <col >
                <col width="230">
                <col >
                <col width="230">
                <col >
            </colgroup>
            <tbody>
            	<c:set var="curRowCnt" value="0" />
            	<fmt:formatNumber var="lastRowCnt" value="${rawCdr.size()/3}" maxFractionDigits="0"/>
            	<c:set var="lastThCnt" value="${rawCdr.size()%3}" />
            	<c:set var="tdRCnt" value="1" />
                <c:forEach var="rawCdrList" items="${rawCdr}">
                	<c:if test="${tdRCnt == '1'}">
                		<tr class="del">
                	</c:if>  
                	<c:choose>
                		<c:when test="${curRowCnt ==  lastRowCnt}">
                			<c:if test="${lastThCnt == '1'}">
		                		<th scope="row" class="cdr_th" >${rawCdrList.rawNm}</th>
	                    		<td id="jobmsg" class="cdr_td" colspan="5">${rawCdrList.rawCdr}</td>
		                	</c:if> 
		                	<c:if test="${lastThCnt == '2'}">
		                		<th scope="row" class="cdr_th" >${rawCdrList.rawNm}</th>
	                    		<td id="jobmsg" class="cdr_td" colspan="3">${rawCdrList.rawCdr}</td>
		                	</c:if> 
		                	<c:if test="${lastThCnt == '3'}">
		                		<th scope="row" class="cdr_th" >${rawCdrList.rawNm}</th>
	                    		<td id="jobmsg" class="cdr_td" >${rawCdrList.rawCdr}</td>
		                	</c:if>  
                		</c:when>
                		<c:otherwise>
                			<th scope="row" class="cdr_th" >${rawCdrList.rawNm}</th>
	                    	<td id="jobmsg" class="cdr_td" >${rawCdrList.rawCdr}</td>	
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
        </c:if>
     </div>
     
     
 </div>
 </form>

