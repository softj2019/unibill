
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
package com.hsck.ubfw.component.fwform.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.fwform.service.FwformService;
import com.hsck.ubfw.component.fwform.vo.FwformVO;

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
@Service("fwformService")
public class FwformServiceImpl extends EgovAbstractServiceImpl implements FwformService {

	private static final Logger LOG = LoggerFactory.getLogger(FwformServiceImpl.class);

	/** ID Generation */
//	@Resource(name="fwformIdGnrService")
//	private EgovIdGnrService fwformIdGnrService;


	@Resource(name = "fwformMapper")
	private FwformMapper fwformMapper;

	@Override
	public List<FwformVO> listFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("listFwform");
		List<FwformVO> data = fwformMapper.listFwform(fwformVO);
		return data;
	}

	@Override
	public Integer getFwformCnt(FwformVO fwformVO) throws Exception {
		LOG.debug("getFwformCnt");
		return fwformMapper.getFwformCnt(fwformVO);
	}

	@Override
	public FwformVO getFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("getFwform");
		FwformVO data = fwformMapper.getFwform(fwformVO);
		return data;
	}


	@Override
	public int saveFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("saveFwform");

		// PK Key 값 여부에따라 분기처리.( or MERGE INTO )
//		int cnt = fwformMapper.getFwformKeyCnt(fwformVO);
//		if ( "0".equals(fwformVO.getUbseq()) || 0 == cnt ){
//			String pkUbseq =  KeyUtil.getUbseq("FWFORM", "ubseq");
//			fwformVO.setUbseq(pkUbseq);
//		}

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwformVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwformVO); // 객체 전체를 복사 하는 경우. fwformVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwformVO.setSessionUserId(loginVO.getSessionUserId());

		return fwformMapper.saveFwform(fwformVO);
	}


	@Override
	public String insertFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("insertFwform");
		String returnResult = "";
		String pkUbseq = KeyUtil.getUbseq("FWFORM", "ubseq");
		fwformVO.setUbseq(pkUbseq);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwformVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwformVO); // 객체 전체를 복사 하는 경우. fwformVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwformVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = fwformMapper.insertFwform(fwformVO);
		if( 0 < sqlResultInt ){
			returnResult = pkUbseq;
		}
		return returnResult;
	}


	@Override
	public int updateFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("updateFwform");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwformVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwformVO); // 객체 전체를 복사 하는 경우. fwformVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwformVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = fwformMapper.updateFwform(fwformVO);
		return sqlResultInt;
	}


	@Override
	public int deleteFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("deleteFwform");
		return fwformMapper.deleteFwform(fwformVO);
	}


	@Override
	public int deleteListFwform(FwformVO fwformVO) throws Exception {
		LOG.debug("deleteFwform");
		if( StringUtils.isNoneBlank(fwformVO.getDelUbseq())){
			fwformVO.setDelUbseqList(StringUtils.split( fwformVO.getDelUbseq() , ","));
		}
		return fwformMapper.deleteListFwform(fwformVO);
	}

}
