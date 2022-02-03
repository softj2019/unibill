<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String passChangeYn = (String)request.getParameter("passChangeYn"); %>
<script type="text/javascript">
	var timeCnt = 0;
    var sTopFormId = "topForm";       // form id

    var passChangeYn = '<%=passChangeYn%>';
    
	$( document ).ready(function() {
		$('.step3').hide();
		$('.main_content > .table').hide();
		if ("${deptGrpId}" == "A10004" || "${deptGrpId}" == "A10039" || "${deptGrpId}" == "00000000") {
			$('.step3').show();
			$('.main_content > .table').show();
		}
		
		if ("${menu_code}" != "") {
			
			// 메뉴명
			COMMON.fn_set_menu('${menu_code}','${menu_nm}','${bmark}');
			
			// 즐겨찾기 메뉴구성
			COMMON.fn_bookmarkMake(sTopFormId, "H");	
			
			// 즐겨찾기 위치 조정
			var bMarkPos   = '${bookmarkPos}';
			var bMarkPosId = "bookposL";
			if (bMarkPos == "L") bMarkPosId = "bookposR";		
			COMMON.fn_bookMarkPos(bMarkPosId, bMarkPos, 'cbp-spmenu-s1', 'cbp-spmenu-s2',"H");
		}
		<c:if test="${'Y' eq timeoutExtent}">
        UTILE.fn_autoLogout_init( ${sessionMaxInactiveIntervalTime} );
        </c:if>
        
        <c:if test="${'Y' eq passChangeYn}">
        UTILE.fn_confirm("비밀번호 사용기한이 만료되었습니다.<br/>지금 비밀번호를 변경하시겠습니까?",function(){
        	CUSTAG.fn_openPop('600','510','/main/passChangePop.do');

        },{falseCallback:function(){
        	 UTILE.fn_ajax("/main/passChangeCancel.do",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
        	    	window.close();  		 
        	 });
        }})
        </c:if>



        $('#newPass').keypress(function(event) {
            if ( event.which == 13 ) {
                if( ( $('#oldPass').val() ) == ( $('#newPass').val() ) ){
                    UTILE.fn_alert("이전 비밀번호와 동일합니다.<br/> 이전 비밀번호와 다른 비밀번호를 사용하여주세요.");
                }else{
                    checkPass();
                }
            }
        });
        $('#newPass2').keypress(function(event) {
            if ( event.which == 13 ) {
                if( ( $('#newPass').val() ) != ( $('#newPass2').val() ) ){
                    UTILE.fn_alert("신규 비밀번호가 일치하지 않습니다.<br/> 비밀번호를 확인해주세요.")
                }else{
                    doChangePass();
                }
            }
        });


        $("button").on("click", function() {
            <c:if test="${'Y' eq timeoutExtent}">
            UTILE.fn_autoLogout_init( ${sessionMaxInactiveIntervalTime} );
            </c:if>
        });


    });


    function checkPass(){
        UTILE.fn_ajax("/main/checkPreviousPassword.json",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
            if( fn_isNotBlank( resultData ) ){
                if( "Y" == resultData.isPreviousPassword ){
                    fn_alert( "최근" + resultData.previousPasswordCnt +" 회 안에 동일한 비밀번호를 사용한적이 있습니다. 다른 비밀번호를 사용해주세요.");
                    $("#newPass").val("");
                    $("#newPass2").val("");
                }else{
                    fn_alert( "사용가능한 비밀번호 입니다.");
                }
            }
        });
    }

    function doChangePass(){

        UTILE.fn_ajax("/main/passChangeSave.json",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
            if( fn_isNotBlank( resultData ) ){
                if( fn_isNotBlank( resultData.msg ) ){
                    fn_alert( resultData.msg );
                }
                if( fn_isNotBlank( resultData.code ) ){
                    if( "isNotLogin" == resultData.code ){

                    }else if( "oldPassEmpty" == resultData.code ){
                        $("#oldPass").focus();

                    }else if( "newPassEmpty" == resultData.code ){
                        $("#newPass").focus();

                    }else if( "newPassMissMatched" == resultData.code || "oldAndNewPassMatched" == resultData.code || "passwordCharacterConditionCheck" == resultData.code ){
                        $("#newPass").val("");
                        $("#newPass2").val("");
                        $("#newPass").focus();
                    }else if( "oldPassMissMatched" == resultData.code ){
                        $("#oldPass").val("");
                        $("#oldPass").focus();
                    }else if( "previousPassword" == resultData.code ){
                        $("#newPass").val("");
                        $("#newPass2").val("");
                    }else if( "SUCCESS" == resultData.code ){
                        UTILE.fn_alert("수정되었습니다.");
                        $('#passChange_pop').hide();
						
                        $("#oldPass").val("");
                        $("#newPass").val("");
                        $("#newPass2").val("");
                        
                        $("#mainForm").find("#firstloginYn").val("N");
                    }

                }
            }
        });
    }

    function passChangePopClose(){
    	var floginYn = '${firstloginYn}';
    	if(floginYn == "Y"){
    		window.location = '/main/logout.do';
    	}else{
    		$('#passChange_pop').hide();   
    	}
/*         <c:choose>
        	<c:when test="${'Y' eq passChangeYn}">
        		window.location = '/main/logout.do';
			</c:when>
			<c:otherwise>
        		$('#passChange_pop').hide();
			</c:otherwise>
        </c:choose>  비밀번호 변경 취소하더라도 로그아웃되지 않도록 변경*/
   	/*  UTILE.fn_ajax("/main/passChangeCancel.do",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
	    	$('#passChange_pop').hide();    		 
	 }); */

	}

    <c:if test="${'Y' eq passChangeYn}">
    UTILE.fn_confirm("비밀번호 사용기한이 만료되었습니다.<br/>비밀번호를 변경하여야 <br/>계속 이용할 수 있습니다.<br/>지금 비밀번호를 변경하시겠습니까?",function(){
//        window.location = '/main/passChangeForm.do';
        COMMON.fn_openPassChange_pop();

    },{falseCallback:function(){
    	 UTILE.fn_ajax("/main/passChangeCancel.do",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
    	    	$('#passChange_pop').hide();    		 
    	 });

    }})
    </c:if>
<c:if test="${'Y' eq passChangeYn}">
UTILE.fn_confirm("비밀번호 사용기한이 만료되었습니다.<br/>지금 비밀번호를 변경하시겠습니까?",function(){
	CUSTAG.fn_openPop('600','510','/main/passChangePop.do');

},{falseCallback:function(){
	 UTILE.fn_ajax("/main/passChangeCancel.do",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
	    	window.close();  		 
	 });
}})
</c:if>
</script>

<form id="topForm" name="topForm" method="POST">
<input type="hidden" id="menu_code" name="menu_code" value="${menu_code}" />
<input type="hidden" id="menu_name" name="menu_name" value="${menu_nm}" />
<input type="hidden" id="bmark" name="bmark" value="${bmark}" />
<input type="hidden" id="role_code" name="role_code" value="${roleCode}" />
<input type="hidden" id="user_id" name="user_id" value="${user_id}" />
<input type="hidden" id="cust_id" name="cust_id" value="${cust_id}" />
<input type="hidden" id="logoutMode" name="logoutMode" value="logOut" />
<input type="hidden" id="roleLevel" name="roleLevel" value="${roleLevel}" />
<input type="hidden" id="grp_cd" name="grp_cd" value="${grp_cd}" />
<input type="hidden" id="rep_cust_id" name="rep_cust_id" value="${rep_cust_id}" />
</form>
  
   <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <div class="m-0"><img src="/dinamicweb/dist/img/logo_sinc_sub.png " class=" float-left" style="margin-top:8px;"></div>
      </li>
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Navbar Search -->
    	<c:if test="${'Y' eq timeoutExtent}">
			<li class="nav-item dropdown mr-3">
		      	<a class="nav-link" href="#" onclick="javascript:UTILE.fn_autoLogout_reset();">
					<span id="timeoutCounterSpan"> </span> 후 자동 로그아웃 [연장]
				</a>
      		</li>
		</c:if>
		<li class="nav-item dropdown mr-3">
		   	<ul>
		   	<li >
			<a class="nav-link" href="#" onclick="CUSTAG.fn_openPop('600','510','/main/passChangePop.do');">
				<span class="mr-2" style="marigin-left:-60px;">비밀번호 변경 </span> 
			</a>
			</li>
			</ul>
      	</li>
      <!-- Messages Dropdown Menu -->
      <li class="nav-item dropdown mr-3">
      	<a class="nav-link" href="#" onclick="COMMON.fn_form_submit('topForm','/main/logout.do');">
          <span class="mr-2" >
          	${userNm}님
          </span><i class="fas fa-sign-out-alt"></i>
        </a>
      </li>
      <!-- <li class="nav-item" style="margin-left: -30px;">
        <a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#" role="button">
          <i class="fas fa-th-large"></i>
        </a>
      </li> -->
      <!-- Notifications Dropdown Menu -->
    </ul>
  </nav>
  
<aside class="main-sidebar sidebar-dark-danger elevation-4"
  style ="background-image: url(/dinamicweb/dist/img/slide_bg_1.png);
  background-position: right bottom;
  background-size: cover;">
    <!-- Brand Logo -->
    <a href="join_4.html" class="brand-link">
      <img src="/dinamicweb/dist/img/simbol.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
      <span class="brand-text font-weight-light"><img src="/dinamicweb/dist/img/logo.png"></span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
               <c:set var="idx" value="1" />
               <c:set var="sMenuid1" value="" />
               <c:set var="sMenuid2" value="" />
               <c:set var="sMenuid3" value="" />
               
               <c:forEach var="list" items="${menuInfo}" varStatus="status">
               		<%-- 1 dept 메뉴 --%>
               		<c:if test="${list.depth1 == '1'}">               		    
               			<li class="nav-item">
			                <a href="#" class="nav-link">
			                  <i class="nav-icon fas fa-copy"></i>
			                  <p>
			                  	${list.menuNm1}
			                  	<i class="right fas fa-angle-left"></i>
			                  </p>
			                </a>
	                </c:if>
	                <%-- 2 dept 메뉴 --%>
	                	<ul class="nav nav-treeview">
	                <c:forEach var="list2" items="${menuInfo2}" varStatus="status">
	                		<c:if test="${list.menuId1 eq list2.upMenuId2}">
	                			<c:forEach var="list3" items="${menuInfo3}" varStatus="status">	                			
									<c:if test="${list2.menuId2 eq list3.upMenuId3}">	
										<li class="nav-item">
			                				<a href="#" class="nav-link" onclick="COMMON.fn_menu_click('${list3.menuId3}','${list3.scrinYn}','${list3.roleYn}','${list3.url}','topForm');">
			                					<i class="fas fa-caret-right  nav-icon"></i>
			                					<p>${list3.menuNm3}</p>
			                				</a>
									</c:if>
									</li>
								</c:forEach>
	                			
	                		</c:if>	
	                			
	                			
	                </c:forEach>			
	                	</ul>			
	                				
	                	</li>
               </c:forEach> 
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
