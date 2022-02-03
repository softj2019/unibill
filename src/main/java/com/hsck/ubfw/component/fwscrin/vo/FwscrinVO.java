
/*******************************************************************************
 * COPYRIGHT(C) 2016 HANSSAK UNIBILL ALL RIGHTS RESERVED.
 * This software is the proprietary information of HANSSAK UNIBILL.
 *
 * Revision History
 * Author   Date            Description
 * ------   ----------      ------------------------------------
 * AA    0000.00.00.         First Draft.( Auto Code Generate )
 *
 *******************************************************************************/
package com.hsck.ubfw.component.fwscrin.vo;

import com.hsck.ubfw.component.comm.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;

/**
 * FWSCRIN 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class FwscrinVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 6795173581241641735L;
    private String scrinId; /* 화면ID [Size=32] [PK_FWSCRIN , position : 1] :  */
    //private String ubseq; /* 자료순번 [Size=22] [ , position : ] : 0  */
    private String scrinNm; /* 화면명 [Size=128] [ , position : ] :  */
    private String tblNm; /* 테이블명 [Size=20] [ , position : ] :  */
    private String xmlId; /* xmlID [Size=32] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 수정시간 [Size=14] [ , position : ] :  */
    private String dtlW; /* 상세가로 [Size=22] [ , position : ] : 0*/
    private String useYn;

    /* 검색 영역 parameters */
    private String searchScrinId; /* 검색 필드 : 화면ID */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchScrinNm; /* 검색 필드 : 화면명 */
    private String searchTblNm; /* 검색 필드 : 테이블명 */
    private String searchXmlId; /* 검색 필드 : xmlID */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 수정시간 */
    private String searchDtlW; /* 검색 필드 : 상세가로 */
    
    private int offsetSize;
    private int limitSize;


}
