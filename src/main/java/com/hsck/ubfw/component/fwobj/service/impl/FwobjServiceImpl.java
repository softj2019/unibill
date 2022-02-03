
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
package com.hsck.ubfw.component.fwobj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.fwobj.service.FwobjService;
import com.hsck.ubfw.component.fwobj.vo.FwobjVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.beanutils.BeanUtils;
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
@Service("fwobjService")
public class FwobjServiceImpl extends EgovAbstractServiceImpl implements FwobjService {

	private static final Logger LOG = LoggerFactory.getLogger(FwobjServiceImpl.class);

	/** ID Generation */
  //  @Resource(name="fwobjIdGnrService")
   // private EgovIdGnrService fwobjIdGnrService;
        
    
    @Resource(name = "fwobjMapper")
    private FwobjMapper fwobjMapper;

	 @Override
	 public List<FwobjVO> listFwobj(FwobjVO fwobjVO) throws Exception {
		 LOG.debug("listFwobj");
		 List<FwobjVO> data = fwobjMapper.listFwobj(fwobjVO);
		 return data;
	 }

	 @Override
	 public Integer getFwobjCnt(FwobjVO fwobjVO) throws Exception {
		 LOG.debug("getFwobjCnt");
		 return fwobjMapper.getFwobjCnt(fwobjVO);
	 }

	 @Override
	 public List<FwobjVO> listFwobjTable(FwobjVO fwobjVO) throws Exception {
		 LOG.debug("listFwobjTable");
		 List<FwobjVO> data = fwobjMapper.listFwobjTable(fwobjVO);
		 return data;
	 }

	 @Override
	 public Integer getFwobjTableCnt(FwobjVO fwobjVO) throws Exception {
		 LOG.debug("getFwobjTableCnt");
		 return fwobjMapper.getFwobjTableCnt(fwobjVO);
	 }

	@Override
	public FwobjVO getFwobj(FwobjVO fwobjVO) throws Exception {
		LOG.debug("getFwobj");
		FwobjVO data = fwobjMapper.getFwobj(fwobjVO);
		return data;
	}


	@Override
	public int saveFwobj(FwobjVO fwobjVO) throws Exception {
		LOG.debug("saveFwobj");

//		if ( "0".equals(fwobjVO.getUbseq()) ){
//			String ubseq = KeyUtil.getUbseq("FWOBJ", "ubseq");
//			fwobjVO.setUbseq(ubseq);
//		}

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwobjVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwobjVO); // 객체 전체를 복사 하는 경우. fwobjVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwobjVO.setSessionUserId(loginVO.getSessionUserId());
 
		return fwobjMapper.saveFwobj(fwobjVO);
	}
	

	@Override
	public String insertFwobj(FwobjVO fwobjVO) throws Exception {
		LOG.debug("insertFwobj");
		String returnStr = "";
//		String pkStr = fwobjIdGnrService.getNextStringId();
//		fwobjVO.setPrototypeId(pkStr);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwobjVO); // session의 정보를 VO에 추가.
 
//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwobjVO); // 객체 전체를 복사 하는 경우. fwobjVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwobjVO.setSessionUserId(loginVO.getSessionUserId());

//		int sqlResultInt = fwobjMapper.insertFwobj(fwobjVO);
//		if( 0 < sqlResultInt ){
//			returnStr = pkStr;
//		}
		return returnStr;
	}


	@Override
	public int updateFwobj(FwobjVO fwobjVO) throws Exception {
		LOG.debug("updateFwobj");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwobjVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwobjVO); // 객체 전체를 복사 하는 경우. fwobjVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwobjVO.setSessionUserId(loginVO.getSessionUserId());
 
		int sqlResultInt = fwobjMapper.updateFwobj(fwobjVO); 
		return sqlResultInt;
	}


	@Override
	public int deleteFwobj(FwobjVO fwobjVO) throws Exception {
		LOG.debug("deleteFwobj");
		return fwobjMapper.deleteFwobj(fwobjVO);
	}


}
