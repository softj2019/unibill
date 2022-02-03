/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.NOTICE = (function(_mod_notice, $, undefined){

	

	
	fn_noticeSearch = function(spage){
		var notice_type = $("#notice_searchFrm").find("#notice_type option:selected").val();
		var s_notice = $("#notice_searchFrm").find("#s_notice").val();
		var s_page = spage;
		if(typeof s_page == "undefined"){
			s_page = 1;
		}
		var param = {"notice_type": notice_type,  "s_notice": s_notice, "s_page": s_page};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/noticeSearch.json',
			data: param,
			success : function(data) {
				if(data.sucess == true){
					$("#n_page").show();
					$("#noticebody").empty();
					for(var i=0; i<data.nList.length; i++){
						var fsn = "";
						var imgfsn = "";
						if(typeof data.nList[i].fileSn != "undefined" && data.nList[i].fileSn != ""){
							fsn = '<img src="/images/notice/bbs/clip.png">'; 
						}
						if(typeof data.nList[i].popupImg != "undefined" && data.nList[i].popupImg != ""){
							imgfsn = '<img src="/images/notice/bbs/clip.png">'; 
						}
						$("#noticebody").append('<tr><td>' + data.nList[i].rownum + '</td><td class="fl" onclick="javascript:NOTICE.fn_noticeDetail(\'' + data.nList[i].ubseq + '\');">'+ data.nList[i].title +'<td>' + fsn + '</td></td><td>' + imgfsn + '</td><td>' + data.nList[i].regId + '</td><td>' + data.nList[i].regTm + '</td></tr>')
						
						
					}
					var totCnt = '<span>총 게시물: <em>' + data.nCnt + '</em></span>';
					var allPage = '<span><em>' + data.curPage + ' / ' + data.allPage + '</em> Page</span>';
					$("#totCnt").html(totCnt);
					$("#totalPage").html(allPage);
					
					$('.pageNum').remove();
					$('#n_page').append('<span class="pageNum"><a class="nnav first" onclick="javascript:NOTICE.fn_firstNotice(' + data.curPage + ');"></a></span>');
					$('#n_page').append('<span class="pageNum"><a class="nnav prev" onclick="javascript:NOTICE.fn_preNotice(' + data.curPage + ');"></a></span>');
					
					for(var i=data.startPage; i<=data.endPage; i++){
						var curPage = data.curPage;
						var curPagehtml = "";
						if(curPage == i){
							if(i==1){
								$('#n_page').append('<span class="pageNum"><a onclick="javascript:NOTICE.fn_noticeSearch(this.innerHTML);" class="on">' + i + '</a></span>');
							}else{
								$('#n_page').append(' <span class="pageNum"><a onclick="javascript:NOTICE.fn_noticeSearch(this.innerHTML);" class="on">' + i + '</a></span>');
							}
						}else{
							if(i==1){
								$('#n_page').append('<span class="pageNum"><a onclick="javascript:NOTICE.fn_noticeSearch(this.innerHTML);">' + i + '</a></span>');
							}else{
								$('#n_page').append(' <span class="pageNum"><a onclick="javascript:NOTICE.fn_noticeSearch(this.innerHTML);">' + i + '</a></span>');
							}
						}
						
						
					}
					$('#n_page').append('<span class="pageNum"><a class="nnav next" onclick="javascript:NOTICE.fn_nextNotice(' + data.curPage + ',' + data.allPage +');"></a></span>');
					$('#n_page').append('<span class="pageNum"><a class="nnav last" onclick="javascript:NOTICE.fn_lasttNotice(' + data.curPage + ',' + data.allPage +');"></a></span>');
				}else{
					alert("notice search fail!");
				}
				
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
	}
	
	fn_preNotice = function(spage){
		var startpage =  spage*1;
		if(startpage % 10 == 0){
			startpage = parseInt(spage / 10)
		}else{
			startpage = parseInt(spage / 10) + 1

		}
		if(startpage > 1){
			NOTICE.fn_noticeSearch((startpage - 2)*10 + 1 );
		}
	}
	
	fn_nextNotice = function(spage, sallpage){
		var startpage =  spage*1;
		if(spage % 10 == 0){
			startpage = parseInt(spage / 10)
		}else{
			startpage = parseInt(spage / 10) + 1

		}
		
		var endtpage =  1;
		if(sallpage % 10 == 0){
			endtpage = parseInt(sallpage / 10)
		}else{
			endtpage = parseInt(sallpage / 10) + 1

		}
		if(startpage < endtpage){
			NOTICE.fn_noticeSearch((startpage)*10 + 1 );
		}
	}
	
	fn_firstNotice = function(spage){
		NOTICE.fn_noticeSearch(1);
	}
	
	fn_lasttNotice = function(spage, sallpage){
		NOTICE.fn_noticeSearch(sallpage);
	}
	
	fn_noticeDetail = function(subseq){
		// 팝업 show
		var tx = ($(window).width() -  1075) / 2;
		var ty = ($(window).height() - 750) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_noticeList_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_noticeList_pop").show();
		
		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectNoticeDetail.json',
			data: 'ubseq=' + subseq,
			datatype : "json",
			success : function(data) {
				
				$("#regtm").html(data.nDetailInfo.regTm);
				$("#regid").html(data.nDetailInfo.regId);
				$("#email").html(data.nDetailInfo.email);
				$("#ntitle").html(data.nDetailInfo.title);
				
				data.nDetailInfo.msg = data.nDetailInfo.msg.replace(/(?:\r\n|\r|\n)/g, '<br />');
				$("#nmsg").html(data.nDetailInfo.msg);
				
				$("#nfilesz").html(data.nDetailInfo.fileSz);
				if(typeof data.nDetailInfo.fileNm == "undefined" ||  data.nDetailInfo.fileNm == ""){
					$("#fdown").hide();
					$("#fileList").html('<tr><td></td></tr>');
				}else{
					$("#fdown").show();
					$("#fdown").removeAttr("onclick");
					$("#fdown").attr("onclick", 'NOTICE.fn_nfileDownload(\'' + data.nDetailInfo.fileSn + '\');');
					
					$("#fileList").html('<tr><td><img src="../../images/notice/bbs/clip.png">&nbsp; ' + data.nDetailInfo.fileNm + '</td></tr>');
				}
				
				$("#nimgfilesz").html(data.nDetailInfo.imgSz);
				if(typeof data.nDetailInfo.imgNm == "undefined" ||  data.nDetailInfo.imgNm == ""){
					$("#imgdown").hide();
					$("#imgfileList").html('<tr><td></td></tr>');
				}else{
					$("#imgdown").show();
					$("#imgdown").removeAttr("onclick");
					$("#imgdown").attr("onclick", 'NOTICE.fn_nimgDownload(\'' + data.nDetailInfo.popupImg + '\');');
					
					$("#imgfileList").html('<tr><td><img src="../../images/notice/bbs/clip.png">&nbsp; ' + data.nDetailInfo.imgNm + '</td></tr>');
				}
				
				
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
				result = false;
			}
		});
	}
	
	fn_nfileDownload = function(fsn){
		try {
			var $form = $('<form></form>');
			$form.attr('action', "/file/noticefileDownload.do");
			$form.attr('method', 'post');
			$form.attr('target', 'proc_frm');
			$form.appendTo('body');
			var isfsn = $('<input type="hidden" value="' + imgsn + '" name="file_sn">');
			$form.append(isfsn);
			$form.submit();
			
		}catch(E){
			$.unibillDialog.alert('에러', E);
		}
	}
	
	fn_nimgDownload = function(imgsn){
		try {
			var $form = $('<form></form>');
			$form.attr('action', "/file/noticefileDownload.do");
			$form.attr('method', 'post');
			$form.attr('target', 'proc_frm');
			$form.appendTo('body');
			var isfsn = $('<input type="hidden" value="' + imgsn + '" name="file_sn">');
			$form.append(isfsn);
			$form.submit();
			
		}catch(E){
			$.unibillDialog.alert('에러', E);
		}
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_notice.fn_noticeSearch 					= fn_noticeSearch;
	_mod_notice.fn_preNotice 						= fn_preNotice;
	_mod_notice.fn_nextNotice 						= fn_nextNotice;
	_mod_notice.fn_firstNotice 						= fn_firstNotice;
	_mod_notice.fn_lasttNotice 						= fn_lasttNotice;
	_mod_notice.fn_noticeDetail 					= fn_noticeDetail;
	
	_mod_notice.fn_nfileDownload 					= fn_nfileDownload;
	_mod_notice.fn_nimgDownload 					= fn_nimgDownload;




	
	
	return _mod_notice;

}(UNI.NOTICE || {}, jQuery));