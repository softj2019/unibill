
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
package com.hsck.ubfw.component.fwobj.vo;


import com.hsck.ubfw.component.comm.vo.BaseVO;
import egovframework.com.cmm.service.Globals;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * FWOBJ 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class FwobjVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2723718287442281574L;

    private String dbName; // marinadb 인경우 TABLE_SCHEMA 가 필요함.

    private String tableNameFrom; /* database 가 가지고있는 테이블 정보에서 추출한 테이블 이름 */
    private String objIdFrom; /* database 가 가지고있는 테이블 정보에서 추출한 컬럼 이름 */
    private String objNmFrom; /* database 가 가지고있는 테이블 정보에서 추출한 컬럼 주석 이름 */
    private String colIdFrom; /* database 가 가지고있는 테이블 정보에서 추출한 컬럼 이름 */
    
    
    private String objId; /* 객체ID [Size=32] [PK_FWOBJ , position : 1] :  */
    private String objNm; /* 객체명 [Size=150] [ , position : ] :  */
    private String colId; /* 컬럼ID [Size=32] [ , position : ] :  */
    private String objType; /* 객체 형식 [Size=32] [ , position : ] :  */
    private String objPos; /* 객체 위치 [Size=1] [ , position : ] :  */
    private String xmlId; /* xml ID [Size=32] [ , position : ] :  */
    private String grpCd; /* 그룹코드 [Size=63] [ , position : ] :  */
    private String remark; /* 설명 [Size=128] [ , position : ] :  */
    private String autoGenCol; /* 자동완성여부 [Size=1] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 변경시간 [Size=14] [ , position : ] :  */
    //private String ubseq; /* 자료순번 [Size=22] [ , position : ] :  */
    private String grpType; /* 그룹타입 [Size=20] [ , position : ] :  */
    private String objSql; /*  [Size=2048] [ , position : ] :  */

    /* 검색 영역 parameters */
    private String searchTableName; /* 검색 필드 : 객체화 할 대상 테이블 이름 */
    private String searchObjId; /* 검색 필드 : 객체ID */
    private String searchObjNm; /* 검색 필드 : 객체명 */
    private String searchColId; /* 검색 필드 : 컬럼ID */
    private String searchObjType; /* 검색 필드 : 객체 형식 */
    private String searchObjPos; /* 검색 필드 : 객체 위치 */
    private String searchXmlId; /* 검색 필드 : xml ID */
    private String searchGrpCd; /* 검색 필드 : 그룹코드 */
    private String searchRemark; /* 검색 필드 : 설명 */
    private String searchAutoGenCol; /* 검색 필드 : 자동완성여부 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 변경시간 */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchGrpType; /* 검색 필드 : 그룹타입 */
    private String searchObjSql; /* 검색 필드 :  */
    
    
    private int offsetSize;
    private int limitSize;
    

    public String getDbName(){
        String dbNameStr = this.dbName;

        if( "Oracle".equals( Globals.DB_TYPE )){

            dbNameStr = StringUtils.substringAfterLast(Globals.DB_URL,":");
        }else if( "Maria".equals( Globals.DB_TYPE )){

            dbNameStr = StringUtils.substringAfterLast(Globals.DB_URL,"/");
        }else if( "MsSql".equals( Globals.DB_TYPE )){

            dbNameStr = StringUtils.substringAfterLast(Globals.DB_URL,"=");
        }

        setDbName( StringUtils.trimToEmpty(dbNameStr) );
        return this.dbName;
    }
}
