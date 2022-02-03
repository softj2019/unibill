/*
* jQuery unibillDialog plugin
* 
* 사용법 : 
* 1. alert
* - $.unibillDialog.alert(title, message, callback);
* 
* 2. confirm
* - $.unibillDialog.confirm(title, message, callback);
* 
* 3. title 
* - 알림, 확인, 경고, 오류 등...
* 
* 4. callback
* function callback(isTrue){
* 	if(isTrue){
* 		
* 	}else{
* 
* 	}
* }
* 
*/

(function($) {
	$.fn.unibillDialog = function() {
		 return this.each();
	}
	
	$.unibillDialog={};
	
	$.unibillDialog.alert = function(title, message, callBack, _width, _height) {
    	//alert("width : " + _width + ", height : " + _height);
		if(typeof width == "undefined"){
			var len = message.indexOf("<br>",0);
			if(len<0) len = message.length;    	//		
			var width  = len*14 + 20; //pjh popup
			if(width>600) width =600;	//
		}else{
			width = _width;
		}
		
    	//alert("unibillDialog> width="+width+" message="+message);
		if(typeof width == "undefined"){
			var height = 200;
		}else{
			height = _height;
		}
		
    	var cl = "t_center";
    	/*
    	if(message.length > 70){
    		cl = "t_left";
    	}*/
    	var $dialog = $('<div id="dialog" title='+title+'><p class="'+ cl +'">'+message+'</p></div>');

    	$dialog.dialog({
			width:width,
    		height:height,
    		bgiframe: true,
			modal: true,
			zindex:1000000,  /*상세팝업(popup.css) css z-index:9999 에서 z-index:99 로 변경*/
			buttons: 
			{
				"확인": function() {					
					$( this ).dialog( "close" );
					if(callBack) callBack();
				}
			},
			open: function() { 
//		        $(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
				$("#open-button").focus();
		    } 
		});
		
		$dialog.dialog('open');		

	};
	
	$.unibillDialog.confirm = function(title, message, callBack) {
    	
    	var width  = 400;
    	var height = 300;
    	
    	var $dialog = $('<div id="dialog" title='+title+'><p class="t_center">'+message+'</p></div>');

    	$dialog.dialog({
			width:width,
    		/*height:height,*/
    		bgiframe: true,
			modal: true,
			zindex:1000000,  /*상세팝업(popup.css) css z-index:9999 에서 z-index:99 로 변경*/
			buttons: 
			{
				"확인": function() {
					$( this ).dialog( "close" );
					if(callBack) callBack();
				},
				"취소": function() {
					$( this ).dialog( "close" );
				}
			},
			open: function() { 
//		        $(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
				$("#open-button").focus();
		    } 
		});
		
		$dialog.dialog('open');		

	};
	
})(jQuery);