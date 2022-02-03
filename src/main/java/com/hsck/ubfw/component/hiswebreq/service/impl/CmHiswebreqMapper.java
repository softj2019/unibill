
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

import com.hsck.ubfw.component.hiswebreq.vo.CmHiswebreqVO;
import com.hsck.ubfw.component.hiswebreq.vo.instPayVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

/**
 * CM_HISWEBREQ에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("cmHiswebreqMapper")
public interface CmHiswebreqMapper {

	/**
	 * CM_HISWEBREQ의 목록을 조회하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * List<CmHiswebreqVO>
	 */
	List<CmHiswebreqVO> listCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;
	/**
	 * CM_HISWEBREQ의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer listCmHiswebreqCnt(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ에서 단건 조회하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * CmHiswebreqVO
	 */
	CmHiswebreqVO getCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 *CM_HISWEBREQ에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * CmHiswebreqVO
	 */
	int saveCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * String
	 */
	int insertCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ에 정보를 수정하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * String
	 */
	int updateCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * int
	 */
	int deleteCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	int getKeyDuplicateCnt(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * 통신사 파일 정산 입력 SQL
	 * @param cmHiswebreqVO
	 */
	void insertTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * 요청점포 정산 입력 SQL
	 * @param cmHiswebreqVO
	 * @throws Exception
	 */
	void insertJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * 해당 정산월의 data를 삭제한다.(mb_tc_tong)
	 * @param cmHiswebreqVO
	 */
	void deleteTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * 해당 정산월의 data를 삭제한다.(mb_rptjumpo)
	 * @param cmHiswebreqVO
	 */
	void deleteJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * mb_rptjumpo 테이블에 들어있는 data를 고객 기준으로 group by 처리하여 다시 mb_tc_tong 에 넣는 SQL 을 실행한다.
	 * @param cmHiswebreqVO
	 */
	void insertMbtctongFromJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	List<CmHiswebreqVO> listCmHiswebreqExcel(CmHiswebreqVO mbMailBillmakehistVO) throws Exception;

	/**
	 * mb_tc_tong 요금 조정. : 요금 조정 관리(mb_cfgbilladd)테이블 정보에따라 mb_tc_tong 에 청구금액(cost)을 조정한다.
	 * @param cmHiswebreqVO
	 */
	void updateAdjustmentForTelecomBilling(CmHiswebreqVO cmHiswebreqVO);

	/**
	 * mb_rptjumpo 요금 조정. : 요금 조정 관리(mb_cfgbilladd)테이블 정보에따라 mb_rptjumpo 에 청구금액(cost)을 조정한다.
	 * @param cmHiswebreqVO
	 */
	void updateAdjustmentForJumpoBilling(CmHiswebreqVO cmHiswebreqVO);
	
	/**
	 * 해지즉시불 계산
	 * @param param
	 */
	void hotbill_proc(HashMap<String, Object> param);
	
	/**
	  * 해지즉시불 조회 
	  * @param model
	  */
	instPayVO getHotbill_summury(instPayVO instPayVO);
	List<instPayVO> getHotbill_detail(instPayVO instPayVO);
	
	/**
	 * 해지즉시불 처리
	 * @param param
	 */
	int hotbill_proc_commit(HashMap<String, Object> param);
}
