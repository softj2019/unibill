
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
 * 마감관리(MB_CFGBILLEND) 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data
class MbCfgbillendVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8538527726754202213L;
    private String billYm; /* 청구월 [Size=6] [PK_MB_CFGBILLEND , position : 1] :  */
//    private String svcId; /* 서비스ID [Size=7] [PK_MB_CFGBILLEND , position : 2] : '*'  */
//    private String ubseq; /* 자료순번 [Size=22] [ , position : ] :  */
    private String billendYn; /* 마감여부 [Size=1] [ , position : ] :  */
    private String regId; /* 등록자 [Size=32] [ , position : ] :  */
    private String updId; /* 변경자 [Size=32] [ , position : ] :  */
    private String regTm; /* 등록시간 [Size=14] [ , position : ] :  */
    private String updTm; /* 변경시간 [Size=14] [ , position : ] :  */

    /* 검색 영역 parameters */
    private String searchBillYm; /* 검색 필드 : 청구월 */
//    private String searchSvcId; /* 검색 필드 : 서비스ID */
    private String searchUbseq; /* 검색 필드 : 자료순번 */
    private String searchBillendYn; /* 검색 필드 : 마감여부 */
    private String searchRegId; /* 검색 필드 : 등록자 */
    private String searchUpdId; /* 검색 필드 : 변경자 */
    //private String searchRegTm; /* 검색 필드 : 등록시간 */
    private String searchUpdTm; /* 검색 필드 : 변경시간 */



    /*  billendYn : NOT NULL  , default :   */
    public void setBillendYn(String billendYn) {
        if (StringUtils.isBlank(billendYn)) {
            this.billendYn = "";
        } else {
            this.billendYn = billendYn;
        }
    }


}