
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

import com.hsck.ubfw.component.hiswebreq.service.MbCfgbillendService;
import com.hsck.ubfw.component.hiswebreq.vo.MbCfgbillendVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//import org.apache.commons.beanutils.BeanUtils;

 /**
 * Service Implements 클레스 : 마감관리(MB_CFGBILLEND)비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("mbCfgbillendService")
public class MbCfgbillendServiceImpl extends EgovAbstractServiceImpl implements MbCfgbillendService {

	private static final Logger LOG = LoggerFactory.getLogger(MbCfgbillendServiceImpl.class);

    
    @Resource(name = "mbCfgbillendMapper")
    private MbCfgbillendMapper mbCfgbillendMapper;

	@Override
	public List<MbCfgbillendVO> listMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception {
		LOG.debug("listMbCfgbillend");
		List<MbCfgbillendVO> data = mbCfgbillendMapper.listMbCfgbillend(mbCfgbillendVO);
		return data;
	}

	@Override
	public Integer getMbCfgbillendCnt(MbCfgbillendVO mbCfgbillendVO) throws Exception {
		LOG.debug("getMbCfgbillendCnt");
		return mbCfgbillendMapper.getMbCfgbillendCnt(mbCfgbillendVO);
	}

	@Override
	public MbCfgbillendVO getMbCfgbillend(MbCfgbillendVO mbCfgbillendVO) throws Exception {
		LOG.debug("getMbCfgbillend");
		MbCfgbillendVO data = mbCfgbillendMapper.getMbCfgbillend(mbCfgbillendVO);
		return data;
	}



}
