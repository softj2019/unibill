<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="/css/notice/1_style.css" />

<style type="text/css">    

 	
</style>

<script type="text/javascript">
$(document).ready(function(){
	//푸터 위치 조정
	//$(".cbp-spmenu-push").height("2000px");
	//이밴트 초기화
	//$("#searchCustId").keypress(function(event) {    if ( event.which == 13 ) {    CUSTOM.fn_custBillSearch();    }    });
	
	
		
});

function notice_write(){
	alert("1111");
	COMMON.fn_submit_fileForm("notice_searchFrm", "proc_frm", "<c:url value='/content/writeNotice.do'/>", "fn_bundle_upload_callback");
}	

function test(){
	alert("tset");
}


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="${sClass}" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 그룹코드관리</h2><!--북마크페이지 class="on"추가-->--%>
<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> FAQ 리스트</h2><!--북마크페이지 class="on"추가-->

<form id="faq_searchFrm" name="faq_searchFrm" method="post" action="">
<input type="hidden" id="scrin_code" name="scrin_code" value="<c:out value=""/>" />
<input type="hidden" id="tableName" name="tableName" value="" />
<input type="hidden" id="dept_main" name="formid" value="" />
<input type="hidden" id="fileName" name="fileName" value="" />
<input type="hidden" id="callBackFunction" name="callBackFunction" value="" />
	<fieldset>
		<!--폼양식-->
		<legend>Search</legend>
		<div class="srh_wrap">
			<div id="faq" style="">
				<label>FAQ<span class="asterisk"></span></label>
				<table>
					<tr>
						<td>
							<select style="padding:0 0px; width: 100px;" id="faq_type" name="faq_type">
								<option value="all" >전체</option>
								<option value="sub" selected="selected">질문</option>
								<option value="msg" >답변</option>
							</select>
						</td>
						<td>
							<input class="form-control input-sm col-xs-3" style="width:700px;margin-left:20px; " type="text" id="s_faq" name="s_faq">			
						</td>
					</tr>
				</table>
				
				
				
			</div>
			<span class="btn_wrap">
            	<button type="button" onclick="javascript:FAQ.fn_faqSearch();" class="btn_srh">조회</button>
            </span>
		</div>
	</fieldset>
</form>

<!-- table -->


<%--<table id="myGrid0A"></table>--%>
<%--<table id="myGrid0B"></table>--%>

<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
	<body>
  <!-- wrapper -->
<div class="wrapper">

    <!-- Main content ----------------------------------->
    <div>
		<div class="list07">
			<div class="detail_table07">
				<div class="table_title07">
				<table>
					<colgroup>
						<col width="10%">
						<col width="*">
						<col width="10%" span="3">
					</colgroup>
					<thead>
					<tr>
						<th>번호</th>
						<th>질문</th>
						<th>파일</th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>
					</thead>
				</table>
				</div>

				<div class="blank"></div>

				<div id="n_contents">
				<table>
					<colgroup>
						<col width="10%">
						<col width="*">
						<col width="10%" span="3">
					</colgroup>
					<tbody id="faqbody">
					</tbody>
				</table>
				</div>
			</div>
			<!-- 페이징 -->
			<div class="page" id="n_page" >
				<div class="totalNo" id="totCnt"><span>총 게시물: <em>0</em></span></div>
				<div class="totalP" id="totalPage"><span><em>0 / 0</em> Page</span></div>
			</div>
		    
			<!-- //페이징 -->
		</div>
    </div>
    <!-- Main content ----------------------------------->

</div>
<!-- ./wrapper -->
</body>

<%-- 공지사항 상세정보 --%>
<div id="notice_div" >
<div id="ly_faqList_pop" class="ui_confirm" style="display:none;">
<div class="confirm_cont" style="width:1070px;height:650px;"><!-- 팝업창 크기에 맞게 위치변경 -->
	<h4>FAQ 리스트</h4>
	<div>

    <!-- Main content ----------------------------------->
    <section class="content">
		<div class="list09">
			<div class="detail_table09">

				<div id="w_contents">
				 <table>
					<colgroup>
						<col width="10%"/>
						<col width="20%" />
						<col width="10%"/>
						<col width="20%" />
						<col width="10%"/>
						<col width="*" />

					</colgroup>
					<tr>
						<th>작성일</th>
						<td id="regtm"></td>
						<th>작성자</th>
						<td id="regid" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="6"> <span id="ntitle" style="width:1030px; height: 150px; overflow:scroll;"></span> </td>
					</tr>
					<tr>
						<td colspan="6" >
						<p id="nmsg" style="height: 250px; overflow:scroll;">
						</p>
						</td>
					</tr>

					<!-- <tr>
						<th colspan="6">
							<div class="file_list">
							<div>첨부파일 용량: <span id="nfilesz"> 00000</span>KB</div>
							<button type="button" onclick="" class="btn04" id="fdown">다운로드</button>
							<table id="fileList">
								<tr><td><img src="../../images/notice/bbs/clip.png">&nbsp; 망연계 제품 자료_20190606.PPT (30.4KB)</td></tr>
							</table>
							</div>
						</th>
					</tr> -->
					<!-- <tr>
						<th colspan="6">
							<div class="file_list">
							<div>첨부이미지파일 용량: <span id="nimgfilesz"> 00000</span>KB</div>
							<button type="button" onclick="" class="btn04" id="imgdown">다운로드</button>
							<table id="imgfileList">
								<tr><td><img src="../../images/notice/bbs/clip.png">&nbsp; 망연계 제품 자료_20190606.PPT (30.4KB)</td></tr>
							</table>
							</div>
						</th>
					</tr> -->
				</table>

				<div class="btn_right">
				<button type="button" onclick="document.getElementById('ly_faqList_pop').style.display = 'none'" class="btn_06_1">닫기</button>
				</div>

				</div>
			</div>
			
		</div>
    </section>
    <!-- /.content -------------------------------------->
  </div>
</div>
    
</div>
</div>
<%-- //공지사항 상세정보 --%>


</div>



