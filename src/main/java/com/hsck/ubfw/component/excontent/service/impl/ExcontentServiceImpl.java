package com.hsck.ubfw.component.excontent.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.excontent.dao.ExMapper;
import com.hsck.ubfw.component.excontent.service.ExcontentService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : ContentServiceImpl.java
 * @Description : 공통 컨텐츠 Business Implement Class	
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

@Service("excontentService")
public class ExcontentServiceImpl extends EgovAbstractServiceImpl implements ExcontentService {
	@Resource(name="commonMapper")
    private CommonMapper commonMapper; 

	@Resource(name="exMapper")
    private ExMapper exMapper; 
	
	/** ContentService */
	@Resource(name = "excontentService")
	private ExcontentService excontentService;
	
	@Override
	public int selectcfgIdCnt(HashMap parameterObject) throws Exception {
		return (int) commonMapper.getSelectCnt("excontent.selectcfgIdCnt", parameterObject);
	}
	
	@Override
	public int selectcfgUserCnt(HashMap parameterObject) throws Exception {
		return (int) commonMapper.getSelectCnt("excontent.selectcfgUserCnt", parameterObject);
	}
	
	@Override
	public int insertcfgUserReg(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("excontent.insertcfgUserReg", parameterObject);
	}
	
	@Override
	public String selectRoleId(HashMap parameterObject) throws Exception {
		return (String) commonMapper.getSelect("excontent.selectRoleId", parameterObject);
	}
	
	@Override
	public String selectId(HashMap parameterObject) throws Exception {
		return (String) commonMapper.getSelect("excontent.selectId", parameterObject);
	}
	
	@Override
	public int insertSendMMS(HashMap parameterObject) throws Exception {
		return commonMapper.insertData("excontent.insertSendMMS", parameterObject);
	}
	
	@Override
	public String selectPass(HashMap parameterObject) throws Exception {
		return (String) commonMapper.getSelect("excontent.selectPass", parameterObject);
	}
	
	@Override
	public int selectcfgUserCntPw(HashMap parameterObject) throws Exception {
		return (int) commonMapper.getSelectCnt("excontent.selectcfgUserCntPw", parameterObject);
	}
}

