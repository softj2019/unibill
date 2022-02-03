/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} JOBMASTER
 */
UNI.JOBCOMMON = (function(_mod_jobcommon, $, undefined){
	
	inputMakeTr = function(name, value, auth, style){
		var disabled = "";   		// readonly, disabled
		if(auth){
			disabled = "readonly";
		}
		
		if(style == undefined) style = "";  // 기본으로 스타일 없는 경우 (수정하고 싶으면 하세요)
		
		return "<input type='text' name='"+name+"' value='"+value+"' "+ disabled +" style='"+style+"'/>";
	}
	
	makeInput = function(type, name, id, val, style, max, readonly, disabled, attr){
		var v = '';
		if(typeof val != 'undefined')	v = val;
		
		var input = "<input type='"+type+"' id='"+id+"' name='"+name+"' value='"+v+"' style='"+style+"' maxlength='"+max+"' ";
		
		if(readonly) input += "readonly='readonly' ";
		if(disabled) input += "disabled='disabled' ";
		if(attr != undefined && attr != '')     input += attr;
		
		input = input + " >";
		
		return input;
	}
	
	selectMakeTr = function(name, dtos, val, auth, style, attr){
		var disabled = "";
		var hiddenTxt = "";
		if(auth){
			disabled = "disabled";
			hiddenTxt = "<input type='hidden' name='" + name + "' value='" + val + "'>";
		}
		
		if(style == undefined) style = "width:99%;";  // 기본으로 스타일 없는 경우 (수정하고 싶으면 하세요)
		if(attr  == undefined) attr = "";
		
		var combo = hiddenTxt+"<select name='"+name+"' style='"+style+"' "+ disabled + " " + attr +"> <option value=''>선택</option>"
		var selected;
		var code;
		var name;
		for(var i =0; i < dtos.length; i++){
			code = dtos[i]["code"];
			name = dtos[i]["name"];
			if(code == val){
				selected = "selected";	
			}else{
				selected = "";
			}
			combo += "<option value='"+code+"' "+selected+">"+ name +"</option>"
		}
		combo += "</select>";
		return combo;
	}
	
	radioMakeTr = function(name, value, auth, attr){
		var disabled = "";   // readonly
		if(auth){
			disabled = "disabled";
		}
		var yChecked = "";
		var nChecked = "";
		if(value == "Y"){
			yChecked = "checked";
		}
		if(value == "N"){
			nChecked = "checked";
		}
		
		var radio = "<input type='radio' value='Y' name='"+name+"' "+yChecked+" "+disabled+" "+attr+">예 <input type='radio' value='N' name='"+name+"' "+nChecked+" "+disabled+" "+attr+">아니오";
		return radio;
	}
	
	radioMakeTr2 = function(name, dtos, val, auth, style, attr){
		var disabled = "";
		var hiddenTxt = "";
		if(auth){
			disabled = "disabled";
			hiddenTxt = "<input type='hidden' name='" + name + "' value='" + val + "'>";
		}
		
		if(style == undefined) style = "width:99%;";  // 기본으로 스타일 없는 경우 (수정하고 싶으면 하세요)
		if(attr  == undefined) attr = "";
		
		var combo = '';
		var checked;
		var code;
		var text;
		for(var i =0; i < dtos.length; i++){
			code = dtos[i]["code"];
			text = dtos[i]["name"];
			if(code == val){
				checked = "checked";	
			}else{
				checked = "";
			}
			combo += "<input type='radio' name='"+name+"' value='"+code+"' "+checked+" "+disabled+">"+ text;
		}
		return combo;
	}
	
	checkboxMakeTr = function(name, auth, dataList, attr, isAll){
		if(dataList.length == 0)	return;
		
		var disabled = "";   // readonly
		if(auth){
			disabled = "disabled";
		}
		var allCheck = false;
		if(isAll){
			allCheck = isAll;
		}
		
		var checkBox='';
		for(var i=0; i<dataList.length; i++){
			if(allCheck){
				if(i == 0)	checkBox += "<input type='checkbox' name='allCheck' onclick='chk_all($(this), &#39;"+name+"&#39;);' checked/>&nbsp;전체&nbsp;&nbsp;"
				
				checkBox += "<input type='checkbox' name='" +name+ "' value='" +dataList[i]["code"]+ "' onclick='chk_one($(this), &#39;"+name+"&#39;);' " +attr+ " "+disabled+" />&nbsp;" +dataList[i]["name"] +"&nbsp;&nbsp;"
			}else{
				checkBox += "<input type='checkbox' name='" +name+ "' value='" +dataList[i]["code"]+ "' " +attr+ " "+disabled+" />&nbsp;" +dataList[i]["name"] +"&nbsp;&nbsp;"	
			}
		}
		
		return checkBox;
	}
	chk_all = function($this, name){
		var td = $this.closest("td");
		if($this.is(":checked")){
			td.find("input:checkbox[name='"+name+"']").prop("checked", true);
		}else{
			td.find("input:checkbox[name='"+name+"']").prop("checked", false);
		}
	}
	chk_one = function($this, name){
		var td = $this.closest("td");
		
		if(td.find("input:checkbox[name='"+name+"']").length == td.find("input:checkbox[name='"+name+"']:checked").length){
			td.find("input:checkbox[name='allCheck']").prop("checked", true);
		}else{
			td.find("input:checkbox[name='allCheck']").prop("checked", false);
		}
	}
	
	chk_undefined = function(val){
		if(val == undefined) val = '';
		return val;
	}
	
	appendItem = function($target, item){
		$target.children().remove();
		$target.text('');
		$target.append(item);
	}
	
	_mod_jobcommon.inputMakeTr    = inputMakeTr;
	_mod_jobcommon.makeInput      = makeInput;
	_mod_jobcommon.selectMakeTr   = selectMakeTr;
	_mod_jobcommon.radioMakeTr    = radioMakeTr;
	_mod_jobcommon.radioMakeTr2   = radioMakeTr2;
	_mod_jobcommon.checkboxMakeTr = checkboxMakeTr;
	_mod_jobcommon.chk_undefined  = chk_undefined;
	_mod_jobcommon.appendItem     = appendItem;
	_mod_jobcommon.chk_all        = chk_all;
	_mod_jobcommon.chk_one        = chk_one;
	
	return _mod_jobcommon;
	
}(UNI.JOBCOMMON|| {}, jQuery));
