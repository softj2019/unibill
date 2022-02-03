package com.hsck.ubfw.component.monitor.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : ContentService.java
 * @Description : 공통 컨텐츠 interface Class
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
	
public interface MonitorService {
	
	public List<EgovMap> daytong(Object parameterObject) throws Exception;
	
	public List<EgovMap> montong(Object parameterObject) throws Exception;
	
	public List<EgovMap> prevmonth(Object parameterObject) throws Exception;
	
	public List<EgovMap> statuschk(Object parameterObject) throws Exception;
	
	public List<EgovMap> monthcur(Object parameterObject) throws Exception;
	
	public List<EgovMap> daycur(Object parameterObject) throws Exception;
	
	public List<EgovMap> getbtype(Object parameterObject) throws Exception;
	
	public List<EgovMap> system(Object parameterObject) throws Exception;
	
	public List<EgovMap> network(Object parameterObject) throws Exception;
	
	public List<EgovMap> daycheck(Object parameterObject) throws Exception;
	
	public List<EgovMap> pbxtong(Object parameterObject) throws Exception;
	
	public List<EgovMap> daybtype(Object parameterObject) throws Exception;
	
	public List<EgovMap> daynobill(Object parameterObject) throws Exception;
	
	public List<EgovMap> almtong(Object parameterObject) throws Exception;
	
	public List<EgovMap> getSysDetail(Object parameterObject) throws Exception;
	
	public boolean delalarm(Object parameterObject) throws Exception;
	
	public boolean upalarm(Object parameterObject) throws Exception;
}