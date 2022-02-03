
/*******************************************************************************
 * COPYRIGHT(C) 2016 HANSSAK UNIBILL ALL RIGHTS RESERVED.
 * This software is the proprietary information of HANSSAK UNIBILL.
 *
 * Revision History
 * Author   Date            Description
 * ------   ----------      ------------------------------------
 * AA    0000. 00. 00.         First Draft.( Auto Code Generate )
 *
 *******************************************************************************/ 
package com.hsck.ubfw.component.fwmenuscrin.service;

import java.util.List;

import com.hsck.ubfw.component.fwmenuscrin.vo.FwmenuscrinVO;

import org.springframework.transaction.annotation.Transactional;

 /**
 * FWMENUSCRIN에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface FwmenuscrinService {

	/**
	 * FWMENUSCRIN 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * List<FwmenuscrinVO>
	 */
	List<FwmenuscrinVO> listFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwmenuscrinCnt(FwmenuscrinVO fwmenuscrinVO) throws Exception;
	
	/**
	 * FWMENUSCRIN 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * FwmenuscrinVO
	 */
	FwmenuscrinVO getFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN 에서 Data를 추가하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * String
	 */
	String insertFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN 에서 Data를 수정하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * String
	 */
	int updateFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN 에서 Data를 삭제하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * int
	 */
	int deleteFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param fwmenuscrinVO
	 * @return
	 * @throws Exception
	 * FwmenuscrinVO
	 */
	int saveFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	 /**
	  * FWMENUSCRIN에서 정보를 여러건 삭제하는 SQL 을 호출한다.( ubseq 값으로 삭제함. )
	  * @param fwmenuscrinVO
	  * @return
	  * @throws Exception
	  */
     int deleteListFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;
 }
