
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
package com.hsck.ubfw.component.fwform.service.impl;

import java.util.List;

import com.hsck.ubfw.component.fwform.vo.FwformVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * FWFORM에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("fwformMapper")
public interface FwformMapper {

	/**
	 * FWFORM의 목록을 조회하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * List<FwformVO>
	 */
	List<FwformVO> listFwform(FwformVO fwformVO) throws Exception;
	/**
	 * FWFORM의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwformCnt(FwformVO fwformVO) throws Exception;

	 /**
	  * ubseq 생성 판단용.
	  * @param fwformVO
	  * @return
	  * @throws Exception
	  */
	Integer getFwformKeyCnt(FwformVO fwformVO) throws Exception;


	/**
	 * FWFORM에서 단건 조회하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * FwformVO
	 */
	FwformVO getFwform(FwformVO fwformVO) throws Exception;

	/**
	 *FWFORM에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * FwformVO
	 */
	int saveFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * String
	 */
	int insertFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM에 정보를 수정하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * String
	 */
	int updateFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * int
	 */
	int deleteFwform(FwformVO fwformVO) throws Exception;

	 /**
	  * FWMENUSCRIN에서 정보를 여러건 삭제하는 SQL 을 호출한다.( ubseq 값으로 삭제함. )
	  * @param fwformVO
	  * @return
	  * @throws Exception
	  */
     int deleteListFwform(FwformVO fwformVO) throws Exception;

}
