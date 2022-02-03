
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
package com.hsck.ubfw.component.hiswebreq.service.impl;

import java.util.List;

import com.hsck.ubfw.component.hiswebreq.vo.MbCfgbillendVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * 마감관리(MB_CFGBILLEND)에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("mbCfgbillendMapper")
public interface MbCfgbillendMapper {

	/**
	 * MB_CFGBILLEND의 목록을 조회하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * List<MbCfgbillendVO>
	 */
	List<MbCfgbillendVO> listMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;
	/**
	 * MB_CFGBILLEND의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getMbCfgbillendCnt(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	/**
	 * MB_CFGBILLEND에서 단건 조회하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * MbCfgbillendVO
	 */
	MbCfgbillendVO getMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	/**
	 *MB_CFGBILLEND에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * MbCfgbillendVO
	 */
	int saveMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	/**
	 * MB_CFGBILLEND에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * String
	 */
	int insertMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	/**
	 * MB_CFGBILLEND에 정보를 수정하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * String
	 */
	int updateMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	/**
	 * MB_CFGBILLEND에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * int
	 */
	int deleteMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

	 /**
	  * FWMENUSCRIN에서 정보를 여러건 삭제하는 SQL 을 호출한다.( ubseq 값으로 삭제함. )
	  * @param mbCfgbillendVO
	  * @return
	  * @throws Exception
	  */
     int deleteListMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

}
