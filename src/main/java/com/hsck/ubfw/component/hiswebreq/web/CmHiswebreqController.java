/*******************************************************************************
 * COPYRIGHT(C) 2016 HANSSAK UNIBILL ALL RIGHTS RESERVED.
 * This software is the proprietary information of HANSSAK UNIBILL.
 *
 * Revision History
 * Author   Date            Description
 * ------   ----------      ------------------------------------
 * AA    0000.00.00.         First Draft.( Auto Code Generate )
 *
 *******************************************************************************/
package com.hsck.ubfw.component.hiswebreq.web;

import com.hsck.ubfw.component.com.cmm.util.ContentUtil;
import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.com.cmm.util.VoUtil;
import com.hsck.ubfw.component.comm.vo.LoginInfo;
import com.hsck.ubfw.component.content.service.ContentService;
import com.hsck.ubfw.component.hiswebreq.service.CmHiswebreqService;
import com.hsck.ubfw.component.hiswebreq.vo.CmHiswebreqVO;
import com.hsck.ubfw.component.hiswebreq.vo.instPayVO;
import com.hsck.ubfw.component.main.model.LoginVO;

import egovframework.com.cmm.service.Globals;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.validation.Valid;
/**
 *
 * @author
 * @since 0000.00.00.
 */
@Controller
public class CmHiswebreqController {

	private static final Logger LOG = LoggerFactory
			.getLogger(CmHiswebreqController.class);

	@Resource(name = "contentService")
	private ContentService contentService;

	@Resource(name = "cmHiswebreqService")
	private CmHiswebreqService cmHiswebreqService;

	/**
	 * ??????HDS ??? ?????? ?????? ??????.( ?????? ?????? ?????? ?????? ??? ?????? ?????? ??????(DB????????? ??????)??? ????????????. ????????? ReqStatus???
	 * "P"?????? ?????? ?????????. )
	 *
	 * @param cmHiswebreqVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmHiswebreq/saveCmHiswebreq.do")
	public String saveCmHiswebreq(
			@ModelAttribute("frmCmHiswebreq") CmHiswebreqVO cmHiswebreqVO,ModelMap model, HttpServletRequest request,HttpServletResponse response) throws Exception {

		// ?????? ??????????????? ??????
		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
		//
		String resultMsg = "";
		String errorMsg = "";
		String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code")); // ??????id
		String tableName = StringUtil.isNullToString(request.getParameter("tableName")); // ????????????
		String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag")); // ?????? ??????
		String ubseq = StringUtil.isNullToString(request.getParameter("ubseq")); // ????????????
		String ubSeqArr = StringUtil.isNullToString(request.getParameter("ubSeqArr")); // ???????????? ?????????
		String reg_id = loginVO.getUserId();
		String upd_id = loginVO.getUserId();

		// LOG.info("########## ?????? (??????/??????/??????) : [scrin_code : " + scrin_code +
		// "][actionFlag : " + actionFlag + "][ubseq : " + ubseq +
		// "][ubSeqArr : " + ubSeqArr + "][reg_id : " + reg_id + "][upd_id : " +
		// upd_id + "]");

		HashMap<String, Object> parameterObject = new HashMap<String, Object>();
		BeanUtils.populate(parameterObject, request.getParameterMap());

		// ?????? ?????? ??????
		parameterObject.put("scrin_code", scrin_code); // ??????ID
		parameterObject.put("showyn", ""); // ???????????? ????????????
		parameterObject.put("find_show_yn", ""); // ???????????? ????????????
		parameterObject.put("dtl_show_yn", "Y"); // ?????? ????????????
		parameterObject.put("role_code", loginVO.getRoleId()); // ??????id
		List<EgovMap> selectColumnsList = contentService.selectSearchObjectList(parameterObject);

		List<EgovMap> keyColList = new ArrayList<EgovMap>();

		if (actionFlag.equals("INSERT") || actionFlag.equals("UPDATE")) {

			// insert ?????? ??????
			List insertColumnsList = ContentUtil.insertColumnsList(request,selectColumnsList, "Columns");

			// insert ????????? ??????
			List insertDataList = ContentUtil.insertColumnsList(request,selectColumnsList, "Data");

			parameterObject.put("tableName", tableName); // ????????????
			parameterObject.put("reg_id", reg_id); // ?????????id
			parameterObject.put("upd_id", upd_id); // ?????????id
			parameterObject.put("insertColumnsList", insertColumnsList);
			parameterObject.put("insertDataList", insertDataList);
			parameterObject.put("masterTableName", tableName); // ???????????? (key ??????
																// ?????????)

			String bill_ym = StringUtil.isNullToString(request.getParameter("bill_ym"));

			VoUtil voUtil = new VoUtil();
			cmHiswebreqVO = (CmHiswebreqVO) voUtil.parameterMapToVoConvert(parameterObject, cmHiswebreqVO);

			LoginInfo loginInfo = new LoginInfo();
			loginInfo.putSessionToVo(cmHiswebreqVO); // session??? ????????? VO??? ??????.
			String autotransMonth  = StringUtil.isNullToString(request.getParameter("autotrans_month"));
			cmHiswebreqVO.setAutotransMonth(autotransMonth); 
			cmHiswebreqVO.setBillYm(bill_ym);
			cmHiswebreqVO.setProgress("5");
			cmHiswebreqVO.setReqStatus("P"); // C=??????,F=??????,P=?????????,R=??????,S=??????,X=????????????
			int result = cmHiswebreqService.saveCmHiswebreq(cmHiswebreqVO);// job_type??? db??? ??????.( select obj_sql from fwformo where scrin_id='???' and obj_id='job_type' )

			if (result > 0) {
				resultMsg = "SAVE_SUCCESS";

				// ???????????? key ?????? ?????? ??????
				keyColList = contentService.selectKeyColList(parameterObject);

				try {
					cmHiswebreqVO = cmHiswebreqService.runBillingJobRequest(cmHiswebreqVO);

					resultMsg = "BILLING_JOB_REQUEST";
					errorMsg = cmHiswebreqVO.getMsg();
				} catch (Exception e) {
					resultMsg = "ERROR";
					errorMsg = "?????? ?????? ??????";

					cmHiswebreqVO.setReqStatus("F");
					cmHiswebreqService.saveCmHiswebreq(cmHiswebreqVO);
				}
			} else {
				resultMsg = "ERROR";
			}

		} else if (actionFlag.equals("DELETE")) {

			try {

				parameterObject.put("tableName", tableName); // ????????????
				parameterObject.put("ubSeqArr", ubSeqArr);
				if ("F".equals(parameterObject.get("req_status")) ||
					"S".equals(parameterObject.get("req_status")) ||	
					"X".equals(parameterObject.get("req_status")) ||	
					"C".equals(parameterObject.get("req_status"))   
						) {
					resultMsg = "?????? ?????? ?????????."; 
				} else {
					contentService.deleteContent(parameterObject);
					resultMsg = "DELETE_SUCCESS";
				}

			} catch (Exception e) {
				resultMsg = "ERROR";
				errorMsg = "?????? ?????? ??????";
			}
		}

		// key ?????? ??????
		String keyCol = "";
		keyCol = getKeyCol(keyColList, keyCol);

		String OID = scrin_code + "|" + ubseq + "|" + actionFlag + "|" + keyCol;
		model.addAttribute("OID", OID);
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction"))); // ????????? ?????? ??????

		LOG.info("OID       : " + OID);
		LOG.info("resultMsg : " + resultMsg);
		LOG.info("errorMsg  : " + errorMsg);
		LOG.info("????????? ?????? ??????(callBackFunction) : "
				+ StringUtil.isNullToString(request
						.getParameter("callBackFunction")));

		return "/unibill/com/cmm/commonMsg";
	}

	/**
	 * ????????????????????? ?????? ?????? ??????.( ?????? ?????? ????????? DB??? ???????????? ?????? ?????? ????????? ?????? ??????????????? ?????? ??????????????? ????????????.
	 * ????????? ReqStatus ??? "S"?????? ?????????. )
	 *
	 * @param cmHiswebreqVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmHiswebreq/saveCmHiswebreqIIAC.do")
	 public String saveCmHiswebreqIIAC(@ModelAttribute("frmCmHiswebreq") CmHiswebreqVO cmHiswebreqVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		 // ?????? ??????????????? ??????
		 LoginVO loginVO = (LoginVO) request.getSession().getAttribute(Globals.SESSION_USER_VO);
//
		 String resultMsg = "";
		 String errorMsg = "";
		 String scrin_code = StringUtil.isNullToString(request.getParameter("scrin_code"));   // ??????id
		 String tableName = StringUtil.isNullToString(request.getParameter("tableName"));  // ????????????
		 String actionFlag = StringUtil.isNullToString(request.getParameter("actionFlag")); // ?????? ??????
		 String ubseq = StringUtil.isNullToString(request.getParameter("ubseq"));      // ????????????
		 String ubSeqArr = StringUtil.isNullToString(request.getParameter("ubSeqArr"));   // ???????????? ?????????
		 String jobType = StringUtil.isNullToString(request.getParameter("job_type"));
		 String custId = StringUtil.isNullToString(request.getParameter("rep_cust_id"));
		 String svcId = StringUtil.isNullToString(request.getParameter("svc_id"));
		 String totCost = StringUtil.isNullToString(request.getParameter("tot_cost"));
		 String autotransMonth = StringUtil.isNullToString(request.getParameter("autotrans_month"));
		 String reg_id = loginVO.getUserId();
		 String upd_id = loginVO.getUserId();

		 String billYm = StringUtil.isNullToString(request.getParameter("billYm"));
		 
//		LOG.info("########## ?????? (??????/??????/??????) : [scrin_code : " + scrin_code + "][actionFlag : " + actionFlag + "][ubseq : " + ubseq + "][ubSeqArr : " + ubSeqArr + "][reg_id : " + reg_id + "][upd_id : " + upd_id + "]");

		 HashMap<String, Object> parameterObject = new HashMap<String, Object>();
		 BeanUtils.populate(parameterObject, request.getParameterMap());

		 // ?????? ?????? ??????
		 parameterObject.put("scrin_code", scrin_code); // ??????ID
		 parameterObject.put("showyn", "");         // ???????????? ????????????
		 parameterObject.put("find_show_yn", "");         // ???????????? ????????????
		 parameterObject.put("dtl_show_yn", "Y");        // ?????? ????????????
		 parameterObject.put("role_code", loginVO.getRoleId());  // ??????id
		 parameterObject.put("autotransMonth", autotransMonth);
		 parameterObject.put("billYm", billYm);
		 parameterObject.put("repCustId", custId);  // ??????ID
		 parameterObject.put("svcId", svcId);  // ???????????????
		 parameterObject.put("totCost", totCost);  // ??????????????? ???????????????
		 List<EgovMap> selectColumnsList = contentService.selectSearchObjectList(parameterObject);

		 List<EgovMap> keyColList = new ArrayList<EgovMap>();


		 if (actionFlag.equals("INSERT") || actionFlag.equals("UPDATE") || actionFlag.equals("WORK_CANCEL")) {

			 // insert ?????? ??????
			 List insertColumnsList = ContentUtil.insertColumnsList(request, selectColumnsList, "Columns");

			 // insert ????????? ??????
			 List insertDataList = ContentUtil.insertColumnsList(request, selectColumnsList, "Data");

			 parameterObject.put("tableName", tableName);   // ????????????
			 parameterObject.put("reg_id", reg_id);         // ?????????id
			 parameterObject.put("upd_id", upd_id);         // ?????????id
			 parameterObject.put("insertColumnsList", insertColumnsList);
			 parameterObject.put("insertDataList", insertDataList);
			 parameterObject.put("masterTableName", tableName);   // ???????????? (key ?????? ?????????)
			 parameterObject.put("jobType", jobType); 
			 

			 VoUtil voUtil = new VoUtil();
			 cmHiswebreqVO = (CmHiswebreqVO) voUtil.parameterMapToVoConvert(parameterObject, cmHiswebreqVO);// : job_type??? db??? ??????.( select obj_sql from fwformo where scrin_id='???' and obj_id='job_type' )
			 LoginInfo loginInfo = new LoginInfo();
			 loginInfo.putSessionToVo(cmHiswebreqVO); // session??? ????????? VO??? ??????.

			 String procNm = "";
			 String para = "";
			 
			 if(!totCost.equals("")) {
				 para = "-tot_cost " + totCost;
			 }
			 
			 cmHiswebreqVO.setAutotransMonth(autotransMonth); 
			 cmHiswebreqVO.setProcNm(procNm);
			 cmHiswebreqVO.setPara(para);
			 cmHiswebreqVO.setPid("");
			 cmHiswebreqVO.setBillYm(billYm);
			 cmHiswebreqVO.setRepCustId(custId);
			 cmHiswebreqVO.setSvcId(svcId);
			 
			 /*if( "10".equals( cmHiswebreqVO.getJobType() ) ){
				 String sday = StringUtil.isNullToString(request.getParameter("sday"));
				 cmHiswebreqVO.setSday(sday);
				 cmHiswebreqVO.setEday(sday);
			 }else{
				 String bill_ym = StringUtil.isNullToString(request.getParameter("bill_ym"));
				 cmHiswebreqVO.setBillYm(bill_ym);
			 }*/

			 if(actionFlag.equals("WORK_CANCEL")){

				 String ubSeqArr2 = StringUtil.isNullToString(request.getParameter("ubSeqArr2"));   // ?????? ?????? ?????? ?????????
				 String reqStatus = StringUtil.isNullToString(request.getParameter("req_status"));
				 
				 if (	("R".equals(reqStatus) || "P".equals(reqStatus) || "T".equals(reqStatus)) 
						 && 
						 StringUtils.isNoneBlank( ubSeqArr2 ) 
						 ) { 
                     String[] ubSeqArr2List = ubSeqArr2.split(",");

					 cmHiswebreqVO.setUbSeqArr2(ubSeqArr2List);
					 cmHiswebreqVO.setReqStatus("C"); // C=??????,F=??????,P=?????????,R=??????,S=??????,X=????????????
					 cmHiswebreqService.updateCmHiswebreq(cmHiswebreqVO);
					 resultMsg = "??????????????? ?????? ?????????????????????.";
				 } else { 
					 resultMsg = "?????? ????????? ????????? ????????????.";
				 }
			 }else{

				 cmHiswebreqVO.setProgress("0");
				 cmHiswebreqVO.setReqStatus("R"); // C=??????,F=??????,P=?????????,R=??????,S=??????,X=????????????

				 int result = cmHiswebreqService.saveCmHiswebreq(cmHiswebreqVO);

				 if (result > 0) {
					 resultMsg = "SAVE_SUCCESS";

					 // ???????????? key ?????? ?????? ??????
					 keyColList = contentService.selectKeyColList(parameterObject);

					 try {
						 cmHiswebreqVO = cmHiswebreqService.runBillingIIACJobRequest(cmHiswebreqVO); //<-------- saveCmHiswebreq??? ?????? ??????!!(2017.04.03)

					 } catch (Exception e) {
						 resultMsg = "ERROR";
						 errorMsg = "?????? ?????? ??????";

						 cmHiswebreqVO.setReqStatus("F");
						 cmHiswebreqService.saveCmHiswebreq(cmHiswebreqVO);
					 }
				 } else {
					 resultMsg = "ERROR";
				 }
			 }


		 } else if (actionFlag.equals("DELETE")) {

			 try {

				 parameterObject.put("tableName", tableName);  // ????????????
				 parameterObject.put("ubSeqArr", ubSeqArr);
				 if (
						 //"R".equals(parameterObject.get("req_status")) &&
						 StringUtils.isNoneBlank( ubSeqArr )
						 ) {

					 contentService.deleteContent(parameterObject);
					 resultMsg = "??????????????? ?????? ?????????????????????.";
				 } else {
					 resultMsg = "????????????????????? ?????? ???????????????.";
				 }

			 } catch (Exception e) {
				 resultMsg = "ERROR";
				 errorMsg = "?????? ?????? ??????";
			 }
		 }


		 // key ?????? ??????
		 String keyCol = "";
		 keyCol = getKeyCol(keyColList, keyCol);

		 String OID = scrin_code + "|" + ubseq + "|" + actionFlag + "|" + keyCol;
		 model.addAttribute("OID", OID);
		 model.addAttribute("resultMsg", resultMsg);
		 model.addAttribute("errorMsg", errorMsg);
		 model.addAttribute("callBackFunction", StringUtil.isNullToString(request.getParameter("callBackFunction")));    // ????????? ?????? ??????

		 LOG.info("OID       : " + OID);
		 LOG.info("resultMsg : " + resultMsg);
		 LOG.info("errorMsg  : " + errorMsg);
		 LOG.info("????????? ?????? ??????(callBackFunction) : " + StringUtil.isNullToString(request.getParameter("callBackFunction")));

		 return "/unibill/com/cmm/commonMsg";
	 }

	/**
	 * key ?????? ??????
	 *
	 * @param keyColList
	 * @param keyCol
	 * @return
	 */
	private String getKeyCol(List<EgovMap> keyColList, String keyCol) {
		if (keyColList.size() > 0) {
			for (int i = 0; i < keyColList.size(); i++) {

				if (i == 0) {
					keyCol = (String) keyColList.get(i).get("keyCol");
				} else {
					keyCol += "@" + (String) keyColList.get(i).get("keyCol");
				}

			}
		}
		return keyCol;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/cmHiswebreq/hotbill_proc.json")
	public @ResponseBody Map<String, Object> hotbill_proc(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String,Object>();
		cmHiswebreqService.hotbill_proc(model, request, response);
		
		return rtnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/cmHiswebreq/getHotbill.json")
	public @ResponseBody Map<String, Object> getHotbill(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rtnMap = cmHiswebreqService.getHotbill(model, request, response);
		return rtnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/cmHiswebreq/hotbill_proc_commit.json")
	public @ResponseBody Map<String, Object> hotbill_proc_commit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> rtnMap = new HashMap<String,Object>();
		int sucFlag = cmHiswebreqService.hotbill_proc_commit(model, request, response);
		rtnMap.put("successFlag", sucFlag);
		return rtnMap;
	}
}
