
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
package com.hsck.ubfw.component.fwmenuscrin.service.impl;

import java.util.List;

import com.hsck.ubfw.component.fwmenuscrin.vo.FwmenuscrinVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * FWMENUSCRIN에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("fwmenuscrinMapper")
public interface FwmenuscrinMapper {

	/**
	 * FWMENUSCRIN의 목록을 조회하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * List<FwmenuscrinVO>
	 */
	List<FwmenuscrinVO> listFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;
	/**
	 * FWMENUSCRIN의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwmenuscrinCnt(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN에서 단건 조회하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * FwmenuscrinVO
	 */
	FwmenuscrinVO getFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 *FWMENUSCRIN에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * FwmenuscrinVO
	 */
	int saveFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * String
	 */
	int insertFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN에 정보를 수정하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * String
	 */
	int updateFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	/**
	 * FWMENUSCRIN에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param fwmenuscrinVO
	 * @return
	 * int
	 */
	int deleteFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	 /**
	  * FWMENUSCRIN에서 정보를 여러건 삭제하는 SQL 을 호출한다.( ubseq 값으로 삭제함. )
	  * @param fwmenuscrinVO
	  * @return
	  * @throws Exception
	  */
     int deleteListFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception;

	 /**
	  * ubseq 생성 판단용.
	  * @param fwmenuscrinVO
	  * @return
	  */
     int getFwmenuscrinKeyCnt(FwmenuscrinVO fwmenuscrinVO);
 }
