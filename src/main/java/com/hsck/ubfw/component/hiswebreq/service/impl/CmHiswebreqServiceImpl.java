
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

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.comm.vo.LoginInfo;
import com.hsck.ubfw.component.hiswebreq.service.CmHiswebreqService;
import com.hsck.ubfw.component.hiswebreq.service.MbCfgbillendService;
import com.hsck.ubfw.component.hiswebreq.vo.CmHiswebreqVO;
import com.hsck.ubfw.component.hiswebreq.vo.MbCfgbillendVO;
import com.hsck.ubfw.component.hiswebreq.vo.instPayVO;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


 /**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("cmHiswebreqService")
public class CmHiswebreqServiceImpl extends EgovAbstractServiceImpl implements CmHiswebreqService {

	private static final Logger LOG = LoggerFactory.getLogger(CmHiswebreqServiceImpl.class);

	/** ID Generation */
    //@Resource(name="cmHiswebreqIdGnrService")
    //private EgovIdGnrService cmHiswebreqIdGnrService;

    @Resource(name = "mbCfgbillendService")
    private MbCfgbillendService mbCfgbillendService;
    
    @Resource(name = "cmHiswebreqMapper")
    private CmHiswebreqMapper cmHiswebreqMapper;

	@Override
	public List<CmHiswebreqVO> listCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("listCmHiswebreq");
		List<CmHiswebreqVO> data = cmHiswebreqMapper.listCmHiswebreq(cmHiswebreqVO);
		return data;
	}

	@Override
	public Integer listCmHiswebreqCnt(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("listCmHiswebreqCnt");
		return cmHiswebreqMapper.listCmHiswebreqCnt(cmHiswebreqVO);
	}

	 @Override
	 public List<CmHiswebreqVO> listCmHiswebreqExcel(CmHiswebreqVO mbMailBillmakehistVO) throws Exception {
		 LOG.debug("listCmHiswebreqExcel");
		 List<CmHiswebreqVO> data = cmHiswebreqMapper.listCmHiswebreqExcel(mbMailBillmakehistVO);
		 return data;
	 }

	@Override
	public CmHiswebreqVO getCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("getCmHiswebreq");
		CmHiswebreqVO data = cmHiswebreqMapper.getCmHiswebreq(cmHiswebreqVO);
		return data;
	}

	 @Override
	public String insertCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("insertCmHiswebreq");
		String returnResult = "";
		String pkUbseq = KeyUtil.getUbseq("CM_HISWEBREQ", "ubseq");
		cmHiswebreqVO.setUbseq(pkUbseq);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(cmHiswebreqVO); // session의 정보를 VO에 추가.
 
//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmHiswebreqVO); // 객체 전체를 복사 하는 경우. cmHiswebreqVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        cmHiswebreqVO.setSessionMemSeq(loginVO.getSessionMemSeq()); 

		int sqlResultInt = cmHiswebreqMapper.insertCmHiswebreq(cmHiswebreqVO);
		if( 0 < sqlResultInt ){
			returnResult = pkUbseq;
		}
		return returnResult;
	}


	@Override
	public int updateCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("updateCmHiswebreq");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(cmHiswebreqVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmHiswebreqVO); // 객체 전체를 복사 하는 경우. cmHiswebreqVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        cmHiswebreqVO.setSessionMemSeq(loginVO.getSessionMemSeq());
 
		int sqlResultInt = cmHiswebreqMapper.updateCmHiswebreq(cmHiswebreqVO); 
		return sqlResultInt;
	}


	@Override
	public int deleteCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		LOG.debug("deleteCmHiswebreq");
		return cmHiswebreqMapper.deleteCmHiswebreq(cmHiswebreqVO);
	}





	 @Override
	 public int saveCmHiswebreq(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 LOG.debug("saveCmHiswebreq");
		 LOG.debug("cmHiswebreqVO.getBillYm() : " + cmHiswebreqVO.getBillYm());

		 // PK Key 값 여부에따라 분기처리.( or MERGE INTO )
		 int cnt = cmHiswebreqMapper.getKeyDuplicateCnt(cmHiswebreqVO);
		 if ("0".equals(cmHiswebreqVO.getUbseq()) || 0 == cnt) {
			 String pkUbseq = KeyUtil.getUbseq("CM_HISWEBREQ", "ubseq");
			 cmHiswebreqVO.setUbseq(pkUbseq);

//			 String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
//			 cmHiswebreqVO.setSday(yyyyMMdd);
			/* Calendar cal = Calendar.getInstance();
			 cal.set(Calendar.YEAR, Integer.parseInt(StringUtils.substring(cmHiswebreqVO.getBillYm(), 0, 4)));
			 cal.set(Calendar.MONTH, Integer.parseInt(StringUtils.substring(cmHiswebreqVO.getBillYm(), 4, 6)) - 1);
			 cal.set(Calendar.DATE, 1);*/
		/* if (!"10".equals(cmHiswebreqVO.getJobType())) {
			 cmHiswebreqVO.setSday(cmHiswebreqVO.getBillYm() + "01");
			 cmHiswebreqVO.setEday(cmHiswebreqVO.getBillYm() + Integer.toString(cal.getActualMaximum(Calendar.DATE)));
		 }*/
//			 cmHiswebreqVO.setProgress("5");
//			 cmHiswebreqVO.setReqStatus("P"); // C=취소,F=실패,P=진행중,R=요청,S=성공,X=취소완료
			 cmHiswebreqVO.setMsg(cmHiswebreqVO.getBillYm() + "월 작업 시작합니다.");
		 }

		 LoginInfo loginInfo = new LoginInfo();
		 loginInfo.putSessionToVo(cmHiswebreqVO); // session의 정보를 VO에 추가.
		 return cmHiswebreqMapper.saveCmHiswebreq(cmHiswebreqVO);
	 }

	 @Override
	 public CmHiswebreqVO runBillingJobRequest(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if (StringUtils.isNoneBlank(cmHiswebreqVO.getBillYm())) {

			 String billY = StringUtils.substring(cmHiswebreqVO.getBillYm(), 0, 4);
			 String billM = StringUtils.substring(cmHiswebreqVO.getBillYm(), 4, 6);

			 Calendar cal = Calendar.getInstance();
			 cal.set(Calendar.YEAR, Integer.parseInt( billY ));
			 cal.set(Calendar.MONTH, Integer.parseInt(billM) - 1);
			 cal.set(Calendar.DATE, 1);

			 cmHiswebreqVO.setBillYm( billY + StringUtils.leftPad( billM ,2,"0") ); // BillYm 셋팅
			 cmHiswebreqVO.setBillYmLastDay(Integer.toString(cal.getActualMaximum(Calendar.DATE))); /* 정산월의 말일의 날짜만 반환 */




			 int beforeBillYmInt = ( Integer.parseInt( billM ) -1 ); // beforeBillYm /* (정산월-1)월 */
			 Calendar beforeCal = Calendar.getInstance();
			 beforeCal.set(Calendar.YEAR, Integer.parseInt( billY ));
			 beforeCal.set(Calendar.MONTH, beforeBillYmInt - 1);
			 beforeCal.set(Calendar.DATE, 1);

			 cmHiswebreqVO.setBeforeBillYm( billY + StringUtils.leftPad( Integer.toString(beforeBillYmInt),2,"0") ); // beforeBillYm 셋팅
			 cmHiswebreqVO.setBeforeBillYmLastDay(Integer.toString(beforeCal.getActualMaximum(Calendar.DATE))); /* (정산월-1)월의 말일의 날짜만 반환 */


			 // MB_CFGBILLEND 테이블에서 정산 가능 여부 확인후 아래 로직(MB_TC_TONG , JUMPO )이 동작 여부를 확인함.
			 MbCfgbillendVO mbCfgbillendVO = new MbCfgbillendVO();
			 mbCfgbillendVO.setBillYm( cmHiswebreqVO.getBillYm() );
			 mbCfgbillendVO.setFirstIndex(0);
			 mbCfgbillendVO.setLastIndex(10);

			 boolean isRun = true;
			 List<MbCfgbillendVO> mbCfgbillendList = mbCfgbillendService.listMbCfgbillend(mbCfgbillendVO);
			 for( MbCfgbillendVO endVO : mbCfgbillendList){
				 if( "Y".equals( endVO.getBillendYn() ) ){
					 isRun = false;
					 cmHiswebreqVO.setMsg("서비스 마감된 청구월입니다.");
				 }
			 }

			 // 동일한 정산월의 Data가 있는경우 해당 정산월 Data를 먼저 삭제후 insert 처리함.
			 if( isRun ){
				 this.deleteTelecomBillingData(cmHiswebreqVO);
				 this.insertTelecomBillingData(cmHiswebreqVO);
				 this.updateAdjustmentForTelecomBilling(cmHiswebreqVO);

				 this.deleteJumpoBillingData(cmHiswebreqVO);
				 this.insertJumpoBillingData(cmHiswebreqVO);
				 this.updateAdjustmentForJumpoBilling(cmHiswebreqVO);

				 //insertMbtctongFromJumpoBillingData(cmHiswebreqVO); // (2017.04.28 확인 : 사용안함!!)


				 //작업이 완료되면 cm_hiswebreq 테이블에 상태값 수정 처리함.
				 cmHiswebreqVO.setProgress("100");
				 cmHiswebreqVO.setReqStatus("S"); // C=취소,F=실패,P=진행중,R=요청,S=성공,X=취소완료
			 }else{

				 cmHiswebreqVO.setProgress("0");
				 cmHiswebreqVO.setReqStatus("F"); // C=취소,F=실패,P=진행중,R=요청,S=성공,X=취소완료
			 }

			 String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
			 cmHiswebreqVO.setEday(yyyyMMdd);


			 saveCmHiswebreq(cmHiswebreqVO);
		 }
		 return cmHiswebreqVO;
	 }

	 @Override
	 public CmHiswebreqVO runBillingIIACJobRequest(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 return cmHiswebreqVO;
	 }


	 @Override
	 public void insertJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBeforeBillYmLastDay() ) ){

			 cmHiswebreqMapper.insertJumpoBillingData(cmHiswebreqVO);
		 }
	 }

	 @Override
	 public void updateAdjustmentForTelecomBilling(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYm() ) ){

			 cmHiswebreqMapper.updateAdjustmentForTelecomBilling(cmHiswebreqVO);
		 }
	 }

	 @Override
	 public void updateAdjustmentForJumpoBilling(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYm() ) ){

			 cmHiswebreqMapper.updateAdjustmentForJumpoBilling(cmHiswebreqVO);
		 }
	 }

	 @Override
	 public void insertMbtctongFromJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYmLastDay() ) ){

			 cmHiswebreqMapper.insertMbtctongFromJumpoBillingData(cmHiswebreqVO);
		 }
	 }


	 @Override
	 public void insertTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYmLastDay() ) ){

			 cmHiswebreqMapper.insertTelecomBillingData(cmHiswebreqVO);
		 }
	 }

	 @Override
	 public void deleteJumpoBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYm() ) ){

			 cmHiswebreqMapper.deleteJumpoBillingData(cmHiswebreqVO);
		 }
	 }

	 @Override
	 public void deleteTelecomBillingData(CmHiswebreqVO cmHiswebreqVO) throws Exception {
		 if(StringUtils.isNoneBlank(cmHiswebreqVO.getBillYm() ) ){

			 cmHiswebreqMapper.deleteTelecomBillingData(cmHiswebreqVO);
		 }
	 }

	@Override
	public void hotbill_proc(Model model, HttpServletRequest request, HttpServletResponse response) {
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		String cust_id   = request.getParameter("cust_id");
		param.put("cust_id", cust_id);
		param.put("reg_id", loginVO.getUserId());
		param.put("msg", "");
		
		cmHiswebreqMapper.hotbill_proc(param);
	}

	@Override
	public HashMap<String, Object> getHotbill(Model model, HttpServletRequest request, HttpServletResponse response) {
		instPayVO searchVO = new instPayVO();
		searchVO.setCustId(request.getParameter("cust_id"));
		
		instPayVO summuryVO = cmHiswebreqMapper.getHotbill_summury(searchVO);
		
		List<instPayVO> detailVO = cmHiswebreqMapper.getHotbill_detail(searchVO);
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("summuryVO", summuryVO);
		rtnMap.put("detailVO", detailVO);
		
		
		return rtnMap;
	}
	
	@Override
	public int hotbill_proc_commit(Model model, HttpServletRequest request, HttpServletResponse response) {
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		String cust_id   = request.getParameter("cust_id");
		param.put("cust_id", cust_id);
		param.put("reg_id", loginVO.getUserId());
		param.put("msg", "");
		
		int sucFlag = 0;
		
		try {
			cmHiswebreqMapper.hotbill_proc_commit(param);
			
		} catch (Exception e) {
			sucFlag = -1;
			LOG.debug("즉시불처리 프로시저 실행 오류");
		}
		return sucFlag;
	}
}
