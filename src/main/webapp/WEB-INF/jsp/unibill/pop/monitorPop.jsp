<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<link rel="stylesheet" type="text/css" href="../../css/monitor/css/style.css" />
<script src="../../js/chart/js/Chart.bundle.js"></script>
<script src="../../js/chart/js/utils.js"></script>
<script src="../../js/chart/js/jquery.min.js" ></script>
<script src="../../js/chart/js/chart.js" ></script>

<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery-1.11.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery-ui-1.11.4/jquery-ui.min.js'/>"></script>	
<script type="text/javascript" src="<c:url value='../../extLib/jquery/jquery.form.js'/>"></script>

<script type="text/javascript" src="<c:url value='../../extLib/jquery/fullscrin/jquery.fullscreen-min.js'/>"></script>
<script type="text/javascript" src="<c:url value='../../extLib/jquery/fullscrin/jquery.fullscreen.js'/>"></script>


<script type="text/javascript">

//window.setTimeout('window.location.reload()',10000); //60초마다 새로고침

function monitorTimmer(){
	var url = CONTEXT_ROOT + "/monitor/selectMonitor.do";
	var $form = $('<form></form>');
	$form.attr('action', url);
	$form.attr('method', 'post');
	$form.attr('target', 'MONITOR_POPUP');
	$form.appendTo('body');
	
	$form.submit();
}


$(document).ready(function() {
	
	fullscreenResize();
	
	$(window).resize(function() {
		//alert("3333 : " +window.innerWidth);
	 }); 
	
	document.onkeydown = fullevent; 
	
	setInterval("monitorTimmer();", 60000);

	chartStart();
	
	// 스크롤이 생기면 스크롤 값 만큼 위드값 감소
	if($("#tong").hasScrollBar()){
		$("#tong").attr('class','t_scroll_on');
	}else{
		$("#tong").attr('class','t_scroll');
	}
	
	if($("#alarm").hasScrollBar()){
		$("#alarm").attr('class','t_scroll_on');
	}else{
		$("#alarm").attr('class','t_scroll');
	}
	
	// 풀스크린 버튼 클릭시
	$('#widebtn').click(function() {
		var agent = navigator.userAgent.toLowerCase();
		if (agent.indexOf("chrome") != -1) {
			cromFullscreen();
		}else{
			fullScreenButton();	
		}
		return false;
	});
	
	
	
});


function fullevent(){
	var keycode = event.keyCode;
	if(122 == keycode){ 
		event.keyCode = 0; 
		event.cancelBubble = true;  
		event.returnValue = false; 
		return false; 
	} //F11 키코드
	
}


function fullscreenResize(){
	//alert("111 : " + $.fullscreen.isFullScreen());
	//alert(screen.width + " : " + window.innerWidth + " : " + screen.height + " : " + window.innerHeight);
	if(screen.width === window.innerWidth && screen.height === window.innerHeight){
		//fullscrin
		$("body").css("overflow-x" , "hidden");
		$("body").css("overflow-y" , "hidden");
		$(".mwarp").css("width",screen.width);
		$(".mwarp").css("height",screen.height);
		
		$("#widebtn").attr("class","minibtn");
		
	} else {
		$("body").css("overflow-x" , "auto");
		$("body").css("overflow-y" , "auto");
		if(window.innerWidth < 1815){
			$(".mwarp").css("width",1815);
		}else{
			$(".mwarp").css("width",window.innerWidth);	
		}
		$(".mwarp").css("height",1080);
		$("#widebtn").attr("class","widebtn");
		
	}
	
	
}

function fullScreenButton(){
	
	//alert("button : " + screen.width + " : " + window.innerWidth + " : " + screen.height + " : " + window.innerHeight);
	if(screen.width === window.innerWidth && screen.height === window.innerHeight){
		//fullscrin
		
		$.fullscreen.exit();
		$("body").css("overflow-x" , "auto");
		$("body").css("overflow-y" , "auto");
		$(".mwarp").css("width",screen.width);
		$(".mwarp").css("height",1080);
		
		$("#widebtn").attr("class","widebtn");
		
	} else {
		
		$('#mwarp').fullscreen();
		$("body").css("overflow-x" , "hidden");
		$("body").css("overflow-y" , "hidden");
		$(".mwarp").css("width",screen.width);
		$(".mwarp").css("height",screen.height);
		
		$("#widebtn").attr("class","minibtn");
		
		
	}
	
}

function cromFullscreen() {
	  if (!document.fullscreenElement &&    // alternative standard method
		      !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement ) {  // current working methods
		    if (document.documentElement.requestFullscreen) {
		      document.documentElement.requestFullscreen();
		    } else if (document.documentElement.msRequestFullscreen) {
		      document.documentElement.msRequestFullscreen();
		    } else if (document.documentElement.mozRequestFullScreen) {
		      document.documentElement.mozRequestFullScreen();
		    } else if (document.documentElement.webkitRequestFullscreen) {
		      document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
		    }
		    $("#widebtn").attr("class","minibtn");
		    
		  } else {
		    if (document.exitFullscreen) {
		      document.exitFullscreen();
		    } else if (document.msExitFullscreen) {
		      document.msExitFullscreen();
		    } else if (document.mozCancelFullScreen) {
		      document.mozCancelFullScreen();
		    } else if (document.webkitExitFullscreen) {
		      document.webkitExitFullscreen();
		    }
		    $("#widebtn").attr("class","widebtn");
		  }
}


$.fn.hasScrollBar = function() {
    return (this.prop("scrollHeight") == 0 && this.prop("clientHeight") == 0)
            || (this.prop("scrollHeight") > this.prop("clientHeight"));
};



function chartStart(){
	/* 전월대비건수  */
	var ctx1 = document.getElementById('billingdonutCanvas1').getContext('2d');
	window.myDoughnut1 = new Chart(ctx1, doughnut1());
	
	//파이차트 가운데 글자셋팅
	pieChartTxtCenter();
	
	/* 전월대비요금 */
	var ctx2 = document.getElementById('billingdonutCanvas2').getContext('2d');
	window.myDoughnut2 = new Chart(ctx2, doughnut2());
	/* 월별현황 */
	var ctx3 = document.getElementById('monthcurCanvas').getContext('2d');
	window.myLine = new Chart(ctx3, monthcurconfig());
	/* 일별현황 */
	var ctx4 = document.getElementById('daycurCanvas').getContext('2d');
	window.myLine2 = new Chart(ctx4, daycurconfig());
	
	
	/* 당일호종류별건수  */
	var ctx5 = document.getElementById('daybtypeCanvas').getContext('2d');
	window.myDoughnut3 = new Chart(ctx5, dayBtypeDoughnut()); 
	/* 당일비과금건수  */
	var ctx6 = document.getElementById('daynobillCanvas').getContext('2d');
	window.myDoughnut4 = new Chart(ctx6, dayNobillDoughnut()); 
	/* 당일호검사건수  */
	var ctx7 = document.getElementById('daycheckCanvas').getContext('2d');
	window.myDoughnut5 = new Chart(ctx7, dayCheckDoughnut());
	
	
	/* 시스템현황  */
	var ctx8 = document.getElementById('systemCanvas').getContext('2d');
	window.mybar = new Chart(ctx8, systemBar());
	/* 디스크사용현황  */
	var ctx9 = document.getElementById('diskCanvas').getContext('2d');
	window.mybar2 = new Chart(ctx9, diskBar());
	/* 네트워크 트래픽  */
	var ctx10 = document.getElementById('networkCanvas').getContext('2d');
	window.myLine3 = new Chart(ctx10, networkBar());
	
	/* 당일호종류별건수  */
	//dayBtypeChart();
	/* 당일비과금건수  */
	//dayNoBillChart();
	/* 당일호검사건수  */
	//daycheckChart();
}

function pieChartTxtCenter(){
	
	   Chart.pluginService.register({
	        beforeDraw: function (chart) {
	        	if (chart.config.options.elements.center) {
	           //Get ctx from string
	           
	           var ctx = chart.chart.ctx;
	           
	           if(ctx.canvas.id == "billingdonutCanvas1" || ctx.canvas.id == "billingdonutCanvas2"){
	        	   
	        	   //Get options from the center object in options
	                var centerConfig = chart.config.options.elements.center;
	                var fontStyle = centerConfig.fontStyle || 'Arial';
	                
	              	var txt1 = centerConfig.text1;
	              	var txt2 = centerConfig.text2;
	                var color = centerConfig.color || '#000';
	                var sidePadding = centerConfig.sidePadding || 20;
	                var sidePaddingCalculated = (sidePadding/100) * (chart.innerRadius * 2)
	                //Start with a base font of 30px
	                
	               //Get the width of the string and also the width of the element minus 10 to give it 5px side padding
	                var stringWidth = ctx.measureText(txt1).width;
	                var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;
	                
	                // Find out how much the font can grow in width.
	                var widthRatio = elementWidth / stringWidth;
	                var newFontSize = Math.floor(30 * widthRatio);
	                var elementHeight = (chart.innerRadius * 2);
	                
	                // Pick a new font size so it will not be larger than the height of label.
	                var fontSizeToUse = Math.min(newFontSize, elementHeight);
	                
	               //Set font settings to draw it correctly.
	                ctx.textAlign = 'center';
	                ctx.textBaseline = 'middle';
	                var centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
	                var centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);
	                ctx.font = fontSizeToUse+"px " + fontStyle;
	                ctx.fillStyle = color;
	                
	                //Draw text in center
	                ctx.font = "25px " + fontStyle;
	                ctx.fillText(txt1, centerX +5, centerY-3);
	                ctx.font = "13px " + fontStyle;
	                ctx.fillText(txt2, centerX, centerY+30);
	                
	           }else if(ctx.canvas.id == "daybtypeCanvas" || ctx.canvas.id == "daynobillCanvas" || ctx.canvas.id == "daycheckCanvas"){
	        	   var centerConfig = chart.config.options.elements.center;
	                var fontStyle = centerConfig.fontStyle || 'Arial';
	                
	              	var txt1 = centerConfig.text1;
	              	var txt2 = centerConfig.text2;
	                var color = centerConfig.color || '#000';
	                var sidePadding = centerConfig.sidePadding || 20;
	                var sidePaddingCalculated = (sidePadding/100) * (chart.innerRadius * 2)
	                //Start with a base font of 30px
	                
	               //Get the width of the string and also the width of the element minus 10 to give it 5px side padding
	                var stringWidth = ctx.measureText(txt1).width;
	                var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;
	                
	                // Find out how much the font can grow in width.
	                var widthRatio = elementWidth / stringWidth;
	                var newFontSize = Math.floor(30 * widthRatio);
	                var elementHeight = (chart.innerRadius * 2);
	                
	                // Pick a new font size so it will not be larger than the height of label.
	                var fontSizeToUse = Math.min(newFontSize, elementHeight);
	                
	               //Set font settings to draw it correctly.
	                ctx.textAlign = 'center';
	                ctx.textBaseline = 'middle';
	                var centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
	                var centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);
	                ctx.font = fontSizeToUse+"px " + fontStyle;
	                ctx.fillStyle = color;
	               
	              	//Draw text in center
	                ctx.font = "17px " + fontStyle;
		            ctx.fillText(txt1, centerX, centerY + 2);
	                
	              	
	                
	                
	           }
	           }
	        }
	     });
}


function doughnut1(){
	var config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [ '${prevmonthpcnt3}', '${prevmonthpcnt}']
					,backgroundColor: [ "#00FFB4", "#565656"]
					,label: 'Dataset 1'
					,cnt : ['${prevmonthcurcnt}', '${prevmonthprecnt}']
					
				}]
				,labels: [ "당월", "전월" ]
			},
					
			options: {
				responsive: false,
				legend: {
					display: false,
					position: 'bottom'
				},
				title: {
					display: false,
					text: 'Chart.js Doughnut Chart'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				elements:{
					center: {
					      text1: ${prevmonthpcnt2} + '% ',
					      text2: '건수',
					      color: "#00FFB4", //Default black
					      fontStyle: 'Verdana', //Default Arial
					      sidePadding: 15 //Default 20 (as a percentage)
					    },
					arc: {
			        	  borderWidth: 0
			            }
				},
				cutoutPercentage:80
				,tooltips: { 
			        callbacks: { 
			          label: function (tooltipItems, data) { 
			        	  		var datasetLabel = data.datasets[tooltipItems.datasetIndex].cnt[tooltipItems.index] ;
			        	  		var label = data.labels[tooltipItems.index];		           
			        	  	return  label + ": " + datasetLabel; 
			          } 
			        } 
			     } 
			}
		};
	
	return config;
}

function doughnut2(){
	var config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [ '${prevmonthpcost3}', '${prevmonthpcost}' ]
					,backgroundColor: [ "#00FFFF","#565656"]
					,label: 'Dataset 1'
					,cost : ['${prevmonthcurcost}', '${prevmonthprecost}']
				}],
				labels: [ "당월", "전월" ]
			},
			options: {
				responsive: false,
				legend: {
					display: false,
					position: 'bottom'
				},
				title: {
					display: false,
					text: 'Chart.js Doughnut Chart'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				elements:{
					center: {
					      text1: ${prevmonthpcost2} + '% ',
					      text2: '요금',
					      color: "#00FFFF", //Default black
					      fontStyle: 'Verdana', //Default Arial
					      sidePadding: 15 //Default 20 (as a percentage)
					    },
					 arc: {
				          borderWidth: 0
				        }
				},
				cutoutPercentage:80
				,tooltips: { 
			        callbacks: { 
			          label: function (tooltipItems, data) { 
			        	  		var datasetLabel = data.datasets[tooltipItems.datasetIndex].cost[tooltipItems.index] ;
			        	  		var label = data.labels[tooltipItems.index];		           
			        	  	return  label + ": " + datasetLabel; 
			          } 
			        } 
			     } 
			}
		};
	
	return config;
}


function monthcurconfig(){
	var xlist = "${monthcurx}";
	var x = xlist.replace("[", "").replace("]","").split(",");
	
	var ylist = "${monthcury}";
	var y = ylist.replace("[", "").replace("]","").split(",");
	 
	var moncurflag = "${moncurflag}";
	var mflag = moncurflag.replace("[", "").replace("]","");
	
	var daycurflag = "${daycurflag}";
	var dflag = daycurflag.replace("[", "").replace("]","");
	
	var monthsinecnt = "${monthsinecnt}";
	var monthinjubcnt = "${monthinjubcnt}";
	var monthsiuecnt = "${monthsiuecnt}";
	var monthidongcnt = "${monthidongcnt}";
	var monthintercnt = "${monthintercnt}";
	var monthkukjecnt = "${monthkukjecnt}";
	var monthgitacnt = "${monthgitacnt}";
	
	var monthsinecost = "${monthsinecost}";
	var monthinjubcost = "${monthinjubcost}";
	var monthsiuecost = "${monthsiuecost}";
	var monthidongcost = "${monthidongcost}";
	var monthintercost = "${monthintercost}";
	var monthkukjecost = "${monthkukjecost}";
	var monthgitacost = "${monthgitacost}";
	
	var monthsinecntData = monthsinecnt.replace("[", "").replace("]","").split(",");
	var monthinjubcntData = monthinjubcnt.replace("[", "").replace("]","").split(",");
	var monthsiuecntData = monthsiuecnt.replace("[", "").replace("]","").split(",");
	var monthidongcntData = monthidongcnt.replace("[", "").replace("]","").split(",");
	var monthintercntData = monthintercnt.replace("[", "").replace("]","").split(",");
	var monthkukjecntData = monthkukjecnt.replace("[", "").replace("]","").split(",");
	var monthgitacntData = monthgitacnt.replace("[", "").replace("]","").split(",");
	
	var monthsinecostData = monthsinecost.replace("[", "").replace("]","").split(",");
	var monthinjubcostData = monthinjubcost.replace("[", "").replace("]","").split(",");
	var monthsiuecostData = monthsiuecost.replace("[", "").replace("]","").split(",");
	var monthidongcostData = monthidongcost.replace("[", "").replace("]","").split(",");
	var monthintercostData = monthintercost.replace("[", "").replace("]","").split(",");
	var monthkukjecostData = monthkukjecost.replace("[", "").replace("]","").split(",");
	var monthgitacostData = monthgitacost.replace("[", "").replace("]","").split(",");
	
	var monthsineData = "";
	var monthinjubData = "";
	var monthsiueData = "";
	var monthidongData = "";
	var monthinterData = "";
	var monthkukjeData = "";
	var monthgitaData = "";
	
	if(mflag == "cost"){
		
		monthsineData = monthsinecost.replace("[", "").replace("]","").split(",");
		monthinjubData = monthinjubcost.replace("[", "").replace("]","").split(",");
		monthsiueData = monthsiuecost.replace("[", "").replace("]","").split(",");
		monthidongData = monthidongcost.replace("[", "").replace("]","").split(",");
		monthinterData = monthintercost.replace("[", "").replace("]","").split(",");
		monthkukjeData = monthkukjecost.replace("[", "").replace("]","").split(",");
		monthgitaData = monthgitacost.replace("[", "").replace("]","").split(",");
		
	}else{
		monthsineData = monthsinecnt.replace("[", "").replace("]","").split(",");
		monthinjubData = monthinjubcnt.replace("[", "").replace("]","").split(",");
		monthsiueData = monthsiuecnt.replace("[", "").replace("]","").split(",");
		monthidongData = monthidongcnt.replace("[", "").replace("]","").split(",");
		monthinterData = monthintercnt.replace("[", "").replace("]","").split(",");
		monthkukjeData = monthkukjecnt.replace("[", "").replace("]","").split(",");
		monthgitaData = monthgitacnt.replace("[", "").replace("]","").split(",");
	}
	
	var config = {

			type: 'line',
			data: {
				labels: [x[0],x[1],x[2],x[3],x[4],x[5]+"(월)"],
				datasets: [{
					backgroundColor: window.chartColors.red
					,borderColor: window.chartColors.red
					,borderWidth: 2
					,data: monthsineData
					,label: [y[0]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
					
				},
				{
					backgroundColor: window.chartColors.yellow
					,borderColor: window.chartColors.yellow
					,borderWidth: 2
					,data: monthsiueData
					,label: [y[1]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.green
					,borderColor: window.chartColors.green
					,borderWidth: 2
					,data: monthidongData
					,label: [y[2]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.blue
					,borderColor: window.chartColors.blue
					,borderWidth: 2
					,data: monthinterData
					,label: [y[3]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.grey
					,borderColor: window.chartColors.grey
					,borderWidth: 2
					,data: monthkukjeData
					,label: [y[4]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.purple
					,borderColor: window.chartColors.purple
					,borderWidth: 2
					,data: monthgitaData
					,label: [y[5]]
					,fill: false
					,lineTension: 0
					,pointRadius: 1
					
				}
				
				]
			},
			options: {
				title: {
					text: 'fill: aaa'  ,
					display: false
				},
				legend: {
					display: true,
					position: "top",
					labels: {
						boxWidth : 5
					}
					
					
				},
				scales: {
					xAxes: [{
						display: true,
						scaleLabel: {
							display: false,
							labelString: 'Month'
						},
						gridLines: {
							drawOnChartArea:false,
							color:"window.chartColors.red",
							zeroLineWidth: 2,
							offsetGridLines: false,
							zeroLineColor:"window.chartColors.red"
						}
					
					}],
					yAxes: [{
						display: true,
						scaleLabel: {
							display: false,
							labelString: 'Value'
							
						},
						ticks: {beginAtZero: true },
						gridLines: {
							drawOnChartArea:false,
							color:"window.chartColors.red",
							zeroLineWidth: 2,
							offsetGridLines: false,
							zeroLineColor:"window.chartColors.red"
						}
						
						
					}]
				},
				responsive: false,
				animation: false,
				maintainAspectRatio : false
				,tooltips: {
					mode: 'nearest',
					intersect: false 
				}
			}
		};
	
	return config;
}

function myLineupdate(){
	var formData = new FormData();
	formData.append("moncurflag" , $("input[name='monthcur']:checked").val());
	
	window.myLine.destroy();
	
	var url = CONTEXT_ROOT + "/monitor/getmonthcur.json";
	
	$.ajax({
		type: 'POST',
		url: url,
		processData: false,
		contentType: false, 
		data: formData,
		success : function(data) {
			monthcurconfigajax(data);
		},
		error: function(xhr, status, error) { 
			alet("error");
		}
	});
	
}

function monthcurconfigajax(data){
	var monthsineData = "";
	var monthinjubData = "";
	var monthsiueData = "";
	var monthidongData = "";
	var monthinterData = "";
	var monthkukjeData = "";
	var monthgitaData = "";
	
	if(data.moncurflag == "cost"){
		monthsineData = data.monthsinecost;
		monthinjubData = data.monthinjubcost;
		monthsiueData = data.monthsiuecost;
		monthidongData = data.monthidongcost;
		monthinterData = data.monthintercost;
		monthkukjeData = data.monthkukjecost;
		monthgitaData = data.monthgitacost;
		
	}else{
		monthsineData = data.monthsinecnt;
		monthinjubData = data.monthinjubcnt;
		monthsiueData = data.monthsiuecnt;
		monthidongData = data.monthidongcnt;
		monthinterData = data.monthintercnt;
		monthkukjeData = data.monthkukjecnt;
		monthgitaData = data.monthgitacnt;
	}
	
	var config = {

			type: 'line',
			data: {
				labels: [data.monthcurx[0],data.monthcurx[1],data.monthcurx[2],data.monthcurx[3],data.monthcurx[4],data.monthcurx[5] + "(월)"],
				datasets: [{
					backgroundColor: window.chartColors.red,
					borderColor: window.chartColors.red,
					borderWidth: 2,
					data: monthsineData,
					label: [data.monthcury[0]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.yellow,
					borderColor: window.chartColors.yellow,
					borderWidth: 2,
					data: monthsiueData,
					label: [data.monthcury[1]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.green,
					borderColor: window.chartColors.green,
					borderWidth: 2,
					data: monthidongData,
					label: [data.monthcury[2]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.blue,
					borderColor: window.chartColors.blue,
					borderWidth: 2,
					data: monthinterData,
					label: [data.monthcury[3]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.grey,
					borderColor: window.chartColors.grey,
					borderWidth: 2,
					data: monthkukjeData,
					label: [data.monthcury[4]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.purple,
					borderColor: window.chartColors.purple,
					borderWidth: 2,
					data: monthgitaData,
					label: [data.monthcury[5]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				}
				
				]
			},
			options: {
				title: {
					text: 'fill: aaa'  ,
					display: false
				},
				legend: {
					display: true,
					position: 'top',
					labels: {
						boxWidth : 5	
					}
					
				},
				scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Month'
					},
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Value'
						
					},
					ticks: {beginAtZero: true },
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}]
			},
			responsive: false,
			animation: false,
			maintainAspectRatio : false
			,tooltips: {
				mode: 'nearest',
				intersect: false 
			}
			}
		};
	
	/* 월별현황 */
	var ctx = document.getElementById('monthcurCanvas').getContext('2d');
	window.myLine = new Chart(ctx, config);
}

function daycurconfig(){
	var xlist = "${daycurx}";
	var x = xlist.replace("[", "").replace("]","").split(",");
	
	var ylist = "${monthcury}";
	var y = ylist.replace("[", "").replace("]","").split(",");
	
	var daycurflag = "${daycurflag}";
	var dflag = daycurflag.replace("[", "").replace("]","");
	
	var daysinecnt = "${daysinecnt}";
	var dayinjubcnt = "${dayinjubcnt}";
	var daysiuecnt = "${daysiuecnt}";
	var dayidongcnt = "${dayidongcnt}";
	var dayintercnt = "${dayintercnt}";
	var daykukjecnt = "${daykukjecnt}";
	var daygitacnt = "${daygitacnt}";
	
	var daysinecost = "${daysinecost}";
	var dayinjubcost = "${dayinjubcost}";
	var daysiuecost = "${daysiuecost}";
	var dayidongcost = "${dayidongcost}";
	var dayintercost = "${dayintercost}";
	var daykukjecost = "${daykukjecost}";
	var daygitacost = "${daygitacost}";
	
	var daysinecntData = daysinecnt.replace("[", "").replace("]","").split(",");
	var dayinjubcntData = dayinjubcnt.replace("[", "").replace("]","").split(",");
	var daysiuecntData = daysiuecnt.replace("[", "").replace("]","").split(",");
	var dayidongcntData = dayidongcnt.replace("[", "").replace("]","").split(",");
	var dayintercntData = dayintercnt.replace("[", "").replace("]","").split(",");
	var daykukjecntData = daykukjecnt.replace("[", "").replace("]","").split(",");
	var daygitacntData = daygitacnt.replace("[", "").replace("]","").split(",");
	
	var daysinecostData = daysinecost.replace("[", "").replace("]","").split(",");
	var dayinjubcostData = dayinjubcost.replace("[", "").replace("]","").split(",");
	var daysiuecostData = daysiuecost.replace("[", "").replace("]","").split(",");
	var dayidongcostData = dayidongcost.replace("[", "").replace("]","").split(",");
	var dayintercostData = dayintercost.replace("[", "").replace("]","").split(",");
	var daykukjecostData = daykukjecost.replace("[", "").replace("]","").split(",");
	var daygitacostData = daygitacost.replace("[", "").replace("]","").split(",");
	
	var daysineData = "";
	var dayinjubData = "";
	var daysiueData = "";
	var dayidongData = "";
	var dayinterData = "";
	var daykukjeData = "";
	var daygitaData = "";
	
	if(dflag == "cost"){
		
		daysineData = daysinecost.replace("[", "").replace("]","").split(",");
		dayinjubData = dayinjubcost.replace("[", "").replace("]","").split(",");
		daysiueData = daysiuecost.replace("[", "").replace("]","").split(",");
		dayidongData = dayidongcost.replace("[", "").replace("]","").split(",");
		dayinterData = dayintercost.replace("[", "").replace("]","").split(",");
		daykukjeData = dayhkukjecost.replace("[", "").replace("]","").split(",");
		daygitaData = daygitacost.replace("[", "").replace("]","").split(",");
		
	}else{
		daysineData = daysinecnt.replace("[", "").replace("]","").split(",");
		dayinjubData = dayinjubcnt.replace("[", "").replace("]","").split(",");
		daysiueData = daysiuecnt.replace("[", "").replace("]","").split(",");
		dayidongData = dayidongcnt.replace("[", "").replace("]","").split(",");
		dayinterData = dayintercnt.replace("[", "").replace("]","").split(",");
		daykukjeData = daykukjecnt.replace("[", "").replace("]","").split(",");
		daygitaData = daygitacnt.replace("[", "").replace("]","").split(",");
	}
	
	var config = {

			type: 'line',
			data: {
				labels: [x[0],x[1],x[2],x[3],x[4],x[5]+"(일)"],
				datasets: [{
					backgroundColor: window.chartColors.red,
					borderColor: window.chartColors.red,
					borderWidth: 2,
					data: daysineData,
					label: [y[0]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.yellow,
					borderColor: window.chartColors.yellow,
					borderWidth: 2,
					data: daysiueData,
					label: [y[1]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.green,
					borderColor: window.chartColors.green,
					borderWidth: 2,
					data: dayidongData,
					label: [y[2]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.blue,
					borderColor: window.chartColors.blue,
					borderWidth: 2,
					data: dayinterData,
					label: [y[3]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.grey,
					borderColor: window.chartColors.grey,
					borderWidth: 2,
					data: daykukjeData,
					label: [y[4]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.purple,
					borderColor: window.chartColors.purple,
					borderWidth: 2,
					data: daygitaData,
					label: [y[5]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				}
				
				]
			},
			options: {
				title: {
					text: 'fill: aaa'  ,
					display: false
				},
				legend: {
					display: true,
					position: 'top',
					labels: {
						boxWidth : 5	
					}
					
				},
				scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Month'
					},
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Value'
						
					},
					ticks: {beginAtZero: true },
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}]
			},
			responsive: false,
			animation: false,
			maintainAspectRatio : false
			,tooltips: {
				mode: 'nearest',
				intersect: false 
			}
			}
		};
	
	return config;
}


function myLinedayupdate(){
	
	var formData = new FormData();
	formData.append("daycurflag" , $("input[name='daycur']:checked").val());
	
	window.myLine2.destroy();
	
	var url = CONTEXT_ROOT + "/monitor/getdaythcur.json";
	
	$.ajax({
		type: 'POST',
		url: url,
		processData: false,
		contentType: false, 
		data: formData,
		success : function(data) {
			daycurconfigajax(data);
		},
		error: function(xhr, status, error) { 
			
		}
	});
	

}


function daycurconfigajax(data){
	var daysineData = "";
	var dayinjubData = "";
	var daysiueData = "";
	var dayidongData = "";
	var dayinterData = "";
	var daykukjeData = "";
	var daygitaData = "";
	
	if(data.daycurflag == "cost"){
		daysineData = data.daysinecost;
		dayinjubData = data.dayinjubcost;
		daysiueData = data.daysiuecost;
		dayidongData = data.dayidongcost;
		dayinterData = data.dayintercost;
		daykukjeData = data.daykjecost;
		daygitaData = data.daygitacost;
		
	}else{
		daysineData = data.daysinecnt;
		dayinjubData = data.dayinjubcnt;
		daysiueData = data.daysiuecnt;
		dayidongData = data.dayidongcnt;
		dayinterData = data.dayintercnt;
		daykukjeData = data.daykukjecnt;
		daygitaData = data.daygitacnt;
	}
	
	var config = {

			type: 'line',
			data: {
				labels: [data.daycurx[0],data.daycurx[1],data.daycurx[2],data.daycurx[3],data.daycurx[4],data.daycurx[5]+"(일)"],
				datasets: [{
					backgroundColor: window.chartColors.red,
					borderColor: window.chartColors.red,
					borderWidth: 2,
					data: daysineData,
					label: [data.monthcury[0]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.yellow,
					borderColor: window.chartColors.yellow,
					borderWidth: 2,
					data: daysiueData,
					label: [data.monthcury[1]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.green,
					borderColor: window.chartColors.green,
					borderWidth: 2,
					data: dayidongData,
					label: [data.monthcury[2]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.blue,
					borderColor: window.chartColors.blue,
					borderWidth: 2,
					data: dayinterData,
					label: [data.monthcury[3]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.grey,
					borderColor: window.chartColors.grey,
					borderWidth: 2,
					data: daykukjeData,
					label: [data.monthcury[4]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.purple,
					borderColor: window.chartColors.purple,
					borderWidth: 2,
					data: daygitaData,
					label: [data.monthcury[5]],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				}
				
				]
			},
			options: {
				title: {
					text: 'fill: aaa'  ,
					display: false
				},
				legend: {
					display: true,
					position: 'top',
					labels: {
						boxWidth : 5	
					}
					
				},
				scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Month'
					},
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Value'
						
					},
					ticks: {beginAtZero: true },
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.red",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.red"
					}
					
				}]
			},
			responsive: false,
			animation: false,
			maintainAspectRatio : false
			,tooltips: {
				mode: 'nearest',
				intersect: false 
			}
			}
		};
	
	/* 일별현황 */
	var ctx = document.getElementById('daycurCanvas').getContext('2d');
	window.myLine2 = new Chart(ctx, config);
}


function systemBar(){
	var systemCPU = "${systemCPU}";
	var systemMEM = "${systemMEM}";
	var systemRCPU = "${systemRCPU}";
	var systemRMEM = "${systemRMEM}";
	
	var systemCPUData = systemCPU.replace("[", "").replace("]","");
	var systemMEMData = systemMEM.replace("[", "").replace("]","");
	var systemRCPUData = systemRCPU.replace("[", "").replace("]","");
	var systemRMEMData = systemRMEM.replace("[", "").replace("]","");
	
	var config = {
			type: 'bar',
			data: {
				labels: ['CPU', 'MEMOEY'],
				datasets: [{
					label: 'USED',
					backgroundColor: window.chartColors.yellow,
					data: [
						systemCPUData,
						systemMEMData
					]
				}, {
					label: 'FREE',
					backgroundColor: "#565656",
					data: [
						systemRCPUData,
						systemRMEMData
					]
				}]
			},
			options: {
				title: {
					display: false,
					text: 'Chart.js Bar Chart - Stacked'
				},
				tooltips: {
					mode: 'index',
					intersect: false
				},
				responsive: true,
				scales: {
					xAxes: [{
						stacked: true,
						barThickness : 30,
						gridLines: {
							display:false,
							zeroLineWidth: 2,
							offsetGridLines: false,
							color : 'rgba(66,66,66,66)'
						}
						
					}],
					yAxes: [{
						stacked: true,
						gridLines: {
							show:true,
							drawOnChartArea:false,
							zeroLineWidth: 2,
							offsetGridLines: false,
							color : 'rgba(66,66,66,66)'
						},
						ticks: {beginAtZero: true }
					}]
				},
				legend: {
					display: false,
					position: 'bottom',
					labels: {
						boxWidth : 5	
					}
					
				}
			}
		};
	
	return config;
}


function diskBar(){
	var systemROOT = "${systemROOT}";
	var systemHOME = "${systemHOME}";
	var systemDATA = "${systemDATA}";
	var systemDB = "${systemDB}";
	var systemHOMEnm = "${systemHOMEnm}";
	
	var systemROOTData = systemROOT.replace("[", "").replace("]","");
	var systemHOMEData = systemHOME.replace("[", "").replace("]","");
	var systemDATAData = systemDATA.replace("[", "").replace("]","");
	var systemDBData = systemDB.replace("[", "").replace("]","");
	 
	var config = {
			type: 'bar',
			data: {
				labels: ['/', systemHOMEnm, '/data', '/db' ],
				datasets: [{
					label: 'USED',
					backgroundColor: "#00daff",
					data: [
						systemROOTData,
						systemHOMEData,
						systemDATAData,
						systemDBData
					]
				}, {
					label: 'FREE',
					backgroundColor: "#565656",
					data: [
						100 - systemROOTData,
						100 - systemHOMEData,
						100 - systemDATAData,
						100 - systemDBData
					]
				}]
			},
			options: {
				title: {
					display: false,
					text: 'Chart.js Bar Chart - Stacked'
				},
				tooltips: {
					mode: 'index',
					intersect: false
				},
				responsive: true,
				scales: {
					xAxes: [{
						stacked: true,
						barThickness : 30,
						gridLines: {
							display:false,
							zeroLineWidth: 2,
							offsetGridLines: false,
							color : 'rgba(66,66,66,66)'
						}
					}],
					yAxes: [{
						stacked: true,
						gridLines: {
							show:true,
							drawOnChartArea:false,
							zeroLineWidth: 2,
							offsetGridLines: false,
							color : 'rgba(66,66,66,66)'
						}
					}]
				},
				legend: {
					display: false,
					position: 'bottom',
					labels: {
						boxWidth : 5	
					}
					
				}
			}
		};
	
	return config;
}


function networkBar(){
	var networX = "${networX}";
	var networkRX = "${networkRX}";
	var networkTX = "${networkTX}";
	
	var networXData = networX.replace("[", "").replace("]","").split(",");
	var networkRXData = networkRX.replace("[", "").replace("]","").split(",");
	var networkTXData = networkTX.replace("[", "").replace("]","").split(",");
	
	var config = {

			type: 'line',
			data: {
				labels: [networXData[0], networXData[1], networXData[2], networXData[3], networXData[4], networXData[5]],
				datasets: [{
					backgroundColor: window.chartColors.red,
					borderColor: window.chartColors.red,
					borderWidth: 2,
					data: networkRXData,
					label: ['송신(KByte) '],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				},
				{
					backgroundColor: window.chartColors.orange,
					borderColor: window.chartColors.orange,
					borderWidth: 2,
					data: networkTXData,
					label: ['수신(KByte)'],
					fill: false,
					lineTension: 0
					,pointRadius: 1
				}
				
				
				]
			},
			options: {
				title: {
					text: 'fill: aaa'  ,
					display: false
				},
				legend: {
					display: true,
					position: 'top',
					labels: {
						boxWidth : 5
						, padding : 5
					}
					
				},
				scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Month'
					},
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.white",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.white"
					}
					
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Value'
						
					},
					gridLines: {
						drawOnChartArea:false,
						color:"window.chartColors.white",
						zeroLineWidth: 2,
						offsetGridLines: false,
						zeroLineColor:"window.chartColors.white"
					},
					ticks: {beginAtZero: true }
					
				}]
			},
			responsive: false,
			animation: false,
			maintainAspectRatio : false
			,tooltips: {
				mode: 'nearest',
				intersect: false 
			}
			}
		};
	return config;
}

function dayBtypeChart(){
var dayBtypeChart = jui.include("chart.builder");
	
	var dayBtypePcnt = "${dayBtypePcnt}";
	var btype = "${monthcury}";
	var btypeList = btype.replace("[", "").replace("]","").replace("%","").split(",");
	var dayBtypePcntList = dayBtypePcnt.replace("[", "").replace("]","").split(",");
	
	var dayBtypenames = null;
	var dayBtypedata = null;
	if(dayBtypePcntList[0] == 0 && dayBtypePcntList[1] == 0 && dayBtypePcntList[2] == 0 && dayBtypePcntList[3] == 0 && dayBtypePcntList[4] == 0 && dayBtypePcntList[5] == 0){
		dayBtypenames = {"1" : "자료없음", "2" : btypeList[1], "3" : btypeList[2], "4" : btypeList[3], "5" : btypeList[4], "6" : btypeList[5]};
		dayBtypedata = {"1" : 100, "2" : dayBtypePcntList[1]*=1, "3" : dayBtypePcntList[2]*=1, "4" : dayBtypePcntList[3]*=1, "5" : dayBtypePcntList[4]*=1, "6" :  dayBtypePcntList[5]*=1}
	}else{
		dayBtypenames = {"1" : btypeList[0], "2" : btypeList[1], "3" : btypeList[2], "4" : btypeList[3], "5" : btypeList[4], "6" : btypeList[5]};
		dayBtypedata = {"1" : dayBtypePcntList[0]*=1, "2" : dayBtypePcntList[1]*=1, "3" : dayBtypePcntList[2]*=1, "4" : dayBtypePcntList[3]*=1, "5" : dayBtypePcntList[4]*=1, "6" :  dayBtypePcntList[5]*=1}	
	}
	
	dayBtypeChart("#daybtypechart", {
	    padding : {
	    	left : 20,    
			top : 35
		},
	    width: "110%",
        height : "110%",
        axis : {
	        data : [ 
	        	dayBtypedata 
	       	]
	    },
	    brush : {
	        type : "donut",
	        showText : "outside",
	        colors : ["#FA120C","#6f2717", "#7c584c", "#e6cfca", "#c9a499", "#93412f", "#bf8274", "#FEB981"],
	        size : 30,
	        format : function(k, v) {
	            return dayBtypenames[k];
	        }
	    },
	    widget : [{
	    	 type : "title",
	         text : "호종류",
	         orient : "middle",
	         size : 15,
	         color : "#f2b355"
	    	},{
		        type : "tooltip",
		        orient : "left",
		        format : function(data, k) {
		            return {
		                key: dayBtypenames[k],
		                value: dayBtypedata[k]
		            }
	        }
	    }],
	    style:{
	    	fontFamily : "NGB",
	    	backgroundColor : null,
	    	pieOuterLineRate : 1.5,
	    	pieOuterLineSize : 20,
	    	pieOuterFontSize : 12,
	    	pieOuterFontColor : "#f2b355",
	    	pieOuterLineColor : "#f2b355"
	    }
	    
	});
	
	var daybtypeTitley = ($('#daybtypechart').find('svg').find('.widget-title').attr('y')*1) + 5;
	$('#daybtypechart').find('svg').find('.widget-title').attr({
		'y' : daybtypeTitley
	});
}

function dayNoBillChart(){
	var daynobillChart = jui.include("chart.builder");
	
	var daynobillCallType = '${daynobillCallType}';
	var daynobillCallTypeList = daynobillCallType.replace("[", "").replace("]","").replace("%","").split(",");
	var daynobillPcnt = '${daynobillPcnt}';
	var daynobillPcntList = daynobillPcnt.replace("[", "").replace("]","").replace("%","").split(",");
	
	var daynobillnames = null;
	var daynobilldata = null;
	if(daynobillPcntList[0] == 0 && daynobillPcntList[1] == 0 && daynobillPcntList[2] == 0 && daynobillPcntList[3] == 0 && daynobillPcntList[4] == 0 && daynobillPcntList[5] == 0){
		daynobillnames = {"1" : "자료없음", "2" : daynobillCallTypeList[1], "3" : daynobillCallTypeList[2], "4" : daynobillCallTypeList[3], "5" : daynobillCallTypeList[4], "6" : daynobillCallTypeList[5]};
		daynobilldata = {"1" : 100, "2" : daynobillPcntList[1]*=1, "3" : daynobillPcntList[2]*=1, "4" : daynobillPcntList[3]*=1, "5" : daynobillPcntList[4]*=1, "6" :  daynobillPcntList[5]*=1}
	}else{
		daynobillnames = {"1" : daynobillCallTypeList[0], "2" : daynobillCallTypeList[1], "3" : daynobillCallTypeList[2], "4" : daynobillCallTypeList[3], "5" : daynobillCallTypeList[4], "6" : daynobillCallTypeList[5]};
		daynobilldata = {"1" : daynobillPcntList[0]*=1, "2" : daynobillPcntList[1]*=1, "3" : daynobillPcntList[2]*=1, "4" : daynobillPcntList[3]*=1, "5" : daynobillPcntList[4]*=1, "6" :  daynobillPcntList[5]*=1}	
	}
	
	daynobillChart("#daynobillchart", {
	    padding : {
	    	left : 20,    
			top : 35
		},
	    width: "110%",
        height : "110%",
        theme : "jennifer",
        axis : {
	        data : [ 
	        	daynobilldata 
	        ]
	        
	    },
	    brush : {
	        type : "donut",
	        showText : "outside",
	        colors : ["#46a280", "#14704e", "#2f8b70", "#a0fcdb", "#7cd8b9", "#0d593d", "#55a68b"],
	        size : 30,
	        format : function(k, v) {
	            return daynobillnames[k];
	        }
	    },
	    widget : [{
	    	 type : "title",
	         text : "비과금",
	         orient : "middle",
	         size : 15,
	         color : "#c0e561"
	    	},{
		        type : "tooltip",
		        orient : "left",
		        format : function(data, k) {
		            return {
		                key: daynobillnames[k],
		                value: daynobilldata[k]
		            }
	        	}
	    }],
	    style:{
	    	fontFamily : "NGB",
	    	backgroundColor : null,
	    	pieOuterLineRate : -0.5,
	    	pieOuterLineSize : 20,
	    	pieOuterFontSize : 12,
	    	pieOuterFontColor : "#c0e561",
	    	pieOuterLineColor : "#c0e561"
	    }
	    
	});
	
	var daynobillTitley = ($('#daynobillchart').find('svg').find('.widget-title').attr('y')*1) + 5;
	$('#daynobillchart').find('svg').find('.widget-title').attr({
		'y' : daynobillTitley
	});
}

function daycheckChart(){
var daycheckChart = jui.include("chart.builder");
	
	var daycheckPcnt = '${daycheckPcntList}';
	var daycheckPcntList = daycheckPcnt.replace("[", "").replace("]","").replace("%","").split(",");
	
	var daychecknames = null;
	var daycheckdata = null;
	if(daycheckPcntList[0] == 0 && daycheckPcntList[1] == 0 && daycheckPcntList[2] == 0 && daycheckPcntList[3] == 0 && daycheckPcntList[4] == 0 && daycheckPcntList[5] == 0){
		daychecknames = {"1": "자료없음", "2": "고액통화", "3": "발신변작", "4": "착신변작", "5": "요율미적용", "6": "번호요류"};
		daycheckdata = {"1" : 100, "2" : 0, "3" : 0, "4" : 0, "5" : 0, "6" : 0}
	}else{
		daychecknames = {"1": "장시간", "2": "고액통화", "3": "발신변작", "4": "착신변작", "5": "요율미적용", "6": "번호요류"};
		daycheckdata = {"1" : daycheckPcntList[0]*=1, "2" : daycheckPcntList[1]*=1, "3" : daycheckPcntList[2]*=1, "4" : daycheckPcntList[3]*=1, "5" : daycheckPcntList[4]*=1, "6" : daycheckPcntList[5]*=1}	
	}
	
	
	daycheckChart("#daycheckchart", {
	    padding : {
	    	left : 20,    
			top : 35
		},
	    width: "110%",
        height : "110%",
        theme : "jennifer",
        axis : {
	        data : [
				daycheckdata
	        ]
	        
	    },
	    brush : {
	        type : "donut",
	        showText : "outside",
	        colors : ["#006eff", "#003dff", "#0066ff", "#00e3ff", "#00b5ff", "#1b5ab7", "#0085d9"],
	        size : 30,
	        format : function(k, v) {
	            return daychecknames[k];
	        }
	    },
	    widget : [{
	    	 type : "title",
	         text : "호검사",
	         orient : "middle",
	         size : 15,
	         color : "#00daff"
	    	},{
		        type : "tooltip",
		        orient : "left",
		        format : function(data, k) {
		            return {
		                key: daychecknames[k],
		                value: daycheckdata[k]
		            }
	        }
	    }],
	    style:{
	    	fontFamily : "NGB",
	    	backgroundColor : null,
	    	pieOuterLineRate : -0.5,
	    	pieOuterLineSize : 20,
	    	pieOuterFontSize : 12,
	    	pieOuterFontColor : "#00daff",
	    	pieOuterLineColor : "#00daff"
	    }
	    
	});
	
	var daycheckTitley = ($('#daycheckchart').find('svg').find('.widget-title').attr('y')*1) + 5;
	$('#daycheckchart').find('svg').find('.widget-title').attr({
		'y' : daycheckTitley
	});
}

function dayBtypeDoughnut(){
	var config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [
						"${dayBtypePcnt[0]}","${dayBtypePcnt[1]}","${dayBtypePcnt[2]}","${dayBtypePcnt[3]}","${dayBtypePcnt[4]}","${dayBtypePcnt[5]}","${dayBtypePcnt[6]}"
					],
					backgroundColor: [
						"#FA120C","#6f2717", "#7c584c", "#e6cfca", "#c9a499", "#93412f", "#bf8274"					                  
					],
					borderWidth: 0,
					label: 'Dataset 1'
					}],
					labels: [
						"${dayBtypePcntName[0]}", "${dayBtypePcntName[1]}", "${dayBtypePcntName[2]}", "${dayBtypePcntName[3]}", "${dayBtypePcntName[4]}", "${dayBtypePcntName[5]}", "${dayBtypePcntName[6]}"  
					]
				},
					
			options: {
				responsive: false,
				legend: {
					display: false,
					position: 'right',
					labels:{
						boxWidth: 10
					}
				},
				title: {
					display: false,
					text: 'Chart.js Doughnut Chart'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				elements:{
					center: {
					      text1: '호종류',
					      text2: '',
					      color: "#E9730C", //Default black
					      fontStyle: 'Verdana', //Default Arial
					      sidePadding: 15 //Default 20 (as a percentage)
					    },
					arc: {
			        	  borderWidth: 0
			            }
				},
				cutoutPercentage:50
			}
		};
	
	return config;
}

function dayNobillDoughnut(){
	var config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [
						"${daynobillPcnt[0]}", "${daynobillPcnt[1]}", "${daynobillPcnt[2]}", "${daynobillPcnt[3]}", "${daynobillPcnt[4]}", "${daynobillPcnt[5]}" 
					],
					backgroundColor: [
						"#46a280", "#14704e", "#2f8b70", "#a0fcdb", "#7cd8b9", "#0d593d"
					],
					borderWidth: 0,
					label: 'Dataset 1'
					}],
					labels: [
						"${daynobillCallType[0]}", "${daynobillCallType[1]}", "${daynobillCallType[2]}", "${daynobillCallType[3]}", "${daynobillCallType[4]}", "${daynobillCallType[5]}"
					]
				},
					
			options: {
				responsive: false,
				legend: {
					display: false,
					position: 'right',
					labels:{
						boxWidth: 10
					}
				},
				title: {
					display: false,
					text: 'Chart.js Doughnut Chart'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				elements:{
					center: {
					      text1: '비과금',
					      text2: '',
					      color: "#77B417", //Default black
					      fontStyle: 'Verdana', //Default Arial
					      sidePadding: 15 //Default 20 (as a percentage)
					    },
					arc: {
			        	  borderWidth: 0
			            }
				},
				cutoutPercentage:50
			}
		};
	
	return config;
}

function dayCheckDoughnut(){
	var config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [
						"${daycheckPcntList[0]}", "${daycheckPcntList[1]}", "${daycheckPcntList[2]}", "${daycheckPcntList[3]}", "${daycheckPcntList[4]}", "${daycheckPcntList[5]}" 
					],
					backgroundColor: [
						"#006eff", "#003dff", "#0066ff", "#00e3ff", "#00b5ff", "#1b5ab7", "#0085d9"
					],
					borderWidth: 0,
					label: 'Dataset 1'
					}],
					labels: [
						"${daycheckPcntNameList[0]}", "${daycheckPcntNameList[1]}", "${daycheckPcntNameList[2]}", "${daycheckPcntNameList[3]}", "${daycheckPcntNameList[4]}", "${daycheckPcntNameList[5]}"  
					]
				},
					
			options: {
				responsive: false,
				legend: {
					display: false,
					position: 'right',
					labels:{
						boxWidth: 10
					}
				},
				title: {
					display: false,
					text: 'Chart.js Doughnut Chart'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				elements:{
					center: {
					      text1: '호검사',
					      text2: '',
					      color: "#0094FF", //Default black
					      fontStyle: 'Verdana', //Default Arial
					      sidePadding: 15 //Default 20 (as a percentage)
					    },
					arc: {
			        	  borderWidth: 0
			            }
				},
				cutoutPercentage:50
			}
		};
	
	return config;
}

function sysdetail(){
	var url = CONTEXT_ROOT + "/monitor/sysdetail.do";
	
	var dlgWidth = 730;
	var dlgHeight = 375;
	
	
	var x = $( window ).width()/2 - dlgWidth/2;
	var y = $( window ).height()/2 - dlgHeight/2;
	
	var win = window.open('', 'SYSTEM_POPUP', "left=" + x + ",top=" + y + ",width=" + dlgWidth + ",height=" + dlgHeight +",titlebar=no,scrollbars=no,resizable=no");
	
	var $form = $('<form></form>');
	$form.attr('action', url);
	$form.attr('method', 'post');
	$form.attr('target', 'SYSTEM_POPUP');
	$form.appendTo('body');
	
	$form.submit();
}

function delalarm(){
	var formData = new FormData();
	var ubseq = new Array();
	$("input[name='delcheck']:checked").each(function(){
		ubseq.push( $(this).closest("td").find("#delcheck").val() );
	})
	
	formData.append("ubseqList" , ubseq);
	var url = CONTEXT_ROOT + "/monitor/delalarm.json";
	
	$.ajax({
		type: 'POST',
		url: url,
		processData: false,
		contentType: false, 
		data: formData,
		success : function(data) {
			if(data.delResult){
				 $("input[name='delcheck']:checked").each(function(){
					for(var i=0; i<ubseq.length; i++){
						if($(this).closest("td").find("#delcheck").val() == ubseq[i]){
							$(this).closest("tr").remove();
						}
					}
				}) 
			}
		
		},
		error: function(xhr, status, error) { 
			alert("error");
		}
	});
}

function resize() {
	if(screen.width == window.innerWidth && screen.height == window.innerHeight){
			$('.mwarp').css('height', window.innerHeight); 
	    } else {
	    	$('.mwarp').css('height', window.innerHeight);
	    } 
	
	
	
}

function full(){
	alert("111");
	
	var e = jQuery.Event( "keypress", { keyCode: 122 } ); 
	$("#full").trigger(e);


	
	
	$("#full").on("click", function(e){
	       alert('F11 pressed');
	   	
		/* if(e.which==122){
		       e.preventDefault();//kill anything that browser may have assigned to it by default
		       alert('F11 pressed');
		       return false;
	 } */
	});
	alert("222");
 	
	
}

</script>


	



<div class="mwarp" id="mwarp">
	<div class="top">
		<div class="logo"><!-- <img src="../../images/monitor/images/mlogo.png"> --></div>
		<div class="btitle"><span>빌링시스템</span> Monitoring</div>
		<div class="topbtn">
			<button class="widebtn" id="widebtn" ></button> 
			<button class="closebtn" onclick="self.close();"></button>		
		</div>
	</div>
	<div class="cont">
		<div class="mtop">
			<table>
			<colgroup>
			<col width="423px;">
			<col width="423px">
			<col width="405px;">
			<col width="148px;">
			<col width="148px;">
			<col width="148px;">
			<col width="148px;">
			</colgroup>
			<thead>
			<tr>
				<th>TODAY 통화</th>
				<th>MONTH 통화</th>
				<th>전월대비 통화분포</th>
				<th class="fc">수집처리</th>
				<th class="fc">통계생성</th>
				<th class="fc">연동상태</th>
				<th class="fc">시스템상태</th>
			</tr>
			</thead>

			<tr>
				<td>
					
					<div class="today">
						<div class="total1"><span class="txt1">${daytongcnt }</span> <span class="txt2">건</span></div>
						<div class="cap">당일건수</div>
						<div class="total2"><div class="gbar"></div></div>
					</div>
					<div class="today">
						<div class="total1"><span class="txt1">${daytongcost }</span> <span class="txt2">원</span></div>
						<div class="cap">당일요금</div>
						<div class="total2"><div class="gbar"></div></div>
					</div>

				</td>
				<td>
					<div class="month">
						<div class="total1"><span class="txt3">${montongcnt }</span> <span class="txt4">건</span></div>
						<div class="cap">당월건수</div>
						<div class="total2"><div class="gbar"></div></div>
					</div>
					<div class="month">
						<div class="total1"><span class="txt3">${montongcost }</span> <span class="txt4">원</span></div>
						<div class="cap">당월요금</div>
						<div class="total2"><div class="gbar"></div></div>
					</div>
				</td>
				<td>
					<div class="gw">
						<div class="gw1"><canvas id="billingdonutCanvas1" width="160px;" height="110px;"></canvas></div>
						<div class="gw1"><canvas id="billingdonutCanvas2" width="160px;" height="110px;"></canvas></div>
					</div>
				</td>
				<td class="fc">
					<c:choose>
						<c:when test="${collStatus !=  'S'}">
							<img src="../../images/monitor/images/002.png">
						</c:when>
						<c:otherwise>
							<img src="../../images/monitor/images/001.png">
						</c:otherwise>
					</c:choose>
				</td>
				<td class="fc">
					<c:choose>
						<c:when test="${tongStatus !=  'S'}">
							<img src="../../images/monitor/images/002.png">
						</c:when>
						<c:otherwise>
							<img src="../../images/monitor/images/001.png">
						</c:otherwise>
					</c:choose>
				</td>
				<td class="fc">
					<c:choose>
						<c:when test="${infStatus !=  'S'}">
							<img src="../../images/monitor/images/002.png">
						</c:when>
						<c:otherwise>
							<img src="../../images/monitor/images/001.png">
						</c:otherwise>
					</c:choose>
				</td>
				<td class="fc">
					<c:choose>
						<c:when test="${sysStatus !=  'S'}">
							<img src="../../images/monitor/images/002.png">
						</c:when>
						<c:otherwise>
							<img src="../../images/monitor/images/001.png">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</table>
		</div>
		<div class="mtable">
			<table>
			<colgroup>
			<col width="1014px;">
			<col width="829px;">
			</colgroup>

			<tr>
			<td>
				<div class="box1">
				<ul>
				<li><div class="title"><h1>월별현황 (최근 6개월)</h1><p><input type="radio" id="monthcur" name="monthcur" value="cost" onclick="myLineupdate()">요금 <input type="radio" id="monthcur" name="monthcur" value="cnt" onclick="myLineupdate()" checked="checked">건수</p></div></li>
				<li><div id="monthcurdiv" class="gb"><canvas id="monthcurCanvas" style="margin:20px 5px 0px 5px;" width="450px;" height="180px;"></canvas></div></li>
				</ul>

				<ul>
				<li><div class="title"><h1>일별현황 (최근 일주일)</h1><p><input type="radio" id="daycur" name="daycur" value="cost" onclick="myLinedayupdate()">요금 <input type="radio" id="daycur" name="daycur" value="cnt" onclick="myLinedayupdate()" checked="checked">건수</p></div></li>
				<li><div id="daycurdiv" class="gb"><canvas id="daycurCanvas" style="margin:20px 5px 0px 5px;" width="450px;" height="180px;"></canvas></div></li>
				</ul>
				</div>
			</td>
			<td>
				<div class="box4">
				<ul>
				<li><div class="title"><h1>시스템현황</h1><p><a href="#" class="more" onclick="sysdetail()">+ more</a></p></div></li>
				<li><div class="gb" ><canvas id="systemCanvas" style="margin:20px 0px 0px 0px;" width="150px;" height="110px;"></canvas></div></li>
				</ul>
				<ul>
				<li><div class="title"><h2>디스크사용현황</h2></div></li>
				<li><div class="gb"><canvas id="diskCanvas" style="margin:20px 0px 0px -10px;" width="150px;" height="110px;"></canvas></div></li>
				</ul>
				<ul>
				<li><div class="title"><h2>네트워크 트래픽(최근 6시간)</h2></div></li>
				<li><div class="gb"><canvas id="networkCanvas" style="margin:20px 0px 0px -10px;" width="240px;" height="180px;"></canvas></div></li>
				</ul>
				</div>
			</td>
			</tr>
			<tr>
			<td rowspan="2">
				<div class="box2">
				<ul>
				<li><div class="title"><h2>당일 호종류 현황</h2></div></li>
				<li><div class="gb1">
						<div class="hog" id="daybtypechart"><canvas id="daybtypeCanvas" style="margin:10px 0px 0px 33px;" width="230px;" height="180px;"></canvas></div>
						<div class="holist">
							<table>
							<thead>
								<tr>
									<th>NO</th>
									<th>호구분</th>
									<th>건수</th>
									<th>점유율</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${daybtypeList ne null || daybtypeList !=''}">
								<c:set var="daybtypeseq" value="1"/>
									<c:forEach var="daybtype" items="${daybtypeList}">
										<c:choose>
					                		<c:when test="${daybtypeseq ==  '1' || daybtypeseq ==  '2' || daybtypeseq ==  '3'}">
					                			<tr class="c1">
					                		</c:when>
					                		<c:otherwise>
					                			<tr>
					                		</c:otherwise>
					                	</c:choose>
					                		<td>${daybtypeseq }</td>
											<td>${daybtype.btypeNm }</td>
											<td>${daybtype.cnt }</td>
											<td>${daybtype.pcnt }%</td>
										</tr>
										<c:set var="daybtypeseq" value="${daybtypeseq + 1}" />
									</c:forEach>
								</c:if>
							</tbody>
							</table>
						</div>
					</div>
				</li>
				</ul>
				<ul>
				<li><div class="title"><h2>당일 비과금 현황</h2></div></li>
				<li><div class="gb1">
						<div class="hog" id="daynobillchart"><canvas id="daynobillCanvas" style="margin:10px 0px 0px 33px;" width="230px;" height="180px;"> </canvas></div>
						<div class="holist">
							<table>
							<thead>
								<tr>
									<th>NO</th>
									<th>비과금구분</th>
									<th>건수</th>
									<th>점유율</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${daynobillList ne null || daynobillList !=''}">
								<c:set var="daynobilleseq" value="1"/>
									<c:forEach var="daynobill" items="${daynobillList}">
										<c:choose>
					                		<c:when test="${daynobilleseq ==  '1' || daynobilleseq ==  '2' || daynobilleseq ==  '3'}">
					                			<tr class="c2">
					                		</c:when>
					                		<c:otherwise>
					                			<tr>
					                		</c:otherwise>
					                	</c:choose>
					                		<td>${daynobilleseq }</td>
											<td>${daynobill.nm }</td>
											<td>${daynobill.cnt }</td>
											<td>${daynobill.pcnt }%</td>
										</tr>
										<c:set var="daynobilleseq" value="${daynobilleseq + 1}" />
									</c:forEach>
								</c:if>
							</tbody>
							</table>
						</div>
					</div>
				</li>
				</ul>
				<ul>
				<li><div class="title"><h2>당일 호검사 현황</h2></div></li>
				<li><div class="gb1">
						<div class="hog" id="daycheckchart"><canvas id="daycheckCanvas" style="margin:10px 0px 0px 33px;" width="230px;" height="180px;"></canvas></div>
						<div class="holist">
							<table>
								<thead>
									<tr>
										<th>NO</th>
										<th>호검사구분</th>
										<th>건수</th>
										<th>점유율</th>
									</tr>
								</thead>
								<tbody>
									<tr class="c3">
										<td>1</td>
										<td>장시간</td>
										<td>${daycheckList[0].longCnt }</td>
										<td>${daycheckList[0].plongCnt }%</td>
										</tr>
										<tr class="c3">
										<td>2</td>
										<td>고액통화</td>
										<td>${daycheckList[0].highCnt }</td>
										<td>${daycheckList[0].phighCnt }%</td>
										</tr>
										<tr class="c3">
										<td>3</td>
										<td>발신변작</td>
										<td>${daycheckList[0].callerCnt }</td>
										<td>${daycheckList[0].pcallerCnt }%</td>
										</tr>
										<tr>
										<td>4</td>
										<td>착신변작</td>
										<td>${daycheckList[0].calleeCnt }</td>
										<td>${daycheckList[0].pcalleeCnt }%</td>
										</tr>
										<tr>
										<td>5</td>
										<td>요율미적용</td>
										<td>${daycheckList[0].chkCnt }</td>
										<td>${daycheckList[0].pchkCnt }%</td>
										</tr>
										<tr>
										<td>6</td>
										<td>번호오류</td>
										<td>${daycheckList[0].telnoErrcnt }</td>
										<td>${daycheckList[0].ptelnoErrcnt }%</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</li>
				</ul>
				</div>
			</td>
			<td>
				<div class="box3">
				<ul>
				<li><div class="title"><h1>통화수집현황</h1><!-- <p><a href="#" class="more">+ more</a></p> --></div></li>
				<li>
					<div class="gb2">
						<div class="t_subj">
							<table class="t_list">
								<colgroup>
									<col width="15%"/>
									<col width="15%"/>
									<col width="12%"/>
									<col width="15%"/>
									<col width="15%"/>
									<col width="10%"/>
									<col width="10%"/>
									<col width="*"/>
								</colgroup>
								<tr>
									<th>교환기</th>
									<th>시작시간</th>
									<th>통화시간(초)</th>
									<th>발신번호</th>
									<th>착신번호</th>
									<th>호구분</th>
									<th>요금(원)</th>
									<th>지역</th>
								</tr>
							</table>
						</div>
						<div id="tong" class="t_scroll">
							<table class="t_list">
								<colgroup>
									<col width="15%"/>
									<col width="15%"/>
									<col width="12%"/>
									<col width="15%"/>
									<col width="15%"/>
									<col width="10%"/>
									<col width="10%"/>
									<col width="*"/>
								</colgroup>
								<c:if test="${pbxtongList ne null || pbxtongList !=''}">
								<c:forEach var="pbxtong" items="${pbxtongList}">
									<tr>
										<td class="pbxtd_eclipse">${pbxtong.pbxNm }</td>
										<td>${pbxtong.stm }</td>
										<td>${pbxtong.dur }</td>
										<td>${pbxtong.ncaller }</td>
										<td>${pbxtong.ncallee }</td>
										<td>${pbxtong.btypeNm }</td>
										<td>${pbxtong.cost }</td>
										<td class="pbxtd_eclipseLast">${pbxtong.calleeAreaNm }</td>
									</tr>
								</c:forEach>
								</c:if>
							</table>
						</div>
					</div>
				</li>
				</ul>
				</div>				
			</td>
			</tr>
			<tr>
			<td>
				<div class="box3">
				<ul>
				<li><div class="title"><h1>알람현황</h1><p><button onclick="delalarm()">알람삭제</button><!-- <a href="#" class="more">+ more</a> --></p></div></li>
				<li>
				<div class="gb2">
					<div class="t_subj">
						<table class="t_list">
							<colgroup>
								<col width="5%"/>
								<col width="18%"/>
								<col width="20%"/>
								<col width="10%"/>
								<col width="*"/>
							</colgroup>
							<tr>
								<th>삭제</th>
								<th>알람구분</th>
								<th>발생시간</th>
								<th>알람등급</th>
								<th>메세지</th>
							</tr>
						</table>
					</div>
					
					<div id="alarm" class="t_scroll">
						<table class="t_list">
							<colgroup>
								<col width="5%"/>
								<col width="18%"/>
								<col width="20%"/>
								<col width="10%"/>
								<col width="*"/>
							</colgroup>
							
							<c:if test="${almtongList ne null || almtongList !=''}">
								<c:forEach var="almtong" items="${almtongList}">
									<tr>
										<td>
											<input type="checkbox" name="delcheck" value="" class="checkb">
											<input type="hidden" id="delcheck"  value="${almtong.ubseq}" >
										</td>
										<td>${almtong.almlNm }</td>
										<td>${almtong.stm }</td>
										<td>${almtong.almNm }</td>
										<td class="alarmtd_eclipse">${almtong.msg }</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</div>
				</li>
				</ul>
				</div>				
			</td>
			</tr>
		</table>	
	</div>
</div>
</div>




