package com.hsck.ubfw.component.cmCfggrpid.vo;

import java.io.Serializable;

import lombok.Data;

import com.hsck.ubfw.component.comm.vo.BaseVO;
import org.apache.commons.lang3.StringUtils;


/**
 * CM_CFGGRPID 테이블에 대응되는 VO 클래스입니다.
 * 
 * @author AA
 * @since 0000.00.00.
 */
public @Data class CmCfggrpidVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String treeId;
	private String treeUpId;
	private String menuDepth;
	private String level;
	private String isLeaf;
	private String expanded;
	private String loaded;

	private String grpType; /*
							 * cfgbasecd grpType 참조 B:건물 J:조직 C:고객유형 D:문서 F:파일
							 * R:요금항목 O:점용위치 [Size=12] :
							 */
	private String grpId; /* 그룹ID [Size=16] : */
	//private String ubseq; /* 자료순번 [Size=22] : */
	private String upGrpId; /* 상위ID [Size=10] : */
	private String grpNm; /* 그룹명 [Size=64] : */
	private String depth="0"; /* 레벨 [Size=22] : */
	private String showSn="1"; /* 출력순번 */
	private String useYn="N"; /* 사용여부 [Size=1] : 'Y' */
	private String manNo; /* 관리번호 [Size=12] : */
	private String regId = "DEFAULT_ID"; /* 등록자 [Size=32] : */
	private String updId = "DEFAULT_ID"; /* 변경자 [Size=32] : */
	private String regTm; /* 등록시간 [Size=14] : */
	private String updTm; /* 수정시간 [Size=14] : */

	private String grpName; /* 그룹명 [Size=64] : */
	private String rootGrpid; /* 그룹ID [Size=16] : */
	
	/* 검색 영역 parameters */
	private String searchGrpType; /*
								 * 검색 필드 : cfgbasecd grpType 참조 B:건물 J:조직 C:고객유형
								 * D:문서 F:파일 R:요금항목 O:점용위치
								 */
	private String searchGrpId; /* 검색 필드 : 그룹ID */
	private String searchUbseq; /* 검색 필드 : 자료순번 */
	private String searchUpGrpId; /* 검색 필드 : 상위ID */
	private String searchGrpNm; /* 검색 필드 : 그룹명 */
	private String searchDepth; /* 검색 필드 : 레벨 */
	//private String searchUseYn; /* 검색 필드 : 사용여부 */
	private String searchManNo; /* 검색 필드 : 관리번호 */
	private String searchRegId; /* 검색 필드 : 등록자 */
	private String searchUpdId; /* 검색 필드 : 변경자 */
	private String searchRegTm; /* 검색 필드 : 등록시간 */
	private String searchUpdTm; /* 검색 필드 : 수정시간 */

	public void setUpGrpId(String upGrpId){
		if(StringUtils.isBlank( upGrpId ) ){
			this.upGrpId = "ROOT";
		}else{
			this.upGrpId = upGrpId;
		}
	}

	public void setDepth(String depth){
		if( depth == null ){
			this.depth = "0";
		}else{
			this.depth = depth;
		}
	}
	public void setShowSn(String showSn){
		if( showSn == null ){
			this.showSn = "0";
		}else{
			this.showSn = showSn;
		}
	}
	public void setUseYn(String useYn){
		if(StringUtils.isBlank( useYn ) ){
			this.useYn = "Y";
		}else{
			this.useYn = useYn;
		}
	}

	/**
	 * Tree 구조를 만들때 부모 노드를 찾는 목적으로 사용.
	 * this 가 부모레벨.
	 * @param findVO 자식 노드( 정보를 이용하여 부모노드를 검색 )
	 * @return
	 */
	public boolean initContains( CmCfggrpidVO findVO ){
		boolean returnObj = false;


		if( !(this.getTreeId()).equals( "-" ) ){

			if( (this.getGrpType()).equals( findVO.getGrpType() ) ){
				if( (this.getTreeId()).equals( findVO.getTreeUpId() ) ){
						returnObj = true;
				}
			}
		}
		return returnObj;
	}


	/**
	 * Tree 구조를 만들때 자식 노드를 찾는 목적으로 사용.
	 * this 가 부모레벨.
	 * @param findVO
	 * @return
	 */
	public boolean initContainsChildren(CmCfggrpidVO findVO) {
		boolean returnObj = false;

		if( !(this.getTreeId()).equals( "-" ) ){

			if( (this.getGrpType()).equals( findVO.getGrpType() ) ){
				if( (this.getTreeId()).equals( findVO.getTreeId() ) ){
					returnObj = true;
				}
			}
		}
		return returnObj;
	}

	public String toStringMy() {
		return "CmCfggrpidVO{" +
				"menuDepth='" + menuDepth + '\'' +
				", level='" + level + '\'' +
				", isLeaf='" + isLeaf + '\'' +
				", treeId='" + treeId + '\'' +
				", treeUpId='" + treeUpId + '\'' +
				", grpType='" + grpType + '\'' +
				", depth='" + depth + '\'' +
				", grpNm='" + grpNm + '\'' +
				", grpId='" + grpId + '\'' +
				", upGrpId='" + upGrpId + '\'' +
				", expanded='" + expanded + '\'' +
				", loaded='" + loaded + '\'' +
				", showSn='" + showSn + '\'' +
				", useYn='" + useYn + '\'' +
				", manNo='" + manNo + '\'' +
				", ubseq='" + getUbseq() + '\'' +
				", grpName='" + grpName + '\'' +
				", rootGrpid='" + rootGrpid + '\'' +

				"searchGrpType='" + searchGrpType + '\'' +
				", searchGrpId='" + searchGrpId + '\'' +
				", searchUbseq='" + searchUbseq + '\'' +
				", searchUpGrpId='" + searchUpGrpId + '\'' +
				", searchGrpNm='" + searchGrpNm + '\'' +
				", searchDepth='" + searchDepth + '\'' +
				'}';
	}
}
