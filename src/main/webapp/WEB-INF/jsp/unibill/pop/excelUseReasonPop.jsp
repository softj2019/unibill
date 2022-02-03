<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/faqPopup.css" />	
<script type="text/javascript">


	$( document ).ready(function() {
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/excontent/getRoleId.json',
			async: false ,
			success : function(data){
					if(data.roleId == "0"){
						$("#role_id").text("아이앤씨");
					}else if(data.roleId == "1"){
						$("#role_id").text("대성");
					}else if(data.roleId == "2"){
						$("#role_id").text("대성점포");
					}else if(data.roleId == "9"){
						$("#role_id").text("일반고객");
					}else{
						$("#role_id").text(data.roleId);
					}
				}
			});
	});
	
	
	
	function excel_use_reaon_mag_insert(){
		var reason_msg = document.getElementById("reason_msg").value;
		reason_msg = reason_msg.trim();
		if(reason_msg == null || reason_msg  == ""){
			$.unibillDialog.alert('확인', '엑셀다운 사유를 입력해주세요');
			return;
		}
		//alert("reason_msg="+$("#reason_msg").val());
		/* textarea ie 이외는 val로 받아야 함 */
		//
		$("#reasonMsg").val($("#reason_msg").val());
		$("#reason_msg").val("");
		$("#ly_exreason_pop").hide();
		// 처리중 메시지 show
		$("#id_loader").show();	 
		COMMON.fn_scrin_block_ui("B");
		
		COMMON.fn_get_form("frm_main", "proc_frm", CONTEXT_ROOT + "/content/contentExcelDown.do", "fn_excelDownload_callback").submit();
	}
	
	
</script>

<%
      Date date = new Date();
      SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
      String strdate = simpleDate.format(date);
%>

<%-- exreason 상세정보 --%>
<form id="frm_exreason" name="frm_exreason" method="post">
<input type="hidden" id="tableName" name="tableName" value="mb_cfggd" />  <%-- 고객ID, 고객명 자동완성기능 위함 --%>
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />

<input type="hidden" id="qtype" name="qtype" value="" />
<input type="hidden" id="qseq" name="qseq" value="" />
<div id="ly_exreason_pop" class="ui_confirm" style="display:none;">
<div class="confirm_cont" style="width:600px;height:500px;"><!-- 팝업창 크기에 맞게 위치변경 -->
        <h4>엑셀 다운로드 [자료 보안 관리 주의]</h4>
        <button type="button" class="close" onclick="document.getElementById('ly_exreason_pop').style.display = 'none';" title="닫기">Close</button>
        
        <div class="card-body login-card-body">
        <table summary="등록/상세정보" class="tb02 ">
            <caption>엑셀 다운로드</caption>
            <colgroup>
                <col width="150px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row">소속</th>
                    <td id="role_id">
                          
                    </td>
                </tr>
                <tr>
                    <th scope="row">작업일시</th>
                    <td>
                        <%= strdate%> 
                    </td>
                    </tr>                
                <tr>           
                <th scope="row">다운로드 사유</th>
                <td>
                  <textarea id="reason_msg" rows="4" class="form-control form-control-sm col-12 col-sm-12 float-left mr-1 text-xs" placeholder="사유를 입력해 주세요"></textarea>
                </td>
                </tr>
            </tbody>
        </table>
        <ul class=" mt-2 mb-2 pt-1 pb-2 border-bottom border-top">
          <li class=" text-bold pt-1 pb-1 text-danger" style=" padding-left:20px"><span> [자료 보안 관리 주의]</span></li>
          <li class=" ml-3 pt-1">&middot; 엑셀 다운로드 시 다운로드 이력을 관리합니다.</li>
          <li class=" ml-3">&middot; 제공받은 자료의 보안관리에 유의하시기 바랍니다.</li>
          <li class=" ml-3">&nbsp;&nbsp;다운로드 작업을 수행하겠습니까?</li>
        </ul>
       
        <div class="text-center text-sm mt-4 mb-3">
        <button type="button" class="btn  btn-danger col-2  text-bold" onclick="excel_use_reaon_mag_insert()">확인</button>
        <button type="button" class="btn  btn-dark col-2 mr-1  text-bold" onclick="document.getElementById('ly_exreason_pop').style.display = 'none';">취소</button>
      </div>
      </div>
    </div>  
  </div>
</form>
