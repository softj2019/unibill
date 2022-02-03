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
		
	
		
	

	});
	
	
	
	function excel_use_reaon_mag_insert(){
		//alert($("#reason_msg").text());
		//alert(document.getElementById("reason_msg").value);
		//if($("#reason_msg").text() == null || $("#reason_msg").text() == ""){
		if(document.getElementById("reason_msg").value == null || document.getElementById("reason_msg").value  == ""){
			$.unibillDialog.alert('확인', '엑셀다운 사유를 입력하여 주세요');
			return;
		}
		//alert("reason_msg="+$("#reason_msg").val());
		/* textarea ie 이외는 val로 받아야 함 */
		$("#reasonMsg").val($("#reason_msg").val());
		$("#ly_exreason_pop").hide();
		
		COMMON.fn_get_form("frm_main", "proc_frm", CONTEXT_ROOT + "/content/contentExcelDown.do", "fn_excelDownload_callback").submit();
	}
		
	function userlock_doc_insert(){
		if($("#frm_detail").find("#user_id").val() == null || $("#frm_detail").find("#user_id").val() == ""){
			$.unibillDialog.alert('확인', '제한해제할 사용자ID를 입력하여 주세요');
			return;
		}
		if(document.getElementById("docno").value == null || document.getElementById("docno").value  == ""){
			$.unibillDialog.alert('확인', '문서번호를 입력하여 주세요');
			return;
		}
		
		$("#userDocNo").val($("#docno").val());
		$("#ly_userlock_pop").hide();
		
		var sDetailFormId= "frm_detail";
		var dtlRadioVal = $(":input:radio[name=dtl_tmp_type]:checked").val();
		if (dtlRadioVal == undefined) dtlRadioVal = "I";

		// 필수항목 체크 없음 / KEY 컬럼 수정 여부에 따르지 않고 무조건 수정 모드 셋팅			
		$("#"+sDetailFormId).find("#actionFlag").val("UPDATE");
		
		var msg = '수정하시겠습니까?';
		var actionFlag = $("#"+sDetailFormId).find("#actionFlag").val();
		
		$.unibillDialog.confirm('확인', msg, 
		    function (isTrue) {
				try {
		            // 처리중 메시지 show
					$("#id_loader").show();	 
					COMMON.fn_scrin_block_ui("B");
					COMMON.fn_submit_createForm(sDetailFormId, "proc_frm", CONTEXT_ROOT + "/content/derestrict.do", "COMMON.fn_reset_callback_content");
				} catch (E) {
					$.unibillDialog.alert('오류', '폼데이터 변환중 오류가 발생하였습니다. :' + E);
				}
			}
		);

		
		//COMMON.fn_get_form("frm_main", "proc_frm", CONTEXT_ROOT + "/content/contentExcelDown.do", "fn_excelDownload_callback").submit();
		
	}
	
	
</script>

<%-- exreason 상세정보 --%>
<form id="frm_userlock" name="frm_userlock" method="post">
<input type="hidden" id="tableName" name="tableName" value="cm_cfguser" />  <%-- 고객ID, 고객명 자동완성기능 위함 --%>
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />

<input type="hidden" id="qtype" name="qtype" value="" />
<input type="hidden" id="qseq" name="qseq" value="" />
<div id="ly_userlock_pop" class="ui_confirm" style="display:none;">
<div class="confirm_cont" style="width:600px;height:200px;"><!-- 팝업창 크기에 맞게 위치변경 -->
        <h4>계정잠금해제신청문서</h4>
        <button type="button" class="close" onclick="document.getElementById('ly_userlock_pop').style.display = 'none';" title="닫기">Close</button>
        
        <div style="heigth:10px;">&nbsp;</div>
        <table summary="추가정보(선택)" class="tb02" style="padding:0px;">
            <caption>추가정보(선택)</caption>
            <colgroup>
                <col width="91px">
                <col >                
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row" style="height:">문서번호</th>                    
                    <td class="paddingTd" >
						<div style="padding: 0px; margin: 0px;">
                    		<input type="text" id="docno" name="docno" />
                    	</div>
					</td>
                </tr>
            </tbody>
        </table>
        
        <div class="btn_pop_wrap"><button type="button" onclick="userlock_doc_insert()" class="btn_pop">확인</button></div>
        
    </div>
       
  </div>
</form>


			    