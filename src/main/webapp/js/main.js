/**
 *  메인화면에 필요한 이벤트
 * @namespace {Object} UNI.MAIN
 */
UNI.MAIN = (function(_mod_main, $, undefined){
	
fn_notice_onload = function(_sUbseq) {
		
		// 팝업 show
		var tx = ($(window).width() -  1200) / 2;
		var ty = ($(window).height() - 550) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_main_notice_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_main_notice_pop").show();

		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/main/selectNoticeDetail.json',
			data: 'ubseq=' + _sUbseq,
			datatype : "json",
			success : function(data) {

				var title = data.rows[0]["title"];
				var regTm = data.rows[0]["regTm"];
				var msg   = data.rows[0]["msg"];

				if (msg != undefined) {
					msg = msg.split("&lt;").join("<");
					msg = msg.split("&gt;").join(">");
					msg = msg.split("<br>").join("\n");
				} else {
					msg = "";
				}				

				$("#main_notice_title").text(title);
				$("#main_notice_regTm").text(regTm);
				$("#main_notice_msg").html(msg);

			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				result = false;
			}
		});
		
		
	}

	
	/**
	 * @method
	 * @description 공지사항 상세보기
	 * @author 홍길동
	 * @param
	 */
	fn_notice_view = function(_sUbseq) {
		
		// 팝업 show
		var tx = ($(window).width() -  1200) / 2;
		var ty = ($(window).height() - 550) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_main_notice_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_main_notice_pop").show();

		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/main/selectNoticeDetail.json',
			data: 'ubseq=' + _sUbseq,
			datatype : "json",
			success : function(data) {

				var title = data.rows[0]["title"];
				var regTm = data.rows[0]["regTm"];
				var msg   = data.rows[0]["msg"];

				if (msg != undefined) {
					msg = msg.split("&lt;").join("<");
					msg = msg.split("&gt;").join(">");
					msg = msg.split("<br>").join("\n");
				} else {
					msg = "";
				}				

				$("#main_notice_title").text(title);
				$("#main_notice_regTm").text(regTm);
				$("#main_notice_msg").html(msg);

			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				result = false;
			}
		});
		
		
	}
	

	/**
	 * @method
	 * @description 신청 상세보기(수정필요)
	 * @author 홍길동
	 * @param
	 */
	fn_od_view = function(_sUbseq) {
		
		// 팝업 show
		var tx = ($(window).width() -  1200) / 2;
		var ty = ($(window).height() - 550) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_main_notice_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_main_notice_pop").show();

		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/main/selectNoticeDetail.json',
			data: 'ubseq=' + _sUbseq,
			datatype : "json",
			success : function(data) {

				var title = data.rows[0]["title"];
				var regTm = data.rows[0]["regTm"];
				var msg   = data.rows[0]["msg"];

				if (msg != undefined) {
					msg = msg.split("&lt;").join("<");
					msg = msg.split("&gt;").join(">");
					msg = msg.split("<br>").join("\n");
				} else {
					msg = "";
				}				

				$("#main_notice_title").text(title);
				$("#main_notice_regTm").text(regTm);
				$("#main_notice_msg").html(msg);

			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				result = false;
			}
		});
		
		
	}
	

	/**
	 * @method
	 * @description 계약 상세보기(수정필요)
	 * @author 홍길동
	 * @param
	 */
	fn_cont_view = function(_sUbseq) {
		
		// 팝업 show
		var tx = ($(window).width() -  1200) / 2;
		var ty = ($(window).height() - 550) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_main_notice_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_main_notice_pop").show();

		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/main/selectNoticeDetail.json',
			data: 'ubseq=' + _sUbseq,
			datatype : "json",
			success : function(data) {

				var title = data.rows[0]["title"];
				var regTm = data.rows[0]["regTm"];
				var msg   = data.rows[0]["msg"];

				if (msg != undefined) {
					msg = msg.split("&lt;").join("<");
					msg = msg.split("&gt;").join(">");
					msg = msg.split("<br>").join("\n");
				} else {
					msg = "";
				}				

				$("#main_notice_title").text(title);
				$("#main_notice_regTm").text(regTm);
				$("#main_notice_msg").html(msg);

			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				result = false;
			}
		});
		
		
	}
	
	/**
	 * @method
	 * @description FAQ 상세보기
	 * @author 홍길동
	 * @param
	 */
	fn_faq_view = function(_sUbseq) {
	//alert("fn_faq_view> _sUbseq="+_sUbseq);
	// 팝업 show
	var tx = ($(window).width() -  1200) / 2;
	var ty = ($(window).height() - 550) / 2;
	if(ty < 0){
		ty = 15;
	}
	
	$("#ly_main_faq_pop2 .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
	$("#ly_main_faq_pop2").show();

	$.ajax({
		async: false,
		type: 'POST',
		url: CONTEXT_ROOT + '/main/selectFaqDetail.json',
		data: 'ubseq=' + _sUbseq,
		datatype : "json",
		success : function(data) {

			var	title = data.rows[0]["title"];
			var msg   = data.rows[0]["msg"];
			var	qseq = data.rows[0]["qseq"];
			var	regTm = data.rows[0]["regTm"];
			

			if (msg != undefined) {
				msg = msg.split("&lt;").join("<");
				msg = msg.split("&gt;").join(">");
				msg = msg.split("<br>").join("\n");
			} else {
				msg = "";
			}				

			$("#main_faq_title").text(title);
			$("#main_faq_regTm").text(regTm);
			$("#main_faq_msg").html(msg);

		},
		error: function(xhr, status, error) {
			$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			result = false;
		}
	});
		
	}
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_main.fn_notice_view      	= fn_notice_view;
	_mod_main.fn_faq_view      		= fn_faq_view;
	_mod_main.fn_od_view        	= fn_od_view;
	_mod_main.fn_cont_view      	= fn_cont_view;
	
	return _mod_main;

}(UNI.MAIN || {}, jQuery));