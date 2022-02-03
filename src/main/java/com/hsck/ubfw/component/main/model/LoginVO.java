package com.hsck.ubfw.component.main.model;

import lombok.Data;

/**
 * @Class Name : LoginVO.java
 * @Description : 사용자정보 VO Class	
 * @Modification Information
 * @author 홍길동
 * @since 2016.10.04
 * @version 1.0.0
 * @see
 *
 * <pre>	
 * << 개정이력(Modification Information) >>
 *	
 *  수정일       수정자      수정내용
 *  ----------   --------    ---------------------------
 *  2016.10.04   홍길동	   	 최초 생성	
 * </pre>	
 */	

public @Data class LoginVO {

	private String ubseq;                /** 자료순번 */
	private String userId;               /** 사용자ID */
	private String userNm;               /** 사용자명 */
	private String pass;                 /** 사용자 비밀번호 */
	private String oldPass;              /** 사용자 비밀번호 */
	private String newPass;              /** 사용자 비밀번호 */
	private String newPass2;             /** 사용자 비밀번호 */
	private String roleId;               /** 권한ID */
	private String dayCnt;               /** 로그인경과일 */
	private String bookmarkPos;          /** 즐겨찾기위치 */
	private String lastloginDt;          /** 마지막로그인시간 */
	private String passChangeYn;         /** 비밀번호 강제 변경 여부 ( Y / N ) */
	private String previousPasswordCnt;  /** 몇회 이전 비밀번호는 다시 사용하지 못하게하는 정책에 따른 값. */
	private String deptGrpId;            /** 로그인한파트너 */
	private String deptGrpNm;            /** 부서명 */
	private String repCustId;            /** 대표고객ID */
	private String custId;               /** 고객ID */
	private String ipchkYn;				 /** IP검사여부 */
	private String failcnt;				 /** 암호실패횟수 */
	private String loginYn;				 /** 로그인 중복여부 */
	private String hisubseq;             /** 자료순번 */
	private String agreeYn;				 /** 동의여부 */
	private String dupYn;				 /** 중복로그인허용여부 */
	private String roleLevel;			 /** 권한레벨 */
	private String grpCd;				 /** 조직*/
	private String firstloginYn;		 /** 처음로그인여부*/
	private String curDate;		 		 /** 현재날짜*/
}