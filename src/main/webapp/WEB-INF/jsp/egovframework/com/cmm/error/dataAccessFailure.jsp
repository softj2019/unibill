<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Error Page</title>
    <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico?1" />
    <link rel="stylesheet" type="text/css" href="/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <script language="javascript">
        function fn_cGoAfterErrorPage(){
            history.back(-1);
        }
    </script>
</head>
<body class="error">
<p class="error_title"><em> </em></p>
<div class="error_content" style="width:900px;">
    <h2>데이터 조회 오류 입니다.</h2>
    <p>동일한 상태가 계속 발생할 경우 관리자에게 연락바랍니다.</p>
    <br />
    <!-- <p>문의사항 : <a href="mailto:admin@admin.com"> admin@admin.com</a></p> -->
    <button type="button" onclick="location='/index.jsp'" class="btn_link">홈으로 이동</button> &nbsp; <button type="button" onClick="javascript:fn_cGoAfterErrorPage();" class="btn_link">이전 페이지로</button>
</div>
</body>
</html>