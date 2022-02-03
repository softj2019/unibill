/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.FAQ = (function(_mod_faq, $, undefined){

	

	
	fn_faqSearch = function(spage){
		var faq_type = $("#faq_searchFrm").find("#faq_type option:selected").val();
		var s_faq = $("#faq_searchFrm").find("#s_faq").val();
		var s_page = spage;
		if(typeof s_page == "undefined"){
			s_page = 1;
		}
		var param = {"faq_type": faq_type,  "s_faq": s_faq, "s_page": s_page};
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/faqSearch.json',
			data: param,
			success : function(data) {
				if(data.sucess == true){
					$("#n_page").show();
					$("#faqbody").empty();
					for(var i=0; i<data.nList.length; i++){
						var fsn = "";
						var imgfsn = "";
						if(typeof data.nList[i].fileSn != "undefined" && data.nList[i].fileSn != ""){
							fsn = '<img src="/images/notice/bbs/clip.png">'; 
						}
						$("#faqbody").append('<tr><td>' + data.nList[i].rownum + '</td><td class="fl" onclick="javascript:FAQ.fn_faqDetail(\'' + data.nList[i].ubseq + '\');">'+ data.nList[i].title +'<td>' + fsn + '</td></td><td>' + data.nList[i].regId + '</td><td>' + data.nList[i].regTm + '</td></tr>')
						
						
					}
					var totCnt = '<span>총 게시물: <em>' + data.nCnt + '</em></span>';
					var allPage = '<span><em>' + data.curPage + ' / ' + data.allPage + '</em> Page</span>';
					$("#totCnt").html(totCnt);
					$("#totalPage").html(allPage);
					
					$('.pageNum').remove();
					$('#n_page').append('<span class="pageNum"><a class="nnav first" onclick="javascript:FAQ.fn_firstNotice(' + data.curPage + ');"></a></span>');
					$('#n_page').append('<span class="pageNum"><a class="nnav prev" onclick="javascript:FAQ.fn_preNotice(' + data.curPage + ');"></a></span>');
					
					for(var i=data.startPage; i<=data.endPage; i++){
						var curPage = data.curPage;
						var curPagehtml = "";
						if(curPage == i){
							if(i==1){
								$('#n_page').append('<span class="pageNum"><a onclick="javascript:FAQ.fn_faqSearch(this.innerHTML);" class="on">' + i + '</a></span>');
							}else{
								$('#n_page').append(' <span class="pageNum"><a onclick="javascript:FAQ.fn_faqSearch(this.innerHTML);" class="on">' + i + '</a></span>');
							}
						}else{
							if(i==1){
								$('#n_page').append('<span class="pageNum"><a onclick="javascript:FAQ.fn_faqSearch(this.innerHTML);">' + i + '</a></span>');
							}else{
								$('#n_page').append(' <span class="pageNum"><a onclick="javascript:FAQ.fn_faqSearch(this.innerHTML);">' + i + '</a></span>');
							}
						}
						
						
					}
					$('#n_page').append('<span class="pageNum"><a class="nnav next" onclick="javascript:FAQ.fn_nextNotice(' + data.curPage + ',' + data.allPage +');"></a></span>');
					$('#n_page').append('<span class="pageNum"><a class="nnav last" onclick="javascript:FAQ.fn_lasttNotice(' + data.curPage + ',' + data.allPage +');"></a></span>');
				}else{
					alert("faq search fail!");
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
		alert(startpage);
		if(startpage > 1){
			FAQ.fn_faqSearch((startpage - 2)*10 + 1 );
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
			FAQ.fn_faqSearch((startpage)*10 + 1 );
		}
	}
	
	fn_firstNotice = function(spage){
		FAQ.fn_faqSearch(1);
	}
	
	fn_lasttNotice = function(spage, sallpage){
		FAQ.fn_faqSearch(sallpage);
	}
	
	fn_faqDetail = function(subseq){
		// 팝업 show
		var tx = ($(window).width() -  1075) / 2;
		var ty = ($(window).height() - 650) / 2;
		if(ty < 0){
			ty = 15;
		}
		
		$("#ly_faqList_pop .confirm_cont").css({left:tx+"px",top:ty+"px", 'position':'absolute'});				
		$("#ly_faqList_pop").show();
		
		$.ajax({
			async: false,
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectFAQDetail.json',
			data: 'ubseq=' + subseq,
			datatype : "json",
			success : function(data) {
				
				$("#regtm").html(data.nDetailInfo.regTm);
				$("#regid").html(data.nDetailInfo.regId);
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
					$("#fdown").attr("onclick", 'FAQ.fn_nfileDownload(\'' + data.nDetailInfo.fileSn + '\');');
					
					$("#fileList").html('<tr><td><img src="../../images/notice/bbs/clip.png">&nbsp; ' + data.nDetailInfo.fileNm + '</td></tr>');
				}
				
				$("#nimgfilesz").html(data.nDetailInfo.imgSz);
				
				
				
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
	_mod_faq.fn_faqSearch 						= fn_faqSearch;
	_mod_faq.fn_preNotice 						= fn_preNotice;
	_mod_faq.fn_nextNotice 						= fn_nextNotice;
	_mod_faq.fn_firstNotice 					= fn_firstNotice;
	_mod_faq.fn_lasttNotice 					= fn_lasttNotice;
	_mod_faq.fn_faqDetail 						= fn_faqDetail;
	
	_mod_faq.fn_nfileDownload 					= fn_nfileDownload;
	_mod_faq.fn_nimgDownload 					= fn_nimgDownload;




	
	
	return _mod_faq;

}(UNI.FAQ || {}, jQuery));