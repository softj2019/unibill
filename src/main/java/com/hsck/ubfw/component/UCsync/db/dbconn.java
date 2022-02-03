package com.hsck.ubfw.component.UCsync.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;



public class dbconn {
	String dbType = "";   	// 디비 타입
	String driver = "";		// 드라이버
	String url = "";  		// 디비 접속 URL
	String userId = "";		// 디비 접속 유저
	String password = "";	// 디비 접속 패스워드
	int patchsize = 100; 	// 한번에 읽어 들일 사이즈
	int batchsize = 100; 	// 한번에 처리할 사이즈
	
	Connection  connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    
    ArrayList<HashMap> resultList = null;
	
	public dbconn() throws Exception{
		int result = 0;
        
        //InputStream in = this.getClass().getResourceAsStream("/conf/globals.properties");
		InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
        Properties props = new Properties();
        props.load(in);
        
        if (dbType == null || "".equals(dbType)) dbType = props.getProperty("Globals.DbType");	
        if (driver == null || "".equals(driver)) driver = props.getProperty("Globals.DriverClassName");
        if (url == null || "".equals(url)) url = props.getProperty("Globals.Url");
        if (userId == null || "".equals(userId)) userId = props.getProperty("Globals.UserName");
        if (password == null || "".equals(password)) password = props.getProperty("Globals.Password");
        if (props.getProperty("Globals.BatchSize") != null && !"".equals(props.getProperty("Globals.BatchSize"))) 
        	batchsize = Integer.parseInt(props.getProperty("Globals.BatchSize"));
        if (props.getProperty("Globals.Patchsize") != null && !"".equals(props.getProperty("Globals.Patchsize"))) 
        	patchsize = Integer.parseInt(props.getProperty("Globals.Patchsize"));
        
        Class.forName(driver);
    	connection = DriverManager.getConnection(url, userId, password);
    	
	}
	
	public Connection getdbconn() throws Exception{
		return connection;
	}
	
	public PreparedStatement getstatement(String sql) throws Exception{
		statement = connection.prepareStatement(sql);
		return statement;
	}
	
	public ArrayList<HashMap> getselectList(PreparedStatement stat) throws Exception{
		stat.setFetchSize(patchsize);
		int result = 0;
        try{
        	// sql 실행
        	rs = stat.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// The column count starts from 1
			
			resultList = new ArrayList<HashMap>();
			while(rs.next()){ 
				HashMap colMap = new HashMap();
				for(int i=1; i<=columnCount; i++) {
					String name = rsmd.getColumnName(i);
					colMap.put(name, rs.getString(name));
			  	}
				resultList.add(colMap);
			}
			
			
			

        }catch(Exception e){
        	result = -1;
        }finally{
        	if (statement != null) try {statement.close();statement = null;} catch(SQLException ex){}
            if (connection != null) try {connection.close();connection = null;} catch(SQLException ex){}
        }
//        
//        errorList.add(result);
//        errorList.add(errorCnt);
        return resultList;
        
	}
	
	
	public ArrayList<HashMap> insert(PreparedStatement stat) throws Exception{
		stat.setFetchSize(patchsize);
		int result = 0;
        try{

        	result = stat.executeUpdate();
        	
        }catch(Exception e){
        	result = -1;
        }finally{
        	if (statement != null) try {statement.close();statement = null;} catch(SQLException ex){}
            if (connection != null) try {connection.close();connection = null;} catch(SQLException ex){}
        }
//        
//        errorList.add(result);
//        errorList.add(errorCnt);
        return resultList;
        
	}
	
	
	
	
	
    

}
