package com.hsck.ubfw.component.cmCfggrptype.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsck.ubfw.component.cmCfggrptype.service.CmCfggrptypeService;
import com.hsck.ubfw.component.cmCfggrptype.vo.CmCfggrptypeVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import org.apache.commons.beanutils.BeanUtils;

 /**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("cmCfggrptypeService")
public class CmCfggrptypeServiceImpl extends EgovAbstractServiceImpl implements CmCfggrptypeService {

	private static final Logger LOG = LoggerFactory.getLogger(CmCfggrptypeServiceImpl.class);

	/** ID Generation */
//    @Resource(name="cmCfggrptypeIdGnrService")
//    private EgovIdGnrService cmCfggrptypeIdGnrService;
        
    
    @Resource(name = "cmCfggrptypeMapper")
    private CmCfggrptypeMapper cmCfggrptypeMapper;

	@Override
	public List<CmCfggrptypeVO> listCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("listCmCfggrptype");
		List<CmCfggrptypeVO> data = cmCfggrptypeMapper.listCmCfggrptype(cmCfggrptypeVO);
		return data;
	}

	@Override
	public Integer getCmCfggrptypeCnt(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("getCmCfggrptypeCnt");
		return cmCfggrptypeMapper.getCmCfggrptypeCnt(cmCfggrptypeVO);
	}

	@Override
	public CmCfggrptypeVO getCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("getCmCfggrptype");
		CmCfggrptypeVO data = cmCfggrptypeMapper.getCmCfggrptype(cmCfggrptypeVO);
		return data;
	}


	@Override
	public int saveCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("saveCmCfggrptype");

		// PK Key 값 여부에따라 분기처리.( or MERGE INTO )
		if ( StringUtils.isBlank((String)cmCfggrptypeVO.getGrpType()) ){
//			String pkStr = cmCfggrptypeIdGnrService.getNextStringId();
//			cmCfggrptypeVO.setGrptype(pkStr);
		}

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(cmCfggrptypeVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrptypeVO); // 객체 전체를 복사 하는 경우. cmCfggrptypeVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        cmCfggrptypeVO.setSessionUserId(loginVO.getSessionUserId());
 
		return cmCfggrptypeMapper.saveCmCfggrptype(cmCfggrptypeVO);
	}
	

	@Override
	public String insertCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("insertCmCfggrptype");
		String returnStr = "";
		String pkStr = "";
//		String pkStr = cmCfggrptypeIdGnrService.getNextStringId();
//		cmCfggrptypeVO.setGrptype(pkStr);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(cmCfggrptypeVO); // session의 정보를 VO에 추가.
 
//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrptypeVO); // 객체 전체를 복사 하는 경우. cmCfggrptypeVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        cmCfggrptypeVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = cmCfggrptypeMapper.insertCmCfggrptype(cmCfggrptypeVO);
		if( 0 < sqlResultInt ){
			returnStr = pkStr;
		}
		return returnStr;
	}


	@Override
	public int updateCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("updateCmCfggrptype");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(cmCfggrptypeVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrptypeVO); // 객체 전체를 복사 하는 경우. cmCfggrptypeVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        cmCfggrptypeVO.setSessionUserId(loginVO.getSessionUserId());
 
		int sqlResultInt = cmCfggrptypeMapper.updateCmCfggrptype(cmCfggrptypeVO); 
		return sqlResultInt;
	}


	@Override
	public int deleteCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception {
		LOG.debug("deleteCmCfggrptype");
		return cmCfggrptypeMapper.deleteCmCfggrptype(cmCfggrptypeVO);
	}


}
