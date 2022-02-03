
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
package com.hsck.ubfw.component.fwform.vo;

import com.hsck.ubfw.component.comm.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;

/**
 * FWFORM 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class FwformVO extends BaseVO implements Serializable {


    private static final long serialVersionUID = 7210888240311009954L;
    private String menuId;
    private String scrinId; /* 화면ID [Size=32] [PK_FWFORM , position : 1] :  */
    private String scrinIds;
    private String[] scrinIdList; /* 여러건의 PK(화면ID) 리스트 : 삭제 목록 */
    private String objId; /* 객체ID [Size=32] [PK_FWFORM , position : 2] :  */
    private String objIds;
    private String[] objIdList; /* 여러건의 PK(객체ID) 리스트 : 삭제 목록 */
    private String menuNm;
    private String scrinNm;
    private String objNm;
    //private String ubseq; /* 자료순번 [Size=22] [ , position : ] : 0  */
    private String upObjId; /* 상위객체ID [Size=32] [ , position : ] : 0 */
    private String findItemW; /* 검색항목가로 [Size=10] [ , position : ] :  */
    private String rowPos; /* 검색행위치 [Size=22] [ , position : ] : 0 */
    private String colPos; /* 검색열위치 [Size=22] [ , position : ] : 0 */
    private String w; /* 검색객체넓이 [Size=10] [ , position : ] :  */
    private String xmlId; /* xmlID [Size=32] [ , position : ] :  */
    private String findShowYn; /* 검색출력여부 [Size=1] [ , position : ] :  */
    private String mustYn; /* 검색필수여부 [Size=1] [ , position : ] :  */
    private String dtlShowYn; /* 상세출력여부 [Size=1] [ , position : ] :  */
    private String dtlObjW; /* 상세객체넓이 [Size=22] [ , position : ] : 0 */
    private String dtlRowPos; /* 상세행위치 [Size=22] [ , position : ] :  */
    private String dtlColPos; /* 상세열위치 [Size=22] [ , position : ] : 0 */
    private String dtlRowMgr; /* 상세행병합 [Size=22] [ , position : ] : 0 */
    private String dtlMustYn; /* 상세필수여부 [Size=1] [ , position : ] :  */
    private String showSn; /* 그리드출력순번 [Size=22] [ , position : ] : 0 */
    private String showType; /* 그리드출력여부 [Size=1] [ , position : ] :  */
    private String cellW; /* 그리드셀가로 [Size=22] [ , position : ] : 0 */
    private String cellSort; /* 그리드셀정렬 [Size=10] [ , position : ] :  */
    private String color; /* 그리드컬러 [Size=16] [ , position : ] :  */
    private String font; /* 그리드폰트 [Size=32] [ , position : ] :  */
    private String bgColor; /* 그리드배경색 [Size=16] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 변경시간 [Size=14] [ , position : ] :  */
    private String dayFindYn; /* 일자검색여부 [Size=1] [ , position : ] :  */
    private String dtlRoleRowMgr; /* 상세권한행병합 [Size=22] [ , position : ] : 0 */

    private String objType; /* 객체타입 [Size=32] [ , position : ] :  */
    private String cryptoYn; /* 암호화여부 [Size=1] [ , position : ] :  */
    private String extFun; /* 실행함수 [Size=128] [ , position : ] :  */
    private String objSql; /* SQL문 [Size=2048] [ , position : ] :  */
    private String findType; /*검색조건*/

    /* 검색 영역 parameters */
    private String searchScrinId; /* 검색 필드 : 화면ID */
    private String searchObjId; /* 검색 필드 : 객체ID */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchUpObjId; /* 검색 필드 : 상위객체ID */
    private String searchFindItemW; /* 검색 필드 : 검색항목가로 */
    private String searchRowPos; /* 검색 필드 : 검색행위치 */
    private String searchColPos; /* 검색 필드 : 검색열위치 */
    private String searchW; /* 검색 필드 : 검색객체넓이 */
    private String searchXmlId; /* 검색 필드 : xmlID */
    private String searchFindShowYn; /* 검색 필드 : 검색출력여부 */
    private String searchDtlShowYn; /* 검색 필드 : 상세출력여부 */
    private String searchDtlObjW; /* 검색 필드 : 상세객체넓이 */
    private String searchDtlRowPos; /* 검색 필드 : 상세행위치 */
    private String searchDtlColPos; /* 검색 필드 : 상세열위치 */
    private String searchDtlRowMgr; /* 검색 필드 : 상세행병합 */
    private String searchDtlMustYn; /* 검색 필드 : 상세필수여부 */
    private String searchShowSn; /* 검색 필드 : 그리드출력순번 */
    private String searchShowType; /* 검색 필드 : 그리드출력여부 */
    private String searchCellW; /* 검색 필드 : 그리드셀가로 */
    private String searchCellSort; /* 검색 필드 : 그리드셀정렬 */
    private String searchColor; /* 검색 필드 : 그리드컬러 */
    private String searchFont; /* 검색 필드 : 그리드폰트 */
    private String searchBgColor; /* 검색 필드 : 그리드배경색 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 변경시간 */
    private String searchDayFindYn; /* 검색 필드 : 일자검색여부 */
    private String searchDtlRoleRowMgr; /* 검색 필드 : 상세권한행병합 */

    private String searchObjType; /* 검색 필드 : 객체타입 */
    private String searchCryptoYn; /* 검색 필드 : 암호화여부 */
    private String searchExtFun; /* 검색 필드 : 실행함수 */
    private String searchObjSql; /* 검색 필드 : SQL문 */
    
    private int offsetSize;
    private int limitSize;


    public String[] getScrinIdList() {
        return scrinIdList;
    }

    public void setScrinIdList(String[] scrinIdList) {
        this.scrinIdList = scrinIdList;
    }

    public String[] getObjIdList() {
        return objIdList;
    }

    public void setObjIdList(String[] objIdList) {
        this.objIdList = objIdList;
    }

}
