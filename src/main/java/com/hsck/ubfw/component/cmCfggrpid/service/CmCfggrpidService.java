package com.hsck.ubfw.component.cmCfggrpid.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hsck.ubfw.component.cmCfggrpid.vo.CmCfggrpidVO;

 /**
 * CM_CFGGRPID에 대한 Service Interface 입니다.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
public interface CmCfggrpidService {

     List<CmCfggrpidVO> listCmCfggrpidConnectBy(CmCfggrpidVO cmCfggrpidVO) throws Exception;

     /**
	 * CM_CFGGRPID 에서 Data를 여러건 조회하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * List<CmCfggrpidVO>
	 */
	List<CmCfggrpidVO> listCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;
	
	/**
	 * 목록은 조회와 동일한 검색 조건을 수행하였을 때의 총 data건수를 조회하는 SQL 을 호출한다.
	 * @param cmCfggrpidVO
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer getCmCfggrpidCnt(CmCfggrpidVO cmCfggrpidVO) throws Exception;
	
	/**
	 * CM_CFGGRPID 에서 Data를 한건 조회하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * CmCfggrpidVO
	 */
	CmCfggrpidVO getCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID 에서 Data를 추가하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * String
	 */
	String insertCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID 에서 Data를 수정하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * String
	 */
	int updateCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID 에서 Data를 삭제하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * int
	 */
	int deleteCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	/**
	 * CM_CFGGRPID 에서 PK값 여부로 Data를 추가 or 수정하는 로직을 수행한다.
	 * @param cmCfggrpidVO
	 * @return
	 * @throws Exception
	 * CmCfggrpidVO
	 */
	int saveCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception;

	 /**
	  * CM_CFGGRPID에서 정보를 Tree 구조에 맞게 자식 노드까지 삭제하는 로직을 수행한다.
	  * @param cmCfggrpidVO
	  * @return
	  * @throws Exception
	  */
     int deleteCmCfggrpidTree(CmCfggrpidVO cmCfggrpidVO) throws Exception;
 }
