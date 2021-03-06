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
package com.hsck.ubfw.component.fwmenu.web;

import com.hsck.ubfw.component.fwmenu.service.FwmenuService;
import com.hsck.ubfw.component.fwmenu.vo.FwmenuVO;
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
public class FwmenuController {

	private static final Logger LOG = LoggerFactory.getLogger(FwmenuController.class);

	@Resource(name = "fwmenuService")
	private FwmenuService fwmenuService;

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
	
//	// /fwmenu/listBasic.do
//	// /fwmenu/Fwmenu/listCode.do
	@RequestMapping(value = "/fwmenu/listFwmenu.do")
	public String listFwmenu(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO, ModelMap model) throws Exception {
		
		LOG.debug("listFwmenu : fwmenuVO=" + fwmenuVO.toString() );
		
		List<FwmenuVO> resultList = fwmenuService.listFwmenu( fwmenuVO );


		// {pageSize=10, pageIndex=1, searchPrototypeTitle=}
		Integer pageSize = fwmenuVO.getPageSize();
		Integer page = fwmenuVO.getPageIndex();
		int totalCnt = fwmenuService.getFwmenuCnt(fwmenuVO);
//		int totalCnt = 0;
//		if( 0 < resultList.size() ){
//			
//			totalCnt = Integer.parseInt( resultList.get(0).getTotalCount() );
//		}
		//int totalPage = (int) Math.ceil(totalCnt / pageSize);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", totalCnt);
        model.addAttribute("pageIndex", page);
		

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(fwmenuVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwmenuVO.getPageUnit());
        paginationInfo.setPageSize(fwmenuVO.getPageSize());
        paginationInfo.setTotalRecordCount(totalCnt);

        model.addAttribute("resultCnt", totalCnt );
        model.addAttribute("paginationInfo", paginationInfo);
        
        

        model.addAttribute("fwmenuVO", fwmenuVO);
        model.addAttribute("resultList", resultList);

		// View??????
		return "unibill_tiles/fwmenu/fwmenuList";
	}

//	@RequestMapping(value = "/fwmenu/getFwmenu.do")
//	public String getFwmenu(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		fwmenuVO = fwmenuService.getFwmenu( fwmenuVO );
//
//        model.addAttribute("fwmenuVO", fwmenuVO);
//
//		// View??????
//		return "unibill_tiles/fwmenu/fwmenuRead";
//	}
//
//	@RequestMapping(value = "/fwmenu/goInsertFwmenu.do")
//	public String goInsertFwmenu(ModelMap model) throws Exception {
//		return "unibill_tiles/fwmenu/fwmenuCret";	
//	}
//	
//	@RequestMapping(value = "/fwmenu/insertFwmenu.do")
//	public String insertFwmenu(@ModelAttribute("frmFwmenu") @Valid FwmenuVO fwmenuVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwmenuVO", fwmenuVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwmenu/fwmenuCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwmenuToken")) {  
//	        String insertPK = fwmenuService.insertFwmenu(fwmenuVO);
//	        retMsg = "??????????????? (??????)?????????????????????.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwmenu/listFwmenu.do";
//	}
//
//	@RequestMapping(value = "/fwmenu/goUpdateFwmenu.do")
//	public String goUpdateFwmenu(@ModelAttribute("frmFwmenu") @Valid FwmenuVO fwmenuVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		fwmenuVO = fwmenuService.getFwmenu( fwmenuVO );
//
//        model.addAttribute("fwmenuVO", fwmenuVO);
//		return "unibill_tiles/fwmenu/fwmenuUpdt";	
//	}
//
//	@RequestMapping(value = "/fwmenu/updateFwmenu.do")
//	public String updateFwmenu(@ModelAttribute("frmFwmenu") @Valid FwmenuVO fwmenuVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwmenuVO", fwmenuVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwmenu/fwmenuCret";
//		}
//		
//		if( StringUtils.isBlank( fwmenuVO.getCodeId() ) ){
//			retMsg = "???????????? ?????? ???????????????.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwmenuToken")) {  
//		    	
//		    	int updateCnt = fwmenuService.updateFwmenu( fwmenuVO );
//		    	retMsg = "??????????????? (??????)?????????????????????.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwmenu/listFwmenu.do";
//	}
//
	@RequestMapping(value = "/fwmenu/deleteFwmenu.do")
	public String deletetFwmenu(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

		String retMsg = "";

		
		if( StringUtils.isBlank( fwmenuVO.getDelUbseq() ) ){
			retMsg = "????????? ????????? ????????????.";
		}else{
			
			fwmenuVO.setDelUbseqList( fwmenuVO.getDelUbseq().split(","));
			
			int deletetCnt = fwmenuService.deleteFwmenu( fwmenuVO );
			retMsg = "??????????????? (??????)?????????????????????.!";
		}

		redirectAttributes.addFlashAttribute("retMsg", retMsg);
		return "redirect:/fwmenu/listFwmenu.do";
	}


	@RequestMapping(value = "/fwmenu/goSaveFwmenu.do")
	public String goSaveFwmenu(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO , ModelMap model) throws Exception {

//		if( 0 == fwmenuscrinVO.getUbseq() ){
//			fwmenuVO = fwmenuService.getFwmenu( fwmenuVO );
//		}

        model.addAttribute("fwmenuVO", fwmenuVO);

		return "unibill_tiles/fwmenu/fwmenuSave";	
	}
	
	@RequestMapping(value = "/fwmenu/saveFwmenu.do")
	public String saveFwmenu(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

		String retMsg = "???????????? ??????????????????";

//		validator.validate(fwmenuVO, bindingResult); //validation ??????
		if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...
		    model.addAttribute("fwmenuVO", fwmenuVO);

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
			}
			
			model.addAttribute("retMsg", retMsg);
			return "unibill_tiles/fwmenu/fwmenuSave";
//			throw new BindException(bindingResult); // WEB-INF/config/framework/springmvc/com-servlet.xml : <prop key="org.springframework.validation.BindException">validationJsonView</prop>
//			return "validationJsonView";
		}
		
		
		int resultCnt = fwmenuService.saveFwmenu( fwmenuVO ); 
				
		if( 0 < resultCnt ){
			retMsg = "??????????????? ?????????????????????.";
		}else{
			retMsg = "????????? ????????? ????????????.";
		}

		Map<String, Object> returnResultMap = new HashMap<String,Object>();
		returnResultMap.put("retMsg", retMsg);
		redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);
	      
		return "redirect:/fwmenu/listFwmenu.do";
	}
	
	
	
   @RequestMapping(value = "/fwmenu/listFwmenuJqgrid.json")
   public @ResponseBody Map<String, Object> listFwmenuJqgridJson(@ModelAttribute FwmenuVO fwmenuVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

	   PaginationInfo paginationInfo = new PaginationInfo();
	   paginationInfo.setCurrentPageNo(fwmenuVO.getPageIndex());
	   paginationInfo.setRecordCountPerPage(fwmenuVO.getRecordCountPerPage());
	   paginationInfo.setPageSize(fwmenuVO.getPageSize());

	   fwmenuVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	   fwmenuVO.setLastIndex(paginationInfo.getLastRecordIndex());

       // ????????????
       List<FwmenuVO> data = fwmenuService.listFwmenu( fwmenuVO );

       // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

	   int totalCnt = fwmenuService.getFwmenuCnt(fwmenuVO);
//	   int totalCnt = data.size();
       fwmenuVO.setTotalRecordCount(totalCnt);

       int totalPage = 1;
       if( fwmenuVO.getTotalRecordCount() > (fwmenuVO.getRecordCountPerPage() ) ){

           totalPage = fwmenuVO.getTotalRecordCount() / fwmenuVO.getRecordCountPerPage();
           int mod = fwmenuVO.getTotalRecordCount()%fwmenuVO.getRecordCountPerPage();
           if( 0 < mod ){
               totalPage = totalPage + 1;
           }

       }


       Map<String , Object> returnMap = new HashMap<String , Object>();

       //??????

//		Object page; // ?????? ?????????
//		Object total; // rows??? ?????? ??? ????????????
//		Object records; // ???????????? ????????? ??????
//		Object totalRecords; // ??? ????????? row ???
//		Object rows; // ????????? data List (key:value)
       
       returnMap.put("retCd", "R");
       returnMap.put("retMsg", "");
       returnMap.put("page", fwmenuVO.getPageIndex());

       returnMap.put("total", totalPage);
       returnMap.put("records", fwmenuVO.getRecordCountPerPage() );
       returnMap.put("totalRecords", fwmenuVO.getTotalRecordCount() );
       returnMap.put("rows", data);
       
       return returnMap;

   }
	
	
	@RequestMapping(value = "/fwmenu/saveFwmenu.json")
	public @ResponseBody Map<String, Object> saveFwmenuJson(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		String retCd = "FAILE";
		String retMsg = "???????????? ??????????????????";

		if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
			}
		}else{
			
			int resultCnt = fwmenuService.saveFwmenu( fwmenuVO ); 
			
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
	
	
	
	
	
	
//
   @RequestMapping(value = "/fwmenu/deleteFwmenu.json")
   public @ResponseBody Map<String, Object> deletetFwmenuJson(@ModelAttribute("frmFwmenu") FwmenuVO fwmenuVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

       String retMsg = "????????? ????????? ????????????.";
       String retCd = "FAILE";

       Map<String , Object> returnMap = new HashMap<String , Object>();

       if ( "0".equals(fwmenuVO.getUbseq())) { //?????? validation ????????? ?????????...

       }else{

//			fwmenuVO.setDelCodeIdList( fwmenuVO.getDelCodeId().split(","));

           int deletetCnt = fwmenuService.deleteFwmenu( fwmenuVO );
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
//		return "redirect:/fwmenu/listFwmenu.do";


   }
}
