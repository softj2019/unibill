/**
 *  공통 컨텐츠 사용되는 그리드
 * @namespace {Object} UNI.CONTENT
 */
UNI.ZIP = (function(_mod_zip, $, undefined){

	
	
	/**
	* @method
	* @description 주소 정보
	* @author 홍성원(2016.12.23)
	*/

	
	// 주소 정보 가져오기
	fn_searchZip = function(){
		// 레이어 팝업 띄우기				 
		var tx = ($(window).width()-$("#ly_zip_pop .confirm_cont").width())/2;
		var ty = ($(window).height())/7;
		if(ty < 0){
			ty = 15;
		}
		$("#ly_zip_pop .confirm_cont").css({left:tx+"px",right:tx+"px",top:ty+"px", 'position':'absolute'});				
		
		$("#ly_zip_pop").show();  // 레이어 팝업 open
	}
	
	fn_execPostCode = function(){
		try{
			new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수
	
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                }
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                $("#frm_detail").find("#zip_no").val(data.zonecode);
	                $("#frm_detail").find("#addr1").val(addr);
	                // 커서를 상세주소 필드로 이동한다.
	                $("#frm_detail").find("#addr2").focus();
	            }
	        }).open();
		}catch(e){
			fn_searchZip();
		}
    }
	
	//------------------------------------------------------------------------------------------------------------------
	//## public 메소드
	//------------------------------------------------------------------------------------------------------------------
	_mod_zip.fn_searchZip						= fn_searchZip;
	_mod_zip.fn_execPostCode					= fn_execPostCode;
	
	
	return _mod_zip;

}(UNI.ZIP || {}, jQuery));