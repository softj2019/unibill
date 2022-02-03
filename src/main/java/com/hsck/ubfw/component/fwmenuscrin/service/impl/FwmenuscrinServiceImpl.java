
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
package com.hsck.ubfw.component.fwmenuscrin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.fwmenuscrin.service.FwmenuscrinService;
import com.hsck.ubfw.component.fwmenuscrin.vo.FwmenuscrinVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import egovframework.rte.fdl.idgnr.EgovIdGnrService;

 /**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("fwmenuscrinService")
public class FwmenuscrinServiceImpl extends EgovAbstractServiceImpl implements FwmenuscrinService {

	private static final Logger LOG = LoggerFactory.getLogger(FwmenuscrinServiceImpl.class);

	/** ID Generation */
   // @Resource(name="fwmenuscrinIdGnrService")
   // private EgovIdGnrService fwmenuscrinIdGnrService;
        
    
    @Resource(name = "fwmenuscrinMapper")
    private FwmenuscrinMapper fwmenuscrinMapper;

	@Override
	public List<FwmenuscrinVO> listFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("listFwmenuscrin");
		List<FwmenuscrinVO> data = fwmenuscrinMapper.listFwmenuscrin(fwmenuscrinVO);
		return data;
	}

	@Override
	public Integer getFwmenuscrinCnt(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("getFwmenuscrinCnt");
		return fwmenuscrinMapper.getFwmenuscrinCnt(fwmenuscrinVO);
	}

	@Override
	public FwmenuscrinVO getFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("getFwmenuscrin");
		FwmenuscrinVO data = fwmenuscrinMapper.getFwmenuscrin(fwmenuscrinVO);
		return data;
	}


	@Override
	public int saveFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("saveFwmenuscrin");

		// PK Key 값 여부에따라 분기처리.( or MERGE INTO )
		//int cnt = fwmenuscrinMapper.getFwmenuscrinKeyCnt(fwmenuscrinVO);
//		if ( "0".equals(fwmenuscrinVO.getUbseq()) || 0 == cnt ){
//			String pkUbseq =  KeyUtil.getUbseq("FWMENUSCRIN", "ubseq");
//			fwmenuscrinVO.setUbseq(pkUbseq);
//		}

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuscrinVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuscrinVO); // 객체 전체를 복사 하는 경우. fwmenuscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwmenuscrinVO.setSessionUserId(loginVO.getSessionUserId());
 
		return fwmenuscrinMapper.saveFwmenuscrin(fwmenuscrinVO);
	}
	

	@Override
	public String insertFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("insertFwmenuscrin");
		String returnResult = "";
		String pkUbseq = KeyUtil.getUbseq("FWMENUSCRIN", "ubseq");
		fwmenuscrinVO.setUbseq(pkUbseq);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuscrinVO); // session의 정보를 VO에 추가.
 
//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuscrinVO); // 객체 전체를 복사 하는 경우. fwmenuscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwmenuscrinVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = fwmenuscrinMapper.insertFwmenuscrin(fwmenuscrinVO);
		if( 0 < sqlResultInt ){
			returnResult = pkUbseq;
		}
		return returnResult;
	}


	@Override
	public int updateFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		LOG.debug("updateFwmenuscrin");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuscrinVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuscrinVO); // 객체 전체를 복사 하는 경우. fwmenuscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwmenuscrinVO.setSessionUserId(loginVO.getSessionUserId());
 
		int sqlResultInt = fwmenuscrinMapper.updateFwmenuscrin(fwmenuscrinVO); 
		return sqlResultInt;
	}


	 @Override
	 public int deleteFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		 LOG.debug("deleteFwmenuscrin");
		 return fwmenuscrinMapper.deleteFwmenuscrin(fwmenuscrinVO);
	 }

	 @Override
	 public int deleteListFwmenuscrin(FwmenuscrinVO fwmenuscrinVO) throws Exception {
		 LOG.debug("deleteFwmenuscrin");
		 if( StringUtils.isNoneBlank(fwmenuscrinVO.getDelUbseq())){
			 fwmenuscrinVO.setDelUbseqList(StringUtils.split( fwmenuscrinVO.getDelUbseq() , ","));
		 }
		 return fwmenuscrinMapper.deleteListFwmenuscrin(fwmenuscrinVO);
	 }


}
