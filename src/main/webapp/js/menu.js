//select box arrow 
$(function () {
//    $('select').selectBox();
})
//sticky
function sticky_relocate() {
    var window_top = $(window).scrollTop();
    
//    alert("$('#sticky-anchor').offset() : " + $('#sticky-anchor').offset());
    if ($('#sticky-anchor').offset() === undefined) return;
    
    var div_top = $('#sticky-anchor').offset().top;
    
    if (window_top > div_top) {
        $('#sticky').addClass('stick');
        $('#sticky-anchor').height($('#sticky').outerHeight());
    } else {
        $('#sticky').removeClass('stick');
        $('#sticky-anchor').height(0);
    }
}
$(function () {
    $(window).scroll(sticky_relocate);
    sticky_relocate();
});
var dir = 1;
var MIN_TOP = 200;
var MAX_TOP = 350;

function autoscroll() {
    var window_top = $(window).scrollTop() + dir;
    if (window_top >= MAX_TOP) {
        window_top = MAX_TOP;
        dir = -1;
    } else if (window_top <= MIN_TOP) {
        window_top = MIN_TOP;
        dir = 1;
    }
    $(window).scrollTop(window_top);
    window.setTimeout(autoscroll, 100);
}

//sub menu
$('.nav > li > .sub_menu_wrap').parent().hover(function () {
    var submenu = $(this).children('.sub_menu_wrap');    
    if (topMenuCloseYn == "Y") {
    	topMenuCloseYn = "N";
    	return;
    }
  
    $(submenu).slideDown(0);
},function(){
	$('.nav > li > .sub_menu_wrap').slideUp(0);
});


	

//$('.nav > li > .sub_menu_wrap').parent().hover(function () {
//    $(this).children().css('color', '#5873ad');
//    //$(this).children().css('border-bottom', '3px solid #5873ad');
//    //$(this).children().css('padding-bottom', '5px');
//}, function () {
//    $(this).children().css('color', '');
//    //$(this).children().css('border-bottom', '');
//    //$(this).children().css('padding-bottom', '');
//
//});


$('.sub_menu_wrap > a').click(function () {
    $(this).parent().css('display','none');
});
//$('.nav > li > .sub_menu_wrap').parent().hover(function () {
//    if ($('nav > li').hasClass('hidden')) {
//        $(document).removeClass('hidden');
//    }
//});



//Path menu
$('.navlist li > a').click(function () {
    $(this).parent().siblings().find('ul').fadeOut(0);
    $(this).next('ul').stop(true, false, true).fadeToggle(0);
    $(this).parent().parent().find('li').siblings().removeClass('navon');
    $(this).parent().addClass('navon');
    return false;
});

//Path menu
$('.navlist li').hover(
	function () {
		$('.nav > li > .sub_menu_wrap').slideUp(0);
	},
	function () {
		//$('.nav > li > .sub_menu_wrap').slideUp(0);
	}
);


/*$(document).click(function () {
    $('.navlist li > a').parent().siblings().find('ul').fadeOut(0);
    $('.navlist li > a').parent().removeClass('navon');
    return false;
});*/
$(document).ready(function () {
    $('.depon').hover(
        function () {
//        	$('.nav > li > .sub_menu_wrap').slideUp(0);
        	$(this).children('.navdep2').slideDown(200);
            $(this).children('.navdep3').slideDown(0);
        },
        function () {
//        	$('.nav > li > .sub_menu_wrap').slideUp(0);
        	$(this).children('.navdep2').slideDown(200);
            $(this).children('.navdep3').slideUp(0);
        }
    );
});

//Path menu
/*$(document).ready(function () {
    $('.navon').hover(
        function () {
            $(this).children('.navdep2').slideDown(200);
            $(this).children('.navdep3').slideDown(200);
        },
        function () {
            $(this).children('.navdep2').slideUp(200);
            $(this).children('.navdep3').slideUp(200);            
        }
    );
});*/
//bookmark

//4dept + - 버튼 클릭
function fn_4deptOpen(menuid) {	
	if ($("#id_"+menuid).css("display") == "none") {	
		$("#id_"+menuid).show();
		$("#img_"+menuid).attr('src','/images/common/ico_minus.png');
	} else {
		$("#id_"+menuid).hide();
		$("#img_"+menuid).attr('src','/images/common/ico_plus.png');
	}
}

//tabMenum
$("ul.tabs li").click(function () {
	$("ul.tabs li").removeClass("on");
	$(this).addClass("on");
	$(".tab_content").hide();
	
	var actionTab = $(this).attr("rel");	
	$("#"+actionTab).show();

	var arr = actionTab.split("_");   // arr[1] : tab_id, arr[2] : menu_id, arr[3] : ubseq
	$("#frm_tab").find("#tab_id").val(arr[1]);      // 탭id
	
//	COMMON.fn_tabMenu('gridMain', 'frm_main', 'frm_tab', arr[1], arr[2], arr[3]);
	COMMON.fn_tabMenu('gridMain', 'frm_main', 'frm_tab', arr[1], arr[3]);
	
});

//개통신청 화면 tabMenum
$("ul.svcTabs li").click(function () {
	$("ul.svcTabs li").removeClass("on");
	$(this).addClass("on");
	$(".svc_tab_content").hide();
	
	var actionTab = $(this).attr("rel");
	$("#"+actionTab).show();
	
	SVCMGR.fn_tab_click("frm_reqst", actionTab);		
});
