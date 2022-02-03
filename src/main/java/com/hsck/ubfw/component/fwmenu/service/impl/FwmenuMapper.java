
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
package com.hsck.ubfw.component.fwmenu.service.impl;

import java.util.List;

import com.hsck.ubfw.component.fwmenu.vo.FwmenuVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * FWMENU에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("fwmenuMapper")
public interface FwmenuMapper {


	 List<FwmenuVO> listFwmenu(FwmenuVO fwmenuVO) throws Exception;
	/**
	 * FWMENU의 목록을 조회하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * List<FwmenuVO>
	 */
	List<FwmenuVO> listFwmenuConnectBy(FwmenuVO fwmenuVO) throws Exception;
	/**
	 * FWMENU의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwmenuCnt(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU에서 단건 조회하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * FwmenuVO
	 */
	FwmenuVO getFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 *FWMENU에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * FwmenuVO
	 */
	int saveFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * String
	 */
	int insertFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU에 정보를 수정하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * String
	 */
	int updateFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * int
	 */
	int deleteFwmenu(FwmenuVO fwmenuVO) throws Exception;

}
