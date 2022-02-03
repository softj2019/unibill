package com.hsck.ubfw.component.monitor.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.monitor.service.MonitorService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 
 * @Class Name : ContentController.java
 * 클래스 설명 : 공통 컨텐츠 Controller Class
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

@Controller	
public class MonitorController  {

	private static final Object EgovMap = null;

	protected Log log = (Log) LogFactory.getLog(this.getClass());		
	
	/** MonitorService */
	@Resource(name = "monitorService")
	private MonitorService monitorService;
	
	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
	
	@Autowired private PlatformTransactionManager transactionManager;
	
	@RequestMapping(value = "/monitor/selectMonitor.do")
	public String selectMonitor(Model model, HttpServletRequest request) throws Exception {
		HashMap<String, String> dayparameterObject =  new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat dayform = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat monform = new SimpleDateFormat("yyyyMM");
		
		// 현재년,월
		dayparameterObject.put("tday", dayform.format(calendar.getTime()));
		dayparameterObject.put("tmon", monform.format(calendar.getTime()));
		
		//지난년,월
		calendar.add(calendar.MONTH,-1);
		dayparameterObject.put("pretmon", monform.format(calendar.getTime()));
		
		// 당일 통화
		List<EgovMap> daytongList = monitorService.daytong(dayparameterObject);
		model.addAttribute("daytongcnt",daytongList.get(0).get("cnt"));
		model.addAttribute("daytongcost",daytongList.get(0).get("cost"));
		
		// 월별 통화
		List<EgovMap> montongList = monitorService.montong(dayparameterObject);
		model.addAttribute("montongcnt",montongList.get(0).get("cnt"));
		model.addAttribute("montongcost",montongList.get(0).get("cost"));
		
		//전월대비
		List<EgovMap> prevmonthList = monitorService.prevmonth(dayparameterObject);
		
		model.addAttribute("prevmonthpcnt",Integer.valueOf((prevmonthList.get(0).get("pcnt").toString())) > 999?"999":prevmonthList.get(0).get("pcnt"));
		model.addAttribute("prevmonthpcnt2",Integer.valueOf((prevmonthList.get(0).get("pcnt2").toString())) > 999?"999":prevmonthList.get(0).get("pcnt2"));
		model.addAttribute("prevmonthpcnt3",Integer.valueOf((prevmonthList.get(0).get("pcnt3").toString())) > 999?"999":prevmonthList.get(0).get("pcnt3"));
		model.addAttribute("prevmonthpcost",Integer.valueOf((prevmonthList.get(0).get("pcost").toString())) > 999?"999":prevmonthList.get(0).get("pcost"));
		model.addAttribute("prevmonthpcost2",Integer.valueOf((prevmonthList.get(0).get("pcost2").toString())) > 999?"999":prevmonthList.get(0).get("pcost2"));
		model.addAttribute("prevmonthpcost3",Integer.valueOf((prevmonthList.get(0).get("pcost3").toString())) > 999?"999":prevmonthList.get(0).get("pcost3"));
		
		model.addAttribute("prevmonthprecnt",(prevmonthList.get(0).get("precnt").toString()));
		model.addAttribute("prevmonthcurcnt",(prevmonthList.get(0).get("curcnt").toString()));
		model.addAttribute("prevmonthprecost",(prevmonthList.get(0).get("precost").toString()));
		model.addAttribute("prevmonthcurcost",(prevmonthList.get(0).get("curcost").toString()));
		
		
		//상태체크
		List<EgovMap> statuschkList = monitorService.statuschk(dayparameterObject);
		model.addAttribute("collStatus",statuschkList.get(0).get("collStatus"));
		model.addAttribute("tongStatus",statuschkList.get(0).get("tongStatus"));
		model.addAttribute("infStatus",statuschkList.get(0).get("infStatus"));
		model.addAttribute("sysStatus",statuschkList.get(0).get("sysStatus"));
		
		model.addAttribute("collMsg",statuschkList.get(0).get("collMsg"));
		model.addAttribute("tongMsg",statuschkList.get(0).get("tongMsg"));
		model.addAttribute("infMsg",statuschkList.get(0).get("infMsg"));
		model.addAttribute("sysMsg",statuschkList.get(0).get("sysMsg"));
		
		//6달전월
		Calendar monthcurcalendar = Calendar.getInstance();
		monthcurcalendar.add(monthcurcalendar.MONTH,-5);
		dayparameterObject.put("preyeartmon", monform.format(monthcurcalendar.getTime()));
		
		//월별현황
		List<EgovMap> monthcurList = monitorService.monthcur(dayparameterObject);
		List<String> ymList = new ArrayList<String>();
		
		List<String> data1 = new ArrayList<String>();
		List<String> data2 = new ArrayList<String>();
		List<String> data3 = new ArrayList<String>();
		List<String> data4 = new ArrayList<String>();
		List<String> data5 = new ArrayList<String>();
		List<String> data6 = new ArrayList<String>();
		List<String> data7 = new ArrayList<String>();
		
		List<String> data8 = new ArrayList<String>();
		List<String> data9 = new ArrayList<String>();
		List<String> data10 = new ArrayList<String>();
		List<String> data11 = new ArrayList<String>();
		List<String> data12 = new ArrayList<String>();
		List<String> data13 = new ArrayList<String>();
		List<String> data14 = new ArrayList<String>();
		
		int i=0;
		for(EgovMap eg: monthcurList){
			ymList.add( ((String)eg.get("ym")).substring(4) );
			
			data1.add(eg.get("sinecnt").toString());
			data2.add(eg.get("injubcnt").toString());
			data3.add(eg.get("siuecnt").toString());
			data4.add(eg.get("idongcnt").toString());
			data5.add(eg.get("intercnt").toString());
			data6.add(eg.get("kukjecnt").toString());
			data7.add(eg.get("gitacnt").toString());
			
			data8.add(eg.get("sinecost").toString());
			data9.add(eg.get("injubcost").toString());
			data10.add(eg.get("siuecost").toString());
			data11.add(eg.get("idongcost").toString());
			data12.add(eg.get("intercost").toString());
			data13.add(eg.get("kukjecost").toString());
			data14.add(eg.get("gitacost").toString());
			
			i++;
		}
		
		
		
		List<EgovMap> getbtypeList = monitorService.getbtype(dayparameterObject);
		List<String> btypeList = new ArrayList<String>();
		
		for(EgovMap eg: getbtypeList){
			btypeList.add((String)eg.get("nm"));
		}
		
		model.addAttribute("monthcurx",ymList);
		model.addAttribute("monthcury",btypeList);
		
		model.addAttribute("monthsinecnt",data1);
		model.addAttribute("monthinjubcnt",data2);
		model.addAttribute("monthsiuecnt",data3);
		model.addAttribute("monthidongcnt",data4);
		model.addAttribute("monthintercnt",data5);
		model.addAttribute("monthkukjecnt",data6);
		model.addAttribute("monthgitacnt",data7);
		
		model.addAttribute("monthsinecost",data8);
		model.addAttribute("monthinjubcost",data9);
		model.addAttribute("monthsiuecost",data10);
		model.addAttribute("monthidongcost",data11);
		model.addAttribute("monthintercost",data12);
		model.addAttribute("monthkukjecost",data13);
		model.addAttribute("monthgitacost",data14);
		
		
		//6일전
		Calendar daycurcalendar = Calendar.getInstance();
		daycurcalendar.add(daycurcalendar.DATE,-5);
		dayparameterObject.put("preyeartday", dayform.format(daycurcalendar.getTime()).substring(0,6));
		dayparameterObject.put("pretday", dayform.format(daycurcalendar.getTime()).substring(6,8));
		
		//일별현황
		List<EgovMap> daycurList = monitorService.daycur(dayparameterObject);
		List<String> tdayList = new ArrayList<String>();
		
		List<String> daydata1 = new ArrayList<String>();
		List<String> daydata2 = new ArrayList<String>();
		List<String> daydata3 = new ArrayList<String>();
		List<String> daydata4 = new ArrayList<String>();
		List<String> daydata5 = new ArrayList<String>();
		List<String> daydata6 = new ArrayList<String>();
		List<String> daydata7 = new ArrayList<String>();
		
		List<String> daydata8 = new ArrayList<String>();
		List<String> daydata9 = new ArrayList<String>();
		List<String> daydata10 = new ArrayList<String>();
		List<String> daydata11 = new ArrayList<String>();
		List<String> daydata12 = new ArrayList<String>();
		List<String> daydata13 = new ArrayList<String>();
		List<String> daydata14 = new ArrayList<String>();
		
		for(EgovMap eg: daycurList){
			tdayList.add(((String)eg.get("tday")).substring(6,8) );
			
			daydata1.add(eg.get("sinecnt").toString());
			daydata2.add(eg.get("injubcnt").toString());
			daydata3.add(eg.get("siuecnt").toString());
			daydata4.add(eg.get("idongcnt").toString());
			daydata5.add(eg.get("intercnt").toString());
			daydata6.add(eg.get("kukjecnt").toString());
			daydata7.add(eg.get("gitacnt").toString());
			
			daydata8.add(eg.get("sinecost").toString());
			daydata9.add(eg.get("injubcost").toString());
			daydata10.add(eg.get("siuecost").toString());
			daydata11.add(eg.get("idongcost").toString());
			daydata12.add(eg.get("intercost").toString());
			daydata13.add(eg.get("kukjecost").toString());
			daydata14.add(eg.get("gitacost").toString());
		}
		
		model.addAttribute("daycurx",tdayList);
		
		model.addAttribute("daysinecnt",daydata1);
		model.addAttribute("dayinjubcnt",daydata2);
		model.addAttribute("daysiuecnt",daydata3);
		model.addAttribute("dayidongcnt",daydata4);
		model.addAttribute("dayintercnt",daydata5);
		model.addAttribute("daykukjecnt",daydata6);
		model.addAttribute("daygitacnt",daydata7);
		
		model.addAttribute("daysinecost",daydata8);
		model.addAttribute("dayinjubcost",daydata9);
		model.addAttribute("daysiuecost",daydata10);
		model.addAttribute("dayidongcost",daydata11);
		model.addAttribute("dayintercost",daydata12);
		model.addAttribute("daykukjecost",daydata13);
		model.addAttribute("daygitacost",daydata14);
		
		List<EgovMap> systemList = monitorService.system(dayparameterObject);
		List<String> systemCPUPcntList = new ArrayList<String>();
		List<String> systemMEMPcntList = new ArrayList<String>();
		List<String> systemRCPUPcntList = new ArrayList<String>();
		List<String> systemRMEMPcntList = new ArrayList<String>();
		List<String> systemROOTPcntList = new ArrayList<String>();
		List<String> systemHOMEPcntList = new ArrayList<String>();
		List<String> systemDATAPcntList = new ArrayList<String>();
		List<String> systemDBPcntList = new ArrayList<String>();
		String homenm = "";
		for(EgovMap eg: systemList){
			if(StringUtil.isNullToString(eg.get("resId")).equals("CPU")){
				systemCPUPcntList.add(StringUtil.isNullToString(eg.get("useRate")));
				systemRCPUPcntList.add(StringUtil.isNullToString(eg.get("ruseRate")));
			}else if(StringUtil.isNullToString(eg.get("resId")).equals("MEM")){
				systemMEMPcntList.add(StringUtil.isNullToString(eg.get("useRate")));
				systemRMEMPcntList.add(StringUtil.isNullToString(eg.get("ruseRate")));
			}
			
			if(StringUtil.isNullToString(eg.get("resNm")).equals("/")){
				systemROOTPcntList.add(eg.get("useRate").toString());
			}else if(StringUtil.isNullToString(eg.get("resNm")).equals("/home") || StringUtil.isNullToString(eg.get("resNm")).equals("/pkg")){
				homenm = StringUtil.isNullToString(eg.get("resNm"));
				systemHOMEPcntList.add(StringUtil.isNullToString(eg.get("useRate")));
			}else if(StringUtil.isNullToString(eg.get("resNm")).equals("/data")){
				systemDATAPcntList.add(StringUtil.isNullToString(eg.get("useRate")));
			}else if(StringUtil.isNullToString(eg.get("resNm")).equals("/db")){
				systemDBPcntList.add(StringUtil.isNullToString(eg.get("useRate")));
			}
			
		}
		
		//시스템현황
		model.addAttribute("systemCPU",systemCPUPcntList.size() == 0?"0":systemCPUPcntList);
		model.addAttribute("systemMEM",systemMEMPcntList.size() == 0?"0":systemMEMPcntList);
		model.addAttribute("systemRCPU",systemCPUPcntList.size() == 0?"100":systemRCPUPcntList);
		model.addAttribute("systemRMEM",systemMEMPcntList.size() == 0?"100":systemRMEMPcntList);
		
		//디스크사용현황
		model.addAttribute("systemROOT",systemROOTPcntList);
		model.addAttribute("systemHOMEnm",homenm);
		model.addAttribute("systemHOME",systemHOMEPcntList);
		model.addAttribute("systemDATA",systemDATAPcntList);
		model.addAttribute("systemDB",systemDBPcntList);
		
		//네트워크 트래픽
		//현재시간(24시)
		Calendar hourcurcalendar = Calendar.getInstance();
		SimpleDateFormat hourform = new SimpleDateFormat("yyyyMMddHHmmss");
		hourcurcalendar.add(hourcurcalendar.HOUR,-5);
		dayparameterObject.put("thour", hourform.format(hourcurcalendar.getTime()));
		
		List<EgovMap> network = monitorService.network(dayparameterObject);
		List<String> networX = new ArrayList<String>();
		List<String> networkRXList = new ArrayList<String>();
		List<String> networkTXList = new ArrayList<String>();
		for(EgovMap eg: network){
			networX.add(eg.get("thour").toString().substring(8));
			
			networkRXList.add(eg.get("rxBytes").toString());
			networkTXList.add(eg.get("txBytes").toString());
		}
		model.addAttribute("networX",networX);
		model.addAttribute("networkRX",networkRXList);
		model.addAttribute("networkTX",networkTXList);
		
		
		//당일 호종류 현황
		List<EgovMap> daybtypeList = monitorService.daybtype(dayparameterObject);
		model.addAttribute("daybtypeList", daybtypeList);
		List<Integer> dayBtypePcntList = new ArrayList<Integer>();
		List<String>  dayBtypePcntNameList = new ArrayList<String>();
		for(EgovMap eg: daybtypeList){
			dayBtypePcntList.add(Integer.valueOf(eg.get("pcnt").toString()));
		}
		
		for(EgovMap eg: getbtypeList){
			dayBtypePcntNameList.add((String)eg.get("nm"));
		}
		
		if(dayBtypePcntList.get(0) == 0 && dayBtypePcntList.get(1) == 0 && dayBtypePcntList.get(2) == 0 && dayBtypePcntList.get(3) == 0 && dayBtypePcntList.get(4) == 0 && dayBtypePcntList.get(5) == 0){
			dayBtypePcntList.set(0, 100);
			dayBtypePcntNameList.set(0,"자료없음");
		}
		
		
		
		model.addAttribute("dayBtypePcnt",dayBtypePcntList);
		model.addAttribute("dayBtypePcntName",dayBtypePcntNameList);
		
		//당일 비과금호 현황
		List<EgovMap> daynobillList = null;
		try {
			daynobillList = monitorService.daynobill(dayparameterObject);
		} catch (Exception e) {
			daynobillList =    new ArrayList<EgovMap>();
		}
		model.addAttribute("daynobillList", daynobillList);
		List<String> daynobillCallTypeList = new ArrayList<String>();
		List<Integer> daynobillPcntList = new ArrayList<Integer>();
		if(daynobillList.size() > 0){
			for(EgovMap eg: daynobillList){
				daynobillCallTypeList.add(eg.get("nm").toString());
				daynobillPcntList.add(Integer.valueOf(eg.get("pcnt").toString()));
			}
			if(daynobillPcntList.get(0) == 0 && daynobillPcntList.get(1) == 0 && daynobillPcntList.get(2) == 0 && daynobillPcntList.get(3) == 0 && daynobillPcntList.get(4) == 0){
				daynobillCallTypeList.set(0, "자료없음");
				daynobillPcntList.set(0, 100);
			}
		}else{
			daynobillCallTypeList.add("자료없음");
			daynobillPcntList.add(100);
		}
		model.addAttribute("daynobillCallType",daynobillCallTypeList);
		model.addAttribute("daynobillPcnt",daynobillPcntList);
		//당일 호검사 현황
		List<EgovMap> daycheckList = null;
		try {
			daycheckList = monitorService.daycheck(dayparameterObject);
		} catch (Exception e) {
			daycheckList = new ArrayList<EgovMap>();
		}
		model.addAttribute("daycheckList", daycheckList);
		List<String> daycheckPcntNameList = new ArrayList<String>();
		List<Integer> daycheckPcntList = new ArrayList<Integer>();
		if(daycheckList.size() > 0 ){
			for(EgovMap eg: daycheckList){
				daycheckPcntList.add(Integer.valueOf(eg.get("plongCnt").toString()));
				daycheckPcntList.add(Integer.valueOf(eg.get("phighCnt").toString()));
				daycheckPcntList.add(Integer.valueOf(eg.get("pcallerCnt").toString()));
				daycheckPcntList.add(Integer.valueOf(eg.get("pcalleeCnt").toString()));
				daycheckPcntList.add(Integer.valueOf(eg.get("pchkCnt").toString()));
				daycheckPcntList.add(Integer.valueOf(eg.get("ptelnoErrcnt").toString()));
			}
			
			daycheckPcntNameList.add("장시간");
			daycheckPcntNameList.add("고액통화");
			daycheckPcntNameList.add("발신변작");
			daycheckPcntNameList.add("착신변작");
			daycheckPcntNameList.add("요율미적용");
			daycheckPcntNameList.add("번호오류");
			
			if(daycheckPcntList.get(0) == 0 && daycheckPcntList.get(1) == 0 && daycheckPcntList.get(2) == 0 && daycheckPcntList.get(3) == 0 && daycheckPcntList.get(4) == 0 && daycheckPcntList.get(5) == 0){
				daycheckPcntNameList.set(0, "자료없음");
				daycheckPcntList.set(0, 100);
			}
		}else{
			daycheckPcntNameList.add("자료없음");
			daycheckPcntList.add(100);
		}
		
		model.addAttribute("daycheckPcntNameList", daycheckPcntNameList);
		model.addAttribute("daycheckPcntList", daycheckPcntList);
		
		//통화수집현황
		List<EgovMap> pbxtongList = monitorService.pbxtong(dayparameterObject);
		model.addAttribute("pbxtongList", pbxtongList);
				
		//알람현황
		List<EgovMap>  almtongList = monitorService.almtong(dayparameterObject);
		model.addAttribute("almtongList", almtongList);
		
		return "unibill_pop/pop/monitorPop";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/monitor/getmonthcur.json")
    public @ResponseBody Map<String, Object> getmonthcurListJson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> dayparameterObject =  new HashMap<String, String>();
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		String moncurflag = "";
		if(request.getParameter("moncurflag") == "" || request.getParameter("moncurflag") == null){
			moncurflag = "cnt";
		}else{
			moncurflag = request.getParameter("moncurflag");
		}
		
		
		//6달전월
		SimpleDateFormat monform = new SimpleDateFormat("yyyyMM");
		Calendar monthcurcalendar = Calendar.getInstance();
		
		dayparameterObject.put("tmon", monform.format(monthcurcalendar.getTime()));
		monthcurcalendar.add(monthcurcalendar.MONTH,-5);
		dayparameterObject.put("preyeartmon", monform.format(monthcurcalendar.getTime()));
				
		//월별현황
		List<EgovMap> monthcurList = monitorService.monthcur(dayparameterObject);
		List<String> ymList = new ArrayList<String>();
				
		List<String> data1 = new ArrayList<String>();
		List<String> data2 = new ArrayList<String>();
		List<String> data3 = new ArrayList<String>();
		List<String> data4 = new ArrayList<String>();
		List<String> data5 = new ArrayList<String>();
		List<String> data6 = new ArrayList<String>();
		List<String> data7 = new ArrayList<String>();
				
		List<String> data8 = new ArrayList<String>();
		List<String> data9 = new ArrayList<String>();
		List<String> data10 = new ArrayList<String>();
		List<String> data11 = new ArrayList<String>();
		List<String> data12 = new ArrayList<String>();
		List<String> data13 = new ArrayList<String>();
		List<String> data14 = new ArrayList<String>();
				
		for(EgovMap eg: monthcurList){
			ymList.add( ((String)eg.get("ym")).substring(4) );
					
			data1.add(eg.get("sinecnt").toString());
			data2.add(eg.get("injubcnt").toString());
			data3.add(eg.get("siuecnt").toString());
			data4.add(eg.get("idongcnt").toString());
			data5.add(eg.get("intercnt").toString());
			data6.add(eg.get("kukjecnt").toString());
			data7.add(eg.get("gitacnt").toString());
				
			data8.add(eg.get("sinecost").toString());
			data9.add(eg.get("injubcost").toString());
			data10.add(eg.get("siuecost").toString());
			data11.add(eg.get("idongcost").toString());
			data12.add(eg.get("intercost").toString());
			data13.add(eg.get("kukjecost").toString());
			data14.add(eg.get("gitacost").toString());
					
		}
				
		List<EgovMap> getbtypeList = monitorService.getbtype(dayparameterObject);
		List<String> btypeList = new ArrayList<String>();
				
		for(EgovMap eg: getbtypeList){
			btypeList.add((String)eg.get("nm"));
		}
		
		returnMap.put("monthcurx",ymList);
		returnMap.put("monthcury",btypeList);
				
		returnMap.put("monthsinecnt",data1);
		returnMap.put("monthinjubcnt",data2);
		returnMap.put("monthsiuecnt",data3);
		returnMap.put("monthidongcnt",data4);
		returnMap.put("monthintercnt",data5);
		returnMap.put("monthkukjecnt",data6);
		returnMap.put("monthgitacnt",data7);
				
		returnMap.put("monthsinecost",data8);
		returnMap.put("monthinjubcost",data9);
		returnMap.put("monthsiuecost",data10);
		returnMap.put("monthidongcost",data11);
		returnMap.put("monthintercost",data12);
		returnMap.put("monthkukjecost",data13);
		returnMap.put("monthgitacost",data14);
		
		returnMap.put("moncurflag", moncurflag);
		
		
		return returnMap;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/monitor/getdaythcur.json")
    public @ResponseBody Map<String, Object> getdaythcurListJson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> dayparameterObject =  new HashMap<String, String>();
		Map<String , Object> returnMap = new HashMap<String , Object>();
		
		String daycurflag = "";
		if(request.getParameter("daycurflag") == "" || request.getParameter("daycurflag") == null){
			daycurflag = "cnt";
		}else{
			daycurflag = request.getParameter("daycurflag");
		}
		
		//6일전
		SimpleDateFormat dayform = new SimpleDateFormat("yyyyMMdd");
		Calendar daycurcalendar = Calendar.getInstance();
		dayparameterObject.put("tday", dayform.format(daycurcalendar.getTime()));
		daycurcalendar.add(daycurcalendar.DATE,-5);
		dayparameterObject.put("preyeartday", dayform.format(daycurcalendar.getTime()).substring(0,6));
		dayparameterObject.put("pretday", dayform.format(daycurcalendar.getTime()).substring(6,8));
		
		//일별현황
		List<EgovMap> daycurList = monitorService.daycur(dayparameterObject);
		List<String> tdayList = new ArrayList<String>();
				
		List<String> daydata1 = new ArrayList<String>();
		List<String> daydata2 = new ArrayList<String>();
		List<String> daydata3 = new ArrayList<String>();
		List<String> daydata4 = new ArrayList<String>();
		List<String> daydata5 = new ArrayList<String>();
		List<String> daydata6 = new ArrayList<String>();
		List<String> daydata7 = new ArrayList<String>();
				
		List<String> daydata8 = new ArrayList<String>();
		List<String> daydata9 = new ArrayList<String>();
		List<String> daydata10 = new ArrayList<String>();
		List<String> daydata11 = new ArrayList<String>();
		List<String> daydata12 = new ArrayList<String>();
		List<String> daydata13 = new ArrayList<String>();
		List<String> daydata14 = new ArrayList<String>();
				
		for(EgovMap eg: daycurList){
			tdayList.add( ((String)eg.get("tday")).substring(6,8) );
					
			daydata1.add(eg.get("sinecnt").toString());
			daydata2.add(eg.get("injubcnt").toString());
			daydata3.add(eg.get("siuecnt").toString());
			daydata4.add(eg.get("idongcnt").toString());
			daydata5.add(eg.get("intercnt").toString());
			daydata6.add(eg.get("kukjecnt").toString());
			daydata7.add(eg.get("gitacnt").toString());
				
			daydata8.add(eg.get("sinecost").toString());
			daydata9.add(eg.get("injubcost").toString());
			daydata10.add(eg.get("siuecost").toString());
			daydata11.add(eg.get("idongcost").toString());
			daydata12.add(eg.get("intercost").toString());
			daydata13.add(eg.get("kukjecost").toString());
			daydata14.add(eg.get("gitacost").toString());
		}
		
		List<EgovMap> getbtypeList = monitorService.getbtype(dayparameterObject);
		List<String> btypeList = new ArrayList<String>();
				
		for(EgovMap eg: getbtypeList){
			btypeList.add((String)eg.get("nm"));
		}
				
		returnMap.put("daycurx",tdayList);
		returnMap.put("monthcury",btypeList);
		
		returnMap.put("daysinecnt",daydata1);
		returnMap.put("dayinjubcnt",daydata2);
		returnMap.put("daysiuecnt",daydata3);
		returnMap.put("dayidongcnt",daydata4);
		returnMap.put("dayintercnt",daydata5);
		returnMap.put("daykukjecnt",daydata6);
		returnMap.put("daygitacnt",daydata7);
				
		returnMap.put("daysinecost",daydata8);
		returnMap.put("dayinjubcost",daydata9);
		returnMap.put("daysiuecost",daydata10);
		returnMap.put("dayidongcost",daydata11);
		returnMap.put("dayintercost",daydata12);
		returnMap.put("daykukjecost",daydata13);
		returnMap.put("daygitacost",daydata14);
		
		returnMap.put("daycurflag", daycurflag);
		
		
		return returnMap;
    }
	
	@RequestMapping(value = "/monitor/sysdetail.do")
	public String sysdetail(Model model, HttpServletRequest request) throws Exception {
		
		HashMap<String, String> parameterObject =  new HashMap<String, String>();
		
		List<EgovMap> getSysDetail = monitorService.getSysDetail(parameterObject);
		model.addAttribute("getSysDetail", getSysDetail);
		
		return "unibill_pop/pop/sysdetailPop";	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value="/monitor/delalarm.json")
    public @ResponseBody Map<String, Object> delalarm(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String , Object> returnMap = new HashMap<String , Object>();
		String[] param = request.getParameter("ubseqList").split(",");
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        
        try {
        	boolean result = true;
        	for(int i=0; i<param.length; i++){
        		HashMap<String, String> parameterObject =  new HashMap<String, String>();
        		parameterObject.put("ubseq",param[i]);
        		boolean rs = monitorService.delalarm(parameterObject);
//        		boolean rs = monitorService.upalarm(parameterObject);
    			if(!rs){ result = false; }
    		}
        	
        	if(result) transactionManager.commit(transactionStatus);
        	else transactionManager.rollback(transactionStatus);
        	
        	returnMap.put("delResult",result);
		} catch (Exception e) {
			returnMap.put("delResult",false);
			transactionManager.rollback(transactionStatus);
		}
        
		return returnMap;
	}
}