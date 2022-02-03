
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
package com.hsck.ubfw.component.fwmenu.service.impl;

import com.hsck.ubfw.component.com.cmm.util.KeyUtil;
import com.hsck.ubfw.component.com.cmm.util.tree.TreeNode;
import com.hsck.ubfw.component.fwmenu.service.FwmenuService;
import com.hsck.ubfw.component.fwmenu.vo.FwmenuVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.beanutils.BeanUtils;

/**
 * Service Implements 클레스 : 비지니스 로직을 구현하는 클레스.
 *
 * @author AA
 * @since 0000.00.00.
 */
@Transactional(rollbackFor = Exception.class)
@Service("fwmenuService")
public class FwmenuServiceImpl extends EgovAbstractServiceImpl implements FwmenuService {

    private static final Logger LOG = LoggerFactory.getLogger(FwmenuServiceImpl.class);

//    src/main/resources/framework/spring/context-idgen.xml 에 bean 을 선언해주세요


    /**
     * ID Generation
     */
//    @Resource(name = "fwmenuIdGnrService")
 //   private EgovIdGnrService fwmenuIdGnrService;


    @Resource(name = "fwmenuMapper")
    private FwmenuMapper fwmenuMapper;

    @Override
    public List<FwmenuVO> listFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("listFwmenu");


        String findMenuNm = StringUtils.defaultIfBlank(fwmenuVO.getSearchMenuNm(), "@#@#@#@^"); // 빈값이 들어오면 임의의 값으로 대체하여 검색(contain 으로 문자열 비교.)

        FwmenuVO rootObj = new FwmenuVO();
        rootObj.setDepth("-1");
        rootObj.setMenuId("M00");
        rootObj.setUpMenuId(null);

        TreeNode<FwmenuVO> treeRoot = new TreeNode<FwmenuVO>(rootObj);

        // 검색조건 확인
        LOG.debug("### fwmenuVO.toString()" + fwmenuVO.toString());
        LOG.debug("### fwmenuVO.getFirstIndex()" + fwmenuVO.getFirstIndex());
        LOG.debug("### fwmenuVO.getLastIndex()" + fwmenuVO.getLastIndex());


        ArrayList<FwmenuVO> baseList = (ArrayList) fwmenuMapper.listFwmenu(fwmenuVO); // depth 로 정렬된 상태로 가져와야한다.
        LOG.debug("#### baseList size : " + baseList.size());

        // 전체 조회한 후 loop 내에서 String Contain 으로 찾아낸 후 역으로 부모 노드 조회하는 방식으로 처리.
        ArrayList<FwmenuVO> searchResult = new ArrayList<FwmenuVO>();

        // 1. DB에서 조회한 Data로 완전한 Tree 구조 만들기
        for (final FwmenuVO tVO : baseList) {

            if (tVO.getMenuNm().contains(findMenuNm)) {
                searchResult.add(tVO);
                LOG.debug("#### MenuNm : founded!! ==>> " + tVO.toString());
            }


            if ("0".equals(tVO.getDepth())) {
                treeRoot.addChild(tVO);
//			}else if( "1".equals( fVO.getDepth() ) ){
            } else {

                Comparable<FwmenuVO> searchCriteria = new Comparable<FwmenuVO>() {
                    /**
                     *
                     * @param treeData : 부모(상위 레벨(depth)의 node
                     * @return
                     */
                    @Override
                    public int compareTo(FwmenuVO treeData) {
//						LOG.debug("#### compareTo ==>> treeData.toString() : " + treeData.toString() + "    /    fVO.toString() : "  + fVO.toString());
                        if (treeData.getMenuId() == null) {
                            return 1;
                        }
                        boolean nodeOk = treeData.initContains(tVO);
                        if (nodeOk) {
//                            LOG.debug("#### compareTo ==>> nodeOk : " + nodeOk + "    |    treeData.toString() : " + treeData.toString() + "    /    fVO.toString() : " + tVO.toString());
                        }
                        return nodeOk ? 0 : 1;
                    }
                };

                TreeNode<FwmenuVO> foundNode = treeRoot.findTreeNode(searchCriteria);
                if (null == foundNode) {

//                    LOG.debug("#### null == foundNode ==>> searchCriteria.toString() : " + searchCriteria.toString());
                } else {

//                    LOG.debug("#### foundNode.data.toString() : " + foundNode.data.toString());
                    foundNode.addChild(tVO);
                }
            }
        }


        // 2. 검색조건에 맞는 Tree 의 node 와 그 node의 모든 직속 부모 node 의 Tree 의 키값 추출.
        LOG.debug("#### String 검색 결과 확인 ==>> searchResult.size() : " + searchResult.size());
        List<String> searchResult2 = new ArrayList<String>(); // 찾아낸 node의 모든 부모 노드 key 값(중복 포함)을 저장한다.
        for (final FwmenuVO findVO : searchResult) {
//            LOG.debug("#### findVO.toString() : " + findVO.toString());

            Comparable<FwmenuVO> searchCriteria2 = new Comparable<FwmenuVO>() {
                /**
                 *
                 * @param treeData : 부모(상위 레벨(depth)의 node
                 * @return
                 */
                @Override
                public int compareTo(FwmenuVO treeData) {
                    if (treeData.getMenuId() == null) {
                        return 1;
                    }
//							LOG.debug("#### searchResult compareTo ==>>   treeData.toString() : " + treeData.toString() + "    /    findVO.toString() : "  + findVO.toString());
                    boolean nodeOk = treeData.initContains(findVO);
                    if (nodeOk) {
//                        LOG.debug("#### searchResult compareTo ==>> nodeOk : " + nodeOk + "    |    treeData.toString() : " + treeData.toString() + "    /    findVO.toString() : " + findVO.toString());
                    }
                    return nodeOk ? 0 : 1;
                }
            };


            // 2-1. 찾아진 노드의 모든 직속 부모 node의 키(MenuId) 값업 추출한다.( 중복된 키 값이 있을 수 있음. ) : 최대 상위 10단계까지 조회할 수 있음.
            TreeNode<FwmenuVO> foundNode2 = treeRoot.findTreeNode(searchCriteria2);
            if (null != foundNode2) {

                searchResult2.add(findVO.getMenuId());
                LOG.debug("#### foundNode2 - 1: " + foundNode2.toString());

                searchResult2.add(foundNode2.data.getMenuId());
                LOG.debug("#### foundNode2 - 2: " + foundNode2.data.toString());

                if(null != foundNode2.parent ){
                    searchResult2.add(foundNode2.parent.data.getMenuId());
                    LOG.debug("#### foundNode2.parent.data Path - 3: " + foundNode2.parent.data.toString());

                    if (null != foundNode2.parent.parent) {
                        searchResult2.add(foundNode2.parent.parent.data.getMenuId());
                        LOG.debug("#### foundNode2.parent.parent.data Path - 4: " + foundNode2.parent.parent.data.toString());

                        if (null != foundNode2.parent.parent.parent) {
                            searchResult2.add(foundNode2.parent.parent.parent.data.getMenuId());
                            LOG.debug("#### foundNode2.parent.parent.parent.data Path - 5: " + foundNode2.parent.parent.parent.data.toString());

                            if (null != foundNode2.parent.parent.parent.parent) {
                                searchResult2.add(foundNode2.parent.parent.parent.parent.data.getMenuId());
                                LOG.debug("#### foundNode2.parent.parent.parent.parent.data Path - 6: " + foundNode2.parent.parent.parent.parent.data.toString());

                                if (null != foundNode2.parent.parent.parent.parent.parent) {
                                    searchResult2.add(foundNode2.parent.parent.parent.parent.parent.data.getMenuId());
                                    LOG.debug("#### foundNode2.parent.parent.parent.parent.parent.data Path - 7: " + foundNode2.parent.parent.parent.parent.parent.data.toString());

                                    if (null != foundNode2.parent.parent.parent.parent.parent.parent) {
                                        searchResult2.add(foundNode2.parent.parent.parent.parent.parent.parent.data.getMenuId());
                                        LOG.debug("#### foundNode2.parent.parent.parent.parent.parent.parent.data Path - 8: " + foundNode2.parent.parent.parent.parent.parent.parent.data.toString());

                                        if (null != foundNode2.parent.parent.parent.parent.parent.parent.parent) {
                                            searchResult2.add(foundNode2.parent.parent.parent.parent.parent.parent.parent.data.getMenuId());
                                            LOG.debug("#### foundNode2.parent.parent.parent.parent.parent.parent.parent.data Path - 9: " + foundNode2.parent.parent.parent.parent.parent.parent.parent.data.toString());

                                            if (null != foundNode2.parent.parent.parent.parent.parent.parent.parent.parent) {
                                                searchResult2.add(foundNode2.parent.parent.parent.parent.parent.parent.parent.parent.data.getMenuId());
                                                LOG.debug("#### foundNode2.parent.parent.parent.parent.parent.parent.parent.parent.data Path - 10: " + foundNode2.parent.parent.parent.parent.parent.parent.parent.parent.data.toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2-2. 찾아진 노드의 모든 직속 부모 node의 키 값업 추출한 값에서 중복된 키 값을 제거한다.
        List<String> searchResult3 = new ArrayList<String>(new HashSet<String>(searchResult2)); // // 찾아낸 node의 모든 부모 노드 key 값(중복 포함)에서 중복 제외된 key 값 추출.
        LOG.debug("#### searchResult3.toString() : " + searchResult3.toString());



        // 3. 반환할 완전한 구조의 Tree 구조에 맞추어진 ArrayList로 만들어 반환한다.
        LOG.debug("#### 확인 ==>> ");
        List<FwmenuVO> dataTemp = new ArrayList<FwmenuVO>();

        Iterator iter = treeRoot.iterator();
        while (iter.hasNext()) {


            TreeNode<FwmenuVO> node = (TreeNode<FwmenuVO>) iter.next();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < node.getLevel(); i++) {
                sb.append("  ");
            }

            if( null != node.data ){

                node.data.setIsLeaf( String.valueOf( node.isLeaf() ) );
                node.data.setDepth( String.valueOf( node.getLevel() ));
                node.data.setLpadMenuNm(sb.toString() + node.data.getMenuNm());
                node.data.setLpadMenuNm2(sb.toString() + node.data.getMenuNm());
            }

            if (!node.isRoot()) {

                String searchMenuNm = fwmenuVO.getMenuNm();
                searchMenuNm = StringUtils.defaultIfBlank( searchMenuNm , fwmenuVO.getLpadMenuNm() );
                searchMenuNm = StringUtils.defaultIfBlank( searchMenuNm , fwmenuVO.getLpadMenuNm2() );
                searchMenuNm = StringUtils.defaultIfBlank( searchMenuNm , "" );
                String searchMenuId = StringUtils.defaultIfBlank( fwmenuVO.getMenuId() , "");
                String searchDepth = StringUtils.defaultIfBlank( fwmenuVO.getDepth() , "");
                String searchUrl = StringUtils.defaultIfBlank( fwmenuVO.getUrl() , "");

                if( null != searchResult3 && 0 < searchResult3.size() ){

                    // 3-1. 검색된 키(MenuId) 값이 있는 경우의 처리.
                    for(String findStr : searchResult3){ // 중복제거된 키값을만 data.add 한다.

                        if( node.data.getMenuId().equals(findStr) ){

                            dataTemp.add(node.data);
//                            LOG.debug("#### node.isRoot() ==>> searchResult3 : " + node.isRoot() + "  :  " + sb.toString() + node.data.toString());
                        }
                    }
                }else{
                    //화면에서 검색 조건( 부모노드를 조회할 필요없는 검색 == 필터링 )

//                    LOG.debug("#### node.isRoot() : " + node.isRoot() + "  :  " + sb.toString() + "  /  " + node.data.toString());

                    if( (StringUtils.isNoneBlank( searchMenuNm ) && (node.data.getMenuNm()).contains(searchMenuNm) )
                            || (StringUtils.isNoneBlank( searchMenuId ) && (node.data.getMenuId()).contains(searchMenuId) )
                            || (StringUtils.isNoneBlank( searchDepth ) && (node.data.getDepth()).contains(searchDepth) )
                            || (StringUtils.isNoneBlank( searchUrl ) && (node.data.getUrl()).contains(searchUrl) )
                            ){
                        dataTemp.add(node.data);

                    }else{
                        if( (StringUtils.isBlank( searchMenuNm ))
                                && (StringUtils.isBlank( searchMenuId ))
                                && (StringUtils.isBlank( searchDepth ))
                                && (StringUtils.isBlank( searchUrl ))
                                ){

                            dataTemp.add(node.data);
                        }
                    }

                }
            }
        }




        List<FwmenuVO> data = new ArrayList<FwmenuVO>();
        int rowNumber = 0;
        int dataSize = 0; // 화면으로 넘길 row 개수.
        for( int i=0; i<dataTemp.size();i++ ) {
            FwmenuVO fm = dataTemp.get(i);
            if( i >= fwmenuVO.getFirstIndex() && i < fwmenuVO.getLastIndex() ) {
                data.add(fm);
            }
        }

        return data;


//		List<FwmenuVO> data = fwmenuMapper.listFwmenu(fwmenuVO);
//		return data;
    }

    @Override
    public Integer getFwmenuCnt(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("getFwmenuCnt");
        return fwmenuMapper.getFwmenuCnt(fwmenuVO);
    }

    @Override
    public FwmenuVO getFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("getFwmenu");
        FwmenuVO data = fwmenuMapper.getFwmenu(fwmenuVO);
        return data;
    }


    @Override
    public int saveFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("saveFwmenu");

        // PK Key 값 여부에따라 분기처리.( or MERGE INTO )
        if ("0".equals(fwmenuVO.getUbseq())) {
            String pkUbseq =  KeyUtil.getUbseq("FWMENU", "ubseq");
            fwmenuVO.setUbseq(pkUbseq);
        }

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuVO); // 객체 전체를 복사 하는 경우. fwmenuVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwmenuVO.setSessionUserId(loginVO.getSessionUserId());

        return fwmenuMapper.saveFwmenu(fwmenuVO);
    }


    @Override
    public String insertFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("insertFwmenu");
        String returnResult = "";
        if (StringUtils.isNoneBlank(fwmenuVO.getMenuId())) {

        	String pkUbseq =  KeyUtil.getUbseq("FWMENU", "ubseq");
            fwmenuVO.setUbseq(pkUbseq);

//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuVO); // session의 정보를 VO에 추가.

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuVO); // 객체 전체를 복사 하는 경우. fwmenuVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다.
//        fwmenuVO.setSessionUserId(loginVO.getSessionUserId());

            int sqlResultInt = fwmenuMapper.insertFwmenu(fwmenuVO);
            if (0 < sqlResultInt) {
                returnResult = pkUbseq;
            }
        }

        return returnResult;
    }


    @Override
    public int updateFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("updateFwmenu");
//        LoginInfo loginInfo = new LoginInfo();
//		  loginInfo.putSessionToVo(fwmenuVO); // session의 정보를 VO에 추가. 

//        LoginVO loginVO = (LoginVO) loginInfo.getLoginInfo();
//	      //BeanUtils.copyProperties(loginVO,fwmenuVO); // 객체 전체를 복사 하는 경우. fwmenuVO에서 loginVO와 일치하는 모든 필드의 값을 복사한다. 
//        fwmenuVO.setSessionUserId(loginVO.getSessionUserId());

        int sqlResultInt = fwmenuMapper.updateFwmenu(fwmenuVO);
        return sqlResultInt;
    }


    @Override
    public int deleteFwmenu(FwmenuVO fwmenuVO) throws Exception {
        LOG.debug("deleteFwmenu");
        return fwmenuMapper.deleteFwmenu(fwmenuVO);
    }


}
