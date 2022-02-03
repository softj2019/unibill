
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
package com.hsck.ubfw.component.fwmenu.vo;

import com.hsck.ubfw.component.comm.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;


/**
 * FWMENU 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class FwmenuVO extends BaseVO implements Serializable {


    private static final long serialVersionUID = -4413139159652670122L;
    private String menuId; /* 메뉴ID [Size=32] [PK_FWMENU1 , position : 1] :  */
    //private String ubseq; /* 자료순번 [Size=22] [ , position : ] : 0  */
    private String menuNm; /* 메뉴명 [Size=128] [ , position : ] :  */
    private String depth; /* 레벨 [Size=22] [ , position : ] :  */
    private String menuSeq; /* 메뉴순서 [Size=22] [ , position : ] :  */
    private String upMenuId; /* 상위메뉴ID [Size=32] [ , position : ] :  */
    private String remark; /* 설명 [Size=512] [ , position : ] :  */
    private String url; /* url [Size=256] [ , position : ] :  */
    private String help; /* 도움말 [Size=512] [ , position : ] :  */
    private String tmpType; /* 템플릿구분 ( A : 관리화면
B : 이력화면
C : 탭화면
D : 그리드편집
E : 통계 ) [Size=1] [ , position : ] :  */
    private String tblNm; /* 테이블명 [Size=20] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 변경시간 [Size=14] [ , position : ] :  */
    private String lpadMenuNm;
    private String lpadMenuNm2;
    private String isLeaf;
    private String expanded;
    private String loaded;

    /* 검색 영역 parameters */
    private String searchMenuId; /* 검색 필드 : 메뉴ID */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchMenuNm; /* 검색 필드 : 메뉴명 */
    private String searchDepth; /* 검색 필드 : 레벨 */
    private String searchMenuSeq; /* 검색 필드 : 메뉴순서 */
    private String searchUpMenuId; /* 검색 필드 : 상위메뉴ID */
    private String searchRemark; /* 검색 필드 : 설명 */
    private String searchUrl; /* 검색 필드 : url */
    private String searchHelp; /* 검색 필드 : 도움말 */
    private String searchTmpType; /* 검색 필드 : 템플릿구분 */
    private String searchTblNm; /* 검색 필드 : 테이블명 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 변경시간 */


    public boolean initContains(FwmenuVO ccVO) {
        boolean returnObj = false;

        if( (this.getMenuId()).equals( ccVO.getUpMenuId() ) ){

                returnObj = true;
        }
        return returnObj;
    }

    @Override
    public String toString() {
        return "FwmenuVO{" +
                "menuId='" + menuId + '\'' +
                ", menuNm='" + menuNm + '\'' +
                ", depth='" + depth + '\'' +
                ", menuSeq='" + menuSeq + '\'' +
                ", upMenuId='" + upMenuId + '\'' +
                ", remark='" + remark + '\'' +
                ", url='" + url + '\'' +
                ", help='" + help + '\'' +
                ", tmpType='" + tmpType + '\'' +
                ", tblNm='" + tblNm + '\'' +
                ", lpadMenuNm='" + lpadMenuNm + '\'' +
                ", lpadMenuNm2='" + lpadMenuNm2 + '\'' +
                ", isLeaf='" + isLeaf + '\'' +
                ", expanded='" + expanded + '\'' +
                ", loaded='" + loaded + '\'' +
                ", searchMenuId='" + searchMenuId + '\'' +
                ", searchUbseq='" + searchUbseq + '\'' +
                ", searchMenuNm='" + searchMenuNm + '\'' +
                ", searchDepth='" + searchDepth + '\'' +
                ", searchMenuSeq='" + searchMenuSeq + '\'' +
                ", searchUpMenuId='" + searchUpMenuId + '\'' +
                ", searchRemark='" + searchRemark + '\'' +
                ", searchUrl='" + searchUrl + '\'' +
                ", searchHelp='" + searchHelp + '\'' +
                ", searchTmpType='" + searchTmpType + '\'' +
                ", searchTblNm='" + searchTblNm + '\'' +
                '}';
    }
}
