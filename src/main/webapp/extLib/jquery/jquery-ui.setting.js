jQuery(function($) {
	// Korean
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd', firstDay: 0,
		isRTL: false		
		/*beforeShow:function(input, inst) {
			alert("input : " + input + "\ninst : " + inst + "\ninst.id : " + inst.id);			
		},
		onSelect:function(dateText, inst){
			alert("dateText : " + dateText + "\ninst : " + inst + "\ninst.id : " + inst.id);
			$(this).val(dateText);
			
		}*/
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	// Configurations
	$.datepicker.setDefaults({
		showOn: 'both',
		buttonImageOnly: true,
		buttonImage: '../images/common/btn_calendar.png',
		buttonText: '달력',
		changeYear: true,
		changeMonth: true,
		showMonthAfterYear: true
	});
    // 연도범위
	$(function () {

		// 기본형식의 달력 연도 범위(현재 년도 기준으로 => -100년 ~ 현재년도 ~ +10년)
	    $('.datepicker').datepicker({
	        yearRange: "-100:+10"
	    });
	});

	// 년도월 선택 달력
	$(".monthPicker").datepicker({
		closeText: '닫기',
		currentText: '적용',
        dateFormat: 'yymm',
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
 
        onClose: function(dateText, inst) {
//            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
//            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
//            $(this).val($.datepicker.formatDate('yymm', new Date(year, month, 1)));
        }
    });
	
	// 적용버튼 클릭시
	jQuery.datepicker._gotoToday = function(id) {
    	
    	var target = jQuery(id);
    	var inst = this._getInst(target[0]);		        	
    	
		var year  = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();

        $("#"+inst.id).val($.datepicker.formatDate('yymm', new Date(year, month, 1)));			        
        this._hideDatepicker();
        
    };

    $(".monthPicker").focus(function () {
        $(".ui-datepicker-calendar").hide();
        $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
        });
    });

});