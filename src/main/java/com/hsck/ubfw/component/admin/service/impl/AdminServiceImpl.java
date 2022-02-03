package com.hsck.ubfw.component.admin.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hsck.ubfw.component.admin.service.AdminService;
import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.content.service.ContentService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : MainServiceImpl.java
 * @Description : 메인 Business Implement Class	
 * @Modification Information
 * @author 홍길동
 * @since 2016.10.04
 * @version 1.0.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일	     수정자		 수정내용
 *  ----------   --------    ---------------------------
 *  2016.10.04   홍길동	   	 최초 생성	
 * </pre>	
 */	

@Service("adminService")
public class AdminServiceImpl extends EgovAbstractServiceImpl implements AdminService {

	private static final Logger log = LoggerFactory.getLogger(ContentUtil.class);
	@Resource(name="commonMapper")
    private CommonMapper commonMapper;

	/** ID Generation */
	//@Resource(name="ddTestIdGnrService")
	//private EgovIdGnrService ddTestIdGnrService;

	/** ID Generation */
	//@Resource(name="ddTestIdGnrService2")
	//private EgovIdGnrService ddTestIdGnrService2;

	/** ContentService */
	@Resource(name = "contentService")
	private ContentService contentService;


	
	
}