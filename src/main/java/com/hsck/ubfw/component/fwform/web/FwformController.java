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
package com.hsck.ubfw.component.fwform.web;

import com.hsck.ubfw.component.fwform.service.FwformService;
import com.hsck.ubfw.component.fwform.vo.FwformVO;
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
public class FwformController{

	private static final Logger LOG = LoggerFactory.getLogger(FwformController.class);

	@Resource(name = "fwformService")
	private FwformService fwformService;

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
	
//	// /fwform/listBasic.do
//	// /fwform/Fwform/listCode.do
	@RequestMapping(value = "/fwform/listFwform.do")
	public String listFwform(@ModelAttribute("frmFwform") FwformVO fwformVO, ModelMap model) throws Exception {
		
		LOG.debug("listFwform : fwformVO=" + fwformVO.toString() );
		
		
		// mysql
 	    int offsetSize = (fwformVO.getPageIndex() - 1) * fwformVO.getPageSize();
 	    fwformVO.setOffsetSize(offsetSize);
 	    fwformVO.setLimitSize(fwformVO.getPageSize());
 	   
		
		List<FwformVO> resultList = fwformService.listFwform( fwformVO );

		Integer pageSize = fwformVO.getPageSize();
		Integer page = fwformVO.getPageIndex();
		int totalCnt = fwformService.getFwformCnt(fwformVO);
		//int totalPage = (int) Math.ceil(totalCnt / pageSize);
		
		model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalCount", totalCnt);
        model.addAttribute("pageIndex", page);
		

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(fwformVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwformVO.getPageUnit());
        paginationInfo.setPageSize(fwformVO.getPageSize());
        paginationInfo.setTotalRecordCount(totalCnt);

        model.addAttribute("resultCnt", totalCnt );
        model.addAttribute("paginationInfo", paginationInfo);
        
        

        model.addAttribute("fwformVO", fwformVO);
        model.addAttribute("resultList", resultList);

		// View호출
		return "unibill_tiles/fwform/fwformList";
	}

//	@RequestMapping(value = "/fwform/getFwform.do")
//	public String getFwform(@ModelAttribute("frmFwform") FwformVO fwformVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		fwformVO = fwformService.getFwform( fwformVO );
//
//        model.addAttribute("fwformVO", fwformVO);
//
//		// View호출
//		return "unibill_tiles/fwform/fwformRead";
//	}
//
//	@RequestMapping(value = "/fwform/goInsertFwform.do")
//	public String goInsertFwform(ModelMap model) throws Exception {
//		return "unibill_tiles/fwform/fwformCret";	
//	}
//	
//	@RequestMapping(value = "/fwform/insertFwform.do")
//	public String insertFwform(@ModelAttribute("frmFwform") @Valid FwformVO fwformVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwformVO", fwformVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwform/fwformCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwformToken")) {  
//	        String insertPK = fwformService.insertFwform(fwformVO);
//	        retMsg = "정상적으로 (저장)처리되었습니다.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwform/listFwform.do";
//	}
//
//	@RequestMapping(value = "/fwform/goUpdateFwform.do")
//	public String goUpdateFwform(@ModelAttribute("frmFwform") @Valid FwformVO fwformVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		fwformVO = fwformService.getFwform( fwformVO );
//
//        model.addAttribute("fwformVO", fwformVO);
//		return "unibill_tiles/fwform/fwformUpdt";	
//	}
//
//	@RequestMapping(value = "/fwform/updateFwform.do")
//	public String updateFwform(@ModelAttribute("frmFwform") @Valid FwformVO fwformVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwformVO", fwformVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwform/fwformCret";
//		}
//		
//		if( StringUtils.isBlank( fwformVO.getCodeId() ) ){
//			retMsg = "존재하지 않는 정보입니다.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwformToken")) {  
//		    	
//		    	int updateCnt = fwformService.updateFwform( fwformVO );
//		    	retMsg = "정상적으로 (수정)처리되었습니다.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwform/listFwform.do";
//	}
//
	@RequestMapping(value = "/fwform/deleteFwform.do")
	public String deletetFwform(@ModelAttribute("frmFwform") FwformVO fwformVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

		String retMsg = "";

		
		if( StringUtils.isBlank( fwformVO.getDelUbseq() ) ){
			retMsg = "삭제할 정보가 없습니다.";
		}else{
			
			fwformVO.setDelUbseqList( fwformVO.getDelUbseq().split(","));
			
			int deletetCnt = fwformService.deleteFwform( fwformVO );
			retMsg = "정상적으로 (삭제)처리되었습니다.!";
		}

		redirectAttributes.addFlashAttribute("retMsg", retMsg);
		return "redirect:/fwform/listFwform.do";
	}


	@RequestMapping(value = "/fwform/goSaveFwform.do")
	public String goSaveFwform(@ModelAttribute("frmFwform") FwformVO fwformVO , ModelMap model) throws Exception {

//        String searchCodeGroup = fwformVO.getSearchCodeGroup();

		if( "0".equals(fwformVO.getUbseq()) ){
			
			fwformVO = fwformService.getFwform( fwformVO );
//			fwformVO.setSearchCodeGroup(searchCodeGroup);
		}

        model.addAttribute("fwformVO", fwformVO);

		return "unibill_tiles/fwform/fwformSave";	
	}
	
	@RequestMapping(value = "/fwform/saveFwform.do")
	public String saveFwform(@ModelAttribute("frmFwform") FwformVO fwformVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

		String retMsg = "입력값을 확인해주세요";

//		validator.validate(fwformVO, bindingResult); //validation 수행
		if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...
		    model.addAttribute("fwformVO", fwformVO);

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
			}
			
			model.addAttribute("retMsg", retMsg);
			return "unibill_tiles/fwform/fwformSave";
//			throw new BindException(bindingResult); // WEB-INF/config/framework/springmvc/com-servlet.xml : <prop key="org.springframework.validation.BindException">validationJsonView</prop>
//			return "validationJsonView";
		}
		
		
		int resultCnt = fwformService.saveFwform( fwformVO ); 
				
		if( 0 < resultCnt ){
			retMsg = "정상적으로 처리되었습니다.";
		}else{
			retMsg = "처리된 정보가 없습니다.";
		}

		Map<String, Object> returnResultMap = new HashMap<String,Object>();
		returnResultMap.put("retMsg", retMsg);
		redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);
	      
		return "redirect:/fwform/listFwform.do";
	}
	
	
	
   @RequestMapping(value = "/fwform/listFwformJqgrid.json")
   public @ResponseBody Map<String, Object> listFwformJqgridJson(@ModelAttribute FwformVO fwformVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

	   PaginationInfo paginationInfo = new PaginationInfo();
	   paginationInfo.setCurrentPageNo(fwformVO.getPageIndex());
	   paginationInfo.setRecordCountPerPage(fwformVO.getRecordCountPerPage());
	   paginationInfo.setPageSize(fwformVO.getPageSize());

	   fwformVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	   fwformVO.setLastIndex(paginationInfo.getLastRecordIndex());
	   
	   

	   // mysql
	   int offsetSize = (fwformVO.getPageIndex() - 1) * fwformVO.getRecordCountPerPage();
	   fwformVO.setOffsetSize(offsetSize);
	   fwformVO.setLimitSize(fwformVO.getRecordCountPerPage());
	   
	   
	    
	   // 항목조회
       List<FwformVO> data = fwformService.listFwform( fwformVO );

       // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

       int totalCnt = fwformService.getFwformCnt(fwformVO);
       fwformVO.setTotalRecordCount(totalCnt);

       int totalPage = 1;
       if( fwformVO.getTotalRecordCount() > (fwformVO.getRecordCountPerPage() ) ){

           totalPage = fwformVO.getTotalRecordCount() / fwformVO.getRecordCountPerPage();
           int mod = fwformVO.getTotalRecordCount()%fwformVO.getRecordCountPerPage();
           if( 0 < mod ){
               totalPage = totalPage + 1;
           }
       }

       Map<String , Object> returnMap = new HashMap<String , Object>();

       //조회

//		Object page; // 현재 페이지
//		Object total; // rows에 의한 총 페이지수
//		Object records; // 보여지는 데이터 개수
//		Object totalRecords; // 총 데이터 row 수
//		Object rows; // 컬럼별 data List (key:value)
       
       returnMap.put("retCd", "R");
       returnMap.put("retMsg", "");
       returnMap.put("page", fwformVO.getPageIndex());

       returnMap.put("total", totalPage);
       returnMap.put("records", fwformVO.getRecordCountPerPage() );
       returnMap.put("totalRecords", fwformVO.getTotalRecordCount() );
       returnMap.put("rows", data);
       
       return returnMap;

   }
	
	
	@RequestMapping(value = "/fwform/saveFwform.json")
	public @ResponseBody Map<String, Object> saveFwformJson(@ModelAttribute("frmFwform") FwformVO fwformVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
		
		Map<String , Object> returnMap = new HashMap<String , Object>();
		String retCd = "FAILE";
		String retMsg = "입력값을 확인해주세요";

		if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...

			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for( FieldError fieldError : fieldErrorList ){

				retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
			}
		}else{
			
			int resultCnt = fwformService.saveFwform( fwformVO ); 
			
			if( 0 < resultCnt ){
				retCd = "SUCCESS";
				retMsg = "정상적으로 처리되었습니다.";
			}else{
				retMsg = "처리된 정보가 없습니다.";
			}
			
		}

		returnMap.put("retCd", retCd);
		returnMap.put("retMsg", retMsg);

		return returnMap;
	}
	
	
	
	
	
	
//
   @RequestMapping(value = "/fwform/deleteFwform.json")
   public @ResponseBody Map<String, Object> deletetFwformJson(@ModelAttribute("frmFwform") FwformVO fwformVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {
	   String retMsg = "삭제할 정보가 없습니다.";
	   String retCd = "FAILE";

	   Map<String , Object> returnMap = new HashMap<String , Object>();

	   int deletetCnt = 0;
	   if( StringUtils.isNoneBlank( fwformVO.getDelUbseq() ) ){

		   fwformVO.setDelUbseqList( fwformVO.getDelUbseq().split(","));

		   deletetCnt = fwformService.deleteListFwform( fwformVO );
		   retMsg = "정상적으로 (삭제)처리되었습니다.!";
		   //}else if( StringUtils.isNoneBlank(fwformVO.getMenuId()) && StringUtils.isNoneBlank(fwformVO.getScrinId())) {
		   //    deletetCnt = fwformService.deleteFwform( fwformVO );
		   //    retMsg = "정상적으로 (삭제)처리되었습니다.!";
	   }else{
		   retMsg = "삭제할 정보가 없습니다.";
	   }

	   if( 0 < deletetCnt ){
		   retCd = "SUCCESS";
		   retMsg = "정상적으로 처리되었습니다.";
	   }else{
		   retMsg = "처리된 정보가 없습니다.";
	   }

	   returnMap.put("retCd", retCd);
	   returnMap.put("retMsg", retMsg);

	   return returnMap;
//		return "redirect:/fwform/listFwform.do";
   }
}
