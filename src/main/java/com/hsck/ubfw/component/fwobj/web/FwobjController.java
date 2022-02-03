package com.hsck.ubfw.component.fwobj.web;

import com.hsck.ubfw.component.cmCfggrptype.service.CmCfggrptypeService;
import com.hsck.ubfw.component.cmCfggrptype.vo.CmCfggrptypeVO;
import com.hsck.ubfw.component.fwobj.service.FwobjService;
import com.hsck.ubfw.component.fwobj.vo.FwobjVO;
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
public class FwobjController {

   private static final Logger LOG = LoggerFactory.getLogger(FwobjController.class);

   @Resource(name = "fwobjService")
   private FwobjService fwobjService;

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
   
//	// /fwobj/listBasic.do
//	// /fwobj/Fwobj/listCode.do
   @RequestMapping(value = "/fwobj/listFwobj.do")
   public String listFwobj(@ModelAttribute("frmFwobj") FwobjVO fwobjVO, ModelMap model) throws Exception {
       
       LOG.debug("listFwobj : fwobjVO=" + fwobjVO.toString() );
       CmCfggrptypeVO cmCfggrptypeVO = new CmCfggrptypeVO();
       List<CmCfggrptypeVO> cmCfggrptypeList = cmCfggrptypeService.listCmCfggrptype(cmCfggrptypeVO);

       model.addAttribute("fwobjVO", fwobjVO);
       model.addAttribute("cmCfggrptypeList", cmCfggrptypeList);
       
       // View호출
       return "unibill_tiles/fwobj/fwobjList";
   }


    @RequestMapping(value = "/fwobj/listFwobjJqgrid.json")
    public @ResponseBody Map<String, Object> listFwobjJqgridJson(@ModelAttribute FwobjVO fwobjVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

//       fwobjVO.setPageSize(1000);
    	
    	// mysql
 	    int offsetSize = (fwobjVO.getPageIndex() - 1) * fwobjVO.getRecordCountPerPage();
 	    fwobjVO.setOffsetSize(offsetSize);
 	    fwobjVO.setLimitSize(fwobjVO.getRecordCountPerPage());


        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(fwobjVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwobjVO.getRecordCountPerPage());
        paginationInfo.setPageSize(fwobjVO.getPageSize());

        fwobjVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        fwobjVO.setLastIndex(paginationInfo.getLastRecordIndex());


        // 항목조회
        List<FwobjVO> data = fwobjService.listFwobj( fwobjVO );

        // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

        int totalCnt = fwobjService.getFwobjCnt(fwobjVO);
        fwobjVO.setTotalRecordCount(totalCnt);

        int totalPage = 1;
        if( fwobjVO.getTotalRecordCount() > (fwobjVO.getRecordCountPerPage() ) ){

            totalPage = fwobjVO.getTotalRecordCount() / fwobjVO.getRecordCountPerPage();
            int mod = fwobjVO.getTotalRecordCount()%fwobjVO.getRecordCountPerPage();
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
        returnMap.put("page", fwobjVO.getPageIndex());

        returnMap.put("total", totalPage);
        returnMap.put("records", fwobjVO.getRecordCountPerPage() );
        returnMap.put("totalRecords", fwobjVO.getTotalRecordCount() );
        returnMap.put("rows", data);

        return returnMap;

    }


    @RequestMapping(value = "/fwobj/listFwobjTableJqgrid.json")
    public @ResponseBody Map<String, Object> listFwobjTableJqgridJson(@ModelAttribute FwobjVO fwobjVO, @RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletResponse response) throws Exception {

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(fwobjVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(fwobjVO.getRecordCountPerPage());
        paginationInfo.setPageSize(fwobjVO.getPageSize());

        fwobjVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        fwobjVO.setLastIndex(paginationInfo.getLastRecordIndex());
        
        // mysql
 	    int offsetSize = (fwobjVO.getPageIndex() - 1) * fwobjVO.getRecordCountPerPage();
 	    fwobjVO.setOffsetSize(offsetSize);
 	    fwobjVO.setLimitSize(fwobjVO.getRecordCountPerPage());

        // 항목조회
        List<FwobjVO> data = fwobjService.listFwobjTable( fwobjVO );

        // {pageSize=10, pageIndex=1, searchPrototypeTitle=}
//       Integer records = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageSize"), "1000"));
//       Integer page = Integer.parseInt(StringUtils.defaultIfBlank((String) commandMap.get("pageIndex"), "1"));

        int totalCnt = fwobjService.getFwobjTableCnt(fwobjVO);
        fwobjVO.setTotalRecordCount(totalCnt);

        int totalPage = 1;
        if( fwobjVO.getTotalRecordCount() > (fwobjVO.getRecordCountPerPage() ) ){

            totalPage = fwobjVO.getTotalRecordCount() / fwobjVO.getRecordCountPerPage();
            int mod = fwobjVO.getTotalRecordCount()%fwobjVO.getRecordCountPerPage();
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
        returnMap.put("page", fwobjVO.getPageIndex());

        returnMap.put("total", totalPage);
        returnMap.put("records", fwobjVO.getRecordCountPerPage() );
        returnMap.put("totalRecords", fwobjVO.getTotalRecordCount() );
        returnMap.put("rows", data);

        return returnMap;

    }

//	@RequestMapping(value = "/fwobj/getFwobj.do")
//	public String getFwobj(@ModelAttribute("frmFwobj") FwobjVO fwobjVO,  ModelMap model , HttpServletRequest request) throws Exception {
//		fwobjVO = fwobjService.getFwobj( fwobjVO );
//
//        model.addAttribute("fwobjVO", fwobjVO);
//
//		// View호출
//		return "unibill_tiles/fwobj/fwobjRead";
//	}
//
//	@RequestMapping(value = "/fwobj/goInsertFwobj.do")
//	public String goInsertFwobj(ModelMap model) throws Exception {
//		return "unibill_tiles/fwobj/fwobjCret";
//	}
//	
//	@RequestMapping(value = "/fwobj/insertFwobj.do")
//	public String insertFwobj(@ModelAttribute("frmFwobj") @Valid FwobjVO fwobjVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//		
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwobjVO", fwobjVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//				
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwobj/fwobjCret";
//		}
//		
//	    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwobjToken")) {  
//	        String insertPK = fwobjService.insertFwobj(fwobjVO);
//	        retMsg = "정상적으로 (저장)처리되었습니다.!";
//	    }
//		
//		
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwobj/listFwobj.do";
//	}
//
//	@RequestMapping(value = "/fwobj/goUpdateFwobj.do")
//	public String goUpdateFwobj(@ModelAttribute("frmFwobj") @Valid FwobjVO fwobjVO, BindingResult bindingResult, ModelMap model) throws Exception {
//		fwobjVO = fwobjService.getFwobj( fwobjVO );
//
//        model.addAttribute("fwobjVO", fwobjVO);
//		return "unibill_tiles/fwobj/fwobjUpdt";
//	}
//
//	@RequestMapping(value = "/fwobj/updateFwobj.do")
//	public String updateFwobj(@ModelAttribute("frmFwobj") @Valid FwobjVO fwobjVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {
//
//		String retMsg = "입력값을 확인해주세요";
//
//		if (bindingResult.hasErrors()) {
//		    model.addAttribute("fwobjVO", fwobjVO);
//
//			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//			for( FieldError fieldError : fieldErrorList ){
//
//				retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
//			}
//			
//			model.addAttribute("retMsg", retMsg);
//			
//			return "unibill_tiles/fwobj/fwobjCret";
//		}
//		
//		if( StringUtils.isBlank( fwobjVO.getCodeId() ) ){
//			retMsg = "존재하지 않는 정보입니다.";
//		}else{
//		    if (EgovDoubleSubmitHelper.checkAndSaveToken("frmFwobjToken")) {  
//		    	
//		    	int updateCnt = fwobjService.updateFwobj( fwobjVO );
//		    	retMsg = "정상적으로 (수정)처리되었습니다.!";
//		    }
//		}
//
//		redirectAttributes.addFlashAttribute("retMsg", retMsg);
//		return "redirect:/fwobj/listFwobj.do";
//	}


   @RequestMapping(value = "/fwobj/goSaveFwobj.do")
   public String goSaveFwobj(@ModelAttribute("frmFwobj") FwobjVO fwobjVO , ModelMap model) throws Exception {

//		List<FwobjVO> fwobjGroupList = fwobjService.listFwobjGroup( new FwobjVO() );
//		
//        String searchCodeGroup = fwobjVO.getSearchCodeGroup();
//        String searchCodeName = fwobjVO.getSearchCodeName();
//
//		
//		if( StringUtils.isNoneBlank( fwobjVO.getCodeId() ) ){
//			
//			fwobjVO = fwobjService.getFwobj( fwobjVO );
//			fwobjVO.setSearchCodeGroup(searchCodeGroup);
//			fwobjVO.setSearchCodeName(searchCodeName);
//		}

       model.addAttribute("fwobjVO", fwobjVO);
//        model.addAttribute("fwobjGroupList", fwobjGroupList);

       
       return "unibill_tiles/fwobj/fwobjSave";
   }
   
   @RequestMapping(value = "/fwobj/saveFwobj.do")
   public String saveFwobj(@ModelAttribute("frmFwobj") FwobjVO fwobjVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status) throws Exception {

       String retMsg = "입력값을 확인해주세요";

//		validator.validate(fwobjVO, bindingResult); //validation 수행
       if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...
           model.addAttribute("fwobjVO", fwobjVO);

           List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
           for( FieldError fieldError : fieldErrorList ){

               retMsg = retMsg + "\\n" + fieldError.getDefaultMessage();
           }
           
           model.addAttribute("retMsg", retMsg);
           return "unibill_tiles/fwobj/fwobjSave";
       }
       
       
       int resultCnt = fwobjService.saveFwobj( fwobjVO ); 
               
       if( 0 < resultCnt ){
           retMsg = "정상적으로 처리되었습니다.";
       }else{
           retMsg = "처리된 정보가 없습니다.";
       }

       Map<String, Object> returnResultMap = new HashMap<String,Object>();
       returnResultMap.put("retMsg", retMsg);
       redirectAttributes.addFlashAttribute("returnResultMap" , returnResultMap);

       return "redirect:/fwobj/listFwobj.do";
   }
   
   
   
   @RequestMapping(value = "/fwobj/saveFwobj.json")
   public @ResponseBody Map<String, Object> saveFwobjJson(@ModelAttribute FwobjVO fwobjVO, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes, SessionStatus status, HttpServletResponse response) throws Exception {
       
       Map<String , Object> returnMap = new HashMap<String , Object>();
       String retCd = "FAILE";
       String retMsg = "입력값을 확인해주세요";

//       if (bindingResult.hasErrors()) { //만일 validation 에러가 있으면...
//
//           List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//           for( FieldError fieldError : fieldErrorList ){
//
//               retMsg = retMsg + "\n" + fieldError.getDefaultMessage();
//           }
//       }else{

           int resultCnt = fwobjService.saveFwobj( fwobjVO );

           if( 0 < resultCnt ){
               retCd = "SUCCESS";
               retMsg = "정상적으로 처리되었습니다.";
           }else if( 0 == resultCnt ){
               retMsg = "처리된 정보가 없습니다.";
           }
//       }

       returnMap.put("retCd", retCd);
       returnMap.put("retMsg", retMsg);
       return returnMap;
   }

    //
    @RequestMapping(value = "/fwobj/deleteFwobj.json")
    public @ResponseBody Map<String, Object> deletetFwobjJson(@ModelAttribute("frmFwobj") FwobjVO fwobjVO, ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

        String retMsg = "삭제할 정보가 없습니다.";
        String retCd = "FAILE";

        Map<String , Object> returnMap = new HashMap<String , Object>();

        if ( "0".equals(fwobjVO.getUbseq()) ) { //만일 validation 에러가 있으면...

        }else{

//			fwobjVO.setDelCodeIdList( fwobjVO.getDelCodeId().split(","));

            int deletetCnt = fwobjService.deleteFwobj( fwobjVO );
            retMsg = "정상적으로 (삭제)처리되었습니다.!";

            if( 0 < deletetCnt ){
                retCd = "SUCCESS";
                retMsg = "정상적으로 처리되었습니다.";
            }else{
                retMsg = "처리된 정보가 없습니다.";
            }

        }

        returnMap.put("retCd", retCd);
        returnMap.put("retMsg", retMsg);

        return returnMap;
//		return "redirect:/fwobj/listFwobj.do";


    }
}
