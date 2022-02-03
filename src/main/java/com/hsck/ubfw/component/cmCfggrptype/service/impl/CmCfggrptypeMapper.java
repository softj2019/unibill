package com.hsck.ubfw.component.cmCfggrptype.service.impl;

import java.util.List;

import com.hsck.ubfw.component.cmCfggrptype.vo.CmCfggrptypeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * CM_CFGGRPTYPE에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("cmCfggrptypeMapper")
public interface CmCfggrptypeMapper {

	/**
	 * CM_CFGGRPTYPE의 목록을 조회하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * List<CmCfggrptypeVO>
	 */
	List<CmCfggrptypeVO> listCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;
	/**
	 * CM_CFGGRPTYPE의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getCmCfggrptypeCnt(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE에서 단건 조회하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * CmCfggrptypeVO
	 */
	CmCfggrptypeVO getCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 *CM_CFGGRPTYPE에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * CmCfggrptypeVO
	 */
	int saveCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * String
	 */
	int insertCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE에 정보를 수정하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * String
	 */
	int updateCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

	/**
	 * CM_CFGGRPTYPE에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param cmCfggrptypeVO
	 * @return
	 * int
	 */
	int deleteCmCfggrptype(CmCfggrptypeVO cmCfggrptypeVO) throws Exception;

}
