package com.hsck.ubfw.component.cmCfggrpid.service.impl;

import java.util.List;

import com.hsck.ubfw.component.cmCfggrpid.vo.CmCfggrpidVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

 /**
 * CM_CFGGRPID에 대한 CRUD 쿼리를 마이바티스로 연결하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Mapper("cmCfggrpidMapper")
public interface CmCfggrpidMapper {

	 /**
	  * CM_CFGGRPID의 목록을 GrpType 값 여부만 첵크하여 조회하는 SQL 을 호출한다. ( Tree 기본 구조를 만들기위한 용도. )
	  * @param cmCfggrpidVO
	  * @return
	  * List<CmCfggrpidVO>
	  */
	 List<CmCfggrpidVO> listCmCfggrpidGrpType(CmCfggrpidVO cmCfggrpidVO) throws Exception;
	 /**
	  * CM_CFGGRPID의 목록을 조회하는 SQL 을 호출한다.
	  * @param cmCfggrpidVO
	  * @return
	  * List<CmCfggrpidVO>
	  */
	 List<CmCfggrpidVO> listCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;
	/**
	 * CM_CFGGRPID의 목록을 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getCmCfggrpidCnt(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID에서 단건 조회하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * CmCfggrpidVO
	 */
	CmCfggrpidVO getCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 *CM_CFGGRPID에  mergeInto 처리하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * CmCfggrpidVO
	 */
	int saveCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID에 정보를 단건 저장하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * String
	 */
	int insertCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID에 정보를 수정하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * String
	 */
	int updateCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID에서 정보를 삭제하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * int
	 */
	int deleteCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	 /**
	  * CM_CFGGRPID에서 정보를 ubseq 기준으로 여러건 삭제하는 SQL 을 호출한다.
	  * @param cmCfggrpidVO
	  * @return
	  * @throws Exception
	  */
	 int deleteListCmCfggrpid(CmCfggrpidVO cmCfggrpidVO);

	 /**
	  * CM_CFGGRPID에서 정보를 Tree 구조에 맞게 자식 노드까지 삭제하는 SQL 을 호출한다.
	  * @param cmCfggrpidVO
	  * @return
	  * @throws Exception
	  */
	int deleteCmCfggrpidTree(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	 /**
	  * Oracle 의 ConnectBy 구문을 사용하는 경우 처리.
	  * @param cmCfggrpidVO
	  * @return
	  */
     List<CmCfggrpidVO> listCmCfggrpidConnectBy(CmCfggrpidVO cmCfggrpidVO);
 }
