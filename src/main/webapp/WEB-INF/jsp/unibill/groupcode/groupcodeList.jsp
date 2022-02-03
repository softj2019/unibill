<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>

<link rel="StyleSheet" type="text/css" href="/css/dtree.css" />
<script type="text/javascript" src="<c:url value='/extLib/dtree.js'/>"></script>

<script type="text/javascript">
	function getDetail(grpId){
		if($("#searchGrpType").val() == "" ) {alert("그룹구분을 선택해 주세요."); return;}
		
		var param = {"grpType":$("#searchGrpType").val(), "grpId": grpId};
		
		$.ajax({
			type: 'POST',
			url: '/groupcode/getDetail.do',
			datatype : "json",
			data: param ,
			success : function(data) {
				$("#grpType").val(data.grpInfo.grpType);
				$("#grpId").val(data.grpInfo.grpId);
				$("#grpNm").val(data.grpInfo.grpNm);
				$("#upGrpId").val(data.grpInfo.upGrpId);
				$("#depth").val(data.grpInfo.depth);
				$("#showSn").val(data.grpInfo.showSn);
				$("#useYn").val(data.grpInfo.useYn);
				
				//d.s(deptId);
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
	}
	function do_search(){
		if($("#searchGrpType").val() == "" ) {alert("그룹구분을 선택해 주세요."); return;}
		
		$("#id_loader").show();	 
		COMMON.fn_scrin_block_ui("B");
		
		var param = { "searchGrpType": $("#searchGrpType").val(), "searchGrpId": $("#searchGrpId").val(), "searchGrpNm": $("#searchGrpNm").val() };
		
		$.ajax({
			type: 'POST',
			url: '/groupcode/getSearch.do',
			datatype : "json",
			data: param ,
			success : function(data) {
				var grpCnt =  data.grpCnt;
				$("#totcnt").text();
				$("#totcnt").text('리스트 (검색수 : ' + grpCnt + '건)');
				
				d = new dTree('d');
				var grpIdList  	  = data.grpIdList;
				var grpNmList  	  = data.grpNmList;
				var upGrpIdList	  = data.upGrpIdList;
				var depthList  	  = data.depthList;
				
				var grpIdLists   = grpIdList.split("|");
				var grpNmLists   = grpNmList.split("|");
				var upGrpIdLists = upGrpIdList.split("|");
				var depthLists    = depthList.split("|");
				
				var grpcd = "";
				var upgrpcd = "";
				var grpnm ="";
				if(grpIdLists != null){
					for(var i=0;  i<grpIdLists.length; i++){
						grpcd = grpIdLists[i];
						upgrpcd = upGrpIdLists[i];
						grpnm = grpNmLists[i];
						d.add("ROOT", -1, 'ROOT');
						//alert(grpcd+" : " +upgrpcd+" : "+grpnm);
						
						d.add(grpcd, upgrpcd, grpnm, "<c:url value='#'/>");
						//d.add(grpcd, upgrpcd, grpnm, "<c:url value='/department/getDetail.do'/>");

					}
					
				}
				var h = "<p align='right' ><a >전체 </a><img src='/images/tree/winType/folderopen.gif' style='cursor:hand' class='middle' onclick='javascript: d.openAll();'/>  &#124; <img src='/images/tree/winType/folder.gif' style='cursor:hand' class='middle' onclick='javascript: d.closeAll();'/></p>";
				$("#dtree").html(h + d.toString());
				
				$("#id_loader").hide();
				
				COMMON.fn_scrin_block_ui("UB");
				
			},
			error: function(xhr, status, error) {
				alert("에러");
				$("#id_loader").hide();
				
				COMMON.fn_scrin_block_ui("UB");
			}
		});
	}
	
	
	function formSave(){
		var grpType = $("#grpType").val();
		var grpId = $("#grpId").val();
		var upGrpId = $("#upGrpId").val();
		var grpNm = $("#grpNm").val();
		var depth = $("#depth").val();
		var showSn = $("#showSn").val();
		var useYn =  $("#useYn").val();
		
		if(!formChk("I")){
			return;	
		}
		
		var param = {"grpType": grpType, "grpId": grpId, "upGrpId": upGrpId, "grpNm": grpNm, "depth": depth, "showSn": showSn, "useYn": useYn};
		
		if(checkPK()) {
			if(confirm("수정 하시겠습니까?") == true){
				alert(doAjax("UPDATE", param));	
			}
		}else{
			if(confirm("등록 하시겠습니까?") == true){
				alert(doAjax("INSERT", param));	
			}
		}
	}
	
	function checkPK(){
		var grpType = $("#grpType").val();
		var grpId = $("#grpId").val();
		
		var chkFlag = false;
		
		var param = {"grpType": grpType, "grpId": grpId};
		
		$.ajax({
			type: 'POST',
			url: '/groupcode/checkPK.do',
			datatype : "json",
			data: param ,
			async: false,
			success : function(data) {
				if(data.checkPK == true){
					chkFlag = true;
				}
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
		return chkFlag;
	}
	
	function doAjax(insFlag, param){
		var msg ="";
		$.ajax({
			type: 'POST',
			url: '/groupcode/'+ insFlag +'.do',
			datatype : "json",
			data: param ,
			async: false,
			success : function(data) {
				if(insFlag=="UPDATE"){
					if(data.result == -1){
						msg="수정중 에러가 발생하였습니다.";
					}else{
						msg ="정상적으로 수정되었습니다.";
					}
					
				}else if(insFlag=="INSERT"){
					if(data.result == -1){
						msg="등록중 에러가 발생하였습니다.";
					}else{
						msg ="정상적으로 등록되었습니다.";
					}
					
				}else{
					if(data.result == -1){
						msg="삭제중 에러가 발생하였습니다.";
					}else{
						msg ="정상적으로 삭제되었습니다.";
					}
				}
				
			},
			error: function(xhr, status, error) {
				msg = "에러가 발생했습니다.";
			}
		});
		
		return msg;
	}
	
	function formChk(flag){
		var chk = true;
		
		if(flag == "D" || flag == "I"){
			if($("#grpType").val() == ""){
				chk = false; 
			}
			if($("#grpId").val() == ""){
				chk = false; 
			}
			if($("#grpNm").val() == ""){
				chk = false; 
			}
			if($("#upGrpId").val() == ""){
				chk = false; 
			}
		}
		
		if(flag == "I"){
			if($("#grpId").val() == ""){
				chk = false; 
			}
			if($("#grpNm").val() == ""){
				chk = false; 
			}
			if($("#upGrpId").val() == ""){
				chk = false; 
			}
			if($("#depth").val() == ""){
				chk = false; 
			}
			if($("#showSn").val() == ""){
				chk = false; 
			}
			if($("#useYn").val() == ""){
				chk = false; 
			}
				
		}
		
		return chk;
	}
	
	
	function formDelete(){
		var grpType = $("#grpType").val();
		var grpId = $("#grpId").val();
		
		
		formChk("D");
		
		var param = {"grpType": grpType, "grpId": grpId};
		
		if(confirm("삭제 하시겠습니까?") == true){
			alert(doAjax("DELETE", param));	
		}
		
		
	}
</script>
<!-- Search  -->
<%--<h2><button type="button" id="btn_bmark" class="${sClass}" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 그룹코드관리</h2><!--북마크페이지 class="on"추가-->--%>
<h2><button type="button" id="btn_bmark" class="on" title="즐겨찾기추가" onclick="COMMON.fn_bookmark_save('frm_main', '', '${menu_code}');"></button> 그룹코드관리</h2><!--북마크페이지 class="on"추가-->

<form id="searchFrm" name="searchFrm" method="post" action="">
	<fieldset>
		<!--폼양식-->
		<legend>Search</legend>
		<div class="srh_wrap">
			<div>
				<label>그룹 구분</label>
				<select id="searchGrpType" name="searchGrpType" class="selectBox" >
					<option value="" selected="selected">선택</option>
					<c:forEach var="gLists" items="${gList}" varStatus="status">
						<option value="${gLists.grpType }" >${gLists.grpNm }</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label>그룹 ID</label>
				<input type="text" title="" id="searchGrpId" name="searchGrpNm"/>
			</div>
			<div>
				<label>그룹 이름</label>
				<input type="text" title="" id="searchGrpNm" name="searchGrpNm"/>
			</div>
			<span class="btn_wrap">
                    <button type="button" onclick="javascript:do_search();" class="btn_srh">조회</button>
                </span>
		</div>
	</fieldset>
</form>

<!-- table -->


<%--<table id="myGrid0A"></table>--%>
<%--<table id="myGrid0B"></table>--%>

<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
	<!-- 조회결과 -->
	<div class="table_wrap" style="padding:0px;">
		<div class="pop_layout_wrap">
			<div class="pop_layout_l" style="width:40%;"> <!-- margin-right=전체width/2+30 -->
				<div class="table_wrapper" style="height:380px;">
					<table id="myGrid01"><tr><td id="totcnt" name="totcnt">리스트 (검색수 : 건)</td></tr></table>
					<table id="myGrid01Page">
						<tr><td>
							<div id="dtree" class="dtree" >
								<p align="right" >
									<a >전체 </a>
									<img src="/images/tree/winType/folderopen.gif" style="cursor:hand" class="middle" />  | 
									<img src="/images/tree/winType/folder.gif" style="cursor:hand" class="middle" />
								</p>
								<script type="text/javascript">
								</script>
							</div>
						</td></tr>
							
					</table>
				</div>
			</div>
			<div class="pop_layout_r" id="rightDiv" name="rightDiv" style="width:55%;padding:0px;"> <!-- width=전체width/2+30 -->
				<!-- 버튼 -->
				<%--<div class="btngroup">--%>
				<%--<button type="button" onclick="" class="btn_pop_add2">추가</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_remove2">삭제</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_add3">전체추가</button>--%>
				<%--<button type="button" onclick="" class="btn_pop_remove3">전체삭제</button>--%>
				<%--</div>--%>
				<div class="confirm_cont" style="width:100%;"><!-- 팝업창 크기에 맞게 위치변경 -->
					<form id="frmHtml" name="frmHtml" method="post" action="">
						
						<input type="hidden" title="자료순번" id="frm_ubseq"       name="ubseq"		value="" />
						<table summary="등록/상세정보" class="tb02">
							<caption>등록/상세정보</caption>
							<colgroup>
								<col width="130">
								<col>
								<col width="130">
								<col>
							</colgroup>
							<tbody>
							<tr>
								<th scope="row">그룹 유형<span class="asterisk"></span></th>
								<td><input type="text" title="그룹 유형" id="grpType" name="grpType" value=""/></td>
								<th scope="row">그룹 ID<span class="asterisk"></span></th>
								<td><input type="text" title="그룹 ID" id="grpId" name="grpId" value=""/></td>
							</tr>
							<tr>
								<th scope="row">상위그룹 ID<span class="asterisk"></span></th>
								<td><input type="text" title="상위그룹 ID" id="upGrpId" name="upGrpId" value=""/></td>
								<th scope="row">그룹 이름<span class="asterisk"></span></th>
								<td ><input type="text" title="그룹 이름" id="grpNm" name="grpNm" value=""/></td>
							</tr>
							<tr>
								<th scope="row">레벨<span class="asterisk"></span></th>
								<td><input type="text" title="레벨" id="depth" name="depth" value=""/></td>
								<th scope="row">출력순번</th>
								<td><input type="text" title="출력순번" id="showSn" name="showSn" value=""/></td>
							</tr>
							<tr>
								<th scope="row">사용여부(Y/N)<span class="asterisk"></span></th>
								<td colspan="3"><input type="text" title="사용여부" id="useYn" name="useYn" value="" /></td>
							</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="btn_pop_wrap">
			<button type="button" onclick="formSave();" class="btn_pop">저장</button>
			<button type="button" onclick="formDelete();" class="btn_pop">삭제</button>

		</div>
	</div>



	<table id='myGrid'></table>



	<%--<ul>--%>
	<%--<li style="float: left;">--%>
	<%--<table id="myGrid01"></table>--%>
	<%--</li>--%>
	<%--<li style="float: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>--%>
	<%--<li>--%>
	<%--<div class="confirm_cont" style="width:500px;"><!-- 팝업창 크기에 맞게 위치변경 -->--%>
	<%--<!-- 등록폼 -->--%>
	<%--<p class="commentre"><span class="asterisk"></span> 필수입력 항목입니다.</p><!--추가-->--%>
	<%--<table summary="등록/상세정보" class="tb02">--%>
	<%--<caption>등록/상세정보</caption>--%>
	<%--<colgroup>--%>
	<%--<col width="90">--%>
	<%--<col>--%>
	<%--<col width="90">--%>
	<%--<col>--%>
	<%--</colgroup>--%>
	<%--<tbody>--%>
	<%--<tr>--%>
	<%--<th scope="row">그룹구분<span class="asterisk"></span></th>--%>
	<%--<td><input type="text" title="그룹구분" id="frm_grpType"       name="grpType"		value="${cmCfggrpidVO.grpType}" /></td>--%>
	<%--<th scope="row">그룹ID<span class="asterisk"></span></th>--%>
	<%--<td><input type="text" title="그룹ID" id="frm_grpId"       name="grpId"		value="${cmCfggrpidVO.grpId}"/></td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<th scope="row">자료순번</th>--%>
	<%--<td><input type="text" title="자료순번" id="frm_ubseq"       name="ubseq"		value="${cmCfggrpidVO.ubseq}" /></td>--%>
	<%--<th scope="row">상위그룹ID<span class="asterisk"></span></th>--%>
	<%--<td><input type="text" title="상위그룹ID" id="frm_upGrpId"       name="upGrpId"		value="${cmCfggrpidVO.upGrpId}"/></td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<th scope="row">그룹명<span class="asterisk"></span></th>--%>
	<%--<td><input type="text" title="그룹명" id="frm_grpNm"       name="grpNm"		value="${cmCfggrpidVO.grpNm}"/></td>--%>
	<%--<th scope="row">레벨</th>--%>
	<%--<td><input type="text" title="레벨" id="frm_depth"       name="depth"		value="${cmCfggrpidVO.depth}"/></td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<th scope="row">사용여부(Y/N)<span class="asterisk"></span></th>--%>
	<%--<td><input type="text" title="사용여부" id="frm_useYn"       name="useYn"		value="${cmCfggrpidVO.useYn}"/></td>--%>
	<%--<th scope="row">관리번호</th>--%>
	<%--<td><input type="text" title="관리번호" id="frm_manNo"       name="manNo"		value="${cmCfggrpidVO.manNo}"/></td>--%>
	<%--</tr>--%>
	<%--</tbody>--%>
	<%--</table>--%>
	<%--<!-- //등록폼 -->--%>
	<%--<div class="btn_pop_wrap">--%>
	<%--<button type="button" onclick="fn_formReset();" class="btn_pop">초기화</button>--%>
	<%--<button type="button" onclick="fn_formSave_AJAX();" class="btn_pop">저장</button>--%>
	<%--</div>--%>
	<%--</div>--%>
	<%--</li>--%>
	<%--</ul>--%>

	<!-- //table -->
	<!-- Paginate -->
	<!--
    <div class="paginate">
    <a href="#" class="direction prev"><span></span><span></span> 처음</a>
    <a href="#" class="direction prev"><span></span> 이전</a>
    <a href="#">1</a>
    <a href="#">2</a>
    <a href="#">3</a>
    <a href="#">4</a>
    <strong>5</strong>
    <a href="#">6</a>
    <a href="#">7</a>
    <a href="#">8</a>
    <a href="#">9</a>
    <a href="#" class="direction next">다음 <span></span></a>
    <a href="#" class="direction next disable">끝 <span></span><span></span></a>
    </div>
    -->
	<!-- //Paginate -->




