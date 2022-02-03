
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
package com.hsck.ubfw.component.fwscrin.service;

import java.util.List;

import com.hsck.ubfw.component.fwscrin.vo.FwscrinVO;

import org.springframework.transaction.annotation.Transactional;

 /**
 * FWSCRIN에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface FwscrinService {

	/**
	 * FWSCRIN 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * List<FwscrinVO>
	 */
	List<FwscrinVO> listFwscrin(FwscrinVO fwscrinVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwscrinVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwscrinCnt(FwscrinVO fwscrinVO) throws Exception;
	
	/**
	 * FWSCRIN 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * FwscrinVO
	 */
	FwscrinVO getFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN 에서 Data를 추가하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * String
	 */
	String insertFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN 에서 Data를 수정하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * String
	 */
	int updateFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN 에서 Data를 삭제하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * int
	 */
	int deleteFwscrin(FwscrinVO fwscrinVO) throws Exception;

	/**
	 * FWSCRIN 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param fwscrinVO
	 * @return
	 * @throws Exception
	 * FwscrinVO
	 */
	int saveFwscrin(FwscrinVO fwscrinVO) throws Exception;

}
