/**
 *  컨텐츠에 필요한 이벤트
 * @namespace {Object} UNI.UTILE
 */
UNI.UTILE = (function(_mod_content, $, undefined){

    var _defaultAJAXMethodType = "POST";

	var vConsole;
	if (typeof console == "undefined") {
		//window.console = {log: function() {}};
		vConsole = window.console || { log:function(){}};
	}else{
		vConsole = window.console;
	}


    fn_isObject = function(_obj){
        return _obj === Object(_obj);
    }

    // oops:  fn_isObject(Object.prototype) -> false
// oops:  fn_isObject(Object.create(null)) -> false
    fn_isObjectInstanceof = function(_obj){
        return _obj instanceof Object;
    }

    fn_isObjectTypeof = function(_obj){
        if (_obj === null) { return false;}
        return ( (typeof _obj === 'function') || (typeof _obj === 'object') );
    }

    /**
	 * @description 빈값 첵크
	 * @method fn_isBlank
	 * @param {object} obj
     * @returns {boolean}
	 * @example UTILE.fn_isBlank(" ") //true
	 * @example UTILE.fn_isBlank("") //true
	 * @example UTILE.fn_isBlank("\n") //true
	 * @example UTILE.fn_isBlank("a") //false
	 * @example UTILE.fn_isBlank(null) //true
	 * @example UTILE.fn_isBlank(undefined) //true
	 * @example UTILE.fn_isBlank(false) //true
	 * @example UTILE.fn_isBlank([]) //true
	 */
    fn_isBlank = function(obj){
        return(!obj || $.trim(obj) === "" || null == obj || 'null' == obj );
    }

    fn_isNotBlank = function(obj){
    	var _r = fn_isBlank(obj);
        return !_r;
    }


    /**
	 * @description str값이 없는 경우 defaultStr 값을 셋팅한다.
	 * @method fn_defaultIfBlank
     * @param str
     * @param defaultStr
     * @returns {*}
     */
	fn_defaultIfBlank = function(str, defaultStr){
		if( fn_isBlank(str) ){
			return defaultStr;
		}else{
			return str;
		}
	}

    /**
	 * @description 브라우저 console 에 출력
	 * @method fn_log
     * @param {string} obj console에 출력할 내용
     */
	fn_log = function(obj){
		if(!isLiveMode){
//			vconsole.log("[UTILE.fn_log] ==>> ", obj);
		}
	}

    /**
	 * @description 브라우저 console 에 출력할때 sPrefix 를 붙여서 출력.
	 * @method fn_log2
     * @param {string} sPrefix obj 값 앞에 출력할 내용
     * @param {string} obj console에 출력할 내용
     */
	fn_log2 = function(sPrefix , obj){
		if(!isLiveMode){
//			vconsole.log("[UTILE.fn_log2] ==>> ", sPrefix + obj);
		}
	}

	/**
	 * @description 확인창(confirm) 띄움.
	 * @method fn_confirm
	 * @param {string} msg - 메시지
	 * @param {function} callback - 콜백함수 (동의한 경우에만 호출됨)
	 * @param {object} options - 추가옵션 (추후 확장용)
	 * 	<ul>
	 *      <li>cancelCalllback : 콜백함수 - null(default)</li>
	 * 	</ul>
	 * 	@example     UTILE.fn_confirm("UTILE.fn_confirm 확인창에서 선택합니까?", function () {
						alert("YES");
					}, {
						falseCallback: function () {
							alert("NO");
						}
					});
	 */
    fn_confirm = function(msg, callback, options) {

        // var yesCallback = callback;
        // var noCallback;
        // if( fn_isNotBlank(options) && fn_isNotBlank(options.falseCallback)){
         //    noCallback = options.falseCallback;
		// }
        //
        //
        // if (confirm( msg ) ){
         //    yesCallback();
        // }else{
         //    noCallback();
		// }

        var width  = 300;
        var height = 150;

        var $dialog = $('<div id="dialog" title="확인"><p class="t_center">'+msg+'</p></div>');

        $dialog.dialog({
            width:width,
			/*height:height,*/
            bgiframe: true,
            modal: true,
            zindex:1000,  /*상세팝업(popup.css) css z-index:9999 에서 z-index:99 로 변경*/
            buttons:
                {
                    "확인": function() {
                        $( this ).dialog( "close" );
                        if( fn_isNotBlank(callback)){
                            callback();
                        }
                    },
                    "취소": function() {
                        $( this ).dialog( "close" );
                        if( fn_isNotBlank(options) && fn_isNotBlank(options.falseCallback)){
                            options.falseCallback();
                        }
                    }
                },
            open: function() {
//		        $(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
                $("#open-button").focus();
            }
        });

        $dialog.dialog('open');
    }

	/**
	 * @description alert 띄움.
	 * @method fn_alert
	 * @param {string|array} msg - 메시지
	 * @param {string} type - 유형 (default - warning)
	 * @param {object} options - 추가옵션 (추후 확장용)
     * 	@example         	    UTILE.fn_alert("UTILE.fn_alert 입니다", "", {
									callback:function() {
										alert("next function");
									}
								});
     );
	 */
	fn_alert = function(msg, type, options) {

		 // alert( msg );

		var title = "알림";
		if("W" == type){
			title = "경고";
		}
		
        var width  = 300;
        var height = 150;

        var $dialog = $('<div id="dialog" title='+title+'><p class="t_center">'+msg+'</p></div>');

        $dialog.dialog({
            width:width,
			/*height:height,*/
            bgiframe: true,
            modal: true,
            zindex:1000,  /*상세팝업(popup.css) css z-index:9999 에서 z-index:99 로 변경*/
            buttons:
                {
                    "확인": function() {
                        $( this ).dialog( "close" );
                        if(msg == "수정되었습니다."){
                        	window.close();
                        }
                        if(fn_isNotBlank(options) && fn_isNotBlank(options.callback)){
                            options.callback();
						}
                    }
                },
            open: function() {
//		        $(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
                $("#open-button").focus();
            }
        });

        $dialog.dialog('open');
	}

    /**
	 * @description 배열 형태의 Object를 식별하기 쉽게 문자열 형태로 출력.
	 * @method fn_objToStringLog
     * @param obj 배열 형태의 Object
     * @returns {*}
     */
	fn_objToStringLog = function(obj){
		if( fn_isBlank(obj) ){
			fn_log("[UTILE.fn_objToStringLog] obj=" + obj );

		}else{
			if ( obj.constructor == Array ){
				var array = $.map( obj , function(value, index) {
                    if( fn_isObject(value) ){

                        var objKeys = Object.keys(value);
                        if( objKeys ){
                            $.each(objKeys, function(i, val){
                                fn_log("[UTILE.fn_objToStringLog2][" + i + "]eval(value." + val + ") ==>> " + eval("value." + val) );
                            });
                        }
                    }else {
                        fn_log("[UTILE.fn_objToStringLog] obj:Array.value[" + index + "]=" + value);
                    }
					return [value];
				});
				return array;
			}else{

                if( fn_isObject(obj) ){

                    var objKeys = Object.keys(obj);
                    if( objKeys ){
                        fn_log("[UTILE.fn_objToStringLog] obj.toString() keys ==>> "  + objKeys );
                        $.each(objKeys, function(i, val){
                            fn_log("[UTILE.fn_objToStringLog] [" + i + "]eval(obj." + val + ") ==>> " + eval("obj." + val) );
                        });
                    }
                }
				fn_log("[UTILE.fn_objToStringLog] obj.toString() ==>> " + obj.toString() );
			}
		}
	}
    /**
	 * @description sPrefix 를 추가하여 배열 형태의 Object를 식별하기 쉽게 문자열 형태로 출력.
     * @param sPrefix
     * @param obj
     * @returns {*}
     */
    fn_objToStringLog2 = function(sPrefix , obj){

        if( fn_isBlank(obj) ){
            fn_log2("[UTILE.fn_objToStringLog2]" + sPrefix+ " obj=" , obj );

        }else{
            if ( obj.constructor == Array ){
                var array = $.map( obj , function(value, index) {

					if( fn_isObject(value) ){

                        var objKeys = Object.keys(value);
                        if( objKeys ){
                            $.each(objKeys, function(i, val){
                                fn_log("[UTILE.fn_objToStringLog2]" + sPrefix+ "[" + i + "]eval(value." + val + ") ==>> " + eval("value." + val) );
                            });
                        }
                    }else{
                        fn_log2("[UTILE.fn_objToStringLog2]" + sPrefix+ " obj:Array.value[" + index +"]=" , value );
                    }

                    return [value];
                });
                return array;
            }else{

                if( fn_isObject(obj) ){

                    var objKeys = Object.keys(obj);
                    if( objKeys ){
                        fn_log("[UTILE.fn_objToStringLog2]" + sPrefix+ " obj.toString() keys ==>> "  + objKeys );
                        $.each(objKeys, function(i, val){
                            fn_log("[UTILE.fn_objToStringLog2]" + sPrefix+ "[" + i + "]eval(obj." + val + ") ==>> " + eval("obj." + val) );
                        });
                    }
                }
                fn_log2("[UTILE.fn_objToStringLog2]" + sPrefix+ " obj.toString() ==>> " , obj.toString() );
            }
        }
	}

	/**
	 * @description 기본(범용) AJAX 처리 (기본은 x-www-form-urlencoded 형태의 POST 방식, Async mode, jQuery.ajax 활용)
	 * @method fn_ajax
	 * @param {string} url - 호출 서버 URL
	 * @param {object} reqParam - 전송할 데이터
	 * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수
	 * @param {object} options - 추가 옵션
	 * 	<ul>
	 * 		<li>async - true(default)/false</li>
	 * 		<li>dimmed - true/false(default)</li>
	 * 		<li>type - "post"(default)/"get"</li>
	 * 		<li>contentType - "application/x-www-form-urlencoded; charset=UTF-8"(default) / ... </li>
	 * 		<li>resultTypeCheck : 지정된 데이터 형식 확인여부 - true(default)/false</li>
	 * 		<li>spinner : 대기중 이미지 표시여부 - true(default)/false</li>
	 * 		<li>errorCallback :  서버쪽 응용 에러(NOK) 인경우 처리할 콜백함수 - null(default)</li>
	 * 		<li>timeout :  서버 응답 대기 시간 (단위:millisecond) - 30000(default)</li>
	 * 	</ul>
	 */
	fn_ajax = function(url, reqParam, callback, options) {

        //fn_objToStringLog2("[UTILE.fn_ajax][Start] url=" , url + "================");
        //fn_objToStringLog2("[UTILE.fn_ajax][Start] reqParam ==>> " , reqParam );
        // fn_objToStringLog2("[UTILE.fn_ajax][Start] callback ==>> " , callback );
        // fn_objToStringLog2("[UTILE.fn_ajax][Start] options ==>> " , options );

		options = $.extend({}, {
			async : true,
			type  : _defaultAJAXMethodType,
			traditional : true,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			resultTypeCheck : true,
			spinner : true,
			dimmed : false,
			errorCallback : null,
			timeout : 30000			// 기본 30초
		}, options);


		$.ajax({
			url		: url,
			async   : options.async,
			type  	: "POST",
			traditional : options.traditional,
			contentType : options.contentType,
			data	: reqParam,
			dataType: options.dataType,  // default : Intelligent Guess
			timeout : options.timeout,
            beforeSend: function(jqXHR, settings) {
                jqXHR.url = settings.url;

				if( options.spinner ){

					if( "/" == CONTEXT_ROOT ){
						CONTEXT_ROOT = "";
					}

					$.blockUI({
						//message : "<img src='" + CONTEXT_ROOT + "/images/common/loading.png' />",
						message : "<div class='loader_wrap'><div class='loader'></div><p>Loading...</p></div>",
						css : {
							top : ($(window).height() - 190) / 2 + 'px',
							left : ($(window).width() - 60) / 2 + 'px',
							width : '100px' ,
							color:		'#fff',
							border:		'0px solid #aaa',
							opacity: 1 ,
							cursor:		'wait'
						}
					});
				}
			},
			success : function (data, textStatus, jqXHR) {

				fn_log("[UTILE.fn_ajax][Success] (URL ==>> " + jqXHR.url + " )");
                if( !fn_isBlank(data) ) {
                    //fn_objToStringLog2(("[UTILE.fn_ajax][Success] data ==>> ", data);
                    if( fn_isNotBlank(data.retMsg) ){

                    	if( fn_isNotBlank(data.retCd) && "SESSION_FINISH" == data.retCd ){
                    		fn_confirm(data.retMsg,function(){
                    			window.location.href = CONTEXT_ROOT + "/main/loginForm.do";
							},null );
						}else{

                            fn_log("[UTILE.fn_ajax][Success] data.retMsg ==>> " + data.retMsg);
                            fn_alert(data.retMsg);
                        }
					}
                }else{

                    if( !fn_isBlank(jqXHR) ) {
                        //fn_objToStringLog2("[UTILE.fn_ajax][Success] jqXHR ==>> ", jqXHR);

                        if( fn_isNotBlank(jqXHR.responseText.retMsg) ){
                            fn_log("[UTILE.fn_ajax][Success] jqXHR.responseText.retMsg ==>> " + jqXHR.responseText.retMsg);
                            fn_alert(jqXHR.responseText.retMsg);
                        }
                    }
                }
                if( !fn_isBlank(textStatus) ){
                    //fn_objToStringLog2("[UTILE.fn_ajax][Success] textStatus ==>> " , textStatus );
                }


				/**
				 * callback 전 화면 unblock 여부
				 * true 이면 콜백 후 콜백화면에서 처리..
				 * 디폴트 콜백 전 처리
				 * */
				if( options.isUnblock!=true ) {
					if( options.spinner ){ $.unblockUI(); }
				}


//				if (options.errorCallback)	options.errorCallback.call(this, data, textStatus, jqXHR);	// 응용에러 콜백지원

				if (typeof callback == 'function') {
					fn_log("[UTILE.fn_ajax][Data Callback]");
					// callback.call(this, data, jqXHR, textStatus);
					// callback.call(this, data, textStatus, jqXHR);
					eval(callback(this, data, textStatus, jqXHR));
				}

			},
			error : function(jqXHR, textStatus, errorThrown) {

				fn_log("[UTILE.fn_ajax][error] (URL ==>> " + jqXHR.url + " )");

                if( !fn_isBlank(jqXHR) ) {
                    //fn_objToStringLog2("[UTILE.fn_ajax][Error] jqXHR ==>> ", jqXHR);
                }
                if( !fn_isBlank(textStatus) ) {
                    //fn_objToStringLog2("[UTILE.fn_ajax][Error] textStatus ==>> ", textStatus);
                }
                if( !fn_isBlank(errorThrown) ) {
                    //fn_objToStringLog2("[UTILE.fn_ajax][Error] errorThrown ==>> ", errorThrown);
                }

				if( options.spinner ){ $.unblockUI(); }

				if( jqXHR.status == 0 ){
					fn_alert('서버 응답이 없습니다.(Timeout)');
				} else if(jqXHR.status == 404){
					fn_alert('서버 경로(400)가 잘못되었습니다.');
				} else if(jqXHR.status == 500){

                    if( fn_isBlank( jqXHR.responseJSON ) ){
						fn_alert('서버 내부 처리에 문제(500)가 발생했습니다.');
                    }else{

                        if( fn_isBlank( jqXHR.responseJSON.message ) ){
                            fn_alert('서버 내부 처리에 문제가 발생했습니다.');
                        }else{
                            fn_alert(jqXHR.responseJSON.message);
                        }
                    }
				} else {

                    if( !fn_isBlank( jqXHR.responseJSON ) ){
                        if( fn_isBlank( jqXHR.responseJSON.message ) ){
                            fn_alert('서버 요청과정에 문제가 발생했습니다.(A)');
                        }else{
                            fn_alert('서버 요청과정에 문제가 발생했습니다.(B)\n' + jqXHR.responseJSON.message);
                        }
					}else if( !fn_isBlank( jqXHR.statusText ) ){
						if( fn_isBlank( jqXHR.statusText ) ){
							fn_alert('서버 요청과정에 문제가 발생했습니다.(C)');
						}else{
							fn_alert('서버 요청과정에 문제가 발생했습니다.(D)\n' + jqXHR.statusText + "(" + jqXHR.responseText + ")" );
						}
                    }else{
                        fn_alert('서버 요청과정에 문제가 발생했습니다.');
                    }
				}

			}
		});

	}

	/**
	 * @description Confirm 확인후 AJAX 처리 (주로 삭제처리시)
	 * 	<ul>
	 * 		<li>fn_ajax 함수 활용</li>
	 * 		<li>msg 를 제외한 나머지 parameter 는 fn_ajax 와 동일</li>
	 * 	</ul>
	 * @method fn_ajax4confirm
	 * @param {string} msg - Confirm 용 메시지
	 * @param {string} url - 호출 서버 URL
	 * @param {object} data - 전송할 데이터
	 * @param {function} callback - 서버 처리가 성공한 경우 호출한 콜백함수
	 * @param {object} options - 추가 옵션
	 */
	fn_ajax4confirm = function(msg, url, data, callback, options) {
		fn_confirm(msg, function() {
			fn_ajax(url, data, callback, options);
		});
	}

    fn_pollingCheck = function() {
        $.ajax({
            url: CONTEXT_ROOT + "/main/pollingCheck.json",
            data: {"timed": new Date().getTime()},
            dataType: "text",
            timeout: 5000,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                fn_log("[state: " + textStatus + ", error: " + errorThrown + " ]<br/>");
                if (textStatus == "timeout") { // 요청
                    fn_pollingCheck(); // 재귀적 호출

                    // 기타 버그, 오류 등 같은 네트워크
                } else {
                    fn_pollingCheck();
                }
            },
            success: function (data, textStatus) {
                fn_log("[state: " + textStatus + ", data: { " + data + "} ]<br/>");

                if (textStatus == "success") { // 요청 성공
                    fn_pollingCheck();
                }
            }
        });
    };


    /**
     * 00:00:00 형태로 문자열 반환
     * @returns {string}
     */
    fn_getDateTime_yyyymmddhhmmss = function ( dateObj ) {

    	if( fn_isBlank( dateObj ) ){

            dateObj = new Date();
        }

        var milliseconds = dateObj.getTime();
        var seconds = milliseconds / 1000;
        fn_log("milliseconds==>>" + milliseconds );
        fn_log("seconds==>>" + seconds );


        var yyyy = dateObj.getFullYear();
        var mm = dateObj.getMonth() < 9 ? "0" + (dateObj.getMonth() + 1) : (dateObj.getMonth() + 1); // getMonth() is zero-based
        var dd  = dateObj.getDate() < 10 ? "0" + dateObj.getDate() : dateObj.getDate();
        var hh = dateObj.getHours() < 10 ? "0" + dateObj.getHours() : dateObj.getHours();
        var min = dateObj.getMinutes() < 10 ? "0" + dateObj.getMinutes() : dateObj.getMinutes();
        var ss = dateObj.getSeconds() < 10 ? "0" + dateObj.getSeconds() : dateObj.getSeconds();

        var yyyymmddhhmmss = "".concat(yyyy).concat(mm).concat(dd).concat(hh).concat(min).concat(ss);
        fn_log("fn_getDateTime_yyyymmddhhmmss ==>> " + yyyymmddhhmmss );

        return yyyymmddhhmmss;
    }
    /**
     * 00:00:00 형태로 문자열 반환
     * @param ss 초
     * @returns {string}
     */
    fn_getTime_hhmmss = function (ss) {
        var nHour=0;
        var nMin=0;
        var nSec=0;
        if(ss>0) {
            nMin = parseInt(ss/60);
            nSec = ss%60;

            if(nMin>60) {
                nHour = parseInt(nMin/60);
                nMin = nMin%60;
            }
        }
        if(nHour<10) nHour = "0"+nHour;
        if(nSec<10) nSec = "0"+nSec;
        if(nMin<10) nMin = "0"+nMin;

        return ""+nHour+":"+nMin+":"+nSec;
    }


	var _autoLogoutNotifySec = parseInt(60); //자동 로그아웃 알림 시간 설정.
	var _timeoutSecInit = parseInt(600);//초기값(초단위) 300 == 5분 , 600 == 10분;
    var _intervalTargetFnObj;
    var _timeoutCountDown;
    var ginterval;

    //검색버튼 클릭시나 페이지 로딩시 호출되는함수 
    fn_autoLogout_init = function(timeoutSec) {
    	
    	globaltimeoutSec = timeoutSec;
    	
    	fn_setCookie('sessionTimeout', parseInt(timeoutSec)); //서버에서 가져온 최초시간을 cookie에 세팅함
    	//fn_setCookie('sessionTimeout', 30);
    	fn_setCookie('globalTime', parseInt(fn_getTimeSec()));
    	
    	ginterval=setInterval("fn_autoLogout_run()", 1000); // 1000 == 1초
    	
    	
    	//fn_getTimeSec();
    	
//    	if( fn_isNotBlank( timeoutSec ) ){
//            _timeoutSecInit = timeoutSec;
//        }
//
//        if( _autoLogoutNotifySec >= _timeoutSecInit ){
//    		fn_alert("자동 로그아웃 알림 시간(" + _autoLogoutNotifySec + ")은 자동 로그아웃 시간(" + _timeoutSecInit + ")보다 작아야 합니다.");
//		}
//
//        _timeoutCountDown = parseInt(timeoutSec);
//
//        fn_setCookie( "timeoutCountDown" , _timeoutCountDown ); // 첵크할 시간을 cookie로 관리하여 브라우저 창을 복제한 경우에 동기화 처리하기위한 목적으로 활용됨.
//        fn_setCookie( "holding" , fn_getMin()+''+fn_getSec());
//        fn_setCookie( "reset" , true);
//        if(timeCnt == 0){
//        	_intervalTargetFnObj = setInterval("fn_autoLogout_run()", 1000); // 1000 == 1초
//        }
//        
//        UTILE.fn_ajax(CONTEXT_ROOT + "/main/pollingCheck.json", null, function(obj, resultData, textStatus, jqXHR){
//
//            if( fn_isNotBlank( resultData ) ){
//            	if( !resultData.isLogin ){
//            		fn_alert("선택시간을 초과 하여 자동 로그아웃 되었습니다.");
//                    window.location.href = CONTEXT_ROOT + "/main/loginForm.do";
//				}
//			}
//		}, {}); // 세션 timeout 연장 목적으로 호출.

    }

    fn_autoLogout_reset = function() {
    	
    	fn_autoLogout_init(globaltimeoutSec);

    }

    
    var isshowlogoutbox=false;
    
    //1초마다 호출되는 roop함수     
    //일정시간 30초동안 입력없으면 자동로그아웃 처리
    //레이어팝업에서도 로그인 페이지로 이동
    fn_autoLogout_run_msgbox  = function() {  
    	 clearInterval(ginterval); 
    	 isshowlogoutbox=true;
		 var check = confirm("로그인 연장하시겠습니까?"); //끝나는시점 modal 
		  if(check) { 
			  UTILE.fn_autoLogout_reset();
			  return check;
		  } 
		  return false;
    }
    
    fn_autoLogout_run_msgbox_modalless  = function() {  
    	if(isshowlogoutbox==true) return true;
    	isshowlogoutbox=true;
   	 //	clearInterval(ginterval);  
    	 
		fn_confirm("로그인 연장하시겠습니까? ",
			function () { 
			 UTILE.fn_autoLogout_reset();
			 isshowlogoutbox=false;
			 return true;
		 	}
		  
     	); 
     	 
		  return false;
   }
    
    fn_autoLogout_run = function() {  //1초마다 호출되는 roop함수 
    	
    	var localTime = parseInt(fn_getTimeSec());
    	
    	var minusT = localTime - fn_getCookie("globalTime");  //현재시간에서  최초에 세팅된 시간의 차이
    	
    	var resultTime = fn_getCookie('sessionTimeout') - minusT;
    	
    	var sessionTimePerSec = fn_getTime_hhmmss(resultTime); //서버시간(초)를 hh:mm:ss 로 변환해서 표시 
    	
    	$("#timeoutCounterSpan").text(sessionTimePerSec);
    	
     
    	if(resultTime ==20) {  
    		 var msgret = fn_autoLogout_run_msgbox_modalless();     
        } 
    	if(resultTime <= 0 || fn_getCookie("globalTime") == undefined) {  
   	//	 var msgret = fn_autoLogout_run_msgbox2();    
   		//if(msgret==true) return; 
   		//window.location.href = CONTEXT_ROOT + "/main/main.do";
		    self.location = CONTEXT_ROOT + "/main/logout.do"; 
		  //  window.location.href = CONTEXT_ROOT + "/main/loginForm.do";
		 	 
       } 
   	
    	
    	
//    	var hold = fn_getCookie("reset"); 
//    	if(hold == 'true'){
//    		setTimeout(function(){
//    			fn_setCookie("reset", false);
//    		}, 1000);
//    		fn_setCookie("timeoutCountDown" , _timeoutSecInit );
//    		_timeoutCountDown = fn_getCookie("timeoutCountDown"); 
//    	}
//    	_timeoutCountDown--;
    	
    	//setInterval(, 5000);
    	
    /*	var hold2 = fn_getCookie("reset");
    	if(hold2 == 'false'){
    		//console.log("2222222")
    		
    		fn_setCookie("timeoutCountDown" , parseInt(_timeoutCountDown));
    	}*/
    	
    	//fn_setCookie("updTm" , fn_getMin()+''+fn_getSec());
    						   
        //var updTm = fn_getCookie("updTm"); 
//        var now = fn_getMin()+''+fn_getSec();
//    	//console.log("now:" + now);
//    	//now = fn_getMin()+''+fn_getSec();
//    	//alert(Number(now) - Number(hold))
//    	if(Number(now) - Number(hold) >= 1){
//    		fn_setCookie("reset" , false);
//    		_timeoutCountDown--;
//    		//_timeoutCountDown = fn_getCookie( "timeoutCountDown" );
//    		//fn_setCookie( "updTm" , fn_getMin()+''+fn_getSec());
//    		fn_setCookie("timeoutCountDown" , parseInt(_timeoutCountDown));
//    		//fn_setCookie("holding" , Number(Number(hold)+1));
//    		
//    		////console.log("if : " + Number(hold-1));
//    	}else{ 
//    		 
//    		//console.log("else");
//    		fn_setCookie("timeoutCountDown" , parseInt("13600"))
//    		//fn_setCookie( "holding" , fn_getDate_ymdhms());
//    		fn_setCookie( "holding" , fn_getMin()+''+fn_getSec());
//    		_timeoutCountDown = fn_getCookie("timeoutCountDown"); 
//    		//_timeoutCountDown = fn_getCookie( "timeoutCountDown" );
//    	}
       // _timeoutCountDown = fn_getCookie( "timeoutCountDown" );

      //  $("#timeoutCounterSpan").text( fn_getTime_hhmmss(_timeoutCountDown) );
        // $("#timeoutCounterSpan").text( fn_getTime_hhmmss(_timeoutCountDown) + "      timeoutCountDown ==>>" + fn_getCookie( "timeoutCountDown") );

       /* if(_timeoutCountDown < 0) {
            clearInterval(_intervalTargetFnObj);
            self.location = CONTEXT_ROOT + "/main/logout.do";*/
        // }else if( _autoLogoutNotifySec == _timeoutCountDown ) {
        // 	fn_confirm("로그인 연장하시겠습니까?",function () {
			// 		fn_log("[fn_autoLogout_run] _timeoutSecInit ==>> " + _timeoutSecInit );
			// 		fn_autoLogout_reset();
			// 	},{
			// 		falseCallback:function(){
        //
        //                 UTILE.fn_ajax(CONTEXT_ROOT + "/main/pollingCheck.json", null, function(obj, resultData, textStatus, jqXHR){
        //
        //                     if( fn_isNotBlank( resultData ) ){
        //                         if( !resultData.isLogin ){
        //                             window.location.href = CONTEXT_ROOT + "/main/loginForm.do";
        //                         }
        //                     }
        //                 }, {});
        //
			// 		}
			// 	}
        // 	);
        }

       // fn_setCookie( "timeoutCountDown" , _timeoutCountDown );
    

    fn_getCookie = function(){
        var cookie = document.cookie;
        fn_log("cookie ==>>" + cookie);
        return cookie;
	}
    /**
     * 쿠키값 추출
     * @param cookieName 쿠키명
     */
    fn_getCookie = function(key) {
        var result;
        if (typeof document === 'undefined') {
            return;
        }
    	// Read

		if (!key) {
			result = {};
		}

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling "get()"
		var cookies = document.cookie ? document.cookie.split('; ') : [];
		var rdecode = /(%[0-9A-Z]{2})+/g;
		var i = 0;

		for (; i < cookies.length; i++) {
			var parts = cookies[i].split('=');
			var cookie = parts.slice(1).join('=');

			if (cookie.charAt(0) === '"') {
				cookie = cookie.slice(1, -1);
			}

			try {
				var name = parts[0].replace(rdecode, decodeURIComponent);

				// fn_log("search key==>>[" + key + "] , cookie name==>>[" + name +"] , cookie val==>>[" + cookie + "]");

				// cookie = converter.read ?
				// 	converter.read(cookie, name) : converter(cookie, name) ||
				// 	cookie.replace(rdecode, decodeURIComponent);

				if (this.json) {
					try {
						cookie = JSON.parse(cookie);
					} catch (e) {}
				}

				if (key === name) {
					result = cookie;
					break;
				}

				if (!key) {
					result[name] = cookie;
				}
			} catch (e) {}
		}


        // fn_log("cookie result1==>>[" + result +"]");

		return result;
	}

    /**
     * 쿠키 설정
     * @param cookieName 쿠키명
     * @param cookieValue 쿠키값
     * @param expireDay 쿠키 유효날짜
     */
    fn_setCookie = function(key, value, attributes) {
        var result;
        if (typeof document === 'undefined') {
            return;
        }

        // Write

        if (arguments.length > 1) {

            attributes = $.extend({}, {
                path: '/'
            }, attributes);

            // attributes = extend({
            //     path: '/'
            // }, api.defaults, attributes);

            if (typeof attributes.expires === 'number') {
                var expires = new Date();
                expires.setMilliseconds(expires.getMilliseconds() + attributes.expires * 864e+5);
                attributes.expires = expires;
            }

            try {
                result = JSON.stringify(value);
                if (/^[\{\[]/.test(result)) {
                    value = result;
                }
            } catch (e) {
            }

            // if (!converter.write) {
            //     value = encodeURIComponent(String(value))
            //         .replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g, decodeURIComponent);
            // } else {
            //     value = converter.write(value, key);
            // }

            key = encodeURIComponent(String(key));
            key = key.replace(/%(23|24|26|2B|5E|60|7C)/g, decodeURIComponent);
            key = key.replace(/[\(\)]/g, escape);

            return (document.cookie = [
                key, '=', value,
                attributes.expires ? '; expires=' + attributes.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                attributes.path ? '; path=' + attributes.path : '',
                attributes.domain ? '; domain=' + attributes.domain : '',
                attributes.secure ? '; secure' : ''
            ].join(''));
        }
    }

    /**
     * 쿠키 삭제
     * @param cookieName 삭제할 쿠키명
     */
    fn_delCookie = function( cookieName ){
        var expireDate = new Date();

        //어제 날짜를 쿠키 소멸 날짜로 설정한다.
        expireDate.setDate( expireDate.getDate() - 1 );
        document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
    }

    /**
     * Camel 표기법으로 변환.
     * UTILE.fn_camelCase('d_day_0');
     * @param str1
     */
    fn_camelCase = function( str1 ){
        var camelCase = (function () {
            var DEFAULT_REGEX = /[-_]+(.)?/g;

            function toUpper(match, group1) {
                return group1 ? group1.toUpperCase() : '';
            }

            return function (str, delimiters) {
                return str.replace(delimiters ? new RegExp('[' + delimiters + ']+(.)', 'g') : DEFAULT_REGEX, toUpper);
            };
        })();
        return camelCase( str1 );
    }

    fn_getMin = function(){
    	var date = new Date();
    	var tm = date.getMinutes().toString();
    /*	//console.log("length"+tm.length)
    	//console.log("분" + tm.length < 2 ? '0'+tm : tm)*/
    	return tm.length < 2 ? '0'+tm : tm;
    }

    fn_getSec = function(){
    	var date = new Date();
    	var tm = date.getSeconds().toString();
    	//console.log("length"+tm.length)
        return tm.length < 2 ? '0'+tm : tm;
    }
    
    fn_getTimeSec = function(){
    	var date = new Date();
    	
    	var hour = date.getHours();
    	var minute = date.getMinutes();
    	var second = date.getSeconds();
    	
    	var ts = ((hour * 60)*60) + (minute*60) + second; //현재시간을 초로변환
    	
    	return ts;
    	
    }

    /**
     * 공통팝업 호출
     * @param sTitle : 타이틀
     * @param sUrl  : 호출할 url
     * @param iWidth : 팝업 넓이
     * @param iHeight : 팝업 높이
     * @param sPosition	: 팝업 위치( CENTER 또는 center 일 경우에만 팝업을 가운데로 위치)
     * @returns
     */
    fn_openPopup = function(sTitle, sUrl, iWidth, iHeight, sPosition){
        if(sTitle==undefined || sTitle=="" || sTitle==null) sTitle = "Popup";
        if(sUrl==undefined || sUrl=="" || sTitle==null){
            alert("호출할 페이지 주소가 없습니다.");
            return;
        }
        if(iWidth==undefined || iWidth=="" || iWidth==null) iWidth = "500";
        if(iHeight==undefined || iHeight=="" || iHeight==null) iHeight = "500";
        if(sPosition==undefined || sPosition=="" || sPosition==null) sPosition = "";

        //sTitle 특수문자 제거
        var specialLetter = "=,.:;{}[]()<>?_|~`!@#$%^&*-+\"'\\/"; //특수문자 정의.
        var newTitle = sTitle;
        var testValue;
        var testIndex;
        for(var i=0; i<sTitle.length; i++){
            testValue = sTitle.charAt(i);
            testIndex = specialLetter.indexOf(testValue);

            if(-1 != testIndex)newTitle = newTitle.replace(testValue, "");
        }

        var tmp = sUrl.split("?");
        var iScrollbars = 'no';
        var popUrl = ((tmp.length<=1) ? tmp[0] :  "");
        var popStatus = "toolbar=no, location=no, status=no, menubar=no, scrollbars="+iScrollbars+", resizeble=no, width="+iWidth+"px, height="+iHeight+"px";

        // 팝업창을 가운데로 위치
        if(sPosition=="CENTER" || sPosition=="center"){
            var top =  (screen.height - iHeight) / 2;
            var left = (screen.width - iWidth) / 2;
            popStatus = popStatus + ", top=" + top + "px, left=" + left + "px";
        }


        var	popTitle = newTitle.replace(/ /gi, "");

        //팝업창 오픈
        window.open(popUrl, popTitle, popStatus);

        // 팝업화면에 넘겨줄 값이 있을 경우
        if(tmp.length>1){
            //동적으로 생성할 form 생성

            $("#popupForm").remove();

            //var $form  = $("#popupForm");

            //if($form.length < 1) {
            var $form = $("<form id='popupForm', method='post', action='"+tmp[0]+"' target='"+popTitle+"'></form>");
            $(document.body).append($form);
            //}
            //이전에 처리한 정보 삭제
            //$form.empty();

            //정보 세팅
            var fld = tmp[1].split("&");
            for(var i=0; i<fld.length; i++){
                var col = fld[i].split("=");
                $("<input></input>").attr({type:"hidden", name:col[0],  value:$.trim(col[1])}).appendTo($form);
            }

            $form.submit();
        }
    }


	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------

    _mod_content.fn_isObject     			= fn_isObject;
    _mod_content.fn_isObjectInstanceof     	= fn_isObjectInstanceof;
    _mod_content.fn_isObjectTypeof     		= fn_isObjectTypeof;

	_mod_content.fn_isBlank     = fn_isBlank;
    _mod_content.fn_isNotBlank 	= fn_isNotBlank;
	_mod_content.fn_defaultIfBlank     = fn_defaultIfBlank;
	_mod_content.fn_log     = fn_log;
	_mod_content.fn_log2     = fn_log2;
	_mod_content.fn_alert     = fn_alert;
    _mod_content.fn_objToStringLog     = fn_objToStringLog;
    _mod_content.fn_objToStringLog2     = fn_objToStringLog2;
	_mod_content.fn_confirm     = fn_confirm;
	_mod_content.fn_ajax     = fn_ajax;
	_mod_content.fn_ajax4confirm     = fn_ajax4confirm;
    _mod_content.fn_pollingCheck = fn_pollingCheck;

    _mod_content.fn_getTime_hhmmss = fn_getTime_hhmmss;
    _mod_content.fn_autoLogout_init = fn_autoLogout_init;
    _mod_content.fn_autoLogout_reset = fn_autoLogout_reset;
    _mod_content.fn_camelCase = fn_camelCase;
    _mod_content.fn_openPopup = fn_openPopup;
    _mod_content.fn_getTimeSec = fn_getTimeSec;

	return _mod_content;

}(UNI.UTILE || {}, jQuery));