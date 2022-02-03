
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
package com.hsck.ubfw.component.fwobj.service;

import java.util.List;

import com.hsck.ubfw.component.fwobj.vo.FwobjVO;

import org.springframework.transaction.annotation.Transactional;

 /**
 * FWOBJ에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface FwobjService {

	/**
	 * FWOBJ 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * List<FwobjVO>
	 */
	List<FwobjVO> listFwobj(FwobjVO fwobjVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwobjVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwobjCnt(FwobjVO fwobjVO) throws Exception;

	 /**
	  * FWOBJ 와 Database 의 Table 정보를 조인한 Data를 여러건 조회하는 로직을 수행한다.
	  * @param fwobjVO
	  * @return
	  * @throws Exception
	  */
     List<FwobjVO> listFwobjTable(FwobjVO fwobjVO) throws Exception;

	 /**
	  * FWOBJ 와 Database 의 Table 정보를 조인한 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	  * @param fwobjVO
	  * @return
	  * @throws Exception
	  */
	 Integer getFwobjTableCnt(FwobjVO fwobjVO) throws Exception;

	 /**
	 * FWOBJ 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * FwobjVO
	 */
	FwobjVO getFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ 에서 Data를 추가하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * String
	 */
	String insertFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ 에서 Data를 수정하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * String
	 */
	int updateFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ 에서 Data를 삭제하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * int
	 */
	int deleteFwobj(FwobjVO fwobjVO) throws Exception;

	/**
	 * FWOBJ 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param fwobjVO
	 * @return
	 * @throws Exception
	 * FwobjVO
	 */
	int saveFwobj(FwobjVO fwobjVO) throws Exception;

}
