
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
package com.hsck.ubfw.component.hiswebreq.vo;

import com.hsck.ubfw.component.comm.vo.BaseVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


/**
 * UI요청작업관리(CM_HISWEBREQ) 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class CmHiswebreqVO extends BaseVO implements Serializable {


    private static final long serialVersionUID = 1842863684445934428L;
//    private String ubseq; /* 자료순번 [Size=22] [PK_CM_HISWEBREQ , position : 1] :  */
//    private String ubseqs;
    private java.math.BigDecimal[] ubseqList; /* 여러건의 PK(자료순번) 리스트 : 삭제 목록 */
    private String pbxSn="0"; /* 교환기순번 [Size=22] [ , position : ] :  */
    private String jobType= "4"; /* 작업유형 [Size=22] [ , position : ] :  */
    private String sday="00000000"; /* 시작일 [Size=8] [ , position : ] :  */
    private String eday; /* 종료일 [Size=8] [ , position : ] :  */
    private String procNm="procNm"; /* 프로세스명 [Size=256] [ , position : ] :  */
    private String para; /* 파라미터 [Size=128] [ , position : ] :  */
    private String reqStatus; /* 요청상태 [Size=1] [ , position : ] :  C=취소,F=실패,P=진행중,R=요청,S=성공,X=취소완료 (SELECT * FROM CM_CFGBASECD WHERE GRP_CD = 'REQ_STATUS') */
    private String pid; /* pid [Size=22] [ , position : ] :  */
    private String progress; /* 진행율 [Size=22] [ , position : ] :  */
    private String msg; /* 메시지 [Size=128] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 변경시간 [Size=14] [ , position : ] :  */
    

    private String billYm; /* 정산월 */
    private String billYmLastDay; /* 정산월의 말일의 날짜만 반환 */

    private String beforeBillYm; /* (정산월-1)월 */
    private String beforeBillYmLastDay; /* (정산월-1)월의 말일의 날짜만 반환 */

    private String hiswebreqFindday; /* 작업구분 */
    private String custIdParam;
    private String repCustId;
    private String autotransMonth;
    private String svcId;

    private String reqStatusLabel;
    private String progressLabel;
    private Object ubSeqArr2;

    /* 검색 영역 parameters */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchPbxSn; /* 검색 필드 : 교환기순번 */
    private String searchJobType; /* 검색 필드 : 작업유형 */
    private String searchSday; /* 검색 필드 : 시작일 */
    private String searchEday; /* 검색 필드 : 종료일 */
    private String searchProcNm; /* 검색 필드 : 프로세스명 */
    private String searchPara; /* 검색 필드 : 파라미터 */
    private String searchReqStatus; /* 검색 필드 : 요청상태 */
    private String searchPid; /* 검색 필드 : pid */
    private String searchProgress; /* 검색 필드 : 진행율 */
    private String searchMsg; /* 검색 필드 : 메시지 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    private String searchRegTm; /* 검색 필드 : 등록시간 */
    //private String searchUpdTm; /* 검색 필드 : 변경시간 */


    public java.math.BigDecimal[] getUbseqList() {
        return ubseqList;
    }

    public void setUbseqList(java.math.BigDecimal[] ubseqList) {
        this.ubseqList = ubseqList;
    }

    /*  pbxSn : NOT NULL  , default :   */
    public void setPbxSn(String pbxSn) {
        if (pbxSn == null) {
            this.pbxSn = "0";
        } else {
            this.pbxSn = pbxSn;
        }
    }

    /*  jobType : NOT NULL  , default :   */
    public void setJobType(String jobType) {
        if (jobType == null) {
            this.jobType = "4";
        } else {
            this.jobType = jobType;
        }
    }

    /*  sday : NOT NULL  , default :   */
    public void setSday(String sday) {
        if (StringUtils.isBlank(sday)) {
            this.sday = "00000000";
        } else {
            this.sday = sday;
        }
    }

    /*  procNm : NOT NULL  , default :   */
    public void setProcNm(String procNm) {
        if (StringUtils.isBlank(procNm)) {
            this.procNm = "procNm";
        } else {
            this.procNm = procNm;
        }
    }

    /*  updId : NOT NULL  , default :   */
    public void setUpdId(String updId) {
        if (StringUtils.isBlank(updId)) {
            this.updId = "";
        } else {
            this.updId = updId;
        }
    }

    /*  updTm : NOT NULL  , default :   */
    public void setUpdTm(String updTm) {
        if (StringUtils.isBlank(updTm)) {
            this.updTm = "";
        } else {
            this.updTm = updTm;
        }
    }


}