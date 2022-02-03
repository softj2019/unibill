
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

import java.io.Serializable;

import lombok.Data;

import com.hsck.ubfw.component.comm.vo.BaseVO;


/**
 * UI요청작업관리(CM_HISWEBREQ) 테이블에 대응되는 VO 클래스입니다.
 *
 * @author AA
 * @since 0000.00.00.
 */
public @Data class instPayVO extends BaseVO implements Serializable {

	private String custId;
	private String custNm;
	private String billYm;
	private String cost;
	private String dcCost;
	private String totCost;
	private String priceplannm;
	private String itemnm;
	private String useamt;
	
    
    


}