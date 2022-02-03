
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
package com.hsck.ubfw.component.fwscrin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.fwscrin.service.FwscrinService;
import com.hsck.ubfw.component.fwscrin.vo.FwscrinVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

 /**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("fwscrinService")
public class FwscrinServiceImpl extends EgovAbstractServiceImpl implements FwscrinService {

	private static final Logger LOG = LoggerFactory.getLogger(FwscrinServiceImpl.class);

//    src/main/resources/framework/spring/context-idgen.xml 에 bean 을 선언해주세요

    
    
	/** ID Generation */
//    @Resource(name="fwscrinIdGnrService")
  //  private EgovIdGnrService fwscrinIdGnrService;
        
    
    @Resource(name = "fwscrinMapper")
    private FwscrinMapper fwscrinMapper;

	@Override
	public List<FwscrinVO> listFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("listFwscrin");
		List<FwscrinVO> data = fwscrinMapper.listFwscrin(fwscrinVO);
		return data;
	}

	@Override
	public Integer getFwscrinCnt(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("getFwscrinCnt");
		return fwscrinMapper.getFwscrinCnt(fwscrinVO);
	}

	@Override
	public FwscrinVO getFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("getFwscrin");
		FwscrinVO data = fwscrinMapper.getFwscrin(fwscrinVO);
		return data;
	}


	@Override
	public int saveFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("saveFwscrin");

		// PK Key 값 여부에따라 분기처리.( or MERGE INTO )
		if ( "0".equals(fwscrinVO.getUbseq()) ){
			String pkUbseq = KeyUtil.getUbseq("FWSCRIN", "ubseq");
			fwscrinVO.setUbseq(pkUbseq);
		}

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwscrinVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwscrinVO); // 객체 전체를 복사 하는 경우. fwscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwscrinVO.setSessionUserId(loginVO.getSessionUserId());
 
		return fwscrinMapper.saveFwscrin(fwscrinVO);
	}
	

	@Override
	public String insertFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("insertFwscrin");
		String returnResult = "";
		String pkUbseq = KeyUtil.getUbseq("FWSCRIN", "ubseq");
		fwscrinVO.setUbseq(pkUbseq);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwscrinVO); // session의 정보를 VO에 추가.
 
//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwscrinVO); // 객체 전체를 복사 하는 경우. fwscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwscrinVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = fwscrinMapper.insertFwscrin(fwscrinVO);
		if( 0 < sqlResultInt ){
			returnResult = pkUbseq;
		}
		return returnResult;
	}


	@Override
	public int updateFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("updateFwscrin");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwscrinVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwscrinVO); // 객체 전체를 복사 하는 경우. fwscrinVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwscrinVO.setSessionUserId(loginVO.getSessionUserId());
 
		int sqlResultInt = fwscrinMapper.updateFwscrin(fwscrinVO); 
		return sqlResultInt;
	}


	@Override
	public int deleteFwscrin(FwscrinVO fwscrinVO) throws Exception {
		LOG.debug("deleteFwscrin");
		return fwscrinMapper.deleteFwscrin(fwscrinVO);
	}


}
