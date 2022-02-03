/**
 * 시스템 기본 네임스페이스
 * @namespace {Object} UNI
 */
var UNI = UNI || {};

/**
* @method
* @description 네임스페이스 축약사용 지원을 위한 함수
* @author 홍길동(2015.11.13)
* @param {String} ns_string - 줄여서 사용하고 싶은 FULL 네임스페이스
* @returns {Obecjt} 줄여서 사용가능한 네임스페이스
*/
UNI.ns = function(ns_string) {
	var parts = ns_string.split('.'),
    	parent = UNI,
    	i;

    if(parts[0] === 'UNI'){
    	parts = parts.slice(1);
    }

    for(i=0; i < parts.length ; i +=1){
    	if ( typeof parent[parts[i]] === "undefined") {
			parent[parts[i]] = {};
        }

        parent = parent[parts[i]];
    }
    return parent;
};

var COMMON				= UNI.ns("UNI.COMMON");
var CONTENT				= UNI.ns("UNI.CONTENT");
var MAIN				= UNI.ns("UNI.MAIN");
var UTILE				= UNI.ns("UNI.UTILE");
var GRID				= UNI.ns("UNI.GRID");
var CUSTOM				= UNI.ns("UNI.CUSTOM");

var STATS				= UNI.ns("UNI.STATS");




//기관관리
var ORG			 		= UNI.ns("UNI.ORG");
//파일업로드
var UPFILE			 	= UNI.ns("UNI.UPFILE");
var NOTICE			 	= UNI.ns("UNI.NOTICE"); // 공지사항
var FAQ			 		= UNI.ns("UNI.FAQ"); 	// FAQ
var CUSTAG			 	= UNI.ns("UNI.CUSTAG"); // 커스텀 태그
var OTP					= UNI.ns("UNI.OTP");	// OTP 


