<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="/css/notice/1_style.css?after" />

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
}	


</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="${sClass}" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 그룹코드관리</h2><!--북마크페이지 class="on"추가-->--%>
<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 공지사항 글쓰기</h2><!--북마크페이지 class="on"추가-->

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
			<div id="notice" style="">
				<label>공지사항<span class="asterisk"></span></label>
				<table>
					<tr>
						<td>
							<select style="padding:0 0px; width: 60px;">
								<option>전체</option>
								<option>제목</option>
								<option>내용</option>
							</select>
						</td>
						<td>
							<input class="form-control input-sm col-xs-3" type="text">			
						</td>
					</tr>
				</table>
				
				
				
			</div>
			<span class="btn_wrap">
            	<button type="button" onclick="javascript:CUSTOM.fn_custBillSearch();" class="btn_srh">조회</button>
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
						<col width="12%">
						<col width="13%" span="3">
					</colgroup>
					<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>파일</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
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
						<col width="12%">
						<col width="13%" span="3">
					</colgroup>
					<tbody>
					<tr class="em"><!--강조-->
						<td><span class="boardmark">공지</span></td>
						<td class="fl"><a href="#n">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템</a><img src="/images/notice/bbs/ico_new.png" alt="new"></td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >11400</td>
					</tr>
					<tr class="em">
						<td><span class="boardmark">공지</span></td>
						<td class="fl reply"><a href="#n">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템</a><img src="/images/notice/bbs/ico_new.png" alt="new"></td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr class="em">
						<td>1</td>
						<td class="fl re_reply"><a href="#n">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템</a><img src="/images/notice/bbs/ico_new.png" alt="new"></td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl re_re_reply">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템</td>
						<td></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl"><a href="#n">원비즈시스템 작업복 및 카렌다 수령</a></td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl"><a href="#n">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템</a></td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl">원비즈시스템 작업복</td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl">원비즈시스템 작업복 및 카렌다 수령 원비즈시스템 작업복 및 카렌다 수령</td>
						<td></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl">원비즈시스템 작업복 및 카렌다</td>
						<td></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="fl">원비즈시스템 작업복 및 카렌다</td>
						<td><img src="/images/notice/bbs/clip.png"></td>
						<td>관리자</td>
						<td>2019-06-26</td>
						<td >400</td>
					</tr>

					</tbody>
				</table>
				</div>
			</div>
			<!-- 페이징 -->
			<div class="page">
				<div class="totalNo"><span>총 게시물: <em>158</em></span></div>
				<div class="totalP"><span><em>1 / 12</em> Page</span></div>
				<span><a class="nnav first" href="#n"></a></span>
				<span><a class="nnav prev" href="#n"></a></span>
				<span><a href="#n">1</a></span>
				<span><a href="#n" class="on">2</a></span>
				<span><a href="#n">3</a></span>
				<!-- <span><a href="#n">4</a></span>
				<span><a href="#n">5</a></span>
				<span><a href="#n">6</a></span>
				<span><a href="#n">7</a></span>
				<span><a href="#n">8</a></span>
				<span><a href="#n">9</a></span>
				<span><a href="#n">10</a></span>-->
				<span><a class="nnav next" href="#n"></a></span>
				<span><a class="nnav last" href="#n"></a></span>
			</div>
		    <div class="btn_right"><button class="btn_04" type="button" onclick="notice_write();">글쓰기</button></div><!-- 관리자 로그인시 버튼확인 -->
			<!-- //페이징 -->
		</div>
    </div>
    <!-- Main content ----------------------------------->

</div>
<!-- ./wrapper -->
</body>


</div>



