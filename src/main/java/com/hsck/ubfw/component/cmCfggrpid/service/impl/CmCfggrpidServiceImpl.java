package com.hsck.ubfw.component.cmCfggrpid.service.impl;

import com.hsck.ubfw.component.cmCfggrpid.service.CmCfggrpidService;
import com.hsck.ubfw.component.cmCfggrpid.vo.CmCfggrpidVO;
import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.com.cmm.util.tree.TreeNode;
import com.hsck.ubfw.component.comm.vo.LoginInfo;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;
//import org.apache.commons.beanutils.BeanUtils;

 /**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor=Exception.class)
@Service("cmCfggrpidService")
public class CmCfggrpidServiceImpl extends EgovAbstractServiceImpl implements CmCfggrpidService {

	private static final Logger LOG = LoggerFactory.getLogger(CmCfggrpidServiceImpl.class);
	public static String specialCharacters = "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`）";

	/** ID Generation */
    //@Resource(name="cmCfggrpidIdGnrService")
    //private EgovIdGnrService cmCfggrpidIdGnrService;
        
    
    @Resource(name = "cmCfggrpidMapper")
    private CmCfggrpidMapper cmCfggrpidMapper;

	@Override
	public List<CmCfggrpidVO> listCmCfggrpidConnectBy(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("listCmCfggrpidConnectBy");
		List<CmCfggrpidVO> data = cmCfggrpidMapper.listCmCfggrpidConnectBy(cmCfggrpidVO);
		return data;
	}

	@Override
	public List<CmCfggrpidVO> listCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("listCmCfggrpid");
		List<CmCfggrpidVO> searchData = cmCfggrpidMapper.listCmCfggrpid(cmCfggrpidVO);

		LOG.debug("#### searchData.size() 확인 : " + searchData.size() );
		LOG.debug("#### 조회조건값 확인 : " + cmCfggrpidVO.toStringMy() );

		String findGrpId = StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpId() , specialCharacters,"");
		String findGrpNm = StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpNm() , specialCharacters,"");

		findGrpId = StringUtils.defaultIfBlank( findGrpId , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)
		findGrpNm = StringUtils.defaultIfBlank( findGrpNm , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)

		int checkCnt = 0;
		if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpType() ) ){
			++checkCnt;
		}
		if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpId() ) ){
			++checkCnt;
		}
		if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpNm() ) ){
			++checkCnt;
		}


		List<CmCfggrpidVO> data = new ArrayList<CmCfggrpidVO>();
		if( 0 == searchData.size() ){
			LOG.debug("#### 검색 결과가 없음!!"  );
			return data;
		}

		if( 0 < checkCnt ){

			CmCfggrpidVO rootObj = new CmCfggrpidVO();
			rootObj.setDepth("-1");
			rootObj.setTreeId("-"); // 실제로 jsp Tree 에서 사용할 키값.( CM_CFGGRPID , CM_CFGGRPTYPE Table을 조합해서 사용하는 구조. )
			rootObj.setTreeUpId(null);

			TreeNode<CmCfggrpidVO> treeRoot = new TreeNode<CmCfggrpidVO>(rootObj);


			// A. 	GrpType 이 있는 경우 최상위 값을 알 수 있으므로 SQL 로 직접 조회.
//			LOG.debug("### cmCfggrpidVO.getSearchGrpType()" + cmCfggrpidVO.getSearchGrpType() );

			ArrayList<CmCfggrpidVO> baseList = (ArrayList)cmCfggrpidMapper.listCmCfggrpidGrpType(cmCfggrpidVO); // depth 로 정렬된 상태로 가져와야한다.
//			LOG.debug("#### baseList size : " + baseList.size() );

			// B. 	GrpType 이 없는 경우 전체 조회한 후 loop 내에서 String Contain 으로 찾아낸 후 역으로 부모 노드 조회하는 방식으로 처리.
			ArrayList<CmCfggrpidVO> searchResult = new ArrayList<CmCfggrpidVO>();



			// 1. DB에서 조회한 Data로 Tree 구조 만들기
			Map<String,Object> objMap = getInitTreeObj(treeRoot , baseList , searchResult , cmCfggrpidVO );
			treeRoot = (TreeNode<CmCfggrpidVO>)objMap.get("treeRoot");
			searchResult = (ArrayList<CmCfggrpidVO>)objMap.get("searchResult");

//			LOG.debug("#### treeRoot.toStringMy() : " + treeRoot.toStringMy() );

			// 2. 검색조건에 맞는 Tree 의 node 와 그 node의 모든 직속 부모 node 의 Tree 의 키값 추출.
//			LOG.debug("#### String 검색 결과 확인 ==>> searchResult.size() : " + searchResult.size() + "    ==>>    listCmCfggrpid : getNodeFound");
			Map<String,List<String>> nodeFoundMap = getNodeFound( searchResult , treeRoot);
			List<String> searchResult3 = nodeFoundMap.get("deduplicationParentList");


//			LOG.debug("#### Tree 구조 확인 ==>> ");
			// 3. Tree 구조가 되도록 정렬(List<CmCfggrpidVO> data)하여 반환한다.
			Iterator iter = treeRoot.iterator();
			while( iter.hasNext() ){
				TreeNode<CmCfggrpidVO> node = (TreeNode<CmCfggrpidVO>) iter.next();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < node.getLevel(); i++) {
					sb.append("		");
				}
//			LOG.debug("#### node.isRoot():" + node.isRoot() + " ==>> " + sb.toString() + node.toString() );
				if( !node.isRoot() ){

					node.data.setGrpName( node.data.getGrpNm() );
					node.data.setIsLeaf( String.valueOf( node.isLeaf() ) );
					node.data.setMenuDepth( String.valueOf( node.getLevel() ));
					node.data.setLevel( String.valueOf( node.getLevel() ));

					if( null != searchResult3 && 0 < searchResult3.size() ){

						// 3-1. 검색된 키(TreeId) 값이 있는 경우의 처리.
						for(String findStr : searchResult3){ // 중복제거된 키값을만 data.add 한다.

							if( node.data.getTreeId().equals(findStr) ){
								data.add(node.data);

//								LOG.debug("#### node.isRoot() ==>> searchResult3 : " + node.isRoot() + "  :  " + sb.toString() + node.data.toString());
							}
						}
					}else{

						data.add(node.data);
//						LOG.debug("#### node.isRoot() : " + node.isRoot() + "  :  " + sb.toString() + node.data.toString());
					}
				}
			}

//			LOG.debug("#### data.size():" + data.size() );
		}


//		List<CmCfggrpidVO> data = cmCfggrpidMapper.listCmCfggrpid(cmCfggrpidVO);
		return data;
	}

	 @Override
	public Integer getCmCfggrpidCnt(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("getCmCfggrpidCnt");
//		return cmCfggrpidMapper.getCmCfggrpidCnt(cmCfggrpidVO);
		return 1000;
	}

	@Override
	public CmCfggrpidVO getCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("getCmCfggrpid");
		CmCfggrpidVO data = cmCfggrpidMapper.getCmCfggrpid(cmCfggrpidVO);
		return data;
	}


	@Override
	public int saveCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("saveCmCfggrpid");

		// PK Key 값 여부에따라 분기처리.( or MERGE INTO )
		if ( "0".equals(cmCfggrpidVO.getUbseq())){
			String pk = KeyUtil.getUbseq("CM_CFGGRPID", "ubseq");
			cmCfggrpidVO.setUbseq(pk);
		}

        LoginInfo loginInfo = new LoginInfo();
		loginInfo.putSessionToVo(cmCfggrpidVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrpidVO); // 객체 전체를 복사 하는 경우. cmCfggrpidVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다.
//        cmCfggrpidVO.setSessionUserId(loginVO.getSessionUserId());

		return cmCfggrpidMapper.saveCmCfggrpid(cmCfggrpidVO);
	}


	@Override
	public String insertCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("insertCmCfggrpid");
		String returnVal = "";
		String pk = KeyUtil.getUbseq("CM_CFGGRPID", "ubseq");
		cmCfggrpidVO.setUbseq(pk);

		if( StringUtils.isBlank(cmCfggrpidVO.getGrpId()) ){
			cmCfggrpidVO.setGrpId("GRP_ID_" + pk);
		}


        LoginInfo loginInfo = new LoginInfo();
		loginInfo.putSessionToVo(cmCfggrpidVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrpidVO); // 객체 전체를 복사 하는 경우. cmCfggrpidVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다.
//        cmCfggrpidVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = cmCfggrpidMapper.insertCmCfggrpid(cmCfggrpidVO);
		if( 0 < sqlResultInt ){
			returnVal = pk;
		}

		return returnVal;
	}


	@Override
	public int updateCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		LOG.debug("updateCmCfggrpid");
        LoginInfo loginInfo = new LoginInfo();
		loginInfo.putSessionToVo(cmCfggrpidVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,cmCfggrpidVO); // 객체 전체를 복사 하는 경우. cmCfggrpidVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다.
//        cmCfggrpidVO.setSessionUserId(loginVO.getSessionUserId());

		int sqlResultInt = cmCfggrpidMapper.updateCmCfggrpid(cmCfggrpidVO);
		return sqlResultInt;
	}


	 @Override
	 public int deleteCmCfggrpid(CmCfggrpidVO cmCfggrpidVO) throws Exception {
		 LOG.debug("deleteCmCfggrpid");
		 return cmCfggrpidMapper.deleteCmCfggrpid(cmCfggrpidVO);
	 }

//	 @Override
//	 public int deleteCmCfggrpidTree(CmCfggrpidVO cmCfggrpidVO) throws Exception {
//		 LOG.debug("deleteCmCfggrpidTree");
//
//		 return cmCfggrpidMapper.deleteCmCfggrpidTree(cmCfggrpidVO);
//	 }
	 @Override
	 public int deleteCmCfggrpidTree(CmCfggrpidVO cmCfggrpidVO) throws Exception {

		LOG.debug("deleteCmCfggrpidTree ==>> cmCfggrpidVO.toStringMy() : " + cmCfggrpidVO.toStringMy() );

		int returnInt = 0;

		 String findGrpType = StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpType() , specialCharacters,"");
		 String findGrpId = StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpId() , specialCharacters,"");
		 String findGrpNm = StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpNm() , specialCharacters,"");

		 if( StringUtils.isBlank( findGrpType ) && StringUtils.isNoneBlank( cmCfggrpidVO.getGrpType() )){
			 findGrpType = cmCfggrpidVO.getGrpType();
			 cmCfggrpidVO.setSearchGrpType( findGrpType );
		 }

		 if( StringUtils.isBlank( findGrpId ) && StringUtils.isNoneBlank( cmCfggrpidVO.getGrpId() )){
			 findGrpId = cmCfggrpidVO.getGrpId();
			 cmCfggrpidVO.setSearchGrpId( findGrpId );
		 }

		 if( StringUtils.isBlank( findGrpNm ) && StringUtils.isNoneBlank( cmCfggrpidVO.getGrpNm() )){
			 findGrpNm = cmCfggrpidVO.getGrpNm();
			 cmCfggrpidVO.setSearchGrpNm( findGrpNm );
		 }


		 List<CmCfggrpidVO> searchData = cmCfggrpidMapper.listCmCfggrpid(cmCfggrpidVO);

		 List<CmCfggrpidVO> data = new ArrayList<CmCfggrpidVO>();
		 if( 0 == searchData.size() ){
			 LOG.debug("#### 삭제 대상 항목이 없음!!"  );
			 return returnInt;
		 }


		 findGrpId = StringUtils.defaultIfBlank( findGrpId , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)
		 findGrpNm = StringUtils.defaultIfBlank( findGrpNm , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)

		 int checkCnt = 0;
		 if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpType() ) ){
			 ++checkCnt;
		 }
		 if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpId() ) ){
			 ++checkCnt;
		 }
		 if( StringUtils.isNoneBlank( cmCfggrpidVO.getSearchGrpNm() ) ){
			 ++checkCnt;
		 }

		 LOG.debug("#### deleteCmCfggrpidTree ==>> checkCnt : " + checkCnt );

		 if( 0 < checkCnt ) {

			 CmCfggrpidVO rootObj = new CmCfggrpidVO();
			 rootObj.setDepth("-1");
			 rootObj.setTreeId("-"); // 실제로 jsp Tree 에서 사용할 키값.( CM_CFGGRPID , CM_CFGGRPTYPE Table을 조합해서 사용하는 구조. )
			 rootObj.setTreeUpId(null);

			 TreeNode<CmCfggrpidVO> treeRoot = new TreeNode<CmCfggrpidVO>(rootObj);


			 // A. 	GrpType 이 있는 경우 최상위 값을 알 수 있으므로 SQL 로 직접 조회.
//			LOG.debug("### cmCfggrpidVO.getSearchGrpType()" + cmCfggrpidVO.getSearchGrpType() );

			 ArrayList<CmCfggrpidVO> baseList = (ArrayList) cmCfggrpidMapper.listCmCfggrpidGrpType(cmCfggrpidVO); // depth 로 정렬된 상태로 가져와야한다. ( Tree 구조를 만들기 위해서는 grpType 를 제외한 검색조건이 포함되어서는 안된다. )
//			LOG.debug("#### baseList size : " + baseList.size() );

			 // 1. DB에서 조회한 Data로 Tree 구조 만들기
			 Map<String, Object> objMap = getInitTreeObj(treeRoot, baseList, new ArrayList<CmCfggrpidVO>(), cmCfggrpidVO);
			 treeRoot = (TreeNode<CmCfggrpidVO>) objMap.get("treeRoot");
//			 searchResult = (ArrayList<CmCfggrpidVO>) objMap.get("searchResult");


			 // 2. 검색조건에 맞는 Tree 의 node 와 그 node의 모든 직속 부모 node 의 Tree 의 키값 추출.
			 // 2-1. Tree 를 만들기 위해 호출한 DB 조회 목록에서
			 cmCfggrpidVO.setSearchGrpType( cmCfggrpidVO.getGrpType() );
			 cmCfggrpidVO.setSearchGrpId( cmCfggrpidVO.getGrpId() );
			 LOG.debug("deleteCmCfggrpidTree ==>> 2-1. Tree 를 만들기 위해 호출한 DB 조회 목록에서 : cmCfggrpidVO.toStringMy() : " + cmCfggrpidVO.toStringMy() );
			 CmCfggrpidVO resultVO = (CmCfggrpidVO)cmCfggrpidMapper.getCmCfggrpid(cmCfggrpidVO); // cmCfggrpid Tree 화면에서 삭제는 단건 삭제만 있음.
			 LOG.debug("#### String 검색 결과 확인 ==>> resultVO.toStringMy() : " + resultVO.toStringMy() + "    ==>>    deleteCmCfggrpidTree : getNodeFound");
			 ArrayList<CmCfggrpidVO> searchResult = new ArrayList<CmCfggrpidVO>();
			 searchResult.add(resultVO);

			 // 2-2. 2-1에서 검색된 CmCfggrpidVO에 해당하는 node와 그 자식 node를 모두 찾는다.
			 Map<String, List<String>> nodeFoundMap = getNodeFound(searchResult, treeRoot);
			 List<String> searchResult3 = nodeFoundMap.get("deduplicationChildrenUbseqList");

			 if( null != searchResult3 && 0 < searchResult3.size() ){

			 	for(String s : searchResult3 ){
			 		LOG.debug("#### searchResult3 : " + s);
				}

				 cmCfggrpidVO.setDelUbseqList((String[])searchResult3.toArray( new String[searchResult3.size()] ) );
				 LOG.debug("#### 삭제 key list 확인 ==>> searchResult3.size() : " + searchResult3.size() );
				 LOG.debug("#### 삭제 key list 확인 ==>> cmCfggrpidVO.getDelUbseqList().length : " + cmCfggrpidVO.getDelUbseqList().length );
			 }

			 returnInt = cmCfggrpidMapper.deleteListCmCfggrpid(cmCfggrpidVO);
		 }

		 return returnInt;
	 }



	 /**
	  * 자식노드를 가지는 Tree 형태로 반환하며 검색 조건에대한 node의 key 값 리스트를 반환한다.
	  *
	  * Map<String,Object> objMap = new HashMap<String,Object>();
	  *     objMap.put("treeRoot",treeRoot);
	  *     objMap.put("searchResult",searchResult);
	  * @param treeRoot 초기화된 Tree 객체
	  * @param baseList DB에서 조회한 목록( Tree 의 node가 될 Data
	  * @param searchResult 검색조건에 해당하는 검색 결과 VO
	  * @param cmCfggrpidVO 검색조건이 포함된 VO
	  * @return objMap
	  */
	 private Map<String,Object> getInitTreeObj(TreeNode<CmCfggrpidVO> treeRoot, ArrayList<CmCfggrpidVO> baseList, ArrayList<CmCfggrpidVO> searchResult, CmCfggrpidVO cmCfggrpidVO) {

		 Map<String,Object> objMap = new HashMap<String,Object>();


		 String findGrpId = StringUtils.defaultIfBlank( StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpId() , specialCharacters,"") , "");
		 String findGrpNm = StringUtils.defaultIfBlank( StringUtils.replaceChars( cmCfggrpidVO.getSearchGrpNm() , specialCharacters,"") , "");

		 findGrpId = StringUtils.defaultIfBlank( findGrpId , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)
		 findGrpNm = StringUtils.defaultIfBlank( findGrpNm , "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)


		 for(final CmCfggrpidVO tVO : baseList){

			 String strGrpId = StringUtils.defaultIfBlank( StringUtils.replaceChars( tVO.getGrpId() , specialCharacters,"") , "");
			 String strGrpNm = StringUtils.defaultIfBlank( StringUtils.replaceChars( tVO.getGrpNm() , specialCharacters,"") , "");

//			 LOG.debug("#### getInitTreeObj ==>> findGrpId=" + findGrpId + " , strGrpId="+ strGrpId + " # strGrpNm=" + strGrpNm + " , findGrpNm=" + findGrpNm );


			 // B-1. String Contain 으로 찾아낸 Node를 저장.
			 if( strGrpId.contains( findGrpId ) ){
				 searchResult.add(tVO);
				 LOG.debug("#### GrpId : founded!! ==>> " + tVO.toStringMy() );
			 }else if( strGrpNm.contains( findGrpNm ) ){
				 searchResult.add(tVO);
				 LOG.debug("#### GrpNm : founded!! ==>> " + tVO.toStringMy() );
			 }


			 if( "0".equals( tVO.getDepth() ) ){
				 treeRoot.addChild(tVO);
			 }else {

				 Comparable<CmCfggrpidVO> searchCriteria = new Comparable<CmCfggrpidVO>() {
					 /**
					  *
					  * @param treeData : 부모(상위 레벨(depth)의 node
					  * @return
					  */
					 @Override
					 public int compareTo(CmCfggrpidVO treeData) {
						 if (treeData.getGrpType() == null) {
							 return 1;
						 }
//							LOG.debug("#### compareTo ==>>   treeData.toStringMy() : " + treeData.toStringMy() + "    /    tVO.toStringMy() : "  + tVO.toStringMy());
						 boolean nodeOk = treeData.initContains(tVO);
						 if( nodeOk ){
//							LOG.debug("#### compareTo ==>> nodeOk : " + nodeOk + "    |    treeData.toStringMy() : " + treeData.toStringMy() + "    /    tVO.toStringMy() : "  + tVO.toStringMy());
						 }
						 return nodeOk ? 0 : 1;
					 }
				 };

				 TreeNode<CmCfggrpidVO> foundNode = treeRoot.findTreeNode(searchCriteria);
				 if( null == foundNode ){

//						LOG.debug("#### null == foundNode ==>> searchCriteria.toString() : " + searchCriteria.toString() );
				 }else{

//						LOG.debug("#### foundNode.data.toString() : " + foundNode.data.toString() );
					 foundNode.addChild(tVO);
				 }
			 }
		 }


		 objMap.put("treeRoot",treeRoot);
		 objMap.put("searchResult",searchResult);

		 return objMap;
	 }

	 /**
	  * Tree 구조에서 검색 항목과 일치하는 node의 모든 직계 부모 노드와 모든 자식노드 정보를 반환한다. ( 중복 제거된 값을 반환한다. )
	  * @param searchResult 검색 항목 정보( Tree 구조를 만들때 검색 문자열에대한 VO정보를 수집하였음. )
	  * @param treeRoot Tree 구조
	  * @return "deduplicationParentList" , "deduplicationChildrenList" , "deduplicationChildrenUbseqList"
	  */
	 private Map<String,List<String>> getNodeFound( ArrayList<CmCfggrpidVO> searchResult , TreeNode<CmCfggrpidVO> treeRoot){

		 if( null != searchResult ){
		 	for( CmCfggrpidVO ccVO : searchResult ){
		 		LOG.debug( "#### searchResult ==>> " + ccVO.toStringMy() );
			}
		 }

		 Map<String,List<String>> foundNodeMap = new HashMap<String,List<String>>();

		 List<String> foundParentList = new ArrayList<String>(); // 찾아낸 node의 모든 부모 노드 key 값(중복 포함)을 저장한다.
		 List<String> foundChildrenList = new ArrayList<String>(); // 찾아낸 node의 모든 자식 노드 key 값(중복 포함)을 저장한다.
		 List<String> foundChildrenUbseqList = new ArrayList<String>(); // 찾아낸 node의 모든 자식 노드 UBSEQ 값(중복 포함)을 저장한다. ( 삭제에서 사용됨. )
		 int recursiveCnt = 4; // 재귀함수 호출 최대 회수

		 // 0. 검색된 VO 수 만큼 loop를 돌면서 Tree 구조에서 부모와 자식 노드 들을 찾아서 반한 한다.
		 for (final CmCfggrpidVO findVO : searchResult) {
			 LOG.debug("#### findVO.toStringMy() : " + findVO.toStringMy());

			 // 검색조건에 맞는 node 는 직속 부모 node든 직속 자식 node든 항상 포함되므로  findTreeNode 로 찾어서 넣는 것이 아니라 무조건 foundXXXList에 포함되어야함.
			 foundParentList.add(findVO.getTreeId());
			 foundChildrenList.add(findVO.getTreeId());
			 foundChildrenUbseqList.add(findVO.getUbseq()+"");

			 // 1. findVO의 직속 부모 node 찾기
			 Comparable<CmCfggrpidVO> searchParentCriteria = new Comparable<CmCfggrpidVO>() {
				 /**
				  * @param treeData : 부모(상위 레벨(depth)의 node
				  * @return
				  */
				 @Override
				 public int compareTo(CmCfggrpidVO treeData) {
					 if (null == treeData.getGrpType() || null == treeData.getTreeId()) {
						 return 1;
					 }
					 boolean nodeOk = treeData.initContains(findVO);
					 if (nodeOk) {
//						 LOG.debug("#### treeData.initContains(findVO) ==>> nodeOk : " + nodeOk + "    |    treeData.toStringMy() : " + treeData.toStringMy() + "    /    findVO.toStringMy() : " + findVO.toStringMy());
					 }
					 return nodeOk ? 0 : 1;
				 }
			 };


			// 1-1. 찾아진 노드의 모든 직속 부모 node의 키(TreeId) 값업 추출한다.( 중복된 키 값이 있을 수 있음. )
			 TreeNode<CmCfggrpidVO> foundParentNode = treeRoot.findTreeNode(searchParentCriteria);
			 if (null != foundParentNode) {

				 foundParentList.add(foundParentNode.data.getTreeId());

				 if (null != foundParentNode.parent) {

					 foundParentList = getParentNodeRecursiveFind( recursiveCnt , foundParentList , foundParentNode );
				 }
			 }







			 // 2. findVO의 직속 자식 node 찾기
			 Comparable<CmCfggrpidVO> searchChildrenCriteria = new Comparable<CmCfggrpidVO>() {
				 @Override
				 public int compareTo(CmCfggrpidVO treeData) {
//					 LOG.debug("#### searchResult compareTo ==>>   treeData.toStringMy() : " + treeData.toStringMy() + "    /    findVO.toStringMy() : "  + findVO.toStringMy());
					 if (null == treeData.getGrpType() || null == treeData.getTreeId()) {
						 return 1;
					 }
					 boolean nodeOk = treeData.initContainsChildren(findVO);
					 if (nodeOk) {
						 LOG.debug("#### treeData.initContainsChildren(findVO) ==>> nodeOk : " + nodeOk);
//						 LOG.debug("#### treeData.initContainsChildren(findVO) ==>> nodeOk : " + nodeOk + "    |    treeData.toStringMy() : " + treeData.toStringMy() + "    /    findVO.toStringMy() : " + findVO.toStringMy());
					 }
					 return nodeOk ? 0 : 1;
				 }
			 };


			 // 2-1. 찾아진 노드의 모든 직속 자식 node의 키(TreeId) 값업 추출한다.( 중복된 키 값이 있을 수 있음. )
			 List<TreeNode<CmCfggrpidVO>> foundChildrenNode = treeRoot.findTreeChaildNode(searchChildrenCriteria);
			 LOG.debug("#### foundChildrenNode ==>> findVO.getTreeId() : " + findVO.getTreeId());

			 if (null != foundChildrenNode) {
//				 LOG.debug("#### foundChildrenNode ==>> foundChildrenNode.size() = " + foundChildrenNode.size() );

			 	for(TreeNode<CmCfggrpidVO> treeCVO : foundChildrenNode ){
					LOG.debug("#### null != foundChildrenNode ==>> treeCVO.data.getTreeId() : " + treeCVO.data.getTreeId() );
					foundChildrenList.add(treeCVO.data.getTreeId());
					foundChildrenUbseqList.add(treeCVO.data.getUbseq()+"");
				}
			 }

		 } // for



		 // 2-2. 찾아진 노드의 모든 직속 부모 node의 키 값업 추출한 값에서 중복된 키 값을 제거한다.
		 List<String> deduplicationParentList = new ArrayList<String>(new HashSet<String>(foundParentList)); // // 찾아낸 node의 모든 부모 노드 key 값(중복 포함)에서 중복 제외된 key 값 추출.

		 // 2-2. 찾아진 노드의 모든 직속 자식 node의 키 값업 추출한 값에서 중복된 키 값을 제거한다.
		 List<String> deduplicationChildrenList = new ArrayList<String>(new HashSet<String>(foundChildrenList)); // // 찾아낸 node의 모든 자식 노드 key 값(중복 포함)에서 중복 제외된 key 값 추출.

		 // 2-3. 찾아진 노드의 모든 직속 자식 node의 UBSEQ 값업 추출한 값에서 중복된 키 값을 제거한다.
		 List<String> deduplicationChildrenUbseqList = new ArrayList<String>(new HashSet<String>(foundChildrenUbseqList)); // // 찾아낸 node의 모든 자식 노드 UBSEQ 값(중복 포함)에서 중복 제외된 UBSEQ 값 추출.

		 foundNodeMap.put("deduplicationParentList", deduplicationParentList);
		 foundNodeMap.put("deduplicationChildrenList", deduplicationChildrenList);
		 foundNodeMap.put("deduplicationChildrenUbseqList", deduplicationChildrenUbseqList);

		 return foundNodeMap;
	 }

	 /**
	  * 찾아낸 노드의 모든 부모 노드들의 키정보를 반환한다.
	  * @param recursiveCnt 재귀 횟수
	  * @param foundParentList 찾아낸 부모 노드 키 리스트
	  * @param foundNode 찾아낸 노드
	  * @return
	  */
	 private List<String> getParentNodeRecursiveFind(int recursiveCnt , List<String> foundParentList, TreeNode<CmCfggrpidVO> foundNode) {
		 int cnt = 0;
		 if( null != foundNode ){

			 TreeNode<CmCfggrpidVO> parentNode = foundNode.parent;

			 if (null != parentNode) {
				 foundParentList.add(parentNode.data.getTreeId());

				 if( cnt <= recursiveCnt ){
					 ++cnt;

					 if (null != parentNode.parent) {
						 LOG.debug( "#### getParentNodeRecursiveFind cnt : " + cnt );
						 getParentNodeRecursiveFind(recursiveCnt , foundParentList, parentNode);
					 }
				 }

			 }
		 }
		 return foundParentList;
	 }


	 /**
	  * 찾아낸 노드의 모든 자식 노드들의 키정보를 반환한다.
	  * @param recursiveCnt 재귀 횟수
	  * @param foundChildrenList 찾아낸 자식 노드 키 리스트
	  * @param foundNode 찾아낸 노드
	  * @return
	  */
	 private List<String> getChildNodeRecursiveFind(int recursiveCnt , List<String> foundChildrenList, TreeNode<CmCfggrpidVO> foundNode) {
		 int cnt = 0;
		 LOG.debug( "#### getChildNodeRecursiveFind === foundChildrenList.toString() :" + foundChildrenList.toString() );

		 if( null != foundNode && null != foundNode.children && 0 < foundNode.children.size() ){

			 List<TreeNode<CmCfggrpidVO>> childNodeList1 = foundNode.children;
			 for(TreeNode<CmCfggrpidVO> treeNode : childNodeList1 ){

				 LOG.debug( "#### getChildNodeRecursiveFind  cnt : " + cnt + "    /    treeNode.data.getTreeId() : " + treeNode.data.getTreeId() );

				 foundChildrenList.add(treeNode.data.getTreeId());

				 if( cnt <= recursiveCnt ){
					 ++cnt;
					 LOG.debug( "#### getChildNodeRecursiveFind cnt : " + cnt + "    /    treeNode.isLeaf() : " + treeNode.isLeaf() + "     /    foundChildrenList.toString() : " + foundChildrenList.toString() );
					 LOG.debug( "#### getChildNodeRecursiveFind treeNode.toString() : " + treeNode.toString());
					 LOG.debug( "#### getChildNodeRecursiveFind treeNode.children.size() : " + treeNode.children.size() + "    /    treeNode.children.toString() : " + treeNode.children.toString());
					 if (null != treeNode.children && 0 < treeNode.children.size() ) {
						 LOG.debug( "#### getChildNodeRecursiveFind treeNode.getLevel() : " + treeNode.getLevel());
						 foundChildrenList = getChildNodeRecursiveFind( recursiveCnt , foundChildrenList, treeNode );
					 }
				 }
			 }
		 }
		 return foundChildrenList;
	 }
	 /**
	  * 찾아낸 노드의 모든 자식 노드들의 UBSEQ 컬럼 정보를 반환한다. ( 삭제에 사용됨. )
	  * @param recursiveCnt 재귀 횟수
	  * @param foundChildrenUbseqList 찾아낸 자식 노드 UBSEQ 리스트
	  * @param foundNode 찾아낸 노드
	  * @return
	  */
	 private List<String> getChildNodeRecursiveFindUbseq(int recursiveCnt , List<String> foundChildrenUbseqList, TreeNode<CmCfggrpidVO> foundNode) {
		 int cnt = 0;
		 LOG.debug( "#### getChildNodeRecursiveFindUbseq === foundChildrenUbseqList.toString() :" + foundChildrenUbseqList.toString() );

		 if( null != foundNode && null != foundNode.children && 0 < foundNode.children.size() ){

			 List<TreeNode<CmCfggrpidVO>> childNodeList1 = foundNode.children;
			 for(TreeNode<CmCfggrpidVO> treeNode : childNodeList1 ){

				 LOG.debug( "#### getChildNodeRecursiveFindUbseq  cnt : " + cnt + "    /    treeNode.data.getTreeId() : " + treeNode.data.getTreeId() );

				 foundChildrenUbseqList.add(treeNode.data.getUbseq() + "");

				 if( cnt <= recursiveCnt ){
					 ++cnt;
					 LOG.debug( "#### getChildNodeRecursiveFindUbseq cnt : " + cnt + "    /    treeNode.isLeaf() : " + treeNode.isLeaf() + "     /    foundChildrenUbseqList.toString() : " + foundChildrenUbseqList.toString() );
					 LOG.debug( "#### getChildNodeRecursiveFindUbseq treeNode.toString() : " + treeNode.toString());
					 LOG.debug( "#### getChildNodeRecursiveFindUbseq treeNode.children.size() : " + treeNode.children.size() + "    /    treeNode.children.toString() : " + treeNode.children.toString());
					 if (null != treeNode.children && 0 < treeNode.children.size() ) {
						 LOG.debug( "#### getChildNodeRecursiveFindUbseq treeNode.getLevel() : " + treeNode.getLevel());
						 foundChildrenUbseqList = getChildNodeRecursiveFindUbseq( recursiveCnt , foundChildrenUbseqList, treeNode );
					 }
				 }
			 }
		 }
		 return foundChildrenUbseqList;
	 }


 }
