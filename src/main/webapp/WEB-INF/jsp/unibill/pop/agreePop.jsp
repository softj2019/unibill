<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/rule_popup.css" />	
<script type="text/javascript">
$( document ).ready(function() {
		
	
//팝업 show
var tx = ($(window).width() -  $("#ly_main_agree_pop .rule_confirm_cont").innerWidth()) / 2;
var ty = ($(window).height() - $("#ly_main_agree_pop .rule_confirm_cont").innerHeight()) / 2;

$("#ly_main_agree_pop .rule_confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
$("#ly_main_agree_pop .rule_confirm_cont").show();
	
});	


function agreeCheck(){
	var agree = $(":input:radio[name=agree]:checked").val();
	if(agree == "no"){
		$.unibillDialog.alert('확인', '동의서에 동의를 하여 주십시오. ');
	}else{
		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/main/updateAgreeYn.json',
			data: 'agree=' + agree,
			datatype : "json",
			success : function(data) {
				document.getElementById('ly_main_agree_pop').style.display = 'none';
				
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}
		});
	}
}
	
	
</script>

<%-- 동의여부 상세정보 --%>
<form id="frm_agree" name="frm_agree" method="post">
	<div id="ly_main_agree_pop" class="ui_confirm" >
		<div class="rule_confirm_cont" style="display:none;">
			<div id="pop_body" >
			<div id="pop_wrap" >
				<div id="pop_header">
					<a href="http://" class="img1"><img src="../images/treatyImages/login/login_logo2.png"/></a>
					<a href="http://" class="img2"><img src="../images/treatyImages/login/pop_close.png" onclick="document.getElementById('ly_main_agree_pop').style.display = 'none';" /></a>
				</div>
						
				<div id="pop_cont">
					<h2>개인정보 수집·이용 동의서</h2>
					<p>저희 한화에스앤씨(주) (이하 회사)는 별정통신2호(재과금)서비스와 관련하여 귀하의 개인정보를 아래와 같이 수집·이용·제공하고자 합니다.
					다음의 사항에 대해 충분히 읽어보신 후 동의 여부를 체크, 서명하여 주시기 바랍니다.</p>
					<!-- table -->
				            <table class="pop_tb">
				                <caption>개인(신용)정보의 수집 항목 및 수집·이용 목적</caption>
				                <colgroup>
				                    <col width="40%">
				                    <col width="60%">
					                <col>
					                </colgroup>
						            <thead>
						                 <tr>
						                 <th scope="col">수집항목</th>
						                 <th scope="col">수집·이용 목적</th>
						                 </tr>
						             </thead>
						             <tbody>
						                 <tr>
						                 <td>성명, 부서, 직급, 유선전화번호, 무선전화번호, 이메일주소</td>
						                 <td>법인에 대한 상품 및 서비스의 원활한 제공을 위한 연락, 고지사항 전달, 기타 계약업무 관계 유지업무</td>
						                 </tr>         
									</tbody>
						     </table>
					<!-- //table -->
					<!-- table -->
					          <table class="pop_tb">
					              <caption>개인(신용)정보의 처리의 위탁</caption>
					              <colgroup>
					                  <col width="40%">
					                  <col width="60%">
					                  <col>
					              </colgroup>
					              <thead>
					                  <tr>
					                  <th scope="col">수탁자</th>
					                  <th scope="col">위탁 업무의 내용</th>
					                  </tr>
					              </thead>
					              <tbody>
					                  <tr>
					                  <td>휴먼파워(주)</td>
					                  <td>빌링업무, 고객관계관리, 기타 안내업무</td>
					                  </tr>   
					                  <tr>
					                  <td>금강비지니스</td>
					                  <td>청구서 및 안내문 등 발송</td>
					                  </tr> 					
								  </tbody>
					          </table>
					 <!-- //table -->
					 <!-- table -->
						      <table class="pop_tb">
						          <caption>(필수)개인(신용)정보의 보유 및 이용 기간</caption>
						          <colgroup>
						              <col width="100%">
						              <col>
						          </colgroup>
						          <tbody>
						              <tr>
						              	<td>
											서비스 가입기간 동안 이용하고 요금정산 과납/오납 등 분쟁대비를 위해 해지 후 요금완납 6개월까지 보유, 이용(단, 약관, 정보통신망 이용촉진 및 정보보호 등에 관한 
											법률, 통신비밀보호법, 국세기본법 등 관계법령에 따라 보존할 필요성이 있는 경우는 규정에 따라 보유함) 
											※ 귀하께서는 개인정보 수집항목 수집동의를 거부하실 수 있으며, 다만 이 경우 계약(업무)관계 유지 및 회사 시스템 접근 및 정보관리 권한 등이 제한될 수 있습니다.
										</td>
						               </tr> 					
								  </tbody>
						       </table>
					 <!-- //table -->
					 <p>개인정보 관련정보를 위와 같이 <strong>수집 · 이용 · 위탁함</strong>에 대해 고지 받았으며 이를 충분히 이해하고 동의합니다.</p>
						 <div>
							<input type="radio" name="agree" id="agree" value="yes"/> <label for="yes">동의</label>
							<input type="radio" name="agree" id="agree" value="no" checked/> <label for="no">미동의</label>
						 </div>
							<p><button type="button"  onclick="agreeCheck();">저장</button></p>
				</div>
			</div>
			</div>
		</div>
	</div>        
	        
	
</form> 


			    