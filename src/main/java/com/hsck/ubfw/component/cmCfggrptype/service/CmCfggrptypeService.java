package com.hsck.ubfw.component.cmCfggrptype.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hsck.ubfw.component.cmCfggrptype.vo.CmCfggrptypeVO;

 /**
 * CM_CFGGRPTYPE에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface CmCfggrptypeService {

	/**
	 * CM_CFGGRPTYPE 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * List<CmCfggrptypeVO>
	 */
	List<CmCfggrptypeVO> listCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getCmCfggrptypeCnt(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;
	
	/**
	 * CM_CFGGRPTYPE 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * CmCfggrptypeVO
	 */
	CmCfggrptypeVO getCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE 에서 Data를 추가하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * String
	 */
	String insertCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE 에서 Data를 수정하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * String
	 */
	int updateCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE 에서 Data를 삭제하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * int
	 */
	int deleteCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * @throws Exception
	 * CmCfggrptypeVO
	 */
	int saveCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

}
