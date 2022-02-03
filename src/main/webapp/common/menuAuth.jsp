<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% response.setStatus(200); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Error Page</title>
    <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico?1" />
    <link rel="stylesheet" type="text/css" href="/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <script language="javascript">
    </script>
</head>
<body class="error">
<p class="error_title"><em> </em></p>
<div class="error_content" style="width:900px;">
    <h2>메뉴 권한이 없습니다.</h2>
    <p>이동하려는 메뉴에 권한이 존재하지 않습니다.</p>
    <p>정상적인 방법으로 메뉴에 접근해 주시기 바랍니다.</p>
    <br />
    <!-- <p>문의사항 : <a href="mailto:admin@admin.com"> admin@admin.com</a></p> -->
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" onclick="location='/main/main.do'" class="btn_link" >메인화면으로 이동</button>
</div>
</body>
</html>