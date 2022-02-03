
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
package com.hsck.ubfw.component.hiswebreq.service;

import com.hsck.ubfw.component.hiswebreq.vo.MbCfgbillendVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

 /**
 * 마감관리(MB_CFGBILLEND)에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface MbCfgbillendService {

	/**
	 * 마감관리(MB_CFGBILLEND) 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param mbCfgbillendVO
	 * @return
	 * List<MbCfgbillendVO>
	 */
	List<MbCfgbillendVO> listMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param mbCfgbillendVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getMbCfgbillendCnt(MbCfgbillendVO mbCfgbillendVO) throws Exception;
	
	/**
	 * 마감관리(MB_CFGBILLEND) 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param mbCfgbillendVO
	 * @return
	 * MbCfgbillendVO
	 */
	MbCfgbillendVO getMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception;

}
