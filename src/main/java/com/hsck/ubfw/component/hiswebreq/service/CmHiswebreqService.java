
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

import com.hsck.ubfw.component.hiswebreq.vo.CmHiswebreqVO;
import com.hsck.ubfw.component.hiswebreq.vo.instPayVO;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * CM_HISWEBREQ에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface CmHiswebreqService {

	/**
	 * CM_HISWEBREQ 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * List<CmHiswebreqVO>
	 */
	List<CmHiswebreqVO> listCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmHiswebreqVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer listCmHiswebreqCnt(CmHiswebreqVO cmHiswebreqVO) throws Exception;

     List<CmHiswebreqVO> listCmHiswebreqExcel(CmHiswebreqVO mbMailBillmakehistVO) throws Exception;

     /**
	 * CM_HISWEBREQ 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * CmHiswebreqVO
	 */
	CmHiswebreqVO getCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;


	 /**
	  * 해당 정산월의 data를 삭제한다.(mb_rptjumpo)
	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 void deleteJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 해당 정산월의 data를 삭제한다.(mb_tc_tong)
	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 void deleteTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 정산 작업을 수행하는 비지니스 로직.<br/>
	  * 동일한 정산월의 Data가 있는경우 해당 정산월 Data를 먼저 삭제후 insert 처리함.<br/>
	  * 작업이 완료되면 cm_hiswebreq 테이블에 상태값 수정 처리함.
	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 CmHiswebreqVO runBillingJobRequest(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 인천공항공사에서 사용할 정산 비지니스 로직<br/>
	  * @param cmHiswebreqVO
	  * @return
	  * @throws Exception
	  */
	 CmHiswebreqVO runBillingIIACJobRequest(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	 * CM_HISWEBREQ 에서 Data를 추가하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * String
	 */
	String insertCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ 에서 Data를 수정하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * String
	 */
	int updateCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ 에서 Data를 삭제하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * int
	 */
	int deleteCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	/**
	 * CM_HISWEBREQ 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param cmHiswebreqVO
	 * @return
	 * @throws Exception
	 * CmHiswebreqVO
	 */
	int saveCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * mb_rptjumpo 테이블에 들어있는 data를 고객 기준으로 group by 처리하여 다시 mb_tc_tong 에 넣는 SQL 을 실행한다.
	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 void insertMbtctongFromJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 정산 작업 요청 관련 로직 작업 수행.
 	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 void insertTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 정산작업을 위해 점포 data를 추가한다.
	  * @param cmHiswebreqVO
	  * @throws Exception
	  */
	 void insertJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * mb_tc_tong 요금 조정. : 요금 조정 관리(mb_cfgbilladd)테이블 정보에따라 mb_tc_tong에 청구금액(cost)을 수정한다.
	  * @param cmHiswebreqVO
	  */
	 void updateAdjustmentForTelecomBilling(CmHiswebreqVO cmHiswebreqVO) throws Exception;
	 /**
	  * mb_rptjumpo 요금 조정. : 요금 조정 관리(mb_cfgbilladd)테이블 정보에따라 mb_rptjumpo 에 청구금액(cost)을 수정한다.
	  * @param cmHiswebreqVO
	  */
	 void updateAdjustmentForJumpoBilling(CmHiswebreqVO cmHiswebreqVO) throws Exception;

	 /**
	  * 해지즉시불 계산 
	  * @param model
	  */
	 void hotbill_proc(Model model, HttpServletRequest request, HttpServletResponse response);
	 
	 /**
	  * 해지즉시불 조회 
	  * @param model
	  */
	 HashMap<String, Object> getHotbill(Model model, HttpServletRequest request, HttpServletResponse response);
	 
	 /**
	  * 해지즉시불 처리 
	  * @param model
	  */
	 int hotbill_proc_commit(Model model, HttpServletRequest request, HttpServletResponse response);
	 
 }
