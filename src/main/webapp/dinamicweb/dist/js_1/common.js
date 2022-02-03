//select box arrow 
$(function () {
    $('select').selectBox();
})
//sticky
function sticky_relocate() {
    var window_top = $(window).scrollTop();
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
    if ($(submenu).is(':hidden')) {
        $(submenu).slideDown(0);
       
    } else {
        $(submenu).slideUp(0);
    }
});
$('.nav > li > .sub_menu_wrap').parent().hover(function () {
    $(this).children().css('color', '#5873ad');
}, function () {
    $(this).children().css('color', '');
});

//Path menu
$('.navlist li > a').click(function () {
    $(this).parent().siblings().find('ul').fadeOut(0);
    $(this).next('ul').stop(true, false, true).fadeToggle(0);
    $(this).parent().parent().find('li').siblings().removeClass('navon');
    $(this).parent().addClass('navon');
    //return false;
});
$(document).click(function () {
    $('.navlist li > a').parent().siblings().find('ul').fadeOut(0);
    $('.navlist li > a').parent().removeClass('navon');
    //return false;
});
$(document).ready(function () {
    $('.depon').hover(
        function () {
            $(this).children('.navdep3').slideDown(0);
        },
        function () {
            $(this).children('.navdep3').slideUp(0);
        }
    );
});
//dialog
$(function () {
    $("#dialog").dialog();
});