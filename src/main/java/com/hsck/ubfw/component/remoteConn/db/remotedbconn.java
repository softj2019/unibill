package com.hsck.ubfw.component.remoteConn.db;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;



public class remotedbconn {
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
	String dbType;   		// 디비 타입
	String driver;			// 드라이버
	String url;  			// 디비 접속 URL
	String userId;		// 디비 접속 유저
	String password;		// 디비 접속 패스워드
	int patchsize = 100; 	// 한번에 읽어 들일 사이즈
	int batchsize = 100; 	// 한번에 처리할 사이즈
	
	Connection  connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    
    ArrayList<HashMap> resultList = null;
	
	public remotedbconn(String dbType) throws Exception{
		this.dbType = dbType;
		
		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
        Properties props = new Properties();
        props.load(in);

        if(dbType.equals("tonghap")) {
        	driver = props.getProperty("Globals.tonghap.Remote.DriverClassName");
            url = props.getProperty("Globals.tonghap.Remote.Url");
            userId = props.getProperty("Globals.tonghap.Remote.UserName");
            password = props.getProperty("Globals.tonghap.Remote.Password");
        }else if(dbType.equals("spas")) {
        	driver = props.getProperty("Globals.spas.Remote.DriverClassName");
            url = props.getProperty("Globals.spas.Remote.Url");
            userId = props.getProperty("Globals.spas.Remote.UserName");
            password = props.getProperty("Globals.spas.Remote.Password");
        }
        
        if (props.getProperty("Globals.BatchSize") != null && !"".equals(props.getProperty("Globals.BatchSize"))) 
        	batchsize = Integer.parseInt(props.getProperty("Globals.BatchSize"));
        if (props.getProperty("Globals.Patchsize") != null && !"".equals(props.getProperty("Globals.Patchsize"))) 
        	patchsize = Integer.parseInt(props.getProperty("Globals.Patchsize"));
        
        Class.forName(driver);
        connection = DriverManager.getConnection(url, userId, password);
    	
    	
	}
	
	public void close() throws Exception{
		connection.close();
	}
	
	public Connection getdbconn() throws Exception{
		return connection;
	}
	
	public PreparedStatement getstatement(String sql) throws Exception{
		statement = connection.prepareStatement(sql);
		return statement;
	}
	
	public ArrayList<HashMap> getselectList(remotedbconn conn, String sql, List<EgovMap> sendList, String className, String methodNm) throws Exception{
		PreparedStatement stat = conn.getstatement(sql);
		int result = 0;
        try{
        	stat.setFetchSize(patchsize);
        	
        	Object[] arg = new Object[]{sendList, stat};
        	Class<?> myClass = Class.forName(className);
        	Object obj = myClass.newInstance();
        	Method[] methods = obj.getClass().getMethods();
        	for(Method m : methods) {
        		if(m.getName().equals(methodNm)){
        			//log.debug("param lngth : " + m.getParameterCount() + ", metodName : " + m.getName());
					m.invoke(obj, arg);
				}
			}
        	
        }catch(Exception e){
        	log.debug("");
        	result = -1;
        }finally{
        	if (stat != null) try {stat.close();stat = null;} catch(SQLException ex){}
            if (conn != null) try {conn.close();conn = null;} catch(SQLException ex){}
        }
//        
//        errorList.add(result);
//        errorList.add(errorCnt);
        return resultList;
        
	}
	
	public HashMap getSqlListCnt(remotedbconn rdbconn, String exeQuery) throws Exception{
		String query = "select count(*) as cnt from (" + exeQuery + ") a";
		statement = rdbconn.getstatement(query);
		statement.setFetchSize(patchsize);
		int result = 0;
		HashMap resultMap = new HashMap();
        try{
        	// sql 실행
        	rs = statement.executeQuery();
        	while(rs.next()){ 
				resultMap.put("total_count", rs.getString("cnt"));
			}
			

        }catch(Exception e){
        	resultMap.put("errMsg", "[ERROR] " + e.toString());
        	result = -1;
        }finally{
        	if (statement != null) try {statement.close();statement = null;} catch(SQLException ex){}
            if (connection != null) try {connection.close();connection = null;} catch(SQLException ex){}
            
        }

        return resultMap;
	}
    
	
	public HashMap getSqlList(remotedbconn rdbconn, String exeQuery) throws Exception{
		statement = rdbconn.getstatement(exeQuery);
		statement.setFetchSize(patchsize);
		int result = 0;
		HashMap resultMap = new HashMap();
        try{
        	// sql 실행
        	rs = statement.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// The column count starts from 1
			
			resultList = new ArrayList<HashMap>();
			ArrayList keyList =  new ArrayList();
			ArrayList keyCamelList =  new ArrayList();
			List<LinkedHashMap> rsList = new ArrayList<LinkedHashMap>();
			ArrayList<HashMap> keyColnmList =  new ArrayList<HashMap>();
			int cnt = 0;
			while(rs.next()){ 
				LinkedHashMap rsMap = new LinkedHashMap();
				for(int i=1; i<=columnCount; i++) {
					String name = rsmd.getColumnName(i);
					if(cnt==0) {
						keyList.add(name);
						keyCamelList.add(CamelUtil.convert2CamelCase(name));
						HashMap tmp = new HashMap();
						tmp.put("colId", name);
						keyColnmList.add(tmp);
						rsMap.put(name, rs.getString(name));
					}else {
						rsMap.put(name, rs.getString(name));
					}
			  	}
				rsList.add(rsMap);
				cnt++;
				
				
			}
			resultMap.put("keyList", keyList);
			resultMap.put("keyCamelList", keyCamelList);
			resultMap.put("colId", keyColnmList);
			resultMap.put("rsList", rsList);
			

			

        }catch(Exception e){
        	resultMap.put("errMsg", "[ERROR] " + e.toString());
        	result = -1;
        }finally{
        	if (statement != null) try {statement.close();statement = null;} catch(SQLException ex){}
            if (connection != null) try {connection.close();connection = null;} catch(SQLException ex){}
        }
//        
//        errorList.add(result);
//        errorList.add(errorCnt);
        
        

	
		
		
        
        return resultMap;
        
	}
	
	
    

}
