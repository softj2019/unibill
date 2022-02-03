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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.rte.psl.dataaccess.util.CamelUtil;



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
	
	public remotedbconn(String dbType, String ip, String port, String db_nm, String userid, String passwd ) throws Exception{
		this.dbType = dbType;
		this.userId = userid;
		this.password = passwd;
		
        InputStream in = this.getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
        Properties props = new Properties();
        props.load(in);
        
        if(dbType.equals("1")){
        	driver = props.getProperty("Globals.RemoteOracleDriverClassName");
        	url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + db_nm;
        }else if(dbType.equals("2")){
        	driver = props.getProperty("Globals.RemoteMariaDriverClassName");
        	url = "jdbc:mariadb://" + ip + ":" + port +"/" + db_nm;
        }else if(dbType.equals("5")) {
        	driver = props.getProperty("Globals.RemotePostgresDriverClassName");
        	url = "jdbc:postgresql://" + ip + ":" + port +"/" + db_nm;
        }
        
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
	
	public ArrayList<HashMap> getselectList(PreparedStatement stat, String insTmpSQL) throws Exception{
		stat.setFetchSize(patchsize);
		int result = 0;
        try{
        	// sql 실행
        	rs = stat.executeQuery();
        	
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String statstr = "";
			
			for(int i=0; i<columnCount; i++){
				if(i==0){
					statstr += "?";
				}else{
					statstr += ",?";
				}
			}
			insTmpSQL += statstr + ")";
			System.out.println("insTmpSQL : " + insTmpSQL);
			//디비접속
			dbconn insertInsaconn = new dbconn();
			PreparedStatement insaStat = insertInsaconn.getstatement(insTmpSQL);
			
			while(rs.next()){
				String val = "val : ";
				for(int i=1; i<=columnCount; i++) {
					String name = rsmd.getColumnName(i);
					
					if(rs.getString(name) != null){
						insaStat.setString(i, rs.getString(name));
						val = val + ", " + rs.getString(name);
					}else{
						insaStat.setString(i, "");
						val = val + ", " + rs.getString(name);
					}
					
				}
				insaStat.addBatch();
				
    			
			}
			
			int[] k = insaStat.executeBatch();
			int success = 0;
			for(int i=0; i<k.length; i++) {
				if(k[i] == 1) success ++;
			}
			
			
			// The column count starts from 1
			
			resultList = new ArrayList<HashMap>();
			
			

        }catch(Exception e){
        	log.debug("임시 테이블 삽입중 예외 발생");
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
