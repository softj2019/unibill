
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
package com.hsck.ubfw.component.fwscrin.service.impl;

import java.util.List;

import com.hsck.ubfw.component.fwscrin.vo.FwscrinVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * FWSCRIN에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("fwscrinMapper")
public interface FwscrinMapper {

	/**
	 * FWSCRIN의 목록을 조회하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * List<FwscrinVO>
	 */
	List<FwscrinVO> listFwscrin(FwscrinVO fwscrinVO) throws Exception;
	/**
	 * FWSCRIN의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwscrinCnt(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN에서 단건 조회하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * FwscrinVO
	 */
	FwscrinVO getFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 *FWSCRIN에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * FwscrinVO
	 */
	int saveFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * String
	 */
	int insertFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN에 정보를 수정하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * String
	 */
	int updateFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * int
	 */
	int deleteFwscrin(FwscrinVO fwscrinVO) throws Exception;

}
