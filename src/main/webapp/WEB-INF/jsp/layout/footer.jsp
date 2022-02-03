<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- footer -->
 <footer class="main-footer text-xs">
    <strong>Copyright &copy; 2022 <a href="https://adminlte.io">SHINSEGAE I&C INC</a>.</strong>
    All Rights Reserved.
    <div class="float-right d-none d-sm-inline-block">
      <div class="float-lg-right">
        <label class=" link mb-2">
          <a href="#" class="mr-1"> 이용약관 </a> ❘
          <a href="#" class="ml-1 mr-1"> 개인정보처리방침 </a> ❘
          <a href="#" class="ml-1"> 매뉴얼 </a>
        </label>
      </div>
    </div>
    
  </footer>
<!-- //footer -->
<!-- 달력 -->
<%-- <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui-1.11.4.js'/>"></script> --%>
<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui.setting.js'/>"></script>
    
<!-- 즐겨찾기 -->
<script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.bookmark.js'/>"></script>
<script>
    var menuLeft = document.getElementById('cbp-spmenu-s1'),
			menuRight = document.getElementById('cbp-spmenu-s2'),
			showLeft = document.getElementById('showLeft'),
			showRight = document.getElementById( 'showRight' ),
			body = document.body;    
    
    	if (showLeft != null && showRight != null) {     	
			showLeft.onclick = function () {
			    classie.toggle(this, 'active');
			    classie.toggle(menuLeft, 'cbp-spmenu-open');
			    disableOther('showLeft');
			};
			showRight.onclick = function () {
			    classie.toggle(this, 'active');
			    classie.toggle(menuRight, 'cbp-spmenu-open');
			    disableOther('showRight');
			};
			function disableOther(button) {
				if (button !== 'showLeft') {
				    classie.toggle(showLeft, 'disabled');
				}
				if (button !== 'showRight') {
				    classie.toggle(showRight, 'disabled');
				}
	        }   
    	}

</script>

<!-- menu -->
<script type="text/javascript" src="<c:url value='/js/menu.js'/>"></script>

<!-- 내부처리 iframe -->
<iframe id="proc_frm" name="proc_frm" frameborder="0" width="0" height="0"></iframe>

<!-- 파일 관련 폼 -->
<form id="download_frm" name="download_frm" method="post" action="/" >
<input type="hidden" id="file_cours" name="file_cours" value=""/>
<input type="hidden" id="file_name" name="file_name" value=""/>
<input type="hidden" id="file_del_yn" name="file_del_yn" value=""/>
</form>

