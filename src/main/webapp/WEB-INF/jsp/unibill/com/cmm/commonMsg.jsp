<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-1.11.1.min.js'/>"></script>	
<script type="text/javaScript" language="javascript" defer="defer">
// 페이지 로딩 초기 설정
$( document ).ready(function() {

	try {

		// 처리중 메시지 hide
		parent.$("#id_loader").hide();	
		parent.COMMON.fn_scrin_block_ui("UB");

		<c:choose>
			<c:when test = "${resultMsg == 'SAVE_SUCCESS'}">				
				parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.insert" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>', '<c:out value="${sPostFun}"/>');				    
						</c:if>
					}
				);				
			</c:when>		
			<c:when test = "${resultMsg == 'UPDATE_SUCCESS'}">
				parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.update" />', 
				    function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
						 	parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>', '<c:out value="${sPostFun}"/>');				    
						</c:if>
					}
				);
		    </c:when>
		    <c:when test = "${resultMsg == 'DELETE_SUCCESS'}">
				parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.delete" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">
						 	parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>', '<c:out value="${sPostFun}"/>');
						</c:if>
					}
				);				
		    </c:when>
		    <c:when test = "${resultMsg == 'ERROR'}">
				parent.$.unibillDialog.alert('알림', '<spring:message code="fail.common.msg" />');
			</c:when>		    
			<c:when test = "${resultMsg == 'SQL_ERROR'}">
				parent.$.unibillDialog.alert('알림', '${errorMsg}');
			</c:when>
			<c:when test = "${resultMsg == 'SAVE_PROCESS'}">
				var msg = '<spring:message code="success.common.process" />';
			    <c:if test = "${!empty errorMsg}">
					msg = "${errorMsg}";
				</c:if>
				parent.$.unibillDialog.alert('알림', msg);
			    <c:if test = "${!empty callBackFunction}">	
					parent.<c:out value="${callBackFunction}"/>(msg+'|<c:out value="${OID}"/>');
                </c:if>
            </c:when>
            <c:when test = "${resultMsg == 'OD_SUCCESS'}">
                parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.od_reqst" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
						</c:if>
					}
				);				
			</c:when>
			<c:when test = "${resultMsg == 'CONT_SUCCESS'}">
            	parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.cont_reqst" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
						</c:if>
					}
				);				
			</c:when>
			<c:when test = "${resultMsg == 'SAVE_SUCCESS2'}">
	        	parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.save" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
						</c:if>
					}
				);				
			</c:when>
			<c:when test = "${resultMsg == 'JOB_SUCCESS'}">
	        	parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.job_confm" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
						</c:if>
					}
				);				
			</c:when>
			<c:when test = "${resultMsg == 'EXCEL_SUCCESS'}">					
				<c:if test = "${!empty callBackFunction}">				
					parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
				</c:if>
			</c:when>
			<c:when test = "${resultMsg == 'TEXCEL_SUCCESS'}">					
			<c:if test = "${!empty callBackFunction}">				
				parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
			</c:if>
		</c:when>
			<c:when test = "${resultMsg == 'CANCL_SUCCESS'}">
	        	parent.$.unibillDialog.alert('알림', '<spring:message code="success.common.reqst_cancl" />',
					function(isTrue) {
						<c:if test = "${!empty callBackFunction}">				
							parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
						</c:if>
					}
				);				
			</c:when>
			<c:when test = "${resultMsg == 'NONE_SUCCESS'}">
        	parent.$.unibillDialog.alert('알림', '등록대상이 아닙니다.',
				function(isTrue) {
					<c:if test = "${!empty callBackFunction}">				
						parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');				    
					</c:if>
				}
			);				
		</c:when>
            <c:when test = "${resultMsg == 'BILLING_JOB_REQUEST'}">
                parent.$.unibillDialog.alert('알림', '${errorMsg}',
                    function(isTrue) {
                        <c:if test = "${!empty callBackFunction}">
                        parent.<c:out value="${callBackFunction}"/>('<c:out value="${OID}"/>');
                        </c:if>
                    }
                );
            </c:when>
            <c:otherwise>
                <c:if test="${resultMsg ne ''}">
                    parent.$.unibillDialog.alert('알림', '${resultMsg}');
                </c:if>
            </c:otherwise>
        </c:choose>
		
	} catch(E) {alert(E);}		
});
</script>
</head>
<body>
<div id="dialog" title="알림">
    <p class="t_center">선택한 행이 없습니다.</p>
</div>
</body>
</html>