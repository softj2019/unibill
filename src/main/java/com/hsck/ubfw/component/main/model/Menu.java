package com.hsck.ubfw.component.main.model;

import java.io.Serializable;

import com.hsck.ubfw.component.comm.vo.BaseVO;

import lombok.Data;

/**
 * @Class Name : Menu.java
 * @Description : 메뉴 VO Class	
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

public @Data class Menu extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String menuId1;      /** 1 dept 메뉴id */
	private String upMenuId1;    /** 1 dept 상위메뉴id */   
	private String menuNm1;      /** 1 dept 메뉴명 */
	private String depth1;       /** 1 dept 레벨 */
	private String menuSeq1;     /** 1 dept 메뉴순서 */
	private String menuId2;      /** 2 dept 메뉴id */
	private String upMenuId2;    /** 2 dept 상위메뉴id */   
	private String menuNm2;      /** 2 dept 메뉴명 */
	private String depth2;       /** 2 dept 레벨 */
	private String menuSeq2;     /** 2 dept 메뉴순서 */
	private String upMenuYn2;    /** 3 dept 메뉴존재여부 */
	private String menuId3;      /** 3 dept 메뉴id */
	private String upMenuId3;    /** 3 dept 상위메뉴id */   
	private String menuNm3;      /** 3 dept 메뉴명 */
	private String depth3;       /** 3 dept 레벨 */
	private String menuSeq3;     /** 3 dept 메뉴순서 */
	private String upMenuYn3;    /** 4 dept 메뉴존재여부 */
	private String menuId4;      /** 4 dept 메뉴id */
	private String upMenuId4;    /** 4 dept 상위메뉴id */   
	private String menuNm4;      /** 4 dept 메뉴명 */
	private String depth4;       /** 4 dept 레벨 */
	private String menuSeq4;     /** 4 dept 메뉴순서 */
	private String url;          /** url */
	private String scrinYn;      /** 화면존재여부 */
	private String roleId;       /** 권한ID */
	private String roleYn;       /** 권한여부 */
		
}