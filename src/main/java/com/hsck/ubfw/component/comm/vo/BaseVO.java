package com.hsck.ubfw.component.comm.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class BaseVO implements Serializable {

	private static final long serialVersionUID = 2305504224035853632L;

	/** 테이블 별 보조 식별키 */
	private String ubseq = "";
	private String delUbseq; /* ',' 로 split 하여 사용하게됨.*/
	private String[] delUbseqList;


	// 공통 컴포넌트에서 이용하는 파라메터.
	private String scrinCode;   // 화면id
	private String tableName;  // 테이블명
	private String actionFlag; // 처리 상태
	private String ubSeqArr;   // 삭제대상 데이터
	private String showyn;         // 검색조건 출력여부
	private String findShowYn;         // 검색조건 출력여부
	private String dtlShowYn;        // 상세 출력여부
	private String roleCode;  // 권한id
	private List insertColumnsList;
	private List insertDataList;
	private List updateColumnsList;
	private String masterTableName;   // 테이블명 (key 컬럼 조회용)



	/** 검색조건 */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 현재페이지 (jqGrid 제공 페이징을 쓰는경우 jqGrid 내부적으로 쓰는 페이지 인덱스) */
	private int page = 1;

    /** 페이지갯수 (페이지 네비게이션에 보일 페이지 갯수) */
    private int pageUnit = 10;

	/** 페이지 사이즈 (페이지 네비게이션에 보일 페이지 묶음 수) */
	private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** 페이지당 보여야할 글수 */
    private int recordCountPerPage = 10;
	/** 페이지당 보여야할 글수 ( jqGrid 제공 페이징을 쓰는 경우 jqGrid 내부적으로 쓰는 한 페이지당 보여질 줄(row)수 */
	private int rows = 10;

	/** select total count */
    private int totalRecordCount = 0;

	/**
	 * jqGrid 컬럼 정렬 정보 : 컬럼 id
	 */
	private String sidx = "";
	/**
	 * jqGrid 컬럼 정렬 정보 : 정렬 방식 (desc,asc)
	 */
	private String sord = "";

    /** login usr_id */
    private String sessionUserId = "hanssak1";
    
    /** login usr_name */
    private String sessionUserNm = "시스템_관리자";

    public String sqlBackupStr = "";

	/**
	 * jqGrid 에서 사용하는 값을 전역으로 사용하는 값에도 셋팅.
	 * @param page 현재페이지 (jqGrid 제공 페이징을 쓰는경우 jqGrid 내부적으로 쓰는 페이지 인덱스)
	 */
    public void setPage(int page){
		this.page = page;
		this.pageIndex = page;
	}

	/**
	 * jqGrid 에서 사용하는 값을 전역으로 사용하는 값에도 셋팅.
	 * @param rows 페이지당 보여야할 글수 ( jqGrid 제공 페이징을 쓰는 경우 jqGrid 내부적으로 쓰는 한 페이지당 보여질 줄(row)수
	 */
	public void setRows(int rows){
    	this.rows = rows;
    	this.recordCountPerPage = rows;
	}

	/**
	 * jqGrid에서 넘언온 컬럼 정보는 camel 표기법으로 넘어오도록 작업한 경우에 DB정렬할 경우 '_' 형태로 컬럼 명을 바꿔줘야하는 경우에 사용함.
	 * @param sidx
	 */
	public void setSidx(String sidx){
//		this.sidx = sidx;

		Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(sidx);

		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "_"+m.group().toLowerCase());
		}
		m.appendTail(sb);

		this.sidx = sb.toString();

	}
}
