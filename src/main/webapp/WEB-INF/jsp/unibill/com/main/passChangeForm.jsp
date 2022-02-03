<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/reset.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login.css'/>" />

    <link rel="stylesheet" type="text/css" href="<c:url value='/extLib/jquery-ui-themes-1.12.1/themes/base/theme.css'/>" />


    <script type="text/javascript" src="<c:url value='/extLib/js-cookie/src/js.cookie.js'/>"></script>

    <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-1.11.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>

    <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.selectbox.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/selectBox.js'/>"></script>

    <script type="text/javascript" src="<c:url value='/extLib/jquery/plugins/blockUI/jquery.blockUI.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/extLib/jquery/jquery.unibillDialog.js'/>"></script>

    <script type="text/javascript" src="<c:url value='/js/namespace.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/content.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/utile.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/grid.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/custom.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/bill.js'/>"></script>

    <% String contextRoot = request.getContextPath(); %>
    <script type="text/javascript">
        var CONTEXT_ROOT = '<%=contextRoot%>';
        var isLiveMode = false;
        //    var isLiveMode = true;

        var sFormId = "frm_changePass";       // form id

        $(document).ready(function() {

            $('#newPass').keypress(function(event) {
                if ( event.which == 13 ) {
//                event.preventDefault();
                    checkPass();

                }
            });
            $('#newPass2').keypress(function(event) {
                if ( event.which == 13 ) {
//                event.preventDefault();
                    doChangePass();

                }
            });

        });

        function checkPass(){
            var chk = $("#isIdSave").is(":checked");//.attr('checked');
            var _userid = $("#userid").val();


            UTILE.fn_ajax("/main/checkPreviousPassword.json",$("#frm_changePass").serializeArray(),function(obj, resultData, textStatus, jqXHR){
                if( fn_isNotBlank( resultData ) ){
                    if( "Y" == resultData.isPreviousPassword ){
                        fn_alert( "최근" + resultData.previousPasswordCnt +" 회 안에 동일한 비밀번호를 사용한적이 있습니다. 다른 비밀번호를 사용해주세요.");
                    }
                }
            })
        }

        function doChangePass(){
            var chk = $("#isIdSave").is(":checked");//.attr('checked');
            var _userid = $("#userid").val();


            $("#" + sFormId).submit();
        }
    </script>
</head>
<body class="login">
<div class="login_wrapper">
    <form id="frm_changePass" name="frm_changePass" action="<c:url value='/main/passChangeSave.do'/>">
        <%--<input type="hidden" name="reqUri" value="${reqUri}"/>--%>
        <div class="input_wrap">
            <p class="input"><input type="text" id="oldPass" name="oldPass" value="" placeholder="이전 비밀번호를 입력하세요."></p>
            <p class="input"><input type="password" id="newPass" name="newPass" value="" placeholder="변경할 비밀번호를 입력하세요."></p>
            <p class="input"><input type="password" id="newPass2" name="newPass2" value="" placeholder="변경할 비밀번호를 확인하세요."></p>
        </div>

        <span class="btn_blue_l">
        <a href="javascript:doChangePass();">비밀번호 변경</a>
        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
        </span>
    </form>
</div>
<script type="text/javascript">
    var msg = '<%=request.getParameter("msg")%>';

    if ("notLogin" == msg) {
        $.unibillDialog.alert('알림', '로그인이 필요합니다.');
    } else if ("notOldPass" == msg) {
        $.unibillDialog.alert('알림', '이전 비밀번호를 입력해주세요. !!');
    } else if ("notNewPass" == msg) {
        $.unibillDialog.alert('알림', '신규 비밀번호를 입력해주세요.');
    } else if ("notEqPass" == msg) {
        $.unibillDialog.alert('알림', '변경할 비밀번호가 일치하지 않습니다..');
    }
</script>
</body>
</html>