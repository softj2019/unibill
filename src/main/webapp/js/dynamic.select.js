function dynamicSelect(code_gubun, function_id, form_id, select_object_id, where_param, selected_value, code_view_yn, code_value) {

//	alert("[code_gubun : " + code_gubun + "]\n[function_id : " + function_id + "]\n[form_id : " + form_id + "]\n[select_object_id : " + select_object_id + "]\n[where_param : " + where_param + "]\n[selected_value : " + selected_value + "]");

	var data = "code_gubun="+code_gubun+"&grp_cd="+where_param+"&obj_id="+select_object_id+"&code_value="+code_value;

	$.ajax({
		type: "post",
		dataType: "json",
		data: data,
		url: function_id,
		success: function(data) {
			
			try {

				var sOptionArr = {};
				sOptionArr[""] = "선택";
				
				if(data.rows.length > 0) {
					for (var i=0; i<data.rows.length; i++) {		
						var code = data.rows[i]["code"];
						var name = data.rows[i]["name"];
						
						if (code_view_yn == "Y") {
							sOptionArr[code] = name + " ("+code+")";
						} else {
							sOptionArr[code] = name;
						}
					}
				}
				
				// 콤보 생성
				$("#"+form_id).find("#"+select_object_id).selectBox('options', sOptionArr);
				
				/*$("#frm_reqst").find("#svc_id").selectBox('options', {
                        '1': 'Value 1',
                        '2': 'Value 2',
                        '3': 'Value 3',
                        '4': 'Value 4',
                        '5': 'Value 5'
                    });*/
				// 콤보 selected
				$("#"+form_id).find("#"+select_object_id).selectBox('value', selected_value);				
				
			} catch(E) {}
		},
		error: function(xhr, status, error) {
			$.unibillDialog.alert('에러', xhr + "</br>" + status + "</br>" + error);
		}
	});
}