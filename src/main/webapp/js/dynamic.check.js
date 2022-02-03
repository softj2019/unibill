function dynamicCheck(code_gubun, function_id, form_id, select_object_id, object_id, where_param, selected_value, code_view_yn, code_value) {

//	alert("[code_gubun : " + code_gubun + "]\n[function_id : " + function_id + "]\n[form_id : " + form_id + "]\n[select_object_id : " + select_object_id + "]\n[where_param : " + where_param + "]\n[selected_value : " + selected_value + "]");

	var sHtml = "";
	var data = "code_gubun="+code_gubun+"&grp_cd="+where_param+"&obj_id="+select_object_id+"&code_value="+code_value;

	$.ajax({
		type: "post",
		dataType: "json",
		data: data,
		url: function_id,
		success: function(data) {
			
			if(data.rows.length > 0) {
				for (var i=0; i<data.rows.length; i++) {		
					var code = data.rows[i]["code"];
					var name = data.rows[i]["name"];

					if (code == selected_value) {
						sHtml += '<input type="checkbox" id="'+object_id+'" name="'+object_id+'" value="'+code+'" checked> ' + name + ' ';
					} else {
						sHtml += '<input type="checkbox" id="'+object_id+'" name="'+object_id+'" value="'+code+'"> ' + name + ' ';
					}
				}
			}

			try {
				// check 생성
				$("#"+select_object_id).html(sHtml);
								
			} catch(E) {}
		},
		error: function(xhr, status, error) {
			$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
		}
	});
}