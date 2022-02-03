package com.hsck.ubfw.component.com.cmm.util;

import egovframework.rte.psl.dataaccess.util.CamelUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LSW on 2017-03-14.
 */
public class VoUtil {
    public Object parameterMapToVoConvert(Map parameterMap, Object vo) throws Exception {

//        HashMap pObject =  new HashMap();

        BeanUtils.populate(vo, parameterMap);

        Iterator<String> pObjectIter =  parameterMap.keySet().iterator();

        HashMap<String,Object> pObjectMap =  new HashMap<String,Object>();
        while(pObjectIter.hasNext()) {

            String keyNm = (String) pObjectIter.next();
            Object val = parameterMap.get(keyNm);

//            if( pObject.get(keyNm) instanceof String ){
//                val = StringUtils.defaultIfBlank( (String)val , null );
//            } // 20170509 정산작업 실행 처리시 오류 발생하어 주석으로 막음!!

//            String val = pObject.get(keyNm).toString();

            pObjectMap.put(CamelUtil.convert2CamelCase(keyNm) , val);
        }

        BeanUtils.populate(vo, pObjectMap);

        return vo;
    }

}
