package com.hsck.ubfw.component.com.cmm.vo;

import lombok.Data;

/**
 * @Class Name : FileVO.java
 * @Description : 첨부파일 VO Class	
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

public @Data class FileVO {
	
	private int     fileSn;   /** 파일순번 */
	private String  ubseq;    /** 자료순번 */
	private String  fileNm;   /** 파일명 */
	private byte[]  rawdata;  /** 자료 */
}