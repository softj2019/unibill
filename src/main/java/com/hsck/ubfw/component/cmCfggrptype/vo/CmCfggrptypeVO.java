package com.hsck.ubfw.component.cmCfggrptype.vo;

import java.io.Serializable;

import com.hsck.ubfw.component.comm.vo.BaseVO;

import lombok.Data;

/**
 * CM_CFGGRPTYPE 테이블에 대응되는 VO 클래스입니다.
 * 
 * @author AA
 * @since 0000.00.00.
 */
public @Data class CmCfggrptypeVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1334016768730686033L;

	private String grpType; /*
							 * cfgbasecd grpType 참조 B:건물 J:조직 C:고객유형 D:문서 F:파일
							 * R:요금항목 O:점용위치 [Size=12] :
							 */
	//private String ubseq; /* 자료순번 [Size=22] : */
	private String grpNm; /* 그룹명 [Size=64] : */
	private String regId; /* 등록자 [Size=32] : */
	private String updId; /* 변경자 [Size=32] : */
	private String regTm; /* 등록시간 [Size=14] : */
	private String updTm; /* 수정시간 [Size=14] : */

	/* 검색 영역 parameters */
	private String searchGrpType; /*
								 * 검색 필드 : cfgbasecd grptype 참조 B:건물 J:조직 C:고객유형
								 * D:문서 F:파일 R:요금항목 O:점용위치
								 */
	private String searchUbseq; /* 검색 필드 : 자료순번 */
	private String searchGrpNm; /* 검색 필드 : 그룹명 */
	private String searchRegId; /* 검색 필드 : 등록자 */
	private String searchUpdId; /* 검색 필드 : 변경자 */
	private String searchRegTm; /* 검색 필드 : 등록시간 */
	//private String searchUpdTm; /* 검색 필드 : 수정시간 */

}
