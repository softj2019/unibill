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
package com.hsck.ubfw.component.fwmenuscrin.web;

import com.hsck.ubfw.component.com.cmm.util.StringUtil;
import com.hsck.ubfw.component.fwmenuscrin.service.FwmenuscrinService;
import com.hsck.ubfw.component.fwmenuscrin.vo.FwmenuscrinVO;
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
public class FwmenuscrinController{

    private static final Logger LOG = LoggerFactory.getLogger(FwmenuscrinController.class);

    @Resource(name = "fwmenuscrinService")
    private FwmenuscrinService fwmenuscrinService;

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

    //	// /fwmenuscrin/listBasic.do
//	// /fwmenuscrin/Fwmenuscrin/listCode.do
    @RequestMapping(value = "/fwmenuscrin/listFwmenuscrin.do")
    public String listFwmenuscrin(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO, ModelMap model) throws Exception {

        LOG.debug("listFwmenuscrin : fwmenuscrinVO=" + fwmenuscrinVO.toString().replaceAll("[\r\n]",""));
        
        // mysql
	    int offsetSize = (fwmenuscrinVO.getPageIndex() - 1) * fwmenuscrinVO.getPageSize();
	    fwmenuscrinVO.setOffsetSize(offsetSize);
	    fwmenuscrinVO.setLimitSize(fwmenuscrinVO.getPageSize());

        List<FwmenuscrinVO> resultList = fwmenuscrinService.listFwmenuscrin( fwmenuscrinVO );


        // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
        Integer pageSize = fwmenuscrinVO.getPageSize();
        Integer page = fwmenuscrinVO.getPageIndex();
        int totalCnt = fwmenuscrinService.getFwmenuscrinCnt(fwmenuscrinVO);
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

        paginationInfo.setCurrentPageNo(fwmenuscrinVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwmenuscrinVO.getPageUnit());
        paginationInfo.setPageSize(fwmenuscrinVO.getPageSize());
        paginationInfo.setTotalRecordCount(totalCnt);

        model.addAttribute("resultCnt", totalCnt );
        model.addAttribute("paginationInfo", paginationInfo);



        model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
        model.addAttribute("resultList", resultList);

        // View호출
        return "unibill_tiles/fwmenuscrin/fwmenuscrinList";
    }

    //	@RequestMapping(value = "/fwmenuscrin/getFwmenuscrin.do")
//	public String getFwmenuscrin(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		fwmenuscrinVO = fwmenuscrinService.getFwmenuscrin( fwmenuscrinVO );
//
//        model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
//
//		// View호출
//		return "unibill_tiles/fwmenuscrin/fwmenuscrinRead";
//	}
//
//	@RequestMapping(value = "/fwmenuscrin/goInsertFwmenuscrin.do")
//	public String goInsertFwmenuscrin(ModelMap model) throws Exception {
//		return "unibill_tiles/fwmenuscrin/fwmenuscrinCret";	
//	}
//	
//	@RequestMapping(value = "/fwmenuscrin/insertFwmenuscrin.do")
//	public String insertFwmenuscrin(@ModelAttribute("frmFwmenuscrin") @Valid FwmenuscrinVO fwmenuscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwmenuscrin/fwmenuscrinCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwmenuscrinToken")) {  
//	        String insertPK = fwmenuscrinService.insertFwmenuscrin(fwmenuscrinVO);
//	        retMsg = "정상적으로 (저장)처리되었습니다.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwmenuscrin/listFwmenuscrin.do";
//	}
//
//	@RequestMapping(value = "/fwmenuscrin/goUpdateFwmenuscrin.do")
//	public String goUpdateFwmenuscrin(@ModelAttribute("frmFwmenuscrin") @Valid FwmenuscrinVO fwmenuscrinVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		fwmenuscrinVO = fwmenuscrinService.getFwmenuscrin( fwmenuscrinVO );
//
//        model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
//		return "unibill_tiles/fwmenuscrin/fwmenuscrinUpdt";	
//	}
//
//	@RequestMapping(value = "/fwmenuscrin/updateFwmenuscrin.do")
//	public String updateFwmenuscrin(@ModelAttribute("frmFwmenuscrin") @Valid FwmenuscrinVO fwmenuscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwmenuscrin/fwmenuscrinCret";
//		}
//		
//		if( StringUtils.isBlank( fwmenuscrinVO.getCodeId() ) ){
//			retMsg = "존재하지 않는 정보입니다.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwmenuscrinToken")) {  
//		    	
//		    	int updateCnt = fwmenuscrinService.updateFwmenuscrin( fwmenuscrinVO );
//		    	retMsg = "정상적으로 (수정)처리되었습니다.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwmenuscrin/listFwmenuscrin.do";
//	}
//
    @RequestMapping(value = "/fwmenuscrin/deleteFwmenuscrin.do")
    public String deletetFwmenuscrin(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

        String retMsg = "";


        if( StringUtils.isBlank( fwmenuscrinVO.getDelUbseq() ) ){
            retMsg = "삭제할 정보가 없습니다.";
        }else{

            fwmenuscrinVO.setDelUbseqList( fwmenuscrinVO.getDelUbseq().split(","));

            int deletetCnt = fwmenuscrinService.deleteFwmenuscrin( fwmenuscrinVO );
            retMsg = "정상적으로 (삭제)처리되었습니다.!";
        }

        redirectAttributes.addFlashAttribute("retMsg", retMsg);
        return "redirect:/fwmenuscrin/listFwmenuscrin.do";
    }


    @RequestMapping(value = "/fwmenuscrin/goSaveFwmenuscrin.do")
    public String goSaveFwmenuscrin(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO , ModelMap model) throws Exception {

//        String searchCodeGroup = fwmenuscrinVO.getSearchCodeGroup();
//        String searchCodeName = fwmenuscrinVO.getSearchCodeName();


        if( "0".equals(fwmenuscrinVO.getUbseq()) ){

            fwmenuscrinVO = fwmenuscrinService.getFwmenuscrin( fwmenuscrinVO );
//			fwmenuscrinVO.setSearchCodeGroup(searchCodeGroup);
//			fwmenuscrinVO.setSearchCodeName(searchCodeName);
        }

        model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);
//        model.addAttribute("fwmenuscrinGroupList", fwmenuscrinGroupList);


        return "unibill_tiles/fwmenuscrin/fwmenuscrinSave";
    }

    @RequestMapping(value = "/fwmenuscrin/saveFwmenuscrin.do")
    public String saveFwmenuscrin(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

        String retMsg = "입력값을 확인해주세요";

//		validator.validate(fwmenuscrinVO, bindingResult); //validation 수행
        if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...
            model.addAttribute("fwmenuscrinVO", fwmenuscrinVO);

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for( FieldError fieldError : fieldErrorList ){

                retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
            }

            model.addAttribute("retMsg", retMsg);
            return "unibill_tiles/fwmenuscrin/fwmenuscrinSave";
//			throw new BindException(bindingResult); // WEB-INF/config/framework/springmvc/com-servlet.xml : <prop key="org.springframework.validation.BindException">validationJsonView</prop>
//			return "validationJsonView";
        }


        int resultCnt = fwmenuscrinService.saveFwmenuscrin( fwmenuscrinVO );

        if( 0 < resultCnt ){
            retMsg = "정상적으로 처리되었습니다.";
        }else{
            retMsg = "처리된 정보가 없습니다.";
        }

        Map<String, Object> returnResultMap = new HashMap<String,Object>();
        returnResultMap.put("retMsg", retMsg);
        redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);

//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		redirectAttributes.addFlashAttribute("searchCodeGroup", fwmenuscrinVO.getSearchCodeGroup());
//		redirectAttributes.addFlashAttribute("searchCodeName", fwmenuscrinVO.getSearchCodeName());
        return "redirect:/fwmenuscrin/listFwmenuscrin.do";
    }



    @RequestMapping(value = "/fwmenuscrin/listFwmenuscrinJqgrid.json")
    public @ResponseBody Map<String, Object> listFwmenuscrinJqgridJson(@ModelAttribute FwmenuscrinVO fwmenuscrinVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(fwmenuscrinVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwmenuscrinVO.getRecordCountPerPage());
        paginationInfo.setPageSize(fwmenuscrinVO.getPageSize());

        fwmenuscrinVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        fwmenuscrinVO.setLastIndex(paginationInfo.getLastRecordIndex());
        
        // mysql
 	    int offsetSize = (fwmenuscrinVO.getPageIndex() - 1) * fwmenuscrinVO.getRecordCountPerPage();
 	    fwmenuscrinVO.setOffsetSize(offsetSize);
 	    fwmenuscrinVO.setLimitSize(fwmenuscrinVO.getRecordCountPerPage());

        // 항목조회
        List<FwmenuscrinVO> data = fwmenuscrinService.listFwmenuscrin( fwmenuscrinVO );

        // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

        int totalCnt = fwmenuscrinService.getFwmenuscrinCnt(fwmenuscrinVO);
        fwmenuscrinVO.setTotalRecordCount(totalCnt);

        int totalPage = 1;
        if( fwmenuscrinVO.getTotalRecordCount() > (fwmenuscrinVO.getRecordCountPerPage() ) ){

            totalPage = fwmenuscrinVO.getTotalRecordCount() / fwmenuscrinVO.getRecordCountPerPage();
            int mod = fwmenuscrinVO.getTotalRecordCount()%fwmenuscrinVO.getRecordCountPerPage();
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
        returnMap.put("page", fwmenuscrinVO.getPageIndex());

        returnMap.put("total", totalPage);
        returnMap.put("records", fwmenuscrinVO.getRecordCountPerPage() );
        returnMap.put("totalRecords", fwmenuscrinVO.getTotalRecordCount() );
        returnMap.put("rows", data);

        return returnMap;

    }


    @RequestMapping(value = "/fwmenuscrin/saveFwmenuscrin.json")
    public @ResponseBody Map<String, Object> saveFwmenuscrinJson(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

        Map<String , Object> returnMap = new HashMap<String , Object>();
        String retCd = "FAILE";
        String retMsg = "입력값을 확인해주세요";

        if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for( FieldError fieldError : fieldErrorList ){

                retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
            }
        }else{

            int resultCnt = fwmenuscrinService.saveFwmenuscrin( fwmenuscrinVO );

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
    @RequestMapping(value = "/fwmenuscrin/deleteFwmenuscrin.json")
    public @ResponseBody Map<String, Object> deletetFwmenuscrinJson(@ModelAttribute("frmFwmenuscrin") FwmenuscrinVO fwmenuscrinVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

        String retMsg = "삭제할 정보가 없습니다.";
        String retCd = "FAILE";

        Map<String , Object> returnMap = new HashMap<String , Object>();

        int deletetCnt = 0;
        if( StringUtils.isNoneBlank( fwmenuscrinVO.getDelUbseq() ) ){
            deletetCnt = fwmenuscrinService.deleteListFwmenuscrin( fwmenuscrinVO );
            retMsg = "정상적으로 (삭제)처리되었습니다.!";
        }else if( StringUtils.isNoneBlank(fwmenuscrinVO.getMenuId()) && StringUtils.isNoneBlank(fwmenuscrinVO.getScrinId())) {
            deletetCnt = fwmenuscrinService.deleteFwmenuscrin( fwmenuscrinVO );
            retMsg = "정상적으로 (삭제)처리되었습니다.!";
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
//		return "redirect:/fwmenuscrin/listFwmenuscrin.do";


    }
}
