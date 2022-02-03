package com.hsck.ubfw.component.scheduler;

import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class schedulerExcute {
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	
	public schedulerExcute() throws Exception{
		log.debug("UCsyncExcute 생성자 실행");
	}
	
	public void inmatInsert(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute insert start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 8; j++) {
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
				stat.executeUpdate();
				log.debug("insert result sucess");
			}
			log.debug("method execute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
	}
	
	public void inmatUpdate(List<EgovMap> sendList, PreparedStatement stat) throws Exception{
		log.debug("method exeute update start");
		try {
			for(int i=0; i<sendList.size(); i++) {
				for(int j = 0; j < 8; j++) {
					log.info("["+j+"]: " + sendList.get(i).get(sendList.get(i).get(j)).toString());
				}
				stat.setString(1, sendList.get(i).get(sendList.get(i).get(2)).toString());
				stat.setString(2, sendList.get(i).get(sendList.get(i).get(3)).toString());
				stat.setString(3, sendList.get(i).get(sendList.get(i).get(4)).toString());
				stat.setString(4, sendList.get(i).get(sendList.get(i).get(5)).toString());
				stat.setString(5, sendList.get(i).get(sendList.get(i).get(6)).toString());
				stat.setString(6, sendList.get(i).get(sendList.get(i).get(7)).toString());
				stat.setString(7, sendList.get(i).get(sendList.get(i).get(0)).toString());
				stat.executeUpdate();
				log.debug("update result sucess");
			}
			log.debug("method execute end");
		} catch (Exception e) {
			log.debug(String.valueOf(e).replaceAll("[\r\n]","") + "insert result fail");
		}
	}
}
 