package com.hsck.ubfw.component.remoteConn.ssg;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class insertExecute{
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
	public insertExecute() throws Exception{
		log.debug("insertExecute 생성자 실행");
	}
	
	public void satExe(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				//US7ASCII 변환
				String us7ascii = StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(0)).toString()); 
				stat.setString(1, us7ascii);
				stat.setDate(2, (Date)sendList.get(i).get(sendList.get(i).get(1)));
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(2)).toString());
				int rsint = stat.executeUpdate();
				
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug("insert result fail");
		}
		
	}
	/* 수주품의 */
	public void satInsert(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 21; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(5)).toString());
				stat.setString(7, sendList.get(i).get(sendList.get(i).get(6)).toString());
				stat.setString(8, sendList.get(i).get(sendList.get(i).get(7)).toString());
				stat.setString(9, sendList.get(i).get(sendList.get(i).get(8)).toString());
				stat.setString(10, sendList.get(i).get(sendList.get(i).get(9)).toString());
				stat.setString(11, sendList.get(i).get(sendList.get(i).get(10)).toString());
				stat.setString(12, sendList.get(i).get(sendList.get(i).get(11)).toString());
				stat.setString(13, sendList.get(i).get(sendList.get(i).get(12)).toString());
				stat.setString(14, sendList.get(i).get(sendList.get(i).get(13)).toString());
				stat.setString(15, sendList.get(i).get(sendList.get(i).get(14)).toString());//charge_contents
				stat.setString(16, sendList.get(i).get(sendList.get(i).get(15)).toString());
				stat.setString(17, sendList.get(i).get(sendList.get(i).get(16)).toString());
				stat.setString(18, sendList.get(i).get(sendList.get(i).get(17)).toString());
				stat.setString(19, sendList.get(i).get(sendList.get(i).get(18)).toString());
				stat.setString(20, sendList.get(i).get(sendList.get(i).get(19)).toString());
				stat.executeUpdate();
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
		
	}

	public void satUpdate(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute update start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 21; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(5)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(6)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(7)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(8)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(9)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(10)).toString());
				stat.setString(7, sendList.get(i).get(sendList.get(i).get(11)).toString());
				stat.setString(8, sendList.get(i).get(sendList.get(i).get(12)).toString());
				stat.setString(9, sendList.get(i).get(sendList.get(i).get(13)).toString());
				stat.setString(10, sendList.get(i).get(sendList.get(i).get(14)).toString());
				stat.setString(11, sendList.get(i).get(sendList.get(i).get(15)).toString());
				stat.setString(12, sendList.get(i).get(sendList.get(i).get(16)).toString());
				stat.setString(13, sendList.get(i).get(sendList.get(i).get(18)).toString());
				stat.setString(14, sendList.get(i).get(sendList.get(i).get(19)).toString());
				stat.setString(15, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(16, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(17, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(18, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(19, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.executeUpdate();
				
				log.debug("update result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "update result fail");
		}
		
	}
	
	public void custInsert(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start count="+sendList.size());
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 15; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(5)).toString());
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(6)).toString()).length() > 50) {
					stat.setString(7, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(6)).toString()).substring(0,49));
				}else {
					stat.setString(7, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(6)).toString()));
				}
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(7)).toString()).length() > 300) {
					stat.setString(8, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(7)).toString()).substring(0,299));
				}else {
					stat.setString(8, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(7)).toString()));
				}
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(8)).toString()).length() > 300) {
					stat.setString(9, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(8)).toString()).substring(0,299));
				}else {
					stat.setString(9, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(8)).toString()));
				}
				stat.setString(10, sendList.get(i).get(sendList.get(i).get(9)).toString());
				stat.setString(11, sendList.get(i).get(sendList.get(i).get(10)).toString());
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(11)).toString()).length() > 200) {
					stat.setString(12, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(11)).toString()).substring(0,199));
				}else {
					stat.setString(12, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(11)).toString()));
				}
				stat.setString(13, sendList.get(i).get(sendList.get(i).get(12)).toString());
				stat.setString(14, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(13)).toString()));
				stat.setString(15, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(14)).toString()));
				stat.executeUpdate();
				
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
		
	}
	
	public void insertgl(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start gl");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 34; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(5)).toString());
				stat.setString(7, sendList.get(i).get(sendList.get(i).get(6)).toString());
				stat.setString(8, sendList.get(i).get(sendList.get(i).get(7)).toString());
				stat.setString(9, sendList.get(i).get(sendList.get(i).get(8)).toString());
				stat.setString(10, sendList.get(i).get(sendList.get(i).get(9)).toString());
				stat.setString(11, sendList.get(i).get(sendList.get(i).get(10)).toString());
				stat.setString(12, sendList.get(i).get(sendList.get(i).get(11)).toString());
				stat.setString(13, sendList.get(i).get(sendList.get(i).get(12)).toString());
				stat.setString(14, sendList.get(i).get(sendList.get(i).get(13)).toString());
				stat.setString(15, sendList.get(i).get(sendList.get(i).get(14)).toString());
				stat.setString(16, sendList.get(i).get(sendList.get(i).get(15)).toString());
				stat.setString(17, sendList.get(i).get(sendList.get(i).get(16)).toString());
				stat.setString(18, sendList.get(i).get(sendList.get(i).get(17)).toString());
				stat.setString(19, sendList.get(i).get(sendList.get(i).get(18)).toString());
				stat.setString(20, sendList.get(i).get(sendList.get(i).get(19)).toString());
				stat.setString(21, sendList.get(i).get(sendList.get(i).get(20)).toString());
				stat.setString(22, sendList.get(i).get(sendList.get(i).get(21)).toString());
				stat.setString(23, sendList.get(i).get(sendList.get(i).get(22)).toString());
				stat.setString(24, sendList.get(i).get(sendList.get(i).get(23)).toString());
				stat.setString(25, sendList.get(i).get(sendList.get(i).get(24)).toString());
				stat.setString(26, sendList.get(i).get(sendList.get(i).get(25)).toString());
				stat.setString(27, sendList.get(i).get(sendList.get(i).get(26)).toString());
				stat.setString(28, sendList.get(i).get(sendList.get(i).get(27)).toString());
				stat.setString(29, sendList.get(i).get(sendList.get(i).get(28)).toString());
				stat.setString(30, sendList.get(i).get(sendList.get(i).get(29)).toString());
				stat.setString(31, sendList.get(i).get(sendList.get(i).get(30)).toString());
				stat.setString(32, sendList.get(i).get(sendList.get(i).get(31)).toString());
				stat.setString(33, sendList.get(i).get(sendList.get(i).get(32)).toString());
				stat.setString(34, sendList.get(i).get(sendList.get(i).get(33)).toString());
				stat.executeUpdate();
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
		
	}
	
	public void inserttemp(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start temp");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 51; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(5)).toString());
				stat.setString(7, sendList.get(i).get(sendList.get(i).get(6)).toString());
				stat.setString(8, sendList.get(i).get(sendList.get(i).get(7)).toString());
				stat.setString(9, sendList.get(i).get(sendList.get(i).get(8)).toString());
				stat.setString(10, sendList.get(i).get(sendList.get(i).get(9)).toString());
				stat.setString(11, sendList.get(i).get(sendList.get(i).get(10)).toString());
				stat.setString(12, sendList.get(i).get(sendList.get(i).get(11)).toString());
				stat.setString(13, sendList.get(i).get(sendList.get(i).get(12)).toString());
				stat.setString(14, sendList.get(i).get(sendList.get(i).get(13)).toString());
				stat.setString(15, sendList.get(i).get(sendList.get(i).get(14)).toString());
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(15)).toString()).length() > 32) {
					stat.setString(16, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(15)).toString()).substring(0,31));
				}else {
					stat.setString(16, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(15)).toString()));
				}
				stat.setString(17, sendList.get(i).get(sendList.get(i).get(16)).toString());
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(17)).toString()).length() > 64) {
					stat.setString(18, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(17)).toString()).substring(0,63));
				}else {
					stat.setString(18, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(17)).toString()));
				}
				if(StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(15)).toString()).length() > 320) {
					stat.setString(19, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(18)).toString()).substring(0,319));
				}else {
					stat.setString(19, StringUtil.euckrTous7ascii(sendList.get(i).get(sendList.get(i).get(18)).toString()));
				}
				stat.setString(20, sendList.get(i).get(sendList.get(i).get(19)).toString());
				stat.setString(21, sendList.get(i).get(sendList.get(i).get(20)).toString());
				stat.setString(22, sendList.get(i).get(sendList.get(i).get(21)).toString());
				stat.setString(23, sendList.get(i).get(sendList.get(i).get(22)).toString());
				stat.setString(24, sendList.get(i).get(sendList.get(i).get(23)).toString());
				stat.setString(25, sendList.get(i).get(sendList.get(i).get(24)).toString());
				stat.setString(26, sendList.get(i).get(sendList.get(i).get(25)).toString());
				stat.setString(27, sendList.get(i).get(sendList.get(i).get(26)).toString());
				stat.setString(28, sendList.get(i).get(sendList.get(i).get(27)).toString());
				stat.setString(29, sendList.get(i).get(sendList.get(i).get(28)).toString());
				stat.setString(30, sendList.get(i).get(sendList.get(i).get(29)).toString());
				stat.setString(31, sendList.get(i).get(sendList.get(i).get(30)).toString());
				stat.setString(32, sendList.get(i).get(sendList.get(i).get(31)).toString());
				stat.setString(33, sendList.get(i).get(sendList.get(i).get(32)).toString());
				stat.setString(34, sendList.get(i).get(sendList.get(i).get(33)).toString());
				stat.setString(35, sendList.get(i).get(sendList.get(i).get(34)).toString());
				stat.setString(36, sendList.get(i).get(sendList.get(i).get(35)).toString());
				stat.setString(37, sendList.get(i).get(sendList.get(i).get(36)).toString());
				stat.setString(38, sendList.get(i).get(sendList.get(i).get(37)).toString());
				stat.setString(39, sendList.get(i).get(sendList.get(i).get(38)).toString());
				stat.setString(40, sendList.get(i).get(sendList.get(i).get(39)).toString());
				stat.setString(41, sendList.get(i).get(sendList.get(i).get(40)).toString());
				stat.setString(42, sendList.get(i).get(sendList.get(i).get(41)).toString());
				stat.setString(43, sendList.get(i).get(sendList.get(i).get(42)).toString());
				stat.setString(44, sendList.get(i).get(sendList.get(i).get(43)).toString());
				stat.setString(45, sendList.get(i).get(sendList.get(i).get(44)).toString());
				stat.setString(46, sendList.get(i).get(sendList.get(i).get(45)).toString());
				stat.setString(47, sendList.get(i).get(sendList.get(i).get(46)).toString());
				stat.setString(48, sendList.get(i).get(sendList.get(i).get(47)).toString());
				stat.setString(49, sendList.get(i).get(sendList.get(i).get(48)).toString());
				stat.setString(50, sendList.get(i).get(sendList.get(i).get(49)).toString()); 
				stat.setString(51, sendList.get(i).get(sendList.get(i).get(50)).toString());
				//stat.setString(52, sendList.get(i).get(sendList.get(i).get(51)).toString());
				//stat.setString(53, sendList.get(i).get(sendList.get(i).get(52)).toString());
				stat.executeUpdate();
				
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
		
	}
	
	public void deletegl(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 4; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.executeUpdate();
				
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
	}
	
	public void deletetemp(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 4; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(1)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.executeUpdate();
				
				log.debug("insert result sucess");
			}
			log.debug("method exeute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
	}
}	
