
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
package com.hsck.ubfw.component.fwobj.service.impl;

import java.util.List;

import com.hsck.ubfw.component.fwobj.vo.FwobjVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * FWOBJ에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("fwobjMapper")
public interface FwobjMapper {

	 /**
	  * FWOBJ의 목록을 조회하는 SQL 을 호출한다.
	  * @param fwobjVO
	  * @return
	  * List<FwobjVO>
	  */
	 List<FwobjVO> listFwobj(FwobjVO fwobjVO) throws Exception;
	 /**
	  * FWOBJ의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	  * @param fwobjVO
	  * @return
	  * @throws Exception
	  * Integer
	  */
	 Integer getFwobjCnt(FwobjVO fwobjVO) throws Exception;

	 /**
	  * FWOBJ 와 Database 의 Table 정보를 조인한 목록을 조회하는 SQL 을 호출한다.
	  * @param fwobjVO
	  * @return
	  * List<FwobjVO>
	  */
	 List<FwobjVO> listFwobjTable(FwobjVO fwobjVO) throws Exception;
	 /**
	  * FWOBJ 와 Database 의 Table 정보를 조인한 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	  * @param fwobjVO
	  * @return
	  * @throws Exception
	  * Integer
	  */
	 Integer getFwobjTableCnt(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ에서 단건 조회하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * FwobjVO
	 */
	FwobjVO getFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 *FWOBJ에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * FwobjVO
	 */
	int saveFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * String
	 */
	int insertFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ에 정보를 수정하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * String
	 */
	int updateFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * int
	 */
	int deleteFwobj(FwobjVO fwobjVO) throws Exception;

}
