package com.hsck.ubfw.component.cmCfggrpid.web;

import com.hsck.ubfw.component.cmCfggrpid.service.CmCfggrpidService;
import com.hsck.ubfw.component.cmCfggrpid.vo.CmCfggrpidVO;
import com.hsck.ubfw.component.cmCfggrptype.service.CmCfggrptypeService;
import com.hsck.ubfw.component.cmCfggrptype.vo.CmCfggrptypeVO;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 /**
 * 
 * @author 
 * @since 0000.00.00.
 */
@Controller
public class CmCfggrpidController {

	private static final Logger LOG = LoggerFactory.getLogger(CmCfggrpidController.class);

	@Resource(name = "cmCfggrpidService")
	private CmCfggrpidService cmCfggrpidService;

	@Resource(name = "cmCfggrptypeService")
	private CmCfggrptypeService cmCfggrptypeService;

	
	
//	@Resource(name = "beanValidatorJSR303")
//	Validator validator;
	
//	@InitBinder
//	public void initBinder(WebDataBinder dataBinder){
//		try {
//			dataBinder.setValidator(this.validator);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	// /cmCfggrpid/listBasic.do
//	// /cmCfggrpid/CmCfggrpid/listCode.do
	@RequestMapping(value = "/cmCfggrpid/listCmCfggrpid.do")
	public String listCmCfggrpid(@ModelAttribute("frmCmCfggrpid") CmCfggrpidVO cmCfggrpidVO, ModelMap model) throws Exception {
		
		LOG.debug("listCmCfggrpid : cmCfggrpidVO=" + cmCfggrpidVO.toString() );
		CmCfggrptypeVO cmCfggrptypeVO = new CmCfggrptypeVO();
		List<CmCfggrptypeVO> cmCfggrptypeList = cmCfggrptypeService.listCmCfggrptype(cmCfggrptypeVO);

//        List<CmCfggrpidVO> resultList = cmCfggrpidService.listCmCfggrpid( cmCfggrpidVO );
        List<CmCfggrpidVO> resultList = null;


		// {pageSize=10, pageIndex=1, searchPrototypeTitle=}
		Integer pageSize = cmCfggrpidVO.getPageSize();
		Integer page = cmCfggrpidVO.getPageIndex();
//		int totalCnt = cmCfggrpidService.getCmCfggrpidCnt(cmCfggrpidVO);
		int totalCnt = 0;
		if( null != resultList && 0 < resultList.size() ){

			totalCnt = resultList.get(0).getTotalRecordCount();
		}
		//int totalPage = (int) Math.ceil(totalCnt / pageSize);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", totalCnt);
        model.addAttribute("pageIndex", page);
		

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(cmCfggrpidVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(cmCfggrpidVO.getPageUnit());
        paginationInfo.setPageSize(cmCfggrpidVO.getPageSize());
        paginationInfo.setTotalRecordCount(totalCnt);

        model.addAttribute("resultCnt", totalCnt );
        model.addAttribute("paginationInfo", paginationInfo);
        
        

        model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
        model.addAttribute("resultList", resultList);
        model.addAttribute("cmCfggrptypeList", cmCfggrptypeList);
        
		// View??????
		return "unibill_tiles/cmCfggrpid/cmCfggrpidList";
//		return "unibill_tiles/cmCfggrpid/jqgrid";
	}


	@RequestMapping(value = "/cmCfggrpid/listCmCfggrpid.json")
	public @ResponseBody Map<String, Object> getJqgridList(@ModelAttribute CmCfggrpidVO cmCfggrpidVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(cmCfggrpidVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(cmCfggrpidVO.getRecordCountPerPage());
		paginationInfo.setPageSize(cmCfggrpidVO.getPageSize());

		cmCfggrpidVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		cmCfggrpidVO.setLastIndex(paginationInfo.getLastRecordIndex());

		// ????????????
		List<CmCfggrpidVO> data = cmCfggrpidService.listCmCfggrpid( cmCfggrpidVO );

		// {pageSize=10, pageIndex=1, searchPrototypeTitle=}
		Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "10"));
		Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));
		int totalCnt = cmCfggrpidService.getCmCfggrpidCnt(cmCfggrpidVO);
		//int totalPage = (int) Math.ceil(totalCnt / records);


		Map<String , Object> returnMap = new HashMap<String , Object>();

		//??????

//		Object page; // ?????? ?????????
//		Object total; // rows??? ?????? ??? ????????????
//		Object records; // ???????????? ????????? ??????
//		Object totalRecords; // ??? ????????? row ???
//		Object rows; // ????????? data List (key:value)
		
		returnMap.put("retCd", "R");
		returnMap.put("retMsg", "");
		returnMap.put("page", "1");
		returnMap.put("total", "5");
		returnMap.put("records", "10");
		returnMap.put("totalRecords", totalCnt);
		returnMap.put("rows", data);

		return returnMap;

	}

//	@RequestMapping(value = "/cmCfggrpid/getCmCfggrpid.do")
//	public String getCmCfggrpid(@ModelAttribute("frmCmCfggrpid") CmCfggrpidVO cmCfggrpidVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		cmCfggrpidVO = cmCfggrpidService.getCmCfggrpid( cmCfggrpidVO );
//
//        model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
//
//		// View??????
//		return "unibill_tiles/cmCfggrpid/cmCfggrpidRead";
//	}
//
//	@RequestMapping(value = "/cmCfggrpid/goInsertCmCfggrpid.do")
//	public String goInsertCmCfggrpid(ModelMap model) throws Exception {
//		return "unibill_tiles/cmCfggrpid/cmCfggrpidCret";	
//	}
//	
//	@RequestMapping(value = "/cmCfggrpid/insertCmCfggrpid.do")
//	public String insertCmCfggrpid(@ModelAttribute("frmCmCfggrpid") @Valid CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/cmCfggrpid/cmCfggrpidCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmCmCfggrpidToken")) {  
//	        String insertPK = cmCfggrpidService.insertCmCfggrpid(cmCfggrpidVO);
//	        retMsg = "??????????????? (??????)?????????????????????.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/cmCfggrpid/listCmCfggrpid.do";
//	}
//
//	@RequestMapping(value = "/cmCfggrpid/goUpdateCmCfggrpid.do")
//	public String goUpdateCmCfggrpid(@ModelAttribute("frmCmCfggrpid") @Valid CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		cmCfggrpidVO = cmCfggrpidService.getCmCfggrpid( cmCfggrpidVO );
//
//        model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
//		return "unibill_tiles/cmCfggrpid/cmCfggrpidUpdt";	
//	}
//
//	@RequestMapping(value = "/cmCfggrpid/updateCmCfggrpid.do")
//	public String updateCmCfggrpid(@ModelAttribute("frmCmCfggrpid") @Valid CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/cmCfggrpid/cmCfggrpidCret";
//		}
//		
//		if( StringUtils.isBlank( cmCfggrpidVO.getCodeId() ) ){
//			retMsg = "???????????? ?????? ???????????????.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmCmCfggrpidToken")) {  
//		    	
//		    	int updateCnt = cmCfggrpidService.updateCmCfggrpid( cmCfggrpidVO );
//		    	retMsg = "??????????????? (??????)?????????????????????.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/cmCfggrpid/listCmCfggrpid.do";
//	}
//
	@RequestMapping(value = "/cmCfggrpid/deleteCmCfggrpidTree.json")
	public @ResponseBody Map<String, Object> deletetCmCfggrpidTree(@ModelAttribute("frmCmCfggrpid") CmCfggrpidVO cmCfggrpidVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

		String retMsg = "????????? ????????? ????????????.";
		String retCd = "FAILE";

		Map<String , Object> returnMap = new HashMap<String , Object>();

//		if ( null == cmCfggrpidVO.getUbseq() || 0 == cmCfggrpidVO.getUbseq() ) { //?????? validation ????????? ?????????...
		if ( StringUtils.isBlank( cmCfggrpidVO.getGrpType() ) || StringUtils.isBlank( cmCfggrpidVO.getGrpId() ) ) { //?????? validation ????????? ?????????...
			retMsg = "?????? ?????? or ?????? ??????????????? ????????????.";
		}else{

//			cmCfggrpidVO.setDelCodeIdList( cmCfggrpidVO.getDelCodeId().split(","));

			int deletetCnt = cmCfggrpidService.deleteCmCfggrpidTree( cmCfggrpidVO );
			retMsg = "??????????????? (??????)?????????????????????.!";

			if( 0 < deletetCnt ){
				retCd = "SUCCESS";
				retMsg = "??????????????? ?????????????????????.";
			}else{
				retMsg = "????????? ????????? ????????????.";
			}

		}

		returnMap.put("retCd", retCd);
		returnMap.put("retMsg", retMsg);

		return returnMap;
//		return "redirect:/cmCfggrpid/listCmCfggrpid.do";


	}


	@RequestMapping(value = "/cmCfggrpid/goSaveCmCfggrpid.do")
	public String goSaveCmCfggrpid(@ModelAttribute("frmCmCfggrpid") CmCfggrpidVO cmCfggrpidVO , ModelMap model) throws Exception {

//		List<CmCfggrpidVO> cmCfggrpidGroupList = cmCfggrpidService.listCmCfggrpidGroup( new CmCfggrpidVO() );
//		
//        String searchCodeGroup = cmCfggrpidVO.getSearchCodeGroup();
//        String searchCodeName = cmCfggrpidVO.getSearchCodeName();
//
//		
//		if( StringUtils.isNoneBlank( cmCfggrpidVO.getCodeId() ) ){
//			
//			cmCfggrpidVO = cmCfggrpidService.getCmCfggrpid( cmCfggrpidVO );
//			cmCfggrpidVO.setSearchCodeGroup(searchCodeGroup);
//			cmCfggrpidVO.setSearchCodeName(searchCodeName);
//		}

        model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);
//        model.addAttribute("cmCfggrpidGroupList", cmCfggrpidGroupList);

        
		return "unibill_tiles/cmCfggrpid/cmCfggrpidSave";	
	}
	
	@RequestMapping(value = "/cmCfggrpid/saveCmCfggrpid.do")
	public String saveCmCfggrpid(@ModelAttribute("frmCmCfggrpid") CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

		String retMsg = "???????????? ??????????????????";

//		validator.validate(cmCfggrpidVO, bindingResult); //validation ??????
		if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...
		    model.addAttribute("cmCfggrpidVO", cmCfggrpidVO);

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
			}
			
			model.addAttribute("retMsg", retMsg);
			return "unibill_tiles/cmCfggrpid/cmCfggrpidSave";
//			throw new BindException(bindingResult); // WEB-INF/config/framework/springmvc/com-servlet.xml : <prop key="org.springframework.validation.BindException">validationJsonView</prop>
//			return "validationJsonView";
		}
		
		
		int resultCnt = cmCfggrpidService.saveCmCfggrpid( cmCfggrpidVO ); 
				
		if( 0 < resultCnt ){
			retMsg = "??????????????? ?????????????????????.";
		}else{
			retMsg = "????????? ????????? ????????????.";
		}

		Map<String, Object> returnResultMap = new HashMap<String,Object>();
		returnResultMap.put("retMsg", retMsg);
		redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);
	      
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		redirectAttributes.addFlashAttribute("searchCodeGroup", cmCfggrpidVO.getSearchCodeGroup());
//		redirectAttributes.addFlashAttribute("searchCodeName", cmCfggrpidVO.getSearchCodeName());
		return "redirect:/cmCfggrpid/listCmCfggrpid.do";
	}



	 @RequestMapping(value = "/cmCfggrpid/saveCmCfggrpid.json")
	 public @ResponseBody Map<String, Object> saveCmCfggrpidJson(@ModelAttribute CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status, HttpServletResponse response) throws Exception {

		 Map<String , Object> returnMap = new HashMap<String , Object>();
		 String retCd = "FAILE";
		 String retMsg = "???????????? ??????????????????";

		 if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...

			 List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			 for( FieldError fieldError : fieldErrorList ){

				 retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
			 }
		 }else{

			 int resultCnt = cmCfggrpidService.saveCmCfggrpid( cmCfggrpidVO );

			 if( 0 < resultCnt ){
				 retCd = "SUCCESS";
				 retMsg = "??????????????? ?????????????????????.";
			 }else{
				 retMsg = "????????? ????????? ????????????.";
			 }
		 }

		 returnMap.put("retCd", retCd);
		 returnMap.put("retMsg", retMsg);

		 return returnMap;
	 }

	 @RequestMapping(value = "/cmCfggrpid/insertCmCfggrpid.json")
	 public @ResponseBody Map<String, Object> insertCmCfggrpidJson(@ModelAttribute CmCfggrpidVO cmCfggrpidVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status, HttpServletResponse response) throws Exception {

		 Map<String , Object> returnMap = new HashMap<String , Object>();
		 String retCd = "FAILE";
		 String retMsg = "???????????? ??????????????????";
		 long resultCnt = 0;

//		 if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...
//
//			 List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			 for( FieldError fieldError : fieldErrorList ){
//
//				 retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
//			 }
		 if( StringUtils.isBlank(cmCfggrpidVO.getGrpType()) ){
			 retMsg = retMsg + "\n" + "?????? ???????????? ??????????????????.";
		 }else{
			 resultCnt = Integer.parseInt(cmCfggrpidService.insertCmCfggrpid(cmCfggrpidVO ));

			 if( 0 < resultCnt ){
				 retCd = "SUCCESS";
				 retMsg = "??????????????? ?????????????????????.";
			 }else{
				 retMsg = "????????? ????????? ????????????.";
			 }

		 }

		 returnMap.put("retCd", retCd);
		 returnMap.put("retMsg", retMsg);
		 returnMap.put("resultGrpId", resultCnt);

		 return returnMap;
	 }

}
