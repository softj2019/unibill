package com.hsck.ubfw.component.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.hsck.ubfw.component.com.cmm.dao.CommonMapper;
import com.hsck.ubfw.component.monitor.dao.MonitorMapper;
import com.hsck.ubfw.component.monitor.service.MonitorService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
//import egovframework.rte.fdl.idgnr.EgovIdGnrService;

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

@Service("monitorService")
public class MonitorServiceImpl extends EgovAbstractServiceImpl implements MonitorService {

	@Resource(name="commonMapper")
    private CommonMapper commonMapper; 
	
	@Resource(name="monitorMapper")
    private MonitorMapper monitorMapper; 
	
	/** ContentService */
	@Resource(name = "monitorService")
	private MonitorService monitorService;
	
	@Resource(name = "fileUploadProperties")
    Properties fileUploadProperties;
	
	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> daytong(Object parameterObject) throws Exception {
		List<EgovMap> daytongList = (List<EgovMap>) commonMapper.getSelectList("monitor.daytong", parameterObject);
		
		if(daytongList == null){
			daytongList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("cnt", "0");
			tmp.put("cost", "0");
			daytongList.add(tmp);
		}
		return daytongList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> montong(Object parameterObject) throws Exception {
		List<EgovMap> montongList = (List<EgovMap>) commonMapper.getSelectList("monitor.montong", parameterObject);
		if(montongList == null){
			montongList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("cnt", "0");
			tmp.put("cost", "0");
			montongList.add(tmp);
		}
		return montongList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> prevmonth(Object parameterObject) throws Exception {
		List<EgovMap> prevmonthList = (List<EgovMap>) commonMapper.getSelectList("monitor.prevmonth", parameterObject);
		if(prevmonthList == null){
			prevmonthList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			prevmonthList.add(tmp);
		}
		return prevmonthList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> monthcur(Object parameterObject) throws Exception {
		List<EgovMap> monthcurList = (List<EgovMap>) commonMapper.getSelectList("monitor.monthcur", parameterObject);
		if(monthcurList == null){
			monthcurList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			monthcurList.add(tmp);
		}
		return monthcurList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> daycur(Object parameterObject) throws Exception {
		List<EgovMap> daycurList = (List<EgovMap>) commonMapper.getSelectList("monitor.daycur", parameterObject);
		if(daycurList == null){
			daycurList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			daycurList.add(tmp);
		}
		return daycurList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getbtype(Object parameterObject) throws Exception {
		List<EgovMap> getbtypeList = (List<EgovMap>) commonMapper.getSelectList("monitor.getbtype", parameterObject);
		if(getbtypeList == null){
			getbtypeList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			tmp.put("pcnt", "0");
			tmp.put("pcost2", "0");
			getbtypeList.add(tmp);
		}
		return getbtypeList;        
    }
	
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> statuschk(Object parameterObject) throws Exception {
		List<EgovMap> statuschkList = (List<EgovMap>) commonMapper.getSelectList("monitor.statuschk", parameterObject);
		if(statuschkList == null || statuschkList.size() == 0){
			statuschkList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("collStatus", "S");
			tmp.put("tongStatus", "S");
			tmp.put("infStatus", "S");
			tmp.put("sysStatus", "S");
			statuschkList.add(tmp);
		}
		return statuschkList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> system(Object parameterObject) throws Exception {
		List<EgovMap> systemList = (List<EgovMap>) commonMapper.getSelectList("monitor.system", parameterObject);
		if(systemList == null || systemList.size() == 0){
			systemList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("collStatus", "S");
			tmp.put("tongStatus", "S");
			tmp.put("infStatus", "S");
			tmp.put("sysStatus", "S");
			systemList.add(tmp);
		}
		return systemList;        
    }
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> network(Object parameterObject) throws Exception {
		List<EgovMap> networkList = (List<EgovMap>) commonMapper.getSelectList("monitor.network", parameterObject);
		if(networkList == null || networkList.size() == 0){
			networkList = new ArrayList<EgovMap>();
			EgovMap tmp = new EgovMap();
			tmp.put("collStatus", "S");
			tmp.put("tongStatus", "S");
			tmp.put("infStatus", "S");
			tmp.put("sysStatus", "S");
			networkList.add(tmp);
		}
		return networkList;        
    }
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> pbxtong(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.pbxtong", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<EgovMap> almtong(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.almtong", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> daybtype(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.daybtype", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> daynobill(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.daynobill", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> daycheck(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.daycheck", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EgovMap> getSysDetail(Object parameterObject) throws Exception {
		try {
			return (List<EgovMap>) commonMapper.getSelectList("monitor.getSysDetail", parameterObject);
		} catch (Exception e) {
			return new ArrayList<EgovMap>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean delalarm(Object parameterObject) throws Exception {
		return monitorMapper.delalarm(parameterObject);
	}
	
	@SuppressWarnings("unchecked")
	public boolean upalarm(Object parameterObject) throws Exception {
		return monitorMapper.upalarm(parameterObject);
	}
	
	
	
}