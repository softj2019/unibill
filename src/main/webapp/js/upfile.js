/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.UPFILE = (function(_mod_upfile, $, undefined){

	
	fn_fileAdd = function(){
		/*if($("#frm_fileDetail").find("#upfilenm").val() == ""){
    		$.unibillDialog.alert('오류', "추가할 파일을 선택하세요.");
			return false;
    	}*/
		
		var ids = jQuery("#fileUploadgrid").jqGrid('getDataIDs');
    	var id = 1;
    	
    	for(var i=0; i<ids.length; i++){
    		if(i == 0 ){
    			id = ids[i]*1
    		}else{
    			if(id < ids[i]*1) id = ids[i]*1;
    		}
    		id++;
    	}
    	
    	$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectFileSeq.json',
			datatype : "json",
			success : function(data) {
				
				if(data.result == true){
					//form insert
					var sHtml = '<input type="file" id="' + data.fileSn + '_fileUpload" name="' + data.fileSn + '_fileUpload" style="display: none;" />\n';
			    	$("#frm_detail").find("#id_detail").append(sHtml);
			    	$("#"+ data.fileSn +"_fileUpload").click();
			    	
			    	var agent = navigator.userAgent.toLowerCase();

			    	if(agent.indexOf('trident') > -1 || agent.indexOf('edge/') > -1) {
			    		//그리드 insert
				    	if($("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").val() != ''){
				    		$("#upfilenm").val($("#"+ data.fileSn +"_fileUpload").val());
				    		
				    		var rowdata = {"file_sn":data.fileSn, "nm":$("#frm_detail").find("#"+ data.fileSn +"_fileUpload").val()};
					    	$("#fileUploadgrid").jqGrid("addRowData", id, rowdata, 'last');
				    	}else{
				    	
				    	}
			    	}else if (agent.indexOf("chrome") != -1) {
				    	$(function() {
				    		$("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").change(function (){
				    			//그리드 insert
						    	if($("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").val() != ''){
						    		$("#upfilenm").val($("#"+ data.fileSn +"_fileUpload").val());
						    		
						    		var rowdata = {"file_sn":data.fileSn, "nm":$("#frm_detail").find("#"+ data.fileSn +"_fileUpload").val()};
							    	$("#fileUploadgrid").jqGrid("addRowData", id, rowdata, 'last');
						    	}else{
						    	}
				    	     });
				    	});

			    	}else{
			    		//그리드 insert
				    	if($("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").val() != ''){
				    		$("#upfilenm").val($("#"+ data.fileSn +"_fileUpload").val());
				    		
				    		var rowdata = {"file_sn":data.fileSn, "nm":$("#frm_detail").find("#"+ data.fileSn +"_fileUpload").val()};
					    	$("#fileUploadgrid").jqGrid("addRowData", id, rowdata, 'last');
				    	}else{
				    	
				    	}
			    	}

			    	
			    	
			    }else{
					$.unibillDialog.alert('알림', '파일순번를 가져오는데 실패 하였습니다.');
				}
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
    	
    	
	}
	
	
	fn_fileRemove = function(){
    	var selects = $( "#fileUploadgrid" ).jqGrid('getGridParam', 'selarrrow');
    	var sellen = new Array();
    	
    	for(var i=0; i<selects.length; i++){
    		sellen[i] = selects[i];
    	}
    	for(var i=0; i<sellen.length; i++){
    		//form delete
    		var sRowData = $("#fileUploadgrid").getRowData(selects[i]);	
			var file_sn = sRowData["file_sn"];
    		$("#frm_detail").find("#id_detail").find("#"+ file_sn +"_fileUpload").remove();
    		//grid delete
    		$('#fileUploadgrid').jqGrid('delRowData',sellen[i]);
    	}
    }
	
	
	
	fn_makeUploadFile = function() {
		var formData = new FormData();
		
		var ids = jQuery("#fileUploadgrid").jqGrid('getDataIDs');
		var rowDatas = $("#fileUploadgrid").jqGrid("getRowData");
		var rCustIdList = [];
		if(ids.length == 0){
			$("#file_pop").hide();
		}else{
			for (var i=0; i<rowDatas.length; i++) {
				rCustIdList.push(rowDatas[i]["file_sn"]);
			}
			var ofileuploadnm = $("#frm_detail").find("#id_detail").find('#fileuploadcnt').val();
			var fileuploadnmCnt = ofileuploadnm*1 + rowDatas.length;
			$("#frm_detail").find("#id_detail").find('#fileuploadnm').val("첨부파일 " + fileuploadnmCnt + "개");
			
			$("#file_pop").hide();
			
		}
	}
	
	fn_makeUploadsFile = function() {
		var fnm = $("#upsfilenm").val();
		$("#frm_detail").find("#id_detail").find('#sfileuploadnm').val($("#upsfilenm").val());
		$("#file_pop").hide();
		
		
	}
	
	fn_fileSearch = function(){
		// 그리드 초기화
		COMMON.fn_clear_grid_data("fileUploadgrid");
		
		COMMON.fn_search_mainGrid("frm_fileDetail", "fileUploadgrid");
	}
	
	fn_fileDel = function(){
		var filegrp = $("#frm_fileDetail").find("#filegrp").val();
		
		//alert("fn_fileDel> filegrp="+filegrp);
		var selects = $( "#fileSearchgrid" ).jqGrid('getGridParam', 'selarrrow');
		if (selects.length == 0) {
			$.unibillDialog.alert('알림', '선택한 행이 없습니다.');
			return;
		}
		
    	var sellen = new Array();
    	var filesns = new Array();
    	
    	for(var i=0; i<selects.length; i++){
    		var sRowData = $("#fileSearchgrid").getRowData(selects[i]);	
			var file_sn = sRowData["file_sn"];
    		sellen[i] = selects[i];
    		filesns[i] = file_sn;
    	}
    	//alert("fn_fileDel> filesns="+filesns);
    	var param = {"filegrp": filegrp,  "filesns": filesns};
    	
    	$.ajax({
			async: false,
			traditional : true,
			type: 'POST',
			url: CONTEXT_ROOT + '/content/delFile.json',
			data: param,
			datatype : "json",
			success : function(data) {
				if(data.dbDelResult == "false"){
					$.unibillDialog.alert('에러', "계약에 등록된 파일이 존재 합니다.");
				}else{
					for(var i=0; i<sellen.length; i++){
			    		//grid delete
			    		$('#fileSearchgrid').jqGrid('delRowData',sellen[i]);
			    	}
				}
				
				//첨부파일 개수 조정
				var ofileuploadnm = $("#frm_detail").find("#id_detail").find('#fileuploadcnt').val();
				var fileuploadnmCnt = ofileuploadnm*1 - sellen.length;
				$("#frm_detail").find("#id_detail").find('#fileuploadnm').val("첨부파일 " + fileuploadnmCnt + "개");
				
				
			},
			error: function(xhr, status, error) {				
				$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
			}
		});
    	
    	
    	
    	
		
		
		
	}

	fn_sfileAdd = function(){
		$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectFileSeq.json',
			datatype : "json",
			success : function(data) {
				
				if(data.result == true){
					//form insert
					var sHtml = '<input type="file" id="' + data.fileSn + '_fileUpload" name="' + data.fileSn + '_fileUpload" style="display: none;" />\n';
			    	$("#frm_detail").find("#id_detail").append(sHtml);
			    	$("#"+ data.fileSn +"_fileUpload").click();
			    	
			    	var agent = navigator.userAgent.toLowerCase();
	
			    	if (agent.indexOf("chrome") != -1) {
				    	$(function() {
				    		$("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").change(function (){
				    			if($("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").val() != ''){
				    				//$("#frm_detail").find("#file_sn").val(data.fileSn);
						    		$("#sfileuploadnm").val($("#"+ data.fileSn +"_fileUpload").val());
						    	}
				    	     });
				    	});
	
			    	}else{
			    		if($("#frm_detail").find("#id_detail").find("#"+ data.fileSn +"_fileUpload").val() != ''){
			    			//$("#frm_detail").find("#file_sn").val(data.fileSn);
				    		$("#sfileuploadnm").val($("#"+ data.fileSn +"_fileUpload").val());
				    	}
			    	}
	
			    	
			    	
			    }else{
					$.unibillDialog.alert('알림', '파일순번를 가져오는데 실패 하였습니다.');
				}
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
	}
	
	
	fn_sfileDel = function(){
		$("#frm_detail").find("#id_detail").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$("#frm_detail").find("#file_sn").val("");
		$("#sfileuploadnm").val("");
    	
	}
	
	
	fn_ifileAdd = function(){
		$("#frm_detail").find("#imgfile").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/content/selectFileSeq.json',
			datatype : "json",
			success : function(data) {
				
				if(data.result == true){
					//form insert
					var sHtml = '<input type="file" id="' + data.fileSn + '_imgUpload" name="' + data.fileSn + '_imgUpload" style="display: none;" />\n';
			    	$("#frm_detail").find("#imgfile").append(sHtml);
			    	$("#"+ data.fileSn +"_imgUpload").click();
			    	
			    	var agent = navigator.userAgent.toLowerCase();
	
			    	if (agent.indexOf("chrome") != -1) {
				    	$(function() {
				    		$("#frm_detail").find("#imgfile").find("#"+ data.fileSn +"_imgUpload").change(function (){
				    			if($("#frm_detail").find("#imgfile").find("#"+ data.fileSn +"_imgUpload").val() != ''){
				    				//$("#frm_detail").find("#file_sn").val(data.fileSn);
						    		$("#imgfileuploadnm").val($("#"+ data.fileSn +"_imgUpload").val());
						    	}
				    	     });
				    	});
	
			    	}else{
			    		if($("#frm_detail").find("#imgfile").find("#"+ data.fileSn +"_imgUpload").val() != ''){
			    			//$("#frm_detail").find("#file_sn").val(data.fileSn);
				    		$("#imgfileuploadnm").val($("#"+ data.fileSn +"_imgUpload").val());
				    	}
			    	}
	
			    	
			    	
			    }else{
					$.unibillDialog.alert('알림', '파일순번를 가져오는데 실패 하였습니다.');
				}
				
			},
			error: function(xhr, status, error) {
				alert("에러");
			}
		});
	}
	
	fn_ifileDel = function(){
		$("#frm_detail").find("#imgfile").find("input[type='file']").each( function(){
			$(this).remove();
		});
		
		$("#frm_detail").find("#file_sn").val("");
		$("#imgfileuploadnm").val("");
    	
	}
	// 공통 파일 업로드 ------------------------------------------------------------------------------------------------------------------
	
	//파일 순번 가져오기
	fn_getfilesn = function(){
		var param = {"key_nm": "file_sn"};		
		var flieno = ""; 
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT + '/receipt/getseqstr.do',
			datatype : "json",
			traditoinal: true,
			data: param ,
			async: false ,
			success : function(data) {   
				//$("#frm_detail").find("#pnumber").attr("value", data.filesn);
				
				flieno = data.filesn;
				
			},
			error: function(xhr, status, error) {
				$.unibillDialog.alert('알림', '파일번호 생성이 오류가 발생하였습니다');
			}
		});
		return flieno;
	}
	
	//파일이름 넣기
	fn_putfilenm =function(cfilenm, pnumber){
		var fileVal = $('#' + 't' + pnumber + '_').val();
		var addFileExt = $("#frm_detail").find("#addFileExt").val();
		addFileExt = addFileExt.replace("[","").replace("]","")
		addFileExt = addFileExt.replace(/ /g,"");
		addFileExt = addFileExt.toLowerCase();
		
		var test = addFileExt.split(",");
		//alert(test[0] + "$"+ test[1] + "$" + test[2]+"$");
	    if( fileVal != "" ){
	        var ext = fileVal.split('.').pop().toLowerCase(); //확장자분리
	        //alert(ext);
	        //아래 확장자가 있는지 체크
	        if($.inArray(ext, test) == -1) {
		       $.unibillDialog.alert('알림', addFileExt + '파일만 업로드 할수 있습니다.');
		       $('#' + 't' + pnumber + '_').val(""); //같은 파일 선택시 alert 안뜨는 오류 처리
		       return;
	        }
	    }
		$("#frm_detail").find("#btn_fileDelete"+ (pnumber)).attr("style", "display:inline");
		$("#frm_detail").find("#cfilenm"+ (pnumber)).val(cfilenm.replace('C:\\fakepath\\', ''));
		$("#frm_detail").find("#cfilelabel"+ (pnumber)).hide();
	}
	
	//추가버튼 
	fn_cfileAdd = function(){
		
		if($('#'+ $("#frm_detail").find("#lastfileid").val()).val() == "선택된 파일이 없습니다"){
			$.unibillDialog.alert('알림', '파일을 등록해주세요');
			return;
		}else{	
			var pnumber = UPFILE.fn_getfilesn();
			$("#frm_detail").find("#lastfileid").val("cfilenm" + pnumber);
			
			var str = '<p><input type="text" id="cfilenm'+pnumber+'" name="cfilenm'+pnumber+'" value="선택된 파일이 없습니다" style="width: 250px;" readonly><label id="cfilelabel'+pnumber+'" className="cfile_button" for="t'+pnumber+'_"><img class="btn_search" style="vertical-align:bottom;"></img></label><input type="file" id="t'+pnumber+'_" name="t'+pnumber+'_" onchange="UPFILE.fn_putfilenm(this.value, '+ pnumber +')" style="display:none;"><button type="button" id="btn_fileDelete'+pnumber+'" name="btn_fileDelete'+pnumber+'" onclick="UPFILE.fn_cfileDelete($(this))" class="btn_fminus" title="삭제" style="display:none"></button></p>';
			$("#frm_detail").find("#fileDiv").append(str);
		}
	}
	
	//삭제버튼
	fn_cfileDelete = function(obj){
		obj.parent().remove();
	}
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------

	_mod_upfile.fn_fileAdd 							= fn_fileAdd;
	_mod_upfile.fn_fileRemove 						= fn_fileRemove;
	_mod_upfile.fn_makeUploadFile 					= fn_makeUploadFile;
	_mod_upfile.fn_fileSearch 						= fn_fileSearch; 
	_mod_upfile.fn_fileDel 							= fn_fileDel;
	
	_mod_upfile.fn_sfileAdd 						= fn_sfileAdd;
	_mod_upfile.fn_sfileDel 						= fn_sfileDel;
	_mod_upfile.fn_makeUploadsFile 					= fn_makeUploadsFile;
	
	_mod_upfile.fn_ifileAdd 						= fn_ifileAdd;
	_mod_upfile.fn_ifileDel 						= fn_ifileDel;
	
	
	// 공통 파일 업로드 ------------------------------------------------------------------------------------------------------------------
	_mod_upfile.fn_cfileAdd							= fn_cfileAdd;
	_mod_upfile.fn_cfileDelete 						= fn_cfileDelete;
	_mod_upfile.fn_putfilenm	 					= fn_putfilenm;
	_mod_upfile.fn_getfilesn						= fn_getfilesn;
	return _mod_upfile;

}(UNI.UPFILE || {}, jQuery));