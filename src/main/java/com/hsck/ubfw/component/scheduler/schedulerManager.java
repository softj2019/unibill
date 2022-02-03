package com.hsck.ubfw.component.scheduler;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hsck.ubfw.component.scheduler.db.remotedbconn;
import com.hsck.ubfw.component.scheduler.query.queryList;
import com.hsck.ubfw.component.content.service.ContentService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@EnableScheduling
@Service
public class schedulerManager{
	private final Logger log = LoggerFactory.getLogger(this.getClass()); //logback
	
	//1분: 0 * * * * *
	@Scheduled(cron = "0 */10 * * * *")
	public void run() throws Exception{
			log.debug("remote insert thred start");
			HashMap parameterObject = new HashMap();
			parameterObject.put("regId",'-');
			parameterObject.put("updId",'-');
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();		  		      		    
			ContentService service = (ContentService) webApplicationContext.getBean("contentService");
			parameterObject.put("erpProcYN", 'N');
			List<EgovMap> sendList = service.remoteInsertData("content.selectRavs", parameterObject);
			
			log.info(sendList.toString());
			int seq = 10000;
			String old = "";
			for(int i = 0; i < sendList.size(); i++) {
				 String name = (String)sendList.get(i).get("inName");
				 String trDate = (String)sendList.get(i).get("trDate");
				 String erpProcYn = (String)sendList.get(i).get("erpProcYn");
				 parameterObject.put("custCd", (String)sendList.get(i).get("custCd"));
				 int deposit = service.depositCnt(parameterObject);
				 if(deposit == 1) {
					 log.info(Integer.toString(deposit));
				 }else if(name.replaceAll(" ", "").contains("보증금") && "N".equals(erpProcYn)) {
					service.updateRavs2(parameterObject);
				 }
				parameterObject.put("trDate", trDate);
				int tseq = service.tdaySeqCnt(parameterObject);
				log.info("trdate: " + trDate);
				
				if(tseq == seq && tseq != 0){
					seq += 1;
				}else if(tseq == 0 && !old.equals(trDate)) {
					seq = 10000;
				}else if(tseq == 0) {
					seq = seq;
				}else {
					seq = tseq + 1;
				}
				parameterObject.put("seq", seq);
				parameterObject.put("custCd", (String)sendList.get(i).get("custCd"));
				service.updateRavs(parameterObject);
				old = trDate;
				seq++;
			}
			
			
			remotedbconn remoteconn = new remotedbconn("tonghap");
			queryList queryList = new queryList();
			String query = queryList.getQueryList("updateInamt");
			String className = "com.hsck.ubfw.component.scheduler.schedulerExcute";
			String method = "inmatUpdate";
			
			//재전송
			parameterObject.put("erpProcYN", 'R');
			List<EgovMap> sendList2 = service.remoteInsertData("content.selectRavs", parameterObject);
			remoteconn.getselectList(remoteconn, query, sendList2, className, method);
			
			remotedbconn remoteconn2 = new remotedbconn("tonghap");
			queryList queryList2 = new queryList();
			String query2 = queryList.getQueryList("insertInamt");
			String className2 = "com.hsck.ubfw.component.scheduler.schedulerExcute";
			String method2 = "inmatInsert";
			
			//전송
			parameterObject.put("erpProcYN", 'N');
			List<EgovMap> sendList3 = service.remoteInsertData("content.selectRavs", parameterObject);
			remoteconn.getselectList(remoteconn2, query2, sendList3, className2, method2);
			
			for(int i = 0; i < sendList2.size(); i++) {
				parameterObject.put("custCd", (String)sendList2.get(i).get("custCd"));
				service.updateRavs3(parameterObject);
			}
			
			for(int i = 0; i < sendList3.size(); i++) {
				parameterObject.put("custCd", (String)sendList3.get(i).get("custCd"));
				service.updateRavs3(parameterObject);
			}
			
			
			
			log.debug("remote insert thred end");
	}
}
 