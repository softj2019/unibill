
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
package com.hsck.ubfw.component.fwmenu.service;

import java.util.List;

import com.hsck.ubfw.component.fwmenu.vo.FwmenuVO;

import org.springframework.transaction.annotation.Transactional;

 /**
 * FWMENU에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface FwmenuService {

	/**
	 * FWMENU 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * List<FwmenuVO>
	 */
	List<FwmenuVO> listFwmenu(FwmenuVO fwmenuVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwmenuVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwmenuCnt(FwmenuVO fwmenuVO) throws Exception;
	
	/**
	 * FWMENU 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * FwmenuVO
	 */
	FwmenuVO getFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU 에서 Data를 추가하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * String
	 */
	String insertFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU 에서 Data를 수정하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * String
	 */
	int updateFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU 에서 Data를 삭제하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * int
	 */
	int deleteFwmenu(FwmenuVO fwmenuVO) throws Exception;

	/**
	 * FWMENU 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param fwmenuVO
	 * @return
	 * @throws Exception
	 * FwmenuVO
	 */
	int saveFwmenu(FwmenuVO fwmenuVO) throws Exception;

}
