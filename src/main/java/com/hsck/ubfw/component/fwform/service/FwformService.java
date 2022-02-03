
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
package com.hsck.ubfw.component.fwform.service;

import java.util.List;

import com.hsck.ubfw.component.fwform.vo.FwformVO;

import org.springframework.transaction.annotation.Transactional;

 /**
 * FWFORM에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface FwformService {

	/**
	 * FWFORM 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * List<FwformVO>
	 */
	List<FwformVO> listFwform(FwformVO fwformVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param fwformVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getFwformCnt(FwformVO fwformVO) throws Exception;
	
	/**
	 * FWFORM 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * FwformVO
	 */
	FwformVO getFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM 에서 Data를 추가하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * String
	 */
	String insertFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM 에서 Data를 수정하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * String
	 */
	int updateFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM 에서 Data를 삭제하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * int
	 */
	int deleteFwform(FwformVO fwformVO) throws Exception;


	/**
	 * FWFORM에서 정보를 여러건 삭제하는 SQL 을 호출한다.( ubseq 값으로 삭제함. )
	 * @param fwformVO
	 * @return
	 * int
	 */
	int deleteListFwform(FwformVO fwformVO) throws Exception;

	/**
	 * FWFORM 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param fwformVO
	 * @return
	 * @throws Exception
	 * FwformVO
	 */
	int saveFwform(FwformVO fwformVO) throws Exception;

}
