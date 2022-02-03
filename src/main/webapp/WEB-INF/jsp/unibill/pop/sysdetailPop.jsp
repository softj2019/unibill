<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="../../css/monitor/css/style_pop.css"/>

<script type="text/javascript">
$(document).ready(function() {
	$("title").text('시스템 상세정보');
});
</script>


	



<div class="pop">
	<div class="top">
		<img src="../../images/monitor/images/pop_logo.png" class="logo">
		<!-- <img src="../../images/monitor/images/pop_close.png" class="close_btn" onclick="self.close();"> -->
	</div>
	
	
	<div class="info">
		<p style="width:100%;">
			<c:forEach var="sysDetail" items="${getSysDetail}">
			Hostname : ${sysDetail.hostNm }<br>
			Cpu info : ${sysDetail.cpu }<br>
			Memory Size : ${sysDetail.mem }<br>
			Os Release Version : ${sysDetail.os }<br>
			Kernel release Version : ${sysDetail.kernel } <br>
			Kernel Bits : ${sysDetail.kernelBit }<br>
			Last Boot time : ${sysDetail.lastBoot }<br>
			Ssh Version : ${sysDetail.sshVer }<br>
			</c:forEach>
		</p>
		
	</div>
	<!-- <img src="../../images/monitor/images/pop_hs.png"> -->

</div>


