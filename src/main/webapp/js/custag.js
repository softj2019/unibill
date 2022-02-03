/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.CUSTAG = (function(_mod_custag, $, undefined){
	//selectbox 공통
	fn_selectbox = function(s_type, s_id){
		var type = s_type;
		var sendData = {"type":type};
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/excontent/getGrpType.json',
			data: sendData,
			async: false ,
			success : function(data){
				$("#"+s_id).empty();
				for(var i = 0; i < data.typeList.length; i++){
					var code = data.typeList[i]["code"];
					var name = data.typeList[i]["name"];
					var option = $("<option value='"+code+"'>"+name+"</option>");
	                $("#"+s_id).append(option);
				}
			}
	 	});
	}

	//아이디 중복체크
	fn_checkId = function(s_id){
		var id = $("#"+s_id).val();
		if(id == "" || id == "undefinded" || id == null){
			$.unibillDialog.alert('경고', '아이디를 입력해주세요.');
			return;
		}
		var sendData = {"id":id};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/excontent/getId.json',
			data: sendData,
			async: false ,
			success : function(data){
				if(data.checkId == true){
					$.unibillDialog.alert('경고', '이미 등록된 아이디입니다.');
				}else if(data.checkId == false){
					$.unibillDialog.alert('알림', '사용할 수 있는 아이디입니다.');
				}
			}
	 	});
	}
	
	//캘린더(최대 1년)
	fn_datepicker = function(s_date, e_date){
		$("#"+s_date).datepicker({
			dateFormat: 'yy-mm-dd' //달력 날짜 형태
           ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
           ,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
           ,changeYear: true //option값 년 선택 가능
           ,changeMonth: true //option값  월 선택 가능                
           ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
           ,buttonImage: "/images/common/btn_calendar.png" //버튼 이미지 경로
           ,buttonImageOnly: true //버튼 이미지만 깔끔하게 보이게함
           ,buttonText: "선택" //버튼 호버 텍스트              
           ,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
           ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
           ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
           ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
           ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
           ,onSelect: function (date) {
   			var endDate = $("#"+e_date);
   			var startDate = $(this).datepicker('getDate');
   			var minDate = $(this).datepicker('getDate');
   			endDate.datepicker('option', 'maxDate', startDate);
   			endDate.datepicker('option', 'minDate', minDate);
   		}
		});
		
		$("#"+e_date).datepicker({
			dateFormat: 'yy-mm-dd' //달력 날짜 형태
           ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
           ,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
           ,changeYear: true //option값 년 선택 가능
           ,changeMonth: true //option값  월 선택 가능                
           ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
           ,buttonImage: "/images/common/btn_calendar.png" //버튼 이미지 경로
           ,buttonImageOnly: true //버튼 이미지만 깔끔하게 보이게함
           ,buttonText: "선택" //버튼 호버 텍스트              
           ,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
           ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
           ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
           ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
           ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
           ,beforeShow: function(){
        	   var max_date  = $("#"+s_date).datepicker('getDate');
       	       max_date.setDate(max_date.getDate()+365); // 가져온시작일에서 +7일추가
       	       return {maxDate: max_date};
           }
		});
	}
	
	//듀얼 모니터 고려한 팝업창 띄우기
	fn_openPop = function(width,height,url){
		var nWidth = width;
		var nHeight = height;
		  
		var curX = window.screenLeft;
		var curY = window.screenTop;
		var curWidth = document.body.clientWidth;
		var curHeight = document.body.clientHeight;
		  
		var nLeft = curX + (curWidth / 2) - (nWidth / 2);
		var nTop = curY + (curHeight / 2) - (nHeight / 2);

		var strOption = "";
		strOption += "left=" + nLeft + "px,";
		strOption += "top=" + nTop + "px,";
		strOption += "width=" + nWidth + "px,";
		strOption += "height=" + nHeight + "px,";
		strOption += "toolbar=no,menubar=no,location=no,";
		strOption += "resizable=yes,status=yes";
		
		window.open(url,'new', strOption);
	    return false;
	}
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_custag.fn_selectbox					= fn_selectbox;
	_mod_custag.fn_checkId 						= fn_checkId;
	_mod_custag.fn_datepicker 					= fn_datepicker;
	_mod_custag.fn_openPop						= fn_openPop;
	return _mod_custag;

}(UNI.CUSTAG || {}, jQuery));