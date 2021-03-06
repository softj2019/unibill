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
package com.hsck.ubfw.component.fwscrin.web;

import com.hsck.ubfw.component.fwscrin.service.FwscrinService;
import com.hsck.ubfw.component.fwscrin.vo.FwscrinVO;
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
public class FwscrinController{

	private static final Logger LOG = LoggerFactory.getLogger(FwscrinController.class);

	@Resource(name = "fwscrinService")
	private FwscrinService fwscrinService;

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
	
//	// /fwscrin/listBasic.do
//	// /fwscrin/Fwscrin/listCode.do
	@RequestMapping(value = "/fwscrin/listFwscrin.do")
	public String listFwscrin(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO, ModelMap model) throws Exception {
		
		LOG.debug("listFwscrin : fwscrinVO=" + fwscrinVO.toString() );
		
		
		// mysql
 	    int offsetSize = (fwscrinVO.getPageIndex() - 1) * fwscrinVO.getPageSize();
 	    fwscrinVO.setOffsetSize(offsetSize);
 	    fwscrinVO.setLimitSize(fwscrinVO.getPageSize());
		
		List<FwscrinVO> resultList = fwscrinService.listFwscrin( fwscrinVO );


		// {pageSize=10, pageIndex=1, searchPrototypeTitle=}
		Integer pageSize = fwscrinVO.getPageSize();
		Integer page = fwscrinVO.getPageIndex();
		int totalCnt = fwscrinService.getFwscrinCnt(fwscrinVO);
		//int totalPage = (int) Math.ceil(totalCnt / pageSize);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", totalCnt);
        model.addAttribute("pageIndex", page);
		

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(fwscrinVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwscrinVO.getPageUnit());
        paginationInfo.setPageSize(fwscrinVO.getPageSize());
        paginationInfo.setTotalRecordCount(totalCnt);

        model.addAttribute("resultCnt", totalCnt );
        model.addAttribute("paginationInfo", paginationInfo);
        
        

        model.addAttribute("fwscrinVO", fwscrinVO);
        model.addAttribute("resultList", resultList);

		// View??????
		return "unibill_tiles/fwscrin/fwscrinList";
	}

//	@RequestMapping(value = "/fwscrin/getFwscrin.do")
//	public String getFwscrin(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		fwscrinVO = fwscrinService.getFwscrin( fwscrinVO );
//
//        model.addAttribute("fwscrinVO", fwscrinVO);
//
//		// View??????
//		return "unibill_tiles/fwscrin/fwscrinRead";
//	}
//
//	@RequestMapping(value = "/fwscrin/goInsertFwscrin.do")
//	public String goInsertFwscrin(ModelMap model) throws Exception {
//		return "unibill_tiles/fwscrin/fwscrinCret";	
//	}
//	
//	@RequestMapping(value = "/fwscrin/insertFwscrin.do")
//	public String insertFwscrin(@ModelAttribute("frmFwscrin") @Valid FwscrinVO fwscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwscrinVO", fwscrinVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwscrin/fwscrinCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwscrinToken")) {  
//	        String insertPK = fwscrinService.insertFwscrin(fwscrinVO);
//	        retMsg = "??????????????? (??????)?????????????????????.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwscrin/listFwscrin.do";
//	}
//
//	@RequestMapping(value = "/fwscrin/goUpdateFwscrin.do")
//	public String goUpdateFwscrin(@ModelAttribute("frmFwscrin") @Valid FwscrinVO fwscrinVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		fwscrinVO = fwscrinService.getFwscrin( fwscrinVO );
//
//        model.addAttribute("fwscrinVO", fwscrinVO);
//		return "unibill_tiles/fwscrin/fwscrinUpdt";	
//	}
//
//	@RequestMapping(value = "/fwscrin/updateFwscrin.do")
//	public String updateFwscrin(@ModelAttribute("frmFwscrin") @Valid FwscrinVO fwscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "???????????? ??????????????????";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwscrinVO", fwscrinVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwscrin/fwscrinCret";
//		}
//		
//		if( StringUtils.isBlank( fwscrinVO.getCodeId() ) ){
//			retMsg = "???????????? ?????? ???????????????.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwscrinToken")) {  
//		    	
//		    	int updateCnt = fwscrinService.updateFwscrin( fwscrinVO );
//		    	retMsg = "??????????????? (??????)?????????????????????.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwscrin/listFwscrin.do";
//	}
//
	@RequestMapping(value = "/fwscrin/deleteFwscrin.do")
	public String deletetFwscrin(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

		String retMsg = "";

		
		if( StringUtils.isBlank( fwscrinVO.getDelUbseq() ) ){
			retMsg = "????????? ????????? ????????????.";
		}else{
			
			fwscrinVO.setDelUbseqList( fwscrinVO.getDelUbseq().split(","));
			
			int deletetCnt = fwscrinService.deleteFwscrin( fwscrinVO );
			retMsg = "??????????????? (??????)?????????????????????.!";
		}

		redirectAttributes.addFlashAttribute("retMsg", retMsg);
		return "redirect:/fwscrin/listFwscrin.do";
	}


	@RequestMapping(value = "/fwscrin/goSaveFwscrin.do")
	public String goSaveFwscrin(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO , ModelMap model) throws Exception {

		if( "0".equals(fwscrinVO.getUbseq()) ){
			
			fwscrinVO = fwscrinService.getFwscrin( fwscrinVO );
//			fwscrinVO.setSearchCodeGroup(searchCodeGroup);
//			fwscrinVO.setSearchCodeName(searchCodeName);
		}

        model.addAttribute("fwscrinVO", fwscrinVO);
//        model.addAttribute("fwscrinGroupList", fwscrinGroupList);

        
		return "unibill_tiles/fwscrin/fwscrinSave";	
	}
	
	@RequestMapping(value = "/fwscrin/saveFwscrin.do")
	public String saveFwscrin(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

		String retMsg = "???????????? ??????????????????";

//		validator.validate(fwscrinVO, bindingResult); //validation ??????
		if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...
		    model.addAttribute("fwscrinVO", fwscrinVO);

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
			}
			
			model.addAttribute("retMsg", retMsg);
			return "unibill_tiles/fwscrin/fwscrinSave";
//			throw new BindException(bindingResult); // WEB-INF/config/framework/springmvc/com-servlet.xml : <prop key="org.springframework.validation.BindException">validationJsonView</prop>
//			return "validationJsonView";
		}
		
		
		int resultCnt = fwscrinService.saveFwscrin( fwscrinVO ); 
				
		if( 0 < resultCnt ){
			retMsg = "??????????????? ?????????????????????.";
		}else{
			retMsg = "????????? ????????? ????????????.";
		}

		Map<String, Object> returnResultMap = new HashMap<String,Object>();
		returnResultMap.put("retMsg", retMsg);
		redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);
	      
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		redirectAttributes.addFlashAttribute("searchCodeGroup", fwscrinVO.getSearchCodeGroup());
//		redirectAttributes.addFlashAttribute("searchCodeName", fwscrinVO.getSearchCodeName());
		return "redirect:/fwscrin/listFwscrin.do";
	}
	
	
	
   @RequestMapping(value = "/fwscrin/listFwscrinJqgrid.json")
   public @ResponseBody Map<String, Object> listFwscrinJqgridJson(@ModelAttribute FwscrinVO fwscrinVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

	   PaginationInfo paginationInfo = new PaginationInfo();
	   paginationInfo.setCurrentPageNo(fwscrinVO.getPageIndex());
	   paginationInfo.setRecordCountPerPage(fwscrinVO.getRecordCountPerPage());
	   paginationInfo.setPageSize(fwscrinVO.getPageSize());

	   fwscrinVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	   fwscrinVO.setLastIndex(paginationInfo.getLastRecordIndex());
	   
	   // mysql
	    int offsetSize = (fwscrinVO.getPageIndex() - 1) * fwscrinVO.getPageSize();
	    fwscrinVO.setOffsetSize(offsetSize);
	    fwscrinVO.setLimitSize(fwscrinVO.getPageSize());
	   

       // ????????????
       List<FwscrinVO> data = fwscrinService.listFwscrin( fwscrinVO );

       // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

       int totalCnt = fwscrinService.getFwscrinCnt(fwscrinVO);
       fwscrinVO.setTotalRecordCount(totalCnt);

       int totalPage = 1;
       if( fwscrinVO.getTotalRecordCount() > (fwscrinVO.getRecordCountPerPage() ) ){

           totalPage = fwscrinVO.getTotalRecordCount() / fwscrinVO.getRecordCountPerPage();
           int mod = fwscrinVO.getTotalRecordCount()%fwscrinVO.getRecordCountPerPage();
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
       returnMap.put("page", fwscrinVO.getPageIndex());

       returnMap.put("total", totalPage);
       returnMap.put("records", fwscrinVO.getRecordCountPerPage() );
       returnMap.put("totalRecords", fwscrinVO.getTotalRecordCount() );
       returnMap.put("rows", data);
       
       return returnMap;

   }
	
	
	@RequestMapping(value = "/fwscrin/saveFwscrin.json")
	public @ResponseBody Map<String, Object> saveFwscrinJson(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		String retCd = "FAILE";
		String retMsg = "???????????? ??????????????????";

		if (bindingResult.hasErrors()) { //?????? validation ????????? ?????????...

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
			}
		}else{
			
			int resultCnt = fwscrinService.saveFwscrin( fwscrinVO ); 
			
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
   @RequestMapping(value = "/fwscrin/deleteFwscrin.json")
   public @ResponseBody Map<String, Object> deletetFwscrinJson(@ModelAttribute("frmFwscrin") FwscrinVO fwscrinVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

       String retMsg = "????????? ????????? ????????????.";
       String retCd = "FAILE";

       Map<String , Object> returnMap = new HashMap<String , Object>();

       if ( "0".equals(fwscrinVO.getUbseq()) ) { //?????? validation ????????? ?????????...

       }else{

//			fwscrinVO.setDelCodeIdList( fwscrinVO.getDelCodeId().split(","));

           int deletetCnt = fwscrinService.deleteFwscrin( fwscrinVO );
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
//		return "redirect:/fwscrin/listFwscrin.do";


   }
}
