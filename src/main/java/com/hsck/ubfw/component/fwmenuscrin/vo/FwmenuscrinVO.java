
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
package com.hsck.ubfw.component.fwmenuscrin.vo;


import com.hsck.ubfw.component.comm.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;

/**
 * FWMENUSCRIN 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class FwmenuscrinVO extends BaseVO implements Serializable {


    private static final long serialVersionUID = -4202546320386791801L;
    private String menuId; /* 메뉴ID [Size=32] [PK_FWMENUSCRIN , position : 1] :  */
    private String scrinId; /* 화면ID [Size=32] [PK_FWMENUSCRIN , position : 2] :  */
    private String menuNm;
    private String scrinNm;
//    private String ubseq; /* 자료순번 [Size=22] [ , position : ] : 0  */
    private String showSn; /* 화면출력순번 [Size=22] [ , position : ] : 0 */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 수정시간 [Size=14] [ , position : ] :  */
    private String gridhg; /* 그리드높이 [Size=22] [ , position : ] : 0*/
    private String dtlTmpType; /* 상세템플릿구분 */

    /* 검색 영역 parameters */
    private String searchMenuId; /* 검색 필드 : 메뉴ID */
    private String searchScrinId; /* 검색 필드 : 화면ID */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchShowSn; /* 검색 필드 : 화면출력순번 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 수정시간 */
    private String searchGridhg; /* 검색 필드 : 그리드높이 */

    private int offsetSize;
    private int limitSize;

}
